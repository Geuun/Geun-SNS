package com.dev.geunsns.apps.post.data.dto.comment.response;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDeleteResponse {

    private String message;
    private Long commentId;

    public CommentDeleteResponse(String message, Long commentId) {
        this.message = message;
        this.commentId = commentId;
    }

    public static CommentDeleteResponse toResponse(CommentDto commentDto) {
        return new CommentDeleteResponse(
                "SUCCESS - DELETE COMMENT",
                commentDto.getId()
        );
    }
}
