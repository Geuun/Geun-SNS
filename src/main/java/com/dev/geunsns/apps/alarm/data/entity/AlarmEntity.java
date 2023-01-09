package com.dev.geunsns.apps.alarm.data.entity;

import com.dev.geunsns.apps.alarm.data.model.AlarmType;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.auditing.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE alarm SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "alarm")
public class AlarmEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람 내용
    private String text;

    // target Post ID
    private Long targetId;

    // 알람 보내는 사람
    private Long fromUserId;
    private Boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public AlarmEntity(Long id, String text, Long targetId, Long fromUserId, AlarmType alarmType, UserEntity user) {
        this.id = id;
        this.text = text;
        this.targetId = targetId;
        this.fromUserId = fromUserId;
        this.isDeleted = false;
        this.alarmType = alarmType;
        this.user = user;
    }
}
