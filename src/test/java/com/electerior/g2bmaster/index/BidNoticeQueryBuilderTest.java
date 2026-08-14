package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 검색 조건 조립.
 *
 * <p>SQL 문자열을 직접 들여다보는 테스트다. 보통은 구현 세부라 보지 않지만, 여기서는
 * <b>SQL 의 모양 자체가 계약</b>이다 — 한 글자 검색어가 MATCH 로 새면 결과가 조용히 0건이 되고,
 * LIKE 이스케이프가 빠지면 사용자가 친 {@code _} 가 와일드카드가 된다. 둘 다 화면에서는
 * '검색이 좀 이상하다'로만 보여서 추적이 어렵다.
 */
class BidNoticeQueryBuilderTest {

	@Test
	@DisplayName("조건이 없으면 WHERE 절도 없다")
	void empty() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder().build();

		assertThat(where.sql()).isEmpty();
		assertThat(where.params()).isEmpty();
		assertThat(where.fullText()).isFalse();
		assertThat(where.relevanceSelect()).isEqualTo(", 0 AS relevance");
	}

	@Test
	@DisplayName("AND 낱말은 MATCH … BOOLEAN MODE 의 '+' 가 된다")
	void andTermsBecomeRequired() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(List.of("서버", "구매"), List.of(), List.of())
				.build();

		assertThat(where.sql()).contains("MATCH(n.notice_name, n.notice_body) AGAINST (:ftQuery IN BOOLEAN MODE)");
		assertThat(where.params().get("ftQuery")).isEqualTo("+\"서버\" +\"구매\"");
		assertThat(where.fullText()).isTrue();
	}

	@Test
	@DisplayName("OR 는 기호 없이, NOT 은 '-' 로 붙는다")
	void orAndNotTerms() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(List.of("서버"), List.of("스토리지"), List.of("임대"))
				.build();

		assertThat(where.params().get("ftQuery")).isEqualTo("+\"서버\" \"스토리지\" -\"임대\"");
	}

	/**
	 * MySQL 불리언 모드에서 {@code -foo} 만 있는 식은 <b>아무 행도 돌려주지 않는다</b>.
	 * 사용자가 기대하는 것은 "그것만 뺀 전체"이므로 MATCH 를 쓰지 않고 LIKE 로 뺀다.
	 */
	@Test
	@DisplayName("제외 낱말만 있으면 MATCH 를 쓰지 않는다 — 그러면 0건이 되기 때문")
	void notOnlyAvoidsMatch() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(List.of(), List.of(), List.of("임대"))
				.build();

		assertThat(where.sql()).doesNotContain("MATCH");
		assertThat(where.sql()).contains("NOT (n.notice_name LIKE");
		assertThat(where.fullText()).isFalse();
	}

	/**
	 * ngram 토큰 크기가 2라 한 글자는 MATCH 가 통째로 버린다 — 0건이 아니라 '조용히 무시'다.
	 */
	@Test
	@DisplayName("한 글자 검색어는 MATCH 가 아니라 LIKE 로 떨어진다")
	void singleCharacterFallsBackToLike() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(List.of("차"), List.of(), List.of())
				.build();

		assertThat(where.sql()).doesNotContain("MATCH");
		assertThat(where.sql()).contains("n.notice_name LIKE");
		assertThat(where.params()).containsValue("%차%");
	}

	@Test
	@DisplayName("한 글자와 두 글자가 섞이면 각자 제 경로로 간다")
	void mixedLengthTerms() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(List.of("차", "서버"), List.of(), List.of())
				.build();

		assertThat(where.params().get("ftQuery")).isEqualTo("+\"서버\"");
		assertThat(where.params()).containsValue("%차%");
		assertThat(where.fullText()).isTrue();
	}

	/**
	 * 사용자가 친 {@code +}·{@code (} 가 연산자로 읽히면 MySQL 이 문법 오류를 내고 검색이 500 이 된다.
	 */
	@Test
	@DisplayName("불리언 연산자 문자는 따옴표 안에 갇힌다")
	void operatorCharactersAreQuoted() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(List.of("A+B", "(주)한국"), List.of(), List.of())
				.build();

		assertThat(where.params().get("ftQuery")).isEqualTo("+\"A+B\" +\"(주)한국\"");
	}

	@Test
	@DisplayName("낱말 안의 큰따옴표는 지운다 — 이스케이프 문법이 없다")
	void embeddedQuotesRemoved() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(List.of("\"서버\""), List.of(), List.of())
				.build();

		assertThat(String.valueOf(where.params().get("ftQuery"))).isEqualTo("+\"서버\"");
	}

	@Test
	@DisplayName("LIKE 와일드카드는 무력화된다 — 사용자가 친 _ 는 한 글자 와일드카드가 아니다")
	void likeWildcardsEscaped() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.region("A_B%C")
				.build();

		assertThat(where.params()).containsValue("%A!_B!%C%");
		assertThat(where.sql()).contains("ESCAPE '!'");
	}

	/**
	 * 지역 제한이 없는 공고(=전국)는 어떤 지역으로 좁혀도 함께 나와야 한다.
	 * 서울 업체가 참가할 수 있는 전국 공고가 빠지는 편이 훨씬 큰 손해다.
	 */
	@Test
	@DisplayName("지역 필터는 '전국(빈 값)' 공고를 함께 남긴다")
	void regionKeepsNationwide() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder().region("경기도").build();

		assertThat(where.sql()).contains("OR n.region = ''");
	}

	/**
	 * 조달청이 대행 공고하는 건은 공고기관이 조달청이고 수요기관이 실제 발주처다.
	 * 한쪽만 보면 그 건이 통째로 빠진다.
	 */
	@Test
	@DisplayName("발주기관명은 공고기관·수요기관 둘 다에서 찾는다")
	void institutionNameChecksBothSides() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.institutionName("평택시")
				.build();

		assertThat(where.sql())
				.contains("n.notice_institution_name LIKE")
				.contains("n.demand_institution_name LIKE");
		// dm_institution 조인은 더 이상 쓰지 않는다(V8) — 조인 별칭이 남아 있으면 SQL 이 깨진다.
		assertThat(where.sql()).doesNotContain("ni.instt_nm").doesNotContain("di.instt_nm");
		assertThat(where.params()).containsValue("%평택시%");
	}

	@Test
	@DisplayName("세부품명번호는 접두 일치라 상위 분류로도 훑을 수 있다")
	void detailProductCodeIsPrefix() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder().detailProductCode("4110").build();

		assertThat(where.params()).containsValue("4110%");
	}

	/** 스위퍼는 주기적으로 도는 것이라, 방금 마감된 건이 아직 '입찰'로 남아 있을 수 있다. */
	@Test
	@DisplayName("'마감 전만'의 마감 판정은 category 가 아니라 마감일시다")
	void activeOnlyChecksCloseDateNotCategory() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder().activeOnly(true).build();

		assertThat(where.sql()).contains("n.close_date IS NULL OR n.close_date >= NOW(6)");
		// 마감을 category 로 빼면 스위퍼 지연을 물려받는다 — 아래 IN 스코프와는 다른 조건이다.
		assertThat(where.sql()).doesNotContain("category <>").doesNotContain("category !=");
	}

	/**
	 * 계획은 마감일시가 아예 없어(NULL) 마감일시 조건을 무조건 통과하고, 사전규격의 마감일시는
	 * 입찰마감이 아니라 <b>의견등록</b>마감이다. 스코프 없이는 "지금 참여할 수 있는 공고"의
	 * 다섯에 하나가 참여 대상이 아닌 문서였다(실측 21.5%).
	 */
	@Test
	@DisplayName("단계 미지정 + '마감 전만'은 입찰 문서(입찰·마감)로 좁힌다")
	void activeOnlyWithoutCategoryScopesToBidDocuments() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder().activeOnly(true).build();

		assertThat(where.sql()).contains("n.category IN ('입찰', '마감')");
	}

	/** 화면의 단계 칩은 activeOnly 기본 ON 과 함께 온다 — 스코프가 이기면 '계획' 칩이 항상 0건이 된다. */
	@Test
	@DisplayName("단계를 직접 고르면 스코프를 걸지 않는다 — '계획'+마감 전만이 0건이 되면 안 된다")
	void activeOnlyKeepsExplicitCategory() {
		// activeOnly 를 category 보다 먼저 불러도 같아야 한다 — 조립이 build() 에 있어 순서 무관.
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.activeOnly(true)
				.category(NoticeCategory.계획)
				.build();

		assertThat(where.sql()).doesNotContain("IN ('입찰', '마감')");
		assertThat(where.sql()).contains("n.category = :");
		assertThat(where.sql()).contains("n.close_date IS NULL OR n.close_date >= NOW(6)");
		assertThat(where.params()).containsValue("계획");
	}

	@Test
	@DisplayName("필터가 여럿이면 AND 로 이어진다")
	void filtersCombineWithAnd() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.category(NoticeCategory.입찰)
				.businessDivision(BusinessDivision.공사)
				.state(NoticeState.취소)
				.build();

		assertThat(where.sql()).startsWith("\n WHERE ");
		assertThat(where.sql()).contains("AND");
		assertThat(where.params()).containsValues("입찰", "공사", "취소");
	}

	@Test
	@DisplayName("null 필터는 조건을 만들지 않는다")
	void nullFiltersSkipped() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.category(null)
				.region("  ")
				.institutionName(null)
				.amountBetween(null, null)
				.build();

		assertThat(where.sql()).isEmpty();
	}

	/**
	 * 금액 조건이 <b>생성 컬럼을 그대로</b> 참조하는지 고정한다.
	 *
	 * <p>V11 이전에는 {@code CAST(JSON_UNQUOTE(JSON_EXTRACT(...)))} 를 식으로 걸었고, 그래서
	 * 금액 필터가 인덱스를 못 타 전수 스캔이었다. 컬럼을 다시 함수로 감싸거나 {@code COALESCE} 를
	 * 여기에 펼치는 순간 같은 상태로 돌아가는데, 증상이 '느려짐' 뿐이라 리뷰로는 잡히지 않는다 —
	 * 그래서 SQL 문자열을 직접 본다.
	 */
	@Test
	@DisplayName("금액 구간은 생성 컬럼 filter_amount 를 함수 없이 참조한다")
	void amountRangeUsesGeneratedColumn() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.amountBetween(1_000_000L, 5_000_000L)
				.build();

		assertThat(where.sql()).contains("n.filter_amount >= :minAmount")
				.contains("n.filter_amount <= :maxAmount");
		// 함수로 감싸면 인덱스를 못 쓴다 — 되돌아가는 것을 여기서 막는다.
		assertThat(where.sql()).doesNotContain("JSON_EXTRACT").doesNotContain("CAST(")
				.doesNotContain("COALESCE");
		assertThat(where.params()).containsEntry("minAmount", 1_000_000L)
				.containsEntry("maxAmount", 5_000_000L);
	}

	/**
	 * 금액 필터가 <b>추정가격 컬럼으로 되돌아가지 않는지</b> 고정한다.
	 *
	 * <p>실측(2026-08-14, 51,478행)으로 확인한 회귀다. 이 조건이 {@code estimated_price} 를 보던
	 * 동안에는 {@code ?minAmount=1} 한 줄에 사전규격 12,119건 전부와 누리·D2B 2,045건 전부가
	 * 사라졌다 — 전체의 28%다. 그 키를 가진 행이 나라장터의 입찰·마감·계획뿐이기 때문인데,
	 * 결과가 '조용히 좁아질' 뿐이라 화면에서는 필터가 아예 안 붙은 것처럼 보였다.
	 *
	 * <p>컬럼 이름 하나만 되돌려도 같은 상태가 되므로 이름을 직접 못박는다.
	 */
	@Test
	@DisplayName("금액 구간은 추정가격 전용 컬럼을 보지 않는다 — 사전규격·누리·D2B 가 통째로 빠졌던 자리다")
	void amountRangeDoesNotUseEstimatedPriceColumn() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.amountBetween(1L, null)
				.build();

		assertThat(where.sql()).doesNotContain("estimated_price");
	}

	// ── 첨부 본문 스코프 ────────────────────────────────────────────────────

	@Test
	@DisplayName("스코프를 켜지 않으면 첨부 재료가 아예 없다 — /text 는 예전 그대로다")
	void attachmentScopeOffLeavesNothing() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(List.of("서버"), List.of(), List.of())
				.build();

		assertThat(where.attachment()).isNull();
		assertThat(where.unionsAttachments()).isFalse();
		assertThat(where.excludesByAttachment()).isFalse();
		assertThat(where.params()).doesNotContainKey("ftDocQuery");
	}

	/**
	 * 첨부 브랜치는 <b>조건 문자열이 아니라 재료</b>로 넘어간다. 조립은 저장소가 한다 —
	 * 여기서 조건을 만들어 버리면 {@code sql()} 을 쓰는 기존 경로가 첨부 조건을 끌고 간다.
	 */
	@Test
	@DisplayName("스코프를 켜도 WHERE 절은 그대로고, 첨부 질의만 따로 실린다")
	void attachmentScopeAddsQueryNotCondition() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.attachmentScope(true)
				.keywords(List.of("서버"), List.of("스토리지"), List.of())
				.build();

		assertThat(where.sql()).doesNotContain("bid_notice_document");
		assertThat(where.unionsAttachments()).isTrue();
		assertThat(where.attachment().includeQuery()).isEqualTo("+\"서버\" \"스토리지\"");
		assertThat(where.params()).containsEntry("ftDocQuery", "+\"서버\" \"스토리지\"");
	}

	/**
	 * 제외 낱말을 첨부 질의에 섞으면 그 <b>파일</b>만 후보에서 빠질 뿐, 공고는 다른 첨부로
	 * 여전히 걸린다. 제외는 공고 단위라야 하므로 집합을 따로 뽑아 반조인한다.
	 */
	@Test
	@DisplayName("제외 낱말은 첨부 포함 질의에 섞이지 않고 따로 빠진다")
	void attachmentExcludeIsSeparate() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.attachmentScope(true)
				.keywords(List.of("서버"), List.of(), List.of("임대", "렌탈"))
				.build();

		assertThat(where.attachment().includeQuery()).isEqualTo("+\"서버\"");
		// 무기호 = 불리언 모드의 OR. 하나라도 들어 있으면 그 공고를 뺀다.
		assertThat(where.attachment().excludeQuery()).isEqualTo("\"임대\" \"렌탈\"");
		assertThat(where.excludesByAttachment()).isTrue();
	}

	/** 제외만 지정한 검색도 첨부까지 걸러야 한다 — 공고 텍스트에만 걸면 범위가 갈린다. */
	@Test
	@DisplayName("검색어 없이 제외만 있어도 첨부 제외는 걸린다")
	void attachmentExcludeAppliesWithoutPositiveTerms() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.attachmentScope(true)
				.keywords(List.of(), List.of(), List.of("임대"))
				.build();

		assertThat(where.unionsAttachments()).isFalse();   // 뒤질 낱말이 없으니 브랜치는 안 만든다
		assertThat(where.excludesByAttachment()).isTrue(); // 그래도 빼기는 한다
	}

	/**
	 * ngram 토큰 크기가 2라 한 글자는 MATCH 가 버리고, 대안인 {@code body_text LIKE '%x%'} 는
	 * 실측 669ms 다(done 3,704행 기준). 화면 질의에 붙일 수 없는 비용이라 건너뛰고,
	 * <b>건너뛰었다는 사실을 응답에 싣는다</b> — 조용히 무시하면 사용자가 못 알아챈다.
	 */
	@Test
	@DisplayName("한 글자 낱말은 첨부 범위에서 빠지고, 빠졌다는 사실이 남는다")
	void singleCharacterTermsSkippedForAttachments() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.attachmentScope(true)
				.keywords(List.of("차", "서버"), List.of(), List.of("컵"))
				.build();

		assertThat(where.attachment().includeQuery()).isEqualTo("+\"서버\"");
		assertThat(where.attachment().skippedTerms()).containsExactlyInAnyOrder("차", "컵");
		// 공고 텍스트 쪽에서는 지금처럼 LIKE 로 계속 걸린다.
		assertThat(where.sql()).contains("n.notice_name LIKE");
	}

	/**
	 * 필터가 두 브랜치에 공통으로 붙어야 한다. 갈라지면 '경기도'로 좁힌 검색이 첨부에서
	 * 걸렸다는 이유로 부산 공고를 데려온다.
	 */
	@Test
	@DisplayName("키워드와 필터가 따로 나오고, 합치면 예전 WHERE 와 같다")
	void keywordAndFilterSplitRecombines() {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.attachmentScope(true)
				.keywords(List.of("서버"), List.of(), List.of())
				.region("경기도")
				.activeOnly(true)
				.build();

		assertThat(where.keywordSql()).contains("MATCH(n.notice_name, n.notice_body)");
		assertThat(where.keywordSql()).doesNotContain("n.region");
		assertThat(where.filterSql()).contains("n.region LIKE").contains("n.close_date");
		assertThat(where.filterSql()).doesNotContain("MATCH");
		assertThat(where.sql()).isEqualTo("\n WHERE " + where.keywordSql() + "\n   AND " + where.filterSql());
	}

	@Test
	@DisplayName("낱말 수 상한을 넘기지 않는다")
	void termsCapped() {
		List<String> many = List.of("가가", "나나", "다다", "라라", "마마", "바바", "사사", "아아", "자자", "차차");

		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(many, List.of(), List.of())
				.build();

		assertThat(String.valueOf(where.params().get("ftQuery")).split("\\+")).hasSizeLessThanOrEqualTo(9);
	}
}
