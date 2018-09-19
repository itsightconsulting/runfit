package com.itsight.service.impl;

import com.itsight.domain.ImagenPlan;
import com.itsight.repository.ImagenPlanRepository;
import com.itsight.service.ImagenPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImagenPlanServiceImpl implements ImagenPlanService {


    private ImagenPlanRepository imagenPlanRepository;

    @Autowired
    public ImagenPlanServiceImpl(ImagenPlanRepository imagenPlanRepository) {
        this.imagenPlanRepository = imagenPlanRepository;
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<ImagenPlan> listAll() {
        // TODO Auto-generated method stub
        return imagenPlanRepository.findAll();
    }

    @Override
    public List<ImagenPlan> findByPlanId(int planId) {
        // TODO Auto-generated method stub
        return imagenPlanRepository.findByPlanId(planId);
    }

    @Override
    public ImagenPlan getImagenPlanById(int imagenPlanId) {
        // TODO Auto-generated method stub
        return imagenPlanRepository.findOne(new Integer(imagenPlanId));
    }

    @Override
    public ImagenPlan add(ImagenPlan imagenPlan) {
        // TODO Auto-generated method stub
        return imagenPlanRepository.save(imagenPlan);
    }

    @Override
    public ImagenPlan update(ImagenPlan imagenPlan) {
        // TODO Auto-generated method stub
        return imagenPlanRepository.saveAndFlush(imagenPlan);
    }

    @Override
    public void delete(int imagenPlanId) {
        // TODO Auto-generated method stub
        imagenPlanRepository.delete(new Integer(imagenPlanId));
    }

}
