package com.dev.geunsns.fixture;

import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.data.entity.PostLikeEntity;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.jpaauditing.BaseEntity;

import java.util.List;

public class PostEntityFixture extends BaseEntity {

    public static PostEntity get(Long id, String title, String body, UserEntity user) {

        PostEntity postEntity = PostEntity.builder()
                .id(id)
                .user(UserEntityFixture.getUser(user.getUserName(), user.getPassword()))
                .title(title)
                .body(body)
                .build();

        return postEntity;
    }
}
