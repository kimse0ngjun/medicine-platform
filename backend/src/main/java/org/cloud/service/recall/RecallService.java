package org.cloud.service.recall;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.RecallStatus;
import org.cloud.mapper.recall.RecallMapper;
import org.cloud.recall.engine.RecallRuleEngine;
import org.cloud.repository.recall.RecallRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecallService {
    private final RecallMapper recallMapper;
    private final RecallRuleEngine engine;
    private final OcrService ocrService;
    private final RecallRepository recallRepository; 

    public RecallResultResponse checkRecall(String lotNumber) {
        RecallBatch batch = recallMapper.findByLotNumber(lotNumber);
        if (batch == null) {
            RecallResultResponse result = new RecallResultResponse();
            result.setStatus(RecallStatus.SAFE);
            result.setRecallReason("조회된 이력이 없습니다.");
            return result;
        }
        return engine.execute(batch);
    }

    public RecallResultResponse checkByImage(MultipartFile file) {
        String ocrText = ocrService.extractText(file);
        String lotNumber = parseLotNumber(ocrText);

        Optional<RecallBatch> recallBatch = recallRepository
            .findByLotNumberWithMedicine(lotNumber);

        if (recallBatch.isPresent()) {
            return engine.execute(recallBatch.get()); 
        }

        RecallResultResponse response = new RecallResultResponse();
        response.setStatus(RecallStatus.SAFE);
        response.setRecallReason("조회된 이력이 없습니다.");
        return response;
    }

    private String parseLotNumber(String ocrText) {
        if (ocrText == null || ocrText.isBlank()) {
            throw new IllegalArgumentException("OCR 텍스트가 비어있습니다.");
        }
        Pattern pattern = Pattern.compile(
            "(?i)lot\\s*no\\.?\\s*[:\\-]?\\s*([A-Z0-9]{4,20})"
        );
        Matcher matcher = pattern.matcher(ocrText.replaceAll("\\s+", " ").trim());
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        Pattern fallback = Pattern.compile(
            "(?i)l[o0]t\\s*n[o0]\\.?\\s*[:\\-]?\\s*([A-Z0-9]{4,20})"
        );
        Matcher fallbackMatcher = fallback.matcher(ocrText);
        if (fallbackMatcher.find()) {
            return fallbackMatcher.group(1).trim();
        }
        throw new IllegalArgumentException(
            "LOT 번호를 찾을 수 없습니다. OCR 결과: [" + ocrText + "]"
        );
    }
}