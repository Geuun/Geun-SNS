package com.dev.geunsns.apps.post.data.dto.post;

import com.dev.geunsns.apps.post.data.entity.PostEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    private Long id;
    private String title;
    private String body;

    // User
    private String userName;

    // BaseEntity / BaseTimeEntity
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private boolean isDeleted;

    private Integer postLikeCount;

//    private Integer status; // 게시물 상태 (삭제 유무 0, 1)

    @Builder
    public PostDto(Long id, String title, String body, String userName, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, boolean isDeleted, Integer postLikeCount) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userName = userName;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.isDeleted = isDeleted;
        this.postLikeCount = postLikeCount;
    }


    //Entity -> Dto (양방향 무한참조 방지)
    public static PostDto toDto(PostEntity postEntity) {
        return PostDto.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .userName(postEntity.getUser().getUserName())
                .createdAt(postEntity.getCreatedAt())
                .createdBy(postEntity.getCreatedBy())
                .modifiedAt(postEntity.getLastModifiedAt())
                .modifiedBy(postEntity.getLastModifiedBy())
                .isDeleted(postEntity.isDeleted())
                .postLikeCount(postEntity.getPostLikeCount())
                .build();
    }

    // Entity List -> Dto List (양방향 무한참조 방지)
    public static Page<PostDto> toDtoList(Page<PostEntity> postEntities) {
        return postEntities.map(PostDto::toDto);
    }
}
