package com.electerior.g2bmaster.market;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * 개찰 결과 저장분을 <b>언제 믿고 언제 다시 묻는가</b>.
 *
 * <p>여기서 틀리면 조용히 둘 중 하나가 된다 — 확정된 명단을 영원히 다시 받아 오거나(쿼터),
 * 개찰 전에 한 번 조회된 공고가 영영 빈 채로 남는다(화면). 둘 다 오류가 나지 않는다.
 */
class BidOpeningResultRepositoryTest {

	private static final String NO = "R26BK01679856";

	@SuppressWarnings("unchecked")
	private static NamedParameterJdbcTemplate jdbcReturning(int count, LocalDateTime fetchedAt,
			String participants) {
		NamedParameterJdbcTemplate jdbc = mock(NamedParameterJdbcTemplate.class);
		when(jdbc.query(anyString(), any(SqlParameterSource.class), any(RowMapper.class)))
				.thenReturn(List.of(Map.of(
						"participants", participants,
						"count", count,
						"fetchedAt", fetchedAt)));
		return jdbc;
	}

	@Test
	@DisplayName("참여업체가 있으면 오래돼도 다시 묻지 않는다 — 개찰이 끝난 값은 안 바뀐다")
	void keepsNonEmptyForever() {
		var repo = new BidOpeningResultRepository(
				jdbcReturning(2, LocalDateTime.now().minusDays(90), "[{\"prcbdrNm\":\"가\"},{\"prcbdrNm\":\"나\"}]"));

		Optional<List<Map<String, Object>>> found = repo.find(NO);

		assertThat(found).isPresent();
		assertThat(found.get()).hasSize(2);
	}

	@Test
	@DisplayName("빈 결과는 TTL 안에서는 그대로 쓴다 — 개찰 전 공고를 열 때마다 상류를 치지 않는다")
	void reusesFreshEmpty() {
		var repo = new BidOpeningResultRepository(
				jdbcReturning(0, LocalDateTime.now().minusMinutes(5), "[]"));

		Optional<List<Map<String, Object>>> found = repo.find(NO);

		// 빈 리스트를 담은 Optional 과 빈 Optional 은 뜻이 다르다 — 앞은 "받아 봤는데 개찰 전".
		assertThat(found).isPresent();
		assertThat(found.get()).isEmpty();
	}

	@Test
	@DisplayName("빈 결과가 TTL 을 넘기면 다시 묻는다 — 개찰은 언젠가 끝난다")
	void refetchesStaleEmpty() {
		var repo = new BidOpeningResultRepository(jdbcReturning(0,
				LocalDateTime.now().minusHours(BidOpeningResultRepository.EMPTY_RETRY_HOURS + 1), "[]"));

		assertThat(repo.find(NO)).isEmpty();
	}

	@Test
	@DisplayName("저장분이 깨져 있어도 화면을 죽이지 않는다")
	void survivesCorruptJson() {
		var repo = new BidOpeningResultRepository(
				jdbcReturning(3, LocalDateTime.now(), "{ 이건 JSON 이 아니다"));

		assertThat(repo.find(NO)).contains(List.of());
	}

	@Test
	@DisplayName("빈 배열도 저장한다 — 저장하지 않으면 개찰 전 공고가 매번 상류를 친다")
	void storesEmptyResult() {
		NamedParameterJdbcTemplate jdbc = mock(NamedParameterJdbcTemplate.class);
		var repo = new BidOpeningResultRepository(jdbc);

		repo.save(NO, List.of());

		ArgumentCaptor<MapSqlParameterSource> params = ArgumentCaptor.forClass(MapSqlParameterSource.class);
		verify(jdbc).update(anyString(), params.capture());
		assertThat(params.getValue().getValue("count")).isEqualTo(0);
		assertThat(params.getValue().getValue("payload")).isEqualTo("[]");
	}

	@Test
	@DisplayName("공고번호가 비면 아무 SQL 도 내지 않는다")
	void ignoresBlankNotice() {
		NamedParameterJdbcTemplate jdbc = mock(NamedParameterJdbcTemplate.class);
		var repo = new BidOpeningResultRepository(jdbc);

		repo.save("  ", List.of(Map.of("prcbdrNm", "가")));
		assertThat(repo.find("")).isEmpty();

		verify(jdbc, never()).update(anyString(), any(SqlParameterSource.class));
	}
}
