package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import com.electerior.g2bmaster.index.BidNoticeIndexRepository.BackfillState;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * 백필 회차의 <b>순서와 상한</b>.
 *
 * <p>여기서 지키는 규칙은 하나다 — <b>참가가능지역은 그 구간의 입찰공고가 이미 색인에 들어온
 * 뒤에만 훑을 수 있다.</b> 지역 오퍼레이션은 행을 만들지 않고 있는 행만 갱신하므로, 아직
 * 공고가 없는 구간의 지역을 받아 오면 그대로 버려지고 워터마크만 지나가 버린다. 그 구간의
 * 지역은 다시 훑을 기회가 없다 — 5년치에서 조용히 새는 종류의 손실이고, 화면에서는
 * "지역 제한 없음(전국)"으로 보여 틀린 줄도 모른다.
 *
 * <p>두 줄기의 진도가 어긋나는 것은 예외가 아니라 평시다: 참가가능지역은 공고 하나에 여러
 * 행이라 같은 기간에 행이 두 배 넘게 많고(실측 2021-08: 입찰공고 29,680건 vs 지역 21,110건에
 * 사전규격·발주계획까지 합치면 지역이 회차당 절반쯤 전진한다), 회차 예산이 출처마다 같으므로
 * 지역 커서는 늘 뒤처진다.
 */
class BidNoticeBackfillOrderTest {

	private static final LocalDateTime TARGET = LocalDateTime.of(2026, 8, 26, 0, 0);

	private static BackfillState job(String source, LocalDateTime watermark) {
		return new BackfillState(source, watermark, TARGET);
	}

	@Test
	void 지역_줄기는_언제나_마지막에_돈다() {
		LocalDateTime same = LocalDateTime.of(2021, 8, 26, 0, 0);
		List<BackfillState> jobs = List.of(
				job("backfill:region", same),
				job("backfill:bid-announce:물품", same),
				job("backfill:nuri:region", same),
				job("backfill:pre-spec:물품", same),
				job("backfill:nuri:bid-announce:물품", same));

		List<String> ordered = BidNoticeIngestService.sortRegionsLast(jobs).stream()
				.map(BackfillState::source).toList();

		// 앞쪽 셋의 상대 순서는 들어온 대로 유지되고, 지역 둘만 뒤로 간다.
		assertThat(ordered).containsExactly(
				"backfill:bid-announce:물품",
				"backfill:pre-spec:물품",
				"backfill:nuri:bid-announce:물품",
				"backfill:region",
				"backfill:nuri:region");
	}

	@Test
	void 지역_상한은_가장_뒤처진_입찰공고_진도로_깎인다() {
		Map<String, LocalDateTime> cursors = new LinkedHashMap<>();
		cursors.put("backfill:bid-announce:물품", LocalDateTime.of(2022, 6, 1, 0, 0));
		cursors.put("backfill:bid-announce:용역", LocalDateTime.of(2022, 3, 1, 0, 0)); // 가장 뒤처짐
		cursors.put("backfill:bid-announce:공사", LocalDateTime.of(2022, 9, 1, 0, 0));
		// 다른 계열은 지역과 무관하다 — 섞여 들어와도 상한을 정하지 못한다.
		cursors.put("backfill:pre-spec:물품", LocalDateTime.of(2021, 9, 1, 0, 0));
		cursors.put("backfill:nuri:bid-announce:물품", LocalDateTime.of(2021, 10, 1, 0, 0));

		assertThat(BidNoticeIngestService.clampToAnnounces(TARGET, cursors, "backfill:bid-announce:"))
				.isEqualTo(LocalDateTime.of(2022, 3, 1, 0, 0));
		assertThat(BidNoticeIngestService.clampToAnnounces(TARGET, cursors, "backfill:nuri:bid-announce:"))
				.isEqualTo(LocalDateTime.of(2021, 10, 1, 0, 0));
	}

	@Test
	void 입찰공고가_이미_끝났으면_깎지_않는다() {
		Map<String, LocalDateTime> cursors = new LinkedHashMap<>();
		// 끝난 줄기는 pendingBackfills 에서 빠지므로 커서 표에 아예 없다.
		cursors.put("backfill:pre-spec:물품", LocalDateTime.of(2021, 9, 1, 0, 0));

		assertThat(BidNoticeIngestService.clampToAnnounces(TARGET, cursors, "backfill:bid-announce:"))
				.isEqualTo(TARGET);
	}

	@Test
	void 입찰공고가_상한을_넘어섰으면_상한이_이긴다() {
		Map<String, LocalDateTime> cursors = new LinkedHashMap<>();
		cursors.put("backfill:bid-announce:물품", TARGET.plusDays(3));

		assertThat(BidNoticeIngestService.clampToAnnounces(TARGET, cursors, "backfill:bid-announce:"))
				.isEqualTo(TARGET);
	}
}
