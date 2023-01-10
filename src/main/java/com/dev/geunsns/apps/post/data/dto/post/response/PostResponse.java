package com.dev.geunsns.apps.post.data.dto.post.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private String message;
    private Long postId;

    @Builder
    public PostResponse(String message, Long postId) {
        this.message = message;
        this.postId = postId;
    }

    public static PostResponse toResponse(String message, Long postId) {
        return PostResponse.builder()
                .message(message)
                .postId(postId)
                .build();
    }
}
