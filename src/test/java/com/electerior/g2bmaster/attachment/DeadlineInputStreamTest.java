package com.electerior.g2bmaster.attachment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * HWP 파싱 마감시한.
 *
 * <p>hwplib 은 어떤 파일에서 예외도 없이 끝나지 않는다 — 백필 실측에서 워커 6개가 전부 여기
 * 물려 7분 넘게 CPU 만 태웠다. 취소로는 못 막는다(CPU 루프가 인터럽트를 안 본다). 대신 그
 * 루프가 <b>읽기를 계속한다</b>는 성질을 이용해, 읽기마다 시간을 확인하게 만든 것이 이 스트림이다.
 */
class DeadlineInputStreamTest {

	@Test
	@DisplayName("마감 전에는 그대로 읽힌다")
	void readsNormallyBeforeDeadline() throws IOException {
		var stream = new DocumentTextExtractor.DeadlineInputStream(
				new ByteArrayInputStream(new byte[] {1, 2, 3}), 10_000);

		assertThat(stream.read()).isEqualTo(1);
		assertThat(stream.readAllBytes()).containsExactly(2, 3);
	}

	@Test
	@DisplayName("마감이 지나면 읽기가 실패한다 — 파서가 다음 바이트를 요구하는 순간 빠져나온다")
	void refusesAfterDeadline() {
		var stream = new DocumentTextExtractor.DeadlineInputStream(
				new ByteArrayInputStream(new byte[] {1, 2, 3}), 0);

		assertThatThrownBy(stream::read).isInstanceOf(IOException.class).hasMessageContaining("시간 초과");
		assertThatThrownBy(() -> stream.read(new byte[3], 0, 3)).isInstanceOf(IOException.class);
		assertThatThrownBy(() -> stream.skip(1)).isInstanceOf(IOException.class);
		// available() 도 막는다 — 파서가 그 값으로 버퍼를 잡는 경로가 있다.
		assertThatThrownBy(stream::available).isInstanceOf(IOException.class);
	}
}
