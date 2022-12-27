package com.dev.geunsns.apps.post.data.dto.comment;

import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSimpleResponse {
    private String message;
    private Long id;



    @Builder
    public CommentSimpleResponse(String message, Long id) {
        this.message = message;
        this.id = id;
    }
}

