#!/usr/bin/env python3
"""나라장터(조달청) 오픈API 문서 일괄 수집기.

공공데이터포털 openapi.do 페이지에서
  1) 페이지에 임베드된 Swagger 2.0 명세(JSON)
  2) 참고문서(활용가이드 docx/hwp/pdf)
를 서비스별로 내려받는다.
"""
import json
import os
import re
import subprocess
import sys
import urllib.parse

_here = os.path.dirname(os.path.abspath(__file__))
OUT = _here[:-len("/tools")] if _here.endswith("/tools") else _here + "/g2b-openapi"
UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126 Safari/537.36"

DATASETS = [
    ("15129394", "나라장터 입찰공고정보서비스"),
    ("15129397", "나라장터 낙찰정보서비스"),
    ("15129427", "나라장터 계약정보서비스"),
    ("15129437", "나라장터 사전규격정보서비스"),
    ("15129468", "나라장터 조달요청서비스"),
    ("15129462", "나라장터 발주계획현황서비스"),
    ("15129415", "나라장터 가격정보현황서비스"),
    ("15129459", "나라장터 계약과정통합공개서비스"),
    ("15129466", "나라장터 사용자정보서비스"),
    ("15129467", "나라장터 업종및근거법규서비스"),
    ("15129471", "나라장터쇼핑몰 품목정보서비스"),
    ("15129417", "물품목록정보서비스"),
    ("15129470", "물품관리정보서비스"),
    ("15129412", "공공조달통계정보서비스"),
    ("15058815", "나라장터 공공데이터개방표준서비스"),
    ("15129456", "누리장터 민간입찰공고서비스"),
    ("15129458", "누리장터 민간낙찰정보서비스"),
    ("15129469", "누리장터 민간계약정보서비스"),
]


def curl(url, out=None, referer=None):
    cmd = ["curl", "-sS", "-m", "90", "-L", "-A", UA]
    if referer:
        cmd += ["-e", referer]
    if out:
        cmd += ["-o", out, "-w", "%{http_code}\t%{size_download}"]
    cmd.append(url)
    r = subprocess.run(cmd, capture_output=True, text=True)
    return r.stdout


def untemplate(s):
    """JS 템플릿 리터럴 이스케이프 해제 -> 순수 JSON 텍스트."""
    buf, i, n = [], 0, len(s)
    while i < n:
        c = s[i]
        if c == "\\" and i + 1 < n and s[i + 1] in "\\`$":
            buf.append(s[i + 1])
            i += 2
        else:
            buf.append(c)
            i += 1
    return "".join(buf)


def main():
    os.makedirs(OUT + "/swagger", exist_ok=True)
    os.makedirs(OUT + "/guides", exist_ok=True)
    index = []

    for pk, label in DATASETS:
        page_url = f"https://www.data.go.kr/data/{pk}/openapi.do"
        html_path = f"{OUT}/.page_{pk}.html"
        status = curl(page_url, out=html_path)
        html = open(html_path, encoding="utf-8", errors="replace").read()

        entry = {"pk": pk, "label": label, "pageUrl": page_url, "http": status}

        # --- swagger ---
        m = re.search(r"const swaggerJson\s*=\s*`(.*?)`\s*;", html, re.S)
        if m:
            try:
                spec = json.loads(untemplate(m.group(1)))
            except json.JSONDecodeError as e:
                entry["swaggerError"] = f"parse: {e}"
                spec = None
            if spec:
                host = spec.get("host", "")
                svc = host.rstrip("/").split("/")[-1]
                fn = f"{OUT}/swagger/{svc or pk}.swagger.json"
                with open(fn, "w", encoding="utf-8") as f:
                    json.dump(spec, f, ensure_ascii=False, indent=2)
                ops = sorted(p.lstrip("/") for p in spec.get("paths", {}))
                entry.update(
                    service=svc,
                    host=host,
                    title=spec.get("info", {}).get("title", ""),
                    description=spec.get("info", {}).get("description", ""),
                    operations=ops,
                    swaggerFile=os.path.relpath(fn, OUT),
                )
        else:
            entry["swaggerError"] = "not found in page"

        # --- 참고문서 ---
        files = []
        for fm in re.finditer(
            r'file-name">([^<]+)</div>.*?fn_fileDownload\(\'([^\']+)\',\'([^\']+)\'\)',
            html,
            re.S,
        ):
            name, atch, sn = fm.group(1).strip(), fm.group(2), fm.group(3)
            dl = (
                "https://www.data.go.kr/cmm/cmm/fileDownload.do"
                f"?atchFileId={atch}&fileDetailSn={sn}"
            )
            safe = re.sub(r"[\\/:*?\"<>|]", "_", name)
            dest = f"{OUT}/guides/{safe}"
            res = curl(dl, out=dest, referer=page_url)
            code, size = (res.split("\t") + ["", ""])[:2]
            files.append({"name": name, "url": dl, "http": code, "bytes": int(size or 0)})
        entry["guides"] = files

        # --- 트래픽/심의 등 메타 ---
        tm = re.search(r"개발계정\s*:\s*([\d,]+)", html)
        if tm:
            entry["devTraffic"] = tm.group(1)

        index.append(entry)
        print(
            f"[{pk}] {label}: swagger={'OK' if 'operations' in entry else entry.get('swaggerError')} "
            f"ops={len(entry.get('operations', []))} guides={len(files)}",
            flush=True,
        )
        os.remove(html_path)

    with open(f"{OUT}/index.json", "w", encoding="utf-8") as f:
        json.dump(index, f, ensure_ascii=False, indent=2)
    print("\nwrote", f"{OUT}/index.json")


if __name__ == "__main__":
    main()
