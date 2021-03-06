package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Servicio;
import com.itsight.domain.jsonb.Tarifario;
import com.itsight.domain.pojo.ServicioPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ServicioRepository;
import com.itsight.service.ServicioService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioServiceImpl extends BaseServiceImpl<ServicioRepository> implements ServicioService {

    public ServicioServiceImpl(ServicioRepository repository) {
        super(repository);
    }

    @Override
    public Servicio save(Servicio entity) {
        return repository.save(entity);
    }

    @Override
    public Servicio update(Servicio entity) {
        return repository.save(entity);
    }

    @Override
    public Servicio findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Servicio findOneWithFT(Integer id) {
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
    public List<Servicio> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Servicio> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Servicio> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Servicio> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Servicio> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Servicio> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Servicio> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Servicio> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Servicio entity, String wildcard) throws CustomValidationException {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(Servicio entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public List<ServicioPOJO> findAllByTrainerId(Integer trainerId) {
        return repository.findAllByTrainerId(trainerId);
    }

    @Override
    public void actualizarByIdAndTrainerId(Integer id, String nombre, String descripcion, String incluye, String infoAdicional, List<Tarifario> tarifarios, Integer trainerId) throws JsonProcessingException {
        String tarifariosString = new ObjectMapper().writeValueAsString(tarifarios);
        repository.updateByIdAndTrainerId(id, nombre, descripcion, incluye, infoAdicional, tarifariosString, trainerId);
    }

    @Override
    public void addClienteServicio(Integer clienteId, Integer servicioId) {
        repository.addClienteServicio(clienteId, servicioId);
    }

    @Override
    public String getTrainerCorreoById(Integer servicioId) {
        return repository.findTrainerCorreoById(servicioId);
    }

    @Override
    public Integer getTotalClientesByTrainerId(Integer trainerId) {
        return repository.getTotalClientesByTrainerId(trainerId);
    }

    @Override
    public Integer getTotalClientesByTrainerIdEmpresa(Integer trainerId) {
        return repository.getTotalClientesByTrainerIdEmpresa(trainerId);
    }

    @Override
    public Integer getTotalClientesServicio() {
        return repository.getTotalClientesServicios();
    }


}
