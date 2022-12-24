package com.dev.geunsns.apps.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public enum UserRole {
	/**
	 * Spring Security Role Naming Rule : ROLE_AUTHORITY
	 */

	ROLE_SUPERADMIN("Super_Admin"),
	ROLE_ADMIN("Admin"),
	ROLE_USER("User"),
	ROLE_ANONYMOUS("Anonymous"),
	;

	private final String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
