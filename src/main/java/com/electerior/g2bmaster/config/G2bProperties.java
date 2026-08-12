package com.electerior.g2bmaster.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.yml 의 {@code g2b.*} 설정을 한 곳에 묶는다.
 *
 * <p>기존 모놀리스는 {@code process.env} 를 모듈마다 직접 읽어서, 어떤 환경변수가
 * 필수인지 코드를 전부 뒤져야 알 수 있었다. 여기서는 타입이 붙은 한 덩어리로 만들어
 * 기동 시점에 바인딩 오류가 드러나게 한다.
 *
 * <p>⚠ <b>생성자를 하나 더 만들지 말 것.</b> 블록을 추가할 때 기존 호출부(대부분 테스트)를
 * 살리려고 짧은 생성자를 얹고 싶어지는데, {@code @ConfigurationProperties} 의 생성자 바인딩은
 * <b>생성자가 둘이면 어느 것을 쓸지 정하지 못한다</b> — "No default constructor found" 로
 * 기동 자체가 실패한다. 컴파일은 멀쩡히 통과하고 앱만 안 뜨는 종류라 더 나쁘다.
 * 블록이 늘면 호출부를 고치는 것이 맞다.
 */
@ConfigurationProperties(prefix = "g2b")
public record G2bProperties(
		OpenApi openapi,
		D2b d2b,
		Ai ai,
		Cors cors,
		Security security,
		Alert alert,
		Sync sync,
		Index index,
		Documents documents,
		Search search) {

	/** 나라장터 OpenAPI (조달청 공공데이터). */
	public record OpenApi(
			String serviceKey,
			String baseUrl,
			int timeoutMs,
			int maxRetries,
			int pageSize) {}

	/** 국방전자조달(D2B) OpenAPI. */
	public record D2b(String serviceKey, String baseUrl, int timeoutMs) {}

	/**
	 * g2bmaster-AI 저장소로 넘기는 추론 호출.
	 *
	 * @param baseUrl       AI 서비스 주소. 언어도 프레임워크도 백엔드는 모른다
	 * @param timeoutMs     읽기 타임아웃. <b>AI 자체 데드라인보다 크고 분석 작업 리스보다 작아야 한다</b> —
	 *                      순서가 깨지면 백엔드가 먼저 포기한 작업을 다른 워커가 다시 집어
	 *                      LLM 비용이 두 배로 난다
	 * @param enabled       꺼 두면 AI 없이 되는 기능만으로 동작한다({@code AiClient} 가 호출 전에 막는다)
	 * @param serviceSecret AI 서비스의 호출자 인증 값({@code AI_SERVICE_SECRET}). AI 쪽 미들웨어가
	 *                      이 값을 설정하면 {@code X-Internal-Secret} 또는 Bearer 토큰을 요구하고,
	 *                      없으면 11개 표면이 전부 401 이 된다. 양쪽이 같은 값을 봐야 한다
	 */
	public record Ai(String baseUrl, int timeoutMs, boolean enabled, String serviceSecret) {}

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

	/**
	 * 공고 검색 색인 적재기.
	 *
	 * @param enabled      주기 적재 on/off. 기본은 꺼 둔다 — 여러 인스턴스가 같은 일을
	 *                     동시에 하면 일일 쿼터만 배로 태운다. 운영 인스턴스 하나만 켠다
	 * @param intervalMs   적재 주기. 나라장터 공고는 분 단위로 올라오므로 10분이면 충분하다
	 * @param sweepMs      입찰 → 마감 전이 주기. 적재보다 훨씬 싼 UPDATE 하나라 자주 돌린다
	 * @param backfillDays 워터마크가 없는 첫 회차에 거슬러 올라갈 기간(일)
	 */
	public record Index(boolean enabled, long intervalMs, long sweepMs, int backfillDays) {}

	/**
	 * 첨부 본문 추출 워커.
	 *
	 * <p>색인 적재와 <b>주기를 따로 두는 이유</b>: 적재는 HTTP 호출 수십 번이고, 추출은 하루
	 * 3,643개 파일 · 약 0.9GB 를 내려받는 일이다. 한 주기에 묶으면 전체가 무거운 쪽에 맞춰진다.
	 *
	 * @param enabled     주기 실행 on/off. 기본은 꺼 둔다 — 여러 인스턴스가 같이 켜면 같은 파일을
	 *                    중복으로 내려받는다. 적재 인스턴스 하나만 켠다
	 * @param intervalMs  회차 간격. 하루치를 야간에 소화하면 충분하므로 적재보다 드물게 돈다
	 * @param batchSize   한 회차에 뽑을 파일 수. 회차 하나가 지나치게 길어지지 않게 자르는 값이다
	 * @param seedBatch   한 회차에 첨부 슬롯을 세울 공고 수. 상류 호출이 없어 batchSize 보다 크게 잡는다
	 * @param concurrency 동시 다운로드 수. 원본 모놀리스는 2였지만 그것은 사용자 요청 중에 도는
	 *                    전경 작업이라 보수적이었다. 야간 배경 작업이라 4로 둔다
	 * @param closeBefore 백필 범위 제한. 값이 있으면 <b>지금부터 이 시각까지 마감되는 공고만</b>
	 *                    색인한다(ISO {@code 2026-08-18T00:00}). 전량은 109,284파일이지만 실무에서
	 *                    급한 것은 "지금 들어갈 수 있는 건"이라, 마감이 임박한 쪽부터 채우는 편이
	 *                    체감 효용이 훨씬 크다. 비워 두면 전량이 대상이다
	 * @param createdBefore 게시일 기준의 다른 축. 값이 있으면 <b>이 시각까지 게시된 공고만</b>
	 *                    색인한다(ISO {@code 2026-08-03T23:59:59}). 마감 축과 달리 <b>이미 마감된
	 *                    공고도 대상</b>이다 — "지난주까지의 공고를 분석해 달라" 같은 소급 분석은
	 *                    마감 임박 순으로는 차례가 영영 오지 않는다. 비워 두면 제한하지 않는다
	 */
	public record Documents(boolean enabled, long intervalMs, int batchSize, int seedBatch, int concurrency,
			String closeBefore, String createdBefore) {}

	/**
	 * 공고 검색 동작.
	 *
	 * @param attachmentBody {@code GET /api/search/notices} 가 첨부 본문까지 볼 것인가. <b>기본 켜짐</b>이다 —
	 *                       "검색이 첨부까지 본다"가 이 제품의 기본 약속이고, 그것을 옵션으로 두면
	 *                       화면마다 켜고 끄다가 어느 화면이 무엇을 본 검색인지 아무도 설명하지 못하게 된다.
	 *                       <p>그래도 스위치를 남기는 이유는 <b>비용 성격이 다르기 때문</b>이다. 첨부 스코프는
	 *                       질의가 UNION 두 갈래로 늘어 실측 5.3ms → 19.9ms 가 된다. 색인이 커진 뒤 상류
	 *                       장애처럼 검색이 느려지는 날, 배포 없이 되돌릴 손잡이가 하나는 있어야 한다.
	 *                       <p>끄면 {@code /api/search/notices} 는 {@code /api/search/notices/text} 와 같은
	 *                       결과를 낸다. 응답 메타의 {@code attachmentSearch.scope} 가 false 로 내려가므로
	 *                       화면은 그 사실을 알 수 있다
	 */
	public record Search(boolean attachmentBody) {}
}
