package com.itsight.service.impl;

import com.itsight.domain.DiaRutinario;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.DiaRutinarioRepository;
import com.itsight.service.DiaRutinarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DiaRutinarioServiceImpl extends BaseServiceImpl<DiaRutinarioRepository> implements DiaRutinarioService {

    public DiaRutinarioServiceImpl(DiaRutinarioRepository repository) {
        super(repository);
    }

    @Override
    public DiaRutinario save(DiaRutinario entity) {
        return repository.save(entity);
    }

    @Override
    public DiaRutinario update(DiaRutinario entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public DiaRutinario findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public DiaRutinario findOneWithFT(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<DiaRutinario> findAll() {
        return repository.findAll();
    }

    @Override
    public List<DiaRutinario> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<DiaRutinario> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<DiaRutinario> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<DiaRutinario> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<DiaRutinario> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<DiaRutinario> findByIdsIn(List<Long> ids) {
        return repository.findAllByIdInOrderById(ids);
    }

    @Override
    public List<DiaRutinario> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(DiaRutinario entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(DiaRutinario entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Long id, boolean flagActivo) {

    }
}
