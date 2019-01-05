package com.itsight.repository;

import com.itsight.domain.MultimediaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultimediaDetalleRepository extends JpaRepository<MultimediaDetalle, Integer> {

    List<MultimediaDetalle> findAllByOrderById();

    @Query("SELECT M FROM MultimediaDetalle M ORDER BY 1 ASC")
    List<MultimediaDetalle> findAllByOrderByIdDesc();

    @Query("SELECT M FROM MultimediaDetalle M where M.trainer.id = ?1")
    List<MultimediaDetalle> findAllbyTrainer(int id);

    @Query("SELECT M FROM MultimediaDetalle M where M.multimediaentrenador.id = ?1 and M.trainer.id = ?2")
    MultimediaDetalle findbyTrainerByEntrenador(int id, int idtrainer);



}
