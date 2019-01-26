package com.itsight.repository;

import com.itsight.domain.MultimediaDetalle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MultimediaDetalleRepository extends JpaRepository<MultimediaDetalle, Integer> {

    List<MultimediaDetalle> findAllByOrderById();

    @Query("SELECT M FROM MultimediaDetalle M ORDER BY 1 ASC")
    List<MultimediaDetalle> findAllByOrderByIdDesc();

    @Query("SELECT M.id FROM MultimediaDetalle M where M.multimediaEntrenador.id = ?1 and M.cliente.id = ?2")
    Optional<Integer> findByMultimediaEntrenadorIdAndClienteId(int multimediaTrainerId, int clienteId);
}
