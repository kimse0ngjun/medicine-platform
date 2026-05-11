package org.cloud.service.user;

import org.cloud.config.JwtProvider;
import org.cloud.dto.user.FindIdRequest;
import org.cloud.dto.user.FindIdResponse;
import org.cloud.dto.user.LoginRequest;
import org.cloud.dto.user.LoginResponse;
import org.cloud.dto.user.SignupRequest;
import org.cloud.dto.user.SignupResponse;
import org.cloud.entity.User;
import org.cloud.repository.user.UserRepository;
import org.cloud.service.recall.RecallService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	public SignupResponse signup(SignupRequest req) {
		
		boolean exists = userRepository.existsByEmail(req.getEmail());
		
		if (exists) {
			throw new RuntimeException(
					"이미 존재하는 이메일입니다.");
		}
		
		User user = new User();
		
		user.setEmail(req.getEmail());
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		
		user.setNickname(req.getNickname());
		
		userRepository.save(user);
		
		return new SignupResponse(
				"회원가입 성공",
				user.getNickname());
	}
	
	public LoginResponse login(LoginRequest req) {
		
	    User user = userRepository.findByEmail(req.getEmail())
	            .orElseThrow(() ->
	                    new RuntimeException("사용자 없음"));
	    
	    boolean matches = 
	    		passwordEncoder.matches(
	    				req.getPassword(),
	    				user.getPassword()
	    				);
	    
	    if (!matches) {
	    	throw new RuntimeException("비밀번호 불일치");
	    }
	    
	    String token = jwtProvider.createToken(user.getEmail());
	    
	    return new LoginResponse(
	    		"로그인 성공",
	    		token,
	    		user.getNickname()
	    		);
	}
	
	public FindIdResponse findId(FindIdRequest req) {

	    User user = userRepository.findByNameAndEmail(
	            req.getNickname(),
	            req.getEmail()
	    ).orElseThrow(() -> new RuntimeException("유저 없음"));

	    return new FindIdResponse(user.getUserId());
	}
}
