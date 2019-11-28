CREATE OR REPLACE FUNCTION func_get_count_servicio_top_plataforma()
    RETURNS TABLE(
                     trainerid int,
                     trainerNombre text,
                     servicioNombre text,
                     servicioId int,
                     qtyClientes bigint,
                     qtyHombre bigint,
                     qtyMujer bigint
                 ) AS
$func$
select
    top_cs.trainer_id trainerid,
    CONCAT(t.nombres, ' ', t.apellidos) trainerNombre,
    top_cs.nombre servicioNombre,
    top_cs.servicio_id servicioId,
    top_cs.qtyClientes qtyClientes,
    top_cs.qtyHombre,
    top_cs.qtyMujer
 from (select count_cs.trainer_id,
              count_cs.nombre,
              count_cs.servicio_id,
              count_cs.qtyClientes,
              count_cs.qtyHombre,
              count_cs.qtyMujer,
              RANK() OVER ( PARTITION BY count_cs.trainer_id ORDER BY count_cs.qtyClientes DESC) rank
       from (select s.servicio_id,
                    s.nombre,
                    s.trainer_id,
                    count(*) qtyClientes,
                    sum(case when c.sexo = 1 then 1 else 0 end) AS qtyHombre,
                    sum(case when c.sexo = 2 then 1 else 0 end) AS qtyMujer
             from runfit.public.cliente_servicio cs
             inner join servicio s on cs.servicio_id = s.servicio_id
             inner join cliente c on cs.cliente_id = c.security_user_id
             group by s.servicio_id, s.nombre) as count_cs
      )as top_cs
 inner join  trainer t on top_cs.trainer_id = t.security_user_id
 where
   top_cs.rank = 1
  order by top_cs.qtyClientes desc
  limit 10
$func$ LANGUAGE sql;
