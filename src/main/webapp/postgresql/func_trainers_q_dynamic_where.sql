DROP FUNCTION func_trainers_q_dynamic_where(text,text,text,text,text,integer,text,text);
CREATE OR REPLACE FUNCTION func_trainers_q_dynamic_where(
    _idiomas	text  = NULL,
    _niveles	text = NULL,
    _formasTra	text = NULL,
    _nombre	    text  = NULL,
    _acerca	    text = NULL,
    _sexo       int = NULL,
    _ubigeo 	text = NULL,
    _servicio     text = NULL,
    _limit      int = NULL,
    _offset     int = 0)
    RETURNS TABLE(id int,
                  nombreCompleto text,
                  especialidad text,
                  ubigeo text,
                  acerca text,
                  canPerValoracion int,
                  totalValoracion double precision,
                  nomImgPerfil text,
                  nomPag text,
                  tipoTrainerId int,
                  rowz int) AS
$func$
select DISTINCT id,
       nombreCompleto,
       especialidad,
       ubigeo,
       acerca,
       canPerValoracion,
       totalValoracion,
       nomImgPerfil,
       nomPag,
       tipoTrainerId,
       CAST(count(*) over() as int) rowz
from (select
    ff.trainer_id id,
    concat(jt.nombres, ' ',jt.apellidos) nombreCompleto,
    especialidad,
    ubigeo,
    acerca,
    can_per_valoracion canPerValoracion,
    total_valoracion totalValoracion,
    CONCAT(ff.uuid_fp, ff.ext_fp) nomImgPerfil,
    nom_pag nomPag,
    s2.nombre servicio,
    jt.flag_activo fg,
    ff.sexo,
    jt.tipo_trainer_id tipoTrainerId
from trainer_ficha ff
         inner join trainer jt on ff.trainer_id=jt.security_user_id
         inner join servicio s2 on ff.trainer_id = s2.trainer_id
where
        ff.trainer_id IN
        (select distinct id from (select id,
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
         ORDER BY 1
        )) as y
    WHERE
    ($4 IS NULL OR public.f_unaccent(LOWER(concat(y.nombreCompleto))) LIKE public.f_unaccent(LOWER(CONCAT('%',$4,'%')))) AND
    ($5 IS NULL OR public.f_unaccent(LOWER(y.acerca)) LIKE public.f_unaccent(LOWER(CONCAT('%',$5,'%')))) AND
    ($6 IS NULL OR y.sexo = $6) AND
    ($7 IS NULL OR y.ubigeo = $7) AND
    ($8 IS NULL OR public.f_unaccent(LOWER(y.servicio)) LIKE public.f_unaccent(LOWER(CONCAT('%', $8,'%')))) AND
     y.fg = true
    LIMIT $9
    OFFSET $10
$func$ LANGUAGE sql;
