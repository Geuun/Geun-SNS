package com.dev.geunsns.apps.post.data.dto.post.response;

import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostGetDetailResponse {

    private Long id;
    private String title;
    private String body;
    private String userName;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;

    @Builder
    public PostGetDetailResponse(Long id, String title, String body, String userName, LocalDateTime createdAt, String createdBy, LocalDateTime lastModifiedAt, String lastModifiedBy) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userName = userName;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
    }

    public static PostGetDetailResponse toResponse(PostDto postDto) {
        return new PostGetDetailResponse(
                postDto.getId(),
                postDto.getTitle(),
                postDto.getBody(),
                postDto.getUserName(),
                postDto.getCreatedAt(),
                postDto.getCreatedBy(),
                postDto.getModifiedAt(),
                postDto.getModifiedBy()
        );
    }
}
