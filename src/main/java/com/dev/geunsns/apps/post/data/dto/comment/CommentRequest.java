package com.dev.geunsns.apps.post.data.dto.comment;

import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequest {

    private String comment;


    // Request -> Entity
    public CommentEntity toEntity(String comment) {
        return CommentEntity.builder()
                .comment(comment)
                .build();
    }
}
