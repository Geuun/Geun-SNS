package com.dev.geunsns.apps.post.data.dto.comment.response;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentGetResponse {
    private Long commentId;
    private String comment;
    private String userName;
    private Long postId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;

    @Builder
    public CommentGetResponse(Long commentId, String comment, String userName, Long postId, LocalDateTime createdAt, String createdBy, LocalDateTime lastModifiedAt, String lastModifiedBy) {
        this.commentId = commentId;
        this.comment = comment;
        this.userName = userName;
        this.postId = postId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
    }

    public static CommentGetResponse toResponse(CommentDto commentDto) {
        return new CommentGetResponse(
                commentDto.getCommentId(),
                commentDto.getComment(),
                commentDto.getUserName(),
                commentDto.getPostId(),
                commentDto.getCreatedAt(),
                commentDto.getCreatedBy(),
                commentDto.getLastModifiedAt(),
                commentDto.getLastModifiedBy()
        );
    }
}
