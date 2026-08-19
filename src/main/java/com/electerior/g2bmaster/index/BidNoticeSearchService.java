package com.electerior.g2bmaster.index;

import com.electerior.g2bmaster.common.PagedResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * 공고 검색 — <b>로컬 색인만</b> 조회한다.
 *
 * <p>이 클래스에 나라장터 클라이언트가 주입되지 않는 것이 설계의 핵심이다. 검색 경로에서
 * 상류 호출을 물리적으로 불가능하게 해 둬야, 나중에 "여기서 한 번만 더 부르면 되는데" 하는
 * 유혹에 구조가 무너지지 않는다. 색인이 낡았다면 그것은 적재기의 문제이지 검색의 문제가 아니다.
 *
 * <p>기존 {@code BidAnnounceService} 와의 차이: 그쪽은 요청마다 나라장터를 3~수십 번 두드리고
 * 2시간 캐시로 버틴다. 이쪽은 인덱스 질의 한 번이라 캐시가 필요 없다.
 */
@Service
public class BidNoticeSearchService {

	private static final Logger log = LoggerFactory.getLogger(BidNoticeSearchService.class);

	private static final ObjectMapper JSON = JsonMapper.builder().build();

	/**
	 * 정렬 화이트리스트.
	 *
	 * <p>사용자 입력을 {@code ORDER BY} 에 그대로 넣으면 SQL 주입이다. 표에 있는 키만 받고
	 * 없는 키는 기본값으로 떨어뜨린다.
	 *
	 * <p>{@code close} 의 {@code IS NULL} 이 앞에 붙는 이유: 마감일시가 없는 행(계획 단계)이
	 * MySQL 기본 오름차순에서 맨 앞에 온다. '마감 임박 순'을 골랐는데 마감이 없는 것부터
	 * 나오면 정렬이 뒤집힌 것처럼 보인다 — NULL 을 항상 뒤로 보낸다.
	 */
	private static final Map<String, String> SORTS = Map.of(
			"created", "n.created_date %s",
			"close", "n.close_date IS NULL, n.close_date %s",
			"name", "n.notice_name %s",
			"updated", "n.updated_at %s",
			// price_detail JSON 을 생성 컬럼으로 승격한 것(V11)을 소스별 대표 금액으로 넓혔다
			// (V20260814113541). 식을 다시 인라인하면 ix_bid_notice_category_filter_amount 를
			// 못 쓰고 정렬이 filesort 로 떨어진다.
			"amount", "n.filter_amount %s",
			// 마진율도 같은 처방이다(V20260814132535). NULL 처리는 방향마다 다르므로
			// 여기에 IS NULL 을 박지 않는다 — {@link #nullsLastPrefix} 참고.
			"margin", "n.margin_rate %s",
			"relevance", "relevance %s");

	/**
	 * 정렬 키를 받쳐 주는 인덱스가 그 컬럼을 <b>어느 방향으로 선언했는가</b>.
	 *
	 * <p>타이브레이커 방향을 정하는 데 쓴다. InnoDB 보조 인덱스는 선행 컬럼을 어떻게 선언했든
	 * PK 접미({@code id})를 <b>항상 오름차순</b>으로 붙인다. 그래서 스캔 방향에 따라 id 순서가 정해진다:
	 *
	 * <pre>
	 *   요청 방향 == 인덱스 선언 방향  →  정방향 스캔  →  id 가 ASC 로 나온다
	 *   요청 방향 != 인덱스 선언 방향  →  역방향 스캔  →  id 가 DESC 로 나온다
	 * </pre>
	 *
	 * <p>타이브레이커를 그 순서와 다르게 적으면 같은 인덱스를 쓰고도 전체를 다시 정렬한다.
	 * 실측(bid_notice 20,403행)이 방향 하나에 얼마가 걸리는지 보여 준다:
	 *
	 * <pre>
	 *   created DESC + id ASC   →  Using index          (인덱스 (category, created_date DESC))
	 *   created DESC + id DESC  →  Using filesort, 8,738행
	 *   updated DESC + id DESC  →  Backward index scan, 20행   (인덱스 (updated_at) = ASC)
	 *   updated DESC + id ASC   →  Using filesort, 16,560행
	 * </pre>
	 *
	 * <p>즉 <b>전역으로 한 방향을 고를 수 없다</b> — 인덱스 선언이 키마다 다르기 때문이다.
	 * 표에 없는 키({@code close}, {@code name}, {@code relevance})는 받쳐 주는 인덱스가 없어
	 * 어느 쪽이든 filesort 이므로 기본값을 쓴다.
	 *
	 * <p>⚠ 이 표는 마이그레이션의 인덱스 선언과 짝이다. 인덱스 방향을 바꾸면 여기도 바꿔야 한다.
	 */
	private static final Map<String, String> INDEX_DIRECTION = Map.of(
			// V7: ix_bid_notice_category_created (category, created_date DESC)
			"created", "DESC",
			// V7: ix_bid_notice_updated (updated_at)  — 방향 미지정이므로 ASC
			"updated", "ASC",
			// V20260814113541: ix_bid_notice_category_filter_amount (category, filter_amount DESC)
			"amount", "DESC",
			// V20260814132535: ix_bid_notice_margin_rate (margin_rate DESC)
			"margin", "DESC");

