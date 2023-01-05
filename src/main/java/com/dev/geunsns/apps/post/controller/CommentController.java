package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.comment.*;
import com.dev.geunsns.apps.post.data.dto.comment.request.CommentAddRequest;
import com.dev.geunsns.apps.post.data.dto.comment.response.*;
import com.dev.geunsns.apps.post.data.dto.comment.request.CommentUpdateRequest;
import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import com.dev.geunsns.apps.post.service.CommentService;
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
    /**
     * Entity -> Dto(toDto) -> Response(toResponse)
     */

    private final String routePath = "/api/v1/posts/";

    private final CommentService commentService;

    @ApiOperation(value = "Comment 조회 기능", notes = "조회할 Comment의 Id를 입력해주세요.")
    @GetMapping("/comments/{id}")
    public Response getComment(@PathVariable Long id, Authentication authentication) {

        CommentDto comment = commentService.getComment(id);

        CommentGetResponse commentGetResponse = CommentGetResponse.toResponse(comment);

        return Response.success(commentGetResponse);
    }


    @ApiOperation(value = "Comment List 조회 기능", notes = "Comment의 List(size = 10)를 조회합니다.")
    @GetMapping("/{postId}/comments")
    public Response<Page<CommentDto>> getCommentList(
            @PageableDefault(size = 10) @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long postId) {

        Page<CommentDto> commentDtos = commentService.getComments(postId, pageable);

        CommentListResponse commentListResponse = new CommentListResponse(commentDtos.getContent(), pageable);

        return Response.success(commentListResponse);
    }

    @ApiOperation(value = "Comment 작성 기능", notes = "추가할 Post의 postId와 Comment의 내용을 입력해주세요")
    @PostMapping("/{postId}/comments") // comment add
    public Response addComment(@RequestBody CommentAddRequest commentAddRequest, @PathVariable Long postId, Authentication authentication) {

        String userName = authentication.getName();

        CommentDto commentDto = commentService.addComment(postId, userName, commentAddRequest);

        CommentResponse commentResponse = CommentResponse.toResponse(commentDto);

        return Response.success(commentResponse);
    }

    @ApiOperation(value = "Comment 수정 기능", notes = "수정할 Comment의 내용을 입력해주세요")
    @PutMapping("/{postId}/comments/{commentId}") // comment update
    public Response modifyComment(@RequestBody CommentUpdateRequest commentUpdateRequest, @PathVariable Long commentId, Authentication authentication) {
        String userName = authentication.getName();
        CommentDto commentDto = commentService.updateComment(userName, commentUpdateRequest, commentId);

        CommentUpdateResponse commentUpdateResponse = CommentUpdateResponse.toResponse(commentDto);

        return Response.success(commentUpdateResponse);
    }

    @ApiOperation(value = "Comment 삭제 기능", notes = "삭제할 Comment의 Id를 입력해주세요")
    @DeleteMapping("/{postId}/comments/{commentId}") // commentdelete
    public Response deleteComment(@PathVariable Long commentId, Authentication authentication) {

        CommentDto deletedComment = commentService.deleteComment(commentId, authentication.getName(), authentication.getAuthorities());

        CommentDeleteResponse commentDeleteResponse = CommentDeleteResponse.toResponse(deletedComment);

        return Response.success(commentDeleteResponse);
    }
}
