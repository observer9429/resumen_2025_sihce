package com.spring.citas.citas.projections;

public interface ResumenCentroProjection {

    String getNombre_eess();
    String getMes_atencion();

    Integer getTotal_mes_citados();
    Integer getTotal_mes_atendidos();
 
    String getDescripcion_del_servicio();

    Integer getTotal_servicio_citados();
    Integer getTotal_servicio_atendidos();
}


