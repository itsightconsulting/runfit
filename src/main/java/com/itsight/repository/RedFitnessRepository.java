package com.itsight.repository;

import com.itsight.domain.RedFitness;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RedFitnessRepository extends JpaRepository<RedFitness, Integer> {

    @EntityGraph(value = "redFitness.integrante")
    RedFitness findOneById(int id);

    @EntityGraph(value = "redFitness.integrante")
    @Query("SELECT R FROM RedFitness R JOIN FETCH R.integrante U JOIN FETCH R.trainer RU  WHERE R.trainer.codigoTrainer = ?1")
    List<RedFitness> findAllByTrainerCodigoTrainer(String codigoTrainer);

    @Modifying
    @Query("UPDATE RedFitness R SET R.nota = ?2 WHERE R.id = ?1")
    void actualizarNotaAIntegrante(int id, String nota);

    @Modifying
    @Query("UPDATE RedFitness R SET R.estadoPlan = ?2, R.fechaFinalPlanificacion = ?3, R.contadorRutinas = ?4 WHERE R.id = ?1")
    void updatePlanStatusAndUltimoDiaPlanificacion(int id, int planStatus, Date diaFinalPlanificacion, int contadorRutinas);

    @Query("SELECT R.trainer.codigoTrainer FROM RedFitness R WHERE R.id = ?1")
    String findCodTrainerById(int id);

    @Query("SELECT R.contadorRutinas FROM RedFitness R where R.id = ?1")
    int getById(int id);

    @Modifying
    @Query("UPDATE RedFitness R SET R.fechaFinalPlanificacion = ?2 WHERE R.id = ?1")
    void updateUltimaFechaPlanificacionById(int id, Date fechaFinalPlanificacion );
}
