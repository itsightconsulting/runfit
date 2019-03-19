package com.itsight.service.impl;

import com.itsight.domain.Musculo;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.MusculoRepository;
import com.itsight.service.MusculoService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MusculoServiceImpl extends BaseServiceImpl<MusculoRepository> implements MusculoService {

    public MusculoServiceImpl(MusculoRepository repository) {
        super(repository);
    }

    @Override
    public Musculo save(Musculo entity) {
        return repository.save(entity);
    }

    @Override
    public Musculo update(Musculo entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Musculo findOne(Integer id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public Musculo findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.delete(new Integer(id));
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Musculo> findAll() {
        return repository.findAllByOrderById();
    }

    @Override
    public List<Musculo> findByNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Musculo> findByNombreContainingIgnoreCase(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Musculo> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Musculo> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Musculo> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Musculo> findByIdsIn(List<Integer> ids) {
        return repository.findByIdIn(ids);
    }

    @Override
    public List<Musculo> listarPorFiltro(String comodin, String estado, String fk) {
        comodin = comodin.equals("0") ? null : comodin;
        List<Musculo> musculos;
        if (comodin == null)
            musculos = repository.findAllByOrderByIdDesc();
        else
            musculos = repository.findAllByNombreContainingIgnoreCaseOrderByIdDesc(comodin);
        return musculos;
    }

    @Override
    public String registrar(Musculo entity, String wildcard) {
        return Utilitarios.customResponse(Enums.ResponseCode.REGISTRO.get(), String.valueOf(repository.save(entity).getId()));
    }

    @Override
    public String actualizar(Musculo entity, String wildcard) {
        return Utilitarios.customResponse(Enums.ResponseCode.ACTUALIZACION.get(), String.valueOf(repository.saveAndFlush(entity).getId()));
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
