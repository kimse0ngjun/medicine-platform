package org.cloud.recall.rule;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.recall.DangerLevel;
import org.cloud.enums.recall.RecallStatus;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class DangerLevelRule implements RecallRule {

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public RecallResultResponse evaluate(RecallBatch batch) {

        RecallResultResponse res = base(batch);

        switch (batch.getDangerLevel()) {
            case HIGH -> {
                res.setStatus(RecallStatus.RECALL);
                res.setRecallReason(batch.getRecallReason());
            }
            case MEDIUM -> {
                res.setStatus(RecallStatus.WARNING);
                res.setRecallReason(batch.getRecallReason());
            }
            case LOW -> {
                res.setStatus(RecallStatus.SAFE);
                res.setRecallReason(batch.getRecallReason());
            }
            default -> {
                res.setStatus(RecallStatus.SAFE);
                res.setRecallReason("위험도 없음");
            }
        }

        return res;
    }

    private RecallResultResponse base(RecallBatch batch) {
        RecallResultResponse r = new RecallResultResponse();
        r.setProductName(batch.getMedicine().getProductName());
        r.setLotNumber(batch.getLotNumber());
        r.setDangerLevel(batch.getDangerLevel());
        r.setExpirationDate(batch.getExpirationDate());
        return r;
    }
}