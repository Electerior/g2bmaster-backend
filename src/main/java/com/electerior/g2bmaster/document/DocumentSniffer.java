package com.electerior.g2bmaster.document;

import com.electerior.g2bmaster.attachment.DocumentTextExtractor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 파일명이 형식을 알려주지 않을 때 <b>바이트를 보고</b> 판정한다.
 *
 * <p><b>왜 필요한가.</b> {@code DocumentTextExtractor} 는 확장자로 파서를 고르는데,
 * 나라장터 첨부 중 상당수가 확장자 없는 이름으로 온다. 특히 <b>사전규격</b>이 그렇다 —
 * 그 오퍼레이션({@code specDocFileUrl1..N})에는 파일명 슬롯이 아예 없어서, 매퍼가
 * {@code "첨부" + 슬롯번호} 로 이름을 지어낸다({@code BidNoticeMapper.attachmentJson}).
 * 실측: 첫 추출 회차 200건 중 <b>20건(10%)</b>이 이 이유로 "형식 미지원" 이 됐고,
 * 사전규격만 놓고 보면 사실상 전부다.
 *
 * <p><b>추출기를 고치지 않고 여기서 보정하는 이유.</b> {@code DocumentTextExtractor.extract} 의
 * "확장자로 판정한다" 는 계약에 단가 분석 경로가 함께 걸려 있고 테스트가 그 모양을 고정하고 있다.
 * 바이트 판정은 <b>적재 쪽 사정</b>이므로 적재 쪽에서 처리한다 — 추출기는 이름이 제대로 붙은
 * 바이트를 받는다는 전제를 유지한다.
 *
 * <p>원본 모놀리스도 같은 일을 한다({@code lib/files.js} 의 {@code detectContainer}).
 */
final class DocumentSniffer {

	/** zip 안을 들여다볼 최대 엔트리 수. 앞쪽 몇 개만 봐도 컨테이너 종류는 갈린다. */
	private static final int MAX_PEEK_ENTRIES = 40;
	/**
	 * zip 엔트리 <b>이름</b>의 인코딩. {@link DocumentTextExtractor#ZIP_NAME_CHARSET} 와 같은 이유다
	 * (요약: 국내 첨부 zip 은 대개 CP949 이고, UTF-8 로 열면 파일이 통째로 실패한다).
	 */
	private static final Charset ZIP_NAME_CHARSET = DocumentTextExtractor.ZIP_NAME_CHARSET;

	private DocumentSniffer() {
	}

	/**
	 * 추출기에 넘길 파일명을 정한다.
	 *
	 * <p><b>확장자가 있어도 내용과 어긋나면 내용이 이긴다.</b> 나라장터 첨부에는 이름만 {@code .hwpx}
	 * 이고 내용은 OLE 바이너리 HWP 인 파일이 실제로 있다(실측 표본 확인). 이름을 믿으면 hwpxlib 이
	 * 열지 못해 "HWPX 파싱 실패" 로 닫히는데, 사유가 거짓이라 로그만 봐서는 원인을 찾을 수 없다.
	 * 판정이 확실할 때만 덮으므로({@link #sniff} 가 {@code null} 이면 이름을 그대로 둔다) 멀쩡한
	 * 파일의 이름이 흔들리지는 않는다.
	 *
	 * <p>파일명 자체는 보존한다 — 확장자를 <b>덧붙일</b> 뿐이다. 규격서 선택
	 * ({@code SpecFileSelector}) 이 원래 이름을 읽기 때문이다.
	 *
	 * @return 확장자가 붙은 이름. 형식을 알 수 없으면 {@code null}
	 */
	static String resolveName(String filename, byte[] bytes) {
		String name = filename == null || filename.isBlank() ? "첨부" : filename.trim();
		String lower = name.toLowerCase();
		boolean known = lower.endsWith(".hwpx") || lower.endsWith(".hwp") || lower.endsWith(".pdf")
				|| lower.endsWith(".xlsx") || lower.endsWith(".xls") || lower.endsWith(".zip");
		String ext = sniff(bytes);
		if (ext == null) {
			return known ? name : null;
		}
		return lower.endsWith("." + ext) ? name : name + "." + ext;
	}

