CREATE OR REPLACE FUNCTION func_distr_ubi_q_dynamic_where(
    _security_user_id  int  = NULL
)
    RETURNS TABLE(departamentoUb text, qtyClientesByDepartamento BIGINT) AS
$func$
SELECT substr(c.ubigeo,1,2) departamentoUb, COUNT(*) qtyClientesByDepartamento
FROM   cliente c INNER JOIN red_fitness rf ON c.security_user_id = rf.cliente_id
WHERE  ( $1 IS NULL  OR trainer_id = $1)
AND  substr(c.ubigeo,1,2) IN (SELECT DISTINCT dep FROM ub_peru)
GROUP BY substr(c.ubigeo,1,2)
ORDER BY substr(c.ubigeo,1,2) ASC;
$func$  LANGUAGE sql;
