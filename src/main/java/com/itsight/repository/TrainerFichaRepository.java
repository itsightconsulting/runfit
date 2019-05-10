package com.itsight.repository;

import com.itsight.domain.TrainerFicha;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TrainerFichaRepository extends JpaRepository<TrainerFicha, Integer> {

    @Query(nativeQuery = true)
    List<TrainerFichaPOJO> findAllWithFgEnt();

    @EntityGraph(value = "trainerFicha.trainer")
    TrainerFicha findByNomPag(String nomPag);

    @Query(nativeQuery = true)
    TrainerFichaPOJO findByNomPagPar(String nomPag);

    @Query(nativeQuery = true)
    TrainerFichaPOJO findByTrainerId(Integer trainerId);
}
