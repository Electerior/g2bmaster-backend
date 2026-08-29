package com.electerior.g2bmaster.beta;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.electerior.g2bmaster.beta.BetaSignupService.BetaSignupResponse;
import com.electerior.g2bmaster.beta.BetaSignupService.BetaStatus;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class BetaSignupControllerTest {

	private BetaSignupService service;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		service = mock(BetaSignupService.class);
		mockMvc = MockMvcBuilders.standaloneSetup(new BetaSignupController(service)).build();
	}

	@Test
	void 공개_모집_현황_계약을_그대로_내린다() throws Exception {
		when(service.status()).thenReturn(new BetaStatus(
				20, 12, OffsetDateTime.parse("2026-08-31T23:59:59+09:00"), true));

		mockMvc.perform(get("/api/beta/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.total").value(20))
				.andExpect(jsonPath("$.remaining").value(12))
				.andExpect(jsonPath("$.open").value(true));
	}

	@Test
	void 공개_신청은_인증_헤더_없이_접수된다() throws Exception {
		when(service.signup(any())).thenReturn(new BetaSignupResponse(
				true, OffsetDateTime.parse("2026-08-28T11:30:00+09:00")));

		mockMvc.perform(post("/api/beta/signups")
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"name":"홍길동","organization":"테스트회사","industry":"IT장비 납품",
							 "email":"hello@example.com","phone":"010-0000-0000","privacyAgreed":true}
							"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.ok").value(true))
				.andExpect(jsonPath("$.receivedAt").exists());
	}
}
