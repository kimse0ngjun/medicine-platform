package org.cloud.dto.verification;

import org.cloud.dto.recall.RecallResultResponse;
import org.cloud.enums.RecallStatus;
import org.cloud.enums.VerificationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerificationResponse {
	private Long Id;
	private VerificationStatus status;
	private RecallResultResponse recallResult;
}
