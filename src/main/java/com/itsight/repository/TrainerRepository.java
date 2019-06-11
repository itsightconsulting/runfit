package com.itsight.repository;

import com.itsight.domain.Trainer;
import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    @Modifying
    @Query(value = "UPDATE Trainer T SET T.fechaUltimoAcceso =?1 WHERE T.id = ?2")
    void updateFechaUltimoAcceso(Date fechaUltimoAcceso, Integer id);

    @EntityGraph(value = "trainer.all")
    List<Trainer> findAllByOrderByIdDesc();

    @EntityGraph(value = "trainer.all")
    List<Trainer> findAllByFlagActivoOrderByIdDesc(Boolean flagActivo);

    @Query("SELECT NEW com.itsight.domain.pojo.UsuarioPOJO(T.id, T.fechaCreacion, CONCAT(T.apellidos,' ',T.nombres), T.flagActivo, T.correo, T.username, T.fechaUltimoAcceso, 'Trainer') FROM Trainer T INNER JOIN T.tipoDocumento D WHERE LOWER(CONCAT(T.apellidos,' ',T.nombres)) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<UsuarioPOJO> findByNombreCompleto(String nombreCompleto);

    @Query(nativeQuery = true)
    UsuGenDTO getById(Integer id);

    @Modifying
    @Query(value = "UPDATE Trainer T SET T.flagActivo =?2 WHERE T.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE Trainer T SET T.flagRutinarioCe =?2 WHERE T.id = ?1")
    void updateFlagRutinarioCeById(Integer id, boolean flagRutinarioCe);

    @Query(value = "SELECT tipo_trainer_id FROM trainer WHERE security_user_id = ?1", nativeQuery = true)
    Integer getTipoTrainerIdById(Integer id);

    @Query(value = "SELECT correo FROM trainer WHERE security_user_id = ?1", nativeQuery = true)
    String getCorreoById(Integer id);

    @Modifying
    @Query(value = "update trainer set flag_activo=true where security_user_id in (select trainer_id from trainer_ficha where tr_emp_id = ?1)", nativeQuery = true)
    void updateMultipleEstadoByTrEmpId(Integer id);

    @Query(value = "SELECT username FROM trainer WHERE security_user_id = ?1", nativeQuery = true)
    String getUsernameById(int id);

    /*@Query(value = "SELECT NEW Trainer(codigoTrainer, nombres, apellidos, apellidoMaterno) FROM Trainer T WHERE T.tipoUsuario.id = 2")
    List<Trainer> findAllTrainers();

    @Query(value = "SELECT T FROM Trainer T INNER JOIN FETCH T.tipoUsuario P JOIN FETCH T.tipoDocumento D  WHERE T.username = ?1")
    Trainer findByUsername(String username);*/
}
