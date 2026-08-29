package com.electerior.g2bmaster.notice;

import com.electerior.g2bmaster.cache.SearchResultCache;
import com.electerior.g2bmaster.common.ApiException;
import com.electerior.g2bmaster.common.G2bDates;
import com.electerior.g2bmaster.common.G2bDates.DateWindow;
import com.electerior.g2bmaster.search.NoticeSearchSupport;
import com.electerior.g2bmaster.search.SearchQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * 입찰결과(낙찰정보) 검색 — {@code GET /api/bid-result} 의 조회부.
 *
 * <p>입찰공고와 달리 <b>수주기회 스코어링을 하지 않는다</b> — 이미 끝난 입찰이라 "개입 가능성"
 * 같은 개념이 없다. 대신 낙찰업체({@code corpNm}) 필터가 붙는다.
 *
 * <h2>로컬 색인만 본다</h2>
 * <p>예전에는 요청마다 {@code ScsbidInfoService} 세 오퍼레이션(물품·용역·공사)에 검색어 × 날짜창
 * 팬아웃을 걸어 상류를 두드렸다. 지금은 {@link BidResultRepository} 의 {@code bid_result} 만 읽고
 * 나라장터를 부르지 않는다 — 상류 호출은 {@code BidResultIngestService} 가 주기적으로만 한다.
 * 공고 검색이 {@code bid_notice} 색인으로 옮겨 간 것과 같은 전환이고, 이유도 같다:
 * 사용자 요청마다 오퍼레이션 팬아웃을 재현하면 쿼터가 마르고 응답이 상류 장애에 묶인다.
 *
 * <p><b>필터는 그대로 남았다.</b> 라이브 경로는 검색어 일부를 상류 파라미터
 * ({@code bidNtceNm}·{@code dtilPrdctClsfcNoNm}·{@code dminsttNm})로 밀어 후보를 좁힌 뒤,
 * 받아온 것을 다시 haystack 으로 걸렀다. 로컬에는 좁힐 상류가 없으므로 창 안의 후보를 그대로
 * 받아 <b>같은 필터를 같은 순서로</b> 돈다 — 상류 파라미터가 놓치던 건까지 걸리므로 결과는
 * 같거나 넓다.
 *
 * <p>색인이 덮지 못한 기간은 결과가 비어 나온다. 어디까지 적재됐는지는
 * {@code GET /api/search/notices/status} 의 {@code bid-result:*} 상태 행에 있고, 과거 구간은
 * {@code POST /api/bid-result/sync?backfillDays=N} 으로 채운다.
 */
@Service
public class BidResultService {

	private final BidResultRepository repository;
	private final SearchResultCache cache;

	public BidResultService(BidResultRepository repository, SearchResultCache cache) {
		this.repository = repository;
		this.cache = cache;
	}

	/**
	 * 캐시는 그대로 둔다.
	 *
	 * <p>상류 쿼터를 아끼려던 장치가 이제는 <b>DB 부하</b>를 아낀다. 기간을 넓게 잡은 조회는
	 * 수천 행을 힙에 올려 haystack 필터를 행마다 돌므로, 페이지를 넘길 때마다 그것을 다시 하는
	 * 것은 상류를 다시 치는 것만큼이나 아깝다({@link SearchCriteria#cacheParams} 가 페이징·정렬을
	 * 키에서 빼는 이유와 같다).
	 */
	public SearchResultCache.Cached<List<Map<String, Object>>> search(SearchCriteria criteria, String corpNm) {
		String corp = corpNm == null ? "" : corpNm.trim();
		String key = NoticeSearchSupport.buildSearchCacheKey("bid-result", criteria.cacheParams("corpNm", corp));
		return cache.getOrFetch(key, () -> fetch(criteria, corp));
	}

