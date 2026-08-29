package com.electerior.g2bmaster.index;

import com.electerior.g2bmaster.common.ApiException;
import com.electerior.g2bmaster.config.OpenApiConfig;
import com.electerior.g2bmaster.security.RequireAppAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 낙찰정보 색인 수동 적재.
 *
 * <p>{@code GET /api/bid-result}(조회)는 {@code NoticeController} 에 있고 이쪽은 적재만 맡는다 —
 * 조회는 로컬 DB 만 보는 무료 경로이고 적재는 나라장터 쿼터를 태우는 경로라, 인증도 태그도
 * 달라야 하는 서로 다른 물건이다.
 *
 * <p>이 엔드포인트가 <b>필요한</b> 이유는 색인이 첫 회차에 최근 7일치만 채우기 때문이다.
 * 그 전 기간은 조회하면 빈 결과가 나오므로, 과거를 보려면 한 번은 거슬러 올라가 채워야 한다.
 * 되감은 워터마크는 회차마다 {@link BidResultIngestService#MAX_ROWS_PER_RUN} 만큼 전진하며
 * 스스로 따라잡으므로, 5년을 지시해도 이 호출 하나가 몇 시간 붙잡혀 있지는 않는다 —
 * 진행 상황은 {@code GET /api/search/notices/status} 의 {@code bid-result:*} 행에 남는다.
 */
@RestController
@RequestMapping("/api/bid-result")
@Tag(name = OpenApiConfig.TAG_INDEX_SEARCH)
public class BidResultSyncController {

	private final BidResultIngestService ingestService;

	public BidResultSyncController(BidResultIngestService ingestService) {
		this.ingestService = ingestService;
	}

	/**
	 * 수동 적재.
	 *
	 * <p>쓰기이자 비용(나라장터 쿼터)이 드는 경로라 앱 키를 요구한다. 초기 구축과 장애 복구용이고,
	 * 평시에는 스케줄러가 알아서 돈다.
	 */
	@Operation(summary = "낙찰정보 색인 수동 적재",
			description = "나라장터 낙찰정보(ScsbidInfoService)를 즉시 받아와 bid_result 색인에 넣는다. "
					+ "backfillDays 로 거슬러 올라갈 기간을 지정한다(0 이면 평시 증분). "
					+ "한 회차의 상한(20,000건)에 걸리면 훑은 곳까지만 워터마크를 전진시키고 "
					+ "다음 회차가 이어 받는다 — 기간을 크게 잡아도 이 호출은 한 회차만 돌고 돌아온다.\n\n"
					+ "이미 적재가 돌고 있으면 409 를 돌려준다.")
	@PostMapping("/sync")
	@RequireAppAuth
	public Map<String, Object> sync(
			@RequestParam(name = "backfillDays", required = false, defaultValue = "0") int backfillDays) {
		BidResultIngestService.IngestResult result = ingestService.runNow(backfillDays);
		if (result == null) {
			throw new ApiException(HttpStatus.CONFLICT,
					"이미 낙찰정보 적재가 진행 중입니다. 잠시 후 다시 시도하세요.");
		}
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("ok", true);
		body.put("totalIndexed", result.totalIndexed());
		body.put("sources", result.sources());
		return body;
	}
}
