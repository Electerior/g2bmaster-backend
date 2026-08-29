# 다중 소스(나라장터·누리장터·D2B) 통합 표시를 위한 DB 스키마 분석

작성일 2026-08-11. 근거: [nuri-openapi.md](nuri-openapi.md)(누리장터 API 심층 분석),
[d2b-openapi/INDEX.md](d2b-openapi/INDEX.md)(D2B API 전수 조사), 그리고 이 저장소의
마이그레이션 V1~V11·Java 코드 실측. 문서 말미 §9에 검증 상태를 남겼다 — 인용된 테이블·컬럼·코드
위치는 전부 실측 확인했고, API 쪽 미확인 항목은 본문에 **확인 필요**로 표시했다.

---

## 0. 요약

- 누리장터·D2B가 현재 "표시"되는 유일한 경로는 요청별 **실시간 팬아웃**(`/api/bid-announce`)이며
  DB를 전혀 읽지 않는다. 로컬 검색(`/api/search/notices`)이 읽는 색인 `bid_notice`에는 나라장터만
  들어 있고 **소스 구분 컬럼이 없다**.
- 추천안: **기존 `bid_notice`를 다중 소스 core 테이블로 승격**(C안 변형). `source ENUM('G2B','NURI','D2B')`
  추가 + PK `(id, source)` 재정의 + 소스 특화 필드는 `source_ext JSON` 격리. dwt 웨어하우스 계통은
  적재기가 미이식 상태라 이번 범위에서 건드리지 않는다.
