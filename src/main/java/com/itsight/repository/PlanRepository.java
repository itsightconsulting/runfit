package com.itsight.repository;

import com.itsight.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    List<Plan> findAllByFlagActivo(Boolean flagActivo);

    List<Plan> findAllByNombreContaining(String nombre);

    @Query("SELECT new Plan(P.id, P.nombre) FROM Plan P")
    List<Plan> readAll();

    @Modifying
    @Query("UPDATE Plan P SET P.flagActivo = ?2 WHERE P.id = ?1")
    void updateFlagActivoById(int id, boolean flagActivo);
}
