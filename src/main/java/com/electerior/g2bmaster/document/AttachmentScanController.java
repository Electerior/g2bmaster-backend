package com.electerior.g2bmaster.document;

import com.electerior.g2bmaster.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code POST /api/scan-attachments} — 화면의 "파일 내" 키워드 검색.
 *
 * <p><b>계약은 원본 모놀리스 그대로다.</b> 프론트({@code useAttachmentScan.ts})가 이미 이
 * 모양으로 부르고 있고, 필드명을 바꾸면 두 저장소가 동시에 깨진다. 그래서 요청·응답의
 * 겉모습은 손대지 않고 <b>속만 바꿨다</b> — 나라장터에서 그때그때 내려받던 것을 색인 조회로.
 *
 * <p><b>무엇이 달라졌나.</b> 원본은 이 요청을 받을 때마다 최대 300개의 PDF·HWPX 를 실제로
 * 내려받아 열었다(건당 8초 타임아웃, 5개씩 배치). 여기서는 {@code bid_notice_document} 를
 * 한 번 조회한다. 상류 호출은 <b>0</b> 이고, 그래서 {@code warmQueued}·{@code warmActive} 처럼
 * 백그라운드 다운로드 큐를 가리키던 필드는 항상 0 이다 — 프론트가 읽어도 깨지지 않도록
 * 자리만 남겨 둔다.
 *
 * <p><b>아직 안 되는 것: 입찰 불가 조항 자동 제외.</b> {@code exclusions} 는 늘 빈 목록이다.
 * 조항 판정은 첨부 본문과 별개의 규칙 묶음이고 이번 범위가 아니었다. 빈 목록을 주면 화면은
 * 아무것도 제외하지 않는다 — 잘못 제외해서 입찰 기회를 지우는 것보다 안전한 방향이다.
 */
@RestController
@RequestMapping("/api")
@Tag(name = OpenApiConfig.TAG_ATTACHMENT)
public class AttachmentScanController {

	/**
	 * 한 요청에 처리할 공고 수 상한. 프론트는 50건씩 잘라 보내지만, 계약상 배열 크기에 제한이
	 * 없어 방어적으로 자른다. 색인 조회라 값이 커도 싸지만 {@code IN} 목록이 무한정 길어지면
	 * 질의 계획이 무너진다.
	 */
	private static final int MAX_SCANS = 500;
	/** 키워드 수 상한 — 원본과 같다. */
	private static final int MAX_KEYWORDS = 20;

	private final DocumentIndexRepository repository;

	public AttachmentScanController(DocumentIndexRepository repository) {
		this.repository = repository;
	}

