package com.itsight.service.impl;

import com.itsight.domain.ConfiguracionCliente;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ConfiguracionClienteRepository;
import com.itsight.service.ConfiguracionClienteService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConfiguracionClienteServiceImpl extends BaseServiceImpl<ConfiguracionClienteRepository> implements ConfiguracionClienteService {

    public ConfiguracionClienteServiceImpl(ConfiguracionClienteRepository repository){
        super(repository);
    }

    @Override
    public ConfiguracionCliente save(ConfiguracionCliente entity) {
        return repository.save(entity);
    }

    @Override
    public ConfiguracionCliente update(ConfiguracionCliente entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public ConfiguracionCliente findOne(int id) {
        return repository.findById(new Integer(id));
    }

    @Override
    public ConfiguracionCliente findOneWithFT(int id) {
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
    public List<ConfiguracionCliente> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ConfiguracionCliente> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<ConfiguracionCliente> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<ConfiguracionCliente> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<ConfiguracionCliente> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<ConfiguracionCliente> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<ConfiguracionCliente> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<ConfiguracionCliente> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(ConfiguracionCliente entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(ConfiguracionCliente entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public String obtenerPostIdFavoritos(int id) {
        return repository.getFavsPostTrainer(id);
    }

    @Override
    public void actualizarPostIdFavoritos(int id, String postsFavsIds) {
        repository.updateFavsPostTrainer(id, postsFavsIds);
    }
}