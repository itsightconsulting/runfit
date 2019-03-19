package com.itsight.service.impl;

import com.itsight.domain.RuConsolidado;
import com.itsight.domain.Rutina;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RuConsolidadoRepository;
import com.itsight.service.RuConsolidadoService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RuConsolidadoServiceImpl extends BaseServiceImpl<RuConsolidadoRepository> implements RuConsolidadoService {

    @Autowired
    public RuConsolidadoServiceImpl(RuConsolidadoRepository repository){
        super(repository);
    }

    @Override
    public RuConsolidado save(RuConsolidado entity) {
        return repository.save(entity);
    }

    @Override
    public RuConsolidado update(RuConsolidado entity) {
        return repository.save(entity);
    }

    @Override
    public RuConsolidado findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public RuConsolidado findOneWithFT(Long id) {
        return repository.getById(id);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<RuConsolidado> findAll() {
        return repository.findAll();
    }

    @Override
    public List<RuConsolidado> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<RuConsolidado> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<RuConsolidado> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<RuConsolidado> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<RuConsolidado> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<RuConsolidado> findByIdsIn(List<Long> ids) {
        return null;
    }

    @Override
    public List<RuConsolidado> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(RuConsolidado entity, String wildcard) {
        entity.setRutina(new Rutina(Integer.parseInt(wildcard)));
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(RuConsolidado entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Long id, boolean flagActivo) {

    }

    @Override
    public void updateMatrizMejoraVelocidades(Long rutinaId, String mVz) {
        repository.updateMatrizMejoraVelocidades(rutinaId, mVz);
    }
}
