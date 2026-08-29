package com.electerior.g2bmaster.notice;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * 낙찰정보 색인 저장소 — {@code bid_result} 에 대한 모든 SQL.
 *
 * <p>{@link BidNoticeIndexRepository} 와 갈라 놓은 이유는 마이그레이션
 * {@code V20260826120000__bid_result.sql} 머리주석에 있다 — 공고 색인은 검색이 쓰는 컬럼만
 * 남긴 narrow 색인이고, 낙찰 결과는 개찰·낙찰업체·낙찰가처럼 컬럼 집합이 전혀 달라 거기에
 * 얹으면 그 narrow 가 오염된다.
 *
 * <p><b>행 전체를 JSON 한 칸에 담는다.</b> 좁은 컬럼만 남기면 프론트가 쓰는 필드가 누락되거나
 * 필드가 늘 때마다 마이그레이션이 필요하다. 조회 경로는 이 JSON 을 다시 Map 으로 풀어
 * {@link BidResultService} 의 기존 in-memory 필터를 그대로 돌리므로, 응답이 라이브 경로와
 * 같은 모양을 유지한다.
 *
 * <p>이 저장소를 {@code index} 가 아니라 {@code notice} 패키지에 둔 것은 읽는 쪽이 여기
 * 하나뿐이기 때문이다({@link BidResultService}). 쓰는 쪽인 적재기는 {@code index} 에 있다 —
 * 상류를 두드리는 코드는 전부 그 패키지에 모아 두는 것이 이 저장소의 관례다.
 *
 * <p>스키마 계약은 {@code BidResultRepositoryTest} 가 SQL 문자열로 지킨다({@code bid_notice}
 * 쪽과 같은 방식이다 — 실행하지 않고 문자열을 못박는다).
 */
@Repository
public class BidResultRepository {

	private static final Logger log = LoggerFactory.getLogger(BidResultRepository.class);

	private static final ObjectMapper JSON = JsonMapper.builder().build();

	/**
	 * 한 번의 조회가 메모리에 올릴 행 수 상한.
	 *
	 * <p>낙찰정보는 한 달 약 6,900건(실측)이라 기본 7일 창이면 1,600건 안팎이다. 다만 사용자가
	 * 기간을 몇 년으로 넓히면 그만큼이 통째로 힙에 올라오고, 그 뒤의 haystack 필터가 행마다
	 * 도므로 응답이 아니라 <b>프로세스</b>가 먼저 위험해진다. 상한에 걸리면 최신 등록분부터
	 * 남기고 잘라내되, 잘랐다는 사실을 로그로 남긴다 — 조용히 자르면 "그 기간엔 그것뿐"으로
	 * 읽힌다.
	 */
	public static final int MAX_ROWS = 20_000;

	/** 적재기가 넘기는 행 하나. {@code row} 는 이미 직렬화된 JSON 문자열이다. */
	public record Row(String bidNtceNo, String bidType, String row, LocalDateTime rgstDt) {}

	private final NamedParameterJdbcTemplate jdbc;

	public BidResultRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	// ── 적재 ────────────────────────────────────────────────────────────────

	/**
	 * 배치 upsert.
	 *
	 * @return 영향받은 행 수 합계(MySQL 은 INSERT 를 1, UPDATE 를 2로 세므로 건수와 다르다)
	 */
	public int upsertAll(List<Row> rows) {
		if (rows == null || rows.isEmpty()) {
			return 0;
		}
		SqlParameterSource[] batch = rows.stream().map(BidResultRepository::bind)
				.toArray(SqlParameterSource[]::new);
		int[] affected = jdbc.batchUpdate(buildUpsertSql(), batch);
		int total = 0;
		for (int count : affected) {
			// executeBatch 는 건별 결과를 모를 때 SUCCESS_NO_INFO(-2)를 준다. 음수를 그대로
			// 더하면 "-2건 색인" 같은 로그가 나오므로 0으로 접는다.
			total += Math.max(count, 0);
		}
		return total;
	}

