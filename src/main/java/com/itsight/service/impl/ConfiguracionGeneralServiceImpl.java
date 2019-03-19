package com.itsight.service.impl;

import com.itsight.domain.ConfiguracionGeneral;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ConfiguracionGeneralRepository;
import com.itsight.service.ConfiguracionGeneralService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConfiguracionGeneralServiceImpl extends BaseServiceImpl<ConfiguracionGeneralRepository> implements ConfiguracionGeneralService {

    public ConfiguracionGeneralServiceImpl(ConfiguracionGeneralRepository repository){
        super(repository);
    }

    @Override
    public ConfiguracionGeneral save(ConfiguracionGeneral entity) {
        return repository.save(entity);
    }

    @Override
    public ConfiguracionGeneral update(ConfiguracionGeneral entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public ConfiguracionGeneral findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public ConfiguracionGeneral findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<ConfiguracionGeneral> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ConfiguracionGeneral> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<ConfiguracionGeneral> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<ConfiguracionGeneral> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<ConfiguracionGeneral> findByFlagActivo(boolean flagActivo) {
        return repository.findByFlagActivo(flagActivo);
    }

    @Override
    public List<ConfiguracionGeneral> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<ConfiguracionGeneral> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<ConfiguracionGeneral> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(ConfiguracionGeneral entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(ConfiguracionGeneral entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
