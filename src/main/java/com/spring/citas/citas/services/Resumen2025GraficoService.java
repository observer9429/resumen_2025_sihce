package com.spring.citas.citas.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.citas.citas.projections.ResumenCentroProjection;
import com.spring.citas.citas.repositories.Resumen2025Repository;

@Service
public class Resumen2025GraficoService {

    @Autowired
    private Resumen2025Repository repo;

    private final List<String> ordenMeses = List.of(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"
    );

    public Map<String, Object> obtenerResumenGrafico(int num) {

        // 🔹 Definir meses según semestre
        List<String> mesesSemestre;

        if (num == 1) {
            mesesSemestre = ordenMeses.subList(0, 6);   // Enero - Junio
        } else if (num == 2) {
            mesesSemestre = ordenMeses.subList(6, 12);  // Julio - Diciembre
        } else {
            throw new IllegalArgumentException("Semestre inválido: use 1 o 2");
        }

        List<ResumenCentroProjection> registros = repo.findAllResumenGrafico();

        // Inicializar acumuladores SOLO para los meses del semestre
        Map<String, Integer> totalCitadosPorMes   = new LinkedHashMap<>();
        Map<String, Integer> totalAtendidosPorMes = new LinkedHashMap<>();

        for (String mes : mesesSemestre) {
            totalCitadosPorMes.put(mes, 0);
            totalAtendidosPorMes.put(mes, 0);
        }

        // Para evitar contar duplicados (centro + mes)
        Set<String> yaContado = new HashSet<>();

        for (ResumenCentroProjection r : registros) {

            String centro = r.getNombre_eess();
            String mes    = r.getMes_atencion();

            // Ignorar meses fuera del semestre
            if (!totalCitadosPorMes.containsKey(mes)) {
                continue;
            }

            String clave = centro + "|" + mes;

            // Evitar duplicados
            if (yaContado.contains(clave)) {
                continue;
            }

            yaContado.add(clave);

            totalCitadosPorMes.put(
                mes,
                totalCitadosPorMes.get(mes) + r.getTotal_mes_citados()
            );

            totalAtendidosPorMes.put(
                mes,
                totalAtendidosPorMes.get(mes) + r.getTotal_mes_atendidos()
            );
        }

        // Armar JSON final
        List<String> meses = new ArrayList<>();
        List<Integer> citados = new ArrayList<>();
        List<Integer> atendidos = new ArrayList<>();

        for (String mes : mesesSemestre) {
            meses.add(mes);
            citados.add(totalCitadosPorMes.get(mes));
            atendidos.add(totalAtendidosPorMes.get(mes));
        }

        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("meses", meses);
        resultado.put("citados", citados);
        resultado.put("atendidos", atendidos);

        return resultado;
    }
}
