CREATE OR REPLACE FUNCTION func_delete_sub_categoria_plantilla_by_id(
    _sub_categoria_plantilla_id  int  = NULL
)
    RETURNS boolean AS
$func$
BEGIN
    DELETE FROM dia_plantilla d WHERE d.semana_plantilla_id IN
                                      (SELECT s.semana_plantilla_id FROM semana_plantilla s
                                                                             JOIN rutina_plantilla r ON s.rutina_plantilla_id = r.rutina_plantilla_id
                                       WHERE r.sub_categoria_plantilla_id = $1);
    DELETE FROM semana_plantilla WHERE  rutina_plantilla_id  IN  ( SELECT rutina_plantilla_id FROM rutina_plantilla
                                                                   WHERE sub_categoria_plantilla_id = $1);
    DELETE FROM rutina_plantilla WHERE sub_categoria_plantilla_id = $1;
    DELETE FROM sub_categoria_plantilla WHERE sub_categoria_plantilla_id = $1;
    RETURN TRUE;
END
$func$
    LANGUAGE
        plpgsql;
