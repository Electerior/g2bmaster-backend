# 조달청_OpenAPI참고자료_나라장터_사전규격정보서비스_1.0

> 출처: 공공데이터포털 참고문서(docx) 자동 변환

조달청 공공데이터 개방
OpenAPI 참고자료
목 차목 차

### 1. 서비스 명세	3


### 1.1 나라장터 사전규격정보서비스	3

가. 서비스 개요	3
나. 오퍼레이션 목록	5
개정 이력

| 버 전 | 변경일 | 변경 구분 | 변경사유 |
|---|---|---|---|
| 1.0 | 2025 | 최초 개정 | 최초 개정 |
| 1.0 | 2026.08 | 현행화 | 샘플데이터, 항목설명 등 나라장터 차세대정보로 현행화 |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |


### 1. 서비스 명세


### 1.1 나라장터 사전규격정보서비스

서비스 개요

| 서비스 정보 | 서비스 ID | HrcspSsstndrdInfoService |  |  |
|---|---|---|---|---|
|  | 서비스명(국문) | 나라장터 사전규격정보서비스 |  |  |
|  | 서비스명(영문) | HrcspSsstndrdInfoService |  |  |
|  | 서비스 설명 | 물품, 용역, 외자, 공사 업무별로 나라장터에 공개된 사전규격정보를 제공하는 서비스로 업무별 사전규격 전체목록 및 기관별, 품목별로 사전규격을 조회할 수 있으며 사전규격등록번호, 품명(사업명), 배정예산액, 관련규격서파일, 규격서 의견 등을 제공하는 나라장터 사전규격정보서비스 |  |  |
| 서비스 보안 | 서비스 인증/권한 | [O] 서비스 Key[ ] 인증서 (GPKI) [] Basic (ID/PW) [ ] 없음 | [ ]WS-Security |  |
|  | 메시지 레벨 암호화 | [  ] 전자서명	[ ] 암호화	[O] 없음 |  |  |
|  | 전송 레벨 암호화 | [  ] SSL			[ O] 없음 |  |  |
| 적용 기술 수준 | 인터페이스 표준 | [  ] SOAP 1.2 (RPC-Encoded, Document Literal, Document Literal Wrapped) [ O ] REST (GET) [ ] RSS 1.0 [ ] RSS 2.0 [ ] Atom 1.0 [ ] 기타 |  |  |
|  | 교환 데이터 표준 | [ O ] XML	[ O ] JSON	[ ] MIME	[ ] MTOM |  |  |
| 서비스 URL | 개발환경 | http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService |  |  |
|  | 운영환경 | http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService |  |  |
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
| 1 | 나라장터 사전규격정보서비스 | getPublicPrcureThngInfoThng | 사전규격 물품 목록 조회 | N/A |
| 2 |  | getInsttAcctoThngListInfoThng | 사전규격 물품 기관별 목록 조회 | N/A |
| 3 |  | getThngDetailMetaInfoThng | 사전규격 물품 품목별 목록 조회 | N/A |
| 4 |  | getPublicPrcureThngInfoFrgcpt | 사전규격 외자 목록 조회 | N/A |
| 5 |  | getInsttAcctoThngListInfoFrgcpt | 사전규격 외자 기관별 목록 조회 | N/A |
| 6 |  | getThngDetailMetaInfoFrgcpt | 사전규격 외자 품목별 목록 조회 | N/A |
| 7 |  | getPublicPrcureThngInfoServc | 사전규격 용역 목록 조회 | N/A |
| 8 |  | getInsttAcctoThngListInfoServc | 사전규격 용역 기관별 목록 조회 | N/A |
| 9 |  | getThngDetailMetaInfoServc | 사전규격 용역 품목별 목록 조회 | N/A |
| 10 |  | getPublicPrcureThngInfoCnstwk | 사전규격 공사 목록 조회 | N/A |
| 11 |  | getInsttAcctoThngListInfoCnstwk | 사전규격 공사 기관별 목록 조회 | N/A |
| 12 |  | getThngDetailMetaInfoCnstwk | 사전규격 공사 품목별 목록 조회 | N/A |
| 13 |  | getPublicPrcureThngInfoThngPPSSrch | 나라장터 검색조건에 의한 사전규격 물품 목록 조회 | N/A |
| 14 |  | getPublicPrcureThngInfoFrgcptPPSSrch | 나라장터 검색조건에 의한 사전규격 외자 목록 조회 | N/A |
| 15 |  | getPublicPrcureThngInfoServcPPSSrch | 나라장터 검색조건에 의한 사전규격 용역 목록 조회 | N/A |
| 16 |  | getPublicPrcureThngInfoCnstwkPPSSrch | 나라장터 검색조건에 의한 사전규격 공사 목록 조회 | N/A |
| 17 |  | getPublicPrcureThngOpinionInfoThng | 나라장터 사전규격 물품 규격서 의견 목록 조회 | N/A |
| 18 |  | getPublicPrcureThngOpinionInfoFrgcpt | 나라장터 사전규격 외자 규격서 의견 목록 조회 | N/A |
| 19 |  | getPublicPrcureThngOpinionInfoServc | 나라장터 사전규격 용역 규격서 의견 목록 조회 | N/A |
| 20 |  | getPublicPrcureThngOpinionInfoCnstwk | 나라장터 사전규격 공사 규격서 의견 목록 조회 | N/A |

