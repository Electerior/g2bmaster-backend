package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.electerior.g2bmaster.notice.BidResultRepository;
import com.electerior.g2bmaster.notice.G2bEndpoints;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 적재 시작 지점.
 *
 * <p>워터마크만 따라가면 <b>상류가 늦게 올린 건을 영원히 못 잡는다</b>. 실측으로
 * 2026-08-27 하루가 상류 105건 / 색인 0건이었는데 적재는 그동안 정상으로 돌고 있었다 —
 * 워터마크가 이미 그 시각을 지난 뒤에 상류 목록에 올라왔기 때문이다. 그래서 워터마크가
 * 얼마나 앞서 있든 최근 구간은 늘 다시 읽는다.
 */
class BidResultRescanWindowTest {

	private BidNoticeIndexRepository stateRepository;
	private BidResultIngestService service;

	@BeforeEach
	void setUp() {
		stateRepository = mock(BidNoticeIndexRepository.class);
		// 설정 없이 띄운다 — 이 테스트가 보는 것은 시작 지점 계산뿐이다(index 가 null 이면 기본값).
		com.electerior.g2bmaster.config.G2bProperties properties =
				new com.electerior.g2bmaster.config.G2bProperties(
						null, null, null, null, null, null, null, null, null, null);
		service = new BidResultIngestService(
				mock(com.electerior.g2bmaster.integration.g2b.G2bApiClient.class),
				new G2bEndpoints("https://apis.data.go.kr/1230000"),
				mock(BidResultRepository.class),
				stateRepository,
				properties);
	}

	@Test
	void 워터마크가_지금이어도_최근_구간은_다시_읽는다() {
		LocalDateTime now = LocalDateTime.of(2026, 8, 29, 19, 0);
		when(stateRepository.readWatermark(anyString())).thenReturn(now);

		LocalDateTime from = service.startOf("bid-result:물품", now, 0);

		assertThat(from).isEqualTo(now.minusDays(BidResultIngestService.RESCAN_DAYS));
	}

	@Test
	void 워터마크가_뒤처져_있으면_그쪽이_이긴다() {
		// 구간을 넓히는 쪽으로만 움직인다 — 재조회 바닥이 오히려 창을 좁히면 안 된다.
		LocalDateTime now = LocalDateTime.of(2026, 8, 29, 19, 0);
		LocalDateTime old = now.minusDays(30);
		when(stateRepository.readWatermark(anyString())).thenReturn(old);

		assertThat(service.startOf("bid-result:물품", now, 0))
				.isEqualTo(old.minusMinutes(BidResultIngestService.OVERLAP_MINUTES));
	}

	@Test
	void 운영자가_지시한_백필은_재조회_구간보다도_넓힐_수_있다() {
		LocalDateTime now = LocalDateTime.of(2026, 8, 29, 19, 0);
		when(stateRepository.readWatermark(anyString())).thenReturn(now);

		assertThat(service.startOf("bid-result:물품", now, 60)).isEqualTo(now.minusDays(60));
	}

	@Test
	void 워터마크가_없는_첫_회차는_기본_백필을_쓴다() {
		LocalDateTime now = LocalDateTime.of(2026, 8, 29, 19, 0);
		when(stateRepository.readWatermark(anyString())).thenReturn(null);

		assertThat(service.startOf("bid-result:물품", now, 0))
				.isEqualTo(now.minusDays(BidResultIngestService.DEFAULT_BACKFILL_DAYS));
	}
}
