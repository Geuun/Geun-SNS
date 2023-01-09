package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.service.MyFeedService;
import com.dev.geunsns.global.exception.response.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class MyFeedController {

    private final String baseLogging = "Post App's MyFeedController";
    private final MyFeedService myFeedService;

    @ApiOperation(value = "내 피드 조회", notes = "자신의 피드를 조회합니다.")
    @GetMapping("/my")
    public Response<Page<PostDto>> getMyFeed(Pageable pageable, Authentication authentication) {

        log.info(baseLogging);

        Page<PostDto> myFeed = myFeedService.getMyFeed(pageable, authentication.getName());

        return Response.success(myFeed);
    }
}
