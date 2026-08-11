# 조달청_OpenAPI참고자료_나라장터_발주계획현황서비스_1.1

> 출처: 공공데이터포털 참고문서(docx) 자동 변환

조달청 공공데이터 개방
OpenAPI 참고자료
목 차목 차

### 1. 서비스 명세	3


### 1.1 나라장터 발주계획현황서비스	3

가. 서비스 개요	3
나. 오퍼레이션 목록	4
개정 이력

| 버 전 | 변경일 | 변경 구분 | 변경사유 |
|---|---|---|---|
| 1.0 | 2025 | 최초 개정 | 최초 개정 |
| 1.1 | 2026.07 | 일부 항목 샘플데이터 및 설명 변경 | [공통] - 업무구분코드,업무유형코드,소관구분코드,발주계획통합번호샘플데이터 및 설명 변경 -차세대 나라장터 개편기준 항목크기 및 항목구분 변경 |
| 1.1 | 2026.07 | 항목추가 | [공통-추가] -발주계획상세URL(orderPlanDtlUrl) -첨부파일존재여부(atchFileExistnceYn) [삭제] -공사관리번호URL(cnstwkMngNo) -발주차수(orderOrd) |
| 1.1 | 2026.07 | 기능추가 | 발주계획현황에 대한 첨부파일 목록 조회 |
|  |  |  |  |
|  |  |  |  |


### 1. 서비스 명세


### 1.1 나라장터 발주계획현황서비스

서비스 개요

| 서비스 정보 | 서비스 ID | OrderPlanSttusService |  |  |
|---|---|---|---|---|
|  | 서비스명(국문) | 나라장터 발주계획현황서비스 |  |  |
|  | 서비스명(영문) | OrderPlanSttusService |  |  |
|  | 서비스 설명 | 발주기관들이 나라장터에 등록한 발주계획정보를 제공하는 서비스로 각 발주기관들이 당해 회계연도에 조달할 공사, 물품, 용역, 외자에 대한 분기별 발주계획(조달대상, 예산액, 발주예정시기, 발주방법, 발주기관 주소, 연락처 등) 공고 내역를 제공하는 나라장터 발주계획현황서비스 |  |  |
| 서비스 보안 | 서비스 인증/권한 | [O] 서비스 Key [ ] 인증서 (GPKI) [] Basic (ID/PW) [ ] 없음 | [ ]WS-Security |  |
|  | 메시지 레벨 암호화 | [  ] 전자서명	[ ] 암호화	[O] 없음 |  |  |
|  | 전송 레벨 암호화 | [  ] SSL			[ O] 없음 |  |  |
| 적용 기술 수준 | 인터페이스 표준 | [  ] SOAP 1.2 (RPC-Encoded, Document Literal, Document Literal Wrapped) [ O ] REST (GET) [ ] RSS 1.0 [ ] RSS 2.0 [ ] Atom 1.0 [ ] 기타 |  |  |
|  | 교환 데이터 표준 | [ O ] XML	[ O ] JSON	[ ] MIME	[ ] MTOM |  |  |
| 서비스 URL | 개발환경 | http://apis.data.go.kr/1230000/ao/OrderPlanSttusService |  |  |
|  | 운영환경 | http://apis.data.go.kr/1230000/ao/OrderPlanSttusService |  |  |
| 서비스 WADL | 개발환경 | N/A |  |  |
|  | 운영환경 | N/A |  |  |
| 서비스 배포 정보 | 서비스 버전 | 1.0 |  |  |
|  | 서비스 시작일 | 2025-01-06 | 배포 일자 | 2025-01-06 |
|  | 서비스 이력 | N/A |  |  |
| 메시지 교환 유형 | [O] Request-Response	[ ] Publish-Subscribe [ ] Fire-and-Forgot		[ ] Notification |  |  |  |
| 메시지 로깅 수준 | 성공 | [O] Header [ ] Body | 실패 | [O] Header [O} Body |
| 사용 제약 사항 (비고) | N/A |  |  |  |
| 서비스 제공자 | 안정우 / 조달청 조달데이터관리팀 / 042-724-7685 |  |  |  |
| 데이터 갱신주기 | 수시 |  |  |  |

오퍼레이션 목록

| 일련번호 | 서비스명(국문) | 오퍼레이션명(영문) | 오퍼레이션명(국문) | 메시지명(영문) |
|---|---|---|---|---|
| 1 | 나라장터 발주계획현황 서비스 | getOrderPlanSttusListThng | 발주계획현황에 대한 물품조회 | N/A |
| 2 |  | getOrderPlanSttusListCnstwk | 발주계획현황에 대한 공사조회 | N/A |
| 3 |  | getOrderPlanSttusListServc | 발주계획현황에 대한 용역조회 | N/A |
| 4 |  | getOrderPlanSttusListFrgcpt | 발주계획현황에 대한 외자조회 | N/A |
| 5 |  | getOrderPlanSttusListThngPPSSrch | 나라장터 검색조건에 의한 발주계획현황에 대한 물품조회 | N/A |
| 6 |  | getOrderPlanSttusListCnstwkPPSSrch | 나라장터 검색조건에 의한 발주계획현황에 대한 공사조회 | N/A |
| 7 |  | getOrderPlanSttusListServcPPSSrch | 나라장터 검색조건에 의한 발주계획현황에 대한 용역조회 | N/A |
| 8 |  | getOrderPlanSttusListFrgcptPPSSrch | 나라장터 검색조건에 의한 발주계획현황에 대한 외자조회 | N/A |

