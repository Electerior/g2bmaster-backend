package com.electerior.g2bmaster.analysis;

import com.electerior.g2bmaster.common.Numbers;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 담합 정황 매트릭스 ({@code lib/collusion-analysis.js} 이식).
 *
 * <p>공고별 개찰결과(참여업체·순위·투찰률)를 모아 두 가지를 본다.
 * <ol>
 *   <li><b>짝(pair)</b> — 같은 두 업체가 1·2위를 반복하는가, 그리고 승수가 반반으로
 *       갈리는가. 번갈아 이기는 패턴은 들러리 입찰의 전형이다.</li>
 *   <li><b>업체(company)</b> — 투찰률이 단조 증가하는가. 낙찰가를 계단식으로 올리는
 *       움직임을 잡는다.</li>
 * </ol>
 *
 * <p>참여업체 한 줄의 이름은 두 벌이다 — 화면 계약({@code bdrNm}·{@code rank}·
 * {@code bidprcRt})과 나라장터 개찰완료 원본({@code prcbdrNm}·{@code opengRank}·
 * {@code bidprcrt}). 둘 다 읽는다({@code field}).
 *
 * <p><b>여기서 나오는 점수는 고발 근거가 아니라 "들여다볼 순서"다.</b> 표본이 2~3건이면
 * 우연으로 만들어지는 패턴이므로, {@code suspicionScore} 에 건수를 곱해 둔 것도 그 이유다.
 */
public final class CollusionAnalysis {

	private CollusionAnalysis() {
	}

	/** 짝 하나에 딸린 개별 사례. */
	public record PairCase(
			Object bidNtceNo,
			Object bidNtceNm,
			Object winner,
			Object runnerUp,
			Object winBidprcRt,
			Object runBidprcRt,
			Object opengDate) {}

	/** 업체 하나에 딸린 개별 사례. {@code role} 은 {@code 낙찰} 또는 {@code 2위}. */
	public record CompanyCase(
			String role,
			Object bidNtceNo,
			Object bidNtceNm,
			Object bidAmt,
			Object bidprcRt,
			Object opengDate) {}

	/**
	 * 1·2위를 함께한 두 업체.
	 *
	 * @param alterScore     번갈아 이긴 정도(0~100). 승수가 반반이면 100
	 * @param suspicionScore {@code 건수 × 번갈아 정도} — 건수가 적으면 자동으로 낮아진다
	 */
	public record Pair(
			String a,
			String b,
			int total,
			int aWins,
			int bWins,
			List<PairCase> cases,
			int alterScore,
			BigDecimal suspicionScore) {}

	/** 업체 요약. {@code avgRate} 는 표본이 없으면 null(0 아님). */
	public record Company(
			String name,
			int wins,
			int runnerUp,
			int appearances,
			BigDecimal avgRate,
			List<BigDecimal> rateHistory,
			boolean isMonotonicallyIncreasing,
			List<CompanyCase> cases) {}

	/** 최종 결과. */
	public record CollusionMatrix(List<Pair> pairs, List<Company> companies) {}

	/**
	 * 개찰결과가 붙은 공고 목록에서 매트릭스를 만든다.
	 *
	 * <p>각 항목은 {@code participants} 키에 참여업체 목록을 갖고 있어야 한다.
	 * 참여업체가 없거나 낙찰 업체명이 비면 그 공고는 통째로 건너뛴다 — 이름 없는 참여자를
	 * 짝으로 묶으면 서로 다른 업체가 한 덩어리가 된다.
	 *
	 * <p><b>낙찰자는 개찰 1순위가 아니라 {@code sucsfbidYn} 이 정한다.</b> 예전에는
	 * {@code participants.get(0)} 을 승자로 봤는데, 실측 220건 중 7건(3.2%)에서 1순위가
	 * 낙찰자가 아니었다 — 더 낮게 쓴 1순위가 적격심사에서 떨어지거나 포기한 경우다
	 * (낙찰자가 2순위 5건·3순위 1건·6순위 1건). 그대로 두면 "누가 이겼나" 통계와 들러리 페어가
	 * 엉뚱한 업체를 승자로 세는데, 화면 배지와 달리 이쪽은 눈에 띄지도 않는다.
	 *
	 * <p>플래그가 붙은 업체가 없으면 그 공고는 건너뛴다. 낙찰정보가 아직 없다는 뜻이고
	 * (개찰은 끝났지만 낙찰자 확정 전), 확정되지 않은 승패를 통계에 넣을 이유가 없다.
	 * 2위는 종전대로 개찰 순위로 본다 — 낙찰자 바로 다음 순위가 곧 경쟁 상대다.
	 */
	/**
	 * 낙찰 업체.
	 *
	 * <p>정규화를 거친 줄은 {@code sucsfbidYn} 을 <b>반드시</b> 갖는다("Y" 또는 "N").
	 * 그러니 키가 하나라도 있으면 판정은 끝난 것이고, 아무도 "Y" 가 아니면 <b>낙찰이 아직
	 * 확정되지 않았다</b>는 뜻이라 그 공고는 통계에서 뺀다 — 개찰은 끝났는데 낙찰자 확정 전인
	 * 구간이 며칠씩 있고, 그때 1순위를 승자로 세면 아직 일어나지 않은 일이 통계에 들어간다.
	 *
	 * <p>키가 <b>아예 없으면</b> 정규화를 거치지 않고 흘러든 원본 줄이다. 그때는 개찰 순위로
	 * 물러선다 — 여기서 빈손으로 돌아가면 "짝 0건"이 되는데, 그것은 아무도 이상하다고 느끼지
	 * 않는 실패다(같은 이유로 존재하는 시험이 있다).
	 */
	private static Map<String, Object> awardedOf(List<Map<String, Object>> participants) {
		boolean normalized = false;
		for (Map<String, Object> p : participants) {
			if (p.containsKey("sucsfbidYn")) {
				normalized = true;
				if ("Y".equalsIgnoreCase(String.valueOf(p.get("sucsfbidYn")))) {
					return p;
				}
			}
		}
		return normalized ? null : participants.get(0);
	}

