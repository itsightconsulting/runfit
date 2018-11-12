package com.itsight.repository;

import com.itsight.domain.MultimediaDetalle;
import com.itsight.domain.MultimediaEntrenador;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultimediaEntrenadorRepository extends JpaRepository<MultimediaEntrenador, Integer> {

    List<MultimediaEntrenador> findAllByOrderById();

    @Query("SELECT M FROM MultimediaEntrenador M ORDER BY 1 ASC")
    List<MultimediaEntrenador> findAllByOrderByIdDesc();

    @Query("SELECT M FROM MultimediaEntrenador M where M.usuario.id = ?1")
    List<MultimediaEntrenador> findAllbyUsuario(int id);

    @Query("SELECT DISTINCT M FROM MultimediaEntrenador M JOIN FETCH M.lstMultimediadetalle D where M.usuario.id = ?1")
    List<MultimediaEntrenador> findAllbyUsuarioTop(@Param("id") int id);

    @Query("SELECT count(M) FROM MultimediaDetalle M where M.multimediaentrenador.id = ?1")
    int findDetalleTopCantidad(int id);

    @Query("SELECT DISTINCT M FROM MultimediaEntrenador M JOIN FETCH M.usuario D where D.codigoTrainer in :ids ORDER BY M.fechaCreacion DESC")
    List<MultimediaEntrenador> findByListEntrenador(@Param("ids") List<String> list);

    @Query("SELECT M FROM MultimediaDetalle M where M.multimediaentrenador.id = ?1")
    List<MultimediaDetalle> findByIdEntrenador(int id);


}
