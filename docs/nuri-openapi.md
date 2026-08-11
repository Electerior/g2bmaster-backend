# 누리장터(민간 전자조달) OpenAPI 3종 심층 분석

정리일 2026-08-11. 서비스 카탈로그·오퍼레이션 목록·원본 명세는 [g2b-openapi/INDEX.md](g2b-openapi/INDEX.md)의
서비스 16~18번(누리장터 3종)에 이미 수집되어 있다 — 이 문서는 그 위에 얹는 심층 분석으로,
**나라장터와의 필드 수준 차이(§3)와 수집 시 주의점(§4)** 이 핵심이다.
DB 반영 방안은 [multi-source-schema.md](multi-source-schema.md) 참조.

분석 대상: `PrvtBidNtceService`(민간입찰공고) / `PrvtScsbidInfoService`(민간낙찰정보) / `PrvtCntrctInfoService`(민간계약정보), 각 1.0.
근거 파일: guides-md의 누리장터 3종 md + swagger의 `PrvtBidNtceService.swagger.json`, `PrvtScsbidInfoService.swagger.json`, `PrvtCntrctInfoService.swagger.json`. 비교 기준: 나라장터 입찰공고정보서비스_1.2 / 낙찰정보서비스_1.1 / 계약정보서비스_1.0 md 및 `BidPublicInfoService` / `ScsbidInfoService` / `CntrctInfoService` swagger. 3절의 필드 diff는 swagger 응답 스키마를 실제 비교한 결과다.

---

## 1. 누리장터란 무엇인가

- **민간수요자 대상 전자조달**: 문서상 누리장터는 "민간입찰공고정보", "민간낙찰정보", "민간계약정보"를 제공하는 시스템이다. 공고기관명(`ntceInsttNm`)은 "**민간발주자기관명**", 계약기관코드(`cntrctInsttCd`)는 "**민간수요자코드**", 계약기관명은 "민간수요자명"으로 정의된다 (민간입찰공고서비스 md, 민간계약정보서비스 md).
- **모집단이 나라장터와 다름**: 나라장터 낙찰·계약 문서의 수요기관은 "조달사업에 관한 법률 제2조에 따라 조달청장에게 계약 체결을 요청할 수 있는 등록기관"(공공)인 반면, 누리장터 샘플 데이터의 주체는 아파트 입주자대표회의(산호한양아파트, 청주푸르지오 캐슬아파트), 재개발조합(연지2구역주택재개발정비사업조합), 영농조합법인, 민간회사(더케이예다함(주), 청라에너지주식회사, 전남관광주식회사) 등 민간이다. 기관코드도 행자부코드 대신 조달청 부여 `M######` 형식(예: `M008472`, `M005836`)이 주로 나타난다.
- **공고분류 자체가 민간 전용**: `bidNtceClsfc`는 "민간물품, 민간공사, 민간기술용역, 민간일반용역, 민간기타"로 구분된다.
- **민간 특화 속성**: 응답에 아파트 정보 블록(`aptHsmpNm` 단지명, 세대수, 동수, 난방방식, 관리비부과면적 등 10개 필드)과 **역경매**(`dtchac*` 9개 필드, 입찰방식명에 "역경매" 존재), **최고가 낙찰**(낙찰방법명에 최고가·적격심사최고가 — 매각공고 존재, 샘플 "근생용지B 매각 입찰공고")이 포함된다. 공공 나라장터에는 없는 구성이다.
- **예정가격 대신 기준금액**: 누리장터 공고는 예가(예정가격) 체계가 아니라 `refAmt`(기준금액, "투찰의 상한금액") + `refAmtUseYn`/`refAmtOpenYn` 체계를 쓴다.
- 서비스 3종 모두 서비스 버전 1.0, 시작일 2025-01-06, 데이터 갱신주기 "수시", REST(GET), XML/JSON 지원. 단 데이터 자체는 2013~2016년 샘플이 존재하므로 과거분까지 적재된 것으로 보인다(추정: 구 누리장터 데이터 승계).

---

## 2. 서비스 3종 상세

### 2.1 PrvtBidNtceService — 누리장터 민간입찰공고서비스

- 엔드포인트: `http://apis.data.go.kr/1230000/ao/PrvtBidNtceService` (swagger 내 원천 URL은 `https://nopenapi.g2b.go.kr/ao/PrvtBidNtceService`)
- 오퍼레이션 10종:

