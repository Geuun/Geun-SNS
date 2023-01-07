package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.postlike.response.PostLikeCountResponse;
import com.dev.geunsns.apps.post.data.dto.postlike.response.PostLikeResponse;
import com.dev.geunsns.apps.post.service.PostLikeService;
import com.dev.geunsns.global.data.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/likes")
    public Response addLike(@PathVariable Long postId, Authentication authentication) {

        boolean checklikePost = postLikeService.addAndRemoveLike(postId, authentication.getName());

        if (!checklikePost) {
            return Response.success(new PostLikeResponse("SUCCESS - Removed Add Like", postId));
        }

        return Response.success(new PostLikeResponse("SUCCESS - Add Like", postId));
    }

    @GetMapping("/{postId}/likes")
    public Response getLikeCount(@PathVariable Long postId, Authentication authentication) {

        Integer likeCount = postLikeService.getLikeCount(postId);

        return Response.success(new PostLikeCountResponse(postId, likeCount));
    }
}
