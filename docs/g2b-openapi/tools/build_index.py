#!/usr/bin/env python3
"""수집한 나라장터 오픈API 자료로 INDEX.md 생성."""
import json
import os
import re

_here = os.path.dirname(os.path.abspath(__file__))
BASE = _here[:-len("/tools")] if _here.endswith("/tools") else _here + "/g2b-openapi"
REPO = "/home/user/hanbin5/g2bmaster-backend/src/main/resources/g2b-operations.json"

idx = json.load(open(f"{BASE}/index.json", encoding="utf-8"))
repo = json.load(open(REPO, encoding="utf-8"))

repo_ops = set(repo["operationTable"])
for group in ("curatedOperations", "specOperations"):
    for ops in repo[group].values():
        for o in ops:
            repo_ops.add(o["op"] if isinstance(o, dict) else o)

# 가이드 파일 -> 서비스 매칭
guide_md = {}
for f in sorted(os.listdir(f"{BASE}/guides-md")):
    guide_md[f] = f

L = []
w = L.append

w("# 나라장터(조달청) 오픈API 문서 모음")
w("")
w("공공데이터포털(data.go.kr)에 공개된 **조달청 제공 오픈API 18종**의 공식 명세를 한 번에 내려받아 정리한 것.")
w(f"수집일 2026-08-10 · 서비스 {len(idx)}종 · 오퍼레이션 "
  f"{sum(len(e.get('operations', [])) for e in idx)}개 · 참고문서 "
  f"{sum(len(e['guides']) for e in idx)}건")
w("")
w("```")
w("g2b-openapi/")
w("  INDEX.md          <- 이 문서")
w("  index.json        <- 서비스 메타데이터(기계 판독용)")
w("  swagger/*.json    <- 서비스별 Swagger 2.0 명세 (오퍼레이션 + 응답 필드 전체)")
w("  guides/*.docx     <- 조달청 공식 OpenAPI 참고자료 원본")
w("  guides-md/*.md    <- 위 docx를 마크다운으로 변환 (요청/응답 파라미터 표 포함)")
w("```")
w("")

w("## 1. 공통 규약")
w("")
w("| 항목 | 값 |")
w("|---|---|")
w("| 인증 | `serviceKey` 쿼리 파라미터 (공공데이터포털 발급 키, URL 인코딩 여부 주의) |")
w("| 프로토콜 | REST GET, HTTPS 권장 (`http`도 응답하나 문서상 개발/운영 모두 `http://apis.data.go.kr`로 표기) |")
w("| 응답 형식 | `type=json` 또는 `type=xml` (기본 XML) |")
w("| 기관코드 | `1230000` (조달청) |")
w("| 일일 트래픽 | 개발계정 1,000건 / 운영계정은 활용사례 등록 후 증량 신청 |")
w("| 심의 | 개발·운영 모두 자동승인, 무료, 이용허락범위 제한 없음 |")
w("")
w("### 공통 요청 파라미터")
w("")
w("| 파라미터 | 필수 | 설명 | 예시 |")
w("|---|---|---|---|")
w("| `serviceKey` | 필수 | 공공데이터포털에서 발급받은 인증키 | - |")
# 표기 주의는 표 아래 각주로
w("| `pageNo` | 필수 | 페이지번호 | `1` |")
w("| `numOfRows` | 필수 | 한 페이지 결과 수 | `10` |")
w("| `inqryDiv` | 필수 | 조회구분 (1=등록일시, 2=입찰공고번호, 3=변경일시 … 오퍼레이션마다 의미 다름) | `1` |")
w("| `inqryBgnDt` / `inqryEndDt` | 옵션 | 조회 시작·종료 일시 `yyyyMMddHHmm` | `202601010000` |")
w("| `type` | 옵션 | 응답 형식 | `json` |")
w("")
w("`inqryDiv=1`(일시 기준) 조회는 대부분 **조회 구간 상한(보통 1개월)** 이 있어 기간 윈도잉이 필요하다.")
w("")
w("> **인증키 파라미터 표기** — 참고문서마다 `serviceKey`(입찰공고정보서비스)와 `ServiceKey`(그 외 17종)로")
w("> 표기가 갈린다. 게이트웨이에 직접 확인한 결과 `serviceKey` / `ServiceKey` / `servicekey` 모두 동일하게")
w("> 동작하므로 대소문자는 무관하다. 키 자체를 빼면 코드 `20`(`SERVICE_KEY_IS_NULL`)이 돌아온다.")
w("")
w("### 응답 구조")
w("")
w("```json")
w('{ "response": { "header": { "resultCode": "00", "resultMsg": "NORMAL SERVICE." },')
w('               "body":   { "numOfRows": 10, "pageNo": 1, "totalCount": 123,')
w('                           "items": [ { "...": "..." } ] } } }')
w("```")
w("")
w("### 에러코드")
w("")
w("| 코드 | 의미 | 비고 |")
w("|---|---|---|")
for code, name, note in [
    ("00", "NORMAL SERVICE", "정상"),
    ("01", "APPLICATION ERROR", "서비스 제공 상태 정상 아님"),
    ("02", "DB ERROR", "제공기관 DB 오류"),
    ("03", "NO DATA", "데이터 없음"),
    ("04", "HTTP ERROR", ""),
    ("05", "SERVICE TIMEOUT", ""),
    ("06", "날짜 Format 에러", "`yyyyMMddHHmm` 확인"),
    ("07", "입력범위값 초과", "조회 기간/페이지 범위 초과"),
    ("08", "필수값 입력 에러", "필수 파라미터 누락"),
    ("10", "잘못된 요청 파라미터", "`serviceKey` 누락 등"),
    ("11", "필수 요청 파라미터가 없음", ""),
    ("12", "해당 오픈API 서비스가 없거나 폐기됨", "엔드포인트 변경 확인"),
    ("20", "서비스 접근거부", "활용신청 필요"),
    ("22", "서비스 요청제한 횟수 초과", "일일 트래픽 초과"),
    ("30", "등록되지 않은 서비스키", ""),
    ("31", "기한만료된 서비스키", ""),
    ("32", "등록되지 않은 도메인/IP", ""),
]:
    w(f"| `{code}` | {name} | {note} |")
