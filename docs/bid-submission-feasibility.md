# 투찰 과정의 서비스 내재화 검토

> 질문: 오픈API로 나라장터의 투찰 과정 전부를 우리 서비스로 가져올 수 있는가?
> 검토일 2026-08-10 · 근거: [`docs/g2b-openapi/`](g2b-openapi/INDEX.md) 명세 18종 191개 오퍼레이션 전수 + 제도 조사

## 결론

**데이터는 전부, 행위는 하나도 가져올 수 없다.** 조달청 오픈API 18종은 전부 조회(GET) 전용이며,
투찰 라이프사이클 10단계 중 **정보 단계 7개는 완전 미러링이 가능**하지만 **행위 단계 3개
(조달업체 등록 → 입찰참가신청 → 투찰서 제출)는 API가 존재하지 않고, 우회 자동화도
인증 구조·이용약관·형법이 삼중으로 막고 있다.** 따라서 목표 아키텍처는 "투찰 대행"이 아니라
**"행위 직전까지의 전 과정을 우리가 소유하고, 행위 순간만 나라장터로 딥링크 핸드오프"**다.

## 1. 투찰 라이프사이클 단계별 커버리지

| # | 단계 | 성격 | API | 커버리지 |
|---|---|---|---|---|
| 1 | 발주계획 | 정보 | `OrderPlanSttusService` (8 ops) | ✅ 전체 |
| 2 | 사전규격 공개·의견 | 정보 | `HrcspSsstndrdInfoService` (20 ops, 의견 *조회* 포함) | ✅ 전체 — 단, 의견 *등록*은 나라장터에서만 |
| 3 | 조달요청 | 정보 | `PrcrmntReqInfoService` (12 ops) | ✅ 전체 |
| 4 | 입찰공고 | 정보 | `BidPublicInfoService` (25 ops) | ✅ 전체 — 일정·기초금액·면허제한·참가가능지역·규격서URL·변경이력 |
| 5 | **조달업체 등록** (인증서·서류) | **행위** | 없음 | ❌ |
| 6 | **입찰참가신청** (PQ·보증서·공동수급협정) | **행위** | 없음 — 접수 *일정*만 4단계 응답에 존재 | ❌ |
| 7 | **투찰** (가격 제출 + 복수예가 추첨번호 2개 선택) | **행위** | 없음 | ❌ |
| 8 | 개찰 | 정보(사후) | `ScsbidInfoService` 개찰결과 계열 | ✅ 사후 전체 — 실시간 개찰 참관은 불가 |
| 9 | 낙찰 | 정보 | `ScsbidInfoService` `getScsbidListSttus*` | ✅ 전체 |
| 10 | 계약·이행 | 정보 | `CntrctInfoService` (21 ops) + `CntrctProcssIntgOpenService` | ✅ 전체 — 공고번호 하나로 전 과정 역추적 가능 |

8단계의 사후 데이터 해상도가 특히 높다 (`swagger/ScsbidInfoService.swagger.json`에서 확인):

- **참가업체별 투찰 전수**: `getOpengResultListInfoOpengCompt` →
  `prcbdrNm`(투찰업체명)·`prcbdrBizno`·`bidprcAmt`(투찰금액)·`bidprcrt`(투찰률)·
  `opengRank`(개찰순위)·`bidprcDt`(투찰일시)·`drwtNo1/2`(추첨번호)
- **예비가격 상세**: `get...PreparPcDetail` → 복수예가 15개 전체(`compnoRsrvtnPrceSno`·
  `bsisPlnprc`), 추첨 결과(`drwtYn`·`drwtNum`), 예정가격(`plnprc`), 기초금액(`bssamt`)
- **유찰·재입찰**: `getOpengResultListInfoFailing` / `...Rebid`

즉 "누가 얼마에 투찰해서 몇 위였고 예가가 어떻게 추첨됐는지"까지 전부 우리 DB에 넣을 수 있다.
경쟁사 투찰 성향 분석·사정율 역산·모의투찰의 원료로 충분하다.

## 2. 행위 단계가 막혀 있는 이유

세 겹이며, 각각 독립적으로 치명적이다.

**(1) 기술 — 쓰기 API가 존재하지 않는다.**
18종 191개 오퍼레이션 전수 확인 결과 전부 조회 전용이다 (`docs/g2b-openapi/verification.json`).
투찰서 제출은 나라장터 웹의 보안모듈 + 사업자 범용 공동인증서 전자서명 안에서만 이뤄진다.
2024-01-01부로 지문보안토큰 *의무*는 폐지되고 차세대 나라장터가 간편인증(네이버·카카오·PASS)을
도입했지만, 이는 본인확인 *수단*이 바뀐 것이지 본인확인 *요건*이 사라진 게 아니다 —
제3자 시스템이 대신 서명할 수 있는 통로는 여전히 없다.

**(2) 제도 — 연계 대상이 아니다.**
나라장터와 시스템 연계가 허용되는 것은 전자조달법상 수요기관의 자체전자조달시스템
(국방전자조달·LH·한전 등)뿐이고, 그마저 차세대 나라장터로 통합되는 방향이다.
민간 SaaS는 연계 승인 대상 자체가 아니다.

**(3) 법·약관 — 우회 자동화는 사업 리스크가 아니라 형사 리스크다.**

