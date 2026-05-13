package org.cloud.controller.recall;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.service.recall.RecallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recall")
@RequiredArgsConstructor
public class RecallController { // Recall 테스트

	private final RecallService recallService;
	
	@GetMapping("/check/{lotNumber}")
	public RecallResultResponse check(@PathVariable("lotNumber") String lotNumber) {
	    return recallService.checkRecall(lotNumber);
	}
	
	@PostMapping("/image")
	public ResponseEntity<?> checkRecallByImage(
			@RequestParam("image") MultipartFile image) {
		return ResponseEntity.ok(recallService.checkByImage(image));
	};
}
