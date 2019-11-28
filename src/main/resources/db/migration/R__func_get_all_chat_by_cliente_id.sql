CREATE OR REPLACE FUNCTION func_get_all_chat_by_cliente_id(_cli_id int)
    RETURNS TABLE
            (
                id                int,
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
select rf.red_fitness_id,
       c.ultimo::text,
       c.mensajes::text,
       c.flag_leido,
       c.fecha_creacion,
       c.fecha_modificacion,
       concat(t.security_user_id, '/', regexp_replace(lower(encode(t.username::bytea, 'base64')), '=', '', 'gi'),
              '.jpg')


           fp_trainer,
       concat(t.nombres, ' ', t.apellidos) nom_trainer
from red_fitness rf
         left join chat c on rf.red_fitness_id = c.chat_id
         inner join trainer t on rf.trainer_id = t.security_user_id
where rf.cliente_id = _cli_id
$func$ LANGUAGE sql;