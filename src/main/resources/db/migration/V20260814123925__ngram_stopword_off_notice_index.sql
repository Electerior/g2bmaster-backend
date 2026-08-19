-- ============================================================
-- V20260814123925__ngram_stopword_off_notice_index.sql
-- 공고 텍스트 FULLTEXT 를 stopword 없이 재색인한다.
--
-- 왜 필요한가:
--   ngram 파서와 InnoDB 기본 stopword 목록은 같이 쓰면 안 되는 조합이다.
--
--   기본 파서는 "토큰이 stopword 와 **같으면**" 버리지만, ngram 파서는
--   "토큰이 stopword 를 **포함하면**" 버린다(MySQL 레퍼런스, ngram Full-Text Parser).
--   그 규칙 자체는 옳다 — ngram 은 구두점으로도 안 끊어서 '매,' ',설' 같이 낱말
--   경계를 넘는 쓰레기 토큰이 생기는데, 구분자를 stopword 로 지정해 걷어내려면
--   "포함" 판정이 필요하다.
--
--   문제는 규칙만 바뀌고 **목록은 낱말 파서용 영어 기능어 목록 그대로**라는 점이다.
--   그 목록에는 한 글자 'a' 와 'i' 가 들어 있다. 영어에서는 진짜 낱말이니 목록에
--   있을 자격이 있지만, "포함" 판정 아래에서 'a' 는 낱말이 아니라 글자로 작동한다.
--   ngram_token_size=2 이므로 **a 나 i 가 든 모든 2글자 토큰이 색인에서 사라진다.**
--   길이 2인 stopword(on, to, of, by, in, is, it …)도 그대로 걸린다.
--
--   그 결과가 두 방향으로 터진다(2026-08-14, bid_notice 51,524행 실측):
--
--     검색어   MATCH   LIKE    무슨 일이 일어나는가
--     ------   -----   ----    --------------------------------------------
--     AMD         62     11    [AM] 소멸 → 사실상 'MD' 검색. GMDSS·MDR 오탐
--     DATA         0     14    [DA][AT][TA] 전멸 → 조용한 0건
--     API          0      7    [AP][PI] 전멸 → 조용한 0건
--     ON           0    519    2글자 stopword
--     TO           0    321    2글자 stopword
--     ER         716    716    (대조군 — a·i 없음, 정상)
--     데이터      618    618    (대조군 — 한글은 목록에 걸릴 게 없다)
--
--   한국어는 멀쩡한데 영문 제품명·약어만 부서진다. 한국어를 위해 켠 ngram 이
--   영어용 stopword 목록과 만나서 생긴 사고다. 조달 공고문은 한글과 라틴 문자가
--   섞이는 텍스트(AMD·API·RAM·DATA·IT)라 정확히 이 지점에 걸린다.
--
--   게다가 ngram_token_size=2 에서는 3글자 이상 stopword(the, about, from …)가
--   전부 무시된다 — "흔한 낱말을 빼서 색인을 줄인다"는 원래 목적은 달성되지 않고
--   부작용만 남는다. 지금 stopword 기능이 하는 일은 영어 검색어를 부수는 것뿐이다.
--
-- 왜 서버 전역 설정(innodb_ft_enable_stopword=OFF)이 아니라 빈 테이블인가:
--   innodb_ft_user_stopword_table 은 SESSION 스코프라 이 마이그레이션 안에서 끝난다.
--   전역 설정은 docker-compose·my.cnf 를 환경마다 챙겨야 하고, 새 환경에서 빠뜨리면
--   재색인이 조용히 버그를 되살린다. 그 실패 모드가 없는 쪽을 고른다.
--   (SET GLOBAL 은 현재 세션에 반영되지 않는다 — 전역으로 가려면 기동 옵션이어야 한다.)
--
-- 확인한 것(2026-08-14):
--   - 앱 계정으로 SET 가능. root 불필요
--   - 빈 테이블을 물리면 기본 목록이 통째로 무시된다
--   - 설정은 **색인에 박힌다**. 변수를 설정하지 않은 별도 커넥션에서도 질의·INSERT 가
--     정상이었다 — 즉 애플리케이션은 아무것도 바꿀 필요가 없다
--   - 재색인 후: AMD → AMD·AMDB 만, RAM·AI·DATA·API 정상, 한글 '서버' 영향 없음
--
-- 주의:
--   - **ft_stopword 테이블을 지우지 말 것.** 0행이지만 이 색인의 설정 근거다.
--   - **앞으로 ft_bid_notice_text / ft_doc_body 를 다시 만드는 사람은 아래 SET 을
--     먼저 실행해야 한다.** 안 하면 기본 목록이 다시 들어가고, 증상은 500 이 아니라
--     '검색 결과가 좀 이상함'이라 리뷰에서 안 잡힌다.
--   - 첨부 본문(ft_doc_body)은 재색인이 길어서 다음 마이그레이션으로 분리했다.
--   - 머지된 뒤에는 이 파일을 고치지 않는다. Flyway 가 체크섬을 들고 있어서,
--     이미 적용한 사람의 앱이 기동하지 않는다. 정정은 새 파일로 한다.
--   - ENUM 은 말미 append 만 한다(INSTANT). 재배열·삭제는 ALGORITHM=COPY 다.
-- ============================================================


