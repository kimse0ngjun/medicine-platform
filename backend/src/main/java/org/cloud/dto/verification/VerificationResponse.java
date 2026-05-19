package org.cloud.dto.verification;

import java.time.LocalDateTime;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.entity.Verification;
import org.cloud.enums.VerificationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerificationResponse { // 1건
	private Long id;
	private VerificationStatus status;
	
    private String inputText;
    private String lotNumber;
    private String result;
	
	private String message;
	
	private LocalDateTime createdAt;
	
	private RecallResultResponse recallResult;
	
	public static VerificationResponse from(Verification entity) {

	    VerificationResponse res = new VerificationResponse();

	    res.setId(entity.getId());
	    res.setStatus(entity.getStatus());
	    
        res.setInputText(entity.getInputText());
        res.setLotNumber(entity.getLotNumber());
        res.setResult(entity.getResult());
        
        res.setCreatedAt(entity.getCreatedAt());

	    res.setMessage(
	            entity.getErrorMessage() != null
	                    ? entity.getErrorMessage()
	                    : entity.getResult()
	    );

	    return res;
	}
}
