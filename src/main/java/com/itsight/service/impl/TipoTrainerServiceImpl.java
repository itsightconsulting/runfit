package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.TipoTrainer;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TipoTrainerRepository;
import com.itsight.service.TipoTrainerService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoTrainerServiceImpl extends BaseServiceImpl<TipoTrainerRepository> implements TipoTrainerService {

    public TipoTrainerServiceImpl(TipoTrainerRepository repository) {
        super(repository);
    }

    @Override
    public TipoTrainer save(TipoTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public TipoTrainer update(TipoTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public TipoTrainer findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TipoTrainer findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TipoTrainer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<TipoTrainer> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<TipoTrainer> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<TipoTrainer> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<TipoTrainer> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TipoTrainer> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<TipoTrainer> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<TipoTrainer> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(TipoTrainer entity, String wildcard) throws CustomValidationException {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(TipoTrainer entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
