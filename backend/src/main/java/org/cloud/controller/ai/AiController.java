package org.cloud.controller.ai;

import java.util.Map;

import org.cloud.dto.ai.GeminiRequest;
import org.cloud.dto.ai.GeminiResponse;
import org.cloud.service.ai.AiService;
import org.cloud.service.ai.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiController {

    private final GeminiService geminiService;
    private final AiService aiService;

    @PostMapping("/chat")
    public GeminiResponse chat(
            @RequestBody GeminiRequest request
    ) {

        String answer =
                geminiService.generate(request.getPrompt());

        return new GeminiResponse(answer);
    }
    
    @GetMapping
    public String test() {
        return geminiService.generate("안녕하세요");
    }
    
    @PostMapping("/summary")
    public GeminiResponse summary(
            @RequestParam("productName") String productName
    ) {
        return new GeminiResponse(
                aiService.generateSummary(productName)
        );
    }
    
}