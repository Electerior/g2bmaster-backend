package com.electerior.g2bmaster.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.electerior.g2bmaster.attachment.DocumentTextExtractor;
import com.electerior.g2bmaster.attachment.HwpTextMain;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * HWP 전용 자식 프로세스.
 *
 * <p>여기서 보려는 것은 텍스트 품질이 아니라 <b>실패했을 때 무엇이 남는가</b>이다. 자식의
 * stderr 를 버리던 동안 {@code last_error} 에는 "종료코드 1" 만 남았고, 실측 186건의 정체가
 * 그 한 줄 뒤에 가려져 있었다 — 파일을 손으로 받아 돌려 보고서야 전부 hwplib 의
 * {@code This is not paragraph} 한 종류임을 알았다. 사유가 없으면 원인에 닿는 데 사람이 든다.
 */
class HwpSubprocessExtractorTest {

	private final HwpSubprocessExtractor extractor = new HwpSubprocessExtractor();

	@Test
	@DisplayName("자식이 실패하면 그 사유가 예외 메시지에 실려 온다")
	void carriesTheChildsReason() {
		// HWP 가 아닌 바이트 — 자식이 파싱에 실패하고 0 이 아닌 코드로 끝난다.
		byte[] notHwp = "이건 HWP 가 아니다".getBytes(StandardCharsets.UTF_8);

		assertThatThrownBy(() -> extractor.extract("가짜.hwp", notHwp))
				.isInstanceOf(DocumentTextExtractor.DocumentParseException.class)
				.hasMessageContaining("가짜.hwp")
				// 사유가 붙어 있어야 한다. "(사유 없음)" 이면 stderr 를 다시 버리고 있는 것이다.
				.hasMessageContaining("Exception");
	}

	@Test
	@DisplayName("빈 바이트도 예외로 닫는다 — 조용히 빈 본문을 만들지 않는다")
	void emptyBytesFailLoudly() {
		assertThatThrownBy(() -> extractor.extract("빈.hwp", new byte[0]))
				.isInstanceOf(DocumentTextExtractor.DocumentParseException.class);
	}

	@Test
	@DisplayName("자식이 시작조차 못 하면 파싱 실패가 아니다 — 그것을 섞으면 멀쩡한 파일이 영구 실패로 닫힌다")
	void aFailedLaunchIsNotAParseFailure() {
		// 실측 6건: 배포 중 target/classes 가 갈아끼워지는 순간 자식이
		// 'Could not find or load main class' 로 죽었다. 파싱 실패는 결정적이라 재시도 없이
		// skip 으로 닫히므로, 이 둘을 같은 예외로 올리면 읽을 수 있는 파일이 영영 버려진다.
		// 종료 코드로 가른다 — 우리 코드가 실제로 돈 경우에만 EXIT_PARSE_FAILED 를 낸다.
		assertThat(HwpTextMain.EXIT_PARSE_FAILED).isNotEqualTo(1).isNotEqualTo(0);
	}
}
