package org.cloud.service.ai;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.service.recall.RecallService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {

    private final RecallService recallService;
    private final PromptBuilder promptBuilder;
    private final GeminiService geminiService;

    public String generateSummary(
            String productName
    ) {

        RecallResultResponse result =
                recallService.checkByProduct(productName);

        String prompt =
                promptBuilder.buildSummaryPrompt(result);

        return geminiService.generate(prompt);
    }
}
