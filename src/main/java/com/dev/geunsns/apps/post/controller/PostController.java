package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.PostAddRequest;
import com.dev.geunsns.apps.post.data.dto.post.PostResponse;
import com.dev.geunsns.apps.post.service.PostService;
import com.dev.geunsns.global.data.response.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final String routePath = "/api/v1/posts/";

    private final PostService postService;

    @ApiOperation(value = "Post 작성 기능", notes = "Post 작성할 내용을 입력해주세요")
    @PostMapping// 포스트 작성
    public Response addPost(@RequestBody PostAddRequest postAddRequest, Authentication authentication) {
        log.info("Add Post Title : {}", postAddRequest.getTitle());
        String userName = authentication.getName();
        log.info("userName : {} ", userName);
        PostDto postDto = postService.addPost(postAddRequest, authentication.getName());

        return Response.success(new PostResponse("ADD POST SUCCESS", postDto.getId()));
    }

    @ApiOperation(value = "Post 조회 기능", notes = "조회할 Post의 Id를 입력해주세요.")
    @GetMapping("/{postId}") // Post view
    public Response findById(@PathVariable Long postId, Authentication authentication) {

        String userName = authentication.getName();
        PostDto getPost = postService.getPost(userName, postId);

        return Response.success(new PostResponse("GET POST SUCCESS", getPost.getId()));
    }

    @ApiOperation(value = "Post List 조회 기능", notes = "Post의 List(size = 20)를 조회합니다.")
    @GetMapping //PostList
    public Response getPostList(@PageableDefault(size = 20) @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> getPostList = postService.getPostList(pageable);

        return Response.success(getPostList);
    }

    @ApiOperation(value = "Post 수정 기능", notes = "Post 수정할 내용을 입력해주세요.")
    @PutMapping("/{id}") // Post Update
    public Response modifyPost(@RequestBody PostAddRequest postAddRequest, @PathVariable Long id, Authentication authentication) {
        String userName = authentication.getName();
        PostDto modifiedPost = postService.updatePost(userName, id, postAddRequest);
        return Response.success(new PostResponse("UPDATE POST SUCCESS", modifiedPost.getId()));
    }

    @ApiOperation(value = "Post 삭제 기능", notes = "삭제할 Post의 Id를 입력해주세요")
    @DeleteMapping("/{id}") // Post Delete
    public Response deletePost(@PathVariable Long id, Authentication authentication) {
        PostDto deletedPost = postService.deletePost(id, authentication.getName(), authentication.getAuthorities());
        return Response.success(new PostResponse("DELETE POST SUCCESS", deletedPost.getId()));
    }
}
