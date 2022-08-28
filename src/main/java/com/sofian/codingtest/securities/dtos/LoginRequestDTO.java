package com.sofian.codingtest.securities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDTO {
    @NotBlank
    @JsonProperty("username_or_email")
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
