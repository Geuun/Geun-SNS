package com.dev.geunsns.apps.post.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.geunsns.apps.post.data.dto.post.addpost.PostAddRequest;
import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.service.PostService;
import com.dev.geunsns.global.config.encrypter.EncrypterConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
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

		PostAddRequest postAddRequest = PostAddRequest.builder()
													  .title(testTitle)
													  .body(testBody)
													  .build();

		when(postService.addPost(any(), any(), any()))
			.thenReturn(PostDto.builder()
							   .id(0)
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
			   .andExpect(jsonPath("$.result.message").value(String.format("Post #%d added successfully", 0)));
	}

	@Test
	@WithAnonymousUser // 인증 X
	@DisplayName("게시물 작성 실패 - 인증 X")
	void addPost_Fail_Test() throws Exception {

		PostAddRequest postAddRequest = PostAddRequest.builder()
													  .title(testTitle)
													  .body(testBody)
													  .build();

		when(postService.addPost(any(), any(), any()))
			.thenThrow(new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "Invalid Permission."));

		mockMvc.perform(post("/api/v1/posts").with(csrf())
											 .contentType(MediaType.APPLICATION_JSON)
											 .content(objectMapper.writeValueAsBytes(postAddRequest))
					   )
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}
}