	/**
	 * 대표 금액 후보 — <b>앞자리가 이긴다</b>.
	 *
	 * <p>생성 컬럼 {@code filter_amount}({@code V20260814113541})의 {@code COALESCE} 순서와
	 * 같은 표다. 필터·정렬은 그 컬럼을 보고, 화면에 적히는 금액과 종류는 이 표로 고른다 —
	 * 둘이 갈라지면 "이 금액으로 걸렀다"는 표시가 거짓이 된다.
	 *
	 * <p>순서의 근거는 마이그레이션 주석에 실측과 함께 적혀 있다. 요지는 추정가격에 가까운
	 * 것부터라는 것이다: 추정가격(G2B) → 배정예산(사전규격·누리) → 기준금액(누리 투찰 상한)
	 * → 기초예비가격(D2B).
	 */
	private static final List<String> AMOUNT_KEYS =
			List.of("estimatedPrice", "assignedBudget", "referenceAmount", "basicExpectedPrice");

	/** 관련도 정렬 키. 저장소가 전문검색 전용 경로를 탈지 판단하는 데도 쓴다. */
	private static final String RELEVANCE = "relevance";

	/** 마진율 정렬 키. NULL 을 뒤로 보내는 규칙이 이 키에만 걸린다({@link #nullsLastPrefix}). */
	private static final String MARGIN = "margin";

	/**
	 * 부가세율. 마진율의 분모(실추정가 = 대표금액 × 1.1)를 화면에 함께 내려주는 데 쓴다.
	 *
	 * <p>계산 자체는 여기가 아니라 생성 컬럼 {@code margin_rate}({@code V20260814132535})가 한다 —
	 * 정의가 두 곳에 있으면 반드시 갈라진다. 여기서 쓰는 것은 <b>같은 분모를 표시용으로</b>
	 * 복원하기 위해서이고, 그래서 상수도 마이그레이션과 같은 값이어야 한다.
	 * 전제는 그쪽 주석에 있다: 대표금액은 부가세 별도, 원가는 부가세 포함.
	 */
	private static final BigDecimal VAT = new BigDecimal("1.1");

	/** 단계 패싯의 이름. 이 축만 WHERE 가 다르다 — 이유는 {@link #facets} 주석. */
	private static final String STAGE_FACET = "category";

	/** 패싯을 뽑을 컬럼. 사용자 입력이 아니라 이 상수만 저장소로 넘어간다. */
	private static final Map<String, String> FACET_COLUMNS = Map.of(
			"category", "category",
			"division", "business_division",
			"region", "region",
			"state", "state",
			"source", "source");

	/** 지역 패싯 상한. 시·도 단위라 스무 개면 전부 덮는다. */
	private static final int FACET_LIMIT = 30;

	private final BidNoticeIndexRepository repository;

	public BidNoticeSearchService(BidNoticeIndexRepository repository) {
		this.repository = repository;
	}

	// ── 검색 ────────────────────────────────────────────────────────────────

