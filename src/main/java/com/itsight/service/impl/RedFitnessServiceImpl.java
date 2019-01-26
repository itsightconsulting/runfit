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
    public RedFitness findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public RedFitness findOneWithFT(int id) {
        return repository.findOneById(id);
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
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public List<RedFitness> listarSegunRedTrainer(String codigoTrainer) {
        return repository.findAllByTrainerCodigoTrainer(codigoTrainer);
    }

    @Override
    public void updateFlagEnActividadById(int id, boolean flagEnEntrenamiento) {
        //repository.updateFlagEnActividadById(id, flagEnEntrenamiento);
    }

    @Override
    public void actualizarNotaACliente(int id, String nota) {
        repository.actualizarNotaACliente(id, nota);
    }

    @Override
    public void updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(int id, int planStatus, Date ultimoDiaPlanificacion, int contadorRutinas) {
        repository.updatePlanStatusAndUltimoDiaPlanificacion(id, planStatus, ultimoDiaPlanificacion, contadorRutinas);
    }

    @Override
    public String findCodTrainerById(int redFitnessId) {
        return repository.findCodTrainerById(redFitnessId);
    }

    @Override
    public void actualizarUltimaFechaPlanificacionById(int id, Date ultimaFecha) {
        repository.updateUltimaFechaPlanificacionById(id, ultimaFecha);
    }

    @Override
    public RedFitness findByTrainerCodigoTrainer(String codTrainer) {
        return repository.findByTrainerCodigoTrainer(codTrainer);
    }

    @Override
    public String findCodTrainerByIdAndRunnerId(int redFitId, int runneId) {
        return repository.findCodTrainerByIdAndRunnerId(redFitId, runneId);
    }

    @Override
    public List<String> findTrainerByUsuarioId(int id) {
        return  repository.findTrainerByUsuarioId(id);
    }

    @Override
    public List<Integer> findTrainerIdByUsuarioId(int id) {
        return  repository.findTrainerIdByUsuarioId(id);
    }

}
