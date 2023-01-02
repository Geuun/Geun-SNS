package com.dev.geunsns.apps.post.data.dto.comment.request;

import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentAddRequest {
    private String comment;

    @Builder
    public CommentAddRequest(String comment) {
        this.comment = comment;
    }

    // Request -> Entity
    public CommentEntity toEntity(String comment) {
        return CommentEntity.builder()
                .comment(comment)
                .build();
    }
}
