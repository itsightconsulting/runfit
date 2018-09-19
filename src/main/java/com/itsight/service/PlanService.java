package com.itsight.service;

import com.itsight.domain.Plan;

import java.util.List;

public interface PlanService {

    List<Plan> listAll();

    Plan add(Plan plan);

    Plan update(Plan plan);

    void delete(int planId);

    Plan getPlanById(int planId);

    List<Plan> findAllByFlagActivo(Boolean flagActivo);

    List<Plan> findAllByNombre(String nombres);

    List<Plan> readAll();

}
