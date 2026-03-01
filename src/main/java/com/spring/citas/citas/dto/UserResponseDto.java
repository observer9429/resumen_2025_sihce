package com.spring.citas.citas.dto;

import java.util.Set;

public class UserResponseDto {

    private Long id;
    private String username;
    private boolean enabled;
    private Set<String> roles;

    public UserResponseDto(Long id, String username,
                           boolean enabled, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<String> getRoles() {
        return roles;
    }
}