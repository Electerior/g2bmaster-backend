package com.electerior.g2bmaster.beta;

import java.time.OffsetDateTime;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** 베타 모집 회차의 공개 표시값, 실제 접수 정원과 외부 저장소의 기존 접수 건수. */
@ConfigurationProperties(prefix = "g2b.beta")
public record BetaProperties(int total, int capacity, int acceptedBaseline, OffsetDateTime deadline) {

	public BetaProperties {
		if (total < 1 || capacity < 1 || capacity > total) {
			throw new IllegalArgumentException("베타 모집 인원 설정이 올바르지 않습니다.");
		}
		if (acceptedBaseline < 0 || acceptedBaseline > capacity) {
			throw new IllegalArgumentException("기존 베타 접수 건수 설정이 올바르지 않습니다.");
		}
		if (deadline == null) {
			throw new IllegalArgumentException("베타 모집 마감 시각이 필요합니다.");
		}
	}
}
