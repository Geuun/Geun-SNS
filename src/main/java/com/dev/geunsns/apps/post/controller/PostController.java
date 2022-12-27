package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.comment.CommentRequest;
import com.dev.geunsns.apps.post.data.dto.comment.CommentResponse;
import com.dev.geunsns.apps.post.data.dto.comment.CommentSimpleResponse;
import com.dev.geunsns.apps.post.data.dto.post.PostDetailResponse;
import com.dev.geunsns.apps.post.data.dto.post.PostRequest;
import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.PostResponse;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.service.PostService;
import com.dev.geunsns.global.data.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

	private final String routePath = "/api/v1/posts/";

	private final PostService postService;

	@PostMapping// 포스트 작성
	public Response<PostResponse> addPost(@RequestBody PostRequest postAddRequest, Authentication authentication) {
		log.info("Add Post Title : {}", postAddRequest.getTitle());
		String userName = authentication.getName();
		log.info("userName : {} ", userName);
		PostDto postDto = postService.addPost(postAddRequest.getTitle(), postAddRequest.getBody(), authentication.getName());

		return Response.success(new PostResponse("SUCCESS", postDto.getId()));
	}

	@GetMapping("/{postId}") // Post view
	public ResponseEntity<Response> findById(@PathVariable Long postId, Authentication authentication) {

		String userName = authentication.getName();
		PostDetailResponse postDetailResponse = postService.getPost(userName, postId);
		return ResponseEntity.ok()
				.body(Response.success(postDetailResponse));
	}

	@GetMapping //PostList
	public Response<Page<PostDto>> getPostList(@PageableDefault(size = 20)
											   @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<PostDto> postDtos = postService.getPostList(pageable);
		return Response.success(postDtos);
	}

	@PutMapping("/{id}") // Post Update
	public ResponseEntity<Response> modifyPost(@RequestBody PostRequest postRequest, @PathVariable Long id, Authentication authentication) {
		String userName = authentication.getName();
		PostResponse postResponse = postService.modifyPost(userName, id,  postRequest);
		return ResponseEntity.ok()
				.body(Response.success(new PostResponse("SUCCESS", id)));
	}

	@DeleteMapping("/{id}") // Post Delete
	public ResponseEntity<Response> deletePost(@PathVariable Long postId, Authentication authentication){
		log.info("게시글 삭제 컨틀로러");

		postService.deletePost(postId, authentication.getName());
		return ResponseEntity.ok().body(Response.success(new PostResponse("SUCCESS", postId)));
	}

	@PostMapping("/{id}/comment") // comment add
	public ResponseEntity<Response> addComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest, Authentication authentication){
		String userName = authentication.getName();
		CommentResponse commentResponse = postService.addComment(id, userName, commentRequest.getComment());
		return ResponseEntity.ok().body(Response.success(commentResponse));
	}

	@PutMapping("/{postId}/comment/{id}") // comment update
	public ResponseEntity<Response> modifyComment(Authentication authentication,@PathVariable Long postId, @PathVariable Long id,@RequestBody CommentRequest commentRequest){
		String userName = authentication.getName();
		CommentResponse commentResponse =postService.updateComment(postId, userName,commentRequest,id);
		return ResponseEntity.ok().body(Response.success(commentResponse));
	}

	@DeleteMapping("/{postId}/comments/{id}") // commentdelete
	public ResponseEntity<Response> deleteComment(@PathVariable Long commentId, Authentication authentication) {

		postService.deleteComment(commentId, authentication.getName());

		return ResponseEntity.ok()
				.body(Response.success(new CommentSimpleResponse("SUCCESS", commentId)));
	}
}
