//package org.cloud.controller.ai;
//
//import java.util.Map;
//
//import org.cloud.service.ai.GeminiService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/api/v1/ai")
//@RequiredArgsConstructor
//public class AiController {
//
//    private final GeminiService geminiService;
//
//    @GetMapping("/test")
//    public ResponseEntity<Map<String, String>> test() {
//        String result = geminiService.analyzeRecall(
//                "타이레놀",
//                "허가사항과 다르게 기재",
//                "HIGH"
//        );
//        return ResponseEntity.ok(Map.of("result", result));
//    }
//}
