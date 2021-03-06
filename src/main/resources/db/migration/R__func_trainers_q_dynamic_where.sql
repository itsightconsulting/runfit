CREATE OR REPLACE FUNCTION func_trainers_q_dynamic_where(
    _idiomas	text  = NULL,
    _niveles	text = NULL,
    _formasTra	text = NULL,
    _nombre	    text  = NULL,
    _acerca	    text = NULL,
    _sexo       int = NULL,
    _ubigeo 	text = NULL,
    _servicio   text = NULL,
    _valoracion double precision = NULL,
    _limit      int = NULL,
    _offset     int = 0)
    RETURNS TABLE(id int,
                  nombreCompleto text,
                  especialidad text,
                  ubigeo text,
                  nomUbigeo text,
                  acerca text,
                  canPerValoracion int,
                  totalValoracion double precision,
                  nomImgPerfil text,
                  nomPag text,
                  tipoTrainerId int,
                  rowz int) AS
$func$

select *,
       CAST(count(*) over() as int) rowz from (
select DISTINCT id,
       nombreCompleto,
       especialidad,
       ubigeo,
       nomUbigeo,
       acerca,
       canPerValoracion,
       totalValoracion,
       nomImgPerfil,
       nomPag,
       tipoTrainerId
from (select
    jt.security_user_id id,
    concat(jt.nombres, ' ',jt.apellidos) nombreCompleto,
    especialidad,
    ubigeo,
    nom_ubigeo nomUbigeo,
    acerca,
    can_per_valoracion canPerValoracion,
    total_valoracion totalValoracion,
    CONCAT(regexp_replace(lower(encode(username::bytea, 'base64')), '=', '', 'gi'), '.jpg') nomImgPerfil,
    nom_pag nomPag,
    s2.nombre servicio,
    jt.flag_activo fg,
    jt.sexo,
    jt.tipo_trainer_id tipoTrainerId
from trainer jt
         inner join trainer_ficha ff on ff.trainer_id=jt.security_user_id
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
    ($7 IS NULL OR public.f_unaccent(LOWER(y.nomUbigeo)) LIKE public.f_unaccent(LOWER(CONCAT('%',$7,'%')))) AND
    ($8 IS NULL OR public.f_unaccent(LOWER(y.servicio)) LIKE public.f_unaccent(LOWER(CONCAT('%', $8,'%')))) AND
    ($9 IS NULL OR coalesce(totalValoracion/NULLIF(canPerValoracion,0), 0) >= $9) AND
    y.fg = true) as fx
    ORDER BY fx.totalValoracion DESC, fx.id ASC
    LIMIT $10
    OFFSET $11
$func$ LANGUAGE sql;
