package com.itsight.repository;

import com.itsight.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findAllByFlagActivo(Boolean flagActivo);

    List<Categoria> findAllByNombreContaining(String nombres);

    List<Categoria> findAllByNombreContainingIgnoreCase(String comodin);

    List<Categoria> findAllByNombreContainingIgnoreCaseAndFlagActivo(String comodin, Boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE Categoria C SET C.flagActivo =?2 WHERE C.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

}
