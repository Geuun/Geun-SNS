package com.dev.geunsns.global.exception;

import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.global.data.response.ErrorResponse;
import com.dev.geunsns.global.data.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionManager {


	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> runtimeException(RuntimeException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getCause(), e.getMessage());
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(Response.error(errorResponse));
	}

	//UserApp
	@ExceptionHandler(UserAppException.class)
	public ResponseEntity<?> userAppException(UserAppException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getUserAppErrorCode(),
														e.getUserErrorMessage());
		log.info(String.valueOf(errorResponse));
		return ResponseEntity
			.status(e
						.getUserAppErrorCode()
						.getHttpStatus()) // e 에서 상태코드 반환
			.body(Response.error(errorResponse)); // e 에서 메세지 반환
	}
}
