package com.electerior.g2bmaster.attachment;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * HWP 텍스트 추출 <b>전용 프로세스</b>의 진입점 — 파일 경로를 받아 본문을 stdout 으로 낸다.
 *
 * <p><b>왜 프로세스를 따로 띄우는가.</b> hwplib 은 어떤 HWP 파일에서 예외도 없이 끝나지 않는다.
 * 백필 실측에서 워커 6개가 전부 같은 자리에 물려 한 스레드가 411초 동안 CPU 408초를 태웠다:
 *
 * <pre>
 *   ForParagraph.controlAndMemo → skipETCRecord
 *     → StreamReaderForCompress.readBytes → InputStream.read   (RUNNABLE)
 * </pre>
 *
 * <p>같은 JVM 안에서는 이걸 못 끊는다. 세 가지를 다 시도했고 전부 안 됐다.
 * <ul>
 *   <li>{@code Future.cancel} — CPU 루프가 인터럽트를 확인하지 않는다</li>
 *   <li>입력 스트림에 마감시한({@code DeadlineInputStream}) — hwplib 이 스트림을 <b>먼저 통째로</b>
 *       읽어 들인 뒤 메모리 버퍼에서 파싱하므로, 멈추는 구간에서는 스트림을 아예 안 건드린다</li>
 *   <li>스레드를 버리고 진행 — 버려진 스레드가 코어를 계속 태워 갈수록 느려진다</li>
 * </ul>
 *
 * <p>남는 방법은 <b>프로세스 경계</b>뿐이다. {@code Process.destroyForcibly()} 는 상대가 협조하지
 * 않아도 확실히 끝낸다. 대가는 JVM 기동 비용(건당 0.3~0.5초)인데, 어차피 파싱이 그보다 오래
 * 걸리므로 감당할 만하다.
 *
 * <p>표준 출력은 <b>본문 전용</b>이다. 로그가 섞이면 그대로 색인 본문이 되므로 아무것도 찍지 않는다.
 */
public final class HwpTextMain {

	private HwpTextMain() {
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.exit(2);
		}
		try {
			Path path = Path.of(args[0]);
			byte[] bytes = Files.readAllBytes(path);
			// 확장자로 파서를 고르므로 .hwp 로 끝나는 이름을 넘긴다(임시파일이 그렇게 만들어진다).
			ParsedDocument parsed = new DocumentTextExtractor().extract(path.getFileName().toString(), bytes);
			System.out.write(parsed.text() == null ? new byte[0] : parsed.text().getBytes(StandardCharsets.UTF_8));
			System.out.flush();
			System.exit(0);
		}
		catch (Throwable e) {
			// 사유는 stderr 로. 부모가 종료코드만 보고 실패로 닫는다.
			System.err.println(e);
			System.exit(1);
		}
	}
}
