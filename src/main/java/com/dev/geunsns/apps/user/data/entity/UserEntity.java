package com.dev.geunsns.apps.user.data.entity;

import com.dev.geunsns.apps.model.UserRole;
import com.dev.geunsns.global.data.jpaauditing.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class UserEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String userName;
	private String password;

	@Enumerated(EnumType.STRING) //Enum 값을 String으로 저장
	private UserRole role;

	@Builder
	public UserEntity(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Builder
	public UserEntity(Long id, String userName, String password) {
		this.id = id;
		this.userName = userName;
		this.password = password;
	}
}