| # | 오퍼레이션 | 설명 |
|---|---|---|
| 1 | getPrvtBidPblancListInfoServc | 용역 공고 목록 |
| 2 | getPrvtBidPblancListInfoThng | 물품 공고 목록 |
| 3 | getPrvtBidPblancListInfoCnstwk | 공사 공고 목록 |
| 4 | getPrvtBidPblancListInfoEtc | 기타 공고 목록 |
| 5 | getPrvtBidPblancListInfoLicenseLimit | 면허제한정보 |
| 6 | getPrvtBidPblancListInfoPrtcptPsblRgn | 참가가능지역정보 |
| 7~10 | getPrvtBidPblancListInfo{Servc,Thng,Cnstwk,Etc}PPSSrch | 나라장터 검색조건에 의한 조회 |

**대표 목록 오퍼레이션(용역/물품/공사/기타 공통) 요청 파라미터**

| 파라미터 | 한글 | 필수 | 설명 |
|---|---|---|---|
| ServiceKey | 서비스키 | 필수 | 공공데이터포털 인증키 |
| numOfRows / pageNo | 페이지 | 필수 | 페이지네이션 |
| type | 타입 | 옵션 | `json` 지정 시 JSON |
| inqryDiv | 조회구분 | 필수 | 1:등록일시, 2:공고게시일시, 3:개찰일시, 4:입찰공고번호 |
| inqryBgnDt / inqryEndDt | 조회시작/종료일시 | 조건부 | `YYYYMMDDHHMM`, inqryDiv 1·2·3 시 필수 |
| bidNtceNo | 입찰공고번호 | 조건부 | inqryDiv 4 시 필수 (예: `R25BK00597093`) |

**응답 필드 (swagger 기준 4개 목록 op 동일, 업무별 상세목록 필드만 상이 — 총 71필드+공통헤더)**

핵심 필드:

| 필드 | 한글 설명 |
|---|---|
| bidNtceNo / bidNtceOrd | 입찰공고번호 / 입찰공고차수(3자리, `000`) |
| bidNtceClsfc | 입찰공고분류 (민간물품/민간공사/민간기술용역/민간일반용역/민간기타) |
| ntceNm | 공고명 |
| ntceDivNm | 공고구분명 [등록공고, 재공고, 긴급공고, 변경공고, 취소공고] |
| ntceInsttNm | 공고기관명(민간발주자기관명) |
| refNo | 참조번호(사용자 문서번호) |
| nticeDt / rgstDt | 게시일시 / 등록일시 (`YYYY-MM-DD HH:MM:SS`) |
| bidMethdNm | 입찰방식명 [수기입찰, 전자입찰, 역경매] |
| cntrctMthdNm | 계약방법명 [일반경쟁, 제한경쟁, 지명경쟁] |
| sucsfbidMthdNm | 낙찰방법명 [최저가, 최고가, 적격심사최저가, 적격심사최고가] |
| bidBeginDt / bidClseDt / opengDt | 입찰개시 / 입찰마감 / 개찰일시 |
| refAmtUseYn / refAmtOpenYn / refAmt | 기준금액 사용·공개 여부 / 기준금액(투찰 상한금액, 원) |
| asignBdgtAmt | 배정예산금액(원) |
| ntceSpecDocUrl1~10 / ntceSpecDocNm1~10 | 공고규격서 파일 URL / 파일명 |
| servcDtlList · prdctDtlList · cnstwkDtlList | 업무별 상세목록(`^` 구분 복합문자열; 용역=[순번^용역명^현장명^완수기한], 물품=[순번^품명^수량^납품기한^납품장소^인도조건], 공사=[순번^공사명^현장명^준공기한]; 기타 op에는 상세목록 없음) |

나머지 필드(축약): `rbidDivNm`(재입찰허용여부) · `bidQlfctNm`(입찰자격명, "공고서 참조" 고정) · `ofclNm`/`ofclTelNo`/`ofclEmail`(담당자) · `opengPlce`(개찰장소) · `bidWgrnteeRcptClseDt`(입찰보증서접수마감) · `sptDscrptDt`/`sptDscrptPlce`(현장설명) · `vatInclsnYnNm`(부가세포함여부명) · `refAmtDscrpt`(기준금액설명, 고정문구) · `rgnLmtDivNm`(지역제한구분명) · 역경매 `dtchacOpenDt`/`dtchacBgnPrce`/`dtchacRmrk`/`dtchacMinRdctnRt`/`dtchacBidprcLmtNum`/`dtchacAutoEtTm`/`dtchacBeforeBidDocClseDt`/`dtchacEtBidDocClseDt`/`bssAmtDtlScrnUrl`(차세대 이후 제공 불가 명시) · 아파트 `aptHsmpNm`/`aptCeoAdrs`/`aptMngOfficeTelNo`/`aptHmpgUrl`/`aptTotar`/`aptMngcstLevyArea`/`aptHshldNum`/`aptCmplNum`/`aptHeatMethdNm`/`aptSubactLrgeNdCmpnintFclty`.

