package com.electerior.g2bmaster.market;

import com.electerior.g2bmaster.analysis.CollusionAnalysis;
import com.electerior.g2bmaster.common.G2bDates;
import com.electerior.g2bmaster.common.G2bDates.DateWindow;
import com.electerior.g2bmaster.common.MapLimit;
import com.electerior.g2bmaster.common.Numbers;
import com.electerior.g2bmaster.integration.g2b.G2bFetchService;
import com.electerior.g2bmaster.market.MarketIntelRequests.CompanyHistoryRequest;
import com.electerior.g2bmaster.market.MarketIntelRequests.OfficerSearchRequest;
import com.electerior.g2bmaster.notice.G2bEndpoints;
import com.electerior.g2bmaster.notice.NoticeFetchSupport;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 시장정보 조회 ({@code docs/api-contract.md} §2.C).
 *
 * <p>세 기능이 <b>같은 한 가지 자료</b>(개찰결과 = 참여업체별 투찰금액)를 다른 각도로 본다.
 * 업체 이력은 시간축으로, 담합 분석은 업체 짝으로, 담당자 조회는 발주 측으로.
 * 그래서 개찰결과 조회기 하나를 세 곳이 공유한다.
 *
 * <p><b>개찰결과 미공개는 오류가 아니다.</b> 개찰 전이거나 API가 그 공고를 지원하지 않으면
 * 빈 목록을 돌려준다 — 여기서 예외를 던지면 "아직 개찰 안 됨"이 화면에 500 으로 뜬다.
 */
@Service
public class MarketIntelService {

	private static final Logger log = LoggerFactory.getLogger(MarketIntelService.class);

	/**
	 * 개찰결과 페이지 크기.
	 *
	 * <p>예전 값(100)은 "참여업체가 100을 넘는 일은 사실상 없다"는 가정이었는데 사실이 아니다 —
	 * 적격심사 공사·용역은 실측으로 151·176·256개사가 나온다. 그 값으로 두면 경쟁 현황이
	 * 조용히 잘린 채 "총 100개사"로 보인다.
	 */
	private static final int OPENING_ROWS = 999;

	/** 한 공고에서 받아 올 참여업체 상한. 999를 넘는 공고는 드물지만 잘라 내지는 않는다. */
	private static final int MAX_OPENING_PARTICIPANTS = 3000;

	/** 업체 이력·담당자 조회 기본 구간(일). 검색 탭(7일)보다 넓다 — 이력은 추세가 목적이다. */
	private static final int DEFAULT_HISTORY_DAYS = 90;

	/** 날짜범위 개찰결과 조회 페이지 크기. */
	private static final int OPENING_RANGE_ROWS = 500;

	/** 날짜범위 개찰결과의 조회구분 — 3(개찰일시). 1은 입력일시, 2는 공고일시라 이력의 축이 아니다. */
	private static final int OPENING_RANGE_INQRY_DIV = 3;

	/** 낙찰정보 조회 페이지 크기. */
	private static final int RESULT_ROWS = 500;

	/** 폴백에서 개찰결과를 개별 조회할 공고 수 상한. 공고 하나당 1콜이라 그대로 호출량이다. */
	private static final int FALLBACK_BID_LIMIT = 30;

	private static final int WIN_FALLBACK_BID_LIMIT = 20;

	/** 담합 분석 처리 상한. 20건이면 개찰결과 20콜이고, 그 이상은 응답이 분 단위로 늘어진다. */
	public static final int MAX_COLLUSION_BIDS = 20;

	private final G2bEndpoints endpoints;
	private final G2bFetchService fetchService;
	private final NoticeFetchSupport support;
	private final BidOpeningResultRepository openingRepository;

	public MarketIntelService(G2bEndpoints endpoints, G2bFetchService fetchService,
			NoticeFetchSupport support, BidOpeningResultRepository openingRepository) {
		this.endpoints = endpoints;
		this.fetchService = fetchService;
		this.support = support;
		this.openingRepository = openingRepository;
	}

