package com.dev.geunsns.apps.post.data.dto.comment.response;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentListResponse {
    private List<CommentDto> content;
    private Pageable pageable;

    @Builder
    public CommentListResponse(List<CommentDto> content, Pageable pageable) {
        this.content = content;
        this.pageable = pageable;
    }
}
