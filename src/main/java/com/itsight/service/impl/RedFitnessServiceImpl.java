package com.itsight.service.impl;

import com.itsight.domain.RedFitness;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RedFitnessRepository;
import com.itsight.service.RedFitnessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class RedFitnessServiceImpl extends BaseServiceImpl<RedFitnessRepository> implements RedFitnessService {

    public RedFitnessServiceImpl(RedFitnessRepository repository){
        super(repository);
    }

    @Override
    public RedFitness save(RedFitness entity) {
        return repository.save(entity);
    }

    @Override
    public RedFitness update(RedFitness entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public RedFitness findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public RedFitness findOneWithFT(Integer id) {
        return repository.findOneById(id);
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
    public List<RedFitness> findAll() {
        return repository.findAll();
    }

    @Override
    public List<RedFitness> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<RedFitness> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<RedFitness> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<RedFitness> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<RedFitness> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<RedFitness> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<RedFitness> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(RedFitness entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(RedFitness entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public List<RedFitness> listarSegunRedTrainer(String codigoTrainer) {
        return repository.findAllByTrainerCodigoTrainer(codigoTrainer);
    }

    @Override
    public void actualizarNotaACliente(Integer id, String nota) {
        repository.actualizarNotaACliente(id, nota);
    }

    @Override
    public void updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(Integer id, int planStatus, Date ultimoDiaPlanificacion, int contadorRutinas) {
        repository.updatePlanStatusAndUltimoDiaPlanificacion(id, planStatus, ultimoDiaPlanificacion, contadorRutinas);
    }

    @Override
    public String findCodTrainerByIdAndRunnerId(Integer id, Integer runneId) {
        return repository.findCodTrainerByIdAndRunnerId(id, runneId);
    }

    @Override
    public List<Integer> findTrainerIdByUsuarioId(Integer id) {
        return  repository.findTrainerIdByUsuarioId(id);
    }

}