	/**
	 * 한 페이지.
	 *
	 * @param includeAttachments 첨부 본문까지 검색 대상에 넣는가. {@code GET /api/search/notices} 는
	 *                           켠 채로, {@code GET /api/search/notices/text} 는 끈 채로 부른다
	 */
	public PagedResponse<Map<String, Object>> search(NoticeSearchRequest request, boolean includeAttachments) {
		BidNoticeQueryBuilder.Where where = buildWhere(request, includeAttachments);
		String sortKey = effectiveSortKey(request, where.fullText());
		String orderBy = orderBy(request, sortKey);

		int total = repository.count(where);
		// 관련도 정렬일 때만 저장소가 전문검색 전용 2단 질의를 쓴다 — 이유는 그쪽 주석 참고.
		List<Map<String, Object>> rows = repository.search(where, orderBy,
				request.perPageValue(), request.offset(), RELEVANCE.equals(sortKey));

		LocalDateTime now = LocalDateTime.now();
		List<Map<String, Object>> items = rows.stream().map(row -> shape(row, now)).toList();
		return new PagedResponse<>(items, total, request.pageValue(), request.perPageValue(),
				searchMeta(where, includeAttachments));
	}

	/**
	 * 응답 메타 — "첨부까지 봤는가, 어디까지 봤는가".
	 *
	 * <p>첨부 검색은 <b>색인된 만큼만</b> 동작한다. 그 사실을 응답에 적지 않으면 화면에서
	 * "그 낱말이 없는 공고"와 "아직 읽지 못한 공고"가 똑같이 보이고, 사용자는 규격서에
	 * 답이 있는 공고를 조용히 놓친다({@code bid_notice_document} 의 {@code needs_ocr} 과 같은 이유다).
	 *
	 * <p>스코프를 껐을 때도 메타를 낸다 — 프론트가 두 엔드포인트를 같은 렌더러로 그리는데
	 * 한쪽에만 칸이 있으면 분기가 생긴다.
	 */
	private Map<String, Object> searchMeta(BidNoticeQueryBuilder.Where where, boolean includeAttachments) {
		Map<String, Object> meta = new LinkedHashMap<>();
		Map<String, Object> attachment = new LinkedHashMap<>();
		attachment.put("scope", includeAttachments);
		// 스코프를 켰어도 실제로 첨부를 뒤졌는지는 별개다 — 검색어가 없거나 한 글자뿐이면 안 뒤진다.
		attachment.put("applied", where.unionsAttachments());
		attachment.put("excludeApplied", where.excludesByAttachment());
		attachment.put("skippedTerms", where.attachment() == null
				? List.of() : where.attachment().skippedTerms());
		if (includeAttachments) {
			attachment.putAll(repository.attachmentCoverage());
		}
		meta.put("attachmentSearch", attachment);
		return meta;
	}

	/**
	 * 패싯 — 같은 조건에서 분류·업종·지역·상태가 각각 몇 건인지.
	 *
	 * <p>화면의 필터 칩에 건수를 붙이려면 필요하다. "0건짜리 필터"를 눌러 보고 나서야 아는
	 * 것과, 누르기 전에 아는 것의 차이가 크다.
	 *
	 * <p><b>단계(category) 패싯만 다른 WHERE 로 센다</b> — '마감 전만'의 단계 스코프를 뺀
	 * 조건이다. 이유는 {@link BidNoticeQueryBuilder#withoutActiveStageScope()}. 나머지 축은
	 * 목록과 완전히 같은 조건이다.
	 *
	 * <p>{@code total} 은 <b>이 응답의 조건 그대로</b>의 총건수다. 화면의 '전체' 칩이 쓴다:
	 * 단계 버킷의 합을 쓰면 스코프를 뺀 수를 더하게 되어, 눌렀을 때 나오는 수보다 커진다.
	 */
	public Map<String, Object> facets(NoticeSearchRequest request, boolean includeAttachments) {
		BidNoticeQueryBuilder.Where where = buildWhere(request, includeAttachments);
		BidNoticeQueryBuilder.Where stageWhere = buildBuilder(request, includeAttachments)
				.withoutActiveStageScope()
				.build();
		Map<String, Object> facets = new LinkedHashMap<>();
		FACET_COLUMNS.forEach((name, column) ->
				facets.put(name, repository.facet(STAGE_FACET.equals(name) ? stageWhere : where, column, FACET_LIMIT)
						.stream()
						.filter(row -> row.get("value") != null && !String.valueOf(row.get("value")).isEmpty())
						.toList()));
		facets.put("total", repository.count(where));
		return facets;
	}

	/** 상세 한 건. 없으면 {@code null}. 소스 미지정이면 G2B → NURI → D2B 우선순위 픽. */
	public Map<String, Object> findOne(String id, String source) {
		Map<String, Object> row = repository.findOne(id, NoticeSource.of(source));
		return row == null ? null : shape(row, LocalDateTime.now());
	}

