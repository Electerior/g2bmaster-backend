-- ============================================================
-- V20260811171600__bid_notice_document.sql
-- 첨부파일 본문 색인 — "파일 내" 키워드 검색이 조회하는 테이블.
--
-- 왜 필요한가:
--   프론트에는 "파일 내" 키워드 입력(SearchHeader.tsx)과 스캔 훅(useAttachmentScan.ts)이
--   이미 있고 `POST /api/scan-attachments` 를 부르는데, 백엔드에 그 라우트가 없다.
--   원본 모놀리스는 그 요청을 받을 때마다 수백 개의 PDF·HWPX 를 실시간으로 내려받아
--   열었다 — 사용자가 기다리는 동안. 이 테이블은 그 작업을 **적재 시점으로 옮긴다**.
--
-- 왜 bid_notice 에 컬럼으로 붙이지 않는가:
--   V7 머리주석이 이미 정한 규칙이다 — "검색은 FULLTEXT 와 좁은 행이 필요하고,
--   적재는 넓은 행과 이력이 필요하다." 공고당 30~200KB 본문을 bid_notice 행에 붙이면
--   첨부와 무관한 목록 조회(트래픽의 대부분)까지 오프페이지 읽기를 물고 다닌다.
--
-- 왜 파일 단위 행인가 (공고 단위가 아니라):
--   1. "어느 파일에서 걸렸는지"를 화면이 보여줘야 한다.
--   2. SpecFileSelector 의 규격서 판정을 파일마다 들고 있어야 단가 분석이 재사용한다.
--   3. sha256·추출 상태·재시도 횟수가 전부 파일의 속성이다.
--
-- 원본 파일(바이너리)은 저장하지 않는다:
--   실측 — 첨부 24개 표본 평균 245KB, 하루 3,643개. 전량 보관하면 약 0.9GB/일,
--   313GB/년이다. 추출 텍스트만 두면 같은 하루가 약 39MB 로 22분의 1이 된다.
--   원본이 필요하면 AttachmentDownloadController 가 나라장터에서 그때그때 스트리밍한다.
--   "마감되면 첨부가 내려간다"는 우려도 실측으로는 확인되지 않았다 —
--   7/23~8/03 에 마감된 건을 포함해 표본 103개 URL 이 12일 뒤에도 전부 200 이었다.
--
-- 용량·성능 실측 (실제 나라장터 첨부 본문으로 27,500행 / 4.54억 자 벤치):
--   - 본문 1.3GB, FULLTEXT(ngram) 보조 테이블 648MB — 본문의 약 0.5배
--   - MATCH('서버') 0.067초 vs 같은 결과를 내는 LIKE '%서버%' 전수 스캔 3.80초 (57배)
--   - 실제 화면 질의(EXISTS + category + 마감전 + 최신순 LIMIT 20) 0.223초
--   - FULLTEXT 최초 생성 10분 42초 — 최초 1회이고, 하루 증분(3,643파일)은 1~2분이다
--
-- 주의:
--   - 머지된 뒤에는 이 파일을 고치지 않는다. Flyway 가 체크섬을 들고 있어서,
--     이미 적용한 사람의 앱이 기동하지 않는다. 정정은 새 파일로 한다.
--   - ENUM 은 말미 append 만 한다(INSTANT). 재배열·삭제는 ALGORITHM=COPY 다.
-- ============================================================


