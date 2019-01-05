package com.itsight.service.impl;

import com.itsight.domain.MultimediaDetalle;
import com.itsight.domain.MultimediaEntrenador;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.MultimediaEntrenadorRepository;
import com.itsight.service.MultimediaEntrenadorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MultimediaEntrenadorImpl extends BaseServiceImpl<MultimediaEntrenadorRepository> implements MultimediaEntrenadorService {

    public MultimediaEntrenadorImpl(MultimediaEntrenadorRepository repository) {
        super(repository);
    }

    @Override
    public MultimediaEntrenador save(MultimediaEntrenador entity) {
        return repository.save(entity);
    }

    @Override
    public MultimediaEntrenador update(MultimediaEntrenador entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public MultimediaEntrenador findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public MultimediaEntrenador findOneWithFT(int id) {
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
    public List<MultimediaEntrenador> findAll() {
        return repository.findAll();
    }

    @Override
    public List<MultimediaEntrenador> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<MultimediaEntrenador> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<MultimediaEntrenador> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<MultimediaEntrenador> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<MultimediaEntrenador> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<MultimediaEntrenador> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<MultimediaEntrenador> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(MultimediaEntrenador entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(MultimediaEntrenador entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        repository.findAllByOrderById();
    }

    @Override
    public List<MultimediaEntrenador> findByTrainer(int id) {
        return  repository.findAllbyTrainer(id);
    }

    @Override
    public List<MultimediaEntrenador> findByTrainerTop(int id) {
        return  repository.findAllbyTrainerTop(id);
    }

    @Override
    public int findDetalleTopCantidad(int id) {
        return  repository.findDetalleTopCantidad(id);
    }

    @Override
    public List<MultimediaEntrenador> findByListEntrenador(List<String> ids) {
        return  repository.findByListEntrenador(ids);
    }

    @Override
    public List<MultimediaDetalle> findByIdEntrenador(int id) {
        return  repository.findByIdEntrenador(id);
    }
}
