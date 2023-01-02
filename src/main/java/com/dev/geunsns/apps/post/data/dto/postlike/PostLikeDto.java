package com.dev.geunsns.apps.post.data.dto.postlike;


import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.data.entity.PostLikeEntity;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeDto {

    private Long id;

    private UserEntity user;
    private PostEntity post;



    @Builder
    public PostLikeDto(Long id, UserEntity user, PostEntity post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }

    // Entity -> Dto
    public static PostLikeDto toDto(PostLikeEntity postLike) {
        return PostLikeDto.builder()
                .id(postLike.getId())
                .user(postLike.getUser())
                .post(postLike.getPost())
                .build();
    }
}
