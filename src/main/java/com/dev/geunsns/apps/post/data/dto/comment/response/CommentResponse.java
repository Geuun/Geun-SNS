package com.dev.geunsns.apps.post.data.dto.comment.response;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {
    private Long commentId;
    private String comment;
    private String userName;
    private Long postId;
    private LocalDateTime createdAt;
    private String createdBy;

    @Builder
    public CommentResponse(Long commentId, String comment, String userName, Long postId, LocalDateTime createdAt, String createdBy) {
        this.commentId = commentId;
        this.comment = comment;
        this.userName = userName;
        this.postId = postId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public static CommentResponse toResponse(CommentDto commentDto) {
        return new CommentResponse(
                commentDto.getCommentId(),
                commentDto.getComment(),
                commentDto.getUserName(),
                commentDto.getPostId(),
                commentDto.getCreatedAt(),
                commentDto.getCreatedBy()
        );
    }
}
