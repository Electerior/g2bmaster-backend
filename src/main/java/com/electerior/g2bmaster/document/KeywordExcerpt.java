package com.electerior.g2bmaster.document;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 첨부 본문에서 키워드가 걸린 대목을 찾아 발췌한다 — 원본 {@code server.js} 의
 * {@code keywordPattern} · {@code findKeywordMatches} · {@code splitPassages} ·
 * {@code bestKeywordExcerpt} 이식.
 *
 * <p><b>왜 DB 가 아니라 여기서 판정하는가.</b> SQL 의 FULLTEXT 는 <b>후보를 좁히는 데만</b>
 * 쓴다. ngram(2) 파서는 '정수기' 를 {@code 정수}+{@code 수기} 로 쪼개 찾으므로 우리가 원하는
 * 부분일치와 의미가 정확히 같지 않고, 무엇보다 <b>어느 키워드가 걸렸는지</b>와 <b>어느 대목인지</b>를
 * 알려주지 못한다. 화면은 그 둘을 다 보여준다. 그래서 최종 판정은 여기가 한다.
 *
 * <p><b>매칭 규칙 두 가지가 비자명하다.</b>
 * <ul>
 *   <li><b>영문·숫자 키워드는 낱말 경계를 본다.</b> {@code RTX} 로 검색했을 때 {@code MRTXP}
 *       같은 부품번호가 걸리면 안 된다. 한글은 교착어라 낱말 경계라는 것이 없으므로 부분일치다
 *       — '서버' 로 '노트북서버구매' 가 걸려야 한다.</li>
 *   <li><b>공백은 느슨하게 본다.</b> {@code "저장 장치"} 로 검색하면 {@code 저장장치} ·
 *       {@code 저장-장치} · {@code 저장_장치} 가 모두 걸린다. 규격서는 같은 말을 표 안에서
 *       띄어쓰기만 달리해 쓰는 일이 흔하다.</li>
 * </ul>
 */
public final class KeywordExcerpt {

	/** 한 대목의 길이. 이보다 길어지면 발췌가 화면에서 문단 덩어리로 보인다. */
	private static final int PASSAGE_CHARS = 650;
	/** 발췌 창 — 걸린 지점의 앞뒤. */
	private static final int LEAD_CHARS = 120;
	private static final int TRAIL_CHARS = 420;
	/** 한 대목에서 셀 매칭 상한. 단가계약 공고는 같은 낱말이 수백 번 나온다. */
	private static final int MAX_MATCHES = 200;

	/** 영문·숫자로만 이뤄진 키워드인가 — 낱말 경계를 적용할지 가른다. */
	private static final Pattern ASCII_ONLY = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9+.#\\-/\\s]*$");
	/** 문장 끝. 한국어는 마침표 없이 '~다' 로 끝나는 줄이 많아 어미도 함께 본다. */
	private static final Pattern SENTENCE_END =
			Pattern.compile("(?<=[.!?。！？]|다\\.|요\\.|임\\.|함\\.)\\s+|\\n+");
	private static final Pattern WHITESPACE = Pattern.compile("\\s+");

	private KeywordExcerpt() {
	}

	/**
	 * 본문에서 가장 좋은 발췌 한 대목을 고른다.
	 *
	 * @return 걸린 키워드가 하나도 없으면 {@code null}
	 */
	public static Result best(String text, List<String> keywords) {
		List<Term> terms = compile(keywords);
		if (terms.isEmpty() || text == null || text.isBlank()) {
			return null;
		}

		String bestPassage = null;
		List<Hit> bestHits = null;
		double bestScore = Double.NEGATIVE_INFINITY;

		for (String passage : passages(text)) {
			List<Hit> hits = find(passage, terms);
			if (hits.isEmpty()) {
				continue;
			}
			// 서로 다른 키워드가 많이 걸린 대목을 크게 우대한다 — 두 조건이 함께 언급된
			// 문장이 사용자가 실제로 읽고 싶은 곳이다. 같은 낱말의 반복은 값을 조금만 올린다.
			Set<Integer> unique = new LinkedHashSet<>();
			hits.forEach(hit -> unique.add(hit.keywordIndex()));
			double score = unique.size() * 100.0
					+ Math.min(hits.size(), 20) * 3.0
					- Math.min(hits.get(0).pos() / 1000.0, 5.0);
			if (score > bestScore) {
				bestScore = score;
				bestPassage = passage;
				bestHits = hits;
			}
		}
		if (bestPassage == null) {
			return null;
		}

		Hit first = bestHits.get(0);
		int from = Math.max(0, first.pos() - LEAD_CHARS);
		int to = Math.min(bestPassage.length(), first.pos() + TRAIL_CHARS);
		String excerpt = WHITESPACE.matcher(bestPassage.substring(from, to)).replaceAll(" ").trim();

		Set<String> matched = new LinkedHashSet<>();
		bestHits.forEach(hit -> matched.add(hit.keyword()));
		return new Result(excerpt, List.copyOf(matched));
	}

