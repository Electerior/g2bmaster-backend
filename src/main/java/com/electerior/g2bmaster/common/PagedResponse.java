package com.electerior.g2bmaster.common;

import java.util.List;
import java.util.Map;

/**
 * 검색 계열 엔드포인트의 공통 페이징 응답.
 *
 * <p>필드명은 기존 모놀리스({@code lib/search.js} 의 {@code paginate()})가 내려주던 것과
 * 그대로 맞춘다 — 프론트를 React 로 새로 쓰더라도 계약을 바꾸면 두 저장소가 동시에
 * 깨지므로, 이식 단계에서는 이름을 유지한다.
 *
 * @param items      현재 페이지 항목
 * @param totalCount 필터링 후 전체 건수
 * @param pageNo     1-base 페이지 번호. 0 이면 페이징 없이 전부 반환한 것이다.
 * @param numOfRows  페이지 크기
 * @param meta       엔드포인트별 부가 정보. 값이 없으면 {@code null} 이고, 그때는
 *                   {@code spring.jackson.default-property-inclusion=non_null} 이 칸을 통째로
 *                   지운다 — 기존 응답의 모양이 바뀌지 않는다는 뜻이다. 4-인자 생성자를 남겨 둔
 *                   이유도 같다: 메타가 필요 없는 호출부는 한 글자도 고치지 않는다
 */
public record PagedResponse<T>(List<T> items, int totalCount, int pageNo, int numOfRows,
		Map<String, Object> meta) {

	public PagedResponse(List<T> items, int totalCount, int pageNo, int numOfRows) {
		this(items, totalCount, pageNo, numOfRows, null);
	}

	/** {@code pageNo == 0} 은 "전부 달라"는 뜻이다 — 기존 동작을 그대로 지킨다. */
	public static <T> PagedResponse<T> of(List<T> all, int pageNo, int numOfRows) {
		int total = all.size();
		if (pageNo <= 0) {
			return new PagedResponse<>(all, total, 0, total);
		}
		int size = Math.max(1, numOfRows);
		int from = Math.min((pageNo - 1) * size, total);
		int to = Math.min(from + size, total);
		return new PagedResponse<>(all.subList(from, to), total, pageNo, size);
	}

	public static <T> PagedResponse<T> empty() {
		return new PagedResponse<>(List.of(), 0, 1, 0);
	}
}
