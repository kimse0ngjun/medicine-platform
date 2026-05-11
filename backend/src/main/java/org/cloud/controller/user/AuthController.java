package org.cloud.controller.user;

import org.cloud.dto.user.FindIdRequest;
import org.cloud.dto.user.FindIdResponse;
import org.cloud.dto.user.LoginRequest;
import org.cloud.dto.user.LoginResponse;
import org.cloud.dto.user.SignupRequest;
import org.cloud.dto.user.SignupResponse;
import org.cloud.service.user.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/signup")
	public SignupResponse signup(@RequestBody SignupRequest req) {
		return authService.signup(req);
	}
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest req) {
		return authService.login(req);
	}
	
	@PostMapping("/find-id")
	public FindIdResponse findId(@RequestBody FindIdRequest req) {
	    return authService.findId(req);
	}
	
}