	/** 본문에 키워드가 하나라도 있는가. 발췌가 필요 없을 때 쓴다. */
	public static boolean matchesAny(String text, List<String> keywords) {
		if (text == null || text.isBlank()) {
			return false;
		}
		for (Term term : compile(keywords)) {
			if (term.pattern().matcher(text).find()) {
				return true;
			}
		}
		return false;
	}

	/** 키워드 하나의 정규식. 규칙은 클래스 주석 참고. */
	static Pattern pattern(String keyword) {
		String raw = keyword == null ? "" : keyword.trim();
		if (raw.isEmpty()) {
			return null;
		}
		String body = String.join("[\\s\\-_/]*",
				List.of(raw.split("\\s+")).stream().filter(s -> !s.isEmpty()).map(Pattern::quote).toList());
		if (body.isEmpty()) {
			return null;
		}
		String regex = ASCII_ONLY.matcher(raw).matches()
				? "(^|[^A-Za-z0-9])(" + body + ")(?=$|[^A-Za-z0-9])"
				: "(" + body + ")";
		return Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	}

	/**
	 * 키워드와 그 정규식을 <b>짝지어</b> 들고 다닌다. 정규식만 모으면 빈 키워드가 걸러지면서
	 * 목록 위치가 어긋나, 걸린 키워드를 되짚을 때 엉뚱한 이름이 화면에 나간다.
	 */
	private static List<Term> compile(List<String> keywords) {
		List<Term> terms = new ArrayList<>();
		if (keywords == null) {
			return terms;
		}
		for (String keyword : keywords) {
			Pattern pattern = pattern(keyword);
			if (pattern != null) {
				terms.add(new Term(keyword.trim(), pattern));
			}
		}
		return terms;
	}

	/**
	 * 본문을 대목으로 자른다. 공백을 먼저 접는 것이 중요하다 — HWP·PDF 추출물은 셀 사이에
	 * 줄바꿈과 공백이 잔뜩 끼어 있어, 접지 않으면 한 대목에 실제 문장이 두어 개밖에 안 들어간다.
	 */
	static List<String> passages(String text) {
		String clean = WHITESPACE.matcher(text).replaceAll(" ").trim();
		List<String> passages = new ArrayList<>();
		if (clean.isEmpty()) {
			return passages;
		}
		StringBuilder buffer = new StringBuilder();
		for (String unit : SENTENCE_END.split(clean)) {
			if (unit.isEmpty()) {
				continue;
			}
			if (buffer.length() + 1 + unit.length() > PASSAGE_CHARS && buffer.length() > 0) {
				passages.add(buffer.toString().trim());
				buffer.setLength(0);
			}
			if (buffer.length() > 0) {
				buffer.append(' ');
			}
			buffer.append(unit);
		}
		if (buffer.length() > 0) {
			passages.add(buffer.toString().trim());
		}
		if (passages.isEmpty()) {
			// 문장 부호가 하나도 없는 문서(표만 있는 규격서가 그렇다)는 길이로 자른다.
			for (int i = 0; i < clean.length(); i += PASSAGE_CHARS) {
				passages.add(clean.substring(i, Math.min(clean.length(), i + PASSAGE_CHARS + 50)));
			}
		}
		return passages;
	}

	private static List<Hit> find(String passage, List<Term> terms) {
		List<Hit> hits = new ArrayList<>();
		for (int index = 0; index < terms.size(); index++) {
			Matcher matcher = terms.get(index).pattern().matcher(passage);
			while (matcher.find() && hits.size() < MAX_MATCHES) {
				// ascii 패턴은 앞 문자를 한 칸 먹고 시작하므로 그룹 2가 실제 낱말이다.
				int start = matcher.groupCount() >= 2 && matcher.group(2) != null
						? matcher.start(2)
						: matcher.start(1);
				hits.add(new Hit(terms.get(index).keyword(), index, start));
			}
		}
		hits.sort((a, b) -> Integer.compare(a.pos(), b.pos()));
		return hits;
	}

	private record Term(String keyword, Pattern pattern) {}

	private record Hit(String keyword, int keywordIndex, int pos) {}

	/** 발췌와 그 대목에서 실제로 걸린 키워드들. */
	public record Result(String excerpt, List<String> matchedKeywords) {}
}
