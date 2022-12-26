package com.dev.geunsns.apps.user.data.dto.join;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinResponse {

	private Long userId;
	private String userName;

	@Builder
	public UserJoinResponse(Long userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
}
