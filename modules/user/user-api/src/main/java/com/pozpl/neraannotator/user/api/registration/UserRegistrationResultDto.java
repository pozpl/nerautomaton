package com.pozpl.neraannotator.user.api.registration;

public class UserRegistrationResultDto {

    private boolean status;
    private String errorMessage;

    public boolean isStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static UserRegistrationResultDto success(){
        final UserRegistrationResultDto  userRegistrationResultDto = new UserRegistrationResultDto();
        userRegistrationResultDto.status = true;

        return userRegistrationResultDto;
    }

    public static UserRegistrationResultDto error(final String errorMessage){
        final UserRegistrationResultDto  userRegistrationResultDto = new UserRegistrationResultDto();
        userRegistrationResultDto.status = false;
        userRegistrationResultDto.errorMessage = errorMessage;

        return userRegistrationResultDto;
    }
}