	/** 낙찰자를 뺀 개찰 순위 첫 업체. 낙찰자가 2순위면 1순위가 여기 온다. */
	private static Map<String, Object> runnerUpOf(List<Map<String, Object>> participants,
			Map<String, Object> winner) {
		for (Map<String, Object> p : participants) {
			if (p != winner) {
				return p;
			}
		}
		return null;
	}

	public static CollusionMatrix buildCollusionMatrix(List<Map<String, Object>> bids) {
		Map<String, PairRecord> pairs = new LinkedHashMap<>();
		Map<String, CompanyRecord> companies = new LinkedHashMap<>();

		for (Map<String, Object> bid : bids == null ? List.<Map<String, Object>>of() : bids) {
			List<Map<String, Object>> participants = sortedParticipants(bid);
			if (participants.isEmpty()) {
				continue;
			}
			Map<String, Object> winner = awardedOf(participants);
			if (winner == null || name(winner).isEmpty()) {
				continue;
			}
			recordCompany(companies, name(winner), "낙찰", bid, winner);

			Map<String, Object> runnerUp = runnerUpOf(participants, winner);
			if (runnerUp == null || name(runnerUp).isEmpty()) {
				continue;
			}
			recordCompany(companies, name(runnerUp), "2위", bid, runnerUp);
			recordPair(pairs, bid, winner, runnerUp);
		}

		List<Pair> pairList = new ArrayList<>(pairs.values().stream().map(CollusionAnalysis::summarizePair).toList());
		pairList.sort(Comparator.comparing(Pair::suspicionScore).reversed()
				.thenComparing(Comparator.comparingInt(Pair::total).reversed()));

		List<Company> companyList = new ArrayList<>(companies.entrySet().stream()
				.map(e -> summarizeCompany(e.getKey(), e.getValue())).toList());
		companyList.sort(Comparator.comparingInt(Company::wins).reversed());

		return new CollusionMatrix(List.copyOf(pairList), List.copyOf(companyList));
	}

	// ── 내부 ────────────────────────────────────────────────────────────────

	@SuppressWarnings("unchecked")
	private static List<Map<String, Object>> sortedParticipants(Map<String, Object> bid) {
		Object raw = bid == null ? null : bid.get("participants");
		if (!(raw instanceof List<?> list)) {
			return List.of();
		}
		List<Map<String, Object>> participants = new ArrayList<>();
		for (Object element : list) {
			if (element instanceof Map<?, ?> map) {
				participants.add((Map<String, Object>) map);
			}
		}
		// 순위가 비었거나 숫자가 아니면 99 — 정렬 끝으로 밀어 1·2위 판정에서 빠지게 한다.
		// 실격 업체(규격서평가부적격 등)가 순위 없이 오므로 이 자리가 실제로 쓰인다.
		participants.sort(Comparator.comparingInt(p -> rank(field(p, "rank", "opengRank"))));
		return participants;
	}

	/**
	 * 참여업체 한 줄에서 값을 꺼낸다. 앞이 화면 계약 이름, 뒤가 나라장터 개찰완료 원본 이름이다.
	 *
	 * <p>정상 경로에서는 {@code MarketIntelService} 가 이미 앞 이름으로 맞춰 준다. 뒤 이름까지
	 * 보는 것은 <b>정규화를 거치지 않은 줄이 흘러들어도 매트릭스가 조용히 비지 않게</b> 하기
	 * 위한 것이다 — 이 분석은 값이 없으면 오류가 아니라 "짝 0건"으로 보이고, 그건 아무도
	 * 이상하다고 느끼지 않는 실패다.
	 */
	private static Object field(Map<String, Object> participant, String contract, String upstream) {
		Object value = participant.get(contract);
		if (value != null && !String.valueOf(value).isBlank()) {
			return value;
		}
		Object raw = participant.get(upstream);
		return raw == null || String.valueOf(raw).isBlank() ? null : raw;
	}

