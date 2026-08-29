package com.electerior.g2bmaster.notice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * 낙찰정보 색인 SQL 의 계약.
 *
 * <p>이 저장소의 SQL 은 실행 없이 문자열로만 검증한다({@code BidNoticeUpsertSqlTest} 와 같은
 * 방식이다). 그래서 <b>문법 오류는 테스트가 아니라 운영에서 처음 터진다</b> — 여기 못박는 것은
 * 그중 실제로 밟은 적 있는 지뢰들이다.
 */
class BidResultSqlTest {

	private static final String SQL = BidResultRepository.buildUpsertSql();

	/**
	 * {@code row} 는 MySQL 8.0.2 부터 예약어다. 백틱을 빼면 {@code INSERT INTO bid_result (...,
	 * row, ...)} 가 문법 오류가 되는데, 이 SQL 은 적재가 처음 도는 순간에만 실행되므로 배포하고
	 * 한참 뒤에야 드러난다. 마이그레이션도 같은 이유로 백틱을 쓰고 있다.
	 */
	@Test
	void row_컬럼은_예약어라_백틱으로_감싼다() {
		assertThat(SQL).contains("`row`");
		// 백틱 없는 맨 row 가 컬럼 자리에 남아 있으면 안 된다.
		assertThat(SQL).doesNotContain(" row ").doesNotContain(",row").doesNotContain("(row");
	}

	/**
	 * 낙찰정보에는 정정 차수가 없다 — 같은 키가 다시 오면 최신 응답이 그냥 옳다. 공고 쪽의
	 * 차수 가드({@code IF(new.notice_order >= ...)})를 흉내 내 넣으면 갱신이 조용히 막힌다.
	 */
	@Test
	void 같은_키가_다시_오면_덮어쓴다() {
		assertThat(SQL).contains("ON DUPLICATE KEY UPDATE");
		assertThat(SQL).doesNotContain(" = IF(");
	}

	/** 조회 창의 앵커이자 워터마크 축이다. 이 칸이 갱신에서 빠지면 정정된 등록일시가 낡는다. */
	@Test
	void 등록일시도_함께_갱신한다() {
		String updates = SQL.substring(SQL.indexOf("ON DUPLICATE KEY UPDATE"));
		assertThat(updates).contains("rgst_dt = new.rgst_dt");
	}

	/** MySQL 8 의 별칭 형식이다. 구식 {@code VALUES()} 는 8.0.20 부터 deprecated 다. */
	@Test
	void 신형_별칭_문법을_쓴다() {
		assertThat(SQL).contains("AS new");
		assertThat(SQL).doesNotContain("VALUES(");
	}
}
