package com.dev.geunsns.apps.user.data.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoleChangeRequest {
    private String roleToBeChanged;

    @Builder
    public UserRoleChangeRequest(String roleToBeChanged) {
        this.roleToBeChanged = roleToBeChanged;
    }
}
