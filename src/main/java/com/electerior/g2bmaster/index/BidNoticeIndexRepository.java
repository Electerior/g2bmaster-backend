package com.electerior.g2bmaster.index;

import com.electerior.g2bmaster.attachment.AttachmentsHash;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * 공고 검색 색인 저장소 — {@code bid_notice} 에 대한 모든 SQL.
 *
 * <p><b>JPA 엔티티가 없는 이유.</b> 이 테이블에 하는 일은 둘뿐이다: (1) 적재기의 배치 upsert,
 * (2) 검색의 동적 SQL. 어느 쪽도 엔티티 수명주기를 타지 않는다 — upsert 를 {@code save()} 로
 * 하면 행마다 SELECT 가 한 번씩 더 나가고(수천 건이면 그게 적재 시간의 대부분이다), 검색은
 * 필터 조합이 열 가지가 넘어 Criteria API 로 쓰면 SQL 을 읽을 수 없게 된다.
 * {@code SavedNoticeRepository} 가 같은 이유로 같은 선택을 했다.
 *
 * <p>대신 스키마 계약은 {@code BidNoticeIndexRepositoryTest} 가 지킨다 — 전 컬럼을 SELECT 해
 * 마이그레이션과 코드가 어긋나면 테스트가 먼저 깨진다.
 */
@Repository
public class BidNoticeIndexRepository {

	/**
	 * 검색 한 페이지의 상한. {@code SearchCriteria.MAX_PER_PAGE} 와 같은 값이며 이유도 같다 —
	 * 이 위로는 응답이 수십 MB가 되어 브라우저가 먼저 죽는다.
	 */
	public static final int MAX_LIMIT = 500;

	/**
	 * 목록에 실어 보내는 컬럼.
	 *
	 * <p>{@code notice_body} 가 <b>통째로는 빠져 있다</b>. 검색 대상 텍스트라 길고(수 KB),
	 * 목록 20건이면 그것만으로 응답이 수백 KB가 된다. 대신 미리보기 300자만 잘라 보내고
	 * 전문은 상세 조회({@link #findOne})에서만 준다.
	 */
	private static final String LIST_COLUMNS = """
			n.id, n.source, n.notice_order, n.notice_name, n.category, n.state, n.business_division,
			n.region, n.demand_institution_code, n.demand_institution_name,
			n.notice_institution_code, n.notice_institution_name, n.before_spec_rgst_no,
			n.product_list, n.detail_product_code, n.lowest_bid_rate, n.price_detail,
			n.created_date, n.close_date, n.updated_at, n.officer_name, n.officer_contact,
			n.ai_summary, n.attachment_urls, n.source_url,
			n.source_ext, n.g2b_pblanc_no, n.g2b_pblanc_odr,
			n.documents_indexed_at,
			LEFT(COALESCE(n.notice_body, ''), 300) AS body_preview
			""";

	/**
	 * 조인이 없다 — 기관명이 색인 안에 있기 때문이다({@code V8} 마이그레이션 주석 참고).
	 *
	 * <p>원래는 {@code dm_institution} 을 두 번 LEFT JOIN 해 이름을 붙였다. 그 표가 비어
	 * 있고(0행) 채울 API 마저 폐기된 데다, 사전규격은 애초에 기관코드가 없어 조인할 키가
	 * 없었다. 결과는 화면의 발주기관 칸이 코드(5795000)이거나 공백인 것이었다.
	 * 기관명은 공고 응답에 매번 같이 오므로 적재 때 그대로 담는 편이 정확하고 싸다.
	 */
	private static final String LIST_FROM = """
			  FROM bid_notice n
			""";

	/**
	 * upsert 가 갱신하는 컬럼.
	 *
	 * <p><b>{@code ai_summary} 와 {@code id} 가 없는 것이 핵심이다.</b> AI 요약은 별도
	 * 파이프라인이 채우므로 재적재가 지워서는 안 된다. 목록에 없는 컬럼은 최초 INSERT 때만
	 * 값이 들어가고 그 뒤로는 적재기가 건드리지 않는다.
	 *
	 * <p>{@code notice_order} 도 여기 없다 — 아래 {@link #buildUpsertSql()} 이 <b>맨 마지막에</b>
	 * 따로 붙인다. 이유는 그쪽 주석 참고.
	 */
	private static final List<String> UPSERT_COLUMNS = List.of(
			"notice_name", "category", "state", "business_division",
			"demand_institution_code", "demand_institution_name",
			"notice_institution_code", "notice_institution_name", "before_spec_rgst_no",
			"product_list", "detail_product_code", "lowest_bid_rate", "price_detail",
			"created_date", "close_date", "officer_name", "officer_contact",
			"notice_body", "attachment_urls", "source_url",
			"source_ext", "g2b_pblanc_no", "g2b_pblanc_odr");

