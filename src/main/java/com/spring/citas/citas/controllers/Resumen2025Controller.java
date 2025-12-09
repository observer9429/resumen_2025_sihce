package com.spring.citas.citas.controllers;

import com.spring.citas.citas.services.Resumen2025Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/atendidos/2025/resumen")
@CrossOrigin("*")
public class Resumen2025Controller {

    @Autowired
    private Resumen2025Service service;

    @GetMapping("/semestre/{num}")
    public Map<String, Object> obtenerDashboardPorSemestre(@PathVariable int num) {
        return service.generarDashboardPorSemestre(num);
    }
}
