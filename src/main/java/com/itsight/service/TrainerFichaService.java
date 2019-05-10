package com.itsight.service;

import com.itsight.domain.TrainerFicha;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface TrainerFichaService extends BaseService<TrainerFicha, Integer> {

    List<TrainerFichaPOJO> findAllWithFgEnt();

    TrainerFichaPOJO findByNomPagPar(String nomPag);

    TrainerFichaPOJO findByTrainerId(Integer trainerId);
}
