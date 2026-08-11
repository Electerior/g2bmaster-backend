#!/usr/bin/env python3
"""수집한 명세의 모든 오퍼레이션이 실제 게이트웨이에 존재하는지 검증.

유효 서비스키 없이도 판별 가능:
  returnReasonCode 30 (SERVICE_KEY_IS_NOT_REGISTERED_ERROR) -> 엔드포인트 존재
  returnReasonCode 12 (NO_OPENAPI_SERVICE_ERROR)            -> 엔드포인트 없음/폐기
"""
import json
import os
import subprocess
import sys
from concurrent.futures import ThreadPoolExecutor

_here = os.path.dirname(os.path.abspath(__file__))
BASE = _here[:-len("/tools")] if _here.endswith("/tools") else _here + "/g2b-openapi"
UA = "Mozilla/5.0 (compatible; g2b-doc-verify/1.0)"
ROOT = "https://apis.data.go.kr/1230000"


def probe(path):
    url = f"{ROOT}/{path}"
    for attempt in range(3):
        r = subprocess.run(
            ["curl", "-sS", "-m", "30", "-A", UA, "-G", url,
             "--data-urlencode", "serviceKey=DUMMYKEY_FOR_PROBE",
             "-d", "pageNo=1", "-d", "numOfRows=1", "-d", "type=json"],
            capture_output=True, text=True,
        )
        body = r.stdout
        if '"returnReasonCode"' in body:
            code = body.split('"returnReasonCode"')[1].split('"')[1]
            return code
        if "SERVICE_KEY_IS_NOT_REGISTERED" in body:
            return "30"
        if "NO_OPENAPI_SERVICE" in body:
            return "12"
    return "??:" + body[:120].replace("\n", " ")


def run(pairs, label):
    print(f"\n=== {label} ({len(pairs)}건) ===", flush=True)
    with ThreadPoolExecutor(max_workers=6) as ex:
        codes = list(ex.map(lambda p: probe(p[1]), pairs))
    out = []
    for (tag, path), code in zip(pairs, codes):
        out.append({"tag": tag, "path": path, "code": code})
    return out


def main():
    idx = json.load(open(f"{BASE}/index.json", encoding="utf-8"))

    # 1) 문서에 실린 191개 오퍼레이션 전수
    documented = []
    for e in idx:
        spec = json.load(open(f"{BASE}/{e['swaggerFile']}", encoding="utf-8"))
        svc_path = "/".join(e["host"].split("/")[2:])
        for p in sorted(spec.get("paths", {})):
            documented.append((e["service"], f"{svc_path}/{p.lstrip('/')}"))

    # 2) 리포에만 있는 구버전 오퍼레이션 (폐기 주장 검증)
    repo = json.load(open(
        "/home/user/hanbin5/g2bmaster-backend/src/main/resources/g2b-operations.json",
        encoding="utf-8"))
    off_all = {p.lstrip("/") for e in idx
               for p in json.load(open(f"{BASE}/{e['swaggerFile']}", encoding="utf-8"))["paths"]}
    legacy = []
    for group in ("curatedOperations", "specOperations"):
        for svc, ops in repo[group].items():
            sp = repo["servicePath"].get(svc)
            if not sp:
                continue
            for o in ops:
                name = o["op"] if isinstance(o, dict) else o
                if name not in off_all:
                    legacy.append((f"{svc}(repo)", f"{sp.lstrip('/')}/{name}"))
    legacy = sorted(set(legacy))

    res_doc = run(documented, "문서 기재 오퍼레이션")
    res_leg = run(legacy, "리포에만 있는 구버전 오퍼레이션")

    ok = [r for r in res_doc if r["code"] == "30"]
    bad = [r for r in res_doc if r["code"] != "30"]
    print(f"\n[문서] 존재 확인 {len(ok)}/{len(res_doc)}")
    for r in bad:
        print(f"  ✗ code={r['code']} {r['path']}")

    gone = [r for r in res_leg if r["code"] == "12"]
    alive = [r for r in res_leg if r["code"] == "30"]
    other = [r for r in res_leg if r["code"] not in ("12", "30")]
    print(f"\n[구버전] 폐기 확인 {len(gone)}/{len(res_leg)}, 아직 살아있음 {len(alive)}")
    for r in alive:
        print(f"  ! 살아있음 {r['path']}")
    for r in other:
        print(f"  ? code={r['code']} {r['path']}")

    json.dump({"documented": res_doc, "legacy": res_leg},
              open(f"{BASE}/verification.json", "w", encoding="utf-8"),
              ensure_ascii=False, indent=2)
    print(f"\nwrote {BASE}/verification.json")
    return 0 if not bad else 1


if __name__ == "__main__":
    sys.exit(main())
