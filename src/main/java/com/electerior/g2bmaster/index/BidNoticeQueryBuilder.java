package com.electerior.g2bmaster.index;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 검색 조건 → SQL {@code WHERE} 절.
 *
 * <p>이 클래스에는 DB 접근이 없다(순수 함수). 조건 조합이 열 가지가 넘어 조합 폭발을 눈으로
 * 확인해야 하는데, 저장소 안에 섞여 있으면 테스트하려고 DB 를 띄워야 하기 때문이다.
 *
 * <h2>한국어 전문검색의 두 가지 함정</h2>
 * <ol>
 *   <li><b>ngram 파서 없이는 부분일치가 안 된다.</b> MySQL 기본 파서는 공백으로 끊어서
 *       '서버'로 '노트북서버구매'를 못 찾는다. 그래서 V7 이 {@code WITH PARSER ngram} 을 걸었다.</li>
 *   <li><b>ngram 은 토큰 크기(기본 2) 미만의 낱말을 통째로 버린다.</b> 한 글자 검색어는
 *       MATCH 로는 <em>절대</em> 걸리지 않는다 — 0건이 아니라 '조용히 무시'라 더 나쁘다.
 *       그래서 한 글자 낱말만 따로 모아 {@code LIKE} 로 떨어뜨린다({@link #SHORT_TERM_LENGTH}).</li>
 * </ol>
 *
 * <h2>조건을 두 묶음으로 나눠 들고 있는 이유</h2>
 * <p>{@link #attachmentScope(boolean)} 가 켜지면 저장소가 <b>공고 텍스트 브랜치와 첨부 본문
 * 브랜치를 UNION</b> 한다. 두 브랜치는 키워드 조건만 다르고 <b>필터는 똑같이</b> 걸려야 한다 —
 * '경기도 · 마감 전'으로 좁힌 검색이 첨부에서 걸렸다는 이유로 부산 공고를 데려오면 안 된다.
 * 그래서 {@link #filters} 와 {@link #keywordConditions} 를 따로 쌓고, 저장소가 필요한 조합으로
 * 다시 붙인다. 한 리스트에 섞어 두면 브랜치마다 무엇을 빼고 넣을지 문자열을 잘라내야 한다.
 */
public final class BidNoticeQueryBuilder {

	/** ngram 토큰 크기(MySQL {@code ngram_token_size} 기본값). 이 미만은 MATCH 가 버린다. */
	private static final int SHORT_TERM_LENGTH = 2;

	/** 낱말 수 상한. 늘어날수록 MATCH 식이 길어지고 LIKE 폴백이 급격히 비싸진다. */
	private static final int MAX_TERMS = 8;

	/**
	 * 조립된 조건.
	 *
	 * @param sql             {@code " WHERE …"} (조건이 없으면 빈 문자열). 공고 텍스트만 보는 경로가 쓴다
	 * @param params          바인딩 파라미터
	 * @param relevanceSelect SELECT 목록에 덧붙일 관련도 식. 전문검색이 없으면 {@code , 0 AS relevance}
	 * @param fullText        MATCH 를 실제로 쓰는가 — 정렬 기본값을 관련도로 할지 판단한다
	 * @param filterSql       키워드를 뺀 필터만의 결합({@code WHERE} 없음). UNION 의 <b>두 브랜치에 공통</b>으로 붙는다
	 * @param keywordSql      공고 텍스트 키워드 조건만의 결합({@code WHERE} 없음)
	 * @param attachment      첨부 본문 스코프. 켜지 않았으면 {@code null}
	 */
	public record Where(String sql, Map<String, Object> params, String relevanceSelect, boolean fullText,
			String filterSql, String keywordSql, Attachment attachment) {

		/**
		 * 첨부 본문 브랜치가 쓸 불리언 질의.
		 *
		 * <p>{@code includeQuery} 는 <b>양(陽) 낱말만</b> 담는다. 제외 낱말을 여기 섞으면 그 파일
		 * 하나가 후보에서 빠질 뿐 공고는 다른 첨부로 여전히 걸린다 — 제외는 공고 단위라야 하므로
		 * {@code excludeQuery} 로 따로 뽑아 저장소가 반조인(anti-join)으로 건다.
		 *
		 * @param includeQuery 첨부에서 찾을 낱말. {@code +"서버" "스토리지"} 꼴
		 * @param excludeQuery 이 낱말이 첨부에 있으면 공고째로 뺀다. {@code "임대" "렌탈"} 꼴(하나라도 걸리면 제외)
		 * @param skippedTerms 첨부 범위에서 건너뛴 낱말 — 한 글자라 ngram 이 못 본다. 화면이 그 사실을 표시한다
		 */
		public record Attachment(String includeQuery, String excludeQuery, List<String> skippedTerms) {

			public boolean hasInclude() {
				return includeQuery != null && !includeQuery.isBlank();
			}

			public boolean hasExclude() {
				return excludeQuery != null && !excludeQuery.isBlank();
			}
		}

		/** 첨부 브랜치를 실제로 UNION 해야 하는가. 제외만 있으면 브랜치 없이 반조인만 건다. */
		public boolean unionsAttachments() {
			return attachment != null && attachment.hasInclude();
		}

		/** 첨부 반조인(제외)을 걸어야 하는가. */
		public boolean excludesByAttachment() {
			return attachment != null && attachment.hasExclude();
		}
	}

	/** 키워드를 뺀 조건 — UNION 의 두 브랜치에 공통으로 붙는다. */
	private final List<String> filters = new ArrayList<>();

	/** 공고 텍스트(제목·본문) 키워드 조건. 첨부 브랜치에는 붙이지 않는다. */
	private final List<String> keywordConditions = new ArrayList<>();

	private final Map<String, Object> params = new LinkedHashMap<>();
	private String relevanceSelect = ", 0 AS relevance";
	private boolean fullText;
	private int paramSeq;

	/** 첨부 본문까지 볼 것인가. {@code BidNoticeSearchService} 가 엔드포인트에 따라 정한다. */
	private boolean attachmentScope;

	private String attachmentInclude = "";
	private String attachmentExclude = "";
	private final List<String> attachmentSkipped = new ArrayList<>();

	// activeOnly 는 다른 필터와 달리 조건을 즉시 쌓지 않고 build() 에서 조립한다 —
	// category 지정 여부에 따라 모양이 달라지는데, 체인 호출 순서에 의미를 실으면
	// buildBuilder() 의 줄 순서를 바꾸는 순간 소리 없이 깨지기 때문이다.
	private boolean activeOnly;
	private boolean categorySpecified;

	/**
	 * 자유 검색어.
	 *
	 * @param andTerms 모두 포함해야 하는 낱말
	 * @param orTerms  하나라도 포함하면 되는 낱말
	 * @param notTerms 포함되면 제외할 낱말
	 */
	public BidNoticeQueryBuilder keywords(List<String> andTerms, List<String> orTerms, List<String> notTerms) {
		List<String> and = capped(andTerms);
		List<String> or = capped(orTerms);
		List<String> not = capped(notTerms);

		StringBuilder booleanQuery = new StringBuilder();
		// 첨부 본문 브랜치용. 양 낱말과 제외 낱말을 따로 든다 — 이유는 Where.Attachment 주석 참고.
		StringBuilder docInclude = new StringBuilder();
		StringBuilder docExclude = new StringBuilder();
		// 양(陽) 낱말 수를 세어 둔다 — MATCH 를 쓸 수 있는지가 여기에 달렸다(아래 참고).
		// 조립된 문자열을 정규식으로 되읽어 판정하면 따옴표 안의 문자에 걸려 틀린다.
		int positives = 0;

		// MATCH 로 감당되는 낱말과, 너무 짧아 LIKE 로 내려야 하는 낱말을 가른다.
		for (String term : and) {
			if (isShort(term)) {
				addLike(term, true);
				attachmentSkipped.add(term);
			}
			else {
				booleanQuery.append('+').append(quote(term)).append(' ');
				docInclude.append('+').append(quote(term)).append(' ');
				positives++;
			}
		}
		for (String term : or) {
			if (isShort(term)) {
				// OR 낱말이 한 글자면 LIKE 로 강제 조건을 걸 수 없다(하나만 만족해도 되므로).
				// 아래에서 OR 묶음으로 따로 처리한다.
				attachmentSkipped.add(term);
				continue;
			}
			booleanQuery.append(quote(term)).append(' ');
			docInclude.append(quote(term)).append(' ');
			positives++;
		}
		for (String term : not) {
			if (isShort(term)) {
				addLike(term, false);
				attachmentSkipped.add(term);
			}
			else {
				booleanQuery.append('-').append(quote(term)).append(' ');
				// 무기호 = 불리언 모드의 OR. "이 중 하나라도 들어 있으면 그 공고를 뺀다".
				docExclude.append(quote(term)).append(' ');
			}
		}
		attachmentInclude = positives > 0 ? docInclude.toString().trim() : "";
		attachmentExclude = docExclude.toString().trim();

		// 한 글자 OR 낱말들은 "이 중 하나라도" 라는 하나의 괄호 조건이 된다.
		List<String> shortOr = or.stream().filter(BidNoticeQueryBuilder::isShort).toList();
		if (!shortOr.isEmpty()) {
			List<String> parts = new ArrayList<>();
			for (String term : shortOr) {
				String key = nextKey("kw");
				params.put(key, "%" + escapeLike(term) + "%");
				parts.add("(n.notice_name LIKE :" + key + " ESCAPE '!' "
						+ "OR n.notice_body LIKE :" + key + " ESCAPE '!')");
			}
			keywordConditions.add("(" + String.join(" OR ", parts) + ")");
		}

		/*
		 * 양(+/무기호) 낱말이 하나도 없으면 MATCH 를 쓰지 않는다.
		 * MySQL 불리언 모드에서 '-foo' 만 있는 식은 아무 행도 돌려주지 않는다 —
		 * "제외만 지정한 검색"이 0건이 되어 버리는데, 사용자가 기대하는 것은
		 * "그것만 뺀 전체"다. 제외 낱말은 이미 LIKE 로도 걸리므로 MATCH 를 빼면 된다.
		 */
		String query = booleanQuery.toString().trim();
		if (positives > 0) {
			params.put("ftQuery", query);
			keywordConditions.add("MATCH(n.notice_name, n.notice_body) AGAINST (:ftQuery IN BOOLEAN MODE)");
			relevanceSelect = ", MATCH(n.notice_name, n.notice_body) "
					+ "AGAINST (:ftQuery IN BOOLEAN MODE) AS relevance";
			fullText = true;
		}
		else if (!query.isEmpty()) {
			// 제외 낱말만 남은 경우 — MATCH 없이 LIKE 로만 뺀다.
			for (String term : not) {
				if (!isShort(term)) {
					addLike(term, false);
				}
			}
		}
		return this;
	}

	// ── 단순 필터 ───────────────────────────────────────────────────────────

	public BidNoticeQueryBuilder category(NoticeCategory category) {
		categorySpecified = category != null;
		return equalsIfPresent("n.category", category == null ? null : category.name(), "category");
	}

	public BidNoticeQueryBuilder state(NoticeState state) {
		return equalsIfPresent("n.state", state == null ? null : state.name(), "state");
	}

	public BidNoticeQueryBuilder businessDivision(BusinessDivision division) {
		return equalsIfPresent("n.business_division", division == null ? null : division.name(), "division");
	}

	/** 공고 출처(나라장터/누리장터/D2B). 미지정이면 전 소스 통합 검색이다. */
	public BidNoticeQueryBuilder source(NoticeSource source) {
		return equalsIfPresent("n.source", source == null ? null : source.name(), "source");
	}

	public BidNoticeQueryBuilder noticeInstitutionCode(String code) {
		return equalsIfPresent("n.notice_institution_code", trimToNull(code), "ntceInsttCd");
	}

	public BidNoticeQueryBuilder demandInstitutionCode(String code) {
		return equalsIfPresent("n.demand_institution_code", trimToNull(code), "dmndInsttCd");
	}

	public BidNoticeQueryBuilder beforeSpecRgstNo(String no) {
		return equalsIfPresent("n.before_spec_rgst_no", trimToNull(no), "beforeSpec");
	}

	/**
	 * 지역. {@code region} 은 콤마로 여러 지역을 담으므로 정확일치가 아니라 포함으로 건다.
	 *
	 * <p>지역 제한이 없는 공고(빈 문자열 = 전국)는 <b>어떤 지역으로 좁혀도 함께 나온다</b>.
	 * 서울 업체가 "서울"로 검색했을 때 참가할 수 있는 전국 공고가 빠지면 그게 더 큰 손해다.
	 */
	public BidNoticeQueryBuilder region(String region) {
		String value = trimToNull(region);
		if (value == null) {
			return this;
		}
		String key = nextKey("region");
		params.put(key, "%" + escapeLike(value) + "%");
		filters.add("(n.region LIKE :" + key + " ESCAPE '!' OR n.region = '')");
		return this;
	}

	/** 세부품명번호. 접두 일치라 상위 분류(예: {@code 4110}) 로도 훑을 수 있다. */
	public BidNoticeQueryBuilder detailProductCode(String code) {
		String value = trimToNull(code);
		if (value == null) {
			return this;
		}
		String key = nextKey("prdct");
		params.put(key, escapeLike(value) + "%");
		filters.add("n.detail_product_code LIKE :" + key + " ESCAPE '!'");
		return this;
	}

	/**
	 * 발주기관명 — 공고기관·수요기관 둘 다에서 찾는다.
	 *
	 * <p>둘을 OR 로 묶는 이유: 사용자는 "이 기관이 내는 공고"를 찾는 것이지 그 기관이
	 * 공고기관인지 수요기관인지를 구분해서 묻지 않는다. 조달청이 대행 공고하는 건은
	 * 공고기관이 조달청이고 수요기관이 실제 발주처라, 한쪽만 보면 그 건이 통째로 빠진다.
	 */
	public BidNoticeQueryBuilder institutionName(String name) {
		String value = trimToNull(name);
		if (value == null) {
			return this;
		}
		String key = nextKey("instt");
		params.put(key, "%" + escapeLike(value) + "%");
		filters.add("(n.notice_institution_name LIKE :" + key + " ESCAPE '!'"
				+ " OR n.demand_institution_name LIKE :" + key + " ESCAPE '!')");
		return this;
	}

	public BidNoticeQueryBuilder officerName(String name) {
		String value = trimToNull(name);
		if (value == null) {
			return this;
		}
		String key = nextKey("ofcl");
		params.put(key, "%" + escapeLike(value) + "%");
		filters.add("n.officer_name LIKE :" + key + " ESCAPE '!'");
		return this;
	}

	// ── 기간 ────────────────────────────────────────────────────────────────

	/** 공고 생성일 구간. {@code to} 는 그 날 23:59:59 까지 포함한다(호출부가 넘겨준 값 그대로 쓴다). */
	public BidNoticeQueryBuilder createdBetween(java.time.LocalDateTime from, java.time.LocalDateTime to) {
		if (from != null) {
			params.put("createdFrom", from);
			filters.add("n.created_date >= :createdFrom");
		}
		if (to != null) {
			params.put("createdTo", to);
			filters.add("n.created_date <= :createdTo");
		}
		return this;
	}

	public BidNoticeQueryBuilder closeBetween(java.time.LocalDateTime from, java.time.LocalDateTime to) {
		if (from != null) {
			params.put("closeFrom", from);
			filters.add("n.close_date >= :closeFrom");
		}
		if (to != null) {
			params.put("closeTo", to);
			filters.add("n.close_date <= :closeTo");
		}
		return this;
	}

	/**
	 * 아직 참여할 수 있는 공고만. 실제 조건은 {@link #build()} 가 두 갈래로 조립한다.
	 *
	 * <ol>
	 *   <li><b>마감 판정은 category 가 아니라 마감일시로.</b> 스위퍼는 주기적으로 도는 것이라
	 *       방금 마감된 건이 아직 '입찰'로 남아 있을 수 있는데, 이 필터까지 그 지연을
	 *       물려받으면 사용자가 이미 닫힌 공고에 시간을 쓴다.</li>
	 *   <li><b>단계를 고르지 않았으면 입찰 문서({@code IN ('입찰','마감')})로 좁힌다.</b>
	 *       계획은 마감일시가 아예 없어(NULL) 마감일시 조건을 무조건 통과하고, 사전규격의
	 *       마감일시는 입찰마감이 아니라 <em>의견등록</em>마감이다. 이 스코프가 없으면
	 *       "지금 참여할 수 있는 공고"의 다섯에 하나가 참여 대상이 아닌 문서였다(실측 21.5%).
	 *       단계를 직접 고른 검색에는 걸지 않는다 — '계획'+마감 전만 보기가 0건이 되면 안 된다.</li>
	 * </ol>
	 */
	public BidNoticeQueryBuilder activeOnly(boolean active) {
		this.activeOnly = active;
		return this;
	}

	// ── 금액 ────────────────────────────────────────────────────────────────

	/**
	 * 금액 구간.
	 *
	 * <p><b>추정가격이 아니라 {@code filter_amount} 를 본다.</b> 추정가격 키를 가진 행은
	 * 나라장터의 입찰·마감·계획뿐이라, 이 조건이 {@code estimated_price} 를 보던 때는
	 * {@code ?minAmount=1} 한 줄에 사전규격 12,119건과 누리·D2B 2,045건이 통째로 사라졌다
	 * (전체의 28%). 사전규격은 배정예산을 가지고 있어 금액을 아는데도 빠졌다.
	 * {@code filter_amount} 는 추정가격→배정예산→기준금액→기초예비가격 순으로 하나를 고른
	 * 생성 컬럼이다 — 무엇이 골라졌는지는 응답의 {@code amountKind} 로 화면까지 내려간다
	 * ({@code V20260814113541}, {@link BidNoticeSearchService#amountOf}).
	 *
	 * <p>금액이 아예 없는 행(실측 2,180건)은 {@code NULL} 이라 이 조건에서 빠진다. 줄일 수 없는
	 * 손실이라 화면이 그 사실을 문장으로 적는다 — 조용히 빼는 것만은 하지 않는다.
	 *
	 * <p>식을 다시 인라인하지 말 것 — 컬럼에 함수를 씌우거나 {@code COALESCE} 를 여기에 펼치는
	 * 순간 {@code ix_bid_notice_filter_amount} 를 못 쓰게 되고, 증상이 '느려짐' 뿐이라
	 * 리뷰에서 잡히지 않는다. 그래서 {@code BidNoticeQueryBuilderTest} 가 SQL 문자열을 직접 본다.
	 */
	public BidNoticeQueryBuilder amountBetween(Long min, Long max) {
		if (min == null && max == null) {
			return this;
		}
		if (min != null) {
			params.put("minAmount", min);
			filters.add("n.filter_amount >= :minAmount");
		}
		if (max != null) {
			params.put("maxAmount", max);
			filters.add("n.filter_amount <= :maxAmount");
		}
		return this;
	}

	// ── 첨부 스코프 ─────────────────────────────────────────────────────────

	/**
	 * 첨부 본문까지 검색 대상에 넣는다.
	 *
	 * <p>켜도 <b>조건 문자열은 달라지지 않는다</b> — 첨부는 별도 브랜치라 SQL 조립이 저장소
	 * 몫이고, 여기서는 그 브랜치가 쓸 불리언 질의만 넘긴다. 조건이 아니라 재료를 넘기는
	 * 형태라야 {@code sql()} 을 쓰는 기존 경로(공고 텍스트만 보는 {@code /text})가 그대로 산다.
	 */
	public BidNoticeQueryBuilder attachmentScope(boolean include) {
		this.attachmentScope = include;
		return this;
	}

	// ── 조립 ────────────────────────────────────────────────────────────────

	public Where build() {
		if (activeOnly) {
			if (!categorySpecified) {
				filters.add("n.category IN ('입찰', '마감')");
			}
			filters.add("(n.close_date IS NULL OR n.close_date >= NOW(6))");
		}
		// 키워드를 앞에 둔다 — 두 리스트로 갈리기 전과 같은 순서라야 실행계획이 그대로다.
		List<String> all = new ArrayList<>(keywordConditions);
		all.addAll(filters);
		String sql = all.isEmpty() ? "" : "\n WHERE " + String.join("\n   AND ", all);

		Where.Attachment attachment = null;
		if (attachmentScope) {
			if (!attachmentInclude.isEmpty()) {
				params.put("ftDocQuery", attachmentInclude);
			}
			if (!attachmentExclude.isEmpty()) {
				params.put("ftDocExclude", attachmentExclude);
			}
			attachment = new Where.Attachment(attachmentInclude, attachmentExclude,
					List.copyOf(attachmentSkipped));
		}
		return new Where(sql, Map.copyOf(params), relevanceSelect, fullText,
				String.join("\n   AND ", filters), String.join("\n   AND ", keywordConditions), attachment);
	}

	// ── 내부 ────────────────────────────────────────────────────────────────

	private BidNoticeQueryBuilder equalsIfPresent(String column, String value, String name) {
		if (value == null) {
			return this;
		}
		String key = nextKey(name);
		params.put(key, value);
		filters.add(column + " = :" + key);
		return this;
	}

	/**
	 * 제목·본문 어느 쪽이든 포함(또는 미포함)해야 한다.
	 *
	 * <p>필터가 아니라 <b>키워드 조건</b>이다 — 첨부 브랜치에 붙으면 안 된다. 첨부 브랜치는
	 * 자기 본문을 보는 것이 목적인데 여기에 공고 제목 LIKE 가 따라붙으면 교집합이 되어,
	 * "제목엔 없고 규격서에만 있는 공고"라는 이 기능의 존재 이유가 사라진다.
	 */
	private void addLike(String term, boolean include) {
		String key = nextKey("kw");
		params.put(key, "%" + escapeLike(term) + "%");
		String match = "(n.notice_name LIKE :" + key + " ESCAPE '!' "
				+ "OR n.notice_body LIKE :" + key + " ESCAPE '!')";
		keywordConditions.add(include ? match : "NOT " + match);
	}

	private static boolean isShort(String term) {
		return term.length() < SHORT_TERM_LENGTH;
	}

	private String nextKey(String prefix) {
		return prefix + (paramSeq++);
	}

	private static List<String> capped(List<String> terms) {
		if (terms == null || terms.isEmpty()) {
			return List.of();
		}
		return terms.stream()
				.filter(t -> t != null && !t.isBlank())
				.map(String::trim)
				.distinct()
				.limit(MAX_TERMS)
				.toList();
	}

	/**
	 * 불리언 모드 낱말을 큰따옴표로 감싼다.
	 *
	 * <p>감싸는 이유는 사용자가 친 {@code +}·{@code -}·{@code *}·{@code (} 가 연산자로 해석돼
	 * 문법 오류(→ 500)를 내는 것을 막기 위해서다. 따옴표 안에서는 전부 literal 이다.
	 * 낱말 자체에 든 따옴표만 지운다 — 이스케이프 문법이 없어 지우는 것이 유일한 방법이다.
	 */
	private static String quote(String term) {
		return '"' + term.replace("\"", " ").trim() + '"';
	}

	/**
	 * {@code LIKE} 와일드카드 무력화.
	 *
	 * <p>{@code %}·{@code _} 를 그대로 두면 사용자가 친 {@code _} 가 '아무 글자 하나'가 되어
	 * 결과가 소리 없이 넓어진다. SQL 표준 {@code ESCAPE} 절과 짝을 이룬다.
	 */
	private static String escapeLike(String value) {
		return value.replace("!", "!!").replace("%", "!%").replace("_", "!_");
	}

	private static String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}
