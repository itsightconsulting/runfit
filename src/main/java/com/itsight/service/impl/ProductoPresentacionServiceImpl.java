package com.itsight.service.impl;

import com.itsight.domain.ProductoPresentacion;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ProductoPresentacionRepository;
import com.itsight.service.ProductoPresentacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoPresentacionServiceImpl extends BaseServiceImpl<ProductoPresentacionRepository> implements ProductoPresentacionService {

    public ProductoPresentacionServiceImpl(ProductoPresentacionRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ProductoPresentacion save(ProductoPresentacion entity) {
        // TODO Auto-generated method stub
        return repository.save(entity);
    }

    @Override
    public ProductoPresentacion update(ProductoPresentacion entity) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(entity);
    }

    @Override
    public ProductoPresentacion findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findOne(id);
    }

    @Override
    public ProductoPresentacion findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.delete(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductoPresentacion> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<ProductoPresentacion> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombre(nombre);
    }

    @Override
    public List<ProductoPresentacion> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<ProductoPresentacion> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductoPresentacion> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductoPresentacion> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductoPresentacion> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductoPresentacion> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String registrar(ProductoPresentacion entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String actualizar(ProductoPresentacion entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

}
