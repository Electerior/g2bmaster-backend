package com.electerior.g2bmaster.attachment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.reader.HWPXReader;
import kr.dogfoot.hwpxlib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwpxlib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwpxlib.tool.textextractor.TextMarks;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

/**
 * 규격서 첨부(HWPX·PDF)를 {@link ParsedDocument} 하나의 규격으로 뽑는다.
 *
 * <p>원본 {@code lib/files.js}(pdf-parse)와 {@code lib/hwp.js} 의 이식 자리다. 원본은
 * "버퍼 in / 텍스트 out 의 순수 함수"였고, 여기도 바이트를 받아 텍스트를 낸다 — 상태를 갖지 않는다.
 *
 * <p><b>형식별 파서가 달라도 출력 규격은 하나다.</b> 그래야 {@link SpecFileSelector} 의
 * 규격서 선택도, AI {@code extract/specs} 의 부품 추출도 형식을 몰라도 된다.
 *
 * <p>지원 형식: <b>HWPX · HWP(5.0 바이너리) · PDF · XLSX/XLS</b>. 규격서가 hwpx/pdf 만 오는 게
 * 아니라 <b>엑셀(품목·수량·규격 표)</b>이나 <b>구형 .hwp</b> 로도 자주 온다 — 이 둘을 못 읽으면
 * 규격서를 통째로 건너뛰고 공고명만으로 단가를 추정해 엉뚱한 결과가 난다(실제 사고). HWP 는
 * hwplib 으로, 엑셀은 POI 로 뽑는다. hwplib 의 표 추출이 hwpxlib 과 완벽히 같진 않지만
 * ({@code porting-status.md}), "못 읽음"보다 낫다 — 텍스트만 뽑아 채점·부품추출에 쓴다.
 *
 * <p><b>ZIP 는 텍스트가 아니라 컨테이너다.</b> 나라장터는 규격서를 종종 여러 파일을 묶은
 * zip 으로 올린다(도면·규격서·내역서 한 묶음). {@link #expandArchive}가 zip 을 풀어 안의
 * HWPX/PDF 를 각각 {@link ParsedDocument} 로 내면, 선택 로직({@link SpecFileSelector})이
 * <b>zip 안의 진짜 규격서를 파일명·내용으로 다시 고른다.</b> zip 을 통째로 규격서로 보지 않는다.
 */
@Component
public class DocumentTextExtractor {

	/**
	 * 텍스트 상한. 잘린 텍스트로 단가를 확정하면 안 되므로, 넘으면 자르되 {@code truncated=true}
	 * 로 표시한다. 규격서 본문은 대개 수만 자이고, LLM 컨텍스트 클램프는 상류(AI)가 따로 한다.
	 */
	public static final int MAX_CHARS = 2_000_000;

	/**
	 * 파일명으로 형식을 판정해 알맞은 파서로 보낸다.
	 *
	 * @throws UnsupportedDocumentException 확장자로 형식을 알 수 없을 때. 조용히 빈 결과를
	 *         내면 "규격서가 비었다"와 "형식을 모른다"가 구분되지 않는다
	 * @throws DocumentParseException       파일이 깨졌거나 형식과 내용이 다를 때
	 */
	public ParsedDocument extract(String filename, byte[] bytes) {
		String lower = filename == null ? "" : filename.toLowerCase();
		if (lower.endsWith(".hwpx")) {
			return extractHwpx(filename, bytes);
		}
		if (lower.endsWith(".hwp")) {
			return extractHwp(filename, bytes);
		}
		if (lower.endsWith(".pdf")) {
			return extractPdf(filename, bytes);
		}
		if (lower.endsWith(".xlsx") || lower.endsWith(".xls")) {
			return extractXlsx(filename, bytes);
		}
		if (lower.endsWith(".htm") || lower.endsWith(".html")) {
			return extractHtml(filename, bytes);
		}
		if (lower.endsWith(".txt")) {
			return extractPlainText(filename, bytes);
		}
		if (lower.endsWith(".hml")) {
			return extractHml(filename, bytes);
		}
		throw new UnsupportedDocumentException(
				"지원하지 않는 형식입니다(HWPX·HWP·HML·PDF·XLSX·HTML·TXT 만): " + filename);
	}

