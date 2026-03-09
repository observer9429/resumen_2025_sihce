package com.spring.citas.citas.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.spring.citas.citas.dto.UserRequestDto;
import com.spring.citas.citas.dto.UserResponseDto;
import com.spring.citas.citas.services.UserService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@PreAuthorize("hasRole('MASTER')")
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

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> search(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role
    ) {
        return ResponseEntity.ok(
                userService.search(username, email, role)
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<List<UserResponseDto>> getAll(Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                userService.getAllExcept(username)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto dto) {

        return ResponseEntity.ok(
                userService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/page")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Page<UserResponseDto>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                userService.getPage(page, size)
        );
    }

}