package com.itsight.repository;

import com.itsight.domain.Rutina;
import com.itsight.domain.RutinaPlantilla;
import com.itsight.domain.dto.RutinaPlantillaDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutinaPlantillaRepository extends JpaRepository<RutinaPlantilla, Integer> {

/*
    @EntityGraph(value = "rutinaPlantilla")
    List<RutinaPlantilla> findByTrainerIdOrderByIdDesc(Integer trainerId);
*/

    @Query("SELECT NEW com.itsight.domain.dto.RutinaPlantillaDTO(R.id,R.nombre, R.totalSemanas, R.dias) FROM RutinaPlantilla R WHERE R.subCategoriaPlantilla.id= ?1")
    List<RutinaPlantillaDTO> findBySubCategoriaId(Integer subCategoriaId);

    @Procedure(name = "fn_validacion_nombre_rutina_plantilla")
    Boolean findNombreRutinaPlantExiste(@Param("_nom_rutina") String nombre,  @Param("_cat_id") Integer categoriaId);

}

