package com.itsight.repository;

import com.itsight.domain.VideoAudioFavorito;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoAudioFavoritoRepository extends JpaRepository<VideoAudioFavorito, Integer> {

    @Query("SELECT U FROM VideoAudioFavorito U WHERE U.cliente.id = ?1 AND U.audio.id = ?2")
    VideoAudioFavorito findByClienteAudio(Integer clienteId, Integer audioId);


    @Query("SELECT U FROM VideoAudioFavorito U WHERE U.cliente.id = ?1 AND U.video.id = ?2")
    VideoAudioFavorito findByClienteVideo(Integer clienteId, Integer videoId);

    @Query("SELECT U FROM VideoAudioFavorito U LEFT JOIN FETCH U.video D LEFT JOIN FETCH U.audio A WHERE U.cliente.id = ?1 AND U.Tipo = ?2")
    List<VideoAudioFavorito> findAllByClienteByTipo(Integer clienteId, int tipo);
    
    @EntityGraph(value = "videoaudio_favorito")
    List<VideoAudioFavorito> findByClienteId(Integer clienteId);


}
