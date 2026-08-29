-- ============================================================
-- V20260814113541__bid_notice_filter_amount.sql
-- 금액 필터·정렬이 보는 컬럼을 '추정가격 하나'에서 '소스별 대표 금액'으로 넓힌다.
--
-- 왜 필요한가 (2026-08-14 실측, bid_notice 51,478행):
--   V11 의 estimated_price 는 price_detail->'$.estimatedPrice' 하나만 본다. 그런데 그 키를
--   가진 행은 나라장터(G2B)의 입찰·마감·계획뿐이다:
--
--     category  source  건수     estimatedPrice  assignedBudget  referenceAmount  basicExpectedPrice
--     마감      G2B     24,480       24,435          16,874             0                  0
--     사전규격  G2B     12,126            0          12,120             0                  0
--     입찰      G2B     10,795       10,749           8,914             0                  0
--     계획      G2B      2,032        1,696           1,696             0                  0
--     마감      NURI       651            0             651            20                  0
--     마감      D2B        591            0             337             0                320
--     입찰      NURI       520            0             520            22                  0
--     입찰      D2B        283            0               2             0                189
--
--   그래서 `?minAmount=1` 처럼 사실상 아무것도 거르지 않아야 할 조건에서도 사전규격 12,119건
--   전부와 NURI·D2B 2,045건 전부가 사라졌다 — 전체의 28%다. 사전규격은 12,120건이 배정예산을
--   가지고 있어 **금액을 아는데도** 못 찾혔고, 화면에는 아무 설명이 없어 사용자에게는
--   "필터가 백엔드에 안 붙었다"로 보인다(단계 패싯이 통째로 사라지므로).
--
-- ⚠ 이 컬럼은 적재기의 의도적인 분리를 필터 목적으로만 되접는다.
--   BidNoticeMapper 는 추정가격·배정예산·기준금액(refAmt)·기초예비가격(bsicExpt)을 **일부러**
--   다른 키에 담는다 — 누리장터엔 예가·추정가격 개념이 없고 기준금액은 투찰 상한이라
--   개념이 다르다(docs/multi-source-schema.md §6, BidNoticeMapper.fromPrivateNotice 주석).
--   그 분리는 price_detail 안에서 **그대로 유지된다**. 여기서 합치는 것은 "이 공고의 규모를
--   가늠할 수 있는 금액 하나"라는 검색 전용 축이고, 어느 종류가 쓰였는지는 응답의
--   amountKind 로 내려가 화면(추정가격/배정예산/기준금액/기초예비가격)에 그대로 적힌다.
--   합치기만 하고 종류를 숨기면 서로 다른 금액을 한 줄로 비교하게 만드는 더 나쁜 거짓말이 된다.
--
-- COALESCE 순서 — 추정가격에 가까운 것부터. 실측으로 확인한 각 자리의 몫:
--   1) estimatedPrice      G2B 전용. 진짜 추정가격이다.
--   2) assignedBudget      사전규격 12,120건과 NURI 1,171건을 살린다. 예산이라 추정가격보다
--                          크지만(부가세·여유분 포함) 규모 비교에는 쓸 수 있다.
--   3) referenceAmount     NURI 기준금액(투찰 상한) 42건. 지금은 그 42건이 모두 배정예산도
--                          가지고 있어 **실제로 이 자리가 이기는 행은 배정예산이 0인 27건뿐**이다.
--   4) basicExpectedPrice  D2B 기초예비가격 509건. 이 자리가 없으면 D2B 370건이 금액 없는 행으로
--                          남는다. 배정예산과 둘 다 있는 139건은 두 값이 **완전히 같아서**(실측)
--                          2)·4) 의 앞뒤를 바꿔도 결과가 변하지 않는다.
--
-- NULLIF 로 0 을 '부재'로 읽는 이유:
--   배정예산 0 이 1,486건 있다(G2B 596 · NURI 890). 이것은 '0원짜리 공고'가 아니라 '미공개'다 —
--   누리장터 민간공고는 예산을 공개하지 않는 것이 흔하다. 0 을 값으로 인정하면
--   `?maxAmount=1000000` 같은 검색이 **금액을 모르는 공고를 '0원'이라며 데려온다**. 모르는 것은
--   빼는 편이 낫다. 덤으로 NULLIF 는 다음 후보로 흘러가게 해 준다 — 배정예산이 0 인 NURI 27건이
--   기준금액을 얻고, 추정가격이 0 인 G2B 495건이 배정예산으로 내려간다(V11 은 그 495건을
--   '0원'으로 취급했다).
--
-- 결과 (실측):
--   금액을 가진 행  36,396 → 49,312건.  금액이 아예 없는 행 2,180건(4.2%)은 여전히 금액 조건에서
--   빠진다 — 계획 336 · G2B 입찰 431 · 사전규격 231 · NURI 863 · D2B 165 등 원본에 금액이 없는
--   행들이라 DB 로는 더 줄일 수 없다. 그 사실은 화면이 금액 조건이 걸렸을 때 문장으로 적는다
--   (AttachmentSearchNote 와 같은 규약 — 검색 범위의 한계는 숨기지 않는다).
--
-- 왜 VIRTUAL 인가: V11 과 같다. ALTER 가 메타데이터 변경으로 끝나고(ALGORITHM=INPLACE),
--   값은 인덱스에만 실체화된다. 이 컬럼은 필터·정렬 전용이고 SELECT 목록에 실리지 않는다
--   (행에 내려가는 금액은 서버가 price_detail 에서 다시 꺼낸다 — 종류까지 알아야 하므로).
--
-- CAST 안전성 (적재된 51,478행 실측): 네 키 모두 JSON 숫자 타입뿐이고
--   (INTEGER 41,095 / UNSIGNED INTEGER 570) 숫자로 파싱 안 되는 문자열은 0건이다.
--   최대값 2,613,554,000,000 → DECIMAL(20,4) 범위 안이다.
--   ⚠ 적재기가 앞으로 숫자 아닌 값을 넣으면 INSERT 자체가 실패한다(생성 컬럼 평가 오류).
--     BidNoticeMapper 의 putNumber 가 Numbers 로 파싱해 숫자만 담으므로 지금은 그럴 경로가 없다.
--
-- 주의:
--   - 머지된 뒤에는 이 파일을 고치지 않는다. Flyway 가 체크섬을 들고 있어서,
--     이미 적용한 사람의 앱이 기동하지 않는다. 정정은 새 파일로 한다.
--   - ENUM 은 말미 append 만 한다(INSTANT). 재배열·삭제는 ALGORITHM=COPY 다.
-- ============================================================

