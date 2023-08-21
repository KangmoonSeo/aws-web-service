package com.example.webservice.web;

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
    public HelloResponseDto helloDto(@RequestParam String name, @RequestParam Integer amount) {
        amount *= 2;
        return new HelloResponseDto(name, amount);
    }
}
