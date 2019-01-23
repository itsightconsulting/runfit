package com.itsight.service.impl;

import com.itsight.domain.Plan;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.PlanRepository;
import com.itsight.service.PlanService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlanServiceImpl extends BaseServiceImpl<PlanRepository> implements PlanService {

    public PlanServiceImpl(PlanRepository repository) {
        super(repository);
    }

    @Override
    public List<Plan> listAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<Plan> readAll() {
        // TODO Auto-generated method stub
        return repository.readAll();
    }

    @Override
    public Plan add(Plan plan) {
        // TODO Auto-generated method stub
        return repository.save(plan);
    }

    @Override
    public Plan update(Plan plan) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(plan);
    }

    @Override
    public void delete(int planId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(planId));
    }

    @Override
    public Plan getPlanById(int planId) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(planId));
    }

    @Override
    public List<Plan> findAllByFlagActivo(Boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public List<Plan> findAllByNombre(String nombres) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContaining(nombres);
    }

    @Override
    public Plan save(Plan entity) {
        return null;
    }

    @Override
    public Plan findOne(int id) {
        return null;
    }

    @Override
    public Plan findOneWithFT(int id) {
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Plan> findAll() {
        return null;
    }

    @Override
    public List<Plan> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Plan> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Plan> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Plan> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Plan> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Plan> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Plan> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Plan entity, String wildcard) {
        return Utilitarios.customResponse(Enums.ResponseCode.REGISTRO.get(), String.valueOf(repository.save(entity).getId()));
    }

    @Override
    public String actualizar(Plan entity, String wildcard) {
        return Utilitarios.customResponse(Enums.ResponseCode.ACTUALIZACION.get(), String.valueOf(repository.saveAndFlush(entity).getId()));
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        repository.updateFlagActivoById(id, flagActivo);
    }
}
