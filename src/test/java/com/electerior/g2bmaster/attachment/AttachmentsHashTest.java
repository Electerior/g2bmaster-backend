package com.electerior.g2bmaster.attachment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 첨부 목록 지문.
 *
 * <p>이 해시는 첨부 재추출의 유일한 방아쇠다. 틀리는 방향이 둘인데 증상이 정반대다.
 *
 * <ul>
 *   <li><b>덜 민감하면</b> — 정정으로 규격서가 바뀌었는데 색인이 낡은 본문을 계속 내준다.
 *       화면은 멀쩡해 보이고, 사용자는 예전 규격으로 입찰을 판단한다.</li>
 *   <li><b>더 민감하면</b> — 아무것도 안 바뀌었는데 매 적재마다 전량 재추출이 걸린다.
 *       하루 3,643개 파일을 다시 내려받는다.</li>
 * </ul>
 */
class AttachmentsHashTest {

	private static final String TWO_FILES = """
			[{"name":"공고문.hwp","url":"https://www.g2b.go.kr/x?bidPbancNo=R26BK01&bidPbancOrd=000&fileSeq=1"},\
			{"name":"규격서.pdf","url":"https://www.g2b.go.kr/x?bidPbancNo=R26BK01&bidPbancOrd=000&fileSeq=2"}]""";

	@Test
	@DisplayName("첨부가 없으면 해시도 없다 — null·공백·빈 배열 모두")
	void noAttachmentsMeansNoHash() {
		assertThat(AttachmentsHash.of(null)).isNull();
		assertThat(AttachmentsHash.of("")).isNull();
		assertThat(AttachmentsHash.of("   ")).isNull();
		assertThat(AttachmentsHash.of("[]")).isNull();
	}

	@Test
	@DisplayName("같은 목록은 언제 불러도 같은 값이다 — 40자 hex")
	void sameListSameHash() {
		String first = AttachmentsHash.of(TWO_FILES);

		assertThat(first).hasSize(40).matches("[0-9a-f]{40}");
		assertThat(AttachmentsHash.of(TWO_FILES)).isEqualTo(first);
	}

	/**
	 * 정정공고의 실제 모습이다 — 첨부 URL 에 차수가 박혀 있어서({@code bidPbancOrd}) 정정이
	 * 나면 URL 이 통째로 바뀐다. 실측에서 첨부 변경 130건이 전부 이 형태였다.
	 */
	@Test
	@DisplayName("차수가 오르면(URL 이 바뀌면) 해시가 달라진다")
	void orderBumpChangesHash() {
		String corrected = TWO_FILES.replace("bidPbancOrd=000", "bidPbancOrd=001");

		assertThat(AttachmentsHash.of(corrected)).isNotEqualTo(AttachmentsHash.of(TWO_FILES));
	}

	@Test
	@DisplayName("파일이 추가·삭제되면 해시가 달라진다")
	void listSizeChangesHash() {
		String oneFile = """
				[{"name":"공고문.hwp","url":"https://www.g2b.go.kr/x?bidPbancNo=R26BK01&bidPbancOrd=000&fileSeq=1"}]""";

		assertThat(AttachmentsHash.of(oneFile)).isNotEqualTo(AttachmentsHash.of(TWO_FILES));
	}

	@Test
	@DisplayName("URL 이 같아도 파일명이 바뀌면 해시가 달라진다")
	void renameChangesHash() {
		String renamed = TWO_FILES.replace("규격서.pdf", "규격서(수정).pdf");

		assertThat(AttachmentsHash.of(renamed)).isNotEqualTo(AttachmentsHash.of(TWO_FILES));
	}

	/**
	 * 정렬하지 않는 것은 의도다. 슬롯 번호가 곧 {@code bid_notice_document.file_seq} 이므로,
	 * 순서가 바뀌었다는 것은 파일과 슬롯의 대응이 바뀌었다는 뜻이다 — 재추출이 맞다.
	 * 정렬해서 이 변화를 덮으면 file_seq 가 엉뚱한 파일을 가리킨 채로 남는다.
	 */
	@Test
	@DisplayName("순서가 바뀌면 해시가 달라진다 — 슬롯 번호가 곧 file_seq 다")
	void orderOfFilesMatters() {
		String swapped = """
				[{"name":"규격서.pdf","url":"https://www.g2b.go.kr/x?bidPbancNo=R26BK01&bidPbancOrd=000&fileSeq=2"},\
				{"name":"공고문.hwp","url":"https://www.g2b.go.kr/x?bidPbancNo=R26BK01&bidPbancOrd=000&fileSeq=1"}]""";

		assertThat(AttachmentsHash.of(swapped)).isNotEqualTo(AttachmentsHash.of(TWO_FILES));
	}
}
