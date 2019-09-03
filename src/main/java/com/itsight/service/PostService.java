package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Post;
import com.itsight.domain.dto.RefUpload;
import com.itsight.domain.jsonb.PostDetalle;
import com.itsight.generic.BaseService;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService extends BaseService<Post, Integer>  {

    List<Post> findAllByTrainerId(Integer trainerId);

    List<Post> findAllActivosByTrainerId(Integer trainerId);

    List<Post> findAllByTrainerIdIn(List<Integer> lstTrainerId);

    List<Post> obtenerPostFavoritos(Integer clienteId);

    void updatePostDetalle(Integer id, Integer clienteId, String cliNomFull,Boolean flagFav) throws JsonProcessingException;


    void actualizarFlagFav(Integer id, Integer clienteId, boolean flgFav);

    String actualizarEstadoPost(Integer id);

    boolean verificarExisteFav(Integer id, Integer clienteId);

    RefUpload guardarAudio(MultipartFile file, int id, Post post);

    String subirAudioAws(MultipartFile file, Integer id, String uuid, String extension) throws CustomValidationException;

}
