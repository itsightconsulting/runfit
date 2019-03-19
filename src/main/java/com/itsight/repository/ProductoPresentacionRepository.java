package com.itsight.repository;

import com.itsight.domain.ProductoPresentacion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoPresentacionRepository extends JpaRepository<ProductoPresentacion, Integer> {

    @Override
    @EntityGraph(value = "presentacion", attributePaths = {})
    List<ProductoPresentacion> findAll();

    @EntityGraph(value = "presentacion", attributePaths = {})
    List<ProductoPresentacion> findAllByNombre(String nombre);

    @EntityGraph(value = "presentacion", attributePaths = {})
    List<ProductoPresentacion> findAllByNombreContainingIgnoreCase(String nombre);

    @Modifying
    @Query(value = "UPDATE Producto P SET P.flagActivo =?2 WHERE P.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);
}
