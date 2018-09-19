package com.itsight.repository;

import com.itsight.domain.RutinaPlantilla;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutinaPlantillaRepository extends JpaRepository<RutinaPlantilla, Integer> {

    @EntityGraph(value = "rutinaPlantilla")
    List<RutinaPlantilla> findByUsuarioIdOrderByIdDesc(int trainerId);
}
