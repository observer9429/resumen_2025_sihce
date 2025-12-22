package com.spring.citas.citas.controllers;

import com.spring.citas.citas.services.Resumen2025DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/resumen2025")
@CrossOrigin(origins = "*") // permitir desde Netlify
public class Resumen2025DetalleController {

    @Autowired
    private Resumen2025DetalleService service;

    @GetMapping("/anual")
    public Map<String, Object> resumenAnual() {
        return service.generarResumenAnualCentros();
    }
}