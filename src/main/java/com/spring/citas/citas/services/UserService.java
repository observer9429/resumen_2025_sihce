package com.spring.citas.citas.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.spring.citas.citas.dto.UserRequestDto;
import com.spring.citas.citas.dto.UserResponseDto;
import com.spring.citas.citas.entities.Role;
import com.spring.citas.citas.entities.User;
import com.spring.citas.citas.repositories.RoleRepository;
import com.spring.citas.citas.repositories.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 CREAR
    public UserResponseDto create(UserRequestDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("El username ya existe");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);

        user.setRoles(resolveRoles(dto));

        return mapToResponse(userRepository.save(user));
    }

    // 🔹 OBTENER POR ID
    public UserResponseDto getById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));
    }

    // 🔹 BUSCAR (TU SEARCH SE RESPETA)
    public List<UserResponseDto> search(
            String username,
            String email,
            String role
    ) {

        return userRepository.search(username, email, role)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔹 LISTAR TODOS (EXCEPTO MASTER LOGUEADO)
    public List<UserResponseDto> getAllExcept(String username) {

        return userRepository.findAllExceptUsername(username)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔹 ACTUALIZAR
    public UserResponseDto update(Long id, UserRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (!user.getUsername().equals(dto.getUsername())
                && userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username ya en uso");
        }
        user.setUsername(dto.getUsername());

        if (!user.getEmail().equals(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email ya en uso");
        }
        user.setEmail(dto.getEmail());

        if (StringUtils.hasText(dto.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (!CollectionUtils.isEmpty(dto.getRoles())) {
            user.setRoles(resolveRoles(dto));
        }

        return mapToResponse(userRepository.save(user));
    }

    // 🔹 ELIMINAR
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no existe");
        }
        userRepository.deleteById(id);
    }

    // 🔹 ROLES CON JERARQUÍA
    private Set<Role> resolveRoles(UserRequestDto dto) {

        Set<Role> roles = new HashSet<>();

        // TODOS TIENEN USER
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER no existe"));
        roles.add(userRole);

        if (CollectionUtils.isEmpty(dto.getRoles())) {
            return roles;
        }

        if (dto.getRoles().contains("ROLE_ADMIN")) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN no existe"));
            roles.add(adminRole);
        }

        if (dto.getRoles().contains("ROLE_MASTER")) {

            Role masterRole = roleRepository.findByName("ROLE_MASTER")
                    .orElseThrow(() -> new RuntimeException("ROLE_MASTER no existe"));
            roles.add(masterRole);

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN no existe"));
            roles.add(adminRole);
        }

        return roles;
    }

    // 🔹 MAPPER
    private UserResponseDto mapToResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
        );
    }

    

    public Page<UserResponseDto> getPagePASADO(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public Page<UserResponseDto> getPage(int page, int size) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = auth.getName(); // 👈 username del master logueado

    Pageable pageable = PageRequest.of(page, size);

    return userRepository
            .findAllByUsernameNot(currentUsername, pageable)
            .map(this::mapToResponse);
}

}