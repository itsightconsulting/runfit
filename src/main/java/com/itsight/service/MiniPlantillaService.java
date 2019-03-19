package com.itsight.service;

import com.itsight.domain.MiniPlantilla;
import com.itsight.generic.BaseService;

import java.util.List;

public interface MiniPlantillaService extends BaseService<MiniPlantilla, Long> {

    MiniPlantilla findByTrainerIdAndEspecificacionSubCategoriaId(Long id, Integer espSubCatId);

    List<MiniPlantilla> findAllByTrainerId(Long trainerId);

    int findPlantillaIdsByTrainerIdAndEspecificacionSubCategoriaId(Long trainerId, Integer esSubCatId);

    List<MiniPlantilla> findAllByListTrainerId(List<Integer> list);

    List<MiniPlantilla> findAllByListTrainerIdBySubCategoriaId(List<Long> list, Integer idsubcategoria);
}
