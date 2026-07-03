package org.cloud.service.verification;

import java.util.List;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.dto.verification.LotVerificationRequest;
import org.cloud.dto.verification.ProductVerificationRequest;
import org.cloud.dto.verification.VerificationDetailResponse;
import org.cloud.dto.verification.VerificationResponse;
import org.cloud.entity.Verification;
import org.cloud.enums.verification.SearchType;
import org.cloud.enums.verification.VerificationStatus;
import org.cloud.repository.verficiation.VerificationRepository;
import org.cloud.service.recall.RecallImageService;
import org.cloud.service.recall.RecallQueryService;
import org.cloud.service.recall.RecallService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final RecallQueryService recallQueryService;

    private final VerificationRepository repository;
    private final RecallService recallService;
    private final RecallImageService imageService;

    public VerificationResponse verifyProduct(ProductVerificationRequest req) {

    	
        Verification v = new Verification();
        v.setType(SearchType.PRODUCT);
        v.setInputText(req.getProductName()); 
        v.setVerificationStatus(VerificationStatus.PENDING);
        repository.save(v);

        try {
            RecallResultResponse result = recallService.checkByProduct(req.getProductName());

            v.setInputText(result.getProductName()); // ← 실제 제품명으로 덮어쓰기
            v.setVerificationStatus(VerificationStatus.SUCCESS);
            v.setRecallStatus(result.getStatus());

            String resultText = buildResultText(result);
            v.setResult(resultText);
        } catch (Exception e) {
            v.setVerificationStatus(VerificationStatus.FAIL);
            v.setResult("검증 실패: " + e.getMessage());
        } finally {
            repository.save(v);
        }

        return VerificationResponse.from(v);
    }
    
    public VerificationResponse verifyLot(LotVerificationRequest req) {

        Verification v = new Verification();
        v.setType(SearchType.LOT);
        v.setInputText(req.getLotNumber());
        v.setLotNumber(req.getLotNumber());
        v.setVerificationStatus(VerificationStatus.PENDING);
        repository.save(v);

        try {
            RecallResultResponse result = recallService.checkByLot(req.getLotNumber());
            v.setVerificationStatus(VerificationStatus.SUCCESS);
            v.setRecallStatus(result.getStatus());
            v.setResult(result.getRecallReason());
            v.setResult(buildResultText(result));
        } catch (Exception e) {
            v.setVerificationStatus(VerificationStatus.FAIL);
            v.setResult("검증 실패: " + e.getMessage());
        } finally {
            repository.save(v);
        }

        return VerificationResponse.from(v);
    }

    public VerificationResponse verifyImage(MultipartFile file) {

        Verification v = new Verification();
        v.setType(SearchType.IMAGE);
        v.setInputText("IMAGE");
        v.setVerificationStatus(VerificationStatus.PENDING);
        repository.save(v);

        try {
            String lot = imageService.extractLotNumber(file);
            RecallResultResponse result = recallService.checkByLot(lot);

            v.setLotNumber(lot);
            v.setVerificationStatus(VerificationStatus.SUCCESS);
            v.setRecallStatus(result.getStatus());
            v.setResult(result.getRecallReason());
            v.setResult(buildResultText(result));
        } catch (Exception e) {
            v.setVerificationStatus(VerificationStatus.FAIL);
            v.setResult("검증 실패: " + e.getMessage());
        } finally {
            repository.save(v);
        }

        return VerificationResponse.from(v);
    }

    public VerificationResponse get(Long id) {

        Verification v = repository.findById(id)
                .orElseThrow();

        VerificationResponse res =
                new VerificationResponse();

        res.setId(v.getId());
        res.setStatus(v.getVerificationStatus());

        switch (v.getVerificationStatus()) {

        	case PENDING -> 
        			res.setMessage(
        					"조회 중입니다.")
        			;
            case SUCCESS ->
                    res.setMessage(
                            "리콜 조회가 완료되었습니다."
                    );

            case FAIL ->
                    res.setMessage(
                            "검증 처리에 실패했습니다."
                    );
        }

        if (v.getVerificationStatus() == VerificationStatus.SUCCESS) {

            RecallResultResponse result =
                    recallService.checkByLot(
                            v.getLotNumber()
                    );

            res.setRecallResult(result);
        }

        return res;
    }
    
    
    public List<VerificationResponse> list() {
        return repository.findAll()
                .stream()
                .map(VerificationResponse::from)
                .toList();
    }
    
    public VerificationDetailResponse get1(Long id) {
    	return repository.findById(id)
    			.map(VerificationDetailResponse::from)
    			.orElseThrow();
    }
    
    @Transactional
    public void delete(Long verificationId) {
    	repository.deleteById(verificationId);
    }
    
    private String buildResultText(RecallResultResponse result) {
        StringBuilder sb = new StringBuilder();

        if (result.getRecallReason() != null) {
            sb.append(result.getRecallReason());
        }

        return sb.length() > 0 ? sb.toString() : "정상";
    }
}