package com.itsight.service;

import com.itsight.domain.Video;
import com.itsight.domain.dto.RefUpload;
import com.itsight.domain.dto.VideoDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface VideoService extends BaseService<Video, Integer> {

    String obtenerNombrePorId(Integer id, String uuid);

    List<Video> obtenerTodosConJerarquia();

    List<VideoDTO> obtenerTodosConJerarquiaDto();

    RefUpload registrarConSubida(Video video);
}
