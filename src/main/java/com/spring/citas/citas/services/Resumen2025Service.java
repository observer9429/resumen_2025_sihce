package com.spring.citas.citas.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.citas.citas.config.ConfigService;
import com.spring.citas.citas.entities.Resumen2025;
import com.spring.citas.citas.repositories.Resumen2025Repository;

@Service
public class Resumen2025Service {

    @Autowired
    private Resumen2025Repository repo;

    @Autowired
    private ConfigService configService;

    public Map<String, Object> generarDashboardPorSemestre(int semestre) {

        // 1) Meses del semestre solicitado
        List<String> meses = semestre == 1 ?
                List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio") :
                List.of("Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre");

        // 2) Centros del config.json
        List<String> centros = configService.getCentros();

        // 3) Inicializar estructura base: centro → mes → 0
        Map<String, Map<String, Integer>> centrosMap = new TreeMap<>();

        for (String c : centros) {
            Map<String, Integer> mesesCentro = new LinkedHashMap<>();
            for (String mes : meses) {
                mesesCentro.put(mes, 0);
            }
            centrosMap.put(c, mesesCentro);
        }

        // 4) Obtener datos reales del semestre
        List<Resumen2025> datos = repo.findByMeses(meses);

        // 5) Rellenar datos reales
        for (Resumen2025 r : datos) {
            if (!centrosMap.containsKey(r.getNombre_eess())) 
                continue;

            centrosMap.get(r.getNombre_eess())
                      .put(r.getMes_atencion(), r.getTotal_mes());
        }

        // 6) Calcular meses_atendidos (total general por mes)
        Map<String, Integer> mesesAtendidos = new LinkedHashMap<>();

        for (String mes : meses) {
            int suma = 0;
            for (String centro : centros) {
                suma += centrosMap.get(centro).get(mes);
            }
            mesesAtendidos.put(mes, suma);
        }

        // 7) Calcular meses_sin_atenciones (cuántos centros tienen 0)
        Map<String, Integer> mesesSinAtenciones = new LinkedHashMap<>();

        for (String mes : meses) {
            int sin = 0;
            for (String centro : centros) {
                if (centrosMap.get(centro).get(mes) == 0)
                    sin++;
            }
            mesesSinAtenciones.put(mes, sin);
        }

        // 8) Construir JSON final
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("total_establecimientos", centros.size());
        data.put("meses_sin_atenciones", mesesSinAtenciones);
        data.put("meses_atendidos", mesesAtendidos); // ← CAMBIO IMPORTANTE
        data.put("centros", centrosMap);

        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("data_atendidos", data);

        return wrapper;
    }
}
