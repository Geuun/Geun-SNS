package com.dev.geunsns.apps.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;


/**
 * DataJpaTest 어노테이션은 인메모리 DB(지원: H2, DERBY, HSQLDB)로 테스트 물리적인 DB를 연결하기위해 AutoConfigureTestDatabase 어노테이션의 replace 옵션 사용)
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Auditing Test")
    void dataJpaAuditingTest() {

        String userName = "AuditingTestUserName";
        String password = "testPwd";

        UserEntity testUser = UserEntity.builder()
                                        .userName(userName)
                                        .password(password)
                                        .build();

        UserEntity savedUser = userRepository.save(testUser);

        System.out.println(String.format("UserName : %s", savedUser.getUserName()));
        System.out.println(String.format("Password : %s", savedUser.getPassword()));
        System.out.println(String.format("userRole : %s", savedUser.getRole()));
        System.out.println(String.format("createdAt : %s", savedUser.getCreatedAt()));
        System.out.println(String.format("createdBy : %s", savedUser.getCreatedBy()));
        System.out.println(String.format("modifiedAt : %s", savedUser.getLastModifiedAt()));
        System.out.println(String.format("modifiedBy : %s", savedUser.getLastModifiedBy()));

        assertDoesNotThrow(() -> {
            assertNotNull(savedUser.getCreatedAt());
            assertNotNull(savedUser.getCreatedBy());
            assertNotNull(savedUser.getLastModifiedAt());
            assertNotNull(savedUser.getLastModifiedBy());
        });
    }
}