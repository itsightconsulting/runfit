package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.Post;
import com.itsight.domain.jsonb.PostDetalle;
import com.itsight.generic.BaseService;

import java.util.List;

public interface PostService extends BaseService<Post, Integer> {

    List<Post> findAllByTrainerId(Integer trainerId);

    List<Post> findAllByTrainerIdIn(List<Integer> lstTrainerId);

    List<Post> obtenerPostFavoritos(Integer clienteId);

    void updatePostDetalle(Integer id, Integer clienteId, String cliNomFull, Boolean flagLiked, Boolean flagFav) throws JsonProcessingException;

    void actualizarFlagLiked(Integer id, Integer clienteId, boolean flgLiked);

    void actualizarFlagFav(Integer id, Integer clienteId, boolean flgFav);

    boolean verificarExisteLike(Integer id, Integer clienteId);
}
