package com.pozpl.nerannotator.auth.impl.user;

import lombok.Data;

@Data
public class AuthResultDto {

    private JwtResponse jwtResponse;
    private String error;

    public JwtResponse getJwtResponse() {
        return jwtResponse;
    }

    public String getError() {
        return error;
    }

    public AuthResultDto(JwtResponse jwtResponse) {
        this.jwtResponse = jwtResponse;
    }

    public AuthResultDto(String error) {
        this.error = error;
    }

    public static AuthResultDto ok(JwtResponse jwtResponse){
        return new AuthResultDto(jwtResponse);
    }

    public static AuthResultDto error(String error) {
        return new AuthResultDto(error);
    }
}
