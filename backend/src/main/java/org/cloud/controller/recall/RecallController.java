package org.cloud.controller.recall;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.service.recall.RecallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/recalls")
@RequiredArgsConstructor
public class RecallController {

    private final RecallService recallService;
    
    @GetMapping("/search")
    public ResponseEntity<?> searchByProductName(
            @RequestParam("productName") String productName
    ) {
        return ResponseEntity.ok(
                recallService.searchByProductName(productName)
        );
    }

    @GetMapping("/lot")
    public ResponseEntity<RecallResultResponse> getByLotNumber(
            @RequestParam("lotNumber") String lotNumber
    ) {

        return ResponseEntity.ok(
                recallService.checkByLot(lotNumber)
        );
    }

    @PostMapping("/image")
    public ResponseEntity<?> searchByImage(
            @RequestParam("image") MultipartFile image
    ) {
        return ResponseEntity.ok(
                recallService.checkByImage(image)
        );
    }
}