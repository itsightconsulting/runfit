package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.Servicio;
import com.itsight.domain.jsonb.Tarifario;
import com.itsight.domain.pojo.ServicioPOJO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface ServicioService extends BaseService<Servicio, Integer> {

    List<ServicioPOJO> findAllByTrainerId(Integer trainerId);

    void actualizarByIdAndTrainerId(Integer id, String nombre, String descripcion, String incluye, String infoAdicional, List<Tarifario> tarifarios, Integer trainerId) throws JsonProcessingException;

    void addClienteServicio(Integer clienteId, Integer servicioId);

    String getTrainerCorreoById(Integer servicioId);

    Integer getTotalClientesByTrainerId(Integer trainerId);

    Integer getTotalClientesServicio();

}
