package org.cloud.recall.rule;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.RecallBatch;

public interface RecallRule {

    RecallResultResponse apply(RecallBatch batch);
    
}
