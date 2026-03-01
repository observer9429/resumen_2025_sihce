package com.spring.citas.citas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.citas.citas.entities.User;


public interface UserRepository  extends JpaRepository<User, Long>{

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
