# 조달청_OpenAPI참고자료_나라장터_사용자정보서비스_1.1

> 출처: 공공데이터포털 참고문서(docx) 자동 변환

조달청 공공데이터 개방
OpenAPI 참고자료
목 차목 차

### 1. 서비스 명세	3


### 1.1 나라장터 사용자정보서비스	3

가. 서비스 개요	3
나. 오퍼레이션 목록	4
개정 이력

| 버 전 | 변경일 | 변경 구분 | 변경사유 |
|---|---|---|---|
| 1.0 | 2025.01.01 | 최초 개정 | 최초 개정 |
| 1.1 | 2025.12.19 | 기능 추가 | [공공데이터 제공신청서_127896]에 의한 기능 추가 추가 오퍼레이션 : 부정당재제업체정보조회 |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |


### 1. 서비스 명세


### 1.1 나라장터 사용자정보서비스

서비스 개요

| 서비스 정보 | 서비스 ID | UsrInfoService02 |  |  |
|---|---|---|---|---|
|  | 서비스명(국문) | 나라장터 사용자정보서비스 |  |  |
|  | 서비스명(영문) | UsrInfoService02 |  |  |
|  | 서비스 설명 | 나라장터에 등록된 조달업체와 수요기관에 대한 정보를 제공하는 서비스로 조달업체정보에는 사업자등록번호, 업체명, 업체주소, 업체의 등록업종정보, 업체의 공급물품정보가 포함되며 수요기관정보에는 수요기관코드(행자부코드가 기본으로 제공되며 행자부코드가 없을 경우 나라장터 수요기관코드가 제공됨), 소관구분, 주소, 최상위기관코드, 최상위기관명 등이 포함되는 나라장터 사용자정보서비스 |  |  |
| 서비스 보안 | 서비스 인증/권한 | [O] 서비스 Key[ ] 인증서 (GPKI) [] Basic (ID/PW) [ ] 없음 | [ ]WS-Security |  |
|  | 메시지 레벨 암호화 | [  ] 전자서명	[ ] 암호화	[O] 없음 |  |  |
|  | 전송 레벨 암호화 | [  ] SSL			[ O] 없음 |  |  |
| 적용 기술 수준 | 인터페이스 표준 | [  ] SOAP 1.2 (RPC-Encoded, Document Literal, Document Literal Wrapped) [ O ] REST (GET) [ ] RSS 1.0 [ ] RSS 2.0 [ ] Atom 1.0 [ ] 기타 |  |  |
|  | 교환 데이터 표준 | [ O ] XML	[ O ] JSON	[ ] MIME	[ ] MTOM |  |  |
| 서비스 URL | 개발환경 | http://apis.data.go.kr/1230000/ao/UsrInfoService02 |  |  |
|  | 운영환경 | http://apis.data.go.kr/1230000/ao/UsrInfoService02 |  |  |
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

| 일련번호 | 서비스(국문) | 오퍼레이션명(영문) | 오퍼레이션명(국문) | 메시지명(영문) |
|---|---|---|---|---|
| 1 | 나라장터 사용자정보서비스 | getDminsttInfo02 | 수요기관정보조회 | N/A |
| 2 |  | getPrcrmntCorpBasicInfo02 | 조달업체 기본정보 | N/A |
| 3 |  | getPrcrmntCorpIndstrytyInfo02 | 조달업체업종정보조회 | N/A |
| 4 |  | getPrcrmntCorpSplyPrdctInfo02 | 조달업체공급물품정보조회 | N/A |

