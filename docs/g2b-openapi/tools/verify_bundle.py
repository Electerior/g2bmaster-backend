#!/usr/bin/env python3
"""수집물 정합성 검증: docx 무결성 · swagger 유효성 · 교차출처 일치 · INDEX 링크."""
import json
import os
import re
import zipfile

_here = os.path.dirname(os.path.abspath(__file__))
BASE = _here[:-len("/tools")] if _here.endswith("/tools") else _here + "/g2b-openapi"
fail = []
warn = []


def check(cond, msg):
    (fail if not cond else warn.__class__()).append(msg) if not cond else None
    return cond


idx = json.load(open(f"{BASE}/index.json", encoding="utf-8"))
print(f"서비스 {len(idx)}종\n")

print("== 1. docx 무결성 (zip CRC) ==")
for f in sorted(os.listdir(f"{BASE}/guides")):
    p = f"{BASE}/guides/{f}"
    try:
        with zipfile.ZipFile(p) as z:
            bad = z.testzip()
            has_doc = "word/document.xml" in z.namelist()
        if bad or not has_doc:
            fail.append(f"docx 손상: {f} (bad={bad}, document.xml={has_doc})")
        else:
            print(f"  OK  {os.path.getsize(p):>7,}B  {f}")
    except Exception as e:
        fail.append(f"docx 열기 실패: {f} — {e}")

print("\n== 2. swagger 유효성 ==")
for e in idx:
    p = f"{BASE}/{e['swaggerFile']}"
    s = json.load(open(p, encoding="utf-8"))
    ops = list(s.get("paths", {}))
    problems = []
    if s.get("swagger") != "2.0":
        problems.append("swagger!=2.0")
    if not s.get("info", {}).get("title"):
        problems.append("title 없음")
    if not ops:
        problems.append("paths 비어있음")
    full = (s.get("host", "") + s.get("basePath", "")).rstrip("/")
    if not full.startswith("apis.data.go.kr/1230000/"):
        problems.append(f"host 이상: {full}")
    # 각 오퍼레이션이 200 응답 스키마를 갖는지
    noresp = [o for o in ops
              if "200" not in s["paths"][o].get("get", {}).get("responses", {})]
    if noresp:
        problems.append(f"200 응답 스키마 없음: {noresp}")
    if problems:
        fail.append(f"{e['service']}: " + "; ".join(problems))
    else:
        # 응답 item 필드 개수
        n = len(re.findall(r'"description"', json.dumps(s, ensure_ascii=False)))
        print(f"  OK  {e['service']:<32} ops={len(ops):>2}  설명필드={n:>4}  {full}")

print("\n== 3. 교차출처 일치 (swagger paths vs 참고문서 docx 본문) ==")
for e in idx:
    s = json.load(open(f"{BASE}/{e['swaggerFile']}", encoding="utf-8"))
    ops = {o.lstrip("/") for o in s.get("paths", {})}
    g = e["guides"][0]["name"] if e["guides"] else None
    if not g:
        fail.append(f"{e['service']}: 참고문서 없음")
        continue
    md = open(f"{BASE}/guides-md/{os.path.splitext(g)[0]}.md",
              encoding="utf-8").read()
    missing = sorted(o for o in ops if o not in md)
    # 문서에만 있는 오퍼레이션 URL
    doc_ops = set(re.findall(r"apis\.data\.go\.kr/1230000/[a-z]{2}/[A-Za-z0-9]+/([A-Za-z0-9]+)", md))
    extra = sorted(doc_ops - ops)
    status = "OK " if not missing and not extra else "DIFF"
    print(f"  {status} {e['service']:<32} swagger={len(ops):>2} 문서언급={len(ops)-len(missing):>2}"
          + (f"  문서누락={missing}" if missing else "")
          + (f"  문서에만={extra}" if extra else ""))
    if missing:
        fail.append(f"{e['service']}: 참고문서에 없는 오퍼레이션 {missing}")
    if extra:
        warn.append(f"{e['service']}: swagger에 없는 문서 오퍼레이션 {extra}")

print("\n== 4. 마크다운 변환 충실도 ==")
for e in idx:
    g = e["guides"][0]["name"]
    md_p = f"{BASE}/guides-md/{os.path.splitext(g)[0]}.md"
    md = open(md_p, encoding="utf-8").read()
    tables = md.count("\n|---")
    nops = len(e["operations"])
    # 오퍼레이션당 최소 2개 표(요청/응답) + 서비스개요 표 기대
    enough = tables >= nops * 2
    print(f"  {'OK ' if enough else 'LOW'} {e['service']:<32} ops={nops:>2} 표={tables:>3} "
          f"{len(md):>7,}자")
    if not enough:
        fail.append(f"{e['service']}: 표 개수 부족 (ops={nops}, tables={tables})")
    # 참고문서마다 serviceKey / ServiceKey 표기가 다름 (게이트웨이는 대소문자 무시)
    if "servicekey" not in md.lower():
        fail.append(f"{e['service']}: 변환본에 serviceKey 파라미터 표 없음")
    # 문서에 따라 "응답 메시지 명세" / "응답 메시지 예제" 표기가 섞여 있음
    if "요청 메시지 명세" not in md:
        fail.append(f"{e['service']}: 요청 메시지 명세 섹션 누락")
    if not any(k in md for k in ("응답 메시지 명세", "응답 메시지 예제")):
        fail.append(f"{e['service']}: 응답 메시지 명세 섹션 누락")
    if "에러코드" not in md:
        fail.append(f"{e['service']}: 에러코드 표 누락")

print("\n== 5. INDEX.md 참조 무결성 ==")
index_md = open(f"{BASE}/INDEX.md", encoding="utf-8").read()
refs = set(re.findall(r"`((?:swagger|guides|guides-md)/[^`]+)`", index_md))
for r in sorted(refs):
    if not os.path.exists(f"{BASE}/{r}"):
        fail.append(f"INDEX.md 깨진 참조: {r}")
print(f"  파일 참조 {len(refs)}건 중 깨진 링크 "
      f"{sum(1 for r in refs if not os.path.exists(f'{BASE}/{r}'))}건")
# 오퍼레이션 표는 "3. 서비스별 오퍼레이션" 절에만 있음 (4절 구버전 표와 구분)
sec3 = index_md.split("## 3. 서비스별 오퍼레이션")[1].split("## 4.")[0]
claimed = len(re.findall(r"^\| `get", sec3, re.M))
actual = sum(len(e["operations"]) for e in idx)
print(f"  INDEX 오퍼레이션 행 {claimed}개 vs 명세 합계 {actual}개")
if claimed != actual:
    fail.append(f"INDEX 오퍼레이션 수 불일치 {claimed} != {actual}")

print("\n" + "=" * 60)
if fail:
    print(f"실패 {len(fail)}건")
    for f_ in fail:
        print("  ✗", f_)
else:
    print("전 항목 통과")
