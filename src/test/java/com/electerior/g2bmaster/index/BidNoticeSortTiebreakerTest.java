package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * 정렬 타이브레이커({@code n.id}) 방향을 고정한다.
 *
 * <p><b>왜 테스트가 필요한가.</b> 방향이 어긋나도 결과 집합은 똑같다 — 순서만 미묘하게 다르고
 * 화면은 정상으로 보인다. 달라지는 것은 실행 계획뿐이라 기능 테스트로는 절대 잡히지 않는다.
 * 실측(bid_notice 20,403행)으로 확인한 차이:
 *
 * <pre>
 *   updated_at DESC, id DESC  →  Backward index scan, 20행
 *   updated_at DESC, id ASC   →  Using filesort,  16,560행
 * </pre>
 *
 * <p>규칙은 하나다 — InnoDB 보조 인덱스는 PK 접미를 언제나 ASC 로 붙이므로,
 * 인덱스를 <b>정방향</b>으로 읽으면 id 가 ASC, <b>역방향</b>이면 DESC 로 나온다.
 * 정방향이 되는 조건은 "요청 방향 == 인덱스 선언 방향" 이다.
 *
 * <p>이 표의 인덱스 선언 방향은 마이그레이션과 짝이다:
 * {@code V7} 의 {@code ix_bid_notice_category_created (category, created_date DESC)},
 * {@code V7} 의 {@code ix_bid_notice_updated (updated_at)} = ASC,
 * {@code V20260814113541} 의 {@code ix_bid_notice_category_filter_amount (category, filter_amount DESC)},
 * {@code V12} 의 {@code ix_bid_notice_created (created_date DESC)}.
 * 마이그레이션에서 방향을 바꾸면 이 테스트가 먼저 깨져야 한다.
 */
class BidNoticeSortTiebreakerTest {

	@ParameterizedTest(name = "{0} {1} → id {2}")
	@DisplayName("타이브레이커 방향은 인덱스 선언 방향과 요청 방향으로 결정된다")
	@CsvSource({
			// created 계열 인덱스는 DESC 로 선언돼 있다 → 최신순(DESC)이 정방향
			"created,  DESC, ASC",
			"created,  ASC,  DESC",
			// amount 인덱스도 DESC 선언 → 금액 큰 순이 정방향
			"amount,   DESC, ASC",
			"amount,   ASC,  DESC",
			// updated 인덱스만 방향 미지정(ASC) → 최근 변경순(DESC)은 역방향 스캔이다
			"updated,  DESC, DESC",
			"updated,  ASC,  ASC",
	})
	void directionFollowsIndexDeclaration(String sortKey, String direction, String expected) {
		assertThat(BidNoticeSearchService.tiebreakerDirection(sortKey, direction)).isEqualTo(expected);
	}

	@Test
	@DisplayName("받쳐 주는 인덱스가 없는 정렬 키는 순서만 고정하면 된다")
	void unindexedSortKeysFallBack() {
		// close/name/relevance 는 어느 방향이든 filesort 라 방향 선택에 의미가 없다.
		// 다만 '고정되어 있을 것'은 페이징 안정성 때문에 필요하다.
		assertThat(BidNoticeSearchService.tiebreakerDirection("close", "ASC")).isEqualTo("ASC");
		assertThat(BidNoticeSearchService.tiebreakerDirection("name", "DESC")).isEqualTo("ASC");
		assertThat(BidNoticeSearchService.tiebreakerDirection("relevance", "DESC")).isEqualTo("ASC");
	}

	@Test
	@DisplayName("updated 와 created 는 같은 요청 방향에서 서로 다른 타이브레이커를 낸다")
	void updatedAndCreatedDisagree() {
		// 이 한 줄이 '전역으로 한 방향을 고를 수 없다'는 사실 자체를 고정한다.
		// 예전에는 둘 다 id DESC 였고, 그래서 created 계열이 전부 filesort 였다.
		assertThat(BidNoticeSearchService.tiebreakerDirection("created", "DESC"))
				.isNotEqualTo(BidNoticeSearchService.tiebreakerDirection("updated", "DESC"));
	}
}
