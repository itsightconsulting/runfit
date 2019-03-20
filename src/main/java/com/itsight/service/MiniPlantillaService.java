package com.itsight.service;

import com.itsight.domain.MiniPlantilla;
import com.itsight.generic.BaseService;

import java.util.List;

public interface MiniPlantillaService extends BaseService<MiniPlantilla, Integer> {

    MiniPlantilla findByTrainerIdAndEspecificacionSubCategoriaId(Integer id, Integer espSubCatId);

    List<MiniPlantilla> findAllByTrainerId(Integer trainerId);

    int findPlantillaIdsByTrainerIdAndEspecificacionSubCategoriaId(Integer trainerId, Integer esSubCatId);

    List<MiniPlantilla> findAllByListTrainerId(List<Integer> list);

    List<MiniPlantilla> findAllByListTrainerIdBySubCategoriaId(List<Integer> list, Integer idsubcategoria);
}
