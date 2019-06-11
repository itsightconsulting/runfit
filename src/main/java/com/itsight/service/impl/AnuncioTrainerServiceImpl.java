package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.AnuncioTrainer;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.AnuncioTrainerRepository;
import com.itsight.service.AnuncioTrainerService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AnuncioTrainerServiceImpl extends BaseServiceImpl<AnuncioTrainerRepository> implements AnuncioTrainerService {

    public AnuncioTrainerServiceImpl(AnuncioTrainerRepository repository) {
        super(repository);
    }

    @Override
    public AnuncioTrainer save(AnuncioTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public AnuncioTrainer update(AnuncioTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public AnuncioTrainer findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public AnuncioTrainer findOneWithFT(Integer id) {
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
    public List<AnuncioTrainer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<AnuncioTrainer> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<AnuncioTrainer> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<AnuncioTrainer> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<AnuncioTrainer> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<AnuncioTrainer> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<AnuncioTrainer> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<AnuncioTrainer> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(AnuncioTrainer entity, String wildcard) throws CustomValidationException {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(AnuncioTrainer entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
