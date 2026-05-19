package org.cloud.service.recall;

import java.util.List;

import org.cloud.dto.recall.RecallDetailResponse;
import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.dto.recall.RecallSearchResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.RecallStatus;
import org.cloud.recall.engine.RecallRuleEngine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Page<RecallBatch> page =
                queryService.findByLot(lotNumber, PageRequest.of(0, 1));
        if (page == null || page.isEmpty()) {
            return safeResponse();
        }
        RecallBatch latest = page.getContent().get(0);
        RecallResultResponse result = engine.execute(latest);
        result.setRecallCount(page.getTotalElements()); // ← 여기서 세팅
        return result;
    }

    public RecallResultResponse checkByImage(MultipartFile file) {

        String lotNumber = imageService.extractLotNumber(file);

        if (lotNumber == null || lotNumber.isBlank()) {
            return safeResponse();
        }

        Page<RecallBatch> page =
                queryService.findByLot(lotNumber, PageRequest.of(0, 1));

        if (page.isEmpty()) {
            return safeResponse();
        }

        RecallBatch latest = page.getContent().get(0);
        return engine.execute(latest);
    }

    public List<RecallSearchResponse> searchByProductName(String keyword) {
        return queryService.findByProductName(keyword);
    }

    public List<RecallDetailResponse> getRecallDetails(String keyword) {
        return queryService.findRecallDetailByProductName(keyword);
    }
    
    public List<RecallDetailResponse> getRecallDetailsByLot(String lotNumber) {
        return queryService.findByLot(lotNumber, Pageable.unpaged())
            .getContent()
            .stream()
            .map(rb -> {
                RecallDetailResponse dto = new RecallDetailResponse();
                dto.setLotNumber(rb.getLotNumber());
                dto.setProductName(rb.getMedicine().getProductName());
                dto.setRecallReason(rb.getRecallReason());
                dto.setDangerLevel(rb.getDangerLevel());
                dto.setRecallDate(rb.getRecallDate());
                dto.setExpirationDate(rb.getExpirationDate());
                return dto;
            })
            .toList();
    }

    public Object search(String type, String keyword) {

        switch (type) {

            case "PRODUCT_NAME":
                return queryService.findByProductName(keyword);

            case "LOT_NUMBER":
                return queryService.findByLot(keyword, PageRequest.of(0, 1));

            default:
                throw new IllegalArgumentException("invalid type");
        }
    }

    private RecallResultResponse safeResponse() {

        RecallResultResponse response = new RecallResultResponse();
        response.setStatus(RecallStatus.SAFE);
        response.setRecallReason("조회된 이력이 없습니다.");
        response.setRecallCount(0L);

        return response;
    }
}