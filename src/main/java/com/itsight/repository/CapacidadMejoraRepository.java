package com.itsight.repository;

import com.itsight.domain.CapacidadMejora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapacidadMejoraRepository extends JpaRepository<CapacidadMejora, Integer> {

    List<CapacidadMejora> findByNombreContainingIgnoreCase(String nombre);

    List<CapacidadMejora> findByIdIn(List<Integer> ids);

    List<CapacidadMejora> findAllByOrderById();

    @Query("SELECT CM FROM CapacidadMejora CM ORDER BY 1 ASC")
    List<CapacidadMejora> findAllByOrderByIdDesc();

    List<CapacidadMejora> findAllByNombreContainingIgnoreCaseOrderByIdDesc(String comodin);
}
