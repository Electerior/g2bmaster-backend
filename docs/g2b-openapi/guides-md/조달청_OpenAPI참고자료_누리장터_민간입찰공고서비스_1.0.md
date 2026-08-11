# 조달청_OpenAPI참고자료_누리장터_민간입찰공고서비스_1.0

> 출처: 공공데이터포털 참고문서(docx) 자동 변환

조달청 공공데이터 개방
OpenAPI 참고자료
목 차목 차

### 1. 서비스 명세	3


### 1.1 누리장터 민간입찰공고서비스	3

가. 서비스 개요	3
나. 오퍼레이션 목록	4
개정 이력

| 버 전 | 변경일 | 변경 구분 | 변경사유 |
|---|---|---|---|
| 1.0 | 2025 | 최초 개정 | 최초 개정 |
| 1.0 | 2026.0624 | 기타변경 | *전남광주통합특별시 지역코드 추가 - 목록정보(일반검색) 소재지 목록 조회 |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |


### 1. 서비스 명세


### 1.1 누리장터 민간입찰공고서비스

서비스 개요

| 서비스 정보 | 서비스 ID | PrvtBidNtceService |  |  |
|---|---|---|---|---|
|  | 서비스명(국문) | 누리장터 민간입찰공고서비스 |  |  |
|  | 서비스명(영문) | PrvtBidNtceService |  |  |
|  | 서비스 설명 | 누리장터에 등록된 민간입찰공고정보를 제공하는 서비스로 용역, 물품, 공사, 기타 업무로 구분하여 입찰공고번호, 게시일시, 공고명, 입찰방식명, 개찰일시, 기준금액, 공고규격서, 아파트정보 등의 정보를 제공 |  |  |
| 서비스 보안 | 서비스 인증/권한 | [O] 서비스 Key[ ] 인증서 (GPKI) [] Basic (ID/PW) [ ] 없음 | [ ]WS-Security |  |
|  | 메시지 레벨 암호화 | [  ] 전자서명	[ ] 암호화	[O] 없음 |  |  |
|  | 전송 레벨 암호화 | [  ] SSL			[ O] 없음 |  |  |
| 적용 기술 수준 | 인터페이스 표준 | [  ] SOAP 1.2 (RPC-Encoded, Document Literal, Document Literal Wrapped) [ O ] REST (GET) [ ] RSS 1.0 [ ] RSS 2.0 [ ] Atom 1.0 [ ] 기타 |  |  |
|  | 교환 데이터 표준 | [ O ] XML	[ O ] JSON	[ ] MIME	[ ] MTOM |  |  |
| 서비스 URL | 개발환경 | http://apis.data.go.kr/1230000/ao/PrvtBidNtceService |  |  |
|  | 운영환경 | http://apis.data.go.kr/1230000/ao/PrvtBidNtceService |  |  |
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
| 1 | 누리장터 민간입찰공고서비스 | getPrvtBidPblancListInfoServc | 민간입찰공고정보에 대한 용역조회 | N/A |
| 2 |  | getPrvtBidPblancListInfoThng | 민간입찰공고정보에 대한 물품조회 | N/A |
| 3 |  | getPrvtBidPblancListInfoCnstwk | 민간입찰공고정보에 대한 공사조회 | N/A |
| 4 |  | getPrvtBidPblancListInfoEtc | 민간입찰공고정보에 대한 기타조회 | N/A |
| 5 |  | getPrvtBidPblancListInfoLicenseLimit | 민간입찰공고정보에 대한 면허제한정보조회 | N/A |
| 6 |  | getPrvtBidPblancListInfoPrtcptPsblRgn | 민간입찰공고정보에 대한 참가가능지역정보조회 | N/A |
| 7 |  | getPrvtBidPblancListInfoServcPPSSrch | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 용역조회 | N/A |
| 8 |  | getPrvtBidPblancListInfoThngPPSSrch | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 물품조회 | N/A |
| 9 |  | getPrvtBidPblancListInfoCnstwkPPSSrch | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 공사조회 | N/A |
| 10 |  | getPrvtBidPblancListInfoEtcPPSSrch | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 기타조회 | N/A |

