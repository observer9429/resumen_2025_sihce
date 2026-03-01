package com.spring.citas.citas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.citas.citas.dto.UserRequestDto;
import com.spring.citas.citas.dto.UserResponseDto;
import com.spring.citas.citas.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 CREAR USUARIO
    @PostMapping
    public ResponseEntity<UserResponseDto> create(
            @Valid @RequestBody UserRequestDto dto) {

        UserResponseDto createdUser = userService.create(dto);

        return new ResponseEntity<>(
                createdUser,
                HttpStatus.CREATED
        );
    }

    // 🔹 OBTENER USUARIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getById(id)
        );
    }
}