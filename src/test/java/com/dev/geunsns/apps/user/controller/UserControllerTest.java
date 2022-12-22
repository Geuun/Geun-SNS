package com.dev.geunsns.apps.user.controller;

import com.dev.geunsns.apps.user.data.dto.UserDto;
import com.dev.geunsns.apps.user.data.dto.join.UserJoinRequest;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.exception.UserErrorCode;
import com.dev.geunsns.apps.user.service.UserService;
import com.dev.geunsns.global.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
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
                    .content(objectMapper.writeValueAsBytes(userJoinRequest))
            ).andDo(print())
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
            .thenThrow(new UserAppException(UserErrorCode.DUPLICATED_USER_NAME, null));

        mockMvc.perform(post("/api/v1/users/join").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userJoinRequest))
            ).andDo(print())
            .andExpect(status().isConflict());
    }
}