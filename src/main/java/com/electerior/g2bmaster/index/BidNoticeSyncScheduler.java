package com.electerior.g2bmaster.index;

import com.electerior.g2bmaster.config.G2bProperties;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.IntFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 색인 적재 주기 실행기.
 *
 * <p>{@code SyncSchedulerService}(사용자가 화면에서 잡는 예약)와 다른 물건이다. 이쪽은
 * 사람이 설정하지 않는 <b>상시 배경 작업</b>이고, 목적이 하나다 — 검색이 상류를 안 치도록
 * 색인을 최신으로 유지하는 것.
 *
 * <p><b>주기가 셋인 이유.</b> 셋 다 비용의 종류가 다르다.
 * <ul>
 *   <li>{@code intervalMs}(조달청 5분) — HTTP 호출 17번. 쿼터가 넉넉해 자주 돌 수 있다.</li>
 *   <li>{@code d2bIntervalMs}(D2B 30분) — HTTP 호출 4번인데 <b>쿼터가 조달청의 1/100</b>이다
 *       (개발계정 오퍼레이션당 하루 100건). 같이 묶으면 D2B 가 전체 신선도를 결정해 버린다.</li>
 *   <li>{@code sweepMs}(마감 전이 5분) — 인덱스를 타는 UPDATE 한 번. 적재에 묶으면 방금 마감된
 *       공고가 그 주기만큼 '입찰'로 검색된다. 싼 것을 자주 돌리는 편이 화면 정확도를 크게 올린다.</li>
 * </ul>
 *
 * <p>주기를 나눠도 스케줄러 스레드가 하나면 결국 직렬화된다 — {@code spring.task.scheduling.pool.size}
 * 를 같이 올려 두었다(application.yml).
 *
 * <p><b>심야에는 두 적재 주기가 함께 늘어난다</b>({@code g2b.index.night}). 조달청 기준 23~07시가
 * 하루 물량의 0.7%뿐이라, 그 시간대의 촘촘한 폴링은 쿼터만 태우고 얻는 것이 없다. 마감 전이는
 * 상류를 부르지 않으므로 심야에도 그대로 5분마다 돈다.
 */
@Service
public class BidNoticeSyncScheduler {

	private static final Logger log = LoggerFactory.getLogger(BidNoticeSyncScheduler.class);

	private final BidNoticeIngestService ingestService;
	private final BidNoticeIndexRepository repository;
	private final G2bProperties.Index config;

	/**
	 * 두 묶음.
	 *
	 * <p><b>잠금이 묶음마다 따로인 이유</b>: 하나로 두면 30분짜리 D2B 회차가 도는 동안 5분짜리
	 * 조달청 회차가 통째로 굶는다. 두 묶음은 쓰는 행이 겹치지 않으므로(출처가 PK 의 일부다)
	 * 같이 돌아도 안전하다. 전 출처를 훑는 수동 실행만 둘 다 쥔다.
	 */
	private final Cycle procurement;

	private final Cycle d2b;

	public BidNoticeSyncScheduler(BidNoticeIngestService ingestService,
			BidNoticeIndexRepository repository, G2bProperties properties) {
		this.ingestService = ingestService;
		this.repository = repository;
		this.config = properties.index();
		this.procurement = new Cycle("조달청 주기", config.intervalMs(), ingestService::ingestProcurement);
		this.d2b = new Cycle("D2B 주기", config.d2bIntervalMs(), ingestService::ingestD2b);
	}

	/**
	 * 주기 하나 — 이름·잠금·마지막 실행 시각·본체를 묶는다.
	 *
	 * <p>{@code @Scheduled} 는 <b>업무시간 주기</b>로 깨어나고, 심야인지는 {@link #due} 가 판정해
	 * 아직 이르면 그냥 물러난다. 크론 두 벌(주간용·야간용)을 두지 않은 이유는 {@code fixedDelay}
	 * 의 성질을 잃지 않으려는 것이다 — 크론은 앞 회차가 길어져도 제 시각에 또 깨어나 겹친다.
	 */
	private static final class Cycle {

