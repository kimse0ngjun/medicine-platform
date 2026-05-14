package org.cloud.service.recall;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecallImageService {

    private final OcrService ocrService;

    public String extractLotNumber(MultipartFile file) {

        String text = ocrService.extractText(file);

        if (text == null || text.isBlank()) {
            return null;
        }

        return parseLotNumber(text);
    }

    private String parseLotNumber(String ocrText) {

        String normalized = ocrText.replaceAll("\\s+", " ").trim();

        Pattern pattern = Pattern.compile(
                "(?i)lot\\s*(no)?\\.?\\s*[:\\-]?\\s*([A-Z0-9-]{4,20})"
        );

        Matcher matcher = pattern.matcher(normalized);

        if (matcher.find()) {
            return matcher.group(2);
        }

        Pattern fallback = Pattern.compile(
                "\\b(?=.*[A-Z])(?=.*\\d)[A-Z0-9-]{5,20}\\b"
        );

        Matcher fallbackMatcher = fallback.matcher(normalized.toUpperCase());

        return fallbackMatcher.find() ? fallbackMatcher.group() : null;
    }
}