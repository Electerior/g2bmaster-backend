package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 심야 게이팅의 시각 판정.
 *
 * <p>자정을 넘는 구간(23~07)이 기본값이라, 이 경계를 반대로 읽으면 <b>업무시간에 적재가 멈추고
 * 심야에만 도는</b> 정반대 사고가 난다. 눈으로는 잘 안 보이는 종류라 표로 고정한다.
 */
class BidNoticeSyncNightGateTest {

	@ParameterizedTest
	@ValueSource(ints = {23, 0, 3, 6})
	@DisplayName("23~07 구간은 자정을 넘어 이어진다")
	void 자정을_넘는_구간(int hour) {
		assertThat(BidNoticeSyncScheduler.isNight(hour, 23, 7)).isTrue();
	}

	@ParameterizedTest
	@ValueSource(ints = {7, 9, 14, 18, 22})
	@DisplayName("업무시간은 심야가 아니다 — 경계 07시와 22시가 특히 중요하다")
	void 업무시간(int hour) {
		assertThat(BidNoticeSyncScheduler.isNight(hour, 23, 7)).isFalse();
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 3, 4})
	@DisplayName("자정을 넘지 않는 구간도 그대로 동작한다")
	void 자정을_안_넘는_구간(int hour) {
		assertThat(BidNoticeSyncScheduler.isNight(hour, 1, 5)).isTrue();
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 5, 12, 23})
	@DisplayName("from 과 to 가 같으면 게이팅을 끈 것이다 — 하루 종일 심야로 읽으면 적재가 통째로 멈춘다")
	void 같은_값이면_게이팅_없음(int hour) {
		assertThat(BidNoticeSyncScheduler.isNight(hour, 0, 0)).isFalse();
	}
}
