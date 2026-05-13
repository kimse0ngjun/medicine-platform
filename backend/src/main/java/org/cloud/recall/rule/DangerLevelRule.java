package org.cloud.recall.rule;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.DangerLevel;
import org.cloud.enums.RecallStatus;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order
public class DangerLevelRule implements RecallRule {

    @Override
    public RecallResultResponse apply(RecallBatch batch) {

        DangerLevel level = batch.getDangerLevel();

        RecallResultResponse result = new RecallResultResponse();

        result.setDangerLevel(level);
        result.setExpirationDate(batch.getExpirationDate());
        result.setRecallReason(batch.getRecallReason());
        result.setProductName(batch.getMedicine().getProductName());

        if (level == DangerLevel.HIGH) {
            result.setStatus(RecallStatus.RECALL);
            return result;
        }

        if (level == DangerLevel.MEDIUM) {
            result.setStatus(RecallStatus.WARNING);
            return result;
        }

        if (level == DangerLevel.LOW) {
            result.setStatus(RecallStatus.SAFE);
            return result;
        }

        return null;
    }
}