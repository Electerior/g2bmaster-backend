package com.electerior.g2bmaster.index;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.electerior.g2bmaster.market.BidOpeningResultRepository;
import com.electerior.g2bmaster.market.MarketIntelService;

/**
 * 개찰 참여업체 백필 — 낙찰이 확정된 공고를 훑어 명단을 미리 받아 둔다.
 *
 * <p><b>왜 날짜범위로 한 번에 못 받나.</b> 상류에서 참여업체 <b>전수</b>를 주는 오퍼레이션은
 * {@code getOpengResultListInfoOpengCompt} 하나뿐이고 그것은 공고번호를 받는다. 날짜범위
 * 조회({@code MarketIntelService.fetchOpeningByDateRange})가 주는 것은 낙찰자 한 명뿐이다.
 * 그래서 전수 수집은 <b>공고 하나당 상류 1콜</b>이고, 이 서비스가 하는 일은 그 1콜을
 * 회차당 몇 건으로 나눠 흘리는 것이다.
 *
 * <p><b>왜 최근 구간만 보나.</b> 창을 넓히면 대상이 만 단위로 늘고 그만큼 쿼터가 이쪽으로만
 * 흐른다. 화면에서 실제로 열리는 것은 최근 낙찰건이고, 그 밖의 공고는 사용자가 열 때
 * write-through 로 저절로 쌓인다(MarketIntelService.fetchOpeningResults). 백필은 그 경로를
 * <b>앞당기는 것</b>이지 대체하는 것이 아니다.
 *
 * <p>한 번도 안 받은 공고만 대상이다. 이미 받은 것의 재조회 규칙(참여업체가 있으면 영구,
 * 비었으면 TTL)은 저장소가 갖는다 — 백필이 빈 결과를 계속 다시 긁으면 개찰 전 공고가 많은
 * 구간에서 쿼터가 그쪽으로만 샌다.
 */
@Service
public class BidOpeningBackfillService {

	private static final Logger log = LoggerFactory.getLogger(BidOpeningBackfillService.class);

	private final BidOpeningResultRepository repository;
	private final MarketIntelService marketIntel;

	/** 한 회차에 받아 올 공고 수. 5분 주기 기준 30건이면 시간당 360건이다. */
	private final int perCycle;

	/** 백필이 훑는 낙찰 등록일 구간(일). */
	private final int windowDays;

	public BidOpeningBackfillService(BidOpeningResultRepository repository,
			MarketIntelService marketIntel,
			@Value("${g2b.index.opening-backfill-limit:30}") int perCycle,
			@Value("${g2b.index.opening-backfill-days:7}") int windowDays) {
		this.repository = repository;
		this.marketIntel = marketIntel;
		this.perCycle = perCycle;
		this.windowDays = windowDays;
	}

	/**
	 * 한 회차.
	 *
	 * @return 이번에 받아 온 공고 수(건너뛴 것·실패는 빼고)
	 */
	public int runOnce() {
		if (perCycle <= 0) {
			return 0;
		}
		List<String> targets = repository.missingSince(
				LocalDateTime.now().minusDays(windowDays), perCycle);
		if (targets.isEmpty()) {
			log.debug("개찰결과 백필 — 최근 {}일에 받아 올 공고 없음", windowDays);
			return 0;
		}
		int fetched = 0;
		int participants = 0;
		int failed = 0;
		for (String no : targets) {
			int count = marketIntel.storeOpeningResults(no);
			if (count == -2) {
				failed += 1;
			}
			else if (count >= 0) {
				fetched += 1;
				participants += count;
			}
		}
		log.info("개찰결과 백필 — 대상 {}건, 저장 {}건(참여업체 {}개사), 실패 {}건",
				targets.size(), fetched, participants, failed);
		return fetched;
	}
}
