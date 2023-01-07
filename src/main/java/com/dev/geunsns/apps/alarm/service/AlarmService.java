package com.dev.geunsns.apps.alarm.service;

import com.dev.geunsns.apps.alarm.data.dto.AlarmDto;
import com.dev.geunsns.apps.alarm.data.entity.AlarmEntity;
import com.dev.geunsns.apps.alarm.exception.AlarmAppExection;
import com.dev.geunsns.apps.alarm.exception.AlarmErrorCode;
import com.dev.geunsns.apps.alarm.repository.AlarmRepository;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public Page<AlarmDto> getAlarmList(Pageable pageable, Authentication authentication) {

        UserEntity user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AlarmAppExection(AlarmErrorCode.USER_NOT_FOUND));

        log.info("[Service Layer] Request userName : {}, userId : {}", user.getUserName(), user.getId());

        Page<AlarmEntity> alarmEntityList = alarmRepository.findAllByUserId(user.getId(), pageable);

        Page<AlarmDto> alarmDtoList = AlarmDto.toListDto(alarmEntityList);

        return alarmDtoList;
    }
}