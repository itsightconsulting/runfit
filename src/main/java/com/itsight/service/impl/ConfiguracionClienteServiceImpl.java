package com.itsight.service.impl;

import com.itsight.domain.ConfiguracionCliente;
import com.itsight.domain.dto.ConfiguracionClienteDTO;
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
    public ConfiguracionCliente findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ConfiguracionCliente findOneWithFT(Integer id) {
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
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public String obtenerPostIdFavoritos(Integer id) {
        return repository.getFavsPostTrainer(id);
    }

    @Override
    public void actualizarPostIdFavoritos(Integer id, String postsFavsIds) {
        repository.updateFavsPostTrainer(id, postsFavsIds);
    }

    @Override
    public String obtenerByIdAndClave(int id, String clave) {
        return repository.findByIdAndClave(id, clave);
    }

    @Override
    public void actualizarById(Integer id, String clave, String valor) {
        repository.updateById(id, clave, valor);
    }

    @Override
    public void actualizarNotificacionChatById(Integer cliId) {
        Integer sustractOrPlusNumber = 1;
        repository.updateNotificacionChatById(cliId, sustractOrPlusNumber);

    }
}
