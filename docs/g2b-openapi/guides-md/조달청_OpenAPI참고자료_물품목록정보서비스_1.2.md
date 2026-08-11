# 조달청_OpenAPI참고자료_물품목록정보서비스_1.2

> 출처: 공공데이터포털 참고문서(docx) 자동 변환

조달청 공공데이터 개방
OpenAPI 참고자료
목 차목 차

### 1. 서비스 명세	3


### 1.1 조달청 물품목록정보서비스	3

가. 서비스 개요	3
나. 오퍼레이션 목록	5
개정 이력

| 버 전 | 변경일 | 변경 구분 | 변경사유 |
|---|---|---|---|
| 1.0 | 2025 | 최초 개정 | 최초 개정 |
| 1.1 | 2026.03 | 기능추가 | *공공데이터 개방신청으로 인한 오퍼레이션 추가 - 품목개별속성정보 조회 |
| 1.2 | 2026.05 | 항목추가 | *공공데이터 개방신청으로 인한 지역명, 등록일시, 변경일시 항목 추가 *전남광주통합특별시 지역코드 추가 - 목록정보(일반검색) 소재지 목록 조회 |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |


### 1. 서비스 명세


### 1.1 조달청 물품목록정보서비스

서비스 개요

| 서비스 정보 | 서비스 ID | ThngListInfoService02 |  |  |
|---|---|---|---|---|
|  | 서비스명(국문) | 조달청 물품목록정보서비스 |  |  |
|  | 서비스명(영문) | ThngListInfoService02 |  |  |
|  | 서비스 설명 | 조달청 목록정보시스템(http://www.g2b.go.kr:8100/index.jsp)에서 제공하는 물품분류정보를 제공하는 서비스 조달청 물품분류번호는 대분류(Segment) - 중분류(Family) - 소분류(Class) - 세분류(Commodity) 4단계의 계층구조로 구성되며, 각 단계별 2자리의 코드를 가지고 있는 총 8자리의 번호로 되어 있으며 다음과 같은 용어로 정의됨 ▶ 물품분류번호 : 품명에 대한 분류번호로서 8자리로 표시 ▶ 물품식별번호 : 품목에 대한 고유번호로서 8자리로 표시 ▶ 세부품명번호 : 세분류(Commodity)의 품명보다 세분화가 필요한 품명은 세부품명으로 분류하며, 이에 대응하는 세부품명번호는 물품분류번호 다음에 2자리를 추가하여 10자리 숫자로 이루어짐 ▶ 품명 : 물품분류번호 8자리에 해당하는 물품의 한글이름 ▶ 품목 : 물품식별번호 8자리에 대한 한글명 ▶ 한글품목명 : 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 ▶ 세부품명 : 세부품명번호 10자리에 대한 한글명 |  |  |
| 서비스 보안 | 서비스 인증/권한 | [O] 서비스 Key[ ] 인증서 (GPKI) [] Basic (ID/PW) [ ] 없음 | [ ]WS-Security |  |
|  | 메시지 레벨 암호화 | [  ] 전자서명	[ ] 암호화	[O] 없음 |  |  |
|  | 전송 레벨 암호화 | [  ] SSL			[ O] 없음 |  |  |
| 적용 기술 수준 | 인터페이스 표준 | [  ] SOAP 1.2 (RPC-Encoded, Document Literal, Document Literal Wrapped) [ O ] REST (GET) [ ] RSS 1.0 [ ] RSS 2.0 [ ] Atom 1.0 [ ] 기타 |  |  |
|  | 교환 데이터 표준 | [ O ] XML	[ O ] JSON	[ ] MIME	[ ] MTOM |  |  |
| 서비스 URL | 개발환경 | http://apis.data.go.kr/1230000/ao/ThngListInfoService02 |  |  |
|  | 운영환경 | http://apis.data.go.kr/1230000/ao/ThngListInfoService02 |  |  |
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
| 1 | 조달청 물품목록정보서비스 | getThngGuidanceMapInfo02 | 목록정보(일반검색) 물품안내지도 조회 | N/A |
| 2 |  | getThngPrdnmLocplcAccotListInfoInfoPrdlstSearch02 | 목록정보(일반검색) 품목 목록 조회 | N/A |
| 3 |  | getThngPrdnmLocplcAccotListInfoInfoPrdnmSearch02 | 목록정보(일반검색) 품명 목록 조회 | N/A |
| 4 |  | getThngPrdnmLocplcAccotListInfoInfoLocplcSearch02 | 목록정보(일반검색) 소재지 목록 조회 | N/A |
| 5 |  | getThngListClChangeHistInfo02 | 목록정보(일반검색) 분류변경이력 조회 | N/A |
| 6 |  | getLsfgdNdPrdlstChghstlnfoSttus02 | 목록정보(일반검색) 품목변경이력 조회 | N/A |
| 7 |  | getPrdctClsfcNoUnit2Info02 | 물품분류2단위 내역조회 | N/A |
| 8 |  | getPrdctClsfcNoUnit4Info02 | 물품분류4단위 내역조회 | N/A |
| 9 |  | getPrdctClsfcNoUnit6Info02 | 물품분류6단위 내역조회 | N/A |
| 10 |  | getPrdctClsfcNoUnit8Info02 | 물품분류8단위 내역조회 | N/A |
| 11 |  | getPrdctClsfcNoUnit10Info02 | 물품분류10단위 내역조회 | N/A |
| 12 |  | getPrdctClsfcNoChgHstry02 | 물품분류변경 이력조회 | N/A |
| 13 |  | getPrdctIndvAtrbInfoList02 | 품목개별속성정보 조회 | N/A |

[목록정보(일반검색) 물품안내지도 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 1 | 오퍼레이션명(국문) | 목록정보(일반검색) 물품안내지도 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getThngGuidanceMapInfo02 |
|  | 오퍼레이션 설명 | 상위 물품분류번호로 검색을 하면 목록정보시스템(일반검색)에서 상위 물품분류번호에 속하는 하위 물품분류번호, 품명, 물품분류관련 설명 등이 조회된다. ▶최상위물품분류ID(upPrdctClsfcNo)가 "root"이며 이 조건으로 조회시 2단위 물품분류번호가 조회됩니다.  2단위의 물품분류ID가 4단위 물품분류번호의 상위 물품분류번호가 됩니다. ex) [1010 : 산동물]의 상위물품번호는 [10: 산동식물및동식물성생산품] ▶검색조건이 없을 경우 최상위 상위물품분류ID=root 데이터가 조회 |  |  |
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
| upPrdctClsfcNo | 상위 물품분류번호 | 10 | 0 | 1111 | 상위 물품분류ID Ex) [1111 : 토사석] 입력시 물품분류 토사석의 하위분류인 [111115 : 흙 및 토양] 등에 대한 물품분류정보 제공 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prdctClsfcNo | 물품분류번호 | 10 | 1 | 111115 | 품명에 대한 분류번호 |
| prdctClsfcNoNm | 품명 | 200 | 1 | 흙및토양 | 물품분류번호에 해당하는 물품의 한글이름 |
| prdctClsfcRltnDscrpt | 물품분류관련설명 | 4000 | 1 | 암석의 풍화물로서 지표면이나 지표 근처에 노출된 암석이 산소 ·물 ·열작용을 받아 대 ·소의 입자로 깨진 혼합물과 화학반응 생성물(점토광물 ·탄산칼슘 등), 유기물로 구성되어 있음. | 물품분류 관련 설명 |
| prdctClsfcNoAplYn | 물품분류번호적용여부 | 1 | 1 | Y | 물품분류번호의 적용여부 |
| prdctClsfcNoUseYn | 물품분류번호사용여부 | 1 | 1 | Y | 물품분류번호의 사용여부 |
| prdctClsfcNoDltYn | 물품분류번호삭제여부 | 1 | 1 | N | 물품분류번호의 삭제여부 |
| prdctClsfcDivCd | 물품분류구분코드 | 2 | 1 | sp | 물품분류번호 2,4,6,8 단위일 경우에는 물품분류구분코드가 'sp'이고 10단위 물품분류번호(세부품명번호)일 경우는 물품분류코드가 "sn" |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getThngGuidanceMapInfo02?upPrdctClsfcNo=1111&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prdctClsfcNo>111115</prdctClsfcNo> <prdctClsfcNoNm>흙및토양</prdctClsfcNoNm> <prdctClsfcRltnDscrpt>암석의 풍화물로서 지표면이나 지표 근처에 노출된 암석이 산소 ·물 ·열작용을 받아 대 ·소의 입자로 깨진 혼합물과 화학반응 생성물(점토광물 ·탄산칼슘 등), 유기물로 구성되어 있음.</prdctClsfcRltnDscrpt> <prdctClsfcNoAplYn>Y</prdctClsfcNoAplYn> <prdctClsfcNoUseYn>Y</prdctClsfcNoUseYn> <prdctClsfcNoDltYn>N</prdctClsfcNoDltYn> <prdctClsfcDivCd>sp</prdctClsfcDivCd> </item> <item> <prdctClsfcNo>111116</prdctClsfcNo> <prdctClsfcNoNm>석재</prdctClsfcNoNm> <prdctClsfcRltnDscrpt>건축·토목 및 기타 제작에 사용되는 돌.</prdctClsfcRltnDscrpt> <prdctClsfcNoAplYn>Y</prdctClsfcNoAplYn> <prdctClsfcNoUseYn>Y</prdctClsfcNoUseYn> <prdctClsfcNoDltYn>N</prdctClsfcNoDltYn> <prdctClsfcDivCd>sp</prdctClsfcDivCd> </item> <items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[목록정보(일반검색) 품목 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 2 | 오퍼레이션명(국문) | 목록정보(일반검색) 품목 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getThngPrdnmLocplcAccotListInfoInfoPrdlstSearch02 |
|  | 오퍼레이션 설명 | 목록정보(일반검색) 품목 목록 조회의 검색조건(세부품명번호,물품식별번호,품명 등)을 입력하면 물품이미지(대),물품분류번호,물품식별번호,세부품명번호,품명,영문품명,한글품목명,삭제유무,사용여부,조달업체등록번호,제조업체명 등 조회된다 ▶ 입력변수가 하나라도 있어야 데이터 조회 됨 |  |  |
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
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 3015159901 | 조회하고자 하는 세부품명번호 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 23060722 | 조회하고자 하는 물품식별번호 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| prdctClsfcNoEngNm | 영문품명 | 200 | 0 | Special roofing materials | 조회하고자 하는 영문품명 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| prdctClsfcNoNm | 품명 | 200 | 0 | 특수지붕재 | 조회하고자 하는 품명 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| krnPrdctNm | 한글품목명 | 200 | 0 | 특수지붕재 | 조회하고자 하는 한글품목명 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 201607100000 | 검색하고자 하는 조회시작일시로 조회일시는 물품식별번호 등록일시. 조회종료일시와 같이 입력해야 결과값이 추출됨 "YYYYMMDDHHMM" *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| inqryEndDt | 조회종료일시 | 12 | 0 | 201607202359 | 검색하고자 하는 조회종료일시로 조회일시는 물품식별번호 등록일시. 조회시작일시와 같이 입력해야 결과값이 추출됨 "YYYYMMDDHHMM" *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| chgPrdBgnDt | 변경기간시작일시 | 12 | 0 | 201607100000 | 변경일시 범위 (시작일시) 'YYYYMMDDHHMM' |
| chgPrdEndDt | 변경기간종료일시 | 12 | 0 | 201607202359 | 변경일시 범위 (종료일시) 'YYYYMMDDHHMM' |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| sno | 순번 | 5 | 1 | 1 | 순번 |
| prdctImgLrge | 물품이미지(대) | 256 | 0 | https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2016/07/12/B201607120958738-554.jpg | 물품이미지(대) |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 26111607 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 1 | 23085997 | 품목에 대한 고유번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 1 | 2611160701 | 품명에 대한 분류번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 태양광발전장치 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| prdctClsfcNoEngNm | 영문품명 | 200 | 0 | Solar generators | 한글품명에 대한 영문이름 |
| prdlstDiv | 품목구분 | 4 | 0 | 계약 | 품목에 대한 구분 |
| cmpntYn | 부품여부 | 1 | 0 | N | 부품여부 |
| krnPrdctNm | 한글품목명 | 200 | 0 | 태양광발전장치, 대은, DE-BIPV-5, 5kW, 계통연계형 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본적인 규격사항 등이 기록된 품목의 명칭 |
| dltYn | 삭제유무 | 1 | 0 | N | 삭제유무 |
| useYn | 사용여부 | 1 | 0 | Y | 사용여부 |
| prcrmntCorpRgstNo | 조달업체등록번호 | 8 | 0 | 12345678 | 조달업체등록번호 |
| mnfctCorpNm | 제조업체명 | 4000 | 0 | 태양광발전장치,주식회사 대은,DE-BIPV-5 | 물품의 제조업체명 |
| rgstDt | 등록일시 | 19 | 1 | 2017-06-25 17:35 | 물품식별번호에 대한 정보 등록일시  'YYYY-MM-DD HH24:MI' |
| prodctCertList | 제품인증목록 | 4000 | 0 | GR,환경표지제품 | 해당 제품이 취득한 인증목록 |
| chgDt | 변경일시 | 19 | 0 | 2017-06-25 17:35 | 물품식별번호에 대한 정보 변경일시  'YYYY-MM-DD HH24:MI' |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getThngPrdnmLocplcAccotListInfoInfoPrdlstSearch02?dtilPrdctClsfcNo=2611160701&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| - <response> - <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> - <body> - <items> - <item> <sno>1</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/P/PV-C103S-L.jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20484306</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 헥스파워시스템, PV-C103S22, 단상, 3kVA, 220V/53W</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,헥스파워시스템(주),PV-C103S22</mnfctCorpNm> <rgstDt>2003-11-12 15:05:10.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> - <item> <sno>2</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2004/12/LST-3.jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20562712</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 엘시스텍, LST-3, 3kW</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,(주)엘시스텍,LST-3</mnfctCorpNm> <rgstDt>2004-12-09 16:21:03.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> - <item> <sno>3</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2004/12/LST-5.jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20563118</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 엘시스텍, LST-5, 5kW</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,(주)엘시스텍,LST-5</mnfctCorpNm> <rgstDt>2004-12-10 18:12:12.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> - <item> <sno>4</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2006/06/M741898.jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20773380</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 우선제어, WS-PCS-1.5K, 1.5kW</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,(주)우선제어,WS-PCS-1.5K</mnfctCorpNm> <rgstDt>2006-06-21 15:37:52.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> - <item> <sno>5</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2006/06/M742675.jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20773381</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 우선제어, WS-PCS-C1, 150W, 위치추적장치</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,(주)우선제어,WS-PCS-C1</mnfctCorpNm> <rgstDt>2006-06-21 15:38:06.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> - <item> <sno>6</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2006/12/EDSG-25K210Wp.jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20877975</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 에디슨코리아, EDSG-25K210Wp, 25kW</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,(주)에디슨코리아,EDSG-25K210Wp</mnfctCorpNm> <rgstDt>2006-12-09 14:50:12.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> - <item> <sno>7</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2006/12/EDSG-20K210Wp.jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20877976</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 에디슨코리아, EDSG-20K210Wp, 20kW</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,(주)에디슨코리아,EDSG-20K210Wp</mnfctCorpNm> <rgstDt>2006-12-09 14:50:16.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> - <item> <sno>8</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2006/12/EDSG-15K210Wp.jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20877977</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 에디슨코리아, EDSG-15K210Wp, 15kW</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,(주)에디슨코리아,EDSG-15K210Wp</mnfctCorpNm> <rgstDt>2006-12-09 14:50:20.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> - <item> <sno>9</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2006/12/BC1627(1).jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20877978</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 에디슨코리아, EDSG-10K210Wp, 10kW</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,(주)에디슨코리아,EDSG-10K210Wp</mnfctCorpNm> <rgstDt>2006-12-09 14:50:23.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> - <item> <sno>10</sno> <prdctImgLrge>https://img.g2b.go.kr:7073/Resource/CataAttach/XezCatalog/XZMOK/item_image/2006/11/50kW(400x400).jpg</prdctImgLrge> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>20877981</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 유니슨, UNS-PVS-50K, 50kW</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <prcrmntCorpRgstNo/> <mnfctCorpNm>태양광발전장치,유니슨(주),UNS-PVS-50K</mnfctCorpNm> <rgstDt>2006-12-09 14:51:59.0</rgstDt> <chgDt>2018-09-11 16:00:26.0</chgDt> <prodctCertList/> <prdlstDiv>계약</prdlstDiv> <cmpntYn>N</cmpntYn> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>20038</totalCount> </body> </response> |

[목록정보(일반검색) 품명 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 3 | 오퍼레이션명(국문) | 목록정보(일반검색) 품명 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getThngPrdnmLocplcAccotListInfoInfoPrdnmSearch02 |
|  | 오퍼레이션 설명 | 목록정보(일반검색) 품명 목록 조회의 검색조건(물품분류번호, 세부품명번호,영문품명, 품명)을 입력하면 물품분류번호,품명,영문품명,세부품명번호 등 조회된다 |  |  |
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
| prdctClsfcNo | 물품분류번호 | 10 | 0 | 12142106 | 조회하고자 하는 물품분류번호 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| dtilPrdctClsfcNo | 세부품명번호 | 8 | 0 | 1214210601 | 조회하고자 하는 세부품명번호 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| prdctClsfcNoEngNm | 영문품명 | 200 | 0 | Inert | 조회하고자 하는 영문품명 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| prdctClsfcNoNm | 품명 | 200 | 0 | 비활성기체 | 조회하고자 하는 품명 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| sno | 순번 | 5 | 1 | 1 | 순번 |
| prdctClsfcNo | 물품분류번호 | 10 | 1 | 12142106 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 1 | 비활성기체혼합물 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| prdctClsfcNoEngNm | 영문품명 | 200 | 1 | Inert gas mixtures | 한글품명에 대한 영문이름 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 1 | 1010160201 | 품명에 대한 분류번호로서 10자리로 표시 |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 비활기성체혼합물 | 세부품명번호 10자리에 대한 한글명 |
| dtilPrdctClsfcNoEngNm | 세부영문품명 | 200 | 0 | Helium mixture gas | 세부품명번호 10자리에 대한 영문명 |
| dtilPrdctClsfcNoNum | 세부품명번호건수 | 5 | 0 | 2 | 세부품명번호 10자리에 대한 매핑건수 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getThngPrdnmLocplcAccotListInfoInfoPrdnmSearch02?prdctClsfcNo=12142106&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <sno>1</sno> <prdctClsfcNo>12142106</prdctClsfcNo> <prdctClsfcNoNm>비활성기체혼합물</prdctClsfcNoNm> <prdctClsfcNoEngNm>Inert gas mixtures</prdctClsfcNoEngNm> <dtilPrdctClsfcNo>1214210601</dtilPrdctClsfcNo> <dtilPrdctClsfcNoNm>헬륨혼합가스</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNoEngNm>Helium mixture gas</dtilPrdctClsfcNoEngNm> <dtilPrdctClsfcNoNum>2</dtilPrdctClsfcNoNum> </item> <item> <sno>2</sno> <prdctClsfcNo>12142106</prdctClsfcNo> <prdctClsfcNoNm>비활성기체혼합물</prdctClsfcNoNm> <prdctClsfcNoEngNm>Inert gas mixtures</prdctClsfcNoEngNm> <dtilPrdctClsfcNo>1214210602</dtilPrdctClsfcNo> <dtilPrdctClsfcNoNm>아르곤혼합가스</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNoEngNm>Argon mixture gas</dtilPrdctClsfcNoEngNm> <dtilPrdctClsfcNoNum>2</dtilPrdctClsfcNoNum> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[목록정보(일반검색) 소재지 목록 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 4 | 오퍼레이션명(국문) | 목록정보(일반검색) 소재지 목록 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getThngPrdnmLocplcAccotListInfoInfoLocplcSearch02 |
|  | 오퍼레이션 설명 | 목록정보(일반검색) 소재지 목록조회의 검색조건(세부품명번호, 물품식별번호,제조업체명, 제조업체의 지역코드 등)을 입력하면 물품분류번호,품명,영문품명,세부품명번호,지역코드 등 조회된다 |  |  |
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
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 2611160701 | 조회하고자 하는 세부품명번호 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 23062106 | 조회하고자 하는 물품식별번호 (G2B물품번호) *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| mnfctCorpNm | 제조업체명 | 100 | 0 | 주식회사 이투지 | 조회하고자 하는 물품의 제조업체명 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| krnPrdctNm | 한글품목명 | 200 | 0 | 태양광발전장치 | 조회하고자 하는 한글품목명 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |
| rgnCd | 지역코드 | 2 | 0 | 50 | 조회하고자 하는 제조업체의 지역코드 11 : 서울특별시 26 : 부산광역시 27 : 대구광역시 28 : 인천광역시 29 : 광주광역시 30 : 대전광역시 31 : 울산광역시 36 : 세종특별자치시 41 : 경기도 42 : 강원도 43 : 충청북도 44 : 충청남도 45 : 전라북도 46 : 전라남도 47 : 경상북도 48 : 경상남도 50 : 제주도 51 : 강원특별자치도 52 : 전북특별자치도 12 : 전남광주통합특별시 99 : 기타 *입력값이 없을 경우 현재일로 부터 하루기준 조회. |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| sno | 순번 | 5 | 1 | 1 | 순번 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 26111607 | 품명에 대한 고유번호로서 8자리로 표시 |
| prdctIdntNo | 물품식별번호 | 8 | 1 | 23062106 | 품목에 대한 고유번호로서 8자리로 표시 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 1 | 2611160701 | 품명에 대한 고유번호로서 10자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 태양광발전장치 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| prdctClsfcNoEngNm | 영문품명 | 100 | 0 | Solar generators | 한글품명에 대한 영문이름 |
| krnPrdctNm | 한글품목명 | 200 | 0 | 태양광발전장치, 대은, DE-BIPV-5, 5kW, 계통연계형 | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본전인 규격사항등이 기록된 품목의 명칭 |
| dltYn | 삭제여부 | 1 | 0 | N | 삭제유무 |
| useYn | 사용여부 | 1 | 0 | Y | 사용여부 |
| mnfctCorpNm | 제조업체명 | 100 | 0 | 현대 | 물품의 제조업체명 |
| rgnCd | 지역코드 | 10 | 0 | 50110 | 제조업체의 지역코드 |
| rgnNm | 지역명 | 200 | 0 | 제주특별자치도 제주시 | 제조업체의 지역명 |
| rgstDt | 등록일시 | 19 | 1 | 2017-06-25 17:35 | 물품식별번호에 대한 정보 등록일시  'YYYY-MM-DD HH24:MI' |
| chgDt | 변경일시 | 10 | 1 | 2016-05-26 17:35 | 물품식별번호에 대한 정보 변경일자  'YYYY-MM-DD HH24:MI' |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getThngPrdnmLocplcAccotListInfoInfoLocplcSearch02?dtilPrdctClsfcNo=2611160701&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <sno>1</sno> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>23090312</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 유니테스트, UNI-PV-TL-50, 50.4kW, 고정형, 계통연계형</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <mnfctCorpNm /> <rgnCd /> </item> <item> <sno>2</sno> <prdctClsfcNo>26111607</prdctClsfcNo> <prdctIdntNo>23089627</prdctIdntNo> <dtilPrdctClsfcNo>2611160701</dtilPrdctClsfcNo> <prdctClsfcNoNm>태양광발전장치</prdctClsfcNoNm> <prdctClsfcNoEngNm>Solar generators</prdctClsfcNoEngNm> <krnPrdctNm>태양광발전장치, 정우엔지니어링, JWE-100KP-HI, 100.8kW, 고정형, 계통연계형</krnPrdctNm> <dltYn>N</dltYn> <useYn>Y</useYn> <mnfctCorpNm /> <rgnCd /> </item></items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[목록정보(일반검색) 분류변경이력 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 5 | 오퍼레이션명(국문) | 목록정보(일반검색) 분류변경이력 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getThngListClChangeHistInfo02 |
|  | 오퍼레이션 설명 | 목록정보(일반검색) 분류변경이력 조회의 검색조건(현재 물품분류번호)을 입력하면 변경전후물품분류번호,변경전후품명 등 물품분류변경 이력이 조회된다 |  |  |
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
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분 입력
1: 물품분류번호 기준검색 2: 변경 일시 기준 검색 |
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 811015 | 현재 물품분류번호 /변경후물품분류번호로 확인 가능
조회구분 1 일경우 필수 |
| inqryBgnDt | 조회기준시작일시 | 12 | 0 | 200101010000 | 검색하고자하는 검색기준시작일시 입력
"YYYYMMDDHHMM"
조회구분 2 일경우 필수 (조회 범위는 최대 12개월로 제한)) |
| inqryEndDt | 조회기준종료일시 | 12 | 0 | 200112312359 | 검색하고자하는 검색기준종료일시 입력
"YYYYMMDDHHMM"
조회구분 2일경우 필수 (조회 범위는 최대 12개월로 제한) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| sno | 순번 | 5 | 1 | 1 | 순번 |
| prdctClsfcDiv | 물품분류구분 | 10 | 1 | 물품분류 | "물품분류" |
| bfchgPrdctClsfcNo | 변경전물품분류번호 | 10 | 0 | 70171799 | 변경전 물품분류번호 |
| bfchgPrdctClsfcNoNm | 번경전품명 | 200 | 0 | 관개관련용역 | 번경전 품명 |
| afchgPrdctClsfcNo | 변경후물품분류번호 | 10 | 0 | 81101510 | 현재 물품분류번호 |
| afchgPrdctClsfcNm | 변경후품명 | 200 | 0 | 토목설계용역 | 현재 품명 |
| chgDivNm | 변경구분명 | 50 | 0 | 분류이동 | N:품명변경, C:분류이동 |
| prdctChgreqNo | 물품변경요청번호 | 13 | 0 | C08004 | 물품변경 요청번호 |
| prdctClsfcDivCd | 물품분류구분코드 | 2 | 0 | sp | 물품분류구분코드 sp:8단위이하품목분류, sn:10단위품목분류 |
| chgDivCd | 변경구분코드 | 4 | 0 | C | 변경구분코드 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getThngListClChangeHistInfo02? inqryDiv=1&prdctClsfcNo=811015&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <sno>1</sno> <prdctClsfcDiv>물품분류</prdctClsfcDiv> <bfchgPrdctClsfcNo>72151599</bfchgPrdctClsfcNo> <bfchgPrdctClsfcNoNm>일반전기소방시설공사</bfchgPrdctClsfcNoNm> <afchgPrdctClsfcNo>72101509</afchgPrdctClsfcNo> <afchgPrdctClsfcNm>소방시설공사</afchgPrdctClsfcNm> <chgDivNm>분류이동</chgDivNm> <prdctChgreqNo>C07985</prdctChgreqNo> <prdctClsfcDivCd>sp</prdctClsfcDivCd> <chgDivCd>C</chgDivCd> </item> <item> <sno>2</sno> <prdctClsfcDiv>물품분류</prdctClsfcDiv> <bfchgPrdctClsfcNo>72151198</bfchgPrdctClsfcNo> <bfchgPrdctClsfcNoNm>일반기계소방시설공사</bfchgPrdctClsfcNoNm> <afchgPrdctClsfcNo>72101509</afchgPrdctClsfcNo> <afchgPrdctClsfcNm>소방시설공사</afchgPrdctClsfcNm> <chgDivNm>분류이동</chgDivNm> <prdctChgreqNo>C07984</prdctChgreqNo> <prdctClsfcDivCd>sp</prdctClsfcDivCd> <chgDivCd>C</chgDivCd> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[목록정보(일반검색) 품목변경이력 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 6 | 오퍼레이션명(국문) | 목록정보(일반검색) 품목변경이력 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getLsfgdNdPrdlstChghstlnfoSttus02 |
|  | 오퍼레이션 설명 | 목록정보(일반검색) 품목변경이력 조회의 검색조건(물품식별번호)을 입력하면 변경전후물품분류번호,변경전후품명,변경전물품속성값 등 품목변경이력이 조회된다 |  |  |
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
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자 하는 조회구분 입력
1: 물품식별번호 기준검색 2: 변경 일시 기준 검색 |
| prdctIdntNo | 물품식별번호 | 8 | 0 | 10001136 | 조회하고자 조회구분 1일경우 필수 |
| inqryBgnDt | 조회기준시작일시 | 12 | 0 | 201105200000 | 검색하고자 하는 검색기준시작일시 입력
"YYYYMMDDHHMM"
조회구분 2일경우 필수 (조회 범위는 최대 1개월로 제한) |
| inqryEndDt | 조회기준종료일시 | 12 | 0 | 201105312359 | 검색하고자 하는 검색기준종료일시 입력
"YYYYMMDDHHMM"
조회구분 2일경우 필수 (조회 범위는 최대 1개월로 제한) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상 | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| sno | 순번 | 5 | 1 | 1 | 순번 |
| prdctIdntNo | 물품식별번호 | 8 | 1 | 20557088 | 물품식별번호(품목에 대한 고유번호로서 8자리로 표시) |
| bfchgPrdctClsfcNo | 변경전물품분류번호 | 11 | 0 | 24101501 | 변경전 물품분류번호 또는 변경전 세부품명번호(변경) |
| afchgPrdctClsfcNo | 변경후물품분류번호 | 11 | 0 | 56101595 | 변경후 물품분류번호 또는 변경후 세부품명번호(변경) |
| bfchgPrdctAttrbtVal | 변경전물품속성값 | 2000 | 0 | 카트, Kartell, IT/K4460, 1000×540×690mm, 4륜, 접이식테이블 | 변경전 물품 속성 값 |
| afchgPrdctAttrbtVal | 변경후물품속성값 | 2000 | 0 | 기타미분류가구, Kartell, IT/K4460, 1000×540×690mm | 변경후 물품 속성 값 |
| prdctInfoChgDate | 물품정보변경일자 | 10 | 1 | 2016-07-12 | 물품분류 정보의 변경일자  'YYYY-MM-DD' |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getLsfgdNdPrdlstChghstlnfoSttus02?inqryDiv=1&prdctIdntNo=20557088&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <sno>1</sno> <prdctIdntNo>20557088</prdctIdntNo> <bfchgPrdctClsfcNo>24101501</bfchgPrdctClsfcNo> <afchgPrdctClsfcNo>56101595</afchgPrdctClsfcNo> <bfchgPrdctAttrbtVal>카트, Kartell, IT/K4460, 1000×540×690mm, 4륜, 접이식테이블</bfchgPrdctAttrbtVal> <afchgPrdctAttrbtVal>기타미분류가구, Kartell, IT/K4460, 1000×540×690mm</afchgPrdctAttrbtVal> <prdctInfoChgDate>20160712</prdctInfoChgDate> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[물품분류2단위 내역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 8 | 오퍼레이션명(국문) | 물품분류2단위 내역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrdctClsfcNoUnit2Info02 |
|  | 오퍼레이션 설명 | 물품분류2단위 내역조회의 검색조건(물품분류번호시작번호, 물품분류번호종료번호,품명,영문품명)을 입력하면 물품분류번호(2단위),품명(2단위),영문품명(2단위),품명해설 등이 조회된다 ▶ 조달청 물품분류번호는 대분류(Segment) - 중분류(Family) - 소분류(Class) - 세분류(Commodity) 4단계의 계층구조로 구성되며, 각 단계별 2자리의 코드를 가지고 있는 총 8자리의 번호로 되어 있으며 물품분류2단위는 대분류에 해당됨 ▶ 조건 입력을 하지 않을 경우 물품분류2단위 전체 조회. |  |  |
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
| prdctClsfcNoBgnNo | 물품분류번호시작번호 | 2 | 0 | 10 | 조회하고자 하는 물품분류2단위 시작번호 |
| prdctClsfcNoEndNo | 물품분류번호종료번호 | 2 | 0 | 11 | 조회하고자 하는 물품분류2단위 종료번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 산동식물및동식물성생산품 | 조회하고자 하는 물품분류명으로 Like 검색
예)조약돌 |
| prdctClsfcNoEngNm | 영문품명 | 200 | 0 | Live Plant and Animal Material and Accessories and Supplies | 조회하고자 하는 영문품명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prdctClsfcNo | 물품분류번호(2단위) | 2 | 1 | 10 | 2단위 물품 분류번호 |
| prdctClsfcNoNm | 품명(2단위) | 200 | 1 | 산동식물및동식물성생산품 | 물품에 대한 2단위 물품분류명 |
| prdctClsfcNoEngNm | 영문품명(2단위) | 100 | 1 | Live Plant and Animal Material and Accessories and Supplies | 물품에 대한 2단위 물품분류 영문명 |
| prdctClsfcNoNmDscrpt | 품명해설 | 4000 | 0 | 생명이 있는 야생과 재배성의 식물과 동물, 생명이 있는 식물들과 그들의 씨앗, 생명이 있는 식물과 동물의 보호, 수용, 사육에 필요한 재료와 설비. | 물품 분류에 대한 상세설명 |
| useYn | 사용여부 | 1 | 0 | Y | 물품분류번호의 사용여부 |
| chgDate | 변경일자 | 10 | 0 | 2002-08-12 | 물품분류번호에 대한 정보 변경일자  'YYYY-MM-DD' |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getPrdctClsfcNoUnit2Info02?prdctClsfcNoBgnNo=10&prdctClsfcNoEndNo=11&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prdctClsfcNo>10</prdctClsfcNo> <prdctClsfcNoNm>산동식물및동식물성생산품</prdctClsfcNoNm> <prdctClsfcNoEngNm>Live Plant and Animal Material and Accessories and Supplies</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>생명이 있는 야생과 재배성의 식물과 동물, 생명이 있는 식물들과 그들의 씨앗, 생명이 있는 식물과 동물의 보호, 수용, 사육에 필요한 재료와 설비.</prdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2002-09-06</chgDate> </item> <item> <prdctClsfcNo>11</prdctClsfcNo> <prdctClsfcNoNm>광물,직물및비식용동식물자원</prdctClsfcNoNm> <prdctClsfcNoEngNm>Mineral and Textile and Inedible Plant and Animal Materials</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>암석, 토양, 광석, 정제 금속 또는 귀금속, 비식용 식물, 삼림, 동물제품, 천연섬유, 인조섬유, 재봉사, 직물, 동물가죽, 찌거기, 배설물과 같은 비 가공물질.</prdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2002-09-06</chgDate> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[물품분류4단위 내역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 9 | 오퍼레이션명(국문) | 물품분류4단위 내역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrdctClsfcNoUnit4Info02 |
|  | 오퍼레이션 설명 | 물품분류4단위 내역조회의 검색조건(물품분류번호시작번호, 물품분류번호종료번호, 품명,영문품명 )을 입력하면 물품분류번호(4단위),품명(4단위),영문품명(4단위),품명해설 등이 조회된다 ▶ 조달청 물품분류번호는 대분류(Segment) - 중분류(Family) - 소분류(Class) - 세분류(Commodity) 4단계의 계층구조로 구성되며, 각 단계별 2자리의 코드를 가지고 있는 총 8자리의 번호로 되어 있으며 물품분류4단위는 중분류에 해당됨 ▶ 조건 입력을 하지 않을 경우 물품분류4단위 전체 조회. |  |  |
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
| prdctClsfcNoBgnNo | 물품분류번호시작번호 | 4 | 0 | 1010 | 조회하고자 하는 물품분류4단위 시작번호 |
| prdctClsfcNoEndNo | 물품분류번호종료번호 | 4 | 0 | 1013 | 조회하고자 하는 물품분류4단위 종료번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 동물수용소및보관소 | 조회하고자 하는 물품분류명으로 Like 검색
예)조약돌 |
| prdctClsfcNoEngNm | 영문품명 | 200 | 0 | Animal containment and habitats | 조회하고자 하는 영문품명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prdctClsfcNo | 물품분류번호(4단위) | 4 | 1 | 1013 | 4단위 물품 분류번호 |
| prdctClsfcNoNm | 품명(4단위) | 200 | 1 | 동물수용소및보관소 | 물품에 대한 4단위 물품분류명 |
| prdctClsfcNoEngNm | 영문품명(4단위) | 100 | 1 | Animal containment and habitats | 물품에 대한 4단위 물품분류 영문명 |
| prdctClsfcNoNmDscrpt | 품명해설 | 4000 | 0 | 생명이 있는 야생 및 사육하는 동물의 보호, 수용, 사육에 필요한 재료와 설비. | 물품 분류에 대한 상세설명 |
| useYn | 사용여부 | 1 | 0 | Y | 물품분류번호의 사용여부 |
| chgDate | 변경일자 | 10 | 0 | 2002-09-06 | 물품분류번호에 대한 정보 변경일자 'YYYY-MM-DD' |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getPrdctClsfcNoUnit4Info02?prdctClsfcNoBgnNo=1010&prdctClsfcNoEndNo=1015&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prdctClsfcNo>1010</prdctClsfcNo> <prdctClsfcNoNm>산동물</prdctClsfcNoNm> <prdctClsfcNoEngNm>Live animals</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>생명이 있는 야생 및 사육하는 동물.</prdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2002-09-06</chgDate> </item> <item> <prdctClsfcNo>1011</prdctClsfcNo> <prdctClsfcNoNm>애완동물용품</prdctClsfcNoNm> <prdctClsfcNoEngNm>Domestic pet products</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>애완동물을 양육하는데 필요한 용품 및 용구.</prdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2002-09-06</chgDate> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[물품분류6단위 내역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 10 | 오퍼레이션명(국문) | 물품분류6단위 내역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrdctClsfcNoUnit6Info02 |
|  | 오퍼레이션 설명 | 물품분류6단위 내역조회의 검색조건(물품분류번호시작번호,물품분류번호종료번호,품명,영문품명)을 입력하면 물품분류번호(6단위),품명(6단위),영문품명(6단위),품명해설 등이 조회된다 ▶ 조달청 물품분류번호는 대분류(Segment) - 중분류(Family) - 소분류(Class) - 세분류(Commodity) 4단계의 계층구조로 구성되며, 각 단계별 2자리의 코드를 가지고 있는 총 8자리의 번호로 되어 있으며 물품분류6단위는 소분류에 해당됨 ▶ 조건 입력을 하지 않을 경우 물품분류6단위 전체 조회 |  |  |
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
| prdctClsfcNoBgnNo | 물품분류번호시작번호 | 6 | 0 | 101316 | 조회하고자 하는 물품분류6단위 시작번호 |
| prdctClsfcNoEndNo | 물품분류번호종료번호 | 6 | 0 | 101316 | 조회하고자 하는 물품분류6단위 종료번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 동물장 | 조회하고자 하는 물품분류명으로 Like 검색
예)조약돌 |
| prdctClsfcNoEngNm | 영문품명 | 200 | 0 | Animal containment | 조회하고자 하는 영문품명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prdctClsfcNo | 물품분류번호(6단위) | 6 | 1 | 101316 | 6단위 물품 분류번호 |
| prdctClsfcNoNm | 품명(6단위) | 200 | 1 | 동물장 | 물품에 대한 6단위 물품분류명 |
| prdctClsfcNoEngNm | 영문품명(6단위) | 100 | 1 | Animal containment | 물품에 대한 6단위 물품분류 영문명 |
| prdctClsfcNoNmDscrpt | 품명해설 | 4000 | 0 | 동물을 사육하는 건축물이며, 동물관리의 능률화와 자연의 기상으로부터 동물을 보호하는 두 가지 기능을 가짐. 따라서 동물장의 요건은 위치 ·방향 ·통풍 ·채광 ·환기 ·보온이 양호하고 사료주기 ·청소 ·위생관리에 적절한 설비가 갖추어져야 함. | 물품 분류에 대한 상세설명 |
| useYn | 사용여부 | 1 | 0 | Y | 물품분류번호의 사용여부 |
| chgDate | 변경일자 | 10 | 0 | 2002-09-06 | 물품분류번호에 대한 정보 변경일자  'YYYY-MM-DD' |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getPrdctClsfcNoUnit6Info02?prdctClsfcNoBgnNo=101015&prdctClsfcNoEndNo=101016&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prdctClsfcNo>101015</prdctClsfcNo> <prdctClsfcNoNm>가축류</prdctClsfcNoNm> <prdctClsfcNoEngNm>Livestock</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>인류가 야생동물을 순치·개량한 것으로 사람의 보호 밑에서 자유로이 번식하는 동물.</prdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2004-12-06</chgDate> </item> <item> <prdctClsfcNo>101016</prdctClsfcNo> <prdctClsfcNoNm>조류및가금류</prdctClsfcNoNm> <prdctClsfcNoEngNm>Birds and fowl</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>척추동물의 한 강으로 앞다리는 날개로 변형되어 비상생활에 적응되었고, 입은 부리로 되어 손을 대신하는 구실을 하며, 온몸이 깃털로 덮인 온혈 동물을 말함.</prdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2004-12-06</chgDate> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

[물품분류8단위 내역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 11 | 오퍼레이션명(국문) | 물품분류8단위 내역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrdctClsfcNoUnit8Info02 |
|  | 오퍼레이션 설명 | 물품분류8단위 내역조회의 검색조건(물품분류번호시작번호, 물품분류번호종료번호, 품명,영문품명)을 입력하면 물품분류번호,품명영문품명,품명해설 등이 조회된다 ▶ 조달청 물품분류번호는 대분류(Segment) - 중분류(Family) - 소분류(Class) - 세분류(Commodity) 4단계의 계층구조로 구성되며, 각 단계별 2자리의 코드를 가지고 있는 총 8자리의 번호로 되어 있으며 물품분류8단위는 세분류에 해당됨 ▶ 조건 입력을 하지 않을 경우 물품분류번호 전체 조회 |  |  |
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
| prdctClsfcNoBgnNo | 물품분류번호시작번호 | 8 | 0 | 10131601 | 조회하고자 하는 물품분류8단위 시작번호 |
| prdctClsfcNoEndNo | 물품분류번호종료번호 | 8 | 0 | 10131702 | 조회하고자 하는 물품분류8단위 종료번호 |
| prdctClsfcNoNm | 품명 | 200 | 0 | 애완동물사육장 | 조회하고자 하는 물품분류명으로 Like 검색
예)조약돌 |
| prdctClsfcNoEngNm | 영문품명 | 200 | 0 | Cages | 조회하고자 하는 영문품명 |

응답 메시지 명세
※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| prdctClsfcNo | 물품분류번호 | 8 | 1 | 10131601 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 1 | 애완동물사육장 | 물품분류번호 8자리에 해당하는 물품의 한글이름 |
| prdctClsfcNoEngNm | 영문품명 | 100 | 1 | Cages | 한글품명에 대한 영문이름 |
| prdctClsfcNoNmDscrpt | 품명해설 | 4000 | 0 | 애완용 동물을 운반하거나 격리수용 시키기 위해 사용하는 장치로 투명한 플라스틱, 철망등으로 제작되어 있음. | 물품 분류에 대한 상세설명 |
| useYn | 사용여부 | 1 | 0 | Y | 물품분류번호의 사용여부 |
| chgDate | 변경일자 | 10 | 0 | 2002-09-06 | 물품분류번호에 대한 정보 변경일자  'YYYY-MM-DD' |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getPrdctClsfcNoUnit8Info02?prdctClsfcNoBgnNo=10131601&prdctClsfcNoEndNo=10131702&prdctClsfcNoNm=&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <prdctClsfcNo>10131601</prdctClsfcNo> <prdctClsfcNoNm>애완동물사육장</prdctClsfcNoNm> <prdctClsfcNoEngNm>Cages</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>애완용 동물을 운반하거나 격리수용 시키기 위해 사용하는 장치로 투명한 플라스틱, 철망등으로 제작되어 있음.</prdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2004-12-06</chgDate> </item> <item> <prdctClsfcNo>10131701</prdctClsfcNo> <prdctClsfcNoNm>육생생물용사육기</prdctClsfcNoNm> <prdctClsfcNoEngNm>Terrariums</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>육생의 작은 동물을 사육하는 곳으로 곤충사육장, 수족관 등이 있음.</prdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2004-12-06</chgDate> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>3</totalCount> </body> </response> |

[물품목록 10단위 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 12 | 오퍼레이션명(국문) | 물품분류10단위 내역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrdctClsfcNoUnit10Info02 |
|  | 오퍼레이션 설명 | 물품분류10단위 내역조회의 검색조건(물품분류번호시작번호, 물품분류번호종료번호, 품명, 영문품명)을 입력하면 세부품명번호,세부품명,세부영문품명,세부품명해설,사용여부 등이 조회된다 ▶ 물품분류번호10단위는 세부품명번호로 세분류(Commodity)의 품명보다 세분화가 필요한 품명을 세부품명으로 분류하며, 이에 대응하는 세부품명번호는 물품분류번호 다음에 2자리를 추가하여 10자리 숫자로 이루어짐 ▶ 조건 입력을 하지 않을 경우 물품분류10단위(세부품명분류) 전체 조회 |  |  |
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
| dtilPrdctClsfcNoBgnNo | 세부품명번호시작번호 | 10 | 0 | 1013160101 | 조회하고자 하는 물품분류10단위 시작번호 |
| dtilPrdctClsfcNoEndNo | 세부품명번호종료번호 | 10 | 0 | 1013160102 | 조회하고자 하는 물품분류10단위 종료번호 |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 0 | 애완동물사육장 | 조회하고자 하는 물품분류명으로 Like 검색
예)조약돌 |
| dtilPrdctClsfcNoEngNm | 세부영문품명 | 200 | 0 | Cages | 조회하고자 하는 세부영문품명 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 1 | 1013160101 | 10단위 물품 분류번호 |
| dtilPrdctClsfcNoNm | 세부품명 | 200 | 1 | 애완동물사육장 | 세부품명번호 10자리에 대한 한글명 |
| dtilPrdctClsfcNoEngNm | 세부영문품명 | 100 | 1 | Cages | 세부품명에 대한 영문명 |
| dtilPrdctClsfcNoNmDscrpt | 세부품명해설 | 100 | 0 | 애완용 동물을 운반하거나 격리수용 시키기 위해 사용하는 장치로 투명한 플라스틱, 철망등으로 제작되어 있음. | 세부품명에 대한 상세설명 |
| useYn | 사용여부 | 1 | 0 | Y | 물품분류번호의 사용여부 |
| chgDate | 변경일자 | 10 | 0 | 2013-08-26 | 물품분류번호에 대한 정보 변경일자  'YYYY-MM-DD' |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getPrdctClsfcNoUnit10Info02?dtilPrdctClsfcNoBgnNo=10&dtilPrdctClsfcNoEndNo=11&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <dtilPrdctClsfcNo>1010150201</dtilPrdctClsfcNo> <dtilPrdctClsfcNoNm>개</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNoEngNm>Dogs</dtilPrdctClsfcNoEngNm> <dtilPrdctClsfcNoNmDscrpt>식육목 개과의 포유류. 한자로 견, 구, 술 등으로 표기함. 포유류 중 가장 오래된 가축으로 거의 전세계에서 사육되며 200여 품종이 있음.</dtilPrdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2010-09-03</chgDate> </item> <item> <dtilPrdctClsfcNo>1010150401</dtilPrdctClsfcNo> <dtilPrdctClsfcNoNm>밍크</dtilPrdctClsfcNoNm> <dtilPrdctClsfcNoEngNm>Mink</dtilPrdctClsfcNoEngNm> <dtilPrdctClsfcNoNmDscrpt>족제비과에 속하는 동물로 서북아메리카, 프랑스 등지에 분포하며, 물가 근처를 좋아하고, 시각은 물속의 물체를 잘 발견할 수 있도록 적응되어 있지는 않으나 물고기를 쫓아 잠수하기 전에 물위에서 먹이의 위치를 잘 보아둠.</dtilPrdctClsfcNoNmDscrpt> <useYn>Y</useYn> <chgDate>2010-09-03</chgDate> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[물품분류변경 이력조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 13 | 오퍼레이션명(국문) | 물품분류변경 이력조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrdctClsfcNoChgHstry02 |
|  | 오퍼레이션 설명 | 물품목록분류(물품분류2단위, 4단위, 6단위, 8단위) 변경이력조회의 검색조건(변경기간시작일자,변경기간종료일자,물품분류번호)을 입력하면 물품분류번호,품명, 변경전후물품분류번호,변경전후물품분류번호명, 변경사유내용 등이 조회된다 |  |  |
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
| chgPrdBgnDt | 변경기간시작일자 | 16 | 1 | 20160501 | 변경일자 범위 (시작일자) 'YYYYMMDD' |
| chgPrdEndDt | 변경기간종료일자 | 16 | 1 | 20160719 | 변경일자 범위 (종료일자) 'YYYYMMDD' |
| prdctClsfcNo | 물품분류번호 | 8 | 0 | 53102503 | 조회하고자 하는 물품분류번호 (물품분류번호는 2단위, 4단위, 6단위, 8단위로 입력 가능) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| chgTyCd | 변경유형코드 | 1 | 1 | U | 변경유형 타입 신규추가시 'I', 변경시 'U', 삭제시 'D' |
| prdctClsfcNo | 물품분류번호 | 10 | 1 | 52121512 | 품명에 대한 분류번호로서 8자리로 표시 |
| prdctClsfcNoNm | 품명 | 200 | 1 | 베갯잇 | 물품분류번호 8자리에 대한 한글명 |
| prdctClsfcNoEngNm | 영문품명 | 200 | 0 | Pillow cases | 물품에 대한 분류 영문명 |
| prdctClsfcNoNmDscrpt | 품명해설 | 4000 | 0 | 베개를 덮어 씌우는 커버로서, 대부분 린넨천, 면 등으로 만듦 | 물품 분류에 대한 상세설명 |
| rgstDt | 등록일시 | 10 | 1 | 2002-09-06 23:19 | 물품분류번호에 대한 정보 등록일자  'YYYY-MM-DD HH24:MI' |
| chgDt | 변경일시 | 10 | 1 | 2016-05-26 17:35 | 물품분류번호에 대한 정보 변경일자  'YYYY-MM-DD HH24:MI' |
| afchgPrdctClsfcNo | 변경후물품분류번호 | 8 | 0 | 52121512 | 변경 후 물품분류번호 |
| afchgPrdctClsfcNoNm | 변경후물품분류번호명 | 200 | 0 | 베갯잇 | 변경 후 물품분류명 |
| bfchgPrdctClsfcNo | 변경전물품분류번호 | 8 | 0 | 52121512 | 변경 전 물품분류번호 |
| bfchgPrdctClsfcNoNm | 변경전물품분류번호명 | 200 | 0 | 배갯잇 | 변경 전 물품분류명 |
| chgRsnCntnts | 변경사유내용 | 4000 | 0 | 물품관리과-914(2016.05.25.)에 의거 품명변경 | 물품분류번호나 물품분류명이 변경된 사유 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/getPrdctClsfcNoChgHstry02?chgPrdBgnDt=20160501&chgPrdEndDt=20160719&prdctClsfcNo=&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <chgTyCd>I</chgTyCd> <prdctClsfcNo>41103517</prdctClsfcNo> <prdctClsfcNoNm>생물안전작업대</prdctClsfcNoNm> <prdctClsfcNoEngNm>Biological safety cabinet</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>외기 또는 정화된 공기가 작업대에 제공되고, 작업대의 공기는 정화되어 작업대 밖으로 배출되는 독립 공간에서 실험할 수 있는 작업대로서, 시료, 환경, 작업자를 보호할 수 있음. 환경과 작업자만 보호할 수 있는 제품을 포함함.</prdctClsfcNoNmDscrpt> <rgstDt>2016-07-19 17:00</rgstDt> <chgDt>2016-07-19 17:00</chgDt> <afchgPrdctClsfcNo /> <afchgPrdctClsfcNoNm /> <bfchgPrdctClsfcNo /> <bfchgPrdctClsfcNoNm /> <chgRsnCntnts>물품관리과-1287(2016.7.18)에 따른 분류신설</chgRsnCntnts> </item> <item> <chgTyCd>I</chgTyCd> <prdctClsfcNo>81111597</prdctClsfcNo> <prdctClsfcNoNm>클라우드서비스</prdctClsfcNoNm> <prdctClsfcNoEngNm>Cloud service</prdctClsfcNoEngNm> <prdctClsfcNoNmDscrpt>사진ㆍ문서ㆍ동영상 등 각종 콘텐츠를 클라우드 서버에 저장한 뒤 인터넷으로 접속해 노트북ㆍ스마트폰 등 다양한 기기로 이용할 수 있는 서비스.</prdctClsfcNoNmDscrpt> <rgstDt>2016-07-15 15:19</rgstDt> <chgDt>2016-07-15 15:19</chgDt> <afchgPrdctClsfcNo /> <afchgPrdctClsfcNoNm /> <bfchgPrdctClsfcNo /> <bfchgPrdctClsfcNoNm /> <chgRsnCntnts>물품관리과-1275(2016.07.15.)에 따른 품명 신설.</chgRsnCntnts> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[품목개별속성정보 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 13 | 오퍼레이션명(국문) | 품목개별속성정보 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrdctIndvAtrbInfoList02 |
|  | 오퍼레이션 설명 | 품목(물품식별)정보에 해당되는 개별 속성 및 속성값 , 측정단위 정보 제공 |  |  |
|  | Call Back URL | N/A |  |  |
|  | 최대 메시지 사이즈 | [ 4000bytes] |  |  |
|  | 평균 응답 시간 | [ 	500	ms] | 초당 최대 트랜잭션 | [ 	30	tps] |

요청 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| ServiceKey | 서비스키 | 400 | 1 | 공공데이터포털에서 받은 인증키 | 공공데이터포털에서 받은 인증키 |
| Type | 타입 | 4 | 0 | json | 오픈API 리턴 타입을 JSON으로 받고 싶을 경우 'json' 으로 지정함 |
| prdctIdntNo | 물품식별번호 | 8 | 1 | 23657020 | 조회하고자 하는 물품식별번호 검색(필수) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상 | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 9 | 데이터 총 개수 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 1 | 5410150201 | UNSPSC에서 분류된 세뷴류의 품명보다 세분화가 필요한 품명에 부여한 10자리 번호 |
| prdctIdntNo | 물품식별번호 | 8 | 1 | 23657020 | 품목 고유번호인 8자리 번호 |
| prdctIdntNoNm | 물품규격명 | 500 | 0 | 보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm | 세부품명, 제조업체명, 제작국/모델명, 특성값 등 기본전인 규격사항등이 기록된 품목의 명칭 |
| attrNm | 속성명 | 200 | 0 | 장식품크기(가로) | 규격에 대한 속성명 |
| attrVal | 속성값 | 4000 | 0 | 13 | 규격에 대한 속성의 값 |
| attrUnit | 측정단위 | 150 | 0 | mm | 규격에 대한 속성의 측정 단위 |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/ThngListInfoService02/ getPrdctIndvAtrbInfoList02?prdctIdntNo=23657020 &pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <dtilPrdctClsfcNo>5410150201</dtilPrdctClsfcNo> <prdctIdntNo>23657020</prdctIdntNo> <prdctIdntNoNm>보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm</prdctIdntNoNm> <attrNm>장식품크기(가로)</attrNm> <attrVal>13</attrVal> <attrUnit>mm</attrUnit> </item> <item> <dtilPrdctClsfcNo>5410150201</dtilPrdctClsfcNo> <prdctIdntNo>23657020</prdctIdntNo> <prdctIdntNoNm>보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm</prdctIdntNoNm> <attrNm>장식품크기(세로)</attrNm> <attrVal>20</attrVal> <attrUnit>mm</attrUnit> </item> <item> <dtilPrdctClsfcNo>5410150201</dtilPrdctClsfcNo> <prdctIdntNo>23657020</prdctIdntNo> <prdctIdntNoNm>보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm</prdctIdntNoNm> <attrNm>무게</attrNm> <attrVal></attrVal> <attrUnit></attrUnit> </item> <item> <dtilPrdctClsfcNo>5410150201</dtilPrdctClsfcNo> <prdctIdntNo>23657020</prdctIdntNo> <prdctIdntNoNm>보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm</prdctIdntNoNm> <attrNm>순도</attrNm> <attrVal>92.5</attrVal> <attrUnit>%</attrUnit> </item> <item> <dtilPrdctClsfcNo>5410150201</dtilPrdctClsfcNo> <prdctIdntNo>23657020</prdctIdntNo> <prdctIdntNoNm>보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm</prdctIdntNoNm> <attrNm>장식품재질</attrNm> <attrVal>실버</attrVal> <attrUnit></attrUnit> </item> <item> <dtilPrdctClsfcNo>5410150201</dtilPrdctClsfcNo> <prdctIdntNo>23657020</prdctIdntNo> <prdctIdntNoNm>보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm</prdctIdntNoNm> <attrNm>장식품형태</attrNm> <attrVal></attrVal> <attrUnit></attrUnit> </item> <item> <dtilPrdctClsfcNo>5410150201</dtilPrdctClsfcNo> <prdctIdntNo>23657020</prdctIdntNo> <prdctIdntNoNm>보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm</prdctIdntNoNm> <attrNm>줄길이</attrNm> <attrVal>420</attrVal> <attrUnit>mm</attrUnit> </item> <item> <dtilPrdctClsfcNo>5410150201</dtilPrdctClsfcNo> <prdctIdntNo>23657020</prdctIdntNo> <prdctIdntNoNm>보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm</prdctIdntNoNm> <attrNm>줄재질</attrNm> <attrVal>실버</attrVal> <attrUnit></attrUnit> </item> <item> <dtilPrdctClsfcNo>5410150201</dtilPrdctClsfcNo> <prdctIdntNo>23657020</prdctIdntNo> <prdctIdntNoNm>보석목걸이, 오드블랑, 꽃담칠보은목걸이-황색, 13×20mm</prdctIdntNoNm> <attrNm>옵션/기타</attrNm> <attrVal></attrVal> <attrUnit></attrUnit> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>9</totalCount> </body> </response> |

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

