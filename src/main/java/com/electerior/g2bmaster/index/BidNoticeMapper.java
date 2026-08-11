package com.electerior.g2bmaster.index;

import com.electerior.g2bmaster.common.Numbers;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * 나라장터 응답 행 → {@link BidNoticeRow}.
 *
 * <p>출처가 셋(입찰공고 · 발주계획 · 사전규격)인데 목적지는 하나다. 필드 이름이 출처마다
 * 다르고(같은 '접수일시'가 {@code bidNtceDt}/{@code rcptDt} 로 온다) 없는 필드도 서로 달라서,
 * 그 차이를 흡수하는 것이 이 클래스의 일 전부다.
 *
 * <p><b>절대 예외를 던지지 않는다.</b> 한 건의 이상한 값 때문에 수천 건짜리 적재 배치가
 * 통째로 실패하는 것이 최악이다. 파싱 못 한 값은 {@code null} 로 두고 넘어간다.
 */
public final class BidNoticeMapper {

	private static final Logger log = LoggerFactory.getLogger(BidNoticeMapper.class);

	private static final ObjectMapper JSON = JsonMapper.builder().build();

	/** 첨부파일 슬롯 수. 입찰공고는 {@code ntceSpecDocUrl1..10}, 사전규격은 {@code specDocFileUrl1..5}. */
	private static final int ANNOUNCE_FILE_SLOTS = 10;
	private static final int PRESPEC_FILE_SLOTS = 5;

	/** {@code region VARCHAR(40)} 상한. 넘치면 잘라야 하는데, 낱말 중간에서 자르지 않는다. */
	private static final int REGION_MAX = 40;

	/** {@code notice_name VARCHAR(500)} 상한. */
	private static final int NAME_MAX = 500;

	/** {@code *_institution_name VARCHAR(300)} 상한. */
	private static final int INSTITUTION_MAX = 300;

	/**
	 * {@code DECIMAL(5,3)} 이 담을 수 있는 최댓값 초과 기준.
	 *
	 * <p>낙찰하한율은 백분율이라 100 이상이 나올 일이 없지만, 원본에 이상값이 섞이면
	 * INSERT 가 통째로 실패한다(MySQL strict 모드). 값 하나 때문에 배치를 잃지 않도록 버린다.
	 */
	private static final BigDecimal RATE_LIMIT = new BigDecimal("100");

	private BidNoticeMapper() {}

	// ── 입찰공고 ────────────────────────────────────────────────────────────