-- ============================================================
-- 1. 빈 stopword 목록
--    행을 넣지 않는 것이 요점이다. 이 테이블이 지정되면 InnoDB 기본 36개 목록은
--    쓰이지 않는다. 칸 이름은 `value` 여야 하고 InnoDB 여야 한다(MySQL 규약).
--
--    나중에 구분자 기반으로 ngram 경계를 끊고 싶어지면 여기에 그 문자만 넣으면 된다 —
--    ngram 이 원래 의도한 용법이 그것이다. 영어 기능어를 다시 넣을 일은 없다.
-- ============================================================
CREATE TABLE `ft_stopword` (
  `value` VARCHAR(30) NOT NULL
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
  COMMENT='ngram FULLTEXT 용 빈 stopword 목록 — 비어 있는 것이 의도다. 지우지 말 것';


-- ============================================================
-- 2. 이 세션에 적용
--    DB 이름을 박지 않는다. 스키마명이 환경마다 다를 수 있는데 SET 은 식을 못 받아서
--    DATABASE() 를 문자열로 조립해 넣는다. Flyway 플레이스홀더를 쓰지 않는 이유도
--    같다 — 해석에 실패하면 기동 자체가 막힌다.
-- ============================================================
SET @g2b_sw_sql = CONCAT('SET SESSION innodb_ft_user_stopword_table = ''',
                         DATABASE(), '/ft_stopword''');
PREPARE g2b_sw_stmt FROM @g2b_sw_sql;
EXECUTE g2b_sw_stmt;
DEALLOCATE PREPARE g2b_sw_stmt;


-- ============================================================
-- 3. 공고 텍스트 색인 재생성
--
--    **DROP 과 ADD 를 반드시 두 문장으로 나눈다.** 한 ALTER 에 묶으면
--
--        ALTER TABLE bid_notice
--          DROP INDEX ft_bid_notice_text,
--          ADD FULLTEXT KEY ft_bid_notice_text (...) WITH PARSER ngram;
--
--    MySQL 이 "결과 정의가 이전과 같다"고 보고 **아무 일도 하지 않는다**. 실측으로
--    처음 이 형태로 적용했을 때 Flyway 실행 시간이 0.115초였고 색인은 그대로였다 —
--    AMD 가 여전히 71건(LIKE 13건)이었다. 오류도 경고도 없다. 두 문장으로 나누면
--    13.0초가 걸리고 AMD 13 = LIKE 13 으로 맞는다.
--
--    성공했는지는 시간으로 판별하지 말고 아래로 확인할 것:
--        SELECT COUNT(*) FROM bid_notice
--         WHERE MATCH(notice_name, notice_body) AGAINST ('"AMD"' IN BOOLEAN MODE);
--    이 값이 notice_name/notice_body LIKE '%AMD%' 건수와 같아야 한다.
--
--    FULLTEXT 추가는 LOCK=NONE 을 지원하지 않는다 — 진행 중에는 쓰기가 막힌다.
--    51,524행 / 83MB 기준 13초다. 첨부 본문(2.9GB)과는 규모가 다르다.
-- ============================================================
ALTER TABLE `bid_notice` DROP INDEX `ft_bid_notice_text`;

ALTER TABLE `bid_notice`
  ADD FULLTEXT KEY `ft_bid_notice_text` (`notice_name`, `notice_body`) WITH PARSER ngram;
