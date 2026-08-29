package com.electerior.g2bmaster.beta;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * 접수 1건을 구글 시트에 함께 남긴다. 운영자가 실제로 보는 접수 목록이 그 시트라서,
 * DB 에만 들어가고 시트에 없으면 아무도 그 신청을 모른다.
 *
 * <p><b>DB 가 원본이고 시트는 사본이다.</b> 그래서 이 클래스는 <b>예외를 던지지 않는다</b> —
 * 호출부가 커밋 뒤 콜백이라 여기서 던지면 이미 저장이 끝난 신청에 대해 사용자에게 실패가
 * 나가고, 사용자는 다시 누른다. 시트가 죽어 있으면 로그만 남기고 접수는 성공시킨다.
 * 놓친 행은 {@code beta_signup} 테이블과 시트를 대조해 사람이 메울 수 있다.
 *
 * <p>계약은 랜딩이 쓰던 것과 같다({@code beta-landing/docs/beta-signup.gs}). Apps Script 는
 * <b>HTTP 상태를 항상 200 으로 주고 결과를 본문에 담는다</b> — {@code {"ok":true}} 또는
 * {@code {"error":"…","code":"DUPLICATE_EMAIL"}}. 그래서 상태 코드만 보면 실패를 못 본다.
 *
 * <p>{@code DUPLICATE_EMAIL} 은 여기서 오류로 취급하되 경고까지만 올린다. 정상적인 경우가
 * 있기 때문이다 — 시트에 먼저 접수된 기존 신청자({@code accepted-baseline})는 DB 에 없어서
 * 우리 중복 검사를 통과한다. 그 사람이 다시 신청하면 DB 에는 들어가고 시트는 거절한다.
 */
@Component
public class BetaSheetClient {

	private static final Logger log = LoggerFactory.getLogger(BetaSheetClient.class);

	private final RestClient client;
	private final BetaSheetProperties properties;

	public BetaSheetClient(RestClient betaSheetRestClient, BetaSheetProperties properties) {
		this.client = betaSheetRestClient;
		this.properties = properties;
	}

	/** 실패해도 던지지 않는다. 위 클래스 주석 참고. */
	public void append(Signup signup) {
		if (!properties.enabled()) {
			log.debug("구글 시트 연동이 꺼져 있어 접수를 시트에 남기지 않는다 (g2b.beta.sheet.url 없음)");
			return;
		}

		try {
			Map<?, ?> body = client.post()
					.uri(properties.url())
					.contentType(MediaType.APPLICATION_JSON)
					.body(payload(signup))
					.retrieve()
					.body(Map.class);

			if (body == null || !Boolean.TRUE.equals(body.get("ok"))) {
				log.warn("구글 시트가 접수를 거절했다 (requestId={}, code={}): {}",
						signup.requestId(),
						body == null ? null : body.get("code"),
						body == null ? "빈 응답" : body.get("error"));
				return;
			}
			log.info("구글 시트에 접수를 남겼다 (requestId={})", signup.requestId());
		}
		catch (Exception e) {
			// DB 에는 이미 들어갔다. 사람이 대조해 메울 수 있도록 식별자를 남긴다.
			log.error("구글 시트 적재 실패 — DB 접수는 유효하다 (requestId={}, email={})",
					signup.requestId(), signup.email(), e);
		}
	}

	/**
	 * 시트가 읽는 필드 그대로. 접수시각은 보내지 않는다 — Apps Script 가 자기 시계로 찍는다.
	 * {@code website}(허니팟)는 넣지 않는다. 값이 있으면 스크립트가 저장하지 않고 성공만 돌려준다.
	 */
	private static Map<String, Object> payload(Signup signup) {
		Map<String, Object> payload = new LinkedHashMap<>();
		payload.put("name", signup.name());
		payload.put("phone", signup.phone());
		payload.put("organization", signup.organization());
		payload.put("industry", signup.industry());
		payload.put("email", signup.email());
		payload.put("privacyAgreed", true);
		payload.put("requestId", signup.requestId());
		return payload;
	}

	/**
	 * 시트로 넘길 값. 서비스가 정규화를 끝낸 뒤의 값이라 여기서 다시 다듬지 않는다.
	 * {@code requestId} 는 비면 안 된다 — 시트 쪽 재시도 판정의 유일한 열쇠다.
	 */
	public record Signup(
			String requestId, String name, String organization, String industry, String email, String phone) {
	}
}
