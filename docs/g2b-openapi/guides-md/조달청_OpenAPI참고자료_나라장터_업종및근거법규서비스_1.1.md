# 조달청_OpenAPI참고자료_나라장터_업종및근거법규서비스_1.1

> 출처: 공공데이터포털 참고문서(docx) 자동 변환

조달청 공공데이터 개방
OpenAPI 참고자료
목 차목 차

### 1. 서비스 명세	3


### 1.1 나라장터 업종 및 근거법규서비스	3

가. 서비스 개요	3
나. 오퍼레이션 목록	4
개정 이력

| 버 전 | 변경일 | 변경 구분 | 변경사유 |
|---|---|---|---|
| 1.0 | 2025 | 최초 개정 | 최초 개정 |
| 1.1 | 2026.3.17 | 수정 | 오퍼레이션명칭 수정 |
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
|  |  |  |  |


### 1. 서비스 명세


### 1.1 나라장터 업종 및 근거법규서비스

서비스 개요

| 서비스 정보 | 서비스 ID | IndstrytyBaseLawrgltInfoService |  |  |
|---|---|---|---|---|
|  | 서비스명(국문) | 나라장터 업종 및 근거법규서비스 |  |  |
|  | 서비스명(영문) | IndstrytyBaseLawrgltInfoService |  |  |
|  | 서비스 설명 | 나라장터 업종 및 근거법규를 조회한다. 법제처가 분류하고 있는 법 분야에 따라 업종이 분류되어 있으며 법 분야별로 업종을 코드화하여 제공 법령의 제정∙개정∙폐지 등으로 업종이 추가/수정/삭제될 경우 조회시점에 유효한 업종 정보만 제공 |  |  |
| 서비스 보안 | 서비스 인증/권한 | [O] 서비스 Key[ ] 인증서 (GPKI) [] Basic (ID/PW) [ ] 없음 | [ ]WS-Security |  |
|  | 메시지 레벨 암호화 | [  ] 전자서명	[ ] 암호화	[O] 없음 |  |  |
|  | 전송 레벨 암호화 | [  ] SSL			[ O] 없음 |  |  |
| 적용 기술 수준 | 인터페이스 표준 | [  ] SOAP 1.2 (RPC-Encoded, Document Literal, Document Literal Wrapped) [ O ] REST (GET) [ ] RSS 1.0 [ ] RSS 2.0 [ ] Atom 1.0 [ ] 기타 |  |  |
|  | 교환 데이터 표준 | [ O ] XML	[ O ] JSON	[ ] MIME	[ ] MTOM |  |  |
| 서비스 URL | 개발환경 | http://apis.data.go.kr/1230000/ao/IndstrytyBaseLawrgltInfoService |  |  |
|  | 운영환경 | http://apis.data.go.kr/1230000/ao/IndstrytyBaseLawrgltInfoService |  |  |
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
| 1 | 나라장터 업종 및 근거법규서비스 | getIndstrytyBaseLawrgltInfoList | 업종 및 근거법규 정보 조회 | N/A |

