package com.dev.geunsns.global.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.geunsns.global.config.security.encrypter.EncrypterConfig;
import com.dev.geunsns.global.service.RootService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RootController.class)
class RootControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RootService rootService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("자릿수 합 - Test")
    void sumOfDigit() throws Exception {
        // when 여기에서는 Service는 검증 대상이 아님
        when(rootService.sumOfDigit(687)) // 값을 지정할 수 있으나 의미가 없다.
                                          .thenReturn(21);

        mockMvc.perform(get("/api/v1/hello/687").with(csrf()))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().string("21"));
    }
}