package com.itsight.service.impl;

import com.itsight.domain.Pais;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.PaisRepository;
import com.itsight.service.PaisService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaisServiceImpl extends BaseServiceImpl<PaisRepository> implements PaisService {

    public PaisServiceImpl(PaisRepository repository){
        super(repository);
    }

    @Override
    public Pais save(Pais entity) {
        return repository.save(entity);
    }

    @Override
    public Pais update(Pais entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Pais findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Pais findOneWithFT(Integer id) {
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
    public List<Pais> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Pais> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Pais> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Pais> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Pais> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Pais> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Pais> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Pais> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Pais entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(Pais entity, String wildcard) {
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
