package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

/**
 * ngram FULLTEXT 색인의 <b>내용물</b>을 검증한다 — 이 패키지에서 유일하게 DB 를 띄우는 테스트다.
 *
 * <h2>왜 SQL 문자열 검증으로는 못 잡는가</h2>
 * <p>2026-08-14 에 "AMD" 검색이 MDR·GMDSS·MDF 를 데려오는 버그가 있었다. 그때
 * {@link BidNoticeQueryBuilder} 가 만든 SQL 은 <b>처음부터 끝까지 정확했다.</b> 망가진 것은
 * 그 SQL 이 조회하는 색인의 내용물이었다 — ngram 파서는 stopword 를 '같으면'이 아니라
 * '포함하면' 버리는데, InnoDB 기본 목록에 한 글자 {@code a}·{@code i} 가 들어 있어
 * {@code a} 나 {@code i} 가 든 2글자 토큰이 색인에서 통째로 빠져 있었다.
 * {@code AMD} 는 {@code [AM][MD]} 로 쪼개지고 {@code AM} 이 사라져 사실상 {@code MD} 검색이었다.
 *
 * <p>그래서 이 패키지의 다른 테스트들(문자열 계약)은 전부 통과한 채로 버그가 살아 있었다.
 * 잡으려면 진짜 색인을 만들고 결과를 보는 수밖에 없다. h2 는 대안이 아니다 — ngram 파서가
 * 없어서 증상 자체가 재현되지 않는다.
 *
 * <h2>수정은 서버 설정이다 — 세션 설정으로는 유지되지 않았다</h2>
 * <p>처음에는 빈 stopword 테이블을 {@code SET SESSION innodb_ft_user_stopword_table} 로 물려
 * 색인을 만들었다. 만든 직후에는 옳았지만 <b>유지되지 않았다.</b> 실측으로 두 번 무너졌다.
 * <ol>
 *   <li>다른 세션이 {@code ALTER TABLE … ADD COLUMN} 을 하면(FULLTEXT 가 있는 테이블은 컬럼
 *       추가가 테이블 재구축을 부른다) 색인이 다시 만들어지면서 기본 목록으로 돌아갔다 —
 *       {@code AMD} 가 다시 {@code GMDSS}·{@code MDR} 을 데려왔다.</li>
 *   <li>행이 들어오고 서버가 재기동한 뒤 공고 색인의 {@code AM} 토큰이 0개가 되어 있었다.
 *       같은 방식으로 만든 첨부 색인은 그대로였다 — 즉 <b>언제 무너질지 예측할 수 없다.</b></li>
 * </ol>
 *
 * <p>그래서 서버 기동 옵션 {@code --innodb-ft-enable-stopword=OFF} 로 옮겼다. 전역값은 새
 * 세션이 물려받으므로 누가 언제 무슨 DDL 로 테이블을 재구축하든 유지된다. 같은 실험을
 * 전역 설정으로 하면 재구축 후에도 {@code AMD}·{@code RAM} 이 옳았다.
 *
 * <p><b>이 테스트의 컨테이너가 그 플래그를 주는 이유가 그것이다.</b> 운영 MySQL 도 같은
 * 옵션으로 떠야 한다 — 빠지면 다음 재구축에서 조용히 버그가 돌아온다. 이 테스트는 그
 * 설정을 <em>전제</em>로 "그 위에서 마이그레이션과 재구축이 검색을 지키는가"를 본다.
 * 운영 설정 자체가 빠진 것은 여기서 못 잡으니 README 의 운영 항목을 같이 볼 것.
 *
 * <h2>{@code @BeforeAll} 이 일부러 테이블을 재구축하는 이유</h2>
 * <p>단언 전에 {@code ADD COLUMN} 을 한 번 끼워 넣는다. 위 1번이 정확히 그 경로이고,
 * 그것을 지나지 않으면 이 테스트는 <b>망가진 설정에서도 통과한다</b>(실제로 그랬다).
 *
 * <h2>실행 조건</h2>
 * <p>Docker 가 필요하다. 없으면 클래스째 건너뛴다({@code disabledWithoutDocker}) — 없는
 * 환경에서 빌드를 깨는 것보다는 낫지만, <b>CI 에 Docker 가 없으면 이 테스트는 아무것도
 * 지키지 못한다.</b> 파이프라인을 손볼 때 그 점을 확인할 것.
 */
@Testcontainers(disabledWithoutDocker = true)
@DisplayName("ngram FULLTEXT 색인 — 실제 MySQL")
class NgramFullTextIndexTest {

	/**
	 * 운영 MySQL 과 같은 기동 옵션이어야 한다. 여기만 고치고 운영을 안 고치면 테스트가
	 * 거짓 안심을 준다 — 반대도 마찬가지다.
	 *
	 * <p>{@code --ngram-token-size=2} 는 MySQL 기본값과 같지만 명시한다. 이 테스트의 단언이
	 * 전부 "2글자 토큰"을 전제로 하기 때문이다.
	 */
	@Container
	static final MySQLContainer MYSQL = new MySQLContainer("mysql:8.0")
			.withDatabaseName("g2b")
			.withCommand("mysqld", "--ngram-token-size=2", "--innodb-ft-enable-stopword=OFF");

	private static NamedParameterJdbcTemplate jdbc;