	/**
	 * 입찰공고 목록 행 → 색인 행.
	 *
	 * @param division 오퍼레이션이 정하는 업종. 응답의 {@code bsnsDivNm} 보다 신뢰도가 높다
	 *                 (물품 오퍼레이션이 돌려준 것은 물품이다)
	 * @param now      마감 여부 판정 기준 시각. 테스트가 고정할 수 있도록 인자로 받는다
	 * @return 공고번호가 없으면 {@code null}(색인할 수 없는 행)
	 */
	public static BidNoticeRow fromBidAnnounce(Map<String, Object> item, BusinessDivision division,
			LocalDateTime now) {
		String id = str(item.get("bidNtceNo"));
		if (id.isEmpty()) {
			return null;
		}

		LocalDateTime closeDate = date(item.get("bidClseDt"));
		// 마감일시를 모르는 공고는 '입찰'로 둔다. '마감'으로 접으면 아직 살아 있는 공고가
		// '마감 전만 보기'에서 사라진다 — 모르는 것을 끝났다고 단정하지 않는다.
		NoticeCategory category = (closeDate != null && closeDate.isBefore(now))
				? NoticeCategory.마감
				: NoticeCategory.입찰;

		List<CaretList.Entry> products = CaretList.parse(str(item.get("purchsObjPrdctList")));
		BusinessDivision resolved = division != null ? division : BusinessDivision.of(str(item.get("bsnsDivNm")));

		String body = body(
				str(item.get("bidNtceNm")),
				str(item.get("ntceInsttNm")),
				str(item.get("dminsttNm")),
				str(item.get("dtilPrdctClsfcNoNm")),
				str(item.get("prdctSpecNm")),
				str(item.get("cntrctCnclsMthdNm")),
				str(item.get("bidMethdNm")),
				str(item.get("sucsfbidMthdNm")),
				str(item.get("bidprcPsblIndstrytyNm")),
				str(item.get("refNo")),
				String.join(" ", CaretList.distinctNames(products)));

		return new BidNoticeRow(
				id,
				NoticeSource.G2B,
				orderOf(item.get("bidNtceOrd")),
				clip(str(item.get("bidNtceNm")), NAME_MAX),
				category,
				NoticeState.fromNoticeKind(str(item.get("ntceKindNm"))),
				resolved,
				// 지역은 별도 오퍼레이션에서 온다. 여기서 빈 값을 넣어도 upsert 가
				// 기존 값을 지우지 않는다(BidNoticeIndexRepository.buildUpsertSql 참고).
				"",
				str(item.get("dminsttCd")),
				clipToNull(str(item.get("dminsttNm")), INSTITUTION_MAX),
				str(item.get("ntceInsttCd")),
				clipToNull(str(item.get("ntceInsttNm")), INSTITUTION_MAX),
				trimToNull(str(item.get("bfSpecRgstNo"))),
				productJson(products),
				trimToNull(str(item.get("dtilPrdctClsfcNo"))),
				rate(item.get("sucsfbidLwltRate")),
				priceJson(
						item.get("asignBdgtAmt"), item.get("presmptPrce"),
						item.get("prdctUprc"), item.get("prdctQty"),
						str(item.get("prdctUnit")), item.get("VAT")),
				date(item.get("bidNtceDt")),
				closeDate,
				trimToNull(str(item.get("ntceInsttOfclNm"))),
				trimToNull(str(item.get("ntceInsttOfclTelNo"))),
				body,
				attachmentJson(item, "ntceSpecFileNm", "ntceSpecDocUrl", ANNOUNCE_FILE_SLOTS),
				firstNonBlank(str(item.get("bidNtceDtlUrl")), str(item.get("bidNtceUrl"))),
				null, null, null);
	}

	// ── 발주계획 ────────────────────────────────────────────────────────────

	/**
	 * 발주계획(조달요청) 행 → 색인 행.
	 *
	 * <p>계획 단계에는 <b>마감일시가 없다</b>(아직 공고가 아니다). {@code close_date} 를 비워
	 * 두는 것이 정확하며, 그래서 '마감 임박 순' 정렬에서 계획은 자연히 뒤로 밀린다.
	 *
	 * <p>발주기관 하나가 공고기관이자 수요기관 역할을 겸하므로 두 코드에 같은 값이 들어간다.
	 */
	public static BidNoticeRow fromProcurementPlan(Map<String, Object> item, BusinessDivision division) {
		String id = str(item.get("prcrmntReqNo"));
		if (id.isEmpty()) {
			return null;
		}
		String institutionCode = trimToNull(str(item.get("orderInsttCd")));
		String productName = str(item.get("rprsntPrdctClsfcNoNm"));

		// 계획은 대표품목 하나만 준다 — 캐럿 목록이 아니라 이름 하나라서 직접 만든다.
		List<CaretList.Entry> products = productName.isEmpty()
				? List.of()
				: List.of(new CaretList.Entry("1", "", productName));

		String body = body(
				str(item.get("prcrmntReqNm")),
				str(item.get("orderInsttNm")),
				productName,
				str(item.get("rprsntSpecDtlsCntnts")),
				str(item.get("cntrctCnclsStleNm")),
				str(item.get("rprsntDlvrPlce")));

		return new BidNoticeRow(
				id,
				NoticeSource.G2B,
				"000",
				clip(str(item.get("prcrmntReqNm")), NAME_MAX),
				NoticeCategory.계획,
				null,
				division != null ? division : BusinessDivision.of(str(item.get("bsnsDivNm"))),
				"",
				institutionCode,
				clipToNull(str(item.get("orderInsttNm")), INSTITUTION_MAX),
				institutionCode,
				clipToNull(str(item.get("orderInsttNm")), INSTITUTION_MAX),
				null,
				productJson(products),
				// 계획 응답에는 세부품명'번호'가 없다 — 대표품명 '이름'만 온다.
				null,
				null,
				priceJson(item.get("bdgtAmt"), item.get("rprsntAmt"), item.get("rprsntUprc"),
						item.get("rprsntQty"), str(item.get("rprsntUnit")), null),
				date(firstNonBlankObject(item.get("rcptDt"), item.get("inptDt"))),
				null,
				trimToNull(str(item.get("prcrmntReqOfclNm"))),
				null,
				body,
				null,
				trimToNull(str(item.get("prcrmntReqInfoUrl"))),
				null, null, null);
	}

