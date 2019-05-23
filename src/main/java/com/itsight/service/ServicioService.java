package com.itsight.service;

import com.itsight.domain.Servicio;
import com.itsight.domain.pojo.ServicioPOJO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface ServicioService extends BaseService<Servicio, Integer> {

    List<ServicioPOJO> findAllByTrainerId(Integer trainerId);
}
