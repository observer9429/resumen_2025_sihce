package com.spring.citas.citas.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.citas.citas.services.Resumen2025GraficoService;

@RestController
@RequestMapping("/api/grafico")
@CrossOrigin("*")
public class Resumen2025GraficoController {
    
    @Autowired
    private Resumen2025GraficoService service;

    @GetMapping("/semestre/{num}")
    public Map<String, Object> obtenerDashboardPorSemestre(@PathVariable int num) {
        return service.obtenerResumenGrafico(num);
    }
}
