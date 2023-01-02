package com.dev.geunsns.apps.post.data.dto.comment.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {

    private String message;
    private Long commentId;

    @Builder
    public CommentResponse(String message, Long commentId) {
        this.message = message;
        this.commentId = commentId;
    }
}
