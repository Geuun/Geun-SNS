package com.dev.geunsns.apps.user.data.dto.join;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {

	private String userName;
	private String password;

	public UserEntity toEntity(String userName, String password) {
		return UserEntity
			.builder()
			.userName(userName)
			.password(password)
			.build();
	}
}
