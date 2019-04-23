package com.itsight.service;

import com.itsight.domain.TrainerFicha;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface TrainerFichaService extends BaseService<TrainerFicha, Integer> {

    List<TrainerFichaPOJO> findAllWithFgEnt();

    TrainerFicha findByNomPag(String nomPag);

    TrainerFichaPOJO findByNomPagPar(String nomPag);
}
