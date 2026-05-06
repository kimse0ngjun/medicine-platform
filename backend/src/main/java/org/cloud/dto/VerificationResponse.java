package org.cloud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerificationResponse {
	private Long Id;
	private String status;
	private String result;
}