[수요기관정보조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 1 | 오퍼레이션명(국문) | 수요기관정보조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getDminsttInfo02 |
|  | 오퍼레이션 설명 | 수요기관코드, 사업자등록번호, 수요기관명을 입력하여 수요기관명, 유효기간,법인등록번호, 사업자등록번호, 소관구분명, 기관유형명, 업태명, 업종명, 주소,등록일시 등 수요기관정보 목록을 조회. |  |  |
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
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 입력
1: 등록일기준 검색
2: 변경일기준 검색
3: 사업자등록번호 기준검색 |
| inqryBgnDt | 조회기준시작일시 | 12 | 0 | 201301010000 | 검색하고자하는 검색기준시작일시 입력
"YYYYMMDDHHMM"
조회구분 1,2인 경우 필수 |
| inqryEndDt | 조회기준종료일시 | 12 | 0 | 201312312359 | 검색하고자하는 검색기준종료일시 입력
"YYYYMMDDHHMM"
조회구분 1,2인 경우 필수 |
| dminsttCd | 수요기관코드 | 7 | 0 | 9111117 | 검색하고자하는 수요기관코드
조회구분 1,2인 경우 선택 |
| dminsttNm | 수요기관명 | 200 | 0 | 경상남도교육청 경상남도거제교육지원청 거제장평중학교 | 검색하고자하는 수요기관명
조회구분 1,2인 경우 선택 |
| bizno | 사업자등록번호 | 10 | 0 | 6128306057 | 검색하고자하는 사업자등록번호
조회구분 3인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| dminsttCd | 수요기관코드 | 7 | 1 | 9111117 | 수요기관코드 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드가 표기됨 |
| vldPrdBgnDt | 유효기간시작일시 | 19 | 0 | 2012-03-15 12:00:00 | 유효기간시작일시 'YYYY-MM-DD HH:MM:SS' |
| vldPrdEndDt | 유효기간종료일시 | 19 | 0 | 2020-12-31 12:00:00 | 유효기간종료일시 'YYYY-MM-DD HH:MM:SS' |
| dminsttNm | 수요기관명 | 200 | 0 | 경상남도교육청 경상남도거제교육지원청 거제장평중학교 | 중앙조달인 경우 조달사업에 관한 법률 제2조(정의)에 따라 수요물자의 구매 공급 또는 시설공사 계약의 체결을 조달청장에게 요청할 수 있도록 조달청장이 인정하여 등록한 기관 또는 자체전자조달시스템을 이용하는 기관인 경우 계약을 의뢰한 기관의 명으로 공고기관과 수요기관이 동일할 수 있음 |
| dminsttAbrvtNm | 수요기관약어명 | 100 | 0 | 거제장평중학교 | 수요기관약어명 |
| dminsttEngNm | 수요기관영문명 | 400 | 0 | Geoje Jangpyug Junior High School | 수요기관영문명 |
| corprtRgstNo | 법인등록번호 | 13 | 0 | 1101110375818 | 법인등록번호 |
| bizno | 사업자등록번호 | 10 | 0 | 6128306057 | 사업자등록번호 |
| jrsdctnDivNm | 소관구분명 | 200 | 0 | 교육기관 | 소관구분명 |
| insttTyCdLrgclsfcNm | 기관유형코드대분류명 | 200 | 0 | 중학교 | 기관유형코드대분류명 |
| insttTyCdMidclsfcNm | 기관유형코드중분류명 | 200 | 0 | 중학교(본교) | 기관유형코드중분류명 |
| insttTyCdSmlclsfcNm | 기관유형코드소분류명 | 200 | 0 | 공립 | 기관유형코드소분류명 |
| bizcndtnNm | 업태명 | 50 | 0 | 교육업 | 업태명 |
| indstrytyNm | 업종명 | 50 | 0 | 교육 | 업종명 |
| ofclFaxNo | 담당자팩스번호 | 25 | 0 | 055-636-9436 | 담당자팩스번호 |
| rgnCd | 지역코드 | 10 | 0 | 48310 | 지역코드 |
| rgnNm | 지역명 | 200 | 0 | 경상남도 거제시 | 지역구분명 |
| zip | 우편번호 | 6 | 0 | 53270 | 우편번호 |
| adrs | 주소 | 100 | 0 | 경상남도 거제시 장평4로 | 주소 |
| dtlAdrs | 상세주소 | 100 | 0 | 40-0 (장평동) | 상세주소 |
| telNo | 전화번호 | 25 | 0 | 055-636-9434 | 전화번호 |
| faxNo | 팩스번호 | 25 | 0 | 055-636-9436 | 팩스번호 |
| hmpgAdrs | 홈페이지주소 | 255 | 0 | www.sawoman.or.kr | 홈페이지주소 |
| dltYn | 삭제여부 | 1 | 0 | N | 삭제여부 |
| toplvlInsttCd | 최상위기관코드 | 7 | 0 | 9010000 | 최상위기관코드 |
| toplvlInsttNm | 최상위기관명 | 200 | 0 | 경상남도교육청 | 최상위기관명 |
| rgstDt | 등록일시 | 19 | 0 | 2013-01-11 16:16:54 | 등록일시  "YYYY-MM-DD HH:MM:SS” |
| chgDt | 변경일시 | 19 | 0 | 2025-01-15 10:38:52 | 변경일시  "YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/UsrInfoService02/getDminsttInfo02?inqryDiv=1&inqryBgnDt=201301010000&inqryEndDt=201312312359&dminsttCd=9111117&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <dminsttCd>9111117</dminsttCd> <vldPrdBgnDt></vldPrdBgnDt> <vldPrdEndDt></vldPrdEndDt> <dminsttNm>경상남도교육청 경상남도거제교육지원청 거제장평중학교</dminsttNm> <dminsttAbrvtNm>거제장평중학교</dminsttAbrvtNm> <dminsttEngNm>Geoje Jangpyug Junior High School</dminsttEngNm> <corprtRgstNo></corprtRgstNo> <bizno>6128306057</bizno> <jrsdctnDivNm>교육기관</jrsdctnDivNm> <insttTyCdLrgclsfcNm>중학교</insttTyCdLrgclsfcNm> <insttTyCdMidclsfcNm>중학교(본교)</insttTyCdMidclsfcNm> <insttTyCdSmlclsfcNm>공립 </insttTyCdSmlclsfcNm> <bizcndtnNm></bizcndtnNm> <indstrytyNm></indstrytyNm> <ofclFaxNo>055-636-9436</ofclFaxNo> <rgnCd>48310</rgnCd> <rgnNm>경상남도 거제시</rgnNm> <zip>53270</zip> <adrs>경상남도 거제시 장평4로</adrs> <dtlAdrs>40-0 (장평동)</dtlAdrs> <telNo>055-636-9434</telNo> <faxNo>055-636-9436</faxNo> <hmpgAdrs></hmpgAdrs> <dltYn>N</dltYn> <toplvlInsttCd>9010000</toplvlInsttCd> <toplvlInsttNm>경상남도교육청</toplvlInsttNm> <rgstDt>2013-01-11 16:16:54</rgstDt> <chgDt>2025-01-15 10:38:52</chgDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[조달업체 기본정보] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 2 | 오퍼레이션명(국문) | 조달업체 기본정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntCorpBasicInfo02 |
|  | 오퍼레이션 설명 | 검색조건에 사업자등록번호와 업체명을 입력하여 사업자등록번호, 업체명, 영문업체명, 개업일시, 지역코드, 지역명, 우편번호, 주소, 상세주소, 전화번호, 팩스번호, 국가명, 홈페이지주소, 제조구분코드, 제조구분명, 종업원수, 업체업무구분코드, 업체업무구분명, 본사구분명, 등록일시, 변경일시, 고유번호증명등록여부, 대표자명 등 조달업체 기본정보 목록을 조회. |  |  |
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
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 입력
1: 등록일기준 검색
2: 변경일기준검색
3: 사업자등록번호 기준검색 |
| inqryBgnDt | 조회기준시작일시 | 12 | 0 | 200101010000 | 검색하고자하는 검색기준시작일시 입력
"YYYYMMDDHHMM"
조회구분 1,2인 경우 필수 |
| inqryEndDt | 조회기준종료일시 | 12 | 0 | 200112312359 | 검색하고자하는 검색기준종료일시 입력
"YYYYMMDDHHMM"
조회구분 1,2인 경우 필수 |
| corpNm | 업체명 | 100 | 0 | 주식회사청마토건 | 검색하고자 하는 업체명
조회구분 1,2인 경우 선택 |
| bizno | 사업자등록번호 | 10 | 0 | 6168122531 | 검색하고자 하는 사업자등록번호
조회구분 3인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| bizno | 사업자등록번호 | 10 | 1 | 6168122531 | 조달업체의 사업자등록번호 |
| corpNm | 업체명 | 100 | 0 | 주식회사청마토건 | 입찰참가자격 등록한 업체명 |
| engCorpNm | 영문업체명 | 100 | 0 | cheongma CO.LTD | 영문업체명 |
| opbizDt | 개업일시 | 19 | 0 | 1997-12-15 12:00:00 | 개업일시 "YYYY-MM-DD HH:MM:SS" |
| rgnCd | 지역코드 | 10 | 0 | 50110 | 조달업체의 지역코드 |
| rgnNm | 지역명 | 200 | 0 | 제주특별자치도 제주시 구좌읍 김녕리 | 조달업체의 지역명 |
| zip | 우편번호 | 6 | 0 | 63348 | 조달업체의 우편번호 |
| adrs | 주소 | 100 | 0 | 제주특별자치도 제주시 구좌읍 김녕남2길 | 조달업체의 주소 |
| dtlAdrs | 상세주소 | 100 | 0 | 17 (거웅 라피네2차) 105동 제비101호 | 조달업체의 상세주소 |
| telNo | 전화번호 | 25 | 0 | 064-712-0471 | 조달업체의 전화번호 |
| faxNo | 팩스번호 | 25 | 0 | 064-712-0472 | 조달업체의 팩스번호 |
| cntryNm | 국가명 | 200 | 0 | 대한민국 | 조달업체의 국가명 |
| hmpgAdrs | 홈페이지주소 | 255 | 0 | www.aaa.co.kr | 조달업체의 홈페이지주소 |
| mnfctDivCd | 제조구분코드 | 2 | 0 | 제240002 | 제조구분코드 제240001:제조
제240002:공급 |
| mnfctDivNm | 제조구분명 | 200 | 0 | 공급 | 제조구분명 |
| emplyeNum | 종업원수 | 10 | 0 | 7 | 조달업체의 종업원수 |
| corpBsnsDivCd | 업체업무구분코드 | 20 | 0 | 01,07 | 업체업무구분코드
01 : 물품 07 : 공사
05 : 용역 03 :일반용역
02,04 : 외자 |
| corpBsnsDivNm | 업체업무구분명 | 200 | 0 | 물품,공사 | 업체업무구분명 |
| hdoffceDivNm | 본사구분명 | 100 | 1 | 본사 | 본사구분명 |
| rgstDt | 등록일시 | 19 | 0 | 2001-08-22 00:00:00 | 등록일시  "YYYY-MM-DD HH:MM:SS" |
| chgDt | 변경일시 | 19 | 0 | 2025-07-26 07:00:37 | 변경일시  "YYYY-MM-DD HH:MM:SS" |
| esntlNoCertRgstYn | 고유번호증명등록여부 | 1 | 0 | N | 고유번호증명등록여부가 'Y'이면 해당 업체의 관련법규(조항조)를 알 수 있음
법령으로 지정된 비영리법인,사회복지사업,금융사업 등 등록여부
ex)장애인복지시설, 국민연금사업, 농업협동조합 |
| ceoNm | 대표자명 | 35 | 0 | 이동환 | 대표자명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/UsrInfoService02/getPrcrmntCorpBasicInfo02?inqryDiv=3&bizno=6168122531&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bizno>6168122531</bizno> <corpNm>주식회사청마토건</corpNm> <engCorpNm>cheongma CO.LTD</engCorpNm> <opbizDt>1997-12-15 00:00:00</opbizDt> <rgnCd>50110</rgnCd> <rgnNm>제주특별자치도 제주시 구좌읍 김녕리</rgnNm> <zip>63348</zip> <adrs>제주특별자치도 제주시 구좌읍 김녕남2길</adrs> <dtlAdrs>17 (거웅 라피네2차) 105동 제비101호</dtlAdrs> <telNo>064-712-0471</telNo> <faxNo>064-712-0472</faxNo> <cntryNm>대한민국</cntryNm> <hmpgAdrs></hmpgAdrs> <mnfctDivCd>제240002</mnfctDivCd> <mnfctDivNm>공급</mnfctDivNm> <emplyeNum>7</emplyeNum> <corpBsnsDivCd>01,07</corpBsnsDivCd> <corpBsnsDivNm>물품,공사</corpBsnsDivNm> <hdoffceDivNm>본사</hdoffceDivNm> <rgstDt>2001-08-22 00:00:00</rgstDt> <chgDt>2025-07-26 07:00:37</chgDt> <esntlNoCertRgstYn>N</esntlNoCertRgstYn> <ceoNm>이동환</ceoNm> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[조달업체업종정보조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 3 | 오퍼레이션명(국문) | 조달업체업종정보조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntCorpIndstrytyInfo02 |
|  | 오퍼레이션 설명 | 검색조건에 사업자등록번호를 입력하여 사업자등록번호, 업종코드, 업종명, 등록일시, 유효기간만료일시, 시스템등록일시, 변경일시, 업종상태명, 대표업종여부 등 조달업체 업종정보 목록을 조회. |  |  |
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
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 입력
1: 사업자등록번호 기준검색 2: 시스템등록일기준 검색
3:시스템변경일기준검색 |
| bizno | 사업자등록번호 | 10 | 0 | 3068134668 | 검색하고자 하는 사업자등록번호 조회구분 1 일경우 필수 |
| inqryBgnDt | 조회기준시작일시 | 12 | 0 | 201501010000 | 검색하고자하는 검색기준시작일시 입력
"YYYYMMDDHHMM"
조회구분 2,3인 경우 필수 |
| inqryEndDt | 조회기준종료일시 | 12 | 0 | 201512302359 | 검색하고자하는 검색기준종료일시 입력
"YYYYMMDDHHMM"
조회구분 2,3인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| bizno | 사업자등록번호 | 10 | 1 | 3068134668 | 사업자등록번호 |
| indstrytyNm | 업종명 | 200 | 0 | 토목공사업 | 업종명 |
| indstrytyCd | 업종코드 | 4 | 0 | 0001 | 업종코드 |
| rgstDt | 등록일시 | 19 | 0 | 2014-03-04 00:00:00 | 등록일시 “YYYY-MM-DD HH:MM:SS” |
| vldPrdExprtDt | 유효기간만료일시 | 19 | 0 | 2017-04-02 00:00:00 | 유효기간만료일시 “YYYY-MM-DD HH:MM:SS” |
| systmRgstDt | 시스템등록일시 | 19 | 0 | 2014-03-10 16:40:27 | 시스템등록일시 “YYYY-MM-DD HH:MM:SS” |
| chgDt | 변경일시 | 19 | 0 | 2015-07-31 17:36:49 | 변경일시 “YYYY-MM-DD HH:MM:SS” |
| indstrytyStatsNm | 업종상태명 | 200 | 0 | 유효기간 경과 | 업종상태명 |
| rprsntIndstrytyYn | 대표업종여부 | 1 | 0 | N | 대표업종여부 [Y/N] |
| systmChgDt | 시스템변경일시 | 19 | 0 | 2014-03-10 16:40:27 | 시스템변경일시 “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/UsrInfoService02/getPrcrmntCorpIndstrytyInfo02?bizno=3068134668&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bizno>3068134668</bizno> <indstrytyNm>토목공사업</indstrytyNm> <indstrytyCd>0001</indstrytyCd> <rgstDt>2014-03-04 00:00:00</rgstDt> <vldPrdExprtDt>2017-04-02 00:00:00</vldPrdExprtDt> <systmRgstDt>2014-03-10 16:40:27</systmRgstDt> <chgDt>2015-07-31 17:36:49</chgDt> <indstrytyStatsNm>유효기간 경과</indstrytyStatsNm> <rprsntIndstrytyYn>N</rprsntIndstrytyYn> <systmChgDt>2014-03-10 16:40:27</systmChgDt> </item> <item> <bizno>3068134668</bizno> <indstrytyNm>건축공사업</indstrytyNm> <indstrytyCd>0002</indstrytyCd> <rgstDt>2015-12-22 00:00:00</rgstDt> <vldPrdExprtDt></vldPrdExprtDt> <systmRgstDt>1900-01-01 00:00:00</systmRgstDt> <chgDt>2018-06-01 17:36:45</chgDt> <indstrytyStatsNm></indstrytyStatsNm> <rprsntIndstrytyYn>Y</rprsntIndstrytyYn> <systmChgDt>2015-12-23 11:28:12</systmChgDt> </item> <item> <bizno>3068134668</bizno> <indstrytyNm>토목건축공사업</indstrytyNm> <indstrytyCd>0003</indstrytyCd> <rgstDt>2014-03-04 00:00:00</rgstDt> <vldPrdExprtDt>2017-04-02 00:00:00</vldPrdExprtDt> <systmRgstDt>2014-03-10 16:40:27</systmRgstDt> <chgDt>2015-07-31 17:36:49</chgDt> <indstrytyStatsNm>유효기간 경과</indstrytyStatsNm> <rprsntIndstrytyYn>N</rprsntIndstrytyYn> <systmChgDt>2014-03-10 16:40:27</systmChgDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>3</totalCount> </body> </response> |

[조달업체공급물품정보조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 4 | 오퍼레이션명(국문) | 조달업체공급물품정보조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntCorpSplyPrdctInfo02 |
|  | 오퍼레이션 설명 | 검색조건에 사업자등록번호를 입력하여 사업자등록번호, 세부품명, 세부품명번호, 등록일시, 변경일시, 대표품명여부 등 조달업체 공급물품정보 목록을 조회. |  |  |
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
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 입력
1: 사업자등록번호 기준검색 2: 등록일기준 검색
3: 변경일기준검색 |
| bizno | 사업자등록번호 | 10 | 0 | 8633000223 | 검색하고자 하는 사업자등록번호 |
| inqryBgnDt | 조회기준시작일시 | 12 | 0 | 202404010000 | 검색하고자하는 검색기준시작일시 입력
"YYYYMMDDHHMM"
조회구분 2,3인 경우 필수 |
| inqryEndDt | 조회기준종료일시 | 12 | 0 | 202404302359 | 검색하고자하는 검색기준종료일시 입력
"YYYYMMDDHHMM"
조회구분 2,3인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| bizno | 사업자등록번호 | 10 | 1 | 8633000223 | 사업자등록번호 |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 프린트및복사용지 | 세부품명번호 10자리에 대한 한글명 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 1411150701 | 품명에 대한 분류번호로서 10자리로 표시 |
| rgstDt | 등록일시 | 19 | 0 | 2024-04-19 09:12:58 | 조달업체에 대한 세부품명 등록일시 “YYYY-MM-DD HH:MM:SS” |
| chgDt | 변경일시 | 19 | 0 | 2024-04-19 09:12:58 | 조달업체에 대한 세부품명 변경일시 “YYYY-MM-DD HH:MM:SS” |
| rprsntPrdctClsfcNoNmYn | 대표품명여부 | 1 | 0 | N | 조달업체에 대한 세부품명의 대표여부 [Y/N] |
| mnfctYn | 제조여부 | 1 | 0 | N | 조달업체에 대한 세부품명의 제조물품여부[Y/N] |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/UsrInfoService02/getPrcrmntCorpSplyPrdctInfo02?bizno=8633000223&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bizno>8633000223</bizno> <dtilPrdctClsfcNoNm>프린트및복사용지</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNo>1411150701</dtilPrdctClsfcNo> <rgstDt>2024-04-19 09:12:58</rgstDt> <chgDt>2024-04-19 09:12:58</chgDt> <rprsntPrdctClsfcNoNmYn>N</rprsntPrdctClsfcNoNmYn> <mnfctYn>N</mnfctYn> </item> <item> <bizno>8633000223</bizno> <dtilPrdctClsfcNoNm>물티슈</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNo>1411170102</dtilPrdctClsfcNo> <rgstDt>2024-04-19 08:58:55</rgstDt> <chgDt>2024-04-19 08:58:55</chgDt> <rprsntPrdctClsfcNoNmYn>N</rprsntPrdctClsfcNoNmYn> <mnfctYn>N</mnfctYn> </item> <item> <bizno>8633000223</bizno> <dtilPrdctClsfcNoNm>화장실용화장지</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNo>1411170401</dtilPrdctClsfcNo> <rgstDt>2024-04-19 09:12:32</rgstDt> <chgDt>2024-04-19 09:12:32</chgDt> <rprsntPrdctClsfcNoNmYn>N</rprsntPrdctClsfcNoNmYn> <mnfctYn>N</mnfctYn> </item> <item> <bizno>8633000223</bizno> <dtilPrdctClsfcNoNm>커피메이커</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNo>4810150501</dtilPrdctClsfcNo> <rgstDt>2024-04-19 09:11:49</rgstDt> <chgDt>2024-04-19 09:11:49</chgDt> <rprsntPrdctClsfcNoNmYn>N</rprsntPrdctClsfcNoNmYn> <mnfctYn>N</mnfctYn> </item> <item> <bizno>8633000223</bizno> <dtilPrdctClsfcNoNm>선물세트</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNo>4910160202</dtilPrdctClsfcNo> <rgstDt>2024-01-24 12:03:10</rgstDt> <chgDt>2024-01-24 12:03:10</chgDt> <rprsntPrdctClsfcNoNmYn>N</rprsntPrdctClsfcNoNmYn> <mnfctYn>N</mnfctYn> </item> <item> <bizno>8633000223</bizno> <dtilPrdctClsfcNoNm>종합운동기구</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNo>4920161101</dtilPrdctClsfcNo> <rgstDt>2024-06-01 15:18:52</rgstDt> <chgDt>2024-06-01 15:18:52</chgDt> <rprsntPrdctClsfcNoNmYn>N</rprsntPrdctClsfcNoNmYn> <mnfctYn>N</mnfctYn> </item> <item> <bizno>8633000223</bizno> <dtilPrdctClsfcNoNm>기타운동경기용구</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNo>4922158201</dtilPrdctClsfcNo> <rgstDt>2024-06-01 15:26:16</rgstDt> <chgDt>2024-06-01 15:26:16</chgDt> <rprsntPrdctClsfcNoNmYn>N</rprsntPrdctClsfcNoNmYn> <mnfctYn>N</mnfctYn> </item> <item> <bizno>8633000223</bizno> <dtilPrdctClsfcNoNm>성인용기저귀</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNo>5310230601</dtilPrdctClsfcNo> <rgstDt>2024-01-24 12:02:24</rgstDt> <chgDt>2024-01-24 12:02:24</chgDt> <rprsntPrdctClsfcNoNmYn>N</rprsntPrdctClsfcNoNmYn> <mnfctYn>N</mnfctYn> </item> <item> <bizno>8633000223</bizno> <dtilPrdctClsfcNoNm>손톱깎이</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNo>5313162101</dtilPrdctClsfcNo> <rgstDt>2024-02-22 12:42:38</rgstDt> <chgDt>2024-02-22 12:42:38</chgDt> <rprsntPrdctClsfcNoNmYn>N</rprsntPrdctClsfcNoNmYn> <mnfctYn>N</mnfctYn> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>9</totalCount> </body> </response> |

[부정당재제업체정보조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 5 | 오퍼레이션명(국문) | 부정당재제업체정보조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getUnptRsttCorpInfo02 |
|  | 오퍼레이션 설명 | 부정당 제재 업체 정보를 사업자등록번호, 재재시작일자 검색조건으로 사업자등록번호, 업체명,법인등록번호, 제재시작일자, 재재종료일자, 재재기관명, 계약법구분,제재근거법률,조항호, 조항호코드, 조항호코드명,시행규칙코드, 시행규칙코드명 목록을 조회. (나라장터미등록업체 , 개인에 대한 부정당제재는 미제공) [국가계약법] –조회시점에 제재만료,해제된 것은 제공되지 않습니다. [지방계약법]-조회시점에 정지,제재만료,해제된 것은 제공되지 않습니다. |  |  |
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
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 입력
1: 사업자등록번호 기준검색 2: 재재시작일자 검색 |
| bizno | 사업자등록번호 | 10 | 0 | 1198686612 | 검색하고자 하는 사업자등록번호 조회구분 1인 경우 필수 |
| inqryBgnDt | 조회기준시작일시 | 12 | 0 | 202508240000 | 검색하고자하는 검색기준시작일시 "YYYYMMDDHHMM"
조회구분 2인 경우 필수 
국가계약법: 최초제재시작일자(201609030000) 
지방계약법: 최초제재시작일자(201609030000) |
| inqryEndDt | 조회기준종료일시 | 12 | 0 | 202511242359 | 검색하고자하는 검색기준종료일시 "YYYYMMDDHHMM"
조회구분 2인 경우 필수 
국가계약법: 최초제재시작일자(201609030000) 
지방계약법: 최초제재시작일자(201609030000) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| bizno | 사업자등록번호 | 10 | 1 | 1198686612 | 사업자등록번호 |
| corpNm | 업체명 | 200 | 1 | 이엠티씨 주식회사 | 부정당업체명 |
| unptRsttDocNm | 제재문서명 | 200 | 0 | 규제개혁법무담당관-13809, 13810 | 부정당제재처리 문서명 |
| rsttBgnDate | 제재시작일자 | 10 | 0 | 2025-10-15 | 제재시작일자("YYYY-MM-DD") |
| rsttEndDate | 제재종료일자 | 10 | 0 | 2026-02-14 | 제재종료일자("YYYY-MM-DD") |
| insttCd | 수요기관코드 | 7 | 0 | 1230000 | 제재 통보 수요기관코드 |
| insttNm | 수요기관명 | 400 | 0 | 조달청 | 제재 통보 수요기관명 |
| lawordNm | 근거법령명 | 500 | 0 | 지방계약법-부정당제재근거법령 | 재재근거 계약법 구분-근거 법률 |
| lawordArtclClause | 조항호 | 50 | 0 | 법31조제1항9호 나목 영92조제2항2호가목 | 조항호 |
| lawordArtclClauseCd | 조항호코드 | 50 | 0 | 002-092-002-002-가-20190625 | 조항호코드 |
| lawordArtclClauseCdNm | 조항호코드명 | 4000 | 0 | 정당한 이유 없이 낙찰된 후 계약을 체결하지 않은 자 또는 계약을 체결한 이후 계약이행(제42조제2항에 따른 계약이행능력 심사 또는 제42조의3제2항에 따른 평가를 위해 제출한 하도급관리계획 및 외주근로자 근로조건 이행계획에 관한 사항, 제88조에 따른 공동계약에 관한 사항 및 「건설산업기본법」 제31조의2에 따른 하도급계획에 관한 사항의 이행을 포함한다)을 하지 않거나 계약서에 정한 조건을 위반하여 이행한 자 | 조항호코드명 |
| enfcAtcsCd | 시행규칙코드 | 200 | 0 | [별표 2]17가 | 시행령규칙코드 |
| enfcPrvNm | 시행규칙코드명 | 500 | 0 | 정당한 이유 없이 계약을 체결하지 않은 자 또는 계약을 체결한 후 계약이행을 하지 않은 자 | 시행령규칙코드명 |
| ntfcnDt | 게재일시 | 19 | 0 | 2025-09-30 09:28 | 부정당 제제 업체 정보 게재한 일시
 “YYYY-MM-DD HH:MM:SS” |
| rsttPrdMonthNum | 제재기간개월수 | 22 | 0 | 4 | 제재시작월과 종료월간의 개월 수 |
| rsttPrdDayNum | 제재기간일수 | 22 | 0 | 0 | 제재시작일과 종료일간의 일수 |
| rsttProgrsNm | 제재상태명 | 500 | 0 | 제재 | 부정당 제제 이력의 최종 상태명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/UsrInfoService02/getUnptRsttCorpInfo02?inqryDiv=1&bizno=1198686612&numOfRows=999&pageNo=1&type=xml&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <unptRsttDocNm>규제개혁법무담당관-13809, 13810</unptRsttDocNm> <bizno>1198686612</bizno> <corpNm>이엠티씨 주식회사</corpNm> <rsttBgnDate>2025-10-15</rsttBgnDate> <rsttEndDate>2026-02-14</rsttEndDate> <insttCd>1230000</insttCd> <insttNm>조달청</insttNm> <lawordNm>지방계약법-부정당제재근거법령</lawordNm> <lawordArtclClause>법31조제1항9호 나목 영92조제2항2호가목</lawordArtclClause> <lawordArtclClauseCd>002-092-002-002-가-20190625</lawordArtclClauseCd> <lawordArtclClauseCdNm>정당한 이유 없이 낙찰된 후 계약을 체결하지 않은 자 또는 계약을 체결한 이후 계약이행(제42조제2항에 따른 계약이행능력 심사 또는 제42조의3제2항에 따른 평가를 위해 제출한 하도급관리계획 및 외주근로자 근로조건 이행계획에 관한 사항, 제88조에 따른 공동계약에 관한 사항 및 「건설산업기본법」 제31조의2에 따른 하도급계획에 관한 사항의 이행을 포함한다)을 하지 않거나 계약서에 정한 조건을 위반하여 이행한 자</lawordArtclClauseCdNm> <enfcAtcsCd>[별표 2]17가</enfcAtcsCd> <enfcPrvNm>정당한 이유 없이 계약을 체결하지 않은 자 또는 계약을 체결한 후 계약이행을 하지 않은 자</enfcPrvNm> <ntfcnDt>2025-09-30 09:28</ntfcnDt> <rsttPrdMonthNum>4</rsttPrdMonthNum> <rsttPrdDayNum>0</rsttPrdDayNum> <rsttProgrsNm>제재</rsttProgrsNm> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

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

