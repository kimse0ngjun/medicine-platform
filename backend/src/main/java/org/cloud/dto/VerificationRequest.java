package org.cloud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerificationRequest {
	private String inputText;
	private String lotNumber;
}
