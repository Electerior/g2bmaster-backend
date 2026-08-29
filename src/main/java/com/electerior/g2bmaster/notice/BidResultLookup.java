package com.electerior.g2bmaster.notice;

import com.electerior.g2bmaster.common.G2bDates;
import com.electerior.g2bmaster.integration.g2b.G2bFetchService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * 공고 하나의 낙찰정보 — <b>색인에 없으면 상류에 한 번 묻는다</b>.
 *
 * <p>{@link BidResultService} 의 창 검색과 목적이 다르다. 저쪽은 "이 기간에 어떤 낙찰이
 * 있었나"이고 여기는 "내가 보고 있는 이 공고, 결과가 나왔나"다. 후자는 공고번호를 이미
 * 알고 있으므로 날짜창도, 공고명 매칭도, 색인 커버리지도 필요 없다.
 *
 * <h2>왜 색인만 보면 안 되는가</h2>
 * <p>낙찰정보 색인은 등록일시 기준 증분 적재라 상류가 늦게 공개한 건을 놓친다(실측:
 * 2026-08-27 하루가 상류 105건 / 색인 0건). 그래서 마감된 공고에서 "낙찰결과 →" 를 눌러도
 * 빈 화면이 나왔다. 공고번호를 아는 이 자리에서만큼은 색인 구멍이 곧 빈 화면일 이유가 없다 —
 * 상류에 <b>단 한 번</b>({@code inqryDiv=4}) 물으면 정확히 그 건이 온다.
 *
 * <p>받아온 것은 색인에 얹는다. 사용자가 실제로 열어 본 공고부터 구멍이 메워지고, 같은
 * 공고를 다시 열면 상류를 두드리지 않는다.
 *
 * <h2>호출 수</h2>
 * <p>구분(물품·용역·공사)을 알면 1회다. 모르면 순서대로 물어 <b>처음 걸리는 데서 멈춘다</b> —
 * 최악 3회. 공고 화면은 구분을 알고 있으므로 실제로는 거의 언제나 1회다.
 */
@Service
public class BidResultLookup {

	private static final Logger log = LoggerFactory.getLogger(BidResultLookup.class);

	private static final ObjectMapper JSON = JsonMapper.builder().build();

	/** 공고번호 기준 조회. 낙찰정보 오퍼레이션의 조회구분 4다. */
	private static final int BY_NOTICE_NO = 4;

	/**
	 * 한 공고의 낙찰정보 행 수 상한.
	 *
	 * <p>낙찰정보에는 정정 차수가 없어 보통 1건이고, 재입찰({@code rbidNo})이 있어도 한 자릿수다.
	 */
	private static final int ROWS = 20;

	private final BidResultRepository repository;
	private final G2bEndpoints endpoints;
	private final G2bFetchService fetchService;

	public BidResultLookup(BidResultRepository repository, G2bEndpoints endpoints,
			G2bFetchService fetchService) {
		this.repository = repository;
		this.endpoints = endpoints;
		this.fetchService = fetchService;
	}

	/**
	 * 공고번호 하나의 낙찰정보.
	 *
	 * @param bidNtceNo 공고번호
	 * @param bidType   사업 구분(물품·용역·공사). 알면 상류 호출이 1회로 끝난다. 몰라도 된다
	 * @return 색인 또는 상류에서 온 행. 아직 낙찰이 확정되지 않았으면 빈 목록
	 */
	public List<Map<String, Object>> byNoticeNo(String bidNtceNo, String bidType) {
		String no = bidNtceNo == null ? "" : bidNtceNo.trim();
		if (no.isEmpty()) {
			return List.of();
		}
		List<Map<String, Object>> indexed = repository.findByBidNtceNo(no);
		if (!indexed.isEmpty()) {
			return indexed;
		}
		return fetchUpstream(no, bidType);
	}

	/**
	 * 상류에 물어 색인에 얹고 돌려준다.
	 *
	 * <p>빈 결과와 호출 실패를 <b>로그로 가른다</b>. 둘 다 빈 목록을 돌려주지만 뜻이 전혀 달라서,
	 * 한데 묶으면 오퍼레이션이 통째로 죽어도 "아직 낙찰 전"으로만 보인다 — 개찰결과 조회가
	 * 실제로 그렇게 몇 달을 조용히 비어 있었다.
	 */
	private List<Map<String, Object>> fetchUpstream(String bidNtceNo, String bidType) {
		for (Map.Entry<String, String> entry : candidates(bidType)) {
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("inqryDiv", BY_NOTICE_NO);
			params.put("bidNtceNo", bidNtceNo);
			params.put("pageNo", 1);
			params.put("numOfRows", ROWS);

			List<Map<String, Object>> items;
			try {
				items = fetchService.callCached(entry.getValue(), params).items();
			}
			catch (RuntimeException ex) {
				log.warn("낙찰정보 단건 조회 실패 {} ({}) — {}", bidNtceNo, entry.getKey(), ex.getMessage());
				continue;
			}
			if (items.isEmpty()) {
				continue;
			}
			store(entry.getKey(), items);
			return enrich(entry.getKey(), items);
		}
		log.debug("낙찰정보 없음 {} — 아직 낙찰이 확정되지 않았거나 유찰", bidNtceNo);
		return List.of();
	}

