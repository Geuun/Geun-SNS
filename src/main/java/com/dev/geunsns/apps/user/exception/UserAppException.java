package com.dev.geunsns.apps.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAppException extends RuntimeException{
    private UserErrorCode userErrorCode;
    private String userErrorMessage;

    // 메세지 입력안했을 때 enum메세지 반환
    public UserAppException(UserErrorCode userErrorCode) {
        this.userErrorCode = userErrorCode;
        this.userErrorMessage = userErrorCode.getErrorMessage();
    }
}
