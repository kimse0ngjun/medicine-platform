package org.cloud.service.user;

import org.cloud.dto.user.UserResponse;
import org.cloud.dto.user.UserUpdateRequest;
import org.cloud.entity.User;
import org.cloud.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
    public UserResponse getMe(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return UserResponse.from(user);
    }

    public UserResponse updateMe(String email, UserUpdateRequest req) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        user.setNickname(req.getNickname());

        return UserResponse.from(userRepository.save(user));
    }

    public void deleteMe(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        user.setDeleted(true);
        userRepository.save(user);
    }

    public UserResponse getById(Long id) {
        return userRepository.findById(id)
                .map(UserResponse::from)
                .orElseThrow();
    }
}
