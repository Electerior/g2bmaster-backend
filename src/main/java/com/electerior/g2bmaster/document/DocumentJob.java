package com.electerior.g2bmaster.document;

/**
 * 추출 워커가 집어 든 일감 한 건 — {@code bid_notice_document} 한 행.
 *
 * <p>{@code bodyText} 가 없는 것이 의도다. 일감을 고를 때 본문까지 읽으면 배치 하나가
 * 수십 MB 가 된다(문서 평균 15,000자). 워커가 필요한 것은 "무엇을 어디서 받아오나"뿐이고,
 * 이전 본문은 이력 이관이 SQL 안에서 직접 옮긴다.
 *
 * @param sha256 지금 색인에 들어 있는 본문의 원본 해시. 새로 받은 파일이 이 값과 같으면
 *               재추출이 필요 없다(추출기 버전만 갱신하고 끝낸다)
 */
public record DocumentJob(
		long id,
		String noticeId,
		String source,
		String noticeOrder,
		int fileSeq,
		String fileName,
		String sourceUrl,
		String sha256,
		String extractorVersion,
		int retryCount) {}
