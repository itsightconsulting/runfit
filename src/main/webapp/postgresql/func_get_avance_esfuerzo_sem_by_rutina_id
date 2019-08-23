CREATE OR REPLACE FUNCTION func_get_avance_esfuerzo_sem_by_rutina_id(
    _rutina_id       int)
    RETURNS TABLE
            (   avanceSemanal text,
                esfuerzoSemanal text
            ) AS
$func$
select string_agg(rs.avanceSem ::character varying ,',' order by rs.semana_id asc),
       string_agg(rs.esfuerzoSem::character varying,',' order by rs.semana_id asc)
from
    (
        select s.semana_id, sum(avance_diario) / 7 as avanceSem,
               sum(esfuerzo_diario) / 7 as esfuerzoSem
        from dia as d
                 inner join semana as s on d.semana_id = s.semana_id
        where s.rutina_id = $1
        group by s.semana_id
    ) AS rs
$func$ LANGUAGE sql;
