package com.pozpl.neraannotator.user.api;

public class UserSaveResultDto {

    private boolean status;
    private String errorMessage;
    private UserIntDto user;

    private UserSaveResultDto(UserIntDto user) {
        this.user = user;
        this.status = true;
    }

    private UserSaveResultDto(String errorMessage) {
        this.errorMessage = errorMessage;
        this.status = false;
    }

    private UserSaveResultDto(){
    }

    public boolean isStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public UserIntDto getUser() {
        return user;
    }

    public static UserSaveResultDto success(UserIntDto user){
        return new UserSaveResultDto(user);
    }

    public static UserSaveResultDto error(final String error){
        return new UserSaveResultDto(error);
    }
}
