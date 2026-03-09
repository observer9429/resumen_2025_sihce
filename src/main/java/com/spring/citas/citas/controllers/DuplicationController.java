package com.spring.citas.citas.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.spring.citas.citas.dto.DuplicationDto;
import com.spring.citas.citas.dto.DuplicationFullDto;
import com.spring.citas.citas.services.DuplicationService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('ADMIN','MASTER')")
@RequestMapping("/duplications")
public class DuplicationController {

    private final DuplicationService duplicationService;

    public DuplicationController(DuplicationService duplicationService) {
        this.duplicationService = duplicationService;
    }

    // 🔹 BUSCAR DUPLICACIONES POR MES (SIEMPRE AÑO 2025)
    @GetMapping("/mes/{mes}")
    public List<DuplicationDto> getByMes(@PathVariable int mes) {

        return duplicationService.getByMes(mes);
    }

    @GetMapping("/full/{mes}")
    public ResponseEntity<List<DuplicationFullDto>> getFull(
            @PathVariable int mes) {

        return ResponseEntity.ok(
                duplicationService.getFullByMes(mes)
        );
    }

    @GetMapping("/excel/{mes}")
    public ResponseEntity<byte[]> exportExcel(@PathVariable int mes) {

        byte[] excel = duplicationService.exportExcel(mes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=citas_duplicadas.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excel);
    }
}