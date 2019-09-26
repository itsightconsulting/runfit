package com.itsight.repository;

import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.domain.dto.RedFitCliDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaPlantillaRepository extends JpaRepository<CategoriaPlantilla, Integer> {

    @Query("SELECT NEW com.itsight.domain.dto.CategoriaPlantillaDTO(C.id,C.nombre, C.tipo, C.favorito) FROM CategoriaPlantilla C WHERE C.trainer.id= ?1")
    List<CategoriaPlantillaDTO> findCategoriasByTrainerId(Integer trainerId);

    @Query("SELECT NEW com.itsight.domain.dto.CategoriaPlantillaDTO(C.id,C.nombre, C.tipo,C.favorito) FROM CategoriaPlantilla C WHERE C.trainer.id= ?1 AND C.tipo = ?2")
    List<CategoriaPlantillaDTO> findCategoriasByTrainerIdAndTipo(Integer trainerId, int tipoRutina);

    @Procedure(name = "fn_validacion_nombre_categoria")
    Boolean findNombreCategPlantExiste(@Param("_nom_cat") String nombre , @Param("_trainer_id") Integer trainerId , @Param("_tipo") Integer tipo );

}
