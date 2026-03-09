package com.spring.citas.citas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.citas.citas.entities.Duplication;

import java.util.List;

public interface DuplicationRepository extends JpaRepository<Duplication, Long> {

    //List<Duplication> findByMesCreacion(Long mesCreacion);

    List<Duplication> findByMesCreacionAndAnoCreacion(Long mesCreacion, Long anoCreacion);

}
