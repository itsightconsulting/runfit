package com.itsight.service;

import com.itsight.domain.CategoriaVideo;
import com.itsight.domain.SubCategoriaEjercicio;
import com.itsight.generic.BaseService;

import java.util.List;

public interface CategoriaVideoService extends BaseService<CategoriaVideo, Integer> {

    void insertArtificio();

    List<CategoriaVideo> findAllByOrderById();
}
