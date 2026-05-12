package org.cloud.service.user;

import java.util.Optional;
import java.util.UUID;

import org.cloud.config.JwtProvider;
import org.cloud.dto.user.FindIdRequest;
import org.cloud.dto.user.FindIdResponse;
import org.cloud.dto.user.FindPasswordRequest;
import org.cloud.dto.user.FindPasswordResponse;
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
	private final EmailService emailService;

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

	    Optional<User> optionalUser =
	            userRepository.findByNicknameAndEmail(
	                    req.getNickname(),
	                    req.getEmail()
	            );

	    if (optionalUser.isEmpty()) {
	        return new FindIdResponse(
	                false,
	                "일치하는 회원이 없습니다.",
	                null
	        );
	    }

	    User user = optionalUser.get();

	    return new FindIdResponse(
	            true,
	            "가입된 이메일입니다.",
	            user.getEmail()
	    );
	}
	
	public FindPasswordResponse findPassword(
	        FindPasswordRequest req
	) {

	    Optional<User> optionalUser =
	            userRepository.findByNicknameAndEmail(
	                    req.getNickname(),
	                    req.getEmail()
	            );

	    if (optionalUser.isEmpty()) {
	        return new FindPasswordResponse(
	                false,
	                "일치하는 회원이 없습니다."
	        );
	    }

	    User user = optionalUser.get();

	    String resetToken =
	            UUID.randomUUID().toString();

	    user.setResetToken(resetToken);

	    userRepository.save(user);

	    String resetLink =
	            "http://localhost:5173/reset-password?token="
	            + resetToken;

	    emailService.sendPasswordResetMail(
	            user.getEmail(),
	            resetLink
	    );

	    return new FindPasswordResponse(
	            true,
	            "비밀번호 재설정 메일이 발송되었습니다."
	    );
	}
}
