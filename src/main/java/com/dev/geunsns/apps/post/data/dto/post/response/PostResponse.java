package com.dev.geunsns.apps.post.data.dto.post.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

	private Long postId;
	private String message;

	@Builder
	public PostResponse(String message, Long postId) {
		this.message = message;
		this.postId = postId;
	}
}
