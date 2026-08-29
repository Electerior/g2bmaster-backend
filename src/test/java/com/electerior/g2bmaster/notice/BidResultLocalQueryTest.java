package com.electerior.g2bmaster.notice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.electerior.g2bmaster.cache.SearchResultCache;
import com.electerior.g2bmaster.common.ApiException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * 입찰결과 조회가 로컬 색인으로 옮겨 오면서 <b>조용히 틀릴 수 있는 자리</b>들.
 *
 * <p>라이브 팬아웃일 때는 날짜와 업종을 상류에 문자열로 넘겼고, 틀리면 오류 응답이 돌아왔다.
 * 로컬은 SQL 조건이라 틀려도 아무 소리 없이 <b>결과만 줄어든다</b> — 그래서 못박는다.
 */
class BidResultLocalQueryTest {

	private BidResultRepository repository;
	private BidResultService service;

	@BeforeEach
	void setUp() {
		repository = mock(BidResultRepository.class);
		when(repository.findWindow(any(), any(), any(), anyInt())).thenReturn(List.of());
		service = new BidResultService(repository, new SearchResultCache());
	}

	/** 저장소에 실제로 간 조회 조건. */
	private record Asked(LocalDateTime from, LocalDateTime toExclusive, Collection<String> types) {}

	@SuppressWarnings("unchecked")
	private Asked asked() {
		ArgumentCaptor<LocalDateTime> from = ArgumentCaptor.forClass(LocalDateTime.class);
		ArgumentCaptor<LocalDateTime> to = ArgumentCaptor.forClass(LocalDateTime.class);
		ArgumentCaptor<Collection<String>> types = ArgumentCaptor.forClass(Collection.class);
		verify(repository).findWindow(from.capture(), to.capture(), types.capture(), anyInt());
		return new Asked(from.getValue(), to.getValue(), types.getValue());
	}

	private static SearchCriteria criteria(String fromDate, String toDate, String bidType) {
		return new SearchCriteria(null, null, null, null, null, fromDate, toDate, null, null, null,
				null, bidType, null);
	}

	private static Map<String, Object> item(String no, String name, String winner, String type) {
		Map<String, Object> row = new LinkedHashMap<>();
		row.put("bidNtceNo", no);
		row.put("bidNtceNm", name);
		row.put("bidwinnrNm", winner);
		row.put("_type", type);
		return row;
	}

	// ── 조회 창 ─────────────────────────────────────────────────────────────

	/**
	 * 화면이 보내는 종료일은 23:59 로 채워져 들어온다({@link SearchCriteria#dates()}).
	 * {@code rgst_dt} 는 {@code DATETIME(6)} 이라 그 값을 그대로 {@code <=} 상한에 쓰면
	 * 23:59:00.000001 부터 23:59:59 사이에 등록된 건이 통째로 사라진다 — 마지막 날 저녁의
	 * 낙찰 건이 조용히 빠지는 종류다.
	 */
	@Test
	void 창_상한은_배타여서_종료일_마지막_1분을_잃지_않는다() {
		service.search(criteria("2026-08-01", "2026-08-07", null), null);

		Asked asked = asked();
		assertThat(asked.from()).isEqualTo(LocalDateTime.of(2026, 8, 1, 0, 0));
		assertThat(asked.toExclusive()).isEqualTo(LocalDateTime.of(2026, 8, 8, 0, 0));
	}

	/** 기간 미지정이면 라이브와 같은 기본 7일 창이다 — 어긋나면 건수가 조용히 달라진다. */
	@Test
	void 기간_미지정이면_기본_7일_창이다() {
		service.search(criteria(null, null, null), null);

		Asked asked = asked();
		assertThat(asked.from().toLocalDate()).isEqualTo(LocalDate.now().minusDays(7));
		assertThat(asked.toExclusive().toLocalDate()).isEqualTo(LocalDate.now().plusDays(1));
	}

	/**
	 * {@code G2bDates.toG2bDt} 는 형식을 검증하지 않아 {@code 2026-8-6} 같은 값이 그대로 통과한다.
	 * 라이브에서는 상류가 오류를 돌려줬지만 로컬에서는 파싱 예외라, 그냥 두면 500 이 된다.
	 */
	@Test
	void 읽을_수_없는_기간은_400_이다() {
		assertThatThrownBy(() -> service.search(criteria("2026-8-6", null, null), null))
				.isInstanceOf(ApiException.class)
				.hasMessageContaining("조회 기간");
	}

	// ── 업종 ────────────────────────────────────────────────────────────────

	/** 업종 미지정은 셋 다 본다 — 라이브에서 오퍼레이션 URL 셋을 전부 부르던 것과 같다. */
	@Test
	void 업종_미지정이면_물품_용역_공사를_모두_본다() {
		service.search(criteria(null, null, null), null);

		assertThat(asked().types()).containsExactly("물품", "용역", "공사");
	}

	@Test
	void 업종을_고르면_그_업종만_읽는다() {
		service.search(criteria(null, null, "용역"), null);

		assertThat(asked().types()).containsExactly("용역");
	}

	// ── 필터 ────────────────────────────────────────────────────────────────

	/**
	 * 라이브 경로는 검색어를 상류 파라미터로도 밀었지만 받아온 뒤 haystack 으로 한 번 더 걸렀다.
	 * 로컬에는 밀 상류가 없으므로 그 필터가 <b>유일한</b> 검색어 조건이 된다 — 빠지면 검색어가
	 * 아무 일도 하지 않게 된다.
	 */
	@Test
	void 검색어_필터는_로컬에서도_그대로_돈다() {
		when(repository.findWindow(any(), any(), any(), anyInt())).thenReturn(List.of(
				item("N1", "노트북 구매", "가나전자", "물품"),
				item("N2", "책상 구매", "다라가구", "물품")));

		List<Map<String, Object>> found = service
				.search(new SearchCriteria("노트북", null, null, null, null, null, null, null, null,
						null, null, null, null), null)
				.value();

		assertThat(found).hasSize(1);
		assertThat(found.get(0)).containsEntry("bidNtceNo", "N1");
	}

	/** 낙찰업체 필터는 입찰결과에만 있는 조건이다. */
	@Test
	void 낙찰업체_필터가_그대로_돈다() {
		when(repository.findWindow(any(), any(), any(), anyInt())).thenReturn(List.of(
				item("N1", "노트북 구매", "가나전자", "물품"),
				item("N2", "책상 구매", "다라가구", "물품")));

		List<Map<String, Object>> found = service.search(criteria(null, null, null), "다라가구").value();

		assertThat(found).hasSize(1);
		assertThat(found.get(0)).containsEntry("bidNtceNo", "N2");
	}

	/**
	 * 저장 키는 (공고번호, 업종)이라 한 공고가 두 행으로 들어와 있을 수 있다. 낙찰정보에는
	 * 정정 차수가 없으므로 공고번호 하나로 접는다(라이브와 같다).
	 */
	@Test
	void 공고번호가_같으면_하나로_접힌다() {
		when(repository.findWindow(any(), any(), any(), anyInt())).thenReturn(List.of(
				item("N1", "노트북 구매", "가나전자", "물품"),
				item("N1", "노트북 구매", "가나전자", "용역")));

		assertThat(service.search(criteria(null, null, null), null).value()).hasSize(1);
	}
}
