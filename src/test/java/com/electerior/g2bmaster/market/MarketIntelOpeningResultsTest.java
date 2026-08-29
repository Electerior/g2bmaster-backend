package com.electerior.g2bmaster.market;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.electerior.g2bmaster.integration.g2b.G2bException;
import com.electerior.g2bmaster.integration.g2b.G2bFetchService;
import com.electerior.g2bmaster.notice.G2bEndpoints;
import com.electerior.g2bmaster.notice.NoticeFetchSupport;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * 개찰결과(참여업체) 조회의 계약.
 *
 * <p>이 조회는 <b>실패해도 빈 배열</b>이라 회귀가 화면에서 "개찰 전"과 구분되지 않는다.
 * 실제로 오퍼레이션 URL이 폐기된 뒤 몇 달 동안 아무도 눈치채지 못했다. 그래서 상류 필드
 * 이름과 요청 파라미터를 여기서 문자열째 고정한다.
 */
class MarketIntelOpeningResultsTest {

	private G2bFetchService fetchService;
	private MarketIntelService service;

	@BeforeEach
	void setUp() {
		fetchService = mock(G2bFetchService.class);
		service = new MarketIntelService(
				new G2bEndpoints("https://apis.data.go.kr/1230000"),
				fetchService,
				mock(NoticeFetchSupport.class));
	}

	private static Map<String, Object> row(String ord, String rank, String name, String amt, String rate) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("bidNtceNo", "R26BK01629628");
		map.put("bidNtceOrd", ord);
		map.put("opengRank", rank);
		map.put("prcbdrNm", name);
		map.put("prcbdrBizno", "6238803773");
		map.put("bidprcAmt", amt);
		map.put("bidprcrt", rate);
		map.put("rmrk", rank.isEmpty() ? "규격서평가부적격" : "정상");
		return map;
	}

	@Test
	void 개찰완료_원본_필드를_화면_계약_이름으로_옮긴다() {
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt()))
				.thenReturn(List.of(row("000", "1", "에프에스아일랜드학생복", "298000", "95.609")));

		Map<String, Object> p = service.fetchOpeningResults("R26BK01629628", "000", "물품").get(0);

		assertThat(p.get("bdrNm")).isEqualTo("에프에스아일랜드학생복");
		assertThat(p.get("bdrBrn")).isEqualTo("6238803773");
		assertThat(p.get("rank")).isEqualTo("1");
		assertThat(p.get("bidAmt")).isEqualTo("298000");
		assertThat(p.get("bidprcRt")).isEqualTo("95.609");
		assertThat(p.get("_type")).isEqualTo("물품");
		// 원본 키도 남는다 — 지우면 지금 안 쓰는 값이 나중에도 화면에 못 온다.
		assertThat(p).containsKeys("prcbdrNm", "opengRank", "bidprcrt", "rmrk");
	}

	@Test
	void 개찰순위_1위를_낙찰로_본다() {
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt())).thenReturn(List.of(
				row("000", "1", "가나", "298000", "95.609"),
				row("000", "2", "다라", "303000", "97.214")));

		List<Map<String, Object>> ps = service.fetchOpeningResults("R26BK01629628", null, "물품");

		assertThat(ps.get(0).get("sucsfbidYn")).isEqualTo("Y");
		assertThat(ps.get(0).get("_won")).isEqualTo(true);
		assertThat(ps.get(1).get("sucsfbidYn")).isEqualTo("N");
		assertThat(ps.get(1).get("_won")).isEqualTo(false);
	}

	@Test
	void 실격_업체의_빈_순위와_금액을_지어내지_않는다() {
		// 0을 채우면 투찰률 추세와 담합 매트릭스가 있지도 않은 0원 투찰을 사실로 읽는다.
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt()))
				.thenReturn(List.of(row("000", "", "베스트학생복 체육복", "", "")));

		Map<String, Object> p = service.fetchOpeningResults("R26BK01629628", "000", "물품").get(0);

		assertThat(p.get("rank")).isEqualTo("");
		assertThat(p.get("bidAmt")).isEqualTo("");
		assertThat(p.get("bidprcRt")).isEqualTo("");
		assertThat(p.get("sucsfbidYn")).isEqualTo("N");
	}

	@Test
	void 차수는_요청에_싣지_않고_응답에서_좁힌다() {
		// bidNtceOrd 를 보내면 차수가 어긋나는 순간 상류가 0건을 준다 — 화면은 차수를 모를 때
		// "000"을 보내므로, 그대로 실어 보내면 재공고된 공고가 전부 빈 채로 나온다.
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt())).thenReturn(List.of(
				row("000", "1", "가나", "1000", "90"),
				row("002", "1", "다라", "2000", "91")));

		List<Map<String, Object>> ps = service.fetchOpeningResults("R26BK01629628", "002", "물품");

		assertThat(ps).hasSize(1);
		assertThat(ps.get(0).get("bdrNm")).isEqualTo("다라");

		@SuppressWarnings("unchecked")
		ArgumentCaptor<Map<String, Object>> params = ArgumentCaptor.forClass(Map.class);
		verify(fetchService).fetchPaged(anyString(), params.capture(), anyInt(), anyInt());
		assertThat(params.getValue()).containsOnlyKeys("bidNtceNo");
	}

	@Test
	void 요청한_차수가_응답에_없으면_전_차수를_그대로_낸다() {
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt()))
				.thenReturn(List.of(row("002", "1", "다라", "2000", "91")));

		assertThat(service.fetchOpeningResults("R26BK01629628", "000", "물품")).hasSize(1);
	}

	@Test
	void 개찰완료_조회는_구분과_무관한_한_오퍼레이션을_쓴다() {
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt())).thenReturn(List.of());

		service.fetchOpeningResults("R26BK01629628", "000", "공사");

		ArgumentCaptor<String> url = ArgumentCaptor.forClass(String.class);
		verify(fetchService).fetchPaged(url.capture(), any(), anyInt(), anyInt());
		assertThat(url.getValue()).isEqualTo("https://apis.data.go.kr/1230000"
				+ "/as/ScsbidInfoService/getOpengResultListInfoOpengCompt");
	}

	@Test
	void 호출이_실패해도_빈_목록이다() {
		// 개찰 전 공고를 열어 본 사용자에게 500을 보여 줄 수는 없다. 다만 로그는 갈라 둔다.
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt()))
				.thenThrow(new G2bException("G2B [12]: 해당 오픈API 서비스가 없거나 폐기됨"));

		assertThat(service.fetchOpeningResults("R26BK01629628", "000", "물품")).isEmpty();
	}

	@Test
	void 공고번호가_없으면_상류를_두드리지_않는다() {
		assertThat(service.fetchOpeningResults("  ", "000", "물품")).isEmpty();
		verify(fetchService, org.mockito.Mockito.never()).fetchPaged(anyString(), any(), anyInt(), anyInt());
	}
}