	@Operation(summary = "첨부 본문 키워드 검색",
			description = "공고 묶음과 키워드를 받아, 색인된 첨부 본문에서 걸린 것만 발췌와 함께 돌려준다. "
					+ "상류(나라장터) 호출 없음. `exclusions` 는 아직 항상 비어 있다(입찰 불가 조항 판정 미이식).")
	@PostMapping("/scan-attachments")
	public ResponseEntity<Map<String, Object>> scan(@RequestBody(required = false) ScanRequest request) {
		List<Scan> scans = request == null || request.scans() == null ? List.of() : request.scans();
		List<String> keywords = normalizeKeywords(request == null ? null : request.fileKeywords());

		// 원본과 같은 조기 반환. 화면은 이 경우 봉투가 짧게 오는 것을 알고 있다.
		if (scans.isEmpty() || keywords.isEmpty()) {
			return ResponseEntity.ok(Map.of("matches", List.of(), "exclusions", List.of(), "scanned", 0));
		}

		List<Scan> items = scans.size() > MAX_SCANS ? scans.subList(0, MAX_SCANS) : scans;

		// 한 공고번호에 화면 행이 여럿 붙을 수 있다(같은 공고가 차수만 달리해 두 번 나오는 경우).
		// 조회는 공고번호 단위로 한 번만 하고, 결과를 행마다 나눠 준다.
		Map<String, List<String>> rowsByNotice = new LinkedHashMap<>();
		for (Scan scan : items) {
			String noticeId = scan.noticeId();
			if (noticeId != null && !noticeId.isBlank() && scan.id() != null) {
				rowsByNotice.computeIfAbsent(noticeId, key -> new ArrayList<>()).add(scan.id());
			}
		}
		List<String> noticeIds = List.copyOf(rowsByNotice.keySet());

		List<DocumentIndexRepository.DocumentText> documents = repository.findMatchable(noticeIds, keywords);
		Set<String> indexed = repository.findIndexedNoticeIds(noticeIds);

		// 공고 하나에 첨부가 여럿이면 가장 좋은 발췌 하나만 화면에 올린다 — 원본과 같은 모양이다.
		Map<String, KeywordExcerpt.Result> bestByNotice = new LinkedHashMap<>();
		Map<String, Set<String>> keywordsByNotice = new LinkedHashMap<>();
		for (DocumentIndexRepository.DocumentText document : documents) {
			KeywordExcerpt.Result result = KeywordExcerpt.best(document.bodyText(), keywords);
			if (result == null) {
				continue;   // SQL 이 후보로 준 것뿐 — 실제 판정은 여기다
			}
			keywordsByNotice.computeIfAbsent(document.noticeId(), key -> new LinkedHashSet<>())
					.addAll(result.matchedKeywords());
			bestByNotice.merge(document.noticeId(), result,
					(existing, candidate) -> candidate.matchedKeywords().size() > existing.matchedKeywords().size()
							? candidate
							: existing);
		}

		List<Map<String, Object>> matches = new ArrayList<>();
		for (Map.Entry<String, KeywordExcerpt.Result> entry : bestByNotice.entrySet()) {
			for (String rowId : rowsByNotice.getOrDefault(entry.getKey(), List.of())) {
				matches.add(Map.of(
						"id", rowId,
						"matchedKeywords", List.copyOf(keywordsByNotice.getOrDefault(entry.getKey(), Set.of())),
						"excerpt", entry.getValue().excerpt(),
						"documentTags", List.of()));
			}
		}

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("matches", matches);
		body.put("exclusions", List.of());
		body.put("scanned", items.size());
		body.put("candidateCount", request.candidateCount() == null ? items.size() : request.candidateCount());
		// 색인에서 바로 나온 공고 수. 원본에서 이 값은 "파일을 다시 안 받은 건수" 였고,
		// 색인 경로에서는 색인이 끝나 있던 공고가 그것에 해당한다.
		body.put("cacheHits", indexed.size());
		// 상류를 두드리지 않으므로 워밍업 큐라는 것이 없다. 프론트가 읽는 자리만 남긴다.
		body.put("warmQueued", 0);
		body.put("warmQueueDepth", 0);
		body.put("warmActive", 0);
		// 계약에 없는 추가 정보. 아직 색인 전인 공고가 몇 건인지 — "안 걸림" 과 구분해야 한다.
		body.put("notIndexed", noticeIds.size() - indexed.size());
		return ResponseEntity.ok(body);
	}

	private static List<String> normalizeKeywords(List<String> raw) {
		if (raw == null) {
			return List.of();
		}
		List<String> keywords = new ArrayList<>();
		for (String keyword : raw) {
			String trimmed = keyword == null ? "" : keyword.trim();
			if (!trimmed.isEmpty() && keywords.size() < MAX_KEYWORDS) {
				keywords.add(trimmed);
			}
		}
		return keywords;
	}

	/** 요청 봉투 — 원본 계약 그대로. 쓰지 않는 필드도 받아 둬야 역직렬화가 깨지지 않는다. */
	public record ScanRequest(
			List<Scan> scans,
			List<String> fileKeywords,
			Boolean excludeBlockingClauses,
			Boolean scanBlocking,
			Integer candidateCount,
			Integer immediateLimit,
			Integer warmLimit) {}

	/**
	 * 화면 행 하나.
	 *
	 * @param id           화면이 응답을 되짚는 내부 키. 서버는 의미를 모르고 그대로 돌려준다
	 * @param bidNtceNo    공고번호. 색인 조회의 유일한 열쇠다
	 * @param fileEntries  원본에서 다운로드 대상이었다. 색인 경로에서는 쓰지 않지만 받아는 둔다
	 */
	public record Scan(
			String id,
			String bidNtceNo,
			String bidNtceSqNo,
			String _type,
			String _tab,
			String _version,
			List<Map<String, Object>> fileEntries) {

		String noticeId() {
			return bidNtceNo;
		}
	}
}
