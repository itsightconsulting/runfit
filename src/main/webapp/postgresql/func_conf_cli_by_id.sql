CREATE OR REPLACE FUNCTION func_conf_cli_by_id(
    _cli_id       int)
    RETURNS TABLE(
                     nombre text,
                     valor text
) AS
$func$
    select param->>'nombre' nombre, param->>'valor' valor from
(select
    jsonb_array_elements(parametros) param
from runfit.public.configuracion_cliente cf inner join cliente c on cf.cliente_id = c.security_user_id
WHERE security_user_id = _cli_id) as t
$func$ LANGUAGE sql;