**부속 오퍼레이션**
- 면허제한(LicenseLimit): inqryDiv 1:등록일시 / 2:입찰공고번호(+`bidNtceOrd` 필수). 응답: `lmtGrpNo`(제한그룹번호), `lmtSno`(제한순번), `lcnsLmtNm`(면허제한명, "건물위생관리업/1162" 형식), `permsnIndstrytyList`(허용업종목록 `[업종명/코드]`), `rgstDt`.
- 참가가능지역(PrtcptPsblRgn): 동일 요청 구조. 응답: `lmtSno`, `prtcptPsblRgnNm`(참가가능지역명), `rgstDt`.
- PPSSrch 4종: **inqryDiv가 1:공고게시일시, 2:개찰일시로 재정의**되고, 추가 검색 파라미터 제공 — `bidNtceNm`, `ntceInsttCd`/`ntceInsttNm`, `dminsttCd`/`dminsttNm`, `refNo`, `prtcptLmtRgnCd`(지역코드표: 11 서울 … 12 전남광주통합특별시, 99 기타)/`prtcptLmtRgnNm`, `indstrytyCd`/`indstrytyNm`, `presmptPrceBgn`/`presmptPrceEnd`, `dtilPrdctClsfcNo`, `masYn`, `prcrmntReqNo`, `bidClseExcpYn`, `intrntnlDivCd`. 응답 필드는 기본 목록 op와 동일(swagger 확인).

### 2.2 PrvtScsbidInfoService — 누리장터 민간낙찰정보서비스

- 엔드포인트: `http://apis.data.go.kr/1230000/ao/PrvtScsbidInfoService`
- 오퍼레이션 7종: `getPrvtScsbidListSttus`(낙찰현황), `getPrvtOpengResultListInfo`(개찰결과), 각각의 `...PPSSrch`, 그리고 `getPrvtOpengResultListInfoOpengCompt`(개찰완료 상세)/`...Failing`(유찰)/`...Rebid`(재입찰).
- 나라장터와 달리 업무별(물품/공사/용역/외자) 분리가 아니라 **단일 오퍼레이션 + `bsnsDivCd` 파라미터**(1물품/2공사/3용역/4기타)로 구분.

**민간 개찰결과 목록 조회(getPrvtOpengResultListInfo) 요청**

| 파라미터 | 필수 | 설명 |
|---|---|---|
| ServiceKey, numOfRows, pageNo, type | 필수/옵션 | 공통 |
| inqryDiv | 필수 | 1:입력(등록)일시, 2:공고일시, 3:개찰일시, 4:입찰공고번호 |
| inqryBgnDt / inqryEndDt | 조건부 | `YYYYMMDDHHMM`, inqryDiv 1·2·3 시 필수 |
| bidNtceNo | 조건부 | inqryDiv 4 시 필수 |

**개찰결과 응답 필드(전체)**

| 필드 | 한글 설명 |
|---|---|
| bidNtceNo / bidNtceOrd | 입찰공고번호(구 11자리·차세대 13자리) / 공고차수 |
| bidClsfcNo | 입찰분류번호(동일 공고번호 내 집행일련번호) |
| rbidNo | 재입찰번호 |
| bidNtceNm | 입찰공고명 |
| dminsttCd / dminsttNm | 수요기관코드 / 수요기관명 |
| opengDt | 개찰일시 |
| prtcptCnum | 참가업체수 |
| progrsDivCdNm | 진행구분코드명 [유찰, 개찰완료, 재입찰] |
| opengCorpInfo | 개찰업체정보 복합문자열 `업체명^사업자번호^대표자^투찰금액^투찰율` (다수 낙찰자는 "낙찰예정자 다수"+1위 정보, 협상계약은 금액·율 미제공) |

**낙찰현황(getPrvtScsbidListSttus) 응답(전체 15필드)**: `bsnsDivNm`(업무구분명) · `bidNtceNo`/`bidNtceOrd`/`rbidNo`/`bidNtceNm` · `dminsttCd`/`dminsttNm` · `rlOpengDt`(실개찰일시) · `prtcptCnum`(참가업체수) · `sucsfbidAmt`(최종낙찰금액) · `sucsfbidRate`(최종낙찰률 = 최종낙찰금액/예정가격×100) · `bidwinnrNm`/`bidwinnrCeoNm`/`bidwinnrAdrs`/`bidwinnrTelNo`(최종낙찰업체 명·대표자·주소·전화).

