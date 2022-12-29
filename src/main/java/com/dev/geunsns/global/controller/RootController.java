package com.dev.geunsns.global.controller;


import com.dev.geunsns.global.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RootController {

	private final AlgorithmService algorithmService;

	@GetMapping("/hello")
	public String hello() {
		return "김재근";
	}

	@GetMapping("/hello/{num}")
	public Integer sum(@PathVariable Integer num){
		return algorithmService.SumOfDigit(num);
	}
}