ALTER TABLE `bid_notice`
  ADD COLUMN `filter_amount` DECIMAL(20,4)
    GENERATED ALWAYS AS (
      COALESCE(
        NULLIF(CAST(JSON_UNQUOTE(JSON_EXTRACT(`price_detail`, '$.estimatedPrice')) AS DECIMAL(20,4)), 0),
        NULLIF(CAST(JSON_UNQUOTE(JSON_EXTRACT(`price_detail`, '$.assignedBudget')) AS DECIMAL(20,4)), 0),
        NULLIF(CAST(JSON_UNQUOTE(JSON_EXTRACT(`price_detail`, '$.referenceAmount')) AS DECIMAL(20,4)), 0),
        NULLIF(CAST(JSON_UNQUOTE(JSON_EXTRACT(`price_detail`, '$.basicExpectedPrice')) AS DECIMAL(20,4)), 0)
      )
    ) VIRTUAL
    COMMENT '검색용 대표 금액 — 추정가격→배정예산→기준금액→기초예비가격 순. 필터·정렬 전용이며 적재기는 쓰지 않는다';

-- ─────────────────────────────────────────────────────────────
-- 인덱스를 반드시 같이 건다. 식을 WHERE 에 인라인하면(=컬럼 없이 COALESCE 를 직접 쓰면)
-- 인덱스가 원천적으로 불가능해 전수 스캔이 된다 — V11 이 고쳤던 바로 그 상태다.
--
-- 두 인덱스 모두 DESC 다. V11 과 같은 이유이고 근거도 같다: InnoDB 보조 인덱스는 키가 같은
-- 구간 안에서 PK 를 오름차순으로 담으므로, 정렬 `filter_amount DESC, id ASC` 와 저장 순서가
-- 맞아떨어져 정렬이 공짜가 된다. ASC 로 선언하면 역방향 스캔이 되어 타이브레이커가 어긋나고
-- filesort 가 붙는다. 대가는 금액 **오름차순** 정렬이 filesort 를 타는 것이고, 화면 기본이
-- '금액 큰 순'이라 V11 과 같은 쪽을 택했다.
--
-- ⚠ 이 방향 선언은 BidNoticeSearchService.INDEX_DIRECTION 표와 짝이다. 한쪽만 바꾸면
--   같은 인덱스를 쓰고도 수천 행을 다시 정렬한다(BidNoticeSortTiebreakerTest 가 지킨다).
-- ─────────────────────────────────────────────────────────────

-- 탭(category) 안에서 금액 필터·정렬. 화면의 기본 사용 패턴이 '탭을 고른 뒤 좁히기'다.
-- 금액 조건만 있고 category 가 없는 질의도 이 인덱스가 skip scan 으로 처리한다(category 4값).
ALTER TABLE `bid_notice`
  ADD KEY `ix_bid_notice_category_filter_amount` (`category`, `filter_amount` DESC);

-- 탭을 고르지 않은 '전체'에서 금액순 정렬 전용. 이것이 없으면 전체 인덱스 스캔 + filesort 이고,
-- 있으면 LIMIT 만큼만 읽고 멈춘다. NULL(금액 없는 2,180건)은 DESC 스캔에서 뒤로 밀린다.
ALTER TABLE `bid_notice`
  ADD KEY `ix_bid_notice_filter_amount` (`filter_amount` DESC);

-- ─────────────────────────────────────────────────────────────
-- V11 의 두 인덱스를 내린다.
--
-- estimated_price 컬럼 자체는 남긴다 — VIRTUAL 이라 인덱스가 없으면 저장도 쓰기 비용도 0 이고,
-- '추정가격만 보는 축'이 다시 필요해질 때 컬럼 정의를 되살릴 필요가 없다. 반면 인덱스는
-- 갱신마다 비용을 문다(적재기가 한 스윕에 수만 행을 upsert 한다). 이 마이그레이션 뒤로
-- estimated_price 를 참조하는 질의는 코드에 하나도 남지 않으므로 순수한 낭비가 된다.
-- 다시 필요하면 새 마이그레이션으로 되건다.
-- ─────────────────────────────────────────────────────────────

ALTER TABLE `bid_notice` DROP KEY `ix_bid_notice_category_amount`;
ALTER TABLE `bid_notice` DROP KEY `ix_bid_notice_amount`;
