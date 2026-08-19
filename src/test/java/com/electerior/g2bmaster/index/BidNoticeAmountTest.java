package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 행에 실리는 대표 금액과 그 종류를 고정한다.
 *
 * <p><b>왜 테스트가 필요한가.</b> 이 선택은 생성 컬럼 {@code filter_amount}
 * ({@code V20260814113541} 의 {@code COALESCE})와 <b>같은 규칙을 자바에서 한 번 더</b> 구현한
 * 것이다 — 필터·정렬은 DB 컬럼이 하고, 화면에 적히는 금액과 종류는 이 메서드가 고른다.
 * 둘이 갈라져도 결과 집합은 그대로라 화면은 정상으로 보인다. 달라지는 것은 "무엇으로 걸렀는가"
 * 라는 설명뿐인데, 그것이 틀리면 사용자는 검증할 방법이 없다(28% 가 조용히 사라지던 원래 버그와
 * 같은 종류의 침묵이다).
 *
 * <p>순서의 근거와 실측은 마이그레이션 주석에 있다. 여기서는 그 순서를 못박기만 한다.
 */
class BidNoticeAmountTest {

	private static Map<String, Object> price(Object... pairs) {
		Map<String, Object> map = new LinkedHashMap<>();
		for (int i = 0; i < pairs.length; i += 2) {
			map.put(String.valueOf(pairs[i]), pairs[i + 1]);
		}
		return map;
	}

	@Test
	@DisplayName("추정가격이 있으면 그것이 이긴다 — 나라장터 입찰·마감의 기본 경로")
	void estimatedPriceWins() {
		BidNoticeSearchService.Amount amount =
				BidNoticeSearchService.amountOf(price("estimatedPrice", 27_200_000, "assignedBudget", 29_920_000));

		assertThat(amount.value()).isEqualTo(27_200_000);
		assertThat(amount.kind()).isEqualTo("estimatedPrice");
	}

	/**
	 * 이 한 줄이 원래 버그의 핵심이다 — 사전규격 12,120건이 배정예산을 가지고 있는데도
	 * 추정가격만 보느라 금액 검색에서 통째로 빠졌다.
	 */
	@Test
	@DisplayName("추정가격이 없으면 배정예산으로 내려간다 — 사전규격 12,120건이 여기서 살아난다")
	void fallsBackToAssignedBudget() {
		BidNoticeSearchService.Amount amount =
				BidNoticeSearchService.amountOf(price("assignedBudget", 29_920_000));

		assertThat(amount.value()).isEqualTo(29_920_000);
		assertThat(amount.kind()).isEqualTo("assignedBudget");
	}

	@Test
	@DisplayName("누리장터는 기준금액, D2B 는 기초예비가격까지 내려간다")
	void fallsBackToSourceSpecificKeys() {
		assertThat(BidNoticeSearchService.amountOf(price("referenceAmount", 29_884_000)).kind())
				.isEqualTo("referenceAmount");
		assertThat(BidNoticeSearchService.amountOf(price("basicExpectedPrice", 39_929_514)).kind())
				.isEqualTo("basicExpectedPrice");
	}

	/**
	 * 0 은 '0원짜리 공고'가 아니라 '미공개'다(실측 1,486건 — 대부분 누리장터 민간공고).
	 *
	 * <p>값으로 인정하면 두 가지가 동시에 깨진다: 화면에 '0원'이 적히고,
	 * {@code ?maxAmount=…} 검색이 금액을 모르는 공고를 데려온다. 그래서 다음 후보로 넘긴다 —
	 * 배정예산이 0 인 누리 27건이 기준금액을 얻는 경로가 이것이다.
	 */
	@Test
	@DisplayName("0 은 값이 아니라 미공개다 — 다음 후보로 넘긴다")
	void zeroIsTreatedAsAbsent() {
		BidNoticeSearchService.Amount amount =
				BidNoticeSearchService.amountOf(price("assignedBudget", 0, "referenceAmount", 29_884_000));

		assertThat(amount.value()).isEqualTo(29_884_000);
		assertThat(amount.kind()).isEqualTo("referenceAmount");
	}

	@Test
	@DisplayName("후보가 하나도 없으면 값도 종류도 null 이다 — 화면이 '—' 로 그린다")
	void noCandidateYieldsNull() {
		assertThat(BidNoticeSearchService.amountOf(price("assignedBudget", 0)).value()).isNull();
		assertThat(BidNoticeSearchService.amountOf(price("unitPrice", 1_000)).kind()).isNull();
		assertThat(BidNoticeSearchService.amountOf(null).value()).isNull();
		assertThat(BidNoticeSearchService.amountOf("깨진 JSON 이라 파싱을 못 한 자리").kind()).isNull();
	}
}
