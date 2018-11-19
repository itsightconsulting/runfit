package com.itsight.repository;

import com.itsight.domain.MiniPlantilla;
import com.itsight.domain.jsonb.DiaRutinarioPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MiniPlantillaRepository extends JpaRepository<MiniPlantilla, Integer> {

    @Query("SELECT M FROM MiniPlantilla M INNER JOIN FETCH M.especificacionSubCategoria SS  INNER JOIN FETCH SS.subCategoriaEjercicio SC INNER JOIN FETCH SC.categoriaEjercicio CE INNER JOIN FETCH CE.forest WHERE M.usuario.id = ?1 ORDER BY SS.id,SC.id")
    List<MiniPlantilla> findAllByUsuarioId(int usuarioId);

    @Query("SELECT M FROM MiniPlantilla M WHERE M.especificacionSubCategoria.id = ?2 AND M.usuario.id = ?1 ")
    MiniPlantilla findByUsuarioIdAndEspecificacionSubCategoriaId(int usuarioId, int especificacionSubCatId);

    @Modifying
    @Query(value = "INSERT INTO mini_plantilla (especificacion_sub_categoria_id, usuario_id) SELECT :id, security_user_id FROM usuario where tipo_usuario_id=2 order by 1", nativeQuery = true)
    void saveEspecificacionesMiniPlantilla(@Param("id") int id);

    @Query(value = "SELECT count(ids) FROM mini_plantilla, jsonb_array_elements(dia_rutinario_ids) as ids  WHERE especificacion_sub_categoria_id=?2 and usuario_id=?1 order by 1;", nativeQuery = true)
    int findPlantillaIdsByUsuarioIdAndEspecificacionSubCategoriaId(int id, int esSubCatId);


    @Query("SELECT M FROM MiniPlantilla M INNER JOIN FETCH M.especificacionSubCategoria SS  INNER JOIN FETCH SS.subCategoriaEjercicio SC INNER JOIN FETCH SC.categoriaEjercicio CE INNER JOIN FETCH CE.forest WHERE M.usuario.id in ?1 and M.diaRutinarioIds is not null ORDER BY SS.id,SC.id")
    List<MiniPlantilla> findAllByListUsuarioId(List<Integer> list);

    @Query("SELECT M FROM MiniPlantilla M INNER JOIN FETCH M.especificacionSubCategoria SS  INNER JOIN FETCH SS.subCategoriaEjercicio SC INNER JOIN FETCH SC.categoriaEjercicio CE INNER JOIN FETCH CE.forest WHERE M.usuario.id in ?1 and M.especificacionSubCategoria.id = ?2 and M.diaRutinarioIds is not null ORDER BY SS.id,SC.id")
    List<MiniPlantilla> findAllByListUsuarioIdBySubCategoriaId(List<Integer> list, int idsubcategoria);
}
