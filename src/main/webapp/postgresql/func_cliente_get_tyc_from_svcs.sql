create or replace function func_cliente_get_tyc_from_svcs(
    _cliente_id int
)
    RETURNS TABLE
            (
                trainer text,
                nombreServicio text,
                tycUrl text
            )
AS
$func$
select t.nombres || ' ' || t.apellidos nom_trainer,s.nombre, t.security_user_id || '/' || s.tycuuid || s.tyc_ext tyc
from servicio s
inner join trainer t on t.security_user_id=s.trainer_id
where s.servicio_id
          in (select servicio_id from cliente_servicio where cliente_id= $1)
    AND s.tycuuid IS NOT NULL;
$func$ LANGUAGE sql;