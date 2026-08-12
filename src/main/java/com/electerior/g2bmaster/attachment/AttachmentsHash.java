package com.electerior.g2bmaster.attachment;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * 첨부 목록의 지문 — {@code bid_notice.attachments_hash} 의 값.
 *
 * <p><b>이 해시 하나가 첨부 재추출의 유일한 방아쇠다.</b> 값이 달라지면 그 공고의 첨부 본문
 * 색인을 다시 만든다. 그러니 "언제 달라져야 하는가"가 이 클래스의 계약이다.
 *
 * <p><b>왜 이것으로 충분한가.</b> 서로 다른 시점에 적재된 입찰공고 6,104건을 비교한 실측:
 *
 * <pre>
 *   차수 같음 · 첨부 같음   5,974건 (97.87%)
 *   차수 오름 · 첨부 다름     130건 ( 2.13%)
 *   차수 같음 · 첨부 다름       0건
 * </pre>
 *
 * 첨부가 바뀌는 일은 <b>예외 없이 차수 상승을 동반</b>했다. 구조적으로 그렇다 — 첨부 URL 에
 * 차수가 박혀 있어서({@code bidPbancOrd=000} → {@code 001}) 정정이 나면 URL 이 통째로 바뀐다.
 * 따라서 목록만 비교해도 관측된 변경의 100% 를 잡는다. 파일을 다시 내려받아 바이트를 비교할
 * 필요가 없고, 나라장터에 추가 요청을 보낼 필요도 없다 — 적재기가 이미 받아 든 응답 안에 있다.
 *
 * <p><b>왜 정렬하지 않는가.</b> 목록은 슬롯 순서({@code ntceSpecDocUrl1..10})로 만들어지고,
 * 그 슬롯 번호가 곧 {@code bid_notice_document.file_seq} 다. 순서가 바뀌었다는 것은 파일과
 * 슬롯의 대응이 바뀌었다는 뜻이므로 <b>재추출이 맞다</b>. 정렬해서 순서 변화를 덮으면
 * file_seq 가 엉뚱한 파일을 가리킨 채로 남는다. (첨부 <i>목록</i>을 정렬하는
 * {@code AnalysisInputHasher} 와 목적이 다르다 — 그쪽은 같은 입력의 재사용 판정이다.)
 *
 * <p><b>JSON 문자열을 그대로 해시한다.</b> 매퍼가 만든 {@code attachment_urls} 를 파싱하지 않고
 * 바이트로 넘긴다. 저장소가 Jackson 을 다시 부르지 않는다는 규칙(BidNoticeRow 주석)을 지키고,
 * 무엇보다 <b>저장된 값과 해시가 어긋날 수 없다</b> — 같은 문자열에서 나오기 때문이다.
 * 대가는 하나: 매퍼가 JSON 표기(키 순서·필드 추가)를 바꾸면 모든 해시가 한꺼번에 달라져
 * 전량 재추출이 걸린다. 표기를 바꿀 일이 생기면 그 사실을 알고 바꿔야 한다.
 */
public final class AttachmentsHash {

	private AttachmentsHash() {
	}

	/**
	 * @param attachmentUrlsJson 매퍼가 만든 {@code [{"name":…,"url":…}]} 문자열. 첨부가 없으면 null
	 * @return sha1 hex 40자, 첨부가 없으면 {@code null}
	 */
	public static String of(String attachmentUrlsJson) {
		// 첨부 없음과 "빈 배열"을 구분하지 않는다 — 둘 다 뽑을 문서가 없다는 같은 뜻이다.
		if (attachmentUrlsJson == null || attachmentUrlsJson.isBlank() || "[]".equals(attachmentUrlsJson.trim())) {
			return null;
		}
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			byte[] digest = sha1.digest(attachmentUrlsJson.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(digest);
		} catch (NoSuchAlgorithmException e) {
			// SHA-1 은 모든 JVM 이 반드시 제공한다(MessageDigest 규격). 여기 오면 런타임이 깨진 것이다.
			throw new IllegalStateException("SHA-1 을 쓸 수 없습니다", e);
		}
	}
}
