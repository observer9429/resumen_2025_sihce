package com.spring.citas.citas.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "resumen_2025")
public class Resumen2025 {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre_eess")
    private String nombre_eess;

    @Column(name = "mes_atencion")
    private String mes_atencion;

    @Column(name = "total_mes")
    private int total_mes;

    @Column(name = "descripcion_del_servicio")
    private String descripcion_del_servicio;

    @Column(name = "total_servicio")
    private int total_servicio;

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

    public int getTotal_mes() {
        return total_mes;
    }

    public void setTotal_mes(int total_mes) {
        this.total_mes = total_mes;
    }

    public String getDescripcion_del_servicio() {
        return descripcion_del_servicio;
    }

    public void setDescripcion_del_servicio(String descripcion_del_servicio) {
        this.descripcion_del_servicio = descripcion_del_servicio;
    }

    public int getTotal_servicio() {
        return total_servicio;
    }

    public void setTotal_servicio(int total_servicio) {
        this.total_servicio = total_servicio;
    }

    
}
