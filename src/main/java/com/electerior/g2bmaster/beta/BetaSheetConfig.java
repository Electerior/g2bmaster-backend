package com.electerior.g2bmaster.beta;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * 구글 시트 웹앱을 향한 HTTP 클라이언트.
 *
 * <p><b>이 저장소의 다른 클라이언트와 달리 {@code SimpleClientHttpRequestFactory} 를 쓰지
 * 않는다.</b> Apps Script 의 {@code /exec} 는 POST 를 받아 일을 끝낸 뒤 결과 본문을
 * {@code script.googleusercontent.com/macros/echo} 로 <b>302</b> 시킨다. 그런데
 * {@code SimpleClientHttpRequestFactory} 는 GET 이 아닌 요청에 대해 리다이렉트 추종을
 * 꺼 버리므로(내부적으로 {@code setInstanceFollowRedirects("GET".equals(method))}),
 * 그걸로 보내면 본문 없는 302 만 받고 {@code {"ok":true}} 인지 {@code {"error":…}} 인지
 * 알 수 없게 된다. 행은 이미 들어간 뒤라 조용히 성공처럼 보이는 게 더 나쁘다.
 *
 * <p>JDK 클라이언트의 {@code Redirect.NORMAL} 은 301·302 를 따라갈 때 POST 를 GET 으로
 * 바꿔 준다 — Apps Script 가 기대하는 바로 그 동작이다(2026-08-29 실측: POST → 302 →
 * GET echo → {@code {"ok":true,"receivedAt":…}}). {@code curl -L} 은 메서드를 유지해
 * echo 주소에 POST 하다 405 를 받으므로, 손으로 확인할 때는 curl 결과를 믿지 말 것.
 */
@Configuration
public class BetaSheetConfig {

	@Bean
	RestClient betaSheetRestClient(BetaSheetProperties properties) {
		HttpClient httpClient = HttpClient.newBuilder()
				.followRedirects(HttpClient.Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(5))
				.build();

		JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
		factory.setReadTimeout(properties.timeout());

		return RestClient.builder().requestFactory(factory).build();
	}
}
