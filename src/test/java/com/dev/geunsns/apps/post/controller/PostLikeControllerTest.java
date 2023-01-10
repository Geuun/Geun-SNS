package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.service.PostLikeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostLikeController.class)
class PostLikeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostLikeService postLikeService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("좋아요 성공 - Add")
    void likePost_Success1() throws Exception {

        when(postLikeService.addAndRemoveLike(any(), any()))
                .thenReturn(true);

        mockMvc.perform(post("/api/v1/posts/1/likes")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").exists());
    }

    @Test
    @WithMockUser
    @DisplayName("좋아요 성공 - Remove")
    void likePost_Success2() throws Exception {

        when(postLikeService.addAndRemoveLike(any(), any()))
                .thenReturn(false);

        mockMvc.perform(post("/api/v1/posts/1/likes")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").exists());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("좋아요 실패 1 - 로그인 하지 않음")
    void likePost_Failed1() throws Exception {

        when(postLikeService.addAndRemoveLike(any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "Invalid Permission"));

        mockMvc.perform(post("/api/v1/posts/1/likes").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("좋아요 실패 2 - 해당 Post가 존재하지 않음")
    void likePost_Failed2() throws Exception {

        when(postLikeService.addAndRemoveLike(any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.POST_NOT_FOUND, "Post Not Found"));

        mockMvc.perform(post("/api/v1/posts/1/likes").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
}