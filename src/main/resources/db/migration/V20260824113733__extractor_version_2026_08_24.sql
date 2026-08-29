-- 추출기 버전 2026-08-11.1 → 2026-08-24.1 상승에 따른 재추출 범위 축소.
--
-- 이번 상승에서 바뀐 파서는 둘뿐이다.
--   1) DocumentTextExtractor.extractXlsx — 수식 셀이 수식 문자열이 아니라 값으로 나온다.
--      (실측: 표본 12건에서 엑셀이 보여주는 숫자 3,502개 중 1,910개(54.5%)만 복구되던 것이
--       수정 후 사실상 전량으로 올랐다. 산출내역서의 단가·금액이 통째로 비어 있었다.)
--   2) DocumentSniffer.sniffZip — docx·pptx 를 'zip' 컨테이너가 아니라 제 파서로 보낸다.
--      (그전에는 빈 본문이 done+needs_ocr 로 남았다: docx 235건(규격서 14건 포함)·pptx 56건.)
--
-- 나머지 형식(PDF·HWP·HWPX·HML·HTML·TXT)의 파서는 한 줄도 건드리지 않았다. 같은 입력에
-- 같은 출력이 나온다. 그래서 이 행들은 새 버전이 뽑은 것과 같다고 표시해 재추출에서 뺀다.
--
-- 왜 굳이 빼는가. 청구 질의(DocumentIndexRepository.buildClaimSql)가 extractor_version 이
-- 다른 done/skip 을 전부 집어 간다. 그대로 두면 65,857행 · 약 28.3GB 를 나라장터에서 다시
-- 내려받는다 — 결과가 바이트 단위로 같은 줄 알면서 받는 것이다. 실제로 다시 받아야 하는
-- 것은 21,879행 · 약 20.4GB 다.
--
-- 확장자가 없는 행(file_ext IS NULL)은 뺄 수 없다. 이름으로 형식을 알 수 없어 내용 스니핑
-- 결과가 xlsx·docx·zip 일 수 있고, 그러면 이번 수정의 대상이다.
UPDATE bid_notice_document
   SET extractor_version = '2026-08-24.1'
 WHERE status IN ('done', 'skip')
   AND extractor_version = '2026-08-11.1'
   AND file_ext IN ('pdf', 'hwp', 'hwpx', 'hml', 'htm', 'html', 'txt', 'hwtx', 'hwt');
