package com.spring.citas.citas.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.spring.citas.citas.dto.UserRequestDto;
import com.spring.citas.citas.dto.UserResponseDto;
import com.spring.citas.citas.entities.Role;
import com.spring.citas.citas.entities.User;
import com.spring.citas.citas.repositories.RoleRepository;
import com.spring.citas.citas.repositories.UserRepository;



@Service
public class UserService {

    private final UserRepository userRepository;//acceso a tabla user
    private final RoleRepository roleRepository;//acceso a tabla roles
    private final PasswordEncoder passwordEncoder;//cifrar clave

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 CREAR USUARIO
    public UserResponseDto create(UserRequestDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();

        if (CollectionUtils.isEmpty(dto.getRoles())) {

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() ->
                            new RuntimeException("ROLE_USER no existe en BD"));
            roles.add(userRole);

        } else {

            for (String roleName : dto.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() ->
                                new RuntimeException("Rol no existe: " + roleName));
                roles.add(role);
            }
        }

        user.setRoles(roles);

        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    // 🔹 OBTENER USUARIO POR ID
    public UserResponseDto getById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no existe"));

        return mapToResponse(user);
    }

    // 🔹 MAPEADOR ENTITY → DTO
    private UserResponseDto mapToResponse(User user) {

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.isEnabled(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
        );
    }
}