CREATE OR REPLACE FUNCTION check_correo_existe(_correo text, OUT result boolean) as
$BODY$
 BEGIN
	  IF _correo !~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$' THEN

		    result := true;
    ELSEIF EXISTS(SELECT 1 FROM cliente WHERE correo = _correo limit 1) THEN

        result := true;

    ELSEIF EXISTS(SELECT 1 FROM trainer WHERE correo = _correo limit 1) THEN

        result := true;

    ELSEIF EXISTS(SELECT 1 from administrador WHERE correo = _correo limit 1) THEN

        result := true;

    ELSE

        result := false;
    END IF;

    RETURN;
END
$BODY$ language plpgsql;