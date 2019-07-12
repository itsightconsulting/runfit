package com.itsight.repository;

import com.itsight.domain.PostulanteTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulanteTrainerRepository extends JpaRepository<PostulanteTrainer, Integer> {

    PostulanteTrainer findByCorreo(String correo);

    @Modifying
    @Query("UPDATE PostulanteTrainer P SET P.flagRegistrado = ?2 WHERE P.id = ?1")
    void updateFlagRegistradoById(Integer id, boolean flag);

    @Modifying
    @Query("UPDATE PostulanteTrainer P SET P.flagCuentaConfirmada = ?2, P.schema = ?3 WHERE P.id = ?1")
    void updateFlagCuentaConfirmadaAndSecret(Integer id, boolean flag, String schema);

    @Query("SELECT P.tipoTrainerId FROM PostulanteTrainer P WHERE P.id = ?1")
    Integer getTipoTrainerIdById(Integer postId);
}
