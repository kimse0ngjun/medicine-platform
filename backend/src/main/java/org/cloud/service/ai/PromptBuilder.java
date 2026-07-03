package org.cloud.service.ai;

import org.cloud.dto.recall.RecallResultResponse;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildSummaryPrompt(
            RecallResultResponse result
    ) {

        return """
            당신은 의약품 회수 정보 분석 AI입니다.

            제품명: %s
            위험도: %s
            회수 건수: %d건
            회수 사유: %s

            아래 형식으로 작성하세요.

            전체 회수 현황:
            ...

            주요 회수 사유:
            ...

            사용자 주의사항:
            ...

            200자 이내
            """
            .formatted(
                result.getProductName(),
                result.getDangerLevel(),
                result.getRecallCount(),
                result.getRecallReason()
            );
    }
}
