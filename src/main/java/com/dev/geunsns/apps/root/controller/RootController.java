package com.dev.geunsns.apps.root.controller;


import com.dev.geunsns.apps.root.service.RootService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RootController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final RootService rootService;

	@GetMapping("/hello")
	public String hello() {
		logger.info("hello");
		return "김재근";
	}

	@GetMapping("/hello/{num}")
	public Integer sum(@PathVariable Integer num){
		logger.info("sumOfDigit");
		return rootService.sumOfDigit(num);
	}
}
