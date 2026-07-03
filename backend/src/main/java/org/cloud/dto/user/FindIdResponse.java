package org.cloud.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindIdResponse {
	
	private boolean success;
	private String message;
	private String email;
}