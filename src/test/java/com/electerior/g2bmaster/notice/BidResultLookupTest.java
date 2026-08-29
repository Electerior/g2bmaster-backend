package com.electerior.g2bmaster.notice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.electerior.g2bmaster.integration.g2b.G2bException;
import com.electerior.g2bmaster.integration.g2b.G2bFetchService;
import com.electerior.g2bmaster.integration.g2b.G2bResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * 공고번호 단건 낙찰정보 조회.
 *
 * <p>이 조회가 있는 이유는 <b>색인 구멍이 곧 빈 화면이면 안 되기 때문</b>이다. 공고번호를
 * 알고 묻는 자리에서는 상류에 한 번만 물으면 정확한 답이 온다. 그 "한 번"이 몇 번으로
 * 불어나지 않는지, 그리고 색인에 있으면 아예 안 나가는지를 여기서 고정한다.
 */
class BidResultLookupTest {

	private static final String BASE = "https://apis.data.go.kr/1230000";
	private static final String THNG = BASE + "/as/ScsbidInfoService/getScsbidListSttusThng";

	private BidResultRepository repository;
	private G2bFetchService fetchService;
	private BidResultLookup lookup;

	@BeforeEach
	void setUp() {
		repository = mock(BidResultRepository.class);
		fetchService = mock(G2bFetchService.class);
		lookup = new BidResultLookup(repository, new G2bEndpoints(BASE), fetchService);
	}

	private static G2bResponse one(String bidNtceNo, String winner) {
		Map<String, Object> item = new LinkedHashMap<>();
		item.put("bidNtceNo", bidNtceNo);
		item.put("bidwinnrNm", winner);
		item.put("sucsfbidAmt", "4213000");
		item.put("rgstDt", "2026-08-27 15:25:06");
		return new G2bResponse(List.of(item), 1, 1, 10, "00", "정상");
	}

	private static G2bResponse none() {
		return new G2bResponse(List.of(), 0, 1, 10, "00", "정상");
	}

	@Test
	void 색인에_있으면_상류를_두드리지_않는다() {
		when(repository.findByBidNtceNo("R26BK01697446"))
				.thenReturn(List.of(Map.of("bidNtceNo", "R26BK01697446", "bidwinnrNm", "코어방재기술")));

		List<Map<String, Object>> rows = lookup.byNoticeNo("R26BK01697446", "물품");

		assertThat(rows).hasSize(1);
		verify(fetchService, never()).callCached(anyString(), any());
	}

	@Test
	void 구분을_알면_상류_호출은_한_번이다() {
		when(repository.findByBidNtceNo(anyString())).thenReturn(List.of());
		when(fetchService.callCached(eq(THNG), any())).thenReturn(one("R26BK01697446", "코어방재기술"));

		List<Map<String, Object>> rows = lookup.byNoticeNo("R26BK01697446", "물품");

		assertThat(rows).hasSize(1);
		assertThat(rows.get(0).get("_type")).isEqualTo("물품");
		verify(fetchService).callCached(anyString(), any());
	}

	@Test
	void 공고번호_조회는_PPSSrch_가_아닌_오퍼레이션에_조회구분_4로_나간다() {
		// PPSSrch 는 inqryDiv=4 를 거절하고 결과코드 08 을 낸다 — URL 을 문자열째 고정한다.
		when(repository.findByBidNtceNo(anyString())).thenReturn(List.of());
		when(fetchService.callCached(anyString(), any())).thenReturn(one("N1", "가나"));

		lookup.byNoticeNo("N1", "물품");

		ArgumentCaptor<String> url = ArgumentCaptor.forClass(String.class);
		@SuppressWarnings("unchecked")
		ArgumentCaptor<Map<String, Object>> params = ArgumentCaptor.forClass(Map.class);
		verify(fetchService).callCached(url.capture(), params.capture());
		assertThat(url.getValue()).isEqualTo(THNG);
		assertThat(params.getValue()).containsEntry("inqryDiv", 4).containsEntry("bidNtceNo", "N1");
	}

