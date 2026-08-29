-- 낙찰정보(입찰결과) 읽기 전용 색인 — GET /api/bid-result 가 나라장터를 두드리지 않고
-- 이 테이블만 조회하게 한다. bid_notice(입찰공고) 와는 별도다.
--
-- 왜 별도 테이블인가 (V7 line 9 의 원칙 + dwt_bid_result 재용 불가):
--   * bid_notice 는 narrow 색인이다 — 입찰공고 검색이 쓰는 컬럼만 있고, "넓히고 싶은
--     유혹이 생기면 dwt_* 를 쓸 것" 이라 적혀 있다. 낙찰 결과는 개찰·낙찰업체·낙찰가 등
--     공고 색인과 전혀 다른 컬럼 집합이라 bid_notice 에 얹으면 그 narrow 가 오염된다.
--   * dwt_bid_result 는 §1-B "의도적 미이식" 이다 — FK 가 미이식 warehouse(dwt_bid_notice,
--     dm_institution, dm_supplier, api_call_log) 를 참조하고 writer 도 0개다. 재용하면
--     미이식 warehouse 를 되살려 아키텍처를 깨므로 쓰지 않는다.
--
-- 왜 JSON 행인가:
--   * 낙찰정보 응답은 필드가 많고(낙찰가·낙찰률·낙찰업체·담당기관·재입찰번호 등) 그중
--     검색·필터·정렬에 쓰는 것은 극소수다. 좁은 컬럼만 남기면 프론트가 쓰는 필드가
--     누락되거나 필드가 늘 때마다 마이그레이션이 필요하다. 그래서 bid_notice 의
--     product_list/price_detail 와 같은 관례대로 <b>보강된 행 전체</b>를 JSON 로 담고,
--     조회·정렬·워터마크만 필요한 rgst_dt·bid_type 을 인덱스 컬럼으로 뺀다.
--   * 조회 경로는 이 JSON 을 Map 으로 되풀어 기존 in-memory 필터(haystack·기관·업체·구분)
--     를 그대로 돈다 — 그래서 응답이 라이브 경로와 바이트 단위로 같다.

CREATE TABLE `bid_result` (
  -- 낙찰정보는 정정 차수 개념이 없어 공고번호 하나로 접는다(라이브 BidResultService 주석과
  -- 동일). bid_type 은 물품/용역/공사 오퍼레이션이 서로 다른 응답을 주므로 같이 키에 둔다.
  `bid_ntce_no`   VARCHAR(40) NOT NULL COMMENT '공고번호 — PK',
  `bid_type`      VARCHAR(8)  NOT NULL COMMENT '사업 구분 — 물품/용역/공사',
  -- 보강된 행 전체(_type 포함). 응답 필드의 유일한 출처다.
  `row`           JSON        NOT NULL COMMENT '보강된 낙찰정보 행(ScsbidInfoService 응답 + _type)',
  -- 조회 창 필터·워터마크·기본 정렬의 앵커. 라이브 경로의 inqryDiv=1(등록일시) 이므로 rgstDt.
  `rgst_dt`       DATETIME(6) NOT NULL COMMENT '등록일시 — 조회 창·워터마크 앵커',
  `updated_at`    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

  PRIMARY KEY (`bid_ntce_no`, `bid_type`),
  KEY `ix_bid_result_rgst_dt` (`rgst_dt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='낙찰정보 읽기 전용 색인 — GET /api/bid-result';

-- 워터마크·실패 이력은 bid_notice 와 같은 bid_notice_sync_state 를 재사용한다.
-- (source = 'bid_result:물품' 처럼 붙여 구분. 새 테이블을 만들 이유가 없다.)