	/**
	 * 표본은 일부러 작게 둔다. 이 테스트가 보는 것은 건수가 아니라 <b>토큰이 색인에 들어갔는가</b>
	 * 이고, 그건 몇 줄로 갈린다.
	 */
	@BeforeAll
	static void migrateSeedAndRebuild() throws SQLException {
		Flyway.configure()
				.dataSource(MYSQL.getJdbcUrl(), MYSQL.getUsername(), MYSQL.getPassword())
				.locations("classpath:db/migration")
				.load()
				.migrate();

		DriverManagerDataSource ds = new DriverManagerDataSource(
				MYSQL.getJdbcUrl(), MYSQL.getUsername(), MYSQL.getPassword());
		jdbc = new NamedParameterJdbcTemplate(ds);

		// 색인은 마이그레이션이 이미 만들었고, 행은 그 뒤에 들어간다 — 운영과 같은 순서다.
		try (Connection c = ds.getConnection();
				PreparedStatement ps = c.prepareStatement(
						"INSERT INTO bid_notice (id, notice_name, notice_body, category, business_division)"
								+ " VALUES (?, ?, '', '입찰', '물품')")) {
			insert(ps, "N-AMD", "AMD 라이젠 서버 구매");
			insert(ps, "N-AMDB", "전자공항지도(AMDB) 시스템 감리 용역");
			insert(ps, "N-MDR", "더케이제주호텔 MDR 서비스 업체 선정");   // AMD 가 아니다
			insert(ps, "N-GMDSS", "경남해양과학고등학교 GMDSS 시뮬레이터"); // AMD 가 아니다
			insert(ps, "N-RAM", "연구용 워크스테이션 RAM 32GB 증설");
			insert(ps, "N-KOR", "노트북서버구매 및 데이터베이스 구축 용역");
			ps.executeBatch();
		}

		// ── 여기가 이 테스트의 핵심이다 ──────────────────────────────────────
		// 나중에 누군가 bid_notice 에 컬럼을 붙이는 상황을 그대로 재현한다. FULLTEXT 가 있는
		// 테이블은 컬럼 추가가 테이블 재구축을 부르고, 그때 색인이 <b>다시</b> 만들어진다.
		// 재구축이 무엇을 물려받느냐가 이 수정의 성패다 — 서버 전역 설정이라야 살아남는다.
		// 이 줄을 지우면 테스트는 망가진 설정에서도 통과한다.
		try (Connection c = ds.getConnection(); Statement st = c.createStatement()) {
			st.execute("ALTER TABLE bid_notice ADD COLUMN zz_rebuild_probe BIGINT DEFAULT NULL");
		}
	}

	private static void insert(PreparedStatement ps, String id, String name) throws SQLException {
		ps.setString(1, id);
		ps.setString(2, name);
		ps.addBatch();
	}

	/** 쿼리 빌더가 만든 조건을 진짜 색인에 대고 실행한다 — 문자열이 아니라 결과를 본다. */
	private static List<String> search(String term) {
		BidNoticeQueryBuilder.Where where = new BidNoticeQueryBuilder()
				.keywords(List.of(term), List.of(), List.of())
				.build();
		return jdbc.queryForList(
				"SELECT n.id FROM bid_notice n" + where.sql(), where.params(), String.class);
	}

	/**
	 * 원래 버그 그대로의 재현이다. {@code AM} 이 색인에서 빠지면 {@code "AMD"} 는 {@code MD}
	 * 검색이 되어 MDR·GMDSS 가 딸려 온다.
	 */
	@Test
	@DisplayName("AMD 는 AMD 를 품은 공고만 — MD 만 든 공고를 데려오지 않는다")
	void amdDoesNotMatchMdOnly() {
		assertThat(search("AMD")).containsExactlyInAnyOrder("N-AMD", "N-AMDB");
	}

	/**
	 * 반대 방향의 증상. {@code RAM} 은 {@code [RA][AM]} 인데 둘 다 {@code a} 를 품어서, 기본
	 * stopword 목록에서는 토큰이 전멸해 <b>0건</b>이 된다. 0건은 오류로 보이지 않아서 더 나쁘다.
	 */
	@Test
	@DisplayName("모든 2글자 토큰이 a 를 품은 낱말도 찾는다 — RAM 이 0건이 되지 않는다")
	void ramIsFoundAtAll() {
		assertThat(search("RAM")).containsExactly("N-RAM");
	}

	/**
	 * stopword 를 끄면서 ngram 자체를 망가뜨리지 않았는지 본다. 이게 깨지면 한국어 부분일치라는
	 * ngram 을 쓰는 이유가 사라진 것이다.
	 */
	@Test
	@DisplayName("한국어 부분일치는 그대로 — 복합어 한가운데 박힌 낱말도 찾는다")
	void koreanSubstringStillWorks() {
		assertThat(search("서버")).contains("N-KOR");
	}

	/**
	 * 위 단언들이 무엇에 기대고 있는지를 못박는다. 이 값이 {@code ON} 이면 다른 실패들의
	 * 원인이 곧바로 읽힌다 — 운영 MySQL 기동 옵션이 빠졌다는 뜻이다.
	 */
	@Test
	@DisplayName("서버 stopword 가 꺼져 있다 — 이 수정이 딛고 선 전제")
	void serverStopwordIsDisabled() {
		String enabled = jdbc.queryForObject(
				"SELECT @@global.innodb_ft_enable_stopword", Map.of(), String.class);
		assertThat(enabled).isEqualTo("0");
	}
}
