package com.dev.geunsns.global.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("")
public class RootController {

    @GetMapping("/api/v1/hello")
    public String hello() {
        return "popin";
    }
}