	/**
	 * 조건 → {@code WHERE}.
	 *
	 * <p>검색과 패싯이 <b>반드시 같은 조건</b>을 써야 하므로 한 곳에서만 만든다. 갈라 두면
	 * 언젠가 한쪽에만 필터가 추가되고, 화면은 "12건"이라 써 놓고 3건을 보여준다.
	 */
	private BidNoticeQueryBuilder buildBuilder(NoticeSearchRequest request, boolean includeAttachments) {
		return new BidNoticeQueryBuilder()
				.attachmentScope(includeAttachments)
				.keywords(request.and(), request.or(), request.not())
				.category(request.categoryValue())
				.state(request.stateValue())
				.excludeState(request.excludeStateValue())
				.businessDivision(request.divisionValue())
				.source(request.sourceValue())
				.region(request.region())
				.noticeInstitutionCode(request.insttCd())
				.demandInstitutionCode(request.dmndInsttCd())
				.institutionName(request.insttNm())
				.detailProductCode(request.detailProductCode())
				.beforeSpecRgstNo(request.beforeSpecRgstNo())
				.officerName(request.officerName())
				.createdBetween(request.createdFrom(), request.createdTo())
				.closeBetween(request.closeFromValue(), request.closeToValue())
				.activeOnly(request.activeOnlyEnabled())
				.amountBetween(request.minAmount(), request.maxAmount());
	}

	private BidNoticeQueryBuilder.Where buildWhere(NoticeSearchRequest request, boolean includeAttachments) {
		return buildBuilder(request, includeAttachments).build();
	}

	/**
	 * 실제로 적용될 정렬 키.
	 *
	 * <p>기본값이 조건에 따라 달라진다 — 검색어가 있으면 <b>관련도</b>, 없으면 <b>최신순</b>.
	 * 검색어 없이 관련도로 정렬하면 전부 0점이라 순서가 사실상 무작위가 되고, 검색어가 있는데
	 * 최신순으로 두면 정확히 맞는 공고가 3페이지 뒤로 밀린다.
	 *
	 * <p>{@link #orderBy} 와 저장소의 경로 선택이 <b>같은 판정</b>을 봐야 하므로 따로 뽑아 둔다.
	 * 둘이 갈라지면 관련도로 정렬하면서 전문검색 전용 질의를 안 타거나 그 반대가 된다.
	 */
	private static String effectiveSortKey(NoticeSearchRequest request, boolean fullText) {
		String key = request.sort() == null ? "" : request.sort().trim();
		return SORTS.containsKey(key) ? key : (fullText ? RELEVANCE : "created");
	}

	/**
	 * 정렬 절.
	 *
	 * <p>마지막에 {@code n.id} 를 붙이는 것은 <b>페이징 안정성</b> 때문이다. 정렬 키가 같은 행이
	 * 여럿일 때(같은 날 올라온 공고가 수백 건이다) 타이브레이커가 없으면 MySQL 이 페이지마다
	 * 다른 순서를 줘서, 2페이지에 1페이지의 공고가 또 나오거나 아예 건너뛴다.
	 *
	 * <p><b>타이브레이커 방향은 정렬 키마다 다르다.</b> 목적은 순서를 <em>고정</em>하는 것이지
	 * 특정 방향이 아니므로, 인덱스가 이미 만들어 둔 순서에 맞추는 편이 공짜다. 규칙과 실측
	 * 근거는 {@link #INDEX_DIRECTION} 에 있다 — 방향 하나가 어긋나면 같은 인덱스를 쓰고도
	 * 수천 행을 다시 정렬한다.
	 */
	static String orderBy(NoticeSearchRequest request, String key) {
		String direction = "asc".equalsIgnoreCase(request.dir()) ? "ASC" : "DESC";
		// 마감 임박은 '가까운 것부터'가 자연스럽다 — 방향 지정이 없으면 오름차순으로 뒤집는다.
		if ("close".equals(key) && request.dir() == null) {
			direction = "ASC";
		}
		return nullsLastPrefix(key, direction) + SORTS.get(key).formatted(direction)
				+ ", n.id " + tiebreakerDirection(key, direction);
	}

