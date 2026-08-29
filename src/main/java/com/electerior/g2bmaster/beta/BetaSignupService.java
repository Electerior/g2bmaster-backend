package com.electerior.g2bmaster.beta;

import com.electerior.g2bmaster.beta.BetaSignupRepository.BetaSignup;
import com.electerior.g2bmaster.common.ApiException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class BetaSignupService {

	static final int MAX_FIELD_LENGTH = 200;
	private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
	private static final ZoneId KST = ZoneId.of("Asia/Seoul");

	private final BetaSignupRepository repository;
	private final BetaProperties properties;
	private final BetaSheetClient sheet;
	private final Clock clock;

	@Autowired
	public BetaSignupService(BetaSignupRepository repository, BetaProperties properties, BetaSheetClient sheet) {
		this(repository, properties, sheet, Clock.system(KST));
	}

	BetaSignupService(
			BetaSignupRepository repository, BetaProperties properties, BetaSheetClient sheet, Clock clock) {
		this.repository = repository;
		this.properties = properties;
		this.sheet = sheet;
		this.clock = clock;
	}

	@Transactional(readOnly = true)
	public BetaStatus status() {
		return status(repository.count(), OffsetDateTime.now(clock));
	}

	@Transactional
	public BetaSignupResponse signup(BetaSignupRequest request) {
		OffsetDateTime receivedAt = OffsetDateTime.now(clock);

		// 봇에게 허니팟 적중 사실을 알려주지 않는다. 저장하지 않고 성공처럼 끝낸다.
		if (request != null && !trim(request.website()).isEmpty()) {
			return new BetaSignupResponse(true, receivedAt);
		}

		NormalizedSignup signup = normalizeAndValidate(request);
		repository.lockSignupGuard();

		// 응답만 유실된 같은 요청은 마감·정원 판정보다 먼저 성공으로 돌린다.
		if (repository.existsByRequestId(signup.requestId())) {
			return new BetaSignupResponse(true, receivedAt);
		}
		if (repository.existsByEmail(signup.email())) {
			throw ApiException.conflict(
					"이미 신청된 이메일입니다. 결과 안내를 기다려 주세요.", "DUPLICATE_EMAIL");
		}

		BetaStatus status = status(repository.count(), receivedAt);
		if (!status.open()) {
			throw new ApiException(HttpStatus.CONFLICT,
					"베타 모집이 마감되었습니다.", "BETA_CLOSED");
		}

		repository.insert(new BetaSignup(
				signup.requestId(), signup.name(), signup.phone(), signup.organization(),
				signup.industry(), signup.email(), LocalDateTime.ofInstant(receivedAt.toInstant(), clock.getZone())));

		// 운영자가 보는 접수 목록은 구글 시트다. 여기서 놓치면 신청은 저장돼도 아무도 모른다.
		// 저장이 실제로 일어난 이 자리에서만 부른다 — 허니팟·재시도·중복은 위에서 이미 돌아갔다.
		afterCommit(() -> sheet.append(new BetaSheetClient.Signup(
				signup.requestId(), signup.name(), signup.organization(),
				signup.industry(), signup.email(), signup.phone())));
		return new BetaSignupResponse(true, receivedAt);
	}

	BetaStatus status(int accepted, OffsetDateTime now) {
		int totalAccepted = properties.acceptedBaseline() + accepted;
		int remaining = Math.max(0, properties.capacity() - totalAccepted);
		boolean open = remaining > 0 && now.isBefore(properties.deadline());
		return new BetaStatus(properties.total(), remaining, properties.deadline(), open);
	}

	private NormalizedSignup normalizeAndValidate(BetaSignupRequest request) {
		if (request == null) {
			throw ApiException.badRequest("요청을 읽지 못했습니다. 잠시 후 다시 시도해 주세요.");
		}

		String name = required(request.name(), "이름을 입력해 주세요.");
		String organization = required(request.organization(), "소속 기관·기업을 입력해 주세요.");
		String industry = required(request.industry(), "업종을 입력해 주세요.");
		String email = required(request.email(), "이메일을 입력해 주세요.").toLowerCase(Locale.ROOT);
		String phone = required(request.phone(), "연락처를 입력해 주세요.");
		if (!EMAIL.matcher(email).matches()) {
			throw ApiException.badRequest("이메일 형식을 확인해 주세요.");
		}
		if (!Boolean.TRUE.equals(request.privacyAgreed())) {
			throw ApiException.badRequest("개인정보 수집·이용 동의가 필요합니다.");
		}

		String requestId = trim(request.requestId());
		if (requestId.isEmpty()) {
			requestId = UUID.randomUUID().toString();
		} else if (requestId.length() > 100) {
			throw ApiException.badRequest("요청 식별자가 너무 깁니다.");
		}
		return new NormalizedSignup(requestId, name, organization, industry, email, phone);
	}

	private static String required(String value, String message) {
		String normalized = trim(value);
		if (normalized.isEmpty()) {
			throw ApiException.badRequest(message);
		}
		if (normalized.length() > MAX_FIELD_LENGTH) {
			throw ApiException.badRequest("입력값이 너무 깁니다. 다시 확인해 주세요.");
		}
		return normalized;
	}

	/**
	 * 커밋된 뒤에 실행한다.
	 *
	 * <p>트랜잭션 안에서 외부 HTTP 를 기다리면 정원 잠금({@code beta_signup_guard} 의
	 * {@code FOR UPDATE})을 쥔 채로 기다리게 되어, 시트가 느린 동안 모든 신청이 한 줄로 선다.
	 * 마지막 한 자리를 다투는 순간에 정확히 그 일이 일어난다.
	 *
	 * <p>동기화가 없는 호출(단위 테스트, 트랜잭션 밖 호출)에서는 그 자리에서 실행한다 —
	 * 조용히 건너뛰면 테스트가 통과하는데 운영에서만 시트가 비는 반대 상황이 된다.
	 */
	private static void afterCommit(Runnable action) {
		if (!TransactionSynchronizationManager.isSynchronizationActive()) {
			action.run();
			return;
		}
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
			@Override
			public void afterCommit() {
				action.run();
			}
		});
	}

	private static String trim(String value) {
		return value == null ? "" : value.trim();
	}

	private record NormalizedSignup(
			String requestId, String name, String organization, String industry, String email, String phone) {
	}

	public record BetaStatus(int total, int remaining, OffsetDateTime deadline, boolean open) {
	}

	public record BetaSignupResponse(boolean ok, OffsetDateTime receivedAt) {
	}
}
