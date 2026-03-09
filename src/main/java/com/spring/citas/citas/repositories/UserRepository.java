package com.spring.citas.citas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.citas.citas.entities.User;


public interface UserRepository  extends JpaRepository<User, Long>{

    //JpaRepository ya incluye soporte para Page, no tienes que agregar nada.


    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);


    //query flexible, permite enviar 1 o mas variables de busqueda
    @Query("""
        SELECT DISTINCT u
        FROM User u
        LEFT JOIN u.roles r
        WHERE (:username IS NULL OR u.username ILIKE CONCAT('%', CAST(:username AS text), '%'))
        AND (:email IS NULL OR u.email ILIKE CONCAT('%', CAST(:email AS text), '%'))
        AND (:role IS NULL OR r.name = :role)
    """)
    List<User> search(
            @Param("username") String username,
            @Param("email") String email,
            @Param("role") String role
    );

    // 🔹 LISTAR TODOS EXCEPTO UN USERNAME (para MASTER)
    @Query("""
        SELECT u
        FROM User u
        WHERE u.username <> :username
    """)
    List<User> findAllExceptUsername(@Param("username") String username);

    Page<User> findAllByUsernameNot(String username, Pageable pageable);

}
