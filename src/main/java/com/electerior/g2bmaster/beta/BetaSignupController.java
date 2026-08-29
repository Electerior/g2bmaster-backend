package com.electerior.g2bmaster.beta;

import com.electerior.g2bmaster.beta.BetaSignupService.BetaSignupResponse;
import com.electerior.g2bmaster.beta.BetaSignupService.BetaStatus;
import com.electerior.g2bmaster.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 로그인 전 랜딩이 쓰는 공개 API. 이 컨트롤러에는 RequireAppAuth를 붙이면 안 된다. */
@RestController
@RequestMapping("/api/beta")
@Tag(name = OpenApiConfig.TAG_BETA)
public class BetaSignupController {

	private final BetaSignupService service;

	public BetaSignupController(BetaSignupService service) {
		this.service = service;
	}

	@Operation(summary = "베타 모집 현황")
	@GetMapping("/status")
	public BetaStatus status() {
		return service.status();
	}

	@Operation(summary = "베타 신청 접수", description = "공개 랜딩 폼의 접수를 저장한다. 앱 API 키를 요구하지 않는다.")
	@PostMapping("/signups")
	public BetaSignupResponse signup(@RequestBody BetaSignupRequest request) {
		return service.signup(request);
	}
}
