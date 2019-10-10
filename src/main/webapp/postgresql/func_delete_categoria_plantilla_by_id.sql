CREATE OR REPLACE FUNCTION func_delete_categoria_plantilla_by_id(
    _categoria_plantilla_id  int  = NULL
)
    RETURNS boolean AS
$func$
BEGIN
    DELETE FROM dia_plantilla d WHERE d.semana_plantilla_id IN
                                      (SELECT s.semana_plantilla_id FROM semana_plantilla s
                                                                            INNER JOIN rutina_plantilla r ON s.rutina_plantilla_id = r.rutina_plantilla_id
                                                                            INNER JOIN sub_categoria_plantilla scp ON r.sub_categoria_plantilla_id = scp.sub_categoria_plantilla_id
                                       WHERE scp.categoria_plantilla_id = $1);
    DELETE FROM semana_plantilla WHERE  rutina_plantilla_id  IN  ( SELECT rutina_plantilla_id FROM rutina_plantilla r INNER JOIN
                                                                   sub_categoria_plantilla scp ON r.sub_categoria_plantilla_id = scp.sub_categoria_plantilla_id
                                                                   WHERE scp.categoria_plantilla_id = $1);
    DELETE FROM rutina_plantilla WHERE sub_categoria_plantilla_id IN (SELECT sub_categoria_plantilla_id from sub_categoria_plantilla
                                                                      WHERE categoria_plantilla_id = $1);
    DELETE FROM sub_categoria_plantilla WHERE categoria_plantilla_id = $1;
    DELETE FROM categoria_plantilla WHERE categoria_plantilla_id = $1;
    RETURN TRUE;
END
$func$
LANGUAGE
 plpgsql;