	// ── 사전규격 ────────────────────────────────────────────────────────────

	/**
	 * 사전규격 행 → 색인 행.
	 *
	 * <p>id 가 {@code bfSpecRgstNo} 인 것이 중요하다. 입찰공고 쪽도 같은 값을
	 * {@code before_spec_rgst_no} 에 담으므로, 사전규격 행의 {@code id} 와 입찰공고 행의
	 * {@code before_spec_rgst_no} 가 맞물려 <b>사전규격 → 입찰공고 교차 이동</b>이 조인 하나로 된다.
	 *
	 * <p><b>기관코드가 없다.</b> 이 오퍼레이션은 기관을 이름({@code orderInsttNm})으로만 준다.
	 * 코드 칸을 비워 두는 대신 이름을 본문에 넣어 기관명 검색에는 걸리게 한다 — 없는 코드를
	 * 지어내는 것보다 이쪽이 정직하다.
	 *
	 * <p>마감일시는 입찰 마감이 아니라 <b>의견등록 마감</b>이다. 사용자가 실제로 지켜야 하는
	 * 기한이라는 점에서 역할이 같아 같은 칸에 넣는다.
	 */
	public static BidNoticeRow fromPreSpec(Map<String, Object> item, BusinessDivision division) {
		String id = str(item.get("bfSpecRgstNo"));
		if (id.isEmpty()) {
			// 등록번호가 없으면 입찰공고와 이을 수 없고 PK 도 못 만든다.
			return null;
		}
		List<CaretList.Entry> products = CaretList.parse(str(item.get("prdctDtlList")));
		String title = firstNonBlank(str(item.get("prdctClsfcNoNm")), str(item.get("refNo")));

		String body = body(
				title,
				str(item.get("orderInsttNm")),
				str(item.get("rlDminsttNm")),
				str(item.get("refNo")),
				String.join(" ", CaretList.distinctNames(products)));

		return new BidNoticeRow(
				id,
				NoticeSource.G2B,
				"000",
				clip(title, NAME_MAX),
				NoticeCategory.사전규격,
				null,
				division != null ? division : BusinessDivision.of(str(item.get("bsnsDivNm"))),
				"",
				// 사전규격은 기관코드를 주지 않는다 — 없는 코드를 지어내지 않고 이름만 담는다.
				// 이 칸이 바로 dm_institution 조인으로는 영영 못 채우던 24.3% 다(V8 주석 참고).
				null,
				clipToNull(str(item.get("rlDminsttNm")), INSTITUTION_MAX),
				null,
				clipToNull(str(item.get("orderInsttNm")), INSTITUTION_MAX),
				id,
				productJson(products),
				null,
				null,
				priceJson(item.get("asignBdgtAmt"), null, null, null, null, null),
				date(item.get("rcptDt")),
				date(item.get("opninRgstClseDt")),
				trimToNull(str(item.get("ofclNm"))),
				trimToNull(str(item.get("ofclTelNo"))),
				body,
				attachmentJson(item, null, "specDocFileUrl", PRESPEC_FILE_SLOTS),
				null,
				null, null, null);
	}

