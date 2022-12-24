package com.dev.geunsns.apps.user.service;

import static org.mockito.ArgumentMatchers.any;

import com.dev.geunsns.apps.user.data.dto.UserDto;
import com.dev.geunsns.apps.user.data.dto.join.UserJoinRequest;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

	private final UserRepository userRepository = Mockito.mock(UserRepository.class);

	private final BCryptPasswordEncoder encoder = Mockito.mock(BCryptPasswordEncoder.class);

	private UserService userService;

	@BeforeEach
	void setUp() { // 의존성 Unit 테스트 단위 주입
		userService = new UserService(userRepository, encoder);
	}

	@Test
	@DisplayName("Service Layer joinUser Test - 회원등록 성공")
	void joinUser() {

		Mockito.when(userRepository.save(any()))
			   .thenReturn(new UserEntity("testUser",
										  "testPwd"));

		UserDto userJoinRequest =
			userService.joinUser(new UserJoinRequest("testUser",
													 "testPwd"));

		assertEquals("testUser", userJoinRequest.getUserName());
	}
}