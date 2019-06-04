package com.itsight.repository;

import com.itsight.domain.RedFitness;
import com.itsight.domain.dto.RedFitCliDTO;
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

    @Query("SELECT NEW com.itsight.domain.dto.RedFitCliDTO(R.id, R.nota, R.msgCliente, R.contadorRutinas, R.estadoPlan, R.fechaFinalPlanificacion, CONCAT(C.apellidos,' ', C.nombres), C.movil, C.fechaUltimoAcceso, C.id, C.correo, C.fechaNacimiento, R.predeterminadaFichaId) FROM RedFitness R JOIN R.cliente C WHERE R.trainer.id = ?1")
    List<RedFitCliDTO> findAllByTrainerId(Integer trainerId);

    @Modifying
    @Query("UPDATE RedFitness R SET R.nota = ?2 WHERE R.id = ?1")
    void actualizarNotaACliente(Integer id, String nota);

    @Modifying
    @Query("UPDATE RedFitness R SET R.estadoPlan = ?2, R.fechaFinalPlanificacion = ?3, R.contadorRutinas = R.contadorRutinas + ?4 WHERE R.id = ?1")
    void updatePlanStatusAndUltimoDiaPlanificacion(Integer id, int planStatus, Date diaFinalPlanificacion, int contadorRutinas);

    @Query("SELECT R.contadorRutinas FROM RedFitness R where R.id = ?1")
    int getById(Integer id);

    @Modifying
    @Query("UPDATE RedFitness R SET R.fechaFinalPlanificacion = ?2 WHERE R.id = ?1")
    void updateUltimaFechaPlanificacionById(Integer id, Date fechaFinalPlanificacion );

    @Query("SELECT R.trainer.id FROM RedFitness R WHERE R.id = ?1 AND R.cliente.id = ?2")
    Integer findTrainerIdByIdAndRunnerId(Integer id, Integer runneId);

    @Query("SELECT M.trainer.id FROM RedFitness M where M.cliente.id = ?1 and M.estadoPlan <> 5")
    List<Integer> findTrainerIdByUsuarioId(Integer id);
}
