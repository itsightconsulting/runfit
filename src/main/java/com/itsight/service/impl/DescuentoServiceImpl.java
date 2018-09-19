package com.itsight.service.impl;

import com.itsight.domain.Descuento;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.DescuentoRepository;
import com.itsight.service.DescuentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DescuentoServiceImpl extends BaseServiceImpl<DescuentoRepository> implements DescuentoService {

    public DescuentoServiceImpl(DescuentoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Descuento save(Descuento entity) {
        // TODO Auto-generated method stub
        return repository.save(entity);
    }

    @Override
    public Descuento update(Descuento entity) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(entity);
    }

    @Override
    public Descuento findOne(int id) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(id));
    }

    @Override
    public Descuento findOneWithFT(int id) {
        // TODO Auto-generated method stub
        return repository.findById(new Integer(id));
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        repository.delete(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> listarPorProductoId(int productoId) {
        // TODO Auto-generated method stub
        return repository.findByProductoPresentacionId(productoId);
    }

    @Override
    public String registrar(Descuento entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String actualizar(Descuento entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

}
