package org.cloud.recall.rule;

import java.time.LocalDate;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.recall.RecallStatus;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class ExpirationRule implements RecallRule {

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public RecallResultResponse evaluate(RecallBatch batch) {

        RecallResultResponse res = base(batch);

        if (batch.getExpirationDate() != null &&
            batch.getExpirationDate().isBefore(LocalDate.now())) {

            res.setStatus(RecallStatus.RECALL);
            res.setRecallReason("유통기한 초과");
            return res;
        }

        res.setStatus(RecallStatus.SAFE);
        res.setRecallReason("유통기한 정상");
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