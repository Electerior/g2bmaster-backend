# 조달청_OpenAPI참고자료_나라장터_가격정보현황서비스_1.1

> 출처: 공공데이터포털 참고문서(docx) 자동 변환

조달청 공공데이터 개방
OpenAPI 참고자료
목 차목 차

### 1. 서비스 명세	3


### 1.1 나라장터 가격정보현황서비스	3

가. 서비스 개요	3
나. 오퍼레이션 목록	5
개정 이력

| 버 전 | 변경일 | 변경 구분 | 변경사유 |
|---|---|---|---|
| 1.0 | 2025 | 최초 개정 | 최초 개정 |
| 1.1 | 2026.03 | 항목추가 | - 공공데이터 개방신청 노무비(lbrcst) 항목추가 * 자원분류및순수자원 |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |


### 1. 서비스 명세


### 1.1 나라장터 가격정보현황서비스

서비스 개요

| 서비스 정보 | 서비스 ID | PriceInfoService |  |  |
|---|---|---|---|---|
|  | 서비스명(국문) | 나라장터 가격정보현황서비스 |  |  |
|  | 서비스명(영문) | PriceInfoService |  |  |
|  | 서비스 설명 | 조달청에서 조사된 또는 계약된 조경수목 가격정보, 시설공통자재(토목, 건축, 기계설비, 전기, 정보통신) 가격정보 및, 토목, 건축, 기계설비 분야 시장시공가격 정보를 제공하는 서비스 |  |  |
| 서비스 보안 | 서비스 인증/권한 | [O] 서비스 Key[ ] 인증서 (GPKI) [] Basic (ID/PW) [ ] 없음 | [ ]WS-Security |  |
|  | 메시지 레벨 암호화 | [  ] 전자서명	[ ] 암호화	[O] 없음 |  |  |
|  | 전송 레벨 암호화 | [  ] SSL			[ O] 없음 |  |  |
| 적용 기술 수준 | 인터페이스 표준 | [  ] SOAP 1.2 (RPC-Encoded, Document Literal, Document Literal Wrapped) [ O ] REST (GET) [ ] RSS 1.0 [ ] RSS 2.0 [ ] Atom 1.0 [ ] 기타 |  |  |
|  | 교환 데이터 표준 | [ O ] XML	[ O ] JSON	[ ] MIME	[ ] MTOM |  |  |
| 서비스 URL | 개발환경 | http://apis.data.go.kr/1230000/ao/PriceInfoService |  |  |
|  | 운영환경 | http://apis.data.go.kr/1230000/ao/PriceInfoService |  |  |
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
| 1 | 나라장터 가격정보현황서비스 | getPriceInfoListFcltyCmmnMtrilEngrk | 시설공통자재(토목) 가격정보 | N/A |
| 2 |  | getPriceInfoListFcltyCmmnMtrilBildng | 시설공통자재(건축) 가격정보 | N/A |
| 3 |  | getPriceInfoListFcltyCmmnMtrilMchnEqp | 시설공통자재(기계설비) 가격정보 | N/A |
| 4 |  | getPriceInfoListFcltyCmmnMtrilElctyIrmc | 시설공통자재(전기, 정보통신) 가격정보 | N/A |
| 5 |  | getPriceInfoListMrktCnstrctPcEngrk | 시장시공가격(토목) 가격정보 | N/A |
| 6 |  | getPriceInfoListMrktCnstrctPcBildng | 시장시공가격(건축) 가격정보 | N/A |
| 7 |  | getPriceInfoListMrktCnstrctPcMchnEqp | 시장시공가격(기계설비) 가격정보 | N/A |
| 8 |  | getCnsttyClsfcInfoList | 공종분류및세부공종 | N/A |
| 9 |  | getStdMarkUprcinfoList | 표준시장단가및시장시공가격 | N/A |
| 10 |  | getNetRsceinfoList | 자원분류및순수자원 | N/A |
| 11 |  | getPriceInfoListFcltyCmmnMtrilTotal | 시설공통자재(종합) 가격정보 | N/A |

[시설공통자재(토목) 가격정보] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 1 | 오퍼레이션명(국문) | 시설공통자재(토목) 가격정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPriceInfoListFcltyCmmnMtrilEngrk |
|  | 오퍼레이션 설명 | 시설공통자재(토목) 가격정보의 검색조건(물품분류번호,품명,물품식별번호,규격명)을 입력하면 물품분류번호,관련부서정보,관련계약정보,가격 등 조회. |  |  |
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
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 30199997 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 100 | 0 | 복공판 | 물품분류번호 8자리에 대한 한글명 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 22146252 | 품목에 대한 고유번호로서 8자리로 표시 |
| krnPrdctNm | 규격명(한글품목명) | 200 | 0 | 복공판,MMA,1000 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prceNticeNo | 가격게시번호 | 20 | 1 | 201603300000001 | 시설공통자재(토목)의 가격게시번호 |
| nticeDt | 게시일시 | 20 | 1 | 2016-03-30 18:08 | 게시일시 'YYYY-MM-DD HH24:MI' |
| bsnsDivCd | 업무구분코드 | 2 | 1 | TM | 시설공통자재(토목)의 업무구분코드 |
| bsnsDivNm | 업무구분명 | 40 | 1 | 시설공통자재(토목) | 시설공통자재(토목)의 업무구분명 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 30199997 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 22146252 | 품목에 대한 고유번호로서 8자리로 표시 |
| invstDeptNm | 조사부서명 | 30 | 0 | 토목환경과 | 시설공통자재(토목)의 조사부서명 |
| invstDeptTelNo | 조사부서전화번호 | 20 | 0 | 070-4056-7378 | 시설공통자재(토목)의 조사부서전화번호 |
| invstOfclNm | 조사담당자명 | 10 | 0 | 정화연 | 시설공통자재(토목)의 조사담당자명 |
| prdctClsfcNoNm | 품명 | 100 | 0 | H형 MMA복공판 | 물품분류번호 8자리에 대한 한글명 |
| krnPrdctNm | 한글품목명 | 200 | 0 | 1000x1990x204 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |
| unit | 단위 | 10 | 0 | EA | 시설공통자재(토목)의 단위 |
| prce | 가격 | 25 | 0 | 490000 | 시설공통자재(토목)의 가격(원화,단위:원) |
| splyJrsdctRgnNm | 공급관할지역명 | 200 | 0 | 전지역(제주제외) | 시설공통자재(토목)의 공급관할지역명 |
| etcCntnts | 기타내용 | 1000 | 0 | 부가가치세법 제12조에 의한 면세품임(단 조경공사에 포함시 과세됨) | 시설공통자재(토목)의 기타내용 |
| mtrlcst | 재료비 | 22 | 0 | 2000 | 시설공통자재(토목)의 재료비(원화,단위:원) |
| lbrcst | 노무비 | 22 | 0 | 3000 | 시설공통자재(토목)의 노무비(원화,단위:원) |
| gnrlexpns | 경비 | 22 | 0 | 4000 | 시설공통자재(토목)의 경비(원화,단위:원) |
| vatYnNm | 부가가치세여부명 | 100 | 0 | 부가가치세별도 | 시설공통자재(토목)의 부가가치세여부명 |
| prceDiv | 가격구분 | 200 | 0 | 기타가격 | 시설공통자재(토목)의 가격구분 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 현장도착도 | 시설공통자재(토목)의 인도조건명 |
| distbStep | 유통단계 | 100 | 0 | 기타조건 | 시설공통자재(토목)의 유통단계 |
| payCndtn | 결제조건 | 100 | 0 | 기타 | 시설공통자재(토목)의 결제조건 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getPriceInfoListFcltyCmmnMtrilEngrk?prdctClsfcNo=30199997&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prceNticeNo>201603300000001</prceNticeNo> <nticeDt>2016-03-30 18:08</nticeDt> <bsnsDivCd>TM</bsnsDivCd> <bsnsDivNm>시설공통자재(토목)</bsnsDivNm> <prdctClsfcNo>30199997</prdctClsfcNo> <prdctIdntNo>22146252</prdctIdntNo> <invstDeptNm>토목환경과</invstDeptNm> <invstDeptTelNo>070-4056-7378</invstDeptTelNo> <invstOfclNm>정화연</invstOfclNm> <prdctClsfcNoNm>H형 MMA복공판</prdctClsfcNoNm> <krnPrdctNm>1000x1990x204</krnPrdctNm> <unit>EA</unit> <prce>490000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst /> <gnrlexpns /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>현장도착도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> <treekndNo /> <specThtLen /> <specWtrtbBtLen /> <specWtrtbLen /> <specBhtDmLen /> <specRootDmLen /> </item> <item> <prceNticeNo>201603300000002</prceNticeNo> <nticeDt>2016-03-30 18:08</nticeDt> <bsnsDivCd>TM</bsnsDivCd> <bsnsDivNm>시설공통자재(토목)</bsnsDivNm> <prdctClsfcNo>30199997</prdctClsfcNo> <prdctIdntNo>22146250</prdctIdntNo> <invstDeptNm>토목환경과</invstDeptNm> <invstDeptTelNo>070-4056-7378</invstDeptTelNo> <invstOfclNm>정화연</invstOfclNm> <prdctClsfcNoNm>H형 일반형복공판</prdctClsfcNoNm> <krnPrdctNm>1000×1990×200</krnPrdctNm> <unit>EA</unit> <prce>440000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst /> <gnrlexpns /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>현장도착도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>481</totalCount> </body> </response> |

