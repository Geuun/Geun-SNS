package com.dev.geunsns.apps.post.data.dto.post;

import com.dev.geunsns.apps.post.data.entity.PostEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

	private String title;
	private String body;

	@Builder
	public PostRequest(String title, String body) {
		this.title = title;
		this.body = body;
	}

	// Dto -> Entity
	public PostEntity toEntity(String title, String body) {
		return PostEntity.builder()
						 .title(title)
						 .body(body)
						 .build();
	}
}
