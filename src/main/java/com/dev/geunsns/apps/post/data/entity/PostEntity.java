package com.dev.geunsns.apps.post.data.entity;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.jpaauditing.BaseEntity;

import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class PostEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String body;

	@Column(columnDefinition = "integer default 1")	// TODO:
													// 생성시 기본 값 1 주입
	private Integer status;							// -1: 삭제된 상태 0: 비공개 상태 1: 기본상태 개발 필요

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@OneToMany(mappedBy = "post")
	private List<CommentEntity> comments = new ArrayList<>();

	@Builder
	public PostEntity(Long id, String title, String body, Integer status, UserEntity user, List<CommentEntity> comments) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.status = status;
		this.user = user;
		this.comments = comments;
	}


	public void updatePost(PostEntity update) {
		this.title = update.title;
		this.body = update.body;
	}

	public void deletePost(Integer status) {
		this.status = status;
	}
}