[시설공통자재(건축) 가격정보] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 2 | 오퍼레이션명(국문) | 시설공통자재(건축) 가격정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPriceInfoListFcltyCmmnMtrilBildng |
|  | 오퍼레이션 설명 | 시설공통자재(건축) 가격정보의 검색조건(물품분류번호,품명,물품식별번호,규격명)을 입력하면 물품분류번호,관련부서정보,관련계약정보,가격 등 조회 |  |  |
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
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 30103698 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 100 | 0 | 각재 | 물품분류번호 8자리에 대한 한글명 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 20141038 | 품목에 대한 고유번호로서 8자리로 표시 |
| krnPrdctNm | 규격명(한글품목명) | 200 | 0 | 각재 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prceNticeNo | 가격게시번호 | 20 | 1 | 201605100001628 | 시설공통자재(건축)의 가격게시번호 |
| nticeDt | 게시일시 | 20 | 1 | 2016-05-11 18:26 | 게시일시 'YYYY-MM-DD HH24:MI' |
| bsnsDivCd | 업무구분코드 | 2 | 1 | GC | 시설공통자재(건축)의 업무구분코드 |
| bsnsDivNm | 업무구분명 | 40 | 1 | 시설공통자재(건축) | 시설공통자재(건축)의 업무구분명 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 30103698 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 20141037 | 품목에 대한 고유번호로서 8자리로 표시 |
| invstDeptNm | 조사부서명 | 30 | 0 | 건축설비과 | 시설공통자재(건축)의 조사부서명 |
| invstDeptTelNo | 조사부서전화번호 | 20 | 0 | 070-4056-7459 | 시설공통자재(건축)의 조사부서전화번호 |
| invstOfclNm | 조사담당자명 | 10 | 0 | 이화정 | 시설공통자재(건축)의 조사담당자명 |
| prdctClsfcNoNm | 품명 | 100 | 1 | 각재 | 물품분류번호 8자리에 대한 한글명 |
| krnPrdctNm | 한글품목명 | 200 | 0 | 각재 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |
| unit | 단위 | 10 | 0 | 재 | 시설공통자재(건축)의 단위 |
| prce | 가격 | 25 | 0 | 1230 | 시설공통자재(건축)의 가격(원화,단위:원) |
| splyJrsdctRgnNm | 공급관할지역명 | 200 | 0 | 전지역(제주제외) | 시설공통자재(건축)의 공급관할지역명 |
| etcCntnts | 기타내용 | 1000 | 0 | 원가계산서가격 | 시설공통자재(건축)의 기타내용 |
| mtrlcst | 재료비 | 22 | 0 | 7250 | 시설공통자재(건축)의 재료비(원화,단위:원) |
| lbrcst | 노무비 | 22 | 0 | 25581 | 시설공통자재(건축)의 노무비(원화,단위:원) |
| gnrlexpns | 경비 | 22 | 0 | 0 | 시설공통자재(건축)의 경비(원화,단위:원) |
| vatYnNm | 부가가치세여부명 | 100 | 0 | 부가가치세별도 | 시설공통자재(건축)의 부가가치세여부명 |
| prceDiv | 가격구분 | 200 | 0 | 기타가격 | 시설공통자재(건축)의 가격구분 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 납품장소도 | 시설공통자재(건축)의 인도조건명 |
| distbStep | 유통단계 | 100 | 0 | 기타조건 | 시설공통자재(건축)의 유통단계 |
| payCndtn | 결제조건 | 100 | 0 | 기타 | 시설공통자재(건축)의 결제조건 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getPriceInfoListFcltyCmmnMtrilBildng?prdctClsfcNo=30103698&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prceNticeNo>201605100001628</prceNticeNo> <nticeDt>2016-05-11 18:26</nticeDt> <bsnsDivCd>GC</bsnsDivCd> <bsnsDivNm>시설공통자재(건축)</bsnsDivNm> <prdctClsfcNo>30103698</prdctClsfcNo> <prdctIdntNo>20141037</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7459</invstDeptTelNo> <invstOfclNm>이화정</invstOfclNm> <prdctClsfcNoNm>각재</prdctClsfcNoNm> <krnPrdctNm>각재, 외송</krnPrdctNm> <unit>재</unit> <prce>1230</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst /> <gnrlexpns /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>납품장소도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> <item> <prceNticeNo>201605100001629</prceNticeNo> <nticeDt>2016-05-11 18:26</nticeDt> <bsnsDivCd>GC</bsnsDivCd> <bsnsDivNm>시설공통자재(건축)</bsnsDivNm> <prdctClsfcNo>30103698</prdctClsfcNo> <prdctIdntNo>20141038</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7459</invstDeptTelNo> <invstOfclNm>이화정</invstOfclNm> <prdctClsfcNoNm>각재</prdctClsfcNoNm> <krnPrdctNm>각재, 외송</krnPrdctNm> <unit>㎥</unit> <prce>369000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst /> <gnrlexpns /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>납품장소도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>1958</totalCount> </body> </response> |