**상세 3종(공고번호 기반, 기간조회 불가)**: 요청은 `bidNtceNo`(필수) + `bidNtceOrd`/`rbidNo`(옵션).
- OpengCompt(개찰완료): `opengRsltDivNm`, `opengRank`(개찰순위), `prcbdrBizno`/`prcbdrNm`/`prcbdrCeoNm`(투찰업체 사업자번호·명·대표자), `bidprcAmt`(투찰금액), `bidprcrt`(투찰률), `rmrk`(비고, 예 "낙찰하한선 미달"), `bidprcDt`(투찰일시).
- Failing(유찰): `opengRsltDivNm`, `bidNtceNo`/`Ord`, `rbidNo`, `nobidRsn`(유찰사유).
- Rebid(재입찰): 위 + `opengDt`, `rbidRsn`(재입찰사유).
- 주의: swagger의 Failing/Rebid 응답 스키마에는 `bidprc*`·`prcbdr*` 필드까지 들어 있으나 guide 명세·응답 예제에는 없음 — 3개 op가 스키마를 공유한 것으로 추정. 실효 필드는 guide 기준으로 보는 것이 안전.
- 부록: md 말미에 공종코드 목록 약 2,700행(0001 토목공사업 등)이 수록되어 있음.
- PPSSrch 2종은 inqryDiv가 **1:공고게시일시, 2:개찰일시, 3:입찰공고번호**로 재정의되고 공고 PPSSrch와 같은 확장 검색 파라미터(`ntceInsttCd`, `prtcptLmtRgnCd`, `indstrytyCd`, `presmptPrceBgn/End` 등)를 받는다. 응답은 기본 op와 동일.

### 2.3 PrvtCntrctInfoService — 누리장터 민간계약정보서비스

- 엔드포인트: `http://apis.data.go.kr/1230000/ao/PrvtCntrctInfoService`
- 오퍼레이션 4종: `getPrvtCntrctInfoList`(계약현황), `getPrvtCntrctInfoListPPSSrch`, `getPrvtCntrctInfoListChgHstry`(변경이력), `getPrvtCntrctInfoListDltHstry`(삭제이력). 업무구분(물품/용역/공사/기타)은 오퍼레이션 분리 없이 응답의 `bsnsDivNm`으로 제공.

**계약현황(getPrvtCntrctInfoList) 요청**

| 파라미터 | 필수 | 설명 |
|---|---|---|
| ServiceKey, numOfRows, pageNo, type | 필수/옵션 | 공통 |
| inqryDiv | 필수 | 1:등록일시, 2:통합계약번호 (ChgHstry는 1:변경일시, DltHstry는 1:삭제일시) |
| inqryBgnDt / inqryEndDt | 조건부 | `YYYYMMDDHHMM`, inqryDiv 1 시 필수 |
| untyCntrctNo | 조건부 | 통합계약번호(13자리), inqryDiv 2 시 필수 |

**계약현황 응답 필드(전체 26필드)**

핵심:

| 필드 | 한글 설명 |
|---|---|
| untyCntrctNo | 통합계약번호(13자리) — 계약현황 데이터의 식별자 |
| bsnsDivNm | 업무구분명 [물품, 용역, 공사, 기타] |
| dcsnCntrctNo / cntrctRefNo | 확정계약번호 / 계약참조번호 (차세대 체계 `R24TA00000001` 등) |
| cntrctNm | 계약명 |
| cntrctInsttCd / cntrctInsttNm | 계약기관코드(민간수요자코드, `M######`) / 계약기관명 |
| cntrctCnclsDate / cntrctPrd | 계약체결일자 / 계약기간(포맷 비일관: "2016.05.02 ~ 2016.06.03" 또는 "23 일") |
| totCntrctAmt / thtmCntrctAmt | 총계약금액(장기계속계약만 의미, 그 외 0 가능) / 금차계약금액(원) |
| cntrctCnclsMthdNm | 계약체결방법명(일반/제한/지명경쟁, 수의, 기타) |
| cntrctInfoUrl / cntrctDtlInfoUrl | 계약정보 URL / 계약상세정보 URL(구 g2b 화면) |
| corpList | 업체목록 복합문자열 `[순번^업체구분^공동도급방식^업체명^대표자^국적^지분율^채권자^담당자]` |
| rgstDt / chgDt | 등록일시 / 변경일시 |

