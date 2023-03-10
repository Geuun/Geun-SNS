package com.dev.geunsns.apps.post.data.dto.postlike.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeCountResponse {
    private Long postId;
    private Integer likeCount;

    @Builder
    public PostLikeCountResponse(Long postId, Integer likeCount) {
        this.postId = postId;
        this.likeCount = likeCount;
    }
}
