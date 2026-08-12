package com.electerior.g2bmaster.document;

import com.electerior.g2bmaster.attachment.DocumentTextExtractor;
import com.electerior.g2bmaster.attachment.HwpTextMain;
import com.electerior.g2bmaster.attachment.ParsedDocument;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * HWP 를 <b>별도 프로세스</b>로 뽑는다 — 안 끝나면 강제로 끝낸다.
 *
 * <p>이유는 {@link HwpTextMain} 머리주석에 있다(요약: hwplib 이 어떤 파일에서 CPU 를 태우며
 * 끝나지 않고, 같은 JVM 안에서는 인터럽트로도 스트림 마감시한으로도 못 끊는다).
 *
 * <p>실패는 전부 {@link DocumentTextExtractor.DocumentParseException} 으로 올린다 — 호출부는
 * "이 파일은 못 읽었다" 만 알면 되고, 그것이 프로세스 강제 종료 때문인지 파싱 오류 때문인지는
 * {@code last_error} 문구로 남는다.
 */
@Component
public class HwpSubprocessExtractor {

	private static final Logger log = LoggerFactory.getLogger(HwpSubprocessExtractor.class);

	/**
	 * 한 파일의 상한. 정상 HWP 는 실측 평균 0.8초에 끝나고 여기에 JVM 기동 0.3~0.5초가 붙는다.
	 * 30초면 25배 이상 여유이고, 넘어가는 것은 사실상 안 끝나는 파일이다.
	 */
	private static final long TIMEOUT_SECONDS = 30;

	private final String javaBin;
	private final String classpath;

	public HwpSubprocessExtractor() {
		this.javaBin = Path.of(System.getProperty("java.home"), "bin", "java").toString();
		// 지금 이 JVM 의 클래스패스를 그대로 물려준다 — 의존성 목록을 두 곳에서 관리하지 않는다.
		this.classpath = System.getProperty("java.class.path");
	}

	/** @return 뽑은 본문. {@code pageCount} 는 0 이다 — 프로세스 경계 너머로 옮길 값이 아니다 */
	public ParsedDocument extract(String filename, byte[] bytes) {
		Path temp = null;
		Path outFile = null;
		Process process = null;
		try {
			temp = Files.createTempFile("g2b-hwp-", ".hwp");
			Files.write(temp, bytes);
			outFile = Files.createTempFile("g2b-hwp-out-", ".txt");

			// **자식의 출력은 파이프가 아니라 파일로 받는다.** 파이프로 받으면 부모가
			// readAllBytes() 로 스트림이 닫히기를 기다리는데, 자식이 안 끝나는 파일에 물리면
			// 스트림도 영원히 안 닫혀 부모까지 같이 멈춘다 — 타임아웃을 걸어 둔 waitFor 는
			// 그 뒤 줄이라 아예 도달하지 못한다. 실측으로 이 상태를 먼저 만들어 봤다.
			process = new ProcessBuilder(List.of(javaBin, "-cp", classpath,
					"com.electerior.g2bmaster.attachment.HwpTextMain", temp.toString()))
					.redirectOutput(outFile.toFile())
					.redirectError(ProcessBuilder.Redirect.DISCARD)
					.start();

			if (!process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
				process.destroyForcibly();
				throw new DocumentTextExtractor.DocumentParseException(
						"HWP 파싱 시간 초과(" + TIMEOUT_SECONDS + "초) — 파서가 멈춰 세워졌습니다: " + filename, null);
			}
			if (process.exitValue() != 0) {
				throw new DocumentTextExtractor.DocumentParseException(
						"HWP 파싱 실패(종료코드 " + process.exitValue() + "): " + filename, null);
			}
			String text = new String(Files.readAllBytes(outFile), StandardCharsets.UTF_8);
			return new ParsedDocument(filename, ParsedDocument.DocumentFormat.HWP, text, 0,
					text.length() >= DocumentTextExtractor.MAX_CHARS);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new DocumentTextExtractor.DocumentParseException("HWP 파싱 중단: " + filename, e);
		}
		catch (IOException e) {
			throw new DocumentTextExtractor.DocumentParseException("HWP 프로세스 실행 실패: " + filename, e);
		}
		finally {
			if (process != null && process.isAlive()) {
				process.destroyForcibly();
			}
			deleteQuietly(temp);
			deleteQuietly(outFile);
		}
	}

	private static void deleteQuietly(Path path) {
		if (path == null) {
			return;
		}
		try {
			Files.deleteIfExists(path);
		}
		catch (IOException e) {
			log.debug("임시 HWP 파일 삭제 실패: {}", path);
		}
	}
}
