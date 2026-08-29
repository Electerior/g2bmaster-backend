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
			20, 20, 6, OffsetDateTime.parse("2026-08-31T23:59:59+09:00"));

	private BetaSignupRepository repository;
	private BetaSheetClient sheet;
	private BetaSignupService service;

	@BeforeEach
	void setUp() {
		repository = mock(BetaSignupRepository.class);
		sheet = mock(BetaSheetClient.class);
		service = new BetaSignupService(repository, PROPERTIES, sheet, CLOCK);
	}

	@Test
	void 현황은_전체_정원에서_기존_시트와_DB_접수_건수를_뺀다() {
		when(repository.count()).thenReturn(3);

		BetaSignupService.BetaStatus status = service.status();

		assertThat(status.total()).isEqualTo(20);
		assertThat(status.remaining()).isEqualTo(11);
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
		when(repository.count()).thenReturn(14);

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

	@Test
	void 저장된_접수는_구글_시트에도_넘긴다() {
		when(repository.existsByEmail("hello@example.com")).thenReturn(false);

		service.signup(request(" HELLO@Example.com "));

		ArgumentCaptor<BetaSheetClient.Signup> forwarded =
				ArgumentCaptor.forClass(BetaSheetClient.Signup.class);
		verify(sheet).append(forwarded.capture());
		// 시트로 가는 값은 DB 에 넣은 것과 같아야 한다 — 정규화 전 원문이 아니다.
		assertThat(forwarded.getValue().email()).isEqualTo("hello@example.com");
		assertThat(forwarded.getValue().requestId()).isNotBlank();
	}

	@Test
	void 저장하지_않은_요청은_시트에도_넘기지_않는다() {
		// 허니팟: 저장도 전달도 없다. 봇 트래픽으로 시트를 두드리지 않기 위해서다.
		service.signup(new BetaSignupRequest(
				"이름", "소속", "업종", "hello@example.com", "010-0000-0000", true, "봇", null));
		verifyNoInteractions(sheet);

		// 같은 requestId 재시도: 이미 넣은 행이라 시트도 다시 부르지 않는다.
		when(repository.existsByRequestId("req-1")).thenReturn(true);
		service.signup(new BetaSignupRequest(
				"이름", "소속", "업종", "hello@example.com", "010-0000-0000", true, null, "req-1"));
		verifyNoInteractions(sheet);

		// 중복 이메일: 저장이 없으니 전달도 없다.
		when(repository.existsByEmail("hello@example.com")).thenReturn(true);
		assertThatThrownBy(() -> service.signup(request("hello@example.com")))
				.isInstanceOf(ApiException.class);
		verifyNoInteractions(sheet);
	}
}