나머지(축약): `cmmnCntrctYn`(공동계약여부) · `grntymnyRate`(보증금률%) · `dfctGrntymnyRate`(하자보증금률%) · `payDivNm`(지급구분명, 직불 등) · `cntrctInsttChrgDeptNm`/`cntrctInsttOfclNm`/`cntrctInsttOfclTelNo`/`cntrctInsttOfclFaxNo`(계약담당) · `crdtrNm`(채권자명).

- ChgHstry: 요청·응답 구조 동일(변경일시 기준 조회).
- DltHstry 응답: `dltDt`(삭제일시), `chgDivNm`(변경구분명, `DEL`), `untyCntrctNo`, `dcsnCntrctNo`, `cntrctRefNo` — **삭제분 동기화용**.
- PPSSrch 요청: inqryDiv **1:계약일자, 2:계약번호, 3:공고번호**; 날짜 파라미터가 `inqryBgnDate`/`inqryEndDate`(**8자리 `YYYYMMDD`**)로 이름·형식이 다름. 추가로 `insttCd`/`insttNm`(민간수요자), `cntrctNm`, `cntrctRefNo`, `cntrctNo`, `ntceNo`. 응답은 기본 op와 동일.

---

## 3. 나라장터 대응 오퍼레이션과의 필드 수준 차이 (swagger diff 실측)

### 3.1 입찰공고: getPrvtBidPblancListInfoServc(71필드) vs getBidPblancListInfoServc(108필드)

공통 23필드: `bidNtceNo`, `bidNtceOrd`, `asignBdgtAmt`, `bidBeginDt`, `bidClseDt`, `bidMethdNm`, `bidWgrnteeRcptClseDt`, `ntceInsttNm`, `ntceSpecDocUrl1~10`, `opengDt`, `opengPlce`, `refNo`, `rgstDt`, `sucsfbidMthdNm`.

**(a) 누리장터에만 있는 필드**
- 아파트 정보 10종: `aptHsmpNm`, `aptCeoAdrs`, `aptMngOfficeTelNo`, `aptHmpgUrl`, `aptTotar`, `aptMngcstLevyArea`, `aptHshldNum`, `aptCmplNum`, `aptHeatMethdNm`, `aptSubactLrgeNdCmpnintFclty`
- 역경매 9종 + URL: `dtchacOpenDt`, `dtchacBgnPrce`, `dtchacRmrk`, `dtchacMinRdctnRt`, `dtchacBidprcLmtNum`, `dtchacAutoEtTm`, `dtchacBeforeBidDocClseDt`, `dtchacEtBidDocClseDt`, `bssAmtDtlScrnUrl`
- 기준금액 체계 4종: `refAmtUseYn`, `refAmtOpenYn`, `refAmt`, `refAmtDscrpt`
- 기타: `bidNtceClsfc`(공고분류), `rbidDivNm`(재입찰허용), `bidQlfctNm`, `vatInclsnYnNm`, `rgnLmtDivNm`, `sptDscrptDt`/`sptDscrptPlce`(현장설명), 업무별 `servcDtlList`/`prdctDtlList`/`cnstwkDtlList`

**(b) 나라장터에만 있는 필드** (용역 기준 87개, 요청된 관점 위주)
- **기관코드**: `ntceInsttCd`(공고기관코드), `dminsttCd`/`dminsttNm`(수요기관) — **누리장터 목록 응답에는 기관 "코드"가 전혀 없고 기관명(`ntceInsttNm`)뿐**. 코드는 PPSSrch의 검색 파라미터로만 존재하고, 낙찰서비스 응답(`dminsttCd`)에서야 얻을 수 있다.
- **예가·복수예비가격**: `presmptPrce`(추정가격), `prearngPrceDcsnMthdNm`(예정가격결정방법), `rsrvtnPrceReMkngMthdNm`(예비가격재작성방법), `drwtPrdprcNum`(추첨예비가격수), `totPrdprcNum`(총예비가격수), `sucsfbidLwltRate`(낙찰하한율), `sucsfbidMthdCd` — 누리장터는 전부 없음(기준금액 `refAmt`로 대체). 나라장터의 기초금액 상세(`...BsisAmount`) 오퍼레이션군도 누리장터에는 없다.
- **참가자격**: `bidQlfctRgstDt`(참가자격등록마감일시), `bidPrtcptLmtYn`(참가제한여부), `indstrytyLmtYn`(업종제한여부), `prdctClsfcLmtYn`(물품분류제한여부), `pqApplDocRcptDt`/`pqApplDocRcptMthdNm`/`pqEvalYn`(PQ), `tpEval*`(기술제안평가), `arslt*`(실적), 공동수급 `cmmnSpldmd*` 4종, `jntcontrctDutyRgnNm1~3`, `rgnDutyJntcontrctRt` — 누리장터는 `bidQlfctNm`("공고서 참조" 고정)과 면허제한·참가가능지역 별도 오퍼레이션이 대신함.
- 기타: `untyNtceNo`, `bidNtceUrl`/`bidNtceDtlUrl`/`stdNtceDocUrl`, `chgDt`/`chgNtceRsn`/`reNtceYn`(변경·재공고), `rbidOpengDt`, `intrbidYn`, `VAT`/`indutyVAT`, `pubPrcrmntClsfc*`(공공조달분류), `purchsObjPrdct*`, `exctvNm`, `crdtrNm`, `dcmtgOprtnDt`(설명회) 등. 물품 op에서는 추가로 `dtilPrdctClsfcNo(Nm)`, `prdctQty`/`prdctUprc`/`prdctSpecNm`, `dlvrTmlmtDt`/`dlvryCndtnNm` 등.

