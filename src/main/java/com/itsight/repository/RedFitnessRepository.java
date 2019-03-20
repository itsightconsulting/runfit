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

    @EntityGraph(value = "redFitness.cliente")
    RedFitness findOneById(Integer id);

    @EntityGraph(value = "redFitness.cliente")
    @Query("SELECT R FROM RedFitness R JOIN FETCH R.cliente U JOIN FETCH R.trainer RU  WHERE R.trainer.codigoTrainer = ?1")
    List<RedFitness> findAllByTrainerCodigoTrainer(String codigoTrainer);

    @Modifying
    @Query("UPDATE RedFitness R SET R.nota = ?2 WHERE R.id = ?1")
    void actualizarNotaACliente(Integer id, String nota);

    @Modifying
    @Query("UPDATE RedFitness R SET R.estadoPlan = ?2, R.fechaFinalPlanificacion = ?3, R.contadorRutinas = R.contadorRutinas + ?4 WHERE R.id = ?1")
    void updatePlanStatusAndUltimoDiaPlanificacion(Integer id, int planStatus, Date diaFinalPlanificacion, int contadorRutinas);

    @Query("SELECT R.trainer.codigoTrainer FROM RedFitness R WHERE R.id = ?1")
    String findCodTrainerById(Integer id);

    @Query("SELECT R.contadorRutinas FROM RedFitness R where R.id = ?1")
    int getById(Integer id);

    @Modifying
    @Query("UPDATE RedFitness R SET R.fechaFinalPlanificacion = ?2 WHERE R.id = ?1")
    void updateUltimaFechaPlanificacionById(Integer id, Date fechaFinalPlanificacion );

    @EntityGraph(value = "redFitness.cliente")
    RedFitness findByTrainerCodigoTrainer(String codTrainer);

    @Query("SELECT R.trainer.codigoTrainer FROM RedFitness R WHERE R.id = ?1 AND R.cliente.id = ?2")
    String findCodTrainerByIdAndRunnerId(Integer id, Integer runneId);

    @Query("SELECT M.trainer.codigoTrainer FROM RedFitness M where M.cliente.id = ?1 and M.estadoPlan != 5")
    List<String> findTrainerByUsuarioId(Integer id);

    @Query("SELECT M.trainer.id FROM RedFitness M where M.cliente.id = ?1 and M.estadoPlan != 5")
    List<Integer> findTrainerIdByUsuarioId(Integer id);
}
