CREATE OR REPLACE FUNCTION func_cliente_fitness_q_by_id(
    _cli_id       int)
    RETURNS TABLE(
                     id int,
                     competencias text,
                     condicionAnatomica text,
                     desObjetivos text,
                     desTerPredom text,
                     desTerPredomOtro text,
                     desgasteZapatilla text,
                     desgasteZapatillaOtro text,
                     diasSemanaCorriendo int,
                     estadoCivil int,
                     fitElementos text,
                     flagCalentamiento boolean,
                     flagEstiramiento boolean,
                     frecuenciaComunicacion int,
                     imc double precision,
                     kilometrajePromedioSemana double precision,
                     mejoras text,
                     nivel int,
                     peso double precision,
                     salud text,
                     sexo int,
                     talla int,
                     tiempoDistancia text,
                     tiempoUnKilometro text,
                     tipoCanalVentaId int,
                     fechaCreacion timestamp,
                     fechaModificacion timestamp,
                     flagActivo boolean,
                     correo text,
                     fechaNacimiento date,
                     fechaUltimoAcceso timestamp,
                     movil text,
                     nombres text,
                     apellidos text,
                     numeroDocumento text,
                     paisId int,
                     tipoDocumentoId int,
                     ubigeo text) AS
$func$
select
    security_user_id,
    competencias::text,
    condicion_anatomica::text,
    des_Objetivos,
    des_Ter_Predom,
    des_Ter_Predom_Otro,
    desgaste_Zapatilla,
    desgaste_Zapatilla_Otro,
    dias_Semana_Corriendo,
    estado_Civil,
    fit_Elementos::text,
    flag_Calentamiento,
    flag_Estiramiento,
    frecuencia_Comunicacion,
    imc,
    kilometraje_Promedio_Semana,
    mejoras::text,
    nivel,
    peso,
    salud::text,
    c.sexo,
    talla,
    tiempo_Distancia,
    tiempo_Un_Kilometro,
    tipo_canal_venta_id,
    fecha_Creacion,
    fecha_Modificacion,
    flag_Activo,
    correo,
    fecha_Nacimiento,
    fecha_Ultimo_Acceso,
    movil,
    nombres,
    apellidos,
    numero_Documento,
    pais_Id,
    tipo_Documento_Id,
    ubigeo
from runfit.public.cliente_fitness cf inner join cliente c on cf.cliente_id = c.security_user_id
WHERE security_user_id = $1
$func$ LANGUAGE sql;