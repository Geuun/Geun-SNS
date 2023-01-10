package com.dev.geunsns.apps.user.service;

import com.dev.geunsns.apps.user.data.dto.response.UserLoginResponse;
import com.dev.geunsns.apps.user.data.model.UserRole;
import com.dev.geunsns.apps.user.data.dto.UserDto;
import com.dev.geunsns.apps.user.data.dto.request.UserJoinRequest;
import com.dev.geunsns.apps.user.data.dto.request.UserLoginRequest;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.exception.UserAppErrorCode;
import com.dev.geunsns.apps.user.repository.UserRepository;
import com.dev.geunsns.global.config.jwt.JwtProvider;
import com.dev.geunsns.global.config.jwt.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtProvider jwtProvider;
    private final String BEARER_TYPE = "Bearer";

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
                    throw new UserAppException(UserAppErrorCode.DUPLICATED_USER_NAME,
                            String.format("UserName '%s' is Duplicated",
                                    userJoinRequest.getUserName()));
                });

        UserEntity savedUser = userRepository.save(userJoinRequest.toEntity(userJoinRequest.getUserName(),
                        encoder.encode(userJoinRequest.getPassword()), UserRole.ROLE_USER
                )
        );
        return UserDto.toDto(savedUser);
    }

    public UserEntity getUserByUserName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserAppException(UserAppErrorCode.NOT_FOUND,
                        String.format("User %s does not exist.", userName))
                );

        return userEntity;
    }

    public String userLogin(UserLoginRequest userLoginRequest) {

        String requestUserName = userLoginRequest.getUserName();
        String requestUserPwd = userLoginRequest.getPassword();

        // userName 검증
        // Entity -> Dto
        UserDto savedUser = UserDto.toDto(getUserByUserName(requestUserName));

        // pwd 검증
        if (!encoder.matches(requestUserPwd, savedUser.getPassword())) {
            throw new UserAppException(UserAppErrorCode.INVALID_PASSWORD,
                    "The password is incorrect. Please check again.");
        }

        // Token 발급
        String token = JwtUtils.generateAccessToken(savedUser.getUserName(), secretKey, expiredTime);

        return token;
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {

        String requestUserName = userLoginRequest.getUserName();
        String requestUserPwd = userLoginRequest.getPassword();

        // userName 검증
        // Entity -> Dto
        UserEntity userEntity = getUserByUserName(requestUserName);

        // pwd 검증
        if (!encoder.matches(requestUserPwd, userEntity.getPassword())) {
            throw new UserAppException(UserAppErrorCode.INVALID_PASSWORD, "The password is incorrect. Please check again.");
        }

        // 검증된 Id, Pwd를 기반으로 JWT Token 발급
        String accessToken = jwtProvider.generateToken(userEntity);
        String refreshToken = jwtProvider.generateRefreshToken(userEntity);
        long expiredTime = jwtProvider.getExpiration(accessToken);

        // Redis에 Token 저장
        redisTemplate.opsForValue().set("RT:" + userEntity.getUserName(),refreshToken, jwtProvider.getExpiration(accessToken), TimeUnit.MILLISECONDS);

        return UserLoginResponse.builder()
                .grantType(BEARER_TYPE)
                .jwt(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(jwtProvider.getExpiration(accessToken))
                .build();
    }
}
