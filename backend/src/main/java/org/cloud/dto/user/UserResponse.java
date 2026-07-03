package org.cloud.dto.user;

import org.cloud.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;

    public static UserResponse from(User user) {
        UserResponse res = new UserResponse();
        res.setId(user.getUserId());
        res.setEmail(user.getEmail());
        res.setNickname(user.getNickname());
        return res;
    }
}
