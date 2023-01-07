package com.dev.geunsns.apps.alarm.data.dto;

import com.dev.geunsns.apps.alarm.data.entity.AlarmEntity;
import com.dev.geunsns.apps.alarm.data.model.AlarmType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmDto {

    private Long id;
    private Long userId;
    private String text;
    private Long targetId;
    private Long fromUserId;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private AlarmType alarmType;

    @Builder
    public AlarmDto(Long id, Long userId, String text, Long targetId, Long fromUserId, LocalDateTime createdAt, Boolean isDeleted, AlarmType alarmType) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.targetId = targetId;
        this.fromUserId = fromUserId;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.alarmType = alarmType;
    }


    public static AlarmDto toDto(AlarmEntity alarmEntity) {
        return AlarmDto.builder()
                .id(alarmEntity.getId())
                .text(alarmEntity.getText())
                .userId(alarmEntity.getUser().getId())
                .targetId(alarmEntity.getTargetId())
                .fromUserId(alarmEntity.getFromUserId())
                .createdAt(alarmEntity.getCreatedAt())
                .isDeleted(alarmEntity.getIsDeleted())
                .alarmType(alarmEntity.getAlarmType())
                .build();
    }

    public static Page<AlarmDto> toListDto(Page<AlarmEntity> alarmEntityList) {

        Page<AlarmDto> postDtoList = alarmEntityList.map(postEntity -> AlarmDto.builder()
                .id(postEntity.getId())
                .text(postEntity.getText())
                .userId(postEntity.getUser().getId())
                .targetId(postEntity.getTargetId())
                .fromUserId(postEntity.getFromUserId())
                .createdAt(postEntity.getCreatedAt())
                .isDeleted(postEntity.getIsDeleted())
                .alarmType(postEntity.getAlarmType())
                .build());

        return postDtoList;
    }
}