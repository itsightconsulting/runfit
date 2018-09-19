package com.itsight.service;

import com.itsight.domain.MiniPlantilla;
import com.itsight.domain.jsonb.DiaRutinarioPk;
import com.itsight.generic.BaseService;

import java.util.List;
import java.util.zip.ZipEntry;

public interface MiniPlantillaService extends BaseService<MiniPlantilla> {

    MiniPlantilla findByUsuarioIdAndEspecificacionSubCategoriaId(int id, int espSubCatId);

    List<MiniPlantilla> findAllByUsuarioId(int usuarioId);

    void relacionarNuevasEspecificaciones(Integer espSubCatId);

    int findPlantillaIdsByUsuarioIdAndEspecificacionSubCategoriaId(int trainerId, int esSubCatId);
}
