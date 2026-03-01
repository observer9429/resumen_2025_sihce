package com.spring.citas.citas.controllers;

import jakarta.validation.Valid;

import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.spring.citas.citas.dto.LoginRequestDto;
import com.spring.citas.citas.dto.LoginResponseDto;
import com.spring.citas.citas.security.JwtUtil;
import com.spring.citas.citas.security.UserDetailsServiceImpl;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //su funcion es responder: ¿Estas credenciales son válidas o no?
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsServiceImpl userDetailsService,
            JwtUtil jwtUtil) {

        this.authenticationManager = authenticationManager;//llama a lUserDetailsService
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponseDto login(
            @Valid @RequestBody LoginRequestDto dto) {

                 System.out.println("🔥 ENTRÓ AL LOGIN 🔥");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(dto.getUsername());

        String token = jwtUtil.generateToken(userDetails);

        return new LoginResponseDto(
                token,
                userDetails.getUsername(),
                userDetails.getAuthorities()
                        .stream()
                        .map(a -> a.getAuthority())
                        .collect(Collectors.toSet())
        );
    }
}