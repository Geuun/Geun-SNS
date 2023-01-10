package com.dev.geunsns.apps.user.data.dto.response;

import com.dev.geunsns.apps.user.data.model.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoleChangeResponse {

    private Long id;
    private String userName;
    private UserRole userRole;

    @Builder
    public UserRoleChangeResponse(Long id, String userName, UserRole userRole) {
        this.id = id;
        this.userName = userName;
        this.userRole = userRole;
    }
}
