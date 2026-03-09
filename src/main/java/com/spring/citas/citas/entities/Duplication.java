package com.spring.citas.citas.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="duplicados_2025")
public class Duplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long codEess;
    private String nombreEess;
    private String descripcionDelServicio;
    private String profesionalDeLaSalud;
    private String consultorio;
    private String turno;
    private String fechaDeCita;
    private Long diaCita;
    private Long mesCita;
    private String mesAtencion;
    private Long anoCita; 
    private String horaInicialCita;
    private String tipoDeCupo;
    private String estadoDeLaCita;
    private String fechaDeCreacion;
    private Long diaCreacion;
    private Long mesCreacion;
    private Long anoCreacion;
    private String tipoDeDocumento;
    private String numeroDeDocumento;
    private String nombresDelPaciente;
    private String apellidoPaternoDelPaciente;
    private String apellidoMaternoDelPaciente;
    private String celular;
    private String genero;
    private Long personalQueCita;
    private String usuarioCc;

    public Duplication() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCodEess() { return codEess; }
    public void setCodEess(Long codEess) { this.codEess = codEess; }

    public String getNombreEess() { return nombreEess; }
    public void setNombreEess(String nombreEess) { this.nombreEess = nombreEess; }

    public String getDescripcionDelServicio() { return descripcionDelServicio; }
    public void setDescripcionDelServicio(String descripcionDelServicio) { this.descripcionDelServicio = descripcionDelServicio; }

    public String getProfesionalDeLaSalud() { return profesionalDeLaSalud; }
    public void setProfesionalDeLaSalud(String profesionalDeLaSalud) { this.profesionalDeLaSalud = profesionalDeLaSalud; }

    public String getConsultorio() { return consultorio; }
    public void setConsultorio(String consultorio) { this.consultorio = consultorio; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getFechaDeCita() { return fechaDeCita; }
    public void setFechaDeCita(String fechaDeCita) { this.fechaDeCita = fechaDeCita; }

    public Long getDiaCita() { return diaCita; }
    public void setDiaCita(Long diaCita) { this.diaCita = diaCita; }

    public Long getMesCita() { return mesCita; }
    public void setMesCita(Long mesCita) { this.mesCita = mesCita; }

    public String getMesAtencion() { return mesAtencion; }
    public void setMesAtencion(String mesAtencion) { this.mesAtencion = mesAtencion; }

    public Long getAnoCita() { return anoCita; }
    public void setAnoCita(Long anoCita) { this.anoCita = anoCita; }

    public String getHoraInicialCita() { return horaInicialCita; }
    public void setHoraInicialCita(String horaInicialCita) { this.horaInicialCita = horaInicialCita; }

    public String getTipoDeCupo() { return tipoDeCupo; }
    public void setTipoDeCupo(String tipoDeCupo) { this.tipoDeCupo = tipoDeCupo; }

    public String getEstadoDeLaCita() { return estadoDeLaCita; }
    public void setEstadoDeLaCita(String estadoDeLaCita) { this.estadoDeLaCita = estadoDeLaCita; }

    public String getFechaDeCreacion() { return fechaDeCreacion; }
    public void setFechaDeCreacion(String fechaDeCreacion) { this.fechaDeCreacion = fechaDeCreacion; }

    public Long getDiaCreacion() { return diaCreacion; }
    public void setDiaCreacion(Long diaCreacion) { this.diaCreacion = diaCreacion; }

    public Long getMesCreacion() { return mesCreacion; }
    public void setMesCreacion(Long mesCreacion) { this.mesCreacion = mesCreacion; }

    public Long getAnoCreacion() { return anoCreacion; }
    public void setAnoCreacion(Long anoCreacion) { this.anoCreacion = anoCreacion; }

    public String getTipoDeDocumento() { return tipoDeDocumento; }
    public void setTipoDeDocumento(String tipoDeDocumento) { this.tipoDeDocumento = tipoDeDocumento; }

    public String getNumeroDeDocumento() { return numeroDeDocumento; }
    public void setNumeroDeDocumento(String numeroDeDocumento) { this.numeroDeDocumento = numeroDeDocumento; }

    public String getNombresDelPaciente() { return nombresDelPaciente; }
    public void setNombresDelPaciente(String nombresDelPaciente) { this.nombresDelPaciente = nombresDelPaciente; }

    public String getApellidoPaternoDelPaciente() { return apellidoPaternoDelPaciente; }
    public void setApellidoPaternoDelPaciente(String apellidoPaternoDelPaciente) { this.apellidoPaternoDelPaciente = apellidoPaternoDelPaciente; }

    public String getApellidoMaternoDelPaciente() { return apellidoMaternoDelPaciente; }
    public void setApellidoMaternoDelPaciente(String apellidoMaternoDelPaciente) { this.apellidoMaternoDelPaciente = apellidoMaternoDelPaciente; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Long getPersonalQueCita() { return personalQueCita; }
    public void setPersonalQueCita(Long personalQueCita) { this.personalQueCita = personalQueCita; }

    public String getUsuarioCc() { return usuarioCc; }
    public void setUsuarioCc(String usuarioCc) { this.usuarioCc = usuarioCc; }
}
