package com.itsight.repository;

import com.itsight.domain.RedFitness;
import com.itsight.domain.dto.RedFitCliDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RedFitnessRepository extends JpaRepository<RedFitness, Integer> {

    @EntityGraph(value = "redFitness.cliente")
    RedFitness findOneById(Integer id);

    /*@Query("SELECT NEW com.itsight.domain.dto.RedFitCliDTO(R.id, R.nota, R.msgCliente, R.contadorRutinas, R.estadoPlan, R.fechaInicialPlanificacion, R.fechaFinalPlanificacion, CONCAT(C.apellidos,' ', C.nombres), C.movil, C.fechaUltimoAcceso, C.id, C.correo, C.fechaNacimiento, R.predeterminadaFichaId) FROM RedFitness R JOIN R.cliente C WHERE R.trainer.id = ?1 AND LOWER(CONCAT(C.nombres,' ',C.apellidos)) LIKE LOWER(CONCAT('%',?2,'%'))")
    List<RedFitCliDTO> findAllByTrainerIdAndNombreCliente(Integer trainerId, String nombres);*/

    @Modifying
    @Query("UPDATE RedFitness R SET R.nota = ?2 WHERE R.id = ?1")
    void actualizarNotaACliente(Integer id, String nota);

    @Modifying
    @Query("UPDATE RedFitness R SET R.estadoPlan = ?2,  R.fechaInicialPlanificacion= CASE WHEN ( R.fechaInicialPlanificacion < ?3 ) THEN  R.fechaInicialPlanificacion ELSE ?3 END, R.fechaFinalPlanificacion = CASE WHEN (R.fechaFinalPlanificacion <?4) THEN ?4 ELSE R.fechaFinalPlanificacion END, R.contadorRutinas = R.contadorRutinas + ?5 WHERE R.id = ?1")
    void updatePlanStatusAndUltimoDiaPlanificacion(Integer id, int planStatus, Date fechaInicialPlanificacion, Date diaFinalPlanificacion, int contadorRutinas);

    @Query("SELECT R.contadorRutinas FROM RedFitness R where R.id = ?1")
    int getById(Integer id);

    @Modifying
    @Query("UPDATE RedFitness R SET R.fechaFinalPlanificacion = ?2 WHERE R.id = ?1")
    void updateUltimaFechaPlanificacionById(Integer id, Date fechaFinalPlanificacion );

    @Query("SELECT R.trainer.id FROM RedFitness R WHERE R.id = ?1 AND R.cliente.id = ?2")
    Integer findTrainerIdByIdAndRunnerId(Integer id, Integer runneId);

    @Query("SELECT M.trainer.id FROM RedFitness M where M.cliente.id = ?1 and M.estadoPlan <> 5")
    List<Integer> findTrainerIdByUsuarioId(Integer id);

    @Query(" SELECT M.trainer.id FROM RedFitness M where M.id IN  (SELECT MAX(R.id) from RedFitness R where R.cliente.id = ?1 and M.estadoPlan <> 5) ")
    Integer findTrainerIdUltimaRutinaByUsuarioId(Integer id);

    @Query(value= "select string_agg(c.correo, ',') from red_fitness rf INNER JOIN cliente c ON rf.cliente_id=c.security_user_id where trainer_id=?1 and rf.flag_activo=true", nativeQuery = true)
    String getAllRunnerMailsByTrainerId(Integer id);

    @Query("SELECT R.id FROM RedFitness R WHERE R.cliente.id = ?1 AND R.trainer.id = ?2")
    Integer findIdByRunnerIdAndTrainerId(int runneId, Integer trainerId);

    @Modifying
    @Query("UPDATE RedFitness R SET R.flagActivo = ?2 WHERE R.id = ?1")
    void updateFlagActivoById(Integer id, boolean flag);

    @Modifying
    @Query("UPDATE RedFitness R SET R.flagActivo = ?3 WHERE R.id = ?1 AND R.trainer.id = ?2")
    void updateFlagActivoByIdAndTrainerId(int id, Integer trainerId, boolean flagActivo);

    @Query("SELECT NEW com.itsight.domain.dto.RedFitCliDTO(R.id,concat(C.nombres,' ', C.apellidos), R.fechaCreacion, C.id ) FROM RedFitness R JOIN R.cliente C WHERE R.trainer.id = ?1 AND R.flagActivo = false AND to_char(R.fechaCreacion, 'YYYY-MM') = ?2")
    List<RedFitCliDTO> findClientesSuspendidosByTrainerId(Integer trainerId, String mes);

    @Query(value="SELECT string_agg(PS.periodo,',') mesesCliSuspendidos FROM (SELECT DISTINCT to_char(RF.fecha_creacion,'YYYYMM') AS periodo FROM red_fitness RF INNER JOIN cliente C ON rf.cliente_id = c.security_user_id  WHERE RF.trainer_id = ?1 AND RF.flag_activo = false ORDER BY periodo ASC) PS",nativeQuery = true)
    String getMesesCliSuspendidos(Integer trainerId);

    @Procedure(name = "fn_validacion_exists_by_trainer_id_and_cliente_id")
    Boolean checkExistsByTrainerIdAndClienteId(Integer trainerId, Integer clienteId);

}
