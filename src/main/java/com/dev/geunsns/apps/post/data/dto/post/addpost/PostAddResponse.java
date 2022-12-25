package com.dev.geunsns.apps.post.data.dto.post.addpost;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAddResponse {

	private Integer postId;
	private String message;

	@Builder
	public PostAddResponse(String message, Integer postId) {
		this.message = message;
		this.postId = postId;
	}
}
