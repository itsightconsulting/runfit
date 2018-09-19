package com.itsight.service;

import com.itsight.domain.EspecificacionSubCategoria;
import com.itsight.generic.BaseService;

import java.util.List;

public interface EspecificacionSubCategoriaService extends BaseService<EspecificacionSubCategoria> {

    List<EspecificacionSubCategoria> listarPorSubCategoria(int subCategoriaEjercicioId);
}
