package com.electerior.g2bmaster.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.yml 의 {@code g2b.*} 설정을 한 곳에 묶는다.
 *
 * <p>기존 모놀리스는 {@code process.env} 를 모듈마다 직접 읽어서, 어떤 환경변수가
 * 필수인지 코드를 전부 뒤져야 알 수 있었다. 여기서는 타입이 붙은 한 덩어리로 만들어
 * 기동 시점에 바인딩 오류가 드러나게 한다.
 */
@ConfigurationProperties(prefix = "g2b")
public record G2bProperties(
		OpenApi openapi,
		D2b d2b,
		Ai ai,
		Cors cors,
		Security security,
		Alert alert,
		Sync sync) {

	/** 나라장터 OpenAPI (조달청 공공데이터). */
	public record OpenApi(
			String serviceKey,
			String baseUrl,
			int timeoutMs,
			int maxRetries,
			int pageSize) {}

	/** 국방전자조달(D2B) OpenAPI. */
	public record D2b(String serviceKey, String baseUrl, int timeoutMs) {}

	/** g2bmaster-AI 저장소로 넘기는 추론 호출. */
	public record Ai(String baseUrl, int timeoutMs, boolean enabled) {}

	/** 프론트가 별도 오리진에서 뜨므로 필요한 CORS 허용 목록. */
	public record Cors(List<String> allowedOrigins) {}

	/** 운영/디버그 엔드포인트를 가리는 공유 비밀값. */
	public record Security(String debugSecret, String alertSecret) {}

	/** 키워드 알림 메일. */
	public record Alert(String from, String to, String keywords, String publicBaseUrl) {

		/** 콤마로 구분된 키워드 문자열을 목록으로 편다. */
		public List<String> keywordList() {
			if (keywords == null || keywords.isBlank()) {
				return List.of();
			}
			return List.of(keywords.split(",")).stream()
					.map(String::trim)
					.filter(s -> !s.isEmpty())
					.toList();
		}
	}

	/** 나라장터 주기 동기화 스케줄러 on/off. */
	public record Sync(boolean enabled) {}
}
