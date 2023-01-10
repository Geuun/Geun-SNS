package com.dev.geunsns.apps.alarm.controller;

import com.dev.geunsns.apps.alarm.data.dto.AlarmDto;
import com.dev.geunsns.apps.alarm.data.dto.response.AlarmResponse;
import com.dev.geunsns.apps.alarm.service.AlarmService;
import com.dev.geunsns.global.exception.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Alarm API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @ApiOperation(value = "Alarm 기능", notes = "유저의 게시물에 등록된 알람을 조회합니다.")
    @GetMapping
    public Response getAlarmList(@PageableDefault(size = 20) @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                 @ApiIgnore Authentication authentication) {

        log.info("Request userName : {}", authentication.getName());

        Page<AlarmDto> getAlarmList = alarmService.getAlarmList(pageable, authentication);

        return Response.success(new AlarmResponse("GET ALARM LIST SUCCESS", getAlarmList.getContent(), pageable));
    }
}