	// ── 개찰결과 ────────────────────────────────────────────────────────────

	/**
	 * 공고 하나의 참여업체별 투찰 내역.
	 *
	 * <p>양쪽 다 빈 목록을 돌려주지만 <b>로그는 갈라 둔다</b>. 예전에는 호출 실패까지
	 * "미공개"로 묻어 버려서, 오퍼레이션 URL이 통째로 죽어 있던 몇 달 동안 화면과 로그
	 * 어디에도 단서가 없었다. 미공개는 debug, 호출 실패는 warn 이다.
	 *
	 * <p>응답을 빈 목록으로 삼키는 것 자체는 그대로다 — 개찰 전 공고를 열어 본 사용자에게
	 * 500을 보여 줄 수는 없다.
	 */
	public List<Map<String, Object>> fetchOpeningResults(String bidNtceNo, String bidNtceSqNo, String type) {
		String no = bidNtceNo == null ? "" : bidNtceNo.trim();
		if (no.isEmpty()) {
			return List.of();
		}
		/*
		 * 저장분이 먼저다. 이 경로는 오래 요청마다 상류를 쳤고 완충은 프로세스 메모리
		 * 캐시뿐이라, 재기동 한 번에 통째로 날아가고 인스턴스 사이에 공유되지도 않았다.
		 * 무엇을 저장하고 언제 다시 묻는지는 BidOpeningResultRepository 참고.
		 */
		Optional<List<Map<String, Object>>> stored = openingRepository.find(no);
		if (stored.isPresent()) {
			return present(stored.get(), bidNtceSqNo, type);
		}

		// 차수(bidNtceOrd)는 일부러 안 싣는다 — narrowToOrd 주석 참고.
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("bidNtceNo", no);

		List<Map<String, Object>> rows;
		try {
			rows = fetchService.fetchPaged(endpoints.opengCompete(), params,
					OPENING_ROWS, MAX_OPENING_PARTICIPANTS);
		}
		catch (RuntimeException ex) {
			// 실패는 저장하지 않는다 — 빈 배열로 굳히면 상류가 살아난 뒤에도 계속 비어 보인다.
			log.warn("개찰결과 조회 실패 {} — {}", no, ex.getMessage());
			return List.of();
		}
		/*
		 * 빈 결과도 저장한다. "받아 봤는데 개찰 전이었다"는 사실이 곧 다음 요청을 아끼는
		 * 정보다. 저장소가 그것만 TTL 로 다시 묻는다.
		 */
		openingRepository.save(no, rows);
		if (rows.isEmpty()) {
			// resultCode 00 · totalCount 0 — 개찰 전이거나 유찰이라 참여업체가 없다(정상).
			log.debug("개찰결과 미공개 {} — 개찰 전이거나 유찰", no);
			return List.of();
		}

		return present(rows, bidNtceSqNo, type);
	}

	/**
	 * 저장분이 없으면 받아 와 저장만 한다 — 백필이 쓴다.
	 *
	 * <p>화면 경로와 <b>같은 함수를 타야</b> 저장 모양이 갈리지 않으므로 조회·저장 부분을
	 * 그대로 쓴다. 다른 점은 정규화를 하지 않는다는 것뿐이다 — 백필은 아무에게도 응답하지
	 * 않으므로 화면 계약으로 옮길 이유가 없다.
	 *
	 * @return 저장한 참여업체 수. 이미 저장돼 있으면 {@code -1}(건너뜀), 실패는 {@code -2}
	 */
	public int storeOpeningResults(String bidNtceNo) {
		String no = bidNtceNo == null ? "" : bidNtceNo.trim();
		if (no.isEmpty()) {
			return -2;
		}
		if (openingRepository.find(no).isPresent()) {
			return -1;
		}
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("bidNtceNo", no);
		List<Map<String, Object>> rows;
		try {
			rows = fetchService.fetchPaged(endpoints.opengCompete(), params,
					OPENING_ROWS, MAX_OPENING_PARTICIPANTS);
		}
		catch (RuntimeException ex) {
			log.warn("개찰결과 백필 실패 {} — {}", no, ex.getMessage());
			return -2;
		}
		openingRepository.save(no, rows);
		return rows.size();
	}

