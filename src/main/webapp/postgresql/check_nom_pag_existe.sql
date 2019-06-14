CREATE OR REPLACE FUNCTION check_nom_pag_existe(_nom_pag text, OUT result boolean) as
$BODY$
 BEGIN
	  IF EXISTS(SELECT 1 FROM trainer_ficha WHERE nom_pag = _nom_pag limit 1) THEN
        result := true;
      ELSE
        result := false;
    END IF;

    RETURN;
END
$BODY$ language plpgsql;