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
		Path errFile = null;
		Process process = null;
		try {
			temp = Files.createTempFile("g2b-hwp-", ".hwp");
			Files.write(temp, bytes);
			outFile = Files.createTempFile("g2b-hwp-out-", ".txt");
			// 자식의 stderr 를 버리지 않는다. 버리면 last_error 에 "종료코드 1" 만 남고, 그 뒤로는
			// 파일을 손으로 받아 돌려 봐야 원인을 안다 — 실측에서 186건의 사유가 그렇게 가려져
			// 있었고, 알고 보니 전부 hwplib 의 'This is not paragraph' 한 종류였다.
			errFile = Files.createTempFile("g2b-hwp-err-", ".txt");

			// **자식의 출력은 파이프가 아니라 파일로 받는다.** 파이프로 받으면 부모가
			// readAllBytes() 로 스트림이 닫히기를 기다리는데, 자식이 안 끝나는 파일에 물리면
			// 스트림도 영원히 안 닫혀 부모까지 같이 멈춘다 — 타임아웃을 걸어 둔 waitFor 는
			// 그 뒤 줄이라 아예 도달하지 못한다. 실측으로 이 상태를 먼저 만들어 봤다.
			process = new ProcessBuilder(List.of(javaBin, "-cp", classpath,
					"com.electerior.g2bmaster.attachment.HwpTextMain", temp.toString()))
					.redirectOutput(outFile.toFile())
					.redirectError(errFile.toFile())
					.start();

			if (!process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
				process.destroyForcibly();
				// 멈춰 세운 뒤에도 미리보기는 읽을 수 있다 — PrvText 는 CFB 스트림 하나라
				// hwplib 을 거치지 않는다. 그렇지 않으면 이 파일들은 색인에서 통째로 사라진다
				// (실측 136건). 앞부분만이라는 사실은 truncated 로 올린다.
				String preview = DocumentTextExtractor.previewText(bytes);
				if (preview != null && !preview.isBlank()) {
					log.debug("HWP 시간 초과 — 미리보기로 대체합니다: {}", filename);
					return new ParsedDocument(filename, ParsedDocument.DocumentFormat.HWP, preview, 0, true);
				}
				throw new DocumentTextExtractor.DocumentParseException(
						"HWP 파싱 시간 초과(" + TIMEOUT_SECONDS + "초) — 파서가 멈춰 세워졌습니다: " + filename, null);
			}
			int exit = process.exitValue();
			if (exit == HwpTextMain.EXIT_PARSE_FAILED) {
				throw new DocumentTextExtractor.DocumentParseException(
						"HWP 파싱 실패: " + filename + " — " + firstLine(errFile), null);
			}
			if (exit != 0) {
				// 자식이 우리 코드에 닿지도 못했다. **파싱 실패가 아니다** — 파싱 실패는
				// 결정적이라 재시도 없이 닫히므로, 이것을 그쪽으로 보내면 멀쩡한 파일이 영구
				// 실패가 된다(실측 6건: 배포 중 target/classes 가 갈아끼워지는 순간에 뜬
				// 'Could not find or load main class'). 일시적 사정이므로 재시도로 돌린다.
				throw new IllegalStateException(
						"HWP 추출 프로세스가 시작되지 못했습니다(종료코드 " + exit + "): "
								+ filename + " — " + firstLine(errFile));
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
			deleteQuietly(errFile);
		}
	}

	/**
	 * 자식이 남긴 사유의 첫 줄. {@code last_error} 는 500자로 잘리므로 스택은 버리고 사유만 싣는다.
	 *
	 * <p>사유를 못 읽는 것 자체는 실패로 만들지 않는다 — 원래 알리려던 실패를 덮어 버리면
	 * 원인이 한 겹 더 멀어진다.
	 */
	private static String firstLine(Path errFile) {
		if (errFile == null) {
			return "(사유 없음)";
		}
		try {
			for (String line : Files.readAllLines(errFile, StandardCharsets.UTF_8)) {
				if (!line.isBlank()) {
					return line.length() > 200 ? line.substring(0, 200) : line;
				}
			}
		}
		catch (IOException | RuntimeException e) {
			return "(사유를 읽지 못했습니다)";
		}
		return "(사유 없음)";
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
