package com.dev.geunsns.fixture;

import com.dev.geunsns.apps.user.data.model.UserRole;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.auditing.BaseEntity;

public class UserEntityFixture extends BaseEntity {

    public static UserEntity getUser(String userName, String password) {
        UserEntity user = UserEntity.builder()
                .id(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.ROLE_USER)
                .build();

        return user;
    }

    public static UserEntity getAdmin(String userName, String password) {
        UserEntity admin = UserEntity.builder()
                .id(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.ROLE_ADMIN)
                .build();

        return admin;
    }
}
