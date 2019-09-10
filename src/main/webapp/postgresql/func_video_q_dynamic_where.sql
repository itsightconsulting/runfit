CREATE OR REPLACE FUNCTION func_video_q_dynamic_where(
    _nom                 text  = NULL,
    _cat_vid_id          int  = NULL,
    _sub_cat_vid_id      int  = NULL,
    _grup_vid_id         int  = NULL,
    _flag                boolean  = NULL,
    _ad_limit            int = NULL,
    _ad_off_set          int = 0)
    RETURNS TABLE(id int,
                  nombre text,
                  rutaWeb text,
                  peso text,
                  duracion text,
                  uuid text,
                  thumbnail text,
                  flagActivo boolean,
                  subCatId int,
                  nomSubCat text,
                  catId int,
                  nomCatVid text,
                  rows int) AS
$func$
select v.video_id id,
       v.nombre,
       v.ruta_web || '?v' || v.version rutaWeb,
       v.peso,
       v.duracion,
       cast(v.uuid as text),
       v.thumbnail::text,
       v.flag_activo flagActivo,
       scv.sub_categoria_video_id subCatId,
       scv.nombre nomSubCat,
       cv.categoria_video_id catId,
       cv.nombre nomCatVid,
       CAST(count(*) over() as int) rowz
from video v
         inner join sub_categoria_video scv on v.sub_categoria_video_id = scv.sub_categoria_video_id
         inner join categoria_video cv on scv.categoria_video_id = cv.categoria_video_id
         inner join grupo_video gv on cv.grupo_video_id = gv.grupo_video_id
WHERE
    ($1 IS NULL OR public.f_unaccent(LOWER(concat(v.nombre))) LIKE public.f_unaccent(LOWER(CONCAT('%',$1,'%')))) AND
    ($2 IS NULL OR cv.categoria_video_id = $2) AND
    ($3 IS NULL OR scv.sub_categoria_video_id = $3) AND
    ($4 IS NULL or gv.grupo_video_id = $4 ) AND
    ($5 IS NULL OR v.flag_activo = $5)
ORDER BY v.video_id desc
LIMIT $6
    OFFSET $7
$func$ LANGUAGE sql;