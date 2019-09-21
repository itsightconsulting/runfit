CREATE OR REPLACE FUNCTION check_red_fitness_exist_by_trainer_id_and_cliente_id(_trainer_id int, _cliente_id int, OUT result boolean) as
$BODY$
BEGIN
    IF EXISTS(SELECT 1 FROM red_fitness
              WHERE trainer_id = _trainer_id
                AND cliente_id = _cliente_id limit 1) THEN
        result := true;
    ELSE
        result := false;
    END IF;

    RETURN;
END
$BODY$ language plpgsql;