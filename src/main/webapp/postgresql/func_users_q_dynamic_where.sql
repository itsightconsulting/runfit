CREATE OR REPLACE FUNCTION func_users_q_dynamic_where(
    _ad_nombre_full       text  = NULL,
    _ad_flag_activo      boolean = NULL,
    _ad_limit            int = NULL,
    _ad_off_set          int = 0)
    RETURNS TABLE(id int,
                  nombreCompleto text,
                  fechaCreacion timestamp,
                  username text,
                  correo text,
                  fechaUltimoAcceso timestamp,
                  flagActivo boolean,
                  tipoUsuario text,
                  rows int) AS
$func$
select
    a.id,
    a.nombreCompleto,
    a.fechaCreacion,
    a.username,
    a.correo,
    a.fechaUltimoAcceso,
    a.flagActivo,
    a.tipoUsuario,
    count(*) over()::int as rows
from (
         select m.security_user_id id,
                m.fecha_creacion fechaCreacion,
                concat(m.apellidos,' ', m.nombres) nombreCompleto,
                m.flag_activo flagActivo,
                m.correo,
                m.username,
                m.fecha_ultimo_acceso fechaUltimoAcceso,
                'Administrador' tipoUsuario
         from administrador m
         union all
         select t.security_user_id id,
                t.fecha_creacion fechaCreacion,
                concat(t.apellidos,' ', t.nombres) nombreCompleto,
                t.flag_activo flagActivo,
                t.correo,
                t.username,
                t.fecha_ultimo_acceso fechaUltimoAcceso,
                'Trainer' tipoUsuario
         from trainer t
         union all
         select u.security_user_id id,
                u.fecha_creacion fechaCreacion,
                concat(u.apellidos,' ', u.nombres) nombreCompleto,
                u.flag_activo flagActivo,
                u.correo,
                u.username,
                u.fecha_ultimo_acceso fechaUltimoAcceso,
                'Cliente' tipoUsuario
         from cliente u
         order by 1) a
WHERE ($1 IS NULL OR lower(a.nombreCompleto) like lower(CONCAT('%',$1,'%')))
AND ($2 IS NULL OR a.flagActivo = $2)
ORDER BY a.nombreCompleto
LIMIT $3
OFFSET $4
$func$ LANGUAGE sql;
