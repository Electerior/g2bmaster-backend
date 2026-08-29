package com.electerior.g2bmaster.beta;

import java.time.LocalDateTime;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/** 베타 신청 저장소. 공개 POST의 정원·중복 판정은 한 DB 잠금 안에서 직렬화한다. */
@Repository
public class BetaSignupRepository {

	private final NamedParameterJdbcTemplate jdbc;

	public BetaSignupRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/** 모든 접수 트랜잭션이 같은 한 행을 잠가 마지막 자리의 경쟁 조건을 막는다. */
	public void lockSignupGuard() {
		jdbc.queryForObject("SELECT id FROM beta_signup_guard WHERE id = 1 FOR UPDATE",
				new MapSqlParameterSource(), Integer.class);
	}

	public int count() {
		Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM beta_signup",
				new MapSqlParameterSource(), Integer.class);
		return count == null ? 0 : count;
	}

	public boolean existsByRequestId(String requestId) {
		Integer count = jdbc.queryForObject(
				"SELECT COUNT(*) FROM beta_signup WHERE request_id = :requestId",
				new MapSqlParameterSource("requestId", requestId), Integer.class);
		return count != null && count > 0;
	}

	public boolean existsByEmail(String email) {
		Integer count = jdbc.queryForObject(
				"SELECT COUNT(*) FROM beta_signup WHERE email = :email",
				new MapSqlParameterSource("email", email), Integer.class);
		return count != null && count > 0;
	}

	public void insert(BetaSignup signup) {
		jdbc.update("""
				INSERT INTO beta_signup (
				  request_id, name, phone, organization, industry, email,
				  privacy_agreed_at, received_at
				) VALUES (
				  :requestId, :name, :phone, :organization, :industry, :email,
				  :privacyAgreedAt, :receivedAt
				)
				""", new MapSqlParameterSource()
				.addValue("requestId", signup.requestId())
				.addValue("name", signup.name())
				.addValue("phone", signup.phone())
				.addValue("organization", signup.organization())
				.addValue("industry", signup.industry())
				.addValue("email", signup.email())
				.addValue("privacyAgreedAt", signup.receivedAt())
				.addValue("receivedAt", signup.receivedAt()));
	}

	public record BetaSignup(
			String requestId,
			String name,
			String phone,
			String organization,
			String industry,
			String email,
			LocalDateTime receivedAt) {
	}
}