[시설공통자재(기계설비) 가격정보] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 3 | 오퍼레이션명(국문) | 시설공통자재(기계설비) 가격정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPriceInfoListFcltyCmmnMtrilMchnEqp |
|  | 오퍼레이션 설명 | 시설공통자재(기계설비) 가격정보의 검색조건(물품분류번호,품명,물품식별번호,규격명)을 입력하면 물품분류번호,관련부서정보,관련계약정보,가격 등 조회 |  |  |
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
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 46191601 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 100 | 0 | 소화용기구 | 물품분류번호 8자리에 대한 한글명 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 20096944 | 품목에 대한 고유번호로서 8자리로 표시 |
| krnPrdctNm | 규격명(한글품목명) | 200 | 0 | 소화용기구 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prceNticeNo | 가격게시번호 | 20 | 1 | 201605100006321 | 시설공통자재(기계설비)의 가격게시번호 |
| nticeDt | 게시일시 | 20 | 1 | 2016-05-11 18:32 | 게시일시 'YYYY-MM-DD HH24:MI' |
| bsnsDivCd | 업무구분코드 | 2 | 1 | GG | 시설공통자재(기계설비)의 업무구분코드 |
| bsnsDivNm | 업무구분명 | 40 | 1 | 시설공통자재(기계설비) | 시설공통자재(기계설비)의 업무구분명 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 46191601 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 20970565 | 품목에 대한 고유번호로서 8자리로 표시 |
| invstDeptNm | 조사부서명 | 30 | 0 | 건축설비과 | 시설공통자재(기계설비)의 조사부서명 |
| invstDeptTelNo | 조사부서전화번호 | 20 | 0 | 070-4056-7409 | 시설공통자재(기계설비)의 조사부서전화번호 |
| invstOfclNm | 조사담당자명 | 10 | 0 | 조상혁 | 시설공통자재(기계설비)의 조사담당자명 |
| prdctClsfcNoNm | 품명 | 100 | 1 | 하론소화설비 | 물품분류번호 8자리에 대한 한글명 |
| krnPrdctNm | 한글품목명 | 200 | 0 | 하론소화설비, 수동조작함, DC26V | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |
| unit | 단위 | 10 | 0 | EA | 시설공통자재(기계설비)의 단위 |
| prce | 가격 | 25 | 0 | 36000 | 시설공통자재(기계설비)의 가격(원화,단위:원) |
| splyJrsdctRgnNm | 공급관할지역명 | 200 | 0 | 전지역(제주제외) | 시설공통자재(기계설비)의 공급관할지역명 |
| etcCntnts | 기타내용 | 1000 | 0 | 성능,규격 업체에문의 | 시설공통자재(기계설비)의 기타내용 |
| mtrlcst | 재료비 | 22 | 0 | 2100 | 시설공통자재(기계설비)의 재료비(원화,단위:원) |
| lbrcst | 노무비 | 22 | 0 | 19500 | 시설공통자재(기계설비)의 노무비(원화,단위:원) |
| gnrlexpns | 경비 | 22 | 0 | 0 | 시설공통자재(기계설비)의 경비(원화,단위:원) |
| vatYnNm | 부가가치세여부명 | 100 | 0 | 부가가치세별도 | 시설공통자재(기계설비)의 부가가치세여부명 |
| prceDiv | 가격구분 | 200 | 0 | 기타가격 | 시설공통자재(기계설비)의 가격구분 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 납품장소도 | 시설공통자재(기계설비)의 인도조건명 |
| distbStep | 유통단계 | 100 | 0 | 기타조건 | 시설공통자재(기계설비)의 유통단계 |
| payCndtn | 결제조건 | 100 | 0 | 기타 | 시설공통자재(기계설비)의 결제조건 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getPriceInfoListFcltyCmmnMtrilMchnEqp?prdctClsfcNo=46191601&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prceNticeNo>201605100004718</prceNticeNo> <nticeDt>2016-05-11 18:30</nticeDt> <bsnsDivCd>GG</bsnsDivCd> <bsnsDivNm>시설공통자재(기계설비)</bsnsDivNm> <prdctClsfcNo>46191601</prdctClsfcNo> <prdctIdntNo>20097109</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7409</invstDeptTelNo> <invstOfclNm>조상혁</invstOfclNm> <prdctClsfcNoNm>소화전</prdctClsfcNoNm> <krnPrdctNm>지상식 옥외 D100×D65</krnPrdctNm> <unit>EA</unit> <prce>323000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst /> <gnrlexpns /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>납품장소도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn </item> <item> <prceNticeNo>201605100004719</prceNticeNo> <nticeDt>2016-05-11 18:30</nticeDt> <bsnsDivCd>GG</bsnsDivCd> <bsnsDivNm>시설공통자재(기계설비)</bsnsDivNm> <prdctClsfcNo>46191601</prdctClsfcNo> <prdctIdntNo>20097108</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7409</invstDeptTelNo> <invstOfclNm>조상혁</invstOfclNm> <prdctClsfcNoNm>소화전</prdctClsfcNoNm> <krnPrdctNm>지하식 옥외 D100×D60</krnPrdctNm> <unit>EA</unit> <prce>170000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst /> <gnrlexpns /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>납품장소도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>1958</totalCount> </body> </response> |

[시설공통자재(전기, 정보통신) 가격정보] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 4 | 오퍼레이션명(국문) | 시설공통자재(전기, 정보통신) 가격정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPriceInfoListFcltyCmmnMtrilElctyIrmc |
|  | 오퍼레이션 설명 | 시설공통자재(전기,정보통신) 가격정보의 검색조건(물품분류번호,품명,물품식별번호,규격명)을 입력하면 물품분류번호,관련부서정보,관련계약정보,가격 등 조회 |  |  |
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
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 39131708 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 100 | 0 | 전선관피팅 | 물품분류번호 8자리에 대한 한글명 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 20174931 | 품목에 대한 고유번호로서 8자리로 표시 |
| krnPrdctNm | 규격명(한글품목명) | 200 | 0 | 관구밀폐기, 실링가스켓, D150 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prceNticeNo | 가격게시번호 | 20 | 1 | 201603110007691 | 시설공통자재(전기,정보통신)의 가격게시번호 |
| nticeDt | 게시일시 | 20 | 1 | 2016-03-10 19:48 | 게시일시 'YYYY-MM-DD HH24:MI' |
| bsnsDivCd | 업무구분코드 | 2 | 1 | JN | 시설공통자재(전기,정보통신)의 업무구분코드 |
| bsnsDivNm | 업무구분명 | 40 | 1 | 시설공통자재(전기,정보통신) | 시설공통자재(전기,정보통신)의 업무구분명 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 39131706 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 20174410 | 품목에 대한 고유번호로서 8자리로 표시 |
| invstDeptNm | 조사부서명 | 30 | 0 | 건축설비과 | 시설공통자재(전기,정보통신)의 조사부서명 |
| invstDeptTelNo | 조사부서전화번호 | 20 | 0 | 070-4056-7588 | 시설공통자재(전기,정보통신)의 조사부서전화번호 |
| invstOfclNm | 조사담당자명 | 10 | 0 | 오상봉 | 시설공통자재(전기,정보통신)의 조사담당자명 |
| prdctClsfcNoNm | 품명 | 100 | 1 | 1종금속제가요전선관 | 물품분류번호 8자리에 대한 한글명 |
| krnPrdctNm | 한글품목명 | 200 | 0 | 16mm, 비방수 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |
| unit | 단위 | 10 | 0 | m | 시설공통자재(전기,정보통신)의 단위 |
| prce | 가격 | 25 | 0 | 360 | 시설공통자재(전기,정보통신)의 가격(원화,단위:원) |
| splyJrsdctRgnNm | 공급관할지역명 | 200 | 0 | 전지역(제주제외) | 시설공통자재(전기,정보통신)의 공급관할지역명 |
| etcCntnts | 기타내용 | 1000 | 0 | 봉합비포함 | 시설공통자재(전기,정보통신)의 기타내용 |
| mtrlcst | 재료비 | 22 | 0 | 20390 | 시설공통자재(전기,정보통신)의 재료비(원화,단위:원) |
| lbrcst | 노무비 | 22 | 0 | 31000 | 시설공통자재(전기,정보통신)의 노무비(원화,단위:원) |
| gnrlexpns | 경비 | 22 | 0 | 0 | 시설공통자재(전기,정보통신)의 경비(원화,단위:원) |
| vatYnNm | 부가가치세여부명 | 100 | 0 | 부가가치세별도 | 시설공통자재(전기,정보통신)의 부가가치세여부명 |
| prceDiv | 가격구분 | 200 | 0 | 기타가격 | 시설공통자재(전기,정보통신)의 가격구분 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 납품장소도 | 시설공통자재(전기,정보통신)의 인도조건명 |
| distbStep | 유통단계 | 100 | 0 | 기타조건 | 시설공통자재(전기,정보통신)의 유통단계 |
| payCndtn | 결제조건 | 100 | 0 | 기타 | 시설공통자재(전기,정보통신)의 결제조건 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getPriceInfoListFcltyCmmnMtrilElctyIrmc?prdctClsfcNo=39131706&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prceNticeNo>201603110007691</prceNticeNo> <nticeDt>2016-03-10 19:48</nticeDt> <bsnsDivCd>JN</bsnsDivCd> <bsnsDivNm>시설공통자재(전기,정보통신)</bsnsDivNm> <prdctClsfcNo>39131706</prdctClsfcNo> <prdctIdntNo>20174409</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7588</invstDeptTelNo> <invstOfclNm>오상봉</invstOfclNm> <prdctClsfcNoNm>1종금속제가요전선관</prdctClsfcNoNm> <krnPrdctNm>12mm, 비방수</krnPrdctNm> <unit>m</unit> <prce>316</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst /> <gnrlexpns /> <prodctFld /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>납품장소도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> <item> <prceNticeNo>201603110007692</prceNticeNo> <nticeDt>2016-03-10 19:48</nticeDt> <bsnsDivCd>JN</bsnsDivCd> <bsnsDivNm>시설공통자재(전기,정보통신)</bsnsDivNm> <prdctClsfcNo>39131706</prdctClsfcNo> <prdctIdntNo>20174410</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7588</invstDeptTelNo> <invstOfclNm>오상봉</invstOfclNm> <prdctClsfcNoNm>1종금속제가요전선관</prdctClsfcNoNm> <krnPrdctNm>16mm, 비방수</krnPrdctNm> <unit>m</unit> <prce>360</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst /> <gnrlexpns /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>납품장소도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>2260</totalCount> </body> </response> |

