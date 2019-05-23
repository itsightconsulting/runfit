package com.itsight.service.impl;

import com.itsight.domain.Disciplina;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.DisciplinaRepository;
import com.itsight.service.DisciplinaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class DisciplinaServiceImpl extends BaseServiceImpl<DisciplinaRepository> implements DisciplinaService {

    public DisciplinaServiceImpl(DisciplinaRepository repository) {
        super(repository);
    }

    @Override
    public Disciplina save(Disciplina entity) {
        return repository.save(entity);
    }

    @Override
    public Disciplina update(Disciplina entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Disciplina findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Disciplina findOneWithFT(Integer id) {
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
    public List<Disciplina> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Disciplina> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Disciplina> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Disciplina> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Disciplina> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Disciplina> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Disciplina> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Disciplina> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Disciplina entity, String wildcard) {
        repository.save(entity);
        return "-1";
    }

    @Override
    public String actualizar(Disciplina entity, String wildcard) {
        repository.saveAndFlush(entity);
        return "-1";
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public void guardarMultipleTrainerDisciplina(Integer trainerId, String disIds) {
        repository.saveMultipleTrainerDisciplina(trainerId, disIds);
    }

    @Override
    public List<String> obtenerDisciplinasByTrainerId(Integer trainerId) {
        return repository.getDisciplinasByTrainerId(trainerId);
    }
}