		private final String name;

		private final long baseIntervalMs;

		private final IntFunction<BidNoticeIngestService.IngestResult> body;

		private final AtomicBoolean running = new AtomicBoolean(false);

		/** 0 = 아직 한 번도 안 돌았다. 기동 직후 첫 회차는 심야여도 그냥 돈다. */
		private final AtomicLong lastStartedAt = new AtomicLong(0);

		private Cycle(String name, long baseIntervalMs,
				IntFunction<BidNoticeIngestService.IngestResult> body) {
			this.name = name;
			this.baseIntervalMs = baseIntervalMs;
			this.body = body;
		}

		/** 심야 배수를 반영한 실효 주기가 지났는가. */
		private boolean due(long nowMs, boolean night, int multiplier) {
			long interval = night ? baseIntervalMs * Math.max(multiplier, 1) : baseIntervalMs;
			return nowMs - lastStartedAt.get() >= interval;
		}
	}

	/**
	 * 지금이 심야인가.
	 *
	 * <p>{@code from > to} 면 자정을 넘는 구간이다(23~07 이 그렇다). 두 값이 같으면 게이팅을
	 * 끈 것으로 본다 — "0시부터 0시까지"를 하루 전체로 읽어 적재를 통째로 멈추는 사고를 막는다.
	 */
	static boolean isNight(int hour, int fromHour, int toHour) {
		if (fromHour == toHour) {
			return false;
		}
		return fromHour < toHour
				? hour >= fromHour && hour < toHour
				: hour >= fromHour || hour < toHour;
	}

	/**
	 * 주기 적재.
	 *
	 * <p>{@code fixedDelayString} 인 것에 뜻이 있다 — {@code fixedRate} 로 두면 적재가 주기보다
	 * 오래 걸릴 때(백필 첫 회차가 그렇다) 다음 실행이 밀려 쌓이고, 결국 나라장터에 동시
	 * 요청 폭풍을 낸다. 끝난 다음에 세는 것이 옳다.
	 */
	@Scheduled(fixedDelayString = "${g2b.index.interval-ms:300000}", initialDelay = 30_000)
	public void ingest() {
		if (!config.enabled()) {
			return;
		}
		runIngest(procurement);
	}

	/**
	 * D2B 주기 적재 — 조달청보다 훨씬 드물게 돈다.
	 *
	 * <p>주기를 나눈 것은 취향이 아니라 쿼터 때문이다. D2B 개발계정은 <b>오퍼레이션당 하루
	 * 100건</b>이라(docs/d2b-openapi/INDEX.md) 조달청과 같은 5분 주기로 돌리면 288회/일로
	 * 3배 가까이 넘긴다. 30분이면 48회/일로 절반을 남긴다 — 운영계정으로 증량하면
	 * {@code INDEX_D2B_INTERVAL_MS} 를 조달청과 같은 값으로 내리면 된다.
	 *
	 * <p>물량으로도 이 쪽이 맞다. D2B 는 하루 100~140건이라 30분 회차당 두세 건이다.
	 *
	 * <p>{@code initialDelay} 를 조달청보다 뒤로 미룬 것은 기동 직후 두 묶음이 동시에
	 * 첫 회차(=백필일 수 있다)를 여는 것을 피하려는 것이다.
	 */
	@Scheduled(fixedDelayString = "${g2b.index.d2b-interval-ms:1800000}", initialDelay = 90_000)
	public void ingestD2b() {
		if (!config.enabled()) {
			return;
		}
		runIngest(d2b);
	}

