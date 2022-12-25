package com.dev.geunsns.apps.post.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public enum PostAppErrorCode {

	INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission. Please try again."),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "The Post was not founded. Please try again."),
	USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "The User was not founded. Please try again."),
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "The Comment was not founded. Please try again."),
	;

	private HttpStatus httpStatus;
	private String errorMessage;
}
