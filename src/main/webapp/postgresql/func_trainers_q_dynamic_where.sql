CREATE OR REPLACE FUNCTION func_trainers_q_dynamic_where(
    _idiomas	text  = NULL,
    _niveles	text = NULL,
    _formasTra	text = NULL,
    _nombre	    text  = NULL,
    _acerca	    text = NULL,
    _sexo       int = NULL,
    _ubigeo 	text = NULL,
    _servicio     text = NULL)
    RETURNS TABLE(id int,
                  nombreCompleto text,
                  especialidad text,
                  ubigeo text,
                  acerca text,
                  canPerValoracion int,
                  totalValoracion double precision,
                  nomImgPerfil text,
                  servicios text,
                  nomPag text) AS
$func$
select
    ff.trainer_id id,
    concat(jt.nombres, ' ',jt.apellidos) nombreCompleto,
    especialidad,
    ubigeo,
    acerca,
    can_per_valoracion canPerValoracion,
    total_valoracion totalValoracion,
    CONCAT(ff.uuid_fp, ff.ext_fp) nomImgPerfil,
    servicios::text servicios,
    nom_pag nomPag
from trainer_ficha ff
         inner join trainer jt on ff.trainer_id=jt.security_user_id
where
    ($4 IS NULL OR LOWER(concat(jt.nombres, ' ',jt.apellidos)) LIKE LOWER(CONCAT('%',$4,'%'))) AND
    ($5 IS NULL OR LOWER(ff.acerca) LIKE LOWER(CONCAT('%',$5,'%'))) AND
    ($6 IS NULL OR ff.sexo = $6) AND
    ($7 IS NULL OR jt.ubigeo = $7) AND
        jt.flag_activo = true AND
        ff.trainer_id IN
        (select id from (select id,
                                idioma,
                                nivel,
                                unnest(string_to_array(formasTrabajo, '|')) ft
                         from (
                                  select 	id,
                                            idioma,
                                            unnest(string_to_array(niveles, '|')) nivel,
                                            formas_trabajo formasTrabajo
                                  from (select * from(
                                                         SELECT trainer_id id,
                                                                unnest(string_to_array(idiomas, '|')) idioma,
                                                                niveles,
                                                                formas_trabajo
                                                         from trainer_ficha
                                                     )  as t  ) as f ) as a ) as b
         where
             ($1 IS NULL OR b.idioma = any(string_to_array($1,'|'))) AND
             ($2 IS NULL OR b.nivel = any(string_to_array($2,'|'))) AND
             ($3 IS NULL OR b.ft = any(string_to_array($3,'|')))
         UNION ALL
         SELECT s.trainer_id id FROM servicio s WHERE LOWER(s.nombre) LIKE CONCAT('%', $8,'%')
         ORDER BY 1
        )
$func$ LANGUAGE sql;
