package org.cloud.service.recall;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.ITesseract;

@Service
public class OcrService {
    private final ITesseract tesseract;

    public OcrService() {
        Tesseract instance = new Tesseract();
        instance.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        instance.setLanguage("eng");           
        instance.setOcrEngineMode(1);        
        instance.setPageSegMode(6);            
        instance.setTessVariable("tessedit_char_whitelist",
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789./: \n");
        this.tesseract = instance;
    }

    public String extractText(MultipartFile file) {
        try {
            File convFile = convert(file);
            String result = tesseract.doOCR(convFile);
            convFile.delete();
            return result;
        } catch (Exception e) {
            throw new RuntimeException("OCR 실패: " + e.getMessage(), e);
        }
    }

    private File convert(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
            ? originalFilename.substring(originalFilename.lastIndexOf("."))
            : ".tmp";
        File convFile = File.createTempFile("ocr-", extension);
        file.transferTo(convFile);
        return convFile;
    }
}