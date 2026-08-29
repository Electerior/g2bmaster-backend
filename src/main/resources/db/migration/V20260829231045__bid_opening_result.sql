-- 개찰 결과(참여업체 전수) 저장 — POST /api/bid-opening-results 가 상류 대신 여기서 읽는다.
--
-- 왜 저장하는가. 이 경로는 지금까지 요청마다 상류를 쳤고, 완충은 프로세스 메모리 캐시
-- 하나(6시간·2000개)뿐이었다. 그래서 백엔드를 재기동할 때마다 통째로 날아가고 인스턴스
-- 사이에 공유되지도 않는다(실측: 8082 가 캐시한 공고를 8097 에 물으면 0.66s — 첫 호출과 같다).
-- 참여업체가 공고당 1,596개사까지 나오는 자료를 매번 다시 받아 오는 셈이었다.
--
-- 왜 공고번호 하나로 접는가. 조회 자체가 그렇게 나간다 — MarketIntelService.fetchOpeningResults
-- 는 차수(bidNtceOrd)를 **일부러 안 싣고** 전 차수를 받아 온 뒤 화면 쪽에서 narrowToOrd 로
-- 좁힌다. 저장 단위를 차수로 쪼개면 한 번 받아 온 것을 차수마다 다시 받아야 한다.
--
-- 왜 정규화 전 원본을 담는가. normalizeParticipant 가 상류 이름(prcbdrNm·opengRank·
-- bidprcAmt·bidprcrt)을 화면 계약명(bdrNm·rank·bidAmt·bidprcRt)으로 옮기는데, 그 매핑이
-- 바뀌는 날 저장분을 버리고 다시 받는 일이 없어야 한다. bid_result 가 행 전체를 JSON 으로
-- 담는 것과 같은 판단이다.
CREATE TABLE `bid_opening_result` (
  `bid_ntce_no`       VARCHAR(40) NOT NULL COMMENT '공고번호 — PK. 차수는 participants 안에 있다',
  -- 상류 응답 그대로의 행 배열. 빈 배열도 유효한 값이다(개찰 전·유찰).
  `participants`      JSON        NOT NULL COMMENT '상류 원본 참여업체 행 배열(정규화 전)',
  -- participants 의 길이를 꺼내 둔다. 재조회 판정이 이 값 하나로 끝나고,
  -- JSON_LENGTH 를 인덱스로 못 쓰기 때문이다.
  `participant_count` INT         NOT NULL COMMENT '참여업체 수 — 0이면 개찰 전이거나 유찰',
  `fetched_at`        DATETIME(6) NOT NULL COMMENT '상류에서 받아 온 시각 — 빈 결과 재시도 판정',
  `updated_at`        DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

  PRIMARY KEY (`bid_ntce_no`),
  -- 빈 결과만 다시 물어보는 스캔이 쓰는 인덱스. 참여업체가 한 명이라도 있으면 개찰이 끝난
  -- 것이라 값이 더 바뀌지 않으므로 재조회 대상이 아니다.
  KEY `ix_bid_opening_result_retry` (`participant_count`, `fetched_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='개찰 참여업체 저장 — POST /api/bid-opening-results 의 읽기 원본';
