package com.pozpl.nerannotator.admin.impl;

import lombok.Data;

@Data
public class UserEditResultDto {

    private boolean status;
    private UserEditDto user;
    private String error;

    public UserEditResultDto(String error) {
        this.error = error;
        this.status = false;
    }

    public UserEditResultDto(UserEditDto user) {
        this.user = user;
        this.status = true;
    }

    public static UserEditResultDto of(final String error){
        return new UserEditResultDto(error);
    }

    public static UserEditResultDto of(final UserEditDto userEditDto) {
        return new UserEditResultDto(userEditDto);
    }
}
