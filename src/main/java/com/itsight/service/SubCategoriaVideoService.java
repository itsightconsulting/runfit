package com.itsight.service;

import com.itsight.domain.SubCategoriaVideo;
import com.itsight.generic.BaseService;

import java.util.List;

public interface SubCategoriaVideoService extends BaseService<SubCategoriaVideo> {

    List<SubCategoriaVideo> listarPorCategoria(int categoriaVideoId);

}
