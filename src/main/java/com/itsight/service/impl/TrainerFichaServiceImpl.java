package com.itsight.service.impl;

import com.itsight.domain.TrainerFicha;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TrainerFichaRepository;
import com.itsight.service.TrainerFichaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainerFichaServiceImpl extends BaseServiceImpl<TrainerFichaRepository> implements TrainerFichaService {

    public TrainerFichaServiceImpl(TrainerFichaRepository repository) {
        super(repository);
    }

    @Override
    public TrainerFicha save(TrainerFicha entity) {
        return null;
    }

    @Override
    public TrainerFicha update(TrainerFicha entity) {
        return null;
    }

    @Override
    public TrainerFicha findOne(Integer id) {
        return null;
    }

    @Override
    public TrainerFicha findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TrainerFicha> findAll() {
        return repository.findAll();
    }

    @Override
    public List<TrainerFichaPOJO> findAllWithFgEnt() {
        return repository.findAllWithFgEnt();
    }

    @Override
    public List<TrainerFicha> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<TrainerFicha> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(TrainerFicha entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(TrainerFicha entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public TrainerFicha findByNomPag(String nomPag) {
        return repository.findByNomPag(nomPag);
    }

    @Override
    public TrainerFichaPOJO findByNomPagPar(String nomPag) {
        return repository.findByNomPagPar(nomPag);
    }
}
