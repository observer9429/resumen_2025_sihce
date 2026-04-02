package com.spring.citas.citas.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.citas.citas.entities.Resumen_2025_v02;
import com.spring.citas.citas.repositories.Resumen2025Repository;
import com.spring.citas.citas.services.Resumen2025DetalleService;
import com.spring.citas.citas.services.ServicePractica;

@RestController
@RequestMapping("/api/json")
@CrossOrigin("*")
public class PracticaMapController {


    @Autowired
    private ServicePractica service; 
    
    //public List<Resumen_2025_v02> obtenerResumenPractica() {

    //public Map<String, Map<String, Integer>> obtenerResumenPractica() {
    @GetMapping("/ver")
    public Map<String, Object> obtenerResumenPractica() {
        return service.getDataWe();
    } 
    
}
