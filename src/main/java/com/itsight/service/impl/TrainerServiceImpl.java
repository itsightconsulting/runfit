package com.itsight.service.impl;

import com.itsight.domain.Trainer;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TrainerRepository;
import com.itsight.service.TrainerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TrainerServiceImpl extends BaseServiceImpl<TrainerRepository> implements TrainerService {

    public TrainerServiceImpl(TrainerRepository repository) {
        super(repository);
    }

    @Override
    public Trainer save(Trainer entity) {
        return repository.save(entity);
    }

    @Override
    public Trainer update(Trainer entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Trainer findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public Trainer findOneWithFT(int id) {
        return null;
    }

    @Override
    public void delete(int id) {
        repository.delete(new Integer(id));
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Trainer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Trainer> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Trainer> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Trainer> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Trainer> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Trainer> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Trainer> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Trainer> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Trainer entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(Trainer entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public String findCodigoTrainerById(int id) {
        return repository.findCodigoTrainerById(id);
    }

    @Override
    public void actualizarFechaUltimoAcceso(Date fechaUltimoAcceso, String id) {
        repository.updateFechaUltimoAcceso(fechaUltimoAcceso, Integer.parseInt(id));

    }
}
