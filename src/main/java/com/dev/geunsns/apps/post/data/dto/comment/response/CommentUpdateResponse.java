package com.dev.geunsns.apps.post.data.dto.comment.response;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateResponse {
    private Long id;
    private String comment;
    private String userName;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder
    public CommentUpdateResponse(Long id, String comment, String userName, Long postId, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.comment = comment;
        this.userName = userName;
        this.postId = postId;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static CommentUpdateResponse toResponse(CommentDto commentDto) {
        return new CommentUpdateResponse(
                commentDto.getCommentId(),
                commentDto.getComment(),
                commentDto.getUserName(),
                commentDto.getPostId(),
                commentDto.getCreatedAt(),
                commentDto.getLastModifiedAt()
        );
    }
}