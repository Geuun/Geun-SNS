package com.dev.geunsns.apps.alarm.data.entity;

import com.dev.geunsns.apps.alarm.data.model.AlarmType;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.jpaauditing.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "alarm")
public class AlarmEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람 내용
    private String text;

    // 알람 받을 대상
    private Long targetId;

    // 알람 보내는 사람
    private Long fromUserId;
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public AlarmEntity(Long id, String text, Long targetId, Long fromUserId, Boolean isDeleted, AlarmType alarmType, UserEntity user) {
        this.id = id;
        this.text = text;
        this.targetId = targetId;
        this.fromUserId = fromUserId;
        this.isDeleted = isDeleted;
        this.alarmType = alarmType;
        this.user = user;
    }
}