	public boolean supports(String filename) {
		String lower = filename == null ? "" : filename.toLowerCase();
		return lower.endsWith(".hwpx") || lower.endsWith(".hwp") || lower.endsWith(".pdf")
				|| lower.endsWith(".xlsx") || lower.endsWith(".xls")
				|| lower.endsWith(".htm") || lower.endsWith(".html") || lower.endsWith(".txt")
				|| lower.endsWith(".hml");
	}

	public boolean isArchive(String filename) {
		return filename != null && filename.toLowerCase().endsWith(".zip");
	}

	// ── ZIP ──────────────────────────────────────────────────────────────────
	/** zip 하나에서 뽑을 최대 문서 수. 규격서 묶음이 이보다 많으면 첨부를 잘못 고른 것에 가깝다. */
	private static final int MAX_ARCHIVE_ENTRIES = 50;
	/** 압축 해제 총량 상한(zip bomb 가드). 개별 엔트리는 여기까지만 읽고 더 크면 건너뛴다. */
	private static final long MAX_ARCHIVE_UNCOMPRESSED = 200L * 1024 * 1024;

	/**
	 * zip 엔트리 <b>이름</b>을 읽을 인코딩.
	 *
	 * <p>기본값(UTF-8)으로 열면 국내 첨부 zip 이 통째로 실패한다 — 한글 파일명이 CP949 로 적혀
	 * 있어서 {@code ZipException: invalid LOC header (bad entry name)} 가 나고, 첫 엔트리에서
	 * 터지므로 <b>묶음 전체</b>가 날아간다. 실측 표본 2개가 정확히 그랬고, MS949 로 열자 3개씩
	 * 정상적으로 읽혔다(백필 실패 60건이 이 사유다).
	 *
	 * <p>UTF-8 로 적힌 zip 이 깨지지는 않는다. 압축기가 UTF-8 을 쓰면 엔트리 플래그 11번 비트를
	 * 세우고, {@link ZipInputStream} 은 그 비트가 선 엔트리를 이 인코딩과 무관하게 UTF-8 로 읽는다.
	 *
	 * <p>이름은 버리는 값이 아니다 — {@code SpecFileSelector} 가 zip 안에서 규격서를 고를 때
	 * 읽는 입력이라, 깨진 이름은 곧 잘못된 규격서 선택이다.
	 */
	public static final Charset ZIP_NAME_CHARSET = Charset.forName("MS949");

	/**
	 * zip 을 풀어 안의 HWPX/PDF 를 각각 텍스트로 뽑는다. 깨진 엔트리 하나가 나머지를 막지 않는다.
	 *
	 * <p>엔트리 이름을 {@link ParsedDocument#filename()} 으로 그대로 쓴다 — 선택 로직이 그 이름으로
	 * "규격서"인지 다시 랭크·채점한다. 중첩 zip 은 한 겹만 따라 들어간다(zip-in-zip 은 드물고,
	 * 무한 재귀와 zip bomb 를 막기 위해 그 이상은 무시한다).
	 *
	 * @return 텍스트를 뽑은 문서들. 지원 형식이 없으면 빈 목록(예외 아님 — 다른 첨부로 넘어간다)
	 */
	public List<ParsedDocument> expandArchive(String filename, byte[] bytes) {
		return expandArchive(filename, bytes, null);
	}

