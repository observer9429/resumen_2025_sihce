package com.spring.citas.citas.dto;

public class DuplicationDto {

    private Long id;
    private String nombreEess;
    private String descripcionDelServicio;
    private String fechaDeCita;
    private String estadoDeLaCita;
    private String fechaDeCreacion;
    private String numeroDeDocumento;
    private String nombresDelPaciente;
    private String apellidoPaternoDelPaciente;
    private String usuarioCc;

    public DuplicationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEess() {
        return nombreEess;
    }

    public void setNombreEess(String nombreEess) {
        this.nombreEess = nombreEess;
    }

    public String getDescripcionDelServicio() {
        return descripcionDelServicio;
    }

    public void setDescripcionDelServicio(String descripcionDelServicio) {
        this.descripcionDelServicio = descripcionDelServicio;
    }

    public String getFechaDeCita() {
        return fechaDeCita;
    }

    public void setFechaDeCita(String fechaDeCita) {
        this.fechaDeCita = fechaDeCita;
    }

    public String getEstadoDeLaCita() {
        return estadoDeLaCita;
    }

    public void setEstadoDeLaCita(String estadoDeLaCita) {
        this.estadoDeLaCita = estadoDeLaCita;
    }

    public String getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(String fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }

    public String getNombresDelPaciente() {
        return nombresDelPaciente;
    }

    public void setNombresDelPaciente(String nombresDelPaciente) {
        this.nombresDelPaciente = nombresDelPaciente;
    }

    public String getApellidoPaternoDelPaciente() {
        return apellidoPaternoDelPaciente;
    }

    public void setApellidoPaternoDelPaciente(String apellidoPaternoDelPaciente) {
        this.apellidoPaternoDelPaciente = apellidoPaternoDelPaciente;
    }

    public String getUsuarioCc() {
        return usuarioCc;
    }

    public void setUsuarioCc(String usuarioCc) {
        this.usuarioCc = usuarioCc;
    }
}
