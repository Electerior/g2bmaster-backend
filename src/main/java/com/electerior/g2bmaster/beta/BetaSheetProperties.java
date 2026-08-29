package com.electerior.g2bmaster.beta;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 접수를 함께 남길 구글 시트(Apps Script 웹앱)의 좌표.
 *
 * <p>URL 에 기본값을 두지 않는다. 이유가 둘이다 — 이 저장소는 공개이고, 그 주소는 아는 사람
 * 누구나 POST 할 수 있는 쓰기 엔드포인트다(웹앱 접근 권한이 '모든 사용자'여야 로그인 전
 * 랜딩이 부를 수 있다). 그리고 값이 박혀 있으면 CI 와 개발자 노트북이 기동만 해도 운영
 * 시트를 향해 쏘게 된다. 그래서 <b>값이 없으면 연동을 끈다</b> — 실제 URL 은 배포 환경의
 * 실행 디렉터리 오버라이드({@code config/application.yml})나 {@code BETA_SHEET_URL} 에 둔다.
 *
 * @param url     Apps Script 웹앱의 {@code .../exec} 주소. 비면 연동 꺼짐.
 * @param timeout 시트 왕복 한도. 커밋 뒤에 부르므로 이 시간만큼 응답이 늦어질 수 있다.
 */
@ConfigurationProperties(prefix = "g2b.beta.sheet")
public record BetaSheetProperties(String url, Duration timeout) {

	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);

	public BetaSheetProperties {
		if (timeout == null || timeout.isZero() || timeout.isNegative()) {
			timeout = DEFAULT_TIMEOUT;
		}
	}

	public boolean enabled() {
		return url != null && !url.isBlank();
	}
}