	// ── 누리장터(민간) ──────────────────────────────────────────────────────

	/**
	 * 누리장터 민간입찰공고 목록 행 → 색인 행 ({@code PrvtBidNtceService.getPrvtBidPblancListInfo*}).
	 *
	 * <p>나라장터와 이름이 다른 필드를 여기서 흡수한다: 공고명 {@code ntceNm}(← bidNtceNm),
	 * 게시일시 {@code nticeDt}(← bidNtceDt), 공고구분 {@code ntceDivNm}(← ntceKindNm),
	 * 계약방법 {@code cntrctMthdNm}(← cntrctCnclsMthdNm), 첨부 파일명 {@code ntceSpecDocNm*}
	 * (← ntceSpecFileNm*). 목록 응답에 기관 <b>코드</b>가 아예 없어 코드 칸은 null 이다 —
	 * 없는 코드를 지어내지 않는다(사전규격과 같은 원칙).
	 *
	 * <p>금액 체계가 다르다: 누리장터에는 예가·추정가격이 없고 <b>기준금액(refAmt, 투찰 상한)</b>이
	 * 있다. 개념이 다르므로 {@code estimatedPrice} 에 넣지 않고 별도 키 {@code referenceAmount} 로
	 * 담는다 — V11 생성 컬럼(estimated_price)은 누리 행에서 NULL 이 되고, 금액 정렬에서 뒤로
	 * 밀리는 것이 의도된 동작이다(docs/multi-source-schema.md §6).
	 */
	public static BidNoticeRow fromPrivateNotice(Map<String, Object> item, BusinessDivision division,
			LocalDateTime now) {
		String id = str(item.get("bidNtceNo"));
		if (id.isEmpty()) {
			return null;
		}

		LocalDateTime closeDate = date(item.get("bidClseDt"));
		NoticeCategory category = (closeDate != null && closeDate.isBefore(now))
				? NoticeCategory.마감
				: NoticeCategory.입찰;

		// 업무별 상세목록은 [순번^품명^수량^…] 형식 — 나라장터 [순번^품명번호^품명]과 달리
		// 두 번째 토큰이 '이름'이다. CaretList 결과의 code 자리를 이름으로 되읽는다.
		List<CaretList.Entry> products = nuriDetailList(firstNonBlank(
				str(item.get("prdctDtlList")), str(item.get("servcDtlList")), str(item.get("cnstwkDtlList"))));

		String body = body(
				str(item.get("ntceNm")),
				str(item.get("ntceInsttNm")),
				str(item.get("bidNtceClsfc")),
				str(item.get("bidMethdNm")),
				str(item.get("cntrctMthdNm")),
				str(item.get("sucsfbidMthdNm")),
				str(item.get("refNo")),
				String.join(" ", CaretList.distinctNames(products)));

		return new BidNoticeRow(
				id,
				NoticeSource.NURI,
				orderOf(item.get("bidNtceOrd")),
				clip(str(item.get("ntceNm")), NAME_MAX),
				category,
				NoticeState.fromNoticeKind(str(item.get("ntceDivNm"))),
				division != null ? division : BusinessDivision.of(str(item.get("bidNtceClsfc"))),
				// 지역은 참가가능지역 오퍼레이션이 따로 채운다(나라장터와 동일 구조).
				"",
				null,
				null,
				null,
				clipToNull(str(item.get("ntceInsttNm")), INSTITUTION_MAX),
				null,
				productJson(products),
				null,
				null,
				priceJsonNamed(
						"assignedBudget", item.get("asignBdgtAmt"),
						"referenceAmount", item.get("refAmt")),
				date(firstNonBlankObject(item.get("nticeDt"), item.get("rgstDt"))),
				closeDate,
				trimToNull(str(item.get("ofclNm"))),
				trimToNull(str(item.get("ofclTelNo"))),
				body,
				attachmentJson(item, "ntceSpecDocNm", "ntceSpecDocUrl", ANNOUNCE_FILE_SLOTS),
				// 누리장터 목록 응답에는 공고 화면 URL 필드가 없다(guides-md 실측).
				null,
				extJson(item, "bidNtceClsfc", "ntceDivNm", "bidMethdNm", "sucsfbidMthdNm",
						"refAmtUseYn", "refAmtOpenYn", "opengDt", "aptHsmpNm", "aptHshldNum",
						"dtchacOpenDt", "dtchacBgnPrce"),
				null, null);
	}

