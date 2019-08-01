CREATE OR REPLACE FUNCTION func_distribucion_estadistica_cliente(
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
          sexo,
          r.fecha_creacion,
          rf.predeterminada_ficha_id
      from runfit.public.cliente_fitness cf inner join cliente c  on cf.cliente_id = c.security_user_id inner join red_fitness rf on c.security_user_id = rf.cliente_id
                                            inner join rutina r on rf.red_fitness_id = r.red_fitness_id;
$func$ LANGUAGE sql;

