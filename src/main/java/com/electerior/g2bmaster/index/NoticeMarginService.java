package com.electerior.g2bmaster.index;

import com.electerior.g2bmaster.common.Numbers;
import java.math.BigDecimal;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 마진 축에 원가를 흘려 넣는 유일한 입구.
 *
 * <p><b>왜 다른 패키지가 저장소를 직접 부르지 않는가.</b> 원가를 만드는 쪽은 딜 분석
 * ({@code market}·{@code pricing})과 저장 공고({@code saved})지만, 그 값이 앉는 곳은 검색 색인
 * ({@code index})이다. 두 쪽이 {@link BidNoticeIndexRepository} 를 각자 부르기 시작하면
 * "무엇이 색인의 마진을 쓰는가"가 코드 전체로 흩어진다. 입구를 하나로 두면 그 목록이 이
 * 클래스의 호출자 목록이 된다.
 *
 * <p><b>실패를 삼킨다.</b> 마진 반영은 딜 분석이나 공고 저장의 <b>부수 효과</b>다. 색인에 없는
 * 공고거나 DB 가 잠깐 흔들렸다고 사용자의 분석 결과나 저장이 실패해서는 안 된다. 대신 조용히
 * 넘어가지 않고 로그를 남긴다 — 마진이 안 붙는 공고를 나중에 추적할 수 있어야 한다.
 *
 * <p>마진율 자체는 여기서 계산하지 않는다. 생성 컬럼 {@code margin_rate}
 * ({@code V20260814132535}) 가 예산과 원가로 계산하며, 부가세 전제도 그쪽 주석에 있다.
 */
@Service
public class NoticeMarginService {

	private static final Logger log = LoggerFactory.getLogger(NoticeMarginService.class);

	private final BidNoticeIndexRepository repository;

	public NoticeMarginService(BidNoticeIndexRepository repository) {
		this.repository = repository;
	}

	/**
	 * 딜 분석이 추정한 원가({@code deal.cost}).
	 *
	 * <p>사람이 확정해 둔 원가가 이미 있으면 <b>저장소가 거부한다</b> — 여기서 판정하지 않는다.
	 * 우선순위 판정이 두 곳에 있으면 반드시 갈라진다.
	 *
	 * @param source 공고 출처 문자열(G2B/NURI/D2B). 모르면 {@code null}
	 * @param cost   원가. 숫자가 아니거나 0 이하면 아무것도 하지 않는다 — 원가 0 은
	 *               '공짜'가 아니라 '모름'이고, 0 을 넣으면 마진율 100% 로 목록 맨 위에 앉는다
	 */
	public void recordEstimated(String bidNtceNo, String source, Object cost) {
		record(bidNtceNo, source, cost, false);
	}

	/** 사람이 확정한 가격표 합계({@code saved_notice.price_total}). 추정을 덮는다. */
	public void recordConfirmed(String bidNtceNo, String source, Object cost) {
		record(bidNtceNo, source, cost, true);
	}

	/**
	 * 추정 마진을 지운다 — 재분석이 원가를 확정하지 못했을 때(UNTRUSTED) 부른다.
	 * 확정({@code confirmed}) 마진은 건드리지 않는다.
	 */
	public void clearEstimated(String bidNtceNo, String source) {
		if (bidNtceNo == null || bidNtceNo.isBlank()) {
			return;
		}
		int updated = repository.clearEstimatedMargin(bidNtceNo, NoticeSource.of(source));
		if (updated == 0) {
			log.debug("지울 추정 마진 없음 — {}", bidNtceNo);
		}
	}

	private void record(String bidNtceNo, String source, Object cost, boolean confirmed) {
		BigDecimal value = Numbers.toNumber(cost);
		if (bidNtceNo == null || bidNtceNo.isBlank() || value == null || value.signum() <= 0) {
			return;
		}
		try {
			int updated = repository.updateMargin(
					bidNtceNo, NoticeSource.of(source), value.longValue(), confirmed);
			if (updated == 0) {
				// 색인에 없는 공고이거나(보존 기간 밖) 확정이 추정을 막은 경우다. 둘 다 정상이라
				// debug 로 둔다 — warn 으로 두면 일괄 분석이 로그를 수천 줄로 덮는다.
				log.debug("마진 반영 대상 없음 — {} ({}), 원가 {}", bidNtceNo,
						confirmed ? "confirmed" : "estimated", value);
			}
		}
		catch (RuntimeException e) {
			log.warn("마진 반영 실패 — {} : {}", bidNtceNo, e.getMessage());
		}
	}

	/**
	 * 이미 쌓인 원가(딜 분석 캐시·저장 공고)를 색인으로 끌어올린다.
	 *
	 * <p>여기서는 실패를 삼키지 않는다 — 운영자가 직접 부르는 경로라, 안 되면 안 됐다고
	 * 알아야 한다.
	 */
	public Map<String, Integer> backfill() {
		Map<String, Integer> result = repository.backfillMargins();
		log.info("마진 백필 — 추정 {}건, 확정 {}건", result.get("estimated"), result.get("confirmed"));
		return result;
	}
}
