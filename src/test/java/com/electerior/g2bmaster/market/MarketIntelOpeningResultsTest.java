package com.electerior.g2bmaster.market;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.electerior.g2bmaster.integration.g2b.G2bException;
import com.electerior.g2bmaster.integration.g2b.G2bFetchService;
import com.electerior.g2bmaster.notice.BidResultRepository;
import com.electerior.g2bmaster.notice.G2bEndpoints;
import com.electerior.g2bmaster.notice.NoticeFetchSupport;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
	private BidOpeningResultRepository openingRepository;
	private BidResultRepository bidResultRepository;
	private MarketIntelService service;

	@BeforeEach
	void setUp() {
		fetchService = mock(G2bFetchService.class);
		openingRepository = mock(BidOpeningResultRepository.class);
		bidResultRepository = mock(BidResultRepository.class);
		// 기본은 "저장분 없음" — 기존 시험들은 전부 상류 경로를 보는 것이다.
		when(openingRepository.find(anyString())).thenReturn(Optional.empty());
		/*
		 * 기본 낙찰정보: 1순위 업체가 낙찰자다. 기존 시험들이 그 전제로 쓰여 있고, 그것이
		 * 96.8% 의 현실이기도 하다. 1순위가 아닌 경우는 아래 전용 시험이 따로 본다.
		 */
		when(bidResultRepository.findByBidNtceNo(anyString())).thenReturn(List.of(
				Map.of("bidwinnrBizno", biznoOf("에프에스아일랜드학생복"),
						"bidwinnrNm", "에프에스아일랜드학생복")));
		service = new MarketIntelService(
				new G2bEndpoints("https://apis.data.go.kr/1230000"),
				fetchService,
				mock(NoticeFetchSupport.class),
				openingRepository,
				bidResultRepository);
	}

	/** 업체마다 다른 사업자번호 — 낙찰자 판정이 번호로 이뤄지므로 겹치면 안 된다. */
	private static String biznoOf(String name) {
		return String.valueOf(6238803773L + Math.abs(name.hashCode() % 1000));
	}

	private static Map<String, Object> row(String ord, String rank, String name, String amt, String rate) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("bidNtceNo", "R26BK01629628");
		map.put("bidNtceOrd", ord);
		map.put("opengRank", rank);
		map.put("prcbdrNm", name);
		// 낙찰자 판정이 사업자번호로 이뤄지므로 업체마다 달라야 한다.
		map.put("prcbdrBizno", biznoOf(name));
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
		assertThat(p.get("bdrBrn")).isEqualTo(biznoOf("에프에스아일랜드학생복"));
		assertThat(p.get("rank")).isEqualTo("1");
		assertThat(p.get("bidAmt")).isEqualTo("298000");
		assertThat(p.get("bidprcRt")).isEqualTo("95.609");
		assertThat(p.get("_type")).isEqualTo("물품");
		// 원본 키도 남는다 — 지우면 지금 안 쓰는 값이 나중에도 화면에 못 온다.
		assertThat(p).containsKeys("prcbdrNm", "opengRank", "bidprcrt", "rmrk");
	}

	@Test
	void 낙찰정보의_낙찰업체에만_배지를_단다() {
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt())).thenReturn(List.of(
				row("000", "1", "에프에스아일랜드학생복", "298000", "95.609"),
				row("000", "2", "다라", "303000", "97.214")));

		List<Map<String, Object>> ps = service.fetchOpeningResults("R26BK01629628", null, "물품");

		assertThat(ps.get(0).get("sucsfbidYn")).isEqualTo("Y");
		assertThat(ps.get(0).get("_won")).isEqualTo(true);
		assertThat(ps.get(1).get("sucsfbidYn")).isEqualTo("N");
		assertThat(ps.get(1).get("_won")).isEqualTo(false);
	}

	/*
	 * ── 1순위가 낙찰자가 아닌 경우 (2026-08-30) ──────────────────────────────────
	 *
	 * 실측 220건 중 7건(3.2%). 일곱 건 모두 실제 낙찰자의 투찰률이 1순위보다 높다 —
	 * 더 낮게 쓴 1순위가 적격심사에서 떨어지거나 포기했다. opengRank 는 투찰가 순위이지
	 * 낙찰 순위가 아니다.
	 */

	@Test
	void 적격심사에서_1순위가_떨어지면_배지는_실제_낙찰자에게_간다() {
		when(bidResultRepository.findByBidNtceNo(anyString())).thenReturn(List.of(
				Map.of("bidwinnrBizno", biznoOf("주식회사 두영건설"), "bidwinnrNm", "주식회사 두영건설")));
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt())).thenReturn(List.of(
				row("000", "1", "이건건설 주식회사", "198000", "90.335"),
				row("000", "2", "주식회사 두영건설", "198001", "90.338")));

		List<Map<String, Object>> ps = service.fetchOpeningResults("R26BK01682199", null, "공사");

		assertThat(ps.get(0).get("sucsfbidYn")).isEqualTo("N");
		assertThat(ps.get(1).get("sucsfbidYn")).isEqualTo("Y");
	}

	@Test
	void 상호가_같은_별개_법인에는_배지를_달지_않는다() {
		/*
		 * 실측 R26BK01665876: "금오건설 주식회사"·"금오건설주식회사"·"금오건설 주식회사" 셋이
		 * 참여했는데 사업자번호가 전부 다른 별개 법인이었다(둘은 실격). 공백만 지워 이름을
		 * 맞추면 배지가 셋에 붙는다 — 사업자번호가 양쪽에 있으면 그것만 본다.
		 */
		when(bidResultRepository.findByBidNtceNo(anyString())).thenReturn(List.of(
				Map.of("bidwinnrBizno", biznoOf("금오건설 주식회사"), "bidwinnrNm", "금오건설 주식회사")));
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt())).thenReturn(List.of(
				row("000", "1", "금오건설 주식회사", "1000", "90"),
				row("000", "", "금오건설주식회사", "", ""),
				row("000", "", "금오건설  주식회사", "", "")));

		List<Map<String, Object>> ps = service.fetchOpeningResults("R26BK01665876", null, "공사");

		assertThat(ps.stream().filter(p -> "Y".equals(p.get("sucsfbidYn")))).hasSize(1);
		assertThat(ps.get(0).get("sucsfbidYn")).isEqualTo("Y");
	}

	@Test
	void 사업자번호가_없는_행은_상호로_찾는다() {
		// 상호는 공백을 지워 맞춘다 — "낙찰 사" 對 "낙찰사".
		when(bidResultRepository.findByBidNtceNo(anyString())).thenReturn(List.of(
				Map.of("bidwinnrBizno", "", "bidwinnrNm", "낙찰 사")));
		Map<String, Object> a = new LinkedHashMap<>(row("000", "1", "탈락사", "1000", "90"));
		Map<String, Object> b = new LinkedHashMap<>(row("000", "2", "낙찰사", "1001", "91"));
		a.remove("prcbdrBizno");
		b.remove("prcbdrBizno");
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt())).thenReturn(List.of(a, b));

		List<Map<String, Object>> ps = service.fetchOpeningResults("R26BK01682199", null, "공사");

		assertThat(ps.get(1).get("sucsfbidYn")).isEqualTo("Y");
	}

	@Test
	void 낙찰정보가_없으면_아무에게도_배지를_달지_않는다() {
		// 개찰은 끝났는데 낙찰자 확정 전인 구간이 며칠씩 있다. 그때 1순위를 낙찰이라 부르면
		// 화면이 아직 일어나지 않은 일을 사실로 말하는 셈이다.
		when(bidResultRepository.findByBidNtceNo(anyString())).thenReturn(List.of());
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt())).thenReturn(List.of(
				row("000", "1", "가나", "1000", "90"),
				row("000", "2", "다라", "1001", "91")));

		List<Map<String, Object>> ps = service.fetchOpeningResults("R26BK01629628", null, "물품");

		assertThat(ps).extracting(p -> p.get("sucsfbidYn")).containsOnly("N");
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

	/*
	 * ── 저장(2026-08-29) ────────────────────────────────────────────────────
	 *
	 * 이 경로는 오래 요청마다 상류를 쳤고 완충은 프로세스 메모리 캐시뿐이었다 — 재기동 한 번에
	 * 통째로 날아가고 인스턴스 사이에 공유되지도 않는다. 아래 셋이 그 저장 계약이다.
	 */

	@Test
	void 저장분이_있으면_상류를_치지_않는다() {
		when(openingRepository.find("R26BK01629628")).thenReturn(Optional.of(
				List.of(row("000", "1", "에프에스아일랜드학생복", "298000", "95.609"))));

		List<Map<String, Object>> out = service.fetchOpeningResults("R26BK01629628", "000", "물품");

		verify(fetchService, never()).fetchPaged(anyString(), any(), anyInt(), anyInt());
		// 저장은 정규화 전 원본이므로 화면 계약명으로 옮겨져 나와야 한다.
		assertThat(out).hasSize(1);
		assertThat(out.get(0)).containsEntry("bdrNm", "에프에스아일랜드학생복");
		assertThat(out.get(0)).containsEntry("rank", "1");
	}

	@Test
	void 받아_온_것을_저장한다_빈_결과도() {
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt())).thenReturn(List.of());

		service.fetchOpeningResults("R26BK01629628", "000", "물품");

		// "받아 봤는데 개찰 전이었다"는 사실을 남기지 않으면 열 때마다 상류를 친다.
		verify(openingRepository).save("R26BK01629628", List.of());
	}

	@Test
	void 상류가_실패하면_저장하지_않는다() {
		when(fetchService.fetchPaged(anyString(), any(), anyInt(), anyInt()))
				.thenThrow(new G2bException("나라장터 점검 중"));

		assertThat(service.fetchOpeningResults("R26BK01629628", "000", "물품")).isEmpty();

		// 빈 배열로 굳히면 상류가 살아난 뒤에도 그 공고만 영영 비어 보인다.
		verify(openingRepository, never()).save(anyString(), any());
	}

	@Test
	void 백필은_이미_저장된_공고를_건너뛴다() {
		when(openingRepository.find("R26BK01629628")).thenReturn(Optional.of(List.of()));

		assertThat(service.storeOpeningResults("R26BK01629628")).isEqualTo(-1);
		verify(fetchService, never()).fetchPaged(anyString(), any(), anyInt(), anyInt());
	}
}
