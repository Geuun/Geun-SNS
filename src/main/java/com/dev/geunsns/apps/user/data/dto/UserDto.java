package com.dev.geunsns.apps.user.data.dto;

import com.dev.geunsns.apps.model.UserRole;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
	private String userName;
	private String password;

	// enum
	private UserRole role;

	// Entity -> Dto
	@Builder
	public static UserDto fromEntity(UserEntity userEntity) {
		return new UserDto(
			userEntity.getId(),
			userEntity.getUserName(),
			userEntity.getPassword(),
			userEntity.getRole()
		);
	}
}