package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SubCategoriaPlantillaProcedureInvoker {

    boolean eliminarSubCategoria(Integer subCategoriaId) throws JsonProcessingException;
}
