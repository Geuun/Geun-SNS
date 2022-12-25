package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.post.addpost.PostAddRequest;
import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.addpost.PostAddResponse;
import com.dev.geunsns.apps.post.service.PostService;
import com.dev.geunsns.global.data.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

	private final String routePath = "/api/v1/posts/";

	private final PostService postService;


	@PostMapping("")
	public Response<PostAddResponse> addPost(@RequestBody PostAddRequest postAddRequest, Authentication authentication) {
		log.info(String.format("Route: %s", routePath));
		log.info(String.format("Message: %s", "request to create a post has been detected."));
		PostDto postDto = postService.addPost(postAddRequest.getTitle(), postAddRequest.getBody(), authentication.getName());

		return Response.success(new PostAddResponse(String.format("Post #%d added successfully", postDto.getId()), postDto.getId()));
	}
}
