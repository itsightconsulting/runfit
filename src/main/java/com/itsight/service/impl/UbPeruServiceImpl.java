package com.itsight.service.impl;

import com.itsight.domain.UbPeru;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.UbPeruRepository;
import com.itsight.service.UbPeruService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UbPeruServiceImpl extends BaseServiceImpl<UbPeruRepository> implements UbPeruService {

    public UbPeruServiceImpl(UbPeruRepository repository){
        super(repository);
    }

    @Override
    public UbPeru save(UbPeru entity) {
        return repository.save(entity);
    }

    @Override
    public UbPeru update(UbPeru entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public UbPeru findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public UbPeru findOneWithFT(int id) {
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
    public List<UbPeru> findAll() {
        return repository.findAll();
    }

    @Override
    public List<UbPeru> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<UbPeru> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<UbPeru> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<UbPeru> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<UbPeru> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<UbPeru> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<UbPeru> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(UbPeru entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(UbPeru entity, String wildcard) {
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }
}
