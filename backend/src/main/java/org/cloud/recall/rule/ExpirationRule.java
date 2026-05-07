package org.cloud.recall.rule;

import java.time.LocalDate;

import org.cloud.dto.RecallResultResponse;
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
			result.setMessage("유통기한 초과");
			
			result.setDangerLevel(batch.getDangerLevel());
			result.setExpirationDate(batch.getExpirationDate());
			
			return result;
		}
		return null; // 다음 Rule 검사
	}
}