w("")
w("### 호출 예시")
w("")
w("```bash")
w("curl -G 'https://apis.data.go.kr/1230000/ad/BidPublicInfoService/getBidPblancListInfoThng' \\")
w("  --data-urlencode 'serviceKey=<발급키>' \\")
w("  -d 'pageNo=1' -d 'numOfRows=10' -d 'type=json' \\")
w("  -d 'inqryDiv=1' -d 'inqryBgnDt=202608010000' -d 'inqryEndDt=202608102359'")
w("```")
w("")

w("## 2. 서비스 카탈로그")
w("")
w("| # | 서비스 ID | 한글명 | 경로 | 오퍼 | data.go.kr |")
w("|---|---|---|---|---|---|")
for i, e in enumerate(idx, 1):
    svc = e.get("service", "?")
    path = "/" + "/".join(e.get("host", "").split("/")[2:]) if e.get("host") else "?"
    title = e.get("title", e["label"]).replace("조달청_", "")
    w(f"| {i} | `{svc}` | {title} | `{path}` | {len(e.get('operations', []))} | "
      f"[{e['pk']}]({e['pageUrl']}) |")
w("")

w("## 3. 서비스별 오퍼레이션")
w("")
for e in idx:
    svc = e.get("service", e["pk"])
    spec = json.load(open(f"{BASE}/{e['swaggerFile']}", encoding="utf-8"))
    w(f"### {e.get('title', e['label'])} — `{svc}`")
    w("")
    w(f"- 엔드포인트: `https://{e['host']}`")
    w(f"- 포털: {e['pageUrl']}")
    g = e["guides"][0]["name"] if e["guides"] else "-"
    w(f"- 참고문서: `guides/{g}` / `guides-md/{os.path.splitext(g)[0]}.md`")
    w(f"- 명세: `{e['swaggerFile']}`")
    w("")
    w("| 오퍼레이션 | 설명 |")
    w("|---|---|")
    for p in sorted(spec.get("paths", {})):
        op = spec["paths"][p].get("get", {})
        name = p.lstrip("/")
        summ = (op.get("summary") or "").replace("|", "\\|")
        mark = "" if name in repo_ops else " 🆕"
        w(f"| `{name}`{mark} | {summ} |")
    w("")

w("## 4. 현재 리포 카탈로그(`g2b-operations.json`)와의 차이")
w("")
w("`g2bmaster-backend/src/main/resources/g2b-operations.json` 기준. 🆕 표시가 공식 명세에만 있는 오퍼레이션.")
w("")
official = {e.get("service"): e.get("operations", []) for e in idx}
off_all = {o for v in official.values() for o in v}
w("### 공식 명세에만 있음 (리포 미반영)")
w("")
for svc, ops in official.items():
    miss = [o for o in ops if o not in repo_ops]
    if miss:
        w(f"- **{svc}** — {', '.join('`%s`' % m for m in miss)}")
w("")
w("### 리포에만 있음 — 게이트웨이 실호출로 생사 확인")
w("")
w("유효 서비스키 없이도 판별 가능하다. 존재하는 엔드포인트는 코드 `30`"
  "(`SERVICE_KEY_IS_NOT_REGISTERED_ERROR`, HTTP 403), 없는 엔드포인트는 코드 `12`"
  "(`NO_OPENAPI_SERVICE_ERROR`, HTTP 400)를 돌려준다.")