[발주계획현황에 대한 물품조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 1 | 오퍼레이션명(국문) | 발주계획현황에 대한 물품조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getOrderPlanSttusListThng |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 발주년월범위, 게시일자범위, 발주기관코드, 발주기관명, 발주계획통합번호로 하여 발주년도, 발주기관, 소관기관, 계약방법, 발주도급금액, 물품분류 정보, 규격항목정보 등 물품에 대한 발주계획현황 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포탈에서 받은 인증키 | 공공데이터포탈에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 1. 발주년월, 게시일시 2. 발주계획통합번호 |
| orderBgnYm | 발주시작년월 | 6 | 0 | 202605 | 검색하고자하는 발주년도, 발주월 기준 조회시작 'YYYYMM'  *조회구분이 1인 경우 필수 |
| orderEndYm | 발주종료년월 | 6 | 0 | 202605 | 검색하고자하는 발주년도, 발주월 기준 조회종료 'YYYYMM' *조회구분이 1인 경우 필수 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202605120000 | 검색하고자하는 게시일시 기준 조회시작 'YYYYMMDDHHmm' *조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202605122359 | 검색하고자하는 게시일시 기준 조회종료 'YYYYMMDDHHmm' *조회구분이 1인 경우 필수 |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20756451 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 7140433 | 검색하고자하는 발주기관코드. 조회구분이 1인 경우 선택 |
| orderInsttNm | 발주기관명 | 200 | 0 | 광주광역시교육청 | 검색하고자하는 발주기관명. 조회구분이 1인 경우 선택 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 01 | 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 물품 | 업무구분명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 업무유형명 |
| orderYear | 발주년도 | 4 | 1 | 2026 | 발주년도 |
| orderInsttCd | 발주기관코드 | 7 | 1 | 7140433 | 발주기관코드는행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| totlmngInsttNm | 총괄기관명 | 200 | 0 | 광주광역시교육청 | 총괄기관명 |
| jrsdctnDivCd | 소관구분코드 | 7 | 0 | 소110003 | 소관구분코드
소110001 : 국가기관
소110002 : 지방자치단체
소110003 : 교육기관
소110004 : 공기업
소110005 : 준정부기관
소110006 : 기타공공기관
소110007 : 지방공기업
소110008 : 기타기관
소110009 : 정부투자기관
소110010 : 지자체 출자출연기관
소110011 : 지방의료원
소110012 : 지자체출연연구원
소110013 : 특수법인
소110014 : 기초자치단체 |
| jrsdctnDivNm | 소관구분명 | 100 | 0 | 교육기관 | 소관구분명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 광주광역시교육청 | 발주기관명 |
| orderPlanSno | 발주계획순번 | 6 | 1 | 0 | 발주계획순번 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 조달방식은 중앙조달과 자체조달로 구분됨 |
| orderMnth | 발주월 | 8 | 1 | 05 | 발주월 "MM" |
| bizNm | 사업명 | 400 | 1 | 가칭)광산고 냉난방기 구매설치 | 발주계획의 사업명 |
| cnstwkRgnNm | 공사지역명 | 100 | 0 | 광주광역시 | 발주계획의 공사지역명 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 전문 | 발주계획의 공종구분명 |
| cntrctMthdNm | 계약방법명 | 20 | 0 | 일반경쟁 | 발주계획의 계약방법명 |
| orderContrctAmt | 발주도급금액 | 30 | 0 | 902000000 | 발주도급금액(원화,원) |
| orderGovsplyMtrcst | 발주관급자재비 | 30 | 0 | 0 | 발주관급자재비(원화,원) |
| orderEtcAmt | 발주기타금액 | 30 | 0 | 0 | 발주기타금액(원화,원) |
| sumOrderAmt | 합계발주금액 | 30 | 0 | 939851000 | 합계발주금액(원화,원) |
| deptNm | 부서명 | 50 | 0 | 재정과 | 발주계획의 담당부서명 |
| ofclNm | 담당자명 | 20 | 0 | 서지원 | 발주계획의 담당자명 |
| telNo | 전화번호 | 20 | 0 | 062-380-4164 | 발주계획의 담당자 전화번호 |
| agrmntYn | 협정여부 | 1 | 0 | N | 발주계획의 협정여부 |
| usgCntnts | 용도내용 | 50 | 0 | 냉난방기 | 발주계획의 용도내용 |
| qtyCntnts | 수량내용 | 12 | 0 | 1 | 발주계획의 수량내용 |
| unit | 단위 | 30 | 0 | 식 | 발주계획의 수량단위 |
| prdctClsfcNo | 물품분류번호 | 11 | 0 | 40101806 | 품명에 대한 분류번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 4010180601 | 품명에 대한 분류번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 열펌프 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| ntceNticeYn | 공고게시여부 | 1 | 1 | Y | 발주계획의 공고게시여부 |
| sumOrderDolAmt | 합계발주미화금액 | 16 | 0 | 0 | 발주계획의 합계발주미화금액(달러) |
| rcritRgstNo | 모집등록번호 | 12 | 0 | 201606271388 | 발주계획의 모집등록번호(외자규격모집공고번호) |
| specItemNm1 | 규격항목명1 | 200 | 0 | Ultra Performance Liquid Chromatography System | 발주계획의 규격항목명1 |
| specItemNm2 | 규격항목명2 | 200 | 0 | Photodiode Array Detector | 발주계획의 규격항목명2 |
| specItemNm3 | 규격항목명3 | 200 | 0 | Chromatography manager software | 발주계획의 규격항목명3 |
| specItemNm4 | 규격항목명4 | 200 | 0 | System Suitability Option Software | 발주계획의 규격항목명4 |
| specItemNm5 | 규격항목명5 | 200 | 0 | Accessories | 발주계획의 규격항목명5 |
| specItemCntnts1 | 규격항목내용1 | 200 | 0 | Type : Binary Precise, High pressure blending | 발주계획의 규격항목내용1 |
| specItemCntnts2 | 규격항목내용2 | 200 | 0 | Number of Sample Plates : 96 vial or morer | 발주계획의 규격항목내용2 |
| specItemCntnts3 | 규격항목내용3 | 200 | 0 | Column Temperature Control : 5.0°C above ambient to 90.0°C or wider | 발주계획의 규격항목내용3 |
| specItemCntnts4 | 규격항목내용4 | 200 | 0 | Wavelength Range : 190~800 nm or wider | 발주계획의 규격항목내용4 |
| specItemCntnts5 | 규격항목내용5 | 200 | 0 | Full 64 bit OS System &#8211; Win 7 pro | 발주계획의 규격항목내용5 |
| bdgtDivCd | 예산구분코드 | 25 | 0 | 9027200000 | 발주계획의 예산구분코드 |
| cnstwkPrdCntnts | 공사기간내용 | 30 | 0 | 202608 | 발주계획의 공사기간내용 |
| nticeDt | 게시일시 | 19 | 0 | 2026-05-12 11:19:03 | 발주계획의 게시일시 "YYYY-MM-DD HH:MM:SS" |
| orderThtmContrctAmt | 발주금차도급금액 | 25 | 0 | 0.0 | 발주계획의 발주금차도급금액(원화,원) |
| orderNtntrsAuxAmt | 발주국고보조금액 | 25 | 0 | 0.0 | 발주계획의 발주국고보조금액(원화,원) |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 전기히트펌프 | 세부품명번호 10자리에 대한 한글명 |
| specCntnts | 규격내용 | 100 | 0 | 시방서 참조 | 발주계획의 규격내용 |
| dsgnDocRdngPlceNm | 설계문서열람장소명 | 100 | 0 | 시방서 참조 | 발주계획의 설계서열람장소명 |
| dsgnDocRdngPrdCntnts | 설계문서열람기간내용 | 100 | 0 | 2026.11~2026.11 | 발주계획의 설계서열람기간내용 |
| rmrkCntnts | 비고내용 | 4000 | 0 | 설계금액 미확정 및 인허가 지연 등으로 건설일정 등은 변경될 수 있음 / 설계서 열람기간은 현장설명일의 결정에 따라 변경될 수 있음 | 발주계획의 비고내용(담당부서 정보) |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20756451 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R26BK01516555000 | 관련된 입찰공고번호 정보 "입찰공고번호1,입찰공고번호2" |
| chgDt | 변경일시 | 19 | 0 | 2026-05-12 11:19:32 | 발주계획의 변경일시 "YYYY-MM-DD HH:MM:SS" |
| orderPlanDtlUrl | 발주계획상세URL | 512 | 0 | https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20756451 | 나라장터시스템의 발주계획상세화면 링크URL |
| atchFileExistnceYn | 첨부파일존재여부 | 1 | 0 | Y | 첨부파일 존재여부 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/OrderPlanSttusService/getOrderPlanSttusListThng?inqryDiv=2&orderPlanUntyNo=R26DD20756451&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivCd>01</bsnsDivCd> <bsnsDivNm>물품</bsnsDivNm> <bsnsTyCd>신120001</bsnsTyCd> <bsnsTyNm>신규(단기)</bsnsTyNm> <orderYear>2026</orderYear> <orderInsttCd>7140433</orderInsttCd> <totlmngInsttNm></totlmngInsttNm> <jrsdctnDivCd>소110003</jrsdctnDivCd> <jrsdctnDivNm>교육기관</jrsdctnDivNm> <orderInsttNm>광주광역시교육청</orderInsttNm> <orderPlanSno>0</orderPlanSno> <prcrmntMethd>자체조달</prcrmntMethd> <orderMnth>05</orderMnth> <bizNm>가칭)광산고 냉난방기 구매설치</bizNm> <cnstwkRgnNm></cnstwkRgnNm> <cnsttyDivNm>전문</cnsttyDivNm> <cntrctMthdNm>일반경쟁</cntrctMthdNm> <orderContrctAmt>902000000</orderContrctAmt> <orderGovsplyMtrcst>0</orderGovsplyMtrcst> <orderEtcAmt>0</orderEtcAmt> <sumOrderAmt>939851000</sumOrderAmt> <deptNm>재정과</deptNm> <ofclNm>서지원</ofclNm> <telNo>062-380-4164</telNo> <agrmntYn>N</agrmntYn> <usgCntnts>냉난방기</usgCntnts> <qtyCntnts>1</qtyCntnts> <unit>식</unit> <prdctClsfcNo>40101806</prdctClsfcNo> <dtilPrdctClsfcNo>4010180601</dtilPrdctClsfcNo> <prdctClsfcNoNm>열펌프</prdctClsfcNoNm> <ntceNticeYn>Y</ntceNticeYn> <sumOrderDolAmt>630730.2</sumOrderDolAmt> <rcritRgstNo></rcritRgstNo> <specItemNm1></specItemNm1> <specItemNm2></specItemNm2> <specItemNm3></specItemNm3> <specItemNm4></specItemNm4> <specItemNm5></specItemNm5> <specItemCntnts1></specItemCntnts1> <specItemCntnts2></specItemCntnts2> <specItemCntnts3></specItemCntnts3> <specItemCntnts4></specItemCntnts4> <specItemCntnts5></specItemCntnts5> <bdgtDivCd></bdgtDivCd> <cnstwkPrdCntnts></cnstwkPrdCntnts> <nticeDt>2026-05-12 11:19:03</nticeDt> <orderThtmContrctAmt>0.0</orderThtmContrctAmt> <orderNtntrsAuxAmt>0.0</orderNtntrsAuxAmt> <dtilPrdctClsfcNoNm>전기히트펌프</dtilPrdctClsfcNoNm> <specCntnts>시방서 참조</specCntnts> <dsgnDocRdngPlceNm></dsgnDocRdngPlceNm> <dsgnDocRdngPrdCntnts></dsgnDocRdngPrdCntnts> <rmrkCntnts></rmrkCntnts> <orderPlanUntyNo>R26DD20756451</orderPlanUntyNo> <bidNtceNoList>R26BK01516555000</bidNtceNoList> <chgDt>2026-05-12 11:19:32</chgDt> <orderPlanDtlUrl>https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20756451</orderPlanDtlUrl> <atchFileExistnceYn> Y</atchFileExistnceYn> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[발주계획현황에 대한 공사조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 2 | 오퍼레이션명(국문) | 발주계획현황에 대한 공사조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getOrderPlanSttusListCnstwk |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 발주년월범위, 게시일자범위, 발주기관코드, 발주기관명, 발주계획통합번호로 하여 발주년도, 발주기관, 소관기관, 계약방법, 발주도급금액, 물품분류 정보, 규격항목정보 등 공사에 대한 발주계획현황 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포탈에서 받은 인증키 | 공공데이터포탈에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 1. 발주년월, 게시일시 2. 발주계획통합번호 |
| orderBgnYm | 발주시작년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회시작 'YYYYMM'  *조회구분이 1인 경우 필수 |
| orderEndYm | 발주종료년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회종료 'YYYYMM' *조회구분이 1인 경우 필수 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202603130000 | 검색하고자하는 게시일시 기준 조회시작 'YYYYMMDDHHmm' * 조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202603132359 | 검색하고자하는 게시일시 기준 조회종료 'YYYYMMDDHHmm' * 조회구분이 1인 경우 필수 |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20688178 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 1352259 | 검색하고자하는 발주기관코드, 조회구분이 1인 경우 선택 |
| orderInsttNm | 발주기관명 | 200 | 0 | 보건복지부 | 검색하고자하는 발주기관명, 조회구분이 1인 경우 선택 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 03 | 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 공사 | 업무구분명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 업무유형명 |
| orderYear | 발주년도 | 4 | 1 | 2026 | 발주년도 |
| orderInsttCd | 발주기관코드 | 7 | 1 | 1352259 | 발주기관코드는행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| totlmngInsttNm | 총괄기관명 | 200 | 0 | 보건복지부 | 총괄기관명 |
| jrsdctnDivCd | 소관구분코드 | 7 | 0 | 소110001 | 소관구분코드
소110001 : 국가기관
소110002 : 지방자치단체
소110003 : 교육기관
소110004 : 공기업
소110005 : 준정부기관
소110006 : 기타공공기관
소110007 : 지방공기업
소110008 : 기타기관
소110009 : 정부투자기관
소110010 : 지자체 출자출연기관
소110011 : 지방의료원
소110012 : 지자체출연연구원
소110013 : 특수법인
소110014 : 기초자치단체 |
| jrsdctnDivNm | 소관구분명 | 100 | 0 | 국가기관 | 소관구분명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 보건복지부 국립부곡병원 | 발주기관명 |
| orderPlanSno | 발주계획순번 | 6 | 1 | 0 | 발주계획순번 |
| prcrmntMethd | 조달방식 | 20 | 0 | 중앙조달 | 조달방식은 중앙조달과 자체조달로 구분됨 |
| orderMnth | 발주월 | 8 | 1 | 03 | 발주월 "MM" |
| bizNm | 사업명 | 400 | 1 | 국립부곡병원 본관 외벽 판넬공사(전면부) | 발주계획의 사업명 |
| cnstwkRgnNm | 공사지역명 | 100 | 0 | 경상남도 | 발주계획의 공사지역명 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 건축 | 발주계획의 공종구분명 |
| cntrctMthdNm | 계약방법명 | 20 | 0 | 제한경쟁 | 발주계획의 계약방법명 |
| orderContrctAmt | 발주도급금액 | 30 | 0 | 486994360 | 발주도급금액(원화,원) |
| orderGovsplyMtrcst | 발주관급자재비 | 30 | 0 | 0 | 발주관급자재비(원화,원) |
| orderEtcAmt | 발주기타금액 | 30 | 0 | 0 | 발주기타금액(원화,원) |
| sumOrderAmt | 합계발주금액 | 30 | 0 | 486994360 | 합계발주금액(원화,원) |
| deptNm | 부서명 | 50 | 0 | 기획운영과 | 발주계획의 담당부서명 |
| ofclNm | 담당자명 | 20 | 0 | 박기숙 | 발주계획의 담당자명 |
| telNo | 전화번호 | 20 | 0 | 055-520-2517 | 발주계획의 담당자 전화번호 |
| agrmntYn | 협정여부 | 1 | 0 | N | 발주계획의 협정여부 |
| usgCntnts | 용도내용 | 50 | 0 | 정보제공 | 발주계획의 용도내용 |
| qtyCntnts | 수량내용 | 12 | 0 | 0 | 발주계획의 수량내용 |
| unit | 단위 | 30 | 0 | 개 | 발주계획의 수량단위 |
| prdctClsfcNo | 물품분류번호 | 11 | 0 | 32101617 | 품명에 대한 분류번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 3210161700 | 품명에 대한 분류번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 스마트카드 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| ntceNticeYn | 공고게시여부 | 1 | 1 | Y | 발주계획의 공고게시여부 |
| sumOrderDolAmt | 합계발주미화금액 | 16 | 0 | 0 | 발주계획의 합계발주미화금액(달러) |
| rcritRgstNo | 모집등록번호 | 12 | 0 | 111 | 발주계획의 모집등록번호(외자규격모집공고번호) |
| specItemNm1 | 규격항목명1 | 200 | 0 | Radiation Tye | 발주계획의 규격항목명1 |
| specItemNm2 | 규격항목명2 | 200 | 0 | Measuring Range | 발주계획의 규격항목명2 |
| specItemNm3 | 규격항목명3 | 200 | 0 | Overload display | 발주계획의 규격항목명3 |
| specItemNm4 | 규격항목명4 | 200 | 0 | Sensitivity | 발주계획의 규격항목명4 |
| specItemNm5 | 규격항목명5 | 200 | 0 | Energy Range | 발주계획의 규격항목명5 |
| specItemCntnts1 | 규격항목내용1 | 200 | 0 | Photon radiation | 발주계획의 규격항목내용1 |
| specItemCntnts2 | 규격항목내용2 | 200 | 0 | 0.01 uSv/h ~ 100 mSv/h | 발주계획의 규격항목내용2 |
| specItemCntnts3 | 규격항목내용3 | 200 | 0 | more than 100 mSv/h, overload indication up to 100 Sv/h | 발주계획의 규격항목내용3 |
| specItemCntnts4 | 규격항목내용4 | 200 | 0 | around 150 cps/uSv/h for photon radiation 660keV(Cs-137) | 발주계획의 규격항목내용4 |
| specItemCntnts5 | 규격항목내용5 | 200 | 0 | Starting from 30keV(count rate)  60 keV ~ 1.3MeV(dose and dose rate measurement) | 발주계획의 규격항목내용5 |
| bdgtDivCd | 예산구분코드 | 25 | 0 | 3961-300-420-03 | 발주계획의 예산구분코드 |
| cnstwkPrdCntnts | 공사기간내용 | 30 | 0 | 3961-300-420-03 | 발주계획의 공사기간내용 |
| nticeDt | 게시일시 | 19 | 0 | 2026-03-13 15:14:21 | 발주계획의 게시일시 "YYYY-MM-DD HH:MM:SS" |
| orderThtmContrctAmt | 발주금차도급금액 | 25 | 0 | 0.0 | 발주계획의 발주금차도급금액(원화,원) |
| orderNtntrsAuxAmt | 발주국고보조금액 | 25 | 0 | 0.0 | 발주계획의 발주국고보조금액(원화,원) |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 강판맨홀뚜껑 | 세부품명번호 10자리에 대한 한글명 |
| specCntnts | 규격내용 | 100 | 0 | 붙임 규격서에 의함 | 발주계획의 규격내용 |
| dsgnDocRdngPlceNm | 설계문서열람장소명 | 100 | 0 | Contruction Site | 발주계획의 설계서열람장소명 |
| dsgnDocRdngPrdCntnts | 설계문서열람기간내용 | 100 | 0 | 2026.11~2026.11 | 발주계획의 설계서열람기간내용 |
| rmrkCntnts | 비고내용 | 4000 | 0 | 설계금액 미확정 및 인허가 지연 등으로 건설일정 등은 변경될 수 있음 / 설계서 열람기간은 현장설명일의 결정에 따라 변경될 수 있음 | 발주계획의 비고내용(담당부서 정보) |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20688178 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R26BK01454051000 | 관련된 입찰공고번호 정보 "입찰공고번호1,입찰공고번호2" |
| chgDt | 변경일시 | 19 | 0 | 2026-03-13 15:14:21 | 발주계획의 변경일시 "YYYY-MM-DD HH:MM:SS" |
| orderPlanDtlUrl | 발주계획상세URL | 512 | 0 | https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20756451 | 나라장터시스템의 발주계획상세화면 링크URL |
| atchFileExistnceYn | 첨부파일존재여부 | 1 | 0 | Y | 첨부파일 존재여부 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/OrderPlanSttusService/getOrderPlanSttusListCnstwk?inqryDiv=2&orderPlanUntyNo=R26DD20688178&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivCd>07</bsnsDivCd> <bsnsDivNm>공사</bsnsDivNm> <bsnsTyCd>신120001</bsnsTyCd> <bsnsTyNm>신규(단기)</bsnsTyNm> <orderYear>2026</orderYear> <orderInsttCd>1352259</orderInsttCd> <totlmngInsttNm>보건복지부</totlmngInsttNm> <jrsdctnDivCd>소110001</jrsdctnDivCd> <jrsdctnDivNm>국가기관</jrsdctnDivNm> <orderInsttNm>보건복지부 국립부곡병원</orderInsttNm> <orderPlanSno>0</orderPlanSno> <prcrmntMethd>중앙조달</prcrmntMethd> <orderMnth>03</orderMnth> <bizNm>국립부곡병원 본관 외벽 판넬공사(전면부) </bizNm> <cnstwkRgnNm>경상남도</cnstwkRgnNm> <cnsttyDivNm>건축</cnsttyDivNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <orderContrctAmt>486994360</orderContrctAmt> <orderGovsplyMtrcst>0</orderGovsplyMtrcst> <orderEtcAmt>0</orderEtcAmt> <sumOrderAmt>486994360</sumOrderAmt> <deptNm>기획운영과</deptNm> <ofclNm>박기숙</ofclNm> <telNo>055-520-2517</telNo> <agrmntYn>N</agrmntYn> <usgCntnts></usgCntnts> <qtyCntnts>0</qtyCntnts> <unit></unit> <prdctClsfcNo></prdctClsfcNo> <dtilPrdctClsfcNo></dtilPrdctClsfcNo> <prdctClsfcNoNm></prdctClsfcNoNm> <ntceNticeYn>Y</ntceNticeYn> <sumOrderDolAmt></sumOrderDolAmt> <rcritRgstNo></rcritRgstNo> <specItemNm1></specItemNm1> <specItemNm2></specItemNm2> <specItemNm3></specItemNm3> <specItemNm4></specItemNm4> <specItemNm5></specItemNm5> <specItemCntnts1></specItemCntnts1> <specItemCntnts2></specItemCntnts2> <specItemCntnts3></specItemCntnts3> <specItemCntnts4></specItemCntnts4> <specItemCntnts5></specItemCntnts5> <bdgtDivCd>3961-300-420-03</bdgtDivCd> <cnstwkPrdCntnts></cnstwkPrdCntnts> <nticeDt>2026-03-13 15:14:21</nticeDt> <orderThtmContrctAmt>0.0</orderThtmContrctAmt> <orderNtntrsAuxAmt>0.0</orderNtntrsAuxAmt> <dtilPrdctClsfcNoNm></dtilPrdctClsfcNoNm> <specCntnts></specCntnts> <dsgnDocRdngPlceNm></dsgnDocRdngPlceNm> <dsgnDocRdngPrdCntnts></dsgnDocRdngPrdCntnts> <rmrkCntnts></rmrkCntnts> <orderPlanUntyNo>R26DD20688178</orderPlanUntyNo> <bidNtceNoList>R26BK01454051000</bidNtceNoList> <chgDt></chgDt> <orderPlanDtlUrl>https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688178</orderPlanDtlUrl> <atchFileExistnceYn> Y</atchFileExistnceYn> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[발주계획현황에 대한 용역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 3 | 오퍼레이션명(국문) | 발주계획현황에 대한 용역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getOrderPlanSttusListServc |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 발주년월범위, 게시일자범위, 발주기관코드, 발주기관명으로 하여 발주년도, 발주기관, 소관기관, 계약방법, 발주도급금액, 물품분류 정보, 규격항목정보 등 용역에 대한 발주계획현황 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포탈에서 받은 인증키 | 공공데이터포탈에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 1. 발주년월, 게시일시 2. 발주계획통합번호 |
| orderBgnYm | 발주시작년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회시작 'YYYYMM'  *조회구분이 1인 경우 필수 |
| orderEndYm | 발주종료년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회종료 'YYYYMM' *조회구분이 1인 경우 필수 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202603130000 | 검색하고자하는 게시일시 기준 조회시작'YYYYMMDDHHmm', * 조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202603142359 | 검색하고자하는 게시일시 기준 조회종료 'YYYYMMDDHHmm', * 조회구분이 1인 경우 필수 |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20688643 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 6260000 | 검색하고자하는 발주기관코드, 조회구분이 1인 경우 선택 |
| orderInsttNm | 발주기관명 | 200 | 0 | 부산광역시 | 검색하고자하는 발주기관명, 조회구분이 1인 경우 선택 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 03 | 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 일반용역 | 업무구분명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 업무유형명 |
| orderYear | 발주년도 | 4 | 1 | 2026 | 발주년도 |
| orderInsttCd | 발주기관코드 | 7 | 1 | 6260000 | 발주기관코드는 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| totlmngInsttNm | 총괄기관명 | 200 | 0 | 부산광역시 | 총괄기관명 |
| jrsdctnDivCd | 소관구분코드 | 7 | 0 | 소110002 | 소관구분코드
소110001 : 국가기관
소110002 : 지방자치단체
소110003 : 교육기관
소110004 : 공기업
소110005 : 준정부기관
소110006 : 기타공공기관
소110007 : 지방공기업
소110008 : 기타기관
소110009 : 정부투자기관
소110010 : 지자체 출자출연기관
소110011 : 지방의료원
소110012 : 지자체출연연구원
소110013 : 특수법인
소110014 : 기초자치단체 |
| jrsdctnDivNm | 소관구분명 | 100 | 0 | 지방자치단체 | 소관구분명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 부산광역시 | 발주기관명 |
| orderPlanSno | 발주계획순번 | 6 | 1 | 0 | 발주계획순번 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 조달방식은 중앙조달과 자체조달로 구분됨 |
| orderMnth | 발주월 | 8 | 1 | 03 | 발주월 "MM" |
| bizNm | 사업명 | 400 | 1 | 2026 피란수도 부산 국가유산 야행 기획 운영 대행 용역 | 발주계획의 사업명 |
| cnstwkRgnNm | 공사지역명 | 100 | 0 | 경상북도 | 발주계획의 공사지역명 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 전문 | 발주계획의 공종구분명 |
| cntrctMthdNm | 계약방법명 | 20 | 0 | 제한경쟁 | 발주계획의 계약방법명 |
| orderContrctAmt | 발주도급금액 | 30 | 0 | 0 | 발주도급금액(원화,원) |
| orderGovsplyMtrcst | 발주관급자재비 | 30 | 0 | 0 | 발주관급자재비(원화,원) |
| orderEtcAmt | 발주기타금액 | 30 | 0 | 0 | 발주기타금액(원화,원) |
| sumOrderAmt | 합계발주금액 | 30 | 0 | 582500000 | 합계발주금액(원화,원) |
| deptNm | 부서명 | 50 | 0 | 문화유산과 | 발주계획의 담당부서명 |
| ofclNm | 담당자명 | 20 | 0 | 이은정 | 발주계획의 담당자명 |
| telNo | 전화번호 | 20 | 0 | 051-888-5092 | 발주계획의 담당자전화번호 |
| agrmntYn | 협정여부 | 1 | 0 | N | 발주계획의 협정여부 |
| usgCntnts | 용도내용 | 50 | 0 | 실험실안전장비 | 발주계획의 용도내용 |
| qtyCntnts | 수량내용 | 12 | 0 | 0 | 발주계획의 수량내용 |
| unit | 단위 | 30 | 0 | set | 발주계획의 수량단위 |
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 41103502 | 품명에 대한 분류번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 4110350201 | 품명에 대한 분류번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 실험실용배기기 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| ntceNticeYn | 공고게시여부 | 1 | 1 | Y | 발주계획의 공고게시여부 |
| sumOrderDolAmt | 합계발주미화금액 | 16 | 0 | 0 | 발주계획의 합계발주미화금액(달러) |
| rcritRgstNo | 모집등록번호 | 12 | 0 | 201606271388 | 발주계획의 모집등록번호(외자규격모집공고번호) |
| specItemNm1 | 규격항목명1 | 200 | 0 | Ultra Performance Liquid Chromatography System | 발주계획의 규격항목명1 |
| specItemNm2 | 규격항목명2 | 200 | 0 | Photodiode Array Detector | 발주계획의 규격항목명2 |
| specItemNm3 | 규격항목명3 | 200 | 0 | Chromatography manager software | 발주계획의 규격항목명3 |
| specItemNm4 | 규격항목명4 | 200 | 0 | System Suitability Option Software | 발주계획의 규격항목명4 |
| specItemNm5 | 규격항목명5 | 200 | 0 | Accessories | 발주계획의 규격항목명5 |
| specItemCntnts1 | 규격항목내용1 | 200 | 0 | Type : Binary Precise, High pressure blending | 발주계획의 규격항목내용1 |
| specItemCntnts2 | 규격항목내용2 | 200 | 0 | Number of Sample Plates : 96 vial or morer | 발주계획의 규격항목내용2 |
| specItemCntnts3 | 규격항목내용3 | 200 | 0 | Column Temperature Control : 5.0°C above ambient to 90.0°C or wider | 발주계획의 규격항목내용3 |
| specItemCntnts4 | 규격항목내용4 | 200 | 0 | Wavelength Range : 190~800 nm or wider | 발주계획의 규격항목내용4 |
| specItemCntnts5 | 규격항목내용5 | 200 | 0 | Full 64 bit OS System &#8211; Win 7 pro | 발주계획의 규격항목내용5 |
| bdgtDivCd | 예산구분코드 | 25 | 0 | 9027200000 | 발주계획의 예산구분코드 |
| cnstwkPrdCntnts | 공사기간내용 | 30 | 0 | 201608 | 발주계획의 공사기간내용 |
| nticeDt | 게시일시 | 19 | 0 | 2026-03-13 17:35:16 | 발주계획의 게시일시 "YYYY-MM-DD HH:MM:SS" |
| orderThtmContrctAmt | 발주금차도급금액 | 25 | 0 | 0.0 | 발주계획의 발주금차도급금액(원화,원) |
| orderNtntrsAuxAmt | 발주국고보조금액 | 25 | 0 | 0.0 | 발주계획의 발주국고보조금액(원화,원) |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 강판맨홀뚜껑 | 세부품명번호 10자리에 대한 한글명 |
| specCntnts | 규격내용 | 100 | 0 | 붙임 규격서에 의함 | 발주계획의 규격내용 |
| dsgnDocRdngPlceNm | 설계문서열람장소명 | 100 | 0 | Contruction Site | 발주계획의 설계서열람장소명 |
| dsgnDocRdngPrdCntnts | 설계문서열람기간내용 | 100 | 0 | 2016.11~2016.11 | 발주계획의 설계서열람기간내용 |
| rmrkCntnts | 비고내용 | 4000 | 0 | 설계금액 미확정 및 인허가 지연 등으로 건설일정 등은 변경될 수 있음 / 설계서 열람기간은 현장설명일의 결정에 따라 변경될 수 있음 | 발주계획의 비고내용(담당부서 정보) |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20688643 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R26BK01412607000 | 관련된 입찰공고번호 정보 "입찰공고번호1,입찰공고번호2" |
| chgDt | 변경일시 | 19 | 0 | 2026-03-13 17:35:16 | 발주계획의 변경일시 "YYYY-MM-DD HH:MM:SS" |
| orderPlanDtlUrl | 발주계획상세URL | 512 | 0 | https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688643 | 나라장터시스템의 발주계획상세화면 링크URL |
| atchFileExistnceYn | 첨부파일존재여부 | 1 | 0 | Y | 첨부파일 존재여부 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/OrderPlanSttusService/getOrderPlanSttusListServc?inqryDiv=2&numOfRows=10&pageNo=1&orderPlanUntyNo=R26DD20688643&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivCd>03</bsnsDivCd> <bsnsDivNm>일반용역</bsnsDivNm> <bsnsTyCd>신120001</bsnsTyCd> <bsnsTyNm>신규(단기)</bsnsTyNm> <orderYear>2026</orderYear> <orderInsttCd>6260000</orderInsttCd> <totlmngInsttNm>부산광역시</totlmngInsttNm> <jrsdctnDivCd>소110002</jrsdctnDivCd> <jrsdctnDivNm>지방자치단체</jrsdctnDivNm> <orderInsttNm>부산광역시</orderInsttNm> <orderPlanSno>0</orderPlanSno> <prcrmntMethd>자체조달</prcrmntMethd> <orderMnth>03</orderMnth> <bizNm>2026 피란수도 부산 국가유산 야행 기획?운영 대행 용역</bizNm> <cnstwkRgnNm></cnstwkRgnNm> <cnsttyDivNm></cnsttyDivNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <orderContrctAmt>0</orderContrctAmt> <orderGovsplyMtrcst>0</orderGovsplyMtrcst> <orderEtcAmt>0</orderEtcAmt> <sumOrderAmt>582500000</sumOrderAmt> <deptNm>문화유산과</deptNm> <ofclNm>이은정</ofclNm> <telNo>051-888-5092</telNo> <agrmntYn>N</agrmntYn> <usgCntnts></usgCntnts> <qtyCntnts>0</qtyCntnts> <unit></unit> <prdctClsfcNo></prdctClsfcNo> <dtilPrdctClsfcNo></dtilPrdctClsfcNo> <prdctClsfcNoNm></prdctClsfcNoNm> <ntceNticeYn>Y</ntceNticeYn> <sumOrderDolAmt></sumOrderDolAmt> <rcritRgstNo></rcritRgstNo> <specItemNm1></specItemNm1> <specItemNm2></specItemNm2> <specItemNm3></specItemNm3> <specItemNm4></specItemNm4> <specItemNm5></specItemNm5> <specItemCntnts1></specItemCntnts1> <specItemCntnts2></specItemCntnts2> <specItemCntnts3></specItemCntnts3> <specItemCntnts4></specItemCntnts4> <specItemCntnts5></specItemCntnts5> <bdgtDivCd></bdgtDivCd> <cnstwkPrdCntnts></cnstwkPrdCntnts> <nticeDt>2026-03-13 17:35:16</nticeDt> <orderThtmContrctAmt>0.0</orderThtmContrctAmt> <orderNtntrsAuxAmt>0.0</orderNtntrsAuxAmt> <dtilPrdctClsfcNoNm></dtilPrdctClsfcNoNm> <specCntnts></specCntnts> <dsgnDocRdngPlceNm></dsgnDocRdngPlceNm> <dsgnDocRdngPrdCntnts></dsgnDocRdngPrdCntnts> <rmrkCntnts></rmrkCntnts> <orderPlanUntyNo>R26DD20688643</orderPlanUntyNo> <bidNtceNoList>R26BK01412607000</bidNtceNoList> <chgDt></chgDt> <orderPlanDtlUrl>https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688643</orderPlanDtlUrl> <atchFileExistnceYn> Y</atchFileExistnceYn> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[발주계획현황에 대한 외자조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 4 | 오퍼레이션명(국문) | 발주계획현황에 대한 외자조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getOrderPlanSttusListFrgcpt |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 발주년월범위, 게시일자범위, 발주기관코드, 발주기관명으로 하여 발주년도, 발주기관, 소관기관, 계약방법, 발주도급금액, 물품분류 정보, 규격항목정보 등 외자에 대한 발주계획현황 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포탈에서 받은 인증키 | 공공데이터포탈에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분, 1. 발주년월, 게시일시, 2. 발주계획통합번호 |
| orderBgnYm | 발주시작년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회시작 'YYYYMM'  *조회구분이 1인 경우 필수 |
| orderEndYm | 발주종료년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회종료 'YYYYMM' *조회구분이 1인 경우 필수 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202603130000 | 검색하고자하는 게시일시 기준 조회시작 'YYYYMMDDHHmm', * 조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202603142359 | 검색하고자하는 게시일시 기준 조회종료 'YYYYMMDDHHmm', * 조회구분이 1인 경우 필수 |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20688240 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| orderInsttCd | 발주기관코드 | 7 | 0 | B551364 | 검색하고자하는 발주기관코드, 조회구분이 1인 경우 선택 |
| orderInsttNm | 발주기관명 | 200 | 0 | 한국기초과학지원연구원 | 검색하고자하는 발주기관명, 조회구분이 1인 경우 선택 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 02 | 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 외자 | 업무구분명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 업무유형명 |
| orderYear | 발주년도 | 4 | 1 | 2026 | 발주년도 |
| orderInsttCd | 발주기관코드 | 7 | 1 | B551364 | 발주기관코드는 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| totlmngInsttNm | 총괄기관명 | 200 | 0 | 한국기초과학지원연구원 | 총괄기관명 |
| jrsdctnDivCd | 소관구분코드 | 7 | 0 | 소110008 | 소관구분코드
소110001 : 국가기관
소110002 : 지방자치단체
소110003 : 교육기관
소110004 : 공기업
소110005 : 준정부기관
소110006 : 기타공공기관
소110007 : 지방공기업
소110008 : 기타기관
소110009 : 정부투자기관
소110010 : 지자체 출자출연기관
소110011 : 지방의료원
소110012 : 지자체출연연구원
소110013 : 특수법인
소110014 : 기초자치단체 |
| jrsdctnDivNm | 소관구분명 | 100 | 0 | 기타기관 | 소관구분명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 한국기초과학지원연구원 | 발주기관명 |
| orderPlanSno | 발주계획순번 | 6 | 1 | 0 | 발주계획순번 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 조달방식은 중앙조달과 자체조달로 구분됨 |
| orderMnth | 발주월 | 8 | 1 | 03 | 발주월 "MM" |
| bizNm | 사업명 | 400 | 1 | 소동물용단층촬영시스템 [In Vivo Xray Radiography MicroCT System] | 발주계획의 사업명 |
| cnstwkRgnNm | 공사지역명 | 100 | 0 | 경상북도 | 발주계획의 공사지역명 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 전문 | 발주계획의 공종구분명 |
| cntrctMthdNm | 계약방법명 | 20 | 0 | 일반경쟁 | 발주계획의 계약방법명 |
| orderContrctAmt | 발주도급금액 | 30 | 0 | 0 | 발주도급금액(원화,원) |
| orderGovsplyMtrcst | 발주관급자재비 | 30 | 0 | 0 | 발주관급자재비(원화,원) |
| orderEtcAmt | 발주기타금액 | 30 | 0 | 0 | 발주기타금액(원화,원) |
| sumOrderAmt | 합계발주금액 | 30 | 0 | 687000000 | 합계발주금액(원화,원) |
| deptNm | 부서명 | 50 | 0 | 구매자산실 | 발주계획의 담당부서명 |
| ofclNm | 담당자명 | 20 | 0 | 성희수 | 발주계획의 담당자명 |
| telNo | 전화번호 | 20 | 0 | 042-865-2582 | 발주계획의 담당자전화번호 |
| agrmntYn | 협정여부 | 1 | 0 | N | 발주계획의 협정여부 |
| usgCntnts | 용도내용 | 50 | 0 | 학술 연구용 | 발주계획의 용도내용 |
| qtyCntnts | 수량내용 | 12 | 0 | 1 | 발주계획의 수량내용 |
| unit | 단위 | 30 | 0 | SET | 발주계획의 수량단위 |
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 99422015 | 품명에 대한 분류번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 9942201501 | 품명에 대한 분류번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 융복합전산화단층엑스선촬영장치(CT) | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| ntceNticeYn | 공고게시여부 | 1 | 1 | Y | 발주계획의 공고게시여부 |
| sumOrderDolAmt | 합계발주미화금액 | 16 | 0 | 458000.0 | 발주계획의 합계발주미화금액(달러) |
| rcritRgstNo | 모집등록번호 | 12 | 0 | 202606271388 | 발주계획의 모집등록번호(외자규격모집공고번호) |
| specItemNm1 | 규격항목명1 | 200 | 0 | Detector type | 발주계획의 규격항목명1 |
| specItemNm2 | 규격항목명2 | 200 | 0 | Pixel number | 발주계획의 규격항목명2 |
| specItemNm3 | 규격항목명3 | 200 | 0 | Image resolution | 발주계획의 규격항목명3 |
| specItemNm4 | 규격항목명4 | 200 | 0 | Pixel size | 발주계획의 규격항목명4 |
| specItemNm5 | 규격항목명5 | 200 | 0 | Scan time | 발주계획의 규격항목명5 |
| specItemCntnts1 | 규격항목내용1 | 200 | 0 | CMOS Flat panel or better | 발주계획의 규격항목내용1 |
| specItemCntnts2 | 규격항목내용2 | 200 | 0 | 2,944 x 2,352 | 발주계획의 규격항목내용2 |
| specItemCntnts3 | 규격항목내용3 | 200 | 0 | 2864 x 2864 x 2272 (High) or better | 발주계획의 규격항목내용3 |
| specItemCntnts4 | 규격항목내용4 | 200 | 0 | 2.86 um or better | 발주계획의 규격항목내용4 |
| specItemCntnts5 | 규격항목내용5 | 200 | 0 | 3.9 sec or better | 발주계획의 규격항목내용5 |
| bdgtDivCd | 예산구분코드 | 25 | 0 | 9027200000 | 발주계획의 예산구분코드 |
| cnstwkPrdCntnts | 공사기간내용 | 30 | 0 | 202612 | 발주계획의 공사기간내용 |
| nticeDt | 게시일시 | 19 | 0 | 2026-03-13 15:35:42 | 발주계획의 게시일시 "YYYY-MM-DD HH:MM:SS" |
| orderThtmContrctAmt | 발주금차도급금액 | 25 | 0 | 0.0 | 발주계획의 발주금차도급금액(원화,원) |
| orderNtntrsAuxAmt | 발주국고보조금액 | 25 | 0 | 0.0 | 발주계획의 발주국고보조금액(원화,원) |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 융복합전산화단층엑스선촬영장치(CT) | 세부품명번호 10자리에 대한 한글명 |
| specCntnts | 규격내용 | 100 | 0 | 붙임 규격서에 의함 | 발주계획의 규격내용 |
| dsgnDocRdngPlceNm | 설계문서열람장소명 | 100 | 0 | Contruction Site | 발주계획의 설계서열람장소명 |
| dsgnDocRdngPrdCntnts | 설계문서열람기간내용 | 100 | 0 | 2016.11~2016.11 | 발주계획의 설계서열람기간내용 |
| rmrkCntnts | 비고내용 | 4000 | 0 | 설계금액 미확정 및 인허가 지연 등으로 건설일정 등은 변경될 수 있음 / 설계서 열람기간은 현장설명일의 결정에 따라 변경될 수 있음 | 발주계획의 비고내용(담당부서 정보) |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20688240 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R26BK01429023000,R26BK01483031000 | 관련된 입찰공고번호 정보 "입찰공고번호1,입찰공고번호2" |
| chgDt | 변경일시 | 19 | 0 | 2026-03-13 15:52:46 | 발주계획의 변경일시 "YYYY-MM-DD HH:MM:SS" |
| orderPlanDtlUrl | 발주계획상세URL | 512 | 0 | https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688240 | 나라장터시스템의 발주계획상세화면 링크URL |
| atchFileExistnceYn | 첨부파일존재여부 | 1 | 0 | N | 첨부파일 존재여부 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/OrderPlanSttusService/getOrderPlanSttusListFrgcpt?inqryDiv=2&numOfRows=10&pageNo=1&orderPlanUntyNo=R26DD20688240&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivCd>02</bsnsDivCd> <bsnsDivNm>외자</bsnsDivNm> <bsnsTyCd></bsnsTyCd> <bsnsTyNm></bsnsTyNm> <orderYear>2026</orderYear> <orderInsttCd>B551364</orderInsttCd> <totlmngInsttNm>한국기초과학지원연구원</totlmngInsttNm> <jrsdctnDivCd>소110008</jrsdctnDivCd> <jrsdctnDivNm>기타기관</jrsdctnDivNm> <orderInsttNm>한국기초과학지원연구원</orderInsttNm> <orderPlanSno>0</orderPlanSno> <prcrmntMethd>자체조달</prcrmntMethd> <orderMnth>03</orderMnth> <bizNm>소동물용단층촬영시스템 [In Vivo Xray Radiography MicroCT System]</bizNm> <cnstwkRgnNm></cnstwkRgnNm> <cnsttyDivNm></cnsttyDivNm> <cntrctMthdNm>일반경쟁</cntrctMthdNm> <orderContrctAmt>0</orderContrctAmt> <orderGovsplyMtrcst>0</orderGovsplyMtrcst> <orderEtcAmt>0</orderEtcAmt> <sumOrderAmt>687000000</sumOrderAmt> <deptNm>구매자산실</deptNm> <ofclNm>성희수</ofclNm> <telNo>042-865-2582</telNo> <agrmntYn>N</agrmntYn> <usgCntnts>학술 연구용</usgCntnts> <qtyCntnts>1</qtyCntnts> <unit>SET</unit> <prdctClsfcNo>99422015</prdctClsfcNo> <dtilPrdctClsfcNo>9942201501</dtilPrdctClsfcNo> <prdctClsfcNoNm></prdctClsfcNoNm> <ntceNticeYn>Y</ntceNticeYn> <sumOrderDolAmt>458000.0</sumOrderDolAmt> <rcritRgstNo></rcritRgstNo> <specItemNm1>Detector type</specItemNm1> <specItemNm2>Pixel number</specItemNm2> <specItemNm3>Image resolution</specItemNm3> <specItemNm4>Pixel size</specItemNm4> <specItemNm5>Scan time</specItemNm5> <specItemCntnts1>CMOS Flat panel or better</specItemCntnts1> <specItemCntnts2>2,944 x 2,352</specItemCntnts2> <specItemCntnts3>2864 x 2864 x 2272 (High) or better</specItemCntnts3> <specItemCntnts4>2.86 um or better</specItemCntnts4> <specItemCntnts5>3.9 sec or better</specItemCntnts5> <bdgtDivCd></bdgtDivCd> <cnstwkPrdCntnts>202612</cnstwkPrdCntnts> <nticeDt>2026-03-13 15:35:42</nticeDt> <orderThtmContrctAmt>0.0</orderThtmContrctAmt> <orderNtntrsAuxAmt>0.0</orderNtntrsAuxAmt> <dtilPrdctClsfcNoNm>융복합전산화단층엑스선촬영장치(CT)</dtilPrdctClsfcNoNm> <specCntnts></specCntnts> <dsgnDocRdngPlceNm></dsgnDocRdngPlceNm> <dsgnDocRdngPrdCntnts></dsgnDocRdngPrdCntnts> <rmrkCntnts></rmrkCntnts> <orderPlanUntyNo>R26DD20688240</orderPlanUntyNo> <bidNtceNoList>R26BK01429023000,R26BK01483031000</bidNtceNoList> <chgDt>2026-03-13 15:52:46</chgDt> <orderPlanDtlUrl>https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688240</orderPlanDtlUrl> <atchFileExistnceYn>N</atchFileExistnceYn> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 발주계획현황에 대한 물품조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 5 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 발주계획현황에 대한 물품조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getOrderPlanSttusListThngPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 발주시작년월, 발주종료년월, 게시일시, 발주기관코드, 발주기관명, 협정여부, 조달방식, 기관소재지, 세부품명번호, 사업명으로 하여 발주년도, 발주기관, 소관기관, 계약방법, 발주도급금액, 물품분류 정보, 규격항목정보 등 물품에 대한 발주계획현황 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 0 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 0 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포탈에서 받은 인증키 | 공공데이터포탈에서 받은 인증키 |
| type | 타입 | 4 | 0 | Json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| orderBgnYm | 발주시작년월 | 6 | 0 | 202605 | 검색하고자하는 발주년도, 발주월 기준 조회시작 'YYYYMM'
* 입력값이 없을 경우 현재일로부터 한달기준 조회 |
| orderEndYm | 발주종료년월 | 6 | 0 | 202605 | 검색하고자하는 발주년도, 발주월 기준 조회종료 'YYYYMM'
* 입력값이 없을 경우 현재일로부터 한달기준 조회 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202605120000 | 검색하고자하는 게시일시 기준 조회시작
'YYYYMMDDHHmm'
* 입력값이 없을 경우 현재일로 부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202605122359 | 검색하고자하는 게시일시 기준 조회종료 'YYYYMMDDHHmm'
* 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 7000126 | 검색하고자하는 발주기관코드 |
| orderInsttNm | 발주기관명 | 200 | 0 | 광주광역시교육청 | 검색하고자하는 발주기관명 |
| agrmntYn | 협정여부 | 1 | 0 | N | 검색하고자하는 발주계획 협정여부 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 검색하고자하는 조달방식 (중앙조달, 자체조달) |
| insttLctNm | 기관소재지명 | 100 | 0 | 전남광주통합특별시 | 검색하고자 하는 기관의 소재지 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 4010180601 | 검색하고자하는 품명 분류번호 |
| bizNm | 사업명 | 400 | 0 | 가칭)광산고 냉난방기 구매설치 | 검색하고자하는 발주계획의 사업명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 01 | 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 물품 | 업무구분명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 업무유형명 |
| orderYear | 발주년도 | 4 | 1 | 2026 | 발주년도 |
| orderInsttCd | 발주기관코드 | 7 | 1 | 7140433 | 발주기관코드는행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| totlmngInsttNm | 총괄기관명 | 200 | 0 | 광주광역시교육청 | 총괄기관명 |
| jrsdctnDivCd | 소관구분코드 | 7 | 0 | 소110003 | 소관구분코드
소110001 : 국가기관
소110002 : 지방자치단체
소110003 : 교육기관
소110004 : 공기업
소110005 : 준정부기관
소110006 : 기타공공기관
소110007 : 지방공기업
소110008 : 기타기관
소110009 : 정부투자기관
소110010 : 지자체 출자출연기관
소110011 : 지방의료원
소110012 : 지자체출연연구원
소110013 : 특수법인
소110014 : 기초자치단체 |
| jrsdctnDivNm | 소관구분명 | 100 | 0 | 교육기관 | 소관구분명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 광주광역시교육청 | 발주기관명 |
| orderPlanSno | 발주계획순번 | 6 | 1 | 0 | 발주계획순번 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 조달방식은 중앙조달과 자체조달로 구분됨 |
| orderMnth | 발주월 | 8 | 1 | 05 | 발주월 "MM" |
| bizNm | 사업명 | 400 | 1 | 가칭)광산고 냉난방기 구매설치 | 발주계획의 사업명 |
| cnstwkRgnNm | 공사지역명 | 100 | 0 | 광주광역시 | 발주계획의 공사지역명 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 전문 | 발주계획의 공종구분명 |
| cntrctMthdNm | 계약방법명 | 20 | 0 | 일반경쟁 | 발주계획의 계약방법명 |
| orderContrctAmt | 발주도급금액 | 30 | 0 | 902000000 | 발주도급금액(원화,원) |
| orderGovsplyMtrcst | 발주관급자재비 | 30 | 0 | 0 | 발주관급자재비(원화,원) |
| orderEtcAmt | 발주기타금액 | 30 | 0 | 0 | 발주기타금액(원화,원) |
| sumOrderAmt | 합계발주금액 | 30 | 0 | 939851000 | 합계발주금액(원화,원) |
| deptNm | 부서명 | 50 | 0 | 재정과 | 발주계획의 담당부서명 |
| ofclNm | 담당자명 | 20 | 0 | 서지원 | 발주계획의 담당자명 |
| telNo | 전화번호 | 20 | 0 | 062-380-4164 | 발주계획의 담당자 전화번호 |
| agrmntYn | 협정여부 | 1 | 0 | N | 발주계획의 협정여부 |
| usgCntnts | 용도내용 | 50 | 0 | 냉난방기 | 발주계획의 용도내용 |
| qtyCntnts | 수량내용 | 12 | 0 | 1 | 발주계획의 수량내용 |
| unit | 단위 | 30 | 0 | 식 | 발주계획의 수량단위 |
| prdctClsfcNo | 물품분류번호 | 11 | 0 | 40101806 | 품명에 대한 분류번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 4010180601 | 품명에 대한 분류번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 열펌프 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| ntceNticeYn | 공고게시여부 | 1 | 1 | Y | 발주계획의 공고게시여부 |
| sumOrderDolAmt | 합계발주미화금액 | 16 | 0 | 0 | 발주계획의 합계발주미화금액(달러) |
| rcritRgstNo | 모집등록번호 | 12 | 0 | 201606271388 | 발주계획의 모집등록번호(외자규격모집공고번호) |
| specItemNm1 | 규격항목명1 | 200 | 0 | Ultra Performance Liquid Chromatography System | 발주계획의 규격항목명1 |
| specItemNm2 | 규격항목명2 | 200 | 0 | Photodiode Array Detector | 발주계획의 규격항목명2 |
| specItemNm3 | 규격항목명3 | 200 | 0 | Chromatography manager software | 발주계획의 규격항목명3 |
| specItemNm4 | 규격항목명4 | 200 | 0 | System Suitability Option Software | 발주계획의 규격항목명4 |
| specItemNm5 | 규격항목명5 | 200 | 0 | Accessories | 발주계획의 규격항목명5 |
| specItemCntnts1 | 규격항목내용1 | 200 | 0 | Type : Binary Precise, High pressure blending | 발주계획의 규격항목내용1 |
| specItemCntnts2 | 규격항목내용2 | 200 | 0 | Number of Sample Plates : 96 vial or morer | 발주계획의 규격항목내용2 |
| specItemCntnts3 | 규격항목내용3 | 200 | 0 | Column Temperature Control : 5.0°C above ambient to 90.0°C or wider | 발주계획의 규격항목내용3 |
| specItemCntnts4 | 규격항목내용4 | 200 | 0 | Wavelength Range : 190~800 nm or wider | 발주계획의 규격항목내용4 |
| specItemCntnts5 | 규격항목내용5 | 200 | 0 | Full 64 bit OS System &#8211; Win 7 pro | 발주계획의 규격항목내용5 |
| bdgtDivCd | 예산구분코드 | 25 | 0 | 9027200000 | 발주계획의 예산구분코드 |
| cnstwkPrdCntnts | 공사기간내용 | 30 | 0 | 202608 | 발주계획의 공사기간내용 |
| nticeDt | 게시일시 | 19 | 0 | 2026-05-12 11:19:03 | 발주계획의 게시일시 "YYYY-MM-DD HH:MM:SS" |
| orderThtmContrctAmt | 발주금차도급금액 | 25 | 0 | 0.0 | 발주계획의 발주금차도급금액(원화,원) |
| orderNtntrsAuxAmt | 발주국고보조금액 | 25 | 0 | 0.0 | 발주계획의 발주국고보조금액(원화,원) |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 전기히트펌프 | 세부품명번호 10자리에 대한 한글명 |
| specCntnts | 규격내용 | 100 | 0 | 시방서 참조 | 발주계획의 규격내용 |
| dsgnDocRdngPlceNm | 설계문서열람장소명 | 100 | 0 | 시방서 참조 | 발주계획의 설계서열람장소명 |
| dsgnDocRdngPrdCntnts | 설계문서열람기간내용 | 100 | 0 | 2026.11~2026.11 | 발주계획의 설계서열람기간내용 |
| rmrkCntnts | 비고내용 | 4000 | 0 | 설계금액 미확정 및 인허가 지연 등으로 건설일정 등은 변경될 수 있음 / 설계서 열람기간은 현장설명일의 결정에 따라 변경될 수 있음 | 발주계획의 비고내용(담당부서 정보) |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20756451 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R26BK01516555000 | 관련된 입찰공고번호 정보 "입찰공고번호1,입찰공고번호2" |
| chgDt | 변경일시 | 19 | 0 | 2026-05-12 11:19:32 | 발주계획의 변경일시 "YYYY-MM-DD HH:MM:SS" |
| orderPlanDtlUrl | 발주계획상세URL | 512 | 0 | https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20756451 | 나라장터시스템의 발주계획상세화면 링크URL |
| atchFileExistnceYn | 첨부파일존재여부 | 1 | 0 | Y | 첨부파일 존재여부 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/OrderPlanSttusService/getOrderPlanSttusListThngPPSSrch? inqryBgnDt=202605120000&inqryEndDt=202605122359&numOfRows=10&pageNo=1&orderBgnYm=202605&orderEndYm=202605&orderInsttCd=7140433&orderInsttNm=광주광역시교육청&agrmntYn=N&prcrmntMethd=자체조달&insttLctNm=전남광주통합특별시&dtilPrdctClsfcNo=4010180601&bizNm=가칭)광산고 냉난방기 구매설치&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivCd>01</bsnsDivCd> <bsnsDivNm>물품</bsnsDivNm> <bsnsTyCd>신120001</bsnsTyCd> <bsnsTyNm>신규(단기)</bsnsTyNm> <orderYear>2026</orderYear> <orderInsttCd>7140433</orderInsttCd> <totlmngInsttNm></totlmngInsttNm> <jrsdctnDivCd>소110003</jrsdctnDivCd> <jrsdctnDivNm>교육기관</jrsdctnDivNm> <orderInsttNm>광주광역시교육청</orderInsttNm> <orderPlanSno>0</orderPlanSno> <prcrmntMethd>자체조달</prcrmntMethd> <orderMnth>05</orderMnth> <bizNm>가칭)광산고 냉난방기 구매설치</bizNm> <cnstwkRgnNm></cnstwkRgnNm> <cnsttyDivNm>전문</cnsttyDivNm> <cntrctMthdNm>일반경쟁</cntrctMthdNm> <orderContrctAmt>902000000</orderContrctAmt> <orderGovsplyMtrcst>0</orderGovsplyMtrcst> <orderEtcAmt>0</orderEtcAmt> <sumOrderAmt>939851000</sumOrderAmt> <deptNm>재정과</deptNm> <ofclNm>서지원</ofclNm> <telNo>062-380-4164</telNo> <agrmntYn>N</agrmntYn> <usgCntnts>냉난방기</usgCntnts> <qtyCntnts>1</qtyCntnts> <unit>식</unit> <prdctClsfcNo>40101806</prdctClsfcNo> <dtilPrdctClsfcNo>4010180601</dtilPrdctClsfcNo> <prdctClsfcNoNm>열펌프</prdctClsfcNoNm> <ntceNticeYn>Y</ntceNticeYn> <sumOrderDolAmt></sumOrderDolAmt> <rcritRgstNo></rcritRgstNo> <specItemNm1></specItemNm1> <specItemNm2></specItemNm2> <specItemNm3></specItemNm3> <specItemNm4></specItemNm4> <specItemNm5></specItemNm5> <specItemCntnts1></specItemCntnts1> <specItemCntnts2></specItemCntnts2> <specItemCntnts3></specItemCntnts3> <specItemCntnts4></specItemCntnts4> <specItemCntnts5></specItemCntnts5> <bdgtDivCd></bdgtDivCd> <cnstwkPrdCntnts></cnstwkPrdCntnts> <nticeDt>2026-05-12 11:19:03</nticeDt> <orderThtmContrctAmt>0.0</orderThtmContrctAmt> <orderNtntrsAuxAmt>0.0</orderNtntrsAuxAmt> <dtilPrdctClsfcNoNm>전기히트펌프</dtilPrdctClsfcNoNm> <specCntnts>시방서 참조</specCntnts> <dsgnDocRdngPlceNm></dsgnDocRdngPlceNm> <dsgnDocRdngPrdCntnts></dsgnDocRdngPrdCntnts> <rmrkCntnts></rmrkCntnts> <orderPlanUntyNo>R26DD20756451</orderPlanUntyNo> <bidNtceNoList>R26BK01516555000</bidNtceNoList> <chgDt>2026-05-12 11:19:32</chgDt> <orderPlanDtlUrl>https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20756451</orderPlanDtlUrl> <atchFileExistnceYn> Y</atchFileExistnceYn> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 발주계획현황에 대한 공사조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 6 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 발주계획현황에 대한 공사조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getOrderPlanSttusListCnstwkPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 발주시작년월, 발주종료년월, 게시일시, 발주기관코드, 발주기관명, 업무유형, 조달방식, 기관소재지, 공종, 사업명으로 하여 발주년도, 발주기관, 소관기관, 계약방법, 발주도급금액, 물품분류 정보, 규격항목정보 등 공사에 대한 발주계획현황 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 0 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 0 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포탈에서 받은 인증키 | 공공데이터포탈에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| orderBgnYm | 발주시작년월 | 6 | 0 | 202601 | 검색하고자하는 발주년도, 발주월 기준 조회시작 'YYYYMM'
* 입력값이 없을 경우 현재일로부터 한달기준 조회 |
| orderEndYm | 발주종료년월 | 6 | 1 | 202601 | 검색하고자하는 발주년도, 발주월 기준 조회종료 'YYYYMM'
* 입력값이 없을 경우 현재일로부터 한달기준 조회 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202601020000 | 검색하고자하는 게시일시 기준 조회시작'YYYYMMDDHHmm'
* 입력값이 없을 경우 현재일로 부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202601022359 | 검색하고자하는 게시일시 기준 조회종료 'YYYYMMDDHHmm'
* 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 4070000 | 검색하고자하는 발주기관코드 |
| orderInsttNm | 발주기관명 | 200 | 0 | 경기도 이천시 | 검색하고자하는 발주기관명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 검색하고자하는 업무유형명 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 검색하고자하는 조달방식 (중앙조달, 자체조달) |
| insttLctNm | 기관소재지명 | 100 | 0 | 서울특별시 | 검색하고자 하는 기관의 소재지 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 전문 | 검색하고자하는 발주계획의 공종구분명 |
| bizNm | 사업명 | 400 | 0 | 지례초등학교 옹벽 및 배수로 설치공사 | 검색하고자하는 발주계획의 사업명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 01 | 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 물품 | 업무구분명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 업무유형명 |
| orderYear | 발주년도 | 4 | 1 | 2026 | 발주년도 |
| orderInsttCd | 발주기관코드 | 7 | 1 | 7140433 | 발주기관코드는행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| totlmngInsttNm | 총괄기관명 | 200 | 0 | 광주광역시교육청 | 총괄기관명 |
| jrsdctnDivCd | 소관구분코드 | 7 | 0 | 소110003 | 소관구분코드
소110001 : 국가기관
소110002 : 지방자치단체
소110003 : 교육기관
소110004 : 공기업
소110005 : 준정부기관
소110006 : 기타공공기관
소110007 : 지방공기업
소110008 : 기타기관
소110009 : 정부투자기관
소110010 : 지자체 출자출연기관
소110011 : 지방의료원
소110012 : 지자체출연연구원
소110013 : 특수법인
소110014 : 기초자치단체 |
| jrsdctnDivNm | 소관구분명 | 100 | 0 | 교육기관 | 소관구분명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 광주광역시교육청 | 발주기관명 |
| orderPlanSno | 발주계획순번 | 6 | 1 | 0 | 발주계획순번 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 조달방식은 중앙조달과 자체조달로 구분됨 |
| orderMnth | 발주월 | 8 | 1 | 05 | 발주월 "MM" |
| bizNm | 사업명 | 400 | 1 | 가칭)광산고 냉난방기 구매설치 | 발주계획의 사업명 |
| cnstwkRgnNm | 공사지역명 | 100 | 0 | 광주광역시 | 발주계획의 공사지역명 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 전문 | 발주계획의 공종구분명 |
| cntrctMthdNm | 계약방법명 | 20 | 0 | 일반경쟁 | 발주계획의 계약방법명 |
| orderContrctAmt | 발주도급금액 | 30 | 0 | 902000000 | 발주도급금액(원화,원) |
| orderGovsplyMtrcst | 발주관급자재비 | 30 | 0 | 0 | 발주관급자재비(원화,원) |
| orderEtcAmt | 발주기타금액 | 30 | 0 | 0 | 발주기타금액(원화,원) |
| sumOrderAmt | 합계발주금액 | 30 | 0 | 939851000 | 합계발주금액(원화,원) |
| deptNm | 부서명 | 50 | 0 | 재정과 | 발주계획의 담당부서명 |
| ofclNm | 담당자명 | 20 | 0 | 서지원 | 발주계획의 담당자명 |
| telNo | 전화번호 | 20 | 0 | 062-380-4164 | 발주계획의 담당자 전화번호 |
| agrmntYn | 협정여부 | 1 | 0 | N | 발주계획의 협정여부 |
| usgCntnts | 용도내용 | 50 | 0 | 냉난방기 | 발주계획의 용도내용 |
| qtyCntnts | 수량내용 | 12 | 0 | 1 | 발주계획의 수량내용 |
| unit | 단위 | 30 | 0 | 식 | 발주계획의 수량단위 |
| prdctClsfcNo | 물품분류번호 | 11 | 0 | 40101806 | 품명에 대한 분류번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 4010180601 | 품명에 대한 분류번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 열펌프 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| ntceNticeYn | 공고게시여부 | 1 | 1 | Y | 발주계획의 공고게시여부 |
| sumOrderDolAmt | 합계발주미화금액 | 16 | 0 | 0 | 발주계획의 합계발주미화금액(달러) |
| rcritRgstNo | 모집등록번호 | 12 | 0 | 201606271388 | 발주계획의 모집등록번호(외자규격모집공고번호) |
| specItemNm1 | 규격항목명1 | 200 | 0 | Ultra Performance Liquid Chromatography System | 발주계획의 규격항목명1 |
| specItemNm2 | 규격항목명2 | 200 | 0 | Photodiode Array Detector | 발주계획의 규격항목명2 |
| specItemNm3 | 규격항목명3 | 200 | 0 | Chromatography manager software | 발주계획의 규격항목명3 |
| specItemNm4 | 규격항목명4 | 200 | 0 | System Suitability Option Software | 발주계획의 규격항목명4 |
| specItemNm5 | 규격항목명5 | 200 | 0 | Accessories | 발주계획의 규격항목명5 |
| specItemCntnts1 | 규격항목내용1 | 200 | 0 | Type : Binary Precise, High pressure blending | 발주계획의 규격항목내용1 |
| specItemCntnts2 | 규격항목내용2 | 200 | 0 | Number of Sample Plates : 96 vial or morer | 발주계획의 규격항목내용2 |
| specItemCntnts3 | 규격항목내용3 | 200 | 0 | Column Temperature Control : 5.0°C above ambient to 90.0°C or wider | 발주계획의 규격항목내용3 |
| specItemCntnts4 | 규격항목내용4 | 200 | 0 | Wavelength Range : 190~800 nm or wider | 발주계획의 규격항목내용4 |
| specItemCntnts5 | 규격항목내용5 | 200 | 0 | Full 64 bit OS System &#8211; Win 7 pro | 발주계획의 규격항목내용5 |
| bdgtDivCd | 예산구분코드 | 25 | 0 | 9027200000 | 발주계획의 예산구분코드 |
| cnstwkPrdCntnts | 공사기간내용 | 30 | 0 | 202608 | 발주계획의 공사기간내용 |
| nticeDt | 게시일시 | 19 | 0 | 2026-05-12 11:19:03 | 발주계획의 게시일시 "YYYY-MM-DD HH:MM:SS" |
| orderThtmContrctAmt | 발주금차도급금액 | 25 | 0 | 0.0 | 발주계획의 발주금차도급금액(원화,원) |
| orderNtntrsAuxAmt | 발주국고보조금액 | 25 | 0 | 0.0 | 발주계획의 발주국고보조금액(원화,원) |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 전기히트펌프 | 세부품명번호 10자리에 대한 한글명 |
| specCntnts | 규격내용 | 100 | 0 | 시방서 참조 | 발주계획의 규격내용 |
| dsgnDocRdngPlceNm | 설계문서열람장소명 | 100 | 0 | 시방서 참조 | 발주계획의 설계서열람장소명 |
| dsgnDocRdngPrdCntnts | 설계문서열람기간내용 | 100 | 0 | 2026.11~2026.11 | 발주계획의 설계서열람기간내용 |
| rmrkCntnts | 비고내용 | 4000 | 0 | 설계금액 미확정 및 인허가 지연 등으로 건설일정 등은 변경될 수 있음 / 설계서 열람기간은 현장설명일의 결정에 따라 변경될 수 있음 | 발주계획의 비고내용(담당부서 정보) |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20756451 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R26BK01516555000 | 관련된 입찰공고번호 정보 "입찰공고번호1,입찰공고번호2" |
| chgDt | 변경일시 | 19 | 0 | 2026-05-12 11:19:32 | 발주계획의 변경일시 "YYYY-MM-DD HH:MM:SS" |
| orderPlanDtlUrl | 발주계획상세URL | 512 | 0 | https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20756451 | 나라장터시스템의 발주계획상세화면 링크URL |
| atchFileExistnceYn | 첨부파일존재여부 | 1 | 0 | Y | 첨부파일 존재여부 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/OrderPlanSttusService/getOrderPlanSttusListCnstwkPPSSrch?inqryBgnDt=202601020000&inqryEndDt=202601022359&numOfRows=10&pageNo=1&orderBgnYm=202601&orderEndYm=202601&orderInsttCd=4070000&orderInsttNm=경기도 이천시&bsnsTyCd=신120001&bsnsTyNm=신규&prcrmntMethd=중앙조달&insttLctNm=경기도&cnsttyDivNm=소방&bizNm=중리지구 다목적실내체육관 건립사업(소방)&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivCd>07</bsnsDivCd> <bsnsDivNm>공사</bsnsDivNm> <bsnsTyCd>신120001</bsnsTyCd> <bsnsTyNm>신규(단기)</bsnsTyNm> <orderYear>2026</orderYear> <orderInsttCd>1352259</orderInsttCd> <totlmngInsttNm>보건복지부</totlmngInsttNm> <jrsdctnDivCd>소110001</jrsdctnDivCd> <jrsdctnDivNm>국가기관</jrsdctnDivNm> <orderInsttNm>보건복지부 국립부곡병원</orderInsttNm> <orderPlanSno>0</orderPlanSno> <prcrmntMethd>중앙조달</prcrmntMethd> <orderMnth>03</orderMnth> <bizNm>국립부곡병원 본관 외벽 판넬공사(전면부) </bizNm> <cnstwkRgnNm>경상남도</cnstwkRgnNm> <cnsttyDivNm>건축</cnsttyDivNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <orderContrctAmt>486994360</orderContrctAmt> <orderGovsplyMtrcst>0</orderGovsplyMtrcst> <orderEtcAmt>0</orderEtcAmt> <sumOrderAmt>486994360</sumOrderAmt> <deptNm>기획운영과</deptNm> <ofclNm>박기숙</ofclNm> <telNo>055-520-2517</telNo> <agrmntYn>N</agrmntYn> <usgCntnts></usgCntnts> <qtyCntnts>0</qtyCntnts> <unit></unit> <prdctClsfcNo></prdctClsfcNo> <dtilPrdctClsfcNo></dtilPrdctClsfcNo> <prdctClsfcNoNm></prdctClsfcNoNm> <ntceNticeYn>Y</ntceNticeYn> <sumOrderDolAmt></sumOrderDolAmt> <rcritRgstNo></rcritRgstNo> <specItemNm1></specItemNm1> <specItemNm2></specItemNm2> <specItemNm3></specItemNm3> <specItemNm4></specItemNm4> <specItemNm5></specItemNm5> <specItemCntnts1></specItemCntnts1> <specItemCntnts2></specItemCntnts2> <specItemCntnts3></specItemCntnts3> <specItemCntnts4></specItemCntnts4> <specItemCntnts5></specItemCntnts5> <bdgtDivCd>3961-300-420-03</bdgtDivCd> <cnstwkPrdCntnts></cnstwkPrdCntnts> <nticeDt>2026-03-13 15:14:21</nticeDt> <orderThtmContrctAmt>0.0</orderThtmContrctAmt> <orderNtntrsAuxAmt>0.0</orderNtntrsAuxAmt> <dtilPrdctClsfcNoNm></dtilPrdctClsfcNoNm> <specCntnts></specCntnts> <dsgnDocRdngPlceNm></dsgnDocRdngPlceNm> <dsgnDocRdngPrdCntnts></dsgnDocRdngPrdCntnts> <rmrkCntnts></rmrkCntnts> <orderPlanUntyNo>R26DD20688178</orderPlanUntyNo> <bidNtceNoList>R26BK01454051000</bidNtceNoList> <chgDt></chgDt> <orderPlanDtlUrl>https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688178</orderPlanDtlUrl> <atchFileExistnceYn> Y</atchFileExistnceYn> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 발주계획현황에 대한 용역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 7 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 발주계획현황에 대한 용역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getOrderPlanSttusListServcPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 발주시작년월, 발주종료년월, 게시일시, 발주기관코드, 발주기관명, 업무유형, 조달방식, 기관소재지, 공종, 사업명으로 하여 발주년도, 발주기관, 소관기관, 계약방법, 발주도급금액, 물품분류 정보, 규격항목정보 등 용역에 대한 발주계획현황 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 0 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 0 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포탈에서 받은 인증키 | 공공데이터포탈에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| orderBgnYm | 발주시작년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회시작 'YYYYMM'
* 입력값이 없을 경우 현재일로부터 한달기준 조회 |
| orderEndYm | 발주종료년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회종료 'YYYYMM'
* 입력값이 없을 경우 현재일로부터 한달기준 조회 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202603130000 | 검색하고자하는 게시일시 기준 조회시작'YYYYMMDDHHmm'
* 입력값이 없을 경우 현재일로 부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202603132359 | 검색하고자하는 게시일시 기준 조회종료 'YYYYMMDDHHmm'
* 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| orderInsttCd | 발주기관코드 | 7 | 0 | 6260000 | 검색하고자하는 발주기관코드 |
| orderInsttNm | 발주기관명 | 200 | 0 | 부산광역시 | 검색하고자하는 발주기관명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 검색하고자하는 업무유형명 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 검색하고자하는 조달방식 (중앙조달, 자체조달) |
| insttLctNm | 기관소재지명 | 100 | 0 | 부산광역시 | 검색하고자 하는 기관의 소재지 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 전문 | 검색하고자하는 발주계획의 공종구분명 |
| bizNm | 사업명 | 400 | 0 | 2026 피란수도 부산 국가유산 야행 기획‧운영 대행 용역 | 검색하고자하는 발주계획의 사업명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 03 | 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 일반용역 | 업무구분명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 업무유형명 |
| orderYear | 발주년도 | 4 | 1 | 2026 | 발주년도 |
| orderInsttCd | 발주기관코드 | 7 | 1 | 6260000 | 발주기관코드는 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| totlmngInsttNm | 총괄기관명 | 200 | 0 | 부산광역시 | 총괄기관명 |
| jrsdctnDivCd | 소관구분코드 | 7 | 0 | 소110002 | 소관구분코드
소110001 : 국가기관
소110002 : 지방자치단체
소110003 : 교육기관
소110004 : 공기업
소110005 : 준정부기관
소110006 : 기타공공기관
소110007 : 지방공기업
소110008 : 기타기관
소110009 : 정부투자기관
소110010 : 지자체 출자출연기관
소110011 : 지방의료원
소110012 : 지자체출연연구원
소110013 : 특수법인
소110014 : 기초자치단체 |
| jrsdctnDivNm | 소관구분명 | 100 | 0 | 지방자치단체 | 소관구분명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 부산광역시 | 발주기관명 |
| orderPlanSno | 발주계획순번 | 6 | 1 | 0 | 발주계획순번 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 조달방식은 중앙조달과 자체조달로 구분됨 |
| orderMnth | 발주월 | 8 | 1 | 03 | 발주월 "MM" |
| bizNm | 사업명 | 400 | 1 | 2026 피란수도 부산 국가유산 야행 기획 운영 대행 용역 | 발주계획의 사업명 |
| cnstwkRgnNm | 공사지역명 | 100 | 0 | 경상북도 | 발주계획의 공사지역명 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 전문 | 발주계획의 공종구분명 |
| cntrctMthdNm | 계약방법명 | 20 | 0 | 제한경쟁 | 발주계획의 계약방법명 |
| orderContrctAmt | 발주도급금액 | 30 | 0 | 0 | 발주도급금액(원화,원) |
| orderGovsplyMtrcst | 발주관급자재비 | 30 | 0 | 0 | 발주관급자재비(원화,원) |
| orderEtcAmt | 발주기타금액 | 30 | 0 | 0 | 발주기타금액(원화,원) |
| sumOrderAmt | 합계발주금액 | 30 | 0 | 582500000 | 합계발주금액(원화,원) |
| deptNm | 부서명 | 50 | 0 | 문화유산과 | 발주계획의 담당부서명 |
| ofclNm | 담당자명 | 20 | 0 | 이은정 | 발주계획의 담당자명 |
| telNo | 전화번호 | 20 | 0 | 051-888-5092 | 발주계획의 담당자전화번호 |
| agrmntYn | 협정여부 | 1 | 0 | N | 발주계획의 협정여부 |
| usgCntnts | 용도내용 | 50 | 0 | 실험실안전장비 | 발주계획의 용도내용 |
| qtyCntnts | 수량내용 | 12 | 0 | 0 | 발주계획의 수량내용 |
| unit | 단위 | 30 | 0 | set | 발주계획의 수량단위 |
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 41103502 | 품명에 대한 분류번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 4110350201 | 품명에 대한 분류번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 실험실용배기기 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| ntceNticeYn | 공고게시여부 | 1 | 1 | Y | 발주계획의 공고게시여부 |
| sumOrderDolAmt | 합계발주미화금액 | 16 | 0 | 0 | 발주계획의 합계발주미화금액(달러) |
| rcritRgstNo | 모집등록번호 | 12 | 0 | 201606271388 | 발주계획의 모집등록번호(외자규격모집공고번호) |
| specItemNm1 | 규격항목명1 | 200 | 0 | Ultra Performance Liquid Chromatography System | 발주계획의 규격항목명1 |
| specItemNm2 | 규격항목명2 | 200 | 0 | Photodiode Array Detector | 발주계획의 규격항목명2 |
| specItemNm3 | 규격항목명3 | 200 | 0 | Chromatography manager software | 발주계획의 규격항목명3 |
| specItemNm4 | 규격항목명4 | 200 | 0 | System Suitability Option Software | 발주계획의 규격항목명4 |
| specItemNm5 | 규격항목명5 | 200 | 0 | Accessories | 발주계획의 규격항목명5 |
| specItemCntnts1 | 규격항목내용1 | 200 | 0 | Type : Binary Precise, High pressure blending | 발주계획의 규격항목내용1 |
| specItemCntnts2 | 규격항목내용2 | 200 | 0 | Number of Sample Plates : 96 vial or morer | 발주계획의 규격항목내용2 |
| specItemCntnts3 | 규격항목내용3 | 200 | 0 | Column Temperature Control : 5.0°C above ambient to 90.0°C or wider | 발주계획의 규격항목내용3 |
| specItemCntnts4 | 규격항목내용4 | 200 | 0 | Wavelength Range : 190~800 nm or wider | 발주계획의 규격항목내용4 |
| specItemCntnts5 | 규격항목내용5 | 200 | 0 | Full 64 bit OS System &#8211; Win 7 pro | 발주계획의 규격항목내용5 |
| bdgtDivCd | 예산구분코드 | 25 | 0 | 9027200000 | 발주계획의 예산구분코드 |
| cnstwkPrdCntnts | 공사기간내용 | 30 | 0 | 201608 | 발주계획의 공사기간내용 |
| nticeDt | 게시일시 | 19 | 0 | 2026-03-13 17:35:16 | 발주계획의 게시일시 "YYYY-MM-DD HH:MM:SS" |
| orderThtmContrctAmt | 발주금차도급금액 | 25 | 0 | 0.0 | 발주계획의 발주금차도급금액(원화,원) |
| orderNtntrsAuxAmt | 발주국고보조금액 | 25 | 0 | 0.0 | 발주계획의 발주국고보조금액(원화,원) |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 강판맨홀뚜껑 | 세부품명번호 10자리에 대한 한글명 |
| specCntnts | 규격내용 | 100 | 0 | 붙임 규격서에 의함 | 발주계획의 규격내용 |
| dsgnDocRdngPlceNm | 설계문서열람장소명 | 100 | 0 | Contruction Site | 발주계획의 설계서열람장소명 |
| dsgnDocRdngPrdCntnts | 설계문서열람기간내용 | 100 | 0 | 2016.11~2016.11 | 발주계획의 설계서열람기간내용 |
| rmrkCntnts | 비고내용 | 4000 | 0 | 설계금액 미확정 및 인허가 지연 등으로 건설일정 등은 변경될 수 있음 / 설계서 열람기간은 현장설명일의 결정에 따라 변경될 수 있음 | 발주계획의 비고내용(담당부서 정보) |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20688643 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R26BK01412607000 | 관련된 입찰공고번호 정보 "입찰공고번호1,입찰공고번호2" |
| chgDt | 변경일시 | 19 | 0 | 2026-03-13 17:35:16 | 발주계획의 변경일시 "YYYY-MM-DD HH:MM:SS" |
| orderPlanDtlUrl | 발주계획상세URL | 512 | 0 | https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688643 | 나라장터시스템의 발주계획상세화면 링크URL |
| atchFileExistnceYn | 첨부파일존재여부 | 1 | 0 | Y | 첨부파일 존재여부 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/OrderPlanSttusService/getOrderPlanSttusListServcPPSSrch?type=xml&inqryBgnDt=202603130000&inqryEndDt=202603132359&numOfRows=10&pageNo=1&orderBgnYm=202603&orderEndYm=202603&orderInsttCd=6260000&orderInsttNm=부산광역시&bsnsTyCd=신120001&bsnsTyNm=신규(단기)&prcrmntMethd=자체조달&insttLctNm=부산광역시&bizNm=2026 피란수도 부산 국가유산 야행 기획‧운영 대행 용역 &pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivCd>03</bsnsDivCd> <bsnsDivNm>일반용역</bsnsDivNm> <bsnsTyCd>신120001</bsnsTyCd> <bsnsTyNm>신규(단기)</bsnsTyNm> <orderYear>2026</orderYear> <orderInsttCd>6260000</orderInsttCd> <totlmngInsttNm>부산광역시</totlmngInsttNm> <jrsdctnDivCd>소110002</jrsdctnDivCd> <jrsdctnDivNm>지방자치단체</jrsdctnDivNm> <orderInsttNm>부산광역시</orderInsttNm> <orderPlanSno>0</orderPlanSno> <prcrmntMethd>자체조달</prcrmntMethd> <orderMnth>03</orderMnth> <bizNm>2026 피란수도 부산 국가유산 야행 기획?운영 대행 용역</bizNm> <cnstwkRgnNm></cnstwkRgnNm> <cnsttyDivNm></cnsttyDivNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <orderContrctAmt>0</orderContrctAmt> <orderGovsplyMtrcst>0</orderGovsplyMtrcst> <orderEtcAmt>0</orderEtcAmt> <sumOrderAmt>582500000</sumOrderAmt> <deptNm>문화유산과</deptNm> <ofclNm>이은정</ofclNm> <telNo>051-888-5092</telNo> <agrmntYn>N</agrmntYn> <usgCntnts></usgCntnts> <qtyCntnts>0</qtyCntnts> <unit></unit> <prdctClsfcNo></prdctClsfcNo> <dtilPrdctClsfcNo></dtilPrdctClsfcNo> <prdctClsfcNoNm></prdctClsfcNoNm> <ntceNticeYn>Y</ntceNticeYn> <sumOrderDolAmt></sumOrderDolAmt> <rcritRgstNo></rcritRgstNo> <specItemNm1></specItemNm1> <specItemNm2></specItemNm2> <specItemNm3></specItemNm3> <specItemNm4></specItemNm4> <specItemNm5></specItemNm5> <specItemCntnts1></specItemCntnts1> <specItemCntnts2></specItemCntnts2> <specItemCntnts3></specItemCntnts3> <specItemCntnts4></specItemCntnts4> <specItemCntnts5></specItemCntnts5> <bdgtDivCd></bdgtDivCd> <cnstwkPrdCntnts></cnstwkPrdCntnts> <nticeDt>2026-03-13 17:35:16</nticeDt> <orderThtmContrctAmt>0.0</orderThtmContrctAmt> <orderNtntrsAuxAmt>0.0</orderNtntrsAuxAmt> <dtilPrdctClsfcNoNm></dtilPrdctClsfcNoNm> <specCntnts></specCntnts> <dsgnDocRdngPlceNm></dsgnDocRdngPlceNm> <dsgnDocRdngPrdCntnts></dsgnDocRdngPrdCntnts> <rmrkCntnts></rmrkCntnts> <orderPlanUntyNo>R26DD20688643</orderPlanUntyNo> <bidNtceNoList>R26BK01412607000</bidNtceNoList> <chgDt></chgDt> <orderPlanDtlUrl>https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688643</orderPlanDtlUrl> <atchFileExistnceYn> Y</atchFileExistnceYn> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 발주계획현황에 대한 외자조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 8 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 발주계획현황에 대한 외자조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getOrderPlanSttusListFrgcptPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 발주시작년월, 발주종료년월, 게시일시, 발주기관코드, 발주기관명, 업무유형, 조달방식, 기관소재지, 사업명으로 하여 발주년도, 발주기관, 소관기관, 계약방법, 발주도급금액, 물품분류 정보, 규격항목정보 등 외자에 대한 발주계획현황 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 0 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 0 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포탈에서 받은 인증키 | 공공데이터포탈에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| orderBgnYm | 발주시작년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회시작 'YYYYMM'
* 입력값이 없을 경우 현재일로부터 한달기준 조회 |
| orderEndYm | 발주종료년월 | 6 | 0 | 202603 | 검색하고자하는 발주년도, 발주월 기준 조회종료 'YYYYMM'
* 입력값이 없을 경우 현재일로부터 한달기준 조회 |
| inqryBgnDt | 조회시작일시 | 12 | 1 | 202603130000 | 검색하고자하는 게시일시 기준 조회시작'YYYYMMDDHHmm'
* 입력값이 없을 경우 현재일로 부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202603132356 | 검색하고자하는 게시일시 기준 조회종료 'YYYYMMDDHHmm'
* 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| orderInsttCd | 발주기관코드 | 7 | 0 | B551364 | 검색하고자하는 발주기관코드 |
| orderInsttNm | 발주기관명 | 200 | 0 | 한국기초과학지원연구원 | 검색하고자하는 발주기관명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 1 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 검색하고자하는 업무유형명 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 검색하고자하는 조달방식 (중앙조달, 자체조달) |
| insttLctNm | 기관소재지명 | 100 | 0 | 대전광역시 유성구 | 검색하고자 하는 기관의 소재지 |
| bizNm | 사업명 | 400 | 0 | 소동물용단층촬영시스템 | 검색하고자하는 발주계획의 사업명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 02 | 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 외자 | 업무구분명 |
| bsnsTyCd | 업무유형코드 | 7 | 0 | 신120001 | 업무유형코드
WTO:WTO
신120001 : 신규(단기) 
신120002 : 장기
신120003 : 계속비
신120004 : 신규(장기) 
신120005 : 장기(계속비) |
| bsnsTyNm | 업무유형명 | 20 | 0 | 신규(단기) | 업무유형명 |
| orderYear | 발주년도 | 4 | 1 | 2026 | 발주년도 |
| orderInsttCd | 발주기관코드 | 7 | 1 | B551364 | 발주기관코드는 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 발주기관 코드가 표기됨 |
| totlmngInsttNm | 총괄기관명 | 200 | 0 | 한국기초과학지원연구원 | 총괄기관명 |
| jrsdctnDivCd | 소관구분코드 | 7 | 0 | 소110008 | 소관구분코드
소110001 : 국가기관
소110002 : 지방자치단체
소110003 : 교육기관
소110004 : 공기업
소110005 : 준정부기관
소110006 : 기타공공기관
소110007 : 지방공기업
소110008 : 기타기관
소110009 : 정부투자기관
소110010 : 지자체 출자출연기관
소110011 : 지방의료원
소110012 : 지자체출연연구원
소110013 : 특수법인
소110014 : 기초자치단체 |
| jrsdctnDivNm | 소관구분명 | 100 | 0 | 기타기관 | 소관구분명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 한국기초과학지원연구원 | 발주기관명 |
| orderPlanSno | 발주계획순번 | 6 | 1 | 0 | 발주계획순번 |
| prcrmntMethd | 조달방식 | 20 | 0 | 자체조달 | 조달방식은 중앙조달과 자체조달로 구분됨 |
| orderMnth | 발주월 | 8 | 1 | 03 | 발주월 "MM" |
| bizNm | 사업명 | 400 | 1 | 소동물용단층촬영시스템 [In Vivo Xray Radiography MicroCT System] | 발주계획의 사업명 |
| cnstwkRgnNm | 공사지역명 | 100 | 0 | 경상북도 | 발주계획의 공사지역명 |
| cnsttyDivNm | 공종구분명 | 20 | 0 | 전문 | 발주계획의 공종구분명 |
| cntrctMthdNm | 계약방법명 | 20 | 0 | 일반경쟁 | 발주계획의 계약방법명 |
| orderContrctAmt | 발주도급금액 | 30 | 0 | 0 | 발주도급금액(원화,원) |
| orderGovsplyMtrcst | 발주관급자재비 | 30 | 0 | 0 | 발주관급자재비(원화,원) |
| orderEtcAmt | 발주기타금액 | 30 | 0 | 0 | 발주기타금액(원화,원) |
| sumOrderAmt | 합계발주금액 | 30 | 0 | 687000000 | 합계발주금액(원화,원) |
| deptNm | 부서명 | 50 | 0 | 구매자산실 | 발주계획의 담당부서명 |
| ofclNm | 담당자명 | 20 | 0 | 성희수 | 발주계획의 담당자명 |
| telNo | 전화번호 | 20 | 0 | 042-865-2582 | 발주계획의 담당자전화번호 |
| agrmntYn | 협정여부 | 1 | 0 | N | 발주계획의 협정여부 |
| usgCntnts | 용도내용 | 50 | 0 | 학술 연구용 | 발주계획의 용도내용 |
| qtyCntnts | 수량내용 | 12 | 0 | 1 | 발주계획의 수량내용 |
| unit | 단위 | 30 | 0 | SET | 발주계획의 수량단위 |
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 99422015 | 품명에 대한 분류번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 9942201501 | 품명에 대한 분류번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 융복합전산화단층엑스선촬영장치(CT) | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| ntceNticeYn | 공고게시여부 | 1 | 1 | Y | 발주계획의 공고게시여부 |
| sumOrderDolAmt | 합계발주미화금액 | 16 | 0 | 458000.0 | 발주계획의 합계발주미화금액(달러) |
| rcritRgstNo | 모집등록번호 | 12 | 0 | 202606271388 | 발주계획의 모집등록번호(외자규격모집공고번호) |
| specItemNm1 | 규격항목명1 | 200 | 0 | Detector type | 발주계획의 규격항목명1 |
| specItemNm2 | 규격항목명2 | 200 | 0 | Pixel number | 발주계획의 규격항목명2 |
| specItemNm3 | 규격항목명3 | 200 | 0 | Image resolution | 발주계획의 규격항목명3 |
| specItemNm4 | 규격항목명4 | 200 | 0 | Pixel size | 발주계획의 규격항목명4 |
| specItemNm5 | 규격항목명5 | 200 | 0 | Scan time | 발주계획의 규격항목명5 |
| specItemCntnts1 | 규격항목내용1 | 200 | 0 | CMOS Flat panel or better | 발주계획의 규격항목내용1 |
| specItemCntnts2 | 규격항목내용2 | 200 | 0 | 2,944 x 2,352 | 발주계획의 규격항목내용2 |
| specItemCntnts3 | 규격항목내용3 | 200 | 0 | 2864 x 2864 x 2272 (High) or better | 발주계획의 규격항목내용3 |
| specItemCntnts4 | 규격항목내용4 | 200 | 0 | 2.86 um or better | 발주계획의 규격항목내용4 |
| specItemCntnts5 | 규격항목내용5 | 200 | 0 | 3.9 sec or better | 발주계획의 규격항목내용5 |
| bdgtDivCd | 예산구분코드 | 25 | 0 | 9027200000 | 발주계획의 예산구분코드 |
| cnstwkPrdCntnts | 공사기간내용 | 30 | 0 | 202612 | 발주계획의 공사기간내용 |
| nticeDt | 게시일시 | 19 | 0 | 2026-03-13 15:35:42 | 발주계획의 게시일시 "YYYY-MM-DD HH:MM:SS" |
| orderThtmContrctAmt | 발주금차도급금액 | 25 | 0 | 0.0 | 발주계획의 발주금차도급금액(원화,원) |
| orderNtntrsAuxAmt | 발주국고보조금액 | 25 | 0 | 0.0 | 발주계획의 발주국고보조금액(원화,원) |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 융복합전산화단층엑스선촬영장치(CT) | 세부품명번호 10자리에 대한 한글명 |
| specCntnts | 규격내용 | 100 | 0 | 붙임 규격서에 의함 | 발주계획의 규격내용 |
| dsgnDocRdngPlceNm | 설계문서열람장소명 | 100 | 0 | Contruction Site | 발주계획의 설계서열람장소명 |
| dsgnDocRdngPrdCntnts | 설계문서열람기간내용 | 100 | 0 | 2016.11~2016.11 | 발주계획의 설계서열람기간내용 |
| rmrkCntnts | 비고내용 | 4000 | 0 | 설계금액 미확정 및 인허가 지연 등으로 건설일정 등은 변경될 수 있음 / 설계서 열람기간은 현장설명일의 결정에 따라 변경될 수 있음 | 발주계획의 비고내용(담당부서 정보) |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20688240 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R26BK01429023000,R26BK01483031000 | 관련된 입찰공고번호 정보 "입찰공고번호1,입찰공고번호2" |
| chgDt | 변경일시 | 19 | 0 | 2026-03-13 15:52:46 | 발주계획의 변경일시 "YYYY-MM-DD HH:MM:SS" |
| orderPlanDtlUrl | 발주계획상세URL | 512 | 0 | https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688240 | 나라장터시스템의 발주계획상세화면 링크URL |
| atchFileExistnceYn | 첨부파일존재여부 | 1 | 0 | N | 첨부파일 존재여부 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/OrderPlanSttusService/getOrderPlanSttusListFrgcptPPSSrch?inqryBgnDt=202603130000&inqryEndDt=202603132356&numOfRows=10&pageNo=1&orderBgnYm=202603&orderEndYm=202603&orderInsttCd=B551364&orderInsttNm=한국기초과학지원연구원&prcrmntMethd=자체조달&insttLctNm=대전광역시 유성구&bizNm=소동물용단층촬영시스템 &ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivCd>02</bsnsDivCd> <bsnsDivNm>외자</bsnsDivNm> <bsnsTyCd></bsnsTyCd> <bsnsTyNm></bsnsTyNm> <orderYear>2026</orderYear> <orderInsttCd>B551364</orderInsttCd> <totlmngInsttNm>한국기초과학지원연구원</totlmngInsttNm> <jrsdctnDivCd>소110008</jrsdctnDivCd> <jrsdctnDivNm>기타기관</jrsdctnDivNm> <orderInsttNm>한국기초과학지원연구원</orderInsttNm> <orderPlanSno>0</orderPlanSno> <prcrmntMethd>자체조달</prcrmntMethd> <orderMnth>03</orderMnth> <bizNm>소동물용단층촬영시스템 [In Vivo Xray Radiography MicroCT System]</bizNm> <cnstwkRgnNm></cnstwkRgnNm> <cnsttyDivNm></cnsttyDivNm> <cntrctMthdNm>일반경쟁</cntrctMthdNm> <orderContrctAmt>0</orderContrctAmt> <orderGovsplyMtrcst>0</orderGovsplyMtrcst> <orderEtcAmt>0</orderEtcAmt> <sumOrderAmt>687000000</sumOrderAmt> <deptNm>구매자산실</deptNm> <ofclNm>성희수</ofclNm> <telNo>042-865-2582</telNo> <agrmntYn>N</agrmntYn> <usgCntnts>학술 연구용</usgCntnts> <qtyCntnts>1</qtyCntnts> <unit>SET</unit> <prdctClsfcNo>99422015</prdctClsfcNo> <dtilPrdctClsfcNo>9942201501</dtilPrdctClsfcNo> <prdctClsfcNoNm></prdctClsfcNoNm> <ntceNticeYn>Y</ntceNticeYn> <sumOrderDolAmt>458000.0</sumOrderDolAmt> <rcritRgstNo></rcritRgstNo> <specItemNm1>Detector type</specItemNm1> <specItemNm2>Pixel number</specItemNm2> <specItemNm3>Image resolution</specItemNm3> <specItemNm4>Pixel size</specItemNm4> <specItemNm5>Scan time</specItemNm5> <specItemCntnts1>CMOS Flat panel or better</specItemCntnts1> <specItemCntnts2>2,944 x 2,352</specItemCntnts2> <specItemCntnts3>2864 x 2864 x 2272 (High) or better</specItemCntnts3> <specItemCntnts4>2.86 um or better</specItemCntnts4> <specItemCntnts5>3.9 sec or better</specItemCntnts5> <bdgtDivCd></bdgtDivCd> <cnstwkPrdCntnts>202612</cnstwkPrdCntnts> <nticeDt>2026-03-13 15:35:42</nticeDt> <orderThtmContrctAmt>0.0</orderThtmContrctAmt> <orderNtntrsAuxAmt>0.0</orderNtntrsAuxAmt> <dtilPrdctClsfcNoNm>융복합전산화단층엑스선촬영장치(CT)</dtilPrdctClsfcNoNm> <specCntnts></specCntnts> <dsgnDocRdngPlceNm></dsgnDocRdngPlceNm> <dsgnDocRdngPrdCntnts></dsgnDocRdngPrdCntnts> <rmrkCntnts></rmrkCntnts> <orderPlanUntyNo>R26DD20688240</orderPlanUntyNo> <bidNtceNoList>R26BK01429023000,R26BK01483031000</bidNtceNoList> <chgDt>2026-03-13 15:52:46</chgDt> <orderPlanDtlUrl>https://www.g2b.go.kr/link/PRPA015_01/single/?oderPlanNo=R26DD20688240</orderPlanDtlUrl> <atchFileExistnceYn>N</atchFileExistnceYn> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[발주계획현황에 대한 첨부파일 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 9 | 오퍼레이션명(국문) | 발주계획현황에 대한 첨부파일 목록조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getOrderPlanSttusAtchFileList |
|  | 오퍼레이션 설명 | 게시일시, 발주계획통합번호의 검색조건을 통해 발주계획의 첨부파일 정보를조회(업무구분코드, 업무구분명, 발주계획통합번호, 발주계획순번, 발주년월, 게시일시, 사업명, 첨부파일수번, 첨부파일명, 첨부파일URL) |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포탈에서 받은 인증키 | 공공데이터포탈에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 1:게시일시 2:발주계획통합번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202605120000 | 검색하고자하는 게시일시 기준 조회시작 'YYYYMMDDHHmm' *조회구분이 1인 경우 필수 (검색기간은 최대 1달로 제한) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202605122359 | 검색하고자하는 게시일시 기준 조회종료 'YYYYMMDDHHmm' *조회구분이 1인 경우 필수 (검색기간은 최대 1달로 제한) |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20756658 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 03 | 검색하고자하는 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 * 조회구분이 1인 경우 선택 |

