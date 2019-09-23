
CREATE OR REPLACE FUNCTION func_distr_ubi_provincia_q_dynamic_where(
    _security_user_id  int  = NULL
)
    RETURNS TABLE(provinciaUb text, provinciaNombre text, qtyClientesByProvincia BIGINT) AS
$func$
SELECT prov.provinciaUb, ub.ub , prov.qtyClientesByProvincia
FROM
    (SELECT substr(c.ubigeo,1,4) provinciaUb, COUNT(distinct security_user_id) qtyClientesByProvincia
     FROM   cliente c INNER JOIN red_fitness rf ON c.security_user_id = rf.cliente_id
                      INNER JOIN rutina r ON r.red_fitness_id = rf.red_fitness_id
     WHERE  ( $1 IS NULL  OR trainer_id = $1)
       AND  substr(c.ubigeo,1,4) IN (SELECT DISTINCT substr(ub_peru_id,1,4) FROM ub_peru)
     GROUP BY substr(c.ubigeo,1,4)
     ORDER BY substr(c.ubigeo,1,4) ASC) as prov INNER JOIN ub_peru ub
                                                           ON prov.provinciaUb = substr(ub.ub_peru_id,1,4)
WHERE substr(ub.ub_peru_id, 5 ,2) = '00'
ORDER BY qtyClientesByProvincia desc
LIMIT 25;
$func$  LANGUAGE sql;
