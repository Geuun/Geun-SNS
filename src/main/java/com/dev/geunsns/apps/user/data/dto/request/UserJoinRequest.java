package com.dev.geunsns.apps.user.data.dto.request;

import com.dev.geunsns.apps.user.data.model.UserRole;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinRequest {

	private String userName;
	private String password;

	@Builder
	public UserJoinRequest(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public UserEntity toEntity(String userName, String password, UserRole role) {
		return UserEntity
			.builder()
			.userName(userName)
			.password(password)
			.role(role)
			.build();
	}
}
