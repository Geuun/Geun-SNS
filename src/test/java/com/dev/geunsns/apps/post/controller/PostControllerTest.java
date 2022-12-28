package com.dev.geunsns.apps.post.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.geunsns.apps.post.data.dto.post.PostDetailResponse;
import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.PostRequest;
import com.dev.geunsns.apps.post.data.dto.post.PostResponse;
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

    private final Integer testId = 1;
    private final String testTitle = "Test Post Title";
    private final String testBody = "Test Post Body";
    private final String testUserName = "Test UserName";

    @Test
    @WithMockUser
    @DisplayName("게시물 작성 성공")
    void addPost_Success_Test() throws Exception {

        PostRequest postAddRequest = PostRequest.builder()
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
               .andExpect(jsonPath("$.result.message").exists())
               .andExpect(jsonPath("$.result.message").value(String.format("SUCCESS", 0)));
    }

    @Test
    @WithAnonymousUser // 인증 X
    @DisplayName("게시물 작성 실패 - 인증 X")
    void addPost_Fail_Test() throws Exception {

        PostRequest postAddRequest = PostRequest.builder()
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
        when(postService.getPost(any(), any())).thenReturn(new PostDetailResponse(1L,
                                                                                  "Test",
                                                                                  "Test",
                                                                                  "Test",
                                                                                  "Test",
                                                                                  "Test"));

        mockMvc.perform(get("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(new PostDetailResponse(1L,
                                                                                           "Test",
                                                                                           "Test",
                                                                                           "Test",
                                                                                           "Test",
                                                                                           "Test"))))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("게시물 수정 - 성공")
    public void Test4() throws Exception {
        PostRequest postRequest = PostRequest.builder()
                                             .title("title")
                                             .body("body")
                                             .build();
        when(postService.modifyPost(any(), any(), any())).thenReturn(new PostResponse("Message", 1L));

        mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(postRequest)))
               .andDo(print())
               .andExpect(jsonPath("$.result.message").exists())
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("게시글 수정 실패 - 포스트 없음")
    void modify_fail2() throws Exception {

        PostRequest postRequest = PostRequest.builder()
                                             .title("title")
                                             .body("body")
                                             .build();

        when(postService.modifyPost(any(), any(), any()))
            .thenThrow(new PostAppException(PostAppErrorCode.POST_NOT_FOUND, "The Post was not found."));

        mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(postRequest)))
               .andDo(print())
               .andExpect(status().is(PostAppErrorCode.POST_NOT_FOUND.getHttpStatus()
                                                                     .value()));
    }


    @Test
    @WithAnonymousUser
    @DisplayName("게시글 수정 실패 - 권한 없음")
    void modify_fail1() throws Exception {
        PostRequest postRequest = PostRequest.builder()
                                             .title("title")
                                             .body("body")
                                             .build();

        when(postService.modifyPost(any(), any(), any()))
            .thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(postRequest)))
               .andDo(print())
               .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser
    @DisplayName("게시글 수정 실패 - 작성자 불일치")
    void modify_fail3() throws Exception {

        PostRequest postRequest = PostRequest.builder()
                                             .title("title")
                                             .body("body")
                                             .build();
        when(postService.modifyPost(any(), any(), any()))
            .thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "작성자 불일치"));

        mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(postRequest)))
               .andDo(print())
               .andExpect(status().is(PostAppErrorCode.INVALID_PERMISSION.getHttpStatus()
                                                                         .value()));
    }


    @Test
    @WithMockUser
    @DisplayName("게시글 수정 실패- DB Error")
    void modify_fail4() throws Exception {

        PostRequest postRequest = PostRequest.builder()
                                             .title("title")
                                             .body("body")
                                             .build();

        when(postService.modifyPost(any(), any(), any()))
            .thenThrow(new PostAppException(PostAppErrorCode.DATABASE_ERROR));

        mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(postRequest)))
               .andDo(print())
               .andExpect(status().is(PostAppErrorCode.DATABASE_ERROR.getHttpStatus()
                                                                     .value()));
    }

    @Test
    @WithMockUser
    @DisplayName("게시글 삭제 성공")
    void delete_success() throws Exception {

        when(postService.deletePost(any(),any())).thenReturn(new PostResponse("Success", 1L));

        mockMvc.perform(delete("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(jsonPath("$.result.message").exists())
               .andExpect(jsonPath("$.resultCode").exists())
               .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("게시글 삭제 실패 - 권한없음")
    void delete_fail1() throws Exception {

        when(postService.deletePost(any(), any()))
            .thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isUnauthorized());
    }

//    @Test
//    @WithMockUser
//    @DisplayName("게시글 삭제 실패 - token 정보와 틀림")
//    void delete_fail2() throws Exception {
//
//        when(postService.deletePost(any(), any()))
//            .thenThrow(new PostAppException(PostAppErrorCode.POST_NOT_FOUND));
//
//        mockMvc.perform(delete("/api/v1/posts/1")
//                            .with(csrf())
//                            .contentType(MediaType.APPLICATION_JSON))
//               .andDo(print())
//               .andExpect(status().is(PostAppErrorCode.POST_NOT_FOUND.getHttpStatus().value()));
//
//    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("게시글 삭제 실패 - 작성자 불일치")
    void delete_fail3() throws Exception {

        when(postService.deletePost(any(), any()))
            .thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().is(PostAppErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
    }

    @Test
    @WithMockUser
    @DisplayName("게시글 삭제 실패 - DB 에러")
    void delete_fail4() throws Exception {

        when(postService.deletePost(any(), any()))
            .thenThrow(new PostAppException(PostAppErrorCode.DATABASE_ERROR));

        mockMvc.perform(delete("/api/v1/posts/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().is(PostAppErrorCode.DATABASE_ERROR.getHttpStatus().value()));
    }

}