	/**
	 * 저장분이든 방금 받아 온 것이든 화면에 나가는 모양으로 바꾼다.
	 *
	 * <p>저장은 <b>정규화 전 원본</b>이라 이 단계를 거쳐야 화면 계약(bdrNm·rank·bidAmt·
	 * bidprcRt)이 된다. 두 경로가 같은 함수를 타야 "저장분만 필드가 다르다"가 생기지 않는다.
	 */
	private List<Map<String, Object>> present(List<Map<String, Object>> rows, String bidNtceSqNo,
			String type) {
		List<Map<String, Object>> scoped = narrowToOrd(rows, bidNtceSqNo);
		List<Map<String, Object>> out = new ArrayList<>(scoped.size());
		for (Map<String, Object> row : scoped) {
			out.add(normalizeParticipant(row, type));
		}
		return out;
	}

	/**
	 * 차수 좁히기는 <b>응답을 받은 뒤에</b> 한다.
	 *
	 * <p>{@code bidNtceOrd} 를 요청에 실으면 차수가 어긋나는 순간 상류가 0건을 준다(실측:
	 * 실제 차수가 002인 공고에 000을 보내면 {@code totalCount=0}). 화면은 차수를 모를 때
	 * 기본값 "000"을 보내오므로, 그대로 실어 보내면 재공고된 공고가 전부 빈 채로 나온다.
	 * 그래서 전 차수를 받아 두고, 요청한 차수가 실제로 있을 때만 그쪽으로 좁힌다.
	 */
	private static List<Map<String, Object>> narrowToOrd(List<Map<String, Object>> rows, String bidNtceSqNo) {
		String ord = bidNtceSqNo == null ? "" : bidNtceSqNo.trim();
		if (ord.isEmpty()) {
			return rows;
		}
		List<Map<String, Object>> matched = rows.stream()
				.filter(row -> ord.equals(str(row.get("bidNtceOrd"))))
				.toList();
		return matched.isEmpty() ? rows : matched;
	}

	/**
	 * 개찰완료 한 줄을 화면 계약({@code bdrNm}·{@code rank}·{@code bidAmt}·{@code bidprcRt}·
	 * {@code sucsfbidYn})으로 옮긴다.
	 *
	 * <p>상류 이름이 다르다: {@code prcbdrNm}·{@code opengRank}·{@code bidprcAmt}·
	 * {@code bidprcrt}(끝이 소문자 t다). 원본 키도 남겨 둔다 — 지우면 사업자번호·투찰일시처럼
	 * 지금은 안 쓰는 값이 나중에도 화면에 못 온다.
	 *
	 * <p><b>{@code sucsfbidYn} 은 상류에 없다.</b> 개찰순위 1위를 낙찰로 본다 — 표본 20건에서
	 * 낙찰정보의 {@code bidwinnrNm} 과 20/20 일치했다. {@code rmrk} 를 쓰지 않는 이유는 그것이
	 * 낙찰 표시가 아니라 투찰 상태이기 때문이다("정상" / "규격서평가부적격").
	 *
	 * <p>실격 업체는 순위·금액·투찰률이 모두 비어 온다. 채우지 않는다 — 0을 넣으면 투찰률
	 * 추세와 담합 매트릭스가 있지도 않은 0원 투찰을 사실로 읽는다.
	 */
	private static Map<String, Object> normalizeParticipant(Map<String, Object> row, String type) {
		Map<String, Object> out = new LinkedHashMap<>(row);
		String rank = firstNonBlank(row, "opengRank", "rank");
		out.put("bdrNm", firstNonBlank(row, "prcbdrNm", "bdrNm"));
		out.put("bdrBrn", firstNonBlank(row, "prcbdrBizno", "bdrBrn"));
		out.put("rank", rank);
		out.put("bidAmt", firstNonBlank(row, "bidprcAmt", "bidAmt"));
		out.put("bidprcRt", firstNonBlank(row, "bidprcrt", "bidprcRt"));
		boolean won = "1".equals(rank);
		out.put("sucsfbidYn", won ? "Y" : "N");
		out.put("_won", won);
		if (type != null && !type.isBlank()) {
			out.put("_type", type);
		}
		return out;
	}

