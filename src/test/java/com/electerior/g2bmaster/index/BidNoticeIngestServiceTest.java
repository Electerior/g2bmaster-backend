package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import com.electerior.g2bmaster.index.BidNoticeIngestService.FetchResult;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

/**
 * {@link BidNoticeIngestService#runWindows} 의 커버리지(coveredTo) 계약
 *
 * <p>워터마크 버그 수정의 핵심 불변 — {@code now} 로 전진하면 안 되고,
 * <b>실제로 완전 탐색한 창 끝(coveredTo)까지만</b> 전진해야 한다.
 *
 * <ul>
 *   <li>전 창 완전 탐색 → {@code capped=false}, {@code coveredTo=to}</li>
 *   <li>예산 소진 → {@code capped=true}, {@code coveredTo}=마지막 완전 탐색 창 끝
 *       (그리고 다음 회차가 그 끝부터 이어 진행해야 한다 — 리블록 금지)</li>
 *   <li>빈 창(0건) → 완전 탐색으로 간주 → {@code coveredTo} 는 그대로 전진</li>
 *   <li>단일 날짜 창조차 예산 초과(이분 불가) → 정직하게 진행 없음
 *       ({@code coveredTo=from}, {@code capped=true})</li>
 * </ul>
 *
 * <p>실제 HTTP 대신 fetch 함수를 위장해 결정적으로 검증한다.
 * 위장 fetch 는 창 길이(일)에 비례해 {@code max(1, 일수) × 1,000} 건을 돌려준다.
 * MAX_ROWS_PER_RUN = 20,000 (private 상수)을 가정한다.
 */
class BidNoticeIngestServiceTest {

	private static final String URL = "http://test";
	private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	private static final int BUDGET = 20_000;

	/** 창 길이(일)에 비례해 건수를 돌려주는 위장 fetch. */
	private static BiFunction<String, String, List<Map<String, Object>>> proportionalFetch() {
		return (from, to) -> {
			long days = Math.max(1, ChronoUnit.DAYS.between(
					LocalDateTime.parse(from, FMT), LocalDateTime.parse(to, FMT)));
			int n = (int) (days * 1_000);
			return rows(n, from);
		};
	}

	private static List<Map<String, Object>> rows(int n, String seed) {
		List<Map<String, Object>> out = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			out.add(Map.of("bidNtceNo", seed + "-" + i, "bidNtceDt", seed, "bidNtceOrd", "1"));
		}
		return out;
	}

	@Test
	void 전_창_완전_탐색이면_capped_아니고_coveredTo는_to까지_전진한다() {
		// 15일 창 = 14,000건 < 20,000 → 한 번에 완전 탐색.
		LocalDateTime from = LocalDateTime.of(2021, 8, 1, 0, 0);
		LocalDateTime to = LocalDateTime.of(2021, 8, 15, 23, 59);
		FetchResult fr = BidNoticeIngestService.runWindows(proportionalFetch(), URL, from, to);
		assertThat(fr.capped()).isFalse();
		assertThat(fr.coveredTo()).isEqualTo(to);
		assertThat(fr.items()).isNotEmpty();
	}

	@Test
	void 예산_초과라도_매_회차_coveredTo가_진행하고_끝내_to에_도착한다_리블록_금지() {
		// 60일 구간 — 한 회차의 20,000 예산으론 한 번에 못 훔.
		// 핵심 검증: 매 회차 coveredTo 가 반드시 전진하고, 결국 to 에 도달한다.
		// (이게 깨지면 워터마크가 제자리에 박혀 무한 재시도 — 5년 백필이 죽는다.)
		LocalDateTime cursor = LocalDateTime.of(2021, 8, 1, 0, 0);
		LocalDateTime to = LocalDateTime.of(2021, 9, 30, 23, 59);
		int rounds = 0;
		while (cursor.isBefore(to) && rounds < 10) {
			FetchResult fr = BidNoticeIngestService.runWindows(proportionalFetch(), URL, cursor, to);
			assertThat(fr.items()).as("round %d: 매 회차 무언가를 훑어야 한다", rounds).isNotEmpty();
			assertThat(fr.coveredTo())
					.as("round %d: coveredTo 는 항상 from 이상, to 이하", rounds)
					.isAfterOrEqualTo(cursor).isBeforeOrEqualTo(to);
			if (fr.capped()) {
				// 예산에 걸렸으면 이번 회차는 to 에 못 닿았을 것이고,
				// 다음 회차가 coveredTo 부터 이어 진행해야 한다.
				assertThat(fr.coveredTo()).as("round %d: capped 이면 to 에 못 닿는다", rounds).isBefore(to);
			}
			else {
				assertThat(fr.coveredTo()).as("round %d: capped 아니라면 to 에 닿는다", rounds).isEqualTo(to);
			}
			LocalDateTime prev = cursor;
			cursor = fr.coveredTo();
			assertThat(cursor).as("round %d: 워터마크는 항상 전진해야 한다 (리블록 금지)", rounds).isAfter(prev);
			rounds++;
		}
		assertThat(cursor).as("10회차 안에 to 에 도달 (리블록 없음)").isEqualTo(to);
		assertThat(rounds).isBetween(2, 10);
	}

	@Test
	void 빈_창은_완전_탐색으로_간주해_coveredTo가_그대로_전진한다() {
		// 0건 창은 예산에 걸리지 않는다 — 그 구간은 '확인됨' 이므로 워터마크는 전진.
		LocalDateTime from = LocalDateTime.of(2021, 8, 1, 0, 0);
		LocalDateTime to = LocalDateTime.of(2021, 9, 30, 23, 59);
		BiFunction<String, String, List<Map<String, Object>>> empty = (f, t) -> List.of();
		FetchResult fr = BidNoticeIngestService.runWindows(empty, URL, from, to);
		assertThat(fr.capped()).isFalse();
		assertThat(fr.coveredTo()).isEqualTo(to);
		assertThat(fr.items()).isEmpty();
	}

	@Test
	void 단일_날짜_창조차_예산_초과면_정직하게_진행_없다() {
		// 1일 창이 25,000건 — 이분 불가. 받아온 만큼만 남기고 coveredTo 는 from 에서 멈춘다.
		LocalDateTime from = LocalDateTime.of(2021, 8, 1, 0, 0);
		LocalDateTime to = LocalDateTime.of(2021, 8, 1, 23, 59);
		BiFunction<String, String, List<Map<String, Object>>> denseDay = (f, t) -> rows(25_000, f);
		FetchResult fr = BidNoticeIngestService.runWindows(denseDay, URL, from, to);
		assertThat(fr.capped()).isTrue();
		assertThat(fr.coveredTo()).isEqualTo(from); // 진행 없음 — 다음 회차에서 재시도
		assertThat(fr.items()).hasSize(25_000);
	}
}
