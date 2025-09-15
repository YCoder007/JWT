package com.yash.login.JWT.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SignUpResponseDTO {

    private Long id;
    private String username;
}
