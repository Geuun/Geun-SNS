package com.dev.geunsns.apps.post.data.dto.postlike.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeResponse {

    private String message;
    private Long postId;

    @Builder
    public PostLikeResponse(String message, Long postId) {
        this.message = message;
        this.postId = postId;
    }
}
