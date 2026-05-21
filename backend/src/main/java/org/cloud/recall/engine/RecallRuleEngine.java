package org.cloud.recall.engine;

import java.util.Comparator;
import java.util.List;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.RecallBatch;
import org.cloud.enums.recall.RecallStatus;
import org.cloud.recall.rule.RecallRule;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecallRuleEngine {

    private final List<RecallRule> rules;

    public RecallResultResponse execute(RecallBatch batch) {

        return rules.stream()
                .sorted(Comparator.comparingInt(RecallRule::priority))
                .map(rule -> rule.evaluate(batch))
                .max(Comparator.comparing(r -> r.getStatus().ordinal()))
                .orElseGet(() -> fallback(batch));
    }

    private RecallResultResponse fallback(RecallBatch batch) {

        RecallResultResponse res = new RecallResultResponse();
        res.setStatus(RecallStatus.SAFE);
        res.setRecallReason("규칙 없음");
        res.setProductName(batch.getMedicine().getProductName());
        res.setLotNumber(batch.getLotNumber());
        res.setRecallCount(0L);

        return res;
    }
}