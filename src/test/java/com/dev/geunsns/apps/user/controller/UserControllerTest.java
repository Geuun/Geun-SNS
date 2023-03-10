package com.dev.geunsns.apps.user.controller;

import com.dev.geunsns.apps.user.data.dto.UserDto;
import com.dev.geunsns.apps.user.data.dto.request.UserJoinRequest;
import com.dev.geunsns.apps.user.data.dto.request.UserLoginRequest;
import com.dev.geunsns.apps.user.exception.UserAppErrorCode;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공 - 중복 userName 없음")
    @WithMockUser
    void joinUser_success_test() throws Exception {

        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("testName")
                .password("testPwd")
                .build();

        when(userService.joinUser(any())).thenReturn(mock(UserDto.class));

        mockMvc.perform(
                        post("/api/v1/users/join").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복 userName 존재")
    @WithMockUser
    void joinUser_fail_test() throws Exception {
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("testName")
                .password("testPwd")
                .build();

        when(userService.joinUser(any()))
                .thenThrow(new UserAppException(UserAppErrorCode.DUPLICATED_USER_NAME));

        mockMvc.perform(post("/api/v1/users/join").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest))
                ).andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Login - 성공")
    @WithMockUser
    void login_success() throws Exception {

        String userName = "testName";
        String password = "testPwd";
        String token = userService.userLogin(any());
        System.out.println("token : " + token);

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .userName(userName)
                .password(password)
                .build();

        when(userService.userLogin(userLoginRequest))
                .thenReturn(token);

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Login 실패 - Id 없음")
    @WithMockUser
    void login_fail_id_not_found() throws Exception {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .userName("wrongUserName")
                .password("1234")
                .build();

        when(userService.userLogin(any()))
                .thenThrow(new UserAppException(UserAppErrorCode.NOT_FOUND));

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Login 실패 - Password 틀림")
    @WithMockUser
    void login_fail_wrong_password() throws Exception {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .userName("wrongUserName")
                .password("1234")
                .build();

        when(userService.userLogin(any()))
                .thenThrow(new UserAppException(UserAppErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


}