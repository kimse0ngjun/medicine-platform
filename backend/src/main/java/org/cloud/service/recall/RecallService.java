package org.cloud.service.recall;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.RecallStatus;
import org.cloud.mapper.recall.RecallMapper;
import org.cloud.recall.engine.RecallRuleEngine;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecallService {

    private final RecallMapper recallMapper;
    private final RecallRuleEngine engine;

    public RecallResultResponse checkRecall(String lotNumber) {

        RecallBatch batch = recallMapper.findByLotNumber(lotNumber);

        if (batch == null) {

            RecallResultResponse result = new RecallResultResponse();

            result.setStatus(RecallStatus.SAFE);
            result.setMessage("정상 의약품");

            return result;
        }

        return engine.execute(batch);
    }
}