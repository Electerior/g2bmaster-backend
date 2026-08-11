# 나라장터(조달청) 오픈API 문서 모음

공공데이터포털(data.go.kr)에 공개된 **조달청 제공 오픈API 18종**의 공식 명세를 한 번에 내려받아 정리한 것.
수집일 2026-08-10 · 서비스 18종 · 오퍼레이션 191개 · 참고문서 18건

> 관련 문서 — 누리장터 3종(서비스 16~18)의 심층 분석(나라장터와의 필드 diff·수집 주의점)은
> [../nuri-openapi.md](../nuri-openapi.md), 국방전자조달 D2B(방위사업청, 1690000)의 API 전수 조사는
> [../d2b-openapi/INDEX.md](../d2b-openapi/INDEX.md), 세 소스를 한 서비스에 표시하기 위한 DB 스키마
> 분석은 [../multi-source-schema.md](../multi-source-schema.md) 참조.

```
g2b-openapi/
  INDEX.md          <- 이 문서
  index.json        <- 서비스 메타데이터(기계 판독용)
  swagger/*.json    <- 서비스별 Swagger 2.0 명세 (오퍼레이션 + 응답 필드 전체)
  guides/*.docx     <- 조달청 공식 OpenAPI 참고자료 원본
  guides-md/*.md    <- 위 docx를 마크다운으로 변환 (요청/응답 파라미터 표 포함)
```

## 1. 공통 규약

| 항목 | 값 |
|---|---|
| 인증 | `serviceKey` 쿼리 파라미터 (공공데이터포털 발급 키, URL 인코딩 여부 주의) |
| 프로토콜 | REST GET, HTTPS 권장 (`http`도 응답하나 문서상 개발/운영 모두 `http://apis.data.go.kr`로 표기) |
| 응답 형식 | `type=json` 또는 `type=xml` (기본 XML) |
| 기관코드 | `1230000` (조달청) |
| 일일 트래픽 | 개발계정 1,000건 / 운영계정은 활용사례 등록 후 증량 신청 |
| 심의 | 개발·운영 모두 자동승인, 무료, 이용허락범위 제한 없음 |

### 공통 요청 파라미터

| 파라미터 | 필수 | 설명 | 예시 |
|---|---|---|---|
| `serviceKey` | 필수 | 공공데이터포털에서 발급받은 인증키 | - |
| `pageNo` | 필수 | 페이지번호 | `1` |
| `numOfRows` | 필수 | 한 페이지 결과 수 | `10` |
| `inqryDiv` | 필수 | 조회구분 (1=등록일시, 2=입찰공고번호, 3=변경일시 … 오퍼레이션마다 의미 다름) | `1` |
| `inqryBgnDt` / `inqryEndDt` | 옵션 | 조회 시작·종료 일시 `yyyyMMddHHmm` | `202601010000` |
| `type` | 옵션 | 응답 형식 | `json` |

`inqryDiv=1`(일시 기준) 조회는 대부분 **조회 구간 상한(보통 1개월)** 이 있어 기간 윈도잉이 필요하다.

> **인증키 파라미터 표기** — 참고문서마다 `serviceKey`(입찰공고정보서비스)와 `ServiceKey`(그 외 17종)로
> 표기가 갈린다. 게이트웨이에 직접 확인한 결과 `serviceKey` / `ServiceKey` / `servicekey` 모두 동일하게
> 동작하므로 대소문자는 무관하다. 키 자체를 빼면 코드 `20`(`SERVICE_KEY_IS_NULL`)이 돌아온다.

### 응답 구조

```json
{ "response": { "header": { "resultCode": "00", "resultMsg": "NORMAL SERVICE." },
               "body":   { "numOfRows": 10, "pageNo": 1, "totalCount": 123,
                           "items": [ { "...": "..." } ] } } }
```

### 에러코드

| 코드 | 의미 | 비고 |
|---|---|---|
| `00` | NORMAL SERVICE | 정상 |
| `01` | APPLICATION ERROR | 서비스 제공 상태 정상 아님 |
| `02` | DB ERROR | 제공기관 DB 오류 |
| `03` | NO DATA | 데이터 없음 |
| `04` | HTTP ERROR |  |
| `05` | SERVICE TIMEOUT |  |
| `06` | 날짜 Format 에러 | `yyyyMMddHHmm` 확인 |
| `07` | 입력범위값 초과 | 조회 기간/페이지 범위 초과 |
| `08` | 필수값 입력 에러 | 필수 파라미터 누락 |
| `10` | 잘못된 요청 파라미터 | `serviceKey` 누락 등 |
| `11` | 필수 요청 파라미터가 없음 |  |
| `12` | 해당 오픈API 서비스가 없거나 폐기됨 | 엔드포인트 변경 확인 |
| `20` | 서비스 접근거부 | 활용신청 필요 |
| `22` | 서비스 요청제한 횟수 초과 | 일일 트래픽 초과 |
| `30` | 등록되지 않은 서비스키 |  |
| `31` | 기한만료된 서비스키 |  |
| `32` | 등록되지 않은 도메인/IP |  |

### 호출 예시

```bash
curl -G 'https://apis.data.go.kr/1230000/ad/BidPublicInfoService/getBidPblancListInfoThng' \
  --data-urlencode 'serviceKey=<발급키>' \
  -d 'pageNo=1' -d 'numOfRows=10' -d 'type=json' \
  -d 'inqryDiv=1' -d 'inqryBgnDt=202608010000' -d 'inqryEndDt=202608102359'
```

