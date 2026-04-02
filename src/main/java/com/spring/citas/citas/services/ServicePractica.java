package com.spring.citas.citas.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.citas.citas.config.ConfigService;
import com.spring.citas.citas.entities.Resumen_2025_v02;
import com.spring.citas.citas.repositories.Resumen2025Repository;

@Service
public class ServicePractica {
    
    private final Resumen2025Repository repo; 

    @Autowired
    private ConfigService configService;

    public ServicePractica(Resumen2025Repository repo){

        this.repo=repo;

    } 

    
    

    //public List<Resumen_2025_v02> getDataWe(){
    //public Map<String, Map<String, Integer>> getDataWe(){
    public Map<String, Object> getDataWe(){

        List<String> mesesEscogidos=new ArrayList<>(
            Arrays.asList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio")
        );
        

        // 2) Centros del config -- OBTENEMOS LOS CENTROS
        List<String> centros = configService.getCentros();
        //System.out.println("conteo de los centros es: "+centros.size());

        //CREANDO MAP RAIZ
        Map<String, Object> laRaiz=new LinkedHashMap<>();

        

        //agregandolo
        laRaiz.put("Total EESS", centros.size());

        laRaiz.put("MesesEESS-sinAtendidos", 22);

        // 3) Inicializar estructura de los centros
        Map<String, Map<String, Integer>> centrosMap = new TreeMap<>();//CREA LA BASE MAP DE LOS CENTROS

        for(String c: centros){//recorremos todos los centros
            Map<String, Integer> listaMeses=new LinkedHashMap<>();//creamos la estructura meses
            //  que sera para cada centro

            for(String mes: mesesEscogidos){
                listaMeses.put(mes, 0);//insertamos lso centros con valor 0
            }

            centrosMap.put(c, listaMeses); //insertamos el nombre del centro como string
            //y de segundo aprametro el Map de meses escogidos, automatico ya detecta el formato
            //que es <"Enero",0> y por eso se inserta directo

        }

        //data de la ddbb
        List<Resumen_2025_v02> datos=repo.findByMeses(mesesEscogidos);

        for(Resumen_2025_v02 r: datos){

            String nombreCentro= r.getNombre_eess();
            String mesAtencion=r.getMes_atencion();
            //centrosmap tiene esta estructura-> <CALCUTA {ENERO: 254  }
            //verificamos que el map de los centro contenga como llave el
            //  valor que nos devuelve el get_mesatencion de la fila de la ddbb
            if(!centrosMap.containsKey(nombreCentro)){
                continue;
            }


        }

        //insertando todos los centros
        laRaiz.put("centros", centrosMap);

        

        Map<String,Object> superRoot=new LinkedHashMap<>();
        superRoot.put("Data-2026", laRaiz);

        /* 
         // 3) Inicializar estructura raiz
        Map<String, Object> root = new TreeMap<>();//json vacio

        //data atendido
        
        

        root.put("total_establecimientos", 77);

        //root.put("data-atendidos", dataAtendidos);

        Map<String, Integer> mesesSinAtenciones=new TreeMap<>();

        mesesSinAtenciones.put("Enero", 25);
        mesesSinAtenciones.put("Febrero", 15);

        root.put("meses-sin-atenciones", mesesSinAtenciones);

        Map<String, Integer> mesesAtendidos = new TreeMap<>();

        mesesAtendidos.put("Enero", 235);
        mesesAtendidos.put("Febrero", 85);

        root.put("meses-Atendidos", mesesAtendidos);

        

        Map<String, Map<String, Integer>> centrosMap = new TreeMap<>();

        Map<String, Integer> centro1 = new TreeMap<>();

        centro1.put("Enero", 0);
        centro1.put("Febrero", 0);
        centro1.put("Marzo", 0);
        centro1.put("Abril", 0);
        centro1.put("Mayo", 0);
        centro1.put("Junio", 44);

        centrosMap.put("7 DE OCTUBRE", centro1);

        //insertamos los centros en el root
        root.put("centros", centrosMap);
        
        //return repo.findAll();
        return root;
        */

        //return centrosMap;
        return superRoot;
    }


}