	private final NamedParameterJdbcTemplate jdbc;

	public BidNoticeIndexRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	// ── 적재 ────────────────────────────────────────────────────────────────

	/**
	 * 배치 upsert. 이미 있는 공고번호는 <b>차수가 같거나 높을 때만</b> 덮어쓴다.
	 *
	 * @return 영향받은 행 수 합계(MySQL 은 INSERT 를 1, UPDATE 를 2로 세므로 건수와 다르다)
	 */
	public int upsertAll(List<BidNoticeRow> rows) {
		if (rows == null || rows.isEmpty()) {
			return 0;
		}
		SqlParameterSource[] batch = rows.stream().map(BidNoticeIndexRepository::bind)
				.toArray(SqlParameterSource[]::new);
		int[] affected = jdbc.batchUpdate(buildUpsertSql(), batch);
		int total = 0;
		for (int count : affected) {
			// executeBatch 는 건별 결과를 모를 때 SUCCESS_NO_INFO(-2)를 준다. 음수를 그대로
			// 더하면 "-2건 색인" 같은 로그가 나오므로 0으로 접는다.
			total += Math.max(count, 0);
		}
		return total;
	}

	/**
	 * upsert SQL.
	 *
	 * <p>두 가지가 이 SQL 의 전부다.
	 *
	 * <p><b>(1) 차수 역행 방지.</b> ERD 의 PK 는 공고번호 하나라 한 공고에 행이 하나뿐이고
	 * 그 행은 최신 차수를 담아야 한다. 그런데 적재는 날짜창을 나눠 병렬로 도는 탓에 000차가
	 * 001차보다 <em>나중에</em> 도착하는 일이 실제로 있다. 그대로 두면 정정 전 내용이 정정 후를
	 * 덮어써 화면이 낡은 마감일시를 보여준다. 그래서 모든 갱신에
	 * {@code IF(new.notice_order >= bid_notice.notice_order, …)} 를 건다.
	 *
	 * <p><b>(2) {@code notice_order} 를 맨 마지막에 갱신한다.</b> MySQL 은
	 * {@code ON DUPLICATE KEY UPDATE} 의 대입을 <b>왼쪽부터 차례로</b> 수행하고, 뒤 대입은 앞
	 * 대입의 결과를 본다. 차수를 먼저 올려 버리면 그 다음 컬럼들의 비교가 이미 새 차수끼리의
	 * 비교가 되어 가드가 통째로 무력해진다 — 항상 참이 된다. 순서가 곧 정확성이다.
	 *
	 * <p><b>(3) {@code region} 만 '빈 값으로 덮지 않기' 규칙이 하나 더 붙는다.</b> 참가가능지역은
	 * 입찰공고 목록이 아니라 <b>별도 오퍼레이션</b>에서 온다. 입찰공고 적재분은 지역을 모르므로
	 * 빈 문자열을 들고 오는데, 그게 지역 적재분이 채워 둔 값을 지우면 지역 필터가 조용히
	 * 비어 버린다. 값이 있을 때만 덮어쓴다.
	 */
	static String buildUpsertSql() {
		String columns = String.join(", ", allInsertColumns());
		String values = allInsertColumns().stream().map(c -> ":" + toCamel(c)).reduce((a, b) -> a + ", " + b)
				.orElseThrow();

		StringBuilder updates = new StringBuilder();
		for (String column : UPSERT_COLUMNS) {
			updates.append("  ").append(column).append(" = IF(")
					.append(guard()).append(", new.").append(column)
					.append(", bid_notice.").append(column).append("),\n");
		}
		// 지역: 가드 + '빈 값이 아닐 것'.
		updates.append("  region = IF(").append(guard())
				.append(" AND new.region <> '', new.region, bid_notice.region),\n");
		updates.append("  updated_at = IF(").append(guard())
				.append(", NOW(6), bid_notice.updated_at),\n");

		// 첨부가 바뀌었으면 이 공고의 첨부 색인을 무효로 돌린다.
		//
		// **반드시 attachments_hash 보다 먼저다.** (2)와 같은 함정이다 — 해시를 먼저 대입하면
		// 그 다음 줄의 비교가 '새 해시 <=> 새 해시' 가 되어 항상 같다고 나오고, 무효화가
		// 영원히 일어나지 않는다. 조용히 틀리는 종류라 테스트로 순서를 고정해 둔다.
		//
		// <=> 는 NULL 안전 비교다. 첨부가 없다가 생긴 경우(NULL → 값)도 '달라졌다'로 잡아야
		// 하는데, 보통의 <> 는 NULL 이 끼면 결과가 NULL 이라 IF 가 거짓 가지로 빠진다.
		updates.append("  documents_indexed_at = IF(").append(guard())
				.append(" AND NOT (new.attachments_hash <=> bid_notice.attachments_hash)")
				.append(", NULL, bid_notice.documents_indexed_at),\n");
		updates.append("  attachments_hash = IF(").append(guard())
				.append(", new.attachments_hash, bid_notice.attachments_hash),\n");
		// 반드시 마지막 — 위 (2) 참고.
		updates.append("  notice_order = IF(").append(guard())
				.append(", new.notice_order, bid_notice.notice_order)");

		return "INSERT INTO bid_notice (" + columns + ")\nVALUES (" + values + ")\nAS new\n"
				+ "ON DUPLICATE KEY UPDATE\n" + updates;
	}