	/**
	 * 값이 없는 행을 뒤로 보내는 접두 절.
	 *
	 * <p><b>마진율에만, 그것도 오름차순에만 붙는다.</b> 마진을 아는 공고는 지금 소수이고
	 * (원가는 딜 분석이나 사람이 확정한 가격표에서만 온다) 나머지는 전부 NULL 이다. MySQL 에서
	 * NULL 은 가장 작은 값이라:
	 *
	 * <pre>
	 *   마진 높은 순(DESC)  →  NULL 이 자연히 맨 뒤. 접두 절이 필요 없다
	 *   마진 낮은 순(ASC)   →  NULL 이 맨 앞. 역마진 공고를 보려고 고른 정렬인데
	 *                          미분석 공고 수만 건이 먼저 나온다
	 * </pre>
	 *
	 * <p>ASC 에 접두 절을 붙이면 filesort 를 문다(실측 47,101행). 그래도 붙이는 쪽이 맞다 —
	 * DESC 가 기본이자 사용의 대부분이라 인덱스 경로는 지켜지고, ASC 는 "역마진부터 보여 달라"는
	 * 요청이므로 순서가 틀린 빠른 답보다 느린 정답이 낫다. {@code close} 가 같은 이유로 같은
	 * 선택을 했다(그쪽은 방향과 무관하게 항상 붙인다 — 받쳐 주는 인덱스가 없어 어차피 filesort 다).
	 *
	 * <p>대안이었던 <b>{@code WHERE margin_rate IS NOT NULL}</b> 은 쓰지 않는다. 정렬을 바꿨을
	 * 뿐인데 결과 집합이 줄어드는 것은 사용자가 검증할 수 없는 종류의 거짓말이다.
	 */
	static String nullsLastPrefix(String key, String direction) {
		return MARGIN.equals(key) && "ASC".equals(direction) ? "n.margin_rate IS NULL, " : "";
	}

	/**
	 * 타이브레이커({@code n.id}) 방향.
	 *
	 * <p>인덱스를 정방향으로 읽으면 PK 접미가 ASC 로, 역방향으로 읽으면 DESC 로 나온다.
	 * 요청 방향이 인덱스 선언 방향과 같으면 정방향이다. 근거는 {@link #INDEX_DIRECTION}.
	 *
	 * <p>{@code private} 이 아닌 것은 테스트 때문이다 — 방향이 하나만 어긋나도 증상이
	 * '조금 느려짐' 뿐이라 리뷰나 기능 테스트로는 잡히지 않는다.
	 */
	static String tiebreakerDirection(String sortKey, String direction) {
		String indexDirection = INDEX_DIRECTION.get(sortKey);
		if (indexDirection == null) {
			// 받쳐 주는 인덱스가 없는 정렬 키 — 어느 쪽이든 filesort 라 순서만 고정하면 된다.
			return "ASC";
		}
		return indexDirection.equals(direction) ? "ASC" : "DESC";
	}

	// ── 행 성형 ─────────────────────────────────────────────────────────────

