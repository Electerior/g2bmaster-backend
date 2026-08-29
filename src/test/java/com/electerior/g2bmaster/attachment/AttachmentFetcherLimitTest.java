package com.electerior.g2bmaster.attachment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.electerior.g2bmaster.common.ApiException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 첨부 크기 상한.
 *
 * <p>상한을 <b>다 받은 뒤</b> 재던 때가 있었다. 실측 5건이 60MB 를 꼬박 내려받고 버려졌고,
 * 그 사이 그 바이트는 힙에 통째로 올라와 있었다 — 동시 6개면 그만큼 곱해진다. 백필처럼
 * 수만 건을 도는 경로에서는 이 한 줄이 대역폭과 메모리 양쪽에 걸린다.
 */
class AttachmentFetcherLimitTest {

	@Test
	@DisplayName("상한을 넘으면 읽다가 끊는다 — 다 읽은 뒤 재지 않는다")
	void stopsReadingPastTheLimit() {
		InputStream tooBig = endlessStream();

		assertThatThrownBy(() -> AttachmentFetcher.readAtMost(tooBig))
				.isInstanceOf(ApiException.class)
				.hasMessageContaining("너무 큽니다");
	}

	@Test
	@DisplayName("상한 이하는 그대로 돌려준다")
	void passesSmallBodies() throws IOException {
		byte[] body = "규격서 본문".getBytes(java.nio.charset.StandardCharsets.UTF_8);

		assertThat(AttachmentFetcher.readAtMost(new ByteArrayInputStream(body))).isEqualTo(body);
	}

	/** 끝나지 않는 스트림. 상한에서 끊지 않으면 이 테스트가 영원히 돈다. */
	private static InputStream endlessStream() {
		return new InputStream() {
			@Override
			public int read() {
				return 0;
			}

			@Override
			public int read(byte[] buffer, int offset, int length) {
				java.util.Arrays.fill(buffer, offset, offset + length, (byte) 0);
				return length;
			}
		};
	}
}
