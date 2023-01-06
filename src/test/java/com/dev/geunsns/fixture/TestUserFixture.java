package com.dev.geunsns.fixture;


import com.dev.geunsns.apps.user.data.model.UserRole;
import lombok.Getter;
import lombok.Setter;

public class TestUserFixture {

    public static TestEntity get() {
        TestEntity testEntity = new TestEntity();

        testEntity.setUserId(1L);
        testEntity.setUserName("test");
        testEntity.setPassword("test");
        testEntity.setUserRole(UserRole.ROLE_USER);

        testEntity.setPostId(1L);
        testEntity.setTitle("test");
        testEntity.setBody("test");

        return testEntity;
    }

    @Getter
    @Setter
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
