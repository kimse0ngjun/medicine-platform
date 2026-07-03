package org.cloud.controller.verification;

import java.util.List;

import org.cloud.dto.verification.LotVerificationRequest;
import org.cloud.dto.verification.ProductVerificationRequest;
import org.cloud.dto.verification.VerificationResponse;
import org.cloud.service.verification.VerificationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/verifications")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/product")
    public ResponseEntity<VerificationResponse> product(@RequestBody ProductVerificationRequest req) {
        return ResponseEntity.ok(verificationService.verifyProduct(req));
    }

    @PostMapping("/lot")
    public ResponseEntity<VerificationResponse> lot(@RequestBody LotVerificationRequest req) {
        return ResponseEntity.ok(verificationService.verifyLot(req));
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public VerificationResponse verifyImage(
            @RequestPart("file") MultipartFile file
    ) {
        return verificationService.verifyImage(file);
    }
    
    // 조회
    @GetMapping("/{verificationId}")
    public ResponseEntity<VerificationResponse> get(
            @PathVariable Long verificationId
    ) {

        return ResponseEntity.ok(
                verificationService.get(verificationId)
        );
    }
    
    @GetMapping
    public ResponseEntity<List<VerificationResponse>> list() {
    	return ResponseEntity.ok(
    			verificationService.list()
    			);
    }
    
    // 삭제
    @DeleteMapping("/{verificationId}")
    public ResponseEntity<Void> delete(
            @PathVariable("verificationId") Long verificationId
    ) {

        verificationService.delete(verificationId);

        return ResponseEntity.noContent().build();
    }
    
//    // 분석
//    @PostMapping("/{id}/analyze")
//    public ResponseEntity<Map<String, String>> analyze(@PathVariable Long id) {
//        String result = verificationService.analyze(id);
//        return ResponseEntity.ok(Map.of("analysis", result));
//    }
}
