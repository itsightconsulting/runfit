package com.itsight.service;

import com.itsight.domain.CategoriaEjercicio;
import com.itsight.generic.BaseService;

import java.util.List;

public interface CategoriaEjercicioService extends BaseService<CategoriaEjercicio> {

    List<CategoriaEjercicio> encontrarCategoriaConSusDepedencias();
}
