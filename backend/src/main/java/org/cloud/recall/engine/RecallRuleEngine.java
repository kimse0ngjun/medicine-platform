package org.cloud.recall.engine;

import java.util.List;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.RecallStatus;
import org.cloud.recall.rule.RecallRule;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecallRuleEngine {

	private final List<RecallRule> rules;

	public RecallResultResponse execute(RecallBatch batch) {
		for (RecallRule rule : rules) {
			RecallResultResponse result = rule.apply(batch);
			
			if (result != null) {
				return result;
			}
			
		}
		
		RecallResultResponse fallback = new RecallResultResponse();
		
		fallback.setStatus(RecallStatus.SAFE);
		fallback.setMessage("정상 의약품");
		
		return fallback;
	}
}
