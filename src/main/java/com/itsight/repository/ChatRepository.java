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
    @Query(value = "INSERT INTO chat(chat_id, ultimo, mensajes, fecha_creacion, flag_leido) values (?1, cast(?2 as jsonb), cast(?3 as jsonb), ?4, false)", nativeQuery = true)
    void registrar(Integer id, String ultimo, String msgs, Date fechaCreacion);

    @Query(value = "SELECT C.flagLeido from Chat C where C.id = ?1")
    Boolean getFlagLeidoById(Integer id);

}
