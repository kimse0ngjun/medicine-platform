package org.cloud.controller.verification;

import org.cloud.dto.verification.VerificationRequest;
import org.cloud.dto.verification.VerificationResponse;
import org.cloud.service.verification.VerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/verifications")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping
    public ResponseEntity<VerificationResponse> start(
            @RequestBody VerificationRequest req
    ) {

        Long id = verificationService.start(req);

        VerificationResponse response =
                new VerificationResponse();

        response.setId(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{verificationId}")
    public ResponseEntity<VerificationResponse> get(
            @PathVariable Long verificationId
    ) {

        return ResponseEntity.ok(
                verificationService.get(verificationId)
        );
    }
}
