package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.postlike.response.PostLikeCountResponse;
import com.dev.geunsns.apps.post.data.dto.postlike.response.PostLikeResponse;
import com.dev.geunsns.apps.post.service.PostLikeService;
import com.dev.geunsns.global.exception.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "PostLike API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostLikeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostLikeService postLikeService;

    @ApiOperation(value = "Post Like 기능", notes = "좋아요 누를 Post의 id를 입력하세요.")
    @PostMapping("/{postId}/likes")
    public Response addLike(@PathVariable Long postId,
                            @ApiIgnore Authentication authentication) {

        logger.info("addLike");

        boolean checklikePost = postLikeService.addAndRemoveLike(postId, authentication.getName());

        if (!checklikePost) {
            return Response.success(new PostLikeResponse("SUCCESS - Removed Add Like", postId));
        }

        return Response.success(new PostLikeResponse("SUCCESS - Add Like", postId));
    }

    @ApiOperation(value = "Post Like Count 기능", notes = "좋아요 갯수를 조회할 Post의 id를 입력하세요.")
    @GetMapping("/{postId}/likes")
    public Response getLikeCount(@PathVariable Long postId) {

        Integer likeCount = postLikeService.getLikeCount(postId);

        return Response.success(new PostLikeCountResponse(postId, likeCount));
    }
}
