package com.itsight.repository;

import com.itsight.domain.ImagenPlan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenPlanRepository extends JpaRepository<ImagenPlan, Integer> {

    @Override
    @EntityGraph(value = "imagenplan.tipoimagen", attributePaths = {})
    List<ImagenPlan> findAll();

    @EntityGraph(value = "imagenplan.tipoimagen", attributePaths = {})
    List<ImagenPlan> findByPlanId(Integer planId);

}