## 2. 서비스 카탈로그

| # | 서비스 ID | 한글명 | 경로 | 오퍼 | data.go.kr |
|---|---|---|---|---|---|
| 1 | `BidPublicInfoService` | 나라장터 입찰공고정보서비스 | `/ad/BidPublicInfoService` | 25 | [15129394](https://www.data.go.kr/data/15129394/openapi.do) |
| 2 | `ScsbidInfoService` | 나라장터 낙찰정보서비스 | `/as/ScsbidInfoService` | 23 | [15129397](https://www.data.go.kr/data/15129397/openapi.do) |
| 3 | `CntrctInfoService` | 나라장터 계약정보서비스 | `/ao/CntrctInfoService` | 21 | [15129427](https://www.data.go.kr/data/15129427/openapi.do) |
| 4 | `HrcspSsstndrdInfoService` | 나라장터 사전규격정보서비스 | `/ao/HrcspSsstndrdInfoService` | 20 | [15129437](https://www.data.go.kr/data/15129437/openapi.do) |
| 5 | `PrcrmntReqInfoService` | 나라장터 조달요청서비스 | `/ao/PrcrmntReqInfoService` | 12 | [15129468](https://www.data.go.kr/data/15129468/openapi.do) |
| 6 | `OrderPlanSttusService` | 나라장터 발주계획현황서비스 | `/ao/OrderPlanSttusService` | 8 | [15129462](https://www.data.go.kr/data/15129462/openapi.do) |
| 7 | `PriceInfoService` | 나라장터 가격정보현황서비스 | `/ao/PriceInfoService` | 11 | [15129415](https://www.data.go.kr/data/15129415/openapi.do) |
| 8 | `CntrctProcssIntgOpenService` | 나라장터 계약과정통합공개서비스 | `/ao/CntrctProcssIntgOpenService` | 4 | [15129459](https://www.data.go.kr/data/15129459/openapi.do) |
| 9 | `UsrInfoService02` | 나라장터 사용자정보 서비스 | `/ao/UsrInfoService02` | 5 | [15129466](https://www.data.go.kr/data/15129466/openapi.do) |
| 10 | `IndstrytyBaseLawrgltInfoService` | 나라장터 업종 및 근거법규서비스 | `/ao/IndstrytyBaseLawrgltInfoService` | 1 | [15129467](https://www.data.go.kr/data/15129467/openapi.do) |
| 11 | `ShoppingMallPrdctInfoService` | 나라장터쇼핑몰 품목정보 서비스 | `/at/ShoppingMallPrdctInfoService` | 9 | [15129471](https://www.data.go.kr/data/15129471/openapi.do) |
| 12 | `ThngListInfoService02` | 물품목록정보서비스 | `/ao/ThngListInfoService02` | 13 | [15129417](https://www.data.go.kr/data/15129417/openapi.do) |
| 13 | `PrdctMngInfoService` | 물품관리정보서비스 | `/ao/PrdctMngInfoService` | 1 | [15129470](https://www.data.go.kr/data/15129470/openapi.do) |
| 14 | `PubPrcrmntStatInfoService` | 공공조달통계정보서비스 | `/at/PubPrcrmntStatInfoService` | 14 | [15129412](https://www.data.go.kr/data/15129412/openapi.do) |
| 15 | `PubDataOpnStdService` | 나라장터 공공데이터개방표준서비스 | `/ao/PubDataOpnStdService` | 3 | [15058815](https://www.data.go.kr/data/15058815/openapi.do) |
| 16 | `PrvtBidNtceService` | 누리장터 민간입찰공고서비스 | `/ao/PrvtBidNtceService` | 10 | [15129456](https://www.data.go.kr/data/15129456/openapi.do) |
| 17 | `PrvtScsbidInfoService` | 누리장터 민간낙찰정보_서비스 | `/ao/PrvtScsbidInfoService` | 7 | [15129458](https://www.data.go.kr/data/15129458/openapi.do) |
| 18 | `PrvtCntrctInfoService` | 누리장터 민간계약정보 서비스 | `/ao/PrvtCntrctInfoService` | 4 | [15129469](https://www.data.go.kr/data/15129469/openapi.do) |

## 3. 서비스별 오퍼레이션

### 조달청_나라장터 입찰공고정보서비스 — `BidPublicInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ad/BidPublicInfoService`
- 포털: https://www.data.go.kr/data/15129394/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_입찰공고정보서비스_1.2.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_입찰공고정보서비스_1.2.md`
- 명세: `swagger/BidPublicInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getBidPblancListBidPrceCalclAInfo` | 입찰공고목록 정보에 대한 입찰가격산식A정보조회 |
| `getBidPblancListEvaluationIndstrytyMfrcInfo` | 입찰공고목록 정보에 대한 평가대상 주력분야 조회 |
| `getBidPblancListInfoChgHstryCnstwk` | 입찰공고목록 정보에 대한 공사변경이력조회 |
| `getBidPblancListInfoChgHstryServc` | 입찰공고목록 정보에 대한 용역변경이력조회 |
| `getBidPblancListInfoChgHstryThng` | 입찰공고목록 정보에 대한 물품변경이력조회 |
| `getBidPblancListInfoCnstwk` | 입찰공고목록 정보에 대한 공사조회 |
| `getBidPblancListInfoCnstwkBsisAmount` | 입찰공고목록 정보에 대한 공사기초금액조회 |
| `getBidPblancListInfoCnstwkPPSSrch` | 나라장터검색조건에 의한 입찰공고공사조회 |
| `getBidPblancListInfoEorderAtchFileInfo` | 입찰공고목록 정보에 대한 e발주 첨부파일정보조회 |
| `getBidPblancListInfoEtc` | 입찰공고목록 정보에 대한 기타공고조회 |
| `getBidPblancListInfoEtcPPSSrch` | 나라장터검색조건에 의한 입찰공고 기타조회 |
| `getBidPblancListInfoFrgcpt` | 입찰공고목록 정보에 대한 외자조회 |
| `getBidPblancListInfoFrgcptPPSSrch` | 나라장터검색조건에 의한 입찰공고외자조회 |
| `getBidPblancListInfoFrgcptPurchsObjPrdct` | 입찰공고목록 정보에 대한 외자 구매대상물품조회 |
| `getBidPblancListInfoLicenseLimit` | 입찰공고목록 정보에 대한 면허제한정보조회 |
| `getBidPblancListInfoPrtcptPsblRgn` | 입찰공고목록 정보에 대한 참가가능지역정보조회 |
| `getBidPblancListInfoServc` | 입찰공고목록 정보에 대한 용역조회 |
| `getBidPblancListInfoServcBsisAmount` | 입찰공고목록 정보에 대한 용역기초금액조회 |
| `getBidPblancListInfoServcPPSSrch` | 나라장터검색조건에 의한 입찰공고용역조회 |
| `getBidPblancListInfoServcPurchsObjPrdct` | 입찰공고목록 정보에 대한 용역 구매대상물품조회 |
| `getBidPblancListInfoThng` | 입찰공고목록 정보에 대한 물품조회 |
| `getBidPblancListInfoThngBsisAmount` | 입찰공고목록 정보에 대한 물품기초금액조회 |
| `getBidPblancListInfoThngPPSSrch` | 나라장터검색조건에 의한 입찰공고물품조회 |
| `getBidPblancListInfoThngPurchsObjPrdct` | 입찰공고목록 정보에 대한 물품 구매대상물품조회 |
| `getBidPblancListPPIFnlRfpIssAtchFileInfo` | 입찰공고목록 정보에 대한 혁신장터 최종제안요청서 교부 첨부파일정보조회 |

### 조달청_나라장터 낙찰정보서비스 — `ScsbidInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/as/ScsbidInfoService`
- 포털: https://www.data.go.kr/data/15129397/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_낙찰정보서비스_1.1.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_낙찰정보서비스_1.1.md`
- 명세: `swagger/ScsbidInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getOpengResultListInfoCnstwk` | 개찰결과 공사 목록 조회 |
| `getOpengResultListInfoCnstwkPPSSrch` | 나라장터 검색조건에 의한 개찰결과 공사 목록 조회 |
| `getOpengResultListInfoCnstwkPreparPcDetail` | 개찰결과 공사 예비가격상세 목록 조회 |
| `getOpengResultListInfoFailing` | 개찰결과 유찰 목록 조회 |
| `getOpengResultListInfoFrgcpt` | 개찰결과 외자 목록 조회 |
| `getOpengResultListInfoFrgcptPPSSrch` | 나라장터 검색조건에 의한 개찰결과 외자 목록 조회 |
| `getOpengResultListInfoFrgcptPreparPcDetail` | 개찰결과 외자 예비가격상세 목록 조회 |
| `getOpengResultListInfoOpengCompt` | 개찰결과 개찰완료 목록 조회 |
| `getOpengResultListInfoRebid` | 개찰결과 재입찰 목록 조회 |
| `getOpengResultListInfoServc` | 개찰결과 용역 목록 조회 |
| `getOpengResultListInfoServcPPSSrch` | 나라장터 검색조건에 의한 개찰결과 용역 목록 조회 |
| `getOpengResultListInfoServcPreparPcDetail` | 개찰결과 용역 예비가격상세 목록 조회 |
| `getOpengResultListInfoThng` | 개찰결과 물품 목록 조회 |
| `getOpengResultListInfoThngPPSSrch` | 나라장터 검색조건에 의한 개찰결과 물품 목록 조회 |
| `getOpengResultListInfoThngPreparPcDetail` | 개찰결과 물품 예비가격상세 목록 조회 |
| `getScsbidListSttusCnstwk` | 낙찰된 목록 현황 공사조회 |
| `getScsbidListSttusCnstwkPPSSrch` | 나라장터 검색조건에 의한 낙찰된 목록 현황 공사조회 |
| `getScsbidListSttusFrgcpt` | 낙찰된 목록 현황 외자조회 |
| `getScsbidListSttusFrgcptPPSSrch` | 나라장터 검색조건에 의한 낙찰된 목록 현황 외자조회 |
| `getScsbidListSttusServc` | 낙찰된 목록 현황 용역조회 |
| `getScsbidListSttusServcPPSSrch` | 나라장터 검색조건에 의한 낙찰된 목록 현황 용역조회 |
| `getScsbidListSttusThng` | 낙찰된 목록 현황 물품조회 |
| `getScsbidListSttusThngPPSSrch` | 나라장터 검색조건에 의한 낙찰된 목록 현황 물품조회 |

### 조달청_나라장터 계약정보서비스 — `CntrctInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/CntrctInfoService`
- 포털: https://www.data.go.kr/data/15129427/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_계약정보서비스_1.0.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_계약정보서비스_1.0.md`
- 명세: `swagger/CntrctInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getCntrctInfoListCnstwk` | 계약현황에 대한 공사조회 |
| `getCntrctInfoListCnstwkChgHstry` | 계약현황에 대한 공사변경이력조회 |
| `getCntrctInfoListCnstwkDltHstry` | 계약현황에 대한 공사삭제이력조회 |
| `getCntrctInfoListCnstwkPPSSrch` | 나라장터검색조건에 의한 계약현황 공사조회 |
| `getCntrctInfoListCnstwkServcInfo` | 계약현황에 대한 공사서비스정보조회 |
| `getCntrctInfoListFrgcpt` | 계약현황에 대한 외자조회 |
| `getCntrctInfoListFrgcptChgHstry` | 계약현황에 대한 외자변경이력조회 |
| `getCntrctInfoListFrgcptDetail` | 계약현황에 대한 외자세부조회 |
| `getCntrctInfoListFrgcptDltHstry` | 계약현황에 대한 외자삭제이력조회 |
| `getCntrctInfoListFrgcptPPSSrch` | 나라장터검색조건에 의한 계약현황 외자조회 |
| `getCntrctInfoListGnrlServcServcInfo` | 계약현황에 대한 일반용역서비스정보조회 |
| `getCntrctInfoListServc` | 계약현황에 대한 용역조회 |
| `getCntrctInfoListServcChgHstry` | 계약현황에 대한 용역변경이력조회 |
| `getCntrctInfoListServcDltHstry` | 계약현황에 대한 용역삭제이력조회 |
| `getCntrctInfoListServcPPSSrch` | 나라장터검색조건에 의한 계약현황 용역조회 |
| `getCntrctInfoListTechServcServcInfo` | 계약현황 정보에 대한 기술용역서비스정보조회 |
| `getCntrctInfoListThng` | 계약현황에 대한 물품조회 |
| `getCntrctInfoListThngChgHstry` | 계약현황에 대한 물품변경이력조회 |
| `getCntrctInfoListThngDetail` | 계약현황에 대한 물품세부조회 |
| `getCntrctInfoListThngDltHstry` | 계약현황에 대한 물품삭제이력조회 |
| `getCntrctInfoListThngPPSSrch` | 나라장터검색조건에 의한 계약현황 물품조회 |

### 조달청_나라장터 사전규격정보서비스 — `HrcspSsstndrdInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService`
- 포털: https://www.data.go.kr/data/15129437/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_사전규격정보서비스_1.0.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_사전규격정보서비스_1.0.md`
- 명세: `swagger/HrcspSsstndrdInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getInsttAcctoThngListInfoCnstwk` | 사전규격 공사 기관별 목록 조회 |
| `getInsttAcctoThngListInfoFrgcpt` | 사전규격 외자 기관별 목록 조회 |
| `getInsttAcctoThngListInfoServc` | 사전규격 용역 기관별 목록 조회 |
| `getInsttAcctoThngListInfoThng` | 사전규격 물품 기관별 목록 조회 |
| `getPublicPrcureThngInfoCnstwk` | 사전규격 공사 목록 조회 |
| `getPublicPrcureThngInfoCnstwkPPSSrch` | 나라장터 검색조건에 의한 사전규격 공사 목록 조회 |
| `getPublicPrcureThngInfoFrgcpt` | 사전규격 외자 목록 조회 |
| `getPublicPrcureThngInfoFrgcptPPSSrch` | 나라장터 검색조건에 의한 사전규격 외자 목록 조회 |
| `getPublicPrcureThngInfoServc` | 사전규격 용역 목록 조회 |
| `getPublicPrcureThngInfoServcPPSSrch` | 나라장터 검색조건에 의한 사전규격 용역 목록 조회 |
| `getPublicPrcureThngInfoThng` | 사전규격 물품 목록 조회 |
| `getPublicPrcureThngInfoThngPPSSrch` | 나라장터 검색조건에 의한 사전규격 물품 목록 조회 |
| `getPublicPrcureThngOpinionInfoCnstwk` | 나라장터 사전규격 공사 규격서 의견 목록 조회 |
| `getPublicPrcureThngOpinionInfoFrgcpt` | 나라장터 사전규격 외자 규격서 의견 목록 조회 |
| `getPublicPrcureThngOpinionInfoServc` | 나라장터 사전규격 용역 규격서 의견 목록 조회 |
| `getPublicPrcureThngOpinionInfoThng` | 나라장터 사전규격 물품 규격서 의견 목록 조회 |
| `getThngDetailMetaInfoCnstwk` | 사전규격 공사 품목별 목록 조회 |
| `getThngDetailMetaInfoFrgcpt` | 사전규격 외자 품목별 목록 조회 |
| `getThngDetailMetaInfoServc` | 사전규격 용역 품목별 목록 조회 |
| `getThngDetailMetaInfoThng` | 사전규격 물품 품목별 목록 조회 |

### 조달청_나라장터 조달요청서비스 — `PrcrmntReqInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService`
- 포털: https://www.data.go.kr/data/15129468/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_조달요청서비스_1.2.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_조달요청서비스_1.2.md`
- 명세: `swagger/PrcrmntReqInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getPrcrmntReqInfoListCnstwk` | 조달요청에 대한 공사조회 |
| `getPrcrmntReqInfoListCnstwkPPSSrch` | 나라장터검색조건에 의한 조달요청 공사조회 |
| `getPrcrmntReqInfoListFrgcpt` | 조달요청에 대한 외자조회 |
| `getPrcrmntReqInfoListFrgcptDetail` | 조달요청에 대한 외자세부조회 |
| `getPrcrmntReqInfoListFrgcptPPSSrch` | 나라장터검색조건에 의한 조달요청 외자조회 |
| `getPrcrmntReqInfoListGnrlServc` | 조달요청에 대한 일반용역조회 |
| `getPrcrmntReqInfoListGnrlServcPPSSrch` | 나라장터검색조건에 의한 조달요청 일반용역조회 |
| `getPrcrmntReqInfoListTechServc` | 조달요청에 대한 기술용역조회 |
| `getPrcrmntReqInfoListTechServcPPSSrch` | 나라장터검색조건에 의한 조달요청 기술용역조회 |
| `getPrcrmntReqInfoListThng` | 조달요청에 대한 물품조회 |
| `getPrcrmntReqInfoListThngDetail` | 조달요청에 대한 물품세부조회 |
| `getPrcrmntReqInfoListThngPPSSrch` | 나라장터검색조건에 의한 조달요청 물품조회 |

### 조달청_나라장터 발주계획현황서비스 — `OrderPlanSttusService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/OrderPlanSttusService`
- 포털: https://www.data.go.kr/data/15129462/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_발주계획현황서비스_1.1.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_발주계획현황서비스_1.1.md`
- 명세: `swagger/OrderPlanSttusService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getOrderPlanSttusListCnstwk` | 발주계획현황에 대한 공사조회 |
| `getOrderPlanSttusListCnstwkPPSSrch` | 나라장터 검색조건에 의한 발주계획현황에 대한 공사조회 |
| `getOrderPlanSttusListFrgcpt` | 발주계획현황에 대한 외자조회 |
| `getOrderPlanSttusListFrgcptPPSSrch` | 나라장터 검색조건에 의한 발주계획현황에 대한 외자조회 |
| `getOrderPlanSttusListServc` | 발주계획현황에 대한 용역조회 |
| `getOrderPlanSttusListServcPPSSrch` | 나라장터 검색조건에 의한 발주계획현황에 대한 용역조회 |
| `getOrderPlanSttusListThng` | 발주계획현황에 대한 물품조회 |
| `getOrderPlanSttusListThngPPSSrch` | 나라장터 검색조건에 의한 발주계획현황에 대한 물품조회 |

### 조달청_나라장터 가격정보현황서비스 — `PriceInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/PriceInfoService`
- 포털: https://www.data.go.kr/data/15129415/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_가격정보현황서비스_1.1.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_가격정보현황서비스_1.1.md`
- 명세: `swagger/PriceInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getCnsttyClsfcInfoList` | 공종분류및세부공종 |
| `getNetRsceinfoList` | 자원분류및순수자원 |
| `getPriceInfoListFcltyCmmnMtrilBildng` | 시설공통자재(건축) 가격정보 |
| `getPriceInfoListFcltyCmmnMtrilElctyIrmc` | 시설공통자재(전기, 정보통신) 가격정보 |
| `getPriceInfoListFcltyCmmnMtrilEngrk` | 시설공통자재(토목) 가격정보 |
| `getPriceInfoListFcltyCmmnMtrilMchnEqp` | 시설공통자재(기계설비) 가격정보 |
| `getPriceInfoListFcltyCmmnMtrilTotal` | 시설공통자재(종합) 가격정보 |
| `getPriceInfoListMrktCnstrctPcBildng` | 시장시공가격(건축) 가격정보 |
| `getPriceInfoListMrktCnstrctPcEngrk` | 시장시공가격(토목) 가격정보 |
| `getPriceInfoListMrktCnstrctPcMchnEqp` | 시장시공가격(기계설비) 가격정보 |
| `getStdMarkUprcinfoList` | 표준시장단가및시장시공가격 정보 |

### 조달청_나라장터 계약과정통합공개서비스 — `CntrctProcssIntgOpenService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/CntrctProcssIntgOpenService`
- 포털: https://www.data.go.kr/data/15129459/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_계약과정통합공개서비스_1.0.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_계약과정통합공개서비스_1.0.md`
- 명세: `swagger/CntrctProcssIntgOpenService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getCntrctProcssIntgOpenCnstwk` | 계약과정통합공개정보에 대한 공사조회 |
| `getCntrctProcssIntgOpenFrgcpt` | 계약과정통합공개정보에 대한 외자조회 |
| `getCntrctProcssIntgOpenServc` | 계약과정통합공개정보에 대한 용역조회 |
| `getCntrctProcssIntgOpenThng` | 계약과정통합공개정보에 대한 물품조회 |

### 조달청_나라장터 사용자정보 서비스 — `UsrInfoService02`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/UsrInfoService02`
- 포털: https://www.data.go.kr/data/15129466/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_사용자정보서비스_1.1.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_사용자정보서비스_1.1.md`
- 명세: `swagger/UsrInfoService02.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getDminsttInfo02` 🆕 | 수요기관정보조회 |
| `getPrcrmntCorpBasicInfo02` 🆕 | 조달업체 기본정보 조회 |
| `getPrcrmntCorpIndstrytyInfo02` 🆕 | 조달업체업종정보조회 |
| `getPrcrmntCorpSplyPrdctInfo02` 🆕 | 조달업체공급물품정보조회 |
| `getUnptRsttCorpInfo02` 🆕 | 부정당제재업체정보조회 |

### 조달청_나라장터 업종 및 근거법규서비스 — `IndstrytyBaseLawrgltInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/IndstrytyBaseLawrgltInfoService`
- 포털: https://www.data.go.kr/data/15129467/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_업종및근거법규서비스_1.1.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_업종및근거법규서비스_1.1.md`
- 명세: `swagger/IndstrytyBaseLawrgltInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getIndstrytyBaseLawrgltInfoList` 🆕 | 업종 및 근거법규 정보 조회 |

### 조달청_나라장터쇼핑몰 품목정보 서비스 — `ShoppingMallPrdctInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/at/ShoppingMallPrdctInfoService`
- 포털: https://www.data.go.kr/data/15129471/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_조달청_나라장터쇼핑몰품목정보서비스_1.3.docx` / `guides-md/조달청_OpenAPI참고자료_조달청_나라장터쇼핑몰품목정보서비스_1.3.md`
- 명세: `swagger/ShoppingMallPrdctInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getDlvrReqDtlInfoList` | 나라장터쇼핑몰 납품요구상세 현황 목록조회 |
| `getDlvrReqInfoList` | 나라장터쇼핑몰 납품요구정보 현황 목록조회 |
| `getMASCntrctPrdctInfoList` | 다수공급자계약 품목정보 |
| `getShoppingMallPrdctInfoList` | 나라장터쇼핑몰 품목 등록 내역 조회 |
| `getSpcifyPrdlstPrcureInfoList` | 특정품목조달내역 목록 조회 |
| `getSpcifyPrdlstPrcureTotList` | 특정품목조달집계 목록 조회 |
| `getThptyUcntrctPrdctInfoList` | 제3자단가계약 품목정보 |
| `getUcntrctPrdctInfoList` | 일반단가계약 품목정보 |
| `getVntrPrdctOrderDealDtlsInfoList` | 벤처나라 물품 주문거래 내역 조회 |

### 조달청_물품목록정보서비스 — `ThngListInfoService02`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/ThngListInfoService02`
- 포털: https://www.data.go.kr/data/15129417/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_물품목록정보서비스_1.2.docx` / `guides-md/조달청_OpenAPI참고자료_물품목록정보서비스_1.2.md`
- 명세: `swagger/ThngListInfoService02.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getLsfgdNdPrdlstChghstlnfoSttus02` 🆕 | 목록정보(일반검색) 품목변경이력 조회 |
| `getPrdctClsfcNoChgHstry02` 🆕 | 물품분류변경 이력조회 |
| `getPrdctClsfcNoUnit10Info02` 🆕 | 물품분류10단위 내역조회 |
| `getPrdctClsfcNoUnit2Info02` 🆕 | 물품분류2단위 내역조회 |
| `getPrdctClsfcNoUnit4Info02` 🆕 | 물품분류4단위 내역조회 |
| `getPrdctClsfcNoUnit6Info02` 🆕 | 물품분류6단위 내역조회 |
| `getPrdctClsfcNoUnit8Info02` 🆕 | 물품분류8단위 내역조회 |
| `getPrdctIndvAtrbInfoList02` 🆕 | 품목개별속성정보 조회 |
| `getThngGuidanceMapInfo02` 🆕 | 목록정보(일반검색) 물품안내지도 조회 |
| `getThngListClChangeHistInfo02` 🆕 | 목록정보(일반검색) 분류변경이력 조회 |
| `getThngPrdnmLocplcAccotListInfoInfoLocplcSearch02` 🆕 | 목록정보(일반검색) 소재지 목록 조회 |
| `getThngPrdnmLocplcAccotListInfoInfoPrdlstSearch02` 🆕 | 목록정보(일반검색) 품목 목록 조회 |
| `getThngPrdnmLocplcAccotListInfoInfoPrdnmSearch02` 🆕 | 목록정보(일반검색) 품명 목록 조회 |

### 조달청_물품관리정보서비스 — `PrdctMngInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/PrdctMngInfoService`
- 포털: https://www.data.go.kr/data/15129470/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_조달청_물품관리정보서비스_1.0.docx` / `guides-md/조달청_OpenAPI참고자료_조달청_물품관리정보서비스_1.0.md`
- 명세: `swagger/PrdctMngInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getPrdctClsfcNoUslfsvc` | 물품분류번호별 내용연수조회 |

### 조달청_공공조달통계정보서비스 — `PubPrcrmntStatInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService`
- 포털: https://www.data.go.kr/data/15129412/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_공공조달통계정보서비스_1.0.docx` / `guides-md/조달청_OpenAPI참고자료_공공조달통계정보서비스_1.0.md`
- 명세: `swagger/PubPrcrmntStatInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getCntrctMthdAccotSttus` | 계약방법별 현황 |
| `getDminsttAccotBsnsObjAccotArslt` | 수요기관별 업무대상별 실적 |
| `getDminsttAccotCntrctMthdAccotArslt` | 수요기관별 계약방법별 실적 |
| `getDminsttAccotEntrprsDivAccotArslt` | 수요기관별 기업구분별 실적 |
| `getDminsttAccotSystmTyAccotArslt` | 수요기관별 시스템유형별 실적 |
| `getEntrprsDivAccotPrcrmntSttus` | 기업구분별 조달 현황 |
| `getInsttDivAccotPrcrmntSttus` | 기관구분별 조달 현황 |
| `getPrcrmntEntrprsAccotBsnsObjAccotArslt` | 조달기업별 업무대상별 실적 |
| `getPrcrmntEntrprsAccotCntrctMthdAccotArslt` | 조달기업별 계약방법별 실적 |
| `getPrcrmntObjectBsnsObjAccotSttus` | 조달목적물(업무대상)별 현황 |
| `getPrdctIdntNoServcAccotArslt` | 품목 및 서비스별 실적 |
| `getRgnDutyCmmnCntrctSttus` | 지역의무공동계약 현황 |
| `getRgnLmtSttus` | 지역제한 현황 |
| `getTotlPubPrcrmntSttus` | 전체 공공조달 현황 |

### 조달청_나라장터 공공데이터개방표준서비스 — `PubDataOpnStdService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/PubDataOpnStdService`
- 포털: https://www.data.go.kr/data/15058815/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_나라장터_공공데이터개방표준서비스_1.2.docx` / `guides-md/조달청_OpenAPI참고자료_나라장터_공공데이터개방표준서비스_1.2.md`
- 명세: `swagger/PubDataOpnStdService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getDataSetOpnStdBidPblancInfo` | 데이터셋 개방표준에 따른 입찰공고정보 |
| `getDataSetOpnStdCntrctInfo` | 데이터셋 개방표준에 따른 계약정보 |
| `getDataSetOpnStdScsbidInfo` | 데이터셋 개방표준에 따른 낙찰정보 |

### 조달청_누리장터 민간입찰공고서비스 — `PrvtBidNtceService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/PrvtBidNtceService`
- 포털: https://www.data.go.kr/data/15129456/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_누리장터_민간입찰공고서비스_1.0.docx` / `guides-md/조달청_OpenAPI참고자료_누리장터_민간입찰공고서비스_1.0.md`
- 명세: `swagger/PrvtBidNtceService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getPrvtBidPblancListInfoCnstwk` | 민간입찰공고정보에 대한 공사조회 |
| `getPrvtBidPblancListInfoCnstwkPPSSrch` | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 공사조회 |
| `getPrvtBidPblancListInfoEtc` | 민간입찰공고정보에 대한 기타조회 |
| `getPrvtBidPblancListInfoEtcPPSSrch` | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 기타조회 |
| `getPrvtBidPblancListInfoLicenseLimit` | 민간입찰공고정보에 대한 면허제한정보조회 |
| `getPrvtBidPblancListInfoPrtcptPsblRgn` | 민간입찰공고정보에 대한 참가가능지역정보조회 |
| `getPrvtBidPblancListInfoServc` | 민간입찰공고정보에 대한 용역조회 |
| `getPrvtBidPblancListInfoServcPPSSrch` | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 용역조회 |
| `getPrvtBidPblancListInfoThng` | 민간입찰공고정보에 대한 물품조회 |
| `getPrvtBidPblancListInfoThngPPSSrch` | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 물품조회 |

### 조달청_누리장터 민간낙찰정보_서비스 — `PrvtScsbidInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/PrvtScsbidInfoService`
- 포털: https://www.data.go.kr/data/15129458/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_누리장터_민간낙찰정보서비스_1.0.docx` / `guides-md/조달청_OpenAPI참고자료_누리장터_민간낙찰정보서비스_1.0.md`
- 명세: `swagger/PrvtScsbidInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getPrvtOpengResultListInfo` | 민간 개찰결과 목록 조회 |
| `getPrvtOpengResultListInfoFailing` | 민간 개찰결과 유찰 목록 조회 |
| `getPrvtOpengResultListInfoOpengCompt` | 민간 개찰결과 개찰완료 목록 조회 |
| `getPrvtOpengResultListInfoPPSSrch` | 나라장터 검색조건에 의한 민간 개찰결과 목록 조회 |
| `getPrvtOpengResultListInfoRebid` | 민간 개찰결과 재입찰 목록 조회 |
| `getPrvtScsbidListSttus` | 민간 낙찰된 목록 현황 조회 |
| `getPrvtScsbidListSttusPPSSrch` | 나라장터 검색조건에 의한 민간 낙찰된 목록 현황 조회 |

### 조달청_누리장터 민간계약정보 서비스 — `PrvtCntrctInfoService`

- 엔드포인트: `https://apis.data.go.kr/1230000/ao/PrvtCntrctInfoService`
- 포털: https://www.data.go.kr/data/15129469/openapi.do
- 참고문서: `guides/조달청_OpenAPI참고자료_누리장터_민간계약정보서비스_1.0.docx` / `guides-md/조달청_OpenAPI참고자료_누리장터_민간계약정보서비스_1.0.md`
- 명세: `swagger/PrvtCntrctInfoService.swagger.json`

| 오퍼레이션 | 설명 |
|---|---|
| `getPrvtCntrctInfoList` | 계약현황 민간조회 |
| `getPrvtCntrctInfoListChgHstry` | 계약현황에 대한 민간변경이력조회 |
| `getPrvtCntrctInfoListDltHstry` | 계약현황에 대한 민간삭제이력조회 |
| `getPrvtCntrctInfoListPPSSrch` | 나라장터 검색조건에 의한 계약현황 민간조회 |

## 4. 현재 리포 카탈로그(`g2b-operations.json`)와의 차이

`g2bmaster-backend/src/main/resources/g2b-operations.json` 기준. 🆕 표시가 공식 명세에만 있는 오퍼레이션.

### 공식 명세에만 있음 (리포 미반영)

- **UsrInfoService02** — `getDminsttInfo02`, `getPrcrmntCorpBasicInfo02`, `getPrcrmntCorpIndstrytyInfo02`, `getPrcrmntCorpSplyPrdctInfo02`, `getUnptRsttCorpInfo02`
- **IndstrytyBaseLawrgltInfoService** — `getIndstrytyBaseLawrgltInfoList`
- **ThngListInfoService02** — `getLsfgdNdPrdlstChghstlnfoSttus02`, `getPrdctClsfcNoChgHstry02`, `getPrdctClsfcNoUnit10Info02`, `getPrdctClsfcNoUnit2Info02`, `getPrdctClsfcNoUnit4Info02`, `getPrdctClsfcNoUnit6Info02`, `getPrdctClsfcNoUnit8Info02`, `getPrdctIndvAtrbInfoList02`, `getThngGuidanceMapInfo02`, `getThngListClChangeHistInfo02`, `getThngPrdnmLocplcAccotListInfoInfoLocplcSearch02`, `getThngPrdnmLocplcAccotListInfoInfoPrdlstSearch02`, `getThngPrdnmLocplcAccotListInfoInfoPrdnmSearch02`

### 리포에만 있음 — 게이트웨이 실호출로 생사 확인

유효 서비스키 없이도 판별 가능하다. 존재하는 엔드포인트는 코드 `30`(`SERVICE_KEY_IS_NOT_REGISTERED_ERROR`, HTTP 403), 없는 엔드포인트는 코드 `12`(`NO_OPENAPI_SERVICE_ERROR`, HTTP 400)를 돌려준다.

| 구버전 오퍼레이션 | 경로 | 실호출 | 판정 |
|---|---|---|---|
| `getIndstrytyBaseLawrgltInfoList02` | `/ao/IndstrytyBaseLawrgltInfoService` | `12` | **폐기됨 — 호출 실패** |
| `getDminsttInfo` | `/ao/UsrInfoService` | `12` | **폐기됨 — 호출 실패** |
| `getPrcrmntCorpBasicInfo` | `/ao/UsrInfoService` | `12` | **폐기됨 — 호출 실패** |
| `getPrcrmntCorpIndstrytyInfo` | `/ao/UsrInfoService` | `12` | **폐기됨 — 호출 실패** |
| `getPrcrmntCorpSplyPrdctInfo` | `/ao/UsrInfoService` | `12` | **폐기됨 — 호출 실패** |
| `getLsfgdNdPrdlstChghstlnfoSttus` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getPrdctClsfcNoChgHstry` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getPrdctClsfcNoUnit10Info` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getPrdctClsfcNoUnit2Info` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getPrdctClsfcNoUnit4Info` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getPrdctClsfcNoUnit6Info` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getPrdctClsfcNoUnit8Info` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getThngGuidanceMapInfo` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getThngListClChangeHistInfo` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getThngPrdnmLocplcAccotListInfoInfoLocplcSearch` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getThngPrdnmLocplcAccotListInfoInfoPrdlstSearch` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |
| `getThngPrdnmLocplcAccotListInfoInfoPrdnmSearch` | `/ao/ThngListInfoService` | `30` | 아직 동작(문서 미기재) |

### 서비스 경로 변경

Swagger `host` 필드와 참고문서(docx) 본문 서비스 URL 양쪽에서 동일하게 확인됨.

| 서비스 | 리포 경로 | 공식 명세 경로 | 리포 경로 실호출 |
|---|---|---|---|
| UsrInfoService → UsrInfoService02 | `/ao/UsrInfoService` | `/ao/UsrInfoService02` | `12` **전부 폐기** |
| ShoppingMallPrdctInfoService05 → ShoppingMallPrdctInfoService | `/at/ShoppingMallPrdctInfoService05` | `/at/ShoppingMallPrdctInfoService` | `12` **전부 폐기** |
| ThngListInfoService → ThngListInfoService02 | `/ao/ThngListInfoService` | `/ao/ThngListInfoService02` | `30` 아직 동작 |

### 조치 요약

- 지금 리포 설정대로 호출하면 **깨지는 오퍼레이션 14개**: `UsrInfoService` 4개, `getIndstrytyBaseLawrgltInfoList02` 1개, `ShoppingMallPrdctInfoService05` 9개.
- `ThngListInfoService` 12개는 구경로가 **아직 응답**하지만 공식 문서에서 빠졌으므로 `ThngListInfoService02` + `*02` 오퍼레이션으로 옮기는 편이 안전하다.

## 5. 검증 결과

이 묶음은 아래 5개 항목을 모두 통과했다. 재현: `tools/verify_endpoints.py`, `tools/verify_bundle.py`

| 검증 | 방법 | 결과 |
|---|---|---|
| 오퍼레이션 실존 | 문서 기재 191개 전수를 게이트웨이에 실호출 | 191/191 존재 확인(코드 30) |
| docx 무결성 | zip CRC + `word/document.xml` 존재 | 18/18 정상 |
| Swagger 유효성 | `swagger:2.0`·`host`·오퍼레이션별 200 응답 스키마 | 18/18 정상 |
| 교차출처 일치 | Swagger `paths` vs 참고문서 docx 본문 오퍼레이션 | 18종 191개 전부 일치 |
| 변환 충실도 | 요청/응답 메시지 명세·에러코드 표 존재, 표 개수 ≥ 오퍼레이션×2 | 18/18 정상 |

`verification.json`에 오퍼레이션별 실호출 응답 코드가 그대로 남아 있다.

