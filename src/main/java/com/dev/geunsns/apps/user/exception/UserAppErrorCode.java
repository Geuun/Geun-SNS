package com.dev.geunsns.apps.user.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public enum UserAppErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "UserName is duplicated."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "The UserName is not found."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "The Password is incorrect."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "You do not have that permission."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "The Role is invalid."),
    ;

    private HttpStatus httpStatus;
    private String errorMessage;
}