	/** 차수 역행 방지 조건. 문자열 비교로 충분하다 — 차수는 '000','001' 처럼 폭이 고정이다. */
	private static String guard() {
		return "new.notice_order >= bid_notice.notice_order";
	}

	private static List<String> allInsertColumns() {
		List<String> columns = new ArrayList<>();
		columns.add("id");
		// PK 의 두 번째 성분(V13). upsert 의 중복 판정이 (id, source) 로 이뤄지므로
		// INSERT 목록에 반드시 있어야 한다 — 빠지면 DEFAULT 'G2B' 로 들어가 소스가 섞인다.
		columns.add("source");
		columns.add("notice_order");
		columns.add("region");
		columns.addAll(UPSERT_COLUMNS);
		// UPSERT_COLUMNS 에 넣지 않는다 — 그러면 일반 루프가 대입 순서를 정하게 되어
		// documents_indexed_at 보다 앞서 버린다. 갱신은 buildUpsertSql 이 순서를 지켜 붙인다.
		columns.add("attachments_hash");
		return columns;
	}

	private static String toCamel(String snake) {
		StringBuilder out = new StringBuilder();
		boolean upper = false;
		for (char c : snake.toCharArray()) {
			if (c == '_') {
				upper = true;
				continue;
			}
			out.append(upper ? Character.toUpperCase(c) : c);
			upper = false;
		}
		return out.toString();
	}

	private static SqlParameterSource bind(BidNoticeRow row) {
		return new MapSqlParameterSource()
				.addValue("id", row.id())
				.addValue("source", row.sourceName())
				.addValue("noticeOrder", row.noticeOrder())
				.addValue("noticeName", row.noticeName())
				.addValue("category", row.categoryName())
				.addValue("state", row.stateName())
				.addValue("businessDivision", row.businessDivisionName())
				.addValue("region", row.region() == null ? "" : row.region())
				.addValue("demandInstitutionCode", row.demandInstitutionCode())
				.addValue("demandInstitutionName", row.demandInstitutionName())
				.addValue("noticeInstitutionCode", row.noticeInstitutionCode())
				.addValue("noticeInstitutionName", row.noticeInstitutionName())
				.addValue("beforeSpecRgstNo", row.beforeSpecRgstNo())
				// JSON 컬럼에 null 을 넣을 때는 타입을 명시해야 한다. 안 그러면 드라이버가
				// 문자열 'null' 로 보내 JSON 파싱 오류가 난다.
				.addValue("productList", row.productList(), Types.VARCHAR)
				.addValue("detailProductCode", row.detailProductCode())
				.addValue("lowestBidRate", row.lowestBidRate())
				.addValue("priceDetail", row.priceDetail(), Types.VARCHAR)
				.addValue("createdDate", row.createdDate())
				.addValue("closeDate", row.closeDate())
				.addValue("officerName", row.officerName())
				.addValue("officerContact", row.officerContact())
				.addValue("noticeBody", row.noticeBody())
				.addValue("attachmentUrls", row.attachmentUrls(), Types.VARCHAR)
				// 행이 아니라 여기서 만든다. 저장되는 JSON 문자열 그 자체에서 뽑으므로
				// 저장값과 해시가 어긋날 수 없다(AttachmentsHash 주석 참고).
				.addValue("attachmentsHash", AttachmentsHash.of(row.attachmentUrls()))
				.addValue("sourceUrl", row.sourceUrl())
				.addValue("sourceExt", row.sourceExt(), Types.VARCHAR)
				.addValue("g2bPblancNo", row.g2bPblancNo())
				.addValue("g2bPblancOdr", row.g2bPblancOdr());
	}

