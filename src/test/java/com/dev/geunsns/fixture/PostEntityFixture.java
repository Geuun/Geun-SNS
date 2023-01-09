package com.dev.geunsns.fixture;

import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.auditing.BaseEntity;

public class PostEntityFixture extends BaseEntity {

    public static PostEntity get(Long id, String title, String body, UserEntity user) {

        PostEntity postEntity = PostEntity.builder()
                .id(id)
                .title(title)
                .body(body)
                .isDeleted(false)
                .postLikeCount(0)
                .build();

        return postEntity;
    }
}
