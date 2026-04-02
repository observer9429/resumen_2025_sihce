package com.spring.citas.citas.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.citas.citas.config.ConfigService;
//import com.spring.citas.citas.entities.Resumen2025;
import com.spring.citas.citas.entities.Resumen_2025_v02;
import com.spring.citas.citas.repositories.Resumen2025Repository;

@Service
public class Resumen2025Service {

    @Autowired
    private Resumen2025Repository repo;
 
    @Autowired
    private ConfigService configService;

    public Map<String, Object> generarDashboardPorSemestre(int semestre) {

        // 1) Meses del semestre
        List<String> meses = semestre == 1 ?
                List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio") :
                List.of("Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre");

        // 2) Centros del config
        List<String> centros = configService.getCentros();

        // 3) Inicializar estructura de los centros
        Map<String, Map<String, Integer>> centrosMap = new TreeMap<>();//CREA LA BASE MAP DE LOS CENTROS
        //Centro → (Mes → Valor)
        for (String c : centros) {
            Map<String, Integer> mesesCentro = new LinkedHashMap<>();
            for (String mes : meses) { //INSERTA TODOS LOS MESES ESCOGIDOS AL MAP
                mesesCentro.put(mes, 0);
            }
            centrosMap.put(c, mesesCentro);//INSERTAMOS CADA CENTRO Y MES CON VALOR EN 0
        }

        // 4) Datos reales
        List<Resumen_2025_v02> datos = repo.findByMeses(meses);//POR MES ATENCION BUSCA

        // 🔑 Para evitar duplicar (centro + mes)
        Set<String> yaContado = new HashSet<>();

        // 5) Rellenar datos (ATENDIDOS)
        for (Resumen_2025_v02 r : datos) {

            String centro = r.getNombre_eess();
            String mes = r.getMes_atencion();
            //centrosmap tiene esta estructura-> <CALCUTA {ENERO: 254  }

            if (!centrosMap.containsKey(centro))//Si el centro no está en config → se ignora
                continue;

            String clave = centro + "|" + mes;//Crear clave única->ALFA Y OMEGA|Enero

            if (yaContado.contains(clave))//Si ya se procesó → se salta
                continue;

            yaContado.add(clave);//Si ya se procesó → se salta

            // ✅ USAR ATENDIDOS, NO CITADOS
            centrosMap.get(centro)
                      .put(mes, r.getTotal_mes_atendidos());//Reemplaza el 0 por el valor real
        }

        // 6) Total atendidos por mes
        Map<String, Integer> mesesAtendidos = new LinkedHashMap<>();

        for (String mes : meses) {
            int suma = 0;
            for (String centro : centros) {
                suma += centrosMap.get(centro).get(mes);//Suma todos los centros en ese mes
            }
            mesesAtendidos.put(mes, suma);
        }

        // 7) Meses sin atenciones
        Map<String, Integer> mesesSinAtenciones = new LinkedHashMap<>();//Cuenta cuántos centros tienen 0

        for (String mes : meses) {//meses -> depende del semestre escogido
            int sin = 0;
            for (String centro : centros) {
                if (centrosMap.get(centro).get(mes) == 0)//Si un centro no atendió → suma
                    sin++;
            }
            mesesSinAtenciones.put(mes, sin);
        }

        // 8) JSON final
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("total_establecimientos", centros.size());
        data.put("meses_sin_atenciones", mesesSinAtenciones);
        data.put("meses_atendidos", mesesAtendidos);
        data.put("centros", centrosMap);

        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("data_atendidos", data);

        return wrapper;
    }
}

