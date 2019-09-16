package com.itsight.repository;

import com.itsight.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Modifying
    @Query(value = "INSERT INTO chat(chat_id, ultimo, mensajes, fecha_creacion, flag_leido, fp_trainer, nom_trainer) " +
                    "values (?1, cast(?2 as jsonb), cast(?3 as jsonb), ?4, false, ?5, ?6)",
            nativeQuery = true)
    void registrar(Integer id, String ultimo, String msgs, Date fechaCreacion, String fpTrainer, String nomTrainer);

    @Query(value = "SELECT C.flagLeido from Chat C where C.id = ?1")
    Boolean getFlagLeidoById(Integer id);

    @Modifying
    @Query(value = "UPDATE Chat C SET C.flagLeido = true where C.id = ?1")
    void updateFlagById(Integer id);
}
