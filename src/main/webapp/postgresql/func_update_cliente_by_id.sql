CREATE OR REPLACE FUNCTION func_update_cliente_by_id(
    _security_user_id int,
    _movil text,
    _nombres text,
    _apellidos text,
    _numero_documento text,
    _pais_id int,
    _fecha_modificacion timestamp,
    _fecha_nacimiento timestamp,
    _modificado_por text,
    _telefono text,
    _tipo_documento_id int,
    _ubigeo text
) RETURNS boolean
AS
$func$
BEGIN
        UPDATE cliente SET
                           movil = COALESCE(_movil, movil),
                           nombres = COALESCE(_nombres, nombres),
                           apellidos = COALESCE(_apellidos, apellidos),
                           numero_documento = COALESCE(_numero_documento, numero_documento),
                           pais_id = COALESCE(_pais_id, pais_id),
                           fecha_modificacion = COALESCE(_fecha_modificacion, fecha_modificacion),--Apartir de acá vienen los campos que pueden ser null
                           fecha_nacimiento = COALESCE(_fecha_nacimiento, fecha_nacimiento),
                           modificado_por = COALESCE(_modificado_por, modificado_por),
                           telefono = COALESCE(_telefono, telefono),
                           tipo_documento_id = COALESCE(_tipo_documento_id, tipo_documento_id),
                           ubigeo = COALESCE(_ubigeo, ubigeo)
        WHERE security_user_id = $1;
        RETURN FOUND;
END
$func$ LANGUAGE plpgsql;
--SELECT column_name, data_type, case when is_nullable = 'NO' then 'true' else 'false' end obligatorio FROM information_schema.columns WHERE
--       table_name = 'cliente' order by 3 desc, 1 asc;

