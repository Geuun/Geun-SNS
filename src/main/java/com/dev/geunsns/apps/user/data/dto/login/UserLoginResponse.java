package com.dev.geunsns.apps.user.data.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
	private String jwtToken;
}
