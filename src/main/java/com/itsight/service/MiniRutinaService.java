package com.itsight.service;

import com.itsight.domain.MiniRutina;
import com.itsight.generic.BaseService;

import java.util.List;

public interface MiniRutinaService extends BaseService<MiniRutina, Long> {

    MiniRutina findByTrainerIdAndCategoriaId(Long id, Integer categoriaId);

    List<MiniRutina> findAllByTrainerId(Long trainerId);

    List<Integer> findAllCategoriaIdByTrainerId(Long trainerId);

    MiniRutina findByCategoriaIdAndTrainerId(Integer catId, Long trainerId);
}
