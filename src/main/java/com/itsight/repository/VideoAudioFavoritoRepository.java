package com.itsight.repository;

import com.itsight.domain.Audio;
import com.itsight.domain.Video;
import com.itsight.domain.VideoAudioFavorito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoAudioFavoritoRepository extends JpaRepository<VideoAudioFavorito, Integer> {

    @Query("SELECT U FROM VideoAudioFavorito U WHERE U.usuario.id = ?1 AND U.audio.id = ?2")
    VideoAudioFavorito findByUsuarioAudio(int idusuario, int idaudio);


    @Query("SELECT U FROM VideoAudioFavorito U WHERE U.usuario.id = ?1 AND U.video.id = ?2")
    VideoAudioFavorito findByUsuarioVideo(int idusuario, int idvideo);

    @Query("SELECT U FROM VideoAudioFavorito U LEFT JOIN FETCH U.video D LEFT JOIN FETCH U.audio A WHERE U.usuario.id = ?1 AND U.Tipo = ?2")
    List<VideoAudioFavorito> findAllByUsuarioByTipo(int idusuario, int tipo);

    //@Query("SELECT U FROM VideoAudioFavorito U JOIN FETCH U.video D JOIN FETCH U.audio A WHERE U.usuario.id = ?1")

    @EntityGraph(value = "videoaudio_favorito")
    List<VideoAudioFavorito> findByUsuarioId(int idusuario);


}
