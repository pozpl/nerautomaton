package com.pozpl.nerannotator.admin.impl;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEditDto {

    private Long id;
    private String username;
    private String email;

    private String resetPasswordCode;
}
