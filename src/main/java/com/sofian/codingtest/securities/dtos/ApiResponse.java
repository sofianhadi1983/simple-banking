package com.sofian.codingtest.securities.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponse {
    private final Boolean success;
    private final String message;
}
