package com.electerior.g2bmaster.market;

import com.electerior.g2bmaster.common.ApiException;
import com.electerior.g2bmaster.config.OpenApiConfig;
import com.electerior.g2bmaster.market.MarketIntelRequests.CollusionRequest;
import com.electerior.g2bmaster.market.MarketIntelRequests.CompanyHistoryRequest;
import com.electerior.g2bmaster.market.MarketIntelRequests.OfficerSearchRequest;
import com.electerior.g2bmaster.market.MarketIntelRequests.OpeningResultRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 시장정보 4종 ({@code docs/api-contract.md} §2.C).
 *
 * <p>전부 POST 인 것은 REST 의미론이 아니라 <b>요청 본문에 공고 객체 배열이 실리기 때문</b>이다
 * (담합 분석은 최대 20건). 원본 계약이 그러하므로 GET 으로 바꾸지 않는다.
 *
 * <p>검증 실패 문구는 <b>사용자 대상 계약</b>이다 — 화면에 그대로 렌더링되므로 다듬지 않는다.
 *
 * <p>{@code POST /api/deal-analysis} 는 여기 없다. 첨부 파싱과 단가 추정이 아직 이식되지
 * 않아 의미 있는 응답을 만들 수 없기 때문이고, 껍데기만 두면 "구현됐는데 값이 이상하다"로
 * 오해된다. {@code pricing.DealCalculator} · {@code pricing.MarketPriceService} 는 이미
 * 준비돼 있으니, 첨부 파싱이 들어오는 시점에 그 둘을 엮으면 된다.
 */
@RestController
@RequestMapping("/api")
@Tag(name = OpenApiConfig.TAG_MARKET)
public class MarketIntelController {

	private final MarketIntelService service;

	public MarketIntelController(MarketIntelService service) {
		this.service = service;
	}

	/**
	 * 공고 하나의 개찰결과(참여업체별 투찰금액).
	 *
	 * <p>미공개·미지원이면 <b>오류가 아니라 빈 배열</b>이다. 개찰 전 공고를 열어 본 것뿐이라
	 * 사용자가 잘못한 것이 없다.
	 */
	@Operation(summary = "개찰결과 (참여업체별 투찰금액)",
			description = "미공개·미지원이면 오류가 아니라 빈 배열이다. 개찰 전 공고를 열어 본 것뿐이다.")
	@PostMapping("/bid-opening-results")
	public Map<String, Object> openingResults(@RequestBody(required = false) OpeningResultRequest request) {
		if (request == null || request.bidNtceNo() == null || request.bidNtceNo().isBlank()) {
			throw ApiException.badRequest("bidNtceNo 필수");
		}
		List<Map<String, Object>> participants = service.fetchOpeningResults(
				request.bidNtceNo(), request.bidNtceSqNo(), request.typeOrDefault());

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("bidNtceNo", request.bidNtceNo());
		response.put("bidNtceSqNo", request.bidNtceSqNo());
		response.put("participants", participants);
		return response;
	}

	/** 업체 낙찰·참여 이력과 투찰률 추세. 업체명 또는 사업자번호 중 하나는 필수. */
	@Operation(summary = "업체 낙찰·참여 이력",
			description = "업체명(`corpNm`) 또는 사업자번호(`brnNo`) 중 하나는 필수. 투찰률 추세를 함께 준다.")
	@PostMapping("/company-history")
	public Map<String, Object> companyHistory(@RequestBody(required = false) CompanyHistoryRequest request) {
		if (request == null || (isBlank(request.corpNm()) && isBlank(request.brnNo()))) {
			throw ApiException.badRequest("업체명 또는 사업자번호 필요");
		}
		return service.companyHistory(request);
	}

	/** 발주기관 담당자별 공고 묶음. */
	@Operation(summary = "발주기관 담당자별 공고 묶음", description = "발주기관명(`insttNm`) 필수.")
	@PostMapping("/officer-search")
	public Map<String, Object> officerSearch(@RequestBody(required = false) OfficerSearchRequest request) {
		if (request == null || isBlank(request.insttNm())) {
			throw ApiException.badRequest("발주기관명 필요");
		}
		return service.officerSearch(request);
	}

	/** 담합 정황 매트릭스. 최대 {@value MarketIntelService#MAX_COLLUSION_BIDS} 건까지 처리한다. */
	@Operation(summary = "담합 정황 매트릭스",
			description = "본문에 공고 배열을 싣는다(그래서 GET 이 아니다). 최대 20건까지 처리한다.")
	@PostMapping("/collusion-analysis")
	public Map<String, Object> collusionAnalysis(@RequestBody(required = false) CollusionRequest request) {
		if (request == null || request.bids() == null || request.bids().isEmpty()) {
			throw ApiException.badRequest("bids 배열 필수");
		}
		return service.collusionAnalysis(request.bids());
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
