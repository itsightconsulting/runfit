CREATE OR REPLACE FUNCTION func_red_fitness_q_dynamic_where(
    _ad_trainer_id       int  = NULL,
    _ad_nombre_full       text  = NULL,
    _ad_limit            int = NULL,
    _ad_off_set          int = 0)
    RETURNS TABLE(id int,
                  nota text,
                  msgCliente text,
                  contadorRutinas int,
                  estadoPlan int,
                  fechaInicialPlanificacion date,
                  fechaFinalPlanificacion date,
                  cliNombreCompleto text,
                  cliMovil text,
                  cliFechaUltimoAcceso timestamp,
                  cliFechaNacimiento date,
                  cliId int,
                  cliCorreo text,
                  predeterminadaFichaId int,
                  rows int) AS
$func$
select
    r.red_fitness_id,
    nota,
    msg_cliente,
    contador_rutinas,
    estado_plan,
    fecha_inicial_planificacion fechaInicialPlanificacion,
    fecha_final_planificacion fechaFinalPlanificacion,
    CONCAT(c.nombres, ' ', c.apellidos) cliNombreCompleto,
    c.movil cliMovil,
    c.fecha_ultimo_acceso cliFechaUltimoAcceso,
    c.fecha_nacimiento cliFechaNacimiento,
    c.security_user_id cliId,
    c.correo cliCorreo,
    r.predeterminada_ficha_id predeterminadaFichaId,
    count(*) over()::int as rows
from red_fitness r inner join cliente c on r.cliente_id = c.security_user_id
WHERE r.trainer_id = $1
AND ($2 IS NULL OR lower(CONCAT(c.nombres, ' ', c.apellidos)) like lower(CONCAT('%',$2,'%')))
AND r.flag_activo = true
ORDER BY cliNombreCompleto
LIMIT $3
OFFSET $4
$func$ LANGUAGE sql;
