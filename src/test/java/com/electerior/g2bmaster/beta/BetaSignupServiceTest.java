package com.electerior.g2bmaster.beta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.electerior.g2bmaster.beta.BetaSignupRepository.BetaSignup;
import com.electerior.g2bmaster.common.ApiException;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class BetaSignupServiceTest {

	private static final Clock CLOCK = Clock.fixed(
			Instant.parse("2026-08-28T02:30:00Z"), ZoneId.of("Asia/Seoul"));
	private static final BetaProperties PROPERTIES = new BetaProperties(
			20, 12, OffsetDateTime.parse("2026-08-31T23:59:59+09:00"));

	private BetaSignupRepository repository;
	private BetaSignupService service;

	@BeforeEach
	void setUp() {
		repository = mock(BetaSignupRepository.class);
		service = new BetaSignupService(repository, PROPERTIES, CLOCK);
	}

	@Test
	void 현황은_실제_접수_정원에서_저장된_건수를_뺀다() {
		when(repository.count()).thenReturn(3);

		BetaSignupService.BetaStatus status = service.status();

		assertThat(status.total()).isEqualTo(20);
		assertThat(status.remaining()).isEqualTo(9);
		assertThat(status.open()).isTrue();
	}

	@Test
	void 공개_신청을_정규화해_저장한다() {
		when(repository.existsByRequestId("request-1")).thenReturn(false);
		when(repository.existsByEmail("hello@example.com")).thenReturn(false);
		when(repository.count()).thenReturn(0);

		BetaSignupService.BetaSignupResponse response = service.signup(request(" HELLO@Example.com "));

		assertThat(response.ok()).isTrue();
		ArgumentCaptor<BetaSignup> saved = ArgumentCaptor.forClass(BetaSignup.class);
		verify(repository).lockSignupGuard();
		verify(repository).insert(saved.capture());
		assertThat(saved.getValue().email()).isEqualTo("hello@example.com");
		assertThat(saved.getValue().industry()).isEqualTo("IT장비 납품");
	}

	@Test
	void 같은_요청ID는_응답이_유실된_재시도로_보고_성공한다() {
		when(repository.existsByRequestId("request-1")).thenReturn(true);

		assertThat(service.signup(request("hello@example.com")).ok()).isTrue();

		verify(repository, never()).insert(any());
		verify(repository, never()).existsByEmail(any());
	}

	@Test
	void 다른_요청의_중복_이메일은_409로_막는다() {
		when(repository.existsByRequestId("request-1")).thenReturn(false);
		when(repository.existsByEmail("hello@example.com")).thenReturn(true);

		assertThatThrownBy(() -> service.signup(request("hello@example.com")))
				.isInstanceOfSatisfying(ApiException.class, error -> {
					assertThat(error.getStatus().value()).isEqualTo(409);
					assertThat(error.getCode()).isEqualTo("DUPLICATE_EMAIL");
				});
	}

	@Test
	void 정원이_차면_DB_잠금_안에서_409로_막는다() {
		when(repository.existsByRequestId("request-1")).thenReturn(false);
		when(repository.existsByEmail("hello@example.com")).thenReturn(false);
		when(repository.count()).thenReturn(12);

		assertThatThrownBy(() -> service.signup(request("hello@example.com")))
				.isInstanceOfSatisfying(ApiException.class,
						error -> assertThat(error.getCode()).isEqualTo("BETA_CLOSED"));
		verify(repository, never()).insert(any());
	}

	@Test
	void 허니팟이_차면_저장하지_않고_성공처럼_응답한다() {
		BetaSignupRequest bot = new BetaSignupRequest(
				null, null, null, null, null, false, "https://spam.example", null);

		assertThat(service.signup(bot).ok()).isTrue();
		verifyNoInteractions(repository);
	}

	private static BetaSignupRequest request(String email) {
		return new BetaSignupRequest(
				" 홍길동 ", " 테스트회사 ", " IT장비 납품 ", email,
				" 010-0000-0000 ", true, "", "request-1");
	}
}