	// ── D2B(국방전자조달) ───────────────────────────────────────────────────

	/**
	 * D2B 공고 행 → 색인 행.
	 *
	 * <p>입력은 {@code D2bNormalizer.normalizeD2bItem} 을 거친 항목이다 — 합성 공고번호
	 * ({@code D2B-기관-공고번호-차수})와 나라장터 모양의 필드가 이미 채워져 있고, 원본 필드
	 * ({@code dcsNo}·{@code demandYear} 등)도 그대로 남아 있다. 합성 규칙을 여기서 다시 만들지
	 * 않는 이유는 팬아웃 경로(/api/bid-announce)와 색인의 id 가 같아야 프론트가 두 경로를
	 * 오갈 수 있기 때문이다.
	 *
	 * @param division 오퍼레이션이 정하는 업종 — 시설(Fclty) 오퍼레이션은 응답의 busiDivs 가
	 *                 비어 있어도 '공사'다. normalizeD2bItem 의 _type 추정보다 신뢰도가 높다
	 */
	public static BidNoticeRow fromD2b(Map<String, Object> item, BusinessDivision division,
			LocalDateTime now) {
		String id = str(item.get("bidNtceNo"));
		// 합성 키의 식별 성분(기관·공고번호·판단번호)이 전부 비면 색인할 수 없는 행이다 —
		// 접두사·계열 태그만 남은 id 는 서로 다른 빈 행들을 하나로 뭉친다.
		if (id.isEmpty() || (str(item.get("orntCode")).isEmpty()
				&& str(item.get("pblancNo")).isEmpty() && str(item.get("dcsNo")).isEmpty())) {
			return null;
		}

		LocalDateTime closeDate = date(item.get("bidClseDt"));
		NoticeCategory category = (closeDate != null && closeDate.isBefore(now))
				? NoticeCategory.마감
				: NoticeCategory.입찰;

		String body = body(
				str(item.get("bidNtceNm")),
				str(item.get("ntceInsttNm")),
				str(item.get("cntrctCnclsMthdNm")),
				str(item.get("bidStle")),
				str(item.get("_noticeStatus")),
				str(item.get("bsnsDivNm")));

		return new BidNoticeRow(
				id,
				NoticeSource.D2B,
				orderOf(item.get("bidNtceOrd")),
				clip(str(item.get("bidNtceNm")), NAME_MAX),
				category,
				NoticeState.fromNoticeKind(str(item.get("pblancSe"))),
				division != null ? division : BusinessDivision.of(str(item.get("bsnsDivNm"))),
				// D2B 목록 응답에는 참가가능지역 개념이 없다 — 빈 값(전국 취급)의 한계는
				// docs/multi-source-schema.md §6 에 문서화돼 있다.
				"",
				null,
				null,
				// D2B 발주기관코드는 조달청 코드 체계와 다른 네임스페이스다. source 컬럼이
				// 행을 가르므로 담아 둔다 — 기관코드 조회는 source 조건과 함께 걸어야 한다.
				trimToNull(str(item.get("orntCode"))),
				clipToNull(str(item.get("ntceInsttNm")), INSTITUTION_MAX),
				null,
				null,
				null,
				null,
				// 기초예비가격(bsicExpt)은 추정가격이 아니다 — 별도 키로 담고 estimatedPrice 는
				// 비워 둔다(상세 오퍼레이션의 estmPrce 를 붙이는 것은 후속 단계).
				priceJsonNamed(
						"basicExpectedPrice", item.get("bsicExpt"),
						"assignedBudget", item.get("budgetAmount")),
				date(item.get("bidNtceDt")),
				closeDate,
				null,
				null,
				body,
				// D2B 응답에는 첨부·공고 URL 필드가 없다(d2b-openapi/INDEX.md §④).
				null,
				null,
				extJson(item, "demandYear", "pblancYear", "orntCode", "dcsNo", "pblancNo",
						"pblancOdr", "cntrwkNo", "busiDivs", "pblancSe", "bidStle", "_d2bOperation",
						"bidPartcptRegistClosDt", "opengDt"),
				trimToNull(str(item.get("g2bPblancNo"))),
				trimToNull(str(item.get("g2bPblancOdr"))));
	}

