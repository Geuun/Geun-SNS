package com.dev.geunsns.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GlobalException extends RuntimeException {

    private GlobalErrorCode ErrorCode;
    private String ErrorMessage;

    // 메세지 입력안했을 때 enum메세지 반환
    public GlobalException(GlobalErrorCode ErrorCode) {
        this.ErrorCode = ErrorCode;
        this.ErrorMessage = ErrorCode.getErrorMessage();
    }

    // 메세지 직접 입력했을 때 해당 메세지 반환
    public GlobalException(GlobalErrorCode ErrorCode, String ErrorMessage) {
        this.ErrorCode = ErrorCode;
        this.ErrorMessage = ErrorMessage;
    }
}
