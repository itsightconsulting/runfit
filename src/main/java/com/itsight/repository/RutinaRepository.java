package com.itsight.repository;

import com.itsight.domain.Rutina;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Integer> {

    @EntityGraph(value = "rutina")
    List<Rutina> findByUsuarioIdOrderByIdDesc(int clienteId);

    @EntityGraph(value = "rutina")
    @Query("SELECT R FROM Rutina R WHERE R.redFitness.id = ?1 ORDER BY 1 DESC")
    List<Rutina> findFirstByRedFitnessIdOrderByIdDesc(int redFitnessId, Pageable pageable);

    @Query("SELECT R FROM Rutina R JOIN FETCH R.lstSemana S WHERE R.id = ?1")
    Rutina findOneWithOneWeekById(int id);

    @Modifying
    @Query("UPDATE Rutina R SET R.semanaIds = ?2 WHERE R.id = ?1")
    void updateSemanaIds(int id, int[] semanaIds);

    @Modifying
    @Query("UPDATE Rutina R SET R.totalSemanas = ?2 WHERE R.id = ?1")
    void updateTotalSemanas(int id, int totalSemanas);

    @Query("SELECT R.redFitness.id FROM Rutina R WHERE R.id = ?1")
    int findRedFitnessIdById(int rutinaId);
}
