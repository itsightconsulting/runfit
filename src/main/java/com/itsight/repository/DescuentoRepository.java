package com.itsight.repository;

import com.itsight.domain.Descuento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Integer> {

    @EntityGraph(value = "descuento.tipo", attributePaths = {})
    Descuento findById(int integer);

    @Override
    @EntityGraph(value = "descuento.tipo", attributePaths = {})
    List<Descuento> findAll();

    @EntityGraph(value = "descuento.tipo", attributePaths = {})
    List<Descuento> findByProductoPresentacionId(int productoId);

    @Modifying
    @Query(value = "UPDATE Descuento D SET D.flagActivo =?2 WHERE D.id = ?1")
    void updateFlagActivoById(int id, boolean flagActivo);

}
