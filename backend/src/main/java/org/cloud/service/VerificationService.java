package org.cloud.service;
import org.cloud.MedicineProjectApplication;
import org.cloud.dto.RecallResultResponse;
import org.cloud.dto.VerificationRequest;
import org.cloud.dto.VerificationResponse;
import org.cloud.entity.Verification;
import org.cloud.enums.VerificationStatus;
import org.cloud.repository.VerificationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository repository;
    private final RecallService recallService;

    public Long start(VerificationRequest req) {

        Verification v = new Verification();
        v.setStatus(VerificationStatus.PENDING);
        v.setInputText(req.getInputText());
        v.setLotNumber(req.getLotNumber());

        repository.save(v);

        processAsync(v.getId(), req);

        return v.getId();
    }

    @Async
    public void processAsync(Long id, VerificationRequest req) {

        Verification v = repository.findById(id).orElseThrow();

        v.setStatus(VerificationStatus.PROCESSING);
        repository.save(v);

        try {
        	RecallResultResponse result = recallService.checkRecall(req.getLotNumber());


        	v.setStatus(VerificationStatus.SUCCESS);
            v.setResult(result.getMessage());

            repository.save(v);

        } catch (Exception e) {
            v.setStatus(VerificationStatus.FAIL);
            v.setResult(e.getMessage());
            repository.save(v);
        }
    }
    
    public VerificationResponse get(Long id) {
    	
    	Verification v = repository.findById(id).orElseThrow();
    	
    	VerificationResponse res = new VerificationResponse();
    	res.setId(v.getId());
    	res.setStatus(v.getStatus());
    	
    	RecallResultResponse result = recallService.checkRecall(v.getLotNumber());
    	
    	res.setRecallResult(result);
    	
    	return res;
    }
}