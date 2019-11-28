CREATE OR REPLACE FUNCTION func_delete_rutina_plantilla_by_id(
    _rutina_plantilla_id  int  = NULL
)
    RETURNS boolean AS
$func$
BEGIN
    DELETE FROM dia_plantilla WHERE semana_plantilla_id IN
                                    (SELECT semana_plantilla_id
                                     FROM semana_plantilla
                                     WHERE rutina_plantilla_id = $1);
    DELETE FROM semana_plantilla WHERE rutina_plantilla_id = $1;
    DELETE FROM rutina_plantilla WHERE rutina_plantilla_id = $1;
    RETURN TRUE;
END
$func$
LANGUAGE
  plpgsql;
