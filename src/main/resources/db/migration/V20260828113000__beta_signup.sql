-- 공개 베타 랜딩 접수. 신청자 IP는 저장하지 않는다.
CREATE TABLE beta_signup_guard (
  id         TINYINT      NOT NULL,
  created_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO beta_signup_guard (id) VALUES (1);

CREATE TABLE beta_signup (
  id                  BIGINT       NOT NULL AUTO_INCREMENT,
  request_id          VARCHAR(100) NOT NULL,
  name                VARCHAR(200) NOT NULL,
  phone               VARCHAR(200) NOT NULL,
  organization        VARCHAR(200) NOT NULL,
  industry            VARCHAR(200) NOT NULL,
  email               VARCHAR(200) NOT NULL,
  privacy_agreed_at   DATETIME(6)  NOT NULL,
  received_at         DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  UNIQUE KEY uq_beta_signup_request (request_id),
  UNIQUE KEY uq_beta_signup_email (email),
  KEY idx_beta_signup_received (received_at)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci
  ROW_FORMAT=DYNAMIC;
