package com.itsight.service;

import com.itsight.domain.ImagenPlan;
import com.itsight.generic.BaseService;

import java.util.List;

public interface ImagenPlanService extends BaseService<ImagenPlan> {

    List<ImagenPlan> listAll();

    List<ImagenPlan> findByPlanId(int planId);

    ImagenPlan add(ImagenPlan imagenPlan);

    ImagenPlan update(ImagenPlan imagenPlan);

    void delete(int imagenPlanId);

}
