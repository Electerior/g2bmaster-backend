-- ============================================================
-- V13__bid_notice_source_dimension.sql
-- 검색 색인에 공고 출처(나라장터/누리장터/D2B) 차원을 도입한다.
-- (V9/V10 은 feat/price-catalog, V12 는 created_sort 인덱스가 선점)
--
-- 왜 필요한가:
--   누리장터·D2B 는 지금까지 요청별 실시간 팬아웃(/api/bid-announce)으로만 표시됐고
--   색인(bid_notice)에는 나라장터만 들어 있었다. 두 소스를 색인에 적재하는 순간
--   "번호 체계를 공유하지 않아 우연히 겹치면 서로를 지운다"는 코드 주석의 위험
--   (NoticeSearchSupport.selectLatestNoticeRevisions)이 DB 의 실제 위험이 된다.
--   실측으로도 확인됐다: 누리장터 공고번호는 나라장터와 같은 차세대 13자리
--   R##BK######## 발번 형식을 쓴다(예: R26BK01618409). 소스가 키에 들어가야 한다.
--
-- 설계 근거: docs/multi-source-schema.md §4.1 (C안 — 기존 bid_notice 를 core 로 승격)
-- ============================================================

-- 1) 소스 컬럼. 기존 행은 전부 나라장터이므로 DEFAULT 백필로 끝난다.
--    API 계층 _source(g2b / private-g2b / d2b)와 1:1 매핑은 NoticeSource enum 이 담당한다.
ALTER TABLE `bid_notice`
  ADD COLUMN `source` ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B'
    COMMENT '공고 출처 — G2B=나라장터, NURI=누리장터(민간), D2B=국방전자조달'
    AFTER `id`;

-- 2) ENUM 확장 — 반드시 말미 append (INSTANT 유지, 재배열·삭제는 COPY 라 금지).
--    business_division: 누리장터는 물품/용역/공사에 더해 '기타'(민간기타) 오퍼레이션이
--    있다(G2bEndpoints.privateNotice 의 네 번째 축). '기타'를 빼면 민간 공고 상당수가
--    분류 불가로 버려진다.
ALTER TABLE `bid_notice`
  MODIFY COLUMN `business_division` ENUM('물품','용역','공사','외자','기타') NOT NULL
    COMMENT '업종코드 — 기타는 누리장터 전용(민간기타)';

--    state: 누리장터 ntceDivNm 의 '긴급공고', D2B pblancSe 의 '긴급'/'연기'를 수용한다.
ALTER TABLE `bid_notice`
  MODIFY COLUMN `state` ENUM('취소','재','다시','정정','긴급','연기') NULL
    COMMENT '공고 상태 — 평시 NULL. 긴급=누리 긴급공고·D2B 긴급, 연기=D2B 연기';

-- 3) PK 재정의: (id, source).
--    id 선행인 이유 — 기존 코드의 WHERE id=? 단독 조회가 PK 프리픽스로 계속 탄다.
--    utf8mb4 VARCHAR(64)=256B + ENUM 1B 로 키 상한(3072B) 안쪽이다.
ALTER TABLE `bid_notice`
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`id`, `source`);

-- 4) 소스 특화 표시 필드 격리 저장소. core 컬럼을 소스마다 넓히지 않기 위한 자리다
--    (V7 이 product_list·price_detail·attachment_urls 를 JSON 으로 두는 관례와 동일).
--    키는 원본 API 필드명 그대로 둔다 — 매핑 표를 하나 더 만들지 않는다.
ALTER TABLE `bid_notice`
  ADD COLUMN `source_ext` JSON DEFAULT NULL
    COMMENT '소스 특화 표시 필드 — NURI: bidNtceClsfc/refAmt/apt*/dtchac*, D2B: dcsNo/demandYear 등';

-- 5) D2B ↔ 나라장터 교차 게시 역참조. D2B 응답의 g2bPblancNo/Odr 는 같은 공고가
--    나라장터에 교차 게시됐을 때의 G2B 공고번호다. 중복 제거·병합 조인 키이므로
--    JSON 이 아니라 실컬럼 + 인덱스로 둔다. 대부분 NULL 이지만 MySQL 에 부분 인덱스가
--    없으므로 그냥 건다(V7 의 before_spec_rgst_no 와 같은 판단).
ALTER TABLE `bid_notice`
  ADD COLUMN `g2b_pblanc_no`  VARCHAR(64) DEFAULT NULL
    COMMENT 'D2B 전용: 나라장터 교차 게시 공고번호(g2bPblancNo)',
  ADD COLUMN `g2b_pblanc_odr` VARCHAR(64) DEFAULT NULL
    COMMENT 'D2B 전용: 교차 게시 공고차수(g2bPblancOdr)',
  ADD KEY `ix_bid_notice_g2b_ref` (`g2b_pblanc_no`);

-- 6) 소스 필터 인덱스. 기존 (category, created_date) 계열은 소스 무필터 질의용으로
--    그대로 두고, "소스 탭 + 최신순"의 기본 경로 하나만 추가한다.
ALTER TABLE `bid_notice`
  ADD KEY `ix_bid_notice_source_category_created` (`source`, `category`, `created_date` DESC);
