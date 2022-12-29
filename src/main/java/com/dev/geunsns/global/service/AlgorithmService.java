package com.dev.geunsns.global.service;


import org.springframework.stereotype.Service;

@Service
public class AlgorithmService {

    public Integer SumOfDigit(Integer num) {
        int sum = 0;

        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }
}
