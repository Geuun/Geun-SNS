package com.dev.geunsns.apps.alarm.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {
    NEW_COMMENT_ON_POST("New Commnet!"),
    NEW_LIKE_ON_POST("New Like!"),
    ;

    private final String alarmText;
}
