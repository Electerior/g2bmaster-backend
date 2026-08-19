package com.electerior.g2bmaster.index;

import com.electerior.g2bmaster.common.ApiException;
import com.electerior.g2bmaster.common.PagedResponse;
import com.electerior.g2bmaster.config.G2bProperties;
import com.electerior.g2bmaster.config.OpenApiConfig;
import com.electerior.g2bmaster.security.RequireAppAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 통합 공고 검색 — 계획 · 사전규격 · 입찰 · 마감을 한 번에.
 *
 * <p>기존 검색 4탭({@code /api/bid-announce} 등)과 <b>공존한다</b>. 그쪽은 요청마다 나라장터를
 * 팬아웃해 D2B·누리장터까지 훑고, 이쪽은 로컬 색인만 본다. 둘은 대체 관계가 아니라 용도가
 * 다르다 — 넓게 훑어야 할 때는 팬아웃이, 빠르게 좁혀야 할 때는 색인이 맞다.
 *
 * <p>응답 봉투는 {@code {items, totalCount, pageNo, numOfRows}} 로 기존 검색과 같다
 * ({@code docs/api-contract.md} §1.1 의 첫 번째 모양). 프론트의 페이징 컴포넌트를 그대로
 * 쓰기 위해서다. 여기에 {@code meta.attachmentSearch} 한 칸이 더 붙는다.
 *
 * <h2>검색이 둘인 이유 — {@code /} 와 {@code /text}</h2>
 * <p><b>기본 검색은 첨부 본문까지 본다.</b> 사용자가 '리튬인산철'을 치는 이유는 그 낱말이 든
 * 공고를 찾으려는 것이지 <em>공고 제목에</em> 그 낱말이 든 공고를 찾으려는 것이 아니다.
 * 규격서에만 적힌 사양이 검색에서 빠지면, 화면은 "그런 공고가 없다"고 거짓말을 한다.
 *
 * <p>그래서 <b>첨부를 안 보는 검색을 따로 뺀다</b>({@code /text}). 옵션 플래그가 아니라 경로를
 * 가르는 이유는 두 검색의 <b>비용과 의미가 다르기 때문</b>이다 — 첨부 스코프는 UNION 두 갈래라
 * 실측 5.3ms → 19.9ms 고, 첨부 색인이 없는 공고는 애초에 후보가 되지 못한다. 자동완성·타이핑
 * 중 미리보기처럼 "지금 당장, 제목에서" 찾아야 하는 경로가 그것을 파라미터 하나로 고르면
 * 어느 화면이 무엇을 본 검색인지 아무도 설명하지 못하게 된다.
 *
 * <p>⚠ {@code /text} 는 {@code /{id}} 와 같은 자리를 놓고 겨룬다. 스프링은 리터럴을 먼저 고르므로
 * 동작은 정확하지만, 공고번호가 정확히 {@code text} 인 건은 상세 조회로 갈 수 없다 — 공고번호는
 * 숫자와 하이픈으로만 이뤄져 실제로는 생기지 않는 충돌이고, 그래서 감수한다.
 */
@RestController
@RequestMapping("/api/search/notices")
@Tag(name = OpenApiConfig.TAG_INDEX_SEARCH)
public class NoticeSearchController {

	private final BidNoticeSearchService searchService;
	private final BidNoticeSyncScheduler scheduler;
	private final BidNoticeIndexRepository repository;
	private final BidNoticeIngestService ingestService;
	private final NoticeMarginService marginService;

	/**
	 * 기본 검색이 첨부까지 볼 것인가. 기본 켜짐이고, 끄면 {@code /} 가 {@code /text} 와 같아진다.
	 * 이유는 {@code G2bProperties.Search} 주석 참고 — 배포 없이 되돌리기 위한 비상 손잡이다.
	 */
	private final boolean attachmentBody;

	public NoticeSearchController(BidNoticeSearchService searchService, BidNoticeSyncScheduler scheduler,
			BidNoticeIndexRepository repository, BidNoticeIngestService ingestService,
			NoticeMarginService marginService, G2bProperties properties) {
		this.searchService = searchService;
		this.scheduler = scheduler;
		this.repository = repository;
		this.ingestService = ingestService;
		this.marginService = marginService;
		// 설정 블록이 없는 경로(일부 테스트)에서도 뜨도록 기본값을 둔다.
		this.attachmentBody = properties.search() == null || properties.search().attachmentBody();
	}

	// ── 기본 검색 (공고 텍스트 + 첨부 본문) ─────────────────────────────────

	@Operation(summary = "공고 통합 검색 (첨부 본문 포함)",
			description = "로컬 색인만 조회한다(나라장터 호출 없음). 검색어는 공고 제목·본문과 **첨부 본문**"
					+ "(bid_notice_document) 양쪽에서 찾고, 제외 낱말도 첨부까지 적용한다. "
					+ "관련도는 공고 텍스트 매치 점수이므로 첨부에서만 걸린 공고는 뒤로 간다. "
					+ "행마다 matchedIn(notice/attachment)·attachmentIndexed 가, 응답에는 "
					+ "meta.attachmentSearch(커버리지·건너뛴 낱말)가 붙는다. "
					+ "첨부를 보지 않는 가벼운 검색은 GET /api/search/notices/text 를 쓴다.\n\n"
					+ "정렬(sort): created·close·name·updated·amount·margin·relevance. "
					+ "**margin** 은 마진율순이다 — (실추정가 − 원가)/실추정가, 실추정가 = 대표금액 × 1.1. "
					+ "원가를 아는 공고(딜 분석을 돌렸거나 가격표를 저장한 건)만 값이 있고 나머지는 "
					+ "marginRate=null 로 뒤에 붙는다. 결과 집합은 줄지 않는다.")
	@GetMapping
	public PagedResponse<Map<String, Object>> search(@ModelAttribute NoticeSearchRequest request) {
		return searchService.search(request, attachmentBody);
	}

