package com.spring.citas.citas.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;

public class UserRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // opcional
    private Set<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}