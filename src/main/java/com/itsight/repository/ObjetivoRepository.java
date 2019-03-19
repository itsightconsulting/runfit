package com.itsight.repository;

import com.itsight.domain.Objetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetivoRepository extends JpaRepository<Objetivo, Integer> {

    List<Objetivo> findAllByFlagActivo(Boolean flagActivo);

    List<Objetivo> findAllByNombreContainingIgnoreCase(String nombres);

    @Modifying
    @Query(value = "UPDATE Objetivo O SET O.flagActivo =?2 WHERE O.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

}