	/**
	 * 날짜범위 개찰결과.
	 *
	 * <p>예전에는 {@code opengBgnDt}·{@code inqryBgnDt} 두 벌을 순서대로 찔러 봤다. 그럴 필요가
	 * 없다 — 이 오퍼레이션의 날짜 파라미터는 {@code inqryBgnDt}/{@code inqryEndDt} 한 벌이고
	 * 무엇을 기준으로 볼지는 {@code inqryDiv} 가 정한다(1 입력일시 · 2 공고일시 · 3 개찰일시).
	 * 업체 이력의 축은 개찰일시이므로 3이다.
	 *
	 * <p><b>여기서 오는 것은 참여업체 전수가 아니라 낙찰자 한 명</b>이다({@link #winnerOf}).
	 * 그게 이 오퍼레이션이 주는 전부이고, 전수가 필요하면 공고별로
	 * {@link #fetchOpeningResults} 를 불러야 한다.
	 */
	public List<Map<String, Object>> fetchOpeningByDateRange(String from, String to) {
		List<Map.Entry<String, String>> urls = new ArrayList<>(endpoints.opengResult().entrySet());
		return MapLimit.flatMap(urls, 3, entry -> {
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("inqryDiv", OPENING_RANGE_INQRY_DIV);
			params.put("inqryBgnDt", from);
			params.put("inqryEndDt", to);
			params.put("pageNo", 1);
			params.put("numOfRows", OPENING_RANGE_ROWS);
			try {
				var response = fetchService.callCached(entry.getValue(), params);
				if (response.totalCount() > response.items().size()) {
					// 한 페이지만 본다. 하루치도 구분당 1000건을 넘으므로 전량을 훑으면 호출량이
					// 쿼터를 태운다. 여기서 못 찾은 업체는 공고별 개별 조회로 물러서므로
					// (companyHistory 의 2단 폴백) 잘림이 곧 누락은 아니다.
					log.debug("개찰결과 날짜조회 {} — {}건 중 {}건만 본다(첫 페이지)",
							entry.getKey(), response.totalCount(), response.items().size());
				}
				List<Map<String, Object>> typed = new ArrayList<>(response.items().size());
				for (Map<String, Object> item : response.items()) {
					Map<String, Object> copy = winnerOf(item);
					copy.put("_type", entry.getKey());
					typed.add(copy);
				}
				return typed;
			}
			catch (RuntimeException ex) {
				log.warn("개찰결과 날짜조회 실패 {} — {}", entry.getKey(), ex.getMessage());
				return List.<Map<String, Object>>of();
			}
		});
	}

	/**
	 * 날짜범위 개찰결과 한 줄에서 낙찰자를 참여업체 모양으로 꺼낸다.
	 *
	 * <p>이 오퍼레이션은 공고 한 건이 한 줄이고, 업체 정보는 {@code opengCorpInfo} 하나에
	 * {@code 업체명^사업자번호^대표자명^투찰금액^투찰률} 로 접혀 있다.
	 *
	 * <p>유찰이면 이 칸이 통째로 비고, 협상에 의한 계약이면 금액·투찰률이 빠지며,
	 * 낙찰예정자가 여럿이면 업체명 자리에 "낙찰예정자 다수"가 온다. 셋 다 그대로 싣는다 —
	 * 없는 값을 지어내면 업체 이력의 투찰률 추세가 조용히 틀어진다.
	 */
	private static Map<String, Object> winnerOf(Map<String, Object> item) {
		Map<String, Object> out = new LinkedHashMap<>(item);
		String[] parts = str(item.get("opengCorpInfo")).split("\\^", -1);
		String name = part(parts, 0);
		out.put("bdrNm", name);
		out.put("bdrBrn", part(parts, 1));
		out.put("bidAmt", part(parts, 3));
		out.put("bidprcRt", part(parts, 4));
		out.put("rank", name.isEmpty() ? "" : "1");
		out.put("sucsfbidYn", name.isEmpty() ? "N" : "Y");
		out.put("_won", !name.isEmpty());
		return out;
	}

