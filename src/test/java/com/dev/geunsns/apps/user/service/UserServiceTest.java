package com.dev.geunsns.apps.user.service;

import com.dev.geunsns.apps.user.data.dto.request.UserJoinRequest;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.exception.UserAppErrorCode;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.repository.UserRepository;
import com.dev.geunsns.global.config.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private final BCryptPasswordEncoder encoder = Mockito.mock(BCryptPasswordEncoder.class);
    private final RedisTemplate<String, String> redisTemplate = Mockito.mock(RedisTemplate.class);
    private final JwtProvider jwtProvider = Mockito.mock(JwtProvider.class);
    private UserService userService;

    @BeforeEach
    void setUp() { // 의존성 Unit 테스트 단위 주입
        userService = new UserService(userRepository, encoder, redisTemplate, jwtProvider);
    }

    @Test
    @DisplayName("Service Layer joinUser Test - 회원등록 성공")
    void joinUser() {

        Mockito.when(userRepository.save(any()))
                .thenReturn(UserEntity.builder()
                        .userName("testUSer")
                        .password("testPwd")
                        .build());

        assertDoesNotThrow(() -> userService.joinUser(new UserJoinRequest("testUser", "testPwd")));
    }

    @Test
    @DisplayName("회원등록 실패 - 유저네임 중복")
    void joinUser_Fail() {

        Mockito.when(userRepository.save(any()))
                .thenThrow(new UserAppException(UserAppErrorCode.DUPLICATED_USER_NAME));

        assertThrows(UserAppException.class, () -> userService.joinUser(new UserJoinRequest("testUser", "testPwd")));
    }
}