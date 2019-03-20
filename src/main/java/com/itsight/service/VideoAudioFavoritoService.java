package com.itsight.service;

import com.itsight.domain.VideoAudioFavorito;
import com.itsight.generic.BaseService;

import java.util.List;

public interface VideoAudioFavoritoService extends BaseService<VideoAudioFavorito, Integer> {

    VideoAudioFavorito getVideoAudioFavoritoAudio(Integer clienteId, Integer audioId);
    VideoAudioFavorito getVideoAudioFavoritoVideo(Integer clienteId, Integer videoId);
    List<VideoAudioFavorito> findAllByClienteByTipo(Integer clienteId, int tipo);
    List<VideoAudioFavorito> findByClienteId(Integer clienteId);

}
