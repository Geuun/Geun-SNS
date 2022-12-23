package com.dev.geunsns.global.data.response;

import com.dev.geunsns.apps.user.data.dto.login.UserLoginResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {


	private String resultCode;
	private T result;

	public static <T> Response<T> error(String resultCode, T result) {
		return new Response(resultCode, result);
	}

	public static <T> Response<T> success(String resultCode, T result) {
		return new Response(resultCode, result);
	}

	public static <T> Response<T> success(T result) {
		return new Response("SUCCESS", result);
	}

	public static Response<Void> success() {
		return new Response("SUCCESS", null);
	}
}
