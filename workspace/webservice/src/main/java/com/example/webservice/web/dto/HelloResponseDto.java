package com.example.webservice.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto { // Data Transfer Object
    private final String name;
    private final int amount;

}
