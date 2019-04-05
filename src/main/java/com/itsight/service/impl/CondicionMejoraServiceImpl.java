package com.itsight.service.impl;

import com.itsight.domain.CondicionMejora;
import com.itsight.domain.dto.CondicionMejoraDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.CondicionMejoraRepository;
import com.itsight.service.CondicionMejoraService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Utilitarios.customResponse;

@Service
@Transactional
public class CondicionMejoraServiceImpl extends BaseServiceImpl<CondicionMejoraRepository> implements CondicionMejoraService {

    public CondicionMejoraServiceImpl(CondicionMejoraRepository repository) {
        super(repository);
    }

    @Override
    public CondicionMejora save(CondicionMejora entity) {
        return repository.save(entity);
    }

    @Override
    public CondicionMejora update(CondicionMejora entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public CondicionMejora findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public CondicionMejora findOneWithFT(Integer id) {
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
    public List<CondicionMejora> findAll() {
        return repository.findAllByOrderById();
    }

    @Override
    public List<CondicionMejora> findByNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<CondicionMejora> findByNombreContainingIgnoreCase(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<CondicionMejora> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<CondicionMejora> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<CondicionMejora> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<CondicionMejora> findByIdsIn(List<Integer> ids) {
        return repository.findByIdIn(ids);
    }

    @Override
    public List<CondicionMejora> listarPorFiltro(String comodin, String estado, String fk) {
        comodin = comodin.equals("0") ? null : comodin;
        List<CondicionMejora> lstAudio;
        if (comodin == null)
            lstAudio = repository.findAllByOrderByIdDesc();
        else
            lstAudio = repository.findAllByNombreContainingIgnoreCaseOrderByIdDesc(comodin);
        return lstAudio;
    }

    @Override
    public String registrar(CondicionMejora entity, String wildcard) {
        repository.save(entity);
        return customResponse(REGISTRO.get(), String.valueOf(entity.getId()));
    }

    @Override
    public String actualizar(CondicionMejora entity, String wildcard) {
        return customResponse(ACTUALIZACION.get(), String.valueOf(entity.getId()));
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public List<CondicionMejoraDTO> getAll() {
        return repository.findAllByOrderByIdAsc();
    }
}
