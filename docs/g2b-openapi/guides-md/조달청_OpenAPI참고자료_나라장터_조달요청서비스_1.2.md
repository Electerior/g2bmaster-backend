# 조달청_OpenAPI참고자료_나라장터_조달요청서비스_1.2

> 출처: 공공데이터포털 참고문서(docx) 자동 변환

조달청 공공데이터 개방
OpenAPI 참고자료
목 차목 차

### 1. 서비스 명세	3


### 1.1 나라장터 조달요청서비스	3

가. 서비스 개요	3
나. 오퍼레이션 목록	5
개정 이력

| 버 전 | 변경일 | 변경 구분 | 변경사유 |
|---|---|---|---|
| 1.0 | 2025 | 최초 개정 | 최초 개정 |
| 1.1 | 2026.02.27 | 항목추가 | -응답메시지 관급자설치관급금액 (cnstwkConstGvspAmt) , 도급자설치관급금액(cnstwkPrtmConrAmt) 공공데이터 제공신청으로 인한 추가 -대상오퍼레이션 조달요청에 대한 공사조회 나라장터 검색조건에 의한 조달요청에 대한 공사조회 |
| 1.2 | 2026.04.09 | 항목추가 | -응답메시지 추정가격(presmptPrce) 공공데이터 제공신청으로 인한 추가 -대상오퍼레이션 조달요청에 대한 공사조회 나라장터 검색조건에 의한 조달요청에 대한 공사조회 |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |


### 1. 서비스 명세


### 1.1 나라장터 조달요청서비스

서비스 개요

| 서비스 정보 | 서비스 ID | PrcrmntReqInfoService |  |  |
|---|---|---|---|---|
|  | 서비스명(국문) | 나라장터 조달요청서비스 |  |  |
|  | 서비스명(영문) | PrcrmntReqInfoService |  |  |
|  | 서비스 설명 | 수요기관 또는 지방청으로부터 나라장터를 통하여 받은 조달요청 정보를 제공하는 서비스로 물품, 공사, 용역, 외자 등 업무 구분별로 조달요청번호, 계약체결형태명, 대표납품장소, 발주기관 내역 정보를 제공하는 나라장터 조달요청서비스 |  |  |
| 서비스 보안 | 서비스 인증/권한 | [O] 서비스 Key[ ] 인증서 (GPKI) [] Basic (ID/PW) [ ] 없음 | [ ]WS-Security |  |
|  | 메시지 레벨 암호화 | [ ] 전자서명	[ ] 암호화	[O] 없음 |  |  |
|  | 전송 레벨 암호화 | [ ] SSL			[ O] 없음 |  |  |
| 적용 기술 수준 | 인터페이스 표준 | [ ] SOAP 1.2 (RPC-Encoded, Document Literal, Document Literal Wrapped) [ O ] REST (GET) [ ] RSS 1.0 [ ] RSS 2.0 [ ] Atom 1.0 [ ] 기타 |  |  |
|  | 교환 데이터 표준 | [ O ] XML	[ O ] JSON	[ ] MIME	[ ] MTOM |  |  |
| 서비스 URL | 개발환경 | http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService |  |  |
|  | 운영환경 | http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService |  |  |
| 서비스 WADL | 개발환경 | N/A |  |  |
|  | 운영환경 | N/A |  |  |
| 서비스 배포 정보 | 서비스 버전 | 1.0 |  |  |
|  | 서비스 시작일 | 2025-01-06 | 배포 일자 | 2025-01-06 |
|  | 서비스 이력 | N/A |  |  |
| 메시지 교환 유형 | [O] Request-Response	[ ] Publish-Subscribe [ ] Fire-and-Forgot		[ ] Notification |  |  |  |
| 메시지 로깅 수준 | 성공 | [O] Header [ ] Body | 실패 | [O] Header [O} Body |
| 사용 제약 사항 (비고) | N/A |  |  |  |
| 서비스 제공자 | 안정우 / 조달청 조달데이터관리팀 / 042-724-7685 / eureka3112@korea.kr |  |  |  |
| 데이터 갱신주기 | 수시 |  |  |  |

오퍼레이션 목록

| 일련번호 | 서비스명(국문) | 오퍼레이션명(영문) | 오퍼레이션명(국문) | 메시지명(영문) |
|---|---|---|---|---|
| 1 | 나라장터 조달요청서비스 | getPrcrmntReqInfoListThng | 조달요청에 대한 물품조회 | N/A |
| 2 |  | getPrcrmntReqInfoListThngDetail | 조달요청에 대한 물품세부조회 | N/A |
| 3 |  | getPrcrmntReqInfoListThngPPSSrch | 나라장터검색조건에 의한 조달요청 물품조회 | N/A |
| 4 |  | getPrcrmntReqInfoListCnstwk | 조달요청에 대한 공사조회 | N/A |
| 5 |  | getPrcrmntReqInfoListCnstwkPPSSrch | 나라장터검색조건에 의한 조달요청 공사조회 | N/A |
| 6 |  | getPrcrmntReqInfoListGnrlServc | 조달요청에 대한 일반용역조회 | N/A |
| 7 |  | getPrcrmntReqInfoListGnrlServcPPSSrch | 나라장터검색조건에 대한 조달요청 일반용역조회 | N/A |
| 8 |  | getPrcrmntReqInfoListTechServc | 조달요청에 대한 기술용역조회 | N/A |
| 9 |  | getPrcrmntReqInfoListTechServcPPSSrch | 나라장터검색조건에 의한 조달요청 기술용역조회 | N/A |
| 10 |  | getPrcrmntReqInfoListFrgcpt | 조달요청에 대한 외자조회 | N/A |
| 11 |  | getPrcrmntReqInfoListFrgcptDetail | 조달요청에 대한 외자세부조회 | N/A |
| 12 |  | getPrcrmntReqInfoListFrgcptPPSSrch | 나라장터검색조건에 의한 조달요청 외자조회 | N/A |

