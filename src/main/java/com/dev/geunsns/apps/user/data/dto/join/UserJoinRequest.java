package com.dev.geunsns.apps.user.data.dto.join;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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

	public UserEntity toEntity(String userName, String password) {
		return UserEntity
			.builder()
			.userName(userName)
			.password(password)
			.build();
	}
}
