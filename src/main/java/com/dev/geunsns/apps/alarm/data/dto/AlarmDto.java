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
    private String text;
    private Long targetId;
    private Long fromUserId;
    private LocalDateTime createdAt;
    private Boolean deletedAt;
    private AlarmType alarmType;

    @Builder
    public AlarmDto(Long id, String text, Long targetId, Long fromUserId, LocalDateTime createdAt, Boolean deletedAt, AlarmType alarmType) {
        this.id = id;
        this.text = text;
        this.targetId = targetId;
        this.fromUserId = fromUserId;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.alarmType = alarmType;
    }


    public static AlarmDto toDto(AlarmEntity alarmEntity) {
        return AlarmDto.builder()
                .id(alarmEntity.getId())
                .text(alarmEntity.getText())
                .targetId(alarmEntity.getTargetId())
                .fromUserId(alarmEntity.getFromUserId())
                .createdAt(alarmEntity.getCreatedAt())
                .deletedAt(alarmEntity.getIsDeleted())
                .alarmType(alarmEntity.getAlarmType())
                .build();
    }

    public static Page<AlarmDto> toDtoList(Page<AlarmEntity> alarmEntityList) {
        return alarmEntityList.map(AlarmDto::toDto);
    }
}