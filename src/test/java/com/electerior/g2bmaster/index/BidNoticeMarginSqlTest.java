package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 마진 백필 SQL 의 모양.
 *
 * <p>{@code BidNoticeUpsertSqlTest} 와 같은 이유로 실행 결과가 아니라 SQL 문자열을 계약으로
 * 고정한다 — 여기서 검사하는 둘은 <b>테스트 DB 가 비어 있으면 통과해 버리는</b> 종류다.
 * 백필은 이미 쌓인 데이터가 있어야 한 줄이라도 갱신하는데, 단위 테스트에는 그 데이터가 없다.
 */
class BidNoticeMarginSqlTest {

	/**
	 * 실제로 밟은 사고의 회귀 테스트.
	 *
	 * <p>타입 검사를 {@code WHERE} 에만 두면 MySQL 이 SELECT 식을 먼저 평가할 수 있고, 원가가
	 * JSON null 인 캐시 행에서 {@code JSON_UNQUOTE} 가 문자열 {@code 'null'} 을 낸다. strict
	 * 모드의 {@code CAST(... AS DECIMAL)} 은 그걸 1366 으로 거절하고, <b>백필 요청 전체가 500</b>
	 * 이 된다 — 한 행 때문에 나머지 수백 건이 함께 죽는다.
	 *
	 * <p>{@code CASE WHEN} 은 분기 안쪽을 조건이 참일 때만 평가하므로 그 경로가 막힌다.
	 * "CAST 가 CASE 안에 있다"가 이 SQL 의 안전성 그 자체다.
	 */
	@Test
	@DisplayName("추정 원가 CAST 는 JSON 타입 검사 CASE 안에 있다")
	void castIsGuardedByJsonType() {
		String sql = BidNoticeIndexRepository.BACKFILL_ESTIMATED_SQL;

		int caseAt = sql.indexOf("CASE WHEN JSON_TYPE(");
		int castAt = sql.indexOf("CAST(");
		int endAt = sql.indexOf("END AS cost");

		assertThat(caseAt).as("타입 검사 CASE 가 있어야 한다").isNotNegative();
		assertThat(castAt).as("CAST 가 CASE 뒤에 온다").isGreaterThan(caseAt);
		assertThat(castAt).as("CAST 가 END 앞에 온다 — 즉 분기 안쪽이다").isLessThan(endAt);
	}

	/**
	 * 확정이 추정에 덮이면 영업이 손으로 맞춘 가격표가 야간 일괄 분석에 조용히 밀린다.
	 * 화면에는 그냥 다른 숫자가 떠 있을 뿐이라 아무도 눈치채지 못한다.
	 */
	@Test
	@DisplayName("추정은 확정을 덮지 않고, 확정은 무엇이든 덮는다")
	void confirmedWinsOverEstimated() {
		assertThat(BidNoticeIndexRepository.BACKFILL_ESTIMATED_SQL)
				.as("추정 갱신에는 우선순위 가드가 붙는다")
				.contains("n.margin_source IS NULL OR n.margin_source = 'estimated'");

		assertThat(BidNoticeIndexRepository.BACKFILL_CONFIRMED_SQL)
				.as("확정 갱신에는 가드가 없다 — 있으면 확정이 확정을 못 덮는다")
				.doesNotContain("n.margin_source IS NULL");
	}

	/**
	 * 원가 0 은 '공짜'가 아니라 '모름'이다. 0 이 색인에 앉으면 마진율 100% 가 되어
	 * <b>목록 맨 위</b>에 뜬다 — 가장 눈에 잘 띄는 자리에 가장 틀린 값이 온다.
	 */
	@Test
	@DisplayName("원가 0 이하는 색인에 앉지 않는다")
	void zeroCostIsNotIndexed() {
		assertThat(BidNoticeIndexRepository.BACKFILL_ESTIMATED_SQL).contains("c.cost > 0");
		assertThat(BidNoticeIndexRepository.BACKFILL_CONFIRMED_SQL).contains("c.price_total > 0");
	}

	/**
	 * 딜 분석 캐시는 (입력 해시 → 결과)라 공고 하나에 행이 여럿이고, 저장 공고는 차수마다
	 * 행이 있다. 접지 않으면 조인이 한 공고에 여러 후보를 물어 오고, MySQL 은 그중 아무거나
	 * 골라 갱신한다 — 같은 백필을 두 번 돌리면 다른 값이 앉을 수 있다.
	 */
	@Test
	@DisplayName("공고당 한 행으로 접는다 — 최신 갱신본")
	void collapsesToLatestRowPerNotice() {
		assertThat(BidNoticeIndexRepository.BACKFILL_ESTIMATED_SQL)
				.contains("MAX(updated_at) AS latest")
				.contains("pick.latest = d.updated_at");
		assertThat(BidNoticeIndexRepository.BACKFILL_CONFIRMED_SQL)
				.contains("MAX(updated_at) AS latest")
				.contains("pick.latest = s.updated_at");
	}
}
