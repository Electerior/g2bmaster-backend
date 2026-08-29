package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.electerior.g2bmaster.config.G2bProperties;
import com.electerior.g2bmaster.integration.g2b.G2bApiClient;
import com.electerior.g2bmaster.notice.BidResultRepository;
import com.electerior.g2bmaster.notice.G2bEndpoints;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * 회차 상한 보고.
 *
 * <p>상한에 걸렸다는 표시는 <b>다음 회차가 이어받아야 한다</b>는 신호다. 그게 매 회차 거짓으로
 * 켜지면 신호가 아니라 배경 소음이 되고, 진짜로 걸린 날 아무도 알아보지 못한다.
 *
 * <p>실제로 그랬다: 창 경계는 분 단위인데 비교 대상인 현재 시각에는 초가 붙어 있어, 277건만
 * 훑은 회차도 "상한 20000건에 도달"이라는 WARN 을 남기고 상태에 '부분'으로 기록됐다.
 */
class BidResultCapReportingTest {

	private G2bApiClient client;
	private BidNoticeIndexRepository stateRepository;
	private BidResultIngestService service;

	@BeforeEach
	void setUp() {
		client = mock(G2bApiClient.class);
		stateRepository = mock(BidNoticeIndexRepository.class);
		service = new BidResultIngestService(
				client,
				new G2bEndpoints("https://apis.data.go.kr/1230000"),
				mock(BidResultRepository.class),
				stateRepository,
				new G2bProperties(null, null, null, null, null, null, null, null, null, null));
	}

	private static Map<String, Object> row(String no) {
		Map<String, Object> item = new LinkedHashMap<>();
		item.put("bidNtceNo", no);
		item.put("bidwinnrNm", "가나건설");
		item.put("rgstDt", "2026-08-29 19:07:20");
		return item;
	}

	@Test
	void 상한에_안_걸린_회차는_부분으로_기록되지_않는다() {
		// 창은 분 단위로 잘려 나가는데 now 에는 초가 붙어 있다 — 그 차이를 상한으로 읽으면 안 된다.
		when(stateRepository.readWatermark(anyString()))
				.thenReturn(LocalDateTime.of(2026, 8, 29, 19, 53, 41));
		when(client.fetchAllPages(anyString(), any(), anyInt(), anyInt()))
				.thenReturn(List.of(row("R26BK01700646")));

		service.runNow(0);

		ArgumentCaptor<String> note = ArgumentCaptor.forClass(String.class);
		verify(stateRepository, org.mockito.Mockito.atLeastOnce())
				.recordSuccess(anyString(), any(), anyInt(), note.capture());
		assertThat(note.getAllValues()).isNotEmpty();
		assertThat(note.getAllValues()).allSatisfy(n -> assertThat(n).doesNotContain("부분"));
	}

	@Test
	void 진짜로_상한에_걸리면_부분으로_기록한다() {
		// 한 창이 상한을 통째로 채우면 그 회차는 정말 다 못 훑은 것이다 — 그때는 신호가 켜져야 한다.
		when(stateRepository.readWatermark(anyString()))
				.thenReturn(LocalDateTime.now().minusDays(20));
		when(client.fetchAllPages(anyString(), any(), anyInt(), anyInt()))
				.thenAnswer(inv -> {
					int limit = inv.getArgument(3);
					return java.util.stream.IntStream.range(0, limit)
							.mapToObj(i -> row("N" + i))
							.toList();
				});

		service.runNow(0);

		ArgumentCaptor<String> note = ArgumentCaptor.forClass(String.class);
		verify(stateRepository, org.mockito.Mockito.atLeastOnce())
				.recordSuccess(anyString(), any(), anyInt(), note.capture());
		assertThat(note.getAllValues()).anySatisfy(n -> assertThat(n).contains("부분"));
	}
}