[민간입찰공고정보에 대한 용역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 1 | 오퍼레이션명(국문) | 민간입찰공고정보에 대한 용역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoServc |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일범위, 공고일범위, 개찰일범위, 입찰공고번호를 입력하여 입찰공고번호, 입찰공고차수, 입찰공고분류, 게시일시, 참조번호, 공고명, 공고구분명, 공고기관명, 입찰방식명, 계약방법명, 낙찰방법명, 재입찰구분명, 입찰자격명, 담당자명, 담당자전화번호, 담당자이메일, 입찰개시일시, 입찰마감일시, 개찰일시, 개찰장소, 입찰보증서접수마감일시, 현장설명일시, 현장설명장소, 부가가치세포함여부명, 기준금액사용여부, 기준금액공개여부, 기준금액, 배정예산금액, 기준금액설명, 지역제한구분명, 용역상세목록, 공고서URL, 아파트정보, 등록일시 등의 누리장터시스템에 등록된 용역 입찰공고 정보 조회 |  |  |
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
| inqryDiv | 조회구분 | 1 | 1 | 1 | 검색하고자하는 조회구분, 1:등록일시 , 2.공고게시일시, 3. 개찰일시, 4.입찰공고번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202501010000 | 검색하고자하는 조회시작일시 "YYYYMMDDHHMM",(조회구분 1,2,3 선택시 필수) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 2025010312359 | 검색하고자하는 조회종료일시 "YYYYMMDDHHMM",(조회구분 1,2,3 선택시 필수) |
| bidNtceNo | 입찰공고번호 | 40 | 0 | R25BK00597093 | 검색하고자 하는 입찰공고번호 (조회구분 '4' 선택시 필수) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총 개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | R25BK00597093 | 입찰공고번호 |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수 |
| bidNtceClsfc | 입찰공고분류 | 30 | 1 | 민간일반용역 | 누리장터 입찰공고의 공고분류 (민간물품, 민간공사, 민간기술용역,민간일반용역, 민간기타) |
| nticeDt | 게시일시 | 19 | 1 | 2025-01-22 08:26:20 | 입찰공고 게시일시 "YYYY-MM-DD HH:MM:SS" |
| refNo | 참조번호 | 105 | 0 | 재무회계팀-342 | 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호 |
| ntceNm | 공고명 | 2000 | 0 | The-K예다함 2025 ~ 2027 회계연도 외부감사인 선임 입찰 | 누리장터 입찰공고의 공고명 |
| ntceDivNm | 공고구분명 | 2000 | 0 | 등록공고 | 누리장터 입찰공고의 공고구분명[등록공고,재공고,긴급공고,변경공고,취소공고]등으로 표기 |
| ntceInsttNm | 공고기관명 | 200 | 1 | 더케이예다함(주) | 누리장터 입찰공고의 공고기관명으로 민간발주자기관명 |
| bidMethdNm | 입찰방식명 | 200 | 1 | 전자입찰 | 누리장터 입찰공고의 입찰방식명[수기입찰, 전자입찰, 역경매] |
| cntrctMthdNm | 계약방법명 | 200 | 1 | 제한경쟁 | 누리장터 입찰공고의 계약방식명 [일반경쟁, 제한경쟁,지명경쟁] |
| sucsfbidMthdNm | 낙찰방법명 | 200 | 1 | 제한경쟁 | 누리장터 입찰공고의 낙찰방식명 [최저가, 최고가, 일적격심사최저가, 적격심사최고가] |
| rbidDivNm | 재입찰구분명 | 20 | 0 | 재입찰 허용 | 누리장터 입찰공고의 재입찰 허용여부명 |
| bidQlfctNm | 입찰자격명 | 20 | 0 | 공고규격서 참조 | “공고서 참조”로 고정되어 있음 |
| ofclNm | 담당자명 | 35 | 0 | 이동근 | 누리장터 입찰공고의 담당자명 |
| ofclTelNo | 담당자전화번호 | 25 | 0 | *********** | 누리장터 입찰공고의 담당자 전화번호 |
| ofclEmail | 담당자이메일 | 100 | 0 | Nine***@thekyedaham.co.kr | 누리장터 입찰공고의 담당자 메일주소 |
| bidBeginDt | 입찰개시일시 | 19 | 0 | 2025-01-22 09:00:00 | 누리장터 입찰공고의 입찰개시일시 "YYYY-MM-DD HH:MM:SS" |
| bidClseDt | 입찰마감일시 | 19 | 0 | 2025-02-05 17:00:00 | 누리장터 입찰공고의 입찰마감일시 "YYYY-MM-DD HH:MM:SS" |
| opengDt | 개찰일시 | 19 | 0 | 2025-02-05 18:00:00 | 누리장터 입찰공고의 개찰(입찰)일시 "YYYY-MM-DD HH:MM:SS" |
| opengPlce | 개찰장소 | 100 | 0 | 전자입찰담당자 PC | 누리장터 입찰공고의 개찰장소 |
| bidWgrnteeRcptClseDt | 입찰보증서접수마감일시 | 19 | 0 | 2025-02-05 17:00:00 | 누리장터 입찰공고의 접수마감일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptDt | 현장설명일시 | 19 | 0 | 2025-02-05 17:00:00 | 누리장터 입찰공고의 현장설명일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptPlce | 현장설명장소 | 100 | 0 | 현장설명장소 | 누리장터 입찰공고의 현장설명장소 |
| vatInclsnYnNm | 부가가치세포함여부명 | 200 | 0 | 부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다. | 누리장터 입찰공고의 부가가치세여부명 |
| refAmtUseYn | 기준금액사용여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격사용여부[Y,N] |
| refAmtOpenYn | 기준금액공개여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격공개여부[Y,N] |
| refAmt | 기준금액 | 25 | 0 | 165000000 | 투찰의 상한금액(원화,원) |
| asignBdgtAmt | 배정예산금액 | 25 | 0 | 165000000 | 누리장터 입찰공고의 배정예산금액(원화,원) |
| refAmtDscrpt | 기준금액설명 | 200 | 0 | 기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다. | 고정된 안내 문구 |
| rgnLmtDivNm | 지역제한구분명 | 100 | 0 | 공고서 참조 | 누리장터 입찰공고의 투찰제한내역으로 투찰제한이나 공고서 참조로 표기 |
| dtchacOpenDt | 역경매공개일시 | 19 | 0 | 2025-02-05 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 공개일시, “YYYY-MM-DD HH:MM:SS” |
| dtchacBgnPrce | 역경매시작가격 | 25 | 0 | 10000000 | 낙찰방법이 복수견적(역경매)일 경우  누리장터 입찰공고의 시작가격을 보여줌 |
| dtchacRmrk | 역경매비고 | 1000 | 0 | 기초금액은 부가가치세 포함금액임 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 비고내용 |
| bssAmtDtlScrnUrl | 기초금액상세화면URL | 500 | 0 | N/A | 낙찰방법이 복수견적(역경매)일 경우 기초금액상세화면URL( 25년도 나라장터 차세대 이후 제공 불가) |
| dtchacMinRdctnRt | 역경매최소인하비율 | 25 | 0 | 0.0 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 최소인하비율을 보여줌 |
| dtchacBidprcLmtNum | 역경매투찰제한횟수 | 100 | 0 | 제한없음 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 투찰제한횟수을 보여줌(회), 투찰제한회수가 0일 경우는 “제한없음” |
| dtchacAutoEtTm | 역경매자동연장시각 | 20 | 0 | 10. | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 자동연장시간(분), 자동연장시각:입찰마감일시 10분 전에 투찰이 이루어질 경우 입찰서마감일시를 해당 분만큼 자동연장합니다. 자동연장시각이 0분 일 경우, 입찰서마감일시 연장이 없는 경우입니다. 자동연장기능은 개찰일시 30분이전까지만 유효합니다. 즉, 입찰서마감일시의 자동연장은 개찰일시 30분 전까지만 최대 연장됩니다 |
| dtchacBeforeBidDocClseDt | 역경매이전입찰서마감일시 | 19 | 0 | 2025-02-05 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 이전입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| dtchacEtBidDocClseDt | 역경매연장입찰문서마감일시 | 19 | 0 | 2025-02-05 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 연장입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| servcDtlList | 용역상세목록 | 4000 | 0..n | [1^^^] | 용역상세목록 [순번^용역명^용역현장명^완수기한일자], [순번^용역명^용역현장명^완수기한일자] |
| ntceSpecDocUrl1 | 공고규격서URL1 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=1&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl2 | 공고규격서URL2 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=2&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl3 | 공고규격서URL3 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=3&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl4 | 공고규격서URL4 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=4&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl5 | 공고규격서URL5 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=3&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl6 | 공고규격서URL6 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=6&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl7 | 공고규격서URL7 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=7&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl8 | 공고규격서URL8 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=8&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl9 | 공고규격서URL9 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=9&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl10 | 공고규격서URL10 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=10&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocNm1 | 공고규격서명1 | 256 | 0 | 1. 2025~2027 회계연도 외부감사인 선입 입찰공고_250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm2 | 공고규격서명2 | 256 | 0 | 2. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm3 | 공고규격서명3 | 256 | 0 | 3. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm4 | 공고규격서명4 | 256 | 0 | 4. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm5 | 공고규격서명5 | 256 | 0 | 5. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm6 | 공고규격서명6 | 256 | 0 | 6. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm7 | 공고규격서명7 | 256 | 0 | 7. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm8 | 공고규격서명8 | 256 | 0 | 8. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm9 | 공고규격서명9 | 256 | 0 | 9. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm10 | 공고규격서명10 | 256 | 0 | 10. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| aptHsmpNm | 아파트단지명 | 100 | 0 | 한밭우성아파트입주자대표회의 | 누리장터 입찰공고 공고기관 아파트 민간발주기관명 (누리장터 화면에는 단지명) |
| aptCeoAdrs | 아파트대표자주소 | 400 | 0 | 대전광역시 중구 문화동141 | 누리장터 입찰공고 공고기관 아파트 주소 |
| aptMngOfficeTelNo | 아파트관리사무소전화번호 | 25 | 0 | 042-585-3051 | 누리장터 입찰공고 공고기관 아파트 전화번호 |
| aptHmpgUrl | 아파트홈페이지URL | 100 | 0 | www.kukdong2.co.kr | 누리장터 입찰공고 공고기관 아파트 홈페이지URL |
| aptTotar | 아파트연면적 | 100 | 0 | 100097.72 | 누리장터 입찰공고 공고기관 아파트 연면적(㎡) |
| aptMngcstLevyArea | 아파트관리비부과면적 | 100 | 0 | 974 | 누리장터 입찰공고 공고기관 아파트 관리비부과면적 (㎡) |
| aptHshldNum | 아파트세대수 | 10 | 0 | 1400 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptCmplNum | 아파트동수 | 10 | 0 | 10 | 누리장터 입찰공고 공고기관 아파트 동수 |
| aptHeatMethdNm | 아파트난방방식명 | 10 | 0 | 중앙 | 누리장터 입찰공고 공고기관 아파트 난방방식 |
| aptSubactLrgeNdCmpnintFclty | 아파트부대및복리시설 | 4000 | 0 | - 관리사무실 : 69.42   - 노인정: 101.49   - 어린이놀이터 : 3,811.01(3개소)  - 주차장(대수) :994대 | 누리장터 입찰공고 공고기관 아파트 부대 및 복리시설 등 |
| rgstDt | 등록일시 | 19 | 1 | 2025-01-22 08:26:20 | 공고의 등록일시 “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoServc?inqryDiv=4&bidNtceNo=R25BK00597093&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>R25BK00597093</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <bidNtceClsfc>민간일반용역</bidNtceClsfc> <nticeDt>2025-01-22 08:26:20</nticeDt> <refNo>재무회계팀-342</refNo> <ntceNm>The-K예다함 2025 ~ 2027 회계연도 외부감사인 선임 입찰</ntceNm> <ntceDivNm>등록공고</ntceDivNm> <ntceInsttNm>더케이예다함(주)</ntceInsttNm> <bidMethdNm>전자입찰</bidMethdNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <sucsfbidMthdNm>최저가낙찰제</sucsfbidMthdNm> <rbidDivNm>재입찰 허용</rbidDivNm> <bidQlfctNm>공고서 참조</bidQlfctNm> <ofclNm>이동근</ofclNm> <ofclTelNo></ofclTelNo> <ofclEmail>nine0659@thekyedaham.co.kr</ofclEmail> <bidBeginDt>2025-01-22 09:00:00</bidBeginDt> <bidClseDt>2025-02-05 17:00:00</bidClseDt> <opengDt>2025-02-05 18:00:00</opengDt> <opengPlce>전자입찰담당자 PC</opengPlce> <bidWgrnteeRcptClseDt></bidWgrnteeRcptClseDt> <sptDscrptDt></sptDscrptDt> <sptDscrptPlce></sptDscrptPlce> <vatInclsnYnNm>부가세 포함(입찰가격은 부가가치세를 포함한 금액을 제출하셔야 합니다.</vatInclsnYnNm> <refAmtUseYn>Y</refAmtUseYn> <refAmtOpenYn>Y</refAmtOpenYn> <refAmt>165000000</refAmt> <asignBdgtAmt>165000000</asignBdgtAmt> <refAmtDscrpt>기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다.</refAmtDscrpt> <rgnLmtDivNm>공고서 참조</rgnLmtDivNm> <dtchacOpenDt></dtchacOpenDt> <dtchacBgnPrce></dtchacBgnPrce> <dtchacRmrk></dtchacRmrk> <bssAmtDtlScrnUrl></bssAmtDtlScrnUrl> <dtchacMinRdctnRt></dtchacMinRdctnRt> <dtchacBidprcLmtNum></dtchacBidprcLmtNum> <dtchacAutoEtTm></dtchacAutoEtTm> <dtchacBeforeBidDocClseDt></dtchacBeforeBidDocClseDt> <dtchacEtBidDocClseDt></dtchacEtBidDocClseDt> <servcDtlList>[1^^^]</servcDtlList> <ntceSpecDocUrl1>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=1&amp;prcmBsneSeCd=22</ntceSpecDocUrl1> <ntceSpecDocUrl2>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=2&amp;prcmBsneSeCd=22</ntceSpecDocUrl2> <ntceSpecDocUrl3></ntceSpecDocUrl3> <ntceSpecDocUrl4></ntceSpecDocUrl4> <ntceSpecDocUrl5></ntceSpecDocUrl5> <ntceSpecDocUrl6></ntceSpecDocUrl6> <ntceSpecDocUrl7></ntceSpecDocUrl7> <ntceSpecDocUrl8></ntceSpecDocUrl8> <ntceSpecDocUrl9></ntceSpecDocUrl9> <ntceSpecDocUrl10></ntceSpecDocUrl10> <ntceSpecDocNm1>1. 2025~2027 회계연도 외부감사인 선입 입찰공고_250122.hwp</ntceSpecDocNm1> <ntceSpecDocNm2>2. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp</ntceSpecDocNm2> <ntceSpecDocNm3></ntceSpecDocNm3> <ntceSpecDocNm4></ntceSpecDocNm4> <ntceSpecDocNm5></ntceSpecDocNm5> <ntceSpecDocNm6></ntceSpecDocNm6> <ntceSpecDocNm7></ntceSpecDocNm7> <ntceSpecDocNm8></ntceSpecDocNm8> <ntceSpecDocNm9></ntceSpecDocNm9> <ntceSpecDocNm10></ntceSpecDocNm10> <aptHsmpNm></aptHsmpNm> <aptCeoAdrs></aptCeoAdrs> <aptMngOfficeTelNo></aptMngOfficeTelNo> <aptHmpgUrl></aptHmpgUrl> <aptTotar></aptTotar> <aptMngcstLevyArea></aptMngcstLevyArea> <aptHshldNum></aptHshldNum> <aptCmplNum></aptCmplNum> <aptHeatMethdNm></aptHeatMethdNm> <aptSubactLrgeNdCmpnintFclty></aptSubactLrgeNdCmpnintFclty> <rgstDt>2025-01-22 08:26:20</rgstDt> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[민간입찰공고정보에 대한 물품조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 2 | 오퍼레이션명(국문) | 민간입찰공고정보에 대한 물품조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoThng |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일범위, 공고일범위, 개찰일범위, 입찰공고번호를 입력하여 입찰공고번호, 입찰공고차수, 입찰공고분류, 게시일시, 참조번호, 공고명, 공고구분명, 공고기관명, 입찰방식명, 계약방법명, 낙찰방법명, 재입찰구분명, 입찰자격명, 담당자명, 담당자전화번호, 담당자이메일, 입찰개시일시, 입찰마감일시, 개찰일시, 개찰장소, 입찰보증서접수마감일시, 현장설명일시, 현장설명장소, 부가가치세포함여부명, 기준금액사용여부, 기준금액공개여부, 기준금액, 배정예산금액, 기준금액설명, 지역제한구분명, 물품목록상세, 공고서URL, 아파트정보, 등록일시 등의 누리장터시스템에 등록된 물품 입찰공고 정보 조회 |  |  |
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
1:등록일시 , 2.공고게시일시, 3. 개찰일시, 4.입찰공고번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202509010000 | 검색하고자하는 조회시작일시 "YYYYMMDDHHMM"
(조회구분 1,2,3 선택시 필수) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202509302359 | 검색하고자하는 조회종료일시 "YYYYMMDDHHMM"
(조회구분 1,2,3 선택시 필수) |
| bidNtceNo | 입찰공고번호 | 40 | 0 | R25BK01041870 | 검색하고자 하는 입찰공고번호
(조회구분 '4' 선택시 필수) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총 개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | R25BK01041870 | 입찰공고번호 |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수 |
| bidNtceClsfc | 입찰공고분류 | 30 | 1 | 물품 | 누리장터 입찰공고의 공고분류 (민간물품, 민간공사, 민간기술용역,민간일반용역, 민간기타) |
| nticeDt | 게시일시 | 19 | 1 | 2025-09-04 10:16:25 | 입찰공고 게시일시 "YYYY-MM-DD HH:MM:SS" |
| refNo | 참조번호 | 105 | 0 | 제2025-09-02호 | 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호 |
| ntceNm | 공고명 | 2000 | 0 | 근생용지B 매각 입찰공고 | 누리장터 입찰공고의 공고명 |
| ntceDivNm | 공고구분명 | 2000 | 0 | 등록공고 | 누리장터 입찰공고의 공고구분명[등록공고,재공고,긴급공고,변경공고,취소공고]등으로 표기 |
| ntceInsttNm | 공고기관명 | 200 | 1 | 연지2구역주택재개발정비사업조합 | 누리장터 입찰공고의 공고기관명으로 민간발주자기관명 |
| bidMethdNm | 입찰방식명 | 200 | 1 | 전자입찰 | 누리장터 입찰공고의 입찰방식명[수기입찰, 전자입찰, 역경매] |
| cntrctMthdNm | 계약방법명 | 200 | 1 | 일반경쟁 | 누리장터 입찰공고의 계약방식명 [일반경쟁, 제한경쟁, 지명경쟁] |
| sucsfbidMthdNm | 낙찰방법명 | 200 | 1 | 최고가 | 누리장터 입찰공고의 낙찰방식명 [최저가, 최고가, 일적격심사최저가, 적격심사최고가] |
| rbidDivNm | 재입찰구분명 | 20 | 0 | 재입찰 허용 | 누리장터 입찰공고의 재입찰 허용여부명 |
| bidQlfctNm | 입찰자격명 | 20 | 0 | 공고서 참조 | “공고서 참조”로 고정되어 있음 |
| ofclNm | 담당자명 | 35 | 0 | 김정민 | 누리장터 입찰공고의 담당자명 |
| ofclTelNo | 담당자전화번호 | 25 | 0 | *********** | 누리장터 입찰공고의 담당자 전화번호 |
| ofclEmail | 담당자이메일 | 100 | 0 | yeonji2.business@gmail.com | 누리장터 입찰공고의 담당자 메일주소 |
| bidBeginDt | 입찰개시일시 | 19 | 0 | 2025-09-04 10:30:00 | 누리장터 입찰공고의 입찰개시일시 "YYYY-MM-DD HH:MM:SS" |
| bidClseDt | 입찰마감일시 | 19 | 0 | 2025-09-12 15:30:00 | 누리장터 입찰공고의 입찰마감일시 "YYYY-MM-DD HH:MM:SS" |
| opengDt | 개찰일시 | 19 | 0 | 2025-09-12 16:30:00 | 누리장터 입찰공고의 개찰(입찰)일시 "YYYY-MM-DD HH:MM:SS" |
| opengPlce | 개찰장소 | 100 | 0 | 조합사무실 | 누리장터 입찰공고의 개찰장소 |
| bidWgrnteeRcptClseDt | 입찰보증서접수마감일시 | 19 | 0 | 2025-09-12 15:30:00 | 누리장터 입찰공고의 접수마감일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptDt | 현장설명일시 | 19 | 0 | 2025-09-12 15:30:00 | 누리장터 입찰공고의 현장설명일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptPlce | 현장설명장소 | 100 | 0 | 조달청나라장터 | 누리장터 입찰공고의 현장설명장소 |
| vatInclsnYnNm | 부가가치세포함여부명 | 200 | 0 | 부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다. | 누리장터 입찰공고의 부가가치세여부명 |
| refAmtUseYn | 기준금액사용여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격사용여부[Y,N] |
| refAmtOpenYn | 기준금액공개여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격공개여부[Y,N] |
| refAmt | 기준금액 | 25 | 0 | 6521014500 | 투찰의 상한금액(원화,원) |
| asignBdgtAmt | 배정예산금액 | 25 | 0 | 0 | 누리장터 입찰공고의 배정예산금액(원화,원) |
| refAmtDscrpt | 기준금액설명 | 200 | 0 | 기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다. | 고정된 안내 문구 |
| rgnLmtDivNm | 지역제한구분명 | 100 | 0 | 공고서 참조 | 누리장터 입찰공고의 투찰제한내역으로 투찰제한이나 공고서 참조로 표기 |
| dtchacOpenDt | 역경매공개일시 | 19 | 0 | 2025-09-12 15:30:00 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 공개일시 “YYYY-MM-DD HH:MM:SS” |
| dtchacBgnPrce | 역경매시작가격 | 25 | 0 | 10000000 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 시작가격을 보여줌 |
| dtchacRmrk | 역경매비고 | 1000 | 0 | 기초금액은 부가가치세 포함금액임 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 비고내용 |
| bssAmtDtlScrnUrl | 기초금액상세화면URL | 500 | 0 | N/A | 낙찰방법이 복수견적(역경매)일 경우 기초금액상세화면URL( 25년도 나라장터 차세대 이후 제공 불가) |
| dtchacMinRdctnRt | 역경매최소인하비율 | 25 | 0 | 0.2 | 낙찰방법이 복수견적(역경매)일 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 최소인하비율을 보여줌 |
| dtchacBidprcLmtNum | 역경매투찰제한횟수 | 100 | 0 | 제한없음 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 투찰제한횟수을 보여줌(회) 투찰제한회수가 0일 경우는 “제한없음” |
| dtchacAutoEtTm | 역경매자동연장시각 | 20 | 0 | 10. | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 자동연장시간(분) 자동연장시각:입찰마감일시 10분 전에 투찰이 이루어질 경우 입찰서마감일시를 해당 분만큼 자동연장합니다.
자동연장시각이 0 분 일 경우,입찰서마감일시 연장이 없는 경우입니다.
자동연장기능은 개찰일시 30분이전까지만 유효합니다.즉, 입찰서마감일시의 자동연장은 개찰일시 30분 전까지만 최대 연장됩니다 |
| dtchacBeforeBidDocClseDt | 역경매이전입찰서마감일시 | 19 | 0 | 2025-09-12 15:30:00 | 낙찰방법이 복수견적(역경매)일 경우 이전입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| dtchacEtBidDocClseDt | 역경매연장입찰문서마감일시 | 19 | 0 | 2025-09-12 15:30:00 | 낙찰방법이 복수견적(역경매)일 경우 연장입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| prdctDtlList | 물품상세목록 | 4000 | 0..n | [1^근생용지B(연지동 431 대 919.1㎡)^1^2025-09-18^조합사무실^기타사항참조] | 물품상세목록
[순번^물품분류명(품명)^수량^납품기한일자^납품장소명^인도조건], [순번^물품분류명(품명)^수량^납품기한일자^납품장소명^인도조건] |
| ntceSpecDocUrl1 | 공고규격서URL1 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=1&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl2 | 공고규격서URL2 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=2&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl3 | 공고규격서URL3 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=3&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl4 | 공고규격서URL4 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=4&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl5 | 공고규격서URL5 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=5&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl6 | 공고규격서URL6 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=6&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl7 | 공고규격서URL7 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=7&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl8 | 공고규격서URL8 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=8&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl9 | 공고규격서URL9 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=9&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl10 | 공고규격서URL10 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=10&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocNm1 | 공고규격서명1 | 256 | 0 | 연지2구역 근생용지B_매각입찰_공고(20250904).hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm2 | 공고규격서명2 | 256 | 0 | 연지2구역 근생용지B_매각입찰_서식.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm3 | 공고규격서명3 | 256 | 0 | 대한1.jpg | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm4 | 공고규격서명4 | 256 | 0 | 대한2.jp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm5 | 공고규격서명5 | 256 | 0 | 대한3.jpg | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm6 | 공고규격서명6 | 256 | 0 | 제일1.jpg | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm7 | 공고규격서명7 | 256 | 0 | 제일2.jpg | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm8 | 공고규격서명8 | 256 | 0 | 연지2구역 근생용지B_매각입찰_서식.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm9 | 공고규격서명9 | 256 | 0 | 연지2구역 근생용지B_매각입찰_서식.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm10 | 공고규격서명10 | 256 | 0 | 연지2구역 근생용지B_매각입찰_서식.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| aptHsmpNm | 아파트단지명 | 100 | 0 | 유천포스코 the#입주자대표회의 | 누리장터 입찰공고 공고기관 아파트 민간발주기관명 (누리장터 화면에는 단지명) |
| aptCeoAdrs | 아파트대표자주소 | 400 | 0 | 대구광역시 달서구 달서대로67-0 (유천동, 유천동 포스코 더#아파트) | 누리장터 입찰공고 공고기관 아파트 주소 |
| aptMngOfficeTelNo | 아파트관리사무소전화번호 | 25 | 0 | 053-641-8100 | 누리장터 입찰공고 공고기관 아파트 연락처 |
| aptHmpgUrl | 아파트홈페이지URL | 100 | 0 | www.kukdong2.co.kr | 누리장터 입찰공고 공고기관 아파트 홈페이지URL |
| aptTotar | 아파트연면적 | 100 | 0 | 116967 | 누리장터 입찰공고 공고기관 아파트 연면적(㎡) |
| aptMngcstLevyArea | 아파트관리비부과면적 | 100 | 0 | 87341.72 | 누리장터 입찰공고 공고기관 아파트 관리비부과면적 (㎡) |
| aptHshldNum | 아파트세대수 | 10 | 0 | 764 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptCmplNum | 아파트동수 | 10 | 0 | 10 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptHeatMethdNm | 아파트난방방식명 | 100 | 0 | 개별 | 누리장터 입찰공고 공고기관 아파트 난방방식 |
| aptSubactLrgeNdCmpnintFclty | 아파트부대및복리시설 | 4000 | 0 | 관리동, 노인정, 휘트니스센터, 경비초소 | 누리장터 입찰공고 공고기관 아파트 부대 및 복리시설 등 |
| rgstDt | 등록일시 | 19 | 1 | 2025-09-04 10:16:25 | 공고의 등록일시 “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoThng?inqryDiv=4&bidNtceNo=R25BK01041870&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>R25BK01041870</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <bidNtceClsfc>민간물품</bidNtceClsfc> <nticeDt>2025-09-04 10:16:25</nticeDt> <refNo>제2025-09-02호</refNo> <ntceNm>근생용지B 매각 입찰공고</ntceNm> <ntceDivNm>등록공고</ntceDivNm> <ntceInsttNm>연지2구역주택재개발정비사업조합</ntceInsttNm> <bidMethdNm>전자입찰</bidMethdNm> <cntrctMthdNm>일반경쟁</cntrctMthdNm> <sucsfbidMthdNm>최고가</sucsfbidMthdNm> <rbidDivNm>재입찰 허용</rbidDivNm> <bidQlfctNm>공고서 참조</bidQlfctNm> <ofclNm>김정민</ofclNm> <ofclTelNo>***********</ofclTelNo> <ofclEmail>yeonji2.business@gmail.com</ofclEmail> <bidBeginDt>2025-09-04 10:30:00</bidBeginDt> <bidClseDt>2025-09-12 15:30:00</bidClseDt> <opengDt>2025-09-12 16:30:00</opengDt> <opengPlce>조합사무실</opengPlce> <bidWgrnteeRcptClseDt></bidWgrnteeRcptClseDt> <sptDscrptDt></sptDscrptDt> <sptDscrptPlce></sptDscrptPlce> <vatInclsnYnNm>부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다.</vatInclsnYnNm> <refAmtUseYn>Y</refAmtUseYn> <refAmtOpenYn>Y</refAmtOpenYn> <refAmt>6521014500</refAmt> <asignBdgtAmt>0</asignBdgtAmt> <refAmtDscrpt>기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다.</refAmtDscrpt> <rgnLmtDivNm>공고서 참조</rgnLmtDivNm> <dtchacOpenDt></dtchacOpenDt> <dtchacBgnPrce></dtchacBgnPrce> <dtchacRmrk></dtchacRmrk> <bssAmtDtlScrnUrl></bssAmtDtlScrnUrl> <dtchacMinRdctnRt></dtchacMinRdctnRt> <dtchacBidprcLmtNum></dtchacBidprcLmtNum> <dtchacAutoEtTm></dtchacAutoEtTm> <dtchacBeforeBidDocClseDt></dtchacBeforeBidDocClseDt> <dtchacEtBidDocClseDt></dtchacEtBidDocClseDt> <prdctDtlList>[1^근생용지B(연지동 431 대 919.1㎡)^1^2025-09-18^조합사무실^기타사항참조]</prdctDtlList> <ntceSpecDocUrl1>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=1&amp;prcmBsneSeCd=21</ntceSpecDocUrl1> <ntceSpecDocUrl2>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=2&amp;prcmBsneSeCd=21</ntceSpecDocUrl2> <ntceSpecDocUrl3>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=3&amp;prcmBsneSeCd=21</ntceSpecDocUrl3> <ntceSpecDocUrl4>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=4&amp;prcmBsneSeCd=21</ntceSpecDocUrl4> <ntceSpecDocUrl5>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=5&amp;prcmBsneSeCd=21</ntceSpecDocUrl5> <ntceSpecDocUrl6>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=6&amp;prcmBsneSeCd=21</ntceSpecDocUrl6> <ntceSpecDocUrl7>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=7&amp;prcmBsneSeCd=21</ntceSpecDocUrl7> <ntceSpecDocUrl8></ntceSpecDocUrl8> <ntceSpecDocUrl9></ntceSpecDocUrl9> <ntceSpecDocUrl10></ntceSpecDocUrl10> <ntceSpecDocNm1>연지2구역 근생용지B_매각입찰_공고(20250904).hwp</ntceSpecDocNm1> <ntceSpecDocNm2>연지2구역 근생용지B_매각입찰_서식.hwp</ntceSpecDocNm2> <ntceSpecDocNm3>대한1.jpg</ntceSpecDocNm3> <ntceSpecDocNm4>대한2.jpg</ntceSpecDocNm4> <ntceSpecDocNm5>대한3.jpg</ntceSpecDocNm5> <ntceSpecDocNm6>제일1.jpg</ntceSpecDocNm6> <ntceSpecDocNm7>제일2.jpg</ntceSpecDocNm7> <ntceSpecDocNm8></ntceSpecDocNm8> <ntceSpecDocNm9></ntceSpecDocNm9> <ntceSpecDocNm10></ntceSpecDocNm10> <aptHsmpNm></aptHsmpNm> <aptCeoAdrs></aptCeoAdrs> <aptMngOfficeTelNo></aptMngOfficeTelNo> <aptHmpgUrl></aptHmpgUrl> <aptTotar></aptTotar> <aptMngcstLevyArea></aptMngcstLevyArea> <aptHshldNum></aptHshldNum> <aptCmplNum></aptCmplNum> <aptHeatMethdNm></aptHeatMethdNm> <aptSubactLrgeNdCmpnintFclty></aptSubactLrgeNdCmpnintFclty> <rgstDt>2025-09-04 10:16:25</rgstDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[민간입찰공고정보에 대한 공사조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 3 | 오퍼레이션명(국문) | 민간입찰공고정보에 대한 공사조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoCnstwk |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일범위, 공고일범위, 개찰일범위, 입찰공고번호를 입력하여 입찰공고번호, 입찰공고차수, 입찰공고분류, 게시일시, 참조번호, 공고명, 공고구분명, 공고기관명, 입찰방식명, 계약방법명, 낙찰방법명, 재입찰구분명, 입찰자격명, 담당자명, 담당자전화번호, 담당자이메일, 입찰개시일시, 입찰마감일시, 개찰일시, 개찰장소, 입찰보증서접수마감일시, 현장설명일시, 현장설명장소, 부가가치세포함여부명, 기준금액사용여부, 기준금액공개여부, 기준금액, 배정예산금액, 기준금액설명, 지역제한구분명, 공사상세목록, 공고서URL, 아파트정보, 등록일시 등의 누리장터시스템에 등록된 공사 입찰공고 정보 조회 |  |  |
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
1:등록일시 , 2.공고게시일시, 3. 개찰일시, 4.입찰공고번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 검색하고자하는 조회시작일시 "YYYYMMDDHHMM"
(조회구분 1,2,3 선택시 필수) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 검색하고자하는 조회종료일시 "YYYYMMDDHHMM"
(조회구분 1,2,3 선택시 필수) |
| bidNtceNo | 입찰공고번호 | 40 | 0 | R25BK01195344 | 검색하고자 하는 입찰공고번호
(조회구분 '4' 선택시 필수) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | R25BK01195344 | 입찰공고번호 |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수 |
| bidNtceClsfc | 입찰공고분류 | 30 | 1 | 민간공사 | 누리장터 입찰공고의 공고분류 (민간물품, 민간공사, 민간기술용역,민간일반용역, 민간기타)) |
| nticeDt | 게시일시 | 19 | 1 | 2025-12-02 10:50:26 | 입찰공고 게시일시 "YYYY-MM-DD HH:MM:SS" |
| refNo | 참조번호 | 105 | 0 | 202512020 | 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호 |
| ntceNm | 공고명 | 2000 | 0 | 2025년지역특화품 비닐하우스 지원사업 | 누리장터 입찰공고의 공고명 |
| ntceDivNm | 공고구분명 | 2000 | 0 | 등록공고 | 누리장터 입찰공고의 공고구분명[등록공고,재공고,긴급공고,변경공고,취소공고]등으로 표기 |
| ntceInsttNm | 공고기관명 | 200 | 1 | 가연농장 | 누리장터 입찰공고의 공고기관명으로 민간발주자기관명 |
| bidMethdNm | 입찰방식명 | 200 | 1 | 전자입찰 | 누리장터 입찰공고의 입찰방식명[수기입찰, 전자입찰, 역경매] |
| cntrctMthdNm | 계약방법명 | 200 | 1 | 제한경쟁 | 누리장터 입찰공고의 계약방식명 [일반경쟁, 제한경쟁, 지명경쟁] |
| sucsfbidMthdNm | 낙찰방법명 | 200 | 1 | 최저가낙찰제 | 누리장터 입찰공고의 낙찰방식명 [최저가, 최고가, 일적격심사최저가, 적격심사최고가] |
| rbidDivNm | 재입찰구분명 | 20 | 0 | 재입찰 허용 | 누리장터 입찰공고의 재입찰 허용여부명 |
| bidQlfctNm | 입찰자격명 | 20 | 0 | 공고규격서 참조 | “공고서 참조”로 고정되어 있음 |
| ofclNm | 담당자명 | 35 | 0 | 황창희 | 누리장터 입찰공고의 담당자명 |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 031-273-0764 | 누리장터 입찰공고의 담당자 전화번호 |
| ofclEmail | 담당자이메일 | 100 | 0 | ostech1@naver.com | 누리장터 입찰공고의 담당자 메일주소 |
| bidBeginDt | 입찰개시일시 | 19 | 0 | 2025-12-02 15:00:00 | 누리장터 입찰공고의 입찰개시일시 "YYYY-MM-DD HH:MM:SS" |
| bidClseDt | 입찰마감일시 | 19 | 0 | 2025-12-08 09:00:00 | 누리장터 입찰공고의 입찰마감일시 "YYYY-MM-DD HH:MM:SS" |
| opengDt | 개찰일시 | 19 | 0 | 2025-12-08 10:00:00 | 누리장터 입찰공고의 개찰(입찰)일시 "YYYY-MM-DD HH:MM:SS" |
| opengPlce | 개찰장소 | 100 | 0 | 진안군 진안읍 선인길 21-19 | 누리장터 입찰공고의 개찰장소 |
| bidWgrnteeRcptClseDt | 입찰보증서접수마감일시 | 19 | 0 | 2025-12-08 09:00:00 | 누리장터 입찰공고의 접수마감일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptDt | 현장설명일시 | 19 | 0 | 2025-12-04 09:30:0 | 누리장터 입찰공고의 현장설명일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptPlce | 현장설명장소 | 100 | 0 | 진안군 진안읍 선인길 21-19 | 누리장터 입찰공고의 현장설명장소 |
| vatInclsnYnNm | 부가가치세포함여부명 | 200 | 0 | 부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다.) | 누리장터 입찰공고의 부가가치세여부명 |
| refAmtUseYn | 기준금액사용여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격사용여부[Y,N] |
| refAmtOpenYn | 기준금액공개여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격공개여부[Y,N] |
| refAmt | 기준금액 | 25 | 0 | 32670000 | 투찰의 상한금액 (원화,원) |
| asignBdgtAmt | 배정예산금액 | 25 | 0 | 32670000 | 누리장터 입찰공고의 배정예산금액 (원화,원) |
| refAmtDscrpt | 기준금액설명 | 200 | 0 | 기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다. | 고정된 안내 문구 |
| rgnLmtDivNm | 지역제한구분명 | 100 | 0 | 투찰제한 | 누리장터 입찰공고의 투찰제한내역으로 투찰제한이나 공고서 참조로 표기 |
| dtchacOpenDt | 역경매공개일시 | 19 | 0 | 2025-12-04 09:30:0 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 공개일시 “YYYY-MM-DD HH:MM:SS” |
| dtchacBgnPrce | 역경매시작가격 | 25 | 0 | 100 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 시작가격을 보여줌 |
| dtchacRmrk | 역경매비고 | 1000 | 0 | 기초금액은 부가가치세 포함금액임 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 비고내용 |
| bssAmtDtlScrnUrl | 기초금액상세화면URL | 500 | 0 | N/A | 낙찰방법이 복수견적(역경매)일 경우 기초금액상세화면URL( 25년도 나라장터 차세대 이후 제공 불가) |
| dtchacMinRdctnRt | 역경매최소인하비율 | 25 | 0 | 0.2 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 최소인하비율을 보여줌 |
| dtchacBidprcLmtNum | 역경매투찰제한횟수 | 100 | 0 | 2 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 투찰제한횟수을 보여줌(회) 투찰제한회수가 0일 경우는 “제한없음” |
| dtchacAutoEtTm | 역경매자동연장시각 | 20 | 0 | 0. | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 자동연장시간(분) 자동연장시각:입찰마감일시 10분 전에 투찰이 이루어질 경우 입찰서마감일시를 해당 분만큼 자동연장합니다.
자동연장시각이 0 분 일 경우,입찰서마감일시 연장이 없는 경우입니다.
자동연장기능은 개찰일시 30분이전까지만 유효합니다.즉, 입찰서마감일시의 자동연장은 개찰일시 30분 전까지만 최대 연장됩니다 |
| dtchacBeforeBidDocClseDt | 역경매이전입찰서마감일시 | 19 | 0 | 2016-07-19 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 이전입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| dtchacEtBidDocClseDt | 역경매연장입찰문서마감일시 | 19 | 0 | 2016-07-19 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 연장입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| cnstwkDtlList | 공사상세목록 | 4000 | 0..n | [1^2025년지역특화품 비닐하우스 지원사업^2025년지역특화품 비닐하우스 지원사업^2025-12-30] | 공사상세목록
[순번^공사명^공사현장명^준공기한일자], [순번^공사명^공사현장명^준공기한일자] |
| ntceSpecDocUrl1 | 공고규격서URL1 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=1&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl2 | 공고규격서URL2 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=2&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl3 | 공고규격서URL3 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=3&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl4 | 공고규격서URL4 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=4&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl5 | 공고규격서URL5 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=5&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl6 | 공고규격서URL6 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=6&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl7 | 공고규격서URL7 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=7&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl8 | 공고규격서URL8 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=8&pmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl9 | 공고규격서URL9 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=9&pcBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl10 | 공고규격서URL10 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=10&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocNm1 | 공고규격서명1 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm2 | 공고규격서명2 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm3 | 공고규격서명3 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm4 | 공고규격서명4 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm5 | 공고규격서명5 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm6 | 공고규격서명6 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm7 | 공고규격서명7 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm8 | 공고규격서명8 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm9 | 공고규격서명9 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm10 | 공고규격서명10 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| aptHsmpNm | 아파트단지명 | 100 | 0 | 동수원자이1차아파트입주자대표회의 | 누리장터 입찰공고 공고기관 아파트 민간발주기관명 (누리장터 화면에는 단지명) |
| aptCeoAdrs | 아파트대표자주소 | 400 | 0 | 경기도 수원시 영통구 태장로82번길32-0 (망포동, 망포마을 동수원 엘지빌리지) | 누리장터 입찰공고 공고기관 아파트 주소 |
| aptMngOfficeTelNo | 아파트관리사무소전화번호 | 25 | 0 | 031-273-0764 | 누리장터 입찰공고 공고기관 아파트 연락처 |
| aptHmpgUrl | 아파트홈페이지URL | 100 | 0 | www.ezville.net | 누리장터 입찰공고 공고기관 아파트 홈페이지URL |
| aptTotar | 아파트연면적 | 100 | 0 | 309301 | 누리장터 입찰공고 공고기관 아파트 연면적(㎡) |
| aptMngcstLevyArea | 아파트관리비부과면적 | 100 | 0 | 246803 | 누리장터 입찰공고 공고기관 아파트 관리비부과면적 (㎡) |
| aptHshldNum | 아파트세대수 | 10 | 0 | 1829 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptCmplNum | 아파트동수 | 10 | 0 | 14 | 누리장터 입찰공고 공고기관 아파트 동수 |
| aptHeatMethdNm | 아파트난방방식 | 100 | 0 | 지역 | 누리장터 입찰공고 공고기관 아파트 난방방식 |
| aptSubactLrgeNdCmpnintFclty | 아파트부대및복리시설 | 4000 | 0 | 관리사무소,노인정,문고,보육시설,지하저수시설,어린이놀이터,휴게시설,주민운동시설등 | 누리장터 입찰공고 공고기관 아파트 부대 및 복리시설 등 |
| rgstDt | 등록일시 | 19 | 1 | 2025-12-02 10:50:26 | 공고의 등록일시 “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoCnstwk?inqryDiv=4&bidNtceNo=R25BK01195344&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>R25BK01195344</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <bidNtceClsfc>민간공사</bidNtceClsfc> <nticeDt>2025-12-02 10:50:26</nticeDt> <refNo>202512020</refNo> <ntceNm>2025년지역특화품 비닐하우스 지원사업</ntceNm> <ntceDivNm>등록공고</ntceDivNm> <ntceInsttNm>가연농장</ntceInsttNm> <bidMethdNm>전자입찰</bidMethdNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <sucsfbidMthdNm>최저가낙찰제</sucsfbidMthdNm> <rbidDivNm>재입찰 허용</rbidDivNm> <bidQlfctNm>공고서 참조</bidQlfctNm> <ofclNm>황창희</ofclNm> <ofclTelNo></ofclTelNo> <ofclEmail>ostech1@naver.com</ofclEmail> <bidBeginDt>2025-12-02 15:00:00</bidBeginDt> <bidClseDt>2025-12-08 09:00:00</bidClseDt> <opengDt>2025-12-08 10:00:00</opengDt> <opengPlce>진안군 진안읍 선인길 21-19</opengPlce> <bidWgrnteeRcptClseDt></bidWgrnteeRcptClseDt> <sptDscrptDt>2025-12-04 09:30:00</sptDscrptDt> <sptDscrptPlce>진안군 진안읍 선인길 21-19</sptDscrptPlce> <vatInclsnYnNm>부가세 포함(입찰가격은 부가가치세를 포함한 금액을 제출하셔야 합니다.</vatInclsnYnNm> <refAmtUseYn>Y</refAmtUseYn> <refAmtOpenYn>Y</refAmtOpenYn> <refAmt>32670000</refAmt> <asignBdgtAmt>32670000</asignBdgtAmt> <refAmtDscrpt>기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다.</refAmtDscrpt> <rgnLmtDivNm>투찰제한</rgnLmtDivNm> <dtchacOpenDt></dtchacOpenDt> <dtchacBgnPrce></dtchacBgnPrce> <dtchacRmrk></dtchacRmrk> <bssAmtDtlScrnUrl></bssAmtDtlScrnUrl> <dtchacMinRdctnRt></dtchacMinRdctnRt> <dtchacBidprcLmtNum></dtchacBidprcLmtNum> <dtchacAutoEtTm></dtchacAutoEtTm> <dtchacBeforeBidDocClseDt></dtchacBeforeBidDocClseDt> <dtchacEtBidDocClseDt></dtchacEtBidDocClseDt> <cnstwkDtlList>[1^2025년지역특화품 비닐하우스 지원사업^2025년지역특화품 비닐하우스 지원사업^2025-12-30]</cnstwkDtlList> <ntceSpecDocUrl1>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=1&amp;prcmBsneSeCd=24</ntceSpecDocUrl1> <ntceSpecDocUrl2></ntceSpecDocUrl2> <ntceSpecDocUrl3></ntceSpecDocUrl3> <ntceSpecDocUrl4></ntceSpecDocUrl4> <ntceSpecDocUrl5></ntceSpecDocUrl5> <ntceSpecDocUrl6></ntceSpecDocUrl6> <ntceSpecDocUrl7></ntceSpecDocUrl7> <ntceSpecDocUrl8></ntceSpecDocUrl8> <ntceSpecDocUrl9></ntceSpecDocUrl9> <ntceSpecDocUrl10></ntceSpecDocUrl10> <ntceSpecDocNm1>지역특화품목_비닐하우스_지원사업 황 창 희.hwp</ntceSpecDocNm1> <ntceSpecDocNm2></ntceSpecDocNm2> <ntceSpecDocNm3></ntceSpecDocNm3> <ntceSpecDocNm4></ntceSpecDocNm4> <ntceSpecDocNm5></ntceSpecDocNm5> <ntceSpecDocNm6></ntceSpecDocNm6> <ntceSpecDocNm7></ntceSpecDocNm7> <ntceSpecDocNm8></ntceSpecDocNm8> <ntceSpecDocNm9></ntceSpecDocNm9> <ntceSpecDocNm10></ntceSpecDocNm10> <aptHsmpNm></aptHsmpNm> <aptCeoAdrs></aptCeoAdrs> <aptMngOfficeTelNo></aptMngOfficeTelNo> <aptHmpgUrl></aptHmpgUrl> <aptTotar></aptTotar> <aptMngcstLevyArea></aptMngcstLevyArea> <aptHshldNum></aptHshldNum> <aptCmplNum></aptCmplNum> <aptHeatMethdNm></aptHeatMethdNm> <aptSubactLrgeNdCmpnintFclty></aptSubactLrgeNdCmpnintFclty> <rgstDt>2025-12-02 10:50:26</rgstDt> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[민간입찰공고정보에 대한 기타조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 4 | 오퍼레이션명(국문) | 민간입찰공고정보에 대한 기타조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoEtc |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 등록일범위, 공고일범위, 개찰일범위, 입찰공고번호를 입력하여 입찰공고번호, 입찰공고차수, 입찰공고분류, 게시일시, 참조번호, 공고명, 공고구분명, 공고기관명, 입찰방식명, 계약방법명, 낙찰방법명, 재입찰구분명, 입찰자격명, 담당자명, 담당자전화번호, 담당자이메일, 입찰개시일시, 입찰마감일시, 개찰일시, 개찰장소, 입찰보증서접수마감일시, 현장설명일시, 현장설명장소, 부가가치세포함여부명, 기준금액사용여부, 기준금액공개여부, 기준금액, 배정예산금액, 기준금액설명, 지역제한구분명, 공고서URL, 아파트정보, 등록일시 등의 누리장터시스템에 등록된 기타 입찰공고 정보 조회 |  |  |
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
1:등록일시 , 2.공고게시일시, 3. 개찰일시, 4.입찰공고번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 201605010000 | 검색하고자하는 조회시작일시 "YYYYMMDDHHMM"
(조회구분 1,2,3 선택시 필수) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 201605052359 | 검색하고자하는 조회종료일시 "YYYYMMDDHHMM"
(조회구분 1,2,3 선택시 필수) |
| bidNtceNo | 입찰공고번호 | 40 | 0 | 20160430911 | 검색하고자 하는 입찰공고번호
(조회구분 '4' 선택시 필수) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | 20150517791 | 입찰공고번호 |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수 |
| bidNtceClsfc | 입찰공고분류 | 30 | 1 | 기타 | 누리장터 입찰공고의 공고분류 (민간물품, 민간공사, 민간기술용역,민간일반용역, 민간기타) |
| nticeDt | 게시일시 | 19 | 1 | 2015-05-18 14:06:04 | 입찰공고 게시일시 "YYYY-MM-DD HH:MM:SS" |
| refNo | 참조번호 | 105 | 0 | M001693 | 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호 |
| ntceNm | 공고명 | 2000 | 0 | 재활용품 수거업체 선정 입찰공고 | 누리장터 입찰공고의 공고명 |
| ntceDivNm | 공고구분명 | 2000 | 0 | 재공고 | 누리장터 입찰공고의 공고구분명[등록공고,재공고,긴급공고,변경공고,취소공고]등으로 표기 |
| ntceInsttNm | 공고기관명 | 200 | 1 | 삼부3단지아파트입주자대표회의 | 누리장터 입찰공고의 공고기관명으로 민간발주자기관명 |
| bidMethdNm | 입찰방식명 | 200 | 1 | 전자입찰 | 누리장터 입찰공고의 입찰방식명[수기입찰, 전자입찰, 역경매] |
| cntrctMthdNm | 계약방법명 | 200 | 1 | 일반경쟁 | 누리장터 입찰공고의 계약방식명 [일반경쟁, 제한경쟁, 지명경쟁] |
| sucsfbidMthdNm | 낙찰방법명 | 200 | 1 | 최고가 | 누리장터 입찰공고의 낙찰방식명 [최저가, 최고가, 일적격심사최저가, 적격심사최고가] |
| rbidDivNm | 재입찰구분명 | 20 | 0 | 재입찰 허용 | 누리장터 입찰공고의 재입찰 허용여부명 |
| bidQlfctNm | 입찰자격명 | 20 | 0 | 공고규격서 참조 | “공고서 참조”로 고정되어 있음 |
| ofclNm | 담당자명 | 35 | 0 | 김영배 | 누리장터 입찰공고의 담당자명 |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 042-523-9042 | 누리장터 입찰공고의 담당자 전화번호 |
| ofclEmail | 담당자이메일 | 100 | 0 | kyb7042@hanmail.net | 누리장터 입찰공고의 담당자 메일주소 |
| bidBeginDt | 입찰개시일시 | 19 | 0 | 2015-05-19 09:00:00 | 누리장터 입찰공고의 입찰개시일시 "YYYY-MM-DD HH:MM:SS" |
| bidClseDt | 입찰마감일시 | 19 | 0 | 2015-05-26 15:00:00 | 누리장터 입찰공고의 입찰마감일시 "YYYY-MM-DD HH:MM:SS" |
| opengDt | 개찰일시 | 19 | 0 | 2015-05-26 16:00:00 | 누리장터 입찰공고의 개찰(입찰)일시 "YYYY-MM-DD HH:MM:SS" |
| opengPlce | 개찰장소 | 100 | 0 | 발주처 입주자회의실 | 누리장터 입찰공고의 개찰장소 |
| bidWgrnteeRcptClseDt | 입찰보증서접수마감일시 | 19 | 0 | 2015-05-26 15:00:00 | 누리장터 입찰공고의 접수마감일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptDt | 현장설명일시 | 19 | 0 | 2015-05-13 13:00:00 | 누리장터 입찰공고의 현장설명일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptPlce | 현장설명장소 | 100 | 0 | 조달청나라장터 | 누리장터 입찰공고의 현장설명장소 |
| vatInclsnYnNm | 부가가치세포함여부명 | 200 | 0 | 부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다.) | 누리장터 입찰공고의 부가가치세여부명 |
| refAmtUseYn | 기준금액사용여부 | 20 | 0 | N | 누리장터 입찰공고의 기준가격사용여부[Y,N] |
| refAmtOpenYn | 기준금액공개여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격공개여부[Y,N] |
| refAmt | 기준금액 | 25 | 0 | 30000000 | 투찰의 상한금액 (원화,원) |
| asignBdgtAmt | 배정예산금액 | 25 | 0 | 65522000 | 누리장터 입찰공고의 배정예산금액 |
| refAmtDscrpt | 기준금액설명 | 200 | 0 | 기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다. | 고정된 안내 문구 |
| rgnLmtDivNm | 지역제한구분명 | 100 | 0 | 공고규격서 참조 | 누리장터 입찰공고의 투찰제한내역 |
| dtchacOpenDt | 역경매공개일시 | 19 | 0 | 2016-07-19 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 공개일시 “YYYY-MM-DD HH:MM:SS” |
| dtchacBgnPrce | 역경매시작가격 | 25 | 0 | 0 | 낙찰방법이 복수견적(역경매)일 경우누리장터 입찰공고의 시작가격을 보여줌 |
| dtchacRmrk | 역경매비고 | 1000 | 0 | 기초금액은 부가가치세 포함금액임 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 비고내용 |
| bssAmtDtlScrnUrl | 기초금액상세화면URL | 500 | 0 | N/A | 낙찰방법이 복수견적(역경매)일 경우 기초금액상세화면URL( 25년도 나라장터 차세대 이후 제공 불가) |
| dtchacMinRdctnRt | 역경매최소인하비율 | 25 | 0 | 0 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 최소인하비율을 보여줌 |
| dtchacBidprcLmtNum | 역경매투찰제한횟수 | 100 | 0 | 0 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 투찰제한횟수을 보여줌(회) 투찰제한회수가 0일 경우는 “제한없음” |
| dtchacAutoEtTm | 역경매자동연장시각 | 20 | 0 | 10. | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 자동연장시간(분) 자동연장시각:입찰마감일시 10분 전에 투찰이 이루어질 경우 입찰서마감일시를 해당 분만큼 자동연장합니다.
자동연장시각이 0 분 일 경우,입찰서마감일시 연장이 없는 경우입니다.
자동연장기능은 개찰일시 30분이전까지만 유효합니다.즉, 입찰서마감일시의 자동연장은 개찰일시 30분 전까지만 최대 연장됩니다 |
| dtchacBeforeBidDocClseDt | 역경매이전입찰서마감일시 | 19 | 0 | 2016-07-19 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 이전입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| dtchacEtBidDocClseDt | 역경매연장입찰문서마감일시 | 19 | 0 | 2016-07-19 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 연장입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| ntceSpecDocUrl1 | 공고규격서URL1 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl2 | 공고규격서URL2 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl3 | 공고규격서URL3 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl4 | 공고규격서URL4 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl5 | 공고규격서URL5 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl6 | 공고규격서URL6 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl7 | 공고규격서URL7 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl8 | 공고규격서URL8 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl9 | 공고규격서URL9 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl10 | 공고규격서URL10 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocNm1 | 공고규격서명1 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-1.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm2 | 공고규격서명2 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-2.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm3 | 공고규격서명3 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-3.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm4 | 공고규격서명4 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-4.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm5 | 공고규격서명5 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-5.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm6 | 공고규격서명6 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-6.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm7 | 공고규격서명7 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-7.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm8 | 공고규격서명8 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-8.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm9 | 공고규격서명9 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-9.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm10 | 공고규격서명10 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-10.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| aptHsmpNm | 아파트단지명 | 100 | 0 | 삼부3단지아파트입주자대표회의 | 누리장터 입찰공고 공고기관 아파트 민간발주기관명 (누리장터 화면에는 단지명) |
| aptCeoAdrs | 아파트대표자주소 | 400 | 0 | 대전광역시 중구 태평로65-0 (태평동, 삼부아파트) | 누리장터 입찰공고 공고기관 아파트 주소 |
| aptMngOfficeTelNo | 아파트관리사무소전화번호 | 25 | 0 | 042-523-9042 | 누리장터 입찰공고 공고기관 아파트 연락처 |
| aptHmpgUrl | 아파트홈페이지URL | 100 | 0 | www.kukdong2.co.kr | 누리장터 입찰공고 공고기관 아파트 홈페이지URL |
| aptTotar | 아파트연면적 | 100 | 0 | 63571 | 누리장터 입찰공고 공고기관 아파트 연면적(㎡) |
| aptMngcstLevyArea | 아파트관리비부과면적 | 100 | 0 | 63173 | 누리장터 입찰공고 공고기관 아파트 관리비부과면적 (㎡) |
| aptHshldNum | 아파트세대수 | 10 | 0 | 510 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptCmplNum | 아파트동수 | 10 | 0 | 8 | 누리장터 입찰공고 공고기관 아파트 동수 |
| aptHeatMethdNm | 아파트난방방식 | 100 | 0 | 중앙 | 누리장터 입찰공고 공고기관 아파트 난방방식 |
| aptSubactLrgeNdCmpnintFclty | 아파트부대및복리시설 | 4000 | 0 | 관리사무소,노인정,어린이놀이터,주차장,승강기 | 누리장터 입찰공고 공고기관 아파트 부대 및 복리시설 등 |
| rgstDt | 등록일시 | 19 | 1 | 2015-04-29 15:39:27 | 공고의 등록일시 “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoEtc?inqryDiv=1&inqryBgnDt=201606010000&inqryEndDt=201606052359&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>20160535028</bidNtceNo> <bidNtceOrd>00</bidNtceOrd> <bidNtceClsfc>기타</bidNtceClsfc> <nticeDt>2016-06-02 15:23:08</nticeDt> <refNo>KBSWOO-2016-0531</refNo> <ntceNm>재활용수거업체 선정공고</ntceNm> <ntceDivNm /> <ntceInsttNm>철산KBS우성아파트입주자대표회의</ntceInsttNm> <bidMethdNm>전자입찰</bidMethdNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <sucsfbidMthdNm>최고가</sucsfbidMthdNm> <rbidDivNm>재입찰 허용</rbidDivNm> <bidQlfctNm>공고서 참조</bidQlfctNm> <ofclNm>김동근</ofclNm> <ofclTelNo>02-2615-6449</ofclTelNo> <ofclEmail>woosung6449@nate.com</ofclEmail> <bidBeginDt>2016-06-07 14:00:00</bidBeginDt> <bidClseDt>2016-06-14 17:00:00</bidClseDt> <opengDt>2016-06-14 18:00:00</opengDt> <opengPlce>입주자대표회의실</opengPlce> <bidWgrnteeRcptClseDt /> <sptDscrptDt>2016-06-07 14:00:00</sptDscrptDt> <sptDscrptPlce>관리사무소</sptDscrptPlce> <vatInclsnYnNm>부가세 포함(입찰가격은 부가가치세를 포함한 금액을 제출하셔야 합니다.)</vatInclsnYnNm> <refAmtUseYn>N</refAmtUseYn> <refAmtOpenYn /> <refAmt /> <asignBdgtAmt /> <refAmtDscrpt /> <rgnLmtDivNm>공고서 참조</rgnLmtDivNm> <dtchacOpenDt /> <dtchacBgnPrce /> <dtchacRmrk /> <bssAmtDtlScrnUrl /> <dtchacMinRdctnRt /> <dtchacBidprcLmtNum /> <dtchacAutoEtTm /> <dtchacBeforeBidDocClseDt /> <dtchacEtBidDocClseDt /> <ntceSpecDocUrl1>https://www.g2b.go.kr:8081/ep/co/fileDownload.do?fileTask=NOTIFY&fileSeq=20160535028::00::1::1</ntceSpecDocUrl1> <ntceSpecDocUrl2 /> <ntceSpecDocUrl3 /> <ntceSpecDocUrl4 /> <ntceSpecDocUrl5 /> <ntceSpecDocUrl6 /> <ntceSpecDocUrl7 /> <ntceSpecDocUrl8 /> <ntceSpecDocUrl9 /> <ntceSpecDocUrl10 /> <ntceSpecDocNm1>20160535028-00_1464677675617_재활용수거업체 선정공고문.hwp</ntceSpecDocNm1> <ntceSpecDocNm2 /> <ntceSpecDocNm3 /> <ntceSpecDocNm4 /> <ntceSpecDocNm5 /> <ntceSpecDocNm6 /> <ntceSpecDocNm7 /> <ntceSpecDocNm8 /> <ntceSpecDocNm9 /> <ntceSpecDocNm10 /> <aptHsmpNm>철산KBS우성아파트입주자대표회의</aptHsmpNm> <aptCeoAdrs>경기도 광명시 시청로139-0 (철산동, 우성아파트)</aptCeoAdrs> <aptMngOfficeTelNo>02-2615-6449</aptMngOfficeTelNo> <aptHmpgUrl /> <aptTotar>88579.87</aptTotar> <aptMngcstLevyArea>85798.35</aptMngcstLevyArea> <aptHshldNum>900</aptHshldNum> <aptCmplNum>9</aptCmplNum> <aptHeatMethdNm>개별</aptHeatMethdNm> <aptSubactLrgeNdCmpnintFclty>경로당 어린이놀이터 주차장 승강기 공동저수시설 전기시설</aptSubactLrgeNdCmpnintFclty> <rgstDt>2016-06-02 15:23:08</rgstDt> </item> <item> <bidNtceNo>20160600313</bidNtceNo> <bidNtceOrd>00</bidNtceOrd> <bidNtceClsfc>기타</bidNtceClsfc> <nticeDt>2016-06-01 11:06:20</nticeDt> <refNo>관리공고제16-08</refNo> <ntceNm>주택화재보험 및 승강기 영업배상 책임보험 가입 입찰공고</ntceNm> <ntceDivNm /> <ntceInsttNm>청주푸르지오 캐슬아파트 입주자대표회의</ntceInsttNm> <bidMethdNm>전자입찰</bidMethdNm> <cntrctMthdNm>일반경쟁</cntrctMthdNm> <sucsfbidMthdNm>최저가</sucsfbidMthdNm> <rbidDivNm>재입찰 허용</rbidDivNm> <bidQlfctNm>공고서 참조</bidQlfctNm> <ofclNm>이현복</ofclNm> <ofclTelNo>043-266-1481</ofclTelNo> <ofclEmail>shalllee@hanmail.net</ofclEmail> <bidBeginDt>2016-06-10 09:00:00</bidBeginDt> <bidClseDt>2016-06-15 17:00:00</bidClseDt> <opengDt>2016-06-16 15:00:00</opengDt> <opengPlce>국가종합전자조달시스템(누리장터)</opengPlce> <bidWgrnteeRcptClseDt>2016-06-15 17:00:00</bidWgrnteeRcptClseDt> <sptDscrptDt /> <sptDscrptPlce /> <vatInclsnYnNm>부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다.)</vatInclsnYnNm> <refAmtUseYn>N</refAmtUseYn> <refAmtOpenYn /> <refAmt /> <asignBdgtAmt /> <refAmtDscrpt /> <rgnLmtDivNm>공고서 참조</rgnLmtDivNm> <dtchacOpenDt /> <dtchacBgnPrce /> <dtchacRmrk /> <bssAmtDtlScrnUrl /> <dtchacMinRdctnRt /> <dtchacBidprcLmtNum /> <dtchacAutoEtTm /> <dtchacBeforeBidDocClseDt /> <dtchacEtBidDocClseDt /> <ntceSpecDocUrl1>https://www.g2b.go.kr:8081/ep/co/fileDownload.do?fileTask=NOTIFY&fileSeq=20160600313::00::1::1</ntceSpecDocUrl1> <ntceSpecDocUrl2 /> <ntceSpecDocUrl3 /> <ntceSpecDocUrl4 /> <ntceSpecDocUrl5 /> <ntceSpecDocUrl6 /> <ntceSpecDocUrl7 /> <ntceSpecDocUrl8 /> <ntceSpecDocUrl9 /> <ntceSpecDocUrl10 /> <ntceSpecDocNm1>20160600313-00_1464746732419_2016주택화재보험및승강기등영업배상책임보험가입입찰공고.hwp</ntceSpecDocNm1> <ntceSpecDocNm2 /> <ntceSpecDocNm3 /> <ntceSpecDocNm4 /> <ntceSpecDocNm5 /> <ntceSpecDocNm6 /> <ntceSpecDocNm7 /> <ntceSpecDocNm8 /> <ntceSpecDocNm9 /> <ntceSpecDocNm10 /> <aptHsmpNm>청주푸르지오 캐슬아파트 입주자대표회의</aptHsmpNm> <aptCeoAdrs>충청북도 청주시 서원구 예체로68-0 (사직동)</aptCeoAdrs> <aptMngOfficeTelNo>043-266-1481</aptMngOfficeTelNo> <aptHmpgUrl /> <aptTotar>601489.76</aptTotar> <aptMngcstLevyArea>433088.61</aptMngcstLevyArea> <aptHshldNum>3599</aptHshldNum> <aptCmplNum>41</aptCmplNum> <aptHeatMethdNm>지역</aptHeatMethdNm> <aptSubactLrgeNdCmpnintFclty>지하주차장,노인정,도서관,훼트니트,어린이집</aptSubactLrgeNdCmpnintFclty> <rgstDt>2016-06-01 11:06:20</rgstDt> </item> </items> <numOfRows>1</numOfRows> <pageNo>1</pageNo> <totalCount>10</totalCount> </body> </response> |

[민간입찰공고정보에 대한 면허제한정보조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 5 | 오퍼레이션명(국문) | 민간입찰공고정보에 대한 면허제한정보조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoLicenseLimit |
|  | 오퍼레이션 설명 | 검색조건에 등록일시범위(통합입찰공고)와 입찰공고번호를 입력하여 입찰공고번호, 입찰공고차수, 제한그룹번호, 제한순번, 면허제한명, 허용업종목록, 등록일시를 포함한 면허제한정보 조회 |  |  |
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
1:등록일시 , 2.입찰공고번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 등록일시 조회시작일시 "YYYYMMDDHHMM"
(조회구분 '1' 선택시 필수) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 등록일시 조회종료일시 "YYYYMMDDHHMM"
(조회구분 '1' 선택시 필수) |
| bidNtceNo | 입찰공고번호 | 40 | 0 | R25BK01195323 | 검색하고자 하는 입찰공고번호
(조회구분이 '2'인 경우 필수) |
| bidNtceOrd | 입찰공고차수 | 3 | 0 | 000 | 검색하고자 하는 입찰공고차수
(조회구분이 2인 경우 필수) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명(미정) |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | R25BK01195323 | 입찰공고 관리번입찰공고 관리번호이며 조달청나라장터 공고건의 형식은 년도(4)+월(2)+순번(5)이며 자체전자조달시스템 보유기관은 각 기관별 형식 별도 사용
*차세대나라장터 번호체계 개편 : R+년도(2)+단계구분(2)+순번(8) 총 13자리 구성
-단계구분: 
BK(입찰), 
TA(계약), 
DD:(발주계획), 
BD(사전규격), 
BK(통합입찰) |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수는 해당 입찰공고에 대한 재공고 및 재입찰 등이 발생되었을 경우 증가되는 수 |
| lmtGrpNo | 제한그룹번호 | 3 | 0 | 1 | 제한면허의 제한그룹번호 |
| lmtSno | 제한순번 | 6 | 0 | 1 | 제한면허의 제한순번 |
| lcnsLmtNm | 면허제한명 | 200 | 0 | 건물위생관리업/1162 | 면허제한명 |
| permsnIndstrytyList | 허용업종목록 | 4000 | 0..n | [청소용역/1197] | 공고의 제한되는 면허에서 허용되는 업종 전체 목록 [허용업종명1/허용업종코드1],[허용업종명2/허용업종코드2] |
| rgstDt | 등록일시 | 19 | 1 | 2025-12-02 10:48:13 | 등록일시(통합입찰공고의 등록일시) “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoLicenseLimit?inqryDiv=2&bidNtceNo=R25BK01195323&bidNtceOrd=00&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>R25BK01195323</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <lmtGrpNo>1</lmtGrpNo> <lmtSno>1</lmtSno> <lcnsLmtNm>건물위생관리업/1162</lcnsLmtNm> <permsnIndstrytyList>[청소용역/1197]</permsnIndstrytyList> <rgstDt>2025-12-02 10:48:13</rgstDt> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[민간입찰공고정보에 대한 참가가능지역정보조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 6 | 오퍼레이션명(국문) | 민간입찰공고정보에 대한 참가가능지역정보조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoPrtcptPsblRgn |
|  | 오퍼레이션 설명 | 검색조건에 등록일시범위(통합입찰공고)와 입찰공고번호를 입력하여 입찰공고번호, 입찰공고차수, 제한그룹번호, 참가가능지역명, 등록일시 등 참가가능지역정보조회 |  |  |
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
1:등록일시 , 2.입찰공고번호 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512010000 | 등록일시 조회시작일시 "YYYYMMDDHHMM"
(조회구분 '1' 선택시 필수) |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512312359 | 등록일시 조회종료일시 "YYYYMMDDHHMM"
(조회구분 '1' 선택시 필수) |
| bidNtceNo | 입찰공고번호 | 40 | 0 | R25BK01252485 | 검색하고자 하는 입찰공고번호
(조회구분이 '2'인 경우 필수) |
| bidNtceOrd | 입찰공고차수 | 3 | 0 | 000 | 검색하고자 하는 입찰공고차수
(조회구분이 2인 경우 필수) |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명(미정) |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 3 | 데이터 총개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | R25BK01252485 | 입찰공고 관리번입찰공고 관리번호이며 조달청나라장터 공고건의 형식은 년도(4)+월(2)+순번(5)이며 자체전자조달시스템 보유기관은 각 기관별 형식 별도 사용
*차세대나라장터 번호체계 개편 : R+년도(2)+단계구분(2)+순번(8) 총 13자리 구성
-단계구분: 
BK(입찰), 
TA(계약), 
DD:(발주계획), 
BD(사전규격), 
BK(통합입찰) |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수는 해당 입찰공고에 대한 재공고 및 재입찰 등이 발생되었을 경우 증가되는 수 |
| lmtSno | 제한순번 | 6 | 0 | 1 | 참가가능지역의 제한순번 |
| prtcptPsblRgnNm | 참가가능지역명 | 200 | 0 | 서울특별시 | 참가가능지역의 명 |
| rgstDt | 등록일시 | 19 | 1 | 2025-12-29 17:46:38 | 등록일시(통합입찰공고의 등록일시) “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoPrtcptPsblRgn?inqryDiv=2&bidNtceNo=R25BK01252485&bidNtceOrd=000&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>R25BK01252485</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <lmtSno>1</lmtSno> <prtcptPsblRgnNm>서울특별시</prtcptPsblRgnNm> <rgstDt>2025-12-29 17:46:38</rgstDt> </item> <item> <bidNtceNo>R25BK01252485</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <lmtSno>2</lmtSno> <prtcptPsblRgnNm>인천광역시</prtcptPsblRgnNm> <rgstDt>2025-12-29 17:46:38</rgstDt> </item> <item> <bidNtceNo>R25BK01252485</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <lmtSno>3</lmtSno> <prtcptPsblRgnNm>경기도</prtcptPsblRgnNm> <rgstDt>2025-12-29 17:46:38</rgstDt> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>3</totalCount> </body> </response> |

[나라장터 검색조건에 의한 민간입찰공고정보에 대한 용역조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 7 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 용역조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoServcPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 공고일시범위, 개찰일시범위를 입력하여 입찰공고번호, 입찰공고차수, 입찰공고분류, 게시일시, 참조번호, 공고명, 공고구분명, 공고기관명, 입찰방식명, 계약방법명, 낙찰방법명, 재입찰구분명, 입찰자격명, 담당자명, 담당자전화번호, 담당자이메일, 입찰개시일시, 입찰마감일시, 개찰일시, 개찰장소, 입찰보증서접수마감일시, 현장설명일시, 현장설명장소, 부가가치세포함여부명, 기준금액사용여부, 기준금액공개여부, 기준금액, 배정예산금액, 기준금액설명, 지역제한구분명, 용역상세목록, 공고서URL, 아파트정보, 등록일시 등의 누리장터시스템에 등록된 용역 입찰공고 정보 조회 |  |  |
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
1:공고게시일시, 2:개찰일시 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202501220000 | 검색하고자 하는 조회시작일시 "YYYYMMDDHHMM"
조회구분이 '1'인 경우 공고게시일시 필수, '2'인 경우 개찰일시 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202501222359 | 검색하고자 하는 조회종료일시 "YYYYMMDDHHMM"
조회구분이 '1'인 경우 공고게시일시 필수, '2'인 경우 개찰일시 필수 |
| bidNtceNm | 입찰공고명 | 100 | 0 | K예다함 2025 ~ 2027 회계연도 외부감사인 선임 입찰 | 검색하고자하는 공고명
※ 공고명 일부 입력시에도 조회 가능 |
| ntceInsttCd | 공고기관코드 | 7 | 0 | M030494 | 검색하고자하는 공고기관코드 |
| ntceInsttNm | 공고기관명 | 200 | 0 | 더케이예다함(주) | 검색하고자하는 공고기관명
※ 공고기관명 일부 입력시에도 조회 가능 |
| dminsttCd | 수요기관코드 | 7 | 0 | M030494 | 검색하고자하는 수요기관코드 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드 입력 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드 입력 |
| dminsttNm | 수요기관명 | 200 | 0 | 더케이예다함(주) | 검색하고자하는 수요기관명
※ 수요기관명 일부 입력시에도 조회 가능 |
| refNo | 참조번호 | 105 | 0 | 재무회계팀-342 | 검색하고자하는 참조번호 (참조번호 : 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호) |
| prtcptLmtRgnCd | 참가제한지역코드 | 2 | 0 | 11 | 검색하고자하는 참가제한지역코드
11 : 서울특별시
26 : 부산광역시
27 : 대구광역시
28 : 인천광역시
29 : 광주광역시
30 : 대전광역시
31 : 울산광역시
36 : 세종특별자치시
41 : 경기도
42 : 강원도
43 : 충청북도
44 : 충청남도
45 : 전라북도
46 : 전라남도
47 : 경상북도
48 : 경상남도
50 : 제주도
51	: 강원특별자치도
52	: 전북특별자치도
12 : 전남광주통합특별시
99 : 기타 |
| prtcptLmtRgnNm | 참가제한지역명 | 100 | 0 | 서울 | 검색하고자하는 참가제한지역명
※ 참가제한지역명 일부 입력시에도 조회 가능 |
| indstrytyCd | 업종코드 | 100 | 0 | 4119 | 검색하고자하는 업종코드 |
| indstrytyNm | 업종명 | 100 | 0 | 토목공사업 | 검색하고자하는 업종명
※ 업종명 일부 입력시에도 조회 가능 |
| presmptPrceBgn | 추정가격시작 | 25 | 0 | 120000 | 검색하고자하는 추정가격범위시작금액이상 |
| presmptPrceEnd | 추정가격종료 | 25 | 0 | 120000 | 검색하고자하는 추정가격범위종료금액이하 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 1012159801 | 검색하고자하는 세부품명번호 |
| masYn | 다수공급경쟁자여부 | 1 | 0 | Y | 검색하고자하는 다수공급경쟁자여부 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | 800 | 검색하고자하는 조달요청번호 |
| bidClseExcpYn | 입찰마감제외여부 | 1 | 0 | Y | 검색하고자하는 입찰마감제외여부 |
| intrntnlDivCd | 국제구분코드 | 1 | 0 | 1 | 검색하고자하는 국제구분코드
국내:1, 국제:2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총 개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | R25BK00597093 | 입찰공고번호 |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수 |
| bidNtceClsfc | 입찰공고분류 | 30 | 1 | 민간일반용역 | 누리장터 입찰공고의 공고분류 (민간물품, 민간공사, 민간기술용역,민간일반용역, 민간기타) |
| nticeDt | 게시일시 | 19 | 1 | 2025-01-22 08:26:20 | 입찰공고 게시일시 "YYYY-MM-DD HH:MM:SS" |
| refNo | 참조번호 | 105 | 0 | 재무회계팀-342 | 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호 |
| ntceNm | 공고명 | 2000 | 0 | The-K예다함 2025 ~ 2027 회계연도 외부감사인 선임 입찰 | 누리장터 입찰공고의 공고명 |
| ntceDivNm | 공고구분명 | 2000 | 0 | 등록공고 | 누리장터 입찰공고의 공고구분명[등록공고,재공고,긴급공고,변경공고,취소공고]등으로 표기 |
| ntceInsttNm | 공고기관명 | 200 | 1 | 더케이예다함(주) | 누리장터 입찰공고의 공고기관명으로 민간발주자기관명 |
| bidMethdNm | 입찰방식명 | 200 | 1 | 전자입찰 | 누리장터 입찰공고의 입찰방식명[수기입찰, 전자입찰, 역경매] |
| cntrctMthdNm | 계약방법명 | 200 | 1 | 제한경쟁 | 누리장터 입찰공고의 계약방식명 [일반경쟁, 제한경쟁,지명경쟁] |
| sucsfbidMthdNm | 낙찰방법명 | 200 | 1 | 제한경쟁 | 누리장터 입찰공고의 낙찰방식명 [최저가, 최고가, 일적격심사최저가, 적격심사최고가] |
| rbidDivNm | 재입찰구분명 | 20 | 0 | 재입찰 허용 | 누리장터 입찰공고의 재입찰 허용여부명 |
| bidQlfctNm | 입찰자격명 | 20 | 0 | 공고규격서 참조 | “공고서 참조”로 고정되어 있음 |
| ofclNm | 담당자명 | 35 | 0 | 이동근 | 누리장터 입찰공고의 담당자명 |
| ofclTelNo | 담당자전화번호 | 25 | 0 | *********** | 누리장터 입찰공고의 담당자 전화번호 |
| ofclEmail | 담당자이메일 | 100 | 0 | Nine***@thekyedaham.co.kr | 누리장터 입찰공고의 담당자 메일주소 |
| bidBeginDt | 입찰개시일시 | 19 | 0 | 2025-01-22 09:00:00 | 누리장터 입찰공고의 입찰개시일시 "YYYY-MM-DD HH:MM:SS" |
| bidClseDt | 입찰마감일시 | 19 | 0 | 2025-02-05 17:00:00 | 누리장터 입찰공고의 입찰마감일시 "YYYY-MM-DD HH:MM:SS" |
| opengDt | 개찰일시 | 19 | 0 | 2025-02-05 18:00:00 | 누리장터 입찰공고의 개찰(입찰)일시 "YYYY-MM-DD HH:MM:SS" |
| opengPlce | 개찰장소 | 100 | 0 | 전자입찰담당자 PC | 누리장터 입찰공고의 개찰장소 |
| bidWgrnteeRcptClseDt | 입찰보증서접수마감일시 | 19 | 0 | 2025-02-05 17:00:00 | 누리장터 입찰공고의 접수마감일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptDt | 현장설명일시 | 19 | 0 | 2025-02-05 17:00:00 | 누리장터 입찰공고의 현장설명일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptPlce | 현장설명장소 | 100 | 0 | 현장설명장소 | 누리장터 입찰공고의 현장설명장소 |
| vatInclsnYnNm | 부가가치세포함여부명 | 200 | 0 | 부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다. | 누리장터 입찰공고의 부가가치세여부명 |
| refAmtUseYn | 기준금액사용여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격사용여부[Y,N] |
| refAmtOpenYn | 기준금액공개여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격공개여부[Y,N] |
| refAmt | 기준금액 | 25 | 0 | 165000000 | 투찰의 상한금액(원화,원) |
| asignBdgtAmt | 배정예산금액 | 25 | 0 | 165000000 | 누리장터 입찰공고의 배정예산금액(원화,원) |
| refAmtDscrpt | 기준금액설명 | 200 | 0 | 기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다. | 고정된 안내 문구 |
| rgnLmtDivNm | 지역제한구분명 | 100 | 0 | 공고서 참조 | 누리장터 입찰공고의 투찰제한내역으로 투찰제한이나 공고서 참조로 표기 |
| dtchacOpenDt | 역경매공개일시 | 19 | 0 | 2025-02-05 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 공개일시, “YYYY-MM-DD HH:MM:SS” |
| dtchacBgnPrce | 역경매시작가격 | 25 | 0 | 10000000 | 낙찰방법이 복수견적(역경매)일 경우  누리장터 입찰공고의 시작가격을 보여줌 |
| dtchacRmrk | 역경매비고 | 1000 | 0 | 기초금액은 부가가치세 포함금액임 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 비고내용 |
| bssAmtDtlScrnUrl | 기초금액상세화면URL | 500 | 0 | N/A | 낙찰방법이 복수견적(역경매)일 경우 기초금액상세화면URL( 25년도 나라장터 차세대 이후 제공 불가) |
| dtchacMinRdctnRt | 역경매최소인하비율 | 25 | 0 | 0.0 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 최소인하비율을 보여줌 |
| dtchacBidprcLmtNum | 역경매투찰제한횟수 | 100 | 0 | 제한없음 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 투찰제한횟수을 보여줌(회), 투찰제한회수가 0일 경우는 “제한없음” |
| dtchacAutoEtTm | 역경매자동연장시각 | 20 | 0 | 10. | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 자동연장시간(분), 자동연장시각:입찰마감일시 10분 전에 투찰이 이루어질 경우 입찰서마감일시를 해당 분만큼 자동연장합니다. 자동연장시각이 0분 일 경우, 입찰서마감일시 연장이 없는 경우입니다. 자동연장기능은 개찰일시 30분이전까지만 유효합니다. 즉, 입찰서마감일시의 자동연장은 개찰일시 30분 전까지만 최대 연장됩니다 |
| dtchacBeforeBidDocClseDt | 역경매이전입찰서마감일시 | 19 | 0 | 2025-02-05 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 이전입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| dtchacEtBidDocClseDt | 역경매연장입찰문서마감일시 | 19 | 0 | 2025-02-05 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 연장입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| servcDtlList | 용역상세목록 | 4000 | 0..n | [1^^^] | 용역상세목록 [순번^용역명^용역현장명^완수기한일자], [순번^용역명^용역현장명^완수기한일자] |
| ntceSpecDocUrl1 | 공고규격서URL1 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=1&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl2 | 공고규격서URL2 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=2&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl3 | 공고규격서URL3 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=3&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl4 | 공고규격서URL4 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=4&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl5 | 공고규격서URL5 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=3&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl6 | 공고규격서URL6 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=6&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl7 | 공고규격서URL7 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=7&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl8 | 공고규격서URL8 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=8&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl9 | 공고규격서URL9 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=9&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl10 | 공고규격서URL10 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&bidPbancOrd=000&fileType=&fileSeq=10&prcmBsneSeCd=22 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocNm1 | 공고규격서명1 | 256 | 0 | 1. 2025~2027 회계연도 외부감사인 선입 입찰공고_250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm2 | 공고규격서명2 | 256 | 0 | 2. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm3 | 공고규격서명3 | 256 | 0 | 3. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm4 | 공고규격서명4 | 256 | 0 | 4. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm5 | 공고규격서명5 | 256 | 0 | 5. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm6 | 공고규격서명6 | 256 | 0 | 6. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm7 | 공고규격서명7 | 256 | 0 | 7. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm8 | 공고규격서명8 | 256 | 0 | 8. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm9 | 공고규격서명9 | 256 | 0 | 9. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm10 | 공고규격서명10 | 256 | 0 | 10. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| aptHsmpNm | 아파트단지명 | 100 | 0 | 한밭우성아파트입주자대표회의 | 누리장터 입찰공고 공고기관 아파트 민간발주기관명 (누리장터 화면에는 단지명) |
| aptCeoAdrs | 아파트대표자주소 | 400 | 0 | 대전광역시 중구 문화동141 | 누리장터 입찰공고 공고기관 아파트 주소 |
| aptMngOfficeTelNo | 아파트관리사무소전화번호 | 25 | 0 | 042-585-3051 | 누리장터 입찰공고 공고기관 아파트 전화번호 |
| aptHmpgUrl | 아파트홈페이지URL | 100 | 0 | www.kukdong2.co.kr | 누리장터 입찰공고 공고기관 아파트 홈페이지URL |
| aptTotar | 아파트연면적 | 100 | 0 | 100097.72 | 누리장터 입찰공고 공고기관 아파트 연면적(㎡) |
| aptMngcstLevyArea | 아파트관리비부과면적 | 100 | 0 | 974 | 누리장터 입찰공고 공고기관 아파트 관리비부과면적 (㎡) |
| aptHshldNum | 아파트세대수 | 10 | 0 | 1400 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptCmplNum | 아파트동수 | 10 | 0 | 10 | 누리장터 입찰공고 공고기관 아파트 동수 |
| aptHeatMethdNm | 아파트난방방식명 | 10 | 0 | 중앙 | 누리장터 입찰공고 공고기관 아파트 난방방식 |
| aptSubactLrgeNdCmpnintFclty | 아파트부대및복리시설 | 4000 | 0 | - 관리사무실 : 69.42   - 노인정: 101.49   - 어린이놀이터 : 3,811.01(3개소)  - 주차장(대수) :994대 | 누리장터 입찰공고 공고기관 아파트 부대 및 복리시설 등 |
| rgstDt | 등록일시 | 19 | 1 | 2025-01-22 08:26:20 | 공고의 등록일시 “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoCnstwkPPSSrch?inqryDiv=1&inqryBgnDt=202501220000&inqryEndDt=202501222359&ntceNm=The-K예다함 2025 ~ 2027 회계연도 외부감사인 선임 입찰&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>R25BK00597093</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <bidNtceClsfc>민간일반용역</bidNtceClsfc> <nticeDt>2025-01-22 08:26:20</nticeDt> <refNo>재무회계팀-342</refNo> <ntceNm>The-K예다함 2025 ~ 2027 회계연도 외부감사인 선임 입찰</ntceNm> <ntceDivNm>등록공고</ntceDivNm> <ntceInsttNm>더케이예다함(주)</ntceInsttNm> <bidMethdNm>전자입찰</bidMethdNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <sucsfbidMthdNm>최저가낙찰제</sucsfbidMthdNm> <rbidDivNm>재입찰 허용</rbidDivNm> <bidQlfctNm>공고서 참조</bidQlfctNm> <ofclNm>이동근</ofclNm> <ofclTelNo></ofclTelNo> <ofclEmail>nine0659@thekyedaham.co.kr</ofclEmail> <bidBeginDt>2025-01-22 09:00:00</bidBeginDt> <bidClseDt>2025-02-05 17:00:00</bidClseDt> <opengDt>2025-02-05 18:00:00</opengDt> <opengPlce>전자입찰담당자 PC</opengPlce> <bidWgrnteeRcptClseDt></bidWgrnteeRcptClseDt> <sptDscrptDt></sptDscrptDt> <sptDscrptPlce></sptDscrptPlce> <vatInclsnYnNm>부가세 포함(입찰가격은 부가가치세를 포함한 금액을 제출하셔야 합니다.</vatInclsnYnNm> <refAmtUseYn>Y</refAmtUseYn> <refAmtOpenYn>Y</refAmtOpenYn> <refAmt>165000000</refAmt> <asignBdgtAmt>165000000</asignBdgtAmt> <refAmtDscrpt>기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다.</refAmtDscrpt> <rgnLmtDivNm>공고서 참조</rgnLmtDivNm> <dtchacOpenDt></dtchacOpenDt> <dtchacBgnPrce></dtchacBgnPrce> <dtchacRmrk></dtchacRmrk> <bssAmtDtlScrnUrl></bssAmtDtlScrnUrl> <dtchacMinRdctnRt></dtchacMinRdctnRt> <dtchacBidprcLmtNum></dtchacBidprcLmtNum> <dtchacAutoEtTm></dtchacAutoEtTm> <dtchacBeforeBidDocClseDt></dtchacBeforeBidDocClseDt> <dtchacEtBidDocClseDt></dtchacEtBidDocClseDt> <servcDtlList>[1^^^]</servcDtlList> <ntceSpecDocUrl1>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=1&amp;prcmBsneSeCd=22</ntceSpecDocUrl1> <ntceSpecDocUrl2>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK00597093&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=2&amp;prcmBsneSeCd=22</ntceSpecDocUrl2> <ntceSpecDocUrl3></ntceSpecDocUrl3> <ntceSpecDocUrl4></ntceSpecDocUrl4> <ntceSpecDocUrl5></ntceSpecDocUrl5> <ntceSpecDocUrl6></ntceSpecDocUrl6> <ntceSpecDocUrl7></ntceSpecDocUrl7> <ntceSpecDocUrl8></ntceSpecDocUrl8> <ntceSpecDocUrl9></ntceSpecDocUrl9> <ntceSpecDocUrl10></ntceSpecDocUrl10> <ntceSpecDocNm1>1. 2025~2027 회계연도 외부감사인 선입 입찰공고_250122.hwp</ntceSpecDocNm1> <ntceSpecDocNm2>2. 2025~2027 회계연도 외부감사인 선임 과업설명서 _250122.hwp</ntceSpecDocNm2> <ntceSpecDocNm3></ntceSpecDocNm3> <ntceSpecDocNm4></ntceSpecDocNm4> <ntceSpecDocNm5></ntceSpecDocNm5> <ntceSpecDocNm6></ntceSpecDocNm6> <ntceSpecDocNm7></ntceSpecDocNm7> <ntceSpecDocNm8></ntceSpecDocNm8> <ntceSpecDocNm9></ntceSpecDocNm9> <ntceSpecDocNm10></ntceSpecDocNm10> <aptHsmpNm></aptHsmpNm> <aptCeoAdrs></aptCeoAdrs> <aptMngOfficeTelNo></aptMngOfficeTelNo> <aptHmpgUrl></aptHmpgUrl> <aptTotar></aptTotar> <aptMngcstLevyArea></aptMngcstLevyArea> <aptHshldNum></aptHshldNum> <aptCmplNum></aptCmplNum> <aptHeatMethdNm></aptHeatMethdNm> <aptSubactLrgeNdCmpnintFclty></aptSubactLrgeNdCmpnintFclty> <rgstDt>2025-01-22 08:26:20</rgstDt> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 민간입찰공고정보에 대한 물품조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 8 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 물품조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoThngPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 공고일시범위, 개찰일시범위를 입력하여 입찰공고번호, 입찰공고차수, 입찰공고분류, 게시일시, 참조번호, 공고명, 공고구분명, 공고기관명, 입찰방식명, 계약방법명, 낙찰방법명, 재입찰구분명, 입찰자격명, 담당자명, 담당자전화번호, 담당자이메일, 입찰개시일시, 입찰마감일시, 개찰일시, 개찰장소, 입찰보증서접수마감일시, 현장설명일시, 현장설명장소, 부가가치세포함여부명, 기준금액사용여부, 기준금액공개여부, 기준금액, 배정예산금액, 기준금액설명, 지역제한구분명, 물품목록상세, 공고서URL, 아파트정보, 등록일시 등의 누리장터시스템에 등록된 물품 입찰공고 정보 조회 |  |  |
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
1:공고게시일시, 2:개찰일시 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202509040000 | 검색하고자 하는 조회시작일시 "YYYYMMDDHHMM"
조회구분이 '1'인 경우 공고게시일시 필수, '2'인 경우 개찰일시 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202509041231 | 검색하고자 하는 조회종료일시 "YYYYMMDDHHMM"
조회구분이 '1'인 경우 공고게시일시 필수, '2'인 경우 개찰일시 필수 |
| bidNtceNm | 입찰공고명 | 100 | 0 | 근생용지B 매각 입찰공고 | 검색하고자하는 공고명
※ 공고명 일부 입력시에도 조회 가능 |
| ntceInsttCd | 공고기관코드 | 7 | 0 | M025083 | 검색하고자하는 공고기관코드 |
| ntceInsttNm | 공고기관명 | 200 | 0 | 연지2구역주택재개발정비사업조합 | 검색하고자하는 공고기관명
※ 공고기관명 일부 입력시에도 조회 가능 |
| dminsttCd | 수요기관코드 | 7 | 0 | M025083 | 검색하고자하는 수요기관코드 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드 입력 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드 입력 |
| dminsttNm | 수요기관명 | 200 | 0 | 연지2구역주택재개발정비사업조합 | 검색하고자하는 수요기관명
※ 수요기관명 일부 입력시에도 조회 가능 |
| refNo | 참조번호 | 105 | 0 | 제2025-09-02호 | 검색하고자하는 참조번호 (참조번호 : 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호) |
| prtcptLmtRgnCd | 참가제한지역코드 | 2 | 0 | 11 | 검색하고자하는 참가제한지역코드
11 : 서울특별시
26 : 부산광역시
27 : 대구광역시
28 : 인천광역시
29 : 광주광역시
30 : 대전광역시
31 : 울산광역시
36 : 세종특별자치시
41 : 경기도
42 : 강원도
43 : 충청북도
44 : 충청남도
45 : 전라북도
46 : 전라남도
47 : 경상북도
48 : 경상남도
50 : 제주도
51	: 강원특별자치도
52	: 전북특별자치도
12 : 전남광주통합특별시
99 : 기타 |
| prtcptLmtRgnNm | 참가제한지역명 | 100 | 0 | 서울 | 검색하고자하는 참가제한지역명
※ 참가제한지역명 일부 입력시에도 조회 가능 |
| indstrytyCd | 업종코드 | 100 | 0 | 4119 | 검색하고자하는 업종코드 |
| indstrytyNm | 업종명 | 100 | 0 | 토목공사업 | 검색하고자하는 업종명
※ 업종명 일부 입력시에도 조회 가능 |
| presmptPrceBgn | 추정가격시작 | 25 | 0 | 120000 | 검색하고자하는 추정가격범위시작금액이상 |
| presmptPrceEnd | 추정가격종료 | 25 | 0 | 120000 | 검색하고자하는 추정가격범위종료금액이하 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 1012159801 | 검색하고자하는 세부품명번호 |
| masYn | 다수공급경쟁자여부 | 1 | 0 | N | 검색하고자하는 다수공급경쟁자여부 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | 800 | 검색하고자하는 조달요청번호 |
| bidClseExcpYn | 입찰마감제외여부 | 1 | 0 | N | 검색하고자하는 입찰마감제외여부 |
| intrntnlDivCd | 국제구분코드 | 1 | 0 | 1 | 검색하고자하는 국제구분코드
국내:1, 국제:2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 1 | 데이터 총 개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | R25BK01041870 | 입찰공고번호 |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수 |
| bidNtceClsfc | 입찰공고분류 | 30 | 1 | 물품 | 누리장터 입찰공고의 공고분류 (민간물품, 민간공사, 민간기술용역,민간일반용역, 민간기타) |
| nticeDt | 게시일시 | 19 | 1 | 2025-09-04 10:16:25 | 입찰공고 게시일시 "YYYY-MM-DD HH:MM:SS" |
| refNo | 참조번호 | 105 | 0 | 제2025-09-02호 | 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호 |
| ntceNm | 공고명 | 2000 | 0 | 근생용지B 매각 입찰공고 | 누리장터 입찰공고의 공고명 |
| ntceDivNm | 공고구분명 | 2000 | 0 | 등록공고 | 누리장터 입찰공고의 공고구분명[등록공고,재공고,긴급공고,변경공고,취소공고]등으로 표기 |
| ntceInsttNm | 공고기관명 | 200 | 1 | 연지2구역주택재개발정비사업조합 | 누리장터 입찰공고의 공고기관명으로 민간발주자기관명 |
| bidMethdNm | 입찰방식명 | 200 | 1 | 전자입찰 | 누리장터 입찰공고의 입찰방식명[수기입찰, 전자입찰, 역경매] |
| cntrctMthdNm | 계약방법명 | 200 | 1 | 일반경쟁 | 누리장터 입찰공고의 계약방식명 [일반경쟁, 제한경쟁, 지명경쟁] |
| sucsfbidMthdNm | 낙찰방법명 | 200 | 1 | 최고가 | 누리장터 입찰공고의 낙찰방식명 [최저가, 최고가, 일적격심사최저가, 적격심사최고가] |
| rbidDivNm | 재입찰구분명 | 20 | 0 | 재입찰 허용 | 누리장터 입찰공고의 재입찰 허용여부명 |
| bidQlfctNm | 입찰자격명 | 20 | 0 | 공고서 참조 | “공고서 참조”로 고정되어 있음 |
| ofclNm | 담당자명 | 35 | 0 | 김정민 | 누리장터 입찰공고의 담당자명 |
| ofclTelNo | 담당자전화번호 | 25 | 0 | *********** | 누리장터 입찰공고의 담당자 전화번호 |
| ofclEmail | 담당자이메일 | 100 | 0 | yeonji2.business@gmail.com | 누리장터 입찰공고의 담당자 메일주소 |
| bidBeginDt | 입찰개시일시 | 19 | 0 | 2025-09-04 10:30:00 | 누리장터 입찰공고의 입찰개시일시 "YYYY-MM-DD HH:MM:SS" |
| bidClseDt | 입찰마감일시 | 19 | 0 | 2025-09-12 15:30:00 | 누리장터 입찰공고의 입찰마감일시 "YYYY-MM-DD HH:MM:SS" |
| opengDt | 개찰일시 | 19 | 0 | 2025-09-12 16:30:00 | 누리장터 입찰공고의 개찰(입찰)일시 "YYYY-MM-DD HH:MM:SS" |
| opengPlce | 개찰장소 | 100 | 0 | 조합사무실 | 누리장터 입찰공고의 개찰장소 |
| bidWgrnteeRcptClseDt | 입찰보증서접수마감일시 | 19 | 0 | 2025-09-12 15:30:00 | 누리장터 입찰공고의 접수마감일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptDt | 현장설명일시 | 19 | 0 | 2025-09-12 15:30:00 | 누리장터 입찰공고의 현장설명일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptPlce | 현장설명장소 | 100 | 0 | 조달청나라장터 | 누리장터 입찰공고의 현장설명장소 |
| vatInclsnYnNm | 부가가치세포함여부명 | 200 | 0 | 부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다. | 누리장터 입찰공고의 부가가치세여부명 |
| refAmtUseYn | 기준금액사용여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격사용여부[Y,N] |
| refAmtOpenYn | 기준금액공개여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격공개여부[Y,N] |
| refAmt | 기준금액 | 25 | 0 | 6521014500 | 투찰의 상한금액(원화,원) |
| asignBdgtAmt | 배정예산금액 | 25 | 0 | 0 | 누리장터 입찰공고의 배정예산금액(원화,원) |
| refAmtDscrpt | 기준금액설명 | 200 | 0 | 기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다. | 고정된 안내 문구 |
| rgnLmtDivNm | 지역제한구분명 | 100 | 0 | 공고서 참조 | 누리장터 입찰공고의 투찰제한내역으로 투찰제한이나 공고서 참조로 표기 |
| dtchacOpenDt | 역경매공개일시 | 19 | 0 | 2025-09-12 15:30:00 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 공개일시 “YYYY-MM-DD HH:MM:SS” |
| dtchacBgnPrce | 역경매시작가격 | 25 | 0 | 10000000 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 시작가격을 보여줌 |
| dtchacRmrk | 역경매비고 | 1000 | 0 | 기초금액은 부가가치세 포함금액임 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 비고내용 |
| bssAmtDtlScrnUrl | 기초금액상세화면URL | 500 | 0 | N/A | 낙찰방법이 복수견적(역경매)일 경우 기초금액상세화면URL( 25년도 나라장터 차세대 이후 제공 불가) |
| dtchacMinRdctnRt | 역경매최소인하비율 | 25 | 0 | 0.2 | 낙찰방법이 복수견적(역경매)일 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 최소인하비율을 보여줌 |
| dtchacBidprcLmtNum | 역경매투찰제한횟수 | 100 | 0 | 제한없음 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 투찰제한횟수을 보여줌(회) 투찰제한회수가 0일 경우는 “제한없음” |
| dtchacAutoEtTm | 역경매자동연장시각 | 20 | 0 | 10. | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 자동연장시간(분) 자동연장시각:입찰마감일시 10분 전에 투찰이 이루어질 경우 입찰서마감일시를 해당 분만큼 자동연장합니다.
자동연장시각이 0 분 일 경우,입찰서마감일시 연장이 없는 경우입니다.
자동연장기능은 개찰일시 30분이전까지만 유효합니다.즉, 입찰서마감일시의 자동연장은 개찰일시 30분 전까지만 최대 연장됩니다 |
| dtchacBeforeBidDocClseDt | 역경매이전입찰서마감일시 | 19 | 0 | 2025-09-12 15:30:00 | 낙찰방법이 복수견적(역경매)일 경우 이전입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| dtchacEtBidDocClseDt | 역경매연장입찰문서마감일시 | 19 | 0 | 2025-09-12 15:30:00 | 낙찰방법이 복수견적(역경매)일 경우 연장입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| prdctDtlList | 물품상세목록 | 4000 | 0..n | [1^근생용지B(연지동 431 대 919.1㎡)^1^2025-09-18^조합사무실^기타사항참조] | 물품상세목록
[순번^물품분류명(품명)^수량^납품기한일자^납품장소명^인도조건], [순번^물품분류명(품명)^수량^납품기한일자^납품장소명^인도조건] |
| ntceSpecDocUrl1 | 공고규격서URL1 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=1&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl2 | 공고규격서URL2 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=2&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl3 | 공고규격서URL3 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=3&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl4 | 공고규격서URL4 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=4&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl5 | 공고규격서URL5 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=5&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl6 | 공고규격서URL6 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=6&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl7 | 공고규격서URL7 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=7&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl8 | 공고규격서URL8 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=8&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl9 | 공고규격서URL9 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=9&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl10 | 공고규격서URL10 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&bidPbancOrd=000&fileType=&fileSeq=10&prcmBsneSeCd=21 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocNm1 | 공고규격서명1 | 256 | 0 | 연지2구역 근생용지B_매각입찰_공고(20250904).hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm2 | 공고규격서명2 | 256 | 0 | 연지2구역 근생용지B_매각입찰_서식.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm3 | 공고규격서명3 | 256 | 0 | 대한1.jpg | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm4 | 공고규격서명4 | 256 | 0 | 대한2.jp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm5 | 공고규격서명5 | 256 | 0 | 대한3.jpg | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm6 | 공고규격서명6 | 256 | 0 | 제일1.jpg | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm7 | 공고규격서명7 | 256 | 0 | 제일2.jpg | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm8 | 공고규격서명8 | 256 | 0 | 연지2구역 근생용지B_매각입찰_서식.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm9 | 공고규격서명9 | 256 | 0 | 연지2구역 근생용지B_매각입찰_서식.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm10 | 공고규격서명10 | 256 | 0 | 연지2구역 근생용지B_매각입찰_서식.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| aptHsmpNm | 아파트단지명 | 100 | 0 | 유천포스코 the#입주자대표회의 | 누리장터 입찰공고 공고기관 아파트 민간발주기관명 (누리장터 화면에는 단지명) |
| aptCeoAdrs | 아파트대표자주소 | 400 | 0 | 대구광역시 달서구 달서대로67-0 (유천동, 유천동 포스코 더#아파트) | 누리장터 입찰공고 공고기관 아파트 주소 |
| aptMngOfficeTelNo | 아파트관리사무소전화번호 | 25 | 0 | 053-641-8100 | 누리장터 입찰공고 공고기관 아파트 연락처 |
| aptHmpgUrl | 아파트홈페이지URL | 100 | 0 | www.kukdong2.co.kr | 누리장터 입찰공고 공고기관 아파트 홈페이지URL |
| aptTotar | 아파트연면적 | 100 | 0 | 116967 | 누리장터 입찰공고 공고기관 아파트 연면적(㎡) |
| aptMngcstLevyArea | 아파트관리비부과면적 | 100 | 0 | 87341.72 | 누리장터 입찰공고 공고기관 아파트 관리비부과면적 (㎡) |
| aptHshldNum | 아파트세대수 | 10 | 0 | 764 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptCmplNum | 아파트동수 | 10 | 0 | 10 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptHeatMethdNm | 아파트난방방식명 | 100 | 0 | 개별 | 누리장터 입찰공고 공고기관 아파트 난방방식 |
| aptSubactLrgeNdCmpnintFclty | 아파트부대및복리시설 | 4000 | 0 | 관리동, 노인정, 휘트니스센터, 경비초소 | 누리장터 입찰공고 공고기관 아파트 부대 및 복리시설 등 |
| rgstDt | 등록일시 | 19 | 1 | 2025-09-04 10:16:25 | 공고의 등록일시 “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoThngPPSSrch?inqryDiv=1&inqryBgnDt=202509040000&inqryEndDt=202509041231&ntceNm=근생용지B 매각 입찰공고&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>R25BK01041870</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <bidNtceClsfc>민간물품</bidNtceClsfc> <nticeDt>2025-09-04 10:16:25</nticeDt> <refNo>제2025-09-02호</refNo> <ntceNm>근생용지B 매각 입찰공고</ntceNm> <ntceDivNm>등록공고</ntceDivNm> <ntceInsttNm>연지2구역주택재개발정비사업조합</ntceInsttNm> <bidMethdNm>전자입찰</bidMethdNm> <cntrctMthdNm>일반경쟁</cntrctMthdNm> <sucsfbidMthdNm>최고가</sucsfbidMthdNm> <rbidDivNm>재입찰 허용</rbidDivNm> <bidQlfctNm>공고서 참조</bidQlfctNm> <ofclNm>김정민</ofclNm> <ofclTelNo>***********</ofclTelNo> <ofclEmail>yeonji2.business@gmail.com</ofclEmail> <bidBeginDt>2025-09-04 10:30:00</bidBeginDt> <bidClseDt>2025-09-12 15:30:00</bidClseDt> <opengDt>2025-09-12 16:30:00</opengDt> <opengPlce>조합사무실</opengPlce> <bidWgrnteeRcptClseDt></bidWgrnteeRcptClseDt> <sptDscrptDt></sptDscrptDt> <sptDscrptPlce></sptDscrptPlce> <vatInclsnYnNm>부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다.</vatInclsnYnNm> <refAmtUseYn>Y</refAmtUseYn> <refAmtOpenYn>Y</refAmtOpenYn> <refAmt>6521014500</refAmt> <asignBdgtAmt>0</asignBdgtAmt> <refAmtDscrpt>기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다.</refAmtDscrpt> <rgnLmtDivNm>공고서 참조</rgnLmtDivNm> <dtchacOpenDt></dtchacOpenDt> <dtchacBgnPrce></dtchacBgnPrce> <dtchacRmrk></dtchacRmrk> <bssAmtDtlScrnUrl></bssAmtDtlScrnUrl> <dtchacMinRdctnRt></dtchacMinRdctnRt> <dtchacBidprcLmtNum></dtchacBidprcLmtNum> <dtchacAutoEtTm></dtchacAutoEtTm> <dtchacBeforeBidDocClseDt></dtchacBeforeBidDocClseDt> <dtchacEtBidDocClseDt></dtchacEtBidDocClseDt> <prdctDtlList>[1^근생용지B(연지동 431 대 919.1㎡)^1^2025-09-18^조합사무실^기타사항참조]</prdctDtlList> <ntceSpecDocUrl1>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=1&amp;prcmBsneSeCd=21</ntceSpecDocUrl1> <ntceSpecDocUrl2>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=2&amp;prcmBsneSeCd=21</ntceSpecDocUrl2> <ntceSpecDocUrl3>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=3&amp;prcmBsneSeCd=21</ntceSpecDocUrl3> <ntceSpecDocUrl4>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=4&amp;prcmBsneSeCd=21</ntceSpecDocUrl4> <ntceSpecDocUrl5>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=5&amp;prcmBsneSeCd=21</ntceSpecDocUrl5> <ntceSpecDocUrl6>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=6&amp;prcmBsneSeCd=21</ntceSpecDocUrl6> <ntceSpecDocUrl7>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01041870&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=7&amp;prcmBsneSeCd=21</ntceSpecDocUrl7> <ntceSpecDocUrl8></ntceSpecDocUrl8> <ntceSpecDocUrl9></ntceSpecDocUrl9> <ntceSpecDocUrl10></ntceSpecDocUrl10> <ntceSpecDocNm1>연지2구역 근생용지B_매각입찰_공고(20250904).hwp</ntceSpecDocNm1> <ntceSpecDocNm2>연지2구역 근생용지B_매각입찰_서식.hwp</ntceSpecDocNm2> <ntceSpecDocNm3>대한1.jpg</ntceSpecDocNm3> <ntceSpecDocNm4>대한2.jpg</ntceSpecDocNm4> <ntceSpecDocNm5>대한3.jpg</ntceSpecDocNm5> <ntceSpecDocNm6>제일1.jpg</ntceSpecDocNm6> <ntceSpecDocNm7>제일2.jpg</ntceSpecDocNm7> <ntceSpecDocNm8></ntceSpecDocNm8> <ntceSpecDocNm9></ntceSpecDocNm9> <ntceSpecDocNm10></ntceSpecDocNm10> <aptHsmpNm></aptHsmpNm> <aptCeoAdrs></aptCeoAdrs> <aptMngOfficeTelNo></aptMngOfficeTelNo> <aptHmpgUrl></aptHmpgUrl> <aptTotar></aptTotar> <aptMngcstLevyArea></aptMngcstLevyArea> <aptHshldNum></aptHshldNum> <aptCmplNum></aptCmplNum> <aptHeatMethdNm></aptHeatMethdNm> <aptSubactLrgeNdCmpnintFclty></aptSubactLrgeNdCmpnintFclty> <rgstDt>2025-09-04 10:16:25</rgstDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 민간입찰공고정보에 대한 공사조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 9 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 공사조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoCnstwkPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 공고일시범위, 개찰일시범위를 입력하여 입찰공고번호, 입찰공고차수, 입찰공고분류, 게시일시, 참조번호, 공고명, 공고구분명, 공고기관명, 입찰방식명, 계약방법명, 낙찰방법명, 재입찰구분명, 입찰자격명, 담당자명, 담당자전화번호, 담당자이메일, 입찰개시일시, 입찰마감일시, 개찰일시, 개찰장소, 입찰보증서접수마감일시, 현장설명일시, 현장설명장소, 부가가치세포함여부명, 기준금액사용여부, 기준금액공개여부, 기준금액, 배정예산금액, 기준금액설명, 지역제한구분명, 공사상세목록, 공고서URL, 아파트정보, 등록일시 등의 누리장터시스템에 등록된 공사 입찰공고 정보 조회 |  |  |
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
1:공고게시일시, 2:개찰일시 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 202512020000 | 검색하고자 하는 조회시작일시 "YYYYMMDDHHMM"
조회구분이 '1'인 경우 공고게시일시 필수, '2'인 경우 개찰일시 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 202512022359 | 검색하고자 하는 조회종료일시 "YYYYMMDDHHMM"
조회구분이 '1'인 경우 공고게시일시 필수, '2'인 경우 개찰일시 필수 |
| bidNtceNm | 입찰공고명 | 100 | 0 | 2025년지역특화품 비닐하우스 지원사업 | 검색하고자하는 공고명
※ 공고명 일부 입력시에도 조회 가능 |
| ntceInsttCd | 공고기관코드 | 7 | 0 | M074373 | 검색하고자하는 공고기관코드 |
| ntceInsttNm | 공고기관명 | 200 | 0 | 가연농장 | 검색하고자하는 공고기관명
※ 공고기관명 일부 입력시에도 조회 가능 |
| dminsttCd | 수요기관코드 | 100 | 0 | M074373 | 검색하고자하는 수요기관코드 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드 입력 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드 입력 |
| dminsttNm | 수요기관명 | 200 | 0 | 가연농장 | 검색하고자하는 수요기관명
※ 수요기관명 일부 입력시에도 조회 가능 |
| refNo | 참조번호 | 105 | 0 | 202512020 | 검색하고자하는 참조번호 (참조번호 : 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호) |
| prtcptLmtRgnCd | 참가제한지역코드 | 2 | 0 | 44 | 검색하고자하는 참가제한지역코드
11 : 서울특별시
26 : 부산광역시
27 : 대구광역시
28 : 인천광역시
29 : 광주광역시
30 : 대전광역시
31 : 울산광역시
36 : 세종특별자치시
41 : 경기도
42 : 강원도
43 : 충청북도
44 : 충청남도
45 : 전라북도
46 : 전라남도
47 : 경상북도
48 : 경상남도
50 : 제주도
51	: 강원특별자치도
52	: 전북특별자치도
12 : 전남광주통합특별시
99 : 기타 |
| prtcptLmtRgnNm | 참가제한지역명 | 100 | 0 | 충청남도 당진시 | 검색하고자하는 참가제한지역명
※ 참가제한지역명 일부 입력시에도 조회 가능 |
| indstrytyCd | 업종코드 | 100 | 0 | 4119 | 검색하고자하는 업종코드 |
| indstrytyNm | 업종명 | 100 | 0 | 산림조합중앙회 | 검색하고자하는 업종명
※ 업종명 일부 입력시에도 조회 가능 |
| presmptPrceBgn | 추정가격시작 | 25 | 0 | 68773000 | 검색하고자하는 추정가격범위시작금액이상 |
| presmptPrceEnd | 추정가격종료 | 25 | 0 | 68773000 | 검색하고자하는 추정가격범위종료금액이하 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 1012159801 | 검색하고자하는 세부품명번호 |
| masYn | 다수공급경쟁자여부 | 1 | 0 | Y | 검색하고자하는 다수공급경쟁자여부 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | 800 | 검색하고자하는 조달요청번호 |
| bidClseExcpYn | 입찰마감제외여부 | 1 | 0 | Y | 검색하고자하는 입찰마감제외여부 |
| intrntnlDivCd | 국제구분코드 | 1 | 0 | 1 | 검색하고자하는 국제구분코드
국내:1, 국제:2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | R25BK01195344 | 입찰공고번호 |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수 |
| bidNtceClsfc | 입찰공고분류 | 30 | 1 | 민간공사 | 누리장터 입찰공고의 공고분류 (민간물품, 민간공사, 민간기술용역,민간일반용역, 민간기타)) |
| nticeDt | 게시일시 | 19 | 1 | 2025-12-02 10:50:26 | 입찰공고 게시일시 "YYYY-MM-DD HH:MM:SS" |
| refNo | 참조번호 | 105 | 0 | 202512020 | 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호 |
| ntceNm | 공고명 | 2000 | 0 | 2025년지역특화품 비닐하우스 지원사업 | 누리장터 입찰공고의 공고명 |
| ntceDivNm | 공고구분명 | 2000 | 0 | 등록공고 | 누리장터 입찰공고의 공고구분명[등록공고,재공고,긴급공고,변경공고,취소공고]등으로 표기 |
| ntceInsttNm | 공고기관명 | 200 | 1 | 가연농장 | 누리장터 입찰공고의 공고기관명으로 민간발주자기관명 |
| bidMethdNm | 입찰방식명 | 200 | 1 | 전자입찰 | 누리장터 입찰공고의 입찰방식명[수기입찰, 전자입찰, 역경매] |
| cntrctMthdNm | 계약방법명 | 200 | 1 | 제한경쟁 | 누리장터 입찰공고의 계약방식명 [일반경쟁, 제한경쟁, 지명경쟁] |
| sucsfbidMthdNm | 낙찰방법명 | 200 | 1 | 최저가낙찰제 | 누리장터 입찰공고의 낙찰방식명 [최저가, 최고가, 일적격심사최저가, 적격심사최고가] |
| rbidDivNm | 재입찰구분명 | 20 | 0 | 재입찰 허용 | 누리장터 입찰공고의 재입찰 허용여부명 |
| bidQlfctNm | 입찰자격명 | 20 | 0 | 공고규격서 참조 | “공고서 참조”로 고정되어 있음 |
| ofclNm | 담당자명 | 35 | 0 | 황창희 | 누리장터 입찰공고의 담당자명 |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 031-273-0764 | 누리장터 입찰공고의 담당자 전화번호 |
| ofclEmail | 담당자이메일 | 100 | 0 | ostech1@naver.com | 누리장터 입찰공고의 담당자 메일주소 |
| bidBeginDt | 입찰개시일시 | 19 | 0 | 2025-12-02 15:00:00 | 누리장터 입찰공고의 입찰개시일시 "YYYY-MM-DD HH:MM:SS" |
| bidClseDt | 입찰마감일시 | 19 | 0 | 2025-12-08 09:00:00 | 누리장터 입찰공고의 입찰마감일시 "YYYY-MM-DD HH:MM:SS" |
| opengDt | 개찰일시 | 19 | 0 | 2025-12-08 10:00:00 | 누리장터 입찰공고의 개찰(입찰)일시 "YYYY-MM-DD HH:MM:SS" |
| opengPlce | 개찰장소 | 100 | 0 | 진안군 진안읍 선인길 21-19 | 누리장터 입찰공고의 개찰장소 |
| bidWgrnteeRcptClseDt | 입찰보증서접수마감일시 | 19 | 0 | 2025-12-08 09:00:00 | 누리장터 입찰공고의 접수마감일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptDt | 현장설명일시 | 19 | 0 | 2025-12-04 09:30:0 | 누리장터 입찰공고의 현장설명일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptPlce | 현장설명장소 | 100 | 0 | 진안군 진안읍 선인길 21-19 | 누리장터 입찰공고의 현장설명장소 |
| vatInclsnYnNm | 부가가치세포함여부명 | 200 | 0 | 부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다.) | 누리장터 입찰공고의 부가가치세여부명 |
| refAmtUseYn | 기준금액사용여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격사용여부[Y,N] |
| refAmtOpenYn | 기준금액공개여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격공개여부[Y,N] |
| refAmt | 기준금액 | 25 | 0 | 32670000 | 투찰의 상한금액 (원화,원) |
| asignBdgtAmt | 배정예산금액 | 25 | 0 | 32670000 | 누리장터 입찰공고의 배정예산금액 (원화,원) |
| refAmtDscrpt | 기준금액설명 | 200 | 0 | 기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다. | 고정된 안내 문구 |
| rgnLmtDivNm | 지역제한구분명 | 100 | 0 | 투찰제한 | 누리장터 입찰공고의 투찰제한내역으로 투찰제한이나 공고서 참조로 표기 |
| dtchacOpenDt | 역경매공개일시 | 19 | 0 | 2025-12-04 09:30:0 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 공개일시 “YYYY-MM-DD HH:MM:SS” |
| dtchacBgnPrce | 역경매시작가격 | 25 | 0 | 100 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 시작가격을 보여줌 |
| dtchacRmrk | 역경매비고 | 1000 | 0 | 기초금액은 부가가치세 포함금액임 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 비고내용 |
| bssAmtDtlScrnUrl | 기초금액상세화면URL | 500 | 0 | N/A | 낙찰방법이 복수견적(역경매)일 경우 기초금액상세화면URL( 25년도 나라장터 차세대 이후 제공 불가) |
| dtchacMinRdctnRt | 역경매최소인하비율 | 25 | 0 | 0.2 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 최소인하비율을 보여줌 |
| dtchacBidprcLmtNum | 역경매투찰제한횟수 | 100 | 0 | 2 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 투찰제한횟수을 보여줌(회) 투찰제한회수가 0일 경우는 “제한없음” |
| dtchacAutoEtTm | 역경매자동연장시각 | 20 | 0 | 0. | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 자동연장시간(분) 자동연장시각:입찰마감일시 10분 전에 투찰이 이루어질 경우 입찰서마감일시를 해당 분만큼 자동연장합니다.
자동연장시각이 0 분 일 경우,입찰서마감일시 연장이 없는 경우입니다.
자동연장기능은 개찰일시 30분이전까지만 유효합니다.즉, 입찰서마감일시의 자동연장은 개찰일시 30분 전까지만 최대 연장됩니다 |
| dtchacBeforeBidDocClseDt | 역경매이전입찰서마감일시 | 19 | 0 | 2016-07-19 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 이전입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| dtchacEtBidDocClseDt | 역경매연장입찰문서마감일시 | 19 | 0 | 2016-07-19 17:00:00 | 낙찰방법이 복수견적(역경매)일 경우 연장입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| cnstwkDtlList | 공사상세목록 | 4000 | 0..n | [1^2025년지역특화품 비닐하우스 지원사업^2025년지역특화품 비닐하우스 지원사업^2025-12-30] | 공사상세목록
[순번^공사명^공사현장명^준공기한일자], [순번^공사명^공사현장명^준공기한일자] |
| ntceSpecDocUrl1 | 공고규격서URL1 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=1&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl2 | 공고규격서URL2 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=2&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl3 | 공고규격서URL3 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=3&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl4 | 공고규격서URL4 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=4&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl5 | 공고규격서URL5 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=5&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl6 | 공고규격서URL6 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=6&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl7 | 공고규격서URL7 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=7&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl8 | 공고규격서URL8 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=8&pmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl9 | 공고규격서URL9 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=9&pcBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl10 | 공고규격서URL10 | 256 | 0 | https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&bidPbancOrd=000&fileType=&fileSeq=10&prcmBsneSeCd=24 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocNm1 | 공고규격서명1 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm2 | 공고규격서명2 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm3 | 공고규격서명3 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm4 | 공고규격서명4 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm5 | 공고규격서명5 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm6 | 공고규격서명6 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm7 | 공고규격서명7 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm8 | 공고규격서명8 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm9 | 공고규격서명9 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm10 | 공고규격서명10 | 256 | 0 | 지역특화품목_비닐하우스_지원사업 황 창 희.hwp | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| aptHsmpNm | 아파트단지명 | 100 | 0 | 동수원자이1차아파트입주자대표회의 | 누리장터 입찰공고 공고기관 아파트 민간발주기관명 (누리장터 화면에는 단지명) |
| aptCeoAdrs | 아파트대표자주소 | 400 | 0 | 경기도 수원시 영통구 태장로82번길32-0 (망포동, 망포마을 동수원 엘지빌리지) | 누리장터 입찰공고 공고기관 아파트 주소 |
| aptMngOfficeTelNo | 아파트관리사무소전화번호 | 25 | 0 | 031-273-0764 | 누리장터 입찰공고 공고기관 아파트 연락처 |
| aptHmpgUrl | 아파트홈페이지URL | 100 | 0 | www.ezville.net | 누리장터 입찰공고 공고기관 아파트 홈페이지URL |
| aptTotar | 아파트연면적 | 100 | 0 | 309301 | 누리장터 입찰공고 공고기관 아파트 연면적(㎡) |
| aptMngcstLevyArea | 아파트관리비부과면적 | 100 | 0 | 246803 | 누리장터 입찰공고 공고기관 아파트 관리비부과면적 (㎡) |
| aptHshldNum | 아파트세대수 | 10 | 0 | 1829 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptCmplNum | 아파트동수 | 10 | 0 | 14 | 누리장터 입찰공고 공고기관 아파트 동수 |
| aptHeatMethdNm | 아파트난방방식 | 100 | 0 | 지역 | 누리장터 입찰공고 공고기관 아파트 난방방식 |
| aptSubactLrgeNdCmpnintFclty | 아파트부대및복리시설 | 4000 | 0 | 관리사무소,노인정,문고,보육시설,지하저수시설,어린이놀이터,휴게시설,주민운동시설등 | 누리장터 입찰공고 공고기관 아파트 부대 및 복리시설 등 |
| rgstDt | 등록일시 | 19 | 1 | 2025-12-02 10:50:26 | 공고의 등록일시 “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoCnstwkPPSSrch?inqryDiv=1&inqryBgnDt=202512020000&inqryEndDt=202512022359&ntceNm=2025년지역특화품 비닐하우스 지원사업&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>R25BK01195344</bidNtceNo> <bidNtceOrd>000</bidNtceOrd> <bidNtceClsfc>민간공사</bidNtceClsfc> <nticeDt>2025-12-02 10:50:26</nticeDt> <refNo>202512020</refNo> <ntceNm>2025년지역특화품 비닐하우스 지원사업</ntceNm> <ntceDivNm>등록공고</ntceDivNm> <ntceInsttNm>가연농장</ntceInsttNm> <bidMethdNm>전자입찰</bidMethdNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <sucsfbidMthdNm>최저가낙찰제</sucsfbidMthdNm> <rbidDivNm>재입찰 허용</rbidDivNm> <bidQlfctNm>공고서 참조</bidQlfctNm> <ofclNm>황창희</ofclNm> <ofclTelNo></ofclTelNo> <ofclEmail>ostech1@naver.com</ofclEmail> <bidBeginDt>2025-12-02 15:00:00</bidBeginDt> <bidClseDt>2025-12-08 09:00:00</bidClseDt> <opengDt>2025-12-08 10:00:00</opengDt> <opengPlce>진안군 진안읍 선인길 21-19</opengPlce> <bidWgrnteeRcptClseDt></bidWgrnteeRcptClseDt> <sptDscrptDt>2025-12-04 09:30:00</sptDscrptDt> <sptDscrptPlce>진안군 진안읍 선인길 21-19</sptDscrptPlce> <vatInclsnYnNm>부가세 포함(입찰가격은 부가가치세를 포함한 금액을 제출하셔야 합니다.</vatInclsnYnNm> <refAmtUseYn>Y</refAmtUseYn> <refAmtOpenYn>Y</refAmtOpenYn> <refAmt>32670000</refAmt> <asignBdgtAmt>32670000</asignBdgtAmt> <refAmtDscrpt>기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다.</refAmtDscrpt> <rgnLmtDivNm>투찰제한</rgnLmtDivNm> <dtchacOpenDt></dtchacOpenDt> <dtchacBgnPrce></dtchacBgnPrce> <dtchacRmrk></dtchacRmrk> <bssAmtDtlScrnUrl></bssAmtDtlScrnUrl> <dtchacMinRdctnRt></dtchacMinRdctnRt> <dtchacBidprcLmtNum></dtchacBidprcLmtNum> <dtchacAutoEtTm></dtchacAutoEtTm> <dtchacBeforeBidDocClseDt></dtchacBeforeBidDocClseDt> <dtchacEtBidDocClseDt></dtchacEtBidDocClseDt> <cnstwkDtlList>[1^2025년지역특화품 비닐하우스 지원사업^2025년지역특화품 비닐하우스 지원사업^2025-12-30]</cnstwkDtlList> <ntceSpecDocUrl1>https://www.g2b.go.kr/pn/pnp/pnpe/UntyAtchFile/downloadFile.do?bidPbancNo=R25BK01195344&amp;bidPbancOrd=000&amp;fileType=&amp;fileSeq=1&amp;prcmBsneSeCd=24</ntceSpecDocUrl1> <ntceSpecDocUrl2></ntceSpecDocUrl2> <ntceSpecDocUrl3></ntceSpecDocUrl3> <ntceSpecDocUrl4></ntceSpecDocUrl4> <ntceSpecDocUrl5></ntceSpecDocUrl5> <ntceSpecDocUrl6></ntceSpecDocUrl6> <ntceSpecDocUrl7></ntceSpecDocUrl7> <ntceSpecDocUrl8></ntceSpecDocUrl8> <ntceSpecDocUrl9></ntceSpecDocUrl9> <ntceSpecDocUrl10></ntceSpecDocUrl10> <ntceSpecDocNm1>지역특화품목_비닐하우스_지원사업 황 창 희.hwp</ntceSpecDocNm1> <ntceSpecDocNm2></ntceSpecDocNm2> <ntceSpecDocNm3></ntceSpecDocNm3> <ntceSpecDocNm4></ntceSpecDocNm4> <ntceSpecDocNm5></ntceSpecDocNm5> <ntceSpecDocNm6></ntceSpecDocNm6> <ntceSpecDocNm7></ntceSpecDocNm7> <ntceSpecDocNm8></ntceSpecDocNm8> <ntceSpecDocNm9></ntceSpecDocNm9> <ntceSpecDocNm10></ntceSpecDocNm10> <aptHsmpNm></aptHsmpNm> <aptCeoAdrs></aptCeoAdrs> <aptMngOfficeTelNo></aptMngOfficeTelNo> <aptHmpgUrl></aptHmpgUrl> <aptTotar></aptTotar> <aptMngcstLevyArea></aptMngcstLevyArea> <aptHshldNum></aptHshldNum> <aptCmplNum></aptCmplNum> <aptHeatMethdNm></aptHeatMethdNm> <aptSubactLrgeNdCmpnintFclty></aptSubactLrgeNdCmpnintFclty> <rgstDt>2025-12-02 10:50:26</rgstDt> </item> </items> <numOfRows>999</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

[나라장터 검색조건에 의한 민간입찰공고정보에 대한 기타조회] 오퍼레이션 명세

| 오퍼레이션 정보 | 오퍼레이션 번호 | 10 | 오퍼레이션명(국문) | 나라장터 검색조건에 의한 민간입찰공고정보에 대한 기타조회 |
|---|---|---|---|---|
|  | 오퍼레이션 유형 | 조회(목록) | 오퍼레이션명(영문) | getPrvtBidPblancListInfoEtcPPSSrch |
|  | 오퍼레이션 설명 | 검색조건을 조회구분, 공고일시범위, 개찰일시범위를 입력하여 입찰공고번호, 입찰공고차수, 입찰공고분류, 게시일시, 참조번호, 공고명, 공고구분명, 공고기관명, 입찰방식명, 계약방법명, 낙찰방법명, 재입찰구분명, 입찰자격명, 담당자명, 담당자전화번호, 담당자이메일, 입찰개시일시, 입찰마감일시, 개찰일시, 개찰장소, 입찰보증서접수마감일시, 현장설명일시, 현장설명장소, 부가가치세포함여부명, 기준금액사용여부, 기준금액공개여부, 기준금액, 배정예산금액, 기준금액설명, 지역제한구분명, 공사상세목록, 공고서URL, 아파트정보, 등록일시 등의 누리장터시스템에 등록된 기타 입찰공고 정보 조회 |  |  |
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
1:공고게시일시, 2:개찰일시 |
| inqryBgnDt | 조회시작일시 | 12 | 0 | 200201010000 | 검색하고자 하는 조회시작일시 "YYYYMMDDHHMM"
조회구분이 '1'인 경우 공고게시일시 필수, '2'인 경우 개찰일시 필수 |
| inqryEndDt | 조회종료일시 | 12 | 0 | 200201012359 | 검색하고자 하는 조회종료일시 "YYYYMMDDHHMM"
조회구분이 '1'인 경우 공고게시일시 필수, '2'인 경우 개찰일시 필수 |
| bidNtceNm | 입찰공고명 | 100 | 0 | 회진초등학교 다목적강당 증축공사 | 검색하고자하는 공고명
※ 공고명 일부 입력시에도 조회 가능 |
| ntceInsttCd | 공고기관코드 | 7 | 0 | 1230000 | 검색하고자하는 공고기관코드 |
| ntceInsttNm | 공고기관명 | 200 | 0 | 조달청 | 검색하고자하는 공고기관명
※ 공고기관명 일부 입력시에도 조회 가능 |
| dminsttCd | 수요기관코드 | 7 | 0 | 8620000 | 검색하고자하는 수요기관코드 행자부코드(행정자치부에서 부여한 기관코드)가 있는 경우 행자부코드 입력 행자부코드가 없는 경우 조달청에서 부여한 수요기관 코드 입력 |
| dminsttNm | 수요기관명 | 200 | 0 | 전라남도장흥교육청 | 검색하고자하는 수요기관명
※ 수요기관명 일부 입력시에도 조회 가능 |
| refNo | 참조번호 | 105 | 0 | 200208 | 검색하고자하는 참조번호 (참조번호 : 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호) |
| prtcptLmtRgnCd | 참가제한지역코드 | 2 | 0 | 11 | 검색하고자하는 참가제한지역코드
11 : 서울특별시
26 : 부산광역시
27 : 대구광역시
28 : 인천광역시
29 : 광주광역시
30 : 대전광역시
31 : 울산광역시
36 : 세종특별자치시
41 : 경기도
42 : 강원도
43 : 충청북도
44 : 충청남도
45 : 전라북도
46 : 전라남도
47 : 경상북도
48 : 경상남도
50 : 제주도
51	: 강원특별자치도
52	: 전북특별자치도
12 : 전남광주통합특별시
99 : 기타 |
| prtcptLmtRgnNm | 참가제한지역명 | 100 | 0 | 서울 | 검색하고자하는 참가제한지역명
※ 참가제한지역명 일부 입력시에도 조회 가능 |
| indstrytyCd | 업종코드 | 100 | 0 | 4119 | 검색하고자하는 업종코드 |
| indstrytyNm | 업종명 | 100 | 0 | 토목공사업 | 검색하고자하는 업종명
※ 업종명 일부 입력시에도 조회 가능 |
| presmptPrceBgn | 추정가격시작 | 25 | 0 | 120000 | 검색하고자하는 추정가격범위시작금액이상 |
| presmptPrceEnd | 추정가격종료 | 25 | 0 | 120000 | 검색하고자하는 추정가격범위종료금액이하 |
| dtilPrdctClsfcNo | 세부품명번호 | 10 | 0 | 1012159801 | 검색하고자하는 세부품명번호 |
| masYn | 다수공급경쟁자여부 | 1 | 0 | Y | 검색하고자하는 다수공급경쟁자여부 |
| prcrmntReqNo | 조달요청번호 | 13 | 0 | 800 | 검색하고자하는 조달요청번호 |
| bidClseExcpYn | 입찰마감제외여부 | 1 | 0 | Y | 검색하고자하는 입찰마감제외여부 |
| intrntnlDivCd | 국제구분코드 | 1 | 0 | 1 | 검색하고자하는 국제구분코드
국내:1, 국제:2 |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
응답 메시지 명세

| 항목명(영문) | 항목명(국문) | 항목크기 | 항목구분 | 샘플데이터 | 항목설명 |
|---|---|---|---|---|---|
| resultCode | 결과코드 | 2 | 1 | 00 | 결과코드 |
| resultMsg | 결과메세지 | 50 | 1 | 정상. | 결과메세지 |
| numOfRows | 한 페이지 결과 수 | 4 | 1 | 10 | 한 페이지 결과 수 |
| pageNo | 페이지 번호 | 4 | 1 | 1 | 페이지 번호 |
| totalCount | 데이터 총 개수 | 4 | 1 | 17 | 데이터 총 개수 |
| bidNtceNo | 입찰공고번호 | 40 | 1 | 20150517791 | 입찰공고번호 |
| bidNtceOrd | 입찰공고차수 | 3 | 1 | 000 | 입찰공고차수 |
| bidNtceClsfc | 입찰공고분류 | 30 | 1 | 기타 | 누리장터 입찰공고의 공고분류 (민간물품, 민간공사, 민간기술용역,민간일반용역, 민간기타) |
| nticeDt | 게시일시 | 19 | 1 | 2015-05-18 14:06 | 입찰공고 게시일시 "YYYY-MM-DD HH:MM:SS" |
| refNo | 참조번호 | 105 | 0 | M001693 | 입찰공고 등과 관련하여 참조가 되는 관리번호(사용자 문서번호) 나라장터 중앙조달 물품/일반용역/외자 : 구매관리번호, 조달요청번호 나라장터 중앙조달 공사/기술용역 : 관리번호 나라장터 자체조달 : 사용자 문서번호(연계된 문서의 번호) 자체전자조달시스템 : 해당기관에서 관리하는 사용자 문서번호 |
| ntceNm | 공고명 | 2000 | 0 | 재활용품 수거업체 선정 입찰공고 | 누리장터 입찰공고의 공고명 |
| ntceDivNm | 공고구분명 | 2000 | 0 | 재공고 | 누리장터 입찰공고의 공고구분명[등록공고,재공고,긴급공고,변경공고,취소공고]등으로 표기 |
| ntceInsttNm | 공고기관명 | 200 | 1 | 삼부3단지아파트입주자대표회의 | 누리장터 입찰공고의 공고기관명으로 민간발주자기관명 |
| bidMethdNm | 입찰방식명 | 200 | 1 | 전자입찰 | 누리장터 입찰공고의 입찰방식명[수기입찰, 전자입찰, 역경매] |
| cntrctMthdNm | 계약방법명 | 200 | 1 | 일반경쟁 | 누리장터 입찰공고의 계약방식명 [일반경쟁, 제한경쟁, 지명경쟁] |
| sucsfbidMthdNm | 낙찰방법명 | 200 | 1 | 최고가 | 누리장터 입찰공고의 낙찰방식명 [최저가, 최고가, 일적격심사최저가, 적격심사최고가] |
| rbidDivNm | 재입찰구분명 | 20 | 0 | 재입찰 허용 | 누리장터 입찰공고의 재입찰 허용여부명 |
| bidQlfctNm | 입찰자격명 | 20 | 0 | 공고규격서 참조 | “공고서 참조”로 고정되어 있음 |
| ofclNm | 담당자명 | 35 | 0 | 김영배 | 누리장터 입찰공고의 담당자명 |
| ofclTelNo | 담당자전화번호 | 25 | 0 | 042-523-9042 | 누리장터 입찰공고의 담당자 전화번호 |
| ofclEmail | 담당자이메일 | 100 | 0 | kyb7042@hanmail.net | 누리장터 입찰공고의 담당자 메일주소 |
| bidBeginDt | 입찰개시일시 | 19 | 0 | 2015-05-19 9:00 | 누리장터 입찰공고의 입찰개시일시 "YYYY-MM-DD HH:MM:SS" |
| bidClseDt | 입찰마감일시 | 19 | 0 | 2015-05-26 15:00 | 누리장터 입찰공고의 입찰마감일시 "YYYY-MM-DD HH:MM:SS" |
| opengDt | 개찰일시 | 19 | 0 | 2015-05-26 16:00 | 누리장터 입찰공고의 개찰(입찰)일시 "YYYY-MM-DD HH:MM:SS" |
| opengPlce | 개찰장소 | 100 | 0 | 발주처 입주자회의실 | 누리장터 입찰공고의 개찰장소 |
| bidWgrnteeRcptClseDt | 입찰보증서접수마감일시 | 19 | 0 | 2015-05-26 15:00 | 누리장터 입찰공고의 접수마감일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptDt | 현장설명일시 | 19 | 0 | 2015-05-13 13:00 | 누리장터 입찰공고의 현장설명일시 "YYYY-MM-DD HH:MM:SS" |
| sptDscrptPlce | 현장설명장소 | 100 | 0 | 조달청나라장터 | 누리장터 입찰공고의 현장설명장소 |
| vatInclsnYnNm | 부가가치세포함여부명 | 200 | 0 | 부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다.) | 누리장터 입찰공고의 부가가치세여부명 |
| refAmtUseYn | 기준금액사용여부 | 20 | 0 | N | 누리장터 입찰공고의 기준가격사용여부[Y,N] |
| refAmtOpenYn | 기준금액공개여부 | 20 | 0 | Y | 누리장터 입찰공고의 기준가격공개여부[Y,N] |
| refAmt | 기준금액 | 25 | 0 | 30000000 | 투찰의 상한금액 (원화,원) |
| asignBdgtAmt | 배정예산금액 | 25 | 0 | 65522000 | 누리장터 입찰공고의 배정예산금액 |
| refAmtDscrpt | 기준금액설명 | 200 | 0 | 기준금액을 사용하는 공고건은 개찰시 투찰금액에 대한 기준금액으로 사용됩니다. | 고정된 안내 문구 |
| rgnLmtDivNm | 지역제한구분명 | 100 | 0 | 공고규격서 참조 | 누리장터 입찰공고의 투찰제한내역 |
| dtchacOpenDt | 역경매공개일시 | 19 | 0 | 2016-07-19 17:00 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 공개일시 |
| dtchacBgnPrce | 역경매시작가격 | 25 | 0 | 0 | 낙찰방법이 복수견적(역경매)일 경우누리장터 입찰공고의 시작가격을 보여줌 |
| dtchacRmrk | 역경매비고 | 1000 | 0 | 기초금액은 부가가치세 포함금액임 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 비고내용 |
| bssAmtDtlScrnUrl | 기초금액상세화면URL | 500 | 0 | N/A | 낙찰방법이 복수견적(역경매)일 경우 기초금액상세화면URL( 25년도 나라장터 차세대 이후 제공 불가) |
| dtchacMinRdctnRt | 역경매최소인하비율 | 25 | 0 | 0 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 최소인하비율을 보여줌 |
| dtchacBidprcLmtNum | 역경매투찰제한횟수 | 100 | 0 | 0 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 투찰제한횟수을 보여줌(회) |
| dtchacAutoEtTm | 역경매자동연장시각 | 20 | 0 | 10 | 낙찰방법이 복수견적(역경매)일 경우 누리장터 입찰공고의 자동연장시간(분) |
| dtchacBeforeBidDocClseDt | 역경매이전입찰서마감일시 | 19 | 0 | 2016-07-19 17:00 | 낙찰방법이 복수견적(역경매)일 경우 이전입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| dtchacEtBidDocClseDt | 역경매연장입찰문서마감일시 | 19 | 0 | 2016-07-19 17:00 | 낙찰방법이 복수견적(역경매)일 경우 연장입찰문서마감일시 "YYYY-MM-DD HH:MM:SS" |
| ntceSpecDocUrl1 | 공고규격서URL1 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl2 | 공고규격서URL2 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl3 | 공고규격서URL3 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl4 | 공고규격서URL4 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl5 | 공고규격서URL5 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl6 | 공고규격서URL6 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl7 | 공고규격서URL7 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl8 | 공고규격서URL8 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl9 | 공고규격서URL9 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocUrl10 | 공고규격서URL10 | 256 | 0 | 추후제공예정 | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표  파일URL |
| ntceSpecDocNm1 | 공고규격서명1 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-1.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm2 | 공고규격서명2 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-2.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm3 | 공고규격서명3 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-3.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm4 | 공고규격서명4 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-4.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm5 | 공고규격서명5 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-5.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm6 | 공고규격서명6 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-6.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm7 | 공고규격서명7 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-7.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm8 | 공고규격서명8 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-8.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm9 | 공고규격서명9 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-9.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| ntceSpecDocNm10 | 공고규격서명10 | 256 | 0 | 20160713550-01_1468574122276_엔씨다이노스 이벤트 대행사 선정입찰공고1-10.docx | 누리장터 입찰공고에 점부되는 공고규격서, 규격서, 적격심사표 |
| aptHsmpNm | 아파트단지명 | 100 | 0 | 삼부3단지아파트입주자대표회의 | 누리장터 입찰공고 공고기관 아파트 민간발주기관명 (누리장터 화면에는 단지명) |
| aptCeoAdrs | 아파트대표자주소 | 400 | 0 | 대전광역시 중구 태평로65-0 (태평동, 삼부아파트) | 누리장터 입찰공고 공고기관 아파트 주소 |
| aptMngOfficeTelNo | 아파트관리사무소전화번호 | 25 | 0 | 042-523-9042 | 누리장터 입찰공고 공고기관 아파트 연락처 |
| aptHmpgUrl | 아파트홈페이지URL | 100 | 0 | www.kukdong2.co.kr | 누리장터 입찰공고 공고기관 아파트 홈페이지URL |
| aptTotar | 아파트연면적 | 100 | 0 | 63571 | 누리장터 입찰공고 공고기관 아파트 연면적(㎡) |
| aptMngcstLevyArea | 아파트관리비부과면적 | 100 | 0 | 63173 | 누리장터 입찰공고 공고기관 아파트 관리비부과면적 (㎡) |
| aptHshldNum | 아파트세대수 | 10 | 0 | 510 | 누리장터 입찰공고 공고기관 아파트 세대수 |
| aptCmplNum | 아파트동수 | 10 | 0 | 8 | 누리장터 입찰공고 공고기관 아파트 동수 |
| aptHeatMethdNm | 아파트난방방식 | 100 | 0 | 중앙 | 누리장터 입찰공고 공고기관 아파트 난방방식 |
| aptSubactLrgeNdCmpnintFclty | 아파트부대및복리시설 | 4000 | 0 | 관리사무소,노인정,어린이놀이터,주차장,승강기 | 누리장터 입찰공고 공고기관 아파트 부대 및 복리시설 등 |
| rgstDt | 등록일시 | 19 | 1 | 2015-04-29 15:39:27 | 공고의 등록일시 “YYYY-MM-DD HH:MM:SS” |

※ 항목구분 : 필수(1), 옵션(0), 1건 이상 복수건(1..n), 0건 또는 복수건(0..n)
요청 / 응답 메시지 예제

| REST(URI) |
|---|
| http://apis.data.go.kr/1230000/ao/PrvtBidNtceService/getPrvtBidPblancListInfoEtcPPSSrch?inqryDiv=1&inqryBgnDt=201605010000&inqryEndDt=201605052359&dminsttCd=M004953&pageNo=1&numOfRows=10&ServiceKey=인증키 |
| 응답 메시지 |
| <response> <header> <resultCode>00</resultCode> <resultMsg>정상</resultMsg> </header> <body> <items> <item> <bidNtceNo>20160502183</bidNtceNo> <bidNtceOrd>00</bidNtceOrd> <bidNtceClsfc>기타</bidNtceClsfc> <nticeDt>2016-05-03 11:19:46</nticeDt> <refNo>20160503</refNo> <ntceNm>폐기물 처리 업체 선정공고</ntceNm> <ntceDivNm /> <ntceInsttNm>우방궁전맨션입주자대표회의</ntceInsttNm> <bidMethdNm>전자입찰</bidMethdNm> <cntrctMthdNm>제한경쟁</cntrctMthdNm> <sucsfbidMthdNm>최저가</sucsfbidMthdNm> <rbidDivNm>재입찰 허용</rbidDivNm> <bidQlfctNm>공고서 참조</bidQlfctNm> <ofclNm>조경미</ofclNm> <ofclTelNo>053-753-8440</ofclTelNo> <ofclEmail>jkm3287@hanmail.net</ofclEmail> <bidBeginDt>2016-05-03 12:00:00</bidBeginDt> <bidClseDt>2016-05-13 18:00:00</bidClseDt> <opengDt>2016-05-13 19:00:00</opengDt> <opengPlce>관리사무소</opengPlce> <bidWgrnteeRcptClseDt>2016-05-13 18:00:00</bidWgrnteeRcptClseDt> <sptDscrptDt>2016-05-10 10:00:00</sptDscrptDt> <sptDscrptPlce>관리사무소</sptDscrptPlce> <vatInclsnYnNm>부가세 미포함(입찰가격은 부가가치세를 제외한 금액을 제출하셔야 합니다.)</vatInclsnYnNm> <refAmtUseYn>N</refAmtUseYn> <refAmtOpenYn /> <refAmt /> <asignBdgtAmt /> <refAmtDscrpt /> <rgnLmtDivNm>공고서 참조</rgnLmtDivNm> <dtchacOpenDt /> <dtchacBgnPrce /> <dtchacRmrk /> <bssAmtDtlScrnUrl /> <dtchacMinRdctnRt /> <dtchacBidprcLmtNum /> <dtchacAutoEtTm /> <dtchacBeforeBidDocClseDt /> <dtchacEtBidDocClseDt />				<ntceSpecDocUrl1>https://www.g2b.go.kr:8081/ep/co/fileDownload.do?fileTask=NOTIFY&fileSeq=20160502183::00::1::1</ntceSpecDocUrl1> <ntceSpecDocUrl2 /> <ntceSpecDocUrl3 /> <ntceSpecDocUrl4 /> <ntceSpecDocUrl5 /> <ntceSpecDocUrl6 /> <ntceSpecDocUrl7 /> <ntceSpecDocUrl8 /> <ntceSpecDocUrl9 /> <ntceSpecDocUrl10 /> <ntceSpecDocNm1>20160502183-00_1462241928336_폐기물 입찰.hwp </ntceSpecDocNm1> <ntceSpecDocNm2 /> <ntceSpecDocNm3 /> <ntceSpecDocNm4 /> <ntceSpecDocNm5 /> <ntceSpecDocNm6 /> <ntceSpecDocNm7 /> <ntceSpecDocNm8 /> <ntceSpecDocNm9 /> <ntceSpecDocNm10 /> <aptHsmpNm>우방궁전맨션입주자대표회의</aptHsmpNm> <aptCeoAdrs>대구광역시 수성구 동대구로274-0 (범어동)</aptCeoAdrs> <aptMngOfficeTelNo>053-753-8439</aptMngOfficeTelNo> <aptHmpgUrl /> <aptTotar>77056</aptTotar> <aptMngcstLevyArea>77056</aptMngcstLevyArea> <aptHshldNum>538</aptHshldNum> <aptCmplNum>5</aptCmplNum> <aptHeatMethdNm>개별</aptHeatMethdNm> <aptSubactLrgeNdCmpnintFclty>관리실,경로당,입주자집회소,놀이터, 운동시설,전기실, 경비초소등</aptSubactLrgeNdCmpnintFclty> <rgstDt>2016-05-03 11:19:46</rgstDt> </item> </items> <numOfRows>10</numOfRows> <pageNo>1</pageNo> <totalCount>1</totalCount> </body> </response> |

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
| 30 | 등록되지 않은 서비스 키 | 잘못된 서비스키를 사용하였거나 서비스키를 URL인코딩하지 않음 | -OpenAPI활용신청정보의 발급받은 서비스키를 다시 확인하시기 바랍니다. - 서비스키 값이 같다면 서비스키가 URL 인코등 되었는지 다시 확인하시기 바랍니다. |
| 31 | 기한 만료된 서비스 키 | OpenAPI 사용기간이 만료됨 (활용연장신청 후 사용가능) | -OpenAPI 활용신청정보의 활용기간을 확인합니다. -활용기간이 지난 서비스는 이용할 수 없으며 연장신청을 통해 승인 받은 후 다시 이용가능 합니다. |
| 32 | 등록되지 않은 도메인명 또는 IP주소 | 활용신청한 서버의 IP와 실제 OpenAPI호출한 서버가 다를 경우 | -OpenAPI 활용신청정보의 등록된 도메인명이나 IP주소를 다시 확인합니다. -IP나 도메인의 정보를 변경하기 위해 변경신청을 할 수 있습니다. |

