package org.cloud.dto;

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
