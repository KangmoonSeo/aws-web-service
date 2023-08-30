package com.example.webservice.controller;

import com.example.webservice.dto.HelloRequestDto;
import com.example.webservice.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hello")
public class HelloController {


    @GetMapping
    public String hello() {
        return "hello";
    }

    @RequestMapping("/dto")
    public HelloResponseDto helloDto(@RequestBody HelloRequestDto requestDto) {

        return new HelloResponseDto(requestDto.getName(), requestDto.getAmount() * 2);
    }
}