응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 2 | 데이터 총개수 |
| bsnsDivCd | 업무구분코드 | 2 | 1 | 03 | 업무구분코드
01: 물품
02,04: 외자
07: 공사
03: 일반용역
05: 기술용역 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 일반용역 | 업무구분명 |
| orderPlanUntyNo | 발주계획통합번호 | 30 | 0 | R26DD20756658 | 발주계획관리번호
*차세대나라장터 번호체계 개편 : R+년도(2)+DD+순번(8) 총 13자리 구성
조회구분이 2인 경우 필수 |
| orderPlanSno | 발주계획순번 | 6 | 1 | 0 | 발주계획순번 |
| orderYearMm | 발주년월 | 4 | 1 | 202605 | 발주년월 “YYYYMM” |
| nticeDt | 게시일시 | 19 | 0 | 2026-03-13 15:35:42 | 2026-05-12 14:02:24 |
| bizNm | 사업명 | 400 | 1 | AI 기반 실시간 코칭이 결합된 스마트 무인 골프 트레이닝 시스템 도입 매장 확산을 위한 IoT 자동화 장비 맞춤 설치·현장 실증 및 유지보수 용역 | 발주계획의 사업명 |
| atchFileSno | 첨부파일순번 | 7 | 0 | 1 | 첨부파일의 순번 |
| atchFileNm | 첨부파일명 | 400 | 0 | (입찰공고문)김캐디_IoT 자동화 설치실증유지보수 용역.hwp | 첨부파일의 문서명 |
| atchFileUrl | 첨부파일URL | 800 | 0 | https://www.g2b.go.kr/pr/prp/prpa/UntyAtchFile/downloadFile.do?oderPlanNo=R26DD20756658&fileSeq=1 | 첨부파일의 다운로드 URL |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/getOrderPlanSttusAtchFileList?inqryDiv=2&numOfRows=10&pageNo=1&orderPlanUntyNo=R26DD20756658&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivCd>03</bsnsDivCd> <bsnsDivNm>일반용역</bsnsDivNm> <orderPlanUntyNo>R26DD20756658</orderPlanUntyNo> <orderPlanSno>0</orderPlanSno> <orderYearMm>202605</orderYearMm> <nticeDt>2026-05-12 14:02:24</nticeDt> <bizNm>AI 기반 실시간 코칭이 결합된 스마트 무인 골프 트레이닝 시스템 도입 매장 확산을 위한 IoT 자동화 장비 맞춤 설치·현장 실증 및 유지보수 용역</bizNm> <atchFileSno>1</atchFileSno> <atchFileNm>(입찰공고문)김캐디_IoT 자동화 설치실증유지보수 용역.hwp</atchFileNm> <atchFileUrl>https://www.g2b.go.kr/pr/prp/prpa/UntyAtchFile/downloadFile.do?oderPlanNo=R26DD20756658&fileSeq=1</atchFileUrl> </item> <item> <bsnsDivCd>03</bsnsDivCd> <bsnsDivNm>일반용역</bsnsDivNm> <orderPlanUntyNo>R26DD20756658</orderPlanUntyNo> <orderPlanSno>0</orderPlanSno> <orderYearMm>202605</orderYearMm> <nticeDt>2026-05-12 14:02:24</nticeDt> <bizNm>AI 기반 실시간 코칭이 결합된 스마트 무인 골프 트레이닝 시스템 도입 매장 확산을 위한 IoT 자동화 장비 맞춤 설치·현장 실증 및 유지보수 용역</bizNm> <atchFileSno>2</atchFileSno> <atchFileNm>붙임4.입찰보증금 지급각서.hwp</atchFileNm> <atchFileUrl>https://www.g2b.go.kr/pr/prp/prpa/UntyAtchFile/downloadFile.do?oderPlanNo=R26DD20756658&fileSeq=2</atchFileUrl> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

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