	// ── 지역 ────────────────────────────────────────────────────────────────

	/**
	 * 참가가능지역 응답행들을 공고별 지역 문자열로 접는다.
	 *
	 * <p>한 공고가 여러 지역을 갖는다(행이 {@code lmtSno} 로 나뉜다). 콤마로 이어 붙이되
	 * {@code VARCHAR(40)} 을 넘기면 <b>낱말 경계에서</b> 자른다 — 중간에서 자르면
	 * '경상남'처럼 존재하지 않는 지역명이 만들어져 필터가 영영 안 걸린다.
	 *
	 * @return 공고번호 → 지역 문자열
	 */
	public static Map<String, String> foldRegions(List<Map<String, Object>> items) {
		Map<String, Set<String>> byNotice = new java.util.LinkedHashMap<>();
		for (Map<String, Object> item : items) {
			String id = str(item.get("bidNtceNo"));
			String region = str(item.get("prtcptPsblRgnNm")).trim();
			if (id.isEmpty() || region.isEmpty()) {
				continue;
			}
			byNotice.computeIfAbsent(id, k -> new LinkedHashSet<>()).add(region);
		}

		Map<String, String> folded = new java.util.LinkedHashMap<>();
		byNotice.forEach((id, regions) -> {
			StringBuilder joined = new StringBuilder();
			for (String region : regions) {
				int added = joined.isEmpty() ? region.length() : region.length() + 1;
				if (joined.length() + added > REGION_MAX) {
					break;
				}
				if (!joined.isEmpty()) {
					joined.append(',');
				}
				joined.append(region);
			}
			if (!joined.isEmpty()) {
				folded.put(id, joined.toString());
			}
		});
		return folded;
	}

	// ── 내부: 조립 ──────────────────────────────────────────────────────────

	/**
	 * 검색 본문 조립.
	 *
	 * <p>같은 낱말이 여러 필드에 반복되는 일이 흔하다(공고명에도 품명에도 '서버'). 그대로
	 * 이어 붙이면 FULLTEXT 의 빈도가 부풀어 관련도 순위가 흔들리므로 <b>중복 조각을 지운다</b>.
	 */
	private static String body(String... parts) {
		Set<String> seen = new LinkedHashSet<>();
		for (String part : parts) {
			if (part != null && !part.isBlank()) {
				seen.add(part.trim());
			}
		}
		return String.join(" ", seen);
	}

	private static String productJson(List<CaretList.Entry> products) {
		if (products.isEmpty()) {
			return null;
		}
		ArrayNode array = JSON.createArrayNode();
		for (CaretList.Entry entry : products) {
			ObjectNode node = array.addObject();
			node.put("seq", entry.seq());
			node.put("code", entry.code());
			node.put("name", entry.name());
		}
		return array.toString();
	}

