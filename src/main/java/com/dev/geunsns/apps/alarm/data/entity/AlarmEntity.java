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

    private String text;
    private Long targetId;
    private Long fromUserId;
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public AlarmEntity(Long id, String text, Long targetId, Long fromUserId, LocalDateTime deletedAt, AlarmType alarmType, UserEntity userEntity) {
        this.id = id;
        this.text = text;
        this.targetId = targetId;
        this.fromUserId = fromUserId;
        this.deletedAt = deletedAt;
        this.alarmType = alarmType;
        this.userEntity = userEntity;
    }
}
