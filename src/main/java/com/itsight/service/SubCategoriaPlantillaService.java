package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.SubCategoriaPlantilla;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.domain.dto.SubCategoriaPlantillaDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface SubCategoriaPlantillaService extends BaseService<SubCategoriaPlantilla, Integer> {


    String agregarSubCategoriaPlantilla(SubCategoriaPlantilla subCategoriaPlantilla, Integer categoriaId) throws CustomValidationException;
    String actualizarSubCategoriaPlantilla(SubCategoriaPlantilla subCategoriaPlantillaId) throws JsonProcessingException;

    List<SubCategoriaPlantillaDTO> obtenerSubCategoriasbyCategoriaId(Integer categoriaId);

    String actualizarFlagFavorito(SubCategoriaPlantilla subCategoriaPlantilla) throws  JsonProcessingException;
    void eliminarSubCategoriaPlantilla(Integer subCatId);

}
