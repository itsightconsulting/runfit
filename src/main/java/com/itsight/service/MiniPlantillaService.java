package com.itsight.service;

import com.itsight.domain.MiniPlantilla;
import com.itsight.domain.jsonb.DiaRutinarioPk;
import com.itsight.generic.BaseService;

import java.util.List;
import java.util.zip.ZipEntry;

public interface MiniPlantillaService extends BaseService<MiniPlantilla> {

    MiniPlantilla findByTrainerIdAndEspecificacionSubCategoriaId(int id, int espSubCatId);

    List<MiniPlantilla> findAllByTrainerId(int trainerId);

    void relacionarNuevasEspecificaciones(Integer espSubCatId);

    int findPlantillaIdsByTrainerIdAndEspecificacionSubCategoriaId(int trainerId, int esSubCatId);

    List<MiniPlantilla> findAllByListTrainerId(List<Integer> list);

    List<MiniPlantilla> findAllByListTrainerIdBySubCategoriaId(List<Integer> list,int idsubcategoria);
}
