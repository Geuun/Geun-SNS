package com.dev.geunsns.apps.post.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostAppException extends RuntimeException {

    private PostAppErrorCode postAppErrorCode;
    private String postAppErrorMessage;

    // 메세지 입력안했을 때 enum메세지 반환
    public PostAppException(PostAppErrorCode postAppErrorCode) {
        this.postAppErrorCode = postAppErrorCode;
        this.postAppErrorMessage = postAppErrorCode.getErrorMessage();
    }
}