	/**
	 * 참가가능지역만 따로 갱신한다.
	 *
	 * <p>지역은 입찰공고 목록이 아니라 별도 오퍼레이션에서 오므로 upsert 경로를 타지 않는다.
	 * <b>아직 색인에 없는 공고에 대한 갱신은 0행에 걸리고 그대로 버려진다</b> — 정상이다.
	 * 같은 주기에서 입찰공고를 먼저 적재하고 지역을 나중에 적재하므로 대개는 맞물리고,
	 * 어긋난 건은 다음 주기의 겹침 구간에서 다시 시도된다.
	 *
	 * @param regions 공고번호 → 지역 문자열
	 * @return 실제로 갱신된 행 수
	 */
	public int updateRegions(Map<String, String> regions, NoticeSource source) {
		if (regions == null || regions.isEmpty()) {
			return 0;
		}
		// 지역 오퍼레이션은 소스별로 따로 있다(나라장터·누리장터). PK 가 (id, source) 이므로
		// 소스 조건 없이 id 로만 갱신하면 번호가 겹치는 다른 소스의 행까지 물든다.
		String sourceName = (source == null ? NoticeSource.G2B : source).name();
		SqlParameterSource[] batch = regions.entrySet().stream()
				.map(entry -> (SqlParameterSource) new MapSqlParameterSource()
						.addValue("id", entry.getKey())
						.addValue("source", sourceName)
						.addValue("region", entry.getValue()))
				.toArray(SqlParameterSource[]::new);

		// 값이 같으면 updated_at 을 건드리지 않는다 — 매 주기마다 전 행의 갱신 시각이
		// 밀리면 '최근 변경' 정렬이 의미를 잃는다.
		int[] affected = jdbc.batchUpdate("""
				UPDATE bid_notice
				   SET region = :region, updated_at = NOW(6)
				 WHERE id = :id AND source = :source AND region <> :region
				""", batch);
		int total = 0;
		for (int count : affected) {
			total += Math.max(count, 0);
		}
		return total;
	}

	// ── 검색 ────────────────────────────────────────────────────────────────

	/**
	 * 한 페이지. {@code where} 는 {@link BidNoticeQueryBuilder} 가 만든 것을 그대로 받는다.
	 *
	 * @param relevanceSorted 관련도 순으로 정렬하는가. 그렇다면 전문검색 전용 2단 질의를 쓴다
	 *                        ({@link #fullTextSql} 참고)
	 */
	public List<Map<String, Object>> search(BidNoticeQueryBuilder.Where where, String orderBy,
			int limit, int offset, boolean relevanceSorted) {
		int page = Math.min(Math.max(limit, 1), MAX_LIMIT);
		int skip = Math.max(offset, 0);
		MapSqlParameterSource params = new MapSqlParameterSource(where.params())
				.addValue("limit", page)
				.addValue("offset", skip);

		String sql;
		if (where.unionsAttachments()) {
			// 관련도 정렬일 때만 안쪽을 잘라도 된다 — 다른 정렬은 매치 전체를 세워야 하므로
			// 상한을 걸면 뒤쪽 공고가 페이지에서 사라진다.
			String innerOrder = "";
			if (relevanceSorted) {
				params.addValue("innerLimit", skip + page);
				innerOrder = "\n         ORDER BY relevance DESC\n         LIMIT :innerLimit";
			}
			sql = "SELECT " + LIST_COLUMNS
					+ ", t.relevance AS relevance, t.notice_hit AS notice_hit, t.doc_hit AS doc_hit"
					+ "\n  FROM (" + candidateSql(where) + innerOrder + ") t"
					+ "\n  JOIN bid_notice n ON n.id = t.fid AND n.source = t.fsource"
					+ "\n ORDER BY " + orderBy + "\n LIMIT :limit OFFSET :offset";
		}
		else if (relevanceSorted && where.fullText()) {
			params.addValue("innerLimit", skip + page);
			sql = fullTextSql(where, orderBy);
		}
		else {
			sql = "SELECT " + LIST_COLUMNS + where.relevanceSelect() + fromClause(where)
					+ where.sql() + attachmentExcludeCondition(where)
					+ "\n ORDER BY " + orderBy + "\n LIMIT :limit OFFSET :offset";
		}
		return jdbc.queryForList(sql, params);
	}

	// ── 첨부 본문까지 보는 검색 ─────────────────────────────────────────────

