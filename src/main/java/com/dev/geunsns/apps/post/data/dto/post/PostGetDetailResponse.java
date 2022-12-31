package com.dev.geunsns.apps.post.data.dto.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostGetDetailResponse {

    private Long id;
    private String title;
    private String body;
    private String userName;
    private String createdAt;
    private String lastModifiedAt;

    @Builder
    public PostGetDetailResponse(Long id, String title, String body, String userName, String createdAt, String lastModifiedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userName = userName;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
}
