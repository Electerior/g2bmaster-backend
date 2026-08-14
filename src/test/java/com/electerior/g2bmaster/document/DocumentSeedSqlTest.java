package com.electerior.g2bmaster.document;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 백필 범위 조립.
 *
 * <p>범위를 잘못 조립해도 <b>실패하지 않는다</b> — 그냥 다른 공고 묶음을 몇 시간 동안 내려받는다.
 * 실측에서 마감 축(`close_date > NOW()`)으로 돌던 백필은 "지난주까지 게시된 공고"를 단 한 건도
 * 집지 않았다(그 축에서는 이미 마감된 27,567건이 영영 차례가 오지 않는다). 그래서 조립 규칙을
 * 문자열로 못박아 둔다.
 */
class DocumentSeedSqlTest {

	@Test
	@DisplayName("범위가 없으면 최신 게시순 전량")
	void unscoped() {
		String sql = DocumentIndexRepository.buildSeedSql(false, false);

		assertThat(sql).doesNotContain(":closeBefore").doesNotContain(":createdBefore");
		assertThat(sql).contains("ORDER BY created_date DESC");
		// 이 둘은 어느 범위에서도 빠지면 안 된다 — 빠지면 이미 끝난 공고를 다시 내려받는다.
		assertThat(sql).contains("documents_indexed_at IS NULL").contains("attachment_urls IS NOT NULL");
	}

	@Test
	@DisplayName("마감 축은 아직 안 끝난 공고만, 마감 급한 순")
	void byCloseDate() {
		String sql = DocumentIndexRepository.buildSeedSql(true, false);

		assertThat(sql).contains("close_date > NOW()").contains("close_date < :closeBefore");
		assertThat(sql).contains("ORDER BY close_date ASC");
	}

	@Test
	@DisplayName("게시일 축은 마감 여부를 보지 않는다 — 소급 분석의 대상은 대개 이미 마감됐다")
	void byCreatedDate() {
		String sql = DocumentIndexRepository.buildSeedSql(false, true);

		assertThat(sql).contains("created_date <= :createdBefore");
		// close_date 조건이 섞이면 "지난주까지의 공고" 중 마감된 것이 통째로 빠진다.
		assertThat(sql).doesNotContain("close_date");
		assertThat(sql).contains("ORDER BY created_date DESC");
	}

	@Test
	@DisplayName("둘 다 주면 둘 다 걸리고 순서는 마감 축이 이긴다")
	void bothAxes() {
		String sql = DocumentIndexRepository.buildSeedSql(true, true);

		assertThat(sql).contains("close_date < :closeBefore").contains("created_date <= :createdBefore");
		assertThat(sql).contains("ORDER BY close_date ASC").doesNotContain("ORDER BY created_date");
	}

	// ── 청구(claim) ──────────────────────────────────────────────────────────

	@Test
	@DisplayName("범위가 없으면 조인도 없다 — 전량 백필에 부질없는 조인을 물리지 않는다")
	void claimUnscoped() {
		String sql = DocumentIndexRepository.buildClaimSql(false, false);

		assertThat(sql).doesNotContain("JOIN bid_notice");
		assertThat(sql).contains("d.status IN ('pending', 'failed')").contains("d.retry_count < 3");
	}

	@Test
	@DisplayName("청구도 씨뿌리기와 같은 범위를 본다 — 큐에는 이전 범위의 행이 남아 있다")
	void claimHonoursScope() {
		String sql = DocumentIndexRepository.buildClaimSql(false, true);

		assertThat(sql).contains("JOIN bid_notice n ON n.id = d.notice_id AND n.source = d.source");
		assertThat(sql).contains("n.created_date <= :createdBefore");
	}

	@Test
	@DisplayName("파서를 고치면 영구 실패로 닫아 둔 파일도 다시 집는다")
	void claimReopensSkippedWhenExtractorChanges() {
		String sql = DocumentIndexRepository.buildClaimSql(false, false);

		// 파싱 실패는 즉시 skip 으로 닫는다(재시도해도 같은 결과라서). 그런데 재추출을 부르는
		// 가장 잦은 사유가 우리 파서의 변경이므로(실측 54%), skip 이 재추출 대상에서 빠지면
		// 고친 파서가 정작 그 파일들에는 영영 닿지 못한다.
		assertThat(sql).contains("d.status IN ('done', 'skip') AND d.extractor_version <> :extractorVersion");
		// 아직 한 번도 못 뽑은 것이 먼저다 — 재추출은 그 뒤로 미룬다.
		assertThat(sql).contains("ORDER BY d.status IN ('done', 'skip'), d.id");
	}

	@Test
	@DisplayName("재시도 소진으로 닫힌 행에도 추출기 버전을 찍는다 — 안 찍으면 청구가 영원히 다시 집는다")
	void failStampsTheVersionWhenItCloses() {
		// 청구는 '낡은 버전의 skip' 을 재추출 대상으로 본다. 그래서 버전이 빈 채로 닫히면
		// 그 행은 매 회차 다시 청구된다 — 실측으로 20개 행이 retry_count=109 까지 갔고,
		// 회차가 9초마다 같은 파일을 다시 내려받으며 헛돌았다.
		String claim = DocumentIndexRepository.buildClaimSql(false, false);

		assertThat(claim).contains("d.status IN ('done', 'skip') AND d.extractor_version <> :extractorVersion");
		// 닫는 쪽(fail)과 집는 쪽(claim)이 같은 칸을 보고 있어야 순환이 끝난다.
		assertThat(DocumentIndexRepository.class.getDeclaredMethods())
				.anyMatch(m -> m.getName().equals("fail"));
	}

	@Test
	@DisplayName("상태 조건은 괄호로 묶여야 한다 — 안 그러면 범위가 뒤쪽 갈래에만 붙는다")
	void claimKeepsStatusDisjunctionGrouped() {
		String sql = DocumentIndexRepository.buildClaimSql(false, true);

		// `(A) OR (B) AND 범위` 로 조립되면 범위 밖의 pending 이 전부 다시 새어 든다.
		// 실패하면 SQL 은 멀쩡히 돌고 결과만 조용히 틀리므로, 모양을 문자열로 못박는다.
		int where = sql.indexOf("WHERE");
		int scope = sql.indexOf("AND n.created_date");
		String condition = sql.substring(where + "WHERE".length(), scope).trim();
		assertThat(condition).startsWith("((").endsWith("))");
	}
}
