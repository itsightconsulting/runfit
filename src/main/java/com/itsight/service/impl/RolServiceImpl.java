package com.itsight.service.impl;

import com.itsight.domain.Rol;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RolRepository;
import com.itsight.service.RolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class RolServiceImpl extends BaseServiceImpl<RolRepository> implements RolService {

    public RolServiceImpl(RolRepository repository) {
        super(repository);
    }

    @Override
    public Rol save(Rol entity) {
        return repository.save(entity);
    }

    @Override
    public Rol update(Rol entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Rol findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public Rol findOneWithFT(int id) {
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
    public List<Rol> findAll() {
        return repository.findAllByOrderById();
    }

    @Override
    public List<Rol> findByNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Rol> findByNombreContainingIgnoreCase(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Rol> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Rol> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Rol> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Rol> findByIdsIn(List<Integer> ids) {
        return repository.findByIdIn(ids);
    }

    @Override
    public List<Rol> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Rol entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(Rol entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }
}
