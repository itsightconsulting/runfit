package com.itsight.repository;

import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.domain.dto.RedFitCliDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaPlantillaRepository extends JpaRepository<CategoriaPlantilla, Integer> {

    @Query("SELECT NEW com.itsight.domain.dto.CategoriaPlantillaDTO(C.id,C.nombre, C.tipo) FROM CategoriaPlantilla C WHERE C.trainer.id= ?1")
    List<CategoriaPlantillaDTO> findCategoriasByTrainerId(Integer trainerId);

}