	/**
	 * DB 행(snake_case) → 프론트가 쓰는 모양(camelCase).
	 *
	 * <p>JSON 컬럼은 <b>문자열이 아니라 값으로</b> 펴서 내려준다. 문자열로 주면 프론트가
	 * 컴포넌트마다 {@code JSON.parse} 를 부르게 되고, 그중 한 곳이 빠지면 화면에
	 * {@code [object Object]} 나 원시 JSON 이 그대로 뜬다.
	 */
	private Map<String, Object> shape(Map<String, Object> row, LocalDateTime now) {
		Map<String, Object> out = new LinkedHashMap<>();
		out.put("id", row.get("id"));
		// DB ENUM 값(G2B/NURI/D2B)과 팬아웃 계약의 _source(g2b/private-g2b/d2b)를 함께 준다 —
		// 프론트가 기존 팬아웃 화면의 출처 뱃지 로직을 그대로 쓸 수 있게.
		NoticeSource source = NoticeSource.of(String.valueOf(row.get("source")));
		out.put("source", row.get("source"));
		if (source != null) {
			out.put("_source", source.apiValue());
			out.put("_sourceLabel", source.label());
		}
		out.put("noticeOrder", row.get("notice_order"));
		out.put("noticeName", row.get("notice_name"));
		out.put("category", row.get("category"));
		out.put("state", row.get("state"));
		out.put("businessDivision", row.get("business_division"));
		out.put("region", row.get("region"));

		out.put("demandInstitutionCode", row.get("demand_institution_code"));
		out.put("demandInstitutionName", row.get("demand_institution_name"));
		out.put("noticeInstitutionCode", row.get("notice_institution_code"));
		out.put("noticeInstitutionName", row.get("notice_institution_name"));

		out.put("beforeSpecRgstNo", row.get("before_spec_rgst_no"));
		out.put("productList", parseJson(row.get("product_list")));
		out.put("detailProductCode", row.get("detail_product_code"));
		out.put("lowestBidRate", row.get("lowest_bid_rate"));
		out.put("priceDetail", parseJson(row.get("price_detail")));

		out.put("createdDate", row.get("created_date"));
		out.put("closeDate", row.get("close_date"));
		out.put("updatedAt", row.get("updated_at"));

		out.put("officerName", row.get("officer_name"));
		out.put("officerContact", row.get("officer_contact"));

		out.put("aiSummary", row.get("ai_summary"));
		out.put("attachmentUrls", parseJson(row.get("attachment_urls")));
		out.put("sourceUrl", row.get("source_url"));
		out.put("sourceExt", parseJson(row.get("source_ext")));
		out.put("g2bPblancNo", row.get("g2b_pblanc_no"));
		out.put("g2bPblancOdr", row.get("g2b_pblanc_odr"));

		// 목록은 미리보기, 상세는 전문. 있는 것만 넣는다.
		out.put("bodyPreview", row.get("body_preview"));
		if (row.containsKey("notice_body")) {
			out.put("noticeBody", row.get("notice_body"));
		}
		if (row.containsKey("relevance")) {
			out.put("relevance", row.get("relevance"));
		}

		// 첨부 본문에서만 걸린 공고는 제목·본문 어디에도 그 낱말이 없다 — 표시하지 않으면
		// 사용자는 관계없는 공고가 섞였다고 읽는다. 어느 쪽에서 걸렸는지를 그대로 내려준다.
		List<String> matchedIn = matchedIn(row);
		if (matchedIn != null) {
			out.put("matchedIn", matchedIn);
		}
		// "안 걸림"과 "아직 못 읽음"을 화면이 가를 수 있게. 첨부가 없는 공고도 false 다 —
		// 어느 쪽이든 "이 공고의 첨부 본문으로는 판단할 수 없다"는 뜻이라 화면에는 같은 사실이다.
		out.put("attachmentIndexed", row.get("documents_indexed_at") != null);

		// 화면이 매번 계산하지 않도록 서버에서 붙인다. 마감이 없으면(계획) null.
		out.put("dday", dday(row.get("close_date"), now));
		out.put("estimatedPrice", numberIn(out.get("priceDetail"), "estimatedPrice"));

		// 금액 필터·정렬이 실제로 본 값과 그 종류. 값만 주면 화면이 배정예산을 추정가격으로
		// 읽어 서로 다른 금액을 한 줄로 비교하게 된다 — 종류를 함께 준다.
		Amount amount = amountOf(out.get("priceDetail"));
		out.put("amount", amount.value());
		out.put("amountKind", amount.kind());

		// 마진 축(V20260814132535). 비율만 주면 화면이 그 수를 검증할 방법이 없다 — 분자·분모를
		// 이루는 원가와 실추정가, 그리고 그 원가가 사람이 확정한 것인지 AI 추정인지를 함께 준다.
		// marginRate 가 null 인 공고는 '마진 0'이 아니라 '아직 원가를 모른다'는 뜻이다.
		out.put("marginRate", row.get("margin_rate"));
		out.put("marginCost", row.get("margin_cost"));
		out.put("marginSource", row.get("margin_source"));
		out.put("marginUpdatedAt", row.get("margin_updated_at"));
		out.put("marginBase", marginBase(amount.value()));
		return out;
	}

	/**
	 * 마진율의 분모 — 실추정가(대표금액 × 부가세).
	 *
	 * <p>DB 가 아니라 여기서 다시 계산한다. 생성 컬럼은 비율만 내놓고 분모는 남기지 않는데,
	 * 화면이 "마진 30%"의 근거를 적으려면 그 분모가 필요하다({@code amountKind} 와 같은 이유다 —
	 * 값만 주고 무엇으로 계산했는지 숨기면 사용자가 검증할 수 없다).
	 */
	private static BigDecimal marginBase(Object amount) {
		if (!(amount instanceof Number number)) {
			return null;
		}
		return new BigDecimal(number.toString()).multiply(VAT).setScale(0, RoundingMode.HALF_UP);
	}

