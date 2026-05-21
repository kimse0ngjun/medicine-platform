//package org.cloud.service.ai;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class GeminiService {
//
//    @Value("${gemini.api.key}")
//    private String apiKey;
//
//    @Value("${gemini.api.url}")
//    private String apiUrl;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public String analyzeRecall(String productName, String recallReason, String dangerLevel) {
//
//        String prompt = String.format("""
//                당신은 의약품 안전 전문가입니다.
//                아래 의약품 회수 정보를 분석해주세요.
//                
//                제품명: %s
//                회수 사유: %s
//                위험도: %s
//                
//                아래 항목을 한국어로 간결하게 답해주세요:
//                1. 회수 사유 요약
//                2. 복용 시 주의사항
//                3. 대처 방법
//                """, productName, recallReason, dangerLevel);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("contents", List.of(
//                Map.of("parts", List.of(
//                        Map.of("text", prompt)
//                ))
//        ));
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
//
//        String url = apiUrl + "?key=" + apiKey;
//
//        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
//
//        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
//        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
//        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
//
//        return (String) parts.get(0).get("text");
//    }
//}
