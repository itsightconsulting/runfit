package com.itsight.repository;

import com.itsight.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    @Query(value = "SELECT T.codigoTrainer FROM Trainer T WHERE T.id = ?1")
    String findCodigoTrainerById(int id);

    @Modifying
    @Query(value = "UPDATE Trainer T SET T.fechaUltimoAcceso =?1 WHERE T.id = ?2")
    void updateFechaUltimoAcceso(Date fechaUltimoAcceso, int id);
}