**(c) 같은 개념, 다른 이름**

| 개념 | 누리장터 | 나라장터 |
|---|---|---|
| 공고명 | `ntceNm` | `bidNtceNm` |
| 공고 게시일시 | `nticeDt` | `bidNtceDt` |
| 공고 종류(등록/재공고/취소 등) | `ntceDivNm` | `ntceKindNm` |
| 계약(체결)방법명 | `cntrctMthdNm` | `cntrctCnclsMthdNm` |
| 규격서 파일명 | `ntceSpecDocNm1~10` | `ntceSpecFileNm1~10` (URL은 양쪽 다 `ntceSpecDocUrl*`) |
| 공고 담당자 | `ofclNm` / `ofclTelNo` / `ofclEmail` | `ntceInsttOfclNm` / `ntceInsttOfclTelNo` / `ntceInsttOfclEmailAdrs` |
| 투찰 상한 개념 | `refAmt`(기준금액) | `bssAmt`(기초금액, BsisAmount op) — 정의는 다르므로 등치 매핑은 부적절(추정: 유사 용도) |

- 면허제한·참가가능지역 op: 나라장터 쪽에만 `bsnsDivNm`(+면허제한에 `indstrytyMfrcFldList`)이 더 있고 나머지 구조 동일.

### 3.2 낙찰: PrvtScsbidInfoService vs ScsbidInfoService

- **낙찰현황** (`getPrvtScsbidListSttus` 15필드 vs `getScsbidListSttusThng` 20필드): 누리 전용 `bsnsDivNm`(단일 op이므로 업무구분 필드 필요). 나라 전용: `bidClsfcNo`, **`bidwinnrBizno`(낙찰업체 사업자번호 — 누리 낙찰현황에는 없음; 사업자번호는 개찰완료 상세 `prcbdrBizno`로만 획득 가능)**, `fnlSucsfCorpOfcl`, `fnlSucsfDate`(최종낙찰일자), `ntceDivCd`, `rgstDt`.
- **개찰결과** (`getPrvtOpengResultListInfo` 11필드 vs `getOpengResultListInfoThng` 16필드): 누리 전용 `rbidNo`. 나라 전용: `inptDt`, `ntceInsttCd`/`ntceInsttNm`, `opengRsltNtcCntnts`, `rbidNtceNo`, `rsrvtnPrceFileExistnceYn`(예비가격파일존재여부).
- **개찰완료 상세**: 나라 전용 `bidClsfcNo`, `cnsttyAccotBidAmtUrl`(공종별투찰금액URL), `drwtNo1`/`drwtNo2`(**복수예비가격 추첨번호 — 누리 없음**).
- **오퍼레이션 수준 격차**: 나라장터의 `getOpengResultListInfo*PreparPcDetail`(복수예비가격 상세: `bssamt`, `plnprc` 예정가격, `drwtYn`, `totRsrvtnPrceNum`, `compnoRsrvtnPrceSno` 등)에 대응하는 누리장터 오퍼레이션이 아예 없다. 또한 나라장터는 낙찰현황·개찰결과를 물품/공사/용역/외자 4개 op로 분리하지만 누리장터는 단일 op + `bsnsDivCd`이며, 외자(Frgcpt) 구분이 없다.
- 유찰/재입찰 op의 응답 명세는 양쪽 유사(`nobidRsn`/`rbidRsn`), 나라 쪽에 `bidClsfcNo`(재입찰에는 `bidClseDt`, `cmmnSpldmdAgrmntClseDt`)가 추가.

