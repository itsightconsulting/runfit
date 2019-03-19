package com.itsight.repository;

import com.itsight.domain.Producto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT P FROM Producto P INNER JOIN FETCH P.categoria C WHERE P.id = ?1")
    Producto findOneWithFT(Integer id);

    @EntityGraph(value = "producto.all", attributePaths = {})
    Producto findProductoById(Integer id);

    @Override
    @EntityGraph(value = "producto.categoria", attributePaths = {})
    List<Producto> findAll();

    @EntityGraph(value = "producto.categoria", attributePaths = {})
    List<Producto> findAllByNombre(String nombre);

    @EntityGraph(value = "producto.categoria", attributePaths = {})
    List<Producto> findAllByNombreContainingIgnoreCase(String nombre);

    @EntityGraph(value = "producto.categoria", attributePaths = {})
    List<Producto> findAllByFlagActivo(boolean flagActivo);

    @EntityGraph(value = "producto.categoria", attributePaths = {})
    List<Producto> findAllByFlagEliminado(boolean flagEliminado);

    @Modifying
    @Query(value = "UPDATE Producto P SET P.flagActivo =?2 WHERE P.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);
}
