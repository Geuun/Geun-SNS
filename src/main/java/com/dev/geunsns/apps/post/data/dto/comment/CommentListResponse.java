package com.dev.geunsns.apps.post.data.dto.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentListResponse {

    private List<CommentResponse> content;
    private Pageable pageable;

    @Builder
    public CommentListResponse(List<CommentResponse> content, Pageable pageable) {
        this.content = content;
        this.pageable = pageable;
    }
}