[업종및근거법규 정보 조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 1 | 오퍼레이션명(국문) | 업종 및 근거법규 정보 조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getIndstrytyBaseLawrgltInfoList |
|  | 오퍼레이션 설명 | 업종 및 근거법규 정보 목록을 조회할 수 있다. - 조건 입력을 하지 않을 경우 전체 조회 |  |  |
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
| indstrytyClsfcCd | 업종분류코드 | 23 | 0 | 41 | 검색하고자하는 업종을 분류하는 코드 |
| indstrytyNm | 업종명 | 200 | 0 | 나무병원(1종) | 검색하고자하는 업종에 대한 분류 명 |
| indstrytyCd | 업종코드 | 4 | 0 | 4161 | 검색하고자하는 업종을 분류하는 코드
코드값) |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 201801010000 | 검색하고자하는 업종등록일 기준 조회시작일시 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 201807312359 | 검색하고자하는 업종등록일 기준 조회종료일시 |
| indstrytyUseYn | 업종사용여부 | 1 | 0 | Y | 업종사용여부, 값이 없을 경우 전체 조회 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 예제

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 전체 결과 수 | 4 | 1 | 1 | 전체 결과 수 |
| indstrytyClsfcCd | 업종분류코드 | 3 | 1 | 03 | 분류코드는 법제처의 「법분야」에 따라 업종 분류(단,분류코드 ='99' : 근거법령 및 관련규정이 없는 임의로 부여한 업종) |
| indstrytyClsfcNm | 업종분류명 | 100 | 1 | 법무 | 업종에 대한 분류 명 |
| indstrytyCd | 업종코드 | 20 | 1 | 1461 | 업종코드: 국가종합전자조달시스템(나라장터) 입찰참가자격등록규정에 따라 조달청이 업종DB분류체계에 의하여 법령분야별로 업종을 분류한 4자리 숫자 코드 |
| indstrytyNm | 업종명 | 200 | 1 | 법무사업(사무소) | 업종에 대한 명 |
| baseLawordNm | 근거법령명 | 220 | 0 | 법무사법 | 근거법령 |
| baseLawordArtclClauseNm | 근거법령조항명 | 220 | 0 | 제14조 | 근거법령 |
| baseLawordUrl | 근거법령URL | 250 | 0 | https://www.g2b.go.kr:8070/um/co/fwdBaseLawDtl.do?lawId=1808&joNo=000900 | 관련법령PDF 파일 URL링크 |
| rltnRgltCntnts | 관련규정내용 | 500 | 0 | 법무사법 제7조, 제8조; 법무사규칙 제18조, 제19조, 제23조; 대한법무사협회 법무사등록규칙 제2조, 제3조, 제15조 | 관련 규정 내용 |
| inclsnLcns | 포함면허 | 2000 | 0..n | [1^0001^토목공사업^0003^토목건축공사업], [0002^토목공사업2^0004^토목건축공사업2] | 업종의 제한업종과 허용업종에 관한 서브데이터셋
[순번^제한업종코드^제한업종명^허용업종코드^허용업종명] |
| indstrytyUseYn | 업종사용여부 | 1 | 0 | Y | 업종사용여부 |
| indstrytyRgstDt | 업종등록일시 | 20 | 1 | 2006-01-12 18:43 | 업종의 등록일시 "YYYY-MM-DD HH:MM" |
| indstrytyChgDt | 업종변경일시 | 20 | 0 | 2014-02-05 13:17 | 업종의 변경일시 "YYYY-MM-DD HH:MM" |

요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/IndstrytyBaseLawrgltInfoService/getIndstrytyBaseLawrgltInfoList?indstrytyClsfcCd=49&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <indstrytyClsfcCd>49</indstrytyClsfcCd> <indstrytyClsfcNm>건설업</indstrytyClsfcNm> <indstrytyCd>0001</indstrytyCd> <indstrytyNm>토목공사업</indstrytyNm> <baseLawordNm>건설산업기본법</baseLawordNm> <baseLawordArtclClauseNm>제9조</baseLawordArtclClauseNm> <baseLawordUrl>https://www.g2b.go.kr:8070/um/co/fwdBaseLawDtl.do?lawId=1808&joNo=000900&note=</baseLawordUrl> <rltnRgltCntnts>건설산업기본법시행령 제7조 내지 제16조, 동시행규칙 제2조 내지 제10조의2</rltnRgltCntnts> <inclsnLcns>[1^0001^토목공사업^0003^토목건축공사업]</inclsnLcns> <indstrytyUseYn>Y</indstrytyUseYn> <indstrytyRgstDt>2006-01-12 18:43</indstrytyRgstDt> <indstrytyChgDt>2006-01-12 18:43</indstrytyChgDt> </item> <item> <indstrytyClsfcCd>49</indstrytyClsfcCd> <indstrytyClsfcNm>건설업</indstrytyClsfcNm> <indstrytyCd>0002</indstrytyCd> <indstrytyNm>건축공사업</indstrytyNm> <baseLawordNm>건설산업기본법</baseLawordNm> <baseLawordArtclClauseNm>제9조</baseLawordArtclClauseNm>   <baseLawordUrl>https://www.g2b.go.kr:8070/um/co/fwdBaseLawDtl.do?lawId=1808&joNo=000900&note=</baseLawordUrl> <rltnRgltCntnts>건설산업기본법시행령 제7조 내지 제16조, 동시행규칙 제2조 내지 제10조의2</rltnRgltCntnts> <inclsnLcns>[0002^건축공사업],[0003^토목건축공사업]</inclsnLcns> <indstrytyUseYn>Y</indstrytyUseYn> <indstrytyRgstDt>2006-01-12 18:43</indstrytyRgstDt> <indstrytyChgDt>2006-01-12 18:43</indstrytyChgDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>2</totalCount> </body> </response> |

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

