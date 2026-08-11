# 국방전자조달시스템(D2B) 공개 OpenAPI 전수 조사 (기준일 2026-08-10)

## ① 개요

- **운영주체**: 방위사업청(DAPA, 기관코드 `1690000`). D2B(www.d2b.go.kr)는 방위사업청이 운영하는 **국방전자조달시스템**으로, 군수품(물품·용역)·시설공사·국외(외자) 조달의 조달계획 → 입찰공고 → 개찰/협상결과 → 계약에 이르는 전 주기를 처리한다. 관리부서: 정보화데이터담당관실(02-2079-6787).
- **나라장터(G2B)와의 관계**: D2B는 나라장터(조달청, 기관코드 1230000)와 별개의 자체 전자조달시스템이다. 다만 D2B API 응답에 **`g2bPblancNo`(G2B공고번호)·`g2bPblancOdr`(G2B공고차수) 필드가 존재**하여, D2B 공고가 나라장터에 교차 게시될 때의 G2B 공고번호 매핑을 API 차원에서 제공한다(국내·국외·시설 공고 목록/상세 공통). 즉 **중복 수집 시 g2bPblancNo로 나라장터 데이터와 조인/중복제거 가능**하다. 교차 게시의 제도적 범위(전량인지 일부인지)는 2차 자료 수준의 추정임(⑥ 참조).
- **엔드포인트 이관(중요)**: 과거에는 `openapi.d2b.go.kr/openapi/service/...` 직접 호출형 서비스(데이터셋 15002017/15002018/15002019/15002040, 해외입찰 15020338, 국방표준 15064293)였으나, **해당 상세 페이지는 전부 404로 폐기**되었고, 현재는 서비스명에 `_GW` 접미가 붙은 후속 서비스가 **공공데이터포털 게이트웨이 `apis.data.go.kr/1690000/...`** 경유로 제공된다(구 서비스와 서비스명/오퍼레이션명은 사실상 동일 계열: BidPblancInfoService 등). `openapi.d2b.go.kr` 호스트(125.61.29.189)는 아직 DNS에 살아 있으나 해외 IP에서는 WAF가 일괄 400을 반환해 직접 검증 불가.

## ② 서비스 카탈로그 (2026-08-10 기준, data.go.kr 등록 방위사업청 OpenAPI 전수 — 총 7건)

