package org.cloud.service;
import org.cloud.MedicineProjectApplication;
import org.cloud.dto.VerificationRequest;
import org.cloud.dto.VerificationResponse;
import org.cloud.entity.Verification;
import org.cloud.repository.VerificationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import Mapper.RecallMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository repository;
    private final RecallMapper recallMapper;

    public Long start(VerificationRequest req) {
    	Verification v = new Verification();
    	v.setStatus("PENDING");
    	v.setInputText(req.getInputText());
    	v.setLotNumber(req.getLotNumber());
    	
    	repository.save(v);
    	
    	processAsync(v.getId(), req);
    	
    	return v.getId();
    }
    
    @Async
    public void processAsync(Long id, VerificationRequest req) {
    	
    	Verification v = repository.findById(id).orElseThrow();
    	
    	v.setStatus("PROCESSING");
        repository.save(v);
        
        try {
        	String reason = recallMapper.findRecallResponse(req.getLotNumber());
        	
        	if (reason != null) {
        		v.setStatus("SUCCESS");
        		v.setResult("회수 대상: " + reason);
        	} else {
        		v.setStatus("SUCCESS");
        		v.setResult("정상 의약품");
        	}
        	
        	repository.save(v);
        } catch (Exception e) {
        	v.setStatus("FAIL");
        	v.setResult(e.getMessage());
        	repository.save(v);
        }
    }
    
    public VerificationResponse get(Long id) {
    	Verification v = repository.findById(id).orElseThrow();
    	
    	VerificationResponse res = new VerificationResponse();
    	res.setId(v.getId());
    	res.setStatus(v.getStatus());
    	res.setResult(v.getResult());
    	
    	return res;
    }
}
