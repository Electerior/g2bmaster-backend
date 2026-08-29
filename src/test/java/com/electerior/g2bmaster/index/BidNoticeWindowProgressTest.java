package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import com.electerior.g2bmaster.index.BidNoticeIngestService.FetchResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

/**
 * 상류가 <b>중간에</b> 죽었을 때 이번 회차가 훑은 것을 지키는가.
 *
 * <p>이것이 없으면 진행이 영원히 안 되는 출처가 생긴다. 실패한 회차는 워터마크를 전진시키지
 * 않는 것이 규칙인데(그 구간이 영영 비는 것을 막는 규칙이다), 61개 창 중 20번째에서 죽으면
 * 앞의 19개를 버리고 다음 회차도 1번 창부터 다시 시작한다 — 같은 자리에서 또 죽는다.
 *
 * <p>실측으로 누리장터 '기타'가 그랬다. 5년 전 구간이 창마다 0건이라 61개 창을 순식간에
 * 두드리다 게이트웨이 429 를 맞았고, 다섯 회차 연속으로 2021-08 에서 한 발짝도 못 나갔다.
 * 그 줄기에 상한이 물려 있던 누리 참가가능지역 백필까지 같이 멈췄다.
 */
class BidNoticeWindowProgressTest {

	private static final LocalDateTime FROM = LocalDateTime.of(2021, 8, 26, 0, 0);
	private static final LocalDateTime TO = LocalDateTime.of(2022, 3, 31, 23, 59);

	/** 창 하나에 rowsPerWindow 행을 주다가, failAtCall 번째 호출에서 터지는 가짜 상류. */
	private static BiFunction<String, String, List<Map<String, Object>>> upstream(
			int rowsPerWindow, int failAtCall, List<String> calls) {
		int[] n = {0};
		return (bgn, end) -> {
			n[0]++;
			calls.add(bgn);
			if (n[0] == failAtCall) {
				throw new IllegalStateException("나라장터 HTTP 429");
			}
			List<Map<String, Object>> rows = new ArrayList<>();
			for (int i = 0; i < rowsPerWindow; i++) {
				rows.add(Map.of("bidNtceNo", bgn + "-" + i));
			}
			return rows;
		};
	}

	@Test
	void 중간에_막히면_거기까지_지키고_사유를_남긴다() {
		List<String> calls = new ArrayList<>();
		// 창마다 10행씩 주다가 4번째 창에서 429. 앞의 세 창은 지켜져야 한다.
		FetchResult fr = BidNoticeIngestService.runWindows(
				upstream(10, 4, calls), "http://u", FROM, TO);

		assertThat(fr.items()).hasSize(30);
		assertThat(fr.capped()).isTrue();
		assertThat(fr.stoppedBy()).contains("429");
		// 워터마크는 세 번째 창 끝까지 전진한다 — 시작점에 머무르지 않는다.
		assertThat(fr.coveredTo()).isAfter(FROM);

		// 상태 행에는 '왜' 부분인지가 남는다. 예산 소진과 구별되어야 한다.
		assertThat(BidNoticeIngestService.partialNote(fr, "30건 조회 / 30건 색인"))
				.contains("상류가 막아 중단").contains("429");
	}

	@Test
	void 첫_창부터_막히면_회차가_통째로_실패한다() {
		List<String> calls = new ArrayList<>();
		// 지킬 것이 없으면 삼키지 않는다 — 인증 실패·쿼터 소진이 이 모양이고,
		// 그때는 워터마크를 세워 두는 것이 맞다.
		org.assertj.core.api.Assertions.assertThatThrownBy(() ->
				BidNoticeIngestService.runWindows(upstream(10, 1, calls), "http://u", FROM, TO))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("429");
	}

	@Test
	void 아무_문제가_없으면_끝까지_훑고_사유가_없다() {
		List<String> calls = new ArrayList<>();
		FetchResult fr = BidNoticeIngestService.runWindows(
				upstream(10, -1, calls), "http://u", FROM, TO);

		assertThat(fr.capped()).isFalse();
		assertThat(fr.stoppedBy()).isNull();
		assertThat(BidNoticeIngestService.partialNote(fr, "x")).startsWith("성공: ");
		// 2021-08-26 ~ 2022-03-31 은 달 경계로 잘려 여덟 창이다.
		assertThat(calls).hasSize(8);
	}
}
