package com.itsight.service;

import com.itsight.domain.Video;
import com.itsight.domain.dto.RefUpload;
import com.itsight.domain.dto.VideoDTO;
import com.itsight.domain.pojo.VideoPOJO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface VideoService extends BaseService<Video, Integer> {

    String obtenerNombrePorId(Integer id, String uuid);

    List<Video> obtenerTodosConJerarquia();

    List<VideoDTO> obtenerTodosConJerarquiaDto();

    RefUpload registrarConSubida(Video video);

    VideoPOJO obtenerFullById(Integer id);


    String addClienteServicio(Integer clienteId, Integer videoId);

    String deleteClienteServicio(Integer clienteId, Integer videoId);


    List<Video> findAllActiveByGrupoVideoIdOrderById(Integer grupoVideoId);

    List<Video> findAllVideosFavByClienteIdOrderById(Integer grupoVideoId);

}
