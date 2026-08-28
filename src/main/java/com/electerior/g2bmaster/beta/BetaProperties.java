package com.electerior.g2bmaster.beta;

import java.time.OffsetDateTime;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** 베타 모집 회차의 공개 표시값과 실제 접수 정원. */
@ConfigurationProperties(prefix = "g2b.beta")
public record BetaProperties(int total, int capacity, OffsetDateTime deadline) {

	public BetaProperties {
		if (total < 1 || capacity < 1 || capacity > total) {
			throw new IllegalArgumentException("베타 모집 인원 설정이 올바르지 않습니다.");
		}
		if (deadline == null) {
			throw new IllegalArgumentException("베타 모집 마감 시각이 필요합니다.");
		}
	}
}
