package com.itsight.service.impl;

import com.itsight.domain.ImagenPlan;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ImagenPlanRepository;
import com.itsight.service.ImagenPlanService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImagenPlanServiceImpl extends BaseServiceImpl<ImagenPlanRepository> implements ImagenPlanService {

    public ImagenPlanServiceImpl(ImagenPlanRepository repository){
        super(repository);
    }

    @Override
    public List<ImagenPlan> listAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<ImagenPlan> findByPlanId(Integer planId) {
        // TODO Auto-generated method stub
        return repository.findByPlanId(planId);
    }

    @Override
    public ImagenPlan add(ImagenPlan imagenPlan) {
        // TODO Auto-generated method stub
        return repository.save(imagenPlan);
    }

    @Override
    public ImagenPlan update(ImagenPlan imagenPlan) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(imagenPlan);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.delete(id);
    }

    @Override
    public ImagenPlan save(ImagenPlan entity) {
        return null;
    }

    @Override
    public ImagenPlan findOne(Integer id) {
        return null;
    }

    @Override
    public ImagenPlan findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<ImagenPlan> findAll() {
        return null;
    }

    @Override
    public List<ImagenPlan> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<ImagenPlan> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<ImagenPlan> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<ImagenPlan> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<ImagenPlan> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<ImagenPlan> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<ImagenPlan> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(ImagenPlan entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(ImagenPlan entity, String wildcard) {
        repository.saveAndFlush(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
