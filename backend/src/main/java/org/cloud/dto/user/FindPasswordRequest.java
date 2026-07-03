package org.cloud.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequest {

	private String nickname;
	private String email;
}

