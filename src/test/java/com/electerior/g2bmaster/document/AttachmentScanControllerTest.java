package com.electerior.g2bmaster.document;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code POST /api/scan-attachments} 의 HTTP 계약.
 *
 * <p>여기서 지키는 것은 <b>프론트와의 봉투</b>다. {@code useAttachmentScan.ts} 가 이 모양에
 * 맞춰져 있고, 필드 하나만 이름이 달라도 화면은 "📄 파일 스캔 오류" 를 띄운다 — 서버는 200 을
 * 주고 로그도 깨끗한데 화면만 깨지는, 조용히 틀리는 종류다.
 */
class AttachmentScanControllerTest {

	private DocumentIndexRepository repository;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		repository = mock(DocumentIndexRepository.class);
		mockMvc = MockMvcBuilders.standaloneSetup(new AttachmentScanController(repository)).build();
	}

	@Test
	@DisplayName("색인된 본문에서 걸린 공고를 발췌와 함께 준다")
	void returnsMatchesWithExcerpt() throws Exception {
		when(repository.findMatchable(anyList(), anyList())).thenReturn(List.of(
				new DocumentIndexRepository.DocumentText("R26BK01", "G2B", 1, "규격서.hwp",
						"본 사업은 GPU 서버 8대를 납품하는 건으로 한다.")));
		when(repository.findIndexedNoticeIds(anyList())).thenReturn(Set.of("R26BK01"));

		mockMvc.perform(post("/api/scan-attachments").contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"scans":[{"id":"row|0","bidNtceNo":"R26BK01","bidNtceSqNo":"000",
								           "_tab":"bid-announce","fileEntries":[]}],
								 "fileKeywords":["서버"],"candidateCount":1}"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.matches.length()").value(1))
				// id 는 화면이 만든 내부 키다. 서버는 의미를 모르고 그대로 돌려줘야 짝이 맞는다.
				.andExpect(jsonPath("$.matches[0].id").value("row|0"))
				.andExpect(jsonPath("$.matches[0].matchedKeywords[0]").value("서버"))
				.andExpect(jsonPath("$.matches[0].excerpt").value(org.hamcrest.Matchers.containsString("서버")))
				.andExpect(jsonPath("$.matches[0].documentTags").isArray())
				.andExpect(jsonPath("$.scanned").value(1))
				.andExpect(jsonPath("$.cacheHits").value(1))
				.andExpect(jsonPath("$.notIndexed").value(0));
	}

	/**
	 * SQL 은 후보만 좁힌다 — ngram(2) 는 '정수기' 를 정수+수기 로 쪼개 찾으므로 우리 매칭 규칙과
	 * 의미가 다르다. 최종 판정은 자바가 하고, 걸러진 후보는 응답에 나가지 않아야 한다.
	 */
	@Test
	@DisplayName("SQL 이 준 후보라도 실제로 안 걸리면 결과에서 뺀다")
	void sqlCandidateIsStillVerified() throws Exception {
		when(repository.findMatchable(anyList(), anyList())).thenReturn(List.of(
				new DocumentIndexRepository.DocumentText("R26BK01", "G2B", 1, "규격서.hwp",
						"정수 처리 설비 납품")));
		when(repository.findIndexedNoticeIds(anyList())).thenReturn(Set.of("R26BK01"));

		mockMvc.perform(post("/api/scan-attachments").contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"scans":[{"id":"row|0","bidNtceNo":"R26BK01","fileEntries":[]}],
								 "fileKeywords":["정수기"]}"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.matches.length()").value(0));
	}

	/**
	 * "안 걸림" 과 "아직 색인 전" 은 다른 상태다. 구분하지 않으면 규격서가 아직 안 뽑힌 공고를
	 * 사용자가 "그 키워드 없는 공고" 로 오해하고 지나친다.
	 */
	@Test
	@DisplayName("아직 색인 안 된 공고 수를 따로 알려준다")
	void reportsNotIndexed() throws Exception {
		when(repository.findMatchable(anyList(), anyList())).thenReturn(List.of());
		when(repository.findIndexedNoticeIds(anyList())).thenReturn(Set.of("R26BK01"));

		mockMvc.perform(post("/api/scan-attachments").contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"scans":[{"id":"a","bidNtceNo":"R26BK01","fileEntries":[]},
								          {"id":"b","bidNtceNo":"R26BK02","fileEntries":[]}],
								 "fileKeywords":["서버"]}"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cacheHits").value(1))
				.andExpect(jsonPath("$.notIndexed").value(1));
	}

	@Test
	@DisplayName("한 공고에 화면 행이 여럿이면 모두에 결과를 붙인다")
	void oneNoticeCanMapToSeveralRows() throws Exception {
		when(repository.findMatchable(anyList(), anyList())).thenReturn(List.of(
				new DocumentIndexRepository.DocumentText("R26BK01", "G2B", 1, "규격서.hwp", "서버 납품")));
		when(repository.findIndexedNoticeIds(anyList())).thenReturn(Set.of("R26BK01"));

		mockMvc.perform(post("/api/scan-attachments").contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"scans":[{"id":"row|0","bidNtceNo":"R26BK01","fileEntries":[]},
								          {"id":"row|1","bidNtceNo":"R26BK01","fileEntries":[]}],
								 "fileKeywords":["서버"]}"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.matches.length()").value(2));
	}

	@Test
	@DisplayName("키워드가 없으면 DB 를 건드리지 않고 조기 반환한다")
	void emptyKeywordsShortCircuits() throws Exception {
		mockMvc.perform(post("/api/scan-attachments").contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"scans":[{"id":"row|0","bidNtceNo":"R26BK01","fileEntries":[]}],
								 "fileKeywords":["  "]}"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.scanned").value(0))
				.andExpect(jsonPath("$.matches.length()").value(0));

		verify(repository, never()).findMatchable(any(), any());
	}

	/**
	 * 입찰 불가 조항 판정은 아직 이식하지 않았다. 빈 목록이라야 화면이 아무것도 제외하지 않는다 —
	 * 잘못 제외해서 입찰 기회를 지우는 것보다 안전한 방향이다.
	 */
	@Test
	@DisplayName("exclusions 는 항상 빈 목록이다 — 미이식이지 '제외 없음' 이 아니다")
	void exclusionsAreAlwaysEmpty() throws Exception {
		when(repository.findMatchable(anyList(), anyList())).thenReturn(List.of());
		when(repository.findIndexedNoticeIds(anyList())).thenReturn(Set.of());

		mockMvc.perform(post("/api/scan-attachments").contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"scans":[{"id":"row|0","bidNtceNo":"R26BK01","fileEntries":[]}],
								 "fileKeywords":["서버"],"excludeBlockingClauses":true,"scanBlocking":true}"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.exclusions.length()").value(0));
	}
}
