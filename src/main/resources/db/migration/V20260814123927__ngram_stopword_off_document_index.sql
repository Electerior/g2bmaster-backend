-- ============================================================
-- V20260814123927__ngram_stopword_off_document_index.sql
-- 첨부 본문 FULLTEXT 를 stopword 없이 재색인한다.
--
-- 왜 필요한가:
--   바로 앞 마이그레이션(V20260814123925)과 같은 이유다 — ngram 파서는 stopword 를
--   '같으면'이 아니라 '포함하면' 버리는데, 기본 목록에 한 글자 'a'·'i' 가 들어 있어
--   a 나 i 가 든 모든 2글자 토큰이 색인에서 사라진다. 사유·실측은 그 파일 머리주석에
--   전부 적어 두었다. 여기서는 같은 처방을 첨부 본문 색인에 적용한다.
--
--   "파일 내" 검색은 규격서에 박힌 영문 모델명을 찾는 것이 주 용도라(AMD·API·RAM·
--   DDR4·SATA) 공고 제목보다 오히려 피해가 크다.
--
-- 왜 파일을 나눴는가:
--   재색인 시간이 두 자릿수 분 단위다. bid_notice_document 는 2026-08-14 기준
--   95,235행 / 본문 2.9GB 다. V20260811171600 머리주석의 실측이 27,500행·1.3GB 에
--   FULLTEXT 최초 생성 10분 42초였으니, 데이터가 2배 남짓 늘어난 지금은 20~30분대로
--   본다. FULLTEXT 추가는 LOCK=NONE 을 지원하지 않아 그동안 이 테이블 쓰기가 막힌다.
--
--   공고 통합검색(AMD 오탐)은 앞 파일만으로 해결된다. 그쪽을 먼저 내보내고, 이 파일이
--   실린 배포는 **첨부 추출 스케줄러를 세운 정비 창에서** 하라는 뜻으로 분리했다.
--   두 파일이 한 배포에 같이 실리면 기동이 그 시간만큼 늦어진다.
--
-- 주의:
--   - SET 을 다시 한다. 세션 변수라 앞 마이그레이션의 설정이 여기까지 따라오지 않는다.
--   - ft_stopword 테이블은 앞 마이그레이션이 만든다. 이 파일은 그것을 참조만 한다.
--   - 머지된 뒤에는 이 파일을 고치지 않는다. Flyway 가 체크섬을 들고 있어서,
--     이미 적용한 사람의 앱이 기동하지 않는다. 정정은 새 파일로 한다.
--   - ENUM 은 말미 append 만 한다(INSTANT). 재배열·삭제는 ALGORITHM=COPY 다.
-- ============================================================


-- ============================================================
-- 1. 이 세션에 빈 stopword 목록 적용
--    DB 이름을 박지 않는 이유는 앞 파일과 같다 — SET 은 식을 못 받으므로
--    DATABASE() 를 문자열로 조립한다.
-- ============================================================
SET @g2b_sw_sql = CONCAT('SET SESSION innodb_ft_user_stopword_table = ''',
                         DATABASE(), '/ft_stopword''');
PREPARE g2b_sw_stmt FROM @g2b_sw_sql;
EXECUTE g2b_sw_stmt;
DEALLOCATE PREPARE g2b_sw_stmt;


-- ============================================================
-- 2. 첨부 본문 색인 재생성
--
--    **DROP 과 ADD 를 한 ALTER 에 묶지 말 것.** 묶으면 MySQL 이 결과 정의가 같다고
--    보고 아무 일도 하지 않는다 — 오류도 경고도 없이 옛 색인이 그대로 남는다.
--    앞 마이그레이션(V20260814123925)에서 실제로 겪었고, 거기 주석에 실측을 남겼다.
--
--    여기가 긴 작업이다. 진행 중 bid_notice_document 쓰기가 막히므로
--    첨부 추출 스케줄러가 도는 시간대는 피할 것.
--
--    끝난 뒤 이것으로 확인한다 — 두 값이 같아야 한다:
--        SELECT COUNT(*) FROM bid_notice_document
--         WHERE MATCH(body_text) AGAINST ('"AMD"' IN BOOLEAN MODE);
--        SELECT COUNT(*) FROM bid_notice_document WHERE body_text LIKE '%AMD%';
-- ============================================================
ALTER TABLE `bid_notice_document` DROP INDEX `ft_doc_body`;

ALTER TABLE `bid_notice_document`
  ADD FULLTEXT KEY `ft_doc_body` (`body_text`) WITH PARSER ngram;
