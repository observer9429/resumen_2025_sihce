package com.spring.citas.citas.services;

import com.spring.citas.citas.config.ConfigService;
import com.spring.citas.citas.projections.ResumenCentroProjection;
import com.spring.citas.citas.repositories.Resumen2025Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Resumen2025DetalleService {

    @Autowired
    private Resumen2025Repository repo;

    @Autowired
    private ConfigService configService;

    private final List<String> ordenMeses = List.of(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"
    );

    public Map<String, Object> generarResumenAnualCentros() {

        List<String> centrosOriginales = configService.getCentros();
        List<String> centrosNumerados = configService.getCentrosNumerados();

        List<ResumenCentroProjection> registros = repo.findAllResumenProyeccion();

        Map<String, Object> resultado = new LinkedHashMap<>();

        // ===== 1) Crear estructura vacía =====
        for (int i = 0; i < centrosOriginales.size(); i++) {

            String centroNumerado = centrosNumerados.get(i);

            Map<String, Object> dataCentro = new LinkedHashMap<>();
            dataCentro.put("total", 0);

            for (String mes : ordenMeses) {
                Map<String, Object> estructuraMes = new LinkedHashMap<>();
                estructuraMes.put("total", 0);  // luego se reemplaza por el real
                dataCentro.put(mes, estructuraMes);
            }

            resultado.put(centroNumerado, dataCentro);
        }

        // ===== 2) Llenar estructura sin duplicar totales =====
        for (ResumenCentroProjection r : registros) {

            String centro = r.getNombre_eess();
            String mes = r.getMes_atencion();

            int index = centrosOriginales.indexOf(centro);
            if (index == -1) continue;

            String centroNumerado = centrosNumerados.get(index);

            Map<String, Object> dataCentro = (Map<String, Object>) resultado.get(centroNumerado);
            Map<String, Object> dataMes = (Map<String, Object>) dataCentro.get(mes);

            // ---- TOTAL DEL MES ----
            // Solo poner el total_mes una vez, nunca sumarlo varias veces
            if ((int) dataMes.get("total") == 0) {
                dataMes.put("total", r.getTotal_mes());
            }

            // ---- TOTAL DEL CENTRO ----
            // Solo sumar una vez por mes
            if (!dataCentro.containsKey("mesContado_" + mes)) {
                int totalCentroActual = (int) dataCentro.get("total");
                dataCentro.put("total", totalCentroActual + r.getTotal_mes());
                dataCentro.put("mesContado_" + mes, true);
            }

            // ---- Servicios ----
            if (r.getDescripcion_del_servicio() != null) {
                dataMes.put(r.getDescripcion_del_servicio(), r.getTotal_servicio());
            }
        }

        // ===== 3) Limpiar flags internos "mesContado" =====
        for (String centro : resultado.keySet()) {
            Map<String, Object> nodo = (Map<String, Object>) resultado.get(centro);
            nodo.keySet().removeIf(k -> k.startsWith("mesContado_"));
        }

        return resultado;
    }
}
