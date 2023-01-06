package com.dev.geunsns.apps.alarm.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum AlarmErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "The User was not founded. Please try again."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission. Please try again."),
    ;

    private HttpStatus httpStatus;
    private String errorMessage;

    AlarmErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
