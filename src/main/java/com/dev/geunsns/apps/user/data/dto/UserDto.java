package com.dev.geunsns.apps.user.data.dto;

import com.dev.geunsns.apps.user.data.model.UserRole;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

	private Long id;
	private String userName;
	private String password;

	// enum
	private UserRole role;

	@Builder
	public UserDto(Long id, String userName, String password, UserRole role) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	// Entity -> Dto
	public static UserDto toDto(UserEntity userEntity) {
		return new UserDto(
			userEntity.getId(),
			userEntity.getUserName(),
			userEntity.getPassword(),
			userEntity.getRole()
		);
	}
}