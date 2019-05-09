package com.itsight.repository;

import com.itsight.domain.ContactoTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoTrainerRepository extends JpaRepository<ContactoTrainer, Integer> {

    @Modifying
    @Query("UPDATE ContactoTrainer C SET C.flagLeido = ?1 WHERE C.id = ?2")
    void updateFlagLeido(boolean flag, Integer id);

    @Modifying
    @Query("UPDATE ContactoTrainer C SET C.flagLeidoFueraFecha = ?1 WHERE C.id = ?2")
    void updateFlagLeidoFueraFecha(boolean flag, Integer id);
}