	private static String name(Map<String, Object> participant) {
		return str(field(participant, "bdrNm", "prcbdrNm"));
	}

	private static int rank(Object value) {
		BigDecimal parsed = Numbers.toNumber(value);
		return parsed == null ? 99 : parsed.intValue();
	}

	private static void recordCompany(Map<String, CompanyRecord> records, String name, String role,
			Map<String, Object> bid, Map<String, Object> participant) {
		CompanyRecord record = records.computeIfAbsent(name, k -> new CompanyRecord());
		if ("낙찰".equals(role)) {
			record.wins++;
		}
		else {
			record.runnerUp++;
		}
		record.appearances++;
		Object rateRaw = field(participant, "bidprcRt", "bidprcrt");
		BigDecimal rate = Numbers.toNumber(rateRaw);
		if (rate != null) {
			record.bidRates.add(rate);
		}
		record.cases.add(new CompanyCase(role,
				bid.get("bidNtceNo"), bid.get("bidNtceNm"),
				field(participant, "bidAmt", "bidprcAmt"), rateRaw, bid.get("opengDate")));
	}

	private static void recordPair(Map<String, PairRecord> records, Map<String, Object> bid,
			Map<String, Object> winner, Map<String, Object> runnerUp) {
		String winnerName = name(winner);
		String runnerName = name(runnerUp);
		// 이름을 정렬해 키를 만든다 — (A,B) 와 (B,A) 가 다른 짝으로 세어지면 안 된다.
		String a = winnerName.compareTo(runnerName) <= 0 ? winnerName : runnerName;
		String b = winnerName.compareTo(runnerName) <= 0 ? runnerName : winnerName;

		PairRecord record = records.computeIfAbsent(a + "|" + b, k -> new PairRecord(a, b));
		record.total++;
		if (winnerName.equals(record.a)) {
			record.aWins++;
		}
		else {
			record.bWins++;
		}
		record.cases.add(new PairCase(bid.get("bidNtceNo"), bid.get("bidNtceNm"),
				winnerName, runnerName,
				field(winner, "bidprcRt", "bidprcrt"), field(runnerUp, "bidprcRt", "bidprcrt"),
				bid.get("opengDate")));
	}

	/**
	 * 짝 요약.
	 *
	 * <p>{@code total < 2} 면 번갈아 점수를 0으로 둔다 — 한 번 만난 두 업체는 정의상
	 * 번갈아 이길 수 없는데, 공식을 그대로 태우면 1.0(완전 교대)이 나온다.
	 */
	private static Pair summarizePair(PairRecord pair) {
		double alter = pair.total >= 2
				? 1 - Math.abs(pair.aWins - pair.bWins) / (double) pair.total
				: 0;
		BigDecimal suspicion = BigDecimal.valueOf(pair.total * alter * 10)
				.setScale(0, RoundingMode.HALF_UP)
				.divide(BigDecimal.TEN, 1, RoundingMode.HALF_UP);
		return new Pair(pair.a, pair.b, pair.total, pair.aWins, pair.bWins,
				List.copyOf(pair.cases), (int) Math.round(alter * 100), suspicion);
	}

	private static Company summarizeCompany(String name, CompanyRecord record) {
		List<BigDecimal> rates = record.bidRates;
		BigDecimal avgRate = null;
		if (!rates.isEmpty()) {
			BigDecimal sum = rates.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
			avgRate = sum.divide(BigDecimal.valueOf(rates.size()), 10, RoundingMode.HALF_UP)
					.setScale(1, RoundingMode.HALF_UP);
		}
		boolean monotonic = rates.size() >= 3;
		for (int i = 1; monotonic && i < rates.size(); i++) {
			monotonic = rates.get(i).compareTo(rates.get(i - 1)) >= 0;
		}
		return new Company(name, record.wins, record.runnerUp, record.appearances,
				avgRate, List.copyOf(rates), monotonic, List.copyOf(record.cases));
	}

	private static String str(Object value) {
		return value == null ? "" : String.valueOf(value);
	}

	private static final class PairRecord {

		private final String a;
		private final String b;
		private int total;
		private int aWins;
		private int bWins;
		private final List<PairCase> cases = new ArrayList<>();

		private PairRecord(String a, String b) {
			this.a = a;
			this.b = b;
		}
	}

	private static final class CompanyRecord {

		private int wins;
		private int runnerUp;
		private int appearances;
		private final List<BigDecimal> bidRates = new ArrayList<>();
		private final List<CompanyCase> cases = new ArrayList<>();
	}
}
