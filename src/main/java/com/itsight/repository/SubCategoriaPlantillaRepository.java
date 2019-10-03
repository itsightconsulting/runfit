package com.itsight.repository;

import com.itsight.domain.SubCategoriaPlantilla;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.domain.dto.SubCategoriaPlantillaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoriaPlantillaRepository extends JpaRepository<SubCategoriaPlantilla, Integer> {

    @Query("SELECT NEW com.itsight.domain.dto.SubCategoriaPlantillaDTO(SC.id,SC.nombre,SC.favorito) FROM SubCategoriaPlantilla SC WHERE SC.categoriaPlantilla.id= ?1")
    List<SubCategoriaPlantillaDTO> findSubCategoriasByCategoriaId(Integer categoriaId);


    @Procedure(name = "fn_validacion_nombre_sub_categoria")
    Boolean findNombreSubCategPlantExiste(@Param("_nom_sub_cat") String nombre , @Param("_cat_id") Integer categoriaId);


}
