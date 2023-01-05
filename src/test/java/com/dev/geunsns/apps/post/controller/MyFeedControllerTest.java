package com.dev.geunsns.apps.post.controller;

import com.dev.geunsns.apps.post.service.MyFeedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyFeedController.class)
class MyFeedControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MyFeedService myFeedService;

    @Autowired
    ObjectMapper objectMapper;

//    @Test
//    @DisplayName("내 피드 조회 성공")
//    @WithMockUser
//    void getMyFeed_Success() throws Exception {
//
//        when(myFeedService.getMyFeed(any(), any()))
//                .thenReturn(Page.empty());
//
//        mockMvc.perform(get("/api/v1/posts/my")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }
}