-- ============================================================
-- 1. 현재 버전 문서 — 검색이 읽는 유일한 문서 테이블
-- ============================================================
CREATE TABLE `bid_notice_document` (
  `id`                BIGINT NOT NULL AUTO_INCREMENT,

  -- ---- 부모 연결 -----------------------------------------------------------
  -- bid_notice 의 PK 는 V13 부터 (id, source) 다. 자식도 두 칸을 함께 들어야 하고,
  -- ENUM 정의가 한 글자라도 다르면 FK 가 걸리지 않는다.
  `notice_id`         VARCHAR(64) NOT NULL COMMENT 'bid_notice.id',
  `source`            ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B' COMMENT 'bid_notice.source',

  -- 부모는 최신 차수만 담는다(V7 §2.1). 이 칸은 "이 파일이 몇 차의 것인가"이고,
  -- 차수가 오르면 이전 차수의 행은 _history 로 옮긴다. 그래서 UNIQUE 에는 없다.
  -- 낮은 차수가 뒤늦게 도착해 새 문서를 덮지 않도록, 적재기가 부모와 같은 가드를
  -- 건다(BidNoticeIndexRepository.buildUpsertSql 의 notice_order 비교와 같은 규칙).
  `notice_order`      VARCHAR(64) NOT NULL DEFAULT '000' COMMENT '이 파일이 속한 차수',

  -- ---- 파일 정체성 ---------------------------------------------------------
  -- 나라장터는 첨부를 배열이 아니라 번호 붙은 평평한 필드로 준다(ntceSpecDocUrl1..10).
  -- 그 N 이 file_seq 다 — FileEntryCollector 가 뽑는 순서와 같다.
  `file_seq`          INT NOT NULL COMMENT '첨부 슬롯 번호 (ntceSpecDocUrlN 의 N)',
  `file_name`         VARCHAR(500) COMMENT '첨부 파일명',
  `file_ext`          VARCHAR(16) COMMENT '소문자 확장자 — hwpx/hwp/pdf/xlsx/zip',
  `source_url`        TEXT COMMENT '원본 다운로드 URL',
  `byte_size`         INT COMMENT '내려받은 바이트 수',
  -- 같은 규격서가 여러 공고에 붙는 일이 흔하다. 해시가 같으면 재추출을 건너뛴다.
  `sha256`            CHAR(64) CHARACTER SET ascii COLLATE ascii_bin COMMENT '원본 파일 해시',
  `is_spec`           TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'SpecFileSelector 가 규격서로 고른 파일',

  -- ---- 본문 ----------------------------------------------------------------
  -- 상한은 애플리케이션이 20만 자로 건다(DocumentTextExtractor.MAX_CHARS 는 200만 자라
  -- 그대로 쓰면 색인이 감당하지 못한다). 실측 중앙값이 12,723자이므로 대부분 온전히 들어간다.
  -- 원본 모놀리스의 3만 자 절단은 p90 이 정확히 상한에 붙어 있었다 — 규격서는 뒤쪽에
  -- 세부 사양이 오는 문서라, 그 절단이 검색이 조용히 놓치는 구간을 만들고 있었다.
  `body_text`         MEDIUMTEXT COMMENT '검색 대상 본문 (상한 20만 자)',
  `char_count`        INT NOT NULL DEFAULT 0 COMMENT '절단 전 원문 글자 수',
  `truncated`         TINYINT(1) NOT NULL DEFAULT 0 COMMENT '잘렸다는 사실을 숨기지 않는다',

  -- 파서가 아무것도 못 뽑는 파일이 있다 — 실측 2,078건 중 30건(1.4%)이 텍스트 0자였고,
  -- 대부분 스캔 PDF 다. 구멍 자체보다 **구멍이 안 보이는 것**이 문제다: 이 칸이 없으면
  -- 화면에서 "그 키워드가 없는 공고"와 "애초에 읽지 못한 공고"가 똑같이 보이고,
  -- 규격서가 스캔본인 공고를 사용자가 조용히 놓친다.
  -- OCR 은 지금 범위가 아니지만(원본 monolith 의 needsOcr 도 판정만 하고 OCR 은 안 했다),
  -- 플래그는 지금 남긴다 — 나중에 OCR 을 붙일 때 재처리 대상을 이 칸 하나로 고른다.
  `needs_ocr`         TINYINT(1) NOT NULL DEFAULT 0
                      COMMENT '텍스트를 못 뽑은 파일(스캔 PDF 등) — 검색이 놓친 게 아니라 못 읽은 것',

  -- ---- 추출 상태 -----------------------------------------------------------
  -- 재추출을 부르는 진짜 이유는 문서 변경이 아니라 파서 변경이다. 실측: 12일 동안
  -- 문서 내용이 바뀐 건 103건 중 0건이었는데, 같은 파일에 추출기만 바꾸니
  -- 70건 중 38건(54%)의 텍스트가 달라졌다(HWP 중첩표 파싱 수정).
  `extractor_version` VARCHAR(40) NOT NULL DEFAULT '' COMMENT '이 텍스트를 뽑은 파서 버전',
  `status`            ENUM('pending','done','skip','failed') NOT NULL DEFAULT 'pending'
                      COMMENT 'pending 은 적재기가 첨부를 발견만 한 상태. 워커가 집어간다',
  `retry_count`       INT NOT NULL DEFAULT 0,
  `last_error`        TEXT,
  `extracted_at`      DATETIME(6) COMMENT '텍스트를 뽑은 시각',
  `verified_at`       DATETIME(6) COMMENT '원본과 마지막으로 대조한 시각(표본 감사)',
  `content_changed_at` DATETIME(6) COMMENT 'sha256 이 실제로 바뀐 시각 — 화면 배지·알림이 쓴다',

  PRIMARY KEY (`id`),

  -- "현재 버전은 슬롯당 하나"를 DB 가 강제한다. 차수를 키에 넣지 않는 이유는 위 참고 —
  -- 이전 차수의 파일은 이 테이블에 남아 있으면 안 된다(검색에 낡은 규격서가 걸린다).
  UNIQUE KEY `uq_doc` (`notice_id`, `source`, `file_seq`),

  -- 추출 워커가 일감을 집는 경로.
  KEY `ix_doc_queue` (`status`, `id`),
  -- 같은 파일이 다른 공고에 또 붙었을 때 재추출을 건너뛰기 위한 조회.
  KEY `ix_doc_sha` (`sha256`),

  -- 한국어 전문검색에는 ngram 파서가 필수다(V7 §2.4 와 같은 이유). 기본 파서는 공백으로만
  -- 끊어서 '서버' 로 '노트북서버' 를 못 찾는다. ngram_token_size 기본값이 2라 한 글자
  -- 검색어는 0건이 아니라 조용히 무시된다 — 검색 계층이 그 경우 LIKE 로 떨어뜨린다.
  FULLTEXT KEY `ft_doc_body` (`body_text`) WITH PARSER ngram,

  -- 공고가 정리되면 문서도 함께 사라져야 한다. 12개월 롤링 삭제도 이 경로를 탄다.
  CONSTRAINT `fk_doc_notice` FOREIGN KEY (`notice_id`, `source`)
    REFERENCES `bid_notice` (`id`, `source`) ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
  ROW_FORMAT=DYNAMIC
  COMMENT='첨부 본문 색인 — 파일 내 키워드 검색이 조회하는 유일한 테이블';


-- ============================================================
-- 2. 대체된 문서 — 보관만 한다
-- ============================================================
-- 덮어쓰면 "8월 3일에 이 키워드로 걸렸던 그 문구가 지금 문서엔 없다"를 설명할 수 없다.
-- 입찰 참여를 판단한 근거가 사라지는 셈이다.
--
-- 그렇다고 한 테이블에 버전을 쌓으면 검색이 같은 공고를 여러 줄로 뱉는다. MySQL 에는
-- 부분 인덱스가 없으므로 "현재 행만 FULLTEXT" 를 조건으로 표현할 방법이 없다 —
-- 테이블을 나누는 것이 유일하게 확실한 방법이다.
--
-- FK 를 걸지 않는다: 공고 행이 롤링 삭제로 정리돼도 이력은 남을 수 있어야 한다.
-- FULLTEXT 를 걸지 않는다: 검색 대상이 아니고, 인덱스만 두 배로 늘어난다.
CREATE TABLE `bid_notice_document_history` (
  `id`                BIGINT NOT NULL AUTO_INCREMENT,
  `document_id`       BIGINT NOT NULL COMMENT '대체되기 전 bid_notice_document.id',
  `notice_id`         VARCHAR(64) NOT NULL,
  `source`            ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B',
  `notice_order`      VARCHAR(64) NOT NULL DEFAULT '000',
  `file_seq`          INT NOT NULL,
  `file_name`         VARCHAR(500),
  `sha256`            CHAR(64) CHARACTER SET ascii COLLATE ascii_bin,
  `body_text`         MEDIUMTEXT,
  `char_count`        INT NOT NULL DEFAULT 0,
  `extractor_version` VARCHAR(40) NOT NULL DEFAULT '',
  `superseded_at`     DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  -- 왜 대체됐는지를 남긴다. '추출기변경' 은 문서가 그대로인데 우리 파서만 바뀐 경우로,
  -- 실측상 이것이 가장 잦은 사유다(54%).
  `superseded_reason` ENUM('차수상승','목록변경','내용변경','추출기변경') NOT NULL,

  PRIMARY KEY (`id`),
  KEY `ix_dochist_notice` (`notice_id`, `source`, `file_seq`, `superseded_at`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
  ROW_FORMAT=DYNAMIC
  COMMENT='대체된 첨부 본문 — 검색 대상 아님. 판단 근거 보존용';


-- ============================================================
-- 3. 부모 쪽 변경 감지 칸
-- ============================================================
-- 실측으로 확인한 사실 하나가 이 설계를 결정했다. 두 시점에 적재된 입찰공고 6,104건을
-- 비교했더니:
--   차수 같음 · 첨부 같음   5,974건 (97.87%)
--   차수 오름 · 첨부 다름     130건 ( 2.13%)
--   차수 같음 · 첨부 다름       0건
-- 첨부가 바뀌는 일은 **예외 없이 차수 상승을 동반한다**. 구조적으로 그렇다 —
-- 첨부 URL 에 차수가 박혀 있어서(bidPbancOrd=000 → 001) 정정이 나면 URL 이 통째로 바뀐다.
--
-- 그래서 변경 감지에 추가 API 호출이나 재다운로드가 필요 없다. 적재기가 어차피 받아 든
-- 응답 안의 첨부 목록을 해시해서 비교하면 끝난다.
ALTER TABLE `bid_notice`
  ADD COLUMN `attachments_hash` CHAR(40) CHARACTER SET ascii COLLATE ascii_bin NULL
    COMMENT '첨부 {url,name} 목록의 sha1 — 값이 바뀌면 문서를 다시 뽑는다',
  -- 검색 화면은 "아직 첨부 색인 전"과 "색인했는데 안 걸림"을 구분해야 한다.
  -- 매번 자식 테이블을 조인해서 세면 45,736행 스캔이 되므로 여기 비정규화해 둔다.
  ADD COLUMN `documents_indexed_at` DATETIME(6) NULL
    COMMENT '이 공고의 첨부 색인이 끝난 시각. NULL 이면 미완료(또는 첨부 없음)';

-- 워커가 "첨부는 있는데 아직 색인 안 된 공고"를 고르는 경로.
ALTER TABLE `bid_notice`
  ADD KEY `ix_bid_notice_docs_pending` (`documents_indexed_at`);
