package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import com.dev.geunsns.apps.post.data.dto.comment.CommentRequest;
import com.dev.geunsns.apps.post.data.dto.comment.CommentResponse;
import com.dev.geunsns.apps.post.data.dto.comment.CommentSimpleResponse;
import com.dev.geunsns.apps.post.data.dto.post.PostDetailResponse;
import com.dev.geunsns.apps.post.data.dto.post.PostRequest;
import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.PostResponse;
import com.dev.geunsns.apps.post.service.CommentService;
import com.dev.geunsns.apps.post.service.PostService;
import com.dev.geunsns.global.data.response.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    private final CommentService commentService;

    @ApiOperation(value = "Post 작성 기능", notes = "Post 작성할 내용을 입력해주세요")
    @PostMapping// 포스트 작성
    public Response addPost(@RequestBody PostRequest postAddRequest, Authentication authentication) {
        log.info("Add Post Title : {}", postAddRequest.getTitle());
        String userName = authentication.getName();
        log.info("userName : {} ", userName);
        PostDto postDto = postService.addPost(postAddRequest, authentication.getName());

        return Response.success(new PostResponse("SUCCESS", postDto.getId()));
    }

    @ApiOperation(value = "Post 조회 기능", notes = "조회할 Post의 Id를 입력해주세요.")
    @GetMapping("/{postId}") // Post view
    public Response findById(@PathVariable Long postId, Authentication authentication) {

        String userName = authentication.getName();
        PostDetailResponse postDetailResponse = postService.getPost(userName, postId);
        return Response.success(postDetailResponse);
    }

    @ApiOperation(value = "Post List 조회 기능", notes = "Post의 List(size = 20)를 조회합니다.")
    @GetMapping //PostList
    public Response<Page<PostDto>> getPostList(@PageableDefault(size = 20)
    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> postDtos = postService.getPostList(pageable);
        return Response.success(postDtos);
    }

    @ApiOperation(value = "Post 수정 기능", notes = "Post 수정할 내용을 입력해주세요.")
    @PutMapping("/{id}") // Post Update
    public Response modifyPost(@RequestBody PostRequest postRequest, @PathVariable Long id, Authentication authentication) {
        String userName = authentication.getName();
        PostResponse postResponse = postService.modifyPost(userName, id, postRequest);
        return Response.success(new PostResponse("SUCCESS", id));
    }

    @ApiOperation(value = "Post 삭제 기능", notes = "삭제할 Post의 Id를 입력해주세요")
    @DeleteMapping("/{id}") // Post Delete
    public Response deletePost(@PathVariable Long postId, Authentication authentication) {
        Long deletePostId = postService.deletePost(postId, authentication.getName(), authentication.getAuthorities());
        return Response.success(new PostResponse("SUCCESS", deletePostId));
    }

    @ApiOperation(value = "Comment List 조회 기능", notes = "Comment를 조회할 Post의 Id를 입력해주세요.")
    @GetMapping("/{id}/comments")
    public Response<Page<CommentDto>> getCommentList(
        @PageableDefault(size = 20) @SortDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        @PathVariable Long id) {
        Page<CommentDto> commentDtos = commentService.getComments(id, pageable);
        return Response.success(commentDtos);
    }

    @ApiOperation(value = "Comment 추가 기능", notes = "추가할 Comment의 내용을 입력해주세요")
    @PostMapping("/{id}/comment") // comment add
    public Response addComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest, Authentication authentication) {
        String userName = authentication.getName();
        CommentResponse commentResponse = commentService.addComment(id, userName, commentRequest.getComment());
        return Response.success(commentResponse);
    }

    @ApiOperation(value = "Comment 수정 기능", notes = "수정할 Comment의 내용을 입력해주세요")
    @PutMapping("/{postId}/comment/{id}") // comment update
    public Response modifyComment(Authentication authentication, @PathVariable Long postId, @PathVariable Long id,
        @RequestBody CommentRequest commentRequest) {
        String userName = authentication.getName();
        CommentResponse commentResponse = commentService.updateComment(postId, userName, commentRequest, id);
        return Response.success(commentResponse);
    }

    @ApiOperation(value = "Comment 삭제 기능", notes = "삭제할 Comment의 Id를 입력해주세요")
    @DeleteMapping("/{postId}/comments/{id}") // commentdelete
    public Response deleteComment(@PathVariable Long commentId, Authentication authentication) {

        commentService.deleteComment(commentId, authentication.getName());

        return Response.success(new CommentSimpleResponse("SUCCESS", commentId));
    }
}
