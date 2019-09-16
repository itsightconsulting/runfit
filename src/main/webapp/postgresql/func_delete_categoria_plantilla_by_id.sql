CREATE OR REPLACE FUNCTION func_delete_categoria_plantilla_by_id(
    _categoria_plantilla_id  int  = NULL
)
    RETURNS boolean AS
$func$
BEGIN
    DELETE FROM dia_plantilla WHERE semana_plantilla_id IN
                                    (SELECT semana_plantilla_id FROM semana_plantilla s
                                                                         JOIN rutina_plantilla r ON s.rutina_plantilla_id = r.rutina_plantilla_id
                                     WHERE r.categoria_plantilla_id = $1);
    DELETE FROM semana_plantilla WHERE  rutina_plantilla_id  IN  ( SELECT rutina_plantilla_id FROM categoria_plantilla
                                                                   WHERE categoria_plantilla_id = $1);
    DELETE FROM rutina_plantilla WHERE categoria_plantilla_id = $1;
    DELETE FROM categoria_plantilla WHERE categoria_plantilla_id = $1;
    RETURN TRUE;
END
$func$
LANGUAGE
plpgsql;