	/**
	 * upsert SQL.
	 *
	 * <p><b>차수 가드가 없는 것이 {@code bid_notice} 와 다른 점이다.</b> 낙찰정보에는 정정 차수
	 * 개념이 없어(응답의 {@code bidNtceOrd} 는 원공고의 차수를 옮겨 적은 값이다) 같은 키가
	 * 다시 오면 그냥 최신 응답이 옳다. 대신 같은 배치 안의 중복은 적재기가 미리 접는다 —
	 * batchUpdate 안에서 같은 PK 를 두 번 건드리면 갱신 순서가 보장되지 않는다.
	 *
	 * <p>{@code row} 는 MySQL 8 예약어라 백틱이 필수다. 빼면 문법 오류가 나는데, 이 저장소의
	 * SQL 은 실행 없이 문자열로만 검증되므로 <b>테스트가 아니라 운영에서</b> 처음 터진다.
	 */
	static String buildUpsertSql() {
		return """
				INSERT INTO bid_result (bid_ntce_no, bid_type, `row`, rgst_dt)
				VALUES (:bidNtceNo, :bidType, :row, :rgstDt)
				AS new
				ON DUPLICATE KEY UPDATE
				  `row` = new.`row`,
				  rgst_dt = new.rgst_dt
				""";
	}

	private static SqlParameterSource bind(Row row) {
		return new MapSqlParameterSource()
				.addValue("bidNtceNo", row.bidNtceNo())
				.addValue("bidType", row.bidType())
				// JSON 컬럼에 null 을 넣을 때는 타입을 명시해야 한다. 안 그러면 드라이버가
				// 문자열 'null' 로 보내 JSON 파싱 오류가 난다(bid_notice 쪽과 같은 이유).
				.addValue("row", row.row(), Types.VARCHAR)
				.addValue("rgstDt", row.rgstDt());
	}

	// ── 조회 ────────────────────────────────────────────────────────────────

	/**
	 * 등록일시 창 안의 낙찰정보를 보강된 행 그대로 돌려준다.
	 *
	 * <p><b>{@code toExclusive} 가 배타인 것이 중요하다.</b> 화면의 종료일은 23:59 로 채워져
	 * 들어오는데({@link SearchCriteria#dates()}), {@code rgst_dt} 는 {@code DATETIME(6)} 이라
	 * {@code <= 23:59:00} 으로 걸면 그 뒤 59초에 등록된 건이 통째로 빠진다. 호출부가 창 끝을
	 * 한 칸 밀어 배타로 넘긴다.
	 *
	 * <p>정렬을 {@code rgst_dt DESC} 로 고정한 것은 상한({@link #MAX_ROWS})에 걸렸을 때
	 * <b>무엇이 남는가</b>를 정하기 위해서다 — 잘려야 한다면 오래된 쪽이 잘리는 편이 낫다.
	 * 최종 정렬은 어차피 호출부가 BM25 나 지정 정렬로 다시 한다.
	 *
	 * @param bidTypes 물품/용역/공사 중 볼 것들. 비면 빈 결과다(호출부가 채워 넘긴다)
	 */
	/**
	 * 공고번호 하나의 낙찰정보. <b>등록일시 창을 보지 않는다.</b>
	 *
	 * <p>공고번호를 알고 묻는 조회에 날짜창은 방해만 된다 — 창의 축은 낙찰 등록일시인데
	 * 사용자가 들고 온 것은 공고이고, 둘은 몇 주씩 떨어져 있다. 실제로 공고 검색에서
	 * "낙찰결과 →" 로 넘어오면 공고일 기준 창이 그대로 실려 와, 색인에 행이 있어도 빗나갔다.
	 *
	 * <p>PK 가 {@code (공고번호, 업종)} 이라 한 공고가 두 업종으로 들어와 있을 수 있다.
	 * 그대로 다 준다 — 접는 것은 호출부의 일이다.
	 */
	public List<Map<String, Object>> findByBidNtceNo(String bidNtceNo) {
		if (bidNtceNo == null || bidNtceNo.isBlank()) {
			return List.of();
		}
		List<Map<String, Object>> rows = jdbc.query("""
				SELECT `row`
				  FROM bid_result
				 WHERE bid_ntce_no = :no
				 ORDER BY rgst_dt DESC
				""", new MapSqlParameterSource().addValue("no", bidNtceNo.trim()),
				(rs, n) -> parseRow(rs.getString("row")));

		List<Map<String, Object>> items = new ArrayList<>(rows.size());
		for (Map<String, Object> row : rows) {
			if (row != null) {
				items.add(row);
			}
		}
		if (items.size() < rows.size()) {
			log.warn("낙찰정보 색인에서 읽지 못한 행이 있습니다 — 공고 {} ({}건 중 {}건만 냅니다).",
					bidNtceNo, rows.size(), items.size());
		}
		return items;
	}

