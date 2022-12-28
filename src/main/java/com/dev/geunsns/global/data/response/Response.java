package com.dev.geunsns.global.data.response;

import com.dev.geunsns.apps.user.data.dto.login.UserLoginResponse;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response<T> {


	private String resultCode;
	private T result;

	@Builder
	public Response(String resultCode, T result) {
		this.resultCode = resultCode;
		this.result = result;
	}

	public static <T> Response success(T successResponse){
		return new Response("SUCCESS", successResponse);
	}

	public static <T> Response error(T errorResponse) {
		return new Response("ERROR", errorResponse);
	}
}
