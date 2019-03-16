package com.itsight.repository;

import com.itsight.domain.Trainer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    @Query(value = "SELECT T.codigoTrainer FROM Trainer T WHERE T.id = ?1")
    String findCodigoTrainerById(int id);

    @Modifying
    @Query(value = "UPDATE Trainer T SET T.fechaUltimoAcceso =?1 WHERE T.id = ?2")
    void updateFechaUltimoAcceso(Date fechaUltimoAcceso, int id);

    @EntityGraph(value = "trainer.all")
    List<Trainer> findAllByOrderByIdDesc();

    @EntityGraph(value = "trainer.all")
    List<Trainer> findAllByFlagActivoOrderByIdDesc(Boolean flagActivo);

    @Query("SELECT T FROM Trainer T INNER JOIN FETCH T.tipoUsuario P JOIN FETCH T.tipoDocumento D  WHERE LOWER(CONCAT(T.apellidos,' ',T.nombres)) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<Trainer> findByNombreCompleto(String nombreCompleto);

    @EntityGraph(value = "trainer.all")
    Trainer findById(int id);

    @Modifying
    @Query(value = "UPDATE Trainer T SET T.flagActivo =?2 WHERE T.id = ?1")
    void updateFlagActivoById(int id, boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE Trainer T SET T.flagRutinarioCe =?2 WHERE T.id = ?1")
    void updateFlagRutinarioCeById(int id, boolean flagRutinarioCe);

    @Query(value = "SELECT T.correo FROM Trainer T WHERE T.correo = ?1")
    List<Trainer> findAllByCorreo(String correo);

    /*@Query(value = "SELECT NEW Trainer(codigoTrainer, nombres, apellidos, apellidoMaterno) FROM Trainer T WHERE T.tipoUsuario.id = 2")
    List<Trainer> findAllTrainers();

    @Query(value = "SELECT T FROM Trainer T INNER JOIN FETCH T.tipoUsuario P JOIN FETCH T.tipoDocumento D  WHERE T.username = ?1")
    Trainer findByUsername(String username);*/
}
