package com.electerior.g2bmaster.document;

import com.electerior.g2bmaster.config.OpenApiConfig;
import com.electerior.g2bmaster.security.RequireAppAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 첨부 본문 색인 운영 표면.
 *
 * <p>주기 실행({@code g2b.documents.enabled})은 기본이 꺼짐이라, 최초 백필과 파서 교체 후
 * 재추출은 여기로 돌린다. 한 번 부르면 <b>한 회차</b>가 돈다 — 남은 일감이 배치 크기보다
 * 많으면 여러 번 불러야 한다. 한 번의 호출이 몇 시간을 붙들고 있지 않게 하려는 것이다.
 */
@RestController
@RequestMapping("/api/documents")
@Tag(name = OpenApiConfig.TAG_ATTACHMENT)
public class DocumentAdminController {

	private final DocumentExtractionWorker worker;
	private final DocumentIndexRepository repository;

	public DocumentAdminController(DocumentExtractionWorker worker, DocumentIndexRepository repository) {
		this.worker = worker;
		this.repository = repository;
	}

	@Operation(summary = "첨부 본문 색인 현황",
			description = "상태별 문서 수와, 첨부는 있는데 아직 색인이 안 끝난 공고 수.")
	@GetMapping("/status")
	public Map<String, Object> status() {
		return Map.of("documents", repository.stats(), "extractorVersion", ExtractorVersion.CURRENT);
	}

	@Operation(summary = "추출 회차 수동 실행",
			description = "한 회차만 돈다. 이미 돌고 있으면 `skipped: true` 로 즉시 반환한다.")
	@PostMapping("/extract")
	@RequireAppAuth
	public Map<String, Object> extract() {
		DocumentExtractionWorker.Result result = worker.runOnce();
		if (result == null) {
			return Map.of("skipped", true, "reason", "이미 실행 중입니다");
		}
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("seeded", result.seeded());
		body.put("extracted", result.extracted());
		body.put("failed", result.failed());
		body.put("noticesCompleted", result.noticesCompleted());
		return body;
	}
}
