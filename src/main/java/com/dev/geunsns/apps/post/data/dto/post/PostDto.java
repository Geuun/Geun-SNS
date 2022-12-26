package com.dev.geunsns.apps.post.data.dto.post;

import com.dev.geunsns.apps.post.data.entity.PostEntity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostDto {

	private Integer id;
	private String title;
	private String body;

	// User
	private String userName;

	// BaseEntity / BaseTimeEntity
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;

	public PostDto(String title, String body, String userName) {
		this.title = title;
		this.body = body;
		this.userName = userName;
	}

	@Builder
	public PostDto(Integer id, String title, String body, String userName, LocalDateTime createdAt,
				   LocalDateTime modifiedAt) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.userName = userName;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	// Entity -> Dto
	public static PostDto toDto(PostEntity postEntity) {
		return new PostDto(
			postEntity.getId(),
			postEntity.getTitle(),
			postEntity.getBody(),
			postEntity.getUser().getUserName(),
			postEntity.getCreatedAt(),
			postEntity.getLastModifiedAt()
		);
	}
}
