package com.spring.citas.citas.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConfigService {

    private Map<String, String> meses; 
    private List<String> centros;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // CARGA DESDE resources DENTRO DEL JAR
        InputStream is = getClass().getResourceAsStream("/config.json");

        Map<String, Object> json = mapper.readValue(
                is,
                new TypeReference<Map<String, Object>>() {}
        );

        this.meses = (Map<String, String>) json.get("meses");
        this.centros = (List<String>) json.get("centros");
    }

    // ✔️ Devuelve MESES ORDENADOS por número
    public LinkedHashMap<Integer, String> getMesesOrdenados() {
        return meses.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(Integer::parseInt)))
                .collect(
                        Collectors.toMap(
                                e -> Integer.parseInt(e.getKey()),
                                Map.Entry::getValue,
                                (a, b) -> a,
                                LinkedHashMap::new
                        )
                );
    }

    public List<String> getCentros() {
        return centros;
    }

    
    //numeramos los centros para la tabla centro detallado
    public List<String> getCentrosNumerados() {
    List<String> centros = getCentros();
    List<String> numerados = new ArrayList<>();

    int index = 1;
    for (String c : centros) {
        numerados.add(index + ".- " + c);
        index++;
    }
    return numerados;
}

}
