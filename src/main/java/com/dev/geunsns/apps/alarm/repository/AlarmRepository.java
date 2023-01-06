package com.dev.geunsns.apps.alarm.repository;

import com.dev.geunsns.apps.alarm.data.entity.AlarmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

    Page<AlarmEntity> findAllByUserId(Long userId, Pageable pageable);
}
