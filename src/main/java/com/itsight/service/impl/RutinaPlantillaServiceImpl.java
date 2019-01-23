package com.itsight.service.impl;

import com.itsight.domain.RutinaPlantilla;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RutinaPlantillaRepository;
import com.itsight.service.RutinaPlantillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RutinaPlantillaServiceImpl extends BaseServiceImpl<RutinaPlantillaRepository> implements RutinaPlantillaService {

    @Autowired
    public RutinaPlantillaServiceImpl(RutinaPlantillaRepository repository) {
        super(repository);
    }

    @Override
    public RutinaPlantilla update(RutinaPlantilla rutina) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(rutina);
    }

    @Override
    public void delete(int rutinaId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(rutinaId));
    }

    @Override
    public RutinaPlantilla save(RutinaPlantilla entity) {
        return repository.save(entity);
    }

    @Override
    public RutinaPlantilla findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public RutinaPlantilla findOneWithFT(int id) {
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findAll() {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> listarPorFiltroPT(int trainerId) {
        return repository.findByTrainerIdOrderByIdDesc(trainerId);
    }

    @Override
    public String registrar(RutinaPlantilla entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(RutinaPlantilla entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }
}