	private static String part(String[] parts, int index) {
		return index < parts.length ? parts[index].trim() : "";
	}

	// ── 업체 이력 ───────────────────────────────────────────────────────────

	/**
	 * 낙찰이력 + 참여이력 + 투찰률 추세.
	 *
	 * <p>세 단계 폴백이 있는 이유: 나라장터는 "이 업체가 참여한 입찰"을 직접 물어보는 API가
	 * 없다. 그래서 (1) 날짜범위 개찰결과를 훑어 이름으로 거르고, 그게 비면 (2) 낙찰정보에
	 * 나온 공고들의 개찰결과를 개별 조회하고, 그것도 비면 (3) 낙찰건만이라도 개찰결과를
	 * 붙인다. 폴백을 지우면 업체 화면이 자주 빈 채로 나온다.
	 */
	public Map<String, Object> companyHistory(CompanyHistoryRequest request) {
		String corp = request.corpNm() == null ? "" : request.corpNm().trim();
		String brn = request.brnNo() == null ? "" : request.brnNo().replaceAll("\\D", "");
		String corpLower = corp.toLowerCase(Locale.ROOT);

		DateWindow window = historyWindow(request.fromDate(), request.toDate());

		Map<String, Object> winBase = new LinkedHashMap<>();
		winBase.put("inqryBgnDt", window.from());
		winBase.put("inqryEndDt", window.to());
		if (!corp.isEmpty()) {
			// 서버측 필터 지원 여부가 오퍼레이션마다 다르다 — 보내되 믿지는 않는다.
			winBase.put("sucsfbidCorpNm", corp);
		}

		List<Map<String, Object>> allResults;
		try {
			allResults = support.fetchEnriched(endpoints.bidResult().values(), winBase, RESULT_ROWS);
		}
		catch (RuntimeException ex) {
			log.debug("낙찰정보 조회 실패: {}", ex.getMessage());
			allResults = List.of();
		}

		List<Map<String, Object>> wins = allResults.stream()
				.filter(item -> matchesCompany(corpLower, brn,
						values(item, "bidwinnrNm", "sucsfbidCorpNm", "corpNm"),
						values(item, "sucsfbidCorpBrn", "brnNo", "brno")))
				.toList();

		List<Map<String, Object>> participations = fetchOpeningByDateRange(window.from(), window.to()).stream()
				.filter(p -> matchesCompany(corpLower, brn, values(p, "bdrNm"), values(p, "bdrBrn", "brno")))
				.map(p -> mapParticipant(p, null))
				.collect(java.util.stream.Collectors.toCollection(ArrayList::new));

		if (participations.isEmpty() && !allResults.isEmpty()) {
			participations = participantsFromBids(
					allResults.stream().filter(b -> !str(b.get("bidNtceNo")).isEmpty())
							.limit(FALLBACK_BID_LIMIT).toList(),
					corpLower, brn, false);
		}
		if (participations.isEmpty() && !wins.isEmpty()) {
			participations = participantsFromBids(
					wins.stream().limit(WIN_FALLBACK_BID_LIMIT).toList(), corpLower, brn, true);
		}

		participations.sort(Comparator.comparing(
				(Map<String, Object> p) -> str(p.get("opengDt"))).reversed());

		List<Map<String, Object>> rates = new ArrayList<>();
		for (Map<String, Object> p : participations) {
			BigDecimal rate = Numbers.toNumber(p.get("bidprcRt"));
			if (rate == null || str(p.get("bidprcRt")).isEmpty()) {
				continue;
			}
			Map<String, Object> point = new LinkedHashMap<>();
			String opengDt = str(p.get("opengDt"));
			point.put("date", opengDt.length() >= 10 ? opengDt.substring(0, 10) : opengDt);
			point.put("rate", rate);
			point.put("won", p.get("_won"));
			point.put("name", p.get("bidNtceNm"));
			point.put("rank", p.get("rank"));
			rates.add(point);
		}
		rates.sort(Comparator.comparing(point -> str(point.get("date"))));

		long wonCount = participations.stream().filter(p -> Boolean.TRUE.equals(p.get("_won"))).count();
		int winCount = wonCount > 0 ? (int) wonCount : wins.size();

		Map<String, Object> stats = new LinkedHashMap<>();
		stats.put("winCount", winCount);
		stats.put("participationCount", participations.size());
		stats.put("winRate", participations.isEmpty() ? null
				: Math.round(winCount * 100.0 / participations.size()));
		stats.put("avgBidRate", averageRate(rates));
		stats.put("bidRateSeries", rates);

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("corpNm", corp);
		response.put("wins", wins);
		response.put("participations", participations);
		response.put("stats", stats);
		response.put("from", window.from());
		response.put("to", window.to());
		return response;
	}

