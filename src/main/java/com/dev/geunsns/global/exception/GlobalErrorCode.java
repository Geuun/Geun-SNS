package com.dev.geunsns.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum GlobalErrorCode {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "You do not have access permission.");

    private HttpStatus httpStatus;
    private String errorMessage;

    GlobalErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
