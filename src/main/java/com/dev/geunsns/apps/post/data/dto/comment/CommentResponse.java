package com.dev.geunsns.apps.post.data.dto.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {

    private Long id;
    private String comment;
    private String userName;
    private Long postId;
    private String createdAt;

    @Builder
    public CommentResponse(Long id, String comment, String userName, Long postId, String createdAt) {
        this.id = id;
        this.comment = comment;
        this.userName = userName;
        this.postId = postId;
        this.createdAt = createdAt;
    }
}
