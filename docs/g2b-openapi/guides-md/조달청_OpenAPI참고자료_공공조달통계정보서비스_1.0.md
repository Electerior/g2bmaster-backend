# 조달청_OpenAPI참고자료_공공조달통계정보서비스_1.0

> 출처: 공공데이터포털 참고문서(docx) 자동 변환

조달청 공공데이터 개방
OpenAPI 참고자료
목 차목 차

### 1. 서비스 명세	3


### 1.1 공공조달통계정보서비스	3

가. 서비스 개요	3
나. 오퍼레이션 목록	5
개정 이력

| 버 전 | 변경일 | 변경 구분 | 변경사유 |
|---|---|---|---|
| 1.0 | 2025 | 최초 개정 | 최초 개정 |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |


### 1. 서비스 명세


### 1.1 공공조달통계정보서비스

서비스 개요

| 서비스 정보 | 서비스 ID | PubPrcrmntStatInfoService |  |  |
|---|---|---|---|---|
|  | 서비스명(국문) | 공공조달통계정보서비스 |  |  |
|  | 서비스명(영문) | PubPrcrmntStatInfoService |  |  |
|  | 서비스 설명 | 나라장터를 포함 24개 전자조달시스템에서 체결한 전자계약정보와 비전자계약정보를 수집하여 전체 공공조달 규모를 파악할 수 있도록 공공조달통계 정보를 제공하는 서비스로 검색조건을 기준년도, 기준년월 범위로 전체 조달실적, 계약방법별 현황, 지역제한 현황, 기관구분별 조달현황, 기업구분별 조달현황, 계약방법별 조달현황, 수요기관별 기업구분별 실적, 수요기관별 업무대상별 실적, 수요기관별 시스템유형별 실적, 조달기업별 계약방법별 실적, 조달기업별 업무대상별 실적, 품목 및 서비스별 실적을 각 유형별로 조회할 수 있음 |  |  |
| 서비스 보안 | 서비스 인증/권한 | [O] 서비스 Key[ ] 인증서 (GPKI) [] Basic (ID/PW) [ ] 없음 | [ ]WS-Security |  |
|  | 메시지 레벨 암호화 | [  ] 전자서명	[ ] 암호화	[O] 없음 |  |  |
|  | 전송 레벨 암호화 | [  ] SSL			[ O] 없음 |  |  |
| 적용 기술 수준 | 인터페이스 표준 | [  ] SOAP 1.2 (RPC-Encoded, Document Literal, Document Literal Wrapped) [ O ] REST (GET) [ ] RSS 1.0 [ ] RSS 2.0 [ ] Atom 1.0 [ ] 기타 |  |  |
|  | 교환 데이터 표준 | [ O ] XML	[ O ] JSON	[ ] MIME	[ ] MTOM |  |  |
| 서비스 URL | 개발환경 | http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService |  |  |
|  | 운영환경 | http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService |  |  |
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
| 1 | 공공조달통계정보서비스 | getTotlPubPrcrmntSttus | 전체 공공조달 현황 | N/A |
| 2 |  | getInsttDivAccotPrcrmntSttus | 기관구분별 조달 현황 | N/A |
| 3 |  | getEntrprsDivAccotPrcrmntSttus | 기업구분별 조달 현황 | N/A |
| 4 |  | getCntrctMthdAccotSttus | 계약방법별 현황 | N/A |
| 5 |  | getRgnLmtSttus | 지역제한 현황 | N/A |
| 6 |  | getRgnDutyCmmnCntrctSttus | 지역의무공동계약 현황 | N/A |
| 7 |  | getPrcrmntObjectBsnsObjAccotSttus | 조달목적물(업무대상)별 현황 | N/A |
| 8 |  | getDminsttAccotEntrprsDivAccotArslt | 수요기관별 기업구분별 실적 | N/A |
| 9 |  | getDminsttAccotCntrctMthdAccotArslt | 수요기관별 계약방법별 실적 | N/A |
| 10 |  | getDminsttAccotBsnsObjAccotArslt | 수요기관별 업무대상별 실적 | N/A |
| 11 |  | getDminsttAccotSystmTyAccotArslt | 수요기관별 시스템유형별 실적 | N/A |
| 12 |  | getPrcrmntEntrprsAccotCntrctMthdAccotArslt | 조달기업별 계약방법별 실적 | N/A |
| 13 |  | getPrcrmntEntrprsAccotBsnsObjAccotArslt | 조달기업별 업무대상별 실적 | N/A |
| 14 |  | getPrdctIdntNoServcAccotArslt | 품목 및 서비스별 실적 | N/A |

