package com.electerior.g2bmaster.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 바이트로 형식 판정.
 *
 * <p>이 클래스가 없으면 <b>사전규격 첨부가 통째로 색인되지 않는다</b> — 그 오퍼레이션은
 * 파일명 슬롯이 없어 매퍼가 {@code 첨부1} 처럼 이름을 지어내고, 확장자가 없으니 추출기가
 * 형식을 못 고른다. 실측 첫 회차 200건 중 20건이 이 이유로 실패했다.
 */
class DocumentSnifferTest {

	@Test
	@DisplayName("확장자와 내용이 맞으면 이름을 건드리지 않는다")
	void keepsUsableName() {
		byte[] pdf = "%PDF-1.7\n...".getBytes(StandardCharsets.ISO_8859_1);
		byte[] hwp = ole(512, "HWP Document File V5.00".getBytes(StandardCharsets.ISO_8859_1));

		// 파일명은 규격서 선택(SpecFileSelector)의 입력이기도 해서 함부로 바꾸면 안 된다.
		assertThat(DocumentSniffer.resolveName("규격서.pdf", pdf)).isEqualTo("규격서.pdf");
		assertThat(DocumentSniffer.resolveName("공고문.HWP", hwp)).isEqualTo("공고문.HWP");
	}

	@Test
	@DisplayName("확장자 없는 사전규격 첨부에 확장자를 붙여 준다")
	void namesExtensionlessAttachment() {
		byte[] pdf = "%PDF-1.7\n...".getBytes(StandardCharsets.ISO_8859_1);

		assertThat(DocumentSniffer.resolveName("첨부1", pdf)).isEqualTo("첨부1.pdf");
	}

	@Test
	@DisplayName("PDF 매직 넘버")
	void detectsPdf() {
		assertThat(DocumentSniffer.sniff("%PDF-1.4 blah".getBytes(StandardCharsets.ISO_8859_1))).isEqualTo("pdf");
	}

	/** HWP 5.0 과 구형 XLS 는 같은 OLE 컨테이너를 쓴다 — 안의 서명으로 갈라야 한다. */
	@Test
	@DisplayName("OLE 컨테이너는 한글 서명으로 hwp/xls 를 가른다")
	void splitsOleBySignature() {
		// HWP 서명은 FileHeader 스트림 '내용'이라 ASCII 로, XLS 는 CFB 디렉터리 '이름'이라 UTF-16LE 로 적힌다.
		byte[] hwp = ole(512, "HWP Document File V5.00".getBytes(StandardCharsets.ISO_8859_1));
		byte[] xls = ole(512, "Workbook".getBytes(StandardCharsets.UTF_16LE));

		assertThat(DocumentSniffer.sniff(hwp)).isEqualTo("hwp");
		assertThat(DocumentSniffer.sniff(xls)).isEqualTo("xls");
	}

	@Test
	@DisplayName("HWP 서명이 8KB 밖에 있어도 찾아낸다")
	void findsHwpSignatureBeyondTheOldScanWindow() {
		// 실측: 실패한 사전규격 첨부 3개의 서명 위치가 63,616 · 79,808 · 122,752 였다.
		// CFB 는 스트림을 어디에 두든 자유라 파일이 64KB 를 넘으면 FileHeader 가 뒤로 밀린다.
		// 앞 8,192 바이트만 보던 때 이것들이 전부 'xls' 로 떨어져 POI 로 흘러갔다(실패 58건).
		byte[] hwp = ole(70_000, "HWP Document File V5.00".getBytes(StandardCharsets.ISO_8859_1));

		assertThat(DocumentSniffer.sniff(hwp)).isEqualTo("hwp");
	}

	@Test
	@DisplayName("엑셀이라는 증거가 없는 OLE 는 xls 로 단정하지 않는다")
	void doesNotGuessXlsWithoutEvidence() {
		// .doc 같은 것을 xls 로 넘기면 POI 가 열려다 실패하고 'XLSX 파싱 실패' 라는 거짓 사유가 남는다.
		// 사유가 거짓이면 로그를 아무리 봐도 원인에 닿지 못한다 — 형식 미지원으로 정직하게 닫는다.
		byte[] doc = ole(512, "WordDocument".getBytes(StandardCharsets.UTF_16LE));

		assertThat(DocumentSniffer.sniff(doc)).isNull();
	}

	@Test
	@DisplayName("확장자가 내용과 어긋나면 내용이 이긴다")
	void contentWinsOverALyingExtension() {
		// 실측 표본: 이름은 .hwpx 인데 내용은 OLE 바이너리 HWP. 이름을 믿으면 hwpxlib 이 열지 못해
		// 'HWPX 파싱 실패' 로 닫히는데, 그 사유는 사실이 아니다.
		byte[] hwp = ole(6_144, "HWP Document File V5.00".getBytes(StandardCharsets.ISO_8859_1));

		assertThat(DocumentSniffer.resolveName("과업내용서.hwpx", hwp)).isEqualTo("과업내용서.hwpx.hwp");
		// 판정이 이름과 같으면 덧붙이지 않는다 — 멀쩡한 파일의 이름이 흔들리면 안 된다.
		assertThat(DocumentSniffer.resolveName("공고문.hwp", hwp)).isEqualTo("공고문.hwp");
	}

	@Test
	@DisplayName("ZIP 컨테이너는 안의 엔트리 이름으로 hwpx/xlsx/zip 을 가른다")
	void splitsZipByEntries() throws IOException {
		assertThat(DocumentSniffer.sniff(zip("mimetype", "Contents/content.hpf"))).isEqualTo("hwpx");
		assertThat(DocumentSniffer.sniff(zip("xl/workbook.xml", "[Content_Types].xml"))).isEqualTo("xlsx");
		// 도면·규격서를 묶은 일반 zip. 컨테이너로 보고 펼친다.
		assertThat(DocumentSniffer.sniff(zip("규격서.hwp", "도면.pdf"))).isEqualTo("zip");
	}

	@Test
	@DisplayName("알 수 없는 바이트는 null — 조용히 빈 결과를 만들지 않는다")
	void unknownIsNull() {
		assertThat(DocumentSniffer.sniff("hello world, not a document".getBytes(StandardCharsets.UTF_8))).isNull();
		assertThat(DocumentSniffer.sniff(new byte[] {1, 2})).isNull();
		assertThat(DocumentSniffer.sniff(null)).isNull();
		assertThat(DocumentSniffer.resolveName("첨부1", new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9})).isNull();
	}

	/** OLE 컨테이너 흉내 — 서명을 원하는 위치에 박는다. */
	private static byte[] ole(int at, byte[] signature) {
		byte[] out = new byte[at + signature.length + 512];
		out[0] = (byte) 0xD0;
		out[1] = (byte) 0xCF;
		out[2] = 0x11;
		out[3] = (byte) 0xE0;
		System.arraycopy(signature, 0, out, at, signature.length);
		return out;
	}

	private static byte[] zip(String... entries) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try (ZipOutputStream zip = new ZipOutputStream(buffer)) {
			for (String entry : entries) {
				zip.putNextEntry(new ZipEntry(entry));
				zip.write("x".getBytes(StandardCharsets.UTF_8));
				zip.closeEntry();
			}
		}
		return buffer.toByteArray();
	}
}
