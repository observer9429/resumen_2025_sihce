package com.spring.citas.citas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.citas.citas.entities.Role;



public interface RoleRepository extends JpaRepository<Role,Long>{

    Optional<Role> findByName(String name);
}
