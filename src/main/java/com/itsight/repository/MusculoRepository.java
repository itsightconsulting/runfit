package com.itsight.repository;

import com.itsight.domain.Musculo;
import com.itsight.domain.Musculo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusculoRepository extends JpaRepository<Musculo, Integer> {

    List<Musculo> findByNombreContainingIgnoreCase(String nombre);

    List<Musculo> findByIdIn(List<Integer> ids);

    List<Musculo> findAllByOrderById();

    @Query("SELECT M FROM Musculo M ORDER BY 1 ASC")
    List<Musculo> findAllByOrderByIdDesc();

    List<Musculo> findAllByNombreContainingIgnoreCaseOrderByIdDesc(String comodin);
}
