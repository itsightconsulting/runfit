package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.AnuncioReceptor;
import com.itsight.domain.pojo.AnuncioPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.AnuncioReceptorRepository;
import com.itsight.service.AnuncioReceptorService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;

@Transactional
@Service
public class AnuncioReceptorServiceImpl extends BaseServiceImpl<AnuncioReceptorRepository> implements AnuncioReceptorService {

    public AnuncioReceptorServiceImpl(AnuncioReceptorRepository repository) {
        super(repository);
    }

    @Override
    public AnuncioReceptor save(AnuncioReceptor entity) {
        return repository.save(entity);
    }

    @Override
    public AnuncioReceptor update(AnuncioReceptor entity) {
        return repository.save(entity);
    }

    @Override
    public AnuncioReceptor findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public AnuncioReceptor findOneWithFT(Integer id) {
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
    public List<AnuncioReceptor> findAll() {
        return repository.findAll();
    }

    @Override
    public List<AnuncioReceptor> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<AnuncioReceptor> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<AnuncioReceptor> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<AnuncioReceptor> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<AnuncioReceptor> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<AnuncioReceptor> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<AnuncioReceptor> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(AnuncioReceptor entity, String wildcard) throws CustomValidationException {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(AnuncioReceptor entity, String wildcard) {
        repository.save(entity);
        return ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public void guardarMultiple(Integer anuncioTrainerId, Integer trainerId) {
        repository.saveMultiple(anuncioTrainerId, trainerId);
    }

    @Override
    public List<AnuncioPOJO> findAllAnuncioByClienteId(Integer clienteId) {
        return repository.findAllAnuncioByClienteId(clienteId);
    }

    @Override
    public String actualizarFlagLeidoByIdAndClienteId(Integer id, Integer clienteId) {
        repository.updateFlagLeidoByIdAndClienteId(id, clienteId);
        return ACTUALIZACION.get();
    }

    @Override
    public String actualizarAllFlagLeidoByClienteId(Integer clienteId) {
        repository.updateAllFlagLeidoByIdAndClienteId(clienteId);
        return ACTUALIZACION.get();
    }
}
