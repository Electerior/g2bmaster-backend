package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@code excludeStateValue()} 의 우선순위 규칙만 본다 — 나머지 접근자는 전부
 * {@code stateValue()} 와 같은 패턴이라 여기서 새로 볼 것이 없다.
 */
class NoticeSearchRequestTest {

	private static NoticeSearchRequest request(String state, String excludeState) {
		return new NoticeSearchRequest(null, null, null, null, null, state, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				excludeState);
	}

	@Test
	@DisplayName("state 없이 excludeState 만 오면 그대로 값이 된다")
	void excludeStateAloneApplies() {
		assertThat(request(null, "취소").excludeStateValue()).isEqualTo(NoticeState.취소);
	}

	@Test
	@DisplayName("state 가 오면 excludeState 는 무시한다 — 프론트가 둘을 동시에 보내지 않으므로 안전한 기본값이다")
	void stateWinsOverExcludeState() {
		assertThat(request("입찰", "취소").excludeStateValue()).isNull();
	}

	@Test
	@DisplayName("둘 다 없으면 null")
	void neitherGivenIsNull() {
		assertThat(request(null, null).excludeStateValue()).isNull();
	}
}
