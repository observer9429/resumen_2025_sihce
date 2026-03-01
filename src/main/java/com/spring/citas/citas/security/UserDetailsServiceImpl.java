package com.spring.citas.citas.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import com.spring.citas.citas.repositories.UserRepository;
import com.spring.citas.citas.entities.User;

import java.util.stream.Collectors;

@Service //crea un bean
public class UserDetailsServiceImpl implements UserDetailsService {
    //Spring Security SIEMPRE busca esta interfaz. -> UserDetailsService
    //Cuando quieras autenticar un usuario, pregúntame a mí

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override //spring llama al metodo de abajo cuando: haces login, o cuando valida un JWT
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no existe"));

        //luego usamos un User de otra libreria, no es nuiestra entidad
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet())
        );
    }
}