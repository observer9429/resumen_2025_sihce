package com.spring.citas.citas.dto;

import java.util.Set;

public class LoginResponseDto {

    private String token;
    private String username;
    private Set<String> roles;

    public LoginResponseDto(String token, String username, Set<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRoles() {
        return roles;
    }
}