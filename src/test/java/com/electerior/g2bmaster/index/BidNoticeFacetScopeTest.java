package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * 패싯이 <b>어느 축을 어떤 WHERE 로</b> 세는가 — DB 없이(저장소는 목).
 *
 * <p>이 배선이 어긋나도 화면은 멀쩡히 뜨고 숫자만 조용히 틀린다. 실제로 '마감 전만'이 켜진
 * 채 단계를 고르지 않으면 계획·사전규격 칩이 0건으로 그려졌다 — 눌러 보면 1,887건·1,854건이
 * 나오는데도(2026-08-14 실측). SQL 의 모양은 {@link BidNoticeQueryBuilderTest} 가 보고,
 * 여기서는 그 SQL 이 <b>단계 축에만</b> 쓰이는지를 못박는다.
 */
class BidNoticeFacetScopeTest {

	private static final String SCOPE = "n.category IN ('입찰', '마감')";

	private BidNoticeIndexRepository repository;
	private BidNoticeSearchService service;

	@BeforeEach
	void setUp() {
		repository = mock(BidNoticeIndexRepository.class);
		when(repository.facet(any(), anyString(), anyInt())).thenReturn(List.of());
		service = new BidNoticeSearchService(repository);
	}

	/** '마감 전 공고만 보기' + 단계 미지정 — 화면의 기본 상태이자 문제가 났던 조합이다. */
	private static NoticeSearchRequest activeOnlyWithoutCategory() {
		return new NoticeSearchRequest(null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, "true", null, null, null, null, null, null);
	}

	@Test
	@DisplayName("단계 패싯만 스코프를 뺀 WHERE 로 센다 — 나머지 축은 목록과 같은 조건이다")
	void stageAxisAloneDropsTheScope() {
		service.facets(activeOnlyWithoutCategory(), false);

		Map<String, String> sqlByColumn = capturedSqlByColumn();
		assertThat(sqlByColumn.get("category")).doesNotContain(SCOPE);
		// 나머지 축이 스코프를 잃으면 목록엔 없는 건이 칩 건수에 섞인다 — 그쪽이 더 나쁜 거짓말이다.
		assertThat(sqlByColumn.get("business_division")).contains(SCOPE);
		assertThat(sqlByColumn.get("region")).contains(SCOPE);
		assertThat(sqlByColumn.get("state")).contains(SCOPE);
		assertThat(sqlByColumn.get("source")).contains(SCOPE);
	}

	@Test
	@DisplayName("total 은 목록과 같은 조건의 총건수다 — '전체' 칩이 버킷 합이 아니라 이것을 쓴다")
	void totalCountsTheListScope() {
		when(repository.count(any())).thenReturn(11_306);

		Map<String, Object> facets = service.facets(activeOnlyWithoutCategory(), false);

		assertThat(facets.get("total")).isEqualTo(11_306);

		ArgumentCaptor<BidNoticeQueryBuilder.Where> where = ArgumentCaptor.forClass(BidNoticeQueryBuilder.Where.class);
		verify(repository).count(where.capture());
		assertThat(where.getValue().sql()).contains(SCOPE);
	}

	/** 패싯 컬럼 → 그 축을 셀 때 쓴 WHERE 의 SQL. */
	private Map<String, String> capturedSqlByColumn() {
		ArgumentCaptor<BidNoticeQueryBuilder.Where> where = ArgumentCaptor.forClass(BidNoticeQueryBuilder.Where.class);
		ArgumentCaptor<String> column = ArgumentCaptor.forClass(String.class);
		verify(repository, times(5)).facet(where.capture(), column.capture(), anyInt());

		Map<String, String> byColumn = new HashMap<>();
		List<BidNoticeQueryBuilder.Where> wheres = where.getAllValues();
		List<String> columns = column.getAllValues();
		for (int i = 0; i < columns.size(); i++) {
			byColumn.put(columns.get(i), wheres.get(i).sql());
		}
		return byColumn;
	}
}
