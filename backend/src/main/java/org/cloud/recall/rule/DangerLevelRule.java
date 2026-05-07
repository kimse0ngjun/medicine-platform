package org.cloud.recall.rule;

import org.cloud.dto.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.DangerLevel;
import org.cloud.enums.RecallStatus;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order()
public class DangerLevelRule implements RecallRule {

    @Override
    public RecallResultResponse apply(RecallBatch batch) {

        RecallResultResponse result = new RecallResultResponse();

        result.setDangerLevel(batch.getDangerLevel());
        result.setExpirationDate(batch.getExpirationDate());

        DangerLevel level = batch.getDangerLevel();

        if (level == DangerLevel.HIGH) {
            result.setStatus(RecallStatus.RECALL);
            result.setMessage(batch.getRecallReason());
            return result;
        }

        if (level == DangerLevel.MEDIUM) {
            result.setStatus(RecallStatus.WARNING);
            result.setMessage(batch.getRecallReason());
            return result;
        }

        if (level == DangerLevel.LOW) {
            result.setStatus(RecallStatus.SAFE);
            result.setMessage(batch.getRecallReason());
            return result;
        }

        return null;
    }
}