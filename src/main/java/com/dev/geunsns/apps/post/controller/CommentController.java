package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import com.dev.geunsns.apps.post.data.dto.comment.CommentRequest;
import com.dev.geunsns.apps.post.data.dto.comment.CommentResponse;
import com.dev.geunsns.apps.post.data.dto.comment.CommentSimpleResponse;
import com.dev.geunsns.apps.post.service.CommentService;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {

    private final String routePath = "/api/v1/posts/";

    private final CommentService commentService;


    @ApiOperation(value = "Comment List 조회 기능", notes = "Comment를 조회할 Post의 Id를 입력해주세요.")
    @GetMapping("/{id}/comments")
    public Response<Page<CommentDto>> getCommentList(
            @PageableDefault(size = 20) @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long id) {
        Page<CommentDto> commentDtos = commentService.getComments(id, pageable);
        return Response.success(commentDtos);
    }

    @ApiOperation(value = "Comment 추가 기능", notes = "추가할 Comment의 내용을 입력해주세요")
    @PostMapping("/{id}/comments") // comment add
    public Response addComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest, Authentication authentication) {
        String userName = authentication.getName();
        CommentResponse commentResponse = commentService.addComment(id, userName, commentRequest.getComment());
        return Response.success(commentResponse);
    }

    @ApiOperation(value = "Comment 수정 기능", notes = "수정할 Comment의 내용을 입력해주세요")
    @PutMapping("/{postId}/comments/{id}") // comment update
    public Response modifyComment(Authentication authentication, @PathVariable Long postId, @PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        String userName = authentication.getName();
        CommentResponse commentResponse = commentService.updateComment(postId, userName, commentRequest, id);
        return Response.success(commentResponse);
    }

    @ApiOperation(value = "Comment 삭제 기능", notes = "삭제할 Comment의 Id를 입력해주세요")
    @DeleteMapping("/{postId}/comments/{id}") // commentdelete
    public Response deleteComment(@PathVariable Long commentId, Authentication authentication) {

        CommentDto deletedComment = commentService.deleteComment(commentId, authentication.getName(), authentication.getAuthorities());

        return Response.success(new CommentSimpleResponse("SUCCESS", deletedComment.getId()));
    }
}
