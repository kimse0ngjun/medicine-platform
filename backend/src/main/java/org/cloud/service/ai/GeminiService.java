package org.cloud.service.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

	@Value("${gemini.api-key}")
	private String apiKey;
	
	@Value("${gemini.api-url}")
	private String apiUrl;

    private final ObjectMapper objectMapper;

    public String generate(String prompt) {

        RestTemplate restTemplate = new RestTemplate();

        String url = apiUrl + "?key=" + apiKey;
        
        Map<String, Object> body = Map.of(
                "contents",
                List.of(
                        Map.of(
                                "parts",
                                List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url,
                        body,
                        String.class
                );

        try {

            JsonNode root =
                    objectMapper.readTree(response.getBody());

            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