	/**
	 * zip 안의 <b>HWP 만</b> 다른 파서로 보내는 변형.
	 *
	 * <p><b>왜 필요한가.</b> 워커는 최상위 {@code .hwp} 를 이미 별도 프로세스로 보낸다 —
	 * hwplib 이 안 끝나는 파일이 있기 때문이다({@code HwpTextMain} 주석). 그런데 zip 을 푸는
	 * 것은 이 클래스이고, 그 안의 엔트리는 {@link #extract} 를 그대로 타서 <b>인프로세스</b>로
	 * 돌아갔다. 실측에서 백필이 정확히 그 구멍으로 멈췄다 — {@code expandInto → extractHwp} 에
	 * 물린 스레드 하나가 20시간 동안 CPU 를 태웠고, 회차가 끝나지 않아 그 뒤 2,242번의 호출이
	 * 전부 "이미 실행 중"으로 물러났다.
	 *
	 * <p>파서를 인자로 받는 이유: 자식 프로세스({@code HwpTextMain})도 이 클래스를 쓴다.
	 * 필드로 박아 두면 자식이 또 자식을 낳는다.
	 *
	 * @param hwpParser zip 안의 {@code .hwp} 를 맡길 파서. {@code null} 이면 종전대로 인프로세스
	 */
	public List<ParsedDocument> expandArchive(String filename, byte[] bytes, HwpParser hwpParser) {
		List<ParsedDocument> docs = new ArrayList<>();
		expandInto(filename, bytes, docs, 1, hwpParser);
		return docs;
	}

	/** HWP 한 건을 뽑는 방법. 실패는 {@link DocumentParseException} 으로 올린다. */
	@FunctionalInterface
	public interface HwpParser {
		ParsedDocument parse(String filename, byte[] bytes);
	}

