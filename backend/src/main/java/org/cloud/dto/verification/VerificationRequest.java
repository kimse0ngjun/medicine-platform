package org.cloud.dto.verification;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerificationRequest {
	private String inputText;
	private String lotNumber;
}
