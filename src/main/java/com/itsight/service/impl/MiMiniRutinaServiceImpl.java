package com.itsight.service.impl;

import com.itsight.domain.MiMiniRutina;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.MiMiniRutinaRepository;
import com.itsight.service.MiMiniRutinaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MiMiniRutinaServiceImpl extends BaseServiceImpl<MiMiniRutinaRepository> implements MiMiniRutinaService {

    public MiMiniRutinaServiceImpl(MiMiniRutinaRepository repository) {
        super(repository);
    }

    @Override
    public MiMiniRutina save(MiMiniRutina entity) {
        return repository.save(entity);
    }

    @Override
    public MiMiniRutina update(MiMiniRutina entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public MiMiniRutina findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public MiMiniRutina findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<MiMiniRutina> findAll() {
        return repository.findAll();
    }

    @Override
    public List<MiMiniRutina> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<MiMiniRutina> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<MiMiniRutina> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<MiMiniRutina> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<MiMiniRutina> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<MiMiniRutina> findByIdsIn(List<Integer> ids) {
        return repository.findAllByIdIn(ids);
    }

    @Override
    public List<MiMiniRutina> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(MiMiniRutina entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(MiMiniRutina entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
