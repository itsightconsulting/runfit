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
    _tipo_documento_id int,
    _ubigeo text,--END Cliente
    _competencias text,--jsonb|STARTS Cliente_Fitness
    _condicion_anatomica text,--jsonb
    _des_objetivos text,
    _des_ter_predom text,
    _des_ter_predom_otro text,
    _desgaste_zapatilla text,
    _desgaste_zapatilla_otro text,
    _dias_semana_corriendo int,
    _estado_civil int,
    _fit_elementos text,
    _flag_calentamiento boolean,
    _flag_estiramiento boolean,
    _imc double precision,
    _kilometraje_promedio_semana double precision,
    _mejoras text,--jsonb
    _nivel int,
    _peso double precision,
    _salud text,--jsonb
    _sexo int,
    _talla int,
    _tiempo_distancia text,
    _tiempo_un_kilometro text
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
                       tipo_documento_id = COALESCE(_tipo_documento_id, tipo_documento_id),
                       ubigeo = COALESCE(_ubigeo, ubigeo)
    WHERE security_user_id = $1;
    UPDATE cliente_fitness SET
                               competencias = COALESCE(_competencias::jsonb, competencias),
                               condicion_anatomica = COALESCE(_condicion_anatomica::jsonb, condicion_anatomica),
                               des_objetivos = COALESCE(_des_objetivos, des_objetivos),
                               des_ter_predom = COALESCE(_des_ter_predom, des_ter_predom),
                               des_ter_predom_otro = COALESCE(_des_ter_predom_otro, des_ter_predom_otro),
                               desgaste_zapatilla = COALESCE(_desgaste_zapatilla, desgaste_zapatilla),
                               desgaste_zapatilla_otro = COALESCE(_desgaste_zapatilla_otro, desgaste_zapatilla_otro),
                               dias_semana_corriendo = COALESCE(_dias_semana_corriendo, dias_semana_corriendo),
                               estado_civil = COALESCE(_estado_civil, estado_civil),
                               fit_elementos = COALESCE(_fit_elementos, fit_elementos),
                               flag_calentamiento = COALESCE(_flag_calentamiento, flag_calentamiento),
                               flag_estiramiento = COALESCE(_flag_estiramiento, flag_estiramiento),
                               imc = COALESCE(_imc, imc),
                               kilometraje_promedio_semana = COALESCE(_kilometraje_promedio_semana, kilometraje_promedio_semana),
                               mejoras = COALESCE(_mejoras::jsonb, mejoras),
                               nivel = COALESCE(_nivel, nivel),
                               peso = COALESCE(_peso, peso),
                               salud = COALESCE(_salud::jsonb, salud),
                               sexo = COALESCE(_sexo, sexo),
                               talla = COALESCE(_talla, talla),
                               tiempo_distancia = COALESCE(_tiempo_distancia, tiempo_distancia),
                               tiempo_un_kilometro = COALESCE(_tiempo_un_kilometro, tiempo_un_kilometro)
    WHERE cliente_id = $1;
    RETURN FOUND;
END
$func$ LANGUAGE plpgsql;
--SELECT column_name, data_type, case when is_nullable = 'NO' then 'true' else 'false' end obligatorio FROM information_schema.columns WHERE
--       table_name = 'cliente' order by 3 desc, 1 asc;
--SELECT column_name, data_type, '_'|| column_name ||' ' || data_type || ',', case when is_nullable = 'NO' then 'true' else 'false' end obligatorio FROM information_schema.columns WHERE
-- table_name = 'cliente_fitness' order by 1 asc;