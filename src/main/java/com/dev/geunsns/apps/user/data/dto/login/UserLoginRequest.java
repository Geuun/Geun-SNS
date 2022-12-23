package com.dev.geunsns.apps.user.data.dto.login;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

	private String userName;
	private String password;

	public UserEntity toEntity(String password) {
		return UserEntity.builder()
						 .userName(this.userName)
						 .password(password)
						 .build();
	}
}