[전체 공공조달 현황] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 1 | 오퍼레이션명(국문) | 전체 공공조달 현황 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getTotlPubPrcrmntSttus |
|  | 오퍼레이션 설명 | 검색조건에 기준년도를 입력하여 기준년월, 공급집계금액 등 전체 공공조달 현황을 조회 |  |  |
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
| srchBssYear | 검색기준년도 | 4 | 0 | 2016 | 검색하고자하는 기준년도 "YYYY" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bssMnth | 기준월 | 2 | 1 | 03 | 기준월 "MM" |
| statDivNm | 통계구분명 | 30 | 1 | 월별 | “월별”,”누적” |
| bssYearAmt | 기준년도금액 | 30 | 1 | 8592311268266 | 기준년도 금액(원,원화) |
| bssYear1Amt | 기준년도+1금액 | 30 | 1 | 0 | 기준년도+1 금액(원,원화) |
| bssYear2Amt | 기준년도+2금액 | 30 | 1 | 0 | 기준년도+2 금액(원,원화) |
| bssYear3Amt | 기준년도+3금액 | 30 | 1 | 0 | 기준년도+3 금액(원,원화) |
| bssYear4Amt | 기준년도+4금액 | 30 | 1 | 0 | 기준년도+4 금액(원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getTotlPubPrcrmntSttus?srchBssYear=2016&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bssMnth>01</bssMnth> <statDivNm>월별</statDivNm> <bssYearAmt>8592311268266</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2Amt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> <item> <bssMnth>01</bssMnth> <statDivNm>누적</statDivNm> <bssYearAmt>8592311268266</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2Amt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> <item> <bssMnth>02</bssMnth> <statDivNm>월별</statDivNm> <bssYearAmt>0</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2Amt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> <item> <bssMnth>02</bssMnth> <statDivNm>누적</statDivNm> <bssYearAmt>0</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2Amt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> <item> <bssMnth>03</bssMnth> <statDivNm>월별</statDivNm> <bssYearAmt>0</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2Amt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> <item> <bssMnth>03</bssMnth> <statDivNm>누적</statDivNm> <bssYearAmt>0</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2Amt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> <item> <bssMnth>04</bssMnth> <statDivNm>월별</statDivNm> <bssYearAmt>0</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2AMt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> <item> <bssMnth>04</bssMnth> <statDivNm>누적</statDivNm> <bssYearAmt>0</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2Amt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> <item> <bssMnth>05</bssMnth> <statDivNm>월별</statDivNm> <bssYearAmt>0</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2Amt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> <item> <bssMnth>05</bssMnth> <statDivNm>누적</statDivNm> <bssYearAmt>0</bssYearAmt> <bssYear1Amt>0</bssYear1Amt> <bssYear2Amt>0</bssYear2Amt> <bssYear3Amt>0</bssYear3Amt> <bssYear4Amt>0</bssYear4Amt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>24</totalCount> </body> </response> |

[기관구분별 조달 현황] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 2 | 오퍼레이션명(국문) | 기관구분별 조달 현황 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getInsttDivAccotPrcrmntSttus |
|  | 오퍼레이션 설명 | 검색조건에 기준년도범위를 입력하여 기준년도, 기준월, 통계구분명, 실적합계건수, 실적합계금액, 국가기관실적건수, 국가기관실적금액, 지방자치단체실적건수, 지방자치단체실적금액, 교육행정기관실적건수, 교육행정기관실적금액, 공기업실적건수, 공기업실적금액 등 기관구분별 조달 현황(을)를 조회 |  |  |
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
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201603 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bssYear | 기준년도 | 4 | 0 | 2016 | 기준년도 "YYYY" 전체, 누계 일 경우 값 없음 |
| bssMnth | 기준월 | 2 | 0 | 03 | 기준월 "MM" 전체, 누계 일 경우 값 없음 |
| statDivNm | 통계구분명 | 30 | 1 | 월별 | “월별”,”누계”,”전체” |
| arsltSumNum | 실적합계건수 | 25 | 1 | 335475 | 실적합계건수 (전체건수) |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 12880757279936.116 | 실적합계금액 (전체합계금액) (원,원화) |
| cntryInsttArsltNum | 국가기관실적건수 | 25 | 1 | 34775 | 국가기관실적건수 |
| cntryInsttArsltAmt | 국가기관실적금액 | 30 | 1 | 2161470973113 | 국가기관실적금액 (원,원화) |
| lcltyAutnGrpArsltNum | 지방자치단체실적건수 | 25 | 1 | 146288 | 지방자치단체실적건수 |
| lcltyAutnGrpArsltAmt | 지방자치단체실적금액 | 30 | 1 | 4993155132393 | 지방자치단체실적금액 (원,원화) |
| eduAdministInsttArsltNum | 교육행정기관실적건수 | 25 | 1 | 122823 | 교육행정기관실적건수 |
| eduAdministInsttArsltAmt | 교육행정기관실적금액 | 30 | 1 | 882608723203 | 교육행정기관실적금액 (원,원화) |
| pblcorpArsltNum | 공기업실적건수 | 25 | 1 | 8347 | 공기업실적건수 |
| pblcorpArsltAmt | 공기업실적금액 | 30 | 1 | 2984965689753.116 | 공기업실적금액 (원,원화) |
| quasiGovInsttArsltNum | 준정부기관실적건수 | 25 | 1 | 10494 | 준정부기관실적건수 |
| quasiGovInsttArsltAmt | 준정부기관실적금액 | 30 | 1 | 1108125078001 | 준정부기관실적금액 (원,원화) |
| etcPubInsttArsltNum | 기타공공기관실적건수 | 25 | 1 | 5911 | 기타공공기관실적건수 |
| etcPubInsttArsltAmt | 기타공공기관실적금액 | 30 | 1 | 195377454154 | 기타공공기관실적금액 (원,원화) |
| lcltyPblcorpArsltNum | 지방공기업실적건수 | 25 | 1 | 6146 | 지방공기업실적건수 |
| lcltyPblcorpArsltAmt | 지방공기업실적금액 | 30 | 1 | 539258100434 | 지방공기업실적금액 (원,원화) |
| lcltyMdlcInsttArsltNum | 지방의료기관실적건수 | 25 | 1 | 420 | 지방의료원실적건수 |
| lcltyMdlcInsttArsltAmt | 지방의료기관실적금액 | 30 | 1 | 623286630 | 지방의료원실적금액 (원,원화) |
| lcltyAutnGrpDntRsrchArsltNum | 지방자치단체출연연구실적건수 | 25 | 1 | 121 | 지자체출연연구실적건수 |
| lcltyAutnGrpDntRsrchArsltAmt | 지방자치단체출연연구실적금액 | 30 | 1 | 623286630 | 지자체출연연구실적금액 (원,원화) |
| spclCorprtArsltNum | 특수법인실적건수 | 25 | 1 | 150 | 특수법인실적건수 |
| spclCorprtArsltAmt | 특수법인실적금액 | 30 | 1 | 2027310353 | 특수법인실적금액 (원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getInsttDivAccotPrcrmntSttus?srchBssYmBgn=201601&srchBssYmEnd=201601&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bssYear/> <bssMnth/> <statDivNm>전체</statDivNm> <arsltSumNum>156060</arsltSumNum> <arsltSumAmt>8721348697602</arsltSumAmt> <cntryInsttArsltNum>18481</cntryInsttArsltNum> <cntryInsttArsltAmt>2619814609733</cntryInsttArsltAmt> <lcltyAutnGrpArsltNum>65042</lcltyAutnGrpArsltNum> <lcltyAutnGrpArsltAmt>2683282083330</lcltyAutnGrpArsltAmt> <eduAdministInsttArsltNum>53015</eduAdministInsttArsltNum> <eduAdministInsttArsltAmt>597253146989</eduAdministInsttArsltAmt> <pblcorpArsltNum>5056</pblcorpArsltNum> <pblcorpArsltAmt>1785165197471</pblcorpArsltAmt> <quasiGovInsttArsltNum>5200</quasiGovInsttArsltNum> <quasiGovInsttArsltAmt>582001853571</quasiGovInsttArsltAmt> <etcPubInsttArsltNum>4360</etcPubInsttArsltNum> <etcPubInsttArsltAmt>225996328031</etcPubInsttArsltAmt> <lcltyPblcorpArsltNum>4432</lcltyPblcorpArsltNum> <lcltyPblcorpArsltAmt>220310659082</lcltyPblcorpArsltAmt> <lcltyMdlcInsttArsltNum>309</lcltyMdlcInsttArsltNum> <lcltyMdlcInsttArsltAmt>6248209187</lcltyMdlcInsttArsltAmt> <lcltyAutnGrpDntRsrchArsltNum>76</lcltyAutnGrpDntRsrchArsltNum> <lcltyAutnGrpDntRsrchArsltAmt>287107380</lcltyAutnGrpDntRsrchArsltAmt> <spclCorprtArsltNum>89</spclCorprtArsltNum> <spclCorprtArsltAmt>989502828</spclCorprtArsltAmt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>월별</statDivNm> <arsltSumNum>156060</arsltSumNum> <arsltSumAmt>8721348697602</arsltSumAmt> <cntryInsttArsltNum>18481</cntryInsttArsltNum> <cntryInsttArsltAmt>2619814609733</cntryInsttArsltAmt> <lcltyAutnGrpArsltNum>65042</lcltyAutnGrpArsltNum> <lcltyAutnGrpArsltAmt>2683282083330</lcltyAutnGrpArsltAmt> <eduAdministInsttArsltNum>53015</eduAdministInsttArsltNum> <eduAdministInsttArsltAmt>597253146989</eduAdministInsttArsltAmt> <pblcorpArsltNum>5056</pblcorpArsltNum> <pblcorpArsltAmt>1785165197471</pblcorpArsltAmt> <quasiGovInsttArsltNum>5200</quasiGovInsttArsltNum> <quasiGovInsttArsltAmt>582001853571</quasiGovInsttArsltAmt> <etcPubInsttArsltNum>4360</etcPubInsttArsltNum> <etcPubInsttArsltAmt>225996328031</etcPubInsttArsltAmt> <lcltyPblcorpArsltNum>4432</lcltyPblcorpArsltNum> <lcltyPblcorpArsltAmt>220310659082</lcltyPblcorpArsltAmt> <lcltyMdlcInsttArsltNum>309</lcltyMdlcInsttArsltNum> <lcltyMdlcInsttArsltAmt>6248209187</lcltyMdlcInsttArsltAmt> <lcltyAutnGrpDntRsrchArsltNum>76</lcltyAutnGrpDntRsrchArsltNum> <lcltyAutnGrpDntRsrchArsltAmt>287107380</lcltyAutnGrpDntRsrchArsltAmt> <spclCorprtArsltNum>89</spclCorprtArsltNum> <spclCorprtArsltAmt>989502828</spclCorprtArsltAmt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>누계</statDivNm> <arsltSumNum>156060</arsltSumNum> <arsltSumAmt>8721348697602</arsltSumAmt> <cntryInsttArsltNum>18481</cntryInsttArsltNum> <cntryInsttArsltAmt>2619814609733</cntryInsttArsltAmt> <lcltyAutnGrpArsltNum>65042</lcltyAutnGrpArsltNum> <lcltyAutnGrpArsltAmt>2683282083330</lcltyAutnGrpArsltAmt> <eduAdministInsttArsltNum>53015</eduAdministInsttArsltNum> <eduAdministInsttArsltAmt>597253146989</eduAdministInsttArsltAmt> <pblcorpArsltNum>5056</pblcorpArsltNum> <pblcorpArsltAmt>1785165197471</pblcorpArsltAmt> <quasiGovInsttArsltNum>5200</quasiGovInsttArsltNum> <quasiGovInsttArsltAmt>582001853571</quasiGovInsttArsltAmt> <etcPubInsttArsltNum>4360</etcPubInsttArsltNum> <etcPubInsttArsltAmt>225996328031</etcPubInsttArsltAmt> <lcltyPblcorpArsltNum>4432</lcltyPblcorpArsltNum> <lcltyPblcorpArsltAmt>220310659082</lcltyPblcorpArsltAmt> <lcltyMdlcInsttArsltNum>309</lcltyMdlcInsttArsltNum> <lcltyMdlcInsttArsltAmt>6248209187</lcltyMdlcInsttArsltAmt> <lcltyAutnGrpDntRsrchArsltNum>76</lcltyAutnGrpDntRsrchArsltNum> <lcltyAutnGrpDntRsrchArsltAmt>287107380</lcltyAutnGrpDntRsrchArsltAmt> <spclCorprtArsltNum>89</spclCorprtArsltNum> <spclCorprtArsltAmt>989502828</spclCorprtArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>3</totalCount> </body> </response> |

[기업구분별 조달 현황]오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 3 | 오퍼레이션명(국문) | 기업구분별 조달 현황 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getEntrprsDivAccotPrcrmntSttus |
|  | 오퍼레이션 설명 | 검색조건에 기준년월 범위를 입력하여 기준년도, 기준월, 통계구분명, 실적합계건수, 실적합계금액, 대기업실적건수, 대기업실적금액, 중견기업실적건수, 중견기업실적금액, 중소기업실적건수, 중소기업실적금액, 외국기업실적건수, 외국기업실적금액, 기타실적건수, 기타실적금액, 미분류실적건, 미분류실적금액 등 기업구분별 조달 현황(을)를 조회 |  |  |
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
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201603 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bssYear | 기준년도 | 4 | 0 | 2016 | 기준년도 "YYYY" 전체, 누계 일 경우 값 없음 |
| bssMnth | 기준월 | 2 | 0 | 03 | 기준월 "MM" 전체, 누계 일 경우 값 없음 |
| statDivNm | 통계구분명 | 30 | 1 | 월별 | “월별”,”누계”,”전체” |
| arsltSumNum | 실적합계건수 | 25 | 1 | 338553 | 실적합계건수 (합계건수) |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 12880757279553.116 | 실적합계금액 (원,원화) (합계금액) |
| lrgeEntrprsArsltNum | 대기업실적건수 | 25 | 1 | 71941 | 대기업실적건수 |
| lrgeEntrprsArsltAmt | 대기업실적금액 | 30 | 1 | 1713346801191 | 대기업실적금액 (원,원화) |
| entrprsOfMidStdArsltNum | 중견기업실적건수 | 25 | 1 | 5537 | 중견기업실적건수 |
| entrprsOfMidStdArsltAmt | 중견기업실적금액 | 30 | 1 | 1248727582331 | 중견기업실적금액 (원,원화) |
| smetprArsltNum | 중소기업실적건수 | 25 | 1 | 213907 | 중소기업실적건수 |
| smetprArsltAmt | 중소기업실적금액 | 30 | 1 | 8354157136152 | 중소기업실적금액 (원,원화) |
| frgncntryEntrprsArsltNum | 외국기업실적건수 | 25 | 1 | 77 | 외국기업실적건수 |
| frgncntryEntrprsArsltAmt | 외국기업실적금액 | 30 | 1 | 43148807945.116 | 외국기업실적금액 (원,원화) |
| etcArsltNum | 기타실적건수 | 25 | 1 | 32664 | 기타실적건수 |
| etcArsltAmt | 기타실적금액 | 30 | 1 | 1495118642924 | 기타실적금액 (원,원화) |
| unClsfcArsltNum | 미분류실적건수 | 25 | 1 | 14427 | 미분류실적건수 |
| unClsfcArsltAmt | 미분류실적금액 | 30 | 1 | 26258309010 | 미분류실적금액 (원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getEntrprsDivAccotPrcrmntSttus?srchBssYmBgn=201601&srchBssYmEnd=201601&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bssYear/> <bssMnth/> <statDivNm>전체</statDivNm> <arsltSumNum>158563</arsltSumNum> <arsltSumAmt>8721348697277</arsltSumAmt> <lrgeEntrprsArsltNum>54930</lrgeEntrprsArsltNum> <lrgeEntrprsArsltAmt>1597819752765</lrgeEntrprsArsltAmt> <entrprsOfMidStdArsltNum>3140</entrprsOfMidStdArsltNum> <entrprsOfMidStdArsltAmt>1002190429661</entrprsOfMidStdArsltAmt> <smetprArsltNum>87914</smetprArsltNum> <smetprArsltAmt>5436591831741</smetprArsltAmt> <frgncntryEntrprsArsltNum>52</frgncntryEntrprsArsltNum> <frgncntryEntrprsArsltAmt>17050961864</frgncntryEntrprsArsltAmt> <etcArsltNum>7851</etcArsltNum> <etcArsltAmt>661642705917</etcArsltAmt> <unClsfcArsltNum>4676</unClsfcArsltNum> <unClsfcArsltAmt>6053015329</unClsfcArsltAmt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>월별</statDivNm> <arsltSumNum>158563</arsltSumNum> <arsltSumAmt>8721348697277</arsltSumAmt> <lrgeEntrprsArsltNum>54930</lrgeEntrprsArsltNum> <lrgeEntrprsArsltAmt>1597819752765</lrgeEntrprsArsltAmt> <entrprsOfMidStdArsltNum>3140</entrprsOfMidStdArsltNum> <entrprsOfMidStdArsltAmt>1002190429661</entrprsOfMidStdArsltAmt> <smetprArsltNum>87914</smetprArsltNum> <smetprArsltAmt>5436591831741</smetprArsltAmt> <frgncntryEntrprsArsltNum>52</frgncntryEntrprsArsltNum> <frgncntryEntrprsArsltAmt>17050961864</frgncntryEntrprsArsltAmt> <etcArsltNum>7851</etcArsltNum> <etcArsltAmt>661642705917</etcArsltAmt> <unClsfcArsltNum>4676</unClsfcArsltNum> <unClsfcArsltAmt>6053015329</unClsfcArsltAmt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>누계</statDivNm> <arsltSumNum>158563</arsltSumNum> <arsltSumAmt>8721348697277</arsltSumAmt> <lrgeEntrprsArsltNum>54930</lrgeEntrprsArsltNum> <lrgeEntrprsArsltAmt>1597819752765</lrgeEntrprsArsltAmt> <entrprsOfMidStdArsltNum>3140</entrprsOfMidStdArsltNum> <entrprsOfMidStdArsltAmt>1002190429661</entrprsOfMidStdArsltAmt> <smetprArsltNum>87914</smetprArsltNum> <smetprArsltAmt>5436591831741</smetprArsltAmt> <frgncntryEntrprsArsltNum>52</frgncntryEntrprsArsltNum> <frgncntryEntrprsArsltAmt>17050961864</frgncntryEntrprsArsltAmt> <etcArsltNum>7851</etcArsltNum> <etcArsltAmt>661642705917</etcArsltAmt> <unClsfcArsltNum>4676</unClsfcArsltNum> <unClsfcArsltAmt>6053015329</unClsfcArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>3</totalCount> </body> </response> |

[계약방법별 현황]오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 4 | 오퍼레이션명(국문) | 계약방법별 현황 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getCntrctMthdAccotSttus |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위를 입력하여 기준년도, 기준월, 통계구분명, 실적합계건수, 실적합계금액, 일반경쟁실적건수, 일반경쟁실적금액, 제한경쟁실적건수, 제한경쟁실적금액, 지명경쟁실적건수, 지명경쟁실적금액, 수의계약실적건수, 수의계약실적금액, 미분류실적건수, 미분류실적금액 등 계약방법별 현황(을)를 조회 |  |  |
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
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201601 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bssYear | 기준년도 | 4 | 0 | 2016 | 기준년도 "YYYY" 전체, 누계 일 경우 값 없음 |
| bssMnth | 기준월 | 2 | 0 | 03 | 기준월 "MM" 전체, 누계 일 경우 값 없음 |
| statDivNm | 통계구분명 | 30 | 1 | 월별 | “월별”,”누계”,”전체” |
| arsltSumNum | 실적합계건수 | 25 | 1 | 338553 | 실적합계건수 (합계건수) |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 12880757279553.116 | 실적합계금액 (원,원화) (합계금액) |
| gnrlCmptArsltNum | 일반경쟁실적건수 | 25 | 1 | 117917 | 일반경쟁실적건수 |
| gnrlCmptArsltAmt | 일반경쟁실적금액 | 30 | 1 | 2651697659947 | 일반경쟁실적금액 (원,원화) |
| lmtCmptArsltNum | 제한경쟁실적건수 | 25 | 1 | 62998 | 제한경쟁실적건수 |
| lmtCmptArsltAmt | 제한경쟁실적금액 | 30 | 1 | 6786556939101 | 제한경쟁실적금액 (원,원화) |
| dsgntCmptArsltNum | 지명경쟁실적건수 | 25 | 1 | 383 | 지명경쟁실적건수 |
| dsgntCmptArsltAmt | 지명경쟁실적금액 | 30 | 1 | 202207794644 | 지명경쟁실적금액 (원,원화) |
| prvtcntrctArsltNum | 수의계약실적건수 | 25 | 1 | 153962 | 수의계약실적건수 |
| prvtcntrctArsltAmt | 수의계약실적금액 | 30 | 1 | 3205906866107 | 수의계약실적금액 (원,원화) |
| unClsfcArsltNum | 미분류실적건수 | 25 | 1 | 215 | 미분류실적건수 |
| unClsfcArsltAmt | 미분류실적금액 | 30 | 1 | 34388020137.116 | 미분류실적금액 (원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getCntrctMthdAccotSttus?srchBssYmBgn=201601&srchBssYmEnd=201601&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bssYear/> <bssMnth/> <statDivNm>전체</statDivNm> <arsltSumNum>156060</arsltSumNum> <arsltSumAmt>8721348697602</arsltSumAmt> <gnrlCmptArsltNum>76588</gnrlCmptArsltNum> <gnrlCmptArsltAmt>1851655685451</gnrlCmptArsltAmt> <lmtCmptArsltNum>19308</lmtCmptArsltNum> <lmtCmptArsltAmt>4971146487459</lmtCmptArsltAmt> <dsgntCmptArsltNum>282</dsgntCmptArsltNum> <dsgntCmptArsltAmt>116433309221</dsgntCmptArsltAmt> <prvtcntrctArsltNum>59749</prvtcntrctArsltNum> <prvtcntrctArsltAmt>1772492666176</prvtcntrctArsltAmt> <unClsfcArsltNum>133</unClsfcArsltNum> <unClsfcArsltAmt>9620549295</unClsfcArsltAmt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>월별</statDivNm> <arsltSumNum>156060</arsltSumNum> <arsltSumAmt>8721348697602</arsltSumAmt> <gnrlCmptArsltNum>76588</gnrlCmptArsltNum> <gnrlCmptArsltAmt>1851655685451</gnrlCmptArsltAmt> <lmtCmptArsltNum>19308</lmtCmptArsltNum> <lmtCmptArsltAmt>4971146487459</lmtCmptArsltAmt> <dsgntCmptArsltNum>282</dsgntCmptArsltNum> <dsgntCmptArsltAmt>116433309221</dsgntCmptArsltAmt> <prvtcntrctArsltNum>59749</prvtcntrctArsltNum> <prvtcntrctArsltAmt>1772492666176</prvtcntrctArsltAmt> <unClsfcArsltNum>133</unClsfcArsltNum> <unClsfcArsltAmt>9620549295</unClsfcArsltAmt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>누계</statDivNm> <arsltSumNum>156060</arsltSumNum> <arsltSumAmt>8721348697602</arsltSumAmt> <gnrlCmptArsltNum>76588</gnrlCmptArsltNum> <gnrlCmptArsltAmt>1851655685451</gnrlCmptArsltAmt> <lmtCmptArsltNum>19308</lmtCmptArsltNum> <lmtCmptArsltAmt>4971146487459</lmtCmptArsltAmt> <dsgntCmptArsltNum>282</dsgntCmptArsltNum> <dsgntCmptArsltAmt>116433309221</dsgntCmptArsltAmt> <prvtcntrctArsltNum>59749</prvtcntrctArsltNum> <prvtcntrctArsltAmt>1772492666176</prvtcntrctArsltAmt> <unClsfcArsltNum>133</unClsfcArsltNum> <unClsfcArsltAmt>9620549295</unClsfcArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>7</totalCount> </body> </response> |

[지역제한 현황]오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 5 | 오퍼레이션명(국문) | 지역제한 현황 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getRgnLmtSttus |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위를 입력하여 기준년도, 기준월, 통계구분명, 계약총액,입찰계약총액, 미입찰계약총액, 지역제한총액, 지역제한총액비율 등 지역제한 현황을 조회 |  |  |
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
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201601 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bssYear | 기준년도 | 4 | 0 | 2016 | 기준년도 "YYYY" 전체, 누계 일 경우 값 없음 |
| bssMnth | 기준월 | 2 | 0 | 03 | 기준월 "MM" 전체, 누계 일 경우 값 없음 |
| statDivNm | 통계구분명 | 30 | 1 | 월별 | “월별”,”누계”,”전체” |
| cntrctTotamt | 계약총액 | 30 | 1 | 12880757279936 | 계약총액(A+B) (원,원화) |
| bidCntrctTotamt | 입찰계약총액 | 30 | 1 | 9981654684652 | 입찰계약총액 화면에서 계약총액(입찰O) (A) (원,원화) |
| unBidCntrctTotamt | 미입찰계약총액 | 30 | 1 | 2899102595284 | 미입찰계약총액 화면에서 계약총액(입찰X) (B) (원,원화) |
| rgnLmtTotamt | 지역제한총액 | 30 | 1 | 2809441344389 | 지역제한총액(C) (원,원화) |
| rgnLmtTotamtRt | 지역제한총액비율 | 10 | 1 | 28.15 | 지역제한합계비율 (C/A) (%) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getRgnLmtSttus?srchBssYmBgn=201601&srchBssYmEnd=201601&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bssYear/> <bssMnth/> <statDivNm>전체</statDivNm> <cntrctTotamt>8721348697602</cntrctTotamt> <bidCntrctTotamt>5287648434530</bidCntrctTotamt> <unBidCntrctTotamt>3433700263072</unBidCntrctTotamt> <rgnLmtTotamt>1064137174391</rgnLmtTotamt> <rgnLmtTotamtRt>20.12</rgnLmtTotamtRt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>월별</statDivNm> <cntrctTotamt>8721348697602</cntrctTotamt> <bidCntrctTotamt>5287648434530</bidCntrctTotamt> <unBidCntrctTotamt>3433700263072</unBidCntrctTotamt> <rgnLmtTotamt>1064137174391</rgnLmtTotamt> <rgnLmtTotamtRt>20.12</rgnLmtTotamtRt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>누계</statDivNm> <cntrctTotamt>8721348697602</cntrctTotamt> <bidCntrctTotamt>5287648434530</bidCntrctTotamt> <unBidCntrctTotamt>3433700263072</unBidCntrctTotamt> <rgnLmtTotamt>1064137174391</rgnLmtTotamt> <rgnLmtTotamtRt>20.12</rgnLmtTotamtRt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>7</totalCount> </body> </response> |

[지역의무공동계약 현황]오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 6 | 오퍼레이션명(국문) | 지역의무공동계약 현황 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getRgnDutyCmmnCntrctSttus |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위를 입력하여 기준년도, 기준월, 통계구분명, 계약총액,입찰계약총액, 미입찰계약총액, 지역의무공동계약총액, 지역의무공동계약총액비율 등 지역의무공동계약 현황(을)를 조회 |  |  |
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
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201601 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bssYear | 기준년도 | 4 | 0 | 2016 | 기준년도 "YYYY" 전체, 누계 일 경우 값 없음 |
| bssMnth | 기준월 | 2 | 0 | 03 | 기준월 "MM" 전체, 누계 일 경우 값 없음 |
| statDivNm | 통계구분명 | 30 | 1 | 월별 | “월별”,”누계”,”전체” |
| cnstwkCntrctTotamt | 공사계약총액 | 30 | 1 | 6392347808578 | 공사계약총액(A+B) (원,원화) |
| bidCntrctTotamt | 입찰계약총액 | 30 | 1 | 4782373074268 | 계약총액 (입찰O)(A) (원,원화) |
| unBidCntrctTotamt | 미입찰계약총액 | 30 | 1 | 1609974734310 | 미입찰계약총액(B) (원,원화) |
| rgnDutyCmmnCntrctTotamt | 지역의무공동계약총액 | 30 | 1 | 761691061628 | 지역의무공동계약총액(C) (원,원화) |
| rgnDutyCmmnCntrctTotamtRt | 지역의무공동계약총액비율 | 10 | 1 | 15.93 | 지역의무공동계약총액비율(C/A) (%) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getRgnDutyCmmnCntrctSttus?srchBssYmBgn=201601&srchBssYmEnd=201603&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bssYear/> <bssMnth/> <statDivNm>전체</statDivNm> <cnstwkCntrctTotamt>15612824138261</cnstwkCntrctTotamt> <bidCntrctTotamt>10118590874660</bidCntrctTotamt> <unBidCntrctTotamt>5494233263601</unBidCntrctTotamt> <rgnDutyCmmnCntrctTotamt>1524652707880</rgnDutyCmmnCntrctTotamt> <rgnDutyCmmnCntrctTotamtRt>15.07</rgnDutyCmmnCntrctTotamtRt> </item> <item> <bssYear>2016</bssYear> <bssMnth>03</bssMnth> <statDivNm>월별</statDivNm> <cnstwkCntrctTotamt>6392347808578</cnstwkCntrctTotamt> <bidCntrctTotamt>4782373074268</bidCntrctTotamt> <unBidCntrctTotamt>1609974734310</unBidCntrctTotamt> <rgnDutyCmmnCntrctTotamt>761691061628</rgnDutyCmmnCntrctTotamt> <rgnDutyCmmnCntrctTotamtRt>15.93</rgnDutyCmmnCntrctTotamtRt> </item> <item> <bssYear>2016</bssYear> <bssMnth>03</bssMnth> <statDivNm>누계</statDivNm> <cnstwkCntrctTotamt>15612824138261</cnstwkCntrctTotamt> <bidCntrctTotamt>10118590874660</bidCntrctTotamt> <unBidCntrctTotamt>5494233263601</unBidCntrctTotamt> <rgnDutyCmmnCntrctTotamt>1524652707880</rgnDutyCmmnCntrctTotamt> <rgnDutyCmmnCntrctTotamtRt>15.07</rgnDutyCmmnCntrctTotamtRt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>7</totalCount> </body> </response> |

[조달목적물(업무대상)별 현황]오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 7 | 오퍼레이션명(국문) | 조달목적물(업무대상)별 현황 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntObjectBsnsObjAccotSttus |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위를 입력하여 기준년도, 기준월, 통계구분명, 실적합계건수, 실적합계금액, 물품실적건수, 물품실적금액, 공사실적건수, 공사실적금액, 일반용역실적건수, 일반용역실적금액, 기술용역실적건수, 기술용역실적금액, 미분류실적건수, 미분류실적금액 등 조달목적물(업무대상)별 현황(을)를 조회 |  |  |
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
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201601 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bssYear | 기준년도 | 4 | 0 | 2016 | 기준년도 "YYYY" 전체, 누계 일 경우 값 없음 |
| bssMnth | 기준월 | 2 | 0 | 03 | 기준월 "MM" 전체, 누계 일 경우 값 없음 |
| statDivNm | 통계구분명 | 30 | 1 | 월별 | “월별”,”누계”,”전체” |
| arsltSumNum | 실적합계건수 | 25 | 1 | 335475 | 실적합계건수 화면의 합계건수 |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 12880757279936.116 | 실적합계금액 (원,원화) 화면의 합계금액 |
| prdctArsltNum | 물품실적건수 | 25 | 1 | 267845 | 물품실적건수 |
| prdctArsltAmt | 물품실적금액 | 30 | 1 | 3989006643612.116 | 물품실적금액 (원,원화) |
| cnstwkArsltNum | 공사실적건수 | 25 | 1 | 33263 | 공사실적건수 |
| cnstwkArsltAmt | 공사실적금액 | 30 | 1 | 6392347808578 | 공사실적금액 (원,원화) |
| gnrlSrvceArsltNum | 일반용역실적건수 | 25 | 1 | 25858 | 일반용역실적건수 |
| gnrlSrvceArsltAmt | 일반용역실적금액 | 30 | 1 | 1870891922706 | 일반용역실적금액 (원,원화) |
| techSrvceArsltNum | 기술용역실적건수 | 25 | 1 | 8509 | 기술용역실적건수 |
| techSrvceArsltAmt | 기술용역실적금액 | 30 | 1 | 628510905040 | 기술용역실적금액 (원,원화) |
| unClsfcArsltNum | 미분류실적건수 | 25 | 1 | 0 | 미분류실적건수 |
| unClsfcArsltAmt | 미분류실적금액 | 30 | 1 | 0 | 미분류실적금액 (원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getPrcrmntObjectBsnsObjAccotSttus?srchBssYmBgn=201601&srchBssYmEnd=201603&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bssYear/> <bssMnth/> <statDivNm>전체</statDivNm> <arsltSumNum>156060</arsltSumNum> <arsltSumAmt>8721348697602</arsltSumAmt> <prdctArsltNum>124107</prdctArsltNum> <prdctArsltAmt>1797145782977</prdctArsltAmt> <cnstwkArsltNum>10136</cnstwkArsltNum> <cnstwkArsltAmt>4845340066904</cnstwkArsltAmt> <gnrlSrvceArsltNum>14467</gnrlSrvceArsltNum> <gnrlSrvceArsltAmt>1604113774853</gnrlSrvceArsltAmt> <techSrvceArsltNum>7350</techSrvceArsltNum> <techSrvceArsltAmt>474749072868</techSrvceArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>월별</statDivNm> <arsltSumNum>156060</arsltSumNum> <arsltSumAmt>8721348697602</arsltSumAmt> <prdctArsltNum>124107</prdctArsltNum> <prdctArsltAmt>1797145782977</prdctArsltAmt> <cnstwkArsltNum>10136</cnstwkArsltNum> <cnstwkArsltAmt>4845340066904</cnstwkArsltAmt> <gnrlSrvceArsltNum>14467</gnrlSrvceArsltNum> <gnrlSrvceArsltAmt>1604113774853</gnrlSrvceArsltAmt> <techSrvceArsltNum>7350</techSrvceArsltNum> <techSrvceArsltAmt>474749072868</techSrvceArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> <item> <bssYear>2016</bssYear> <bssMnth>01</bssMnth> <statDivNm>누계</statDivNm> <arsltSumNum>156060</arsltSumNum> <arsltSumAmt>8721348697602</arsltSumAmt> <prdctArsltNum>124107</prdctArsltNum> <prdctArsltAmt>1797145782977</prdctArsltAmt> <cnstwkArsltNum>10136</cnstwkArsltNum> <cnstwkArsltAmt>4845340066904</cnstwkArsltAmt> <gnrlSrvceArsltNum>14467</gnrlSrvceArsltNum> <gnrlSrvceArsltAmt>1604113774853</gnrlSrvceArsltAmt> <techSrvceArsltNum>7350</techSrvceArsltNum> <techSrvceArsltAmt>474749072868</techSrvceArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>7</totalCount> </body> </response> |

[수요기관별 기업구분별 실적]오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 8 | 오퍼레이션명(국문) | 수요기관별 기업구분별 실적 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getDminsttAccotEntrprsDivAccotArslt |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위, 수요기관코드, 수요기관명, 하위기관실적포함여부, 연계시스템코드을 입력하여 수요기관코드, 수요기관명, 실적합계건수, 실적합계금액, 대기업실적건수, 대기업실적금액, 중견기업실적건수, 중견기업실적금액, 중소기업실적건수, 중소기업실적금액, 외국기업실적건수, 외국기업실적금액, 기타실적건수, 기타실적금액, 미분류실적건수, 미분류실적금액 등 수요기관별 기업구분별 실적 (을)를 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플 데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| srchBssYmBgn | 검색기준년월시작 | 6 | 1 | 201603 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 1 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |
| dminsttCd | 수요기관코드 | 7 | 0 | 1210000 | 검색하고자하는 수요기관코드 |
| dminsttNm | 수요기관명 | 200 | 0 | 국세청 | 검색하고자 하는 수요기관명 |
| lwrInsttArsltInclsnYn | 하위기관실적포함여부 | 1 | 0 | Y | 검색하고자하는 하위기관실적포함여부 |
| linkSystmCd | 연계시스템코드 | 4 | 0 | 1000 | 검색하고자하는 연계시스템코드
1000:나라장터(중앙조달)
1100:나라장터(자체조달)
2100:강원랜드
2200:농수산물사이버거래소(학교급식)
2300:한국국토정보공사
2400:방위사업청
2500:인천국제공항공사
2600:학교장터
2700:한국가스공사
2800:한국과학기술연구원
2900:한국국제협력단
3000:한국도로공사
3100:한국마사회
3200:한국석유공사
3300:한국수력원자력
3400:한국수자원공사
3500:한국전기연구원
3600:한국전력공사
3700:한국전자통신연구원
3800:한국조폐공사
3900:한국지역난방공사
4000:한국철도공사
4100:한국철도시설공단
4200:한국토지주택공사
4300:한전KDN
4400:디브레인
9000:수기 * 연계시스템코드를 입력하지 않을 경우는 전체시스템으로부터 수집된 실적이 보임 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| linkSystmNm | 연계시스템명 | 100 | 1 | 전체 | 전체로 조회시에는 “전체” 연계시스템 코드로 검색시에는 연계시스템명 |
| dminsttCd | 수요기관코드 | 7 | 0 | 1210000 | 수요기관코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드가 표기됨 |
| dminsttNm | 수요기관명 | 200 | 1 | 국세청 | 중앙조달인 경우 조달사업에 관한 법률 제2조(정의)에 따라 수요물자의 구매 공급 또는 시설공사 계약의 체결을 조달청장에게 요청할 수 있도록 조달청장이 인정하여 등록한 기관 또는 자체전자조달시스템을 이용하는 기관인 경우 계약을 의뢰한 기관의 명으로 공고기관과 수요기관이 동일할 수 있음 |
| arsltSumNum | 실적합계건수 | 25 | 1 | 96 | 합계건수 |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 6361424481 | 합계금액 (원,원화) |
| lrgeEntrprsArsltNum | 대기업실적건수 | 25 | 1 | 52 | 대기업실적건수 |
| lrgeEntrprsArsltAmt | 대기업실적금액 | 30 | 1 | 573121412 | 대기업실적금액 (원,원화) |
| entrprsOfMidStdArsltNum | 중견기업실적건수 | 25 | 1 | 1 | 중견기업실적건수 |
| entrprsOfMidStdArsltAmt | 중견기업실적금액 | 30 | 1 | 823957923 | 중견기업실적금액 (원,원화) |
| smetprArsltNum | 중소기업실적건수 | 25 | 1 | 42 | 중소기업실적건수 |
| smetprArsltAmt | 중소기업실적금액 | 30 | 1 | 4944788766 | 중소기업실적금액 (원,원화) |
| frgncntryEntrprsArsltNum | 외국기업실적건수 | 25 | 1 | 0 | 외국기업실적건수 |
| frgncntryEntrprsArsltAmt | 외국기업실적금액 | 30 | 1 | 0 | 외국기업실적금액 (원,원화) |
| nonPrftmkCorprtArsltNum | 기타실적건수 | 25 | 1 | 1 | 기타실적건수 |
| nonPrftmkCorprtArsltAmt | 기타실적금액 | 30 | 1 | 19556380 | 기타실적금액 (원,원화) |
| unClsfcArsltNum | 미분류실적건수 | 25 | 1 | 0 | 미분류실적건수 |
| unClsfcArsltAmt | 미분류실적금액 | 30 | 1 | 0 | 미분류실적금액 (원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getDminsttAccotEntrprsDivAccotArslt?srchBssYmBgn=201601&srchBssYmEnd=201601&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <linkSystmNm>전체</linkSystmNm> <dminsttCd>B552130</dminsttCd> <dminsttNm>가축위생방역지원본부</dminsttNm> <arsltSumNum>7</arsltSumNum> <arsltSumAmt>1717897360</arsltSumAmt> <lrgeEntrprsArsltNum>3</lrgeEntrprsArsltNum> <lrgeEntrprsArsltAmt>984197400</lrgeEntrprsArsltAmt> <EntrprsOfMidStdArsltNum>0</EntrprsOfMidStdArsltNum> <EntrprsOfMidStdArsltAmt>0</EntrprsOfMidStdArsltAmt> <smetprArsltNum>4</smetprArsltNum> <smetprArsltAmt>733699960</smetprArsltAmt> <frgncntryEntrprsArsltNum>0</frgncntryEntrprsArsltNum> <frgncntryEntrprsArsltAmt>0</frgncntryEntrprsArsltAmt> <nonPrftmkCorprtArsltNum>0</nonPrftmkCorprtArsltNum> <nonPrftmkCorprtArsltAmt>0</nonPrftmkCorprtArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> <item> <linkSystmNm>전체</linkSystmNm> <dminsttCd>D272001</dminsttCd> <dminsttNm>가축위생방역지원본부 강원도본부</dminsttNm> <arsltSumNum>58</arsltSumNum> <arsltSumAmt>7167940</arsltSumAmt> <lrgeEntrprsArsltNum>58</lrgeEntrprsArsltNum> <lrgeEntrprsArsltAmt>7167940</lrgeEntrprsArsltAmt> <EntrprsOfMidStdArsltNum>0</EntrprsOfMidStdArsltNum> <EntrprsOfMidStdArsltAmt>0</EntrprsOfMidStdArsltAmt> <smetprArsltNum>0</smetprArsltNum> <smetprArsltAmt>0</smetprArsltAmt> <frgncntryEntrprsArsltNum>0</frgncntryEntrprsArsltNum> <frgncntryEntrprsArsltAmt>0</frgncntryEntrprsArsltAmt> <nonPrftmkCorprtArsltNum>0</nonPrftmkCorprtArsltNum> <nonPrftmkCorprtArsltAmt>0</nonPrftmkCorprtArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>19461</totalCount> </body> </response> |

[수요기관별 계약방법별 실적]오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 9 | 오퍼레이션명(국문) | 수요기관별 계약방법별 실적 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getDminsttAccotCntrctMthdAccotArslt |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위, 수요기관코드, 수요기관명, 하위기관실적포함여부, 연계시스템코드을 입력하여 수요기관코드, 수요기관명, 실적합계건수, 실적합계금액, 일반경쟁실적건수, 일반경쟁실적금액, 제한경쟁실적건수, 제한경쟁실적금액, 지명경쟁실적건수, 지명경쟁실적금액, 수의계약실적건수, 수의계약실적금액, 미분류실적건수, 미분류실적금액, 수기등록실적건수, 수기등록실적금액 등 수요기관별 계약방법별 실적을 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플 데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201603 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |
| dminsttCd | 수요기관코드 | 7 | 0 | 1210000 | 검색하고자하는 수요기관코드 |
| dminsttNm | 수요기관명 | 200 | 0 | 기획재정부 | 검색하고자하는 수요기관명 |
| lwrInsttArsltInclsnYn | 하위기관실적포함여부 | 1 | 0 | Y | 검색하고자하는 하위기관실적포함여부 |
| linkSystmCd | 연계시스템코드 | 4 | 0 | 1000 | 검색하고자하는 연계시스템코드
1000:나라장터(중앙조달)
1100:나라장터(자체조달)
2100:강원랜드
2200:농수산물사이버거래소(학교급식)
2300:한국국토정보공사
2400:방위사업청
2500:인천국제공항공사
2600:학교장터
2700:한국가스공사
2800:한국과학기술연구원
2900:한국국제협력단
3000:한국도로공사
3100:한국마사회
3200:한국석유공사
3300:한국수력원자력
3400:한국수자원공사
3500:한국전기연구원
3600:한국전력공사
3700:한국전자통신연구원
3800:한국조폐공사
3900:한국지역난방공사
4000:한국철도공사
4100:한국철도시설공단
4200:한국토지주택공사
4300:한전KDN
4400:디브레인
9000:수기 * 연계시스템코드를 입력하지 않을 경우는 전체시스템으로부터 수집된 실적이 보임 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| linkSystmNm | 연계시스템명 | 100 | 1 | 전체 | 전체로 조회시에는 “전체” 연계시스템 코드로 검색시에는 연계시스템명 |
| dminsttCd | 수요기관코드 | 7 | 0 | 1210000 | 수요기관코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드가 표기됨 |
| dminsttNm | 수요기관명 | 200 | 1 | 국세청 | 중앙조달인 경우 조달사업에 관한 법률 제2조(정의)에 따라 수요물자의 구매 공급 또는 시설공사 계약의 체결을 조달청장에게 요청할 수 있도록 조달청장이 인정하여 등록한 기관 또는 자체전자조달시스템을 이용하는 기관인 경우 계약을 의뢰한 기관의 명으로 공고기관과 수요기관이 동일할 수 있음 |
| arsltSumNum | 실적합계건수 | 25 | 1 | 94 | 합계건수 |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 6361424482 | 합계금액 (원,원화) |
| gnrlCmptArsltNum | 일반경쟁실적건수 | 25 | 1 | 64 | 일반경쟁실적건수 |
| gnrlCmptArsltAmt | 일반경쟁실적금액 | 30 | 1 | 210327580 | 일반경쟁실적금액 (원,원화) |
| lmtCmptArsltNum | 제한경쟁실적건수 | 25 | 1 | 1 | 제한경쟁실적건수 |
| lmtCmptArsltAmt | 제한경쟁실적금액 | 30 | 1 | 823957923 | 제한경쟁실적금액 (원,원화) |
| dsgntCmptArsltNum | 지명경쟁실적건수 | 25 | 1 | 0 | 지명경쟁실적건수 |
| dsgntCmptArsltAmt | 지명경쟁실적금액 | 30 | 1 | 0 | 지명경쟁실적금액 (원,원화) |
| prvtcntrctArsltNum | 수의계약실적건수 | 25 | 1 | 6 | 수의계약실적건수 |
| prvtcntrctArsltAmt | 수의계약실적금액 | 30 | 1 | 2845633300 | 수의계약실적금액 (원,원화) |
| unClsfcArsltNum | 미분류실적건수 | 25 | 1 | 0 | 미분류실적건수 |
| unClsfcArsltAmt | 미분류실적금액 | 30 | 1 | 0 | 미분류실적금액 (원,원화) |
| mnllbrRgstArsltNum | 수기등록실적건수 | 25 | 1 | 0 | 수기등록실적건수 비전자계약실적건수 |
| mnllbrRgstArsltAmt | 수기등록실적금액 | 30 | 1 | 0 | 수기등록실적금액 (원,원화) 비전자계약실적금액 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getDminsttAccotCntrctMthdAccotArslt?srchBssYmBgn=201601&srchBssYmEnd=201603&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <linkSystmNm>전체</linkSystmNm> <dminsttCd>B552130</dminsttCd> <dminsttNm>가축위생방역지원본부</dminsttNm> <arsltSumNum>7</arsltSumNum> <arsltSumAmt>1717897360</arsltSumAmt> <gnrlCmptArsltNum>5</gnrlCmptArsltNum> <gnrlCmptArsltAmt>1106138000</gnrlCmptArsltAmt> <lmtCmptArsltNum>0</lmtCmptArsltNum> <lmtCmptArsltAmt>0</lmtCmptArsltAmt> <dsgntCmptArsltNum>0</dsgntCmptArsltNum> <dsgntCmptArsltAmt>0</dsgntCmptArsltAmt> <prvtcntrctArsltNum>2</prvtcntrctArsltNum> <prvtcntrctArsltAmt>611759360</prvtcntrctArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> <mnllbrRgstArsltNum>0</mnllbrRgstArsltNum> <mnllbrRgstArsltAmt>0</mnllbrRgstArsltAmt> </item> <item> <linkSystmNm>전체</linkSystmNm> <dminsttCd>D272001</dminsttCd> <dminsttNm>가축위생방역지원본부 강원도본부</dminsttNm> <arsltSumNum>58</arsltSumNum> <arsltSumAmt>7167940</arsltSumAmt> <gnrlCmptArsltNum>58</gnrlCmptArsltNum> <gnrlCmptArsltAmt>7167940</gnrlCmptArsltAmt> <lmtCmptArsltNum>0</lmtCmptArsltNum> <lmtCmptArsltAmt>0</lmtCmptArsltAmt> <dsgntCmptArsltNum>0</dsgntCmptArsltNum> <dsgntCmptArsltAmt>0</dsgntCmptArsltAmt> <prvtcntrctArsltNum>0</prvtcntrctArsltNum> <prvtcntrctArsltAmt>0</prvtcntrctArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> <mnllbrRgstArsltNum>0</mnllbrRgstArsltNum> <mnllbrRgstArsltAmt>0</mnllbrRgstArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>19461</totalCount> </body> </response> |

[수요기관별 업무대상별 실적] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 10 | 오퍼레이션명(국문) | 수요기관별 업무대상별 실적 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getDminsttAccotBsnsObjAccotArslt |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위, 수요기관코드, 수요기관명, 하위기관실적포함여부, 연계시스템코드을 입력하여 수요기관코드, 수요기관명, 실적합계건수, 실적합계금액, 물품실적건수, 물품실적금액, 공사실적건수, 공사실적금액, 일반용역실적건수, 일반용역실적금액, 기술용역실적건수, 기술용역실적금액, 미분류실적건수, 미분류실적금액 등 수요기관별 업무대상별 실적을 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플 데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201601 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |
| dminsttCd | 수요기관코드 | 7 | 0 | 1230000 | 검색하고자하는 수요기관코드 |
| dminsttNm | 수요기관명 | 200 | 0 | 조달청 | 검색하고자하는 수요기관명 |
| lwrInsttArsltInclsnYn | 하위기관실적포함여부 | 1 | 0 | Y | 검색하고자하는 하위기관실적포함여부 |
| linkSystmCd | 연계시스템코드 | 4 | 0 | 1000 | 검색하고자하는 연계시스템코드
1000:나라장터(중앙조달)
1100:나라장터(자체조달)
2100:강원랜드
2200:농수산물사이버거래소(학교급식)
2300:한국국토정보공사
2400:방위사업청
2500:인천국제공항공사
2600:학교장터
2700:한국가스공사
2800:한국과학기술연구원
2900:한국국제협력단
3000:한국도로공사
3100:한국마사회
3200:한국석유공사
3300:한국수력원자력
3400:한국수자원공사
3500:한국전기연구원
3600:한국전력공사
3700:한국전자통신연구원
3800:한국조폐공사
3900:한국지역난방공사
4000:한국철도공사
4100:한국철도시설공단
4200:한국토지주택공사
4300:한전KDN
4400:디브레인
9000:수기 * 연계시스템코드를 입력하지 않을 경우는 전체시스템으로부터 수집된 실적이 보임 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| linkSystmNm | 연계시스템명 | 100 | 1 | 전체 | 전체로 조회시에는 “전체” 연계시스템코드로 검색시에는 연계시스템명 |
| dminsttCd | 수요기관코드 | 7 | 0 | 1210000 | 수요기관코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드가 표기됨 |
| dminsttNm | 수요기관명 | 200 | 1 | 국세청 | 중앙조달인 경우 조달사업에 관한 법률 제2조(정의)에 따라 수요물자의 구매 공급 또는 시설공사 계약의 체결을 조달청장에게 요청할 수 있도록 조달청장이 인정하여 등록한 기관 또는 자체전자조달시스템을 이용하는 기관인 경우 계약을 의뢰한 기관의 명으로 공고기관과 수요기관이 동일할 수 있음 |
| arsltSumNum | 실적합계건수 | 25 | 1 | 94 | 실적합계건수 |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 6361424482 | 실적합계금액 (원,원화) |
| prdctArsltNum | 물품실적건수 | 25 | 1 | 81 | 물품실적건수 |
| prdctArsltAmt | 물품실적금액 | 30 | 1 | 2223999640 | 물품실적금액 (원,원화) |
| cnstwkArsltNum | 공사실적건수 | 25 | 1 | 0 | 공사실적건수 |
| cnstwkArsltAmt | 공사실적금액 | 30 | 1 | 0 | 공사실적금액 (원,원화) |
| gnrlSrvceArsltNum | 일반용역실적건수 | 25 | 1 | 13 | 일반용역실적건수 |
| gnrlSrvceArsltAmt | 일반용역실적금액 | 30 | 1 | 4137424842 | 일반용역실적금액 (원,원화) |
| techSrvceArsltNum | 기술용역실적건수 | 25 | 1 | 0 | 기술용역실적건수 |
| techSrvceArsltAmt | 기술용역실적금액 | 30 | 1 | 0 | 기술용역실적금액 (원,원화) |
| unClsfcArsltNum | 미분류실적건수 | 25 | 1 | 0 | 미분류실적건수 |
| unClsfcArsltAmt | 미분류실적금액 | 30 | 1 | 45000 | 미분류실적금액 (원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getDminsttAccotBsnsObjAccotArslt?srchBssYmBgn=201603&srchBssYmEnd=201603&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <linkSystmNm>전체</linkSystmNm> <dminsttCd>B552130</dminsttCd> <dminsttNm>가축위생방역지원본부</dminsttNm> <arsltSumNum>5</arsltSumNum> <arsltSumAmt>380141960</arsltSumAmt> <prdctArsltNum>5</prdctArsltNum> <prdctArsltAmt>380141960</prdctArsltAmt> <cnstwkArsltNum>0</cnstwkArsltNum> <cnstwkArsltAmt>0</cnstwkArsltAmt> <gnrlSrvceArsltNum>0</gnrlSrvceArsltNum> <gnrlSrvceArsltAmt>0</gnrlSrvceArsltAmt> <techSrvceArsltNum>0</techSrvceArsltNum> <techSrvceArsltAmt>0</techSrvceArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> <item> <linkSystmNm>전체</linkSystmNm> <dminsttCd>D272001</dminsttCd> <dminsttNm>가축위생방역지원본부 강원도본부</dminsttNm> <arsltSumNum>22</arsltSumNum> <arsltSumAmt>3136010</arsltSumAmt> <prdctArsltNum>22</prdctArsltNum> <prdctArsltAmt>3136010</prdctArsltAmt> <cnstwkArsltNum>0</cnstwkArsltNum> <cnstwkArsltAmt>0</cnstwkArsltAmt> <gnrlSrvceArsltNum>0</gnrlSrvceArsltNum> <gnrlSrvceArsltAmt>0</gnrlSrvceArsltAmt> <techSrvceArsltNum>0</techSrvceArsltNum> <techSrvceArsltAmt>0</techSrvceArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>18225</totalCount> </body> </response> |

[수요기관별 시스템유형별 실적] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 11 | 오퍼레이션명(국문) | 수요기관별 시스템유형별 실적 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getDminsttAccotSystmTyAccotArslt |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위, 수요기관코드, 수요기관명, 하위기관실적포함여부, 연계시스템코드을 입력하여 수요기관코드, 수요기관명, 실적합계건수, 실적합계금액, 중앙조달실적건수, 중앙조달실적금액, 자체조달실적건수, 자체조달실적금액, 자체조달시스템실적건수, 자체조달시스템실적금액, 수기조달실적건수, 수기조달실적금액 등 수요기관별 시스템유형별 실적을 조회 |  |  |
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
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201601 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |
| dminsttCd | 수요기관코드 | 7 | 0 | 1210000 | 검색하고자하는 수요기관코드 |
| dminsttNm | 수요기관명 | 200 | 0 | 국세청 | 검색하고자하는 수요기관명 |
| lwrInsttArsltInclsnYn | 하위기관실적포함여부 | 1 | 0 | Y | 하위기관실적포함여부 |
| linkSystmCd | 연계시스템코드 | 4 | 0 | 1000 | 검색하고자하는 연계시스템코드
1000:나라장터(중앙조달)
1100:나라장터(자체조달)
2100:강원랜드
2200:농수산물사이버거래소(학교급식)
2300:한국국토정보공사
2400:방위사업청
2500:인천국제공항공사
2600:학교장터
2700:한국가스공사
2800:한국과학기술연구원
2900:한국국제협력단
3000:한국도로공사
3100:한국마사회
3200:한국석유공사
3300:한국수력원자력
3400:한국수자원공사
3500:한국전기연구원
3600:한국전력공사
3700:한국전자통신연구원
3800:한국조폐공사
3900:한국지역난방공사
4000:한국철도공사
4100:한국철도시설공단
4200:한국토지주택공사
4300:한전KDN
4400:디브레인
9000:수기 * 연계시스템코드를 입력하지 않을 경우는 전체시스템으로부터 수집된 실적이 보임 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| linkSystmNm | 연계시스템명 | 100 | 1 | 전체 | 전체로 조회시에는 “전체” 연계시스템코드로 검색시에는 연계시스템명 |
| dminsttCd | 수요기관코드 | 7 | 0 | 1210000 | 수요기관코드로 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드가 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드가 표기됨 |
| dminsttNm | 수요기관명 | 200 | 1 | 국세청 | 중앙조달인 경우 조달사업에 관한 법률 제2조(정의)에 따라 수요물자의 구매 공급 또는 시설공사 계약의 체결을 조달청장에게 요청할 수 있도록 조달청장이 인정하여 등록한 기관 또는 자체전자조달시스템을 이용하는 기관인 경우 계약을 의뢰한 기관의 명으로 공고기관과 수요기관이 동일할 수 있음 |
| arsltSumNum | 실적합계건수 | 25 | 1 | 94 | 실적합계건수 |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 6361424482 | 실적합계금액 (원,원화) |
| centrPrcrmntArsltNum | 중앙조달실적건수 | 25 | 1 | 82 | 나라장터중앙조달공급건수 |
| centrPrcrmntArsltAmt | 중앙조달실적금액 | 30 | 1 | 5516018550 | 나라장터중앙조달공급금액 (원,원화) |
| selfPrcrmntArsltNum | 자체조달실적건수 | 25 | 1 | 12 | 나라장터(자체) 나라장터를 통하여 공공기관들이 자체 발주(공고)하여 조달한 건수 |
| selfPrcrmntArsltAmt | 자체조달실적금액 | 30 | 1 | 845405932 | 나라장터(자체) 나라장터를 통하여 공공기관들이 자체 발주(공고)하여 조달한 금액 (원,원화) |
| systmPrcrmntArsltNum | 자체조달시스템실적건수 | 25 | 1 | 0 | 자체조달시스템실적건수, 공공기관이 운영하고 있는 전자조달시스템으로, 나라장터 외 23개 자체조달시스템으로 조달한 건수 |
| systmPrcrmntArsltAmt | 자체조달시스템실적금액 | 30 | 1 | 0 | 자체조달시스템실적금액, 공공기관이 운영하고 있는 전자조달시스템으로, 나라장터 외 23개 자체조달시스템으로 조달한 금액(원,원화) |
| mnllbrPrcrmntArsltNum | 수기조달실적건수 | 25 | 1 | 0 | 수기등록실적건수 전자조달시스템을 이용하지 않은 비전자계약실적건수 |
| mnllbrPrcrmntArsltAmt | 수기조달실적금액 | 30 | 1 | 0 | 수기등록실적금액 전자조달시스템을 이용하지 않은 비전자계약실적금액(원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getDminsttAccotSystmTyAccotArslt?srchBssYmBgn=201601&srchBssYmEnd=201603&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <linkSystmNm>전체</linkSystmNm> <dminsttCd>B552130</dminsttCd> <dminsttNm>가축위생방역지원본부</dminsttNm> <arsltSumNum>7</arsltSumNum> <arsltSumAmt>1717897360</arsltSumAmt> <centrPrcrmntArsltNum>7</centrPrcrmntArsltNum> <centrPrcrmntArsltAmt>1717897360</centrPrcrmntArsltAmt> <selfPrcrmntArsltNum>0</selfPrcrmntArsltNum> <selfPrcrmntArsltAmt>0</selfPrcrmntArsltAmt> <systmPrcrmntArsltNum>0</systmPrcrmntArsltNum> <systmPrcrmntArsltAmt>0</systmPrcrmntArsltAmt> <mnllbrPrcrmntArsltNum>0</mnllbrPrcrmntArsltNum> <mnllbrPrcrmntArsltAmt>0</mnllbrPrcrmntArsltAmt> </item> <item> <linkSystmNm>전체</linkSystmNm> <dminsttCd>D272001</dminsttCd> <dminsttNm>가축위생방역지원본부 강원도본부</dminsttNm> <arsltSumNum>58</arsltSumNum> <arsltSumAmt>7167940</arsltSumAmt> <centrPrcrmntArsltNum>58</centrPrcrmntArsltNum> <centrPrcrmntArsltAmt>7167940</centrPrcrmntArsltAmt> <selfPrcrmntArsltNum>0</selfPrcrmntArsltNum> <selfPrcrmntArsltAmt>0</selfPrcrmntArsltAmt> <systmPrcrmntArsltNum>0</systmPrcrmntArsltNum> <systmPrcrmntArsltAmt>0</systmPrcrmntArsltAmt> <mnllbrPrcrmntArsltNum>0</mnllbrPrcrmntArsltNum> <mnllbrPrcrmntArsltAmt>0</mnllbrPrcrmntArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>19461</totalCount> </body> </response> |

[조달기업별 계약방법별 실적] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 12 | 오퍼레이션명(국문) | 조달기업별 계약방법별 실적 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntEntrprsAccotCntrctMthdAccotArslt |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위, 업체통합번호, 업체명, 연계시스템코드을 입력하여 업체통합번호, 업체명, 실적합계건수, 실적합계금액, 일반경쟁실적건수, 일반경쟁실적금액, 제한경쟁실적건수, 제한경쟁실적금액, 지명경쟁실적건수, 지명경쟁실적금액, 수의계약실적건수, 수의계약실적금액, 미분류실적건수, 미분류실적금액, 수기등록실적건수, 수기등록실적금액 등 조달기업별 계약방법별 실적을 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플 데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| srchBssYmBgn | 검색기준년월시작 | 6 | 1 | 201601 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 1 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |
| corpUntyNo | 업체통합번호 | 10 | 0 | 2118620691 | 검색하고자하는 업체통합번호가 없으면 기준년월기준 전체조회 |
| corpNm | 업체명 | 100 | 0 | 태양유니스 | 검색하고자하는 업체명 |
| linkSystmCd | 연계시스템코드 | 4 | 0 | 1000 | 검색하고자하는 연계시스템코드
1000:나라장터(중앙조달)
1100:나라장터(자체조달)
2100:강원랜드
2200:농수산물사이버거래소(학교급식)
2300:한국국토정보공사
2400:방위사업청
2500:인천국제공항공사
2600:학교장터
2700:한국가스공사
2800:한국과학기술연구원
2900:한국국제협력단
3000:한국도로공사
3100:한국마사회
3200:한국석유공사
3300:한국수력원자력
3400:한국수자원공사
3500:한국전기연구원
3600:한국전력공사
3700:한국전자통신연구원
3800:한국조폐공사
3900:한국지역난방공사
4000:한국철도공사
4100:한국철도시설공단
4200:한국토지주택공사
4300:한전KDN
4400:디브레인
9000:수기 * 연계시스템코드를 입력하지 않을 경우는 전체시스템으로부터 수집된 실적이 보임 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| linkSystmNm | 연계시스템명 | 100 | 1 | 전체 | 전체로 조회시에는 “전체” 연계시스템코드로 검색시에는 연계시스템명 |
| corpUntyNo | 업체통합번호 | 10 | 0 | 2118620691 | 업체통합번호 |
| corpNm | 업체명 | 100 | 1 | 주식회사 태양유니스 | 업체명 |
| arsltSumNum | 실적합계건수 | 25 | 1 | 4 | 실적합계건수 |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 4463500635 | 실적합계금액 (원,원화) |
| gnrlCmptArsltNum | 일반경쟁실적건수 | 25 | 1 | 1 | 일반경쟁실적건수 |
| gnrlCmptArsltAmt | 일반경쟁실적금액 | 30 | 1 | 32945752 | 일반경쟁실적금액 (원,원화) |
| lmtCmptArsltNum | 제한경쟁실적건수 | 25 | 1 | 2 | 제한경쟁실적건수 |
| lmtCmptArsltAmt | 제한경쟁실적금액 | 30 | 1 | 4409949883 | 제한경쟁실적금액 (원,원화) |
| dsgntCmptArsltNum | 지명경쟁실적건수 | 25 | 1 | 1 | 지명경쟁실적건수 |
| dsgntCmptArsltAmt | 지명경쟁실적금액 | 30 | 1 | 0 | 지명경쟁실적금액 (원,원화) |
| prvtcntrctArsltNum | 수의계약실적건수 | 25 | 1 | 1 | 수의계약실적건수 |
| prvtcntrctArsltAmt | 수의계약실적금액 | 30 | 1 | 20605000 | 수의계약실적금액 (원,원화) |
| unClsfcArsltNum | 미분류실적건수 | 25 | 1 | 0 | 미분류실적건수 |
| unClsfcArsltAmt | 미분류실적금액 | 30 | 1 | 0 | 미분류실적금액 (원,원화) |
| mnllbrRgstArsltNum | 수기등록실적건수 | 25 | 1 | 0 | 수기등록실적건수 전자조달시스템을 이용하지 않은 비전자계약실적건수 |
| mnllbrRgstArsltAmt | 수기등록실적금액 | 30 | 1 | 0 | 수기등록실적금액 전자조달시스템을이용하지 않은 비전자계약실적금액(원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getPrcrmntEntrprsAccotCntrctMthdAccotArslt?srchBssYmBgn=201601&srchBssYmEnd=201601&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <linkSystmNm>전체</linkSystmNm> <corpUntyNo>5061875867</corpUntyNo> <corpNm>광장서점 문구</corpNm> <arsltSumNum>0</arsltSumNum> <arsltSumAmt>0</arsltSumAmt> <gnrlCmptArsltNum>0</gnrlCmptArsltNum> <gnrlCmptArsltAmt>0</gnrlCmptArsltAmt> <lmtCmptArsltNum>0</lmtCmptArsltNum> <lmtCmptArsltAmt>0</lmtCmptArsltAmt> <dsgntCmptArsltNum>0</dsgntCmptArsltNum> <dsgntCmptArsltAmt>0</dsgntCmptArsltAmt> <prvtcntrctArsltNum>0</prvtcntrctArsltNum> <prvtcntrctArsltAmt>0</prvtcntrctArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> <mnllbrRgstArsltNum>0</mnllbrRgstArsltNum> <mnllbrRgstArsltAmt>0</mnllbrRgstArsltAmt> </item> <item> <linkSystmNm>전체</linkSystmNm> <corpUntyNo>5150578005</corpUntyNo> <corpNm>대동하우징</corpNm> <arsltSumNum>0</arsltSumNum> <arsltSumAmt>0</arsltSumAmt> <gnrlCmptArsltNum>0</gnrlCmptArsltNum> <gnrlCmptArsltAmt>0</gnrlCmptArsltAmt> <lmtCmptArsltNum>0</lmtCmptArsltNum> <lmtCmptArsltAmt>0</lmtCmptArsltAmt> <dsgntCmptArsltNum>0</dsgntCmptArsltNum> <dsgntCmptArsltAmt>0</dsgntCmptArsltAmt> <prvtcntrctArsltNum>0</prvtcntrctArsltNum> <prvtcntrctArsltAmt>0</prvtcntrctArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> <mnllbrRgstArsltNum>0</mnllbrRgstArsltNum> <mnllbrRgstArsltAmt>0</mnllbrRgstArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>160644</totalCount> </body> </response> |

[조달기업별 업무대상별 실적] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 13 | 오퍼레이션명(국문) | 조달기업별 업무대상별 실적 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrcrmntEntrprsAccotBsnsObjAccotArslt |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위, 업체통합번호, 업체명, 연계시스템코드을 입력하여 업체통합번호, 업체명, 실적합계건수, 실적합계금액, 물품실적건수, 물품실적금액, 공사실적건수, 공사실적금액, 일반용역실적건수, 일반용역실적금액, 기술용역실적건수, 기술용역실적금액, 미분류실적건수, 미분류실적금액 등 조달기업별 업무대상별 실적을 조회 |  |  |
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
| srchBssYmBgn | 검색기준년월시작 | 6 | 1 | 201601 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 1 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |
| corpUntyNo | 업체통합번호 | 10 | 0 | 1398119223 | 검색하고자하는 업체통합번호가 없으면 기준년월기준 전체조회 |
| corpNm | 업체명 | 100 | 0 | (사)늘푸름 | 검색하고자하는 업체명 |
| linkSystmCd | 연계시스템코드 | 4 | 0 | 1000 | 검색하고자하는 연계시스템코드
1000:나라장터(중앙조달)
1100:나라장터(자체조달)
2100:강원랜드
2200:농수산물사이버거래소(학교급식)
2300:한국국토정보공사
2400:방위사업청
2500:인천국제공항공사
2600:학교장터
2700:한국가스공사
2800:한국과학기술연구원
2900:한국국제협력단
3000:한국도로공사
3100:한국마사회
3200:한국석유공사
3300:한국수력원자력
3400:한국수자원공사
3500:한국전기연구원
3600:한국전력공사
3700:한국전자통신연구원
3800:한국조폐공사
3900:한국지역난방공사
4000:한국철도공사
4100:한국철도시설공단
4200:한국토지주택공사
4300:한전KDN
4400:디브레인
9000:수기 * 연계시스템코드를 입력하지 않을 경우는 전체시스템으로부터 수집된 실적이 보임 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| linkSystmNm | 연계시스템명 | 100 | 1 | 전체 | 전체로 조회시에는 “전체” 연계시스템코드로 검색시에는 연계시스템명 |
| corpUntyNo | 업체통합번호 | 10 | 0 | 2118620691 | 업체통합번호 |
| corpNm | 업체명 | 100 | 1 | 주식회사 태양유니스 | 업체명 |
| arsltSumNum | 실적합계건수 | 25 | 1 | 4 | 합계건수 |
| arsltSumAmt | 실적합계금액 | 30 | 1 | 4463500635 | 합계금액 (원,원화) |
| prdctArsltNum | 물품실적건수 | 25 | 1 | 0 | 물품실적건수 |
| prdctArsltAmt | 물품실적금액 | 30 | 1 | 0 | 물품실적금액 (원,원화) |
| cnstwkArsltNum | 공사실적건수 | 25 | 1 | 2 | 공사실적건수 |
| cnstwkArsltAmt | 공사실적금액 | 30 | 1 | 4409949883 | 공사실적금액 (원,원화) |
| gnrlSrvceArsltNum | 일반용역실적건수 | 25 | 1 | 0 | 일반용역실적건수 |
| gnrlSrvceArsltAmt | 일반용역실적금액 | 30 | 1 | 0 | 일반용역실적금액 (원,원화) |
| techSrvceArsltNum | 기술용역실적건수 | 25 | 1 | 2 | 기술용역실적건수 |
| techSrvceArsltAmt | 기술용역실적금액 | 30 | 1 | 53550752 | 기술용역실적금액 (원,원화) |
| unClsfcArsltNum | 미분류실적건수 | 25 | 1 | 0 | 미분류실적건수 |
| unClsfcArsltAmt | 미분류실적금액 | 30 | 1 | 0 | 미분류실적금액 (원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getPrcrmntEntrprsAccotBsnsObjAccotArslt?srchBssYmBgn=201601&srchBssYmEnd=201601&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <linkSystmNm>전체</linkSystmNm> <corpUntyNo>3012759420</corpUntyNo> <corpNm>가온누리</corpNm> <arsltSumNum>0</arsltSumNum> <arsltSumAmt>0</arsltSumAmt> <prdctArsltNum>0</prdctArsltNum> <prdctArsltAmt>0</prdctArsltAmt> <cnstwkArsltNum>0</cnstwkArsltNum> <cnstwkArsltAmt>0</cnstwkArsltAmt> <gnrlSrvceArsltNum>0</gnrlSrvceArsltNum> <gnrlSrvceArsltAmt>0</gnrlSrvceArsltAmt> <techSrvceArsltNum>0</techSrvceArsltNum> <techSrvceArsltAmt>0</techSrvceArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> <item> <linkSystmNm>전체</linkSystmNm> <corpUntyNo>5061875867</corpUntyNo> <corpNm>광장서점 문구</corpNm> <arsltSumNum>0</arsltSumNum> <arsltSumAmt>0</arsltSumAmt> <prdctArsltNum>0</prdctArsltNum> <prdctArsltAmt>0</prdctArsltAmt> <cnstwkArsltNum>0</cnstwkArsltNum> <cnstwkArsltAmt>0</cnstwkArsltAmt> <gnrlSrvceArsltNum>0</gnrlSrvceArsltNum> <gnrlSrvceArsltAmt>0</gnrlSrvceArsltAmt> <techSrvceArsltNum>0</techSrvceArsltNum> <techSrvceArsltAmt>0</techSrvceArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>160644</totalCount> </body> </response> |

[품목 및 서비스별 실적] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 14 | 오퍼레이션명(국문) | 품목 및 서비스별 실적 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrdctIdntNoServcAccotArslt |
|  | 오퍼레이션 설명 | 검색조건에 기준년월범위, 물품분류번호, 품명을 입력하여 물품분류번호, 품명,실적합계건수, 실적합계금액, 일반경쟁실적건수, 일반경쟁실적금액, 제한경쟁실적건수, 제한경쟁실적금액, 지명경쟁실적건수, 지명경쟁실적금액, 수의계약실적건수, 수의계약실적금액, 미분류실적건수, 미분류실적금액 등 품목 및 서비스별 실적을 조회 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플 데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| srchBssYmBgn | 검색기준년월시작 | 6 | 0 | 201601 | 검색하고자하는 기준년월시작 "YYYYMM" |
| srchBssYmEnd | 검색기준년월종료 | 6 | 0 | 201603 | 검색하고자하는 기준년월종료 "YYYYMM" |
| prdctClsfcNo | 물품분류번호 | 10 | 0 | 72151299 | 검색하고자하는 물품분류번호가 없으면 검색기준년월기준 전체조회 |
| prdctClsfcNm | 품명 | 100 | 0 | 냉난방공조공사 | 검색하고자하는 품명 |
| linkSystmCd | 연계시스템코드 | 4 | 0 | 1000 | 검색하고자하는 연계시스템코드
1000:나라장터(중앙조달)
1100:나라장터(자체조달)
2100:강원랜드
2200:농수산물사이버거래소(학교급식)
2300:한국국토정보공사
2400:방위사업청
2500:인천국제공항공사
2600:학교장터
2700:한국가스공사
2800:한국과학기술연구원
2900:한국국제협력단
3000:한국도로공사
3100:한국마사회
3200:한국석유공사
3300:한국수력원자력
3400:한국수자원공사
3500:한국전기연구원
3600:한국전력공사
3700:한국전자통신연구원
3800:한국조폐공사
3900:한국지역난방공사
4000:한국철도공사
4100:한국철도시설공단
4200:한국토지주택공사
4300:한전KDN
4400:디브레인
9000:수기 * 연계시스템코드를 입력하지 않을 경우는 전체시스템으로부터 수집된 실적이 보임 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| linkSystmNm | 연계시스템명 | 100 | 1 | 전체 | 전체로 조회시에는 “전체” 연계시스템코드로 검색시에는 연계시스템명 |
| prdctClsfcNo | 물품분류번호 | 10 | 0 | 72151299 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNm | 품명 | 100 | 1 | 냉난방공조공사 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| arsltSumNum | 실적합계건수 | 30 | 1 | 18479 | 합계건수 |
| arsltSumAmt | 실적합계금액 | 25 | 1 | 31016532763 | 합계금액 (원,원화) |
| gnrlCmptArsltNum | 일반경쟁실적건수 | 30 | 1 | 18195 | 일반경쟁실적건수 |
| gnrlCmptArsltAmt | 일반경쟁실적금액 | 25 | 1 | 30480561763 | 일반경쟁실적금액 (원,원화) |
| lmtCmptArsltNum | 제한경쟁실적건수 | 30 | 1 | 279 | 제한경쟁실적건수 |
| lmtCmptArsltAmt | 제한경쟁실적금액 | 25 | 1 | 513593000 | 제한경쟁실적금액 (원,원화) |
| dsgntCmptArsltNum | 지명경쟁실적건수 | 30 | 1 | 0 | 지명경쟁실적건수 |
| dsgntCmptArsltAmt | 지명경쟁실적금액 | 25 | 1 | 0 | 지명경쟁실적금액 (원,원화) |
| prvtcntrctArsltNum | 수의계약실적건수 | 30 | 1 | 5 | 수의계약실적건수 |
| prvtcntrctArsltAmt | 수의계약실적금액 | 25 | 1 | 22378000 | 수의계약실적금액 (원,원화) |
| unClsfcArsltNum | 미분류실적건수 | 30 | 1 | 0 | 미분류실적건수 |
| unClsfcArsltAmt | 미분류실적금액 | 25 | 1 | 0 | 미분류실적금액 (원,원화) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/at/PubPrcrmntStatInfoService/getPrdctIdntNoServcAccotArslt?srchBssYmBgn=201601&srchBssYmEnd=201603&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <linkSystmNm>전체</linkSystmNm> <prdctClsfcNo>10101506</prdctClsfcNo> <prdctClsfcNm>말</prdctClsfcNm> <arsltSumNum>1</arsltSumNum> <arsltSumAmt>4950000</arsltSumAmt> <gnrlCmptArsltNum>0</gnrlCmptArsltNum> <gnrlCmptArsltAmt>0</gnrlCmptArsltAmt> <lmtCmptArsltNum>0</lmtCmptArsltNum> <lmtCmptArsltAmt>0</lmtCmptArsltAmt> <dsgntCmptArsltNum>0</dsgntCmptArsltNum> <dsgntCmptArsltAmt>0</dsgntCmptArsltAmt> <prvtcntrctArsltNum>1</prvtcntrctArsltNum> <prvtcntrctArsltAmt>4950000</prvtcntrctArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> <item> <linkSystmNm>전체</linkSystmNm> <prdctClsfcNo>10101511</prdctClsfcNo> <prdctClsfcNm>돼지</prdctClsfcNm> <arsltSumNum>693</arsltSumNum> <arsltSumAmt>5030093102</arsltSumAmt> <gnrlCmptArsltNum>39</gnrlCmptArsltNum> <gnrlCmptArsltAmt>382505030</gnrlCmptArsltAmt> <lmtCmptArsltNum>48</lmtCmptArsltNum> <lmtCmptArsltAmt>428079279</lmtCmptArsltAmt> <dsgntCmptArsltNum>5</dsgntCmptArsltNum> <dsgntCmptArsltAmt>26915180</dsgntCmptArsltAmt> <prvtcntrctArsltNum>601</prvtcntrctArsltNum> <prvtcntrctArsltAmt>4192593613</prvtcntrctArsltAmt> <unClsfcArsltNum>0</unClsfcArsltNum> <unClsfcArsltAmt>0</unClsfcArsltAmt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>4179</totalCount> </body> </response> |

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

