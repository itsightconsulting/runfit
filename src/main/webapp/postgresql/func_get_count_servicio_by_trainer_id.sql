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
     where  s.trainer_id = $1
     group by s.servicio_id, s.nombre
     order by qtyClientes desc
     limit 5
$func$ LANGUAGE SQL;

