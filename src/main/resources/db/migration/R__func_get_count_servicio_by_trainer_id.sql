CREATE OR REPLACE FUNCTION func_get_count_servicio_by_trainer_id(
    _security_user_id  int
)
    RETURNS TABLE(
                     id int,
                     nombre text,
                     qtyClientes bigint,
                     qtyHombre bigint,
                     qtyMujer bigint
                 ) AS
$func$
     select
           s.servicio_id,
           s.nombre,
           count(*) qtyClientes,
           sum(case when c.sexo = 1 then 1 else 0 end) AS qtyHombre,
           sum(case when c.sexo = 2 then 1 else 0 end) AS qtyMujer
     from runfit.public.cliente_servicio cs
     inner join servicio s  on cs.servicio_id = s.servicio_id
     inner join cliente c on cs.cliente_id = c.security_user_id
     WHERE S.trainer_id = ( SELECT CASE WHEN TF.tr_emp_id IS NULL THEN TF.trainer_id ELSE TF.tr_emp_id END
                            FROM trainer_ficha TF
                            WHERE TF.trainer_id = $1)  AND
                            CS.cliente_id IN (
                            SELECT RF.cliente_id  from red_fitness RF where RF.trainer_id = $1
                            AND EXTRACT(DAY FROM RF.fecha_creacion- CS.fecha_creacion) < 1)
     group by s.servicio_id, s.nombre
     order by qtyClientes desc
     limit 5
$func$ LANGUAGE SQL;


