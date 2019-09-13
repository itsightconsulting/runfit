CREATE OR REPLACE FUNCTION func_get_all_chat_by_cliente_id(_cli_id int)
    RETURNS TABLE
            (
                ultimo            text,
                mensajes          text,
                flagLeido         boolean,
                fechaCreacion     timestamp,
                fechaModificacion timestamp,
                fpTrainer         text,
                nomTrainer        text
            )
AS
$func$
select ultimo::text, mensajes::text, flag_leido, fecha_creacion, fecha_modificacion, fp_trainer, nom_trainer
from chat
where chat_id IN (
    select red_fitness_id
    from runfit.public.red_fitness
    where cliente_id = _cli_id
)
$func$ LANGUAGE sql;