	/**
	 * 첨부 본문 브랜치를 뽑아내는 파생 테이블.
	 *
	 * <p><b>왜 {@code OR} 이 아니라 {@code UNION ALL} 인가.</b> 읽기에는
	 * {@code MATCH(공고) OR EXISTS(MATCH(첨부))} 가 자연스럽지만, 그렇게 쓰면 두 FULLTEXT
	 * 인덱스 중 <b>어느 쪽도 구동 경로가 되지 못한다</b> — 옵티마이저가 {@code bid_notice} 를
	 * 전수 훑으며 행마다 상관 서브쿼리를 돌린다. 브랜치를 갈라 놓으면 각자 자기 인덱스를 탄다.
	 *
	 * <p>실측(2026-08-12, {@code bid_notice} 45,736행 · {@code bid_notice_document} done 3,704행,
	 * 검색어 '서버' → 공고 571건 · 첨부 502행, 마감 전 + 입찰문서 필터, 관련도순 20건):
	 * <pre>
	 *   공고 텍스트만(현행 2단 질의)          5.3ms
	 *   UNION ALL (공고 ∪ 첨부)              19.9ms   ← 이 경로
	 *   UNION + 첨부 제외 반조인             21.5ms
	 *   MATCH(공고) OR EXISTS(MATCH(첨부))  424.0ms   ← 80배. 쓰면 안 되는 형태
	 * </pre>
	 *
	 * <p><b>관련도는 공고 텍스트 점수만 쓴다.</b> 첨부에서만 걸린 행은 0점이라 관련도순에서
	 * 뒤로 간다 — 제목·본문 매치가 더 강한 신호라는 판단이고, 덕분에 두 점수를 합산하느라
	 * GROUP BY 뒤에 다시 정렬하는 비용도 들지 않는다. 어느 쪽에서 걸렸는지는
	 * {@code notice_hit}/{@code doc_hit} 로 그대로 내려보내 화면이 표시한다.
	 *
	 * <p><b>첨부의 AND 는 파일 단위다.</b> {@code +"서버" +"스토리지"} 는 <em>한 파일 안에</em>
	 * 둘 다 있어야 걸린다. 규격서 하나가 곧 그 공고의 사양이라 실무 의미가 있고, 공고 단위로
	 * 접으려면 낱말마다 서브쿼리를 따로 걸어야 해서 비용이 낱말 수에 비례한다.
	 */
	static String candidateSql(BidNoticeQueryBuilder.Where where) {
		String filters = where.filterSql().isEmpty() ? "" : "\n             AND " + where.filterSql();
		String noticeBranch = "SELECT n.id AS fid, n.source AS fsource" + where.relevanceSelect()
				+ ", 1 AS notice_hit, 0 AS doc_hit"
				+ "\n             FROM bid_notice n"
				+ "\n            WHERE " + where.keywordSql() + filters;
		String docBranch = "SELECT n.id AS fid, n.source AS fsource, 0 AS relevance"
				+ ", 0 AS notice_hit, 1 AS doc_hit"
				+ "\n             FROM bid_notice_document d"
				+ "\n             JOIN bid_notice n ON n.id = d.notice_id AND n.source = d.source"
				+ "\n            WHERE d.status = 'done'"
				+ "\n              AND MATCH(d.body_text) AGAINST (:ftDocQuery IN BOOLEAN MODE)" + filters;

		String union = "\n           " + noticeBranch + "\n           UNION ALL\n           " + docBranch;
		String antiJoin = "";
		String antiWhere = "";
		if (where.excludesByAttachment()) {
			antiJoin = "\n         LEFT JOIN (" + attachmentExcludeSetSql() + ") xd"
					+ "\n                ON xd.notice_id = u.fid AND xd.source = u.fsource";
			antiWhere = "\n          WHERE xd.notice_id IS NULL";
		}
		return "\n         SELECT u.fid, u.fsource, MAX(u.relevance) AS relevance,"
				+ " MAX(u.notice_hit) AS notice_hit, MAX(u.doc_hit) AS doc_hit"
				+ "\n           FROM (" + union + "\n           ) u" + antiJoin + antiWhere
				+ "\n          GROUP BY u.fid, u.fsource";
	}

	/**
	 * 제외 낱말이 첨부에 들어 있는 공고 집합.
	 *
	 * <p>상관 {@code NOT EXISTS} 로 브랜치마다 거는 것보다 <b>한 번 뽑아 반조인</b>하는 편이 싸다 —
	 * 같은 조건의 count 로 실측 34.6ms → 24.2ms. 제외 낱말은 후보마다 달라지지 않으므로
	 * 집합을 한 번만 만들면 되는데, 상관 서브쿼리는 그것을 행마다 되풀이한다.
	 */
	private static String attachmentExcludeSetSql() {
		return "SELECT DISTINCT notice_id, source FROM bid_notice_document"
				+ "\n                     WHERE status = 'done'"
				+ "\n                       AND MATCH(body_text) AGAINST (:ftDocExclude IN BOOLEAN MODE)";
	}