	// ── 담당자 조회 ─────────────────────────────────────────────────────────

	/**
	 * 발주기관 담당자별 공고 묶음.
	 *
	 * <p>담당자 정보는 별도 API가 없고 <b>공고 항목에 실려 있다</b>. 그래서 기간 내 공고를
	 * 훑어 이름·전화·이메일 조합으로 묶는 수밖에 없다. 셋 중 하나라도 있어야 담당자로 센다 —
	 * 셋 다 비면 "담당자 미기재 공고"들이 한 덩어리로 묶여 버린다.
	 */
	public Map<String, Object> officerSearch(OfficerSearchRequest request) {
		String insttNm = request.insttNm().trim();
		DateWindow window = historyWindow(request.fromDate(), request.toDate());

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("dminsttNm", insttNm);
		params.put("inqryBgnDt", window.from());
		params.put("inqryEndDt", window.to());

		List<Map<String, Object>> items = support.fetchEnriched(
				endpoints.bidAnnounce().values(), params, 300);

		// dminsttNm 파라미터가 무시되는 오퍼레이션이 있어 로컬에서 한 번 더 거른다.
		List<Map<String, Object>> pool = items.stream()
				.filter(item -> NoticeFetchSupport.institutionMatches(item, insttNm))
				.toList();

		Map<String, Map<String, Object>> officers = new LinkedHashMap<>();
		for (Map<String, Object> item : pool) {
			String name = str(item.get("ntceInsttOfclNm")).trim();
			String tel = str(item.get("ntceInsttOfclTelNo")).trim();
			String email = str(item.get("ntceInsttOfclEmailAdrs")).trim();
			if (name.isEmpty() && tel.isEmpty() && email.isEmpty()) {
				continue;
			}
			String key = name + "|" + tel + "|" + email;
			Map<String, Object> officer = officers.computeIfAbsent(key, k -> {
				Map<String, Object> created = new LinkedHashMap<>();
				created.put("name", name);
				created.put("tel", tel);
				created.put("email", email);
				created.put("insttNm", str(item.get("ntceInsttNm")));
				created.put("dminsttNm", blankTo(str(item.get("dminsttNm")), insttNm));
				created.put("bids", new ArrayList<Map<String, Object>>());
				return created;
			});
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> bids = (List<Map<String, Object>>) officer.get("bids");
			Map<String, Object> bid = new LinkedHashMap<>();
			bid.put("bidNtceNo", str(item.get("bidNtceNo")));
			bid.put("bidNtceNm", str(item.get("bidNtceNm")));
			bid.put("bidNtceDt", firstNonBlank(item, "bidNtceDt", "rgstDt"));
			bid.put("bidClseDt", str(item.get("bidClseDt")));
			bid.put("asignBdgtAmt", str(item.get("asignBdgtAmt")));
			bid.put("dminsttNm", str(item.get("dminsttNm")));
			bid.put("type", blankTo(str(item.get("_type")), "물품"));
			bids.add(bid);
		}

		List<Map<String, Object>> sorted = officers.values().stream()
				.sorted(Comparator.comparingInt((Map<String, Object> o) -> ((List<?>) o.get("bids")).size())
						.reversed())
				.toList();

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("insttNm", insttNm);
		response.put("from", window.from());
		response.put("to", window.to());
		response.put("officers", sorted);
		response.put("totalBids", pool.size());
		return response;
	}

