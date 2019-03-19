package com.itsight.service;

import com.itsight.domain.EspecificacionSubCategoria;
import com.itsight.generic.BaseService;

import java.util.List;

public interface EspecificacionSubCategoriaService extends BaseService<EspecificacionSubCategoria, Integer> {

    List<EspecificacionSubCategoria> listarPorSubCategoria(Integer subCategoriaEjercicioId);
    List<EspecificacionSubCategoria> findBySubCategoriaEjercicioId(Integer id);
}
