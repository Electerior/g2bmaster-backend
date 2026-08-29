package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 첨부 본문까지 보는 검색의 SQL 모양.
 *
 * <p>여기서 고정하는 것들은 전부 <b>실행은 멀쩡한데 조용히 틀리거나 조용히 느려지는</b>
 * 종류다. 결과 건수는 맞고 로그도 깨끗한데 응답이 80배 느려지거나(OR 형태), 필터가 한쪽
 * 브랜치에만 붙어 '경기도'로 좁힌 검색에 부산 공고가 섞인다. 둘 다 리뷰로는 안 잡히므로
 * SQL 문자열 자체를 계약으로 박아 둔다.
 *
 * <p>실측 근거는 {@code BidNoticeIndexRepository.candidateSql} 머리주석에 있다.
 */
class AttachmentSearchSqlTest {

	private static BidNoticeQueryBuilder.Where where(List<String> and, List<String> not) {
		return new BidNoticeQueryBuilder()
				.attachmentScope(true)
				.keywords(and, List.of(), not)
				.region("경기도")
				.activeOnly(true)
				.build();
	}

	/**
	 * {@code MATCH(공고) OR EXISTS(MATCH(첨부))} 는 두 FULLTEXT 인덱스 중 어느 쪽도 구동
	 * 경로가 되지 못해 전수 스캔이 된다 — 실측 424ms 대 19.9ms(21배). 읽기에는 그쪽이
	 * 자연스러워서 언젠가 "간단히 정리"되기 쉬운 자리라 못을 박는다.
	 */
	@Test
	@DisplayName("두 브랜치를 UNION 으로 가른다 — OR EXISTS 로 합치지 않는다")
	void branchesAreUnionedNotOred() {
		String sql = BidNoticeIndexRepository.candidateSql(where(List.of("서버"), List.of()));

		assertThat(sql).contains("UNION ALL");
		assertThat(sql).doesNotContain("OR EXISTS");
		// 각 브랜치가 자기 인덱스를 탄다.
		assertThat(sql).contains("MATCH(n.notice_name, n.notice_body)");
		assertThat(sql).contains("MATCH(d.body_text) AGAINST (:ftDocQuery IN BOOLEAN MODE)");
	}

	/**
	 * 필터가 한쪽에만 붙으면 "첨부에서 걸렸다"는 이유로 필터 밖 공고가 결과에 섞인다.
	 * 건수만 보고는 절대 못 알아채는 종류의 오염이다.
	 */
	@Test
	@DisplayName("필터는 두 브랜치에 똑같이 붙는다")
	void filtersApplyToBothBranches() {
		String sql = BidNoticeIndexRepository.candidateSql(where(List.of("서버"), List.of()));

		String[] branches = sql.split("UNION ALL");
		assertThat(branches).hasSize(2);
		for (String branch : branches) {
			assertThat(branch).contains("n.region LIKE");
			assertThat(branch).contains("n.close_date");
		}
	}

	/** 공고 텍스트 키워드가 첨부 브랜치에 새면 교집합이 되어 이 기능의 존재 이유가 사라진다. */
	@Test
	@DisplayName("공고 텍스트 조건이 첨부 브랜치로 새지 않는다")
	void noticeKeywordStaysOutOfDocumentBranch() {
		// 한 글자는 LIKE 로 떨어지는 조건이라, 새면 눈에 잘 띈다.
		String sql = BidNoticeIndexRepository.candidateSql(where(List.of("서버", "차"), List.of()));

		String documentBranch = sql.split("UNION ALL")[1];
		assertThat(documentBranch).doesNotContain("n.notice_name LIKE");
		assertThat(documentBranch).doesNotContain("MATCH(n.notice_name");
	}

	/**
	 * 같은 공고가 제목과 규격서 양쪽에 걸리면 UNION 이 두 줄을 낸다. 접지 않으면 총건수가
	 * 부풀고 목록에 같은 공고가 두 번 뜬다.
	 */
	@Test
	@DisplayName("두 브랜치에 걸린 공고는 한 줄로 접는다")
	void duplicatesAreFolded() {
		String sql = BidNoticeIndexRepository.candidateSql(where(List.of("서버"), List.of()));

		assertThat(sql).contains("GROUP BY u.fid, u.fsource");
		// 어느 쪽에서 걸렸는지는 접은 뒤에도 남아야 화면이 표시할 수 있다.
		assertThat(sql).contains("MAX(u.notice_hit)").contains("MAX(u.doc_hit)");
	}

	/**
	 * 제외를 브랜치마다 상관 {@code NOT EXISTS} 로 걸면 같은 집합을 후보 행마다 다시 만든다 —
	 * 실측 34.6ms 대 24.2ms. 집합을 한 번 뽑아 반조인하는 형태를 고정한다.
	 */
	@Test
	@DisplayName("첨부 제외는 상관 서브쿼리가 아니라 반조인이다")
	void exclusionUsesAntiJoin() {
		String sql = BidNoticeIndexRepository.candidateSql(where(List.of("서버"), List.of("임대")));

		assertThat(sql).contains("LEFT JOIN").contains("xd.notice_id IS NULL");
		assertThat(sql).doesNotContain("NOT EXISTS");
		assertThat(sql).contains("MATCH(body_text) AGAINST (:ftDocExclude IN BOOLEAN MODE)");
	}

