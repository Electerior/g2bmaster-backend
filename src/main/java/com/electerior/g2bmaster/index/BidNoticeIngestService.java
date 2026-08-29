package com.electerior.g2bmaster.index;

import com.electerior.g2bmaster.common.G2bDates;
import com.electerior.g2bmaster.common.G2bDates.DateWindow;
import com.electerior.g2bmaster.config.G2bProperties;
import com.electerior.g2bmaster.integration.d2b.D2bClient;
import com.electerior.g2bmaster.integration.d2b.D2bNormalizer;
import com.electerior.g2bmaster.integration.g2b.G2bApiClient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 나라장터 → 로컬 검색 색인 적재기.
 *
 * <p>이 서비스가 <b>유일하게</b> 나라장터를 두드린다(검색 경로에서 상류 호출을 없애는 것이
 * 이 구조의 목적이다). 사용자 요청은 {@link BidNoticeSearchService} 를 통해 로컬 DB 만 본다.
 *
 * <h2>왜 출처가 아홉인가</h2>
 * <pre>
 *   입찰공고 4 (물품·용역·공사·외자)  — category = 입찰/마감
 *   발주계획 4 (물품·일반용역·공사·외자) — category = 계획
 *   사전규격 3 (물품·용역·공사)        — category = 사전규격
 *   참가가능지역 1                      — region 보강
 * </pre>
 * 업종이 각각 다른 오퍼레이션인 것이 나라장터 API 의 근본 제약이고, 그래서 사용자 요청마다
 * 이 팬아웃을 재현할 수 없다 — 주기 적재가 선택이 아니라 필연인 이유다.
 *
 * <h2>워터마크와 겹침</h2>
 * <p>출처마다 "어디까지 받았는가"를 {@code bid_notice_sync_state} 에 남기고 다음 회차는 그
 * 뒤부터 읽는다. 다만 워터마크에서 <b>{@link #OVERLAP_MINUTES}분 뒤로 물러나</b> 시작한다 —
 * 등록 직후의 공고가 목록 API 에 즉시 나타나지 않는 일이 있어, 딱 이어 붙이면 그 틈에 들어온
 * 건이 영영 색인되지 않는다. 겹쳐 읽은 건은 upsert 가 흡수하므로 중복이 생기지 않는다.
 */
@Service
public class BidNoticeIngestService {

	private static final Logger log = LoggerFactory.getLogger(BidNoticeIngestService.class);

	/** 워터마크에서 물러나 다시 읽는 폭. 위 '워터마크와 겹침' 참고. */
	static final int OVERLAP_MINUTES = 30;

	/** 워터마크가 없는 첫 회차에 거슬러 올라갈 기간(일). */
	static final int DEFAULT_BACKFILL_DAYS = 7;

	/**
	 * 백필 줄기의 상태 키 접두사.
	 *
	 * <p>출처 하나가 {@code bid_notice_sync_state} 에 행을 <b>둘</b> 갖는다는 것이 백필 설계의
	 * 전부다 — 머리 줄기({@code bid-announce:물품})는 지금을 쫓고, 과거 줄기
	 * ({@code backfill:bid-announce:물품})는 5년 전에서 지금을 향해 전진한다. 둘은 같은 upsert 로
	 * 같은 테이블에 쓰고, 차수 가드가 있어 겹쳐도 안전하다.
	 *
	 * <p>워터마크 하나를 되감아 쓰지 않은 이유가 이것이다. 되감으면 따라잡을 때까지
	 * <b>오늘 올라온 공고가 색인되지 않는다</b>(5년치 실측 추정 3~6시간). 백필은 급하지 않고
	 * 신선도는 급하다 — 둘을 한 커서에 태우면 급한 쪽이 진다.
	 */
	static final String BACKFILL_PREFIX = "backfill:";

	/**
	 * 백필 기본 범위(일) — 5년.
	 *
	 * <p>윤년을 세어 1826일이다(365×5 + 1). 상류가 이 정도 과거를 실제로 준다는 것은
	 * 실측으로 확인했다(2026-08-26: 입찰·발주계획·사전규격·누리장터·참가가능지역 모두
	 * 2021-08 창에 정상 응답, 조달청 입찰물품 한 달치 10,176건).
	 */
	static final int DEFAULT_HISTORY_DAYS = 1826;

	/**
	 * 한 출처·한 회차의 행 수 <b>예산</b>.
	 *
	 * <p>백필을 크게 잡으면 한 오퍼레이션이 수십만 건을 들고 오다가 힙과 일일 쿼터를 동시에
	 * 태운다. 예산이 바닥나면 그 회차는 거기까지만 색인하고, 워터마크가 조금씩 전진하며
	 * 다음 회차들이 나머지를 따라잡는다.
	 *
	 * <p>딱 떨어지는 상한이 아니라 <b>예산</b>인 것에 뜻이 있다. 이 값은 "창을 새로 열 것인가"만
	 * 정하고, 한 번 연 창은 끝까지 받는다({@link #fetchWindows}) — 워터마크가 창 경계에서만
	 * 전진하므로, 잘라 받은 뒷부분은 어차피 버려지기 때문이다. 그래서 한 회차가 실제로
	 * 담는 행은 이 값을 마지막 창 크기만큼 넘을 수 있다.
	 */
	static final int MAX_ROWS_PER_RUN = 20_000;

	private static final DateTimeFormatter G2B_DT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

	/** 적재 한 회차의 결과. 운영 화면과 로그가 읽는다. */
	public record SourceResult(String source, int fetched, int indexed, boolean ok, String message) {}

	/**
	 * {@link #fetchWindows} 의 결과.
	 *
	 * <p>{@code capped} 가 {@code true} 면 이번 회차가 {@link #MAX_ROWS_PER_RUN} 예산에 걸려
	 * 전체 구간을 훑지 못했다. 그때 {@code coveredTo} 는 <b>마지막으로 완전히 훑은 창 끝</b>이고,
	 * 그 시각까지만 워터마크를 전진시켜야 한다 — 구간 끝으로 전진하면 미훑은 창이
	 * 영영 사라진다(5년 백필에서 수만 건 누락).
	 *
	 * <p>{@code stoppedBy} 는 <b>상류 오류 때문에</b> 중간에 멈췄을 때의 사유다(평소 {@code null}).
	 * 예산 소진으로 멈춘 것과 구별해야 한다 — 둘 다 '부분 성공'이지만 운영자가 할 일이 다르다.
	 *
	 * <p>{@code capped} 가 {@code false} 면 남은 창이 없다는 뜻이고, 그때 호출자는
	 * {@code coveredTo} 가 아니라 <b>구간 끝을 그대로</b> 워터마크로 쓴다. {@code coveredTo} 는
	 * G2B 형식(분 단위)이라 초 아래가 잘려 있어서, 그것을 쓰면 워터마크가 상한에 영원히
	 * 못 닿는다 — 상한이 있는 백필 줄기가 끝나지 못한다.
	 */
	record FetchResult(List<Map<String, Object>> items, boolean capped, LocalDateTime coveredTo,
			String stoppedBy) {}

	public record IngestResult(List<SourceResult> sources, int totalIndexed, int sweptToClosed) {}

	/** 출처 하나 — 오퍼레이션 URL 과 그것이 뜻하는 소스·분류·업종. */
	record Source(String key, String url, NoticeSource origin, NoticeCategory category,
			BusinessDivision division) {}

	/** D2B 출처 하나 — URL 이 아니라 오퍼레이션 이름으로 부른다(D2bClient 규약). */
	record D2bSource(String key, String operation, BusinessDivision division) {}

	private final G2bApiClient client;
	private final D2bClient d2bClient;
	private final BidNoticeIndexRepository repository;
	private final List<Source> sources;
	private final List<D2bSource> d2bSources;
	private final String regionUrl;
	private final String nuriRegionUrl;

	/** 워터마크가 아직 없는 출처가 처음 돌 때 거슬러 올라갈 기간(일). */
	private final int firstRunBackfillDays;

	/** 백필 지시에 기간이 없을 때 쓸 기본 범위(일). 기본 5년. */
	private final int historyDays;

	public BidNoticeIngestService(G2bApiClient client, D2bClient d2bClient,
			BidNoticeIndexRepository repository, G2bProperties properties) {
		this.client = client;
		this.d2bClient = d2bClient;
		this.repository = repository;
		String base = trimTrailingSlash(properties.openapi().baseUrl());
		this.sources = buildSources(base);
		this.d2bSources = buildD2bSources();
		this.regionUrl = base + "/ad/BidPublicInfoService/getBidPblancListInfoPrtcptPsblRgn";
		this.nuriRegionUrl = base + "/ao/PrvtBidNtceService/getPrvtBidPblancListInfoPrtcptPsblRgn";
		// 설정이 없는 경로(일부 테스트)에서도 뜨도록 기본값을 둔다.
		this.firstRunBackfillDays = properties.index() == null || properties.index().backfillDays() <= 0
				? DEFAULT_BACKFILL_DAYS
				: properties.index().backfillDays();
		this.historyDays = properties.index() == null || properties.index().historyDays() <= 0
				? DEFAULT_HISTORY_DAYS
				: properties.index().historyDays();
	}

	/**
	 * 오퍼레이션 표.
	 *
	 * <p>업종 이름이 출처마다 어긋나는 것이 여기서 드러난다 — 입찰공고는 {@code Servc}(용역),
	 * 발주계획은 {@code GnrlServc}(일반용역)다. 접미사를 규칙으로 만들지 않고 하나씩 적는 이유가
	 * 그것이다. 규칙으로 접으면 언젠가 한 오퍼레이션이 404 로 조용히 빠진다.
	 */
	private static List<Source> buildSources(String base) {
		String announce = base + "/ad/BidPublicInfoService/getBidPblancListInfo";
		String plan = base + "/ao/PrcrmntReqInfoService/getPrcrmntReqInfoList";
		String preSpec = base + "/ao/HrcspSsstndrdInfoService/getPublicPrcureThngInfo";
		String prvt = base + "/ao/PrvtBidNtceService/getPrvtBidPblancListInfo";

		List<Source> list = new ArrayList<>();
		// 입찰공고 — category 는 마감일시를 보고 매퍼가 입찰/마감으로 가른다.
		list.add(new Source("bid-announce:물품", announce + "ThngPPSSrch", NoticeSource.G2B, NoticeCategory.입찰, BusinessDivision.물품));
		list.add(new Source("bid-announce:용역", announce + "ServcPPSSrch", NoticeSource.G2B, NoticeCategory.입찰, BusinessDivision.용역));
		list.add(new Source("bid-announce:공사", announce + "CnstwkPPSSrch", NoticeSource.G2B, NoticeCategory.입찰, BusinessDivision.공사));
		list.add(new Source("bid-announce:외자", announce + "FrgcptPPSSrch", NoticeSource.G2B, NoticeCategory.입찰, BusinessDivision.외자));
		// 발주계획
		list.add(new Source("bid-plan:물품", plan + "Thng", NoticeSource.G2B, NoticeCategory.계획, BusinessDivision.물품));
		list.add(new Source("bid-plan:용역", plan + "GnrlServc", NoticeSource.G2B, NoticeCategory.계획, BusinessDivision.용역));
		list.add(new Source("bid-plan:공사", plan + "Cnstwk", NoticeSource.G2B, NoticeCategory.계획, BusinessDivision.공사));
		list.add(new Source("bid-plan:외자", plan + "Frgcpt", NoticeSource.G2B, NoticeCategory.계획, BusinessDivision.외자));
		// 사전규격 — 외자 오퍼레이션이 없다(원본 API 에 없음).
		list.add(new Source("pre-spec:물품", preSpec + "Thng", NoticeSource.G2B, NoticeCategory.사전규격, BusinessDivision.물품));
		list.add(new Source("pre-spec:용역", preSpec + "Servc", NoticeSource.G2B, NoticeCategory.사전규격, BusinessDivision.용역));
		list.add(new Source("pre-spec:공사", preSpec + "Cnstwk", NoticeSource.G2B, NoticeCategory.사전규격, BusinessDivision.공사));
		// 누리장터(민간) 입찰공고 — 구분이 넷이다(기타를 빼면 민간 공고 상당수가 사라진다,
		// PrivateNoticeService 머리주석 참고). PPSSrch 가 아닌 기본 목록 오퍼레이션을 쓴다:
		// 기본 목록의 inqryDiv=1 이 '등록일시' 라서 정정·취소 재수집이 걸리기 때문이다
		// (PPSSrch 는 1=공고게시일시로 재정의돼 있다 — docs/nuri-openapi.md §4-(1)).
		list.add(new Source("nuri:bid-announce:물품", prvt + "Thng", NoticeSource.NURI, NoticeCategory.입찰, BusinessDivision.물품));
		list.add(new Source("nuri:bid-announce:용역", prvt + "Servc", NoticeSource.NURI, NoticeCategory.입찰, BusinessDivision.용역));
		list.add(new Source("nuri:bid-announce:공사", prvt + "Cnstwk", NoticeSource.NURI, NoticeCategory.입찰, BusinessDivision.공사));
		list.add(new Source("nuri:bid-announce:기타", prvt + "Etc", NoticeSource.NURI, NoticeCategory.입찰, BusinessDivision.기타));
		return List.copyOf(list);
	}

	/**
	 * D2B 오퍼레이션 표 — 팬아웃(D2bBidAnnounceService)과 같은 네 오퍼레이션이다.
	 * 국내(Dmstc)는 응답의 busiDivs(물품/용역)가 업종을 정하므로 division 을 null 로 두고,
	 * 시설(Fclty)은 busiDivs 가 비어 와도 '공사'다 — 오퍼레이션이 곧 업종이다.
	 */
	private static List<D2bSource> buildD2bSources() {
		return List.of(
				new D2bSource("d2b:bid-announce:국내경쟁", "getDmstcCmpetBidPblancList", null),
				new D2bSource("d2b:bid-announce:시설경쟁", "getFcltyCmpetBidPblancList", BusinessDivision.공사),
				new D2bSource("d2b:bid-announce:국내수의", "getDmstcOthbcVltrnNtatPlanList", null),
				new D2bSource("d2b:bid-announce:시설수의", "getFcltyOthbcVltrnNtatPlanList", BusinessDivision.공사));
	}

	/** 운영 화면이 목록을 보여줄 수 있도록 공개한다. */
	public List<String> sourceKeys() {
		List<String> keys = new ArrayList<>(sources.stream().map(Source::key).toList());
		keys.add("region");
		keys.add("nuri:region");
		keys.addAll(d2bSources.stream().map(D2bSource::key).toList());
		return List.copyOf(keys);
	}

	// ── 실행 ────────────────────────────────────────────────────────────────

	/**
	 * 전 출처 1회차 + 마감 전이. 수동 적재가 쓰는 경로다.
	 *
	 * <p><b>한 출처가 실패해도 나머지는 계속한다.</b> 나라장터는 오퍼레이션 단위로 점검·장애가
	 * 나므로, 하나가 죽었다고 전체 적재를 멈추면 멀쩡한 여덟 출처의 데이터까지 낡는다.
	 *
	 * @param backfillDays <b>0 이면 평시 증분</b>. 0보다 크면 워터마크를 무시하고 그만큼
	 *                     거슬러 올라가 다시 읽는다({@link #startOf} 참고)
	 */
	public IngestResult ingestAll(int backfillDays) {
		return ingest(sources, d2bSources, true, backfillDays);
	}

	/**
	 * 조달청 계열(나라장터·누리장터 + 참가가능지역)만.
	 *
	 * <p>D2B 와 갈라 놓은 이유는 <b>쿼터가 두 자릿수 배로 다르기 때문</b>이다. 조달청 계정은
	 * 오퍼레이션당 하루 수천 건이라 5분 주기(288회/일)가 여유롭지만, D2B 개발계정은 오퍼레이션당
	 * 하루 100건이라 같은 주기로 돌리면 3배 가까이 초과한다 — docs/d2b-openapi/INDEX.md.
	 * 한 주기로 묶으면 둘 중 빡빡한 쪽이 전체 신선도를 결정해 버린다.
	 */
	public IngestResult ingestProcurement(int backfillDays) {
		return ingest(sources, List.of(), true, backfillDays);
	}

	/** D2B 만. 지역 오퍼레이션은 D2B 에 없으므로 건너뛴다. */
	public IngestResult ingestD2b(int backfillDays) {
		return ingest(List.of(), d2bSources, false, backfillDays);
	}

	// ── 백필(과거 구간) ─────────────────────────────────────────────────────

	/**
	 * 과거 구간 백필을 연다 — 출처마다 {@code backfill:} 줄기를 세운다.
	 *
	 * <p>이 호출은 <b>지시만 남기고 즉시 돌아온다</b>. 실제 훑기는 백필 주기가 회차마다
	 * {@link #MAX_ROWS_PER_RUN} 만큼 갉아 먹으며 진행하고, 진행 상황은 상태 행의 워터마크에
	 * 남으므로 앱이 죽어도 이어서 한다. 5년치는 회차 수십 번짜리 작업이라 요청 하나로
	 * 끝낼 수 있는 종류가 아니다.
	 *
	 * <p><b>D2B 는 대상이 아니다.</b> 취향이 아니라 상류가 과거를 주지 않기 때문이다
	 * (2026-08-26 실측: {@code getDmstcCmpetBidPblancList} 에 {@code anmtDateBegin} 을
	 * 2026-05 이전으로 주면 {@code totalCount=0} 이고, 5년 범위를 통째로 줘도 최근 것
	 * 291건만 온다). 게다가 {@link D2bClient} 는 {@code pageNo=1} 고정이라 한 호출에
	 * 999건이 상한이다. 국방 공고의 과거는 <b>지금부터 쌓아 가는 수밖에 없다</b> —
	 * 여기에 D2B 를 끼워 넣으면 "돌았는데 0건"이라는 거짓 성공만 남는다.
	 *
	 * @param days 거슬러 올라갈 기간(일). 5년이면 1826
	 * @return 연 줄기 수
	 */
	public int openBackfill(int days) {
		int span = days > 0 ? days : historyDays;
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime from = now.minusDays(span);
		String note = "백필 지시: %s ~ %s (%d일)".formatted(from.format(G2B_DT), now.format(G2B_DT), span);

		int opened = 0;
		for (Source source : sources) {
			repository.openBackfill(BACKFILL_PREFIX + source.key(), from, now, note);
			opened++;
		}
		// 지역도 같은 구간을 덮어야 한다. 안 그러면 백필로 들어온 5년치 공고의 참가가능지역이
		// 전부 비어 '전국'으로 읽힌다 — 지역 필터가 조용히 틀린다.
		repository.openBackfill(BACKFILL_PREFIX + "region", from, now, note);
		repository.openBackfill(BACKFILL_PREFIX + "nuri:region", from, now, note);
		opened += 2;

		log.info("백필을 열었습니다 — {}개 줄기, {} ~ {}", opened, from.format(G2B_DT), now.format(G2B_DT));
		return opened;
	}

	/** 열려 있는 백필 줄기를 전부 닫는다. @return 닫힌 줄기 수 */
	public int cancelBackfill() {
		int closed = repository.cancelBackfills();
		log.info("백필을 중단했습니다 — {}개 줄기", closed);
		return closed;
	}

	/**
	 * 백필 한 회차 — 열려 있는 줄기를 하나씩 {@link #MAX_ROWS_PER_RUN} 만큼 전진시킨다.
	 *
	 * <p>머리 줄기와 <b>주기를 따로 두는 이유</b>는 신선도다. 한 주기에 묶으면 5년치를 갉는
	 * 동안 오늘 공고가 그만큼 늦고, 백필이 끝날 때까지 그 상태가 이어진다.
	 *
	 * <p>지역 줄기의 상한을 입찰공고 줄기의 진도로 <b>깎는다</b>(clamp). 지역 오퍼레이션은
	 * 행을 만들지 않고 있는 행만 갱신하므로, 아직 공고가 안 들어온 구간의 지역을 받아 오면
	 * 그대로 버려지고 워터마크만 지나가 버린다 — 그 구간의 지역은 영영 비게 된다.
	 * 두 줄기의 진도가 다른 것은 정상이다(지역이 입찰공고보다 행이 많아 더 느리게 전진한다).
	 */
	public IngestResult backfillOnce() {
		List<BidNoticeIndexRepository.BackfillState> jobs = repository.pendingBackfills();
		if (jobs.isEmpty()) {
			return new IngestResult(List.of(), 0, 0);
		}

		LocalDateTime now = LocalDateTime.now();
		Map<String, Source> byKey = new LinkedHashMap<>();
		for (Source source : sources) {
			byKey.put(source.key(), source);
		}
		Map<String, LocalDateTime> cursors = new LinkedHashMap<>();
		for (BidNoticeIndexRepository.BackfillState job : jobs) {
			cursors.put(job.source(), job.watermark());
		}

		List<SourceResult> results = new ArrayList<>();
		int totalIndexed = 0;
		// 지역은 늘 마지막이다 — 같은 회차 안에서 입찰공고가 먼저 들어와 있어야 붙을 대상이 있다.
		for (BidNoticeIndexRepository.BackfillState job : sortRegionsLast(jobs)) {
			String key = job.source().substring(BACKFILL_PREFIX.length());
			SourceResult result;
			if ("region".equals(key)) {
				result = ingestRegions(job.source(), regionUrl, NoticeSource.G2B, now,
						clampToAnnounces(job.targetTo(), cursors, BACKFILL_PREFIX + "bid-announce:"), 0);
			}
			else if ("nuri:region".equals(key)) {
				result = ingestRegions(job.source(), nuriRegionUrl, NoticeSource.NURI, now,
						clampToAnnounces(job.targetTo(), cursors, BACKFILL_PREFIX + "nuri:bid-announce:"), 0);
			}
			else {
				Source source = byKey.get(key);
				if (source == null) {
					// 출처 표가 바뀌어 짝을 잃은 줄기. 매 회차 실패로 시끄러워지지 않게 닫는다.
					log.warn("백필 줄기 {} 에 맞는 출처가 없습니다 — 건너뜁니다.", job.source());
					continue;
				}
				result = ingestOne(source, job.source(), now, job.targetTo(), 0);
			}
			results.add(result);
			totalIndexed += result.indexed();
		}
		return new IngestResult(List.copyOf(results), totalIndexed, 0);
	}

	/** 지역 줄기를 뒤로 보낸 순서. 나머지 순서는 그대로 둔다(워터마크 오름차순). */
	static List<BidNoticeIndexRepository.BackfillState> sortRegionsLast(
			List<BidNoticeIndexRepository.BackfillState> jobs) {
		List<BidNoticeIndexRepository.BackfillState> ordered = new ArrayList<>(jobs.size());
		List<BidNoticeIndexRepository.BackfillState> regions = new ArrayList<>();
		for (BidNoticeIndexRepository.BackfillState job : jobs) {
			if (job.source().endsWith("region")) {
				regions.add(job);
			}
			else {
				ordered.add(job);
			}
		}
		ordered.addAll(regions);
		return ordered;
	}

	/**
	 * 지역 줄기의 상한을 같은 계열 입찰공고 줄기들의 <b>가장 뒤처진 진도</b>로 깎는다.
	 *
	 * <p>짝이 되는 줄기가 하나도 없으면(=전부 끝났거나 애초에 안 열렸으면) 깎지 않는다.
	 */
	static LocalDateTime clampToAnnounces(LocalDateTime targetTo,
			Map<String, LocalDateTime> cursors, String announcePrefix) {
		LocalDateTime slowest = null;
		for (Map.Entry<String, LocalDateTime> entry : cursors.entrySet()) {
			if (!entry.getKey().startsWith(announcePrefix) || entry.getValue() == null) {
				continue;
			}
			if (slowest == null || entry.getValue().isBefore(slowest)) {
				slowest = entry.getValue();
			}
		}
		if (slowest == null || slowest.isAfter(targetTo)) {
			return targetTo;
		}
		return slowest;
	}

	/**
	 * 회차 하나.
	 *
	 * <p>마감 전이는 어느 묶음으로 불려도 돈다 — 출처와 무관한 시간 함수이고 인덱스를 타는
	 * UPDATE 한 번이라, 조건을 붙여 아끼는 것보다 그냥 도는 편이 싸고 덜 틀린다.
	 */
	private IngestResult ingest(List<Source> g2bSources, List<D2bSource> defenseSources,
			boolean includeRegions, int backfillDays) {
		LocalDateTime now = LocalDateTime.now();
		List<SourceResult> results = new ArrayList<>();
		int totalIndexed = 0;

		for (Source source : g2bSources) {
			SourceResult result = ingestOne(source, now, backfillDays);
			results.add(result);
			totalIndexed += result.indexed();
		}

		for (D2bSource source : defenseSources) {
			SourceResult result = ingestD2bOne(source, now, backfillDays);
			results.add(result);
			totalIndexed += result.indexed();
		}

		// 지역은 입찰공고가 색인된 뒤에 돌아야 붙을 대상이 있다 — 순서가 의미를 갖는다.
		// 나라장터·누리장터가 각각 참가가능지역 오퍼레이션을 따로 갖는다(D2B 는 없다).
		if (includeRegions) {
			results.add(ingestRegions("region", regionUrl, NoticeSource.G2B, now, backfillDays));
			results.add(ingestRegions("nuri:region", nuriRegionUrl, NoticeSource.NURI, now, backfillDays));
		}

		int swept = repository.sweepClosed();
		if (swept > 0) {
			log.info("마감 전이: {}건", swept);
		}
		return new IngestResult(List.copyOf(results), totalIndexed, swept);
	}

	/** 출처 하나 — 머리 줄기(상태 키 = 출처 키, 상한 = 지금). */
	SourceResult ingestOne(Source source, LocalDateTime now, int backfillDays) {
		return ingestOne(source, source.key(), now, now, backfillDays);
	}

	/**
	 * 출처 하나. 예외를 밖으로 내보내지 않고 결과에 담는다.
	 *
	 * <p><b>{@code now} 와 {@code to} 가 갈라져 있는 것이 핵심이다.</b> {@code to} 는 이번에
	 * 훑을 구간의 끝(백필 줄기면 백필을 지시한 시각)이고, {@code now} 는 <b>지금</b>이다.
	 * 매퍼가 마감일시를 {@code now} 와 견줘 입찰/마감을 가르므로, 5년 전 구간을 훑으면서
	 * {@code to} 를 넘기면 이미 끝난 공고가 전부 '입찰'로 들어온다.
	 *
	 * @param stateKey 워터마크를 읽고 쓸 상태 행. 머리 줄기는 출처 키, 과거 줄기는
	 *                 {@code backfill:} 접두사가 붙은 키다 — 둘이 서로의 진행을 밀지 않는다
	 */
	SourceResult ingestOne(Source source, String stateKey, LocalDateTime now, LocalDateTime to,
			int backfillDays) {
		LocalDateTime from = startOf(stateKey, now, backfillDays);
		if (!from.isBefore(to)) {
			// 훑을 구간이 없다(백필이 상한에 닿은 뒤 등). 워터마크를 건드리지 않고 물러난다.
			return new SourceResult(stateKey, 0, 0, true, "구간 없음");
		}
		try {
			FetchResult fr = fetchWindows(source.url(), from, to);
			List<BidNoticeRow> rows = new ArrayList<>(fr.items().size());
			for (Map<String, Object> item : fr.items()) {
				BidNoticeRow row = map(source, item, now);
				if (row != null) {
					rows.add(row);
				}
			}
			// 같은 배치 안에 같은 공고번호가 두 번 나오면(차수 정정) 마지막 것만 남긴다.
			// batchUpdate 안에서 같은 PK 를 두 번 건드리면 갱신 순서가 보장되지 않는다.
			List<BidNoticeRow> deduped = dedupeKeepLatest(rows);

			repository.upsertAll(deduped);
			// 상한에 걸렸으면 실제로 훑은 끝(coveredTo)까지만 전진시킨다.
			// to 로 전진하면 미훑은 창이 영영 사라진다(5년 백필에서 수만 건 누락).
			LocalDateTime watermark = fr.capped() ? fr.coveredTo() : to;
			String note = partialNote(fr,
					"%d건 조회 / %d건 색인".formatted(fr.items().size(), deduped.size()));
			repository.recordSuccess(stateKey, watermark, deduped.size(), note);
			log.info("색인 {} — 조회 {}건, 색인 {}건{}", stateKey, fr.items().size(), deduped.size(),
					fr.capped() ? " (부분: " + fr.coveredTo().format(G2B_DT) + "까지)" : "");
			return new SourceResult(stateKey, fr.items().size(), deduped.size(), true, "ok");
		}
		catch (RuntimeException ex) {
			// 워터마크는 전진시키지 않는다 — 다음 주기가 같은 구간을 다시 시도해야 한다.
			String reason = ex.getMessage() == null ? ex.toString() : ex.getMessage();
			repository.recordFailure(stateKey, reason);
			log.warn("색인 실패 {} — {}", stateKey, reason);
			return new SourceResult(stateKey, 0, 0, false, reason);
		}
	}

	/** 참가가능지역 보강 — 머리 줄기. */
	SourceResult ingestRegions(String key, String url, NoticeSource origin, LocalDateTime now,
			int backfillDays) {
		return ingestRegions(key, url, origin, now, now, backfillDays);
	}

	/**
	 * 참가가능지역 보강. 이미 색인된 공고의 {@code region} 만 채운다(소스별 오퍼레이션).
	 *
	 * <p>이 오퍼레이션은 <b>행을 만들지 않는다</b> — 없는 공고에 대한 지역은 0행에 걸려 조용히
	 * 버려진다. 그래서 이 줄기가 훑는 구간은 언제나 입찰공고 줄기가 <b>이미 덮은 구간 안</b>
	 * 이어야 한다. 백필에서 그 조건을 지키는 것은 호출부다({@link #backfillOnce} 의 상한 clamp).
	 */
	SourceResult ingestRegions(String key, String url, NoticeSource origin, LocalDateTime now,
			LocalDateTime to, int backfillDays) {
		LocalDateTime from = startOf(key, now, backfillDays);
		if (!from.isBefore(to)) {
			return new SourceResult(key, 0, 0, true, "구간 없음");
		}
		try {
			FetchResult fr = fetchWindows(url, from, to);
			Map<String, String> regions = BidNoticeMapper.foldRegions(fr.items());
			int updated = repository.updateRegions(regions, origin);
			LocalDateTime watermark = fr.capped() ? fr.coveredTo() : to;
			String note = partialNote(fr,
					"%d건 조회 / %d건 색인".formatted(fr.items().size(), regions.size()));
			repository.recordSuccess(key, watermark, updated, note);
			log.info("색인 지역({}) — 조회 {}건, 공고 {}개, 갱신 {}건{}", key, fr.items().size(),
					regions.size(), updated, fr.capped() ? " (부분)" : "");
			return new SourceResult(key, fr.items().size(), updated, true, "ok");
		}
		catch (RuntimeException ex) {
			String reason = ex.getMessage() == null ? ex.toString() : ex.getMessage();
			repository.recordFailure(key, reason);
			log.warn("색인 실패 {} — {}", key, reason);
			return new SourceResult(key, 0, 0, false, reason);
		}
	}

	/**
	 * D2B 출처 하나. 나라장터 경로와 달리 {@link D2bClient} 를 쓰고 날짜 창을 나누지 않는다 —
	 * D2B 는 일 단위 공고일({@code anmtDateBegin/End}) 필터라 31일 상한 문제가 없고,
	 * 공개수의 오퍼레이션은 날짜 파라미터 자체를 받지 않아 조회 후 걸러야 한다
	 * (D2bNormalizer.d2bParamsForOperation 주석 참고).
	 */
	SourceResult ingestD2bOne(D2bSource source, LocalDateTime now, int backfillDays) {
		LocalDateTime from = startOf(source.key(), now, backfillDays);
		String fromDate = from.format(DateTimeFormatter.BASIC_ISO_DATE);
		String toDate = now.format(DateTimeFormatter.BASIC_ISO_DATE);
		try {
			List<Map<String, Object>> raw = d2bClient.call(source.operation(),
					D2bNormalizer.d2bParamsForOperation(source.operation(), null, fromDate, toDate));

			List<BidNoticeRow> rows = new ArrayList<>(raw.size());
			for (Map<String, Object> item : raw) {
				Map<String, Object> normalized = D2bNormalizer.normalizeD2bItem(
						new LinkedHashMap<>(item), source.operation());
				// 공개수의는 서버측 날짜 필터가 없어 여기서 거른다(팬아웃 경로와 동일 규칙).
				if (!D2bNormalizer.isDateInRange(normalized.get("bidNtceDt"), fromDate, toDate)) {
					continue;
				}
				BidNoticeRow row = BidNoticeMapper.fromD2b(normalized, source.division(), now);
				if (row != null) {
					rows.add(row);
				}
			}
			List<BidNoticeRow> deduped = dedupeKeepLatest(rows);
			repository.upsertAll(deduped);
			repository.recordSuccess(source.key(), now, deduped.size(),
					"성공: %d건 조회 / %d건 색인".formatted(raw.size(), deduped.size()));
			log.info("색인 {} — 조회 {}건, 색인 {}건", source.key(), raw.size(), deduped.size());
			return new SourceResult(source.key(), raw.size(), deduped.size(), true, "ok");
		}
		catch (RuntimeException ex) {
			String reason = ex.getMessage() == null ? ex.toString() : ex.getMessage();
			repository.recordFailure(source.key(), reason);
			log.warn("색인 실패 {} — {}", source.key(), reason);
			return new SourceResult(source.key(), 0, 0, false, reason);
		}
	}

	// ── 내부 ────────────────────────────────────────────────────────────────

	private BidNoticeRow map(Source source, Map<String, Object> item, LocalDateTime now) {
		if (source.origin() == NoticeSource.NURI) {
			return BidNoticeMapper.fromPrivateNotice(item, source.division(), now);
		}
		return switch (source.category()) {
			case 입찰, 마감 -> BidNoticeMapper.fromBidAnnounce(item, source.division(), now);
			case 계획 -> BidNoticeMapper.fromProcurementPlan(item, source.division());
			case 사전규격 -> BidNoticeMapper.fromPreSpec(item, source.division());
		};
	}

	/**
	 * 이번 회차가 읽어 올 구간의 시작 시각.
	 *
	 * <p>{@code forcedBackfillDays} 가 <b>0 이면 평시 증분</b>이다(워터마크 − 겹침).
	 * 0보다 크면 운영자가 "그만큼 거슬러 올라가 다시 읽어라"고 지시한 것이고, 그때는
	 * 워터마크가 있어도 <b>구간을 넓히는 쪽으로만</b> 작동한다(둘 중 이른 시각).
	 *
	 * <p>이 구분이 필요한 이유: 스키마가 바뀌어 기존 행을 다시 채워야 할 때
	 * (예: 기관명 컬럼 추가) 워터마크가 있으면 새 공고만 들어와 옛 행은 영영 빈 채로 남는다.
	 * 반대로 주기 실행이 매번 며칠씩 되읽으면 증분의 의미가 사라지고 쿼터만 태우므로,
	 * 스케줄러는 항상 0을 넘긴다.
	 */
	private LocalDateTime startOf(String sourceKey, LocalDateTime now, int forcedBackfillDays) {
		LocalDateTime watermark = repository.readWatermark(sourceKey);
		LocalDateTime incremental = watermark == null ? null : watermark.minusMinutes(OVERLAP_MINUTES);
		LocalDateTime forced = forcedBackfillDays > 0 ? now.minusDays(forcedBackfillDays) : null;

		if (incremental == null) {
			// 첫 회차 — 지시가 없으면 설정된 기본 백필만큼.
			return forced != null ? forced : now.minusDays(firstRunBackfillDays);
		}
		if (forced == null) {
			return incremental;
		}
		return forced.isBefore(incremental) ? forced : incremental;
	}

	/**
	 * 기간을 API 가 삼킬 수 있는 창으로 잘라 전부 훑는 <b>순수 로직</b>.
	 *
	 * <p>{@link G2bDates#splitG2bDateRange} 가 31일 상한을 지킨다 — 넘기면 결과코드 07
	 * (입력범위값 초과)이 나고 그 구간이 통째로 빈다.
	 *
	 * <p>한 회차의 행 수 상한({@link #MAX_ROWS_PER_RUN})은 힙·일일 쿼터를 보호하는 <b>예산</b>이다.
	 * 걸리면 그 회차는 거기까지만 훑고, <b>워터마크가 실제로 훑은 끝({@code coveredTo})까지만
	 * 전진</b>해 다음 회차가 나머지를 따라잡는다. 구간 끝({@code to})으로 전진하면 미훑은 창이
	 * 영영 사라지므로(5년 백필에서 수만 건 누락) 절대 하면 안 된다.
	 *
	 * <p>창 하나가 상한을 넘기면 그 창을 {@link G2bDates#splitG2bRangeHalf} 로 쪼개 더 작은
	 * 조각부터 훑는다 — 워터마크가 항상 전진해 무한 재시도(라이블록)가 생기지 않는다.
	 *
	 * <p>클라이언트 의존이 없는 순수 함수라 테스트가 직접 뛴다. {@code fetchWindow} 는
	 * (창 시작, 창 끝) G2B 형식 문자열을 받아 그 창 전체의 행을 돌려주는 함수다.
	 */
	static FetchResult runWindows(BiFunction<String, String, List<Map<String, Object>>> fetchWindow,
			String url, LocalDateTime from, LocalDateTime to) {
		List<Map<String, Object>> all = new ArrayList<>();
		// 완전히 훑은 가장 먼 끝. 한 창도 못 훑으면 from(=진행 없음)이 된다 — 호출자는
		// 이 시각까지만 워터마크를 전진시킨다(역행하지 않는다).
		LocalDateTime coveredTo = from;
		// 상류 오류로 중간에 멈췄을 때의 사유. 예산 소진과 구별해 상태 행에 적는다.
		String stoppedBy = null;
		List<DateWindow> pending = new ArrayList<>(
				G2bDates.splitG2bDateRange(from.format(G2B_DT), to.format(G2B_DT)));
		int i = 0;
		while (i < pending.size()) {
			if (all.size() >= MAX_ROWS_PER_RUN) {
				break; // 회차 예산 소진 — 다음 회차가 남은 창부터 이어서
			}
			DateWindow w = pending.get(i);
			// 창은 **통째로** 받는다. 남은 예산에 맞춰 잘라 받으면 안 된다 — 워터마크는 창
			// 경계에서만 전진하므로, 잘려 온 뒷부분은 그대로 버려지고 다음 회차가 같은 구간을
			// 다시 내려받는다. 그래서 예산은 "창을 새로 열 것인가"만 정하고, 연 창은 끝까지 받는다.
			List<Map<String, Object>> got;
			try {
				got = fetchWindow.apply(w.from(), w.to());
			}
			catch (RuntimeException ex) {
				// 이 창에서 상류가 죽었다. **이미 훑은 창은 지키고** 여기서 물러난다.
				//
				// 지키지 않으면 진행이 영원히 안 되는 출처가 생긴다. 실패는 워터마크를 전진시키지
				// 않는다는 규칙 때문에, 61개 창 중 20번째에서 죽으면 다음 회차도 1번 창부터 다시
				// 시작해 같은 자리에서 또 죽는다. 실측으로 누리장터 '기타'가 그랬다 — 창마다 0건이라
				// 61개를 순식간에 두드리다 게이트웨이 429 를 맞고, 다섯 회차 연속으로 2021-08 에서
				// 한 발짝도 못 나갔다(그 줄기에 물린 누리 참가가능지역 백필까지 같이 멈췄다).
				//
				// 한 창도 못 훑었으면 지킬 것이 없으므로 그대로 올린다 — 인증 실패·쿼터 소진처럼
				// "이 출처가 통째로 안 되는" 경우가 그것이고, 그때는 워터마크를 세우는 것이 맞다.
				if (!coveredTo.isAfter(from)) {
					throw ex;
				}
				stoppedBy = ex.getMessage() == null ? ex.toString() : ex.getMessage();
				log.warn("적재가 {} 창에서 막혀 이번 회차는 {}까지만 훑었습니다 — 다음 회차가 이어서 훑습니다: {} ({})",
						w.from(), coveredTo.format(G2B_DT), url, stoppedBy);
				break;
			}
			if (got.size() < MAX_ROWS_PER_RUN) {
				all.addAll(got);
				coveredTo = G2bDates.parseG2bDt(w.to());
				i++;
				continue;
			}
			// 창 하나가 회차 예산 전체보다 크다(실측상 드물다). 쪼개서 더 작게 훑는다 —
			// 안 쪼개면 이 창은 어느 회차에서도 통째로 들어오지 못해 영원히 제자리다.
			List<DateWindow> halves = G2bDates.splitG2bRangeHalf(w.from(), w.to());
			if (halves == null) {
				// 단일 날짜조차 상한 초과. 받아온 만큼만 남기고 여기서 끝.
				all.addAll(got);
				log.error("적재: 단일 날짜 창이 상한({})을 초과해 전체 커버 불가 — {} ~ {} ({}). 다음 회차에서 재시도.",
						MAX_ROWS_PER_RUN, w.from(), w.to(), url);
				break;
			}
			// 이 창을 두 반으로 갈아끼운다(부분으로 받아온 got 은 접두어라 버린다 — 반으로
			// 다시 훑으면 겹침 없이 전체를 담는다). i 는 유지해 더 작은 반부터 다시 훑는다.
			pending.subList(i, i + 1).clear();
			pending.addAll(i, halves);
		}
		// **'남은 창이 있는가'로 판정한다.** coveredTo 를 to 와 견주면 안 된다 — 창 경계는
		// G2B 형식(yyyyMMddHHmm)이라 분 단위로 잘려 있고 to 는 초·나노까지 있는 '지금'이라,
		// 전부 훑은 회차도 언제나 '부분'이 된다. 그러면 워터마크가 매번 to 에 못 미쳐,
		// 상한이 있는 줄기(백필)는 마지막 창을 영원히 다시 훑는다.
		boolean capped = i < pending.size();
		if (capped && stoppedBy == null) {
			log.warn("적재 예산 {}건에 도달해 이번 회차는 {}까지만 훑었습니다 — 다음 회차가 이어서 훑습니다: {}",
					MAX_ROWS_PER_RUN, coveredTo.format(G2B_DT), url);
		}
		return new FetchResult(all, capped, coveredTo, stoppedBy);
	}

	/**
	 * {@link #runWindows} 를 실제 클라이언트에 연결해 부른다.
	 *
	 * <p>{@code inqryDiv=1} 은 '등록일시 기준'이다. 공고일시 기준(2)으로 두면 과거 공고의
	 * 정정이 조회되지 않아 색인이 낡는다 — 우리가 쫓는 것은 '변경'이지 '게시'가 아니다.
	 */
	private FetchResult fetchWindows(String url, LocalDateTime from, LocalDateTime to) {
		return runWindows((bgn, end) -> {
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("inqryDiv", 1);
			params.put("inqryBgnDt", bgn);
			params.put("inqryEndDt", end);
			return client.fetchAllPages(url, params, G2bApiClient.LOADER_PAGE_SIZE, MAX_ROWS_PER_RUN);
		}, url, from, to);
	}

	/**
	 * 상태 행에 적을 한 줄.
	 *
	 * <p>부분 성공을 <b>왜</b> 부분인지까지 적는다. 예산이 모자란 것과 상류가 막은 것은 같은
	 * '부분'이지만 운영자가 할 일이 다르다 — 앞은 기다리면 되고, 뒤는 사유를 봐야 한다.
	 * 가짜 {@code ok} 를 남기지 않는다는 규칙(V7 주석)의 연장이다.
	 */
	static String partialNote(FetchResult fr, String counts) {
		if (!fr.capped()) {
			return "성공: " + counts;
		}
		String covered = fr.coveredTo().format(G2B_DT);
		return fr.stoppedBy() == null
				? "성공(부분): %s — %s까지 커버, 다음 회차 이어서".formatted(counts, covered)
				: "성공(부분): %s — %s까지 커버, 상류가 막아 중단: %s".formatted(counts, covered, fr.stoppedBy());
	}

	/** 같은 (소스, 공고번호)가 여럿이면 차수가 가장 높은 것만 남긴다(순서 무관하게 결정적). */
	static List<BidNoticeRow> dedupeKeepLatest(List<BidNoticeRow> rows) {
		Map<String, BidNoticeRow> latest = new LinkedHashMap<>();
		for (BidNoticeRow row : rows) {
			// PK 와 같은 (id, source) 로 접는다 — id 만 쓰면 소스가 다른 동번호가 서로를 지운다.
			String key = row.sourceName() + "|" + row.id();
			BidNoticeRow existing = latest.get(key);
			if (existing == null || row.noticeOrder().compareTo(existing.noticeOrder()) >= 0) {
				latest.put(key, row);
			}
		}
		return List.copyOf(latest.values());
	}

	private static String trimTrailingSlash(String value) {
		String url = value == null ? "" : value;
		return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
	}
}