### 3.3 계약: PrvtCntrctInfoService vs CntrctInfoService

- **계약현황** (`getPrvtCntrctInfoList` 26필드 vs `getCntrctInfoListThng` 39필드): 공통 25필드(`untyCntrctNo`, `bsnsDivNm`, `dcsnCntrctNo`, `cntrctRefNo`, 금액·기관·업체목록·URL 등).
  - 누리 전용: `dfctGrntymnyRate`(하자보증금률).
  - 나라 전용: **`ntceNo`(공고번호), `reqNo`(조달요청번호)** — 누리 계약현황 응답에는 공고번호 역참조 필드가 없어 공고↔계약 연결은 PPSSrch의 `ntceNo` 검색으로만 가능. 그 외 `cntrctDate`, `baseLawNm`/`baseDtls`(근거법규), `cntrctInsttJrsdctnDivNm`(소관구분), `dfrcmpnstRt`(지체상금률), `dminsttList`(수요기관목록), `lngtrmCtnuDivNm`(장기계속구분), `pubPrcrmntClsfcNo/Nm/LrgClsfcNm/MidClsfcNm`(공공조달분류), `infoBizYn`.
- **PPSSrch 파라미터**: 나라 전용 `insttDivCd`(계약/수요기관 구분), `insttClsfcCd`(기관분류), `cntrctDivCd`, `cntrctMthdCd`, `prdctClsfcNoNm`, `dcsnCntrctNo`, `reqNo`; 누리 전용 `cntrctNm`, `cntrctNo`(공통: `inqryBgnDate/EndDate`, `insttCd`/`insttNm`, `cntrctRefNo`, `ntceNo`).
- **삭제이력**: 나라 쪽에 `chgDt`, `rgstDt` 추가 외 동일 구조.
- 오퍼레이션 수준: 나라장터는 업무별(Thng/Cnstwk/Servc/Frgcpt) × (현황/상세/변경/삭제/PPSSrch) + ServcInfo 등 21개, 누리장터는 4개로 축소(상세 Detail 오퍼레이션 없음).

### 3.4 공고번호(bidNtceNo) 형식과 ord

- **형식 이원화**: 구 체계 = 11자리 `년도(4)+월(2)+순번(5)`(샘플 `20160430911`), 차세대(나라장터 개편 이후) = 13자리 `테스트여부(1: R실제/T모의)+년도(2)+번호구분(2: BK입찰/TA계약)+순번(8)`(샘플 `R25BK00597093`). 항목크기가 공고서비스는 40, 낙찰서비스 문서는 11로 적혀 있으나 낙찰서비스 설명문에도 13자리 개편이 명시되어 있어 11은 구판 잔재로 추정. **수집 스키마는 두 형식을 모두 수용해야 함.**
- **ord**: 3개 서비스 모두 `bidNtceOrd` 존재(공고 3자리 `000`, 낙찰 문서상 2~3자리 혼재). 낙찰에는 추가로 `bidClsfcNo`(집행일련번호)와 `rbidNo`(재입찰번호)가 있어 개찰 레코드의 자연키는 (bidNtceNo, bidNtceOrd, bidClsfcNo, rbidNo). 계약은 공고번호가 아닌 `untyCntrctNo`(13자리)가 식별자이고 ord 없음.

---

## 4. 수집 시 주의점

**(1) inqryDiv는 오퍼레이션마다 의미가 다르다 — 하드코딩 금지**

| 오퍼레이션군 | inqryDiv 의미 |
|---|---|
| 공고 목록 4종 | 1 등록일시 / 2 공고게시일시 / 3 개찰일시 / 4 입찰공고번호 |
| 공고 면허제한·참가가능지역 | 1 등록일시 / 2 입찰공고번호(+`bidNtceOrd` 필수) |
| 공고 PPSSrch 4종 | **1 공고게시일시 / 2 개찰일시** (같은 숫자가 다른 뜻) |
| 낙찰현황·개찰결과 | 1 등록(입력)일시 / 2 공고일시 / 3 개찰일시 / 4 입찰공고번호 |
| 낙찰 PPSSrch 2종 | 1 공고게시일시 / 2 개찰일시 / 3 입찰공고번호 |
| 계약현황·변경·삭제 | 1 등록/변경/삭제일시 / 2 통합계약번호 |
| 계약 PPSSrch | 1 계약일자 / 2 계약번호 / 3 공고번호 |

