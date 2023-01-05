package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import com.dev.geunsns.apps.post.data.dto.comment.request.CommentAddRequest;
import com.dev.geunsns.apps.post.data.dto.comment.response.CommentResponse;
import com.dev.geunsns.apps.post.data.dto.comment.request.CommentUpdateRequest;
import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.service.CommentService;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.fixture.PostEntityFixture;
import com.dev.geunsns.fixture.TestUserFixture;
import com.dev.geunsns.fixture.UserEntityFixture;
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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("댓글 작성 성공")
    void addPostComment_Success() throws Exception {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();
        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());
        PostEntity post = PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        CommentEntity comment = CommentEntity.builder()
                .id(1L)
                .user(user)
                .post(post)
                .comment("test comment")
                .build();

        CommentDto commentDto = CommentDto.toDto(comment);

        when(commentService.addComment(any(), any(), any()))
                .thenReturn(commentDto);

        CommentAddRequest commentAddRequest = CommentAddRequest.builder()
                .comment("test")
                .build();

        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentAddRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(jsonPath("$.result.comment").exists())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("댓글 수정 성공")
    void updateComment_Success() throws Exception {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();
        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());
        PostEntity post = PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        CommentEntity comment = CommentEntity.builder()
                .id(1L)
                .user(user)
                .post(post)
                .comment("test comment")
                .build();

        CommentUpdateRequest commentUpdateRequest = CommentUpdateRequest.builder()
                .comment("updated comment")
                .build();

        CommentDto commentDto = CommentDto.toDto(comment);

        when(commentService.updateComment(any(), any(), any()))
                .thenReturn(commentDto);

        mockMvc.perform(put("/api/v1/posts/1/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentUpdateRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.result.commentId").exists())
                .andExpect(jsonPath("$.result.comment").exists())
                .andExpect(jsonPath("$.result.userName").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk());
    }
}