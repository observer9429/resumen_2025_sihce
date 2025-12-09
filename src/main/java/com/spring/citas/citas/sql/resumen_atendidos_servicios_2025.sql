WITH meses AS (
    SELECT unnest(ARRAY[
        'Enero','Febrero','Marzo','Abril','Mayo','Junio',
        'Julio','Agosto','Setiembre','Octubre','Noviembre','Diciembre'
    ]) AS mes
),

-- Centros del dataset
centros AS (
    SELECT DISTINCT nombre_eess FROM data_2025
),

-- Conteos reales (solo ATENDIDO y año 2025)
conteos AS (
    SELECT 
        nombre_eess,
        mes_atencion,
        descripcion_del_servicio,
        COUNT(*) AS total
    FROM data_2025
    WHERE estado_de_la_cita = 'ATENDIDO'
      AND ano_cita = '2025'
    GROUP BY nombre_eess, mes_atencion, descripcion_del_servicio
),

-- Totales por mes
totales_mes AS (
    SELECT 
        nombre_eess,
        mes_atencion,
        SUM(total) AS total_mes
    FROM conteos
    GROUP BY nombre_eess, mes_atencion
),

-- Totales por centro
totales_centro AS (
    SELECT 
        nombre_eess,
        SUM(total_mes) AS total_centro
    FROM totales_mes
    GROUP BY nombre_eess
),

-- Servicios agrupados por mes (solo los que tienen atenciones)
servicios_por_mes AS (
    SELECT 
        c.nombre_eess,
        c.mes_atencion,
        jsonb_object_agg(
            descripcion_del_servicio,
            total ORDER BY descripcion_del_servicio
        ) AS servicios
    FROM conteos c
    GROUP BY c.nombre_eess, c.mes_atencion
),

-- Meses completos con totales aunque no haya servicios
meses_completos AS (
    SELECT 
        ce.nombre_eess,
        m.mes AS mes_atencion,
        COALESCE(tm.total_mes, 0) AS total_mes,
        COALESCE(spm.servicios, '{}'::jsonb) AS servicios
    FROM centros ce
    CROSS JOIN meses m
    LEFT JOIN totales_mes tm
        ON tm.nombre_eess = ce.nombre_eess
       AND tm.mes_atencion = m.mes
    LEFT JOIN servicios_por_mes spm
        ON spm.nombre_eess = ce.nombre_eess
       AND spm.mes_atencion = m.mes
),

-- JSON por meses + total del mes incluido
json_meses AS (
    SELECT 
        nombre_eess,
        jsonb_object_agg(
            mes_atencion,
            (
                jsonb_build_object('total', total_mes) || servicios
            ) ORDER BY mes_atencion
        ) AS meses_json
    FROM meses_completos
    GROUP BY nombre_eess
)

SELECT jsonb_build_object(
    'data',
    jsonb_object_agg(
        jm.nombre_eess,
        (
            jsonb_build_object(
                'total', COALESCE(tc.total_centro, 0)
            ) || jm.meses_json
        ) ORDER BY jm.nombre_eess
    )
)
FROM json_meses jm
LEFT JOIN totales_centro tc
    ON tc.nombre_eess = jm.nombre_eess;
