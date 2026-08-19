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
	 * 한 출처·한 회차가 받아올 행 수 상한.
	 *
	 * <p>백필을 크게 잡으면 한 오퍼레이션이 수십만 건을 들고 오다가 힙과 일일 쿼터를 동시에
	 * 태운다. 상한에 걸리면 그 회차는 거기까지만 색인하고, 워터마크가 조금씩 전진하며
	 * 다음 회차들이 나머지를 따라잡는다.
	 */
	static final int MAX_ROWS_PER_RUN = 20_000;

	private static final DateTimeFormatter G2B_DT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

	/** 적재 한 회차의 결과. 운영 화면과 로그가 읽는다. */
	public record SourceResult(String source, int fetched, int indexed, boolean ok, String message) {}

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

	/** 출처 하나. 예외를 밖으로 내보내지 않고 결과에 담는다. */
	SourceResult ingestOne(Source source, LocalDateTime now, int backfillDays) {
		LocalDateTime from = startOf(source.key(), now, backfillDays);
		try {
			List<Map<String, Object>> items = fetchWindows(source.url(), from, now);
			List<BidNoticeRow> rows = new ArrayList<>(items.size());
			for (Map<String, Object> item : items) {
				BidNoticeRow row = map(source, item, now);
				if (row != null) {
					rows.add(row);
				}
			}
			// 같은 배치 안에 같은 공고번호가 두 번 나오면(차수 정정) 마지막 것만 남긴다.
			// batchUpdate 안에서 같은 PK 를 두 번 건드리면 갱신 순서가 보장되지 않는다.
			List<BidNoticeRow> deduped = dedupeKeepLatest(rows);

			repository.upsertAll(deduped);
			repository.recordSuccess(source.key(), now, deduped.size(),
					"성공: %d건 조회 / %d건 색인".formatted(items.size(), deduped.size()));
			log.info("색인 {} — 조회 {}건, 색인 {}건", source.key(), items.size(), deduped.size());
			return new SourceResult(source.key(), items.size(), deduped.size(), true, "ok");
		}
		catch (RuntimeException ex) {
			// 워터마크는 전진시키지 않는다 — 다음 주기가 같은 구간을 다시 시도해야 한다.
			String reason = ex.getMessage() == null ? ex.toString() : ex.getMessage();
			repository.recordFailure(source.key(), reason);
			log.warn("색인 실패 {} — {}", source.key(), reason);
			return new SourceResult(source.key(), 0, 0, false, reason);
		}
	}

	/** 참가가능지역 보강. 이미 색인된 공고의 {@code region} 만 채운다(소스별 오퍼레이션). */
	SourceResult ingestRegions(String key, String url, NoticeSource origin, LocalDateTime now,
			int backfillDays) {
		LocalDateTime from = startOf(key, now, backfillDays);
		try {
			List<Map<String, Object>> items = fetchWindows(url, from, now);
			Map<String, String> regions = BidNoticeMapper.foldRegions(items);
			int updated = repository.updateRegions(regions, origin);
			repository.recordSuccess(key, now, updated,
					"성공: %d건 조회 / %d개 공고 / %d건 갱신".formatted(items.size(), regions.size(), updated));
			log.info("색인 지역({}) — 조회 {}건, 공고 {}개, 갱신 {}건", key, items.size(), regions.size(), updated);
			return new SourceResult(key, items.size(), updated, true, "ok");
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
	 * 기간을 API 가 삼킬 수 있는 창으로 잘라 전부 훑는다.
	 *
	 * <p>{@link G2bDates#splitG2bDateRange} 가 31일 상한을 지킨다 — 넘기면 결과코드 07
	 * (입력범위값 초과)이 나고 그 구간이 통째로 빈다.
	 */
	private List<Map<String, Object>> fetchWindows(String url, LocalDateTime from, LocalDateTime to) {
		List<Map<String, Object>> all = new ArrayList<>();
		for (DateWindow window : G2bDates.splitG2bDateRange(from.format(G2B_DT), to.format(G2B_DT))) {
			Map<String, Object> params = new LinkedHashMap<>();
			// inqryDiv=1 은 '등록일시 기준'이다. 공고일시 기준(2)으로 두면 과거 공고의 정정이
			// 조회되지 않아 색인이 낡는다 — 우리가 쫓는 것은 '변경'이지 '게시'가 아니다.
			params.put("inqryDiv", 1);
			params.put("inqryBgnDt", window.from());
			params.put("inqryEndDt", window.to());

			int remaining = MAX_ROWS_PER_RUN - all.size();
			if (remaining <= 0) {
				log.warn("적재 상한 {}건에 도달해 이번 회차는 여기까지만 색인합니다: {}", MAX_ROWS_PER_RUN, url);
				break;
			}
			all.addAll(client.fetchAllPages(url, params, G2bApiClient.LOADER_PAGE_SIZE, remaining));
		}
		return all;
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
