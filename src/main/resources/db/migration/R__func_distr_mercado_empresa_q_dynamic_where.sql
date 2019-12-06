CREATE OR REPLACE FUNCTION func_distr_mercado_empresa_q_dynamic_where(
    _security_user_id  int  = NULL
)
    RETURNS TABLE(
                     id int,
                     tipoCanalVentaId int,
                     condicionAnatomica text,
                     fechaNacimiento date,
                     ubigeo text,
                     sexo int,
                     fechaCreacion timestamp,
                     predeterminadaFichaId int
                 ) AS
$func$
select
          c.security_user_id,
          tipo_canal_venta_id,
          condicion_anatomica :: text,
          fecha_nacimiento,
          ubigeo,
          c.sexo,
          r.fecha_creacion,
          rf.predeterminada_ficha_id
      from runfit.public.cliente_fitness cf inner join cliente c  on cf.cliente_id = c.security_user_id inner join red_fitness rf on c.security_user_id = rf.cliente_id
                                            inner join rutina r on rf.red_fitness_id = r.red_fitness_id
      where
      ($1 is null or (rf.trainer_id in ( select tf.trainer_id from trainer_ficha tf where tf.tr_emp_id = $1)));
$func$ LANGUAGE sql;
