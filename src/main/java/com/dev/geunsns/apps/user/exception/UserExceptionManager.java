package com.dev.geunsns.apps.user.exception;

import com.dev.geunsns.global.data.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionManager {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(UserAppException.class)
    public ResponseEntity<?> userAppException(UserAppException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus()) // e 에서 상태코드 반환
                .body(e.getErrorCode().getMessage()); // e 에서 메세지 반환
    }

}
