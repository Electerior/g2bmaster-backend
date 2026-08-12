package com.electerior.g2bmaster.document;

import com.electerior.g2bmaster.config.G2bProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 첨부 추출 주기 실행기.
 *
 * <p>{@code BidNoticeSyncScheduler} 와 나란한 물건이지만 <b>주기가 따로다</b>. 적재는 HTTP
 * 호출 수십 번짜리이고, 추출은 하루 3,643개 파일 · 약 0.9GB 를 내려받는 일이다. 같은 주기에
 * 묶으면 전체가 무거운 쪽에 맞춰져, 색인이 최신인데 본문만 30분씩 늦는 상태가 된다.
 *
 * <p>기본이 꺼짐인 것도 같은 이유다 — 여러 인스턴스가 같이 켜지면 같은 파일을 중복으로
 * 내려받는다. 적재 인스턴스 하나만 켠다.
 */
@Service
public class DocumentExtractionScheduler {

	private static final Logger log = LoggerFactory.getLogger(DocumentExtractionScheduler.class);

	private final DocumentExtractionWorker worker;
	private final G2bProperties.Documents config;

	public DocumentExtractionScheduler(DocumentExtractionWorker worker, G2bProperties properties) {
		this.worker = worker;
		this.config = properties.documents();
	}

	/**
	 * {@code fixedDelayString} 은 이전 실행이 <b>끝난 뒤부터</b> 센다. 회차가 간격보다 오래
	 * 걸려도 겹치지 않는다 — 첫 백필처럼 한 회차가 길어질 때 이 차이가 중요하다.
	 */
	@Scheduled(fixedDelayString = "${g2b.documents.interval-ms:1800000}", initialDelay = 60_000)
	public void run() {
		if (!config.enabled()) {
			return;
		}
		try {
			worker.runOnce();
		} catch (RuntimeException e) {
			// 스케줄러 안에서 예외가 새면 그 뒤 회차가 아예 안 돈다.
			log.warn("첨부 추출 회차가 실패했습니다: {}", e.toString(), e);
		}
	}
}