	/**
	 * 금액 필터·정렬이 본 값과 그 종류.
	 *
	 * @param value 고른 금액. 어느 후보도 없으면 {@code null}
	 * @param kind  {@code estimatedPrice} / {@code assignedBudget} / {@code referenceAmount}
	 *              / {@code basicExpectedPrice}. 값이 없으면 {@code null}
	 */
	record Amount(Object value, String kind) {

		static final Amount NONE = new Amount(null, null);
	}

	/**
	 * {@code price_detail} 에서 대표 금액 하나를 고른다.
	 *
	 * <p><b>순서와 0 처리가 생성 컬럼 {@code filter_amount} 와 정확히 같아야 한다</b>
	 * ({@code V20260814113541}). 어긋나면 화면이 "이 금액으로 걸렀다"며 필터가 실제로 본 것과
	 * 다른 숫자를 보여주게 되고, 그 종류의 거짓말은 사용자가 검증할 방법이 없다.
	 * DB 가 아니라 여기서 다시 고르는 이유는 <b>종류</b>까지 알아야 하기 때문이다 —
	 * 값만 필요했다면 생성 컬럼을 SELECT 하면 그만이다.
	 *
	 * <p>0 을 값으로 인정하지 않는 것도 그쪽과 같다. 배정예산 0 은 '0원짜리 공고'가 아니라
	 * '미공개'다(실측 1,486건, 대부분 누리장터 민간공고). 0 으로 내려보내면 화면에 '0원'이라
	 * 적히고 {@code maxAmount} 검색이 금액을 모르는 공고를 데려온다.
	 */
	static Amount amountOf(Object priceDetail) {
		for (String key : AMOUNT_KEYS) {
			Object value = numberIn(priceDetail, key);
			if (value instanceof Number number && number.doubleValue() != 0) {
				return new Amount(value, key);
			}
		}
		return Amount.NONE;
	}

	/**
	 * 이 행이 걸린 경로. 첨부 스코프를 타지 않은 질의는 두 칸이 아예 없으므로 {@code null} 이다.
	 *
	 * <p>MySQL 이 {@code 1 AS notice_hit} 를 {@code Long}·{@code BigDecimal} 중 무엇으로 줄지는
	 * UNION 분기와 드라이버 버전에 달렸다. 타입을 가정하지 않고 수치로 읽는다.
	 */
	private static List<String> matchedIn(Map<String, Object> row) {
		if (!row.containsKey("notice_hit") && !row.containsKey("doc_hit")) {
			return null;
		}
		List<String> matched = new java.util.ArrayList<>(2);
		if (isTrue(row.get("notice_hit"))) {
			matched.add("notice");
		}
		if (isTrue(row.get("doc_hit"))) {
			matched.add("attachment");
		}
		return List.copyOf(matched);
	}

	private static boolean isTrue(Object value) {
		return value instanceof Number number && number.longValue() > 0;
	}

	/**
	 * 남은 일수. 오늘 마감이면 0, 이미 지났으면 음수.
	 *
	 * <p>시각이 아니라 <b>날짜</b>로 센다. 사용자가 'D-1' 을 보고 기대하는 것은 '내일까지'이지
	 * '24시간 남음'이 아니다.
	 */
	private static Long dday(Object closeDate, LocalDateTime now) {
		if (!(closeDate instanceof LocalDateTime close)) {
			return null;
		}
		return ChronoUnit.DAYS.between(LocalDate.from(now), LocalDate.from(close));
	}

	private static Object numberIn(Object priceDetail, String field) {
		if (priceDetail instanceof Map<?, ?> map) {
			Object value = map.get(field);
			return value instanceof Number || value instanceof BigDecimal ? value : null;
		}
		return null;
	}

	/**
	 * JSON 컬럼 문자열 → 값.
	 *
	 * <p>깨진 JSON 이 들어 있어도 검색 결과 전체를 500 으로 만들지 않는다 — 그 칸만 null 로
	 * 두고 넘어간다. 색인 데이터의 흠이 조회 가능성을 무너뜨려서는 안 된다.
	 */
	private static Object parseJson(Object raw) {
		if (raw == null) {
			return null;
		}
		String text = String.valueOf(raw);
		if (text.isBlank()) {
			return null;
		}
		try {
			return JSON.readValue(text, Object.class);
		}
		catch (JacksonException ex) {
			log.debug("색인 JSON 을 읽지 못했습니다: {}", ex.getMessage());
			return null;
		}
	}
}
