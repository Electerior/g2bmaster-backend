package com.electerior.g2bmaster.document;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 씨뿌리기·청구 질의의 모양.
 *
 * <p>이 두 질의는 <b>틀려도 실패하지 않는다</b> — 그냥 엉뚱한 묶음을 몇 시간 동안 내려받는다.
 * 예전에 절대 시각으로 백필 창을 잡던 시절에는 창이 낡은 줄 모르고 워커가 매 회차 0건을 집으며
 * 돌았다(실측: 미색인 14,523건이 전부 창 밖). 지금은 창이 없고 순서가 전부라, 그 순서를
 * 문자열로 못박아 둔다.
 */
class DocumentSeedSqlTest {

	@Test
	@DisplayName("씨뿌리기는 언제나 최신 게시순 전량 — 범위를 자르는 파라미터가 없다")
	void seedIsAlwaysNewestFirst() {
		String sql = DocumentIndexRepository.buildSeedSql();

		assertThat(sql).contains("ORDER BY created_date DESC");
		// 창을 다시 들이면 그 창은 반드시 낡는다. 파라미터가 생기는 것 자체를 막는다.
		assertThat(sql).doesNotContain(":closeBefore").doesNotContain(":createdBefore");
		// 이 둘이 빠지면 이미 끝난 공고를 다시 내려받는다.
		assertThat(sql).contains("documents_indexed_at IS NULL").contains("attachment_urls IS NOT NULL");
	}

	@Test
	@DisplayName("청구도 최신 공고 먼저 — 씨뿌리기와 순서가 어긋나면 최신순은 이름만 남는다")
	void claimFollowsTheSameOrder() {
		String sql = DocumentIndexRepository.buildClaimSql();

		// 게시일로 정렬하려면 공고를 붙여야 한다. 조인이 빠지면 정렬 기준 자체가 사라진다.
		assertThat(sql).contains("JOIN bid_notice n ON n.id = d.notice_id AND n.source = d.source");
		assertThat(sql).contains("n.created_date DESC");
		// d.id 는 세운 순서(=가장 오래된 것 먼저)라 단독으로 쓰면 최신순을 정확히 뒤집는다.
		// 동점 처리로만 남아야 한다.
		assertThat(sql.indexOf("n.created_date DESC")).isLessThan(sql.indexOf("d.id\n"));
	}

	@Test
	@DisplayName("아직 한 번도 못 뽑은 것이 재추출보다 먼저다")
	void freshBeforeReExtract() {
		String sql = DocumentIndexRepository.buildClaimSql();

		// 거짓=0 이 먼저 정렬된다. 파서를 올려 전량 재추출이 걸린 날에도 신규가 뒤로 밀리면
		// 안 된다 — 재추출은 이미 검색되는 문서를 다듬는 일이고, 신규는 아예 안 걸리는 문서다.
		assertThat(sql).contains("ORDER BY d.status IN ('done', 'skip')");
	}

	@Test
	@DisplayName("파서를 고치면 영구 실패로 닫아 둔 파일도 다시 집는다")
	void claimReopensSkippedWhenExtractorChanges() {
		String sql = DocumentIndexRepository.buildClaimSql();

		// 파싱 실패는 즉시 skip 으로 닫는다(재시도해도 같은 결과라서). 그런데 재추출을 부르는
		// 가장 잦은 사유가 우리 파서의 변경이므로(실측 54%), skip 이 재추출 대상에서 빠지면
		// 고친 파서가 정작 그 파일들에는 영영 닿지 못한다.
		assertThat(sql).contains("d.status IN ('done', 'skip') AND d.extractor_version <> :extractorVersion");
	}

	@Test
	@DisplayName("재시도 소진으로 닫힌 행에도 추출기 버전을 찍는다 — 안 찍으면 청구가 영원히 다시 집는다")
	void failStampsTheVersionWhenItCloses() {
		// 청구는 '낡은 버전의 skip' 을 재추출 대상으로 본다. 그래서 버전이 빈 채로 닫히면
		// 그 행은 매 회차 다시 청구된다 — 실측으로 20개 행이 retry_count=109 까지 갔고,
		// 회차가 9초마다 같은 파일을 다시 내려받으며 헛돌았다.
		String claim = DocumentIndexRepository.buildClaimSql();

		assertThat(claim).contains("d.status IN ('done', 'skip') AND d.extractor_version <> :extractorVersion");
		// 닫는 쪽(fail)과 집는 쪽(claim)이 같은 칸을 보고 있어야 순환이 끝난다.
		assertThat(DocumentIndexRepository.class.getDeclaredMethods())
				.anyMatch(m -> m.getName().equals("fail"));
	}

	@Test
	@DisplayName("상태 조건은 괄호로 묶여 있어야 한다 — 조건을 하나 더 붙이는 다음 사람을 위한 못이다")
	void claimKeepsStatusDisjunctionGrouped() {
		String sql = DocumentIndexRepository.buildClaimSql();

		// `(A) OR (B) AND 조건` 으로 조립되면 AND 가 뒤쪽 갈래에만 붙어 앞쪽이 통째로 새어 든다.
		// 실패하면 SQL 은 멀쩡히 돌고 결과만 조용히 틀리므로, 모양을 문자열로 못박는다.
		int where = sql.indexOf("WHERE");
		int order = sql.indexOf("ORDER BY");
		String condition = sql.substring(where + "WHERE".length(), order).trim();
		assertThat(condition).startsWith("((").endsWith("))");
	}
}
