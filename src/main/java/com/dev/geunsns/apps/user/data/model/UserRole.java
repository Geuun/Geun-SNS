package com.dev.geunsns.apps.user.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    /**
     * Spring Security Role Naming Rule : ROLE_AUTHORITY
     */

    ROLE_SUPER_ADMIN("Super_Admin"),
    ROLE_ADMIN("Admin"),
    ROLE_USER("User"),
    ROLE_ANONYMOUS("Anonymous"),
    ;

    private final String role;
}
