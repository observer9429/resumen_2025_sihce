package com.spring.citas.citas.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Table(name = "resumen_inc")
@Entity
@Table(name = "resumen_inc")
public class Resumen_2025_v02 {
    
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_eess")
    private String nombre_eess;

    @Column(name = "mes_atencion")
    private String mes_atencion;

    @Column(name = "total_mes_citados")
    private int total_mes_citados;

    @Column(name = "total_mes_atendidos")
    private int total_mes_atendidos;
    
    @Column(name = "descripcion_del_servicio")
    private String descripcion_del_servicio;

    @Column(name = "total_servicio_citados")
    private int total_servicio_citados;

    @Column(name = "total_servicio_atendidos")
    private int total_servicio_atendidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_eess() {
        return nombre_eess;
    }

    public void setNombre_eess(String nombre_eess) {
        this.nombre_eess = nombre_eess;
    }

    public String getMes_atencion() {
        return mes_atencion;
    }

    public void setMes_atencion(String mes_atencion) {
        this.mes_atencion = mes_atencion;
    }

    public int getTotal_mes_citados() {
        return total_mes_citados;
    }

    public void setTotal_mes_citados(int total_mes_citados) {
        this.total_mes_citados = total_mes_citados;
    }

    public int getTotal_mes_atendidos() {
        return total_mes_atendidos;
    }

    public void setTotal_mes_atendidos(int total_mes_atendidos) {
        this.total_mes_atendidos = total_mes_atendidos;
    }

    public String getDescripcion_del_servicio() {
        return descripcion_del_servicio;
    }

    public void setDescripcion_del_servicio(String descripcion_del_servicio) {
        this.descripcion_del_servicio = descripcion_del_servicio;
    }

    public int getTotal_servicio_citados() {
        return total_servicio_citados;
    }

    public void setTotal_servicio_citados(int total_servicio_citados) {
        this.total_servicio_citados = total_servicio_citados;
    }

    public int getTotal_servicio_atendidos() {
        return total_servicio_atendidos;
    }

    public void setTotal_servicio_atendidos(int total_servicio_atendidos) {
        this.total_servicio_atendidos = total_servicio_atendidos;
    }

    
}
