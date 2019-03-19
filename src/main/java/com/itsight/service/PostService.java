package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.Post;
import com.itsight.domain.jsonb.PostDetalle;
import com.itsight.generic.BaseService;

import java.util.List;

public interface PostService extends BaseService<Post, Long> {

    List<Post> findAllByTrainerId(Long trainerId);

    List<Post> findAllByTrainerIdIn(List<Integer> lstTrainerId);

    List<Post> obtenerPostFavoritos(Long clienteId);

    void updatePostDetalle(Long id, Long clienteId, String cliNomFull, Boolean flagLiked, Boolean flagFav) throws JsonProcessingException;

    void actualizarFlagLiked(Long id, Long clienteId, boolean flgLiked);

    void actualizarFlagFav(Long id, Long clienteId, boolean flgFav);

    boolean verificarExisteLike(Long id, Long clienteId);
}