- 마이그레이션 번호: V9/V10 은 `feat/price-catalog`, V12 는 created_sort 인덱스가 선점 — 본 설계는 **V13/V14 로 적용 완료**(아래 상태 블록). ⚠ **이 번호 선점 관행은 끝났다** — V14 이후 신규는 전부 14자리 타임스탬프이고 `./tools/new-migration.sh` 가 만든다([README](../README.md#마이그레이션-추가)). 아래에서 `V15` 로 적은 것은 **계획 라벨**이지 파일명이 아니다.

---

## 구현 상태 (2026-08-11 — 설계의 1~4단계 적용·실검증 완료)

- **V13**(`bid_notice` 소스 차원)·**V14**(파생 테이블 전파) 마이그레이션 적용 완료. 색인 적재기
  (`BidNoticeIngestService`)에 누리장터 4출처+지역, D2B 4오퍼레이션이 추가되어 **실데이터 적재·검증
  완료**: G2B 2,997 / NURI 61 / D2B 209행(2일 백필), 검색 `source` 필터·패싯·FULLTEXT·상세 동작 확인.
- 실측으로 "확인 필요"가 해소된 것:
  1. **누리장터는 나라장터와 차세대 13자리 발번 공간을 공유한다**(둘 다 `R##BK########`) —
     `(id, source)` PK 가 이론이 아니라 필수임이 확정.
  2. **D2B `pblancNo` 는 오퍼레이션 계열마다 발번 공간이 따로 돈다** — 국내경쟁과 시설수의가 같은
     `LQG0044` 를 서로 다른 공고에 썼다. 합성 키에 계열 태그(DC/FC/DN/FN)·연도·공사번호 성분을 추가해
     해결(`D2bNormalizer`). 기존 팬아웃 경로의 잠재 병합 버그도 함께 고쳐짐.
  3. **시설 공고명은 `cntrwkNm`** — 시설경쟁 응답에 `bidNm` 키 자체가 없다(정규화기에 폴백 추가).
  4. **`g2bPblancNo` 는 나라장터 공고번호 형식이 아니다**(실측 `2026EKA00114511`,
     `2026LPC01442026-12059` — 연도+기관 계열의 D2B 내부 표기). §4.1 의 "교차 게시 조인 키" 전제는
     성립하지 않으므로 실컬럼은 원본 보존·후속 연구용이다. 직접 조인 정책(§8-3)은 보류.
  5. D2B 게이트웨이는 `_type=json` 으로 JSON 을 준다(문서상 XML 전용) — `D2bClient` 반영.
- 남은 후속: V15(D2B stg 원문 보존층 — 색인 직행 선례로 우선순위 하향), 누리 낙찰·계약 dwt,
  `docs/api-contract.md` 계약 문서에 `source` 파라미터 반영.

## 1. 현행 구조 실측 (변경 대상의 정확한 모양)

### 1-A. 표시 경로는 두 계통이다

| 계통 | 경로 | 읽는 것 | 소스 커버 |
|---|---|---|---|
| 로컬 색인 | `/api/search/notices` → `BidNoticeIndexRepository` | **`bid_notice` 단독**(조인 없음 — V8에서 `dm_institution` LEFT JOIN 제거) | **G2B만** |
| 실시간 팬아웃 | `/api/bid-announce` → `BidAnnounceService` | DB 안 읽음 — 나라장터+D2B+누리장터 API를 요청마다 호출해 메모리 병합 | g2b / d2b / private-g2b (`NoticeController.java`의 `SOURCE_COVERAGE` 하드코딩) |

- `bid_notice` PK는 **`id` 단독**(`V7__bid_notice_search_index.sql`). `id`는 category별로 의미가
  다르다(입찰=공고번호, 계획=조달요청번호, 사전규격=사전규격등록번호). `notice_order`는 PK가 아니고
  "한 공고당 한 행(최신 차수)" + upsert 가드 `new.notice_order >= bid_notice.notice_order`
  (3자리 zero-pad 문자열 비교 전제, `BidNoticeIndexRepository.java:132-159`).
- 고정 ENUM: `category('계획','사전규격','입찰','마감')`, `state('취소','재','다시','정정') NULL 허용`,
  `business_division('물품','용역','공사','외자') NOT NULL`.
- `notice_body` MEDIUMTEXT + **FULLTEXT(ngram)**, V11에서 `estimated_price` VIRTUAL 생성 컬럼 +
  DESC 인덱스 2본 추가(실측 20,403행). → V20260814113541에서 금액 축을 `filter_amount`
  (소스별 대표 금액 COALESCE) VIRTUAL + DESC 인덱스 2본으로 옮겼고, `estimated_price`의
  인덱스 2본은 내렸다(컬럼은 유지). 사유는 아래 §6 및 그 마이그레이션 주석.
- `dwt_*` 팩트는 표시 경로에 없다(읽는 코드는 운영 집계 COUNT뿐).

### 1-B. 적재 경로

- 색인 적재(`BidNoticeIngestService`)만 구현되어 있고 **나라장터 12개 출처 → `bid_notice` 직행**
  (raw/stg를 거치지 않음). 워터마크는 `bid_notice_sync_state`(PK `source VARCHAR(64)`,
  키 형식 `'bid-announce:물품'`).
- raw→stg→dwt 웨어하우스 적재기는 **이 저장소에 없다** — `POST /api/system/backfill`이 501
  `NOT_PORTED`를 던지며 원본 CLI를 지시한다(`SystemController.java:184-215`).
- **누리장터 스테이징 `stg_prvt_*` 13종은 이미 존재**(V4)하나, `g2b-operations.json`의 `serviceTable`에
  Prvt 서비스가 없어 dwt 병합 규칙이 정의돼 있지 않고 병합 코드도 없다. 전부 `stg_id` 서러게이트 PK +
  전 컬럼 TEXT append-only.
- **D2B는 DB 흔적이 0** — raw/stg/dwt 어디에도 없다. `integration/d2b/D2bClient → D2bNormalizer`가
  실시간 응답을 나라장터 모양으로 정규화해 팬아웃 응답에만 얹는다.

### 1-C. 식별자 충돌은 이미 알려진 위험이다

- 코드 주석이 명시한다: *"나라장터·D2B·누리장터가 공고번호 체계를 공유하지 않아서, 우연히 번호가
  겹치면 서로를 지워 버리기 때문"* — 그래서 팬아웃 중복 제거 키가 `_source + "|" + bidNtceNo`다
  (`NoticeSearchSupport.java` `selectLatestNoticeRevisions`).
- D2B는 단일 공고번호가 없어 `bidNtceNo`를 **`"D2B-" + orntCode + "-" + (pblancNo|dcsNo) + "-" + 차수`로
  합성**한다(`D2bNormalizer.java` `normalizeD2bItem`). 차수는 `pblancOdr` 또는 실재하는 오타 필드
  `pblanc0dr` 폴백, 기본 `"1"` — 나라장터의 `'000'` 3자리 체계와 폭이 다르다.
- 파생 저장물도 같은 자연키를 쓴다: `saved_notice`·`dwt_notice_scan` PK `(bid_ntce_no, bid_ntce_ord)`,
  `analysis_history`의 재사용 UNIQUE 키 `reuse_key_bn`(V6, VIRTUAL·VARCHAR(384)) — 전부 소스 무구분이라
  번호 충돌 시 저장 카드·분석 캐시가 섞인다.

---

## 2. 설계 목표와 제약

### 2.1 목표

1. **표시 경로의 영속화**: 누리·D2B를 색인 `bid_notice`에 적재해 로컬 검색·FULLTEXT·저장·분석 캐시가
   세 소스 모두에 동작하게 한다.
2. **소스 간 키 충돌 제거를 DB 제약으로 격상**: 현재는 런타임 관례(`_source|bidNtceNo`, `"D2B-"` 접두사)
   뿐이다. 저장 계층에서 소스가 키의 일부여야 한다.
3. **원문 보존 계층 정합**: 누리는 `stg_prvt_*`를 살리고, D2B는 스테이징을 신설한다.
4. **기존 G2B 경로 무중단**: 2만여 행, upsert 가드, FULLTEXT, V6 생성 컬럼 UNIQUE, 마감 스위퍼가
   그대로 동작해야 한다.

### 2.2 MySQL 8.0 제약 (이 저장소가 이미 부딪혀 우회한 것들)

- **부분 인덱스 없음** → 술어형 UNIQUE는 생성 컬럼 + UNIQUE로 대체(V6의 확립된 패턴).
- **인덱스 키 3072바이트 상한** — `(id, source)` PK는 utf8mb4 VARCHAR(64)=256B + ENUM 1B로 여유 충분.
- **ENUM 확장은 말미 append일 때만 INSTANT** — 값 재배열·삭제는 COPY이므로 금지.
- **PK 재정의는 테이블 리빌드** — `bid_notice` 2만 행 규모라 수 초. `dwt_bid_notice`(149컬럼 +
  자식 FK 4계통)는 파급이 커서 이번에 안 건드린다(A안 기각 사유 중 하나).
- **FULLTEXT(ngram)는 물리 테이블에만** — UNION 뷰 불가(B안 기각 사유).
- **STORED 생성 컬럼 기반 컬럼에 ON DELETE SET NULL FK 불가** → `analysis_history.reuse_key_*`는
  VIRTUAL 유지 강제(V6 주석).

---

## 3. 대안 비교

| 평가축 | (A) dwt 팩트에 source 추가 + 자연키 확장 | (B) 소스별 팩트 분리 + 통합 뷰 | (C) 통합 core notice + 소스별 확장 |
|---|---|---|---|
| 표시 쿼리 | 표시 경로가 dwt를 안 읽는다 — 고쳐도 화면에 안 나옴 | UNION 뷰는 FULLTEXT·인덱스 정렬 불가. "통합 테이블"로 가면 사실상 C | 검색 경로가 이미 단일 테이블 단독 조회 — 컬럼 하나로 소스 필터·facet이 기존 인덱스 전략에 얹힘 |
| 검색 인덱스 | dwt에 FULLTEXT 없음, 149컬럼 넓은 행에 걸기도 부적절(V7 머리주석의 분리 사유) | 소스별 테이블마다 FULLTEXT·인덱스 10여 본 중복 유지 | V7 인덱스 재사용 + `(source, …)` 선행 인덱스 소수 추가 |
| 적재 파이프라인 | dwt 적재기 미이식 — 실행 주체가 없음. `dm_institution` 하드 FK 2개가 코드 없는 누리·D2B 기관에서 즉시 깨짐 | upsert·가드·스위퍼·워터마크 3벌 | 기존 "출처 목록 + 매퍼 + 단일 upsert" 구조에 소스별 fromXxx 매퍼만 추가 |
| 기존 데이터 이행 | `dwt_bid_notice` PK 확장 시 자식 4테이블 FK 재작성 — 최대 파급 | 기존 `bid_notice`를 "G2B 테이블"로 재해석 — 코드 전면 수정 | 기존 행은 `source DEFAULT 'G2B'` 백필로 무변경. PK 재정의 1회 |
| 소스 특화 필드 | 149컬럼에 누리 아파트 10종·역경매 9종·D2B 판단번호 체계를 또 폄 | 소스별 테이블이라 자연스러움(유일한 장점) | `source_ext JSON`으로 격리 — core는 좁게 유지(V7 철학) |

**추천: C안 — 단, core 테이블을 "신설"하지 않고 기존 `bid_notice`를 core로 승격한다.**
신설하면 FULLTEXT·인덱스 10본·upsert 가드·마감 스위퍼·V11 생성 컬럼을 중복 구현하고 검색 서비스를
두 테이블로 갈라야 한다. 소스별 확장은 (i) 표시용 소량 필드 → `source_ext JSON`(V7이 이미
`product_list`·`price_detail`·`attachment_urls`를 JSON으로 두는 관례), (ii) 원문 전체 → 소스별
stg 테이블(누리는 기존 `stg_prvt_*` 재사용, D2B는 신설)로 이원화한다. 단 하나의 예외:
**D2B의 `g2bPblancNo`는 G2B 행과의 중복 제거 조인 키이므로 JSON이 아닌 실컬럼+인덱스**로 둔다.

A안은 dwt 적재기가 이식된 뒤 "소스별 dwt 테이블" 형태로 재론한다(§8의 후행 단계).

---

## 4. 추천안 DDL 스케치

### 4.1 V13 — `bid_notice` 소스 차원 도입

```sql
-- ============================================================
-- V13__bid_notice_source_dimension.sql
-- (V9/V10 은 feat/price-catalog 가 선점 — V11:30-34 참조)
-- ============================================================

-- 1) 소스 컬럼. 기존 행은 전부 나라장터이므로 DEFAULT 백필로 끝난다.
ALTER TABLE `bid_notice`
  ADD COLUMN `source` ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B'
    COMMENT '공고 출처 — API 계층 _source(g2b/private-g2b/d2b)와 1:1 매핑'
    AFTER `id`;

-- 2) ENUM 확장 — 반드시 "말미 append" (INSTANT 유지).
--    business_division: 누리장터 공고분류에 '민간기타'가 있고 적재 축도
--    물품/공사/용역/기타 4종이라 '기타'가 필요하다 (nuri-openapi.md §1).
ALTER TABLE `bid_notice`
  MODIFY COLUMN `business_division` ENUM('물품','용역','공사','외자','기타') NOT NULL
    COMMENT '업종코드 — 기타는 누리장터 전용(민간기타)';

--    state: 누리 ntceDivNm 의 '긴급공고', D2B pblancSe 의 '긴급'/'연기'를 수용.
ALTER TABLE `bid_notice`
  MODIFY COLUMN `state` ENUM('취소','재','다시','정정','긴급','연기') NULL
    COMMENT '공고 상태 — 평시 NULL. 긴급=NURI 긴급공고·D2B 긴급, 연기=D2B 연기';

-- 3) PK 재정의: (id, source).
--    id 선행인 이유 — 기존 코드의 WHERE id=? 단독 조회가 PK 프리픽스로 계속 탄다.
--    소스가 키에 들어가는 순간 "우연한 번호 충돌이 서로를 지우는" 위험이
--    런타임 관례에서 DB 제약으로 격상된다.
ALTER TABLE `bid_notice`
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`id`, `source`);

-- 4) 소스 특화 표시 필드 격리 저장소.
--    내부 키 규약(적재기 문서화 대상):
--      NURI: {bidNtceClsfc, refAmtUseYn, refAmtOpenYn, refAmtDscrpt, ntceDivNm,
--             apt:{aptHsmpNm, aptHshldNum, ...},        -- 아파트 10종
--             dtchac:{dtchacOpenDt, dtchacBgnPrce, ...}} -- 역경매 9종
--      D2B : {demandYear, orntCode, dcsNo, pblancNo, pblancOdr, cntrwkNo,
--             busiDivs, pblancSe, bsicExpt, bidPartcptRegistClosDt}
ALTER TABLE `bid_notice`
  ADD COLUMN `source_ext` JSON DEFAULT NULL
    COMMENT '소스 특화 표시 필드 — 키는 원본 API 필드명 그대로';

-- 5) D2B ↔ G2B 교차 게시 역참조. 중복 제거 조인 키이므로 실컬럼.
--    대부분 NULL 이지만 MySQL 에 부분 인덱스가 없어 그냥 건다
--    (V7 의 before_spec_rgst_no 와 같은 판단).
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
```

**적재기 규약(코드 변경, DDL 아님)**:

- upsert 가드가 3자리 문자열 비교 전제이므로 **모든 소스의 차수를 3자리 zero-pad로 정규화**해 넣는다.
  누리 `bidNtceOrd`는 이미 3자리(nuri-openapi.md §3.4), D2B `pblancOdr`(오타 `pblanc0dr` 폴백 포함)는
  `"1"→"001"`.
- D2B `id`는 합성 규칙 `D2B-{orntCode}-{pblancNo|dcsNo}-{차수}`(`D2bNormalizer.java`)를 유지하되
  **성분 보강이 필요하다**: 실측 결과 현행 합성식에 국외 공고의 `pblancYear`/`groupNo`, 시설 공고의
  `cntrwkNo` 성분이 없다. D2B 국외 자연키는 `pblancYear+pblancNo+pblancOdr+groupNo`, 시설 상세 키에는
  `cntrwkNo`가 들어가므로(d2b-openapi/INDEX.md §③) 이대로 색인 PK로 쓰면 연도·그룹·공사번호가 다른
  행이 같은 `id`로 합쳐질 수 있다.
- 누리 `bidNtceNo`는 원본 그대로 쓴다(11자리 구체계·13자리 `R25BK…` 차세대 병존 — VARCHAR(64) 수용).
  누리 차세대 번호가 나라장터 차세대 번호와 같은 발번 공간을 쓰는지는 **확인 필요** — 같더라도
  `(id, source)` PK 하에서는 안전하고, 이중 게재 병합 정책만 별도 결정하면 된다.
- 금액 매핑: 누리 `asignBdgtAmt`→`price_detail.assignedBudget`, `refAmt`→`price_detail.referenceAmount`
  (기준금액은 추정가격과 개념이 달라 `estimatedPrice`에 넣지 않는다 — nuri-openapi.md §3.1(c)).
  D2B 목록 `bsicExpt`→`price_detail.basicExpectedPrice`, 상세 `budgetAmount`→`assignedBudget`,
  `estmPrce`(추정가격)→`estimatedPrice`. 결과적으로 **V11 생성 컬럼 `estimated_price`는 표현식 변경
  없이** 동작한다: 누리 전 행과 D2B 목록-단계 행은 NULL(금액 정렬 시 후순위 — 의도된 NULL 정책, §6).
  ⚠ **정렬에서는 의도된 후순위였지만 필터에서는 그렇지 않았다**(2026-08-14 실측). `estimated_price`가
  NULL인 행은 `minAmount`/`maxAmount` 조건에서 **통째로 빠진다** — 사전규격 12,119건과 누리·D2B
  2,045건, 전체의 28%다. `price_detail` 안의 분리는 그대로 두되, **필터·정렬 축만**
  `filter_amount`(추정가격→배정예산→기준금액→기초예비가격 COALESCE, 0은 미공개로 간주)로 옮겼다.
  합친 사실은 숨기지 않는다 — 응답의 `amountKind`가 어느 금액인지 말하고 화면이 그것을 적는다.
  ⚠ 주의: 현행 팬아웃 `D2bNormalizer`는 `bsicExpt|budgetAmount`를 **`presmptPrce`에 넣어 표시**하고
  있다(실측). 색인 적재를 시작할 때 이 정책과 위 정책 중 하나로 **통일**해야 화면·색인 간 금액이
  어긋나지 않는다 — 본 문서는 "기초예비가격≠추정가격" 분리를 권장.

### 4.2 V14 — 공고번호를 참조하는 운영 테이블 파급

```sql
-- ============================================================
-- V14__notice_ref_tables_source.sql
-- bid_ntce_no 를 자연키로 쓰는 파생 테이블에 소스 차원을 전파한다.
-- 소스가 없으면 번호 충돌 시 저장 카드·분석 캐시가 섞인다.
-- ============================================================

-- 1) saved_notice: PK (bid_ntce_no, bid_ntce_ord) → + source
ALTER TABLE `saved_notice`
  ADD COLUMN `source` ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B' AFTER `bid_ntce_ord`;
ALTER TABLE `saved_notice`
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`bid_ntce_no`, `bid_ntce_ord`, `source`);

-- 2) dwt_notice_scan: 동일 처리 (첨부 스캔·임베딩 캐시의 소스 격리)
ALTER TABLE `dwt_notice_scan`
  ADD COLUMN `source` ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B' AFTER `bid_ntce_ord`;
ALTER TABLE `dwt_notice_scan`
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`bid_ntce_no`, `bid_ntce_ord`, `source`);

-- 3) analysis_history: 재사용 UNIQUE 키(V6)에 소스 성분 추가.
--    reuse_key_bn 은 VIRTUAL 이어야 한다 — 기반 컬럼에 ON DELETE SET NULL FK 가 있어
--    STORED 는 실패한다(V6 주석의 제약이 그대로 적용).
ALTER TABLE `analysis_history`
  ADD COLUMN `notice_source` ENUM('G2B','NURI','D2B') NOT NULL DEFAULT 'G2B'
    COMMENT '공고 출처 — bid_ntce_no 네임스페이스 구분' AFTER `bid_ntce_ord`;

ALTER TABLE `analysis_history` DROP KEY `uk_analysis_history_reuse_bn`;
ALTER TABLE `analysis_history` DROP COLUMN `reuse_key_bn`;
ALTER TABLE `analysis_history`
  ADD COLUMN `reuse_key_bn` VARCHAR(400) COLLATE utf8mb4_bin
    GENERATED ALWAYS AS (
      IF(`bid_ntce_no` IS NOT NULL AND `input_hash` IS NOT NULL AND `prompt_version` IS NOT NULL,
         CONCAT_WS(CHAR(31 USING utf8mb4),
                   `notice_source`,                    -- ← 추가 성분
                   `bid_ntce_no`,
                   COALESCE(`bid_ntce_ord`, ''),
                   CONVERT(`input_hash` USING utf8mb4),
                   `prompt_version`,
                   COALESCE(`analysis_mode`, ''),
                   `deep_mode`),
         NULL)
    ) VIRTUAL
    COMMENT '입찰공고 분석 재사용 키 — V6 정의(VARCHAR(384))에 notice_source 성분 추가';
ALTER TABLE `analysis_history`
  ADD UNIQUE KEY `uk_analysis_history_reuse_bn` (`reuse_key_bn`);
-- reuse_key_pr / reuse_key_ps 는 조달요청·사전규격(G2B 전용 개념)이라 무변경.
-- idx_analysis_history_notice (bid_ntce_no, bid_ntce_ord) 는 프리픽스 조회가 유효해 유지.
```

**V6 생성 컬럼 영향 정리**: `reuse_key_pr`/`reuse_key_ps`·`export_job.active_request_hash`·
`dwt_pre_specification`의 표현식 컬럼은 G2B 전용 자연키 기반이라 무영향. 영향은 `reuse_key_bn`
1건뿐이며 VARCHAR(400)·utf8mb4_bin 인덱스 1600B < 3072B로 상한 안이다.

### 4.3 V15 — D2B 원문 보존층(stg) + 발주기관 코드 차원

D2B는 raw/stg가 0개다. `stg_prvt_*`와 동일한 규약(서러게이트 `stg_id` + `operation` + 전 컬럼 TEXT
append-only)으로 신설한다. 단 `g2b-operations.json`은 생성 파일이고(`tools/gen-g2b-operations.js`)
생성기가 나라장터 swagger 기반이므로, D2B 테이블은 **카탈로그 생성기를 거치지 않는 별도 마이그레이션**
으로 만들고 D2B 클라이언트(`integration/d2b`)가 직접 적재한다. 컬럼 전개의 원천은
[d2b-openapi/swagger/](d2b-openapi/swagger/)의 보존 명세.

```sql
-- ============================================================
-- V<타임스탬프>__d2b_staging_and_dims.sql — 대표 1종 예시. 아래 목록의 나머지는 동일 패턴.
--   stg_d2b_dmstc_cmpet_bid_pblanc_list / _detail
--   stg_d2b_dmstc_othbc_vltrn_ntat_plan_list / _detail
--   stg_d2b_outnatn_cmpet_bid_pblanc_list / _detail
--   stg_d2b_fclty_cmpet_bid_pblanc_list / _detail
--   stg_d2b_fclty_othbc_vltrn_ntat_plan_list / _detail
--   stg_d2b_dmstc_prcure_plan_list (조달계획 → category='계획' 소스)
-- ============================================================
CREATE TABLE `stg_d2b_dmstc_cmpet_bid_pblanc_list` (
  `stg_id`                       BIGINT NOT NULL AUTO_INCREMENT,
  `operation`                    VARCHAR(64) NOT NULL,
  `pblanc_no`                    TEXT COMMENT '공고번호',
  `pblanc_odr`                   TEXT COMMENT '공고차수',
  `pblanc_se`                    TEXT COMMENT '공고구분: 정상/긴급/정정/취소/연기',
  `demand_year`                  TEXT COMMENT '요구년도',
  `dcs_no`                       TEXT COMMENT '판단번호',
  `bid_nm`                       TEXT COMMENT '입찰명',
  `ornt`                         TEXT COMMENT '발주기관명',
  `ornt_code`                    TEXT COMMENT '발주기관코드',
  `bid_partcpt_regist_clos_dt`   TEXT COMMENT '참가등록마감',
  `biddoc_presentn_clos_dt`      TEXT COMMENT '입찰서제출마감',
  `openg_dt`                     TEXT COMMENT '개찰일시',
  `cntrct_mth`                   TEXT COMMENT '계약방법',
  `bsic_expt`                    TEXT COMMENT '기초예비가격',
  `busi_divs`                    TEXT COMMENT '물품/용역',
  `g2b_pblanc_no`                TEXT COMMENT 'G2B 교차 게시 공고번호',
  `g2b_pblanc_odr`               TEXT COMMENT 'G2B 교차 게시 차수',
  `api_call_id`                  CHAR(36) CHARACTER SET ascii COLLATE ascii_general_ci,
  `created_at`                   DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`stg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC
  COMMENT='D2B 국내 경쟁입찰공고 목록 — stg_prvt_* 와 동일한 append-only 규약';

-- D2B 발주기관 코드 차원 — CodeInqireService.getOrntCodeList (code, codeNm) 그대로.
-- dm_institution 에 넣지 않는 이유는 §5.1.
CREATE TABLE `dm_d2b_ornt` (
  `ornt_code`  VARCHAR(20) NOT NULL,
  `ornt_nm`    TEXT NOT NULL,
  `created_at` DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`ornt_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC
  COMMENT='D2B 발주기관코드 — getOrntCodeList. 조달청 instt_cd 와 별도 네임스페이스';
```

raw 원문은 기존 `raw_generic_list` 재사용을 검토하되 D2B가 XML 전용이므로 XML→JSON 변환 저장 규약을
적재기에 명시한다(**확인 필요**: `raw_generic_list` 컬럼이 XML 원문 문자열 수용에 적합한지 — 부적합하면
`raw_d2b_list` 1종 신설).

### 4.4 이번 범위에서 하지 않는 것 (명시적 비변경)

- `dwt_bid_notice` PK·FK 무변경. 누리 stg→dwt 병합은 dwt 적재기 이식과 함께 후행(§8 6단계).
- `bid_notice`의 FULLTEXT·`estimated_price`·기존 인덱스 무변경.
- 누리 낙찰(`PrvtScsbidInfoService`)·계약(`PrvtCntrctInfoService`)의 dwt 반영(자연키
  `(bidNtceNo, bidNtceOrd, bidClsfcNo, rbidNo)` / `untyCntrctNo`, DltHstry 삭제 동기화 포함)은
  공고 표시와 분리된 후속 설계.

---

## 5. 차원 영향

### 5.1 dm_institution — "확장하지 않는다"가 결론

- 상황: `dm_institution`은 실측 0행이고 채울 API는 폐기됐으며(V8 머리주석), 색인은 V8에서 기관명
  컬럼을 병기해 조인 의존을 이미 끊었다. 누리는 목록 응답에 기관 **코드가 아예 없고**(이름
  `ntceInsttNm`뿐 — 코드는 낙찰 응답 `dminsttCd`의 `M######`로만 획득), D2B는 자체 `orntCode` 체계다.
- 결정:
  1. **색인 경로**: `notice_institution_name`/`demand_institution_name`(V8)에 누리 `ntceInsttNm`,
     D2B `ornt`를 그대로 담는다. 코드 컬럼은 누리 NULL, D2B는 `orntCode`를 담되 — G2B 7자리 코드와
     네임스페이스가 섞이므로 기관코드 조회는 반드시 `source` 조건을 함께 걸도록 검색 서비스에
     규약화한다. (`orntCode`가 조달청 7자리와 형식 충돌하는지는 **확인 필요** — 코드 체계 미공개.)
  2. **dm_institution PK(`instt_cd`) 무변경**. 누리 `M######`는 접두사가 달라 공존 가능해 보이나
     (**확인 필요** — 샘플 관찰 수준), 색인이 이 표에 의존하지 않으므로 지금 넣을 이유가 없다.
     낙찰·계약 dwt 이식 시점에 재론.
  3. D2B는 `dm_d2b_ornt` 별도 차원(§4.3) — `orntCode`가 목록/상세 조회의 필수 필터 축이라
     기준정보 캐시 가치가 있다.

### 5.2 dm_supplier — biz_no 공통, 그대로 수용

- `biz_no VARCHAR(32)` PK에 누리 개찰완료 `prcbdrBizno`·계약 `corpList`의 사업자번호, D2B 참가업체
  `bznsRgnb`를 upsert하면 된다 — 스키마 변경 불요.
- 주의 2건: (i) 누리는 개인정보 마스킹이 존재(전화·이메일 확인 — nuri-openapi.md §4-(6)) —
  **사업자번호 자체의 마스킹 여부 확인 필요**, 마스킹 값은 PK 삽입 전에 걸러야 한다.
  (ii) 누리 낙찰현황에는 사업자번호가 없어(`bidwinnrBizno` 부재) 업체 차원 연결은 개찰완료 상세를
  별도 호출해야 채워진다.

### 5.3 분류체계 — D2B NSN은 기존 UNSPSC 차원에 넣지 않는다

- `dm_product_classification`은 UNSPSC 자릿수 레벨 체계다. D2B 품목명세서의 `fsc`/`niin`(NSN)·
  `specNumb`·`partNumb`는 전혀 다른 체계이므로 강제 매핑하면 차원이 오염된다.
- 이번 범위: NSN·규격번호는 `source_ext` 또는 stg 컬럼에 보존만. 색인 `detail_product_code`는
  D2B에서 NULL.
- 후행 옵션: `dm_nsn`(fsc, niin, 품명 국/영) 차원 신설 + KDSISInfoService 보강 — 화면에 NSN 필터가
  필요해질 때. (**확인 필요**: KDSIS 응답 필드로 차원을 채울 수 있는지.)
- 누리: 목록 응답에 물품분류 필드가 없다(검색 파라미터 `dtilPrdctClsfcNo`만 존재) →
  `detail_product_code`·`product_list` NULL. 업무별 상세목록(`prdctDtlList` 등 `^` 복합문자열)은
  파싱해 `product_list` JSON에 넣을 수 있으나 코드 성분이 없어 name 위주다.

---

## 6. 필드 커버리지 매트릭스 (표시 핵심 필드 × 3소스)

색인 컬럼 기준. "목록"=목록 오퍼레이션 응답, "상세"=상세 오퍼레이션 추가 호출 필요.

| 색인 컬럼 | G2B (나라장터) | NURI (누리장터) | D2B | NULL 정책 |
|---|---|---|---|---|
| `id` / `notice_order` | `bidNtceNo`/`bidNtceOrd` | `bidNtceNo`/`bidNtceOrd`(11자리·13자리 병존) | 합성 `D2B-{orntCode}-{pblancNo\|dcsNo}-{odr}` + 국외 연도·groupNo, 시설 cntrwkNo 성분 보강(§4.1); 차수 3자리 정규화 | NOT NULL — 합성 실패 시 행 폐기 |
| `notice_name` | `bidNtceNm` | `ntceNm` | `bidNm`(물품)/`cntrwkNm`(시설)/`othbcNtatNm`(공개수의) | NOT NULL DEFAULT '' 유지 |
| `notice_institution_name` | `ntceInsttNm` | `ntceInsttNm`(민간발주자기관명) | `ornt` | 없으면 NULL |
| `notice_institution_code` | `ntceInsttCd` | **없음 → NULL**(목록 응답에 코드 부재) | `orntCode` | NULL 허용(기존). 기관코드 조회는 source 조건 필수(§5.1) |
| `demand_institution_name/code` | `dminsttNm`/`dminsttCd` | 목록에 없음 → NULL(낙찰 응답 `dminsttCd/Nm`으로 후행 보강 가능) | 개념 없음 → NULL | NULL |
| `price_detail.assignedBudget` | `asignBdgtAmt` | `asignBdgtAmt` | 상세 `budgetAmount` | 목록-단계 D2B 행은 키 없음 |
| `price_detail.estimatedPrice` → `estimated_price`(V11) | `presmptPrce` | **없음**(예가 체계 부재 — 기준금액 `refAmt`는 별도 키 `referenceAmount`) | 상세 `estmPrce` (목록 `bsicExpt`는 `basicExpectedPrice`로 분리 — 현행 팬아웃의 `presmptPrce` 매핑과 정책 통일 필요, §4.1 ⚠) | NULL — 금액 **정렬**에서만 후순위. **필터는 `filter_amount`를 본다**(아래 줄) |
| `filter_amount`(V20260814113541) — 검색 전용 대표 금액 | `estimatedPrice`(입찰·마감·계획) / `assignedBudget`(사전규격) | `assignedBudget`, 없으면 `referenceAmount` | `basicExpectedPrice`, 상세가 붙으면 `estimatedPrice`/`assignedBudget` | 후보가 전부 없거나 0이면 NULL — **그 행은 금액 조건에서 빠진다**(실측 2,180건 = 4.2%, 화면이 문장으로 알린다) |
| `created_date` (게시) | `bidNtceDt` | `nticeDt`(게시)·`rgstDt`(등록) — 게시일 우선 | **확인 필요** — 요청 파라미터 `anmtDateBegin/End`(공고일)는 있으나 응답 필드 미확인. 확인 전 수집시각 대체 금지, NULL | NULL 허용(기존) |
| `close_date` (마감) | `bidClseDt` | `bidClseDt` | `biddocPresentnClosDt`(경쟁)/`prqudoPresentnClosDate` 계열(공개수의 — 응답 필드명 **확인 필요**) | NULL이면 마감 스위퍼 미전이 — 스위퍼는 close_date 기준 동작 유지, 소스 조건 불요 |
| `state` | `ntceKindNm` 매핑(기존) | `ntceDivNm`: 재공고→'재', 변경공고→'정정', 취소공고→'취소', 긴급공고→'긴급', 등록공고→NULL | `pblancSe`: 정상→NULL, 긴급→'긴급', 정정→'정정', 취소→'취소', 연기→'연기' | 평시 NULL(기존 규약) |
| `business_division` | 오퍼레이션 접미 물품/공사/용역/외자 | 오퍼레이션 접미 물품/공사/용역/**기타** | `busiDivs`(물품/용역)+시설 오퍼→'공사', 국외 오퍼→'외자' | NOT NULL — 매핑 불가 시 행 폐기 |
| `region` | 참가가능지역 오퍼(기존) | `getPrvtBidPblancListInfoPrtcptPsblRgn`의 `prtcptPsblRgnNm`(공고당 N행 → 콤마 결합, 기존 규약) | 상세의 지역제한 목록(**응답 필드명 확인 필요**) — 1차는 '' | '' = 전국(기존 규약의 한계 — D2B에선 '미상'과 구분 불가, 문서화) |
| `source_url` | `bidNtceDtlUrl`/`bidNtceUrl` | **없음 → NULL**(공고 URL 필드는 나라장터 전용; `bssAmtDtlScrnUrl`은 차세대 이후 제공 불가 명시) | **없음 → NULL**(첨부·URL 필드 부재 — d2b-openapi/INDEX.md §④). d2b.go.kr 화면 딥링크 합성은 **확인 필요** | NULL이면 프론트가 "원문 링크 없음" 표시 |
| `attachment_urls` | `ntceSpecDocUrl1~10`+`ntceSpecFileNm1~10` | `ntceSpecDocUrl1~10`+`ntceSpecDocNm1~10`(**파일명 필드명이 다름** 주의) | **없음 → NULL** | NULL(빈 배열 아님)로 통일 — 기존 규약 유지 |
| `g2b_pblanc_no/odr` | — | — | `g2bPblancNo`/`g2bPblancOdr` | D2B 외 NULL |
| `notice_body`(FULLTEXT 대상) | 기존 결합 규칙 | `ntceNm`+상세목록(`servcDtlList`/`prdctDtlList`/`cnstwkDtlList` 파싱 결과)+`bidMethdNm`+`cntrctMthdNm`+면허제한(`lcnsLmtNm`) 등 결합 | `bidNm`+`cntrctMth`+`reprsntPrdlstNm`(조달계획) 등 결합 | 결합 소스가 없으면 notice_name만 |

---

## 7. 운영 테이블 수용성

**결론: 4종 모두 DDL 변경 없이 수용 가능. 단 service_id 네임스페이스 규약 1건이 필수다.**

| 테이블 | 키 구조(실측) | 수용성 판단 |
|---|---|---|
| `sync_state` | PK `(service_id, operation, inqry_div VARCHAR(64) DEFAULT '')` (V1) | **충돌 1건 주의**: D2B 계약 서비스명이 `CntrctInfoService`로 **나라장터 계약 서비스와 동명**. bare 이름을 쓰면 워터마크가 섞인다 → `service_id` 소스 접두 규약 강제: `d2b:CntrctInfoService`, `nuri:PrvtBidNtceService`(Prvt로 이미 유일하나 일관성 위해 동일 적용). 최장 24자 < 64 ✔. `inqry_div`: 누리는 오퍼레이션마다 의미가 달라(nuri-openapi.md §4-(1)) `'1:rgstDt'`처럼 의미 병기 권장. D2B는 inqryDiv 개념이 없고 기간축이 오퍼별로 달라 기간축 이름(`'opengDate'` 등)을 넣는다 |
| `g2b_sync_coverage` | PK `(service_id, operation, inqry_div, coverage_date)` (V1) | 구조는 소스 무관 — 같은 접두 규약으로 수용. 이름의 `g2b_` 접두는 부정확해지지만 rename은 실익 없어 주석 처리. 누리 계약 PPSSrch의 8자리 `YYYYMMDD` 파라미터도 `coverage_date DATE`에 맞음 |
| `api_call_log` | PK `call_id`, `service_id/operation VARCHAR(64)`, `params_json` (V1) | 그대로 수용. D2B XML 응답도 `result_code TEXT`가 흡수. 트래픽 상한이 극단적으로 다른 점(D2B 개발계정 일 100건)은 레이트리밋 로직 이슈 |
| `bid_notice_sync_state` | PK `source VARCHAR(64)`, 키 형식 `'bid-announce:물품'` (V7) | 키 문자열 확장으로 수용: `nuri:bid-announce:용역`, `d2b:bid-announce:국내물품`, `d2b:plan:국내` 등 < 64 ✔. 누리 상세 3종(OpengCompt 등)은 기간조회가 없어 워터마크 대상이 아니고 2단계 파이프라인의 파생 호출로만 존재 |
| `sync_schedule` | UNIQUE `(time_of_day, scope)`, scope=curated/important/all (V5) | 전 소스 일괄이면 무변경. 소스별 시각 분리가 필요해지면 scope 값 확장(`all-nuri` 등)으로 해결, DDL 불요 |

---

## 8. 마이그레이션·롤아웃 순서

### 마이그레이션 번호

V1~V14 는 순번이고 그 안에서 V9/V10 은 `feat/price-catalog`, V12 는 created_sort 가 선점했다.
**V14 가 마지막 순번이다** — 이후 신규는 `./tools/new-migration.sh` 가 만드는 14자리
타임스탬프다. 아래 표의 `V15` 는 계획 라벨이며, 실제 파일명은
`V20260812093015__d2b_staging_and_dims.sql` 꼴이 된다.

| 버전 | 내용 | 위험도 |
|---|---|---|
| **V13** | `bid_notice` 소스 차원: `source` ENUM(DEFAULT 'G2B' 백필), `business_division`+'기타', `state`+'긴급','연기'(말미 append=INSTANT), PK `(id, source)` 재정의(리빌드, 2만 행), `source_ext` JSON, `g2b_pblanc_no/odr`+인덱스, `(source, category, created_date)` 인덱스 | 중 — PK 리빌드 1회. 기존 upsert SQL은 컬럼 미지정 시 DEFAULT 'G2B'로 구버전 코드와 호환 |
| **V14** | 파생 테이블 파급: `saved_notice`·`dwt_notice_scan` PK에 source, `analysis_history.notice_source` + `reuse_key_bn` VIRTUAL 재생성 | 중 — UNIQUE 재생성은 기존 전 행이 G2B라 단사 대응, 안전 |
| **V15** | D2B stg 테이블군 + `dm_d2b_ornt` (raw는 `raw_generic_list` 재사용 검토 — **확인 필요** 후 필요 시 `raw_d2b_list`) | 저 — 신설만 |
| (후행) V16+ | 누리 stg→dwt 병합층(`g2b-operations.json` **생성기 수정** 필요 — 손편집 불가), 누리 낙찰·계약 dwt, `dm_nsn` | dwt 적재기 이식(원본 CLI 의존 해소)과 동시 진행 조건 |

### 롤아웃 순서

1. **V13+V14 적용** — 코드 변경 없이도 무해(전 행 G2B 백필, `WHERE id=?`는 PK 프리픽스로 유효).
   단 `id` 단독 조회가 이론상 최대 3행을 반환할 수 있으므로, 적용 직후 배포하는 백엔드부터 조회·upsert·
   저장·분석 경로에 `source` 조건을 넣는다. 팬아웃 `_source ∈ {g2b, private-g2b, d2b}` ↔ DB
   `{G2B, NURI, D2B}` 매핑표를 단일 유틸로.
2. **누리장터 색인 적재 가동** — `BidNoticeIngestService.buildSources()`에 누리 출처 추가(공고 목록
   4종, inqryDiv=1 등록일시 증분; 취소·변경은 ChgHstry 오퍼레이션이 없으므로 `ntceDivNm` 재수집으로
   갱신 — nuri-openapi.md §4-(5)), `BidNoticeMapper`에 fromPrivateNotice 계열 추가(§6 매트릭스),
   면허제한·참가가능지역 부속 오퍼 반영. 워터마크 키 `nuri:*`. 기간 슬라이스 1개월 이하
   (nuri-openapi.md §4-(3)).
3. **D2B 색인 적재 가동** — V14 stg 경유 또는 색인 직행(기존 G2B 색인 적재가 raw/stg를 건너뛰는
   선례와 동형). `dm_d2b_ornt` 선적재 후 목록 수집. **일 100건 쿼터**(운영 증량 전)에 맞춘 보수적
   스케줄 필수. `g2b_pblanc_no` 채움과 함께 **교차 게시 중복 정책** 가동: `bid_notice(D2B행).g2b_pblanc_no
   = bid_notice(G2B행).id` 매칭 시 병합/배지 표시(교차 게시 범위는 추정이므로 매칭률 실측 후 확정).
4. **검색 API 노출** — `BidNoticeQueryBuilder`에 source 필터·facet, `SOURCE_COVERAGE` 하드코딩을
   색인 실데이터 기반으로 교체, `docs/api-contract.md` §A-2 갱신. SSRF 허용목록은 누리 첨부가
   `www.g2b.go.kr`, D2B가 `d2b.go.kr`이라 기존 목록으로 충족 — 단 `nopenapi.g2b.go.kr` 계열 URL이
   응답에 나타나는지 **확인 필요**.
5. **팬아웃 경로 축소** — 색인 커버리지 확인 후 `/api/bid-announce`를 색인 우선 + 실시간 보충으로
   전환(별도 결정, 스키마 무관).
6. **(후행) 웨어하우스 정합** — dwt 적재기 이식 시 V15+로 누리 병합층·낙찰·계약 진행. 이때도
   `dwt_bid_notice` PK는 건드리지 않고 소스별 테이블(C안의 웨어하우스판)로 간다 —
   `dm_institution` 하드 FK가 코드 없는 소스와 양립하지 않는다는 §5.1의 사실이 그때도 유효하다.

---

## 9. 검증 상태

**실측 확인(2026-08-11)** — 본 문서의 전제가 되는 사실:

- V11 실존: `estimated_price` VIRTUAL + DESC 인덱스 2본, `bid_notice` 20,403행, **V9/V10
  feat/price-catalog 선점** 주석(`V11:30-34`). ※ 이 문장은 당시 기준이다 — 지금 신규는
  순번이 아니라 타임스탬프다(위 §8 마이그레이션 번호).
- `bid_notice` PK `(id)` 단독·ENUM 3종 값·FULLTEXT·`bid_notice_sync_state` PK `source`(V7),
  `saved_notice`·`dwt_notice_scan` PK `(bid_ntce_no, bid_ntce_ord)`(V5),
  `reuse_key_bn` VARCHAR(384) VIRTUAL + UNIQUE(V6), `stg_prvt_*` 13종의 `stg_id` 서러게이트 PK(V4),
  `sync_state`·`g2b_sync_coverage`·`api_call_log` 키 구조(V1).
- 소스 충돌 주석과 중복 제거 키 `_source|bidNtceNo`(`NoticeSearchSupport.java`),
  `"D2B-"` 합성 키·`pblanc0dr` 오타 폴백·기본 차수 `"1"`·`bsicExpt→presmptPrce` 매핑
  (`D2bNormalizer.java`), `SOURCE_COVERAGE` 하드코딩(`NoticeController.java`),
  backfill 501 NOT_PORTED(`SystemController.java`).
- D2B API: 대표 3개 오퍼레이션 serviceKey 없이 호출 → `SERVICE_KEY_IS_NULL`(실존), 가짜 오퍼레이션
  → `NO_OPENAPI_SERVICE_ERROR`(음성 대조). Swagger 7종 추출·보존(경로 수 12/19/7/3/1/2/1 —
  [d2b-openapi/INDEX.md](d2b-openapi/INDEX.md) §②와 일치).

**미확인(본문에 "확인 필요"로 표시)**: 누리·나라장터 차세대 공고번호 발번 공간 공유 여부 /
D2B `orntCode`와 조달청 7자리 코드의 형식 충돌 여부 / D2B 목록 응답의 게시일 필드 /
D2B 공개수의 마감·지역제한 응답 필드명 / 누리 사업자번호 마스킹 여부 / `raw_generic_list`의
XML 원문 수용 적합성 / KDSIS 응답 필드 / d2b.go.kr 화면 딥링크 합성 가능성 /
`nopenapi.g2b.go.kr` URL의 응답 내 등장 여부.
