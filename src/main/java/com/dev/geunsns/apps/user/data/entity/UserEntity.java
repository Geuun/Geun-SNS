package com.dev.geunsns.apps.user.data.entity;

import com.dev.geunsns.apps.model.UserRole;
import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.user.data.dto.UserDto;
import com.dev.geunsns.global.config.jpaauditing.BaseEntity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

	private LocalDateTime deletedAt;

	@OneToMany(mappedBy = "user")
	private List<PostEntity> post = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<CommentEntity> comments = new ArrayList<>();

	@Builder
	public UserEntity(Long id, String userName, String password, UserRole role, LocalDateTime deletedAt) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.deletedAt = deletedAt;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.toString()));
	}


}
