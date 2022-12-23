package com.dev.geunsns.apps.user.data.dto;

import com.dev.geunsns.apps.user.data.Role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
	private String userName;
	private String password;
	private UserRole role;
}