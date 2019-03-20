package com.itsight.service.impl;

import com.itsight.domain.ListaPlantillaSimple;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ListaPlantillaSimpleRepository;
import com.itsight.service.ListaPlantillaSimpleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ListaPlantillaSimpleServiceImpl extends BaseServiceImpl<ListaPlantillaSimpleRepository> implements ListaPlantillaSimpleService {


    public ListaPlantillaSimpleServiceImpl(ListaPlantillaSimpleRepository repository) {
        super(repository);
    }

    @Override
    public ListaPlantillaSimple save(ListaPlantillaSimple entity) {
        return repository.save(entity);
    }

    @Override
    public ListaPlantillaSimple update(ListaPlantillaSimple entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public ListaPlantillaSimple findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public ListaPlantillaSimple findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<ListaPlantillaSimple> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ListaPlantillaSimple> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<ListaPlantillaSimple> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<ListaPlantillaSimple> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<ListaPlantillaSimple> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<ListaPlantillaSimple> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<ListaPlantillaSimple> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<ListaPlantillaSimple> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(ListaPlantillaSimple entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(ListaPlantillaSimple entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