| 장치 | 내용 | 우리에게 의미 |
|---|---|---|
| 대리입찰 금지 | 인증서 대여·대리투찰 원천 차단 (지문인식 도입의 입법 취지) | 고객 인증서를 받아 대신 투찰하는 모델 자체가 불가 |
| 동일IP 중복투찰 금지 (2014~) | 같은 공고에 동일 IP 복수 투찰 시 무효 | 우리 서버가 여러 고객사를 대행하는 순간 고객 입찰이 **무효**가 됨 |
| 국가종합전자조달시스템 이용약관 | 매크로·유사 프로그램 이용 제한 | 브라우저 자동화로 투찰 흉내도 약관 위반 |
| 형법 제315조 입찰방해죄 | 위태범 — 실제 피해 없어도 공정성 위태화만으로 성립 | 자동투찰 시도 자체가 구성요건에 걸릴 수 있음 |

## 3. 가져올 수 있는 최대 범위 — 딥링크 핸드오프 모델

행위 3단계를 뺀 나머지 전부를 소유하고, 행위 시점에 정확한 화면으로 넘긴다.
공고 응답에 핸드오프에 필요한 것이 이미 다 있다:

- **딥링크**: `bidNtceDtlUrl`(공고상세) / `bidNtceUrl` / `ntceSpecDocUrl1~10`(규격서) / `stdNtceDocUrl`
- **행위 마감 시계**: `bidQlfctRgstDt`(참가자격등록 마감) → `pqApplDocRcptDt`(PQ 접수) →
  `bidWgrnteeRcptClseDt`(보증서 마감) → `cmmnSpldmdAgrmntClseDt`(공동수급 마감) →
  `bidBeginDt`/`bidClseDt`(투찰 개시·마감) → `opengDt`(개찰) → `rbidOpengDt`(재입찰 개찰)

이 필드들로 만들 수 있는 것:

1. **행위 체크리스트 + 알림** — 공고별로 위 마감 시계를 타임라인화, 단계별 임박 알림.
   변경공고는 `ChgHstry` 계열로 감지해 일정 재계산.
2. **참가자격 사전 진단** — `getBidPblancListInfoLicenseLimit`(면허제한) +
   `...PrtcptPsblRgn`(참가가능지역) + `IndstrytyBaseLawrgltInfoService`(업종·근거법규) +
   `UsrInfoService02`(업체 등록업종·공급물품)를 고객 프로필과 대조 → "이 공고, 넣을 수 있음/없음".
3. **투찰가 의사결정 지원** — 과거 `PreparPcDetail`(예가 분포) + `OpengCompt`(경쟁사 투찰
   전수)로 사정율 분석·모의투찰. AI 저장소의 추론 계층과 맞물리는 지점.
4. **사후 자동 회수** — 개찰 후 `inqryDiv` 윈도잉 폴링으로 개찰결과→낙찰→계약을 이어 붙여
   공고 카드 하나에 전 과정을 닫는다. `CntrctProcssIntgOpenService`가 공고번호 키의 백필용.

한계 두 가지는 명시해 둔다: **개찰 실시간 중계 불가**(결과 등록 후 조회만 가능, 폴링 주기만큼
지연), **일일 트래픽**(개발계정 1,000건 — 운영계정 증량 신청 필요, `docs/g2b-openapi/INDEX.md` §1).

## 4. 하지 말아야 할 것

- 고객 공동인증서/간편인증 자격 수탁 후 대리투찰 — 대리입찰 금지 정면 위반.
- 브라우저 자동화(매크로)로 참가신청·투찰 제출 — 약관 위반 + 입찰방해죄 리스크 +
  동일IP 중복투찰로 고객 입찰 무효.
- "반자동 투찰"(우리가 채워놓고 클릭만 고객이) 류의 회색지대 — 위 세 장치가 전부 걸린다.

## 근거

- 오퍼레이션 전수·조회전용 확인: [`docs/g2b-openapi/INDEX.md`](g2b-openapi/INDEX.md) §5, `verification.json`
- 지문인식 전자입찰(대리입찰 차단 취지): [정책브리핑](https://www.korea.kr/briefing/actuallyView.do?newsId=148765292)
- 지문보안토큰 의무 폐지·차세대 간편인증: [보안뉴스](https://m.boannews.com/html/detail.html?idx=120087), [정보통신신문](https://www.koit.co.kr/news/articleView.html?idxno=119548)
- 자체전자조달시스템의 차세대 통합: [연합(다음)](https://v.daum.net/v/1mhG4M2uGp?f=p)
- 동일IP 중복투찰 금지: [한국건설산업연구원](https://www.ricon.re.kr/board/view.php?no=3452&group=issue&page=construction_info&cate=10)
- 국가종합전자조달시스템 이용약관: [국가법령정보센터](https://www.law.go.kr/%ED%96%89%EC%A0%95%EA%B7%9C%EC%B9%99/%EA%B5%AD%EA%B0%80%EC%A2%85%ED%95%A9%EC%A0%84%EC%9E%90%EC%A1%B0%EB%8B%AC%EC%8B%9C%EC%8A%A4%ED%85%9C%20%EC%9D%B4%EC%9A%A9%EC%95%BD%EA%B4%80)
- 정부서비스 매크로 이용 제한: [정부24 공지](https://www.gov.kr/portal/ntcItm/108068?Mcode=1118)
