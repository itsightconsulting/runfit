package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.Post;
import com.itsight.domain.jsonb.PostDetalle;
import com.itsight.generic.BaseService;

import java.util.List;

public interface PostService extends BaseService<Post> {

    void actualizacionDetalle(int id, List<PostDetalle> postDetalleList) throws JsonProcessingException;

    List<Post> findAllByTrainerId(int trainerId);

    List<Post> findAllByTrainerIdIn(List<Integer> lstTrainerId);

    List<Post> obtenerPostFavoritos(int clienteId);

    void updatePostDetalle(int id, int clienteId, String cliNomFull, Boolean flagLiked, Boolean flagFav) throws JsonProcessingException;

    void actualizarFlagLiked(int id, int clienteId, boolean flgLiked);

    void actualizarFlagFav(int id, int clienteId, boolean flgFav);

    boolean verificarExisteLike(int id, int clienteId);
}
