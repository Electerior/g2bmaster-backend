package com.electerior.g2bmaster.index;

/**
 * 공고 출처 — ERD {@code source} 의 값 집합 {@code {G2B, NURI, D2B}} (V13).
 *
 * <p>세 시스템은 공고번호 체계를 공유하지 않으면서도 <b>형식은 겹칠 수 있다</b> — 누리장터가
 * 나라장터와 같은 차세대 13자리 {@code R##BK########} 발번을 쓰는 것이 실측으로 확인됐다.
 * 그래서 색인 PK 가 {@code (id, source)} 이고, 이 enum 이 그 두 번째 성분이다.
 *
 * <p>팬아웃 응답의 {@code _source} 값(g2b / private-g2b / d2b)과 DB ENUM 값의 매핑을
 * 이 한 곳에서만 한다 — 흩어 두면 언젠가 한 곳이 어긋난다.
 */
public enum NoticeSource {

	G2B("g2b", "나라장터"),
	NURI("private-g2b", "누리장터"),
	D2B("d2b", "국방전자조달");

	/** 팬아웃 응답 {@code _source} 의 값. 프론트 계약이라 바꾸지 않는다. */
	private final String apiValue;
	private final String label;

	NoticeSource(String apiValue, String label) {
		this.apiValue = apiValue;
		this.label = label;
	}

	public String apiValue() {
		return apiValue;
	}

	public String label() {
		return label;
	}

	/**
	 * DB ENUM 값({@code G2B}) 또는 API {@code _source} 값({@code private-g2b}) 어느 쪽이 와도 받는다.
	 *
	 * @return 알 수 없으면 {@code null}(= 필터 없음)
	 */
	public static NoticeSource of(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		String v = value.trim();
		for (NoticeSource source : values()) {
			if (source.name().equalsIgnoreCase(v) || source.apiValue.equalsIgnoreCase(v)) {
				return source;
			}
		}
		return null;
	}
}
