#!/usr/bin/env python3
"""의존성 없는 docx -> markdown 변환기 (문단 + 표, 문서 순서 유지)."""
import glob
import os
import re
import sys
import xml.etree.ElementTree as ET
import zipfile

W = "{http://schemas.openxmlformats.org/wordprocessingml/2006/main}"


def para_text(p):
    parts = []
    for node in p.iter():
        if node.tag == W + "t":
            parts.append(node.text or "")
        elif node.tag == W + "tab":
            parts.append("\t")
        elif node.tag == W + "br":
            parts.append("\n")
    return "".join(parts).strip()


def cell_text(tc):
    lines = [para_text(p) for p in tc.findall(W + "p")]
    return " ".join(x for x in lines if x).replace("|", "\\|").strip()


def style_of(p):
    ps = p.find(W + "pPr")
    if ps is None:
        return ""
    st = ps.find(W + "pStyle")
    return st.get(W + "val", "") if st is not None else ""


def table_md(tbl):
    rows = []
    for tr in tbl.findall(W + "tr"):
        cells = [cell_text(tc) for tc in tr.findall(W + "tc")]
        if cells:
            rows.append(cells)
    if not rows:
        return ""
    width = max(len(r) for r in rows)
    rows = [r + [""] * (width - len(r)) for r in rows]
    out = ["| " + " | ".join(rows[0]) + " |", "|" + "---|" * width]
    for r in rows[1:]:
        out.append("| " + " | ".join(r) + " |")
    return "\n".join(out)


def convert(path):
    with zipfile.ZipFile(path) as z:
        xml = z.read("word/document.xml")
    root = ET.fromstring(xml)
    body = root.find(W + "body")
    out, blank = [], 0
    for child in body:
        if child.tag == W + "p":
            t = para_text(child)
            if not t:
                blank += 1
                continue
            st = style_of(child)
            m = re.match(r"(?:Heading|heading)(\d)", st)
            if m:
                out.append("\n" + "#" * min(int(m.group(1)) + 1, 6) + " " + t + "\n")
            elif re.match(r"^\s*(\d+(\.\d+)*)\.?\s+\S", t) and len(t) < 80:
                out.append("\n### " + t + "\n")
            else:
                out.append(t)
            blank = 0
        elif child.tag == W + "tbl":
            md = table_md(child)
            if md:
                out.append("\n" + md + "\n")
    return "\n".join(out)


def main():
    src, dst = sys.argv[1], sys.argv[2]
    os.makedirs(dst, exist_ok=True)
    for f in sorted(glob.glob(src + "/*.docx")):
        base = os.path.splitext(os.path.basename(f))[0]
        md = convert(f)
        with open(f"{dst}/{base}.md", "w", encoding="utf-8") as fh:
            fh.write(f"# {base}\n\n> 출처: 공공데이터포털 참고문서(docx) 자동 변환\n\n{md}\n")
        print(f"{base}: {len(md):,} chars")


if __name__ == "__main__":
    main()