	/** @return 확장자(점 없음), 모르면 {@code null} */
	static String sniff(byte[] bytes) {
		if (bytes == null || bytes.length < 8) {
			return null;
		}
		if (startsWith(bytes, new byte[] {'%', 'P', 'D', 'F'})) {
			return "pdf";
		}
		// OLE Compound File — HWP 5.0 · 구형 XLS · DOC 가 같은 컨테이너를 쓴다.
		//
		// **서명을 파일 전체에서 찾는다.** 앞 8KB 만 보던 때 실측 표본 3개의 서명 위치가
		// 63,616 · 79,808 · 122,752 였다 — CFB 는 스트림을 어디에 두든 자유라서 파일이
		// 64KB 를 넘으면 FileHeader 가 뒤로 밀린다. 그때 전부 'xls' 로 떨어져 POI 가 열려다
		// 실패했고(실측 58건), 사전규격 첨부는 파일명이 없어 이 판정에만 의존한다.
		if (startsWith(bytes, new byte[] {(byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0})) {
			if (indexOf(bytes, "HWP Document File".getBytes(StandardCharsets.ISO_8859_1)) >= 0) {
				return "hwp";
			}
			// 엑셀이라는 증거가 있을 때만 xls 다. CFB 디렉터리 항목 이름은 UTF-16LE 로 적힌다.
			// 증거 없이 xls 로 단정하면 .doc 같은 것이 POI 로 흘러가 "XLSX 파싱 실패" 라는
			// 거짓 사유를 남긴다 — 형식 미지원으로 정직하게 닫는 편이 낫다.
			return hasOleStream(bytes, "Workbook") || hasOleStream(bytes, "Book") ? "xls" : null;
		}
		// ZIP 컨테이너 — HWPX·XLSX·DOCX·일반 zip 이 전부 여기로 온다. 안을 봐야 갈린다.
		if (startsWith(bytes, new byte[] {'P', 'K', 0x03, 0x04})) {
			return sniffZip(bytes);
		}
		return null;
	}

	private static String sniffZip(byte[] bytes) {
		boolean sawHwpx = false;
		boolean sawXlsx = false;
		try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytes), ZIP_NAME_CHARSET)) {
			ZipEntry entry;
			int seen = 0;
			while ((entry = zip.getNextEntry()) != null && seen++ < MAX_PEEK_ENTRIES) {
				String entryName = entry.getName() == null ? "" : entry.getName().toLowerCase();
				// HWPX: mimetype = application/hwp+zip, 본문은 Contents/ 아래.
				if (entryName.equals("mimetype") || entryName.startsWith("contents/")
						|| entryName.endsWith("content.hpf")) {
					sawHwpx = true;
				}
				if (entryName.startsWith("xl/")) {
					sawXlsx = true;
				}
			}
		} catch (IOException e) {
			return "zip";   // 못 열면 일반 zip 으로 넘겨 expandArchive 가 다시 시도하게 둔다
		}
		if (sawHwpx) {
			return "hwpx";
		}
		if (sawXlsx) {
			return "xlsx";
		}
		// docx 를 포함해 그 밖의 zip 은 컨테이너로 본다 — 안에 규격서가 들어 있을 수 있다.
		return "zip";
	}

	private static boolean startsWith(byte[] bytes, byte[] magic) {
		if (bytes.length < magic.length) {
			return false;
		}
		for (int i = 0; i < magic.length; i++) {
			if (bytes[i] != magic[i]) {
				return false;
			}
		}
		return true;
	}

	/** CFB 디렉터리 항목 이름은 UTF-16LE 다. 스트림 이름 하나로 컨테이너의 정체가 갈린다. */
	private static boolean hasOleStream(byte[] bytes, String streamName) {
		return indexOf(bytes, streamName.getBytes(StandardCharsets.UTF_16LE)) >= 0;
	}

	/**
	 * 바이트 배열 안에서 부분열 찾기.
	 *
	 * <p>{@code new String(bytes, ISO_8859_1).contains(...)} 로도 되지만 그것은 파일 크기만 한
	 * 문자열을 한 번 더 만든다 — 여기 오는 첨부는 수 MB 짜리도 있고, 회차마다 300개를 돈다.
	 */
	private static int indexOf(byte[] haystack, byte[] needle) {
		outer:
		for (int i = 0; i <= haystack.length - needle.length; i++) {
			for (int j = 0; j < needle.length; j++) {
				if (haystack[i + j] != needle[j]) {
					continue outer;
				}
			}
			return i;
		}
		return -1;
	}
}
