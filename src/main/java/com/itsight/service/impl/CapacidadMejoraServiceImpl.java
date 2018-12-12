package com.itsight.service.impl;

import com.itsight.domain.CapacidadMejora;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.CapacidadMejoraRepository;
import com.itsight.service.CapacidadMejoraService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Utilitarios.customResponse;

@Service
@Transactional
public class CapacidadMejoraServiceImpl extends BaseServiceImpl<CapacidadMejoraRepository> implements CapacidadMejoraService {

    public CapacidadMejoraServiceImpl(CapacidadMejoraRepository repository) {
        super(repository);
    }

    @Override
    public CapacidadMejora save(CapacidadMejora entity) {
        return repository.save(entity);
    }

    @Override
    public CapacidadMejora update(CapacidadMejora entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public CapacidadMejora findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public CapacidadMejora findOneWithFT(int id) {
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
    public List<CapacidadMejora> findAll() {
        return repository.findAllByOrderById();
    }

    @Override
    public List<CapacidadMejora> findByNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<CapacidadMejora> findByNombreContainingIgnoreCase(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<CapacidadMejora> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<CapacidadMejora> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<CapacidadMejora> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<CapacidadMejora> findByIdsIn(List<Integer> ids) {
        return repository.findByIdIn(ids);
    }

    @Override
    public List<CapacidadMejora> listarPorFiltro(String comodin, String estado, String fk) {
        comodin = comodin.equals("0") ? null : comodin;
        List<CapacidadMejora> lstAudio;
        if (comodin == null)
            lstAudio = repository.findAllByOrderByIdDesc();
        else
            lstAudio = repository.findAllByNombreContainingIgnoreCaseOrderByIdDesc(comodin);
        return lstAudio;
    }

    @Override
    public String registrar(CapacidadMejora entity, String wildcard) {
        repository.save(entity);
        return customResponse(REGISTRO.get(), String.valueOf(entity.getId()));
    }

    @Override
    public String actualizar(CapacidadMejora entity, String wildcard) {
        return customResponse(ACTUALIZACION.get(), String.valueOf(entity.getId()));
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }
}
