package com.itsight.service.impl;

import com.itsight.domain.Paquete;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.PaqueteRepository;
import com.itsight.service.PaqueteService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaqueteServiceImpl extends BaseServiceImpl<PaqueteRepository> implements PaqueteService {

    public PaqueteServiceImpl(PaqueteRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<Paquete> listAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<Paquete> findAllByFlagActivo(Boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public Paquete getPaqueteById(int paqueteId) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(paqueteId));
    }

    @Override
    public Paquete update(Paquete paquete) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(paquete);
    }

    @Override
    public void delete(int paqueteId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(paqueteId));
    }

    @Override
    public List<Paquete> findAllByNombreContainingOrDescripcionContaining(String nombre, String descripcion) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingOrDescripcionContaining(nombre, descripcion);
    }

    @Override
    public Paquete save(Paquete entity) {
        return null;
    }

    @Override
    public Paquete findOne(int id) {
        return null;
    }

    @Override
    public Paquete findOneWithFT(int id) {
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Paquete> findAll() {
        return null;
    }

    @Override
    public List<Paquete> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Paquete> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Paquete> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Paquete> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Paquete> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Paquete> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Paquete> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Paquete entity, String wildcard) {
        entity.setNombreContrato("");
        entity.setRutaContrato("");
        return Utilitarios.customResponse(Enums.ResponseCode.REGISTRO.get(), String.valueOf(repository.save(entity).getId()));
    }

    @Override
    public String actualizar(Paquete entity, String wildcard) {
        Paquete qPaquete = repository.findRouteNamesById(entity.getId());
        entity.setNombreContrato(qPaquete.getNombreContrato());
        entity.setRutaContrato(qPaquete.getRutaContrato());
        return Utilitarios.customResponse(Enums.ResponseCode.ACTUALIZACION.get(), String.valueOf(repository.saveAndFlush(entity).getId()));
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        repository.updateFlagActivoById(id, flagActivo);
    }
}
