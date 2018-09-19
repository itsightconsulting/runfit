package com.itsight.repository;

import com.itsight.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    List<Plan> findAllByFlagActivo(Boolean flagActivo);

    List<Plan> findAllByNombreContaining(String nombre);

    @Query("SELECT new Plan(P.id, P.nombre) FROM Plan P")
    List<Plan> readAll();
}
