package com.dev.geunsns.global.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse<T> {

	/**
	 * Error를 감싸서 Response의 Body에 전달
	 */
	private T errorCode;
	private String errorMessage;

	@Builder
	public ErrorResponse(T errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}