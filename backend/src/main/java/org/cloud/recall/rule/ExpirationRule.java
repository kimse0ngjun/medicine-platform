package org.cloud.recall.rule;

import java.time.LocalDate;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.RecallStatus;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ExpirationRule implements RecallRule {

    @Override
    public RecallResultResponse apply(RecallBatch batch) {

        if (batch.getExpirationDate() != null &&
            batch.getExpirationDate().isBefore(LocalDate.now())) {

            RecallResultResponse result = new RecallResultResponse();

            result.setStatus(RecallStatus.RECALL);

            result.setDangerLevel(batch.getDangerLevel());
            result.setExpirationDate(batch.getExpirationDate());
            result.setProductName(batch.getMedicine().getProductName());
            
            result.setRecallReason("유통기한 초과"); 

            return result;
        }

        return null;
    }
}
