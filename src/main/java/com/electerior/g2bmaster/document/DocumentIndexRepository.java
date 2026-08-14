package com.electerior.g2bmaster.document;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * {@code bid_notice_document} 접근.
 *
 * <p><b>이 저장소의 규칙 하나.</b> 새 본문이 준비되기 <em>전에</em> 옛 본문을 지우지 않는다.
 * 정정이 나면 {@code documents_indexed_at} 만 NULL 이 되고(적재기가 한다), 옛 본문은 계속
 * 검색에 걸린다. 지금 지워 버리면 정정 순간부터 추출이 끝날 때까지 그 공고가 파일 검색에서
 * <b>사라지는 창</b>이 생긴다 — 사용자에게는 "어제 보이던 공고가 오늘 안 보인다"가 되는데,
 * 낡은 규격서가 잠깐 걸리는 것보다 나쁘다.
 *
 * <p>그래서 이력 이관이 두 시점으로 갈린다.
 * <ul>
 *   <li><b>씨뿌리기({@link #seed})</b> — 첨부 목록·차수가 바뀐 것을 알게 된 시점.
 *       {@code 차수상승} · {@code 목록변경}</li>
 *   <li><b>완료({@link #complete})</b> — 새 본문이 손에 들어온 시점.
 *       {@code 내용변경} · {@code 추출기변경}</li>
 * </ul>
 */
@Repository
public class DocumentIndexRepository {

	private static final ObjectMapper JSON = JsonMapper.builder().build();

	/** 본문 상한. {@code DocumentTextExtractor.MAX_CHARS}(200만)를 그대로 쓰면 색인이 감당하지 못한다. */
	public static final int MAX_BODY_CHARS = 200_000;

	private final NamedParameterJdbcTemplate jdbc;

	public DocumentIndexRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * 첨부 색인이 아직 안 끝난 공고 한 묶음. {@code attachment_urls} 를 그대로 들고 온다.
	 *
	 * <p>{@code closeBefore} 가 있으면 <b>지금부터 그 시각까지 마감되는 공고</b>로 좁히고,
	 * <b>마감이 급한 순</b>으로 준다. 전량 백필은 109,284파일이라 하루로 안 끝나는데, 실무에서
	 * 급한 것은 "지금 들어갈 수 있는 건"이다 — 마감 임박 순이 체감 효용이 가장 크다.
	 *
	 * <p>{@code createdBefore} 는 <b>게시일 기준</b>의 다른 축이다("지난주 월요일까지의 공고를
	 * 분석해 달라"). 마감 축과 겹치지 않는다 — 이미 마감된 공고도 대상이 되므로, 마감 임박
	 * 순서로는 영영 차례가 오지 않는 구간을 이 축이 채운다. 둘 다 주면 둘 다 걸리고, 순서는
	 * 마감 축이 이긴다(그쪽이 더 급한 요구다).
	 */
	public List<PendingNotice> findNoticesNeedingSeed(int limit, String closeBefore, String createdBefore) {
		boolean byClose = closeBefore != null && !closeBefore.isBlank();
		boolean byCreated = createdBefore != null && !createdBefore.isBlank();
		MapSqlParameterSource params = new MapSqlParameterSource("limit", limit);
		if (byClose) {
			params.addValue("closeBefore", closeBefore.replace('T', ' '));
		}
		if (byCreated) {
			params.addValue("createdBefore", createdBefore.replace('T', ' '));
		}
		return jdbc.query(buildSeedSql(byClose, byCreated), params, (rs, n) -> new PendingNotice(
				rs.getString("id"), rs.getString("source"), rs.getString("notice_order"),
				rs.getString("attachment_urls")));
	}

	/**
	 * 씨뿌리기 질의. 조립 규칙만 따로 떼어 문자열로 검증한다 — 범위를 잘못 조립하면 백필이
	 * 조용히 <b>다른 공고 묶음</b>을 몇 시간 동안 내려받는다(실패가 아니라 헛일이라 눈에 안 띈다).
	 */
	static String buildSeedSql(boolean byClose, boolean byCreated) {
		return """
				SELECT id, source, notice_order, attachment_urls
				  FROM bid_notice
				 WHERE documents_indexed_at IS NULL
				   AND attachment_urls IS NOT NULL
				"""
				+ (byClose ? "   AND close_date > NOW() AND close_date < :closeBefore\n" : "")
				// 게시일 축은 마감 여부를 보지 않는다 — 이미 마감된 공고도 분석 대상이다.
				+ (byCreated ? "   AND created_date <= :createdBefore\n" : "")
				+ (byClose ? " ORDER BY close_date ASC\n" : " ORDER BY created_date DESC\n")
				+ " LIMIT :limit";
	}

	/**
	 * 공고 하나의 첨부 슬롯대로 문서 행을 세운다.
	 *
	 * <p>슬롯 번호({@code file_seq})가 정체성이다. 같은 슬롯에 다른 파일이 걸렸으면 그것은
	 * 교체이고, 옛 행을 이력으로 넘긴 뒤 다시 {@code pending} 으로 돌린다. 본문은 그대로
	 * 남겨 둔다 — 위 클래스 주석 참고.
	 *
	 * @return 새로 뽑아야 할 슬롯 수
	 */
	public int seed(PendingNotice notice) {
		List<FileSlot> slots = parseSlots(notice.attachmentUrls());
		if (slots.isEmpty()) {
			// 첨부 필드가 있었는데 슬롯이 하나도 안 나오는 경우(빈 배열 등). 다시 볼 일이 없다.
			markNoticeIndexed(notice.id(), notice.source());
			return 0;
		}

		// 이 공고에서 사라진 슬롯은 색인에서도 사라져야 한다. 첨부가 5개에서 3개로 줄면
		// 4·5번 슬롯의 옛 본문이 남아 검색에 걸린다 — 지금은 존재하지 않는 문서인데도.
		supersedeRemovedSlots(notice, slots.size());

		int changed = 0;
		for (FileSlot slot : slots) {
			changed += seedSlot(notice, slot);
		}
		return changed;
	}

	private int seedSlot(PendingNotice notice, FileSlot slot) {
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("noticeId", notice.id())
				.addValue("source", notice.source())
				.addValue("noticeOrder", notice.noticeOrder())
				.addValue("fileSeq", slot.seq())
				.addValue("fileName", slot.name())
				.addValue("fileExt", slot.ext())
				.addValue("sourceUrl", slot.url());

		// 이미 같은 슬롯이 있고 내용이 달라졌으면 먼저 이력으로 넘긴다.
		// 차수가 올랐으면 '차수상승', 아니면 '목록변경' 이다.
		String archive = """
				INSERT INTO bid_notice_document_history
				  (document_id, notice_id, source, notice_order, file_seq, file_name, sha256,
				   body_text, char_count, extractor_version, superseded_reason)
				SELECT d.id, d.notice_id, d.source, d.notice_order, d.file_seq, d.file_name, d.sha256,
				       d.body_text, d.char_count, d.extractor_version,
				       IF(:noticeOrder > d.notice_order, '차수상승', '목록변경')
				  FROM bid_notice_document d
				 WHERE d.notice_id = :noticeId AND d.source = :source AND d.file_seq = :fileSeq
				   AND d.status = 'done'
				   AND NOT (d.source_url <=> :sourceUrl AND d.file_name <=> :fileName)
				""";
		jdbc.update(archive, params);

		// 새 슬롯이면 INSERT, 바뀐 슬롯이면 pending 으로 되돌린다.
		// body_text 를 건드리지 않는 것이 핵심 — 새 본문이 올 때까지 옛 본문이 검색을 지킨다.
		String upsert = """
				INSERT INTO bid_notice_document
				  (notice_id, source, notice_order, file_seq, file_name, file_ext, source_url, status)
				VALUES (:noticeId, :source, :noticeOrder, :fileSeq, :fileName, :fileExt, :sourceUrl, 'pending')
				AS new
				ON DUPLICATE KEY UPDATE
				  status = IF(bid_notice_document.source_url <=> new.source_url
				              AND bid_notice_document.file_name <=> new.file_name,
				              bid_notice_document.status, 'pending'),
				  retry_count = IF(bid_notice_document.source_url <=> new.source_url
				                   AND bid_notice_document.file_name <=> new.file_name,
				                   bid_notice_document.retry_count, 0),
				  notice_order = new.notice_order,
				  file_name = new.file_name,
				  file_ext = new.file_ext,
				  source_url = new.source_url
				""";
		return jdbc.update(upsert, params);
	}

	/** 첨부 개수가 줄어 사라진 슬롯을 이력으로 넘기고 지운다. */
	private void supersedeRemovedSlots(PendingNotice notice, int slotCount) {
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("noticeId", notice.id())
				.addValue("source", notice.source())
				.addValue("slotCount", slotCount);
		jdbc.update("""
				INSERT INTO bid_notice_document_history
				  (document_id, notice_id, source, notice_order, file_seq, file_name, sha256,
				   body_text, char_count, extractor_version, superseded_reason)
				SELECT id, notice_id, source, notice_order, file_seq, file_name, sha256,
				       body_text, char_count, extractor_version, '목록변경'
				  FROM bid_notice_document
				 WHERE notice_id = :noticeId AND source = :source AND file_seq > :slotCount
				""", params);
		jdbc.update("""
				DELETE FROM bid_notice_document
				 WHERE notice_id = :noticeId AND source = :source AND file_seq > :slotCount
				""", params);
	}

	/**
	 * 일감 청구. 뽑을 것이 없는 행은 건너뛴다.
	 *
	 * <p>세 종류를 한 질의로 고른다.
	 * <ol>
	 *   <li>{@code pending} — 아직 뽑지 않았다</li>
	 *   <li>{@code failed} 이고 재시도가 3회 미만 — 일시적 실패였을 수 있다</li>
	 *   <li>{@code done} 인데 추출기 버전이 다르다 — 파서가 바뀌었으니 다시 뽑는다</li>
	 * </ol>
	 *
	 * <p><b>{@code FOR UPDATE SKIP LOCKED} 를 쓰지 않는다.</b> 분석 큐와 달리 여기서는 그것이
	 * 도움이 안 된다 — 잠금을 잡은 채로 파일을 내려받으면(건당 수 초) 트랜잭션이 그만큼 열려
	 * 있고, 짧게 잡았다 놓으면 잠금이 아무것도 막지 못한다. 중복 실행은 {@code DocumentExtractionWorker}
	 * 의 실행 가드 하나로 막는다. 적재 인스턴스는 하나여야 한다는 규칙(notice-search-index.md §3.4)이
	 * 이미 있으므로 그 위에 얹는 것으로 충분하다.
	 *
	 * <p>{@code ORDER BY status = 'done'} 은 <b>아직 한 번도 안 뽑은 것을 먼저</b> 준다는 뜻이다
	 * (거짓=0 이 먼저 정렬된다). 파서를 올려 전량 재추출이 걸린 날에도 신규 공고가 뒤로 밀리지
	 * 않아야 한다 — 재추출은 이미 검색되는 문서를 다듬는 일이고, 신규는 아예 안 걸리는 문서다.
	 */
	public List<DocumentJob> claim(int limit, String extractorVersion, String closeBefore, String createdBefore) {
		boolean byClose = closeBefore != null && !closeBefore.isBlank();
		boolean byCreated = createdBefore != null && !createdBefore.isBlank();
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("limit", limit)
				.addValue("extractorVersion", extractorVersion);
		if (byClose) {
			params.addValue("closeBefore", closeBefore.replace('T', ' '));
		}
		if (byCreated) {
			params.addValue("createdBefore", createdBefore.replace('T', ' '));
		}
		return jdbc.query(buildClaimSql(byClose, byCreated), params, (rs, n) -> new DocumentJob(
				rs.getLong("id"), rs.getString("notice_id"), rs.getString("source"),
				rs.getString("notice_order"), rs.getInt("file_seq"), rs.getString("file_name"),
				rs.getString("source_url"), rs.getString("sha256"),
				rs.getString("extractor_version"), rs.getInt("retry_count")));
	}

	/**
	 * 청구 질의. <b>씨뿌리기와 같은 범위를 걸어야 한다</b> — 큐에는 이전 범위로 세워 둔 행이
	 * 그대로 남아 있어서, 청구가 범위를 안 보면 워커는 몇 시간 동안 <b>지금 요청과 무관한
	 * 공고</b>를 내려받는다(실측: 대기 10,019건 중 요청 범위는 1,883건뿐이었다).
	 *
	 * <p>상태 조건의 괄호가 핵심이다. 원본은 {@code (A) OR (B)} 였고 여기에 범위를 그냥 이어
	 * 붙이면 {@code AND} 가 뒤쪽 갈래에만 붙어, 범위 밖의 pending 이 전부 다시 새어 든다.
	 */
	static String buildClaimSql(boolean byClose, boolean byCreated) {
		return """
				SELECT d.id, d.notice_id, d.source, d.notice_order, d.file_seq, d.file_name, d.source_url,
				       d.sha256, d.extractor_version, d.retry_count
				  FROM bid_notice_document d
				"""
				+ (byClose || byCreated
						? "  JOIN bid_notice n ON n.id = d.notice_id AND n.source = d.source\n" : "")
				+ """
				 WHERE ((d.status IN ('pending', 'failed') AND d.retry_count < 3)
				     OR (d.status IN ('done', 'skip') AND d.extractor_version <> :extractorVersion))
				"""
				+ (byClose ? "   AND n.close_date > NOW() AND n.close_date < :closeBefore\n" : "")
				+ (byCreated ? "   AND n.created_date <= :createdBefore\n" : "")
				// 재추출(추출기 버전 변경)은 아직 한 번도 못 뽑은 것 뒤로 미룬다.
				+ " ORDER BY d.status IN ('done', 'skip'), d.id\n"
				+ " LIMIT :limit";
	}

	/**
	 * 추출 성공. 내용이 실제로 달라졌을 때만 이력을 남긴다.
	 *
	 * <p>{@code sha256} 이 그대로인데 텍스트만 달라졌다면 그것은 <b>추출기가 바뀐 것</b>이다.
	 * 원본이 같으니 사유를 '내용변경' 으로 적으면 나중에 이력을 읽는 사람이 발주기관이 문서를
	 * 고쳤다고 오해한다.
	 */
	public void complete(DocumentJob job, ExtractionResult result) {
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("id", job.id())
				.addValue("sha256", result.sha256())
				.addValue("byteSize", result.byteSize())
				.addValue("bodyText", result.bodyText())
				.addValue("charCount", result.charCount())
				.addValue("truncated", result.truncated())
				.addValue("needsOcr", result.needsOcr())
				.addValue("isSpec", result.isSpec())
				.addValue("extractorVersion", ExtractorVersion.CURRENT);

		jdbc.update("""
				INSERT INTO bid_notice_document_history
				  (document_id, notice_id, source, notice_order, file_seq, file_name, sha256,
				   body_text, char_count, extractor_version, superseded_reason)
				SELECT id, notice_id, source, notice_order, file_seq, file_name, sha256,
				       body_text, char_count, extractor_version,
				       IF(sha256 <=> :sha256, '추출기변경', '내용변경')
				  FROM bid_notice_document
				 WHERE id = :id AND status = 'done' AND body_text IS NOT NULL
				   AND NOT (sha256 <=> :sha256 AND extractor_version <=> :extractorVersion)
				""", params);

		jdbc.update("""
				UPDATE bid_notice_document
				   SET body_text = :bodyText, char_count = :charCount, truncated = :truncated,
				       needs_ocr = :needsOcr, sha256 = :sha256, byte_size = :byteSize,
				       is_spec = :isSpec, extractor_version = :extractorVersion,
				       status = 'done', retry_count = 0, last_error = NULL,
				       extracted_at = NOW(6), verified_at = NOW(6),
				       content_changed_at = IF(sha256 <=> :sha256, content_changed_at, NOW(6))
				 WHERE id = :id
				""", params);
	}

	/**
	 * <b>다시 해도 같은 결과</b>인 실패. 재시도 없이 바로 닫는다.
	 *
	 * <p>파싱 실패는 결정적이다 — 같은 바이트를 같은 파서에 넣으면 같은 자리에서 깨진다.
	 * 그런데 재시도는 파일을 <b>다시 내려받는 것</b>부터 시작하므로, 3회까지 끌면 결과가 뻔한
	 * 실패에 대역폭을 세 배로 쓴다(실측: 타임아웃 119건 · 파서 오류 186건이 이 경로였다).
	 *
	 * <p><b>그래서 추출기 버전을 함께 적는다.</b> 이 칸이 없으면 파서를 고쳐도 여기 닫힌 파일은
	 * 영원히 다시 시도되지 않는다 — 재추출을 부르는 가장 잦은 사유가 문서 변경이 아니라 우리
	 * 파서의 변경인데(실측 54%), 그 대상에서 통째로 빠지는 셈이다. {@link #buildClaimSql} 이
	 * 낡은 버전의 {@code skip} 을 다시 집는다.
	 */
	public void skipPermanent(long id, String message) {
		jdbc.update("""
				UPDATE bid_notice_document
				   SET status = 'skip',
				       last_error = LEFT(:message, 500),
				       extractor_version = :extractorVersion,
				       verified_at = NOW(6)
				 WHERE id = :id
				""", new MapSqlParameterSource().addValue("id", id).addValue("message", message)
						.addValue("extractorVersion", ExtractorVersion.CURRENT));
	}

	/**
	 * 추출 실패. 3회째면 {@code skip} 으로 닫아 큐가 같은 파일을 영원히 물고 있지 않게 한다.
	 *
	 * <p><b>닫을 때 추출기 버전을 반드시 찍는다.</b> 청구가 "낡은 버전의 {@code skip}" 을 재추출
	 * 대상으로 집기 때문에({@link #buildClaimSql}), 버전이 빈 채로 닫힌 행은 <b>매 회차 다시
	 * 청구된다</b> — 실측으로 20개 행이 {@code retry_count=109} 까지 갔고, 회차가 9초마다
	 * 같은 파일을 다시 내려받으며 헛돌았다. {@code skipPermanent} 는 처음부터 찍고 있었는데
	 * 재시도 소진 경로만 빠져 있었다.
	 */
	public void fail(long id, String message) {
		jdbc.update("""
				UPDATE bid_notice_document
				   SET status = IF(retry_count + 1 >= 3, 'skip', 'failed'),
				       extractor_version = IF(retry_count + 1 >= 3, :extractorVersion, extractor_version),
				       retry_count = retry_count + 1,
				       last_error = LEFT(:message, 500),
				       verified_at = NOW(6)
				 WHERE id = :id
				""", new MapSqlParameterSource().addValue("id", id).addValue("message", message)
						.addValue("extractorVersion", ExtractorVersion.CURRENT));
	}

	/**
	 * 공고의 남은 일감이 없으면 색인 완료로 표시한다.
	 *
	 * <p>{@code skip} 도 완료로 친다 — 세 번 실패한 파일을 기다리면 그 공고는 영원히
	 * "색인 중" 으로 남고, 화면이 계속 "아직 첨부 색인 전" 을 보여준다.
	 */
	public int markNoticeIndexedIfDone(String noticeId, String source) {
		return jdbc.update("""
				UPDATE bid_notice n
				   SET n.documents_indexed_at = NOW(6)
				 WHERE n.id = :noticeId AND n.source = :source
				   AND NOT EXISTS (
				       SELECT 1 FROM bid_notice_document d
				        WHERE d.notice_id = n.id AND d.source = n.source
				          AND d.status IN ('pending', 'failed'))
				""", new MapSqlParameterSource().addValue("noticeId", noticeId).addValue("source", source));
	}

	private int markNoticeIndexed(String noticeId, String source) {
		return jdbc.update("""
				UPDATE bid_notice SET documents_indexed_at = NOW(6)
				 WHERE id = :noticeId AND source = :source
				""", new MapSqlParameterSource().addValue("noticeId", noticeId).addValue("source", source));
	}

	/**
	 * 주어진 공고들의 첨부 본문 중 <b>키워드가 걸릴 만한 것</b>을 가져온다.
	 *
	 * <p>여기서 SQL 이 하는 일은 <b>후보 좁히기</b>다. 최종 판정과 발췌는 {@link KeywordExcerpt} 가
	 * 한다 — 이유는 그쪽 주석 참고.
	 *
	 * <p><b>왜 FULLTEXT 를 조건에 넣는가.</b> 공고번호만으로 걸러도 행 수는 이미 적지만
	 * ({@code IN} 50건 × 파일 2.9개 ≈ 145행), 그 행들의 {@code body_text} 를 전부 자바로
	 * 실어 나르면 한 묶음이 4~5MB 다. 40묶음이면 180MB — 키워드가 걸린 몇 건만 받아오면
	 * 그것이 거의 0 이 된다.
	 *
	 * <p><b>ngram 은 두 글자부터다.</b> 한 글자 키워드는 색인이 통째로 무시하므로
	 * ({@code ngram_token_size=2}) LIKE 로 떨어뜨린다 — 검색 계층이 공고명에 대해 이미 쓰는
	 * 것과 같은 규칙이다. 공백이 든 키워드도 LIKE 로 보낸다: 우리 매칭 규칙이 공백을 느슨하게
	 * 보는데(저장 장치 = 저장장치) 그 의미를 전문검색으로는 표현할 수 없다.
	 */
	public List<DocumentText> findMatchable(List<String> noticeIds, List<String> keywords) {
		if (noticeIds == null || noticeIds.isEmpty() || keywords == null || keywords.isEmpty()) {
			return List.of();
		}

		List<String> phrases = new ArrayList<>();
		List<String> likes = new ArrayList<>();
		for (String keyword : keywords) {
			String term = keyword == null ? "" : keyword.trim();
			if (term.isEmpty()) {
				continue;
			}
			// 따옴표는 불리언 모드의 구문 구분자라 값에 그대로 들어가면 질의가 깨진다.
			String safe = term.replace("\"", " ").trim();
			if (safe.length() >= 2 && !safe.contains(" ")) {
				phrases.add("\"" + safe + "\"");
			} else {
				likes.add(safe);
			}
		}
		if (phrases.isEmpty() && likes.isEmpty()) {
			return List.of();
		}

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("noticeIds", noticeIds);
		List<String> clauses = new ArrayList<>();
		if (!phrases.isEmpty()) {
			// 공백으로 이으면 불리언 모드의 기본 의미가 OR 다 — 하나라도 걸리면 후보다.
			params.addValue("booleanQuery", String.join(" ", phrases));
			clauses.add("MATCH(body_text) AGAINST(:booleanQuery IN BOOLEAN MODE)");
		}
		for (int i = 0; i < likes.size(); i++) {
			params.addValue("like" + i, "%" + likes.get(i).replace("%", "\\%").replace("_", "\\_") + "%");
			clauses.add("body_text LIKE :like" + i);
		}

		String sql = """
				SELECT notice_id, source, file_seq, file_name, body_text
				  FROM bid_notice_document
				 WHERE notice_id IN (:noticeIds)
				   AND status = 'done' AND body_text IS NOT NULL
				   AND (%s)
				 ORDER BY notice_id, file_seq
				""".formatted(String.join(" OR ", clauses));
		return jdbc.query(sql, params, (rs, n) -> new DocumentText(
				rs.getString("notice_id"), rs.getString("source"), rs.getInt("file_seq"),
				rs.getString("file_name"), rs.getString("body_text")));
	}

	/** 색인이 끝난 공고번호 집합 — "안 걸림" 과 "아직 색인 전" 을 가르는 데 쓴다. */
	public Set<String> findIndexedNoticeIds(List<String> noticeIds) {
		if (noticeIds == null || noticeIds.isEmpty()) {
			return Set.of();
		}
		return Set.copyOf(jdbc.queryForList("""
				SELECT id FROM bid_notice
				 WHERE id IN (:noticeIds) AND documents_indexed_at IS NOT NULL
				""", new MapSqlParameterSource("noticeIds", noticeIds), String.class));
	}

	/** 운영 화면·백필이 보는 현황. */
	public Map<String, Object> stats() {
		Map<String, Object> out = new LinkedHashMap<>();
		jdbc.query("SELECT status, COUNT(*) c FROM bid_notice_document GROUP BY status",
				rs -> {
					out.put(rs.getString("status"), rs.getLong("c"));
				});
		Map<String, Object> notices = jdbc.queryForMap("""
				SELECT COUNT(*) total,
				       SUM(documents_indexed_at IS NULL AND attachment_urls IS NOT NULL) pendingNotices
				  FROM bid_notice
				""", new MapSqlParameterSource());
		out.putAll(notices);
		return out;
	}

	// ── 내부 ────────────────────────────────────────────────────────────────

	/**
	 * {@code [{"name":…,"url":…}]} → 슬롯 목록. 배열의 <b>위치가 곧 슬롯 번호</b>다
	 * (매퍼가 {@code ntceSpecDocUrl1..10} 순서대로 넣는다).
	 */
	static List<FileSlot> parseSlots(String attachmentUrlsJson) {
		List<FileSlot> slots = new ArrayList<>();
		if (attachmentUrlsJson == null || attachmentUrlsJson.isBlank()) {
			return slots;
		}
		JsonNode array;
		try {
			array = JSON.readTree(attachmentUrlsJson);
		} catch (RuntimeException e) {
			return slots;
		}
		if (!array.isArray()) {
			return slots;
		}
		int seq = 0;
		for (JsonNode node : array) {
			seq++;
			String url = text(node, "url");
			if (url.isBlank()) {
				continue;
			}
			slots.add(new FileSlot(seq, text(node, "name"), url));
		}
		return slots;
	}

	private static String text(JsonNode node, String field) {
		JsonNode value = node.get(field);
		return value == null || value.isNull() ? "" : value.asString();
	}

	/** 적재기가 본 첨부 목록 그대로의 공고 한 건. */
	public record PendingNotice(String id, String source, String noticeOrder, String attachmentUrls) {}

	/** 첨부 슬롯 하나. */
	public record FileSlot(int seq, String name, String url) {

		/** 확장자. 파서 선택은 {@code DocumentTextExtractor} 가 파일명으로 하고, 이 값은 표시·통계용이다. */
		public String ext() {
			String lower = name == null ? "" : name.toLowerCase();
			int dot = lower.lastIndexOf('.');
			if (dot < 0 || dot == lower.length() - 1) {
				return null;
			}
			String ext = lower.substring(dot + 1);
			return ext.length() > 16 ? null : ext;
		}
	}

	/** 스캔이 읽는 문서 한 건. */
	public record DocumentText(String noticeId, String source, int fileSeq, String fileName, String bodyText) {}

	/** 추출 한 건의 결과. */
	public record ExtractionResult(
			String sha256,
			int byteSize,
			String bodyText,
			int charCount,
			boolean truncated,
			boolean needsOcr,
			boolean isSpec) {}
}
