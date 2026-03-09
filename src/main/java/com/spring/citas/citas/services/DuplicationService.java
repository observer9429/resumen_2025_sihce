package com.spring.citas.citas.services;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.spring.citas.citas.dto.DuplicationDto;
import com.spring.citas.citas.dto.DuplicationFullDto;
import com.spring.citas.citas.entities.Duplication;
import com.spring.citas.citas.repositories.DuplicationRepository;

import java.util.List;
import java.util.stream.Collectors;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class DuplicationService {

    private final DuplicationRepository duplicationRepository;

    public DuplicationService(DuplicationRepository duplicationRepository) {
        this.duplicationRepository = duplicationRepository;
    }

    public List<DuplicationDto> getByMes(int mes){

        return duplicationRepository
                .findByMesCreacionAndAnoCreacion((long) mes, 2025L)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private DuplicationDto convertToDto(Duplication d) {

        DuplicationDto dto = new DuplicationDto();

        dto.setId(d.getId());
        dto.setNombreEess(d.getNombreEess());
        dto.setDescripcionDelServicio(d.getDescripcionDelServicio());
        dto.setFechaDeCita(d.getFechaDeCita());
        dto.setEstadoDeLaCita(d.getEstadoDeLaCita());
        dto.setFechaDeCreacion(d.getFechaDeCreacion());
        dto.setNumeroDeDocumento(d.getNumeroDeDocumento());
        dto.setNombresDelPaciente(d.getNombresDelPaciente());
        dto.setApellidoPaternoDelPaciente(d.getApellidoPaternoDelPaciente());
        dto.setUsuarioCc(d.getUsuarioCc());

        return dto;
    }

    private DuplicationFullDto mapToFullDto(Duplication d) {

        return new DuplicationFullDto(

                d.getId(),
                d.getCodEess(),
                d.getNombreEess(),
                d.getDescripcionDelServicio(),
                d.getProfesionalDeLaSalud(),
                d.getConsultorio(),
                d.getTurno(),
                d.getFechaDeCita(),
                d.getDiaCita(),
                d.getMesCita(),
                d.getMesAtencion(),
                d.getAnoCita(),
                d.getHoraInicialCita(),
                d.getTipoDeCupo(),
                d.getEstadoDeLaCita(),
                d.getFechaDeCreacion(),
                d.getDiaCreacion(),
                d.getMesCreacion(),
                d.getAnoCreacion(),
                d.getTipoDeDocumento(),
                d.getNumeroDeDocumento(),
                d.getNombresDelPaciente(),
                d.getApellidoPaternoDelPaciente(),
                d.getApellidoMaternoDelPaciente(),
                d.getCelular(),
                d.getGenero(),
                d.getPersonalQueCita(),
                d.getUsuarioCc()

        );
    }


    public List<DuplicationFullDto> getFullByMes(int mes) {

        return duplicationRepository
                .findByMesCreacionAndAnoCreacion((long) mes, 2025L)
                .stream()
                .map(this::mapToFullDto)
                .toList();
    }

    public byte[] exportExcel(int mes) {

        List<DuplicationFullDto> data =
                duplicationRepository
                .findByMesCreacionAndAnoCreacion((long) mes, 2025L)
                .stream()
                .map(this::mapToFullDto)
                .toList();

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Duplicados");

            Row header = sheet.createRow(0);

            String[] columns = {
                    "ID","COD EESS","EESS","SERVICIO","PROFESIONAL","CONSULTORIO",
                    "TURNO","FECHA CITA","DIA CITA","MES CITA","MES ATENCION","AÑO CITA",
                    "HORA","TIPO CUPO","ESTADO","FECHA CREACION","DIA CREACION",
                    "MES CREACION","AÑO CREACION","TIPO DOC","NUM DOC","NOMBRES",
                    "APELLIDO PATERNO","APELLIDO MATERNO","CELULAR","GENERO",
                    "PERSONAL CITA","USUARIO"
            };

            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            int rowNum = 1;

            for (DuplicationFullDto d : data) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(d.getId());
                row.createCell(1).setCellValue(d.getCodEess());
                row.createCell(2).setCellValue(d.getNombreEess());
                row.createCell(3).setCellValue(d.getDescripcionDelServicio());
                row.createCell(4).setCellValue(d.getProfesionalDeLaSalud());
                row.createCell(5).setCellValue(d.getConsultorio());
                row.createCell(6).setCellValue(d.getTurno());
                row.createCell(7).setCellValue(d.getFechaDeCita());
                row.createCell(8).setCellValue(d.getDiaCita());
                row.createCell(9).setCellValue(d.getMesCita());
                row.createCell(10).setCellValue(d.getMesAtencion());
                row.createCell(11).setCellValue(d.getAnoCita());
                row.createCell(12).setCellValue(d.getHoraInicialCita());
                row.createCell(13).setCellValue(d.getTipoDeCupo());
                row.createCell(14).setCellValue(d.getEstadoDeLaCita());
                row.createCell(15).setCellValue(d.getFechaDeCreacion());
                row.createCell(16).setCellValue(d.getDiaCreacion());
                row.createCell(17).setCellValue(d.getMesCreacion());
                row.createCell(18).setCellValue(d.getAnoCreacion());
                row.createCell(19).setCellValue(d.getTipoDeDocumento());
                row.createCell(20).setCellValue(d.getNumeroDeDocumento());
                row.createCell(21).setCellValue(d.getNombresDelPaciente());
                row.createCell(22).setCellValue(d.getApellidoPaternoDelPaciente());
                row.createCell(23).setCellValue(d.getApellidoMaternoDelPaciente());
                row.createCell(24).setCellValue(d.getCelular());
                row.createCell(25).setCellValue(d.getGenero());
                row.createCell(26).setCellValue(d.getPersonalQueCita());
                row.createCell(27).setCellValue(d.getUsuarioCc());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando Excel", e);
        }
    }
}