	public List<Map<String, Object>> findWindow(LocalDateTime fromInclusive, LocalDateTime toExclusive,
			Collection<String> bidTypes, int limit) {
		if (bidTypes == null || bidTypes.isEmpty()) {
			return List.of();
		}
		int cap = Math.min(Math.max(limit, 1), MAX_ROWS);
		List<Map<String, Object>> rows = jdbc.query("""
				SELECT `row`
				  FROM bid_result
				 WHERE rgst_dt >= :from
				   AND rgst_dt < :to
				   AND bid_type IN (:types)
				 ORDER BY rgst_dt DESC
				 LIMIT :limit
				""", new MapSqlParameterSource()
						.addValue("from", fromInclusive)
						.addValue("to", toExclusive)
						.addValue("types", bidTypes)
						.addValue("limit", cap),
				(rs, n) -> parseRow(rs.getString("row")));

		List<Map<String, Object>> items = new ArrayList<>(rows.size());
		int broken = 0;
		for (Map<String, Object> row : rows) {
			if (row == null) {
				broken++;
				continue;
			}
			items.add(row);
		}
		if (broken > 0) {
			// 깨진 JSON 이 있어도 조회 전체를 500 으로 만들지 않는다 — 그 행만 빼고 넘어간다.
			// 색인 데이터의 흠이 조회 가능성을 무너뜨려서는 안 된다(bid_notice 쪽과 같은 원칙).
			log.warn("낙찰정보 색인에서 읽지 못한 행이 {}건 있습니다 — 그만큼 결과에서 빠집니다.", broken);
		}
		// 잘렸는지는 읽어 온 행 수로 판단한다 — 깨진 행을 뺀 뒤의 수로 보면 상한에 걸렸는데도
		// 안 걸린 것으로 읽힌다.
		if (rows.size() >= cap) {
			log.warn("낙찰정보 조회가 상한 {}건에 걸렸습니다 — {} ~ {} 구간의 오래된 쪽이 잘렸습니다.",
					cap, fromInclusive, toExclusive);
		}
		return items;
	}

	/**
	 * 저장된 JSON 한 행을 Map 으로 되푼다.
	 *
	 * <p>{@code LinkedHashMap} 으로 감싸는 이유는 호출부가 <b>가변 맵</b>을 기대하기 때문이다 —
	 * 라이브 경로에서 넘어오던 맵이 그랬고, 필터·정렬이 필드를 얹는 자리가 있다.
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Object> parseRow(String json) {
		if (json == null || json.isBlank()) {
			return null;
		}
		try {
			Object parsed = JSON.readValue(json, Object.class);
			return parsed instanceof Map<?, ?> map ? new LinkedHashMap<>((Map<String, Object>) map) : null;
		}
		catch (JacksonException ex) {
			log.debug("낙찰정보 색인 JSON 을 읽지 못했습니다: {}", ex.getMessage());
			return null;
		}
	}
}