	@Operation(summary = "검색 패싯 (첨부 본문 포함)",
			description = "같은 조건에서 분류·업종·지역·상태별 건수. 검색과 **반드시 같은 후보 집합**을 센다 — "
					+ "칩의 건수와 목록이 어긋나면 화면이 거짓말을 하게 된다.")
	@GetMapping("/facets")
	public Map<String, Object> facets(@ModelAttribute NoticeSearchRequest request) {
		return searchService.facets(request, attachmentBody);
	}

	// ── 공고 텍스트만 보는 검색 ─────────────────────────────────────────────

	@Operation(summary = "공고 텍스트 검색 (첨부 제외)",
			description = "제목·본문만 본다. 첨부 색인 상태와 무관하게 결과가 일정하고 질의가 한 갈래라 "
					+ "빠르다(실측 5.3ms vs 첨부 포함 19.9ms) — 자동완성처럼 응답 시간이 곧 기능인 경로용이다. "
					+ "파라미터·응답 모양은 기본 검색과 같다.")
	@GetMapping("/text")
	public PagedResponse<Map<String, Object>> searchNoticeText(@ModelAttribute NoticeSearchRequest request) {
		return searchService.search(request, false);
	}

	@Operation(summary = "공고 텍스트 검색 패싯 (첨부 제외)",
			description = "GET /api/search/notices/text 와 같은 후보 집합의 패싯.")
	@GetMapping("/text/facets")
	public Map<String, Object> noticeTextFacets(@ModelAttribute NoticeSearchRequest request) {
		return searchService.facets(request, false);
	}

	/**
	 * 색인 현황.
	 *
	 * <p>검색 앞에 둔 이유는 경로 충돌 때문이 아니라 <b>순서</b> 때문이다 — 스프링은 더 구체적인
	 * 패턴을 먼저 고르지만, {@code /{id}} 가 위에 있으면 읽는 사람이 헷갈린다.
	 */
	@Operation(summary = "색인 현황",
			description = "출처별 워터마크·마지막 결과와 분류별 색인 건수. 색인이 언제 것인지 화면에 표시한다.")
	@GetMapping("/status")
	public Map<String, Object> status() {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("sources", repository.syncStates());
		body.put("summary", repository.indexSummary());
		body.put("knownSources", ingestService.sourceKeys());
		return body;
	}

	@Operation(summary = "공고 상세", description = "공고 본문 전문을 포함한다. "
			+ "같은 번호가 여러 출처에 있을 수 있으므로 source(G2B/NURI/D2B)로 특정할 수 있다 — "
			+ "미지정이면 G2B → NURI → D2B 순으로 먼저 있는 행을 준다.")
	@GetMapping("/{id}")
	public Map<String, Object> detail(@PathVariable("id") String id,
			@RequestParam(name = "source", required = false) String source) {
		Map<String, Object> found = searchService.findOne(id, source);
		if (found == null) {
			// 문구는 화면에 그대로 뜬다 — docs/api-contract.md §1.1 참고.
			throw ApiException.notFound("색인에 없는 공고입니다: " + id);
		}
		return found;
	}

	/**
	 * 수동 적재.
	 *
	 * <p>쓰기이자 비용(나라장터 쿼터)이 드는 경로라 앱 키를 요구한다. 초기 구축과 장애 복구용이고,
	 * 평시에는 스케줄러가 알아서 돈다.
	 */
	@Operation(summary = "색인 수동 적재",
			description = "나라장터에서 즉시 받아와 색인한다. backfillDays 로 거슬러 올라갈 기간을 지정한다. "
					+ "이미 적재가 돌고 있으면 409 를 돌려준다.")
	@PostMapping("/sync")
	@RequireAppAuth
	public Map<String, Object> sync(
			@RequestParam(name = "backfillDays", required = false, defaultValue = "0") int backfillDays) {
		BidNoticeIngestService.IngestResult result = scheduler.runNow(backfillDays);
		if (result == null) {
			throw new ApiException(org.springframework.http.HttpStatus.CONFLICT,
					"이미 색인 적재가 진행 중입니다. 잠시 후 다시 시도하세요.");
		}
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("ok", true);
		body.put("totalIndexed", result.totalIndexed());
		body.put("sweptToClosed", result.sweptToClosed());
		body.put("sources", result.sources());
		return body;
	}

	/**
	 * 마진 축 백필.
	 *
	 * <p>평시에는 딜 분석과 공고 저장이 그때그때 채운다. 이 경로는 <b>이미 쌓여 있던</b> 원가를
	 * 한 번에 끌어올리기 위한 것이다 — 마진 컬럼을 처음 붙였을 때, 그리고 색인을 다시 쌓아
	 * 마진이 날아갔을 때.
	 *
	 * <p>쓰기라 앱 키를 요구한다. 나라장터를 두드리지 않으므로 {@code /sync} 와 달리 쿼터는 안 든다.
	 */
	@Operation(summary = "마진 축 백필",
			description = "이미 저장된 원가(deal_analysis_result 의 추정 원가 · saved_notice 의 확정 가격표 합계)를 "
					+ "검색 색인의 마진 컬럼으로 끌어올린다. 확정이 추정을 덮는다. 나라장터 호출은 없다. "
					+ "이후 GET /api/search/notices?sort=margin 이 그 값으로 정렬한다.")
	@PostMapping("/margins/backfill")
	@RequireAppAuth
	public Map<String, Object> backfillMargins() {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("ok", true);
		body.putAll(marginService.backfill());
		return body;
	}
}
