package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.advice.CustomValidationException;
import com.itsight.controller.PostController;
import com.itsight.domain.Post;
import com.itsight.domain.dto.RefUpload;
import com.itsight.domain.jsonb.PostDetalle;
import com.itsight.domain.pojo.AwsStresPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.PostRepository;
import com.itsight.service.PostService;
import com.itsight.util.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.itsight.util.Enums.Msg.FAIL_SUBIDA_IMG_GENERICA;
import static com.itsight.util.Enums.Msg.SUCCESS_SUBIDA_IMG;
import static com.itsight.util.Enums.ResponseCode.*;

@Service
@Transactional
public class PostServiceImpl extends BaseServiceImpl<PostRepository> implements PostService {

    private static final Logger LOGGER = LogManager.getLogger(PostController.class);

    public PostServiceImpl(PostRepository repository) {
        super(repository);
    }

    @Override
    public Post save(Post entity) {
        return repository.save(entity);
    }

    @Override
    public Post update(Post entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Post findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Post findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Post> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Post> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Post> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Post> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Post> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Post> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Post> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Post entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(Post entity, String wildcard) {
        repository.saveAndFlush(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public List<Post> findAllByTrainerId(Integer trainerId) {
        return repository.findAllByTrainerId(trainerId);
    }

    @Override
    public List<Post> findAllActivosByTrainerId(Integer trainerId) {

        return repository.findAllActivosByTrainerId(trainerId);
    }

    @Override
    public List<Post> findAllByTrainerIdIn(List<Integer> lstTrainerId) {
        return repository.findAllByTrainerIdIn(lstTrainerId);
    }

    @Override
    public void updatePostDetalle(Integer id, Integer clienteId, String cliNomFull, Boolean flagFav) throws JsonProcessingException {
        PostDetalle postDetalle = new PostDetalle(clienteId, cliNomFull,flagFav, new Date(), null);
        repository.updatePostDetalle(id, new ObjectMapper().writeValueAsString(postDetalle));
    }

    @Override
    public boolean verificarExisteFav(Integer id, Integer clienteId) {
        return repository.checkFavExists(id, clienteId).orElse(false);
    }

    @Override
    public void actualizarFlagFav(Integer id, Integer clienteId, boolean flgFav) {
        repository.actualizarPostDetalleFlag(id, clienteId, flgFav, "flgFav", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    @Override
    public List<Post> obtenerPostFavoritos(Integer clienteId) {
        return repository.getPostFavoritos(clienteId);
    }


    @Override
    public RefUpload guardarAudio(int id, Post post, String extensionAudio) {
        try {
            String extension = "." + extensionAudio;
            RefUpload fileUpload = new RefUpload();

            if(id == 0) {
                fileUpload.setExtFile(extension);
                post.setRutaWeb(fileUpload.getUuid() + fileUpload.getExtFile());
                post.setUuid(fileUpload.getUuid());
                post.setFlagActivo(true);
                post.setLstDetalle(new ArrayList<>());
                // Pasando la imagen  o archivo desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                Post p = repository.save(post);
                fileUpload.setId(p.getId());
            }else{
                Post currentPost = repository.findById(id).orElse(null);
                currentPost.setLstDetalle(new ArrayList<>());
                currentPost.setPeso(post.getPeso());
                currentPost.setDuracion(post.getDuracion());
                currentPost.setTitulo(post.getTitulo());
                currentPost.setDescripcion(post.getDescripcion());
                Post p = repository.saveAndFlush(currentPost);
                fileUpload.setId(p.getId());
                fileUpload.setUuid(p.getUuid());
            }
            return fileUpload;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String subirFile(MultipartFile file, Integer id, String uuid, String extension) throws CustomValidationException {
        boolean success = uploadImageToAws3(file, new AwsStresPOJO(aws3accessKey, aws3secretKey, aws3region, aws3PostBucket, "audio/"+id+"/", uuid, extension), LOGGER);
        if(success){
            return SUCCESS_SUBIDA_IMG.get();
        }
        throw new CustomValidationException(FAIL_SUBIDA_IMG_GENERICA.get(), EX_VALIDATION_FAILED.get());
    }

    @Override
    public String actualizarEstadoPost(Integer id) {

        Post post = this.findOne(id);
        boolean estadoActual = post.isFlagActivo();
        post.setFlagActivo(!post.isFlagActivo());
        this.update(post);
        int tipo = post.getTipo();

        if(tipo == 1){
            String[] splitNameFile = post.getRutaWeb().split("\\.");
            String extension = "." + splitNameFile[splitNameFile.length - 1];
            boolean success = addTagStatusToAws3(new AwsStresPOJO(aws3accessKey, aws3secretKey, aws3region, aws3PostBucket, "audio/"+id+"/", post.getUuid().toString(),extension), estadoActual, LOGGER);
            if(success){
                return EXITO_GENERICA.get();
            }
            return EX_GENERIC.get();
        }
        return EXITO_GENERICA.get();
    }
}
