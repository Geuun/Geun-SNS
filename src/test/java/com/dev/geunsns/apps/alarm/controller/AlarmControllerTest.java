package com.dev.geunsns.apps.alarm.controller;

import com.dev.geunsns.apps.alarm.data.dto.AlarmDto;
import com.dev.geunsns.apps.alarm.data.entity.AlarmEntity;
import com.dev.geunsns.apps.alarm.data.model.AlarmType;
import com.dev.geunsns.apps.alarm.exception.AlarmAppExection;
import com.dev.geunsns.apps.alarm.exception.AlarmErrorCode;
import com.dev.geunsns.apps.alarm.service.AlarmService;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.fixture.AlarmEntityFixture;
import com.dev.geunsns.fixture.PostEntityFixture;
import com.dev.geunsns.fixture.TestUserFixture;
import com.dev.geunsns.fixture.UserEntityFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlarmController.class)
class AlarmControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlarmService alarmService;

    @Test
    @WithMockUser
    @DisplayName("알람 목록 조회 성공 - Comment")
    void getAlarmList_Comment_Success() throws Exception {

        UserEntity user1 = UserEntityFixture.getUser("testUser1", "testPwd1");
        UserEntity user2 = UserEntityFixture.getUser("testUser2", "testPwd2");

        PostEntity post1 = PostEntityFixture.get(1L, "testTitle1", "testContent1", user1);
        PostEntity post2 = PostEntityFixture.get(2L, "testTitle2", "testContent2", user2);

        AlarmEntity commentAlarm1 = AlarmEntityFixture.getCommentAlarm(user2.getId(), post1.getId()); // user2가 user1의 post1에 댓글을 달았을 때
        AlarmEntity commentAlarm2 = AlarmEntityFixture.getCommentAlarm(user1.getId(), post2.getId()); // user1이 user2의 post2에 댓글을 달았을 때

        List<AlarmEntity> alarmEntities = new ArrayList<>();
        alarmEntities.add(commentAlarm1);
        alarmEntities.add(commentAlarm2);

        Page<AlarmEntity> alarmEntityPage = new PageImpl<>(alarmEntities);
        Page<AlarmDto> alarmDtoPage = AlarmDto.toListDto(alarmEntityPage);

        when(alarmService.getAlarmList(any(), any()))
                .thenReturn(alarmDtoPage);

        mockMvc.perform(get("/api/v1/alarms").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.content[0].id").exists())
                .andExpect(jsonPath("$.result.content[0].userId").exists())
                .andExpect(jsonPath("$.result.content[0].text").exists())
                .andExpect(jsonPath("$.result.content[0].targetId").exists())
                .andExpect(jsonPath("$.result.content[0].fromUserId").exists())
                .andExpect(jsonPath("$.result.content[0].alarmType").exists()
                );
    }

    @Test
    @WithMockUser
    @DisplayName("알람 목록 조회 성공 - Like")
    void getAlarmList_Like_Success() throws Exception {
        TestUserFixture.TestEntity testEntity = TestUserFixture.get();

        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());

        PostEntity post =
                PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        AlarmEntity commentAlarm1 =
                AlarmEntityFixture.getLikeAlarm(user.getId(), post.getId());
        AlarmEntity commentAlarm2 =
                AlarmEntityFixture.getLikeAlarm(user.getId(), post.getId());

        List<AlarmEntity> commentAlarms = new ArrayList<>();
        commentAlarms.add(commentAlarm1);
        commentAlarms.add(commentAlarm2);

        Page<AlarmEntity> alarmList = new PageImpl<>(commentAlarms);

        Page<AlarmDto> alarmDtoList = AlarmDto.toListDto(alarmList);

        when(alarmService.getAlarmList(any(), any()))
                .thenReturn(alarmDtoList);

        mockMvc.perform(get("/api/v1/alarms")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content[0].alarmType").exists())
                .andExpect(jsonPath("$.result.content[0].alarmType").value(AlarmType.NEW_LIKE_ON_POST.name())
                );


    }

    @Test
    @WithAnonymousUser
    @DisplayName("알람 목록 조회 실패 - 권한 없음")
    void getAlarmList_Fail() throws Exception {

        when(alarmService.getAlarmList(any(), any()))
                .thenThrow(new AlarmAppExection(AlarmErrorCode.INVALID_PERMISSION));

        mockMvc.perform(get("/api/v1/alarms")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized()
                );
    }
}