[조달요청에 대한 물품조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 1 | 오퍼레이션명(국문) | 조달요청에 대한 물품조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListThng |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 입력일시, 접수번호 입력하여 조달요청번호, 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 물품에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1:입력일시, 2:조달요청번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 입력일시범위 시작 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 입력일시범위 종료 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00141944 | 검색하고자하는 조달요청번호
조회구분 2일 경우 필수
.나라장터화면에서 요청접수번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 30 | 1 | 물품 | 입찰업무를 구분하는 명으로 물품, 일반용역, 기술용역, 공사, 외자로 구분함 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00141944 | 조달요청을 관리하기 위한 번호이며
 *번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| cntrctCnclsStleNm | 계약체결형태명 | 100 | 0 | 총액계약 | *총액계약은 계약목적물 전체에 대하여 단가가 아닌 총액으로 체결하는 계약형태
*단가계약은 수요 빈도가 많은 품목에 대하여 단가에 의해 예정수량을 명시하고 체결하는 계약형태, 
*제3자단가계약은 각 수요기관에서 공통적으로 필요로 하는 수요물자를 계약시 미리 단가만을 정하여 계약을 체결하고 각 수요기관에서 직접 납품요구하여 구매하는 계약형태 |
| rprsntDlvrPlce | 대표납품장소 | 256 | 0 | 이천양정여자고등학교 체육관 | 조달요청의 대표납품장소 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 7530538 | 발주기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| orderInsttNm | 발주기관명 | 200 | 0 | 경기도교육청 이천양정여자고등학교 | 발주기관의 명 |
| leaseYn | 리스여부 | 1 | 0 | N | 총액 조달요청일 경우 수요기관이 물품대금을 리스계약을 통해 지불할 것인지에 대한 구분(Y/N) |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-11 10:02:55 | 담당자가 조달요청서를 접수한 일시 ”YYYY-MM-DD HH:MM:SS” |
| prcrmntReqNm | 조달요청명 | 1000 | 0 | 이천양정여고 우수조달제품(체육관 LED 전광판) 구매 시행 | 조달요청의 조달요청명 |
| rprsntPrdctClsfcNoNm | 대표품명 | 300 | 0 | 전광판 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| rprsntUprc | 대표단가 | 25 | 0 | 449739000 | 조달요청의 대표단가 |
| rprsntQty | 대표수량 | 25 | 0 | 1 | 조달요청의 대표수량 |
| rprsntAmt | 대표금액 | 25 | 0 | 449739000 | 조달요청의 대표금액(원화, 원) |
| bdgtAmt | 예산금액 | 22 | 0 | 453467010 | 품대에 수수료를 합한금액 (원화, 원) |
| prcrmntReqOfclEmpNo | 조달요청담당자사번 | 8 | 0 | ****** | 조달요청시 조달청 담당자의 사번 |
| prcrmntReqOfclNm | 조달요청담당자명 | 35 | 0 | 김지혜 | 조달요청시 조달청 담당자의 명 |
| prcrmntReqInfoUrl | 조달요청정보URL | 500 | 0 | N/A | *2025년 나라장터 차세대 이후 제공 불가 |
| rprsntUnit | 대표단위 | 30 | 0 | 식 | 조달요청의 대표단위 |
| rprsntSpecDtlsCntnts | 대표규격명세내용 | 300 | 0 | 영상스크린표시부(W)8,000mm*(H)4,480mm, 현수막 스크린 표시부(W)11,200mm*(H)960mm,  배너 스크린표시부(W)2,560mm*(H)1,440mm | 조달요청의 대표규격명세내용 |
| rprsntDedtDate | 대표납기일자 | 10 | 0 | 2026-01-31 | 조달요청의 대표납기일자(YYYY-MM-DD) |
| rprsntDlvrDaynum | 대표납품일수 | 5 | 0 | 90 | 조달요청의 대표납품일수 |
| rprsntPrdctidntno | 대표물품식별번호 | 8 | 0 | 20165060 | 조달요청의 대표물품식별번호 |
| frstyearUntyCntrctIdntNo | 초년도통합계약식별번호 | 17 | 0 | 100001218750002 | 장기계약시 초년도 통합계약식별번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-12-11 09:22:10 | 입력일시 "YYYY-MM-DD HH:MM:SS" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListThng?inqryDiv=2&prcrmntReqNo=R25DC00141944&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>물품</bsnsDivNm> <prcrmntReqNo>R25DC00141944</prcrmntReqNo> <cntrctCnclsStleNm>총액계약</cntrctCnclsStleNm> <rprsntDlvrPlce>이천양정여자고등학교 체육관</rprsntDlvrPlce> <orderInsttCd>7530538</orderInsttCd> <orderInsttNm>경기도교육청 이천양정여자고등학교</orderInsttNm> <leaseYn>N</leaseYn> <rcptDt>2025-12-11 10:02:55</rcptDt> <prcrmntReqNm>이천양정여고 우수조달제품(체육관 LED 전광판) 구매 시행</prcrmntReqNm> <rprsntPrdctClsfcNoNm>전광판</rprsntPrdctClsfcNoNm> <rprsntUprc>449739000</rprsntUprc> <rprsntQty>1</rprsntQty> <rprsntAmt>449739000</rprsntAmt> <bdgtAmt>453467010</bdgtAmt> <prcrmntReqOfclEmpNo>******</prcrmntReqOfclEmpNo> <prcrmntReqOfclNm>김지혜</prcrmntReqOfclNm> <prcrmntReqInfoUrl></prcrmntReqInfoUrl> <rprsntUnit>식</rprsntUnit> <rprsntSpecDtlsCntnts>영상스크린표시부(W)8,000mm*(H)4,480mm, 현수막 스크린 표시부(W)11,200mm*(H)960mm,  배너 스크린표시부(W)2,560mm*(H)1,440mm</rprsntSpecDtlsCntnts> <rprsntDedtDate>2026-01-31</rprsntDedtDate> <rprsntDlvrDaynum>0</rprsntDlvrDaynum> <rprsntPrdctidntno></rprsntPrdctidntno> <frstyearUntyCntrctIdntNo></frstyearUntyCntrctIdntNo> <inptDt>2025-12-11 09:22:10</inptDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[조달요청에 대한 물품세부조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 2 | 오퍼레이션명(국문) | 조달요청에 대한 물품세부조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListThngDetail |
|  | 오퍼레이션 설명 | 검색조건을 조달요청번호를 입력하여 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 물품세부에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00141944 | 검색하고자하는 조달요청번호 .나라장터화면에서 요청접수번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00141944 | 조달요청을 관리하기 위한 번호이며
 *번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| prcrmntReqDeNo | 조달요청일련번호 | 5 | 1 | 1 | 조달요청서 내역 일련번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 전광판 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| krnPrdctNm | 한글품목명 | 200 | 0 | 영상스크린표시부(W)8,000mm*(H)4,480mm, 현수막 스크린 표시부(W)11,200mm*(H)960mm,  배너 스크린표시부(W)2,560mm*(H)1,440mm | 조달요청의 한글품목명 |
| prdctQty | 물품수량 | 25 | 0 | 1 | 물품 조달요청의 물품수량 |
| qtyUprcAmt | 수량단가금액 | 25 | 0 | 449739000 | 조달요청의 수량단가금액(원화, 원) |
| prdctAmt | 물품금액 | 25 | 0 | 449739000 | 물품 조달요청의 물품금액(원화, 원) |
| prdctUnit | 물품단위 | 30 | 0 | 식 | 물품 조달요청의 물품단위 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2026-01-31 00:00:00 | 물품의 납품기한”YYYY-MM-DD HH:MM:SS” |
| dlvrPlceNm | 납품장소명 | 256 | 0 | 이천양정여자고등학교 체육관 | 납품장소명 |
| dlvryCndtnCd | 인도조건코드 | 7 | 0 | 인010005 | 인도조건을 나타내는 항목으로 코드값을 기재 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 현장설치도 | 조달요청의 인도조건명 |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 계약된 날로부터 납품기한까지의 일수 |
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 55121903 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 22558844 | 품목에 대한 고유번호로서 8자리로 표시 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListThngDetail?prcrmntReqNo=R25DC00141944&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prcrmntReqNo>R25DC00141944</prcrmntReqNo> <prcrmntReqDeNo>1</prcrmntReqDeNo> <prdctClsfcNoNm>전광판</prdctClsfcNoNm> <krnPrdctNm>영상스크린표시부(W)8,000mm*(H)4,480mm, 현수막 스크린 표시부(W)11,200mm*(H)960mm,  배너 스크린표시부(W)2,560mm*(H)1,440mm</krnPrdctNm> <prdctQty>1</prdctQty> <qtyUprcAmt>449739000</qtyUprcAmt> <prdctAmt>449739000</prdctAmt> <prdctUnit>식</prdctUnit> <dlvrTmlmtDt>2026-01-31 00:00:00</dlvrTmlmtDt> <dlvrPlceNm>이천양정여자고등학교 체육관</dlvrPlceNm> <dlvryCndtnCd>인010005</dlvryCndtnCd> <dlvryCndtnNm>현장설치도</dlvryCndtnNm> <dlvrDaynum>0</dlvrDaynum> <prdctClsfcNo>55121903</prdctClsfcNo> <prdctIdntNo></prdctIdntNo> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터검색조건에 의한 조달요청 물품조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 3 | 오퍼레이션명(국문) | 나라장터검색조건에 의한 조달요청 물품조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListThngPPSSrch |
|  | 오퍼레이션 설명 | 나라장터 검색조건(조회구분, 접수일시, 결재일시, 조달요청번호, 접수기관 품명) 등을 입력하여 조달요청번호, 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 물품에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1:접수일시, 2:결재일시, 3:조달요청번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 접수일시, 결재일시범위 시작 'YYYYMMDDHHMM"
조회구분이 1, 2인 경우 필수 (조회일시범위는 1개월 로 제한) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 접수일시범위 종료 'YYYYMMDDHHMM"
조회구분이 1, 2인 경우 필수 (조회일시범위는 1개월 로 제한) |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00081684 | 조달요청을 관리하기 위한 번호이며
 *번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| rcptBrnofceNm | 접수지청명 | 200 | 0 | 서울지방조달청 | 검색하고자하는 접수지청명 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 열교환장치 | 검색하고자하는 품명 |
| orderInsttCd | 발주기관코드 | 7 | 0 | Z015641 | 검색하고자하는 발주기관코드 |
| orderInsttNm | 발주기관명 | 200 | 0 | 남양주도시공사 | 검색하고자하는 발주기관명 |
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 40101802 | 검색하고자하는 물품분류번호 |
| reqDivCd | 요청구분코드 | 1 | 0 | 계120001 | 검색하고자하는 계약구분 
: 계120001: 총액계약, 
계120002: 일반단가계약, 
계120003: 제3자단가계약, 
계120004: 다수공급자계약
조회구분이 1, 2인 경우 선택 |
| specDocYn | 규격문서여부 | 1 | 0 | N | 검색하고자하는 규격문서여부
조회구분이 1, 2인 경우 선택 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 30 | 1 | 물품 | 입찰업무를 구분하는 명으로 물품, 용역, 공사, 외자로 구분함 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00106771 | 조달요청을 관리하기 위한 번호이며
 *번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| cntrctCnclsStleNm | 계약체결형태명 | 100 | 0 | 총액계약 | *총액계약은 계약목적물 전체에 대하여 단가가 아닌 총액으로 체결하는 계약형태
*단가계약은 수요 빈도가 많은 품목에 대하여 단가에 의해 예정수량을 명시하고 체결하는 계약형태, 
*제3자단가계약은 각 수요기관에서 공통적으로 필요로 하는 수요물자를 계약시 미리 단가만을 정하여 계약을 체결하고 각 수요기관에서 직접 납품요구하여 구매하는 계약형태 |
| rprsntDlvrPlce | 대표납품장소 | 256 | 0 | 별내노인복지관 건립 현장 | 조달요청의 대표납품장소 |
| orderInsttCd | 발주기관코드 | 7 | 0 | Z015641 | 발주기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| orderInsttNm | 발주기관명 | 200 | 0 | 남양주도시공사 | 발주기관의 명 |
| leaseYn | 리스여부 | 1 | 0 | N | 총액 조달요청일 경우 수요기관이 물품대금을 리스계약을 통해 지불할 것인지에 대한 구분(Y/N) |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-15 13:22:28 | 담당자가 조달요청서를 접수한 일시 ”YYYY-MM-DD HH:MM:SS” |
| prcrmntReqNm | 조달요청명 | 100 | 0 | 별내노인복지관 건립사업 지열설비 조달구매 | 조달요청의 조달요청명 |
| rprsntPrdctClsfcNoNm | 대표품명 | 200 | 0 | 열교환장치 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| rprsntUprc | 대표단가 | 25 | 0 | 881728000 | 조달요청의 대표단가 |
| rprsntQty | 대표수량 | 25 | 0 | 1 | 조달요청의 대표수량 |
| rprsntAmt | 대표금액 | 25 | 0 | 881728000 | 조달요청의 대표금액(원화, 원) |
| bdgtAmt | 예산금액 | 21 | 0 | 67198720 | 품대에 수수료를 합한금액 (원화, 원) |
| prcrmntReqOfclEmpNo | 조달요청담당자사번 | 8 | 0 | 888739130 | 조달요청시 조달청 담당자의 사번 |
| prcrmntReqOfclNm | 조달요청담당자명 | 35 | 0 | ****** | 조달요청시 조달청 담당자의 명 |
| prcrmntReqInfoUrl | 조달요청정보URL | 500 | 0 | N/A | *2025년 나라장터 차세대 이후 제공 불가 |
| rprsntUnit | 대표단위 | 30 | 0 | 식 | 조달요청의 대표단위 |
| rprsntSpecDtlsCntnts | 대표규격명세내용 | 200 | 0 | 기타열교환기, 지앤지테크놀러지, GeoSSUM-HyD200-H300, 752㎡, 수랭식 | 조달요청의 대표규격명세내용 |
| rprsntDedtDate | 대표납기일자 | 10 | 0 | 2027-03-31 | 조달요청의 대표납기일자(YYYY-MM-DD) |
| rprsntDlvrDaynum | 대표납품일수 | 5 | 0 | 0 | 조달요청의 대표납품일수 |
| rprsntPrdctidntno | 대표물품식별번호 | 8 | 0 | 24882275 | 조달요청의 대표물품식별번호 |
| frstyearUntyCntrctIdntNo | 초년도통합계약식별번호 | 15 | 0 | 100001218750002 | 장기계약시 초년도 통합계약식별번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-09-18 17:15:06 | 입력일시 "YYYY-MM-DD HH:MM:SS" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListThngPPSSrch?inqryDiv=1&inqryBgnDt=202512010000&inqryEndDt=202512312359&numOfRows=999&pageNo=1&rcptBrnofceNm=서울지방조달청&prdctClsfcNoNm=열교환장치&orderInsttCd=Z015641&orderInsttNm=남양주도시공사&prdctClsfcNo=40101802&reqDivCd=계120001&specDocYn=N&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>물품</bsnsDivNm> <prcrmntReqNo>R25DC00106771</prcrmntReqNo> <cntrctCnclsStleNm>총액계약</cntrctCnclsStleNm> <rprsntDlvrPlce>별내노인복지관 건립 현장</rprsntDlvrPlce> <orderInsttCd>Z015641</orderInsttCd> <orderInsttNm>남양주도시공사</orderInsttNm> <leaseYn>N</leaseYn> <rcptDt>2025-12-15 13:22:28</rcptDt> <prcrmntReqNm>별내노인복지관 건립사업 지열설비 조달구매</prcrmntReqNm> <rprsntPrdctClsfcNoNm>열교환장치</rprsntPrdctClsfcNoNm> <rprsntUprc>881728000</rprsntUprc> <rprsntQty>1</rprsntQty> <rprsntAmt>881728000</rprsntAmt> <bdgtAmt>888739130</bdgtAmt> <prcrmntReqOfclEmpNo>******</prcrmntReqOfclEmpNo> <prcrmntReqOfclNm>유재승</prcrmntReqOfclNm> <prcrmntReqInfoUrl></prcrmntReqInfoUrl> <rprsntUnit>식</rprsntUnit> <rprsntSpecDtlsCntnts>기타열교환기, 지앤지테크놀러지, GeoSSUM-HyD200-H300, 752㎡, 수랭식</rprsntSpecDtlsCntnts> <rprsntDedtDate>2027-03-31</rprsntDedtDate> <rprsntDlvrDaynum>0</rprsntDlvrDaynum> <rprsntPrdctidntno>24882275</rprsntPrdctidntno> <frstyearUntyCntrctIdntNo></frstyearUntyCntrctIdntNo> <inptDt>2025-09-18 17:15:06</inptDt> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[조달요청에 대한 공사조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 4 | 오퍼레이션명(국문) | 조달요청에 대한 공사조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListCnstwk |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 입력일시, 조달요청번호를 입력하여 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 공사에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1:입력일시, 2:조달요청번호
나라장터화면에서 요청접수번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 입력일시범위 시작 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 입력일시범위 종료 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00135705 | 검색하고자하는 조달요청번호
조회구분 2일 경우 필수
.나라장터화면에서 요청접수번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 30 | 1 | 공사 | 입찰업무를 구분하는 명으로 물품, 용역, 공사, 외자로 구분함 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00135705 | 조달요청을 관리하기 위한 번호이며
 *번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| prcrmntReqNm | 조달요청명 | 100 | 0 | (가칭)동진학교 신축공사(장기계속-2차) | 조달요청의 조달요청명 |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-01 14:49:18 | 담당자가 조달요청서를 접수한 일시 ”YYYY-MM-DD HH:MM:SS” |
| orderInsttCd | 발주기관코드 | 7 | 0 | 7021000 | 발주기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울특별시교육청 서울특별시동부교육지원청 | 발주기관의 명 |
| cntrctCnclsMthdNm | 계약체결방법명 | 200 | 0 | 총액계약 | 계약체결의 방법을 구분하는 명으로
대안, 턴키, 일반계약, P.Q, 수의계약, 실적경쟁으로 구분 단, 기술검토정보가 없을 경우는 “검토 중”으로 표기 |
| cntrctDisposNm | 계약성질명 | 30 | 0 | 장기 | 조달요청이 신규인지 장기인지 구분 |
| totCnstwkScleAmt | 총공사규모금액 | 25 | 0 | 40766166000 | 조달요청의 총공사규모금액(원화, 원) |
| cnsttyNm | 공종명 | 200 | 0 | 건축공사업 | 조달요청의 공종명 단, 기술검토정보가 없을경우는 “검토 중”으로 표기 |
| contrctAmt | 도급금액 | 25 | 0 | 32194026000 | 도급금액은 추정가격에 부가세를 더한 금액(원화, 원) |
| thtmContrctAmt | 금차도급금액 | 25 | 0 | 735000000 | 금차도급금액은 당해 차수에 대한 도급금액 (원화,원) |
| mainCnsttyDeptNm | 주공종부서명 | 200 | 0 | 시설총괄과 | 조달요청의 주공종부서의 명 |
| govsplyAmt | 관급금액 | 25 | 0 | 8572140000 | 발주자(수요기관)이 제공하는 관급자재의 설치비용(원화, 원) |
| totCnstwkDaynum | 총공사일수 | 20 | 0 | 795 | 조달요청의 총공사일 |
| thtmCnstwkDaynum | 금차공사일수 | 20 | 0 | 45 | 조달요청의 금차공사일수 |
| etcAmt | 기타금액 | 25 | 0 | 0 | 사전검토의 항목으로,  추정가격에 부가세를 더한 금액(원화, 원) |
| cnstrtsiteRgnNm | 공사현장지역명 | 200 | 0 | 서울특별시 | 조달요청의 공사현장지역명 |
| cnstwkPrearngAmt | 공사예정금액 | 25 | 0 | 36814448000 | 관급금액을 제외한 총 공사의 예정금액으로 부가세를 포함한 금액(원화, 원) |
| techRvwReqstDate | 기술검토의뢰일자 | 10 | 0 | 2025-12-01 | 조달요청의 기술검토의뢰일자(YYYY-MM-DD) |
| prcrmntReqOfclEmpNo | 조달요청담당자사번 | 8 | 0 | ****** | 조달요청시 조달청 담당자의 사번 |
| prcrmntReqOfclNm | 조달요청담당자명 | 35 | 0 | 주영은 | 조달요청시 조달청 담당자의 명 |
| prcrmntReqInfoUrl | 조달요청정보URL | 500 | 0 | N/A | *2025년 나라장터 차세대 이후 제공 불가 |
| rcptBrnofceNm | 접수지청명 | 200 | 0 | 서울지방조달청 | 조달요청의 접수지청명 |
| thtmBdgtAmt | 금차예산금액 | 25 | 0 | 32194026000 | 조달요청의 금차예산금액(원화, 원) |
| frstyearPrcrmntReqNo | 초년도조달요청번호 | 13 | 0 | 24120405_3 | 장기계약시 초년도 조달요청번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-12-01 09:13:57 | 입력일시 "YYYY-MM-DD HH:MM:SS" |
| cnstwkConstGvspAmt | 공사관급자설치관급금액 | 25 | 0 | 3951718000 | 공사관급자설치관급금액(원화, 원) |
| cnstwkPrtmConrAmt | 공사도급자설치관급금액 | 25 | 0 | 4620422000 | 조달요청의 공사도급자설치관급금액(원화, 원) |
| presmptPrce | 추정가격 | 25 | 0 | 29267296363 | 추정가격 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListCnstwk?inqryDiv=2&numOfRows=10&pageNo=1&prcrmntReqNo=R25DC00135705&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>공사</bsnsDivNm> <prcrmntReqNo>R25DC00135705</prcrmntReqNo> <prcrmntReqNm>(가칭)동진학교 신축공사(장기계속-2차)</prcrmntReqNm> <rcptDt>2025-12-01 14:49:18</rcptDt> <orderInsttCd>7021000</orderInsttCd> <orderInsttNm>서울특별시교육청 서울특별시동부교육지원청</orderInsttNm> <cntrctCnclsMthdNm>총액계약</cntrctCnclsMthdNm> <cntrctDisposNm>장기</cntrctDisposNm> <totCnstwkScleAmt>40766166000</totCnstwkScleAmt> <cnsttyNm></cnsttyNm> <contrctAmt>32194026000</contrctAmt> <thtmContrctAmt>735000000</thtmContrctAmt> <mainCnsttyDeptNm></mainCnsttyDeptNm> <govsplyAmt>8572140000</govsplyAmt> <totCnstwkDaynum>795</totCnstwkDaynum> <thtmCnstwkDaynum>45</thtmCnstwkDaynum> <etcAmt>0</etcAmt> <cnstrtsiteRgnNm>서울특별시</cnstrtsiteRgnNm> <cnstwkPrearngAmt>36814448000</cnstwkPrearngAmt> <techRvwReqstDate></techRvwReqstDate> <prcrmntReqOfclEmpNo>******</prcrmntReqOfclEmpNo> <prcrmntReqOfclNm>주영은</prcrmntReqOfclNm> <prcrmntReqInfoUrl></prcrmntReqInfoUrl> <rcptBrnofceNm>서울지방조달청</rcptBrnofceNm> <thtmBdgtAmt>32194026000</thtmBdgtAmt> <frstyearPrcrmntReqNo>24120405_3</frstyearPrcrmntReqNo> <inptDt>2025-12-01 09:13:57</inptDt> <cnstwkConstGvspAmt>3951718000</cnstwkConstGvspAmt> <cnstwkPrtmConrAmt>735000000</cnstwkPrtmConrAmt> <presmptPrce>29267296363</presmptPrce> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터검색조건에 의한 조달요청 공사조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 5 | 오퍼레이션명(국문) | 나라장터검색조건에 의한 조달요청 공사조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListCnstwkPPSSrch |
|  | 오퍼레이션 설명 | 나라장터 검색조건(조회구분, 접수일시, 조달요청번호, 접수지청명, 조달요청명, 발주기관, 조달요청번호) 등을 입력하여 조달요청번호, 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 공사에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1.접수일시, 2.조달요청번호
.나라장터화면에서 요청접수번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 접수일시 범위 시작 'YYYYMMDDHHMM"
조회구분이 1일 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512010000 | 검색하고자하는 접수일시종료 'YYYYMMDDHHMM"
조회구분이 1일 경우 필수 |
| rcptBrnofceNm | 접수지청명 | 200 | 0 | 서울지방조달청 | 검색하고자하는 접수지청명 |
| prcrmntReqNm | 조달요청명 | 100 | 0 | (가칭)동진학교 신축공사(장기계속-2차) | 검색하고자하는 조달요청명 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 7021000 | 검색하고자하는 발주기관코드 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울특별시교육청 서울특별시동부교육지원청 | 검색하고자하는 발주기관명 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00135705 | 조달요청을 관리하기 위한 번호이며
 *번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 30 | 1 | 공사 | 입찰업무를 구분하는 명으로 물품, 용역, 공사, 외자로 구분함 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00135705 | 조달요청을 관리하기 위한 번호이며
 *번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| prcrmntReqNm | 조달요청명 | 100 | 0 | (가칭)동진학교 신축공사(장기계속-2차) | 조달요청의 조달요청명 |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-01 14:49:18 | 담당자가 조달요청서를 접수한 일시 ”YYYY-MM-DD HH:MM:SS” |
| orderInsttCd | 발주기관코드 | 7 | 0 | 7021000 | 발주기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울특별시교육청 서울특별시동부교육지원청 | 발주기관의 명 |
| cntrctCnclsMthdNm | 계약체결방법명 | 200 | 0 | 총액계약 | 계약체결의 방법을 구분하는 명으로
대안, 턴키, 일반계약, P.Q, 수의계약, 실적경쟁으로 구분 단, 기술검토정보가 없을 경우는 “검토 중”으로 표기 |
| cntrctDisposNm | 계약성질명 | 30 | 0 | 장기 | 조달요청이 신규인지 장기인지 구분 |
| totCnstwkScleAmt | 총공사규모금액 | 25 | 0 | 40766166000 | 조달요청의 총공사규모금액(원화, 원) |
| cnsttyNm | 공종명 | 200 | 0 | 건축공사업 | 조달요청의 공종명 단, 기술검토정보가 없을경우는 “검토 중”으로 표기 |
| contrctAmt | 도급금액 | 25 | 0 | 32194026000 | 도급금액은 추정가격에 부가세를 더한 금액(원화, 원) |
| thtmContrctAmt | 금차도급금액 | 25 | 0 | 735000000 | 금차도급금액은 당해 차수에 대한 도급금액 (원화,원) |
| mainCnsttyDeptNm | 주공종부서명 | 200 | 0 | 시설총괄과 | 조달요청의 주공종부서의 명 |
| govsplyAmt | 관급금액 | 25 | 0 | 8572140000 | 발주자(수요기관)이 제공하는 관급자재의 설치비용(원화, 원) |
| totCnstwkDaynum | 총공사일수 | 20 | 0 | 795 | 조달요청의 총공사일 |
| thtmCnstwkDaynum | 금차공사일수 | 20 | 0 | 45 | 조달요청의 금차공사일수 |
| etcAmt | 기타금액 | 25 | 0 | 0 | 사전검토의 항목으로,  추정가격에 부가세를 더한 금액(원화, 원) |
| cnstrtsiteRgnNm | 공사현장지역명 | 200 | 0 | 서울특별시 | 조달요청의 공사현장지역명 |
| cnstwkPrearngAmt | 공사예정금액 | 25 | 0 | 36814448000 | 관급금액을 제외한 총 공사의 예정금액으로 부가세를 포함한 금액(원화, 원) |
| techRvwReqstDate | 기술검토의뢰일자 | 10 | 0 | 2025-12-01 | 조달요청의 기술검토의뢰일자(YYYY-MM-DD) |
| prcrmntReqOfclEmpNo | 조달요청담당자사번 | 8 | 0 | ****** | 조달요청시 조달청 담당자의 사번 |
| prcrmntReqOfclNm | 조달요청담당자명 | 35 | 0 | 주영은 | 조달요청시 조달청 담당자의 명 |
| prcrmntReqInfoUrl | 조달요청정보URL | 500 | 0 | N/A | *2025년 나라장터 차세대 이후 제공 불가 |
| rcptBrnofceNm | 접수지청명 | 200 | 0 | 서울지방조달청 | 조달요청의 접수지청명 |
| thtmBdgtAmt | 금차예산금액 | 25 | 0 | 32194026000 | 조달요청의 금차예산금액(원화, 원) |
| frstyearPrcrmntReqNo | 초년도조달요청번호 | 13 | 0 | 24120405_3 | 장기계약시 초년도 조달요청번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-12-01 09:13:57 | 입력일시 "YYYY-MM-DD HH:MM:SS" |
| cnstwkConstGvspAmt | 공사관급자설치관급금액 | 25 | 0 | 3951718000 | 공사관급자설치관급금액(원화, 원) |
| cnstwkPrtmConrAmt | 공사도급자설치관급금액 | 25 | 0 | 4620422000 | 조달요청의 공사도급자설치관급금액(원화, 원) |
| presmptPrce | 추정가격 | 25 | 0 | 29267296363 | 추정가격 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListCnstwkPPSSrch?inqryDiv=1&inqryBgnDt=202512010000&inqryEndDt=202512312359&rcptBrnofceNm=서울지방조달청&prcrmntReqNm=(가칭)동진학교 신축공사(장기계속-2차)&orderInsttCd=7021000&orderInsttNm=서울특별시교육청 서울특별시동부교육지원청&numOfRows=10&pageNo=1 &ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>공사</bsnsDivNm> <prcrmntReqNo>R25DC00135705</prcrmntReqNo> <prcrmntReqNm>(가칭)동진학교 신축공사(장기계속-2차)</prcrmntReqNm> <rcptDt>2025-12-01 14:49:18</rcptDt> <orderInsttCd>7021000</orderInsttCd> <orderInsttNm>서울특별시교육청 서울특별시동부교육지원청</orderInsttNm> <cntrctCnclsMthdNm>총액계약</cntrctCnclsMthdNm> <cntrctDisposNm>장기</cntrctDisposNm> <totCnstwkScleAmt>40766166000</totCnstwkScleAmt> <cnsttyNm></cnsttyNm> <contrctAmt>32194026000</contrctAmt> <thtmContrctAmt>735000000</thtmContrctAmt> <mainCnsttyDeptNm></mainCnsttyDeptNm> <govsplyAmt>8572140000</govsplyAmt> <totCnstwkDaynum>795</totCnstwkDaynum> <thtmCnstwkDaynum>45</thtmCnstwkDaynum> <etcAmt>0</etcAmt> <cnstrtsiteRgnNm>서울특별시</cnstrtsiteRgnNm> <cnstwkPrearngAmt>36814448000</cnstwkPrearngAmt> <techRvwReqstDate></techRvwReqstDate> <prcrmntReqOfclEmpNo>******</prcrmntReqOfclEmpNo> <prcrmntReqOfclNm>주영은</prcrmntReqOfclNm> <prcrmntReqInfoUrl></prcrmntReqInfoUrl> <rcptBrnofceNm>서울지방조달청</rcptBrnofceNm> <thtmBdgtAmt>32194026000</thtmBdgtAmt> <frstyearPrcrmntReqNo>24120405_3</frstyearPrcrmntReqNo> <inptDt>2025-12-01 09:13:57</inptDt> <cnstwkConstGvspAmt>3951718000</cnstwkConstGvspAmt> <cnstwkPrtmConrAmt>735000000</cnstwkPrtmConrAmt> <presmptPrce>29267296363</presmptPrce> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[조달요청에 대한 일반용역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 6 | 오퍼레이션명(국문) | 조달요청에 대한 일반용역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListGnrlServc |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 입력일시, 접수번호 입력하여 조달요청번호, 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 일반용역에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1:입력일시, 2:조달요청번호
.나라장터화면에서 요청접수번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 입력일시범위 시작 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 입력일시범위 종료 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00135684 | 검색하고자하는 조달요청번호
.나라장터화면에서 요청접수번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 30 | 1 | 일반용역 | 입찰업무를 구분하는 명으로 물품, 용역, 공사, 외자로 구분함 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00135684 | 조달요청을 관리하기 위한 번호이며
 *번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| cntrctCnclsStleNm | 계약체결형태명 | 100 | 0 | 총액계약 | *총액계약은 계약목적물 전체에 대하여 단가가 아닌 총액으로 체결하는 계약형태
*단가계약은 수요 빈도가 많은 품목에 대하여 단가에 의해 예정수량을 명시하고 체결하는 계약형태, 
*제3자단가계약은 각 수요기관에서 공통적으로 필요로 하는 수요물자를 계약시 미리 단가만을 정하여 계약을 체결하고 각 수요기관에서 직접 납품요구하여 구매하는 계약형태 |
| rprsntDlvrPlce | 대표납품장소 | 256 | 0 | 국립장애인도서관 | 조달요청의 대표납품장소 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 1371829 | 발주기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| orderInsttNm | 발주기관명 | 200 | 0 | 문화체육관광부 국립장애인도서관 | 발주기관의 명 |
| leaseYn | 리스여부 | 1 | 0 | N | 총액 조달요청일 경우 수요기관이 물품대금을 리스계약을 통해 지불할 것인지에 대한 구분(Y/N) |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-01 13:44:13 | 담당자가 조달요청서를 접수한 일시 “YYYY-MM-DD HH:MM:SS” |
| prcrmntReqNm | 조달요청명 | 100 | 0 | 2025~2026년 국립장애인도서관 정보시스템 통합유지관리(2년차) | 조달요청의 조달요청명 |
| rprsntPrdctClsfcNoNm | 대표품명 | 200 | 0 | 정보시스템유지관리서비스 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| rprsntUprc | 대표단가 | 25 | 0 | 442000000 | 조달요청의 대표단가 |
| rprsntQty | 대표수량 | 25 | 0 | 1 | 조달요청의 대표수량 |
| rprsntAmt | 대표금액 | 25 | 0 | 442000000 | 조달요청의 대표금액(원화, 원) |
| bdgtAmt | 예산금액 | 21 | 0 | 446036120 | 품대에 수수료를 합한금액 (원화, 원) |
| prcrmntReqOfclEmpNo | 조달요청담당자사번 | 8 | 0 | ****** | 조달요청시 조달청 담당자의 사번 |
| prcrmntReqOfclNm | 조달요청담당자명 | 35 | 0 | 유승은 | 조달요청시 조달청 담당자의 명 |
| prcrmntReqInfoUrl | 조달요청정보URL | 500 | 0 | N/A | *2025년 나라장터 차세대 이후 제공 불가 |
| rprsntUnit | 대표단위 | 30 | 0 | 식 | 조달요청의 대표단위 |
| rprsntSpecDtlsCntnts | 대표규격명세내용 | 200 | 0 | 2025~2026년 국립장애인도서관 정보시스템 통합유지관리 | 조달요청의 대표규격명세내용 |
| rprsntDedtDate | 대표납기일자 | 10 | 0 | 2026-12-31 | 조달요청의 대표납기일자(YYYY-MM-DD) |
| rprsntDlvrDaynum | 대표납품일수 | 5 | 0 | 0 | 조달요청의 대표납품일수 |
| rprsntPrdctidntno | 대표물품식별번호 | 8 | 0 | N/A | 조달요청의 대표물품식별번호 |
| frstyearUntyCntrctIdntNo | 초년도통합계약식별번호 | 15 | 0 | 122482628_1 | 장기계약시 초년도 통합계약식별번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-12-01 08:54:35 | 입력일시 "YYYY-MM-DD HH:MM:SS" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListGnrlServc?inqryDiv=2&numOfRows=10&pageNo=1&prcrmntReqNo=R25DC00141944&type=xml&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>일반용역</bsnsDivNm> <prcrmntReqNo>R25DC00135684</prcrmntReqNo> <cntrctCnclsStleNm>총액계약</cntrctCnclsStleNm> <rprsntDlvrPlce>국립장애인도서관</rprsntDlvrPlce> <orderInsttCd>1371829</orderInsttCd> <orderInsttNm>문화체육관광부 국립장애인도서관</orderInsttNm> <leaseYn>N</leaseYn> <rcptDt>2025-12-01 13:44:13</rcptDt> <prcrmntReqNm>2025~2026년 국립장애인도서관 정보시스템 통합유지관리(2년차)</prcrmntReqNm> <rprsntPrdctClsfcNoNm>정보시스템유지관리서비스</rprsntPrdctClsfcNoNm> <rprsntUprc>442000000</rprsntUprc> <rprsntQty>1</rprsntQty> <rprsntAmt>442000000</rprsntAmt> <bdgtAmt>446036120</bdgtAmt> <prcrmntReqOfclEmpNo>******</prcrmntReqOfclEmpNo> <prcrmntReqOfclNm>유승은</prcrmntReqOfclNm> <prcrmntReqInfoUrl></prcrmntReqInfoUrl> <rprsntUnit></rprsntUnit> <rprsntSpecDtlsCntnts>2025~2026년 국립장애인도서관 정보시스템 통합유지관리</rprsntSpecDtlsCntnts> <rprsntDedtDate>2026-12-31</rprsntDedtDate> <rprsntDlvrDaynum>0</rprsntDlvrDaynum> <rprsntPrdctidntno></rprsntPrdctidntno> <frstyearUntyCntrctIdntNo>122482628_1</frstyearUntyCntrctIdntNo> <inptDt>2025-12-01 08:54:35</inptDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터검색조건에 의한 조달요청 일반용역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 7 | 오퍼레이션명(국문) | 나라장터검색조건에 의한 조달요청 일반용역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListGnrlServcPPSSrch |
|  | 오퍼레이션 설명 | 나라장터 검색조건(조회구분, 접수일시, 조달요청번호, 접수지청명, 조달요청명, 발주기관, 조달요청번호) 등을 입력하여 조달요청번호, 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 일반용역에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1:접수일시, 2:조달요청번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 접수일시범위 시작 'YYYYMMDDHHMM"
조회구분이 1인 경우 필수(조회일시범위는 1개월 로 제한) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 접수일시범위 종료 'YYYYMMDDHHMM"
조회구분이 1인 경우 필수(조회일시범위는 1개월 로 제한) |
| rcptBrnofceNm | 접수지청명 | 200 | 0 | 서울지방조달청 | 검색하고자하는 지청명
EX)본청, 서울지방조달청 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 정보시스템개발서비스 | 검색하고자하는 품명 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 1352159 | 검색하고자하는 발주기관코드 |
| orderInsttNm | 발주기관명 | 200 | 0 | 보건복지부 질병관리본부 | 검색하고자하는 발주기관명 |
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 81111899 | 검색하고자하는 물품분류번호 |
| reqDivCd | 요청구분코드 | 1 | 0 | 1 | 검색하고자하는 계약구분 
계120001: 총액계약, 
계120002: 일반단가계약, 
계120003: 제3자단가계약, 
계120004: 다수공급자계약
조회구분이 1, 2인 경우 선택 |
| specDocYn | 규격문서여부 | 1 | 0 | N | 검색하고자하는 규격문서여부 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | 0016600835 | 검색하고자하는 조달요청번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 30 | 1 | 일반용역 | 입찰업무를 구분하는 명으로 물품, 용역, 공사, 외자로 구분함 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00135684 | 조달요청을 관리하기 위한 번호이며
 *번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| cntrctCnclsStleNm | 계약체결형태명 | 100 | 0 | 총액계약 | *총액계약은 계약목적물 전체에 대하여 단가가 아닌 총액으로 체결하는 계약형태
*단가계약은 수요 빈도가 많은 품목에 대하여 단가에 의해 예정수량을 명시하고 체결하는 계약형태, 
*제3자단가계약은 각 수요기관에서 공통적으로 필요로 하는 수요물자를 계약시 미리 단가만을 정하여 계약을 체결하고 각 수요기관에서 직접 납품요구하여 구매하는 계약형태 |
| rprsntDlvrPlce | 대표납품장소 | 256 | 0 | 국립장애인도서관 | 조달요청의 대표납품장소 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 1371829 | 발주기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| orderInsttNm | 발주기관명 | 200 | 0 | 문화체육관광부 국립장애인도서관 | 발주기관의 명 |
| leaseYn | 리스여부 | 1 | 0 | N | 총액 조달요청일 경우 수요기관이 물품대금을 리스계약을 통해 지불할 것인지에 대한 구분(Y/N) |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-01 13:44:13 | 담당자가 조달요청서를 접수한 일시 “YYYY-MM-DD HH:MM:SS” |
| prcrmntReqNm | 조달요청명 | 100 | 0 | 2025~2026년 국립장애인도서관 정보시스템 통합유지관리(2년차) | 조달요청의 조달요청명 |
| rprsntPrdctClsfcNoNm | 대표품명 | 200 | 0 | 정보시스템유지관리서비스 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| rprsntUprc | 대표단가 | 25 | 0 | 442000000 | 조달요청의 대표단가 |
| rprsntQty | 대표수량 | 25 | 0 | 1 | 조달요청의 대표수량 |
| rprsntAmt | 대표금액 | 25 | 0 | 442000000 | 조달요청의 대표금액(원화, 원) |
| bdgtAmt | 예산금액 | 21 | 0 | 446036120 | 품대에 수수료를 합한금액 (원화, 원) |
| prcrmntReqOfclEmpNo | 조달요청담당자사번 | 8 | 0 | ****** | 조달요청시 조달청 담당자의 사번 |
| prcrmntReqOfclNm | 조달요청담당자명 | 35 | 0 | 유승은 | 조달요청시 조달청 담당자의 명 |
| prcrmntReqInfoUrl | 조달요청정보URL | 500 | 0 | N/A | *2025년 나라장터 차세대 이후 제공 불가 |
| rprsntUnit | 대표단위 | 30 | 0 | 식 | 조달요청의 대표단위 |
| rprsntSpecDtlsCntnts | 대표규격명세내용 | 200 | 0 | 2025~2026년 국립장애인도서관 정보시스템 통합유지관리 | 조달요청의 대표규격명세내용 |
| rprsntDedtDate | 대표납기일자 | 10 | 0 | 2026-12-31 | 조달요청의 대표납기일자(YYYY-MM-DD) |
| rprsntDlvrDaynum | 대표납품일수 | 5 | 0 | 0 | 조달요청의 대표납품일수 |
| rprsntPrdctidntno | 대표물품식별번호 | 8 | 0 | N/A | 조달요청의 대표물품식별번호 |
| frstyearUntyCntrctIdntNo | 초년도통합계약식별번호 | 15 | 0 | 122482628_1 | 장기계약시 초년도 통합계약식별번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-12-01 08:54:35 | 입력일시 "YYYY-MM-DD HH:MM:SS" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListGnrlServc?inqryDiv=1&inqryBgnDt=202512010000&inqryEndDt=202512312359&numOfRows=10&pageNo=1&prdctClsfcNoNm=정보시스템유지관리서비스&orderInsttCd=1371829&orderInsttNm=문화체육관광부 국립장애인도서관&prdctClsfcNo=81111899&reqDivCd=계120001&type=xml&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>일반용역</bsnsDivNm> <prcrmntReqNo>R25DC00135684</prcrmntReqNo> <cntrctCnclsStleNm>총액계약</cntrctCnclsStleNm> <rprsntDlvrPlce>국립장애인도서관</rprsntDlvrPlce> <orderInsttCd>1371829</orderInsttCd> <orderInsttNm>문화체육관광부 국립장애인도서관</orderInsttNm> <leaseYn>N</leaseYn> <rcptDt>2025-12-01 13:44:13</rcptDt> <prcrmntReqNm>2025~2026년 국립장애인도서관 정보시스템 통합유지관리(2년차)</prcrmntReqNm> <rprsntPrdctClsfcNoNm>정보시스템유지관리서비스</rprsntPrdctClsfcNoNm> <rprsntUprc>442000000</rprsntUprc> <rprsntQty>1</rprsntQty> <rprsntAmt>442000000</rprsntAmt> <bdgtAmt>446036120</bdgtAmt> <prcrmntReqOfclEmpNo>******</prcrmntReqOfclEmpNo> <prcrmntReqOfclNm>유승은</prcrmntReqOfclNm> <prcrmntReqInfoUrl></prcrmntReqInfoUrl> <rprsntUnit></rprsntUnit> <rprsntSpecDtlsCntnts>2025~2026년 국립장애인도서관 정보시스템 통합유지관리</rprsntSpecDtlsCntnts> <rprsntDedtDate>2026-12-31</rprsntDedtDate> <rprsntDlvrDaynum>0</rprsntDlvrDaynum> <rprsntPrdctidntno></rprsntPrdctidntno> <frstyearUntyCntrctIdntNo>122482628_1</frstyearUntyCntrctIdntNo> <inptDt>2025-12-01 08:54:35</inptDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[조달요청에 대한 기술용역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 8 | 오퍼레이션명(국문) | 조달요청에 대한 기술용역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListTechServc |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 입력일시, 접수번호 입력하여 조달요청번호, 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 기술용역에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1:입력일시, 2:조달요청번호
.나라장터화면에서 요청접수번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 입력일시범위 시작 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 입력일시범위 종료 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00136607 | 검색하고자하는 조달요청번호
.나라장터화면에서 요청접수번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 30 | 1 | 기술용역 | 입찰업무를 구분하는 명으로 물품, 용역, 공사, 외자로 구분함 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00136607 | 조달요청을 관리하기 위한 번호이며
*번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| prcrmntReqNm | 조달요청명 | 100 | 0 | (장기2차)서울갈현초 그린스마트 미래학교 및 복합화 전기공사 감리용역 | 조달요청의 조달요청명 |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-03 09:13:56 | 담당자가 조달요청서를 접수한 일시 ”YYYY-MM-DD HH:MM:SS” |
| orderInsttCd | 발주기관코드 | 7 | 0 | 7031000 | 발주기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울특별시교육청 서울특별시서부교육지원청 | 발주기관의 명 |
| cntrctCnclsMthdNm | 계약체결방법명 | 200 | 0 | 총액계약 | 계약체결의 방법을 구분하는 명으로
대안, 턴키, 일반계약, P.Q, 수의계약, 실적경쟁으로 구분 단, 기술검토정보가 없을 경우는 “검토 중”으로 표기 |
| cntrctDisposNm | 계약성질명 | 30 | 0 | 장기 | 조달요청이 신규인지 장기인지 구분 |
| totCnstwkScleAmt | 총공사규모금액 | 25 | 0 | 657619500 | 조달요청의 총공사규모금액(원화, 원) |
| cnsttyNm | 공종명 | 200 | 0 | 검토 중 | 조달요청의 공종명 단,기술검토정보가 없을 경우 “검토 중” |
| contrctAmt | 도급금액 | 25 | 0 | 0 | 도급금액은 추정가격에 부가세를 더한 금액(원화, 원) |
| mainCnsttyDeptNm | 주공종부서명 | 200 | 0 | N/A | 조달요청의 주공종부서의 명 |
| govsplyAmt | 관급금액 | 25 | 0 | 0 | 발주자(수요기관)이 제공하는 관급자재의 설치비용(원화, 원) |
| totCnstwkDaynum | 총공사일수 | 20 | 0 | 1170 | 조달요청의 총공사일 |
| thtmCnstwkDaynum | 금차공사일수 | 20 | 0 | 72 | 조달요청의 금차공사일수 |
| etcAmt | 기타금액 | 25 | 0 | 0 | 사전검토의 항목으로,  추정가격에 부가세를 더한 금액(원화, 원) |
| cnstrtsiteRgnNm | 공사현장지역명 | 200 | 0 | 서울특별시 은평구 | 조달요청의 공사현장지역명 |
| totSrvceBdgtAmt | 총용역예산금액 | 25 | 0 | 40172680 | 관급금액을 제외한 총용역예산금액으로 부가세를 포함한 금액(원화, 원) |
| techRvwReqstDate | 기술검토의뢰일자 | 10 | 0 | 2025-12-02 | 조달요청의 기술검토의뢰일자(YYYY-MM-DD) |
| prcrmntReqOfclEmpNo | 조달요청담당자사번 | 8 | 0 | ****** | 조달요청시 조달청 담당자의 사번 |
| prcrmntReqOfclNm | 조달요청담당자명 | 35 | 0 | 김상기 | 조달요청시 조달청 담당자의 명 |
| prcrmntReqInfoUrl | 조달요청정보URL | 500 | 0 | N/A | *2025년 나라장터 차세대 이후 제공 불가 |
| rcptBrnofceNm | 접수지청명 | 200 | 0 | 조달청 | 조달요청의 접수지청명 |
| thtmBdgtAmt | 금차예산금액 | 25 | 0 | 0 | 조달요청의 금차용역예산금액(원화, 원) |
| frstyearPrcrmntReqNo | 초년도조달요청번호 | 13 | 0 | 24930377_5 | 장기계약시 초년도 조달요청번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-12-02 09:19:23 | 입력일시 "YYYY-MM-DD HH:MM:SS" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListTechServc?inqryDiv=2&inqryBgnDt=202512010000&inqryEndDt=202512312359&numOfRows=999&pageNo=1&prcrmntReqNo=R25DC00136607&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>기술용역</bsnsDivNm> <prcrmntReqNo>R25DC00136607</prcrmntReqNo> <prcrmntReqNm>(장기2차)서울갈현초 그린스마트 미래학교 및 복합화 전기공사 감리용역</prcrmntReqNm> <rcptDt>2025-12-03 09:13:56</rcptDt> <orderInsttCd>7031000</orderInsttCd> <orderInsttNm>서울특별시교육청 서울특별시서부교육지원청</orderInsttNm> <cntrctCnclsMthdNm>총액계약</cntrctCnclsMthdNm> <cntrctDisposNm>장기</cntrctDisposNm> <totCnstwkScleAmt>657619500</totCnstwkScleAmt> <cnsttyNm></cnsttyNm> <contrctAmt>0</contrctAmt> <mainCnsttyDeptNm></mainCnsttyDeptNm> <govsplyAmt>0</govsplyAmt> <totCnstwkDaynum>1170</totCnstwkDaynum> <thtmCnstwkDaynum>72</thtmCnstwkDaynum> <etcAmt>0</etcAmt> <cnstrtsiteRgnNm>서울특별시 은평구</cnstrtsiteRgnNm> <totSrvceBdgtAmt>40172680</totSrvceBdgtAmt> <techRvwReqstDate></techRvwReqstDate> <prcrmntReqOfclEmpNo>******</prcrmntReqOfclEmpNo> <prcrmntReqOfclNm>김상기</prcrmntReqOfclNm> <prcrmntReqInfoUrl></prcrmntReqInfoUrl> <rcptBrnofceNm>조달청</rcptBrnofceNm> <thtmBdgtAmt>0</thtmBdgtAmt> <frstyearPrcrmntReqNo>24930377_5</frstyearPrcrmntReqNo> <inptDt>2025-12-02 09:19:23</inptDt> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터검색조건에 의한 조달요청 기술용역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 9 | 오퍼레이션명(국문) | 나라장터검색조건에 의한 조달요청 기술용역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListTechServcPPSSrch |
|  | 오퍼레이션 설명 | 나라장터 검색조건(조회구분, 접수일시, 조달요청번호, 접수지청명, 조달요청명, 발주기관, 조달요청번호) 등을 입력하여 조달요청번호, 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 기술용역에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1.접수일시, 2.조달요청번호
.나라장터화면에서 요청접수번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 접수일시 범위 시작 'YYYYMMDDHHMM"
조회구분이 1이면 필수(조회일시범위는 1개월 로 제한) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 접수일시종료 'YYYYMMDDHHMM"
조회구분이 1이면 필수(조회일시범위는 1개월 로 제한) |
| rcptBrnofceNm | 접수지청명 | 200 | 0 | 조달청 | 검색하고자하는 지청명
조회구분이 1일 경우 선택
EX)본청, 서울지방조달청 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 7031000 | 검색하고자 하는 발주기관코드
조회구분이 1일 경우 선택 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울특별시교육청 서울특별시서부교육지원청 | 검색하고자하는 발주기관명
조회구분이 1일 경우 선택 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00136607 | 검색하고자하는 조달요청번호(하이픈없이 숫자만 입력 EX) 1603216-00 =>160321600 조회구분 2일 경우 필수
.나라장터 화면에서 공사관리번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 30 | 1 | 기술용역 | 입찰업무를 구분하는 명으로 물품, 용역, 공사, 외자로 구분함 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00136607 | 조달요청을 관리하기 위한 번호이며
*번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| prcrmntReqNm | 조달요청명 | 100 | 0 | (장기2차)서울갈현초 그린스마트 미래학교 및 복합화 전기공사 감리용역 | 조달요청의 조달요청명 |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-03 09:13:56 | 담당자가 조달요청서를 접수한 일시 ”YYYY-MM-DD HH:MM:SS” |
| orderInsttCd | 발주기관코드 | 7 | 0 | 7031000 | 발주기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울특별시교육청 서울특별시서부교육지원청 | 발주기관의 명 |
| cntrctCnclsMthdNm | 계약체결방법명 | 200 | 0 | 총액계약 | 계약체결의 방법을 구분하는 명으로
대안, 턴키, 일반계약, P.Q, 수의계약, 실적경쟁으로 구분 단, 기술검토정보가 없을 경우는 “검토 중”으로 표기 |
| cntrctDisposNm | 계약성질명 | 30 | 0 | 장기 | 조달요청이 신규인지 장기인지 구분 |
| totCnstwkScleAmt | 총공사규모금액 | 25 | 0 | 657619500 | 조달요청의 총공사규모금액(원화, 원) |
| cnsttyNm | 공종명 | 200 | 0 | 검토 중 | 조달요청의 공종명 단,기술검토정보가 없을 경우 “검토 중” |
| contrctAmt | 도급금액 | 25 | 0 | 0 | 도급금액은 추정가격에 부가세를 더한 금액(원화, 원) |
| mainCnsttyDeptNm | 주공종부서명 | 200 | 0 | N/A | 조달요청의 주공종부서의 명 |
| govsplyAmt | 관급금액 | 25 | 0 | 0 | 발주자(수요기관)이 제공하는 관급자재의 설치비용(원화, 원) |
| totCnstwkDaynum | 총공사일수 | 20 | 0 | 1170 | 조달요청의 총공사일 |
| thtmCnstwkDaynum | 금차공사일수 | 20 | 0 | 72 | 조달요청의 금차공사일수 |
| etcAmt | 기타금액 | 25 | 0 | 0 | 사전검토의 항목으로,  추정가격에 부가세를 더한 금액(원화, 원) |
| cnstrtsiteRgnNm | 공사현장지역명 | 200 | 0 | 서울특별시 은평구 | 조달요청의 공사현장지역명 |
| totSrvceBdgtAmt | 총용역예산금액 | 25 | 0 | 40172680 | 관급금액을 제외한 총용역예산금액으로 부가세를 포함한 금액(원화, 원) |
| techRvwReqstDate | 기술검토의뢰일자 | 10 | 0 | 2025-12-02 | 조달요청의 기술검토의뢰일자(YYYY-MM-DD) |
| prcrmntReqOfclEmpNo | 조달요청담당자사번 | 8 | 0 | ****** | 조달요청시 조달청 담당자의 사번 |
| prcrmntReqOfclNm | 조달요청담당자명 | 35 | 0 | 김상기 | 조달요청시 조달청 담당자의 명 |
| prcrmntReqInfoUrl | 조달요청정보URL | 500 | 0 | N/A | *2025년 나라장터 차세대 이후 제공 불가 |
| rcptBrnofceNm | 접수지청명 | 200 | 0 | 조달청 | 조달요청의 접수지청명 |
| thtmBdgtAmt | 금차예산금액 | 25 | 0 | 0 | 조달요청의 금차용역예산금액(원화, 원) |
| frstyearPrcrmntReqNo | 초년도조달요청번호 | 13 | 0 | 24930377_5 | 장기계약시 초년도 조달요청번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-12-02 09:19:23 | 입력일시 "YYYY-MM-DD HH:MM:SS" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListTechServcPPSSrch?inqryDiv=2&prcrmntReqNo=R25DC00136607&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>기술용역</bsnsDivNm> <prcrmntReqNo>R25DC00136607</prcrmntReqNo> <prcrmntReqNm>(장기2차)서울갈현초 그린스마트 미래학교 및 복합화 전기공사 감리용역</prcrmntReqNm> <rcptDt>2025-12-03 09:13:56</rcptDt> <orderInsttCd>7031000</orderInsttCd> <orderInsttNm>서울특별시교육청 서울특별시서부교육지원청</orderInsttNm> <cntrctCnclsMthdNm>총액계약</cntrctCnclsMthdNm> <cntrctDisposNm>장기</cntrctDisposNm> <totCnstwkScleAmt>657619500</totCnstwkScleAmt> <cnsttyNm></cnsttyNm> <contrctAmt>0</contrctAmt> <mainCnsttyDeptNm></mainCnsttyDeptNm> <govsplyAmt>0</govsplyAmt> <totCnstwkDaynum>1170</totCnstwkDaynum> <thtmCnstwkDaynum>72</thtmCnstwkDaynum> <etcAmt>0</etcAmt> <cnstrtsiteRgnNm>서울특별시 은평구</cnstrtsiteRgnNm> <totSrvceBdgtAmt>40172680</totSrvceBdgtAmt> <techRvwReqstDate></techRvwReqstDate> <prcrmntReqOfclEmpNo>******</prcrmntReqOfclEmpNo> <prcrmntReqOfclNm>김상기</prcrmntReqOfclNm> <prcrmntReqInfoUrl></prcrmntReqInfoUrl> <rcptBrnofceNm>조달청</rcptBrnofceNm> <thtmBdgtAmt>0</thtmBdgtAmt> <frstyearPrcrmntReqNo>24930377_5</frstyearPrcrmntReqNo> <inptDt>2025-12-02 09:19:23</inptDt> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[조달요청에 대한 외자조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 10 | 오퍼레이션명(국문) | 조달요청에 대한 외자조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListFrgcpt |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 입력일시, 조달요청번호를 입력하여 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 외자에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1:입력일시, 2:조달요청번호
.나라장터화면에서 요청접수번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 입력일시범위 시작 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 입력일시범위 종료 'YYYYMMDDHHMM"
조회구분 1일 경우 필수 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00139112 | 검색하고자하는 조달요청번호
.나라장터화면에서 요청접수번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00139112 | 조달요청을 관리하기 위한 번호이며
*번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| engRprsntPrdctNm | 영문대표물품명 | 100 | 0 | 컴퓨터서버 | 조달요청의 영문대표물품명 |
| dminsttCd | 수요기관코드 | 7 | 1 | 1360000 | 수요기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드가 표기됨 |
| dminsttNm | 수요기관명 | 200 | 0 | 기상청 | 중앙조달인 경우 조달사업에 관한 법률 제2조(정의)에 따라 수요물자의 구매 공급 또는 시설공사 계약의 체결을 조달청장에게 요청할 수 있도록 조달청장이 인정하여 등록한 기관 또는 자체전자조달시스템을 이용하는 기관인 경우 계약을 의뢰한 기관의 명으로 공고기관과 수요기관이 동일할 수 있음 |
| cptalDivCd | 자금구분코드 | 1 | 0 | 자090002 | 조달요청의 자금구분코드 자090001:KFX 자090002:LEASE 자090003:ADB/L 자090004:IBRD/L 자090005:OECF/L |
| cptalDivNm | 자금구분명 | 200 | 0 | LEASE | 조달요청의 자금구분명
KFX(정부보유외환) 
LEASE(LEASE 계약) 
ADB/L(아시아개발은행차관)
IBRD/L(국제부흥개발은행차관) 
OECF/L(일본해외경재협회차관) |
| totlPrdctIdntNoNum | 전체품목건수 | 25 | 0 | 1 | 조달요청의 전체품목건수 |
| asignDolAmt | 배정미화금액 | 25 | 0 | 59394160 | 조달요청의 배정미화금액(미화, 달러) |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-08 10:36:17 | 담당자가 조달요청서를 접수한 일시 “YYYY-MM-DD HH:MM:SS” |
| frstyearUntyCntrctIdntNo | 초년도통합계약식별번호 | 15 | 0 | 100001218750002 | 장기계약시 초년도 통합계약식별번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-12-05 11:10:40 | 입력일시 "YYYY-MM-DD HH:MM:SS" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListFrgcpt?inqryDiv=2&prcrmntReqNo=R25DC00139112pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prcrmntReqNo>R25DC00139112</prcrmntReqNo> <engRprsntPrdctNm>컴퓨터서버</engRprsntPrdctNm> <dminsttCd>1360000</dminsttCd> <dminsttNm>기상청</dminsttNm> <cptalDivCd>자090002</cptalDivCd> <cptalDivNm>LEASE</cptalDivNm> <totlPrdctIdntNoNum>1</totlPrdctIdntNoNum> <asignDolAmt>59394160.6</asignDolAmt> <rcptDt>2025-12-08 10:36:17</rcptDt> <frstyearUntyCntrctIdntNo></frstyearUntyCntrctIdntNo> <inptDt>2025-12-05 11:10:40</inptDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[조달요청에 대한 외자세부조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 11 | 오퍼레이션명(국문) | 조달요청에 대한 외자세부조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListFrgcptDetail |
|  | 오퍼레이션 설명 | 검색조건을 조달요청번호를 입력하여 계약체결형태명, 대표납품장소, 발주기관, 조달요청명 등 외자세부에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00139112 | 검색하고자하는 조달요청번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00139112 | 조달요청을 관리하기 위한 번호이며
*번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| engRprsntPrdctNm | 영문대표물품명 | 100 | 0 | 컴퓨터서버 | 조달요청의 영문대표물품명 |
| cptalDivNm | 자금구분명 | 200 | 0 | LEASE | 조달요청의 자금구분명 |
| dminsttCd | 수요기관코드 | 7 | 1 | 1360000 | 조달요청의 수요기관코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드가 표기됨 |
| dminsttNm | 수요기관명 | 200 | 0 | 기상청 | 중앙조달인 경우 조달사업에 관한 법률 제2조(정의)에 따라 수요물자의 구매 공급 또는 시설공사 계약의 체결을 조달청장에게 요청할 수 있도록 조달청장이 인정하여 등록한 기관 또는 자체전자조달시스템을 이용하는 기관인 경우 계약을 의뢰한 기관의 명으로 공고기관과 수요기관이 동일할 수 있음 |
| totlPrdctIdntNoNum | 전체품목건수 | 25 | 0 | 1 | 조달요청의 전체품목건수 |
| asignDolAmt | 배정미화금액 | 25 | 0 | 59394160 | 조달요청의 배정미화금액(미화, 달러) |
| rcptDt | 접수일시 | 19 | 1 | 2025-12-05 11:10:40 | 담당자가 조달요청서를 접수한 일시 “YYYY-MM-DD HH:MM:SS” |
| prcrmntReqChrgDeptNm | 조달요청담당부서명 | 100 | 0 | 해외물자과 | 조달요청의 조달요청담당부서명 |
| prcrmntReqOfclNm | 조달요청담당자명 | 35 | 0 | 허수진 | 조달요청시 조달청 담당자의 명 |
| prcrmntReqOfclEmpno | 조달요청담당자사번 | 8 | 0 | ****** | 조달요청시 조달청 담당자의 사번 |
| brnofceNm | 지청명 | 200 | 0 | 조달청 | 조달요청서를 접수한 담당자의 소속 지청명 |
| itemNo | ITEM번호 | 5 | 0 | 0 | 외자에서 사용되는 ITEM번호 |
| reqHskNo | 요청HSK번호 | 10 | 0 | 8471491010 | 조달요청의 요청HSK번호 |
| prdctClsfcNo | 물품분류번호 | 10 | 0 | 4321150102 | 품명에 대한 분류번호 8자리 또는 세부분류번호 10자리로 표시 |
| reqNm | 요청품명 | 200 | 0 | 제안요청서 참고 | 조달요청의 요청품명 |
| reqQty | 요청수량 | 25 | 0 | 1 | 조달요청의 요청수량 |
| reqUnitNm | 요청단위명 | 200 | 0 | SETS | 조달요청의 요청단위명 |
| prdctAccotAsignAmt | 물품별배정금액 | 25 | 0 | 59394160 | 물품 조달요청의 물품별배정금액(원화, 원) |
| frgncptlCntrctCnclsMthdNm | 외자계약체결방법명 | 200 | 0 | 일반경쟁 | 계약체결의 방법을 구분하는 명
*일반경쟁계약은 계약 대상 물품의 규격 및 시방서와 계약조건 등을 널리 공고하여 일정한 자격을 가진 불특정 다수인의 입찰희망자를 모두 경쟁 입찰하는 계약방법
*제한경쟁계약은 일반·지명경쟁계약제도의 단점을 보완하기 위해 실적제한, 기술보유제한, 특정물품제한, 지역제한 등을 두는 계약방법
*지명경쟁계약은 계약상대자의 신용과 실적 등에 있어 적당하다고 인정하는 특정 다수의 경쟁 참가자를 지명하여 계약 상대방을 결정하는 계약방법
*수의계약은 계약상대자를 결정함에 있어 경쟁방법에 의하지 않고 특정인을 선정하여 계약하는 계약방법. 그외 일반->견적, 수의->견적, 소액수의, 납품요구, 협상에의한계약, 규격가격동시, 일반경쟁(2단계입찰), 일반경쟁(3자단가)/다수공급자계약 등으로 구분 |
| cnclDivNm | 취소구분명 | 30 | 0 | - | 조달요청의 취소구분명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListFrgcptDetail?prcrmntReqNo=R25DC00139112&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prcrmntReqNo>R25DC00139112</prcrmntReqNo> <engRprsntPrdctNm>컴퓨터서버</engRprsntPrdctNm> <cptalDivNm>LEASE</cptalDivNm> <dminsttCd>1360000</dminsttCd> <dminsttNm>기상청</dminsttNm> <totlPrdctIdntNoNum>1</totlPrdctIdntNoNum> <lonimprtNo></lonimprtNo> <asignDolAmt>59394160.6</asignDolAmt> <rcptDt>2025-12-05 11:10:40</rcptDt> <prcrmntReqChrgDeptNm>해외물자과</prcrmntReqChrgDeptNm> <prcrmntReqOfclNm>허수진</prcrmntReqOfclNm> <prcrmntReqOfclEmpno>******</prcrmntReqOfclEmpno> <brnofceNm>조달청</brnofceNm> <itemNo>0</itemNo> <reqHskNo>8471491010</reqHskNo> <prdctClsfcNo>4321150102</prdctClsfcNo> <reqNm>제안요청서 참고</reqNm> <reqQty>1</reqQty> <reqUnitNm>SETS</reqUnitNm> <prdctAccotAsignAmt>59394160.6</prdctAccotAsignAmt> <frgncptlCntrctCnclsMthdNm>일반경쟁</frgncptlCntrctCnclsMthdNm> <cnclDivNm>-</cnclDivNm> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터검색조건에 의한 조달요청 외자조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 12 | 오퍼레이션명(국문) | 나라장터검색조건에 의한 조달요청 외자조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntReqInfoListFrgcptPPSSrch |
|  | 오퍼레이션 설명 | 나라장터 검색조건(조회구분, 접수일시, 조달요청번호, 영문대표물품명, 발주기관, 조달요청번호) 등을 입력하여 조달요청번호, 발주기관, 영문대표물품명, 전체품목건수, 배정미화금액 등 외자에 대한 조달요청 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분
1.접수일시, 2.조달요청번호
나라장터화면에서 구매요청번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 접수일시범위 시작 'YYYYMMDDHHMM"(조회일시범위는 1개월 로 제한) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 접수일시범위 종료 'YYYYMMDDHHMM"(조회일시범위는 1개월 로 제한) |
| engRprsntPrdctNm | 영문대표물품명 | 100 | 0 | 컴퓨터서버 | 검색하고자하는 영문대표물품명
조회구분이 1일 경우 선택 |
| dminsttCd | 수요기관코드 | 7 | 0 | 1360000 | 검색하고자하는 수요기관코드
조회구분이 1일 경우 선택 |
| dminsttNm | 수요기관명 | 200 | 0 | 기상청 | 검색하고자하는 수요기관명
조회구분이 1일 경우 선택 |
| cptalDivCd | 자금구분코드 | 1 | 0 | 자090002 | 검색하고자하는 자금구분코드
조회구분이 1일 경우 선택
자090001:KFX
자090002:LEASE
자090003:ADB/L
자090004:IBRD/L
자090005:OECF/L |
| ofclDeptDivCd | 담당자부서코드 | 3 | 0 | 1230496 | 검색하고자하는 담당자부서코드
조회구분이 1일 경우 선택
1230496: 해외물자과 1230463: 외자기기과 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | R25DC00139112 | 검색하고자하는 조달요청번호
조회구분 2일 경우 필수
.나라장터화면에서 구매요청번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| prcrmntReqNo | 조달요청번호 | 13 | 1 | R25DC00139112 | 조달요청을 관리하기 위한 번호이며
*번호체계 :R+년도(2)+DC+순번(8) 총 13자리 구성 적용 |
| engRprsntPrdctNm | 영문대표물품명 | 100 | 0 | 컴퓨터서버 | 조달요청의 영문대표물품명 |
| dminsttCd | 수요기관코드 | 7 | 1 | 1360000 | 수요기관의 코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드가 표기됨 |
| dminsttNm | 수요기관명 | 200 | 0 | 기상청 | 중앙조달인 경우 조달사업에 관한 법률 제2조(정의)에 따라 수요물자의 구매 공급 또는 시설공사 계약의 체결을 조달청장에게 요청할 수 있도록 조달청장이 인정하여 등록한 기관 또는 자체전자조달시스템을 이용하는 기관인 경우 계약을 의뢰한 기관의 명으로 공고기관과 수요기관이 동일할 수 있음 |
| cptalDivCd | 자금구분코드 | 1 | 0 | 자090002 | 조달요청의 자금구분코드 자090001:KFX 자090002:LEASE 자090003:ADB/L 자090004:IBRD/L 자090005:OECF/L |
| cptalDivNm | 자금구분명 | 200 | 0 | LEASE | 조달요청의 자금구분명
KFX(정부보유외환) 
LEASE(LEASE 계약) 
ADB/L(아시아개발은행차관)
IBRD/L(국제부흥개발은행차관) 
OECF/L(일본해외경재협회차관) |
| totlPrdctIdntNoNum | 전체품목건수 | 25 | 0 | 1 | 조달요청의 전체품목건수 |
| asignDolAmt | 배정미화금액 | 25 | 0 | 59394160 | 조달요청의 배정미화금액(미화, 달러) |
| rcptDt | 접수일시 | 19 | 0 | 2025-12-08 10:36:17 | 담당자가 조달요청서를 접수한 일시 “YYYY-MM-DD HH:MM:SS” |
| frstyearUntyCntrctIdntNo | 초년도통합계약식별번호 | 15 | 0 | 100001218750002 | 장기계약시 초년도 통합계약식별번호 |
| inptDt | 입력일시 | 19 | 1 | 2025-12-05 11:10:40 | 입력일시 "YYYY-MM-DD HH:MM:SS" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrcrmntReqInfoService/getPrcrmntReqInfoListFrgcptPPSSrch?inqryDiv=1&inqryBgnDt=202512010000&inqryEndDt=202512312359&numOfRows=999&pageNo=1&engRprsntPrdctNm=컴퓨터서버&dminsttCd=1360000&dminsttNm=기상청&cptalDivCd=자090002&ofclDeptDivCd=1230463&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prcrmntReqNo>R25DC00139112</prcrmntReqNo> <engRprsntPrdctNm>컴퓨터서버</engRprsntPrdctNm> <dminsttCd>1360000</dminsttCd> <dminsttNm>기상청</dminsttNm> <cptalDivCd>자090002</cptalDivCd> <cptalDivNm>LEASE</cptalDivNm> <totlPrdctIdntNoNum>1</totlPrdctIdntNoNum> <asignDolAmt>59394160.6</asignDolAmt> <rcptDt>2025-12-08 10:36:17</rcptDt> <frstyearUntyCntrctIdntNo></frstyearUntyCntrctIdntNo> <inptDt>2025-12-05 11:10:40</inptDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

OPEN API 에러코드별 조치방안

| Code | 코드값 | 설명 | 조치방안 |
|---|---|---|---|
| 01 | Application Error | 제공기관 서비스 제공 상태가 원할하지 않습니다. | 서비스 제공기관의 관리자에게 문의하시기 바랍니다. |
| 02 | DB Error | 제공기관 서비스 제공 상태가 원할하지 않습니다. | 서비스 제공기관의 관리자에게 문의하시기 바랍니다. |
| 03 | No Data | 데이터 없음 에러 |  |
| 04 | HTTP Error | 제공기관 서비스 제공 상태가 원할하지 않습니다. | 서비스 제공기관의 관리자에게 문의하시기 바랍니다. |
| 05 | service time out | 제공기관 서비스 제공 상태가 원할하지 않습니다 | 서비스 제공기관의 관리자에게 문의하시기 바랍니다. |
| 06 | 날짜Format 에러 | 날짜 Default, Format Error | 날짜형식을 확인 하시기 바랍니다. |
| 07 | 입력범위값 초과 에러 | 요청하신 OpenAPI의 파라미터 입력값 범위가 초과 되었습니다. | 기술문서를 다시 한번 확인하여 주시기 바랍니다. |
| 08 | 필수값 입력 에러 | 요청하신 OpenAPI의 필수 파라미터가 누락되었습니다. | 기술문서를 다시 한번 확인하여 주시기 바랍니다. |
| 10 | 잘못된 요청 파라미터 에러 | OpenAPI 요청시 ServiceKey 파라미터가 없음 | -OpenAPI 요청시 ServiceKey 파라미터가 누락되었습니다. -OpenAPI 요청 URL을 확인하시기 바랍니다. |
| 11 | 필수 요청 파라미터가 없음 | 요청하신 OpenAPI의 필수 파라미터가 누락되었습니다. | 기술문서를 다시 한번 확인하시어 주시기 바랍니다. |
| 12 | 해당 오픈API 서비스가 없거나 폐기됨 | OpenAPI 호출시 URL이 잘못됨 | -제공기관 관리자에게 폐기된 서비스인지 확인바랍니다. 폐기된 서비스가 아니면 개발가이드에서 OpenAPI요청 URL을 다시 확인하시기 바랍니다. |
| 20 | 서비스 접근 거부 | 활용승인이 되지 않은 OpenAPI호출 | -OpenAPI활용신청정보의 승인상태를 확인하시기 바랍니다. -활용신청에 대해 제공기관 담당자가 확인 후 '승인'이후 부터 사용할 수 있습니다. -신청 후 2~3일 소요되고 결과는 회원가입 시 등록한 e-mail로 발송됩니다. |
| 22 | 서비스 요청 제한 횟수 초과 에러 | 일일 활용건수가 초과함(활용건수 증가 필요) | -OpenAPI활용신청정보의 서비스 상세기능별 일일 트래픽량을 확인하시기 바랍니다. -개발계정의 경우 제공기관에서 정의한 트래픽을 초과하여 활용할 수 없습니다. -운영계정의 경우 변경신청을 통해서 일일트래픽량을 변경 할 수 있습니다. |
| 30 | 등록되지 않은 서비스 키 | 잘못된 서비스키를 사용하였거나 서비스키를 URL인코딩하지 | -OpenAPI활용신청정보의 발급받은 서비스키를 다시 확인하시기 바랍니다. - 서비스키 값이 같다면 서비스키가 URL 인코등 되었는지 다시 확인하시기 바랍니다. |
| 31 | 기한 만료된 서비스 키 | OpenAPI 사용기간이 만료됨 (활용연장신청 후 사용가능) | -OpenAPI 활용신청정보의 활용기간을 확인합니다. -활용기간이 지난 서비스는 이용할 수 없으며 연장신청을 통해 승인 받은 후 다시 이용가능 합니다. |
| 32 | 등록되지 않은 도메인명 또는 IP주소 | 활용신청한 서버의 IP와 실제 OpenAPI호출한 서버가 다를 경우 | -OpenAPI 활용신청정보의 등록된 도메인명이나 IP주소를 다시 확인합니다. -IP나 도메인의 정보를 변경하기 위해 변경신청을 할 수 있습니다. |