	// ── 담합 분석 ───────────────────────────────────────────────────────────

	/** 공고들에 개찰결과를 붙여 담합 매트릭스를 만든다. 최대 {@value #MAX_COLLUSION_BIDS} 건. */
	public Map<String, Object> collusionAnalysis(List<Map<String, Object>> bids) {
		List<Map<String, Object>> slice = bids.stream().limit(MAX_COLLUSION_BIDS).toList();

		List<Map<String, Object>> enriched = MapLimit.map(slice, 4, bid -> {
			Map<String, Object> copy = new LinkedHashMap<>(bid);
			copy.put("participants", fetchOpeningResults(
					str(bid.get("bidNtceNo")),
					str(bid.get("bidNtceSqNo")),
					blankTo(str(bid.get("_type")), "물품")));
			return copy;
		});

		CollusionAnalysis.CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(enriched);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("bids", enriched);
		response.put("pairs", matrix.pairs());
		response.put("companies", matrix.companies());
		return response;
	}

	// ── 내부 ────────────────────────────────────────────────────────────────

	/** 개찰결과를 공고 단위로 개별 조회하는 폴백. */
	private List<Map<String, Object>> participantsFromBids(List<Map<String, Object>> bids,
			String corpLower, String brn, boolean fallbackToFirst) {
		List<Map<String, Object>> found = MapLimit.map(bids, 4, bid -> {
			String type = normalizeType(str(bid.get("_type")));
			List<Map<String, Object>> participants = fetchOpeningResults(
					str(bid.get("bidNtceNo")),
					firstNonBlank(bid, "bidNtceSqNo", "bidNtceOrd"),
					type);
			Map<String, Object> match = participants.stream()
					.filter(p -> matchesCompany(corpLower, brn, values(p, "bdrNm"), values(p, "bdrBrn")))
					.findFirst()
					.orElse(fallbackToFirst && !participants.isEmpty() ? participants.get(0) : null);
			if (match == null) {
				return null;
			}
			Map<String, Object> withTotal = new LinkedHashMap<>(match);
			withTotal.put("_totalPts", participants.size());
			return mapParticipant(withTotal, bid);
		});
		List<Map<String, Object>> out = new ArrayList<>();
		for (Map<String, Object> item : found) {
			if (item != null) {
				out.add(item);
			}
		}
		return out;
	}

