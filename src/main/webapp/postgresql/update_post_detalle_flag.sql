-- FUNCTION: public.update_post_detalle_flag(integer, integer, boolean, text, timestamp without time zone)

-- DROP FUNCTION public.update_post_detalle_flag(integer, integer, boolean, text, timestamp without time zone);

CREATE OR REPLACE FUNCTION public.update_post_detalle_flag(
	_post_id integer,
	_cli_id integer,
	_flg boolean,
	_flg_name text,
	_fe_mod text,
	OUT res boolean)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE
AS $BODY$
DECLARE _pos int;
 BEGIN
	  _pos := (SELECT
                   pos-1
                   FROM
                       post,
                       jsonb_array_elements(detalle) WITH ORDINALITY arr(elem, pos)
                   WHERE
                   post_id = _post_id AND
                   CAST(elem->>'cliId' AS INT) = _cli_id);
	  UPDATE post set detalle = jsonb_set(detalle, CONCAT('{',_pos,'}')::text[], detalle->_pos || CONCAT('{"',_flg_name,'":', _flg::text,',"feModif":"', _fe_mod ,'"}')::jsonb) where post_id = _post_id;
	  res := true;
	  RETURN;
END
$BODY$;
