package com.dev.geunsns.apps.user.controller;

import com.dev.geunsns.apps.user.data.dto.UserDto;
import com.dev.geunsns.apps.user.data.dto.join.UserJoinRequest;
import com.dev.geunsns.apps.user.data.dto.join.UserJoinResponse;
import com.dev.geunsns.apps.user.data.dto.login.UserLoginRequest;
import com.dev.geunsns.apps.user.data.dto.login.UserLoginResponse;
import com.dev.geunsns.apps.user.service.UserService;
import com.dev.geunsns.global.data.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public Response<UserJoinResponse> joinUser(@RequestBody UserJoinRequest userJoinRequest) {
		UserDto userDto = userService.joinUser(userJoinRequest);
		UserJoinResponse userJoinResponse = new UserJoinResponse(userDto.getId(),
																 userDto.getUserName());
		return Response.success("SUCCESS", userJoinResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<Response<UserLoginResponse>> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
		String token = userService.userLogin(userLoginRequest.getUserName(),
											 userLoginRequest.getPassword());
		return ResponseEntity
			.ok()
			.body(Response.success(new UserLoginResponse(token)));
	}
}