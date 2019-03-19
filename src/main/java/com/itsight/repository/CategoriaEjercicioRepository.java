package com.itsight.repository;

import com.itsight.domain.CategoriaEjercicio;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaEjercicioRepository extends JpaRepository<CategoriaEjercicio, Integer> {

    @Override
    @EntityGraph(value = "categoriaEjercicio")
    List<CategoriaEjercicio> findAll();

    @Query("SELECT DISTINCT C FROM CategoriaEjercicio C JOIN FETCH C.lstSubCategoriaEjercicio ORDER BY 1 ASC ")
    List<CategoriaEjercicio> findDistinctByOrderById();

    @EntityGraph(value = "categoriaEjercicio")
    List<CategoriaEjercicio> findAllByFlagActivo(Boolean flagActivo);

    @EntityGraph(value = "categoriaEjercicio")
    List<CategoriaEjercicio> findAllByNombreContaining(String nombres);

    @EntityGraph(value = "categoriaEjercicio")
    List<CategoriaEjercicio> findAllByNombreContainingIgnoreCase(String nombres);

    @EntityGraph(value = "categoriaEjercicio")
    List<CategoriaEjercicio> findAllByNombreContainingIgnoreCaseAndFlagActivo(String nombre, Boolean flagActivo);

    @EntityGraph(value = "categoriaEjercicio")
    CategoriaEjercicio findById(Integer id);

    @Modifying
    @Query(value = "UPDATE CategoriaEjercicio C SET C.flagActivo =?2 WHERE C.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

}
