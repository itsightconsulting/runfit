package com.itsight.repository;

import com.itsight.domain.MiniRutina;
import com.itsight.domain.jsonb.MiRutinaPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiniRutinaRepository extends JpaRepository<MiniRutina, Integer> {

    @Query("SELECT M FROM MiniRutina M INNER JOIN FETCH M.categoria C WHERE M.trainer.id = ?1 ORDER BY M.id")
    List<MiniRutina> findAllByTrainerId(int trainerId);

    @Query("SELECT M FROM MiniRutina M WHERE M.trainer.id = ?1 AND M.categoria.id = ?2")
    MiniRutina findByTrainerIdAndEspecificacionSubCategoriaId(int trainerId, int especificacionSubCatId);

    @Query("SELECT M.categoria.id FROM MiniRutina M WHERE M.trainer.id = ?1")
    List<Integer> findAllCategoriaIdByTrainerId(int trainerId);

    @Query("SELECT M FROM MiniRutina M WHERE M.categoria.id = ?1 AND M.trainer.id = ?2")
    MiniRutina findByCategoriaIdAndTrainerId(int catId, int trainerId);
}
