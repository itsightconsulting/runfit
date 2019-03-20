package com.itsight.service;

import com.itsight.domain.Video;
import com.itsight.generic.BaseService;

import java.util.List;

public interface VideoService extends BaseService<Video, Integer> {

    String obtenerNombrePorId(Integer id, String uuid);

    List<Video> obtenerTodosConJerarquia();
}
