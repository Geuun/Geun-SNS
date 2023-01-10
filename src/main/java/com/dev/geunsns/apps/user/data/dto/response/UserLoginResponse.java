package com.dev.geunsns.apps.user.data.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginResponse {

    private String grantType;
    private String jwt; //TODO: accessToken -> API 명세에 맞게 jwt로 변경 이후 다시 accessToken 으로 변경 예정
    private String refreshToken;
    private Long refreshTokenExpirationTime;

    @Builder
    public UserLoginResponse(String grantType, String jwt, String refreshToken, Long refreshTokenExpirationTime) {
        this.grantType = grantType;
        this.jwt = jwt;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}
