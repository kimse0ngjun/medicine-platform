package org.cloud.controller.user;

import org.cloud.dto.user.UserResponse;
import org.cloud.dto.user.UserUpdateRequest;
import org.cloud.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getMe(Authentication authentication) {
        String email = authentication.getName();
        return userService.getMe(email);
    }

    @PutMapping("/me")
    public UserResponse updateMe(
            Authentication authentication,
            @RequestBody UserUpdateRequest req
    ) {
        String email = authentication.getName();
        return userService.updateMe(email, req);
    }

    @DeleteMapping("/me")
    public void deleteMe(Authentication authentication) {
        String email = authentication.getName();
        userService.deleteMe(email);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }
}
