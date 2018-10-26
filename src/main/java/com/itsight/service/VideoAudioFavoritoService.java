package com.itsight.service;

import com.itsight.domain.Audio;
import com.itsight.domain.Video;
import com.itsight.domain.VideoAudioFavorito;
import com.itsight.generic.BaseService;

import java.util.List;

public interface VideoAudioFavoritoService extends BaseService<VideoAudioFavorito> {

    VideoAudioFavorito getVideoAudioFavoritoAudio(int idusuario, int idaudio);
    VideoAudioFavorito getVideoAudioFavoritoVideo(int idusuario, int idvideo);
    List<VideoAudioFavorito> findAllByUsuarioByTipo(int idusuario, int tipo);
    List<VideoAudioFavorito> findByUsuarioId(int idusuario);

}
