package com.electerior.g2bmaster.index;

import com.electerior.g2bmaster.common.G2bDates;
import com.electerior.g2bmaster.common.G2bDates.DateWindow;
import com.electerior.g2bmaster.config.G2bProperties;
import com.electerior.g2bmaster.integration.g2b.G2bApiClient;
import com.electerior.g2bmaster.notice.BidEnrichment;
import com.electerior.g2bmaster.notice.BidResultRepository;
import com.electerior.g2bmaster.notice.G2bEndpoints;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * 나라장터 낙찰정보 → 로컬 {@code bid_result} 색인 적재기.
 *
 * <p>{@link BidNoticeIngestService} 와 같은 자리에 있고 같은 뼈대를 쓴다 — 워터마크로 구간을
 * 정하고, 31일 창으로 잘라 훑고, 회차 상한에 걸리면 <b>실제로 훑은 끝까지만</b> 워터마크를
 * 전진시킨다. 다른 것은 대상 오퍼레이션({@code ScsbidInfoService})과 저장 테이블뿐이다.
 *
 * <p><b>왜 별도 적재기인가.</b> 공고 적재기는 출처 표 · 매퍼 · 마감 전이 · 지역 보강 · 백필
 * 줄기까지 얽혀 있고 그 전부가 {@code bid_notice} 한 테이블을 향한다. 낙찰정보는 컬럼 집합도
 * 다르고(마이그레이션 {@code V20260826120000} 머리주석) 마감이라는 상태도 지역도 없다.
 * 거기에 끼워 넣으면 {@code Source} 레코드에 "이 출처는 매퍼가 다르다"는 분기가 하나 더 는다.
 *
 * <p><b>워터마크는 {@code bid_notice_sync_state} 를 함께 쓴다</b> — 마이그레이션이 지시한 대로다
 * ({@code source = 'bid-result:물품'}). 그래서 {@link BidNoticeIndexRepository} 를 상태 기록에만
 * 주입받는다. 다만 {@code target_to} 는 <b>절대 쓰지 않는다</b>: 그 칸이 채워진 행은
 * {@link BidNoticeIndexRepository#pendingBackfills()} 가 접두사 구분 없이 전부 집어 가고,
 * {@link BidNoticeIngestService#backfillOnce()} 가 {@code backfill:} 을 잘라낸 키로 공고 출처
 * 표를 뒤지다 매 회차 경고만 남긴다.
 *
 * <p><b>과거 구간은 수동 적재로 채운다</b>({@code POST /api/bid-result/sync?backfillDays=N}).
 * 공고 쪽처럼 백필 줄기를 따로 세우지 않은 이유는 낙찰정보에 신선도 압박이 없기 때문이다 —
 * 이미 끝난 입찰이라 "오늘 것이 몇 시간 늦으면 기회를 놓친다"는 성질이 없다. 대신 되감은
 * 워터마크가 회차마다 {@link #MAX_ROWS_PER_RUN} 만큼 전진하며 스스로 따라잡는다.
 */
@Service
public class BidResultIngestService {

	private static final Logger log = LoggerFactory.getLogger(BidResultIngestService.class);

	private static final ObjectMapper JSON = JsonMapper.builder().build();

	/** 상태 행 키의 접두사. {@code bid-result:물품} 처럼 업종을 붙여 쓴다. */
	static final String SOURCE_PREFIX = "bid-result:";

	/**
	 * 워터마크에서 물러나 다시 읽는 폭.
	 *
	 * <p>등록 직후의 낙찰 건이 목록 API 에 즉시 나타나지 않는 일이 있어, 딱 이어 붙이면 그 틈에
	 * 들어온 건이 영영 색인되지 않는다. 겹쳐 읽은 건은 upsert 가 흡수한다.
	 */
	static final int OVERLAP_MINUTES = 30;

	/** 워터마크가 없는 첫 회차에 거슬러 올라갈 기간(일). 설정이 없으면 공고 쪽과 같은 7일. */
	static final int DEFAULT_BACKFILL_DAYS = 7;

	/**
	 * 한 업종·한 회차가 받아올 행 수 상한.
	 *
	 * <p>낙찰정보는 한 업종 한 달이 7,000건 안팎(실측)이라 31일 창 두 개면 상한에 닿는다.
	 * 걸리면 그 회차는 거기까지만 색인하고 다음 회차가 이어 받는다.
	 */
	static final int MAX_ROWS_PER_RUN = 20_000;

	private static final DateTimeFormatter G2B_DT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

	/** 창 하나가 상한을 넘겨 반으로 쪼갤 때 그 재귀가 끝나지 않는 것을 막는 깊이. */
	private static final int MAX_SPLIT_DEPTH = 5;

	/** 적재 한 회차의 결과. 운영 엔드포인트와 로그가 읽는다. */
	public record SourceResult(String source, int fetched, int indexed, boolean ok, String message) {}

	/** 회차 전체의 결과. */
	public record IngestResult(List<SourceResult> sources, int totalIndexed) {}

	/** {@link #fetchWindows} 의 결과. {@code capped} 면 {@code coveredTo} 까지만 훑었다. */
	private record FetchResult(List<Map<String, Object>> items, boolean capped, LocalDateTime coveredTo) {}

	private final G2bApiClient client;

	private final G2bEndpoints endpoints;

	private final BidResultRepository repository;

	/** 워터마크·실패 이력 전용. 낙찰정보 행은 여기에 쓰지 않는다. */
	private final BidNoticeIndexRepository stateRepository;

	private final int firstRunBackfillDays;

	private final boolean scheduleEnabled;

	/** 회차 겹침 방지. 수동 적재와 주기 적재가 같은 잠금을 쥔다. */
	private final AtomicBoolean running = new AtomicBoolean(false);

	public BidResultIngestService(G2bApiClient client, G2bEndpoints endpoints,
			BidResultRepository repository, BidNoticeIndexRepository stateRepository,
			G2bProperties properties) {
		this.client = client;
		this.endpoints = endpoints;
		this.repository = repository;
		this.stateRepository = stateRepository;
		// 설정이 없는 경로(일부 테스트)에서도 뜨도록 기본값을 둔다.
		G2bProperties.Index index = properties.index();
		this.firstRunBackfillDays = index == null || index.backfillDays() <= 0
				? DEFAULT_BACKFILL_DAYS
				: index.backfillDays();
		this.scheduleEnabled = index != null && index.enabled();
	}

	// ── 실행 ────────────────────────────────────────────────────────────────

	/**
	 * 주기 적재.
	 *
	 * <p>주기를 공고(5분)보다 길게 둔 것은 물량과 급함이 다르기 때문이다 — 낙찰정보는 이미 끝난
	 * 입찰이라 몇 분 늦어도 잃는 것이 없고, 업종 셋을 훑는 호출이 매번 나간다.
	 *
	 * <p>{@code fixedDelayString} 인 것에 뜻이 있다({@link BidNoticeSyncScheduler#ingest()} 와
	 * 같은 이유) — {@code fixedRate} 로 두면 되감은 워터마크를 따라잡는 동안 다음 실행이 밀려
	 * 쌓이고, 결국 나라장터에 동시 요청 폭풍을 낸다.
	 */
	@Scheduled(fixedDelayString = "${g2b.index.bid-result-interval-ms:600000}", initialDelay = 60_000)
	public void ingestScheduled() {
		if (!scheduleEnabled) {
			return;
		}
		if (runNow(0) == null) {
			log.debug("낙찰정보 적재가 이미 돌고 있어 이번 회차는 건너뜁니다.");
		}
	}

	/**
	 * 회차 하나. 잠금을 쥐고 업종 전부를 훑는다.
	 *
	 * <p>스케줄러도 수동 적재도 이리로 들어온다. 스위치({@code g2b.index.enabled})는 "저절로 돌
	 * 것인가"를 정하는 것이지 "운영자가 시켜도 안 돈다"는 뜻이 아니므로 여기서는 보지 않는다.
	 *
	 * @param backfillDays <b>0 이면 평시 증분</b>. 0보다 크면 워터마크를 무시하고 그만큼 거슬러
	 *                     올라가 다시 읽는다(구간을 넓히는 쪽으로만 작동한다)
	 * @return 이미 돌고 있어 물러났으면 {@code null}
	 */
	public IngestResult runNow(int backfillDays) {
		if (!running.compareAndSet(false, true)) {
			return null;
		}
		try {
			LocalDateTime now = LocalDateTime.now();
			List<SourceResult> results = new ArrayList<>();
			int totalIndexed = 0;
			for (Map.Entry<String, String> entry : endpoints.bidResult().entrySet()) {
				SourceResult result = ingestOne(entry.getKey(), entry.getValue(), now, backfillDays);
				results.add(result);
				totalIndexed += result.indexed();
			}
			return new IngestResult(List.copyOf(results), totalIndexed);
		}
		finally {
			running.set(false);
		}
	}

	/**
	 * 업종 하나. 예외를 밖으로 내보내지 않고 결과에 담는다.
	 *
	 * <p>한 업종이 실패해도 나머지는 계속한다 — 나라장터는 오퍼레이션 단위로 점검·장애가 나므로,
	 * 하나가 죽었다고 전체를 멈추면 멀쩡한 둘의 데이터까지 낡는다.
	 */
	SourceResult ingestOne(String bidType, String url, LocalDateTime now, int backfillDays) {
		String stateKey = SOURCE_PREFIX + bidType;
		LocalDateTime from = startOf(stateKey, now, backfillDays);
		if (!from.isBefore(now)) {
			return new SourceResult(stateKey, 0, 0, true, "구간 없음");
		}
		try {
			FetchResult fr = fetchWindows(url, from, now);
			List<BidResultRepository.Row> rows = new ArrayList<>(fr.items().size());
			int undated = 0;
			for (Map<String, Object> item : fr.items()) {
				BidResultRepository.Row row = toRow(bidType, item);
				if (row == null) {
					undated++;
					continue;
				}
				rows.add(row);
			}
			if (undated > 0) {
				// 등록일시가 없으면 조회 창에 놓을 자리가 없다 — 넣어도 어떤 검색에도 안 걸린다.
				// 버리되 몇 건인지는 남긴다(조용히 사라지는 것이 가장 나쁘다).
				log.warn("낙찰정보 {} — 등록일시를 읽지 못한 {}건을 건너뜁니다.", stateKey, undated);
			}
			List<BidResultRepository.Row> deduped = dedupeKeepLast(rows);

			repository.upsertAll(deduped);
			// 상한에 걸렸으면 실제로 훑은 끝(coveredTo)까지만 전진시킨다. now 로 전진하면
			// 미훑은 창이 영영 사라진다(공고 적재기와 같은 불변식이다).
			LocalDateTime watermark = fr.capped() ? fr.coveredTo() : now;
			String note = fr.capped()
					? "성공(부분): %d건 조회 / %d건 색인 — %s까지 커버, 다음 회차 이어서"
							.formatted(fr.items().size(), deduped.size(), fr.coveredTo().format(G2B_DT))
					: "성공: %d건 조회 / %d건 색인".formatted(fr.items().size(), deduped.size());
			stateRepository.recordSuccess(stateKey, watermark, deduped.size(), note);
			log.info("색인 {} — 조회 {}건, 색인 {}건{}", stateKey, fr.items().size(), deduped.size(),
					fr.capped() ? " (부분: " + fr.coveredTo().format(G2B_DT) + "까지)" : "");
			return new SourceResult(stateKey, fr.items().size(), deduped.size(), true, "ok");
		}
		catch (RuntimeException ex) {
			// 워터마크는 전진시키지 않는다 — 다음 회차가 같은 구간을 다시 시도해야 한다.
			String reason = ex.getMessage() == null ? ex.toString() : ex.getMessage();
			stateRepository.recordFailure(stateKey, reason);
			log.warn("색인 실패 {} — {}", stateKey, reason);
			return new SourceResult(stateKey, 0, 0, false, reason);
		}
	}

	// ── 내부 ────────────────────────────────────────────────────────────────

	/**
	 * 응답 한 건을 저장할 행으로 바꾼다.
	 *
	 * <p>보강({@code _type} + {@link BidEnrichment#enrichBidNotice})을 <b>적재 때</b> 끝내는 것이
	 * 핵심이다. 라이브 경로는 {@code NoticeFetchSupport.fetchEnriched} 가 응답마다 같은 일을
	 * 했으므로, 여기서 같은 순서로 해 두면 조회 경로가 꺼내 쓰는 행이 그때와 같은 모양이 된다.
	 *
	 * @return 공고번호나 등록일시가 없으면 {@code null} — 저장할 자리가 없다
	 */
	private static BidResultRepository.Row toRow(String bidType, Map<String, Object> item) {
		String bidNtceNo = str(item.get("bidNtceNo"));
		if (bidNtceNo.isEmpty()) {
			return null;
		}
		LocalDateTime rgstDt = G2bDates.parseG2bDt(item.get("rgstDt"));
		if (rgstDt == null) {
			return null;
		}
		// 라이브 경로(NoticeFetchSupport.fetchEnriched)와 같은 순서다 — 사본을 뜨고, _type 을
		// 먼저 얹고, 그 위에 보강을 돌린다. 순서가 바뀌면 _type 을 보고 값을 정하는 보강 항목이
		// 달라진다. 업종 키(물품/용역/공사)는 G2bEndpoints.typeOfUrl 이 그 URL 에서 뽑던 값과 같다.
		Map<String, Object> enriched = new LinkedHashMap<>(item);
		enriched.put("_type", bidType);
		BidEnrichment.enrichBidNotice(enriched);
		try {
			return new BidResultRepository.Row(bidNtceNo, bidType, JSON.writeValueAsString(enriched), rgstDt);
		}
		catch (JacksonException ex) {
			log.debug("낙찰정보 행을 JSON 으로 쓰지 못했습니다 ({}): {}", bidNtceNo, ex.getMessage());
			return null;
		}
	}

	/**
	 * 같은 (공고번호, 업종)이 여럿이면 <b>마지막 것</b>만 남긴다.
	 *
	 * <p>공고 적재기는 차수가 높은 쪽을 남기지만 낙찰정보에는 정정 차수가 없다. 응답이 등록일시
	 * 역순이 아니므로 "나중에 온 것이 최신"이라는 보장도 없지만, 같은 키가 한 창에 두 번 오는
	 * 것은 재입찰({@code rbidNo})뿐이고 그때 둘의 내용은 같다. 접는 목적은 우열이 아니라
	 * batchUpdate 안에서 같은 PK 를 두 번 건드리지 않는 것이다.
	 */
	static List<BidResultRepository.Row> dedupeKeepLast(List<BidResultRepository.Row> rows) {
		Map<String, BidResultRepository.Row> latest = new LinkedHashMap<>();
		for (BidResultRepository.Row row : rows) {
			latest.put(row.bidNtceNo() + "|" + row.bidType(), row);
		}
		return List.copyOf(latest.values());
	}

	/**
	 * 이번 회차가 읽어 올 구간의 시작 시각.
	 *
	 * <p>{@code forcedBackfillDays} 가 0 이면 평시 증분(워터마크 − 겹침)이다. 0보다 크면 운영자가
	 * "그만큼 거슬러 올라가 다시 읽어라"고 지시한 것이고, 그때도 <b>구간을 넓히는 쪽으로만</b>
	 * 작동한다(둘 중 이른 시각).
	 */
	private LocalDateTime startOf(String stateKey, LocalDateTime now, int forcedBackfillDays) {
		LocalDateTime watermark = stateRepository.readWatermark(stateKey);
		LocalDateTime incremental = watermark == null ? null : watermark.minusMinutes(OVERLAP_MINUTES);
		LocalDateTime forced = forcedBackfillDays > 0 ? now.minusDays(forcedBackfillDays) : null;

		if (incremental == null) {
			return forced != null ? forced : now.minusDays(firstRunBackfillDays);
		}
		if (forced == null) {
			return incremental;
		}
		return forced.isBefore(incremental) ? forced : incremental;
	}

	/**
	 * 기간을 API 가 삼킬 수 있는 창으로 잘라 전부 훑는다.
	 *
	 * <p>{@link G2bDates#splitG2bDateRange} 가 31일 상한을 지킨다 — 넘기면 결과코드 07
	 * (입력범위값 초과)이 나고 그 구간이 통째로 빈다.
	 *
	 * <p>회차 상한({@link #MAX_ROWS_PER_RUN})에 걸리면 <b>이미 담은 것이 있을 때는 쪼개지 않고
	 * 물러난다</b>. 쪼개 봐야 남은 예산이 짧아 또 걸리고, 그때마다 부분으로 받아온 것을 버리므로
	 * 같은 구간을 여러 번 내려받게 된다. 회차의 첫 창인데도 넘겼을 때만 진짜로 '창이 너무 큰'
	 * 경우이고, 그때는 쪼개야 진행한다(안 쪼개면 워터마크가 영원히 제자리다).
	 */
	private FetchResult fetchWindows(String url, LocalDateTime from, LocalDateTime to) {
		List<Map<String, Object>> all = new ArrayList<>();
		// 완전히 훑은 가장 먼 끝. 한 창도 못 훑으면 from(=진행 없음)이 된다.
		LocalDateTime coveredTo = from;
		List<DateWindow> pending = new ArrayList<>(
				G2bDates.splitG2bDateRange(from.format(G2B_DT), to.format(G2B_DT)));
		int splits = 0;
		int i = 0;
		while (i < pending.size()) {
			int remaining = MAX_ROWS_PER_RUN - all.size();
			if (remaining <= 0) {
				break;
			}
			DateWindow w = pending.get(i);
			boolean firstOfRun = all.isEmpty();
			Map<String, Object> params = new LinkedHashMap<>();
			// inqryDiv=1 은 '등록일시 기준'이다. 라이브 경로도 같은 값을 썼으므로
			// (G2bFetchService.fetchUrl 의 putIfAbsent) 이 색인이 덮는 축도 등록일시다.
			params.put("inqryDiv", 1);
			params.put("inqryBgnDt", w.from());
			params.put("inqryEndDt", w.to());

			List<Map<String, Object>> got = client.fetchAllPages(url, params,
					G2bApiClient.LOADER_PAGE_SIZE, remaining);
			if (got.size() < remaining) {
				all.addAll(got);
				coveredTo = G2bDates.parseG2bDt(w.to());
				i++;
				continue;
			}
			if (!firstOfRun) {
				break;
			}
			List<DateWindow> halves = splits < MAX_SPLIT_DEPTH
					? G2bDates.splitG2bRangeHalf(w.from(), w.to())
					: null;
			if (halves == null) {
				// 더 쪼갤 수 없다(단일 날짜이거나 깊이 상한). 받아온 만큼만 남기고 여기서 끝낸다.
				all.addAll(got);
				log.error("낙찰정보 적재: 창 하나가 상한({})을 넘어 전체 커버 불가 — {} ~ {} ({}). 다음 회차에서 재시도.",
						MAX_ROWS_PER_RUN, w.from(), w.to(), url);
				break;
			}
			splits++;
			// 부분으로 받아온 got 은 접두어라 버린다 — 반으로 다시 훑으면 겹침 없이 전체를 담는다.
			pending.subList(i, i + 1).clear();
			pending.addAll(i, halves);
		}
		boolean capped = coveredTo.isBefore(to);
		if (capped) {
			log.warn("낙찰정보 적재 상한 {}건에 도달해 이번 회차는 {}까지만 훑었습니다 — 다음 회차가 이어서: {}",
					MAX_ROWS_PER_RUN, coveredTo.format(G2B_DT), url);
		}
		return new FetchResult(all, capped, coveredTo);
	}

	private static String str(Object value) {
		return value == null ? "" : String.valueOf(value).trim();
	}
}
