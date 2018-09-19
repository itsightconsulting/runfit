package com.itsight.service.impl;

import com.itsight.domain.Plan;
import com.itsight.repository.PlanRepository;
import com.itsight.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private PlanRepository planRepository;

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public List<Plan> listAll() {
        // TODO Auto-generated method stub
        return planRepository.findAll();
    }

    @Override
    public List<Plan> readAll() {
        // TODO Auto-generated method stub
        return planRepository.readAll();
    }

    @Override
    public Plan add(Plan plan) {
        // TODO Auto-generated method stub
        return planRepository.save(plan);
    }

    @Override
    public Plan update(Plan plan) {
        // TODO Auto-generated method stub
        return planRepository.saveAndFlush(plan);
    }

    @Override
    public void delete(int planId) {
        // TODO Auto-generated method stub
        planRepository.delete(new Integer(planId));
    }

    @Override
    public Plan getPlanById(int planId) {
        // TODO Auto-generated method stub
        return planRepository.findOne(new Integer(planId));
    }

    @Override
    public List<Plan> findAllByFlagActivo(Boolean flagActivo) {
        // TODO Auto-generated method stub
        return planRepository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public List<Plan> findAllByNombre(String nombres) {
        // TODO Auto-generated method stub
        return planRepository.findAllByNombreContaining(nombres);
    }

}
