package com.itsight.service;

import com.itsight.domain.RutinaPlantilla;
import com.itsight.domain.dto.RutinaPlantillaDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface RutinaPlantillaService extends BaseService<RutinaPlantilla, Integer> {
/*
    List<RutinaPlantilla> listarPorFiltroPT(Integer trainerId);

*/
   String agregarRutinaPrediseñada(RutinaPlantillaDTO rutinaPlantillaDTO);

   String actualizarRutinaPrediseñada(RutinaPlantillaDTO rutinaPlantillaDTO);

   List<RutinaPlantillaDTO> listarRutinasPredByCatId(Integer categoriaId);


}

