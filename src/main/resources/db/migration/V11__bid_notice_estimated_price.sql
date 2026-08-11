-- ============================================================
-- V11__bid_notice_estimated_price.sql
-- 추정가격을 price_detail JSON 에서 생성 컬럼으로 승격 + 인덱스
--
-- 왜 필요한가 (실측):
--   금액 구간 필터와 금액 정렬이 아래 식을 WHERE 와 ORDER BY 양쪽에 그대로 넣었다.
--     CAST(JSON_UNQUOTE(JSON_EXTRACT(n.price_detail,'$.estimatedPrice')) AS DECIMAL(20,4))
--   함수를 씌운 컬럼이라 인덱스가 원천적으로 불가능하다. bid_notice 20,403행에서
--   EXPLAIN 이 type=ALL / key=NULL / rows=17477 을 냈다 — 즉 전수 스캔이다.
--   V7 의 컬럼 주석과 docs/notice-search-index.md §6 이 이 자리를 "생성 컬럼 승격 자리"로
--   미리 지목해 두었고, 이 마이그레이션이 그것을 실행한다.
--
-- 왜 VIRTUAL 인가:
--   STORED 로 두면 ALTER 가 테이블을 통째로 다시 쓴다(ALGORITHM=COPY). VIRTUAL 은 컬럼 추가가
--   메타데이터 변경이라 즉시 끝나고, 값은 인덱스에만 실체화된다. 이 컬럼은 검색 필터·정렬에만
--   쓰이고 SELECT 목록에는 실리지 않으므로 저장할 이유가 없다.
--   (analysis_history.reuse_key_* 가 VIRTUAL 인 것은 FK 제약 때문이고 사유가 다르다.
--    export_job.active_request_hash 가 STORED 인 것은 UNIQUE 제약이 걸려서다.)
--
-- CAST 가 안전한 근거 (적재된 20,403행 실측):
--   price_detail IS NULL            137건
--   estimatedPrice 키 없음        5,222건   → 둘 다 생성 결과가 NULL 이고 정상이다
--   JSON 숫자 타입               15,181건   (INTEGER 14,949 / UNSIGNED 231 / DOUBLE 1)
--   숫자로 파싱 안 되는 문자열        0건   ← 이것이 0 이라 CAST 가 실패하지 않는다
--   최대값 548,000,000,000 → DECIMAL(20,4) 범위 안이다.
--
--   ⚠ 적재기가 앞으로 숫자 아닌 값을 넣으면 INSERT 자체가 실패한다(생성 컬럼 평가 오류).
--     BidNoticeMapper 는 Numbers 로 파싱해 숫자만 담으므로 지금은 그럴 경로가 없다.
--
-- ⚠ 버전 번호 주의:
--   V9 / V10 은 feat/price-catalog 브랜치가 이미 쓰고 있다(deal_analysis_result, price_catalog).
--   그래서 main 기준 다음 번호인 V9 가 아니라 V11 을 쓴다. 두 브랜치를 합칠 때
--   price-catalog 를 **먼저** 넣어야 순서가 맞는다 — 이 파일이 먼저 적용된 DB 에
--   나중에 V9/V10 이 들어오면 Flyway 가 out-of-order 로 거부한다.
-- ============================================================

ALTER TABLE `bid_notice`
  ADD COLUMN `estimated_price` DECIMAL(20,4)
    GENERATED ALWAYS AS (
      CAST(JSON_UNQUOTE(JSON_EXTRACT(`price_detail`, '$.estimatedPrice')) AS DECIMAL(20,4))
    ) VIRTUAL
    COMMENT '추정가격 — price_detail 에서 승격. 검색 필터·정렬 전용이며 적재기는 쓰지 않는다';

-- ─────────────────────────────────────────────────────────────
-- 두 인덱스 모두 DESC 다. V7 이 ix_bid_notice_category_created 를 (category, created_date DESC)
-- 로 선언한 것과 같은 이유이고, 실측으로 확인했다.
--
-- InnoDB 보조 인덱스는 키가 같은 구간 안에서 PK 를 오름차순으로 담는다. 정렬이
-- `estimated_price DESC, id ASC` 이므로 인덱스도 DESC 여야 저장 순서와 일치해 정렬이 공짜다.
-- ASC 로 선언하면 같은 인덱스를 쓰고도 역방향 스캔이 되어 id 가 DESC 로 나오고,
-- 타이브레이커가 어긋나 filesort 가 붙는다(실측: rows=9487 + Using filesort).
--
-- 대가: 금액 **오름차순** 정렬은 filesort 를 탄다. 화면 기본이 '금액 큰 순'이라 이쪽을 택했다.
-- ─────────────────────────────────────────────────────────────

-- 탭(category) 안에서 금액 필터·정렬. 화면의 기본 사용 패턴이 '탭을 고른 뒤 좁히기'다.
-- 금액 조건만 있고 category 가 없는 질의도 이 인덱스가 skip scan 으로 처리한다
-- (category 가 4값뿐이라 성립한다 — 실측 rows=5,979 로 단일컬럼 강제(8,883)보다 낫다).
ALTER TABLE `bid_notice`
  ADD KEY `ix_bid_notice_category_amount` (`category`, `estimated_price` DESC);

-- 탭을 고르지 않은 '전체'에서 금액순 정렬 전용.
-- 이것이 없으면 전체 인덱스 스캔 + filesort(rows=17,940)이고, 있으면 LIMIT 만큼만 읽고 멈춘다
-- (실측 rows=20). NULL(추정가격 미기재 5,222건)은 DESC 스캔에서 뒤로 밀리므로 앞을 막지 않는다.
ALTER TABLE `bid_notice`
  ADD KEY `ix_bid_notice_amount` (`estimated_price` DESC);
