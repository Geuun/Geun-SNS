package com.dev.geunsns.apps.post.data.dto.comment;

import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDto {

    private Long id;
    private String comment;
    private String userName;
    private Long postId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;


    @Builder
    public CommentDto(Long id, String comment, String userName, Long postId, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        this.id = id;
        this.comment = comment;
        this.userName = userName;
        this.postId = postId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public static CommentDto toDto(CommentEntity commentEntity) {
        return new CommentDto(
                commentEntity.getId(),
                commentEntity.getComment(),
                commentEntity.getUser().getUserName(),
                commentEntity.getPost().getId(),
                commentEntity.getCreatedAt(),
                commentEntity.getCreatedBy(),
                commentEntity.getLastModifiedAt(),
                commentEntity.getLastModifiedBy()
        );
    }
}
