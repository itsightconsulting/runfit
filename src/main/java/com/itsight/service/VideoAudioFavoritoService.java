package com.itsight.service;

import com.itsight.domain.VideoAudioFavorito;
import com.itsight.generic.BaseService;

import java.util.List;

public interface VideoAudioFavoritoService extends BaseService<VideoAudioFavorito, Long> {

    VideoAudioFavorito getVideoAudioFavoritoAudio(Long clienteId, Long audioId);
    VideoAudioFavorito getVideoAudioFavoritoVideo(Long clienteId, Long videoId);
    List<VideoAudioFavorito> findAllByClienteByTipo(Long clienteId, int tipo);
    List<VideoAudioFavorito> findByClienteId(Long clienteId);

}
