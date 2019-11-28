CREATE OR REPLACE FUNCTION check_rutina_plant_predisen_existe(_nom_rutina text, _sub_cat_id integer  ,OUT result boolean) as
$BODY$
BEGIN
    IF EXISTS(SELECT 1 FROM rutina_plantilla WHERE upper(nombre) = upper(_nom_rutina) AND sub_categoria_plantilla_id = _sub_cat_id LIMIT 1) THEN
        result := true;
    ELSE
        result := false;
    END IF;
    RETURN;
END
$BODY$ language plpgsql;