증분 수집 기준: 공고는 inqryDiv=1(등록일시), 계약은 현황(등록)+변경이력(변경일시)+삭제이력(삭제일시) 3축을 함께 돌려야 완전 동기화된다. 낙찰 상세 3종(OpengCompt/Failing/Rebid)은 기간조회가 없고 `bidNtceNo` 필수라서 목록 op에서 번호를 얻은 뒤 개별 조회하는 2단계 파이프라인이 필요하다.

**(2) 날짜 파라미터 형식 함정**: 기본은 `inqryBgnDt`/`inqryEndDt` 12자리 `YYYYMMDDHHMM`. **계약 PPSSrch만 `inqryBgnDate`/`inqryEndDate` 8자리 `YYYYMMDD`** — 이름과 자릿수 둘 다 다르다. 응답의 일시는 `YYYY-MM-DD HH:MM:SS` 문자열.

**(3) 조회구간 상한**: 누리장터 3종 문서에는 기간 상한이 명시되어 있지 않다(나라장터 입찰공고 1.2 문서는 일부 오퍼레이션에 "범위는 최대 1개월로 제한" 명시). 에러코드 07(입력범위값 초과)이 정의되어 있으므로 동일한 상한이 걸려 있을 가능성이 있다(추정). 안전하게 1개월 이하(권장: 일 단위) 슬라이스로 분할 호출하는 것이 좋다.

**(4) 페이지네이션**: `pageNo`/`numOfRows` 요청 + 응답 `totalCount`로 표준 오프셋 방식. `totalCount` 기준으로 마지막 페이지까지 순회하면 된다. numOfRows 항목크기는 4(문서 예제 응답에 999 사용 사례 있음). 일일 트래픽 제한(에러 22) 존재. 데이터 없음은 HTTP 에러가 아니라 resultCode `03`(No Data)로 올 수 있고, 정상은 `00`.

**(5) 상태·취소·변경 표현 방식**
- 공고: 취소·변경이 별도 이력 오퍼레이션 없이 `ntceDivNm`[등록공고/재공고/긴급공고/변경공고/취소공고] 값으로 목록에 나타난다. 나라장터의 ChgHstry(공고 변경이력) 대응 op가 누리장터에는 없으므로, 등록일시 기준 재수집 시 같은 (bidNtceNo, bidNtceOrd)의 변경·취소공고 레코드로 상태를 갱신해야 한다(재공고·재입찰 시 `bidNtceOrd` 증가).
- 개찰: `progrsDivCdNm`[유찰/개찰완료/재입찰]이 상태 축. 유찰이면 `opengCorpInfo`가 빈 값. 재입찰 발생 시 `rbidNo` 증가.
- 계약: 변경은 ChgHstry(변경일시 기준, 전체 레코드 재전송), 삭제는 DltHstry(`chgDivNm`=DEL)로 제공 — 삭제이력을 반영하지 않으면 지워진 계약이 남는다.

**(6) 파싱 주의**
- 복합 문자열 필드가 많다: `corpList`, `servcDtlList`/`prdctDtlList`/`cnstwkDtlList`, `permsnIndstrytyList`, `opengCorpInfo` — `^` 구분자 + `[...],[...]` 연결 형식이며 빈 토큰(`^^`) 존재. XML 안 문자열이므로 별도 파서 필요.
- `opengCorpInfo`는 다수 낙찰자·협상계약에서 형태가 달라짐(§2.2).
- `cntrctPrd`는 "기간 문자열"과 "n 일"이 혼재. `totCntrctAmt`는 장기계속계약 외 0 — 금액 집계는 `thtmCntrctAmt` 사용.
- 개인정보 마스킹: 전화(핸드폰 `*` 처리), 이메일 부분 마스킹 샘플 존재 — 원본 값 보장이 안 됨.
- `ServiceKey`(md) vs `serviceKey`(swagger) 표기 혼재; 키는 URL 인코딩 필수(에러 30).
- 첨부 URL(`ntceSpecDocUrl*`)은 `www.g2b.go.kr` 다운로드 링크(쿼리에 `prcmBsneSeCd`가 업무구분 힌트: 21 물품/22 용역/24 공사 — 샘플 관찰, 추정). `bssAmtDtlScrnUrl`은 차세대 이후 제공 불가로 명시. `cntrctDtlInfoUrl`은 구 나라장터(`:8067`) 화면 URL이라 현재 유효성 불확실(추정).
- 엔드포인트: 문서는 `apis.data.go.kr/1230000/ao/...`(누리장터는 `/ao/`, 나라장터 입찰공고는 `/ad/`), swagger 원천은 `nopenapi.g2b.go.kr/ao/...` — 공공데이터포털 키 기준으로는 전자를 사용.