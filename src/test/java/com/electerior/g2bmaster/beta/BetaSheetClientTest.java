package com.electerior.g2bmaster.beta;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

/**
 * 시트 적재의 계약을 고정한다.
 *
 * <p>여기서 지키는 것은 두 가지다 — 시트가 읽는 필드 이름과, <b>실패가 새어 나가지 않는다</b>는
 * 성질. 두 번째가 특히 중요하다. 이 호출은 커밋 뒤에 일어나므로 여기서 예외가 나가면 이미
 * 저장이 끝난 신청자에게 실패 화면이 뜨고, 그 사람은 다시 신청한다.
 */
class BetaSheetClientTest {

	private static final String URL = "https://example.test/macros/s/deployment/exec";

	private MockRestServiceServer server;
	private BetaSheetClient client;

	@BeforeEach
	void setUp() {
		RestClient.Builder builder = RestClient.builder();
		server = MockRestServiceServer.bindTo(builder).build();
		client = new BetaSheetClient(builder.build(), new BetaSheetProperties(URL, null));
	}

	@Test
	void 시트가_읽는_필드_이름_그대로_보낸다() {
		server.expect(requestTo(URL))
				.andExpect(method(HttpMethod.POST))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("홍길동"))
				.andExpect(jsonPath("$.phone").value("010-0000-0000"))
				.andExpect(jsonPath("$.organization").value("테스트회사"))
				.andExpect(jsonPath("$.industry").value("IT장비 납품"))
				.andExpect(jsonPath("$.email").value("hello@example.com"))
				.andExpect(jsonPath("$.privacyAgreed").value(true))
				.andExpect(jsonPath("$.requestId").value("req-1"))
				// 허니팟을 실어 보내면 스크립트가 저장하지 않고 성공만 돌려준다 — 조용한 유실이다.
				.andExpect(jsonPath("$.website").doesNotExist())
				.andRespond(withSuccess(
						"{\"ok\":true,\"receivedAt\":\"2026-08-29T03:00:00.000Z\"}",
						MediaType.APPLICATION_JSON));

		client.append(signup());

		server.verify();
	}

	@Test
	void 시트가_본문으로_거절해도_던지지_않는다() {
		// Apps Script 는 오류도 200 으로 준다. 상태 코드만 보면 실패를 못 본다.
		server.expect(requestTo(URL)).andRespond(withSuccess(
				"{\"error\":\"이미 신청된 이메일입니다.\",\"code\":\"DUPLICATE_EMAIL\"}",
				MediaType.APPLICATION_JSON));

		assertThatCode(() -> client.append(signup())).doesNotThrowAnyException();

		server.verify();
	}

	@Test
	void 시트가_죽어도_던지지_않는다() {
		server.expect(requestTo(URL)).andRespond(withServerError());

		assertThatCode(() -> client.append(signup())).doesNotThrowAnyException();

		server.verify();
	}

	@Test
	void URL_이_없으면_아무_데도_보내지_않는다() {
		RestClient.Builder builder = RestClient.builder();
		MockRestServiceServer idle = MockRestServiceServer.bindTo(builder).build();
		BetaSheetClient disabled = new BetaSheetClient(builder.build(), new BetaSheetProperties(" ", null));

		assertThatCode(() -> disabled.append(signup())).doesNotThrowAnyException();

		idle.verify(); // 기대한 호출이 없고 실제 호출도 없어야 통과한다
	}

	private static BetaSheetClient.Signup signup() {
		return new BetaSheetClient.Signup(
				"req-1", "홍길동", "테스트회사", "IT장비 납품", "hello@example.com", "010-0000-0000");
	}
}
