package com.pozpl.neraannotator.user.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class UserIntDto {

    private Long id;

    private String username;

    private String firstName;
    private String lastName;

    private String email;

    @JsonIgnore
    private String password;

    private List<ERole> roles;

    public UserIntDto(Long id, String username, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserIntDto(Long id, String username, String firstName, String lastName, String email, String password, List<ERole> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<ERole> getRoles() {
        return roles;
    }

    public static UserIntDto of(final Long id,
                                final String username,
                                final String firstName,
                                final String lastName,
                                final String email,
                                final String password){
        return new UserIntDto(id, username, firstName, lastName, email, password);
    }

    public static UserIntDto of(final Long id,
                                final String username,
                                final String firstName,
                                final String lastName,
                                final String email,
                                final String password,
                                final List<ERole> roles){
        return new UserIntDto(id, username, firstName, lastName, email, password, roles);
    }
}
