package com.itsight.service;

import com.itsight.domain.SubCategoriaEjercicio;
import com.itsight.generic.BaseService;

import java.util.List;

public interface SubCategoriaEjercicioService extends BaseService<SubCategoriaEjercicio, Integer> {

    void insertaArtificio();

    List<SubCategoriaEjercicio> findAllByOrderById();

}
