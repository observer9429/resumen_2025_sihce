package com.spring.citas.citas.services;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.citas.citas.config.ConfigService;
import com.spring.citas.citas.entities.Resumen_2025_v02;
import com.spring.citas.citas.projections.ResumenCentroProjection;
import com.spring.citas.citas.repositories.Resumen2025Repository;

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
        List<String> centrosNumerados  = configService.getCentrosNumerados();

        List<ResumenCentroProjection> registros = repo.findAllResumenProyeccion();

        Map<String, Object> resultado = new LinkedHashMap<>();

        // ===== 1) CREAR TODOS LOS CENTROS EN 0 ===== 
        for (int i = 0; i < centrosOriginales.size(); i++) {

            String centroNumerado = centrosNumerados.get(i);

            Map<String, Object> dataCentro = new LinkedHashMap<>();
            dataCentro.put("total_citados", 0);
            dataCentro.put("total_atendidos", 0);

            for (String mes : ordenMeses) {
                Map<String, Object> dataMes = new LinkedHashMap<>();
                dataMes.put("total_citados", 0);
                dataMes.put("total_atendidos", 0);
                dataCentro.put(mes, dataMes);
            }

            resultado.put(centroNumerado, dataCentro);
        }

        // ===== 2) RELLENAR CON DATA REAL =====
        for (ResumenCentroProjection r : registros) {

            String centro = r.getNombre_eess();
            String mes    = r.getMes_atencion();

            int index = centrosOriginales.indexOf(centro);
            if (index == -1) continue;

            String centroNumerado = centrosNumerados.get(index);

            Map<String, Object> dataCentro =
                    (Map<String, Object>) resultado.get(centroNumerado);

            Map<String, Object> dataMes =
                    (Map<String, Object>) dataCentro.get(mes);

            // TOTAL DEL MES (una sola vez)
            //con el condicional evitamos sobreescribir si ya se asigno datos
            if ((int) dataMes.get("total_citados") == 0
                    && (int) dataMes.get("total_atendidos") == 0) {

                dataMes.put("total_citados", r.getTotal_mes_citados());
                dataMes.put("total_atendidos", r.getTotal_mes_atendidos());
            }

            // TOTAL DEL CENTRO (una vez por mes)
            if (!dataCentro.containsKey("mesContado_" + mes)) {

                dataCentro.put(
                        "total_citados",
                        (int) dataCentro.get("total_citados")
                                + r.getTotal_mes_citados()
                );

                dataCentro.put(
                        "total_atendidos",
                        (int) dataCentro.get("total_atendidos")
                                + r.getTotal_mes_atendidos()
                );

                dataCentro.put("mesContado_" + mes, true);
            }

            // SERVICIOS
            if (r.getDescripcion_del_servicio() != null) {

                Map<String, Object> servicio = new LinkedHashMap<>();
                servicio.put("total_citados", r.getTotal_servicio_citados());
                servicio.put("total_atendidos", r.getTotal_servicio_atendidos());

                dataMes.put(r.getDescripcion_del_servicio(), servicio);
            }
        }

        // ===== 3) LIMPIAR FLAGS INTERNOS =====
        for (Object centroObj : resultado.values()) {
            Map<String, Object> nodo = (Map<String, Object>) centroObj;
            nodo.keySet().removeIf(k -> k.startsWith("mesContado_"));
        }

        return resultado;
    }
}
