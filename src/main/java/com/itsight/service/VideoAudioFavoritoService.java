package com.itsight.service;

import com.itsight.domain.VideoAudioFavorito;
import com.itsight.generic.BaseService;

import java.util.List;

public interface VideoAudioFavoritoService extends BaseService<VideoAudioFavorito> {

    VideoAudioFavorito getVideoAudioFavoritoAudio(int clienteId, int idaudio);
    VideoAudioFavorito getVideoAudioFavoritoVideo(int clienteId, int idvideo);
    List<VideoAudioFavorito> findAllByClienteByTipo(int clienteId, int tipo);
    List<VideoAudioFavorito> findByClienteId(int clienteId);

}
