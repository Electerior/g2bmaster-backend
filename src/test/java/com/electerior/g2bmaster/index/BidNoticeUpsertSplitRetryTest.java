package com.electerior.g2bmaster.index;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * 배치 하나가 거부될 때 <b>나머지가 살아남는가</b>.
 *
 * <p>이 테스트가 있는 이유는 실제로 당해서다. {@code rewriteBatchedStatements=true} 라 배치는
 * 서버에서 다중행 INSERT <b>한 문장</b>이 되고, 한 행이 거부되면 문장 전체가 롤백된다.
 * 5년 백필에서 {@code backfill:bid-announce:물품} 이 2021-12 구간의 "Out of range value" 한 건에
 * 걸려 다섯 회차 연속 0건이었고, 실패하면 워터마크를 전진시키지 않는 규칙 때문에 그 출처는
 * <b>영원히</b> 같은 구간을 다시 시도했다. 평시 증분(회차당 20~30행)에서는 드러나지 않는다 —
 * 과거를 훑을 때만 밟는 종류다.
 */
class BidNoticeUpsertSplitRetryTest {

	private static BidNoticeRow row(String id) {
		return new BidNoticeRow(id, NoticeSource.G2B, "000", "공고 " + id, NoticeCategory.입찰, null,
				BusinessDivision.물품, "", null, null, null, null, null, null, null, null, null,
				LocalDateTime.of(2021, 12, 1, 0, 0), null, null, null, "본문", null, null, null, null, null);
	}

	/** 지정한 id 가 묶음에 들어 있으면 통째로 거부하는 가짜 JDBC — MySQL 의 문장 단위 롤백을 흉내낸다. */
	private static NamedParameterJdbcTemplate poisonedBy(String badId, List<List<String>> attempts) {
		NamedParameterJdbcTemplate jdbc = mock(NamedParameterJdbcTemplate.class);
		when(jdbc.batchUpdate(anyString(), any(SqlParameterSource[].class))).thenAnswer(call -> {
			SqlParameterSource[] batch = call.getArgument(1);
			List<String> ids = new ArrayList<>();
			for (SqlParameterSource p : batch) {
				ids.add(String.valueOf(p.getValue("id")));
			}
			attempts.add(ids);
			if (ids.contains(badId)) {
				throw new DataIntegrityViolationException("PreparedStatementCallback; SQL [INSERT ...]",
						new java.sql.SQLException("Data truncation: Out of range value for column '(null)' at row 1"));
			}
			int[] affected = new int[batch.length];
			java.util.Arrays.fill(affected, 1);
			return affected;
		});
		return jdbc;
	}

	@Test
	void 썩은_한_행만_버리고_나머지는_전부_들어간다() {
		List<List<String>> attempts = new ArrayList<>();
		BidNoticeIndexRepository repo = new BidNoticeIndexRepository(poisonedBy("bad", attempts));

		List<BidNoticeRow> rows = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			rows.add(row("ok" + i));
		}
		rows.add(3, row("bad"));

		// 8건 중 7건이 들어간다. 예전 구현은 여기서 0을 돌려주고 출처를 통째로 멈췄다.
		assertThat(repo.upsertAll(rows)).isEqualTo(7);

		// 마지막에는 반드시 한 건짜리 묶음으로 좁혀져 그 행이 특정된다.
		assertThat(attempts).anyMatch(ids -> ids.equals(List.of("bad")));
		// 가르는 횟수는 log2(8)+1 회 안쪽이어야 한다 — 행마다 한 번씩 넣는 퇴화가 아니다.
		assertThat(attempts).hasSizeLessThanOrEqualTo(2 * 8);
	}

	@Test
	void 멀쩡한_묶음은_한_번에_끝난다() {
		List<List<String>> attempts = new ArrayList<>();
		BidNoticeIndexRepository repo = new BidNoticeIndexRepository(poisonedBy("없는id", attempts));

		assertThat(repo.upsertAll(List.of(row("a"), row("b"), row("c")))).isEqualTo(3);
		// 정상 회차에서는 한 번도 갈리지 않는다.
		assertThat(attempts).hasSize(1);
	}

	@Test
	void 빈_묶음은_아무것도_하지_않는다() {
		List<List<String>> attempts = new ArrayList<>();
		BidNoticeIndexRepository repo = new BidNoticeIndexRepository(poisonedBy("bad", attempts));

		assertThat(repo.upsertAll(List.of())).isZero();
		assertThat(repo.upsertAll(null)).isZero();
		assertThat(attempts).isEmpty();
	}
}
