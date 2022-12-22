package com.dev.geunsns.apps.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UserErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "UserName is duplicated."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "The UserName is not found."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "The Password is incorrect.");

    private HttpStatus httpStatus;
    private String errorMessage;
}
