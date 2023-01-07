package com.dev.geunsns.fixture;


import com.dev.geunsns.apps.user.data.model.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class TestUserFixture {

    public static TestEntity get() {
        return TestEntity.builder()
                .userId(1L)
                .userName("test")
                .password("test")
                .userRole(UserRole.ROLE_USER)
                .postId(1L)
                .title("test")
                .body("test")
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class TestEntity {

        private Long userId;
        private String userName;
        private String password;
        private UserRole userRole;

        private Long postId;
        private String title;
        private String body;
    }
}
