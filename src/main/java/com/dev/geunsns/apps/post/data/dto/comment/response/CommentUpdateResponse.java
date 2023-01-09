package com.dev.geunsns.apps.post.data.dto.comment.response;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateResponse {
    private Long id;
    private String comment;
    private String userName;
    private Long postId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;

    @Builder
    public CommentUpdateResponse(Long id, String comment, String userName, Long postId, LocalDateTime createdAt, String createdBy, LocalDateTime lastModifiedAt, String lastModifiedBy) {
        this.id = id;
        this.comment = comment;
        this.userName = userName;
        this.postId = postId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
    }

    public static CommentUpdateResponse toResponse(CommentDto commentDto) {
        return new CommentUpdateResponse(
                commentDto.getCommentId(),
                commentDto.getComment(),
                commentDto.getUserName(),
                commentDto.getPostId(),
                commentDto.getCreatedAt(),
                commentDto.getCreatedBy(),
                commentDto.getLastModifiedAt(),
                commentDto.getLastModifiedBy()
        );
    }
}


/**
 * Json Response
 * {
 * 	"resultCode": "SUCCESS",
 * 	"result":{
 * 		"id": 4,
 * 		"comment": "modify comment",
 * 		"userName": "test",
 * 		"postId": 2,
 * 		"createdAt": "2022-12-20T16:15:04.270741",
 * 		"lastModifiedAt": "2022-12-23T16:15:04.270741"
 *                }
 * }
 */