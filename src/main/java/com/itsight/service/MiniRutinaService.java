package com.itsight.service;

import com.itsight.domain.MiniRutina;
import com.itsight.generic.BaseService;

import java.util.List;

public interface MiniRutinaService extends BaseService<MiniRutina> {

    MiniRutina findByTrainerIdAndCategoriaId(int id, int categoriaId);

    List<MiniRutina> findAllByTrainerId(int trainerId);

    List<Integer> findAllCategoriaIdByTrainerId(int trainerId);

    MiniRutina findByCategoriaIdAndTrainerId(int catId, int trainerId);
}
