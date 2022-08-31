package com.sofian.codingtest.securities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtAuthenticationResponse {
    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("token_type")
    private String tokenType = "Bearer";
}
