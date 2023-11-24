package com.careerup.careerupspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSigninDTO {
    private String email;
    private String password;
}
