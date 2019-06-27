CREATE OR REPLACE FUNCTION func_rutina_q_by_cliente_id(
    _cliente_id           int  = NULL,
    _ad_limit            int = NULL)
    RETURNS TABLE(id int,
                  anios int,
                  meses int,
                  totalSemanas int,
                  dias int,
                  tipoRutinaId int,
                  fechaInicio date,
                  fechaFin date,
                  control text,
                  rows int) AS
$func$
select
    rutina_id id,
    anios,
    meses,
    total_semanas totalSemanas,
    dias,
    tipo_rutina_id,
    fecha_inicio fechaInicio,
    fecha_fin fechaFin,
    control::text,
    count(*) over()::int as rows
from rutina
WHERE cliente_id = $1
AND flag_activo = true
ORDER BY id desc
LIMIT $2
$func$ LANGUAGE sql;