	/**
	 * 참여 내역 한 건을 화면 계약 모양으로 맞춘다.
	 *
	 * <p>{@code overrideBid} 가 있으면 공고 정보를 그쪽에서 가져온다 — 개찰결과 응답에는
	 * 공고명·기관이 없는 경우가 많아, 개별 조회 폴백에서는 원 공고를 겹쳐야 한다.
	 */
	private static Map<String, Object> mapParticipant(Map<String, Object> p, Map<String, Object> overrideBid) {
		Map<String, Object> bid = overrideBid == null ? Map.of() : overrideBid;
		Map<String, Object> out = new LinkedHashMap<>();
		out.put("bidNtceNo", pick(bid, "bidNtceNo", p, "bidNtceNo"));
		out.put("bidNtceNm", pick(bid, "bidNtceNm", p, "bidNtceNm"));
		out.put("ntceInsttNm", firstNonBlank(bid, "dminsttNm").isEmpty()
				? firstNonBlank(p, "ntceInsttNm", "dminsttNm") : str(bid.get("dminsttNm")));
		out.put("dminsttNm", firstNonBlank(bid, "dminsttNm").isEmpty()
				? firstNonBlank(p, "dminsttNm", "ntceInsttNm") : str(bid.get("dminsttNm")));
		out.put("opengDt", firstNonBlank(bid, "rlOpengDt", "opengDt").isEmpty()
				? firstNonBlank(p, "opengDt", "rlOpengDt")
				: firstNonBlank(bid, "rlOpengDt", "opengDt"));
		out.put("presmptPrce", firstNonBlank(bid, "presmptPrce").isEmpty()
				? firstNonBlank(p, "presmptPrce", "asignBdgtAmt") : str(bid.get("presmptPrce")));
		out.put("_type", firstNonBlank(bid, "_type").isEmpty()
				? firstNonBlank(p, "_type", "bsnsDivNm") : str(bid.get("_type")));
		out.put("bdrNm", str(p.get("bdrNm")));
		out.put("rank", str(p.get("rank")));
		out.put("bidAmt", str(p.get("bidAmt")));
		out.put("bidprcRt", str(p.get("bidprcRt")));
		out.put("sucsfbidYn", blankTo(str(p.get("sucsfbidYn")), "N"));
		out.put("_won", "Y".equals(str(p.get("sucsfbidYn"))));
		if (p.get("_totalPts") != null) {
			out.put("totalParticipants", p.get("_totalPts"));
		}
		return out;
	}

	/** 사업자번호가 있으면 그쪽이 우선이다 — 상호는 겹치지만 사업자번호는 유일하다. */
	private static boolean matchesCompany(String corpLower, String brn, List<String> names, List<String> brns) {
		if (!brn.isEmpty() && brns.stream().anyMatch(v -> v.replaceAll("\\D", "").equals(brn))) {
			return true;
		}
		return !corpLower.isEmpty()
				&& names.stream().anyMatch(v -> v.toLowerCase(Locale.ROOT).contains(corpLower));
	}

	private static BigDecimal averageRate(List<Map<String, Object>> rates) {
		if (rates.isEmpty()) {
			return null;
		}
		BigDecimal sum = BigDecimal.ZERO;
		for (Map<String, Object> point : rates) {
			sum = sum.add((BigDecimal) point.get("rate"));
		}
		return sum.divide(BigDecimal.valueOf(rates.size()), 3, RoundingMode.HALF_UP);
	}

	private DateWindow historyWindow(String fromDate, String toDate) {
		DateWindow def = G2bDates.defaultDates(DEFAULT_HISTORY_DAYS);
		String from = G2bDates.toG2bDt(fromDate, false);
		String to = G2bDates.toG2bDt(toDate, true);
		return new DateWindow(from == null ? def.from() : from, to == null ? def.to() : to);
	}

	private static String normalizeType(String raw) {
		if (raw.contains("용역")) {
			return "용역";
		}
		return raw.contains("공사") ? "공사" : "물품";
	}

	private static String pick(Map<String, Object> primary, String primaryKey,
			Map<String, Object> fallback, String fallbackKey) {
		String value = str(primary.get(primaryKey));
		return value.isEmpty() ? str(fallback.get(fallbackKey)) : value;
	}

	private static List<String> values(Map<String, Object> item, String... keys) {
		Set<String> out = new LinkedHashSet<>();
		for (String key : keys) {
			String value = str(item.get(key));
			if (!value.isEmpty()) {
				out.add(value);
			}
		}
		return List.copyOf(out);
	}

	private static String firstNonBlank(Map<String, Object> item, String... keys) {
		for (String key : keys) {
			String value = str(item.get(key));
			if (!value.isBlank()) {
				return value;
			}
		}
		return "";
	}

	private static String blankTo(String value, String fallback) {
		return value == null || value.isBlank() ? fallback : value;
	}

	private static String str(Object value) {
		return value == null ? "" : String.valueOf(value);
	}
}
