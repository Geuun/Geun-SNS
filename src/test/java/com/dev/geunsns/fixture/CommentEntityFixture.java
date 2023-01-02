package com.dev.geunsns.fixture;

import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.jpaauditing.BaseEntity;

public class CommentEntityFixture extends BaseEntity {

    public static CommentEntity get(Long id, String comment, PostEntity post, UserEntity user) {

        CommentEntity commentEntity = CommentEntity.builder()
                .id(id)
                .user(user)
                .post(post)
                .comment("test")
                .build();

        return commentEntity;
    }
}
