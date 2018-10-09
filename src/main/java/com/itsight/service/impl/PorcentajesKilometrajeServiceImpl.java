package com.itsight.service.impl;

import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.domain.dto.PorcentajeKilometrajeDto;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.PorcentajesKilometrajeRepository;
import com.itsight.service.PorcentajesKilometrajeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PorcentajesKilometrajeServiceImpl extends BaseServiceImpl<PorcentajesKilometrajeRepository> implements PorcentajesKilometrajeService {

    public PorcentajesKilometrajeServiceImpl(PorcentajesKilometrajeRepository repository) {
        super(repository);
    }

    @Override
    public PorcentajesKilometraje save(PorcentajesKilometraje entity) {
        return repository.save(entity);
    }

    @Override
    public PorcentajesKilometraje update(PorcentajesKilometraje entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public PorcentajesKilometraje findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public PorcentajesKilometraje findOneWithFT(int id) {
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
    public List<PorcentajesKilometraje> findAll() {
        return repository.findAll();
    }

    @Override
    public List<PorcentajesKilometraje> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<PorcentajesKilometraje> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<PorcentajesKilometraje> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<PorcentajesKilometraje> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<PorcentajesKilometraje> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<PorcentajesKilometraje> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<PorcentajesKilometraje> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(PorcentajesKilometraje entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(PorcentajesKilometraje entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public List<PorcentajesKilometraje> findAllByUsuarioId(int trainerId) {
        return repository.findAllByTrainerId(trainerId);
    }

    @Override
    public PorcentajesKilometraje findByTrainerIdAndDistancia(int trainerId, int distancia) {
        return repository.findByTrainerIdAndDistancia(trainerId, distancia);
    }

    @Override
    public void actualizarPorcentajesComplexByTrainerIdAndDistancia(int trainerId, int distancia, List<PorcentajeKilometrajeDto> porcentajes) {
        String textNombre1 = "{0,\"semanas\","+porcentajes.get(0).getIndice()+",\"porcentajes\"}";
        String textNombre2 = "{1,\"semanas\","+porcentajes.get(1).getIndice()+",\"porcentajes\"}";
        String textNombre3 = "{2,\"semanas\","+porcentajes.get(2).getIndice()+",\"porcentajes\"}";

        repository.updatePorcentajesComplex(trainerId, distancia, textNombre1, "["+porcentajes.get(0).getPorcentajes()+"]", textNombre2,"["+ porcentajes.get(1).getPorcentajes() +"]", textNombre3, "[" +porcentajes.get(2).getPorcentajes()+ "]");
    }
}