	/** UNION 을 타지 않는 경로(키워드가 없거나 한 글자뿐)에도 첨부 제외는 걸어야 한다. */
	private static String fromClause(BidNoticeQueryBuilder.Where where) {
		if (!where.excludesByAttachment()) {
			return LIST_FROM;
		}
		return LIST_FROM + "  LEFT JOIN (" + attachmentExcludeSetSql() + ") xd"
				+ "\n         ON xd.notice_id = n.id AND xd.source = n.source\n";
	}

	private static String attachmentExcludeCondition(BidNoticeQueryBuilder.Where where) {
		if (!where.excludesByAttachment()) {
			return "";
		}
		return where.sql().isEmpty() ? "\n WHERE xd.notice_id IS NULL" : "\n   AND xd.notice_id IS NULL";
	}

	/**
	 * 관련도 정렬 전용 2단 질의.
	 *
	 * <p><b>왜 평범하게 못 쓰는가.</b> InnoDB 전문검색은 {@code ORDER BY <점수> LIMIT n} 을 보면
	 * 스토리지 엔진에 "점수순 상위 n건만 달라"는 힌트({@code Ft_hints: sorted, limit = n})를 내려
	 * 매치 전체를 올려 받지 않는다. 그런데 {@code ORDER BY} 에 <b>점수 말고 다른 것이 하나라도
	 * 붙으면</b> 그 힌트가 사라지고, 매치된 행을 전부 서버로 올려 정렬한다. 우리는 페이징
	 * 안정성 때문에 {@code n.id} 타이브레이커를 반드시 붙여야 하므로 정면충돌이다.
	 *
	 * <p>실측(bid_notice 20,403행, {@code '구매'} → 매치 4,752건):
	 * <pre>
	 *   ORDER BY rel DESC, id ASC          24.6ms   4,752행을 읽어 정렬
	 *   ORDER BY rel DESC (타이브레이커 X)   0.70ms      20행   ← 빠르지만 페이징이 흔들린다
	 *   2단 (아래)                          0.79ms      20행   ← 둘 다 얻는다
	 * </pre>
	 *
	 * <p><b>구조.</b> 안쪽은 점수만으로 정렬해 힌트를 살린 채 {@code offset + limit} 건의 id 만
	 * 뽑고, 바깥이 그 결과에 타이브레이커를 걸어 페이지를 자른다. 바깥 정렬 대상은 최대
	 * {@code offset + limit} 건이라 비용이 없다.
	 *
	 * <p>남는 한계: 안쪽 컷오프 경계에서 점수가 같은 행이 걸치면 어느 쪽이 창에 들어올지는
	 * 엔진이 정한다. 표가 바뀌지 않는 한 결정적이므로 실사용에서는 드러나지 않지만,
	 * '완전한' 안정성은 아니다 — 그 대가로 31배를 얻는다.
	 *
	 * <p>관련도 정렬이 <b>아닐</b> 때는 이 경로를 타지 않는다. 검색어가 있어도 최신순으로
	 * 정렬하면 어차피 매치 전체를 정렬해야 해서 힌트가 성립하지 않기 때문이다.
	 */
	private static String fullTextSql(BidNoticeQueryBuilder.Where where, String orderBy) {
		// relevanceSelect 는 ", MATCH(…) AS relevance" 형태다 — 식을 여기에 복제하지 않고
		// 그대로 이어 붙인다. 두 곳에 적으면 한쪽만 고쳐져 점수가 갈린다.
		//
		// ⚠ PK 는 (id, source) 복합키다(V13). 조인 키에서 source 를 빼면 같은 공고번호를 가진
		// 다른 소스의 행과 팬아웃되어 한 건이 여러 줄로 나온다 — 안쪽에서 두 컬럼을 다 들고 온다.
		String inner = "SELECT n.id AS fid, n.source AS fsource" + where.relevanceSelect() + LIST_FROM
				+ where.sql() + "\n         ORDER BY relevance DESC\n         LIMIT :innerLimit";
		return "SELECT " + LIST_COLUMNS + ", t.relevance AS relevance"
				+ "\n  FROM (" + inner + ") t"
				+ "\n  JOIN bid_notice n ON n.id = t.fid AND n.source = t.fsource"
				+ "\n ORDER BY " + orderBy
				+ "\n LIMIT :limit OFFSET :offset";
	}

	public int count(BidNoticeQueryBuilder.Where where) {
		// UNION 은 같은 공고를 두 브랜치에서 낼 수 있다. GROUP BY 로 접은 뒤에 세지 않으면
		// 제목과 규격서 양쪽에 걸린 공고가 총건수에서 두 번 세어져, 화면의 '12건'과 실제
		// 목록 길이가 어긋난다.
		String sql = where.unionsAttachments()
				? "SELECT COUNT(*) FROM (" + candidateSql(where) + "\n       ) c"
				: "SELECT COUNT(*)" + fromClause(where) + where.sql() + attachmentExcludeCondition(where);
		Integer total = jdbc.queryForObject(sql, new MapSqlParameterSource(where.params()), Integer.class);
		return total == null ? 0 : total;
	}

