package com.dev.geunsns.global.service;

import com.dev.geunsns.apps.root.service.RootService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RootServiceTest {

    RootService rootService = new RootService();

    @Test
    @DisplayName("자릿 수 합 Service Test")
    void sumOfDigits() {

        assertEquals(21, rootService.sumOfDigit(687));
        assertEquals(22, rootService.sumOfDigit(787));
        assertEquals(0, rootService.sumOfDigit(0));
        assertEquals(8, rootService.sumOfDigit(224));

    }
}