[사전규격 물품 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 1 | 오퍼레이션명(국문) | 사전규격 물품 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngInfoThng |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일시범위, 변경일시범위, 사전규격등록번호로 입력하여 물품에 대한 사전규격등록번호, 품명, 발주기관명, 수요기관명, 관련 규격문서파일 등 나라장터 사전규격정보목록을 조회 |  |  |
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
1. 등록일시
2. 사전규격등록번호 3. 변경일시 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 조회시작일시 'YYYYMMDDHHMM'
조회구분이 1과 3인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 조회종료일시 'YYYYMMDDHHMM'
조회구분이 1과 3인 경우 필수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R25BD00086373 | 검색하고자하는 사전규격등록번호
조회구분이 2인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 물품 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 도로시설처-5711 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년 안양천교(독산2교) 외 3개소 아스팔트 콘크리트 구매 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울시설공단 | 발주기관의 명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 서울시설공단 | 실수요기관의 명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 329076000 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 07:36:09 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-06 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 02-2290-4603 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 전윤희 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-12-31 00:00:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086373 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| prdctDtlList | 물품상세목록 | 4000 | 0 | [1^3011159701^아스팔트콘크리트] | 물품상세목록
[사전규격물품순번^세부품명번호^세부품명],[사전규격물품순번^세부품명번호^세부품명] |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 07:36:08 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 07:36:09 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00944582 | 관련된 입찰공고번호 목록 ‘입찰공고번호1,입찰공고번호2’ |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngInfoThng?inqryDiv=2& bfSpecRgstNo=R25BD00086373&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>물품</bsnsDivNm> <refNo>도로시설처-5711</refNo> <prdctClsfcNoNm>2025년 안양천교(독산2교) 외 3개소 아스팔트 콘크리트 구매</prdctClsfcNoNm> <orderInsttNm>서울시설공단</orderInsttNm> <rlDminsttNm>서울시설공단</rlDminsttNm> <asignBdgtAmt>329076000</asignBdgtAmt> <rcptDt>2025-07-01 07:36:09</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>02-2290-4603</ofclTelNo> <ofclNm>전윤희</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt>2025-12-31 00:00:00</dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086373</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3></specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <prdctDtlList>[1^3011159701^아스팔트콘크리트]</prdctDtlList> <rgstDt>2025-07-01 07:36:08</rgstDt> <chgDt>2025-07-01 07:36:09</chgDt> <bidNtceNoList>R25BK00944582</bidNtceNoList> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 물품 기관별 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 2 | 오퍼레이션명(국문) | 사전규격 물품 기관별 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getInsttAcctoThngListInfoThng |
|  | 오퍼레이션 설명 | 물품에 대한 사전규격정보를 기관별로 조회할 수 있는 오퍼레이션으로 검색조건을 등록일시범위, 발주기관명, 실수요기관명 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 물품에 대한 나라장터 사전규격정보 기관별 목록을 조회 |  |  |
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
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자 하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자 하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울시설공단 | 검색하고자 하는 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 서울시설공단 | 검색하고자 하는 실수요기관명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 물품 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 도로시설처-5711 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년 안양천교(독산2교) 외 3개소 아스팔트 콘크리트 구매 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울시설공단 | 발주기관의 명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 서울시설공단 | 실수요기관의 명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 329076000 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 07:36:09 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-06 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 02-2290-4603 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 전윤희 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-12-31 00:00:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086373 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| prdctDtlList | 물품상세목록 | 4000 | 0 | [1^3011159701^아스팔트콘크리트] | 물품상세목록
[사전규격물품순번^세부품명번호^세부품명],[사전규격물품순번^세부품명번호^세부품명] |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 07:36:08 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 07:36:09 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getInsttAcctoThngListInfoThng?inqryBgnDt=202507010000&inqryEndDt=202507012359&numOfRows=10&pageNo=1&orderInsttNm=서울시설공단&rlDminsttNm=서울시설공단&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>물품</bsnsDivNm> <refNo>도로시설처-5711</refNo> <prdctClsfcNoNm>2025년 안양천교(독산2교) 외 3개소 아스팔트 콘크리트 구매</prdctClsfcNoNm> <orderInsttNm>서울시설공단</orderInsttNm> <rlDminsttNm>서울시설공단</rlDminsttNm> <asignBdgtAmt>329076000</asignBdgtAmt> <rcptDt>2025-07-01 07:36:09</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>02-2290-4603</ofclTelNo> <ofclNm>전윤희</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt>2025-12-31 00:00:00</dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086373</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3></specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <prdctDtlList>[1^3011159701^아스팔트콘크리트]</prdctDtlList> <rgstDt>2025-07-01 07:36:08</rgstDt> <chgDt>2025-07-01 07:36:09</chgDt> <bidNtceNoList>R25BK00944582</bidNtceNoList> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 물품 품목별 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 3 | 오퍼레이션명(국문) | 사전규격 물품 품목별 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getThngDetailMetaInfoThng |
|  | 오퍼레이션 설명 | 물품에 대한 사전규격정보를 품목별로 조회할 수 있는 오퍼레이션으로 검색조건을 등록일시범위, 품명으로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 물품에 대한 나라장터 사전규격정보 품목별 목록을 조회 |  |  |
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
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자 하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자 하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 가이드링 등 41종 제조구매 | 검색하고자 하는 품명(사업명)/물품분류명 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 3011159701 | 검색하고자하는 세부품명번호 |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 철도용오링 | 검색하고자하는 세부품명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 물품 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 도로시설처-5711 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년 안양천교(독산2교) 외 3개소 아스팔트 콘크리트 구매 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울시설공단 | 발주기관의 명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 서울시설공단 | 실수요기관의 명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 329076000 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 07:36:09 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-06 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 02-2290-4603 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 전윤희 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-12-31 00:00:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086373 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| prdctDtlList | 물품상세목록 | 4000 | 0 | [1^3011159701^아스팔트콘크리트] | 물품상세목록
[사전규격물품순번^세부품명번호^세부품명],[사전규격물품순번^세부품명번호^세부품명] |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 07:36:08 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 07:36:09 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getThngDetailMetaInfoThng?inqryBgnDt=202507010000&inqryEndDt=202507012359&numOfRows=10&pageNo=1&dtilPrdctClsfcNo=3011159701&dtilPrdctClsfcNoNm=아스팔트콘크리트&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>물품</bsnsDivNm> <refNo>도로시설처-5711</refNo> <prdctClsfcNoNm>2025년 안양천교(독산2교) 외 3개소 아스팔트 콘크리트 구매</prdctClsfcNoNm> <orderInsttNm>서울시설공단</orderInsttNm> <rlDminsttNm>서울시설공단</rlDminsttNm> <asignBdgtAmt>329076000</asignBdgtAmt> <rcptDt>2025-07-01 07:36:09</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>02-2290-4603</ofclTelNo> <ofclNm>전윤희</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt>2025-12-31 00:00:00</dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086373</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3></specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <prdctDtlList>[1^3011159701^아스팔트콘크리트]</prdctDtlList> <rgstDt>2025-07-01 07:36:08</rgstDt> <chgDt>2025-07-01 07:36:09</chgDt> <bidNtceNoList>R25BK00944582</bidNtceNoList> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 외자 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 4 | 오퍼레이션명(국문) | 사전규격 외자 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngInfoFrgcpt |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일시범위, 변경일시범위, 사전규격등록번호로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 외자에 대한 나라장터 사전규격정보목록을 조회 |  |  |
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
1. 등록일시
2. 사전규격등록번호
3. 변경일시 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 조회구분이 1인 경우 필수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R25BD00086462 | 검색하고자하는 사전규격등록번호 조회구분이 2인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 외자 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 총무과-7065 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 조달청 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 국립한밭대학교 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 355692 | 배정예산액(달러) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 10:43:35 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 070-4056-7330 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 070-4056-7323 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 박수정 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-07-01 10:43:35 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086462 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 10:43:33 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 10:43:35 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00954663,R25BK00972189 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngInfoFrgcpt?inqryDiv=2&bfSpecRgstNo=R25BD00086462&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>외자</bsnsDivNm> <refNo>총무과-7065</refNo> <prdctClsfcNoNm>2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매</prdctClsfcNoNm> <orderInsttNm>조달청</orderInsttNm> <rlDminsttNm>국립한밭대학교</rlDminsttNm> <asignBdgtAmt>355692</asignBdgtAmt> <rcptDt>2025-07-01 10:43:35</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>070-4056-7330</ofclTelNo> <ofclNm>박수정</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt></dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086462</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&amp;fileType=BFDTL&amp;fileSeq=1</specDocFileUrl1> <specDocFileUrl2></specDocFileUrl2> <specDocFileUrl3></specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <rgstDt>2025-07-01 10:43:33</rgstDt> <chgDt>2025-07-01 10:43:35</chgDt> <bidNtceNoList>R25BK00954663,R25BK00972189</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 외자 기관별 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 5 | 오퍼레이션명(국문) | 사전규격 외자 기관별 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getInsttAcctoThngListInfoFrgcpt |
|  | 오퍼레이션 설명 | 외자에 대한 사전규격정보를 기관별로 조회할 수 있는 오퍼레이션으로 검색조건을 등록일시범위, 발주기관명, 수요기관명으로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 외자에 대한 나라장터 사전규격정보 기관별 목록을 조회 |  |  |
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
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| orderInsttNm | 발주기관명 | 200 | 0 | 조달청 | 검색하고자하는 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 국립한밭대학교 | 검색하고자하는 실수요기관명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 외자 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 총무과-7065 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 조달청 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 국립한밭대학교 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 355692 | 배정예산액(달러) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 10:43:35 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 070-4056-7330 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 070-4056-7323 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 박수정 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-07-01 10:43:35 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086462 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 10:43:33 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 10:43:35 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00954663,R25BK00972189 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getInsttAcctoThngListInfoFrgcpt? inqryBgnDt=202507010000&inqryEndDt=202507012359&orderInsttNm=조달청&rlDminsttNm=전라남도 보건환경연구원&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>외자</bsnsDivNm> <refNo>총무과-7065</refNo> <prdctClsfcNoNm>2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매</prdctClsfcNoNm> <orderInsttNm>조달청</orderInsttNm> <rlDminsttNm>국립한밭대학교</rlDminsttNm> <asignBdgtAmt>355692</asignBdgtAmt> <rcptDt>2025-07-01 10:43:35</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>070-4056-7330</ofclTelNo> <ofclNm>박수정</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt></dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086462</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&amp;fileType=BFDTL&amp;fileSeq=1</specDocFileUrl1> <specDocFileUrl2></specDocFileUrl2> <specDocFileUrl3></specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <rgstDt>2025-07-01 10:43:33</rgstDt> <chgDt>2025-07-01 10:43:35</chgDt> <bidNtceNoList>R25BK00954663,R25BK00972189</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 외자 품목별 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 6 | 오퍼레이션명(국문) | 사전규격 외자 품목별 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getThngDetailMetaInfoFrgcpt |
|  | 오퍼레이션 설명 | 외자에 대한 사전규격정보를 품목별로 조회할 수 있는 오퍼레이션으로 검색조건을 등록일시범위, 품명으로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 외자에 대한 나라장터 사전규격정보 품목별 목록을 조회 |  |  |
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
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매 | 검색하고자하는 품명(사업명)/물품분류명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 외자 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 총무과-7065 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 조달청 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 국립한밭대학교 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 355692 | 배정예산액(달러) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 10:43:35 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 070-4056-7330 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 070-4056-7323 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 박수정 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-07-01 10:43:35 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086462 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 10:43:33 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 10:43:35 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00954663,R25BK00972189 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getThngDetailMetaInfoFrgcpt?inqryBgnDt=202507010000&inqryEndDt=202507012359&prdctClsfcNoNm=2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>외자</bsnsDivNm> <refNo>총무과-7065</refNo> <prdctClsfcNoNm>2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매</prdctClsfcNoNm> <orderInsttNm>조달청</orderInsttNm> <rlDminsttNm>국립한밭대학교</rlDminsttNm> <asignBdgtAmt>355692</asignBdgtAmt> <rcptDt>2025-07-01 10:43:35</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>070-4056-7330</ofclTelNo> <ofclNm>박수정</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt></dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086462</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&amp;fileType=BFDTL&amp;fileSeq=1</specDocFileUrl1> <specDocFileUrl2></specDocFileUrl2> <specDocFileUrl3></specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <rgstDt>2025-07-01 10:43:33</rgstDt> <chgDt>2025-07-01 10:43:35</chgDt> <bidNtceNoList>R25BK00954663,R25BK00972189</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 용역 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 7 | 오퍼레이션명(국문) | 사전규격 용역 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngInfoServc |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일시범위, 변경일시범위, 사전규격등록번호로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 용역에 대한 나라장터 사전규격정보목록을 조회 |  |  |
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
1. 등록일시
2. 사전규격등록번호
3. 변경일시 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 조회시작일시 'YYYYMMDDHHMM' 조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 조회종료일시 'YYYYMMDDHHMM' 조회구분이 1인 경우 필수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R25BD00086379 | 검색하고자하는 사전규격등록번호 조회구분이 2인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 일반용역 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 경영기획단-4742 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년 가명정보 활용 컨설팅 지원 용역 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 118628232 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 08:18:23 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-07 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 042-479-4123 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 이경민 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-12-12 00:00:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086379 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| prdctDtlList | 물품상세목록 | 4000 | 0 | [1^4321150102^컴퓨터서버],[2^4321150901^태블릿컴퓨터] | 물품상세목록
[사전규격물품순번^세부품명번호^세부품명],[사전규격물품순번^세부품명번호^세부품명] |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 08:18:21 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 08:18:23 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00947937 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngInfoServc?inqryDiv=2&bfSpecRgstNo=R25BD00086379&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>일반용역</bsnsDivNm> <refNo>경영기획단-4742</refNo> <prdctClsfcNoNm>2025년 가명정보 활용 컨설팅 지원 용역</prdctClsfcNoNm> <orderInsttNm>재단법인대전정보문화산업진흥원</orderInsttNm> <rlDminsttNm>재단법인대전정보문화산업진흥원</rlDminsttNm> <asignBdgtAmt>118628232</asignBdgtAmt> <rcptDt>2025-07-01 08:18:23</rcptDt> <opninRgstClseDt>2025-07-07 23:59:00</opninRgstClseDt> <ofclTelNo>042-479-4123</ofclTelNo> <ofclNm>이경민</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt>2025-12-12 00:00:00</dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086379</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=3</specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <prdctDtlList></prdctDtlList> <rgstDt>2025-07-01 08:18:21</rgstDt> <chgDt>2025-07-01 08:18:23</chgDt> <bidNtceNoList>R25BK00947937</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 용역 기관별 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 8 | 오퍼레이션명(국문) | 사전규격 용역 기관별 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getInsttAcctoThngListInfoServc |
|  | 오퍼레이션 설명 | 용역에 대한 사전규격정보를 기관별로 조회할 수 있는 오퍼레이션으로 검색조건을 등록일시범위, 발주기관명, 수요기관명으로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 용역에 대한 나라장터 사전규격정보 기관별 목록을 조회 |  |  |
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
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM' 입력값이 없을 경우 현재일로부터 하루기준 조회 * |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| orderInsttNm | 발주기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 검색하고자하는 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 검색하고자하는 실수요기관명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 일반용역 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 경영기획단-4742 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년 가명정보 활용 컨설팅 지원 용역 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 118628232 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 08:18:23 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-07 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 042-479-4123 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 이경민 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-12-12 00:00:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086379 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| prdctDtlList | 물품상세목록 | 4000 | 0 | [1^4321150102^컴퓨터서버],[2^4321150901^태블릿컴퓨터] | 물품상세목록
[사전규격물품순번^세부품명번호^세부품명],[사전규격물품순번^세부품명번호^세부품명] |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 08:18:21 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 08:18:23 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00947937 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getInsttAcctoThngListInfoServc?inqryBgnDt=201605010000&inqryEndDt=201605052359&orderInsttNm=인천항만공사&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>일반용역</bsnsDivNm> <refNo>경영기획단-4742</refNo> <prdctClsfcNoNm>2025년 가명정보 활용 컨설팅 지원 용역</prdctClsfcNoNm> <orderInsttNm>재단법인대전정보문화산업진흥원</orderInsttNm> <rlDminsttNm>재단법인대전정보문화산업진흥원</rlDminsttNm> <asignBdgtAmt>118628232</asignBdgtAmt> <rcptDt>2025-07-01 08:18:23</rcptDt> <opninRgstClseDt>2025-07-07 23:59:00</opninRgstClseDt> <ofclTelNo>042-479-4123</ofclTelNo> <ofclNm>이경민</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt>2025-12-12 00:00:00</dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086379</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=3</specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <prdctDtlList></prdctDtlList> <rgstDt>2025-07-01 08:18:21</rgstDt> <chgDt>2025-07-01 08:18:23</chgDt> <bidNtceNoList>R25BK00947937</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 용역 품목별 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 9 | 오퍼레이션명(국문) | 사전규격 용역 품목별 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getThngDetailMetaInfoServc |
|  | 오퍼레이션 설명 | 용역에 대한 사전규격정보를 품목별로 조회할 수 있는 오퍼레이션으로 검색조건을 등록일시범위, 품명, 세부품명, 세부품명번호로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 용역에 대한 나라장터 사전규격정보 품목별 목록을 조회 |  |  |
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
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 미지테러물질 추적·확인 및 특성예측 기술개발 | 검색하고자하는 품명(사업명)/물품분류명 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 8090902801 | 검색하고자하는 세부품명번호 |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 환경에너지연구조사서비스 | 검색하고자하는 세부품명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 일반용역 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 경영기획단-4742 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년 가명정보 활용 컨설팅 지원 용역 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 118628232 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 08:18:23 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-07 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 042-479-4123 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 이경민 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-12-12 00:00:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086379 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| prdctDtlList | 물품상세목록 | 4000 | 0 | [1^4321150102^컴퓨터서버],[2^4321150901^태블릿컴퓨터] | 물품상세목록
[사전규격물품순번^세부품명번호^세부품명],[사전규격물품순번^세부품명번호^세부품명] |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 08:18:21 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 08:18:23 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00947937 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getThngDetailMetaInfoServc?inqryBgnDt=201605010000&inqryEndDt=201605052359&prdctClsfcNoNm=인천항 유지준설공사&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>일반용역</bsnsDivNm> <refNo>경영기획단-4742</refNo> <prdctClsfcNoNm>2025년 가명정보 활용 컨설팅 지원 용역</prdctClsfcNoNm> <orderInsttNm>재단법인대전정보문화산업진흥원</orderInsttNm> <rlDminsttNm>재단법인대전정보문화산업진흥원</rlDminsttNm> <asignBdgtAmt>118628232</asignBdgtAmt> <rcptDt>2025-07-01 08:18:23</rcptDt> <opninRgstClseDt>2025-07-07 23:59:00</opninRgstClseDt> <ofclTelNo>042-479-4123</ofclTelNo> <ofclNm>이경민</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt>2025-12-12 00:00:00</dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086379</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=3</specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <prdctDtlList></prdctDtlList> <rgstDt>2025-07-01 08:18:21</rgstDt> <chgDt>2025-07-01 08:18:23</chgDt> <bidNtceNoList>R25BK00947937</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 공사 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 10 | 오퍼레이션명(국문) | 사전규격 공사 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngInfoCnstwk |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일시범위, 변경일시범위, 사전규격등록번호로 입력하여 공사에 대한 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 나라장터 사전규격정보목록을 조회 |  |  |
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
1. 등록일시
2. 사전규격등록번호
3. 변경일시 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 조회구분이 1인 경우 필수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R25BD00086778 | 검색하고자하는 사전규격등록번호 조회구분이 2인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 공사 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 2025-17호 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 경복대학교 남양주캠퍼스 외부석재 바닥 및 계단 보수 공사 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 경복대학교 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 경복대학교 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 154000000 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 17:28:03 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-06 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 031-570-9571 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 류형래 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-07-06 23:59:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086778 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=55 | 규격서화일5 |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 17:28:01 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 17:28:03 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00948767 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngInfoCnstwk?inqryDiv=2&bfSpecRgstNo=R25BD00086778&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>공사</bsnsDivNm> <refNo>2025-17호</refNo> <prdctClsfcNoNm>경복대학교 남양주캠퍼스 외부석재 바닥 및 계단 보수 공사</prdctClsfcNoNm> <orderInsttNm>경복대학교</orderInsttNm> <rlDminsttNm>경복대학교</rlDminsttNm> <asignBdgtAmt>154000000</asignBdgtAmt> <rcptDt>2025-07-01 17:28:03</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>031-570-9571</ofclTelNo> <ofclNm>류형래</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt></dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086778</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=3</specDocFileUrl3> <specDocFileUrl4>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=4</specDocFileUrl4> <specDocFileUrl5>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=5</specDocFileUrl5> <rgstDt>2025-07-01 17:28:01</rgstDt> <chgDt>2025-07-01 17:28:03</chgDt> <bidNtceNoList>R25BK00948767</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[사전규격 공사 기관별 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 11 | 오퍼레이션명(국문) | 사전규격 공사 기관별 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getInsttAcctoThngListInfoCnstwk |
|  | 오퍼레이션 설명 | 공사에 대한 사전규격정보를 기관별로 조회할 수 있는 오퍼레이션으로 검색조건을 등록일시범위, 발주기관명, 수요기관명으로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 공사에 대한 나라장터 사전규격정보 기관별 목록을 조회 |  |  |
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
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| orderInsttNm | 발주기관명 | 200 | 0 | 경복대학교 | 검색하고자하는 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 경복대학교 | 검색하고자하는 실수요기관명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 공사 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 2025-17호 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 경복대학교 남양주캠퍼스 외부석재 바닥 및 계단 보수 공사 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 경복대학교 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 경복대학교 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 154000000 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 17:28:03 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-06 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 031-570-9571 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 류형래 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-07-06 23:59:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086778 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=55 | 규격서화일5 |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 17:28:01 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 17:28:03 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00948767 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getInsttAcctoThngListInfoCnstwk?inqryBgnDt=201605010000&inqryEndDt=201605052359&orderInsttNm=수도행정과&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>공사</bsnsDivNm> <refNo>경주시 맑은물사업소 수도행정과 공고 제2016-59호</refNo> <prdctClsfcNoNm>광역상수도 관로이설공사(청령교 배수관로)</prdctClsfcNoNm> <orderInsttNm>경상북도 경주시 맑은물사업소 수도행정과</orderInsttNm> <rlDminsttNm>경상북도 경주시 맑은물사업소 수도행정과</rlDminsttNm> <asignBdgtAmt>30200000</asignBdgtAmt> <rcptDt>2016-05-01 17:35:33</rcptDt> <opninRgstClseDt>2016-05-06 23:59:00</opninRgstClseDt> <ofclTelNo>054-760-7811</ofclTelNo> <ofclNm>한영애</ofclNm> <swBizObjYn /> <dlvrTmlmtDt /> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>356782</bfSpecRgstNo> <specDocFileUrl1 /> <specDocFileUrl2 /> <specDocFileUrl3 /> <specDocFileUrl4 /> <specDocFileUrl5 /> <rgstDt>2016-05-01 17:35:33</rgstDt> <chgDt /> <bidNtceNoList>20160500093</bidNtceNoList> </item> <item> <bsnsDivNm>공사</bsnsDivNm> <refNo>경주시 맑은물사업소 수도행정과 공고 제2016-60호</refNo> <prdctClsfcNoNm>읍지역 상수도 노후관 개체공사(안강읍 안강리)</prdctClsfcNoNm> <orderInsttNm>경상북도 경주시 맑은물사업소 수도행정과</orderInsttNm> <rlDminsttNm>경상북도 경주시 맑은물사업소 수도행정과</rlDminsttNm> <asignBdgtAmt>140380000</asignBdgtAmt> <rcptDt>2016-05-04 12:43:23</rcptDt> <opninRgstClseDt>2016-05-09 23:59:00</opninRgstClseDt> <ofclTelNo>054-760-7811</ofclTelNo> <ofclNm>한영애</ofclNm> <swBizObjYn /> <dlvrTmlmtDt /> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>357764</bfSpecRgstNo> <specDocFileUrl1 /> <specDocFileUrl2 /> <specDocFileUrl3 /> <specDocFileUrl4 /> <specDocFileUrl5 /> <rgstDt>2016-05-04 12:43:23</rgstDt> <chgDt /> <bidNtceNoList>20160504399</bidNtceNoList> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>10</totalCount> </body> </response> |

[사전규격 공사 품목별 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 12 | 오퍼레이션명(국문) | 사전규격 공사 품목별 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getThngDetailMetaInfoCnstwk |
|  | 오퍼레이션 설명 | 공사에 대한 사전규격정보를 품목별로 조회할 수 있는 오퍼레이션으로 검색조건을 등록일시범위, 품명으로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 공사에 대한 나라장터 사전규격정보 품목별 목록을 조회 |  |  |
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
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 등록일시 기준 조회시작 ‘YYYYMMDDHHMM' 입력값이 없을 경우 현재일로부터 하루기준 조회 * |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 등록일시 기준 조회종료 ‘YYYYMMDDHHMM' * 입력값이 없을 경우 현재일로부터 하루기준 조회 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 경복대학교 남양주캠퍼스 외부석재 바닥 및 계단 보수 공사 | 검색하고자하는 품명(사업명)/물품분류명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 공사 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 2025-17호 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 경복대학교 남양주캠퍼스 외부석재 바닥 및 계단 보수 공사 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 경복대학교 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 경복대학교 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 154000000 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 17:28:03 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-06 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 031-570-9571 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 류형래 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-07-06 23:59:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086778 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=55 | 규격서화일5 |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 17:28:01 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 17:28:03 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00948767 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getThngDetailMetaInfoCnstwk?inqryBgnDt=201605010000&inqryEndDt=201605052359&prdctClsfcNoNm=관로이설공사&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>공사</bsnsDivNm> <refNo>경주시 맑은물사업소 수도행정과 공고 제2016-59호</refNo> <prdctClsfcNoNm>광역상수도 관로이설공사(청령교 배수관로)</prdctClsfcNoNm> <orderInsttNm>경상북도 경주시 맑은물사업소 수도행정과</orderInsttNm> <rlDminsttNm>경상북도 경주시 맑은물사업소 수도행정과</rlDminsttNm> <asignBdgtAmt>30200000</asignBdgtAmt> <rcptDt>2016-05-01 17:35:33</rcptDt> <opninRgstClseDt>2016-05-06 23:59:00</opninRgstClseDt> <ofclTelNo>054-760-7811</ofclTelNo> <ofclNm>한영애</ofclNm> <swBizObjYn /> <dlvrTmlmtDt /> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>356782</bfSpecRgstNo> <specDocFileUrl1 /> <specDocFileUrl2 /> <specDocFileUrl3 /> <specDocFileUrl4 /> <specDocFileUrl5 /> <rgstDt>2016-05-01 17:35:33</rgstDt> <chgDt /> <bidNtceNoList>20160500093</bidNtceNoList> </item> </itmes> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>10</totalCount> </body> </response> |

[나라장터 검색조건에 의한 사전규격 물품 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 13 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 사전규격 물품 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngInfoThngPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 접수일시범위, 사전규격등록번호, 참조번호, 공고기관코드, 공고기관명, 수요기관코드, 수요기관명, 품명, SW사업대상여부, 세부품명번호로 입력하여 물품에 대한 사전규격등록번호, 품명, 발주기관명, 수요기관명, 관련 규격문서파일 등 나라장터 사전규격정보목록을 조회 |  |  |
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
1. 접수일시
2. 사전규격등록번호
3. 참조번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 사전규격의 접수시작일자 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 사전규격의 접수종료일자 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R25BD00086373 | 검색하고자하는 사전규격의 등록번호 |
| refNo | 참조번호 | 105 | 0 | 도로시설처-5711 | 검색하고자하는 참조번호 |
| ntceInsttCd | 공고기관코드 | 7 | 0 | Z003626 | 검색하고자하는 공고기관코드 |
| ntceInsttNm | 공고기관명 | 200 | 0 | 서울시설공단 | 검색하고자하는 공고기관명 |
| dminsttCd | 수요기관코드 | 7 | 0 | Z003626 | 검색하고자하는 수요기관코드 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드 입력 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드 입력 |
| dminsttNm | 수요기관명 | 200 | 0 | 서울시설공단 | 검색하고자하는 수요기관명 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2016 업무포털 및 경영정보시스템 개선 및 유지관리 | 검색하고자하는 품명(사업명)/물품분류명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | 검색하고자하는 SW사업대상여부 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 3011159701 | 검색하고자하는 품명 분류번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 물품 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 도로시설처-5711 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년 안양천교(독산2교) 외 3개소 아스팔트 콘크리트 구매 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 서울시설공단 | 발주기관의 명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 서울시설공단 | 실수요기관의 명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 329076000 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 07:36:09 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-06 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 02-2290-4603 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 전윤희 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-12-31 00:00:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086373 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| prdctDtlList | 물품상세목록 | 4000 | 0 | [1^3011159701^아스팔트콘크리트] | 물품상세목록
[사전규격물품순번^세부품명번호^세부품명],[사전규격물품순번^세부품명번호^세부품명] |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 07:36:08 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 07:36:09 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00944582 | 관련된 입찰공고번호 목록 ‘입찰공고번호1,입찰공고번호2’ |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngInfoThngPPSSrch?inqryDiv=2&bfSpecRgstNo=R25BD00086373&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>물품</bsnsDivNm> <refNo>도로시설처-5711</refNo> <prdctClsfcNoNm>2025년 안양천교(독산2교) 외 3개소 아스팔트 콘크리트 구매</prdctClsfcNoNm> <orderInsttNm>서울시설공단</orderInsttNm> <rlDminsttNm>서울시설공단</rlDminsttNm> <asignBdgtAmt>329076000</asignBdgtAmt> <rcptDt>2025-07-01 07:36:09</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>02-2290-4603</ofclTelNo> <ofclNm>전윤희</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt>2025-12-31 00:00:00</dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086373</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086373&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3></specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <prdctDtlList>[1^3011159701^아스팔트콘크리트]</prdctDtlList> <rgstDt>2025-07-01 07:36:08</rgstDt> <chgDt>2025-07-01 07:36:09</chgDt> <bidNtceNoList>R25BK00944582</bidNtceNoList> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 사전규격 외자 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 14 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 사전규격 외자 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngInfoFrgcptPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 접수일시범위, 사전규격등록번호, 참조번호, 공고기관코드, 공고기관명, 수요기관코드, 수요기관명, 품명, SW사업대상여부로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 외자에 대한 나라장터 사전규격정보목록을 조회 |  |  |
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
1. 접수일시
2. 사전규격등록번호
3. 참조번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 사전규격의 접수시작일자 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 사전규격의 접수종료일자 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R25BD00086462 | 검색하고자하는 사전규격의 등록번호 |
| refNo | 참조번호 | 105 | 0 | 총무과-7065 | 검색하고자하는 참조번호 |
| ntceInsttCd | 공고기관코드 | 7 | 0 | 1230000 | 검색하고자하는 공고기관코드 |
| ntceInsttNm | 공고기관명 | 200 | 0 | 조달청 | 검색하고자하는 공고기관명 |
| dminsttCd | 수요기관코드 | 7 | 0 | 7008289 | 검색하고자하는 수요기관코드 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드 입력 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드 입력 |
| dminsttNm | 수요기관명 | 200 | 0 | 국립한밭대학교 | 검색하고자하는 수요기관명 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매 | 검색하고자하는 품명(사업명)/물품분류명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | 검색하고자하는 SW사업대상여부 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 외자 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 총무과-7065 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 조달청 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 국립한밭대학교 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 355692 | 배정예산액(달러) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 10:43:35 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 070-4056-7330 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 070-4056-7323 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 박수정 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-07-01 10:43:35 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086462 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 10:43:33 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 10:43:35 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00954663,R25BK00972189 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngInfoFrgcptPPSSrch?inqryDiv=2&bfSpecRgstNo=R25BD00086462&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>외자</bsnsDivNm> <refNo>총무과-7065</refNo> <prdctClsfcNoNm>2025년도 실험실습기자재 확충 사업(특이1)_고해상도 주사전자현미경 구매</prdctClsfcNoNm> <orderInsttNm>조달청</orderInsttNm> <rlDminsttNm>국립한밭대학교</rlDminsttNm> <asignBdgtAmt>355692</asignBdgtAmt> <rcptDt>2025-07-01 10:43:35</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>070-4056-7330</ofclTelNo> <ofclNm>박수정</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt></dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086462</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086462&amp;fileType=BFDTL&amp;fileSeq=1</specDocFileUrl1> <specDocFileUrl2></specDocFileUrl2> <specDocFileUrl3></specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <rgstDt>2025-07-01 10:43:33</rgstDt> <chgDt>2025-07-01 10:43:35</chgDt> <bidNtceNoList>R25BK00954663,R25BK00972189</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 사전규격 용역 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 15 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 사전규격 용역 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngInfoServcPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 접수일시범위, 사전규격등록번호, 참조번호, 공고기관코드, 공고기관명, 수요기관코드, 수요기관명, 품명, SW사업대상여부, 세부품명번호 로 입력하여 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 용역에 대한 나라장터 사전규격정보목록을 조회 |  |  |
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
1. 접수일시
2. 사전규격등록번호
3. 참조번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 사전규격의 접수시작일자 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 사전규격의 접수종료일자 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R25BD00086379 | 검색하고자하는 사전규격의 등록번호 |
| refNo | 참조번호 | 105 | 0 | R25BD00086379 | 검색하고자하는 참조번호 |
| ntceInsttCd | 공고기관코드 | 7 | 0 | B553541 | 검색하고자하는 공고기관코드 |
| ntceInsttNm | 공고기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 검색하고자하는 공고기관명 |
| dminsttCd | 수요기관코드 | 7 | 0 | B553541 | 검색하고자하는 수요기관코드 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드 입력 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드 입력 |
| dminsttNm | 수요기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 검색하고자하는 수요기관명 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년 가명정보 활용 컨설팅 지원 용역 | 검색하고자하는 품명(사업명)/물품분류명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | 검색하고자하는 SW사업대상여부 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 4321150102 | 검색하고자하는 품명 분류번호 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 일반용역 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 경영기획단-4742 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 2025년 가명정보 활용 컨설팅 지원 용역 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 재단법인대전정보문화산업진흥원 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 118628232 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 08:18:23 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-07 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 042-479-4123 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 이경민 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-12-12 00:00:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086379 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=5 | 규격서화일5 |
| prdctDtlList | 물품상세목록 | 4000 | 0 | [1^4321150102^컴퓨터서버],[2^4321150901^태블릿컴퓨터] | 물품상세목록
[사전규격물품순번^세부품명번호^세부품명],[사전규격물품순번^세부품명번호^세부품명] |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 08:18:21 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 08:18:23 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00947937 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngInfoServcPPSSrch?inqryDiv=2&bfSpecRgstNo=R25BD00086379&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>일반용역</bsnsDivNm> <refNo>경영기획단-4742</refNo> <prdctClsfcNoNm>2025년 가명정보 활용 컨설팅 지원 용역</prdctClsfcNoNm> <orderInsttNm>재단법인대전정보문화산업진흥원</orderInsttNm> <rlDminsttNm>재단법인대전정보문화산업진흥원</rlDminsttNm> <asignBdgtAmt>118628232</asignBdgtAmt> <rcptDt>2025-07-01 08:18:23</rcptDt> <opninRgstClseDt>2025-07-07 23:59:00</opninRgstClseDt> <ofclTelNo>042-479-4123</ofclTelNo> <ofclNm>이경민</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt>2025-12-12 00:00:00</dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086379</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086379&fileType=BFDTL&fileSeq=3</specDocFileUrl3> <specDocFileUrl4></specDocFileUrl4> <specDocFileUrl5></specDocFileUrl5> <prdctDtlList></prdctDtlList> <rgstDt>2025-07-01 08:18:21</rgstDt> <chgDt>2025-07-01 08:18:23</chgDt> <bidNtceNoList>R25BK00947937</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 사전규격 공사 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 16 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 사전규격 공사 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngInfoCnstwkPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 접수일시범위, 사전규격등록번호, 참조번호, 공고기관코드, 공고기관명, 수요기관코드, 수요기관명, 품명으로 입력하여 공사에 대한 사전규격등록번호, 품명, 발주기관, 수요기관, 관련 규격문서파일 등 나라장터 사전규격정보목록을 조회 |  |  |
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
1. 접수일시
2. 사전규격등록번호
3. 참조번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202507010000 | 검색하고자하는 사전규격의 접수시작일자 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202507012359 | 검색하고자하는 사전규격의 접수종료일자 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R25BD00086778 | 검색하고자하는 사전규격의 등록번호 |
| refNo | 참조번호 | 105 | 0 | 충청남도공주교육지원청 공고 제2016-29호 | 검색하고자하는 참조번호 |
| ntceInsttCd | 공고기관코드 | 7 | 0 | 7008093 | 검색하고자하는 공고기관코드 |
| ntceInsttNm | 공고기관명 | 200 | 0 | 경복대학교 | 검색하고자하는 공고기관명 |
| dminsttCd | 수요기관코드 | 7 | 0 | 7008093 | 검색하고자하는 수요기관코드 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드 입력 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드 입력 |
| dminsttNm | 수요기관명 | 200 | 0 | 경복대학교 | 검색하고자하는 수요기관명 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 경복대학교 남양주캠퍼스 외부석재 바닥 및 계단 보수 공사 | 검색하고자하는 품명(사업명)/물품분류명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bsnsDivNm | 업무구분명 | 20 | 1 | 공사 | 업무구분명 |
| refNo | 참조번호 | 105 | 0 | 2025-17호 | 참조번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 경복대학교 남양주캠퍼스 외부석재 바닥 및 계단 보수 공사 | 품명(사업명)/물품분류명 |
| orderInsttNm | 발주기관명 | 200 | 0 | 경복대학교 | 발주기관명 |
| rlDminsttNm | 실수요기관명 | 200 | 0 | 경복대학교 | 실수요기관명 |
| asignBdgtAmt | 배정예산금액 | 22 | 0 | 154000000 | 배정예산액(원화,원) |
| rcptDt | 접수일시 | 19 | 0 | 2025-07-01 17:28:03 | 접수일시 ‘YYYY-MM-DD HH:MM:SS’ |
| opninRgstClseDt | 의견등록마감일시 | 19 | 0 | 2025-07-06 23:59:00 | 의견등록마감일시 ‘YYYY-MM-DD HH:MM:SS’ |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 031-570-9571 | 사전규격정보의 담당자전화번호 |
| ofclNm | 담당자명 | 35 | 0 | 류형래 | 사전규격정보의 담당자명 |
| swBizObjYn | SW사업대상여부 | 1 | 0 | N | SW사업대상여부 |
| dlvrTmlmtDt | 납품기한일시 | 19 | 0 | 2025-07-06 23:59:00 | 납품기한일시 ‘YYYY-MM-DD HH:MM:SS’ |
| dlvrDaynum | 납품일수 | 5 | 0 | 0 | 납품일수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R25BD00086778 | 사전규격등록번호 |
| specDocFileUrl1 | 규격문서파일URL1 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=1 | 규격서화일1 |
| specDocFileUrl2 | 규격문서파일URL2 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=2 | 규격서화일2 |
| specDocFileUrl3 | 규격문서파일URL3 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=3 | 규격서화일3 |
| specDocFileUrl4 | 규격문서파일URL4 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=4 | 규격서화일4 |
| specDocFileUrl5 | 규격문서파일URL5 | 255 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=55 | 규격서화일5 |
| rgstDt | 등록일시 | 19 | 1 | 2025-07-01 17:28:01 | 등록일시  ‘YYYY-MM-DD HH:MM:SS’ |
| chgDt | 변경일시 | 19 | 0 | 2025-07-01 17:28:03 | 변경일시  ‘YYYY-MM-DD HH:MM:SS’ |
| bidNtceNoList | 입찰공고번호목록 | 1000 | 0 | R25BK00948767 | 관련된 입찰공고번호 목록 입찰공고번호1,입찰공고번호2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngInfoCnstwkPPSSrch?inqryDiv=2&bfSpecRgstNo=R25BD00086778&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bsnsDivNm>공사</bsnsDivNm> <refNo>2025-17호</refNo> <prdctClsfcNoNm>경복대학교 남양주캠퍼스 외부석재 바닥 및 계단 보수 공사</prdctClsfcNoNm> <orderInsttNm>경복대학교</orderInsttNm> <rlDminsttNm>경복대학교</rlDminsttNm> <asignBdgtAmt>154000000</asignBdgtAmt> <rcptDt>2025-07-01 17:28:03</rcptDt> <opninRgstClseDt>2025-07-06 23:59:00</opninRgstClseDt> <ofclTelNo>031-570-9571</ofclTelNo> <ofclNm>류형래</ofclNm> <swBizObjYn>N</swBizObjYn> <dlvrTmlmtDt></dlvrTmlmtDt> <dlvrDaynum>0</dlvrDaynum> <bfSpecRgstNo>R25BD00086778</bfSpecRgstNo> <specDocFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=1</specDocFileUrl1> <specDocFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=2</specDocFileUrl2> <specDocFileUrl3>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=3</specDocFileUrl3> <specDocFileUrl4>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=4</specDocFileUrl4> <specDocFileUrl5>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R25BD00086778&fileType=BFDTL&fileSeq=5</specDocFileUrl5> <rgstDt>2025-07-01 17:28:01</rgstDt> <chgDt>2025-07-01 17:28:03</chgDt> <bidNtceNoList>R25BK00948767</bidNtceNoList> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 사전규격 물품 규격서 의견 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 17 | 오퍼레이션명(국문) | 나라장터 사전규격 물품 규격서 의견 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngOpinionInfoThng |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일시범위, 사전규격등록번호로 입력하여 사전규격등록번호, 참조번호, 의견제목, 작성업체명, 작성자명, 입력일시, 작성자전화번호, 작성자이메일, 관련 규격서의견파일, 의견내용 등 나라장터 사전규격 물품 규격서 의견 목록을 조회 |  |  |
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
1. 등록일시
2. 사전규격등록번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202605010000 | 검색하고자하는 조회시작일시 'YYYYMMDDHHMM'
조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202605302359 | 검색하고자하는 조회종료일시 'YYYYMMDDHHMM'
조회구분이 1인 경우 필수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R26BD00222629 | 검색하고자하는 사전규격등록번호
조회구분이 2인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상 | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R26BD00222629 | 사전규격등록번호 |
| refNo | 참조번호 | 6 | 0 | 시흥시 회계과 | 참조번호 |
| opninNo | 의견번호 | 10 | 0 | 1 | 사전규격정보의 의견번호 |
| rplyNo | 답변번호 | 10 | 0 | 0 | 사전규격정보의 답변번호 |
| opninTitl | 의견제목 | 300 | 0 | 사전규격 공개에 대한 검토의견 및 요청사항 | 사전규격정보 의견/답변 제목 |
| mkngCorpNm | 작성업체명 | 100 | 0 | (주)씨제이대한통운 | 사전규격정보 의견/답변 제출 업체명 |
| mkrNm | 작성자명 | 35 | 0 | 박선규 | 사전규격정보 의견/답변 작성자명 |
| inptDt | 입력일시 | 19 | 0 | 2026-05-07 09:53:25 | 사전규격정보 의견/답변 등록일시 ‘YYYY-MM-DD HH:MM:SS’ |
| mkrTel | 작성자전화번호 | 25 | 0 | *********** | 사전규격정보 의견/답변 제출 작성자 전화번호 |
| mkrEmail | 작성자이메일 | 100 | 0 | sk******@cj.net | 사전규격정보 의견/답변 작성자 이메일 주소 |
| specDocOpninFileUrl1 | 규격서의견파일URL1 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00222629&opnnSqno=1&fileType=BFOPNN&fileSeq=1 | 사전규격정보 의견/답변 등록시 첨부한 파일1 |
| specDocOpninFileUrl2 | 규격서의견파일URL2 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00222629&opnnSqno=1&fileType=BFOPNN&fileSeq=2 | 사전규격정보 의견/답변 등록시 첨부한 파일2 |
| specDocOpninFileUrl3 | 규격서의견파일URL3 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00222629&opnnSqno=1&fileType=BFOPNN&fileSeq=3 | 사전규격정보 의견/답변 등록시 첨부한 파일3 |
| specDocOpninFileUrl4 | 규격서의견파일URL4 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00222629&opnnSqno=1&fileType=BFOPNN&fileSeq=4 | 사전규격정보 의견/답변 등록시 첨부한 파일4 |
| specDocOpninFileUrl5 | 규격서의견파일URL5 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00222629&opnnSqno=1&fileType=BFOPNN&fileSeq=5 | 사전규격정보 의견/답변 등록시 첨부한 파일5 |
| opninCntnts | 의견내용 | 800 | 0 | 고된 업무에 수고 많으십니다.첨부와 같이 검토의견 및 요청사항을 보내 드리오니 답변 부탁 드립니다.감사하니다. | 사전규격정보 의견/답변 내용 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngOpinionInfoThng?inqryDiv=2&bfSpecRgstNo=R26BD00222629&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bfSpecRgstNo>R26BD00222629</bfSpecRgstNo> <refNo>시흥시 회계과</refNo> <opninNo>1</opninNo> <rplyNo>0</rplyNo> <opninTitl>사전규격 공개에 대한 검토의견 및 요청사항</opninTitl> <mkngCorpNm>(주)씨제이대한통운</mkngCorpNm> <mkrNm>박선규</mkrNm> <inptDt>2026-05-07 09:53:25</inptDt> <mkrTel>***********</mkrTel> <mkrEmail> sk******@cj.net</mkrEmail> <specDocOpninFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00222629&amp;opnnSqno=1&amp;fileType=BFOPNN&amp;fileSeq=1</specDocOpninFileUrl1> <specDocOpninFileUrl2></specDocOpninFileUrl2> <specDocOpninFileUrl3></specDocOpninFileUrl3> <specDocOpninFileUrl4></specDocOpninFileUrl4> <specDocOpninFileUrl5></specDocOpninFileUrl5> <opninCntnts>고된 업무에 수고 많으십니다. 첨부와 같이 검토의견 및 요청사항을 보내 드리오니 답변 부탁 드립니다. 감사하니다.</opninCntnts> </item> <item> <bfSpecRgstNo>R26BD00222629</bfSpecRgstNo> <refNo>시흥시 회계과</refNo> <opninNo>1</opninNo> <rplyNo>1</rplyNo> <opninTitl>관련 답변</opninTitl> <mkngCorpNm>경기도 시흥시</mkngCorpNm> <mkrNm>고정모</mkrNm> <inptDt>2026-05-18 16:26:02</inptDt> <mkrTel>0313106160</mkrTel> <mkrEmail>********korea.kr</mkrEmail> <specDocOpninFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00222629&amp;opnnSqno=1&amp;anSqno=1&amp;fileType=BFOPNN&amp;fileSeq=1</specDocOpninFileUrl1> <specDocOpninFileUrl2></specDocOpninFileUrl2> <specDocOpninFileUrl3></specDocOpninFileUrl3> <specDocOpninFileUrl4></specDocOpninFileUrl4> <specDocOpninFileUrl5></specDocOpninFileUrl5> <opninCntnts>안녕하세요. 시흥시청 하수관리과 고정모입니다. 붙임과 같이 발주부서 검토의견 드립니다. 감사합니다.</opninCntnts> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[나라장터 사전규격 외자 규격서 의견 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 18 | 오퍼레이션명(국문) | 나라장터 사전규격 외자 규격서 의견 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngOpinionInfoFrgcpt |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일시범위, 사전규격등록번호로 입력하여 사전규격등록번호, 참조번호, 의견제목, 작성업체명, 작성자명, 입력일시, 작성자전화번호, 작성자이메일, 관련 규격서의견파일, 의견내용 등 나라장터 사전규격 외자 규격서 의견 목록을 조회 |  |  |
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
1. 접수일시
2. 사전규격등록번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202603010000 | 검색하고자하는 조회시작일시 'YYYYMMDDHHMM'
조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202603302359 | 검색하고자하는 조회종료일시 'YYYYMMDDHHMM'
조회구분이 1인 경우 필수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R26BD00197208 | 검색하고자하는 사전규격등록번호
조회구분이 2인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상 | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R26BD00197208 | 사전규격등록번호 |
| refNo | 참조번호 | 6 | 0 | 장비관리과-732 | 참조번호 |
| opninNo | 의견번호 | 10 | 0 | 1 | 사전규격정보의 의견번호 |
| rplyNo | 답변번호 | 10 | 0 | 1 | 사전규격정보의 답변번호 |
| opninTitl | 의견제목 | 300 | 0 | 관련 답변 | 사전규격정보 의견/답변 제목 |
| mkngCorpNm | 작성업체명 | 100 | 0 | 관세청 인천공항세관 | 사전규격정보 의견/답변 제출 업체명 |
| mkrNm | 작성자명 | 35 | 0 | 박순경 | 사전규격정보 의견/답변 작성자명 |
| inptDt | 입력일시 | 19 | 0 | 2026-03-24 14:28:38 | 사전규격정보 의견/답변 등록일시 ‘YYYY-MM-DD HH:MM:SS’ |
| mkrTel | 작성자전화번호 | 25 | 0 | 0327224782 | 사전규격정보 의견/답변 제출 작성자 전화번호 |
| mkrEmail | 작성자이메일 | 100 | 0 | Kc******@korea.kr | 사전규격정보 의견/답변 작성자 이메일 주소 |
| specDocOpninFileUrl1 | 규격서의견파일URL1 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00197208&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=1 | 사전규격정보 의견/답변 등록시 첨부한 파일1 |
| specDocOpninFileUrl2 | 규격서의견파일URL2 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00197208&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=2 | 사전규격정보 의견/답변 등록시 첨부한 파일2 |
| specDocOpninFileUrl3 | 규격서의견파일URL3 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00197208&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=3 | 사전규격정보 의견/답변 등록시 첨부한 파일3 |
| specDocOpninFileUrl4 | 규격서의견파일URL4 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00197208&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=4 | 사전규격정보 의견/답변 등록시 첨부한 파일4 |
| specDocOpninFileUrl5 | 규격서의견파일URL5 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00197208&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=5 | 사전규격정보 의견/답변 등록시 첨부한 파일5 |
| opninCntnts | 의견내용 | 800 | 0 | 사전규격등록번호 R26BD00197208 건에 대한 귀 사의 의견을 검토한 결과를 붙임과 같이 알려드립니다. | 사전규격정보 의견/답변 내용 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngOpinionInfoFrgcpt?inqryDiv=2&bfSpecRgstNo=R26BD00197208&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bfSpecRgstNo>R26BD00197208</bfSpecRgstNo> <refNo>장비관리과-732</refNo> <opninNo>1</opninNo> <rplyNo>0</rplyNo> <opninTitl>납품실적 및 무상유지보수 완화요청의건</opninTitl> <mkngCorpNm>디비디펜스</mkngCorpNm> <mkrNm>김태은</mkrNm> <inptDt>2026-03-20 12:34:30</inptDt> <mkrTel>***********</mkrTel> <mkrEmail>eu*****@daum.net</mkrEmail> <specDocOpninFileUrl1></specDocOpninFileUrl1> <specDocOpninFileUrl2></specDocOpninFileUrl2> <specDocOpninFileUrl3></specDocOpninFileUrl3> <specDocOpninFileUrl4></specDocOpninFileUrl4> <specDocOpninFileUrl5></specDocOpninFileUrl5> <opninCntnts>1. RFP 36Page 수행실적 관련하여 동일규격의 물품(양방향 X-Ray검색기)에 대해서만 수행실적으로 인정한다. 라고 되어있는 부분 완화 요청드립니다. --&gt; 요청 내용 :  거대 X-Ray장비로써 우리나라에 관세청 이외는 도입하는 기관이 거의 없고, 동일 규격의 물품에 대해서만 수행실적으로 인정하면 특정업체에게만 유리하게 적용되기때문에 수화물 검색기 납품실적으로 완화를 요청드립니다. 2. RFP 15Page무상 하자보증기간 관련하여 단, 제너레이터(예비품) 포함 부품의 무상 하자보증기간은 5년으로 한다. 라고 되어있는 부분 완화 요청드립니다. --&gt; 요청 내용 : 제너레이터 장애시 막대한 비용이 소요되기 때문에 5년을 3년으로 완화시켜주기를 요청드립니다.</opninCntnts> </item> <item> <bfSpecRgstNo>R26BD00197208</bfSpecRgstNo> <refNo>장비관리과-732</refNo> <opninNo>1</opninNo> <rplyNo>1</rplyNo> <opninTitl>관련 답변</opninTitl> <mkngCorpNm>관세청 인천공항세관</mkngCorpNm> <mkrNm>박순경</mkrNm> <inptDt>2026-03-24 14:28:38</inptDt> <mkrTel>0327224782</mkrTel> <mkrEmail>kc******@korea.kr</mkrEmail> <specDocOpninFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00197208&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=1</specDocOpninFileUrl1> <specDocOpninFileUrl2></specDocOpninFileUrl2> <specDocOpninFileUrl3></specDocOpninFileUrl3> <specDocOpninFileUrl4></specDocOpninFileUrl4> <specDocOpninFileUrl5></specDocOpninFileUrl5> <opninCntnts>사전규격등록번호 R26BD00197208 건에 대한 귀 사의 의견을 검토한 결과를 붙임과 같이 알려드립니다.</opninCntnts> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[나라장터 사전규격 용역 규격서 의견 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 19 | 오퍼레이션명(국문) | 나라장터 사전규격 용역 규격서 의견 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngOpinionInfoServc |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일시범위, 사전규격등록번호로 입력하여 사전규격등록번호, 참조번호, 의견제목, 작성업체명, 작성자명, 입력일시, 작성자전화번호, 작성자이메일, 관련 규격서의견파일, 의견내용 등 나라장터 사전규격 용역 규격서 의견 목록을 조회 |  |  |
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
1. 접수일시
2. 사전규격등록번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202603010000 | 검색하고자하는 조회시작일시 'YYYYMMDDHHMM'
조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202603302359 | 검색하고자하는 조회종료일시 'YYYYMMDDHHMM'
조회구분이 1인 경우 필수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R26BD00189302 | 검색하고자하는 사전규격등록번호
조회구분이 2인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상 | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R26BD00189302 | 사전규격등록번호 |
| refNo | 참조번호 | 6 | 0 | 이용자보호단-2026-188 | 참조번호 |
| opninNo | 의견번호 | 10 | 0 | 1 | 사전규격정보의 의견번호 |
| rplyNo | 답변번호 | 10 | 0 | 1 | 사전규격정보의 답변번호 |
| opninTitl | 의견제목 | 300 | 0 | 관련 답변 | 사전규격정보 의견/답변 제목 |
| mkngCorpNm | 작성업체명 | 100 | 0 | 한국인터넷진흥원 | 사전규격정보 의견/답변 제출 업체명 |
| mkrNm | 작성자명 | 35 | 0 | 박정기 | 사전규격정보 의견/답변 작성자명 |
| inptDt | 입력일시 | 19 | 0 | 2026-03-16 15:08:41 | 사전규격정보 의견/답변 등록일시 ‘YYYY-MM-DD HH:MM:SS’ |
| mkrTel | 작성자전화번호 | 25 | 0 | 0618201151 | 사전규격정보 의견/답변 제출 작성자 전화번호 |
| mkrEmail | 작성자이메일 | 100 | 0 | Pa*****@kisa.or.kr | 사전규격정보 의견/답변 작성자 이메일 주소 |
| specDocOpninFileUrl1 | 규격서의견파일URL1 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00189302&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=1 | 사전규격정보 의견/답변 등록시 첨부한 파일1 |
| specDocOpninFileUrl2 | 규격서의견파일URL2 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00189302&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=2 | 사전규격정보 의견/답변 등록시 첨부한 파일2 |
| specDocOpninFileUrl3 | 규격서의견파일URL3 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00189302&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=3 | 사전규격정보 의견/답변 등록시 첨부한 파일3 |
| specDocOpninFileUrl4 | 규격서의견파일URL4 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00189302&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=4 | 사전규격정보 의견/답변 등록시 첨부한 파일4 |
| specDocOpninFileUrl5 | 규격서의견파일URL5 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00189302&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=5 | 사전규격정보 의견/답변 등록시 첨부한 파일5 |
| opninCntnts | 의견내용 | 800 | 0 | 한국인터넷진흥원 입니다. 의견 주신 부분에 대하여  아래와 같이 답변 드립니다. 제시된 의견을 검토한 결과 클라우드 서비스 활용 시에는 CSAP 인증 클라우드 서비스를 사용하도록 제안요청서를 보완*하였으며, *  제안요청서 첨부 개인정보 처리와 관련된 사항은 개인정보보호법에 따라 개인정보 처리 위탁계약 체결 등을 통해 관리될 예정입니다. 본공고시  의견 반영된  제안요청서로 진행하겠습니다. 감사합니다. 박정기 드림.. | 사전규격정보 의견/답변 내용 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngOpinionInfoServc?inqryDiv=2&bfSpecRgstNo=R26BD00189302&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bfSpecRgstNo>R26BD00189302</bfSpecRgstNo> <refNo>이용자보호단-2026-188</refNo> <opninNo>1</opninNo> <rplyNo>0</rplyNo> <opninTitl>온라인 설문조사 도구 관련 건의</opninTitl> <mkngCorpNm>와이즈인컴퍼니</mkngCorpNm> <mkrNm>김원표</mkrNm> <inptDt>2026-03-03 11:09:46</inptDt> <mkrTel>025585144</mkrTel> <mkrEmail>wi*@wiseinc.co.kr</mkrEmail> <specDocOpninFileUrl1></specDocOpninFileUrl1> <specDocOpninFileUrl2></specDocOpninFileUrl2> <specDocOpninFileUrl3></specDocOpninFileUrl3> <specDocOpninFileUrl4></specDocOpninFileUrl4> <specDocOpninFileUrl5></specDocOpninFileUrl5> <opninCntnts>귀 기관에서 휴대전화 및 이메일 이용자 대상 설문조사 용역을 온라인 설문을 포함하여 발주계획이신 것으로 확인하였습니다 설문조사·만족도조사·조직/직원진단 등을 외주용역으로 수행하더라도, 개인정보 처리 책임(위탁자 감독·관리)은 발주기관에 귀속되므로, RFP/계약 단계에서 조사도구 요건을 반드시 명시해야 합니다[개인정보보호법 22조, 26조, 29조 등)] 1. 개인정보가 포함되는 조사(직원·학생·학부모 포함)는 CSAP 인증 설문도구(또는 동등한 공공 보안검증을 충족한 도구) 및 기관 승인 채널만 사용하도록 RFP에 명시. 2. 미인증/미승인 외부 서비스 및 개인계정 기반 설문도구 사용 금지를 계약 조항으로 포함(수탁자 우회 사용 차단). 3. 수탁자가 제3자 서비스(설문툴/클라우드/문자발송 등)를 이용하는 경우 재위탁으로 간주하여 사전 서면 승인·목록 제출·연쇄 계약을 의무화. 제26조(업무위탁에 따른 개인정보의 처리 제한) 4. 해외 사업자 설문도구 사용은 국외이전 이슈가 발생할 수 있으므로 원칙 금지 또는 법정 절차(예: 별도 동의 등) 충족 시에만 허용하도록 명시. 제29조(국외이전) 5. 착수 전 “사용 도구/인증서(또는 동등자료)·개인정보 처리흐름·안전조치 계획” 제출, 종료 시 “반환/파기 증적” 제출을 검수·대금지급 조건으로 설정. 제29조(안전조치의무) 6. 위반 시 계약 해지·손해배상·입찰 제한 등 제재를 명확히 하여 실효성을 확보. 개인정보란, 이름, 연락처 등을 직접 수집하는 경우 외에 결합(성별+연령대+지역+IP주소 등)도 이에 해당되므로, 대부분의 외부 솔루션을 이용한 설문조사는 이에 해당됩니다. 어떤 외부 기업이 용역을 진행하더라도 CSAP(클라우드보안인증) 인증 또는 이에 준하는 보안수준을 갖춘 조사도구를 이용해야 한다는 점을 반영해주시기 바랍니다</opninCntnts> </item> <item> <bfSpecRgstNo>R26BD00189302</bfSpecRgstNo> <refNo>이용자보호단-2026-188</refNo> <opninNo>1</opninNo> <rplyNo>1</rplyNo> <opninTitl>관련 답변</opninTitl> <mkngCorpNm>한국인터넷진흥원</mkngCorpNm> <mkrNm>박정기</mkrNm> <inptDt>2026-03-16 15:08:41</inptDt> <mkrTel>0618201151</mkrTel> <mkrEmail>pa******@kisa.or.kr</mkrEmail> <specDocOpninFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00189302&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=1</specDocOpninFileUrl1> <specDocOpninFileUrl2></specDocOpninFileUrl2> <specDocOpninFileUrl3></specDocOpninFileUrl3> <specDocOpninFileUrl4></specDocOpninFileUrl4> <specDocOpninFileUrl5></specDocOpninFileUrl5> <opninCntnts>안녕하세요 한국인터넷진흥원 입니다. 의견 주신 부분에 대하여  아래와 같이 답변 드립니다. 제시된 의견을 검토한 결과 클라우드 서비스 활용 시에는 CSAP 인증 클라우드 서비스를 사용하도록 제안요청서를 보완*하였으며, *  제안요청서 첨부 개인정보 처리와 관련된 사항은 개인정보보호법에 따라 개인정보 처리 위탁계약 체결 등을 통해 관리될 예정입니다. 본공고시  의견 반영된  제안요청서로 진행하겠습니다. 감사합니다. 감사합니다. 박정기 드림. </opninCntnts> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[나라장터 사전규격 공사 규격서 의견 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 20 | 오퍼레이션명(국문) | 나라장터 사전규격 공사 규격서 의견 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPublicPrcureThngOpinionInfoCnstwk |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일시범위, 사전규격등록번호로 입력하여 사전규격등록번호, 참조번호, 의견제목, 작성업체명, 작성자명, 입력일시, 작성자전화번호, 작성자이메일, 관련 규격서의견파일, 의견내용 등 나라장터 사전규격 공사 규격서 의견 목록을 조회 |  |  |
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
1. 접수일시
2. 사전규격등록번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202601010000 | 검색하고자하는 조회시작일시 'YYYYMMDDHHMM'
조회구분이 1인 경우 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202601302359 | 검색하고자하는 조회종료일시 'YYYYMMDDHHMM'
조회구분이 1인 경우 필수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 0 | R26BD00167845 | 검색하고자하는 사전규격등록번호
조회구분이 2인 경우 필수 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상 | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bfSpecRgstNo | 사전규격등록번호 | 13 | 1 | R26BD00167845 | 사전규격등록번호 |
| refNo | 참조번호 | 6 | 0 | 2026011400031 | 참조번호 |
| opninNo | 의견번호 | 10 | 0 | 1 | 사전규격정보의 의견번호 |
| rplyNo | 답변번호 | 10 | 0 | 0 | 사전규격정보의 답변번호 |
| opninTitl | 의견제목 | 300 | 0 | 의견등록 | 사전규격정보 의견/답변 제목 |
| mkngCorpNm | 작성업체명 | 100 | 0 | 한국정보통신공사협회 | 사전규격정보 의견/답변 제출 업체명 |
| mkrNm | 작성자명 | 35 | 0 | 김선명 | 사전규격정보 의견/답변 작성자명 |
| inptDt | 입력일시 | 19 | 0 | 2026-01-20 17:02:51 | 사전규격정보 의견/답변 등록일시 ‘YYYY-MM-DD HH:MM:SS’ |
| mkrTel | 작성자전화번호 | 25 | 0 | 0234886135 | 사전규격정보 의견/답변 제출 작성자 전화번호 |
| mkrEmail | 작성자이메일 | 100 | 0 | mn******@kica.or.kr | 사전규격정보 의견/답변 작성자 이메일 주소 |
| specDocOpninFileUrl1 | 규격서의견파일URL1 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00167845&opnnSqno=1&fileType=BFOPNN&fileSeq=1 | 사전규격정보 의견/답변 등록시 첨부한 파일1 |
| specDocOpninFileUrl2 | 규격서의견파일URL2 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00167845&opnnSqno=1&fileType=BFOPNN&fileSeq=2 | 사전규격정보 의견/답변 등록시 첨부한 파일2 |
| specDocOpninFileUrl3 | 규격서의견파일URL3 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00167845&opnnSqno=1&fileType=BFOPNN&fileSeq=3 | 사전규격정보 의견/답변 등록시 첨부한 파일3 |
| specDocOpninFileUrl4 | 규격서의견파일URL4 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00167845&opnnSqno=1&fileType=BFOPNN&fileSeq=4 | 사전규격정보 의견/답변 등록시 첨부한 파일4 |
| specDocOpninFileUrl5 | 규격서의견파일URL5 | 200 | 0 | https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00167845&opnnSqno=1&fileType=BFOPNN&fileSeq=5 | 사전규격정보 의견/답변 등록시 첨부한 파일5 |
| opninCntnts | 의견내용 | 800 | 0 | 사전규격에 대한 의견을 등록합니다. | 사전규격정보 의견/답변 내용 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/HrcspSsstndrdInfoService/getPublicPrcureThngOpinionInfoCnstwk?inqryDiv=2&bfSpecRgstNo=R26BD00167845&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bfSpecRgstNo>R26BD00167845</bfSpecRgstNo> <refNo>202601140003</refNo> <opninNo>1</opninNo> <rplyNo>0</rplyNo> <opninTitl>의견등록</opninTitl> <mkngCorpNm>한국정보통신공사협회</mkngCorpNm> <mkrNm>김선명</mkrNm> <inptDt>2026-01-20 17:02:51</inptDt> <mkrTel>0234886135</mkrTel> <mkrEmail>mn******@kica.or.kr</mkrEmail> <specDocOpninFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00167845&opnnSqno=1&fileType=BFOPNN&fileSeq=1</specDocOpninFileUrl1> <specDocOpninFileUrl2></specDocOpninFileUrl2> <specDocOpninFileUrl3></specDocOpninFileUrl3> <specDocOpninFileUrl4></specDocOpninFileUrl4> <specDocOpninFileUrl5></specDocOpninFileUrl5> <opninCntnts>사전규격에 대한 의견을 등록합니다.</opninCntnts> </item> <item> <bfSpecRgstNo>R26BD00167845</bfSpecRgstNo> <refNo>202601140003</refNo> <opninNo>1</opninNo> <rplyNo>1</rplyNo> <opninTitl>관련 답변</opninTitl> <mkngCorpNm>세종대학교 산학협력단</mkngCorpNm> <mkrNm>백영재</mkrNm> <inptDt>2026-01-21 19:02:25</inptDt> <mkrTel>0234083032</mkrTel> <mkrEmail>****@sejong.ac.kr</mkrEmail> <specDocOpninFileUrl1>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00167845&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=1</specDocOpninFileUrl1> <specDocOpninFileUrl2>https://www.g2b.go.kr/pn/pnz/pnza/UntyAtchFile/downloadFile.do?bfSpecRegNo=R26BD00167845&opnnSqno=1&anSqno=1&fileType=BFOPNN&fileSeq=2</specDocOpninFileUrl2> <specDocOpninFileUrl3></specDocOpninFileUrl3> <specDocOpninFileUrl4></specDocOpninFileUrl4> <specDocOpninFileUrl5></specDocOpninFileUrl5> <opninCntnts>안녕하십니까, 의견주신 사항에 대해 답변 드립니다. 본 공사의 물량내역에 포함된 통신설비공사의 경우, 다음과 같은 사유로 공사를 제한하거나 도급계약을 분리하지 아니합니다. 1. 정보통신공사업법 제3조제2호 및 정보통신공사업법 시행령 제4조제1항제4호에 의거, 라우터(네트워크 연결장치) 또는 허브의 증설을 수반하지 않는 5회선 이하의 근거리통신망(LAN)선로의 증설공사이므로 정보통신공사업자로 도급인을 제한하지 아니합니다. 2. 정보통신공사업법 시행령 제25조제6호 및 동 시행령 제4조제1항제4호에 의거, 도급계약 분리의 예외 사유에 해당합니다. 이에 의견 검토를 요청드리며, 추가적인 조치가 필요하다면 언제든 편히 의견주시기 바랍니다. 감사합니다.</opninCntnts> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

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
| 11 | 필수 요청 파라미터가 없음 | 요청하신 OpenAPI의 필수 파라미터가 누락되었습니다. | 기술문서를 다시 한번 확인하여 주시기 바랍니다. |
| 12 | 해당 오픈API 서비스가 없거나 폐기됨 | OpenAPI 호출시 URL이 잘못됨 | -제공기관 관리자에게 폐기된 서비스인지 확인바랍니다. 폐기된 서비스가 아니면 개발가이드에서 OpenAPI요청 URL을 다시 확인하시기 바랍니다. |
| 20 | 서비스 접근 거부 | 활용승인이 되지 않은 OpenAPI호출 | -OpenAPI활용신청정보의 승인상태를 확인하시기 바랍니다. -활용신청에 대해 제공기관 담당자가 확인 후 '승인'이후 부터 사용할 수 있습니다. -신청 후 2~3일 소요되고 결과는 회원가입 시 등록한 e-mail로 발송됩니다. |
| 22 | 서비스 요청 제한 횟수 초과 에러 | 일일 활용건수가 초과함(활용건수 증가 필요) | -OpenAPI활용신청정보의 서비스 상세기능별 일일 트래픽량을 확인하시기 바랍니다. -개발계정의 경우 제공기관에서 정의한 트래픽을 초과하여 활용할 수 없습니다. -운영계정의 경우 변경신청을 통해서 일일트래픽량을 변경 할 수 있습니다. |
| 30 | 등록되지 않은 서비스 키 | 잘못된 서비스키를 사용하였거나 서비스키를 URL인코딩하지 않음 | -OpenAPI활용신청정보의 발급받은 서비스키를 다시 확인하시기 바랍니다. - 서비스키 값이 같다면 서비스키가 URL 인코등 되었는지 다시 확인하시기 바랍니다. |
| 31 | 기한 만료된 서비스 키 | OpenAPI 사용기간이 만료됨 (활용연장신청 후 사용가능) | -OpenAPI 활용신청정보의 활용기간을 확인합니다. -활용기간이 지난 서비스는 이용할 수 없으며 연장신청을 통해 승인 받은 후 다시 이용가능 합니다. |
| 32 | 등록되지 않은 도메인명 또는 IP주소 | 활용신청한 서버의 IP와 실제 OpenAPI호출한 서버가 다를 경우 | -OpenAPI 활용신청정보의 등록된 도메인명이나 IP주소를 다시 확인합니다. -IP나 도메인의 정보를 변경하기 위해 변경신청을 할 수 있습니다. |

