package com.electerior.g2bmaster.attachment;

import static org.assertj.core.api.Assertions.assertThat;

import com.electerior.g2bmaster.attachment.SpecFileSelector.Candidate;
import com.electerior.g2bmaster.attachment.SpecFileSelector.Choice;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpecFileSelectorTest {

	@Test
	@DisplayName("isSpecFile — 파일명에 '규격서'가 있어야 한다")
	void isSpecFile() {
		assertThat(SpecFileSelector.isSpecFile("물품규격서.hwp")).isTrue();
		assertThat(SpecFileSelector.isSpecFile("입찰공고문.pdf")).isFalse();
		assertThat(SpecFileSelector.isSpecFile(null)).isFalse();
	}

	@Test
	@DisplayName("'규격입찰 설명서'는 규격서가 아니다 — 2026-08-03 오선택 사례")
	void explanatoryDocumentOutranksNothing() {
		assertThat(SpecFileSelector.specFilenameRank("규격입찰 설명서.hwp")).isEqualTo(3);
		assertThat(SpecFileSelector.specFilenameRank("물품 규격서.hwp")).isZero();
	}

	@Test
	@DisplayName("파일명 순위 — 낮을수록 규격서 유력")
	void filenameRanking() {
		assertThat(SpecFileSelector.specFilenameRank("과업지시서.hwp")).isZero();
		assertThat(SpecFileSelector.specFilenameRank("시방서.pdf")).isZero();
		assertThat(SpecFileSelector.specFilenameRank("SPEC_v2.docx")).isZero();
		assertThat(SpecFileSelector.specFilenameRank("요구사항 정리.hwp")).isEqualTo(1);
		assertThat(SpecFileSelector.specFilenameRank("입찰공고.pdf")).isEqualTo(2);
		assertThat(SpecFileSelector.specFilenameRank("첨부.hwp")).isEqualTo(1);
		assertThat(SpecFileSelector.specFilenameRank("청렴서약서.hwp")).isEqualTo(3);
	}

	@Test
	@DisplayName("'내역서'는 규격서 취급하지 않는다 — 공사 물량 산출서가 대부분이라 일부러 뺐다")
	void quantityScheduleIsNotSpec() {
		assertThat(SpecFileSelector.specFilenameRank("물량내역서.xlsx")).isNotZero();
	}

	@Test
	@DisplayName("빈 문서는 0점")
	void emptyScoresZero() {
		assertThat(SpecFileSelector.specContentScore("", "규격서.hwp")).isZero();
		assertThat(SpecFileSelector.specContentScore("   ", "규격서.hwp")).isZero();
	}

	@Test
	@DisplayName("짧은 문서는 감점, 표와 단위가 많으면 가점")
	void contentScoring() {
		String thin = "규격 안내";
		String rich = ("| 품명 | 규격 | 수량 | 단위 |\n".repeat(30))
				+ "납품 모델 제조사 성능 인증 재질 치수 용량\n".repeat(20)
				+ "128GB 메모리 2대, 4TB 저장장치 8개, 16코어 CPU 1식\n".repeat(15);

		assertThat(SpecFileSelector.specContentScore(thin, "규격서.hwp"))
				.isLessThan(SpecFileSelector.specContentScore(rich, "규격서.hwp"));
		assertThat(SpecFileSelector.specContentScore(rich, "규격서.hwp")).isPositive();
	}

	@Test
	@DisplayName("같은 내용이라도 파일명이 '설명서'면 크게 감점된다")
	void filenamePenaltyApplies() {
		String body = "규격 사양 수량 단위 납품\n".repeat(50);

		int asSpec = SpecFileSelector.specContentScore(body, "물품규격서.hwp");
		int asExplanation = SpecFileSelector.specContentScore(body, "규격입찰 설명서.hwp");

		assertThat(asSpec - asExplanation).isEqualTo(40);   // +15 vs -25
	}

	@Test
	@DisplayName("chooseSpec — 후보가 없으면 none")
	void chooseNothing() {
		Choice choice = SpecFileSelector.chooseSpec(List.of(), null, 0);
		assertThat(choice.chosen()).isNull();
		assertThat(choice.confidence()).isEqualTo("none");
	}

	@Test
	@DisplayName("chooseSpec — AI 판정이 없으면 최고점을 heuristic 으로 고른다")
	void chooseHeuristicWithoutAi() {
		Choice choice = SpecFileSelector.chooseSpec(
				List.of(new Candidate("a.hwp", "", 10), new Candidate("b.hwp", "", 50)), null, 5);

		assertThat(choice.chosen().name()).isEqualTo("b.hwp");
		assertThat(choice.confidence()).isEqualTo("heuristic");
	}

	@Test
	@DisplayName("chooseSpec — AI 가 맞다고 하면 confirmed")
	void confirmedByAi() {
		Choice choice = SpecFileSelector.chooseSpec(
				List.of(new Candidate("b.hwp", "", 50)), true, 5);
		assertThat(choice.confidence()).isEqualTo("confirmed");
	}

	@Test
	@DisplayName("chooseSpec — AI 가 아니라고 하면 차점자로 내려간다")
	void fallsBackWhenAiRejectsTop() {
		Choice choice = SpecFileSelector.chooseSpec(
				List.of(new Candidate("top.hwp", "", 50), new Candidate("next.hwp", "", 30)), false, 5);

		assertThat(choice.chosen().name()).isEqualTo("next.hwp");
		assertThat(choice.confidence()).isEqualTo("heuristic");
	}

	@Test
	@DisplayName("chooseSpec — 차점자가 기준 미달이면 최고점을 estimated 로 유지")
	void keepsTopWhenNoAlternative() {
		Choice choice = SpecFileSelector.chooseSpec(
				List.of(new Candidate("top.hwp", "", 50), new Candidate("next.hwp", "", 1)), false, 5);

		assertThat(choice.chosen().name()).isEqualTo("top.hwp");
		assertThat(choice.confidence()).isEqualTo("estimated");
	}

	@Test
	@DisplayName("chooseSpec — 최고점이 기준 미달이면 estimated")
	void estimatedWhenBelowMinScore() {
		Choice choice = SpecFileSelector.chooseSpec(List.of(new Candidate("a.hwp", "", 2)), true, 10);
		assertThat(choice.confidence()).isEqualTo("estimated");
	}
}
