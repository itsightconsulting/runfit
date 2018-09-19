package com.itsight.service.impl;

import com.itsight.domain.Semana;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.SemanaRepository;
import com.itsight.service.SemanaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SemanaServiceImpl extends BaseServiceImpl<SemanaRepository> implements SemanaService {

    public SemanaServiceImpl(SemanaRepository repository) {
        super(repository);
    }

    @Override
    public Semana save(Semana entity) {
        return repository.save(entity);
    }

    @Override
    public Semana update(Semana entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Semana findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public Semana findOneWithFT(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Semana> findAll() {
        return null;
    }

    @Override
    public List<Semana> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Semana> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Semana> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Semana> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Semana> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Semana> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Semana> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Semana entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(Semana entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) { }

    @Override
    public Semana findOneWithDaysById(int id) {
        return repository.findOneWithDays(id);
    }
}
