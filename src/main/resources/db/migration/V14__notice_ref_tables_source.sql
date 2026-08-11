-- ============================================================
-- V14__notice_ref_tables_source.sql
-- bid_ntce_no 를 자연키로 쓰는 파생 테이블에 소스 차원을 전파한다.
--
-- V13 으로 색인에 누리장터·D2B 가 들어오면, 공고번호만으로 키를 잡는 파생 저장물
-- (저장 카드·첨부 스캔 캐시·분석 이력)이 소스 간 번호 충돌에 노출된다 — 누리장터가
-- 나라장터와 같은 R##BK 발번 형식을 쓰는 것이 실측으로 확인됐으므로 이론이 아니다.
-- 기존 행은 전부 나라장터이므로 DEFAULT 'G2B' 백필로 안전하고, 기존 코드의
-- (bid_ntce_no, bid_ntce_ord) 조회는 PK 프리픽스로 계속 동작한다.
--
-- 설계 근거: docs/multi-source-schema.md §4.2
-- ============================================================

-- 1) saved_notice: PK (bid_ntce_no, bid_ntce_ord) → + source
ALTER TABLE `saved_notice`
  ADD COLUMN `source` ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B'
    COMMENT '공고 출처 — bid_ntce_no 네임스페이스 구분'
    AFTER `bid_ntce_ord`;
ALTER TABLE `saved_notice`
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`bid_ntce_no`, `bid_ntce_ord`, `source`);

-- 2) dwt_notice_scan: 동일 처리 (첨부 스캔·임베딩 캐시의 소스 격리)
ALTER TABLE `dwt_notice_scan`
  ADD COLUMN `source` ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B'
    COMMENT '공고 출처 — bid_ntce_no 네임스페이스 구분'
    AFTER `bid_ntce_ord`;
ALTER TABLE `dwt_notice_scan`
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`bid_ntce_no`, `bid_ntce_ord`, `source`);

-- 3) analysis_history: 입찰공고 재사용 UNIQUE 키(V6 의 reuse_key_bn)에 소스 성분 추가.
--    reuse_key_pr(조달요청)·reuse_key_ps(사전규격)는 나라장터 전용 개념이라 무변경.
--
--    ⚠ reuse_key_bn 은 반드시 VIRTUAL — 기반 컬럼에 ON DELETE SET NULL FK 가 있어
--    STORED 는 ERROR 1215 로 죽는다(V6 머리주석의 제약이 그대로 적용된다).
--    길이: V6 의 VARCHAR(384) + 소스 성분('G2B'|'NURI'|'D2B' ≤4자 + 구분자 1)
--    → VARCHAR(400) (인덱스 1600B < 3072B).
ALTER TABLE `analysis_history`
  ADD COLUMN `notice_source` ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B'
    COMMENT '공고 출처 — bid_ntce_no 네임스페이스 구분'
    AFTER `bid_ntce_ord`;

ALTER TABLE `analysis_history` DROP KEY `uk_analysis_history_reuse_bn`;
ALTER TABLE `analysis_history` DROP COLUMN `reuse_key_bn`;
ALTER TABLE `analysis_history`
  ADD COLUMN `reuse_key_bn` VARCHAR(400) COLLATE utf8mb4_bin
    GENERATED ALWAYS AS (
      IF(`bid_ntce_no` IS NOT NULL AND `input_hash` IS NOT NULL AND `prompt_version` IS NOT NULL,
         CONCAT_WS(CHAR(31 USING utf8mb4),
                   `notice_source`,
                   `bid_ntce_no`,
                   COALESCE(`bid_ntce_ord`, ''),
                   CONVERT(`input_hash` USING utf8mb4),
                   `prompt_version`,
                   COALESCE(`analysis_mode`, ''),
                   `deep_mode`),
         NULL)
    ) VIRTUAL
    COMMENT '입찰공고 분석 재사용 키 — V6 정의에 notice_source 성분 추가';
ALTER TABLE `analysis_history`
  ADD UNIQUE KEY `uk_analysis_history_reuse_bn` (`reuse_key_bn`);
