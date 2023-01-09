package com.dev.geunsns.fixture;

import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.data.entity.PostLikeEntity;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.auditing.BaseEntity;

public class PostLikeEntityFixture extends BaseEntity {

    public static PostLikeEntity get(Long id, UserEntity user, PostEntity post) {

        PostLikeEntity postLikeEntity = PostLikeEntity.builder()
                .id(1L)
                .user(user)
                .post(post)
                .build();

        return postLikeEntity;
    }
}
