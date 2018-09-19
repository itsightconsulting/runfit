package com.itsight.repository;

import com.itsight.domain.MiMiniRutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiMiniRutinaRepository extends JpaRepository<MiMiniRutina, Integer> {

    List<MiMiniRutina> findAllByIdIn(List<Integer> ids);
}