	private void expandInto(String archiveName, byte[] bytes, List<ParsedDocument> out, int depth,
			HwpParser hwpParser) {
		if (bytes == null || bytes.length == 0) {
			return;
		}
		long budget = MAX_ARCHIVE_UNCOMPRESSED;
		try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytes), ZIP_NAME_CHARSET)) {
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				if (out.size() >= MAX_ARCHIVE_ENTRIES) {
					break;
				}
				if (entry.isDirectory()) {
					continue;
				}
				String name = innerName(entry.getName());
				boolean nested = depth < 2 && name.toLowerCase().endsWith(".zip");
				if (!supports(name) && !nested) {
					continue;   // HWPX·PDF·(한 겹의)zip 만 관심 대상
				}
				byte[] inner = readEntry(zip, budget);
				if (inner == null) {
					continue;   // 크기 초과 등 — 건너뛴다
				}
				budget -= inner.length;
				if (nested) {
					expandInto(name, inner, out, depth + 1, hwpParser);
					continue;
				}
				try {
					out.add(hwpParser != null && name.toLowerCase().endsWith(".hwp")
							? hwpParser.parse(name, inner)
							: extract(name, inner));
				}
				catch (RuntimeException e) {
					// 깨진 문서 하나가 묶음 전체를 막지 않는다 — 다음 엔트리로.
				}
			}
		}
		catch (IOException e) {
			throw new DocumentParseException("ZIP 해제 실패: " + archiveName, e);
		}
	}

	/** zip 엔트리 이름에서 경로를 떼고 파일명만. 압축기가 넣은 디렉터리 접두를 무시한다. */
	private static String innerName(String entryName) {
		if (entryName == null) {
			return "";
		}
		String n = entryName.replace('\\', '/');
		int slash = n.lastIndexOf('/');
		return slash >= 0 ? n.substring(slash + 1) : n;
	}

	/** 현재 엔트리를 읽되 남은 예산까지만. 초과하면 {@code null}(zip bomb 가드). */
	private static byte[] readEntry(ZipInputStream zip, long budget) {
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] chunk = new byte[8192];
			int read;
			long total = 0;
			while ((read = zip.read(chunk)) != -1) {
				total += read;
				if (total > budget) {
					return null;
				}
				buffer.write(chunk, 0, read);
			}
			return buffer.toByteArray();
		}
		catch (IOException e) {
			return null;
		}
	}

	// ── HWPX ─────────────────────────────────────────────────────────────────
	private ParsedDocument extractHwpx(String filename, byte[] bytes) {
		java.io.File temp = writeTemp(bytes, ".hwpx");
		try {
			HWPXFile hwpx = HWPXReader.fromFile(temp);
			// 문단 사이에 줄바꿈, 표 셀 사이에 탭 — specContentScore 가 줄/탭으로 구조를 읽는다.
			TextMarks marks = new TextMarks()
					.paraSeparatorAnd("\n")
					.lineBreakAnd("\n")
					.tabAnd("\t");
			// 컨트롤(표·그림 등) 텍스트도 문단 사이에 끼워 뽑는다 — 규격서의 표 안 값이 핵심이다.
			String text = TextExtractor.extract(hwpx, TextExtractMethod.InsertControlTextBetweenParagraphText, true, marks);
			int sectionCount = hwpx.sectionXMLFileList() == null ? 0 : hwpx.sectionXMLFileList().count();
			return clampInto(filename, ParsedDocument.DocumentFormat.HWPX, text, sectionCount);
		}
		catch (Exception e) {
			// **본문 XML 을 직접 읽는 것이 먼저다.** HWPX 는 zip 안에 Contents/sectionN.xml 로
			// 본문을 그대로 갖고 있어서, hwpxlib 이 넘어져도 텍스트는 <hp:t> 에 온전히 있다.
			// 미리보기와 달리 이것은 발췌가 아니라 전문이다(실측 표본: 618개 <hp:t>, 449문단).
			String body = sectionXmlText(bytes);
			if (body != null && !body.isBlank()) {
				return clampInto(filename, ParsedDocument.DocumentFormat.HWPX, body, 0);
			}
			// 본문 XML 도 못 읽으면 미리보기라도. 이쪽은 앞부분뿐이라 truncated 로 올린다.
			String preview = zipPreviewText(bytes);
			if (preview != null && !preview.isBlank()) {
				ParsedDocument partial =
						clampInto(filename, ParsedDocument.DocumentFormat.HWPX, preview, 0);
				return new ParsedDocument(partial.filename(), partial.format(), partial.text(), 0, true);
			}
			throw new DocumentParseException("HWPX 파싱 실패: " + filename, e);
		}
		finally {
			deleteQuietly(temp);
		}
	}

	/**
	 * HWPX 의 미리보기 텍스트({@code Preview/PrvText.txt}). HWP 쪽 {@link #previewText} 와 같은 취지다.
	 *
	 * <p>인코딩은 UTF-8 이다(HWP 의 OLE 스트림이 UTF-16LE 인 것과 다르다 — 실측 확인).
	 */
	/**
	 * HWPX 본문 XML({@code Contents/sectionN.xml})에서 직접 텍스트를 뽑는다.
	 *
	 * <p>hwpxlib 이 못 여는 파일이 있는데(실측 14건, 전부 계약예규·일반조건 같은 표준 문서),
	 * 그 파일들도 본문 XML 자체는 멀쩡하다. 미리보기 폴백이 그것들을 못 건진 이유는 단순하다 —
	 * {@code Preview/PrvText.txt} 가 아예 없는 포장이었다.
	 *
	 * <p>문단은 {@code <hp:p>}, 글자는 {@code <hp:t>} 다. 문단 단위로 줄을 나눠야 표와 조항이
	 * 붙지 않는다. 이름공간 접두사({@code hp:})는 jsoup XML 파서에서 태그 이름의 일부다.
	 */
	private static String sectionXmlText(byte[] bytes) {
		StringBuilder text = new StringBuilder();
		try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytes), ZIP_NAME_CHARSET)) {
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				String name = entry.getName() == null ? "" : entry.getName().toLowerCase();
				if (!name.startsWith("contents/section") || !name.endsWith(".xml")) {
					continue;
				}
				org.jsoup.nodes.Document xml = org.jsoup.Jsoup.parse(
						new String(zip.readAllBytes(), StandardCharsets.UTF_8), "",
						org.jsoup.parser.Parser.xmlParser());
				for (org.jsoup.nodes.Element paragraph : xml.select("hp|p")) {
					String line = paragraph.text();
					if (!line.isBlank()) {
						text.append(line).append('\n');
					}
				}
			}
		}
		catch (IOException | RuntimeException e) {
			return null;
		}
		return text.toString();
	}

	private static String zipPreviewText(byte[] bytes) {
		try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytes), ZIP_NAME_CHARSET)) {
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				if ("Preview/PrvText.txt".equalsIgnoreCase(entry.getName())) {
					return new String(zip.readAllBytes(), StandardCharsets.UTF_8);
				}
			}
			return null;
		}
		catch (IOException | RuntimeException e) {
			return null;
		}
	}

	// ── 평문·HTML ────────────────────────────────────────────────────────────
	/**
	 * 매직 넘버가 없는 형식들. 첨부에 실제로 섞여 온다(실측 {@code .htm} 18건 · {@code .txt} 1건).
	 *
	 * <p>{@code .htm} 은 태그를 걷어내야 본문이 된다 — 안 그러면 색인에 {@code <table>} 같은
	 * 마크업이 그대로 들어가 발췌가 읽히지 않는다. jsoup 은 이미 의존성에 있다.
	 */
	private ParsedDocument extractHtml(String filename, byte[] bytes) {
		String html = decodeText(bytes);
		String text = org.jsoup.Jsoup.parse(html).wholeText();
		return clampInto(filename, ParsedDocument.DocumentFormat.TEXT, text, 0);
	}

	private ParsedDocument extractPlainText(String filename, byte[] bytes) {
		return clampInto(filename, ParsedDocument.DocumentFormat.TEXT, decodeText(bytes), 0);
	}

	/**
	 * HML/HWPML — 한글의 XML 저장 형식.
	 *
	 * <p><b>이름이 {@code .hwp} 인 채로 온다.</b> 실측에서 "형식 미지원" 으로 닫힌 {@code .hwp}
	 * 23건이 전부 이것이었다(표본 3개 모두 루트가 {@code <HWPML>}). 바이너리 HWP 파서에 넣으면
	 * OLE 컨테이너가 아니라 넘어지므로, 판정은 {@link DocumentSniffer} 가 루트 요소로 한다.
	 *
	 * <p>문단({@code <P>}) 단위로 줄을 나눈다 — 전체 텍스트를 한 덩어리로 뽑으면 표와 문단이
	 * 붙어 버려 {@code specContentScore} 가 구조를 못 읽는다. {@code BINDATA}(그림·OLE 를
	 * base64 로 품고 있다)는 <b>반드시 걷어낸다</b>: 그대로 두면 수백 KB 의 base64 가 본문으로
	 * 들어가 색인이 부풀고 발췌가 쓰레기가 된다.
	 */
	private ParsedDocument extractHml(String filename, byte[] bytes) {
		try {
			org.jsoup.nodes.Document xml =
					org.jsoup.Jsoup.parse(decodeText(bytes), "", org.jsoup.parser.Parser.xmlParser());
			xml.select("BINDATA, BINITEM, SCRIPT, SCRIPTCODE").remove();
			StringBuilder text = new StringBuilder();
			for (org.jsoup.nodes.Element paragraph : xml.select("P")) {
				String line = paragraph.text();
				if (!line.isBlank()) {
					text.append(line).append('\n');
				}
			}
			String body = text.length() > 0 ? text.toString() : xml.text();
			if (body.isBlank()) {
				throw new DocumentParseException("HML 본문이 비어 있습니다: " + filename, null);
			}
			return clampInto(filename, ParsedDocument.DocumentFormat.HWP, body, 0);
		}
		catch (DocumentParseException e) {
			throw e;
		}
		catch (RuntimeException e) {
			throw new DocumentParseException("HML 파싱 실패: " + filename, e);
		}
	}

	/**
	 * 평문 디코딩. UTF-8 로 <b>엄격하게</b> 시도하고 실패하면 CP949 로 본다.
	 *
	 * <p>대체문자(U+FFFD)를 허용하는 느슨한 디코딩을 쓰면 CP949 문서가 조용히 물음표 덩어리가
	 * 되어 색인에 들어간다 — 검색이 안 되는데 실패로도 안 잡히는 최악의 상태다.
	 */
	private static String decodeText(byte[] bytes) {
		String text;
		try {
			text = StandardCharsets.UTF_8.newDecoder().decode(java.nio.ByteBuffer.wrap(bytes)).toString();
		}
		catch (java.nio.charset.CharacterCodingException e) {
			text = new String(bytes, ZIP_NAME_CHARSET);   // MS949
		}
		// BOM 은 본문이 아니다. 남겨 두면 XML 파서가 첫 요소를 못 찾고, 색인에는 보이지 않는
		// 문자가 첫 글자로 들어가 발췌 앞이 어긋난다.
		return text.startsWith("﻿") ? text.substring(1) : text;
	}

	// ── PDF ──────────────────────────────────────────────────────────────────
	private ParsedDocument extractPdf(String filename, byte[] bytes) {
		try (PDDocument pdf = Loader.loadPDF(bytes)) {
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setSortByPosition(true);   // 2단 편집·표에서 읽는 순서를 좌→우, 위→아래로
			String text = stripper.getText(pdf);
			return clampInto(filename, ParsedDocument.DocumentFormat.PDF, text, pdf.getNumberOfPages());
		}
		catch (IOException e) {
			throw new DocumentParseException("PDF 파싱 실패: " + filename, e);
		}
	}

	// ── HWP 5.0 (바이너리) ─────────────────────────────────────────────────────
	/**
	 * HWP 한 건의 파싱 상한.
	 *
	 * <p><b>hwplib 은 어떤 파일에서 사실상 끝나지 않는다.</b> 백필 실측에서 워커 6개가 전부
	 * 여기에 물려 7분 넘게 CPU 를 태우며 한 건도 진전이 없었다. 스택은 늘 같았다:
	 *
	 * <pre>
	 *   ForParagraph.controlAndMemo → skipETCRecord
	 *     → StreamReaderForCompress.readBytes → InputStream.read   (RUNNABLE, 100% CPU)
	 * </pre>
	 *
	 * 레코드 헤더가 말하는 길이가 실제와 어긋나면 압축 스트림에서 그 길이만큼 <b>한 바이트씩</b>
	 * 읽으려 든다. 예외도 안 나고 그냥 안 끝난다.
	 *
	 * <p>그래서 시간을 스트림에 건다. 취소로는 못 막는다 — CPU 를 태우는 루프는 인터럽트를
	 * 확인하지 않으므로 {@code Future.cancel} 은 스레드를 영영 놔주지 않는다. 반면 이 루프는
	 * <b>읽기를 계속하므로</b>, 읽기마다 마감을 보게 하면 즉시 빠져나온다.
	 *
	 * <p>20초로 잡은 근거: 정상 HWP 는 실측 평균 <b>0.8초</b>에 끝난다(첫 회차 200파일/41초,
	 * 동시 4). 25배를 줘도 넉넉하고, 반대로 상한이 크면 물린 파일 하나가 워커 하나를 그만큼
	 * 붙든다 — 6개가 동시에 물리면 회차가 통째로 멈춘다.
	 */
	private static final long HWP_PARSE_TIMEOUT_MS = 20_000;

	// hwplib 은 hwpxlib 과 클래스 이름이 겹친다(TextExtractor·TextExtractMethod) — 그래서
	// 여기선 전부 완전수식명으로 쓴다. HWP 내부는 UCS-2 라 hwplib 이 디코딩해 자바 String(유니코드)로
	// 준다 — 한글/UTF-8 은 그대로 보존된다.
	private ParsedDocument extractHwp(String filename, byte[] bytes) {
		try {
			kr.dogfoot.hwplib.object.HWPFile hwp =
					kr.dogfoot.hwplib.reader.HWPReader.fromInputStream(
							new DeadlineInputStream(new ByteArrayInputStream(bytes), HWP_PARSE_TIMEOUT_MS));
			// 컨트롤(표·글상자) 텍스트도 문단 사이에 끼워 뽑는다 — 규격서의 표 안 값이 핵심이다.
			String text = kr.dogfoot.hwplib.tool.textextractor.TextExtractor.extract(
					hwp, kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod.InsertControlTextBetweenParagraphText);
			return clampInto(filename, ParsedDocument.DocumentFormat.HWP, text, sectionCount(hwp));
		}
		catch (Exception e) {   // hwplib 은 checked Exception 을 던진다
			// 본문을 못 읽어도 미리보기는 대개 살아 있다 — 아래 주석 참고.
			String preview = previewText(bytes);
			if (preview != null && !preview.isBlank()) {
				ParsedDocument partial =
						clampInto(filename, ParsedDocument.DocumentFormat.HWP, preview, 0);
				// **잘렸다고 표시한다.** 미리보기는 문서의 앞부분일 뿐이라, 온전한 본문인 척하면
				// "그 키워드가 없는 공고" 와 "앞부분까지만 읽은 공고" 가 화면에서 똑같아진다.
				return new ParsedDocument(partial.filename(), partial.format(), partial.text(), 0, true);
			}
			throw new DocumentParseException("HWP 파싱 실패: " + filename, e);
		}
	}

	/**
	 * HWP 의 미리보기 텍스트({@code PrvText} 스트림).
	 *
	 * <p><b>왜 이 폴백이 필요한가.</b> hwplib 이 {@code This is not paragraph} 로 넘어지는 문서군이
	 * 있다(실측 232건, 백필 실패의 최대 항목). 그런데 그 파일들도 CFB 안에 미리보기 스트림은
	 * 온전히 갖고 있다 — 표본 3개 전부 그랬다. 본문을 못 읽었다고 <b>아무것도 없는 것으로</b>
	 * 두면 "규격서에 그 부품이 없다"와 "규격서를 못 읽었다"가 검색 결과에서 구분되지 않는다.
	 *
	 * <p>완전한 본문이 아니다. 한글이 저장할 때 만들어 두는 앞부분 발췌라, 제목·발주기관·개요는
	 * 대개 들어 있고 뒤쪽 세부 사양은 없다. 그래서 {@code truncated} 로 표시해 올린다.
	 *
	 * <p>hwplib 을 전혀 건드리지 않는다 — 그래서 <b>파서가 멈춰 세워진 뒤에도</b> 쓸 수 있다
	 * ({@code HwpSubprocessExtractor} 의 타임아웃 경로가 이것을 부른다).
	 *
	 * @return 미리보기 평문. 스트림이 없거나 읽지 못하면 {@code null}(실패로 만들지 않는다)
	 */
	public static String previewText(byte[] bytes) {
		try (POIFSFileSystem fs = new POIFSFileSystem(new ByteArrayInputStream(bytes));
				InputStream stream = fs.getRoot().createDocumentInputStream("PrvText")) {
			// PrvText 는 UTF-16LE 평문이다(압축도 암호화도 없다).
			return new String(stream.readAllBytes(), StandardCharsets.UTF_16LE);
		}
		catch (IOException | RuntimeException e) {
			return null;
		}
	}

	/**
	 * 마감시한이 지나면 읽기를 거부하는 스트림.
	 *
	 * <p>파서가 다음 바이트를 요구하는 순간 {@link IOException} 이 나고, 호출부의 catch 가 그것을
	 * {@link DocumentParseException} 으로 바꾼다 — 그 파일 하나만 실패로 닫히고 나머지는 계속 간다.
	 *
	 * <p>{@code available()} 까지 막는 이유: 파서가 그 값을 보고 버퍼를 잡는 경로가 있어서,
	 * 읽기만 막으면 마감 이후에도 큰 배열을 한 번 더 할당하려 들 수 있다.
	 */
	static final class DeadlineInputStream extends java.io.FilterInputStream {

		private final long deadlineNanos;

		DeadlineInputStream(java.io.InputStream in, long timeoutMs) {
			super(in);
			this.deadlineNanos = System.nanoTime() + timeoutMs * 1_000_000L;
		}

		private void check() throws IOException {
			if (System.nanoTime() > deadlineNanos) {
				throw new IOException("파싱 시간 초과 — 파일이 파서를 멈춰 세웠습니다");
			}
		}

		@Override
		public int read() throws IOException {
			check();
			return super.read();
		}

		@Override
		public int read(byte[] buffer, int offset, int length) throws IOException {
			check();
			return super.read(buffer, offset, length);
		}

		@Override
		public long skip(long n) throws IOException {
			check();
			return super.skip(n);
		}

		@Override
		public int available() throws IOException {
			check();
			return super.available();
		}
	}

	/** 구역 수(모르면 0). 버전에 따라 접근자가 달라 실패해도 0 으로 넘긴다 — pageCount 는 부가정보다. */
	private static int sectionCount(kr.dogfoot.hwplib.object.HWPFile hwp) {
		try {
			return hwp.getBodyText().getSectionList().size();
		}
		catch (RuntimeException e) {
			return 0;
		}
	}

	// ── XLSX / XLS (엑셀) ──────────────────────────────────────────────────────
	// 규격서가 엑셀 표(품목·수량·규격)로 오는 경우가 흔하다. 셀을 탭, 행을 줄바꿈으로 펴면
	// SpecFileSelector 의 표 밀도(탭 행)와 LLM 부품 추출이 그대로 읽는다. WorkbookFactory 가
	// xlsx(OOXML)·xls(OLE2)를 자동 판별한다.
	private ParsedDocument extractXlsx(String filename, byte[] bytes) {
		try (Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(bytes))) {
			DataFormatter formatter = new DataFormatter();
			StringBuilder sb = new StringBuilder();
			int sheetCount = wb.getNumberOfSheets();
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = wb.getSheetAt(s);
				if (sheet == null) {
					continue;
				}
				if (s > 0) {
					sb.append("\n\n");
				}
				for (Row row : sheet) {
					int last = row.getLastCellNum();
					if (last <= 0) {
						continue;
					}
					StringBuilder line = new StringBuilder();
					for (int c = 0; c < last; c++) {
						if (c > 0) {
							line.append('\t');
						}
						Cell cell = row.getCell(c);
						if (cell != null) {
							// 셀 안의 탭/줄바꿈은 공백으로 — 표 구조(탭=열, 줄=행)를 깨지 않게.
							line.append(formatter.formatCellValue(cell).replace('\t', ' ').replace('\n', ' '));
						}
					}
					if (!line.toString().isBlank()) {
						sb.append(line).append('\n');
					}
				}
			}
			return clampInto(filename, ParsedDocument.DocumentFormat.XLSX, sb.toString(), sheetCount);
		}
		catch (IOException | RuntimeException e) {
			throw new DocumentParseException("XLSX 파싱 실패: " + filename, e);
		}
	}

	// ── 공통 ─────────────────────────────────────────────────────────────────
	private ParsedDocument clampInto(String filename, ParsedDocument.DocumentFormat format,
			String rawText, int pageCount) {
		String text = rawText == null ? "" : rawText;
		boolean truncated = text.length() > MAX_CHARS;
		if (truncated) {
			text = text.substring(0, MAX_CHARS);
		}
		// 캐리지리턴 정규화만 한다. 좌표계를 훼손하는 변형은 하지 않는다 — 인용 대조가 깨진다.
		text = text.replace("\r\n", "\n").replace("\r", "\n");
		return new ParsedDocument(filename, format, text, pageCount, truncated);
	}

	private static java.io.File writeTemp(byte[] bytes, String suffix) {
		try {
			java.io.File temp = java.io.File.createTempFile("g2b-spec-", suffix);
			java.nio.file.Files.write(temp.toPath(), bytes);
			return temp;
		}
		catch (IOException e) {
			throw new UncheckedIOException("임시 파일 생성 실패", e);
		}
	}

	private static void deleteQuietly(java.io.File file) {
		if (file != null) {
			//noinspection ResultOfMethodCallIgnored
			file.delete();
		}
	}

	/** 확장자로 형식을 알 수 없음. 4xx 로 다뤄야 한다(재시도해도 결과가 같다). */
	public static class UnsupportedDocumentException extends RuntimeException {
		public UnsupportedDocumentException(String message) {
			super(message);
		}
	}

	/** 파일이 깨졌거나 형식과 내용이 다름. */
	public static class DocumentParseException extends RuntimeException {
		public DocumentParseException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
