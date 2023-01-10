package com.dev.geunsns.global.config.auditing;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    /**
     * 다른 곳에서 인스턴스 생성하지 못하게 추상클래스로 선언
     */

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime lastModifiedAt;

//	@PrePersist // Entity 생성되기 직전에 실행
//	public void onPrePersist() {
//		this.createdAt = SimpleDateFormat.);
//		this.lastModifiedAt = createdAt;
//	}
//
//	@PreUpdate // Entity 수정되기 직전에 실행
//	public void onPreUpdate() {
//		this.lastModifiedAt =
//	}
}
