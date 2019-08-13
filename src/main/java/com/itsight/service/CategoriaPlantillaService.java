package com.itsight.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface CategoriaPlantillaService extends BaseService<CategoriaPlantilla, Integer> {

    String agregarCategoriaPlantilla(CategoriaPlantilla categoriaPlantilla) throws JsonProcessingException;
    String actualizarCategoriaPlantilla(CategoriaPlantilla categoriaPlantilla) throws JsonProcessingException;

    List<CategoriaPlantillaDTO> obtenerCategoriasbyTrainerId();

    List<CategoriaPlantillaDTO> obtenerCategoriasbyTrainerIdAndTipo(int tipoRutina);
    String actualizarFlagFavorito(CategoriaPlantilla categoriaPlantilla) throws  JsonProcessingException;
}
