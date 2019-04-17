package com.itsight.service.impl;

import com.itsight.domain.Modulo;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ModuloRepository;
import com.itsight.service.ModuloService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModuloServiceImpl extends BaseServiceImpl<ModuloRepository> implements ModuloService {

    public ModuloServiceImpl(ModuloRepository repository) {
        super(repository);
    }

    @Override
    public Modulo save(Modulo entity) {
        return repository.save(entity);
    }

    @Override
    public Modulo update(Modulo entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Modulo findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Modulo findOneWithFT(Integer id) {
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
    public List<Modulo> findAll() {
        return repository.findAll();
    }

    @Override
    public Modulo findOneByNombre(String nombre){
        return repository.findByNombre(nombre);
    }

    @Override
    public List<Modulo> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Modulo> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Modulo> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Modulo> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Modulo> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Modulo> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Modulo> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Modulo entity, String wildcard) {
        repository.save(entity);
        return "-1";
    }

    @Override
    public String actualizar(Modulo entity, String wildcard) {
        repository.save(entity);
        return "-2";
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
