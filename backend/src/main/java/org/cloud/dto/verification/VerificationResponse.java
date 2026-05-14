package org.cloud.dto.verification;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.enums.VerificationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerificationResponse {
	private Long id;
	private VerificationStatus status;
	private String message;
	private RecallResultResponse recallResult;
}