| # | 서비스명 | Base URL | 오퍼 수 | data.go.kr | 포맷 | 트래픽 | 승인 |
|---|---|---|---|---|---|---|---|
| 1 | 군수품조달정보 **입찰공고**_GW (BidPblancInfoService) | `apis.data.go.kr/1690000/BidPblancInfoService` | 12 | [15158416](https://www.data.go.kr/data/15158416/openapi.do) | XML | 개발 100/일, 운영 활용사례 등록 시 증량 | 개발·운영 모두 자동승인 |
| 2 | 군수품조달정보 **입찰결과**_GW (BidResultInfoService) | `apis.data.go.kr/1690000/BidResultInfoService` | 19 | [15158417](https://www.data.go.kr/data/15158417/openapi.do) | XML | 동일 | 자동승인 |
| 3 | 군수품조달정보 **조달계획**_GW (PrcurePlanInfoService) | `apis.data.go.kr/1690000/PrcurePlanInfoService` | 7 | [15158418](https://www.data.go.kr/data/15158418/openapi.do) | XML | 동일 | 자동승인 |
| 4 | 군수품조달정보 **계약정보**_GW (CntrctInfoService) | `apis.data.go.kr/1690000/CntrctInfoService` | 3 | [15158419](https://www.data.go.kr/data/15158419/openapi.do) | XML | 동일 | 자동승인 |
| 5 | 군수품조달정보 **코드조회**_GW (CodeInqireService) | `apis.data.go.kr/1690000/CodeInqireService` | 1 | [15158489](https://www.data.go.kr/data/15158489/openapi.do) | XML | 동일 | 자동승인 |
| 6 | **해외입찰정보**_GW (DCAIBidInfoService, 방산수출입지원시스템) | `apis.data.go.kr/1690000/DCAIBidInfoService` | 2 | [15158420](https://www.data.go.kr/data/15158420/openapi.do) | XML | 동일 | 자동승인 |
| 7 | **국방표준종합서비스**_GW (KDSISInfoService, 국방규격) | `apis.data.go.kr/1690000/KDSISInfoService` | 1 | [15158490](https://www.data.go.kr/data/15158490/openapi.do) | XML | 동일 | 자동승인 |

- 1~5가 D2B 본체 데이터, 6은 D2B 도메인에서 제공되던 방산수출입지원(해외 발주처 입찰정보), 7은 국방규격(KDS) 조회로 조달계획·공고와 규격번호로 연계된다. 등록일은 1~5가 2016-06-01(구 서비스 승계), 6이 2018-10-23, 7이 2020-09-21. 수정일 전부 2026-05-18~20.
- 비용 무료. 예상 후보였던 "개찰결과정보"는 별도 서비스가 아니라 **입찰결과_GW**에 통합(개찰일시·복수예비가격·참가업체 포함). D2B 웹에 존재하는 **매각(판매)입찰 게시판은 OpenAPI 미제공**(카탈로그에 없음).

## ③ 서비스별 오퍼레이션과 주요 필드

모든 오퍼레이션 공통: GET, `serviceKey`(필수) + `pageNo`/`numOfRows` 페이지네이션, 응답 envelope `response > header(resultCode, resultMsg) + body(totalCount, pageNo, numOfRows, items > item[])`, produces `application/xml`.

### 1. BidPblancInfoService (입찰공고, 12오퍼)
물품(국내/국외) × 시설, 경쟁입찰 × 공개수의협상 매트릭스 + 품목명세서:

| 오퍼레이션 | 내용 | 주요 요청 파라미터 |
|---|---|---|
| getDmstcCmpetBidPblancList / Detail | 국내 경쟁입찰공고 목록/상세 | opengDateBegin/End(개찰일), anmtDateBegin/End(공고일), orntCode, bidNm, g2bPblancNoOdr / 상세: demandYear+orntCode+dcsNo+pblancNo+pblancOdr |
| getDmstcOthbcVltrnNtatPlanList / Detail | 국내 공개수의협상계획 목록/상세 | prqudoPresentnClosDateBegin/End(견적마감), othbcNtatNm, pblancOrDcsNo |
| getOutnatnCmpetBidPblancList / Detail | 국외(외자) 경쟁입찰공고 목록/상세 | opengDateBegin/End, pblancNo, g2bPblancNo / 상세: pblancYear+pblancNo+pblancOdr+dcsNo+groupNo |
| getFcltyCmpetBidPblancList / Detail | 시설 경쟁입찰공고 목록/상세 | cntrwkNm(공사명), orntCode / 상세: pblancYear+pblancSeCode+pblancNo+pblancOdr+cntrwkNo |
| getFcltyOthbcVltrnNtatPlanList / Detail | 시설 공개수의협상계획 목록/상세 | pblancNoOrCntrwkNo 등 |
| getDmstcCmpetBidPblancItem / getOutnatnCmpetBidPblancItem | 국내/국외 품목명세서 | demandYear+dcsNo+orntCode / pblancYear+pblancNo+groupNo |

핵심 응답 필드 — 목록: `pblancNo/pblancOdr`(공고번호/차수), `pblancSe`(정상/긴급/정정/취소/연기), `demandYear`(요구년도), `dcsNo`(판단번호), `bidNm`(입찰명), `ornt/orntCode`(발주기관), `bidPartcptRegistClosDt`·`biddocPresentnClosDt`(참가등록·입찰서 마감), `opengDt`(개찰일시), `cntrctMth`, `bsicExpt`(기초예비가격), `g2bPblancNo/Odr`, `busiDivs`(물품/용역). 상세 추가: `budgetAmount`(예산금액), `estmPrce`(추정가격), `scsbidLwltRt`(낙찰하한율), `asessRtLwlt/Uplmt`(사정률), 면허제한·지역제한 목록, 사업설명회 일시/장소, 담당자. 품목명세서: `prcmRnum`(조달요구번호), `fsc/niin`(NSN), `specNumb`(규격번호), `partNumb`, 단가·수량·납기·납지. **첨부파일 URL 필드는 없음**(규격 정보는 Item 오퍼·KDSIS로 대체).

### 2. BidResultInfoService (입찰결과/개찰, 19오퍼)
- 결과 목록/상세: 국내 경쟁입찰(List/Detail), 국내 공개수의협상(List/Detail), 국외 경쟁입찰(List/Detail), 국외 협상(getOutnatnNtatResultList), 시설 경쟁입찰(List/Detail), 시설 공개수의협상(List/Detail).
- 부가: **참가업체**(…ResultMnufList: 물품 경쟁/물품 공개수의/시설 경쟁/시설 공개수의) 및 **복수예비가격**(…ResultBsicList: 동일 4종).
- 조회 파라미터: `opengDateBegin/End`(개찰일자) 또는 `ntatComptDateBegin/End`(협상완료일), `orntCode`, `bidNm`/`cntrwkNm`, `g2bPblancNoOdr`, 국외는 `purchsRequstNo`(구매요청번호).
- 핵심 응답: 목록 `bidResult`(입찰결과), `sucbidrDecsnMth`(낙찰자결정방법), `opengDt`; 참가업체 `mfkrName`(업체명), `bznsRgnb`(사업자번호), `tbidAmnt/tbidUtpr`(투찰금액), `bidnRank`(낙찰순위), `bidnRate`(낙찰율), `prceScor/totaScor`(가격·적격심사점수), `gpsTime`(투찰일시); 복수예비가격 `bsicSeqn`+`prdfPrce`+`choiYsno`(선택여부).

### 3. PrcurePlanInfoService (조달계획, 7오퍼)
- getDmstcPrcurePlanList/Detail(국내), getFcltyPrcurePlanList/Detail(시설), getOutnatnPrcurePlanList(국외), getOutnatnPrcurePlanDcsDtls/DcsDetail(국외 판단내역 목록/상세).
- 조회: `orderPrearngeMtBegin/End`(발주예정월), `demandYear`, `orntCode`, `reprsntPrdlstNm`, 국외는 `invntryNo`(재고번호)·`cmpntNo`(부품번호).
- 핵심 응답: `dcsNo`(판단번호), `reprsntPrdlstNm`(대표품목명), `budgetAmount`(예산금액), `orderPrearngeMt`, `excutTy`(집행유형), `cntrctMth`, `progrsSttus(Code)`(조달판단중→…→계약완료 상태), `spcifyPrcureAt`(특정조달여부); 국외는 NSN(`invntryNo`)·`qlityAssrncGrad`(품질보증등급)·장비코드/명 등.

### 4. CntrctInfoService (계약정보, 3오퍼 — 목록만)
- getDmstcCntrctInfoList / getOutnatnCntrctInfoList / getFcltyCntrctInfoList. 조회: `cntrctDateBegin/End`(계약일자, 필수 성격), `orntCode`, `cntrctNm`.
- 핵심 응답(국내/시설): `cntrctNo`, `cntrctNm`, `cntrctDate`, `cntrctEntrpsNm`(계약업체명), `cntrctAmnt`(계약금액), `cntrctPlanaAmnt`(예정가격), `cntrctBidnRate`(낙찰률), `cntrctSttus`, `dcsNo`. 국외 목록은 `cntrctNo/cntrctEntrpsNm/cntrctDate/cntrctDivs` 4개 필드로 매우 얇음.

### 5. CodeInqireService (1오퍼)
- getOrntCodeList(발주기관코드목록): 파라미터 `codeNm`, 응답 `code`+`codeNm`. 타 서비스의 `orntCode` 필터 기준정보.

### 6. DCAIBidInfoService (해외입찰정보, 2오퍼)
- getDCAIBidInfoList(`bidFrom/bidTo` 기간) / getDCAIBidInfoDetail(`seqn`). 해외 발주처 입찰·조달계획 수집정보: `bidNameKor/Eng`, `orgPlace(Nm)`(국가), `region(Nm)`, `anucType`(1 입찰공고/2 조달계획/3 기타), 상세에 `amnt`(총금액)·`currUnit`(화폐)·`orderOrgan`·무기체계 대/중/소분류·**`oriUrl`(원문 URL)**.

### 7. KDSISInfoService (국방표준, 1오퍼)
- getKDSISSpecNoList: NSN(`scNsn`)·규격번호(`scRqstno`)·품명(국/영)·CAGE코드 등 27개 검색 파라미터의 국방규격 목록 조회. D2B 조달과는 규격번호/NSN으로 연계.

### D2B 공고번호 체계 (필드 구조에서 확인)
- **국내 물품**: `요구년도(demandYear) + 발주기관(orntCode) + 판단번호(dcsNo) [+ 항목번호(iemNo)]`가 실질 키이며 공고는 `pblancNo + pblancOdr(차수)`. 즉 나라장터의 단일 `bidNtceNo`와 달리 **판단번호(조달판단 문서) 중심의 복합키**.
- **국외(외자)**: `pblancYear + pblancNo + pblancOdr + groupNo`, 구매요청번호(`purchsRequstNo`) 병행.
- **시설**: `cntrwkNo(공사번호)` 중심. 매각(판매)입찰은 API 부재.

## ④ 나라장터(조달청) API와의 구조적 차이

| 항목 | D2B (1690000) | 나라장터 (1230000) |
|---|---|---|
| 게이트웨이 | apis.data.go.kr 경유(_GW, 2016 등록 구서비스를 openapi.d2b.go.kr에서 이관) | apis.data.go.kr 네이티브 |
| 응답 포맷 | **XML 전용**(swagger produces application/xml, 포털 표기 XML. JSON `type` 파라미터 없음) | XML+JSON(`type=json` 지원) |
| 업무 구분 축 | 서비스 내부 오퍼레이션으로 분기: 물품(국내 Dmstc/국외 Outnatn) × 시설(Fclty) × 경쟁입찰(CmpetBid)/공개수의협상(OthbcVltrnNtat) | 물품/공사/용역/외자를 오퍼레이션 접미(Thng/Cnstwk/Servc/Frgcpt)로 분기 |
| 키 체계 | 판단번호(dcsNo)+요구년도+기관코드 복합키, 공고번호+차수 | 공고번호(bidNtceNo)+차수(bidNtceOrd) 단일 축 |
| 필드 명명 | 국문 로마자 축약(관용 표기: ornt=발주기관, dcs=판단, othbc=공개, prqudo=견적서…) — 나라장터와 같은 정부 축약 스타일이나 어휘가 상이. 일부 오퍼(복수예비가격·품목명세서)는 구세대 스타일 혼재(ordrYear, dmstItnb, bidxDate) | ntce/opengDt/presmptPrce 계열 |
| 조회 방식 | 기간 파라미터가 오퍼별 상이(개찰일/공고일/견적마감일/협상완료일/계약일/발주예정월) + 상세는 복합키 지정 필요 | inqryDiv+inqryBgnDt/EndDt 표준화 |
| 첨부/URL | 첨부파일·공고문 URL 필드 없음 | ntceSpecDocUrl 등 첨부 URL 제공 |
| 교차 참조 | g2bPblancNo/Odr로 나라장터 공고번호 역참조 제공 | D2B 참조 필드 없음 |
| 인증·페이지네이션 | 동일(serviceKey / pageNo·numOfRows·totalCount), 개발계정 트래픽 100은 나라장터 대비 매우 작음 | serviceKey, 트래픽 상이 |

## ⑤ 실검증 로그 (2026-08-10, 본 환경에서 직접 실행)

| 검증 | 결과 |
|---|---|
| `curl http://apis.data.go.kr/1690000/{svc}/{op}` — ②표 1~5의 **전 42개 오퍼레이션** + DCAIBidInfoService 2개 + KDSISInfoService 1개 (총 45개) | 전부 `<errMsg>SERVICE_KEY_IS_NULL</errMsg> returnReasonCode 20` → **엔드포인트 실존·가동 확인** |
| 동일 서비스에 가짜 오퍼(`BidPblancInfoService/getNoSuchOp`), 가짜 경로(`/1290000/...`) | `NO_OPENAPI_SERVICE_ERROR`(code 12) → 위 45건이 위양성 아님을 교차 확인 |
| `http://openapi.d2b.go.kr/openapi/service/...`(구 엔드포인트, 5개 경로) 및 루트 | 모든 경로에서 WAF HTML `400 Bad Request`(추적코드 포함) — 해외 IP 차단으로 실존 여부 판별 불가. DNS는 정상(125.61.29.189) |
| data.go.kr 상세 페이지 7건(15158416~20, 15158489, 15158490) 원문 HTML 확보, 내장 Swagger JSON 파싱 | 오퍼레이션·파라미터·응답 스키마·Base URL 전부 원문에서 추출 |
| 구 데이터셋 15002017/15002019/15002040/15020338/15064293 | data.go.kr **404**(포털 자체 "페이지를 찾을 수 없습니다") → 폐기 확정. 대조군 15129394(나라장터)·포털 메인은 정상 응답 |
| data.go.kr 검색(dType=API, 키워드 "국방전자조달"=5건, "방위사업청"=오픈API 9건 중 방사청 제공 7건) | ②표가 전수임을 확인(법제처 제공 2건 제외) |

## ⑥ 확신도

**직접 확인(높음)** — 원문 페이지/스키마/실호출 근거:
- 7개 서비스의 존재·Base URL·오퍼레이션 목록·요청 파라미터·응답 필드·XML 전용·트래픽(개발 100)·자동승인·수정일: data.go.kr 원문 HTML 내장 Swagger에서 추출.
- 45개 전 오퍼레이션의 가동: SERVICE_KEY_IS_NULL 응답으로 확인(단, 키 없이는 실데이터·필드 실측은 불가).
- 구(비-GW) 데이터셋 페이지 폐기(404), 현행 카탈로그가 7건 전수라는 점.
- `g2bPblancNo/Odr` 필드 존재(교차 게시 매핑 제공).

**2차 자료·추정(중간~낮음)**:
- 구 서비스가 `openapi.d2b.go.kr/openapi/service/...` 직접 호출형이었다는 점: 검색 스니펫 근거(구 URL 실측은 WAF 400으로 불가).
- 나라장터 교차 게시의 제도적 범위(전 공고 게재 여부): g2bPblancNo 필드와 일반적 게재 관행에서 추정. 실데이터로 커버리지 미검증.
- "매각(판매)입찰 API 부재": data.go.kr 카탈로그 부재 기준의 소극적 결론(D2B 내부/비공개 API 존재 가능성 배제 못함).
- JSON 미지원: swagger `produces`와 포털 포맷 표기 기준. 게이트웨이 차원의 비문서화 `_type=json` 지원 여부는 인증키 없이 미검증.
- 개발계정 트래픽 "100"의 단위(일별)는 포털 관례에 따른 해석.

## ⑦ 문서 구성

```
d2b-openapi/
  INDEX.md          <- 이 문서
  swagger/*.json    <- 서비스별 Swagger 2.0 명세 — data.go.kr 상세 페이지 내장 스펙을 추출해 보존
                       (BidPblancInfoService, BidResultInfoService, PrcurePlanInfoService,
                        CntrctInfoService, CodeInqireService, DCAIBidInfoService, KDSISInfoService)
```

- 스펙 추출·보존일 2026-08-11. 경로 수 실측: 12 / 19 / 7 / 3 / 1 / 2 / 1 — ②의 오퍼 수와 일치.
- 2026-08-11 표본 재검증: `getDmstcCmpetBidPblancList`·`getDmstcCntrctInfoList`·`getDCAIBidInfoList`에
  serviceKey 없이 호출 → 전부 `SERVICE_KEY_IS_NULL`(코드 20), 가짜 오퍼레이션은
  `NO_OPENAPI_SERVICE_ERROR`(코드 12) — ⑤의 결론 재확인.
- 나라장터·누리장터 명세는 [../g2b-openapi/INDEX.md](../g2b-openapi/INDEX.md),
  누리장터 심층 분석은 [../nuri-openapi.md](../nuri-openapi.md),
  DB 반영 방안은 [../multi-source-schema.md](../multi-source-schema.md) 참조.
