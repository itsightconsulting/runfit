CREATE OR REPLACE FUNCTION get_correo_by_id(_user_id int, OUT result text) as
$BODY$
BEGIN
    SELECT into result correo FROM cliente WHERE security_user_id = _user_id;
    IF result IS NULL THEN
        SELECT into result correo FROM trainer WHERE security_user_id = _user_id;
    END IF;
    IF result IS NULL THEN
        SELECT into result correo FROM administrador WHERE security_user_id = _user_id;
    END IF;
    IF result IS NULL THEN
        SELECT into result correo FROM visitante WHERE security_user_id = _user_id;
    END IF;
    RETURN;
END
$BODY$ language plpgsql;

