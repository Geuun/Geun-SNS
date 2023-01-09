package com.dev.geunsns.apps.post.data.dto.postlike.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeRequest {

    private Long id;

    @Builder
    public PostLikeRequest(Long id) {
        this.id = id;
    }
}
