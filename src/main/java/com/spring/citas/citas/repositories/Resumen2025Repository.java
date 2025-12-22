package com.spring.citas.citas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.citas.citas.entities.Resumen2025;
import com.spring.citas.citas.entities.Resumen_2025_v02;
import com.spring.citas.citas.projections.ResumenCentroProjection;

import java.util.List;


public interface  Resumen2025Repository  extends JpaRepository<Resumen_2025_v02, Long>{
    
    //para atendidos
    @Query("SELECT r FROM Resumen_2025_v02 r WHERE r.mes_atencion IN :meses ORDER BY r.nombre_eess ASC, r.mes_atencion ASC")
    List<Resumen_2025_v02> findByMeses(@Param("meses") List<String> meses);

    //para detalles de cada centro
    @Query(value = """
        SELECT 
            nombre_eess,
            mes_atencion,
            total_mes_citados,
            total_mes_atendidos,
            descripcion_del_servicio,
            total_servicio_citados,
            total_servicio_atendidos
        FROM resumen_inc
        ORDER BY nombre_eess, mes_atencion
    """, nativeQuery = true)
    List<ResumenCentroProjection> findAllResumenProyeccion();


    //para el gráfico
    @Query(value = """
        SELECT 
            nombre_eess,
            mes_atencion,
            total_mes_citados,
            total_mes_atendidos
        FROM resumen_inc
        ORDER BY nombre_eess, mes_atencion
    """, nativeQuery = true)
    List<ResumenCentroProjection> findAllResumenGrafico();
  
}
