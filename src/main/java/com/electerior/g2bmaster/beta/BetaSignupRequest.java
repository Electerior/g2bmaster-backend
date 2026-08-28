package com.electerior.g2bmaster.beta;

/** 공개 베타 신청 폼. 검증은 허니팟을 먼저 판정해야 하므로 서비스에서 수행한다. */
public record BetaSignupRequest(
		String name,
		String organization,
		String industry,
		String email,
		String phone,
		Boolean privacyAgreed,
		String website,
		String requestId) {
}
