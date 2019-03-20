package com.itsight.service;

import com.itsight.domain.MiniRutina;
import com.itsight.generic.BaseService;

import java.util.List;

public interface MiniRutinaService extends BaseService<MiniRutina, Integer> {

    MiniRutina findByTrainerIdAndCategoriaId(Integer id, Integer categoriaId);

    List<MiniRutina> findAllByTrainerId(Integer trainerId);

    List<Integer> findAllCategoriaIdByTrainerId(Integer trainerId);

    MiniRutina findByCategoriaIdAndTrainerId(Integer catId, Integer trainerId);
}
