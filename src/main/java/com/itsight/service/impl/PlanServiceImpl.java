package com.itsight.service.impl;

import com.itsight.domain.Plan;
import com.itsight.domain.Usuario;
import com.itsight.domain.UsuarioPlan;
import com.itsight.repository.PlanRepository;
import com.itsight.repository.UsuarioPlanRepository;
import com.itsight.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private PlanRepository planRepository;
    private UsuarioPlanRepository usuarioPlanRepository;

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository, UsuarioPlanRepository usuarioPlanRepository) {
        this.planRepository = planRepository;
        this.usuarioPlanRepository = usuarioPlanRepository;
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
