package com.dev.geunsns.apps.user.service;

import com.dev.geunsns.apps.user.data.dto.UserDto;
import com.dev.geunsns.apps.user.data.dto.join.UserJoinRequest;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.exception.UserErrorCode;
import com.dev.geunsns.apps.user.repository.UserRepository;
import com.dev.geunsns.global.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	@Value("${jwt.token.secret}")
	private String secretKey;

	private final long expiredTime = 1000 * 60 * 60; // ms 단위

	public UserDto joinUser(UserJoinRequest userJoinRequest) {
		/**
		 * 회원가입 시 userName 중복 체크 진행
		 * 중복 -> Exception() 처리
		 */
		userRepository.findByUserName(userJoinRequest.getUserName())
					  .ifPresent(user -> {
						  throw new UserAppException(UserErrorCode.DUPLICATED_USER_NAME,
													 String.format("UserName %s is Duplicated",
																   userJoinRequest.getUserName()));
					  });

		UserEntity savedUser = userRepository.save(userJoinRequest.toEntity(userJoinRequest.getUserName(),
																			encoder.encode(userJoinRequest.getPassword())
																		   )
												  );
		return UserDto.builder()
					  .id(savedUser.getId())
					  .userName(savedUser.getUserName())
					  .password(savedUser.getPassword())
					  .role(savedUser.getRole())
					  .build();
	}

	public UserEntity getUserByUserName(String userName) {
		return userRepository.findByUserName(userName)
							 .orElseThrow(() -> new UserAppException(UserErrorCode.NOT_FOUND,
																	 String.format("User %s does not exist.",
																				   userName)));
	}

	public String userLogin(String userName, String password) {
		/**
		 * 예외처리
		 * 1. 로그인 시 DB에 userName이 없는 경우 -> NotFound
		 * 2. 비밀번호가 틀린경우 -> Invalid pwd
		 * 3. 모든 예외 처리 통과시 Token 발급
		 */

		// 1. userName 중복 검사
		UserEntity loginTryUser = getUserByUserName(userName);

		// 2. password 검사
		if (! encoder.matches(password, loginTryUser.getPassword())) {
			throw new UserAppException(UserErrorCode.INVALID_PASSWORD,
									   "The password is incorrect. Please check again.");
		}

		// 3. Token 발급
		String token = JwtTokenUtils.generateToken(userName,
												   secretKey,
												   expiredTime);

		return token;
	}
}