	@Test
	@DisplayName("제외 낱말이 없으면 반조인을 만들지 않는다")
	void noExclusionNoAntiJoin() {
		String sql = BidNoticeIndexRepository.candidateSql(where(List.of("서버"), List.of()));

		assertThat(sql).doesNotContain("LEFT JOIN");
		assertThat(sql).doesNotContain("ftDocExclude");
	}

	/**
	 * 총건수는 <b>후보 집합을 만들지 않고</b> 센다.
	 *
	 * <p>{@code candidateSql} 을 그대로 재사용하면 세기만 하는 데도 매치 행마다 FULLTEXT
	 * 점수를 계산하고 두 브랜치를 임시 테이블로 실체화한 뒤 {@code MAX()} 세 개를 집계한다.
	 * 색인이 318만 행으로 커진 뒤 '공사'(107만 건 매치)에서 22.7초가 나왔다 — 결과는 맞고
	 * 로그도 깨끗한, 정확히 이 파일이 잠그려는 종류의 결함이다.
	 */
	@Test
	@DisplayName("총건수 질의는 관련도·hit 플래그를 계산하지 않는다")
	void countSqlSkipsScoringAndFlags() {
		String sql = BidNoticeIndexRepository.disjointCountSql(where(List.of("서버"), List.of()));

		assertThat(sql).doesNotContain("relevance");
		assertThat(sql).doesNotContain("notice_hit");
		assertThat(sql).doesNotContain("doc_hit");
		// 실체화의 신호. 후보를 만들지 않으므로 둘 다 없어야 한다.
		assertThat(sql).doesNotContain("UNION");
		assertThat(sql).doesNotContain("GROUP BY");
	}

	/**
	 * |공고 ∪ 첨부| = |공고| + |첨부 중 공고에 안 걸린 것|.
	 *
	 * <p>부정을 <b>첨부 쪽에</b> 걸어야 훑는 양이 첨부 브랜치 크기로 묶인다. 반대로 걸면
	 * 공고 전체를 다시 훑어 이득이 사라진다 — 값은 같게 나오므로 테스트가 없으면 뒤집혀도 모른다.
	 */
	@Test
	@DisplayName("첨부 브랜치에서 공고 매치를 빼 두 번 세지 않는다")
	void countSqlSubtractsOverlapOnDocSide() {
		String sql = BidNoticeIndexRepository.disjointCountSql(where(List.of("서버"), List.of()));

		assertThat(sql).contains("COUNT(DISTINCT d.notice_id, d.source)");
		// 부정이 붙는 자리는 첨부 브랜치다.
		int docBranch = sql.indexOf("bid_notice_document");
		assertThat(docBranch).isGreaterThan(-1);
		assertThat(sql.indexOf("AND NOT (")).isGreaterThan(docBranch);
	}

	/** 필터는 세는 쪽에서도 두 항에 똑같이 붙어야 한다 — 한쪽만 붙으면 건수가 목록과 어긋난다. */
	@Test
	@DisplayName("총건수 질의에도 필터가 두 항 모두에 붙는다")
	void countSqlAppliesFiltersToBothTerms() {
		String sql = BidNoticeIndexRepository.disjointCountSql(where(List.of("서버"), List.of()));

		assertThat(sql.split("n\\.region LIKE", -1)).hasSize(3);
	}

	/**
	 * 관련도 정렬일 때 공고 브랜치를 <b>union 에 넣기 전에</b> 자른다.
	 *
	 * <p>union 뒤에서 자르면 이미 다 세운 뒤다 — 색인이 318만 행으로 커진 뒤 '공사'(107만 건
	 * 매치)에서 후보 세우기에만 23.1초가 들었다. 미리 자르면 4.5초다(5.2배).
	 *
	 * <p>정확성은 relevance 의 성질에서 온다: 첨부에서만 걸린 행은 0점이고 공고 매치는 0보다
	 * 크다(실측 최소 0.498). 그러니 공고 브랜치가 창을 채우면 첨부 전용 행은 상위에 못 온다.
	 */
	@Test
	@DisplayName("관련도 정렬이면 공고 브랜치를 union 전에 자른다")
	void prunesNoticeBranchBeforeUnion() {
		String sql = BidNoticeIndexRepository.candidateSql(where(List.of("서버"), List.of()), true);

		String noticeBranch = sql.split("UNION ALL")[0];
		assertThat(noticeBranch).contains("LIMIT :innerLimit");
		// 안쪽에 타이브레이커를 붙이면 107만 행의 id 를 함께 세운다 — 1.46s 가 9.63s 가 된다.
		assertThat(noticeBranch).doesNotContain("fid ASC");
	}

	@Test
	@DisplayName("관련도 정렬이 아니면 자르지 않는다 — 뒤쪽 공고가 페이지에서 사라진다")
	void keepsFullBranchForOtherSorts() {
		String sql = BidNoticeIndexRepository.candidateSql(where(List.of("서버"), List.of()), false);

		assertThat(sql).doesNotContain("LIMIT :innerLimit");
	}

	@Test
	@DisplayName("첨부 브랜치는 자르지 않는다 — 거기서 자르면 첨부 전용 매치가 사라진다")
	void neverPrunesDocBranch() {
		String sql = BidNoticeIndexRepository.candidateSql(where(List.of("서버"), List.of()), true);

		String docBranch = sql.split("UNION ALL")[1];
		assertThat(docBranch).contains("MATCH(d.body_text)");
		assertThat(docBranch).doesNotContain("LIMIT");
	}
}
