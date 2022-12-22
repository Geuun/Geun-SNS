package com.dev.geunsns.apps.user.data.Role;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    ;

    private String role;
}
