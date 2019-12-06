CREATE OR REPLACE FUNCTION func_distr_ubi_lima_distrito_empresa_q_dynamic_where(
    _security_user_id  int  = NULL
)
    RETURNS TABLE(distritoUb text, distritoNombre text, qtyClientesByDistrito BIGINT) AS
$func$
SELECT distr.ubigeo, ub.ub , distr.qtyClientesByDistrito
FROM
    (SELECT c.ubigeo, COUNT(distinct security_user_id) qtyClientesByDistrito
     FROM   cliente c INNER JOIN red_fitness rf ON c.security_user_id = rf.cliente_id
                      INNER JOIN rutina r ON r.red_fitness_id = rf.red_fitness_id
     WHERE        ($1 is null or (rf.trainer_id in
     ( select tf.trainer_id from trainer_ficha tf where tf.tr_emp_id = $1)))
     AND c.ubigeo IN (SELECT DISTINCT ub_peru_id FROM ub_peru)
     GROUP BY c.ubigeo
     ORDER BY c.ubigeo ASC) as distr INNER JOIN ub_peru ub
                                                           ON distr.ubigeo = ub.ub_peru_id
WHERE substr(ub.ub_peru_id, 5 ,2) <> '00'
AND ( substr(ub.ub_peru_id, 1,4) = '1501' or substr(ub.ub_peru_id,1,4) = '0701' )
ORDER BY qtyClientesByDistrito desc
LIMIT 25;
$func$ LANGUAGE sql;