	/**
	 * 세부 가격 표. 값이 하나도 없으면 {@code null} 을 돌려준다 — 빈 객체 {@code {}} 를 넣으면
	 * 화면이 "가격 정보 있음"으로 오해한다.
	 */
	private static String priceJson(Object budget, Object estimated, Object unitPrice, Object quantity,
			String unit, Object vat) {
		ObjectNode node = JSON.createObjectNode();
		putNumber(node, "assignedBudget", budget);
		putNumber(node, "estimatedPrice", estimated);
		putNumber(node, "unitPrice", unitPrice);
		putNumber(node, "quantity", quantity);
		putNumber(node, "vat", vat);
		if (unit != null && !unit.isBlank()) {
			node.put("unit", unit.trim());
		}
		return node.isEmpty() ? null : node.toString();
	}

	private static void putNumber(ObjectNode node, String field, Object value) {
		BigDecimal number = Numbers.toNumber(value);
		if (number != null) {
			node.put(field, number);
		}
	}

	/**
	 * 키 이름을 직접 지정하는 가격 표 — 누리장터({@code referenceAmount})·D2B({@code basicExpectedPrice})
	 * 처럼 나라장터의 고정 키 집합에 없는 금액 개념을 담는다. {@code (키, 값)} 쌍의 나열이다.
	 */
	private static String priceJsonNamed(Object... keyValues) {
		ObjectNode node = JSON.createObjectNode();
		for (int i = 0; i + 1 < keyValues.length; i += 2) {
			putNumber(node, String.valueOf(keyValues[i]), keyValues[i + 1]);
		}
		return node.isEmpty() ? null : node.toString();
	}

	/**
	 * 소스 특화 필드({@code source_ext} 컬럼, V13)를 JSON 으로 접는다. 값이 있는 키만 담고,
	 * 전부 비면 {@code null} — 빈 객체를 넣으면 화면이 "확장 정보 있음"으로 오해한다.
	 * 키는 원본 API 필드명 그대로다(매핑 표를 하나 더 만들지 않는다).
	 */
	private static String extJson(Map<String, Object> item, String... keys) {
		ObjectNode node = JSON.createObjectNode();
		for (String key : keys) {
			String value = str(item.get(key)).trim();
			if (!value.isEmpty()) {
				node.put(key, value);
			}
		}
		return node.isEmpty() ? null : node.toString();
	}

	/**
	 * 누리장터 업무별 상세목록 파서. 형식이 {@code [순번^품명^수량^납품기한…]} 으로,
	 * 나라장터의 {@code [순번^품명번호^품명]} 과 달리 <b>두 번째 토큰이 이름</b>이다.
	 * {@link CaretList#parse} 를 재사용하되 code 자리에 온 이름을 되돌린다.
	 */
	private static List<CaretList.Entry> nuriDetailList(String raw) {
		List<CaretList.Entry> parsed = CaretList.parse(raw == null ? "" : raw);
		List<CaretList.Entry> remapped = new ArrayList<>(parsed.size());
		for (CaretList.Entry entry : parsed) {
			String name = entry.code().isEmpty() ? entry.name() : entry.code();
			if (!name.isEmpty()) {
				remapped.add(new CaretList.Entry(entry.seq(), "", name));
			}
		}
		return List.copyOf(remapped);
	}

	/**
	 * 첨부 목록. 파일명 슬롯이 없는 출처(사전규격)는 {@code nameField} 를 {@code null} 로 준다.
	 * URL 이 빈 슬롯은 건너뛴다 — 열 칸 중 대여섯은 늘 비어 있다.
	 */
	private static String attachmentJson(Map<String, Object> item, String nameField, String urlField,
			int slots) {
		ArrayNode array = JSON.createArrayNode();
		for (int i = 1; i <= slots; i++) {
			String url = str(item.get(urlField + i));
			if (url.isBlank()) {
				continue;
			}
			ObjectNode node = array.addObject();
			String name = nameField == null ? "" : str(item.get(nameField + i));
			node.put("name", name.isBlank() ? "첨부" + i : name);
			node.put("url", url);
		}
		return array.isEmpty() ? null : array.toString();
	}