	/**
	 * 수동 실행 — 운영 엔드포인트가 부른다.
	 *
	 * <p>{@code enabled} 와 무관하게 동작한다. 스위치는 "저절로 돌 것인가"를 정하는 것이지
	 * "운영자가 시켜도 안 돈다"는 뜻이 아니다.
	 *
	 * <p>전 출처를 훑으므로 두 잠금을 다 쥔다. 하나만 잡히면 잡은 것을 도로 놓고 물러난다 —
	 * 안 그러면 조달청 잠금을 쥔 채 D2B 를 기다리다 5분 주기까지 같이 막는다.
	 *
	 * @return 이미 돌고 있어 물러났으면 {@code null}
	 */
	public BidNoticeIngestService.IngestResult runNow(int backfillDays) {
		if (!procurement.running.compareAndSet(false, true)) {
			return null;
		}
		if (!d2b.running.compareAndSet(false, true)) {
			procurement.running.set(false);
			return null;
		}
		try {
			// 그대로 넘긴다. 0 이면 평시 증분이고, 양수면 그만큼 되읽으라는 지시다.
			return ingestService.ingestAll(backfillDays);
		}
		finally {
			d2b.running.set(false);
			procurement.running.set(false);
		}
	}

	private void runIngest(Cycle cycle) {
		// 심야 판정이 잠금보다 먼저다 — 물러날 회차가 잠금을 잡았다 놓을 이유가 없다.
		G2bProperties.Night night = config.night();
		long now = System.currentTimeMillis();
		if (!cycle.due(now, isNight(LocalTime.now().getHour(), night.fromHour(), night.toHour()),
				night.multiplier())) {
			return;
		}
		if (!cycle.running.compareAndSet(false, true)) {
			log.info("{} 적재를 건너뜁니다 — 이전 회차가 아직 돌고 있습니다.", cycle.name);
			return;
		}
		// 물러난 회차는 이 시각을 갱신하지 않는다. 갱신해 버리면 심야에 "돌지도 않았는데
		// 방금 돈 것"이 되어 다음 회차가 또 밀리고, 결국 아무 때도 안 돈다.
		cycle.lastStartedAt.set(now);
		long startedAt = System.nanoTime();
		try {
			// 주기 실행은 언제나 증분(0)이다. 여기에 백필 일수를 넘기면 매 회차가 며칠씩
			// 되읽어 증분의 의미가 사라지고 일일 쿼터만 태운다. 첫 회차의 기본 백필은
			// 적재기가 설정(g2b.index.backfill-days)에서 직접 읽는다.
			BidNoticeIngestService.IngestResult result = cycle.body.apply(0);
			long failed = result.sources().stream().filter(source -> !source.ok()).count();
			log.info("{} 적재 완료 — {}건 색인, 마감 전이 {}건, 실패 출처 {}개, {}ms",
					cycle.name, result.totalIndexed(), result.sweptToClosed(), failed,
					(System.nanoTime() - startedAt) / 1_000_000);
		}
		catch (RuntimeException ex) {
			// 적재기는 출처별로 예외를 삼키므로 여기까지 오는 것은 DB 장애 같은 공통 실패다.
			// 스케줄러 스레드에서 예외가 새면 이후 실행이 통째로 멈추므로 반드시 잡는다.
			log.error("{} 적재가 통째로 실패했습니다: {}", cycle.name, ex.getMessage(), ex);
		}
		finally {
			cycle.running.set(false);
		}
	}

	/**
	 * 마감 전이만 따로.
	 *
	 * <p>적재가 꺼져 있어도 돈다. 색인이 더 이상 갱신되지 않더라도, 이미 들어와 있는 공고의
	 * 마감 여부는 시간만 지나면 저절로 바뀌기 때문이다.
	 */
	@Scheduled(fixedDelayString = "${g2b.index.sweep-ms:300000}", initialDelay = 60_000)
	public void sweep() {
		try {
			int swept = repository.sweepClosed();
			if (swept > 0) {
				log.info("마감 전이 {}건", swept);
			}
		}
		catch (RuntimeException ex) {
			// DB 가 아직 없거나 잠깐 끊긴 경우. 5분 뒤에 다시 온다.
			log.warn("마감 전이 실패: {}", ex.getMessage());
		}
	}
}
