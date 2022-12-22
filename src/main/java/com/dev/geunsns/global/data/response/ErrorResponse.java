package com.dev.geunsns.global.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse<T> {

    /**
     * Error를 감싸서 Response의 Body에 전달
     */
    private T errorCode;
    private String errorMessage;
}