	private List<Map<String, Object>> fetch(SearchCriteria criteria, String corpNm) {
		DateWindow window = criteria.dates();
		LocalDateTime from = parse(window.from());
		// 창 끝은 23:59 로 채워져 들어온다. rgst_dt 는 DATETIME(6) 이라 그대로 상한에 쓰면
		// 그 뒤 59초에 등록된 건이 통째로 빠진다 — 한 칸 밀어 배타 상한으로 넘긴다.
		LocalDateTime toExclusive = parse(window.to()).plusMinutes(1);

		// 라이브 경로가 오퍼레이션 URL 을 고르던 자리다. 로컬에서는 그 선택이 bid_type 필터가 된다.
		List<String> types = G2bEndpoints.BID_TYPES.stream()
				.filter(type -> G2bEndpoints.bidTypeMatches(type, criteria.type()))
				.toList();

		List<Map<String, Object>> fetched = repository.findWindow(from, toExclusive, types,
				BidResultRepository.MAX_ROWS);

		// 낙찰정보는 정정 차수 개념이 없어 공고번호 하나로 접는다. 저장 키는 (공고번호, 업종)이라
		// 한 공고가 두 업종으로 들어와 있을 수 있고, 그때는 먼저 만난 것을 남긴다(라이브와 같다).
		Set<String> seen = new LinkedHashSet<>();
		List<String> andTerms = criteria.and();
		List<String> orTerms = criteria.or();
		List<String> notTerms = criteria.not();
		String corpLower = corpNm.toLowerCase(Locale.ROOT);

		List<Map<String, Object>> filtered = new ArrayList<>();
		for (Map<String, Object> item : fetched) {
			if (!seen.add(str(item.get("bidNtceNo")))) {
				continue;
			}
			String haystack = criteria.itemSearch()
					? BidEnrichment.bidItemHaystack(item)
					: BidEnrichment.bidSearchHaystack(item);
			if (!SearchQuery.matchesQuery(haystack, andTerms, orTerms, notTerms)) {
				continue;
			}
			if (!NoticeFetchSupport.institutionMatches(item, criteria.insttNm())) {
				continue;
			}
			if (!corpLower.isEmpty() && !corpFields(item).contains(corpLower)) {
				continue;
			}
			filtered.add(item);
		}
		return NoticeFetchSupport.filterByBidType(filtered, criteria.type());
	}

	// ── 내부 ────────────────────────────────────────────────────────────────

	/**
	 * 조회 창 문자열({@code yyyyMMddHHmm})을 시각으로 바꾼다.
	 *
	 * <p>{@link G2bDates#toG2bDt} 는 하이픈만 떼고 형식을 검증하지 않는다 — {@code 2026-8-6}
	 * 같은 입력이 {@code 2026860000} 이 되어 여기서 터진다. 라이브 경로에서는 그 값이 상류로
	 * 나가 오류 응답이 됐지만 로컬에서는 파싱 예외라 500 이 된다. 잘못된 것은 요청이므로
	 * 400 으로 돌려준다.
	 */
	private static LocalDateTime parse(String g2bDt) {
		LocalDateTime parsed;
		try {
			parsed = G2bDates.parseG2bDt(g2bDt);
		}
		catch (RuntimeException ex) {
			parsed = null;
		}
		if (parsed == null) {
			throw new ApiException(HttpStatus.BAD_REQUEST,
					"조회 기간을 읽을 수 없습니다: %s (YYYY-MM-DD 형식으로 주세요)".formatted(g2bDt));
		}
		return parsed;
	}

	private static String corpFields(Map<String, Object> item) {
		StringBuilder sb = new StringBuilder();
		for (String key : new String[] {"sucsfbidCorpNm", "bidwinnrNm", "corpNm"}) {
			String value = str(item.get(key));
			if (!value.isEmpty()) {
				sb.append(value).append(' ');
			}
		}
		return sb.toString().toLowerCase(Locale.ROOT);
	}

	private static String str(Object value) {
		return value == null ? "" : String.valueOf(value);
	}
}
