package com.itsight.service;

import com.itsight.domain.ImagenPlan;

import java.util.List;

public interface ImagenPlanService {

    List<ImagenPlan> listAll();

    List<ImagenPlan> findByPlanId(int planId);

    ImagenPlan add(ImagenPlan imagenPlan);

    ImagenPlan update(ImagenPlan imagenPlan);

    void delete(int imagenPlanId);

    ImagenPlan getImagenPlanById(int imagenPlanId);

}
