CREATE OR REPLACE FUNCTION func_get_avance_esfuerzo_by_rutina_id(
    _rutina_id       int)
    RETURNS TABLE
            (   avanceDiario text,
                esfuerzoDiario text,
                avanceSemanal text,
                esfuerzoSemanal text

            ) AS
$func$
select metr_dia.avanceDiario,
       metr_dia.esfuerzoDiario,
       metr_sem.avanceSem ,
       metr_sem.esfuerzoSem
from(
    select string_agg(rs.avanceSem ::character varying ,',' order by rs.semana_id asc) avanceSem,
           string_agg(rs.esfuerzoSem::character varying,',' order by rs.semana_id asc) esfuerzoSem
from
    (
        select s.semana_id, sum(avance_diario) / 7 as avanceSem,
               sum(esfuerzo_diario) / 7 as esfuerzoSem
        from dia as d
                 inner join semana as s on d.semana_id = s.semana_id
        where s.rutina_id = $1
        group by s.semana_id
    ) AS rs) as metr_sem ,
(select string_agg(rd.esfuerzoDiario::character varying,',' order by rd.dia_id asc) esfuerzoDiario,
       string_agg(rd.avanceDiario::character varying,',' order by rd.dia_id asc) avanceDiario
from
    (
        select d.dia_id, d.avance_diario as avanceDiario,
               d.esfuerzo_diario esfuerzoDiario
        from dia as d
                 inner join semana as s on d.semana_id = s.semana_id
        where s.rutina_id = $1
        group by d.dia_id
    ) AS rd) as metr_dia
$func$ LANGUAGE sql;

