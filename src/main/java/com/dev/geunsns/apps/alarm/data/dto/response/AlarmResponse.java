package com.dev.geunsns.apps.alarm.data.dto.response;


import com.dev.geunsns.apps.alarm.data.dto.AlarmDto;
import com.dev.geunsns.apps.alarm.data.model.AlarmType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmResponse {

    private Long id;
    private String text;
    private Long targetId;
    private Long fromUserId;
    private AlarmType alarmType;
    private LocalDateTime createdAt;

    @Builder
    public AlarmResponse(Long id, String text, Long targetId, Long fromUserId, AlarmType alarmType, LocalDateTime createdAt) {
        this.id = id;
        this.text = text;
        this.targetId = targetId;
        this.fromUserId = fromUserId;
        this.alarmType = alarmType;
        this.createdAt = createdAt;
    }

    public static AlarmResponse toResponse(AlarmDto alarmDto) {
        return AlarmResponse.builder()
                .id(alarmDto.getId())
                .text(alarmDto.getText())
                .targetId(alarmDto.getTargetId())
                .fromUserId(alarmDto.getFromUserId())
                .alarmType(alarmDto.getAlarmType())
                .createdAt(alarmDto.getCreatedAt())
                .build();
    }
}
