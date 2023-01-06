package com.dev.geunsns.apps.alarm.controller;

import com.dev.geunsns.apps.alarm.data.dto.AlarmDto;
import com.dev.geunsns.apps.alarm.data.entity.AlarmEntity;
import com.dev.geunsns.apps.alarm.service.AlarmService;
import com.dev.geunsns.global.data.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public Response getAlarmList(Pageable pageable, Authentication authentication) {

        Page<AlarmDto> alarmDtoList = alarmService.getAlarmList(pageable, authentication);

        return Response.success(alarmDtoList);
    }
}
