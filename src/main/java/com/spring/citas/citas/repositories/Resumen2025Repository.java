package com.spring.citas.citas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.citas.citas.entities.Resumen2025;
import com.spring.citas.citas.projections.ResumenCentroProjection;

import java.util.List;


public interface  Resumen2025Repository  extends JpaRepository<Resumen2025, Long>{
    
    @Query("SELECT r FROM Resumen2025 r WHERE r.mes_atencion IN :meses ORDER BY r.nombre_eess ASC, r.mes_atencion ASC")
    List<Resumen2025> findByMeses(@Param("meses") List<String> meses);

    @Query(value = """
        SELECT 
            nombre_eess,
            mes_atencion,
            total_mes,
            descripcion_del_servicio,
            total_servicio
        FROM resumen_2025
        ORDER BY nombre_eess, mes_atencion
    """, nativeQuery = true)
    List<ResumenCentroProjection> findAllResumenProyeccion();


}
