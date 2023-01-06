package com.dev.geunsns.fixture;

import com.dev.geunsns.apps.alarm.data.entity.AlarmEntity;
import com.dev.geunsns.apps.alarm.data.model.AlarmType;

public class AlarmEntityFixture {
    public static AlarmEntity getCommentAlarm(Long fromUserId, Long targetId) {
        return AlarmEntity.builder()
                .alarmType(AlarmType.NEW_COMMENT_ON_POST)
                .fromUserId(fromUserId)
                .targetId(targetId)
                .text("New Comment!")
                .build();
    }

    public static AlarmEntity getLikeAlarm(Long fromUserId, Long targetId) {
        return AlarmEntity.builder()
                .alarmType(AlarmType.NEW_LIKE_ON_POST)
                .fromUserId(fromUserId)
                .targetId(targetId)
                .text("New Like!")
                .build();
    }

}

