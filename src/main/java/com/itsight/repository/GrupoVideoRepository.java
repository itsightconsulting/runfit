package com.itsight.repository;

import com.itsight.domain.GrupoVideo;
import com.itsight.domain.GrupoVideo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoVideoRepository extends JpaRepository<GrupoVideo, Integer> {

    @Override
    @EntityGraph(value = "grupoVideo")
    List<GrupoVideo> findAll();

    @Query("SELECT DISTINCT C FROM GrupoVideo C JOIN FETCH C.lstCategoriaVideo ORDER BY 1 ASC ")
    List<GrupoVideo> findDistinctByOrderById();

    @EntityGraph(value = "grupoVideo")
    List<GrupoVideo> findAllByFlagActivo(Boolean flagActivo);

    @EntityGraph(value = "grupoVideo")
    List<GrupoVideo> findAllByNombreContaining(String nombres);

    @EntityGraph(value = "grupoVideo")
    List<GrupoVideo> findAllByNombreContainingIgnoreCase(String nombres);

    @EntityGraph(value = "grupoVideo")
    List<GrupoVideo> findAllByNombreContainingIgnoreCaseAndFlagActivo(String nombre, Boolean flagActivo);

    @EntityGraph(value = "grupoVideo")
    GrupoVideo findById(int id);

    @Modifying
    @Query(value = "UPDATE GrupoVideo C SET C.flagActivo =?2 WHERE C.id = ?1")
    void updateFlagActivoById(int id, boolean flagActivo);

}