	/**
	 * 물어볼 구분과 순서.
	 *
	 * <p>공고가 알려 준 구분이 맵에 있으면 그것 하나다. 없으면 물품 → 용역 → 공사 순으로
	 * 훑는다 — {@link G2bEndpoints} 의 맵 순서를 그대로 쓴다.
	 */
	private List<Map.Entry<String, String>> candidates(String bidType) {
		Map<String, String> all = endpoints.bidResultByNo();
		String type = bidType == null ? "" : bidType.trim();
		String url = all.get(type);
		return url != null ? List.of(Map.entry(type, url)) : List.copyOf(all.entrySet());
	}

	/** 화면이 쓰는 모양으로 보강한다 — 적재 경로가 색인에 넣는 것과 같은 행이다. */
	private static List<Map<String, Object>> enrich(String bidType, List<Map<String, Object>> items) {
		List<Map<String, Object>> out = new ArrayList<>(items.size());
		for (Map<String, Object> item : items) {
			Map<String, Object> enriched = new LinkedHashMap<>(item);
			enriched.put("_type", bidType);
			BidEnrichment.enrichBidNotice(enriched);
			out.add(enriched);
		}
		return out;
	}

	/**
	 * 색인에 얹는다. <b>실패해도 응답은 그대로 낸다</b> — 사용자가 보려던 것은 이미 손에 있고,
	 * 저장은 다음 사람을 위한 덤이다.
	 */
	private void store(String bidType, List<Map<String, Object>> items) {
		List<BidResultRepository.Row> rows = new ArrayList<>(items.size());
		for (Map<String, Object> item : items) {
			BidResultRepository.Row row = toRow(bidType, item);
			if (row != null) {
				rows.add(row);
			}
		}
		if (rows.isEmpty()) {
			return;
		}
		try {
			repository.upsertAll(rows);
		}
		catch (RuntimeException ex) {
			log.warn("낙찰정보 단건 색인 저장 실패 — 응답에는 영향 없음: {}", ex.getMessage());
		}
	}

	/**
	 * 상류 응답 한 줄 → 저장 행. 적재기와 <b>같은 함수를 쓴다</b>.
	 *
	 * <p>둘이 갈라지면 같은 공고가 들어온 경로에 따라 다른 모양으로 저장되고, 화면에서는
	 * 그것이 "가끔 필드가 비는" 형태로 나타난다 — 원인을 찾기 가장 어려운 종류다.
	 *
	 * <p>{@code rgstDt} 가 없으면 버린다. 등록일시는 조회 창의 앵커라 그 값이 없는 행은
	 * 넣어도 어떤 창 검색에도 걸리지 않는다.
	 */
	public static BidResultRepository.Row toRow(String bidType, Map<String, Object> item) {
		String bidNtceNo = item.get("bidNtceNo") == null ? "" : String.valueOf(item.get("bidNtceNo")).trim();
		if (bidNtceNo.isEmpty()) {
			return null;
		}
		LocalDateTime rgstDt = G2bDates.parseG2bDt(item.get("rgstDt"));
		if (rgstDt == null) {
			return null;
		}
		// 사본을 뜨고, _type 을 먼저 얹고, 그 위에 보강을 돌린다. 순서가 바뀌면 _type 을 보고
		// 값을 정하는 보강 항목이 달라진다.
		Map<String, Object> enriched = new LinkedHashMap<>(item);
		enriched.put("_type", bidType);
		BidEnrichment.enrichBidNotice(enriched);
		try {
			return new BidResultRepository.Row(bidNtceNo, bidType, JSON.writeValueAsString(enriched), rgstDt);
		}
		catch (JacksonException ex) {
			log.debug("낙찰정보 행을 JSON 으로 쓰지 못했습니다 ({}): {}", bidNtceNo, ex.getMessage());
			return null;
		}
	}
}
