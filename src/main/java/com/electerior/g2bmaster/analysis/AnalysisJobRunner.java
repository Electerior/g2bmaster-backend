package com.electerior.g2bmaster.analysis;

import com.electerior.g2bmaster.integration.ai.AiClient;
import jakarta.annotation.PreDestroy;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * 분석 워커 루프 — {@code lib/analysis-job-runner.js} 이식.
 *
 * <p>구조는 원본 그대로다: 청구 → heartbeat 시작 → 실행 → 완료/실패 → heartbeat 정지.
 * <b>추론은 하지 않는다</b> — {@link AiClient#itemSummary} 로 넘기고 결과의
 * {@code _analysisHistoryId} 만 본다(→ {@code docs/ai-boundary.md}).
 *
 * <p><b>기본은 꺼져 있다</b>({@code g2b.analysis.runner-enabled=false}). 켜지 않으면 큐에 작업이
 * 쌓이기만 하고 아무것도 소비되지 않는다 — AI 저장소가 아직 없는 현 단계의 정상 상태다.
 *
 * <p>여러 인스턴스가 동시에 켜져도 안전하다. 청구가 {@code FOR UPDATE SKIP LOCKED} 라
 * 같은 행을 둘이 집지 못한다.
 */
@Component
public class AnalysisJobRunner {

	private static final Logger log = LoggerFactory.getLogger(AnalysisJobRunner.class);

	private static final ObjectMapper MAPPER = JsonMapper.builder().build();

	private final AnalysisJobRepository jobRepository;
	private final AiClient aiClient;
	private final AnalysisProperties properties;

	/** 소유권 확인용 워커 식별자. 프로세스마다 달라야 리스 회수가 제대로 동작한다. */
	private final String workerId = "analysis-" + ProcessHandle.current().pid()
			+ "-" + UUID.randomUUID().toString().substring(0, 8);

	private final AtomicBoolean running = new AtomicBoolean(false);

	private ExecutorService workers;
	private ScheduledExecutorService heartbeats;

	public AnalysisJobRunner(AnalysisJobRepository jobRepository, AiClient aiClient, AnalysisProperties properties) {
		this.jobRepository = jobRepository;
		this.aiClient = aiClient;
		this.properties = properties;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startIfEnabled() {
		if (!properties.runnerEnabled()) {
			log.info("분석 워커가 꺼져 있습니다 (g2b.analysis.runner-enabled=false). 큐는 쌓이기만 합니다.");
			return;
		}
		if (!aiClient.isEnabled()) {
			// 켜 두고 AI 를 끄면 모든 작업이 max_attempts 까지 실패한 뒤 failed 로 굳는다.
			// 조용히 큐를 태워 없애는 것보다 뜨지 않는 편이 낫다.
			log.warn("분석 워커를 켜려 했으나 AI 가 꺼져 있습니다 (g2b.ai.enabled=false). 시작하지 않습니다.");
			return;
		}
		start();
	}

	void start() {
		if (!running.compareAndSet(false, true)) {
			return;
		}
		int concurrency = properties.concurrency();
		workers = Executors.newFixedThreadPool(concurrency, Thread.ofPlatform().name("analysis-worker-", 0).factory());
		heartbeats = Executors.newScheduledThreadPool(1, Thread.ofPlatform().name("analysis-heartbeat-", 0).factory());
		for (int i = 0; i < concurrency; i++) {
			int index = i;
			workers.submit(() -> loop(index));
		}
		log.info("분석 워커 {}개 시작 (workerId={}, lease={}ms)", concurrency, workerId, properties.leaseMs());
	}

	@PreDestroy
	public void stop() {
		if (!running.compareAndSet(true, false)) {
			return;
		}
		if (heartbeats != null) {
			heartbeats.shutdownNow();
		}
		if (workers != null) {
			workers.shutdown();   // 진행 중인 작업은 끝까지 둔다 — 중간 절단은 리스 만료 회수에 맡기는 게 낫다.
		}
	}

	// ── 루프 ────────────────────────────────────────────────────────────────

	private void loop(int index) {
		if (index == 0) {
			sweepExpired();
		}
		while (running.get()) {
			AnalysisJob job;
			try {
				job = jobRepository.claim(workerId, properties.leaseMs());
			}
			catch (RuntimeException e) {
				log.warn("분석 작업 청구 실패: {}", e.getMessage());
				sleep(properties.pollMs());
				continue;
			}
			if (job == null) {
				// 큐가 비었을 때가 만료 회수를 돌리기 가장 좋은 시점이다(원본과 같은 판단).
				if (index == 0) {
					sweepExpired();
				}
				sleep(properties.pollMs());
				continue;
			}
			execute(job);
		}
	}

	private void execute(AnalysisJob job) {
		long id = job.getId();
		// heartbeat 주기는 리스의 1/3 이다. 한 번 놓쳐도 리스가 살아 있어야 회수되지 않는다.
		long interval = Math.max(1000L, properties.leaseMs() / 3);
		ScheduledFuture<?> beat = heartbeats.scheduleAtFixedRate(() -> {
			try {
				jobRepository.heartbeat(id, workerId, properties.leaseMs());
			}
			catch (RuntimeException e) {
				log.warn("분석 작업 {} heartbeat 실패: {}", id, e.getMessage());
			}
		}, interval, interval, TimeUnit.MILLISECONDS);

		try {
			Map<String, Object> payload = readPayload(job.getPayload());
			Map<String, Object> result = aiClient.itemSummary(payload);

			// AiClient 가 이미 _analysisHistoryId 부재와 aiFallback 을 걸러 준다.
			// 여기서 다시 보는 것은 계약이 바뀌었을 때 조용히 통과하지 않게 하기 위해서다.
			long historyId = historyId(result);
			if (historyId <= 0) {
				throw new IllegalStateException("분석 이력 ID가 없는 작업 결과입니다.");
			}
			if (jobRepository.complete(id, workerId, historyId) == null) {
				throw new IllegalStateException("분석 작업 " + id + " 완료 소유권을 잃었습니다.");
			}
		}
		catch (RuntimeException e) {
			// 지수 백오프 — 원본 retryBaseMs × 2^(attempt-1).
			// attempt_count 는 청구 시점에 이미 1 이상으로 올라가 있다.
			long backoff = properties.retryBaseMs() * (1L << Math.max(0, job.getAttemptCount() - 1));
			log.warn("분석 작업 {} 실패({}/{}): {}", id, job.getAttemptCount(), job.getMaxAttempts(), e.getMessage());
			try {
				jobRepository.fail(id, workerId, e.getMessage(), backoff);
			}
			catch (RuntimeException failure) {
				log.warn("분석 작업 {} 실패 기록조차 실패: {}", id, failure.getMessage());
			}
		}
		finally {
			beat.cancel(false);
		}
	}

	private void sweepExpired() {
		try {
			int recovered = jobRepository.recoverExpired();
			if (recovered > 0) {
				log.info("리스 만료 분석 작업 {}건을 회수했습니다.", recovered);
			}
		}
		catch (RuntimeException e) {
			log.warn("만료 작업 회수 실패: {}", e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> readPayload(String payload) {
		if (payload == null || payload.isBlank()) {
			return Map.of();
		}
		return MAPPER.readValue(payload, Map.class);
	}

	private static long historyId(Map<String, Object> result) {
		Object value = result.get("_analysisHistoryId");
		if (value == null) {
			value = result.get("analysisHistoryId");
		}
		if (value instanceof Number number) {
			return number.longValue();
		}
		try {
			return value == null ? 0L : Long.parseLong(String.valueOf(value).trim());
		}
		catch (NumberFormatException e) {
			return 0L;
		}
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
