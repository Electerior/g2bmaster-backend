package com.electerior.g2bmaster.market;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * 개찰 참여업체 저장소 — {@code bid_opening_result} 에 대한 모든 SQL.
 *
 * <p>스키마와 "왜 저장하는가"는 마이그레이션 {@code V20260829231045__bid_opening_result.sql}
 * 머리주석에 있다. 여기서는 <b>읽기 판정</b>만 설명한다.
 *
 * <p><b>참여업체가 한 명이라도 있으면 다시 묻지 않는다.</b> 개찰이 끝나 명단이 공개된
 * 뒤에는 그 값이 바뀌지 않는다. 반대로 빈 결과는 "개찰 전이거나 유찰"이라 언젠가 채워질 수
 * 있으므로 {@link #EMPTY_RETRY_HOURS} 가 지나면 다시 묻는다. 이 비대칭이 이 저장소의 요점이다 —
 * 전부 TTL 로 두면 확정된 자료를 영원히 다시 받게 되고, 전부 영구 저장하면 개찰 전에 한 번
 * 조회된 공고가 영영 빈 채로 남는다.
 */
@Repository
public class BidOpeningResultRepository {

	private static final Logger log = LoggerFactory.getLogger(BidOpeningResultRepository.class);

	/** 빈 결과를 다시 묻기까지의 시간. 개찰은 하루 단위로 진행되므로 6시간이면 촘촘하다. */
	static final int EMPTY_RETRY_HOURS = 6;

	private static final TypeReference<List<Map<String, Object>>> ROWS = new TypeReference<>() { };

	private final NamedParameterJdbcTemplate jdbc;
	private final ObjectMapper json = JsonMapper.builder().build();

	public BidOpeningResultRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * 저장된 참여업체. 다시 물어야 하는 상태면 비어 있는 Optional 이다.
	 *
	 * <p>빈 리스트를 담은 Optional 과 빈 Optional 은 뜻이 다르다 — 앞은 "받아 봤는데 개찰 전
	 * 이었다(아직 유효)", 뒤는 "받은 적이 없거나 다시 물을 때가 됐다"다. 이 구분이 없으면
	 * 개찰 전 공고를 열 때마다 상류를 친다.
	 */
	public Optional<List<Map<String, Object>>> find(String bidNtceNo) {
		String no = bidNtceNo == null ? "" : bidNtceNo.trim();
		if (no.isEmpty()) {
			return Optional.empty();
		}
		List<Map<String, Object>> found = jdbc.query("""
				SELECT participants, participant_count, fetched_at
				  FROM bid_opening_result
				 WHERE bid_ntce_no = :no
				""", new MapSqlParameterSource("no", no), (rs, i) -> Map.of(
						"participants", String.valueOf(rs.getString("participants")),
						"count", rs.getInt("participant_count"),
						"fetchedAt", rs.getObject("fetched_at", LocalDateTime.class)));
		if (found.isEmpty()) {
			return Optional.empty();
		}
		Map<String, Object> row = found.get(0);
		int count = (int) row.get("count");
		if (count == 0 && isStale((LocalDateTime) row.get("fetchedAt"))) {
			return Optional.empty();
		}
		return Optional.of(parse(String.valueOf(row.get("participants")), no));
	}

	private static boolean isStale(LocalDateTime fetchedAt) {
		return fetchedAt == null || fetchedAt.isBefore(LocalDateTime.now().minusHours(EMPTY_RETRY_HOURS));
	}

	private List<Map<String, Object>> parse(String raw, String no) {
		try {
			return json.readValue(raw, ROWS);
		}
		catch (JacksonException ex) {
			// 저장분이 깨졌다고 화면을 죽이지 않는다 — 비었다고 보면 호출부가 상류로 물러선다.
			log.warn("개찰결과 저장분 파싱 실패 {} — {}", no, ex.getMessage());
			return List.of();
		}
	}

	/**
	 * 상류에서 받아 온 원본 행을 그대로 저장한다.
	 *
	 * <p>빈 배열도 저장한다. "받아 봤는데 개찰 전이었다"는 사실 자체가 정보이고, 그것을
	 * 남기지 않으면 개찰 전 공고가 열릴 때마다 상류를 친다.
	 */
	public void save(String bidNtceNo, List<Map<String, Object>> rows) {
		String no = bidNtceNo == null ? "" : bidNtceNo.trim();
		if (no.isEmpty()) {
			return;
		}
		List<Map<String, Object>> safe = rows == null ? List.of() : rows;
		String payload;
		try {
			payload = json.writeValueAsString(safe);
		}
		catch (JacksonException ex) {
			// 저장에 실패해도 이번 응답은 이미 손에 있다 — 화면을 막지 않는다.
			log.warn("개찰결과 직렬화 실패 {} — {}", no, ex.getMessage());
			return;
		}
		jdbc.update("""
				INSERT INTO bid_opening_result (bid_ntce_no, participants, participant_count, fetched_at)
				VALUES (:no, CAST(:payload AS JSON), :count, :now)
				ON DUPLICATE KEY UPDATE
				  participants = VALUES(participants),
				  participant_count = VALUES(participant_count),
				  fetched_at = VALUES(fetched_at)
				""", new MapSqlParameterSource()
						.addValue("no", no)
						.addValue("payload", payload)
						.addValue("count", safe.size())
						.addValue("now", LocalDateTime.now()));
	}

	/**
	 * 아직 받아 온 적이 없는 공고번호 — 백필이 쓴다.
	 *
	 * <p>이미 받은 것은 위의 비대칭 규칙(참여업체가 있으면 영구, 없으면 재시도)이 관리하므로
	 * 여기서는 <b>한 번도 안 받은 것</b>만 낸다. 백필이 빈 결과를 계속 다시 긁으면 개찰 전
	 * 공고가 많은 구간에서 쿼터가 그쪽으로만 흐른다.
	 */
	public List<String> missingSince(LocalDateTime since, int limit) {
		return jdbc.queryForList("""
				SELECT r.bid_ntce_no
				  FROM bid_result r
				  LEFT JOIN bid_opening_result o ON o.bid_ntce_no = r.bid_ntce_no
				 WHERE r.rgst_dt >= :since
				   AND o.bid_ntce_no IS NULL
				 GROUP BY r.bid_ntce_no
				 ORDER BY MAX(r.rgst_dt) DESC
				 LIMIT :limit
				""", new MapSqlParameterSource()
						.addValue("since", since)
						.addValue("limit", limit), String.class);
	}

	/** 저장 현황 — 백필 로그와 시스템 화면이 쓴다. */
	public Map<String, Object> coverage() {
		return jdbc.queryForMap("""
				SELECT COUNT(*) AS stored,
				       COALESCE(SUM(participant_count > 0), 0) AS withParticipants,
				       COALESCE(SUM(participant_count), 0) AS participantRows
				  FROM bid_opening_result
				""", new MapSqlParameterSource());
	}
}
