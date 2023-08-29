package com.example.webservice.web.controller;

import com.example.webservice.web.dto.HelloRequestDto;
import com.example.webservice.web.dto.HelloResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