	@Test
	void 구분을_모르면_찾을_때까지만_훑는다() {
		when(repository.findByBidNtceNo(anyString())).thenReturn(List.of());
		when(fetchService.callCached(anyString(), any())).thenReturn(none());
		when(fetchService.callCached(eq(BASE + "/as/ScsbidInfoService/getScsbidListSttusServc"), any()))
				.thenReturn(one("N1", "가나"));

		List<Map<String, Object>> rows = lookup.byNoticeNo("N1", "");

		assertThat(rows).hasSize(1);
		assertThat(rows.get(0).get("_type")).isEqualTo("용역");
		// 물품 → 용역에서 걸렸으므로 공사는 묻지 않는다.
		verify(fetchService, never())
				.callCached(eq(BASE + "/as/ScsbidInfoService/getScsbidListSttusCnstwk"), any());
	}

	@Test
	void 상류에서_받은_것을_색인에_얹는다() {
		// 사용자가 실제로 열어 본 공고부터 구멍이 메워진다 — 같은 공고를 다시 열면 상류에 안 간다.
		when(repository.findByBidNtceNo(anyString())).thenReturn(List.of());
		when(fetchService.callCached(anyString(), any())).thenReturn(one("N1", "가나"));

		lookup.byNoticeNo("N1", "물품");

		@SuppressWarnings("unchecked")
		ArgumentCaptor<List<BidResultRepository.Row>> rows = ArgumentCaptor.forClass(List.class);
		verify(repository).upsertAll(rows.capture());
		assertThat(rows.getValue()).hasSize(1);
		assertThat(rows.getValue().get(0).bidNtceNo()).isEqualTo("N1");
		assertThat(rows.getValue().get(0).bidType()).isEqualTo("물품");
	}

	@Test
	void 등록일시가_없으면_색인에_넣지_않는다() {
		// rgst_dt 는 조회 창의 앵커다 — 없는 행을 넣으면 어떤 창 검색에도 안 걸린다.
		when(repository.findByBidNtceNo(anyString())).thenReturn(List.of());
		Map<String, Object> item = new LinkedHashMap<>();
		item.put("bidNtceNo", "N1");
		item.put("bidwinnrNm", "가나");
		when(fetchService.callCached(anyString(), any()))
				.thenReturn(new G2bResponse(List.of(item), 1, 1, 10, "00", "정상"));

		assertThat(lookup.byNoticeNo("N1", "물품")).hasSize(1);
		verify(repository, never()).upsertAll(any());
	}

	@Test
	void 저장에_실패해도_응답은_그대로_낸다() {
		when(repository.findByBidNtceNo(anyString())).thenReturn(List.of());
		when(fetchService.callCached(anyString(), any())).thenReturn(one("N1", "가나"));
		org.mockito.Mockito.doThrow(new IllegalStateException("DB down")).when(repository).upsertAll(any());

		assertThat(lookup.byNoticeNo("N1", "물품")).hasSize(1);
	}

	@Test
	void 낙찰_확정_전이면_빈_목록이다() {
		when(repository.findByBidNtceNo(anyString())).thenReturn(List.of());
		when(fetchService.callCached(anyString(), any())).thenReturn(none());

		assertThat(lookup.byNoticeNo("N1", "물품")).isEmpty();
	}

	@Test
	void 호출이_실패해도_빈_목록이다() {
		when(repository.findByBidNtceNo(anyString())).thenReturn(List.of());
		when(fetchService.callCached(anyString(), any()))
				.thenThrow(new G2bException("G2B [08]: 필수값 입력 에러"));

		assertThat(lookup.byNoticeNo("N1", "물품")).isEmpty();
	}

	@Test
	void 공고번호가_비면_아무것도_하지_않는다() {
		assertThat(lookup.byNoticeNo("  ", "물품")).isEmpty();
		verify(repository, never()).findByBidNtceNo(anyString());
		verify(fetchService, never()).callCached(anyString(), any());
	}
}
