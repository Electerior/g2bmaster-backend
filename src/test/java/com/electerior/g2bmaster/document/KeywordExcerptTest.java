package com.electerior.g2bmaster.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 첨부 본문 키워드 매칭·발췌.
 *
 * <p>여기서 지키는 것은 <b>화면에 그대로 보이는 계약</b>이다 — 무엇이 걸렸다고 표시되는지,
 * 어느 대목이 발췌되는지. 원본 {@code bestKeywordExcerpt} 의 규칙을 그대로 옮겼으므로,
 * 두 계통이 같은 공고에 대해 같은 답을 내야 한다.
 */
class KeywordExcerptTest {

	@Test
	@DisplayName("한글은 부분일치다 — '서버' 로 '노트북서버구매' 가 걸린다")
	void koreanIsSubstringMatch() {
		KeywordExcerpt.Result result = KeywordExcerpt.best("본 건은 노트북서버구매 사업이다.", List.of("서버"));

		assertThat(result).isNotNull();
		assertThat(result.matchedKeywords()).containsExactly("서버");
	}

	/**
	 * 영문·숫자에 부분일치를 적용하면 부품번호가 통째로 걸린다. 규격서에는
	 * {@code MRTXP-2000} 같은 품번이 흔해서, 'RTX' 검색이 GPU 와 무관한 공고를 잔뜩 물어 온다.
	 */
	@Test
	@DisplayName("영문·숫자는 낱말 경계를 본다 — 'RTX' 가 'MRTXP' 에 걸리지 않는다")
	void asciiRespectsWordBoundary() {
		assertThat(KeywordExcerpt.best("품번 MRTXP-2000 규격", List.of("RTX"))).isNull();
		assertThat(KeywordExcerpt.best("GPU: RTX 5090 8장", List.of("RTX"))).isNotNull();
	}

	/**
	 * 규격서는 같은 말을 표 안에서 띄어쓰기만 달리해 쓴다. 이걸 못 잡으면 사용자가
	 * 띄어쓰기를 바꿔 가며 여러 번 검색해야 한다.
	 */
	@Test
	@DisplayName("공백은 느슨하게 본다 — '저장 장치' 로 '저장장치'·'저장-장치' 가 걸린다")
	void whitespaceIsFlexible() {
		assertThat(KeywordExcerpt.best("내장 저장장치 2TB", List.of("저장 장치"))).isNotNull();
		assertThat(KeywordExcerpt.best("내장 저장-장치 2TB", List.of("저장 장치"))).isNotNull();
		assertThat(KeywordExcerpt.best("내장 저장 장치 2TB", List.of("저장 장치"))).isNotNull();
	}

	@Test
	@DisplayName("대소문자를 가리지 않는다")
	void caseInsensitive() {
		assertThat(KeywordExcerpt.best("장비: rtx 5090", List.of("RTX"))).isNotNull();
	}

	@Test
	@DisplayName("안 걸리면 null 이다 — 빈 발췌를 만들지 않는다")
	void noMatchIsNull() {
		assertThat(KeywordExcerpt.best("정수기 구매 건", List.of("서버"))).isNull();
		assertThat(KeywordExcerpt.best("", List.of("서버"))).isNull();
		assertThat(KeywordExcerpt.best("서버 구매", List.of())).isNull();
		assertThat(KeywordExcerpt.best("서버 구매", List.of("  "))).isNull();
	}

	/**
	 * 서로 다른 키워드가 함께 언급된 대목이 사용자가 실제로 읽고 싶은 곳이다.
	 * 같은 낱말이 반복되기만 하는 대목보다 우선해야 한다.
	 */
	@Test
	@DisplayName("여러 키워드가 함께 걸린 대목을 고른다")
	void prefersPassageWithMoreDistinctKeywords() {
		String text = "서버 서버 서버 서버 서버 관련 일반사항이다. "
				+ "제2장. 본 사업은 서버 및 저장장치를 함께 납품하는 건으로 한다.";

		KeywordExcerpt.Result result = KeywordExcerpt.best(text, List.of("서버", "저장장치"));

		assertThat(result).isNotNull();
		assertThat(result.matchedKeywords()).containsExactlyInAnyOrder("서버", "저장장치");
		assertThat(result.excerpt()).contains("저장장치");
	}

	@Test
	@DisplayName("발췌는 걸린 지점 앞뒤를 잘라 온다 — 문서 전체가 아니다")
	void excerptIsAWindowNotTheWholeDocument() {
		String filler = "가나다라마바사아자차카타파하 ".repeat(400);
		String text = filler + " 핵심은 RTX 5090 8장이다. " + filler;

		KeywordExcerpt.Result result = KeywordExcerpt.best(text, List.of("RTX"));

		assertThat(result).isNotNull();
		assertThat(result.excerpt()).contains("RTX");
		// 앞 120 + 뒤 420 자 창이라 대목 하나를 넘지 않는다.
		assertThat(result.excerpt().length()).isLessThan(650);
		assertThat(result.excerpt().length()).isLessThan(text.length() / 10);
	}

	/**
	 * 정규식 특수문자가 든 키워드가 그대로 패턴에 들어가면 질의가 깨지거나(예외) 엉뚱한 것이
	 * 걸린다. 실제 검색어에 {@code C++} · {@code 3.5"} 같은 것이 들어온다.
	 */
	@Test
	@DisplayName("정규식 특수문자가 든 키워드도 그냥 글자로 취급한다")
	void specialCharactersAreLiteral() {
		assertThat(KeywordExcerpt.best("개발언어는 C++ 로 한다", List.of("C++"))).isNotNull();
		assertThat(KeywordExcerpt.best("개발언어는 CXX 로 한다", List.of("C++"))).isNull();
	}

	@Test
	@DisplayName("문장 부호가 없는 표만 있는 문서도 자른다")
	void tableOnlyDocumentStillSplits() {
		String table = "| 품목 | 수량 | ".repeat(200) + " | 서버 | 8 |";

		KeywordExcerpt.Result result = KeywordExcerpt.best(table, List.of("서버"));

		assertThat(result).isNotNull();
		assertThat(result.excerpt()).contains("서버");
	}

	@Test
	@DisplayName("matchesAny 는 발췌 없이 걸림 여부만 본다")
	void matchesAnyIsCheapCheck() {
		assertThat(KeywordExcerpt.matchesAny("노트북서버구매", List.of("서버"))).isTrue();
		assertThat(KeywordExcerpt.matchesAny("정수기", List.of("서버"))).isFalse();
		assertThat(KeywordExcerpt.matchesAny(null, List.of("서버"))).isFalse();
	}
}
