package com.electerior.g2bmaster.analysis;

import static org.assertj.core.api.Assertions.assertThat;

import com.electerior.g2bmaster.analysis.CollusionAnalysis.CollusionMatrix;
import com.electerior.g2bmaster.analysis.CollusionAnalysis.Company;
import com.electerior.g2bmaster.analysis.CollusionAnalysis.Pair;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 담합 정황 매트릭스.
 *
 * <p>{@code lib/collusion-analysis.js} 에는 자체 검증이 없었다. 하지만 이 결과는 사람이
 * "이 짝을 들여다볼까"를 정하는 데 쓰이므로, 점수 정의(번갈아 정도 × 건수)가 조용히
 * 바뀌면 안 된다.
 */
class CollusionAnalysisTest {

	private static Map<String, Object> bid(String no, String name, Object... participants) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("bidNtceNo", no);
		map.put("bidNtceNm", name);
		map.put("opengDate", "2026-07-01");
		List<Object> list = new ArrayList<>(List.of(participants));
		map.put("participants", list);
		return map;
	}

	/**
	 * 참여업체 한 줄. <b>낙찰 여부는 순위가 아니라 {@code sucsfbidYn} 이 정한다</b> —
	 * 실측 220건 중 7건에서 개찰 1순위가 낙찰자가 아니었다(적격심사 탈락·포기).
	 * 편의상 순위 1을 낙찰로 두되, 그렇지 않은 경우는 {@link #participant(String, String, String, boolean)} 로 쓴다.
	 */
	private static Map<String, Object> participant(String name, String rank, String rate) {
		return participant(name, rank, rate, "1".equals(rank));
	}

	private static Map<String, Object> participant(String name, String rank, String rate, boolean won) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("bdrNm", name);
		map.put("rank", rank);
		map.put("bidprcRt", rate);
		map.put("bidAmt", "1000");
		map.put("sucsfbidYn", won ? "Y" : "N");
		return map;
	}

	/** 나라장터 개찰완료 원본 이름 그대로인 줄. 정규화를 거치지 않고 흘러든 경우다. */
	private static Map<String, Object> upstreamParticipant(String name, String rank, String rate) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("prcbdrNm", name);
		map.put("opengRank", rank);
		map.put("bidprcrt", rate);
		map.put("bidprcAmt", "1000");
		return map;
	}

	@Test
	void 정규화되지_않은_원본_필드명으로도_짝을_만든다() {
		// 값이 없으면 오류가 아니라 "짝 0건"으로 보인다 — 아무도 이상하다고 느끼지 않는 실패다.
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "노트북",
						upstreamParticipant("B사", "2", "88"),
						upstreamParticipant("A사", "1", "90"))));

		assertThat(matrix.pairs()).hasSize(1);
		Pair pair = matrix.pairs().get(0);
		assertThat(pair.cases().get(0).winner()).isEqualTo("A사");
		assertThat(pair.cases().get(0).runnerUp()).isEqualTo("B사");
		assertThat(pair.cases().get(0).winBidprcRt()).isEqualTo("90");
		assertThat(matrix.companies()).extracting(Company::name).containsExactlyInAnyOrder("A사", "B사");
	}

	@Test
	void 순위가_뒤섞여_와도_1위와_2위를_고른다() {
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "노트북",
						participant("B사", "2", "88"),
						participant("A사", "1", "90"))));

		assertThat(matrix.pairs()).hasSize(1);
		Pair pair = matrix.pairs().get(0);
		assertThat(pair.cases().get(0).winner()).isEqualTo("A사");
		assertThat(pair.cases().get(0).runnerUp()).isEqualTo("B사");
	}

	@Test
	void 짝_키는_이름_순서에_흔들리지_않는다() {
		// (A,B)와 (B,A)가 다른 짝으로 세어지면 교대 패턴이 통째로 안 보인다.
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "1차", participant("A사", "1", "90"), participant("B사", "2", "91")),
				bid("N2", "2차", participant("B사", "1", "92"), participant("A사", "2", "93"))));

		assertThat(matrix.pairs()).hasSize(1);
		Pair pair = matrix.pairs().get(0);
		assertThat(pair.total()).isEqualTo(2);
		assertThat(pair.aWins()).isEqualTo(1);
		assertThat(pair.bWins()).isEqualTo(1);
		assertThat(pair.alterScore()).isEqualTo(100);            // 완전 교대
		assertThat(pair.suspicionScore()).isEqualByComparingTo("2.0");
	}

	@Test
	void 한_번만_만난_짝은_교대_점수가_0이다() {
		// 공식을 그대로 태우면 1.0(완전 교대)이 나온다 — 한 번 만난 둘은 정의상 교대할 수 없다.
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "1차", participant("A사", "1", "90"), participant("B사", "2", "91"))));

		assertThat(matrix.pairs().get(0).alterScore()).isZero();
		assertThat(matrix.pairs().get(0).suspicionScore()).isEqualByComparingTo("0.0");
	}

	@Test
	void 투찰률_단조_증가를_잡는다() {
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "1차", participant("A사", "1", "80"), participant("B사", "2", "95")),
				bid("N2", "2차", participant("A사", "1", "85"), participant("B사", "2", "96")),
				bid("N3", "3차", participant("A사", "1", "90"), participant("B사", "2", "97"))));

		Company a = matrix.companies().stream()
				.filter(c -> c.name().equals("A사")).findFirst().orElseThrow();
		assertThat(a.wins()).isEqualTo(3);
		assertThat(a.appearances()).isEqualTo(3);
		assertThat(a.isMonotonicallyIncreasing()).isTrue();
		assertThat(a.avgRate()).isEqualByComparingTo("85.0");
	}

	@Test
	void 표본이_3건_미만이면_단조_증가로_보지_않는다() {
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "1차", participant("A사", "1", "80"), participant("B사", "2", "95")),
				bid("N2", "2차", participant("A사", "1", "85"), participant("B사", "2", "96"))));

		Company a = matrix.companies().stream()
				.filter(c -> c.name().equals("A사")).findFirst().orElseThrow();
		assertThat(a.isMonotonicallyIncreasing()).isFalse();
	}

	@Test
	void 참여업체가_없거나_이름이_비면_그_공고를_건너뛴다() {
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "참여없음"),
				bid("N2", "이름없음", participant("", "1", "90"))));

		assertThat(matrix.pairs()).isEmpty();
		assertThat(matrix.companies()).isEmpty();
	}

	@Test
	void 단독_입찰은_업체만_기록하고_짝은_만들지_않는다() {
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "단독", participant("A사", "1", "99"))));

		assertThat(matrix.pairs()).isEmpty();
		assertThat(matrix.companies()).hasSize(1);
		assertThat(matrix.companies().get(0).wins()).isEqualTo(1);
	}

	@Test
	void 투찰률_표본이_없으면_평균은_0이_아니라_null_이다() {
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "단독", participant("A사", "1", ""))));

		assertThat(matrix.companies().get(0).avgRate()).isNull();
		assertThat(matrix.companies().get(0).rateHistory()).isEmpty();
	}

	@Test
	void 짝은_의심도_내림차순으로_정렬된다() {
		CollusionMatrix matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "1", participant("A사", "1", "90"), participant("B사", "2", "91")),
				bid("N2", "2", participant("C사", "1", "90"), participant("D사", "2", "91")),
				bid("N3", "3", participant("D사", "1", "90"), participant("C사", "2", "91"))));

		assertThat(matrix.pairs().get(0).a()).isEqualTo("C사");   // 2건 교대가 위로
		assertThat(matrix.pairs().get(0).total()).isEqualTo(2);
	}

	/*
	 * ── 낙찰자는 1순위가 아닐 수 있다 (2026-08-30) ──────────────────────────────
	 *
	 * 예전에는 participants.get(0) 을 승자로 봤다. 실측 220건 중 7건(3.2%)에서 그것이
	 * 틀렸다 — 더 낮게 쓴 1순위가 적격심사에서 떨어지거나 포기하고 다음 순위가 낙찰됐다
	 * (낙찰자가 2순위 5건 · 3순위 1건 · 6순위 1건). 화면 배지와 달리 이 통계는 틀려도
	 * 눈에 띄지 않으므로 여기서 못박는다.
	 */

	@Test
	@DisplayName("낙찰은 개찰 1순위가 아니라 sucsfbidYn 이 정한다")
	void awardFollowsFlagNotRank() {
		var matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "적격심사 탈락 건",
						participant("탈락사", "1", "90", false),
						participant("낙찰사", "2", "91", true))));

		var winner = matrix.companies().stream().filter(c -> c.name().equals("낙찰사")).findFirst();
		assertThat(winner).isPresent();
		assertThat(winner.get().wins()).isEqualTo(1);

		var dropped = matrix.companies().stream().filter(c -> c.name().equals("탈락사")).findFirst();
		assertThat(dropped).isPresent();
		// 1순위였지만 낙찰이 아니다. 2위(경쟁 상대)로 기록된다.
		assertThat(dropped.get().wins()).isZero();
		assertThat(dropped.get().runnerUp()).isEqualTo(1);
	}

	@Test
	@DisplayName("낙찰자가 6순위여도 그 업체가 승자다")
	void awardCanBeFarDownTheRanking() {
		var matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "여섯 번째가 낙찰",
						participant("1순위", "1", "90", false),
						participant("2순위", "2", "91", false),
						participant("6순위", "6", "95", true))));

		var winner = matrix.companies().stream().filter(c -> c.name().equals("6순위")).findFirst();
		assertThat(winner).isPresent();
		assertThat(winner.get().wins()).isEqualTo(1);
	}

	@Test
	@DisplayName("낙찰이 확정되지 않은 공고는 통계에서 뺀다 — 1순위를 승자로 추정하지 않는다")
	void skipsBidsWithoutConfirmedAward() {
		/*
		 * 개찰은 끝났는데 낙찰자 확정 전인 구간이 며칠씩 있다. 그때 1순위를 낙찰로 세면
		 * 아직 일어나지 않은 일이 승패 통계에 들어간다. 실제 경로에서는 낙찰정보가 있는
		 * 행만 담합 분석에 들어가므로 이 분기는 방어선이다.
		 */
		var matrix = CollusionAnalysis.buildCollusionMatrix(List.of(
				bid("N1", "낙찰 미확정",
						participant("A사", "1", "90", false),
						participant("B사", "2", "91", false))));

		assertThat(matrix.companies()).isEmpty();
		assertThat(matrix.pairs()).isEmpty();
	}
}
