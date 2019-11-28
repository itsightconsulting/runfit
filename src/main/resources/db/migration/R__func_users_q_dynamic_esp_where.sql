CREATE OR REPLACE FUNCTION func_users_q_dynamic_esp_where(
    _ad_nombre_full       text  = NULL,
    _ad_flag_activo      boolean = NULL,
    _ad_limit            int = NULL,
    _ad_off_set          int = 0,
    _ad_tipo_usuario     int = 0)
    RETURNS TABLE(id int,
                  nombreCompleto text,
                  fechaCreacion timestamp,
                  username varchar,
                  correo varchar,
                  fechaUltimoAcceso timestamp,
                  flagActivo boolean,
                  tipoUsuario text,
                  rows int) AS
$func$
begin
    CASE $5
        when 1 then
            return query select
                             m.security_user_id id,
                             concat(m.apellidos,' ', m.nombres) nombreCompleto,
                             m.fecha_creacion fechaCreacion,
                             m.username,
                             m.correo,
                             m.fecha_ultimo_acceso fechaUltimoAcceso,
                             m.flag_activo flagActivo,
                             'Administrador'::text tipoUsuario,
                             count(*) over()::int as rows
                         from administrador m
                         WHERE ($1 IS NULL OR lower(concat(m.apellidos,' ', m.nombres)) like lower(CONCAT('%',$1,'%')))
                           AND ($2 IS NULL OR m.flag_activo = $2)
                         ORDER BY nombreCompleto
                         LIMIT $3
                             OFFSET $4;
        when 2 then
            return query select t.security_user_id id,
                                concat(t.apellidos,' ', t.nombres) nombreCompleto,
                                t.fecha_creacion fechaCreacion,
                                t.username,
                                t.correo,
                                t.fecha_ultimo_acceso fechaUltimoAcceso,
                                t.flag_activo flagActivo,
                                'Trainer'::text tipoUsuario,
                                count(*) over()::int as rows
                         from trainer t
                         WHERE ($1 IS NULL OR lower(concat(t.apellidos,' ', t.nombres)) like lower(CONCAT('%',$1,'%')))
                           AND ($2 IS NULL OR t.flag_activo = $2)
                         ORDER BY nombreCompleto
                         LIMIT $3
                             OFFSET $4;
        when 3 then
            return query select u.security_user_id id,
                                concat(u.apellidos,' ', u.nombres) nombreCompleto,
                                u.fecha_creacion fechaCreacion,
                                u.username,
                                u.correo,
                                u.fecha_ultimo_acceso fechaUltimoAcceso,
                                u.flag_activo flagActivo,
                                'Cliente'::text tipoUsuario,
                                count(*) over()::int as rows
                         from cliente u
                         WHERE ($1 IS NULL OR lower(concat(u.apellidos,' ', u.nombres)) like lower(CONCAT('%',$1,'%')))
                           AND ($2 IS NULL OR u.flag_activo = $2)
                         ORDER BY nombreCompleto
                         LIMIT $3
                             OFFSET $4;
        else
            return;
        end case;
END;
$func$ LANGUAGE plpgsql;
