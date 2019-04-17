package com.itsight.service.impl;

import com.itsight.domain.Correo;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.CorreoRepository;
import com.itsight.service.CorreoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CorreoServiceImpl extends BaseServiceImpl<CorreoRepository> implements CorreoService {

    public CorreoServiceImpl(CorreoRepository repository) {
        super(repository);
    }

    @Override
    public Correo save(Correo entity) {
        return repository.save(entity);
    }

    @Override
    public Correo update(Correo entity) {
        return repository.save(entity);
    }

    @Override
    public Correo findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Correo findOneWithFT(Integer id) {
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
    public List<Correo> findAll() {
        return null;
    }

    @Override
    public List<Correo> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Correo> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Correo> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Correo> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Correo> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Correo> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Correo> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Correo entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(Correo entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
