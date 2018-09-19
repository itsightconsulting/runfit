package com.itsight.service.impl;

import com.itsight.domain.UsuarioPlan;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.UsuarioFitnessRepository;
import com.itsight.repository.UsuarioPlanRepository;
import com.itsight.service.UsuarioPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioPlanServiceImpl implements UsuarioPlanService {

    private UsuarioPlanRepository usuarioPlanRepository;

    @Autowired
    public UsuarioPlanServiceImpl(UsuarioPlanRepository usuarioPlanRepository) {
        this.usuarioPlanRepository = usuarioPlanRepository;
    }
 
    @Override
    public List<UsuarioPlan> listAll() {
        // TODO Auto-generated method stub
        return usuarioPlanRepository.findAll();
    }

    @Override
    public UsuarioPlan add(UsuarioPlan usuarioPlan) {
        // TODO Auto-generated method stub
        return usuarioPlanRepository.save(usuarioPlan);
    }

    @Override
    public UsuarioPlan update(UsuarioPlan usuarioPlan) {
        // TODO Auto-generated method stub
        return usuarioPlanRepository.saveAndFlush(usuarioPlan);
    }

    @Override
    public void delete(int usuarioPlanId) {
        // TODO Auto-generated method stub
        usuarioPlanRepository.delete(new Integer(usuarioPlanId));
    }

    @Override
    public UsuarioPlan findOneById(int id) {
        // TODO Auto-generated method stub
        return usuarioPlanRepository.findOne(new Integer(id));
    }


}