	// ── 내부: 값 변환 ───────────────────────────────────────────────────────

	/** 낙찰하한율. 범위를 벗어난 값은 저장하지 않는다({@link #RATE_LIMIT} 주석 참고). */
	private static BigDecimal rate(Object value) {
		BigDecimal rate = Numbers.toNumber(value);
		if (rate == null) {
			return null;
		}
		if (rate.signum() < 0 || rate.compareTo(RATE_LIMIT) >= 0) {
			log.debug("낙찰하한율이 DECIMAL(5,3) 범위를 벗어나 버립니다: {}", rate);
			return null;
		}
		return rate;
	}

	/**
	 * 차수. 비어 있으면 {@code '000'}.
	 *
	 * <p>0으로 채워 세 자리로 맞추는 것이 중요하다. upsert 의 차수 역행 방지가 <b>문자열
	 * 비교</b>라서, {@code '1'} 과 {@code '000'} 이 섞이면 {@code '1' > '000'} 은 맞지만
	 * {@code '10' < '9'} 가 되어 10차가 9차를 못 덮는다.
	 */
	private static String orderOf(Object value) {
		String order = str(value).trim();
		if (order.isEmpty()) {
			return "000";
		}
		// 숫자면 세 자리로 정규화하고, 아니면(있을 수 없지만) 원본을 살린다.
		try {
			return String.format("%03d", Integer.parseInt(order));
		}
		catch (NumberFormatException ex) {
			return order;
		}
	}

	/** 지원 형식: {@code yyyy-MM-dd HH:mm:ss} / {@code yyyy-MM-dd HH:mm} / {@code yyyy-MM-dd}. */
	private static final DateTimeFormatter[] DATE_FORMATS = {
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
	};

	/**
	 * 날짜 파싱. 나라장터는 같은 의미의 칸에도 초가 있기도 없기도 하다.
	 *
	 * @return 못 읽으면 {@code null}
	 */
	static LocalDateTime date(Object value) {
		String raw = str(value).trim();
		if (raw.isEmpty()) {
			return null;
		}
		for (DateTimeFormatter format : DATE_FORMATS) {
			try {
				return LocalDateTime.parse(raw, format);
			}
			catch (java.time.format.DateTimeParseException ignored) {
				// 다음 형식 시도
			}
		}
		try {
			return java.time.LocalDate.parse(raw).atStartOfDay();
		}
		catch (java.time.format.DateTimeParseException ex) {
			log.debug("날짜를 읽지 못했습니다: {}", raw);
			return null;
		}
	}

	/** {@link #clip} 과 같되 빈 값을 {@code null} 로 만든다 — 빈 문자열 기관명은 '없음'이다. */
	private static String clipToNull(String value, int max) {
		String clipped = clip(value, max);
		return clipped.isEmpty() ? null : clipped;
	}

	private static String clip(String value, int max) {
		if (value == null) {
			return "";
		}
		String trimmed = value.trim();
		return trimmed.length() <= max ? trimmed : trimmed.substring(0, max);
	}

	private static String str(Object value) {
		return value == null ? "" : String.valueOf(value);
	}

	private static String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}

	private static String firstNonBlank(String... values) {
		for (String value : values) {
			if (value != null && !value.isBlank()) {
				return value.trim();
			}
		}
		return null;
	}

	private static Object firstNonBlankObject(Object... values) {
		for (Object value : values) {
			if (value != null && !String.valueOf(value).isBlank()) {
				return value;
			}
		}
		return null;
	}

	/** 첨부 목록이 비어 있지 않은지 — 테스트가 쓰는 편의 메서드. */
	static List<String> attachmentUrlsOf(String json) {
		List<String> urls = new ArrayList<>();
		if (json == null) {
			return urls;
		}
		JSON.readTree(json).forEach(node -> urls.add(node.get("url").asString()));
		return urls;
	}
}
