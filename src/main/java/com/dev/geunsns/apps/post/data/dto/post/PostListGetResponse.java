package com.dev.geunsns.apps.post.data.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListGetResponse {

    private List<PostGetDetailResponse> content;
    private Pageable pageable;

    @Builder
    public PostListGetResponse(List<PostGetDetailResponse> content, Pageable pageable) {
        this.content = content;
        this.pageable = pageable;
    }
}