[시장시공가격(토목) 가격정보] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 5 | 오퍼레이션명(국문) | 시장시공가격(토목) 가격정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPriceInfoListMrktCnstrctPcEngrk |
|  | 오퍼레이션 설명 | 시장시공가격(토목) 정보의 검색조건(물품분류번호,품명,물품식별번호,규격명)을 입력하면 물품분류번호,관련부서정보,관련계약정보,시장시공가격 등 조회 |  |  |
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
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 0 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 100 | 0 | ACE JOINT | 물품분류번호 8자리에 대한 한글명 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 90000000 | 품목에 대한 고유번호로서 8자리로 표시 |
| krnPrdctNm | 규격명(한글품목명) | 200 | 0 | NB-T120 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prceNticeNo | 가격게시번호 | 20 | 1 | 201603110008603 | 시장시공가격(토목)의 가격게시번호 |
| nticeDt | 게시일시 | 20 | 1 | 2016-03-10 19:48 | 게시일시 'YYYY-MM-DD HH24:MI' |
| bsnsDivCd | 업무구분코드 | 2 | 1 | S1 | 시장시공가격(토목)의 업무구분코드 |
| bsnsDivNm | 업무구분명 | 40 | 1 | 시장시공가격(토목) | 시장시공가격(토목)의 업무구분명 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 0 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 90000000 | 품목에 대한 고유번호로서 8자리로 표시 |
| invstDeptNm | 조사부서명 | 30 | 0 | 예산사업관리과 | 시장시공가격(토목)의 조사부서명 |
| invstDeptTelNo | 조사부서전화번호 | 20 | 0 | 070-4056-7239 | 시장시공가격(토목)의 조사부서전화번호 |
| invstOfclNm | 조사담당자명 | 10 | 0 | 윤재영 | 시장시공가격(토목)의 조사담당자명 |
| prdctClsfcNoNm | 품명 | 100 | 1 | ACE JOINT | 물품분류번호 8자리에 대한 한글명 |
| krnPrdctNm | 한글품목명 | 200 | 0 | NB-T120 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |
| unit | 단위 | 10 | 0 | M | 시장시공가격(토목)의 단위 |
| prce | 가격 | 25 | 0 | 948000 | 시장시공가격(토목)의 가격(원화,단위:원) |
| splyJrsdctRgnNm | 공급관할지역명 | 200 | 0 | 전지역(제주제외) | 시장시공가격(토목)의 공급관할지역명 |
| etcCntnts | 기타내용 | 1000 | 0 | Motor별도,가대포함 | 시장시공가격(토목)의 기타내용 |
| mtrlcst | 재료비 | 22 | 0 | 758000 | 시장시공가격(토목)의 재료비(원화,단위:원) |
| lbrcst | 노무비 | 22 | 0 | 161000 | 시장시공가격(토목)의 노무비(원화,단위:원) |
| gnrlexpns | 경비 | 22 | 0 | 29000 | 시장시공가격(토목)의 경비(원화,단위:원) |
| vatYnNm | 부가가치세여부명 | 100 | 0 | 부가가치세별도 | 시장시공가격(토목)의 부가가치세여부명 |
| prceDiv | 가격구분 | 200 | 0 | 기타가격 | 시장시공가격(토목)의 가격구분 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 현장설치도 | 시장시공가격(토목)의 인도조건명 |
| distbStep | 유통단계 | 100 | 0 | 기타조건 | 시장시공가격(토목)의 유통단계 |
| payCndtn | 결제조건 | 100 | 0 | 기타 | 시장시공가격(토목)의 결제조건 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getPriceInfoListMrktCnstrctPcEngrk?prdctClsfcNo=0&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prceNticeNo>201603110008603</prceNticeNo> <nticeDt>2016-03-10 19:48</nticeDt> <bsnsDivCd>S1</bsnsDivCd> <bsnsDivNm>시장시공가격(토목)</bsnsDivNm> <prdctClsfcNo>0</prdctClsfcNo> <prdctIdntNo>90000000</prdctIdntNo> <invstDeptNm>예산사업관리과</invstDeptNm> <invstDeptTelNo>070-4056-7239</invstDeptTelNo> <invstOfclNm>윤재영</invstOfclNm> <prdctClsfcNoNm>ACE JOINT</prdctClsfcNoNm> <krnPrdctNm>NB-T120</krnPrdctNm> <unit>M</unit> <prce>948000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst>758000</mtrlcst> <lbrcst>161000</lbrcst> <gnrlexpns>29000</gnrlexpns> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>현장설치도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> <item> <prceNticeNo>201603110008604</prceNticeNo> <nticeDt>2016-03-10 19:48</nticeDt> <bsnsDivCd>S1</bsnsDivCd> <bsnsDivNm>시장시공가격(토목)</bsnsDivNm> <prdctClsfcNo>0</prdctClsfcNo> <prdctIdntNo>90000000</prdctIdntNo> <invstDeptNm>예산사업관리과</invstDeptNm> <invstDeptTelNo>070-4056-7239</invstDeptTelNo> <invstOfclNm>윤재영</invstOfclNm> <prdctClsfcNoNm>ACE JOINT</prdctClsfcNoNm> <krnPrdctNm>NB-T35</krnPrdctNm> <unit>M</unit> <prce>395000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst>316000</mtrlcst> <lbrcst>67000</lbrcst> <gnrlexpns>12000</gnrlexpns> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>현장설치도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item>> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>91</totalCount> </body> </response> |

