package com.itsight.service;

import com.itsight.domain.RutinaPlantilla;
import com.itsight.domain.dto.RutinaPlantillaDTO;
import com.itsight.generic.BaseService;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface RutinaPlantillaService extends BaseService<RutinaPlantilla, Integer> {
/*
    List<RutinaPlantilla> listarPorFiltroPT(Integer trainerId);

*/
   String agregarRutinaPrediseñada(RutinaPlantillaDTO rutinaPlantillaDTO);

   String actualizarRutinaPrediseñada(RutinaPlantillaDTO rutinaPlantillaDTO);

   List<RutinaPlantillaDTO> listarRutinasPredByCatId(Integer categoriaId);

   void agregarRutinadesdePlantilla(RutinaPlantilla rutinaPlantilla,String fechaInicio,String fechaFin, Integer redFitID, Integer cliId,Integer tipoRutina);


}

