CREATE OR REPLACE FUNCTION func_get_count_departamento_cliente()
    RETURNS TABLE(departamentoUb text, qtyClientesByDepartamento BIGINT) AS
$func$
SELECT substr(c.ubigeo,1,2) departamentoUb, COUNT(*) qtyClientesByDepartamento
FROM   cliente c
WHERE  substr(c.ubigeo,1,2) in (select distinct dep from ub_peru)
GROUP BY substr(c.ubigeo,1,2)
ORDER BY substr(c.ubigeo,1,2) ASC;
$func$  LANGUAGE sql;