[시장시공가격(건축) 가격정보] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 6 | 오퍼레이션명(국문) | 시장시공가격(건축) 가격정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPriceInfoListMrktCnstrctPcBildng |
|  | 오퍼레이션 설명 | 시장시공가격(건축) 정보의 검색조건(물품분류번호,품명,물품식별번호,규격명)을 입력하면 물품분류번호,관련부서정보,관련계약정보,시장시공가격 등 조회 |  |  |
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
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 0 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 100 | 0 | ACODECK(PIR) 판넬/부자재 포함 | 물품분류번호 8자리에 대한 한글명 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 90000000 | 품목에 대한 고유번호로서 8자리로 표시 |
| krnPrdctNm | 규격명(한글품목명) | 200 | 0 | 50T(하부:0.6t유공실리콘강판상 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prceNticeNo | 가격게시번호 | 20 | 1 | 201603110009226 | 시장시공가격(건축)의 가격게시번호 |
| nticeDt | 게시일시 | 20 | 1 | 2016-03-10 19:48 | 게시일시 'YYYY-MM-DD HH24:MI' |
| bsnsDivCd | 업무구분코드 | 2 | 1 | S2 | 시장시공가격(건축)의 업무구분코드 |
| bsnsDivNm | 업무구분명 | 40 | 1 | 시장시공가격(건축) | 시장시공가격(건축)의 업무구분명 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 0 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 90000000 | 품목에 대한 고유번호로서 8자리로 표시 |
| invstDeptNm | 조사부서명 | 30 | 0 | 건축설비과 | 시장시공가격(건축)의 조사부서명 |
| invstDeptTelNo | 조사부서전화번호 | 20 | 0 | 070-4056-7393 | 시장시공가격(건축)의 조사부서전화번호 |
| invstOfclNm | 조사담당자명 | 10 | 0 | 황지혜 | 시장시공가격(건축)의 조사담당자명 |
| prdctClsfcNoNm | 품명 | 100 | 1 | ACODECK(PIR) 판넬/부자재 포함 | 물품분류번호 8자리에 대한 한글명 |
| krnPrdctNm | 한글품목명 | 200 | 0 | 50T(하부:0.6t유공실리콘강판상 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |
| unit | 단위 | 10 | 0 | M2 | 시장시공가격(건축)의 단위 |
| prce | 가격 | 25 | 0 | 51700 | 시장시공가격(건축)의 가격(원화,단위:원) |
| splyJrsdctRgnNm | 공급관할지역명 | 200 | 0 | 전지역(제주제외) | 시장시공가격(건축)의 공급관할지역명 |
| etcCntnts | 기타내용 | 1000 | 0 | 성능,규격 업체에문의 | 시장시공가격(건축)의 기타내용 |
| mtrlcst | 재료비 | 22 | 0 | 44000 | 시장시공가격(건축)의 재료비(원화,단위:원) |
| lbrcst | 노무비 | 22 | 0 | 5000 | 시장시공가격(건축)의 노무비(원화,단위:원) |
| gnrlexpns | 경비 | 22 | 0 | 2700 | 시장시공가격(건축)의 경비(원화,단위:원) |
| vatYnNm | 부가가치세여부명 | 100 | 0 | 부가가치세별도 | 시장시공가격(건축)의 부가가치세여부명 |
| prceDiv | 가격구분 | 200 | 0 | 기타가격 | 시장시공가격(건축)의 가격구분 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 현장설치도 | 시장시공가격(건축)의 인도조건명 |
| distbStep | 유통단계 | 100 | 0 | 기타조건 | 시장시공가격(건축)의 유통단계 |
| payCndtn | 결제조건 | 100 | 0 | 기타 | 시장시공가격(건축)의 결제조건 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getPriceInfoListMrktCnstrctPcBildng?prdctClsfcNo=0&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prceNticeNo>201603110009222</prceNticeNo> <nticeDt>2016-03-10 19:48</nticeDt> <bsnsDivCd>S2</bsnsDivCd> <bsnsDivNm>시장시공가격(건축)</bsnsDivNm> <prdctClsfcNo>0</prdctClsfcNo> <prdctIdntNo>90000000</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7393</invstDeptTelNo> <invstOfclNm>황지혜</invstOfclNm> <prdctClsfcNoNm>ACODECK(PIR) 판넬/부자재 포함</prdctClsfcNoNm> <krnPrdctNm>50T(하부:0.6t유공실리콘강판상</krnPrdctNm> <unit>M2</unit> <prce>51700</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst>44000</mtrlcst> <lbrcst>5000</lbrcst> <gnrlexpns>2700</gnrlexpns> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>현장설치도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> <item> <prceNticeNo>201603110009224</prceNticeNo> <nticeDt>2016-03-10 19:48</nticeDt> <bsnsDivCd>S2</bsnsDivCd> <bsnsDivNm>시장시공가격(건축)</bsnsDivNm> <prdctClsfcNo>0</prdctClsfcNo> <prdctIdntNo>90000000</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7393</invstDeptTelNo> <invstOfclNm>황지혜</invstOfclNm> <prdctClsfcNoNm>ACODECK(PIR) 판넬/부자재 포함</prdctClsfcNoNm> <krnPrdctNm>75T(하부:0.6t유공실리콘강판상</krnPrdctNm> <unit>M2</unit> <prce>56800</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst>49000</mtrlcst> <lbrcst>5100</lbrcst> <gnrlexpns>2700</gnrlexpns> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>현장설치도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>91</totalCount> </body> </response> |

[시장시공가격(기계설비) 가격정보] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 7 | 오퍼레이션명(국문) | 시장시공가격(기계설비) 가격정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPriceInfoListMrktCnstrctPcMchnEqp |
|  | 오퍼레이션 설명 | 시장시공가격(기계설비) 정보의 검색조건(물품분류번호, 품명, 물품식별번호, 규격명)을 입력하면 물품분류번호, 관련부서정보, 관련계약정보, 시장시공가격 등 조회 |  |  |
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
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 0 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 100 | 0 | 풍량조절담파 | 물품분류번호 8자리에 대한 한글명 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 90000000 | 품목에 대한 고유번호로서 8자리로 표시 |
| krnPrdctNm | 규격명(한글품목명) | 200 | 0 | 자동식 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prceNticeNo | 가격게시번호 | 20 | 1 | 201603110009343 | 시장시공가격(기계설비)의 가격게시번호 |
| nticeDt | 게시일시 | 20 | 1 | 2016-03-10 19:48 | 게시일시 'YYYY-MM-DD HH24:MI' |
| bsnsDivCd | 업무구분코드 | 2 | 1 | S3 | 시장시공가격(기계설비)의 업무구분코드 |
| bsnsDivNm | 업무구분명 | 40 | 1 | 시장시공가격(기계설비) | 시장시공가격(기계설비)의 업무구분명 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 0 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 90000000 | 품목에 대한 고유번호로서 8자리로 표시 |
| invstDeptNm | 조사부서명 | 30 | 0 | 건축설비과 | 시장시공가격(기계설비)의 조사부서명 |
| invstDeptTelNo | 조사부서전화번호 | 20 | 0 | 070-4056-7407 | 시장시공가격(기계설비)의 조사부서전화번호 |
| invstOfclNm | 조사담당자명 | 10 | 0 | 배영수 | 시장시공가격(기계설비)의 조사담당자명 |
| prdctClsfcNoNm | 품명 | 100 | 1 | A.H.U | 물품분류번호 8자리에 대한 한글명 |
| krnPrdctNm | 한글품목명 | 200 | 0 | 150,000 CMH 이하 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |
| unit | 단위 | 10 | 0 | 대 | 시장시공가격(기계설비)의 단위 |
| prce | 가격 | 25 | 0 | 1183000 | 시장시공가격(기계설비)의 가격(원화,단위:원) |
| splyJrsdctRgnNm | 공급관할지역명 | 200 | 0 | 전지역(제주제외) | 시장시공가격(기계설비)의 공급관할지역명 |
| etcCntnts | 기타내용 | 1000 | 0 | rlxk | 시장시공가격(기계설비)의 기타내용 |
| mtrlcst | 재료비 | 22 | 0 | 0 | 시장시공가격(기계설비)의 재료비(원화,단위:원) |
| lbrcst | 노무비 | 22 | 0 | 1419000 | 시장시공가격(기계설비)의 노무비(원화,단위:원) |
| gnrlexpns | 경비 | 22 | 0 | 0 | 시장시공가격(기계설비)의 경비(원화,단위:원) |
| vatYnNm | 부가가치세여부명 | 100 | 0 | 부가가치세별도 | 시장시공가격(기계설비)의 부가가치세여부명 |
| prceDiv | 가격구분 | 200 | 0 | 기타가격 | 시장시공가격(기계설비)의 가격구분 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 현장설치도 | 시장시공가격(기계설비)의 인도조건명 |
| distbStep | 유통단계 | 100 | 0 | 기타조건 | 시장시공가격(기계설비)의 유통단계 |
| payCndtn | 결제조건 | 100 | 0 | 기타 | 시장시공가격(기계설비)의 결제조건 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getPriceInfoListMrktCnstrctPcMchnEqp?prdctClsfcNo=0&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prceNticeNo>201603110009343</prceNticeNo> <nticeDt>2016-03-10 19:48</nticeDt> <bsnsDivCd>S3</bsnsDivCd> <bsnsDivNm>시장시공가격(기계설비)</bsnsDivNm> <prdctClsfcNo>0</prdctClsfcNo> <prdctIdntNo>90000000</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7407</invstDeptTelNo> <invstOfclNm>배영수</invstOfclNm> <prdctClsfcNoNm>A.H.U</prdctClsfcNoNm> <krnPrdctNm>10,000 CMH 이하</krnPrdctNm> <unit>대</unit> <prce>108000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst>108000</lbrcst> <gnrlexpns /> <prodctFld /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>현장설치도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> <item> <prceNticeNo>201603110009344</prceNticeNo> <nticeDt>2016-03-10 19:48</nticeDt> <bsnsDivCd>S3</bsnsDivCd> <bsnsDivNm>시장시공가격(기계설비)</bsnsDivNm> <prdctClsfcNo>0</prdctClsfcNo> <prdctIdntNo>90000000</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7407</invstDeptTelNo> <invstOfclNm>배영수</invstOfclNm> <prdctClsfcNoNm>A.H.U</prdctClsfcNoNm> <krnPrdctNm>20,000 CMH 이하</krnPrdctNm> <unit>대</unit> <prce>151000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst>151000</lbrcst> <gnrlexpns /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>현장설치도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> - <item> <prceNticeNo>201603110009345</prceNticeNo> <nticeDt>2016-03-10 19:48</nticeDt> <bsnsDivCd>S3</bsnsDivCd> <bsnsDivNm>시장시공가격(기계설비)</bsnsDivNm> <prdctClsfcNo>0</prdctClsfcNo> <prdctIdntNo>90000000</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-7407</invstDeptTelNo> <invstOfclNm>배영수</invstOfclNm> <prdctClsfcNoNm>A.H.U</prdctClsfcNoNm> <krnPrdctNm>30,000 CMH 이하</krnPrdctNm> <unit>대</unit> <prce>192000</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst>192000</lbrcst> <gnrlexpns /> <prodctFld /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>현장설치도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>91</totalCount> </body> </response> |

[공종분류및세부공종] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 8 | 오퍼레이션명(국문) | 공종분류및세부공종 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getCnsttyClsfcInfoList |
|  | 오퍼레이션 설명 | 시설공사공종분류 검색조건(공사분류코드)을 입력하면 수량산출공종분류코, 수량산출코드명 등 조회. |  |  |
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

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| cnstwkDivCd | 공사구분코드 | 1 | 1 | A | 공사를 분류하는 코드 |
| cnstwkDivNm | 공사구분코드명 | 200 | 1 | 건축공사 | 공사를 분류하는 코드명 |
| LvlqtyCalcCtyclCd1 | 일레벨수량산출공종분류코드 | 8 | 1 | J | 일레벨수량산출분류코드 |
| LvlqtyCalcCtyclNm1 | 일레벨수량산출공종분류명 | 100 | 1 | 금속공사 | 일레벨수량산출공종분류명 |
| LvlqtyCalcCtyclDscrpt1 | 일레벨수량산출공종분류설명 | 200 | 0 | 일레벨수량산출설명 | 일레벨수량산출설명 |
| LvlqtyCalcCtyclCd2 | 이레벨수량산출공종분류코드 | 8 | 1 | JM | 이레벨수량산출분류코드 |
| LvlqtyCalcCtyclNm2 | 이레벨수량산출공종분류명 | 100 | 1 | 잡철물 제작설치 | 이레벨수량산출공종분류명 |
| LvlqtyCalcCtyclDscrpt2 | 이레벨수량산출공종분류설명 | 200 | 0 | 이레벨수량산출설명 | 이레벨수량산출설명 |
| LvlqtyCalcCtyclCd3 | 삼레벨수량산출공종분류코드 | 8 | 1 | JM5 | 삼레벨수량산출분류코드 |
| LvlqtyCalcCtyclNm3 | 삼레벨수량산출공종분류명 | 100 | 1 | 잡철물 제작 및 설치 | 삼레벨수량산출공종분류명 |
| LvlqtyCalcCtyclDscrpt3 | 삼레벨수량산출공종분류설명 | 200 | 0 | 22년 표준품셈 개정 | 삼레벨수량산출설명 |
| LvlqtyCalcCtyclCd4 | 사레벨수량산출공종분류코드 | 8 | 1 | JM51 | 사레벨수량산출분류코드 |
| LvlqtyCalcCtyclNm4 | 사레벨수량산출공종분류명 | 100 | 1 | 제품설치 | 사레벨수량산출공종분류명 |
| LvlqtyCalcCtyclDscrpt4 | 사레벨수량산출공종분류설명 | 200 | 0 | 22년 표준품셈 개정 | 사레벨수량산출설명 |
| LvlqtyCalcCtyclCd5 | 오레벨수량산출공종분류코드 | 8 | 1 | JM511 | 오레벨수량산출분류코드 |
| LvlqtyCalcCtyclNm5 | 오레벨수량산출공종분류명 | 100 | 1 | 제품설치 | 오레벨수량산출공종분류명 |
| LvlqtyCalcCtyclDscrpt5 | 오레벨수량산출공종분류설명 | 200 | 0 | 22년 표준품셈 개정 | 오레벨수량산출설명 |
| qtyCalcCtyclcd | 수량산출공종코드 | 16 | 1 | AJM51100010S | 수량산출공종코드 |
| qtyCalcCtyclNm | 수량산출공종명 | 100 | 1 | 잡철물 제작 및 설치(제품 설치) | 수량산출공종명 |
| spec | 규격 | 200 | 0 | 일반철재 | 규격 |
| unit | 단위 | 20 | 0 | Ton | 단위 |
| dscrpt | 설명 | 4000 | 0 | 자재 절단 및 설치작업이 포함되어 있음. | 세부공종 설명 |
| SpectnYn | 규격화여부 | 1 | 0 | Y | 규격화 여부 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getCnsttyClsfcInfoList?pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <cnstwkDivCd>A</cnstwkDivCd> <cnstwkDivNm>건축공사</cnstwkDivNm> <LvlqtyCalcCtyclCd1>A</LvlqtyCalcCtyclCd1> <LvlqtyCalcCtyclNm1>공통공사</LvlqtyCalcCtyclNm1> <LvlqtyCalcCtyclDscrpt1/> <LvlqtyCalcCtyclCd2>AA</LvlqtyCalcCtyclCd2> <LvlqtyCalcCtyclNm2>가설공사/1</LvlqtyCalcCtyclNm2> <LvlqtyCalcCtyclDscrpt2/> <LvlqtyCalcCtyclCd3>AA1</LvlqtyCalcCtyclCd3> <LvlqtyCalcCtyclNm3>임시시설</LvlqtyCalcCtyclNm3> <LvlqtyCalcCtyclDscrpt3/> <LvlqtyCalcCtyclCd4>AA16</LvlqtyCalcCtyclCd4> <LvlqtyCalcCtyclNm4>가설울타리</LvlqtyCalcCtyclNm4> <LvlqtyCalcCtyclDscrpt4/> <LvlqtyCalcCtyclCd5>AA161</LvlqtyCalcCtyclCd5> <LvlqtyCalcCtyclNm5>칼라강판, H=1.5</LvlqtyCalcCtyclNm5> <LvlqtyCalcCtyclDscrpt5/> <qtyCalcCtyclcd>AAA161000000</qtyCalcCtyclcd> <qtyCalcCtyclNm>조립식가설울타리</qtyCalcCtyclNm> <spec>H=2.0,3개월 </spec> <unit>m</unit> <dscrpt/> <SpectnYn>Y</SpectnYn> </item> <item> <cnstwkDivCd>A</cnstwkDivCd> <cnstwkDivNm>건축공사</cnstwkDivNm> <LvlqtyCalcCtyclCd1>A</LvlqtyCalcCtyclCd1> <LvlqtyCalcCtyclNm1>공통공사</LvlqtyCalcCtyclNm1> <LvlqtyCalcCtyclDscrpt1/> <LvlqtyCalcCtyclCd2>AA</LvlqtyCalcCtyclCd2> <LvlqtyCalcCtyclNm2>가설공사/1</LvlqtyCalcCtyclNm2> <LvlqtyCalcCtyclDscrpt2/> <LvlqtyCalcCtyclCd3>AA1</LvlqtyCalcCtyclCd3> <LvlqtyCalcCtyclNm3>임시시설</LvlqtyCalcCtyclNm3> <LvlqtyCalcCtyclDscrpt3/> <LvlqtyCalcCtyclCd4>AA16</LvlqtyCalcCtyclCd4> <LvlqtyCalcCtyclNm4>가설울타리</LvlqtyCalcCtyclNm4> <LvlqtyCalcCtyclDscrpt4/> <LvlqtyCalcCtyclCd5>AA161</LvlqtyCalcCtyclCd5> <LvlqtyCalcCtyclNm5>칼라강판, H=1.5</LvlqtyCalcCtyclNm5> <LvlqtyCalcCtyclDscrpt5/> <qtyCalcCtyclcd>AAA161000100</qtyCalcCtyclcd> <qtyCalcCtyclNm>조립식가설울타리</qtyCalcCtyclNm> <spec>H=1.5,3개월 이하</spec> <unit>m</unit> <dscrpt/> <SpectnYn>Y</SpectnYn> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>83527</totalCount> </body> </response> |

[표준시장단가및시장시공가격 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 9 | 오퍼레이션명(국문) | 표준시장단가및시장시공가격 정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getStdMarkUprcinfoList |
|  | 오퍼레이션 설명 | 시설공사 표준시장단가 공사구분(토목,건축,전기,통신,설비) 발표일에 구성된 세부공종, 품명, 규격,단위, 재료비,노무비,경비 합계 |  |  |
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
1: 공표일자 |
| inqryBgnDate | 조회시작일자 | 8 | 0 | 20220922 | 검색하고자하는 공표일자 "YYYYMMDD” |
| inqryEndDate | 조회종료일자 | 8 | 0 | 20220923 | 검색하고자하는 공표일자 "YYYYMMDD” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| pblctDate | 공표일자 | 8 | 1 | 20220923 | 자원단가 발표일자 |
| cnstwkDivCd | 공사구분코드 | 1 | 1 | A | 공사를 분류하는 코드 |
| cnstwkDivCdNm | 공사구분코드명 | 200 | 1 | 건축공사 | 공사를 분류하는 코드명 |
| uprcDivNm | 단가구분명 | 200 | 1 | 시장시공가격 | 세부공종구분 |
| qtyCalcCtyclcd | 수량산출공종코드 | 16 | 1 | AEG111100200 | 시설공사세부공종코드 |
| prdnm | 품명 | 100 | 1 | 내화피복재 | 품명 |
| spec | 규격 | 200 | 0 | 모노코트MK-6,1시간(16mm) | 규격 |
| unit | 단위 | 20 | 0 | M2 | 단위 |
| mtrlcstUprc | 재료비단가 | 22 | 0 | 4133 | 재료비단가 |
| lbrcstUprc | 노무비단가 | 22 | 0 | 4167 | 노무비단가 |
| gnrexpnsUprc | 경비단가 | 22 | 0 | 0 | 경비단가 |
| uprcAplCndtnCntnts | 단가적용조건내용 | 4000 | 0 | 부가가치세별도 | 단가를적용하는조건내용 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getStdMarkUprcinfoList?inqryDiv=1&inqryBgnDate=20220922&inqryEndDate=20220923&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |


| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <pblctDate>20220923</pblctDate> <cnstwkDivCd>A</cnstwkDivCd> <cnstwkDivCdNm>건축공사</cnstwkDivCdNm> <uprcDivNm>시장시공가격</uprcDivNm> <qtyCalcCtyclcd>AEG111100200</qtyCalcCtyclcd> <prdnm>내화피복재</prdnm> <spec>모노코트MK-6,1시간(16mm)</spec> <unit>M2</unit> <mtrlcstUprc>4133</mtrlcstUprc> <lbrcstUprc>4167</lbrcstUprc> <gnrexpnsUprc>0</gnrexpnsUprc> <uprcAplCndtnCntnts/> </item> <item> <pblctDate>20220923</pblctDate> <cnstwkDivCd>A</cnstwkDivCd> <cnstwkDivCdNm>건축공사</cnstwkDivCdNm> <uprcDivNm>시장시공가격</uprcDivNm> <qtyCalcCtyclcd>AEG111100800</qtyCalcCtyclcd> <prdnm>내화피복재</prdnm> <spec>에스코트CF,1시간(12mm)</spec> <unit>M2</unit> <mtrlcstUprc>4846</mtrlcstUprc> <lbrcstUprc>5854</lbrcstUprc> <gnrexpnsUprc>0</gnrexpnsUprc> <uprcAplCndtnCntnts/> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>991</totalCount> </body> </response> |
|---|

[자원분류 및 순수자원 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 10 | 오퍼레이션명(국문) | 자원분류및순수자원 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getNetRsceinfoList |
|  | 오퍼레이션 설명 | 조달청 시설공사 가격조사 및 관리업무 규정에 따라 자원분류 및 순수자원을 정기적으로 가격을 조사한 자료 |  |  |
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

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| rsceTyExtrnlCd | 자원유형외부코드 | 1 | 1 | M | 경제 생산에 이용되는 원료의 유형을 구분하는 외부사용 코드 |
| grpUnitCnstwkExtrnlCdNm | 그룹단위공사외부코드명 | 200 | 1 | 자재 | 통합코드의 명칭 |
| lvlRsceClsfcNo1 | 일레벨자원분류번호 | 8 | 1 | 11 | 자원분류번호 |
| lvlRsceClsfcNm1 | 일레벨자원분류명 | 100 | 1 | 광물,직물및비식용동식물자원 | 자원분류명 |
| lvlRsceClsfcDscrpt1 | 일레벨자원분류설명 | 4000 | 0 | 암석, 토양, 광석, 정제 금속 또는 귀금속, 비식용 식물, 삼림, 동물제품, 천연섬유, 인조섬유, 재봉사, 직물, 동물가죽, 찌거기, 배설물과 같은 비 가공물질. | 자원분류설명 |
| lvlRsceClsfcNo2 | 이레벨자원분류번호 | 8 | 1 | 1110 | 자원분류번호 |
| lvlRsceClsfcNm2 | 이레벨자원분류명 | 100 | 1 | 광물,광석및금속 | 자원분류명 |
| lvlRsceClsfcDscrpt2 | 이레벨자원분류설명 | 4000 | 0 | 암석, 토양, 광석, 정제 금속 또는 귀금속 등의 비 가공물질. | 자원분류설명 |
| lvlRsceClsfcNo3 | 삼레벨자원분류번호 | 8 | 1 | 111015 | 자원분류번호 |
| lvlRsceClsfcNm3 | 삼레벨자원분류명 | 100 | 1 | 광물 | 자원분류명 |
| lvlRsceClsfcDscrpt3 | 삼레벨자원분류설명 | 4000 | 0 | 천연으로 나며 질이 고르고 화학적 조성이 일정한 물질로, 대부분 결정체 상태의 무기질이나 석탄 같은 유기질도 있음. | 자원분류설명 |
| lvlRsceClsfcNo4 | 사레벨자원분류번호 | 8 | 1 | 11101524 | 자원분류번호 |
| lvlRsceClsfcNm4 | 사레벨자원분류명 | 100 | 1 | 형석 | 자원분류명 |
| lvlRsceClsfcDscrpt4 | 사레벨자원분류설명 | 4000 | 0 | 풀루오르화 칼슘으로 이루어진 광물로, 입방정계에 속하며, 무색, 담녹색, 보라색 등으로 변화 무쌍하고, 유리와 같은 광택이 나는 약하고 무른 결정으로, 알루미늄 제련이나 제철의 용제로 사용됨. | 자원분류설명 |
| gnrlexpnsItemGrpExtrnCd | 경비항목그룹외부코드 | 4 | 1 | E1 | 물품관리구분코드 |
| prdctMngDivNm | 경비항목그룹외부코드명 | 200 | 1 | 건설기계 | 물품관리구분명 |
| netRsceCd | 순수자원코드 | 16 | 1 | 1110152424863832 | 순수자원코드 |
| rsceNm | 자원명 | 200 | 0 | 형석 | 품명 |
| rsceSpecNm | 자원규격명 | 200 | 1 | 형석, CaF2 97% 초과, 가루(파우더) | 규격 |
| unit | 단위 | 20 | 0 | 톤 | 단위 |
| rsceDscrpt | 자원설명 | 4000 | 0 | 무수부산 등 모든 유무기 불소산업 소재의 출발 원료 | 설명 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 인도조건명 | 인도조건명 |
| lbrcst | 노무비 | 22 | 0 | 3000 | 순수자원의 노무비(원화,단위:원) |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getNetRsceinfoList?pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <rsceTyExtrnlCd>E</rsceTyExtrnlCd> <grpUnitCnstwkExtrnlCdNm>경비항목</grpUnitCnstwkExtrnlCdNm> <lvlRsceClsfcNo1>00</lvlRsceClsfcNo1> <lvlRsceClsfcNm1>건설기계</lvlRsceClsfcNm1> <lvlRsceClsfcDscrpt1/> <lvlRsceClsfcNo2>0000</lvlRsceClsfcNo2> <lvlRsceClsfcNm2>건설기계</lvlRsceClsfcNm2> <lvlRsceClsfcDscrpt2/> <lvlRsceClsfcNo3>000001</lvlRsceClsfcNo3> <lvlRsceClsfcNm3>토공장비(1)</lvlRsceClsfcNm3> <lvlRsceClsfcDscrpt3/> <lvlRsceClsfcNo4>00000101</lvlRsceClsfcNo4> <lvlRsceClsfcNm4>불도저(무한궤도)</lvlRsceClsfcNm4> <lvlRsceClsfcDscrpt4/> <gnrlexpnsItemGrpExtrnCd>E1</gnrlexpnsItemGrpExtrnCd> <prdctMngDivNm>건설기계</prdctMngDivNm> <netRsceCd>0000010100070000</netRsceCd> <rsceNm>불도저(무한궤도)</rsceNm> <rsceSpecNm>7 ton</rsceSpecNm> <unit>대</unit> <rsceDscrpt/> <dlvryCndtnNm/> <lbrcst>0</lbrcst> </item> <item> <rsceTyExtrnlCd>E</rsceTyExtrnlCd> <grpUnitCnstwkExtrnlCdNm>경비항목</grpUnitCnstwkExtrnlCdNm> <lvlRsceClsfcNo1>00</lvlRsceClsfcNo1> <lvlRsceClsfcNm1>건설기계</lvlRsceClsfcNm1> <lvlRsceClsfcDscrpt1/> <lvlRsceClsfcNo2>0000</lvlRsceClsfcNo2> <lvlRsceClsfcNm2>건설기계</lvlRsceClsfcNm2> <lvlRsceClsfcDscrpt2/> <lvlRsceClsfcNo3>000001</lvlRsceClsfcNo3> <lvlRsceClsfcNm3>토공장비(1)</lvlRsceClsfcNm3> <lvlRsceClsfcDscrpt3/> <lvlRsceClsfcNo4>00000101</lvlRsceClsfcNo4> <lvlRsceClsfcNm4>불도저(무한궤도)</lvlRsceClsfcNm4> <lvlRsceClsfcDscrpt4/> <gnrlexpnsItemGrpExtrnCd>E1</gnrlexpnsItemGrpExtrnCd> <prdctMngDivNm>건설기계</prdctMngDivNm> <netRsceCd>0000010100100000</netRsceCd> <rsceNm>불도저(무한궤도)</rsceNm> <rsceSpecNm>10 ton</rsceSpecNm> <unit>대</unit> <rsceDscrpt/> <dlvryCndtnNm/> <lbrcst>0</lbrcst> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>58376</totalCount> </body> </response> |

[시설공통자재(종합) 가격정보] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 11 | 오퍼레이션명(국문) | 시설공통자재(종합) 가격정보 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPriceInfoListFcltyCmmnMtrilTotal |
|  | 오퍼레이션 설명 | 시설공통자재(토목, 건축, 기계설비, 전기·정보통신) 가격정보의 검색조건(물품분류번호,품명,물품식별번호,규격명)을 입력하면 물품분류번호,관련부서정보,관련계약정보,가격 등 조회. |  |  |
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
1: 가격게시일시 |
| inqryBgnDate | 조회시작일자 | 8 | 1 | 20240701 | 검색하고자하는 가격게시일시 "YYYYMMDD” |
| inqryEndDate | 조회종료일자 | 8 | 1 | 20240705 | 검색하고자하는 가격게시일시 "YYYYMMDD” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 1 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prceNticeNo | 가격게시번호 | 20 | 1 | 202304050006802 | 시설공통자재 가격게시번호 |
| nticeDt | 게시일시 | 20 | 1 | 2023-04-05 16:12:42 | 게시일시 'YYYY-MM-DD HH24:MI' |
| bsnsDivCd | 업무구분코드 | 2 | 1 | GG | 시설공통자재 업무구분코드 |
| bsnsDivNm | 업무구분명 | 40 | 1 | 시설공통자재(기계설비) | 시설공통자재 업무구분명 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 40141901 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 22510386 | 품목에 대한 고유번호로서 8자리로 표시 |
| invstDeptNm | 조사부서명 | 30 | 0 | 건축설비과 | 시설공통자재 조사부서명 |
| invstDeptTelNo | 조사부서전화번호 | 20 | 0 | 070-4056-6146 | 시설공통자재 조사부서전화번호 |
| invstOfclNm | 조사담당자명 | 10 | 0 | 김영희 | 시설공통자재 조사담당자명 |
| prdctClsfcNoNm | 품명 | 100 | 0 | 스텐밴드 | 물품분류번호 8자리에 대한 한글명 |
| krnPrdctNm | 한글품목명 | 200 | 0 | D75 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |
| unit | 단위 | 10 | 0 | 개 | 시설공통자재 단위 |
| prce | 가격 | 25 | 0 | 638 | 시설공통자재 가격(원화,단위:원) |
| splyJrsdctRgnNm | 공급관할지역명 | 200 | 0 | 전지역(제주제외) | 시설공통자재 공급관할지역명 |
| etcCntnts | 기타내용 | 1000 | 0 |  | 시설공통자재기타내용 |
| mtrlcst | 재료비 | 22 | 0 |  | 시설공통자재 재료비(원화,단위:원) |
| lbrcst | 노무비 | 22 | 0 |  | 시설공통자재 노무비(원화,단위:원) |
| gnrlexpns | 경비 | 22 | 0 |  | 시설공통자재 경비(원화,단위:원) |
| vatYnNm | 부가가치세여부명 | 100 | 0 | 부가가치세별도 | 시설공통자재 부가가치세여부명 |
| prceDiv | 가격구분 | 200 | 0 | 기타가격 | 시설공통자재 가격구분 |
| dlvryCndtnNm | 인도조건명 | 200 | 0 | 납품장소도 | 시설공통자재 인도조건명 |
| distbStep | 유통단계 | 100 | 0 | 기타조건 | 시설공통자재 유통단계 |
| payCndtn | 결제조건 | 100 | 0 | 기타 | 시설공통자재 결제조건 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PriceInfoService/getPriceInfoListFcltyCmmnMtrilTotal?inqryDiv=1&inqryBgnDate=20230101&inqryEndDate=20230613&pageNo=1&numOfRows=1&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prceNticeNo>202304050006802</prceNticeNo> <nticeDt>2023-04-05 16:12:42</nticeDt> <bsnsDivCd>GG</bsnsDivCd> <bsnsDivNm>시설공통자재(기계설비)</bsnsDivNm> <prdctClsfcNo>40141901</prdctClsfcNo> <prdctIdntNo>22510386</prdctIdntNo> <invstDeptNm>건축설비과</invstDeptNm> <invstDeptTelNo>070-4056-6146</invstDeptTelNo> <invstOfclNm>김영희</invstOfclNm> <prdctClsfcNoNm>스텐밴드</prdctClsfcNoNm> <krnPrdctNm>D75</krnPrdctNm> <unit>개</unit> <prce>638</prce> <splyJrsdctRgnNm>전지역(제주제외)</splyJrsdctRgnNm> <etcCntnts /> <mtrlcst /> <lbrcst /> <gnrlexpns /> <vatYnNm>부가가치세별도</vatYnNm> <prceDiv>기타가격</prceDiv> <dlvryCndtnNm>납품장소도</dlvryCndtnNm> <distbStep>기타조건</distbStep> <payCndtn>기타</payCndtn> </item> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

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