	/**
	 * 첨부 본문 색인 커버리지 — 검색 응답의 메타가 쓴다.
	 *
	 * <p><b>왜 이 숫자를 응답에 싣나.</b> 첨부까지 찾는다고 해 놓고 색인이 일부에만 있으면,
	 * 사용자는 "이 공고엔 그 낱말이 없구나"와 "아직 안 읽은 공고구나"를 구분할 수 없다.
	 * 커버리지를 같이 주면 화면이 그 경계를 말할 수 있다.
	 *
	 * <p>60초 캐시를 두는 이유는 값이 아니라 <b>비용</b> 때문이다 — 실측 6.3ms 로, 20ms 짜리
	 * 검색에 매번 붙이면 30% 를 커버리지 세는 데 쓴다. 색인은 30분 주기로 늘어나므로
	 * 1분 낡은 값이 화면에서 문제가 되지 않는다.
	 */
	public Map<String, Object> attachmentCoverage() {
		CoverageSnapshot snapshot = coverage;
		long now = System.currentTimeMillis();
		if (snapshot != null && now - snapshot.takenAt() < COVERAGE_TTL_MS) {
			return snapshot.value();
		}
		Map<String, Object> fresh = jdbc.queryForMap("""
				SELECT COUNT(*) AS totalNotices,
				       COUNT(documents_indexed_at) AS indexedNotices
				  FROM bid_notice
				""", new MapSqlParameterSource());
		Map<String, Object> value = Map.copyOf(fresh);
		coverage = new CoverageSnapshot(value, now);
		return value;
	}

	/** 커버리지 캐시 수명. 첨부 추출 워커가 30분 주기라 1분이면 충분히 새 값이다. */
	private static final long COVERAGE_TTL_MS = 60_000;

	/** {@code volatile} 하나면 충분하다 — 경합해서 두 번 세어도 결과가 같다. */
	private volatile CoverageSnapshot coverage;

	private record CoverageSnapshot(Map<String, Object> value, long takenAt) {}

