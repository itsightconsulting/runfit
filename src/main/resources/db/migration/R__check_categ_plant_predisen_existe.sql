CREATE OR REPLACE FUNCTION check_categ_plant_predisen_existe(_nom_cat text, _trainer_id integer, _tipo integer  ,OUT result boolean) as
$BODY$
BEGIN
    IF EXISTS(SELECT 1 FROM categoria_plantilla WHERE upper(nombre) = upper(_nom_cat) AND tipo = _tipo AND trainer_id =_trainer_id LIMIT 1) THEN
        result := true;
    ELSE
        result := false;
    END IF;
    RETURN;
END
$BODY$ language plpgsql;


