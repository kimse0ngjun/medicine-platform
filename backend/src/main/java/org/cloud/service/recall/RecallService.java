package org.cloud.service.recall;

import java.util.List;

import org.cloud.dto.recall.RecallDetailResponse;
import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.dto.recall.RecallSearchResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.RecallStatus;
import org.cloud.recall.engine.RecallRuleEngine;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecallService {

    private final RecallQueryService queryService;

    private final RecallImageService imageService;

    private final RecallRuleEngine engine;

    public RecallResultResponse checkByLot(String lotNumber) {

        Page<RecallBatch> page = queryService.findByLot(lotNumber);

        if (page == null || page.getContent().isEmpty()) {
            throw new IllegalArgumentException("조회된 RecallBatch가 없습니다.");
        }

        RecallBatch latestBatch = page.getContent().get(0);

        return engine.execute(latestBatch);
    }

    public RecallResultResponse checkByImage(MultipartFile file) {

        String lotNumber = imageService.extractLotNumber(file);

        if (lotNumber == null) {
            return safeResponse();
        }

        Page<RecallBatch> page = queryService.findByLot(lotNumber);

        if (page == null || page.getContent().isEmpty()) {
            return safeResponse();
        }

        RecallBatch latestBatch = page.getContent().get(0);

        return engine.execute(latestBatch);
    }

    private RecallResultResponse safeResponse() {

        RecallResultResponse response =
                new RecallResultResponse();

        response.setStatus(RecallStatus.SAFE);

        response.setRecallReason(
                "조회된 이력이 없습니다."
        );

        return response;
    }

    public List<RecallSearchResponse>
    searchByProductName(String productName) {

        return queryService.findByProductName(
                productName
        );
    }
    
    public List<RecallDetailResponse> getRecallDetail(String productName) {
        return queryService.findRecallDetail(productName);
    }
}