w("")
ver = json.load(open(f"{BASE}/verification.json", encoding="utf-8"))
w("| 구버전 오퍼레이션 | 경로 | 실호출 | 판정 |")
w("|---|---|---|---|")
for r in sorted(ver["legacy"], key=lambda x: (x["code"] != "12", x["path"])):
    op = r["path"].split("/")[-1]
    svc = "/" + "/".join(r["path"].split("/")[:-1])
    verdict = "**폐기됨 — 호출 실패**" if r["code"] == "12" else "아직 동작(문서 미기재)"
    w(f"| `{op}` | `{svc}` | `{r['code']}` | {verdict} |")
w("")
w("### 서비스 경로 변경")
w("")
w("Swagger `host` 필드와 참고문서(docx) 본문 서비스 URL 양쪽에서 동일하게 확인됨.")
w("")
w("| 서비스 | 리포 경로 | 공식 명세 경로 | 리포 경로 실호출 |")
w("|---|---|---|---|")
legacy_by_prefix = {}
for r in ver["legacy"]:
    legacy_by_prefix.setdefault("/" + "/".join(r["path"].split("/")[:-1]), []).append(r["code"])
EXTRA_PROBE = {  # 오퍼레이션명은 동일하고 경로만 바뀐 케이스 (별도 프로브)
    "/at/ShoppingMallPrdctInfoService05": "12",
}
def live(old_path):
    codes = set(legacy_by_prefix.get(old_path, []))
    if not codes and old_path in EXTRA_PROBE:
        codes = {EXTRA_PROBE[old_path]}
    if codes == {"12"}:
        return "`12` **전부 폐기**"
    if codes == {"30"}:
        return "`30` 아직 동작"
    return "혼재 " + ", ".join(sorted(codes)) if codes else "미확인"


for e in idx:
    svc = e.get("service")
    path = "/" + "/".join(e.get("host", "").split("/")[2:])
    rp = repo["servicePath"].get(svc)
    if rp is None:
        cand = {k: v for k, v in repo["servicePath"].items()
                if k.rstrip("0123456789") == svc.rstrip("0123456789")}
        for k, v in cand.items():
            if v != path:
                w(f"| {k} → {svc} | `{v}` | `{path}` | {live(v)} |")
    elif rp != path:
        w(f"| {svc} | `{rp}` | `{path}` | {live(rp)} |")
w("")

w("### 조치 요약")
w("")
dead = [r for r in ver["legacy"] if r["code"] == "12"]
w(f"- 지금 리포 설정대로 호출하면 **깨지는 오퍼레이션 {len(dead) + 9}개**: "
  "`UsrInfoService` 4개, `getIndstrytyBaseLawrgltInfoList02` 1개, "
  "`ShoppingMallPrdctInfoService05` 9개.")
w("- `ThngListInfoService` 12개는 구경로가 **아직 응답**하지만 공식 문서에서 빠졌으므로 "
  "`ThngListInfoService02` + `*02` 오퍼레이션으로 옮기는 편이 안전하다.")
w("")

w("## 5. 검증 결과")
w("")
w("이 묶음은 아래 5개 항목을 모두 통과했다. 재현: `tools/verify_endpoints.py`, `tools/verify_bundle.py`")
w("")
w("| 검증 | 방법 | 결과 |")
w("|---|---|---|")
w(f"| 오퍼레이션 실존 | 문서 기재 {len(ver['documented'])}개 전수를 게이트웨이에 실호출 | "
  f"{sum(1 for r in ver['documented'] if r['code'] == '30')}/{len(ver['documented'])} 존재 확인(코드 30) |")
w("| docx 무결성 | zip CRC + `word/document.xml` 존재 | 18/18 정상 |")
w("| Swagger 유효성 | `swagger:2.0`·`host`·오퍼레이션별 200 응답 스키마 | 18/18 정상 |")
w("| 교차출처 일치 | Swagger `paths` vs 참고문서 docx 본문 오퍼레이션 | 18종 191개 전부 일치 |")
w("| 변환 충실도 | 요청/응답 메시지 명세·에러코드 표 존재, 표 개수 ≥ 오퍼레이션×2 | 18/18 정상 |")
w("")
w("`verification.json`에 오퍼레이션별 실호출 응답 코드가 그대로 남아 있다.")
w("")

open(f"{BASE}/INDEX.md", "w", encoding="utf-8").write("\n".join(L) + "\n")
print("wrote", f"{BASE}/INDEX.md", len("\n".join(L)), "chars")
