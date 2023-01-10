package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.request.PostAddRequest;
import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.service.PostService;
import com.dev.geunsns.global.config.security.encrypter.EncrypterConfig;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @MockBean
    EncrypterConfig encrypterConfig;

    @Autowired
    ObjectMapper objectMapper;

    private final String testTitle = "Test Post Title";
    private final String testBody = "Test Post Body";

    @Test
    @WithMockUser
    @DisplayName("게시물 작성 성공")
    void addPost_Success_Test() throws Exception {

        PostAddRequest postAddRequest = PostAddRequest.builder()
                .title(testTitle)
                .body(testBody)
                .build();

        when(postService.addPost(any(), any()))
                .thenReturn(PostDto.builder()
                        .id(0L)
                        .build());

        mockMvc.perform(post("/api/v1/posts").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postAddRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(jsonPath("$.result.postId").value(0))
                .andExpect(jsonPath("$.result.message").exists());
    }

    @Test
    @WithAnonymousUser // 인증 X
    @DisplayName("게시물 작성 실패 - 인증 X")
    void addPost_Fail_Test() throws Exception {

        PostAddRequest postAddRequest = PostAddRequest.builder()
                .title(testTitle)
                .body(testBody)
                .build();

        when(postService.addPost(any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "Invalid Permission."));

        mockMvc.perform(post("/api/v1/posts").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postAddRequest))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("게시글 1개 조회")
    public void getPost() throws Exception {
        when(postService.getPost(any()))
                .thenReturn(PostDto.builder()
                        .id(1L)
                        .title("test")
                        .body("test")
                        .userName("test")
                        .build());

        mockMvc.perform(get("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(PostDto.builder()
                                .id(1L)
                                .title("test")
                                .body("test")
                                .userName("test")
                                .build())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("게시물 수정 - 성공")
    public void update_Success() throws Exception {

        PostAddRequest postAddRequest = PostAddRequest.builder()
                .title("title")
                .body("body")
                .build();

        when(postService.updatePost(any(), any(), any(), any()))
                .thenReturn(PostDto.builder()
                        .title("test")
                        .body("test")
                        .build());

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postAddRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("게시글 수정 실패 - 포스트 없음")
    void updatePost_fail1() throws Exception {

        PostAddRequest postAddRequest = PostAddRequest.builder()
                .title("title")
                .body("body")
                .build();

        when(postService.updatePost(any(), any(), any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.POST_NOT_FOUND, "The Post was not found."));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postAddRequest)))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }


    @Test
    @WithAnonymousUser
    @DisplayName("게시글 수정 실패 - 권한 없음")
    void updatePost_fail2() throws Exception {
        PostAddRequest postAddRequest = PostAddRequest.builder()
                .title("title")
                .body("body")
                .build();

        when(postService.updatePost(any(), any(), any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postAddRequest)))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("게시글 수정 실패 - 작성자 불일치")
    void updatePost_fail3() throws Exception {

        PostAddRequest postAddRequest = PostAddRequest.builder()
                .title("title")
                .body("body")
                .build();
        when(postService.updatePost(any(), any(), any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "작성자 불일치"));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postAddRequest)))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }


    @Test
    @WithMockUser
    @DisplayName("게시글 수정 실패- DB Error")
    void updatePost_fail4() throws Exception {

        PostAddRequest postAddRequest = PostAddRequest.builder()
                .title("title")
                .body("body")
                .build();

        when(postService.updatePost(any(), any(), any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.DATABASE_ERROR));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postAddRequest)))
                .andExpect(status().is(PostAppErrorCode.DATABASE_ERROR.getHttpStatus().value()))
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("게시글 삭제 성공")
    void delete_success() throws Exception {

        when(postService.deletePost(any(), any(), any()))
                .thenReturn(PostDto.builder()
                        .id(1L)
                        .build());

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("게시글 삭제 실패 - 권한없음")
    void deletePost_Fail1() throws Exception {

        when(postService.deletePost(any(), any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("게시글 삭제 실패 - token 정보와 틀림")
    void deletePost_Fail2() throws Exception {

        when(postService.deletePost(any(), any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.POST_NOT_FOUND));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andDo(print());

    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("게시글 삭제 실패 - 작성자 불일치")
    void deletePost_Fail3() throws Exception {

        given(postService.deletePost(any(), any(), any()))
                .willThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("게시글 삭제 실패 - DB 에러")
    void deletePost_Fail4() throws Exception {

        when(postService.deletePost(any(), any(), any()))
                .thenThrow(new PostAppException(PostAppErrorCode.DATABASE_ERROR));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(PostAppErrorCode.DATABASE_ERROR.getHttpStatus().value()))
                .andDo(print());
    }

}