CREATE OR REPLACE FUNCTION func_rutina_q_by_id(
    _id           int  = NULL)
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
WHERE rutina_id = $1
AND flag_activo = true
$func$ LANGUAGE sql;