	/**
	 * 상세 한 건 — 본문 전문 포함.
	 *
	 * <p>PK 가 {@code (id, source)} 라 같은 번호가 소스별로 최대 3행일 수 있다. 소스를
	 * 지정하지 않은 기존 호출(프론트 구계약)은 G2B → NURI → D2B 순으로 첫 행을 준다 —
	 * 어느 행이 올지 엔진에 맡기면 같은 URL 이 새로고침마다 다른 공고를 보여준다.
	 *
	 * @param source {@code null} 이면 소스 무지정(우선순위 픽)
	 * @return 없으면 {@code null}
	 */
	public Map<String, Object> findOne(String id, NoticeSource source) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		String sourceFilter = "";
		if (source != null) {
			params.addValue("source", source.name());
			sourceFilter = " AND n.source = :source";
		}
		List<Map<String, Object>> rows = jdbc.queryForList(
				"SELECT " + LIST_COLUMNS + ", n.notice_body" + LIST_FROM
						+ " WHERE n.id = :id" + sourceFilter
						+ "\n ORDER BY FIELD(n.source, 'G2B', 'NURI', 'D2B')\n LIMIT 1",
				params);
		return rows.isEmpty() ? null : new LinkedHashMap<>(rows.get(0));
	}

	/**
	 * 패싯 — 현재 조건에서 각 분류가 몇 건인지.
	 *
	 * <p>{@code column} 은 호출부가 고른 <b>고정 컬럼명</b>만 들어온다({@link BidNoticeSearchService}
	 * 의 상수). 사용자 입력을 여기에 넘기면 SQL 주입이 되므로 절대 넓히지 말 것.
	 */
	public List<Map<String, Object>> facet(BidNoticeQueryBuilder.Where where, String column, int limit) {
		// 패싯은 검색과 반드시 같은 후보 집합을 세야 한다 — 칩에 붙는 건수가 목록과 어긋나면
		// 그 화면은 거짓말을 하는 것이다. 그래서 검색과 똑같은 파생 테이블을 재사용한다.
		String sql = where.unionsAttachments()
				? "SELECT n." + column + " AS value, COUNT(*) AS count"
						+ "\n  FROM (" + candidateSql(where) + "\n       ) t"
						+ "\n  JOIN bid_notice n ON n.id = t.fid AND n.source = t.fsource"
						+ "\n GROUP BY n." + column + "\n ORDER BY count DESC\n LIMIT :facetLimit"
				: "SELECT n." + column + " AS value, COUNT(*) AS count"
						+ fromClause(where) + where.sql() + attachmentExcludeCondition(where)
						+ "\n GROUP BY n." + column + "\n ORDER BY count DESC\n LIMIT :facetLimit";
		return jdbc.queryForList(sql,
				new MapSqlParameterSource(where.params()).addValue("facetLimit", limit));
	}

	// ── 상태 전이 ───────────────────────────────────────────────────────────

	/**
	 * 마감이 지난 '입찰' 을 '마감' 으로 민다.
	 *
	 * <p>이 스위퍼가 없으면 {@code category} 는 적재 시점의 판정에 굳어 버려, 어제 적재한
	 * 공고가 오늘 마감돼도 계속 '입찰'로 검색된다. 화면의 '마감 전만 보기'가 거짓말을 하게 되는
	 * 지점이 정확히 여기다.
	 *
	 * <p>{@code close_date IS NOT NULL} 을 명시하는 이유: NULL 은 '마감일시 미상'이지 '지났다'가
	 * 아니다. SQL 비교에서 NULL 은 어차피 참이 안 되지만, 의도를 SQL 에 적어 둔다.
	 *
	 * @return 전이된 행 수
	 */
	public int sweepClosed() {
		return jdbc.update("""
				UPDATE bid_notice
				   SET category = '마감', updated_at = NOW(6)
				 WHERE category = '입찰'
				   AND close_date IS NOT NULL
				   AND close_date < NOW(6)
				""", new MapSqlParameterSource());
	}

	// ── 워터마크 ────────────────────────────────────────────────────────────

	/** @return 저장된 워터마크. 처음 도는 출처면 {@code null} */
	public LocalDateTime readWatermark(String source) {
		List<LocalDateTime> found = jdbc.queryForList(
				"SELECT watermark FROM bid_notice_sync_state WHERE source = :source",
				new MapSqlParameterSource("source", source), LocalDateTime.class);
		return found.isEmpty() ? null : found.get(0);
	}

	/** 성공 기록 — 워터마크를 전진시킨다. */
	public void recordSuccess(String source, LocalDateTime watermark, int rowCount, String note) {
		jdbc.update("""
				INSERT INTO bid_notice_sync_state
				  (source, watermark, last_run_at, last_success_at, last_result, last_row_count,
				   consecutive_failures)
				VALUES (:source, :watermark, NOW(6), NOW(6), :note, :rowCount, 0)
				AS new
				ON DUPLICATE KEY UPDATE
				  watermark = new.watermark,
				  last_run_at = new.last_run_at,
				  last_success_at = new.last_success_at,
				  last_result = new.last_result,
				  last_row_count = new.last_row_count,
				  consecutive_failures = 0
				""", new MapSqlParameterSource()
						.addValue("source", source)
						.addValue("watermark", watermark)
						.addValue("rowCount", rowCount)
						.addValue("note", note));
	}

	/**
	 * 실패 기록 — <b>워터마크는 그대로 둔다.</b>
	 *
	 * <p>실패했는데 워터마크를 전진시키면 그 구간의 공고가 영원히 색인에 안 들어온다.
	 * 다음 주기가 같은 구간을 다시 시도하게 두는 것이 유일하게 안전한 선택이다.
	 */
	public void recordFailure(String source, String reason) {
		jdbc.update("""
				INSERT INTO bid_notice_sync_state
				  (source, last_run_at, last_result, consecutive_failures)
				VALUES (:source, NOW(6), :reason, 1)
				AS new
				ON DUPLICATE KEY UPDATE
				  last_run_at = new.last_run_at,
				  last_result = new.last_result,
				  consecutive_failures = bid_notice_sync_state.consecutive_failures + 1
				""", new MapSqlParameterSource()
						.addValue("source", source)
						.addValue("reason", reason));
	}

	/** 운영 화면이 읽는 적재 현황 전체. */
	public List<Map<String, Object>> syncStates() {
		return jdbc.queryForList("""
				SELECT source, watermark, last_run_at, last_success_at, last_result,
				       last_row_count, consecutive_failures
				  FROM bid_notice_sync_state
				 ORDER BY source
				""", new MapSqlParameterSource());
	}

	/** 색인 규모 요약 — 소스×분류별 건수와 가장 최근 적재 시각. */
	public List<Map<String, Object>> indexSummary() {
		return jdbc.queryForList("""
				SELECT source, category, COUNT(*) AS count, MAX(updated_at) AS last_indexed_at
				  FROM bid_notice
				 GROUP BY source, category
				 ORDER BY source, category
				""", new MapSqlParameterSource());
	}
}
