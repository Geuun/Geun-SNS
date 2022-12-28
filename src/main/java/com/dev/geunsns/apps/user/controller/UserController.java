package com.dev.geunsns.apps.user.controller;

import com.dev.geunsns.apps.user.data.dto.UserDto;
import com.dev.geunsns.apps.user.data.dto.join.UserJoinRequest;
import com.dev.geunsns.apps.user.data.dto.join.UserJoinResponse;
import com.dev.geunsns.apps.user.data.dto.login.UserLoginRequest;
import com.dev.geunsns.apps.user.data.dto.login.UserLoginResponse;
import com.dev.geunsns.apps.user.service.UserService;
import com.dev.geunsns.global.data.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;
	private final String routePath = "/api/v1/users/";

	@PostMapping("/join")
	public Response<UserJoinResponse> joinUser(@RequestBody UserJoinRequest userJoinRequest) {
		log.info(String.format("Message: %s", "Member registration attempt detected"));
		log.info(String.format("Route: %s", routePath + "join"));
		UserDto userDto = userService.joinUser(userJoinRequest);
		UserJoinResponse userJoinResponse = new UserJoinResponse(userDto.getId(),
																 userDto.getUserName());
		return Response.success(userJoinResponse.getUserId());
	}

	@PostMapping("/login")
	public ResponseEntity<Response<UserLoginResponse>> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
		log.info(String.format("Message: %s", "A login attempt has been detected."));
		log.info(String.format("Route: %s", routePath + "login"));
		String token = userService.userLogin(userLoginRequest);
		log.info(String.format("UserName : %s getToken", userLoginRequest.getUserName()));
		return ResponseEntity
			.ok()
			.body(Response.success(new UserLoginResponse(token)));
	}
}