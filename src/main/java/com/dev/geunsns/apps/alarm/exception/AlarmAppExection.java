package com.dev.geunsns.apps.alarm.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmAppExection extends RuntimeException {

    private AlarmErrorCode alarmErrorCode;
    private String alarmErrorMessage;

    @Builder
    public AlarmAppExection(AlarmErrorCode alarmErrorCode) {
        this.alarmErrorCode = alarmErrorCode;
        this.alarmErrorMessage = alarmErrorCode.getErrorMessage();
    }
}
