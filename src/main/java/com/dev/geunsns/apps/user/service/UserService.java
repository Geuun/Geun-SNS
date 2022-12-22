package com.dev.geunsns.apps.user.service;

import com.dev.geunsns.apps.user.data.dto.UserDto;
import com.dev.geunsns.apps.user.data.dto.join.UserJoinRequest;
import com.dev.geunsns.apps.user.data.entity.User;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.exception.UserErrorCode;
import com.dev.geunsns.apps.user.repository.UserRepository;
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
                throw new UserAppException(
                    // errorMessage 없을 때 enum ErrorMessage 출력해보기
                    UserErrorCode.DUPLICATED_USER_NAME
                );
            });

        User savedUser = userRepository.save(userJoinRequest.toEntity(
            userJoinRequest.getUserName(),
            userJoinRequest.getPassword()
        ));
        return UserDto.builder()
            .id(savedUser.getId())
            .userName(savedUser.getUserName())
            .password(savedUser.getPassword())
            .role(savedUser.getRole())
            .build();
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() ->
            new UserAppException(
                // errorMessage 있을 때 errorMessage 출력해보기
                UserErrorCode.NOT_FOUND,
                "해당 유저가 존재하지 않습니다."
            )
        );
    }
}
