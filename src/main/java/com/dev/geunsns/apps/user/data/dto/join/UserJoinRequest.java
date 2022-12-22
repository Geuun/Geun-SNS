package com.dev.geunsns.apps.user.data.dto.join;

import com.dev.geunsns.apps.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    private String userName;
    private String password;

    public User toEntity(String userName, String password) {
        return User.builder()
                .userName(userName)
                .password(password)
                .build();
    }
}
