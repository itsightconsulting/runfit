package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.domain.Post;
import com.itsight.domain.jsonb.PostDetalle;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.PostRepository;
import com.itsight.service.PostService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl extends BaseServiceImpl<PostRepository> implements PostService {

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
    public Post findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public Post findOneWithFT(int id) {
        return null;
    }

    @Override
    public void delete(int id) {
        repository.delete(new Integer(id));
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
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public void actualizacionDetalle(int id, List<PostDetalle> postDetalleList) throws JsonProcessingException {
        String detalle = new ObjectMapper().writeValueAsString(postDetalleList);
        repository.updatePostDetalle(id, detalle);
    }

    @Override
    public List<Post> findAllByTrainerId(int trainerId) {
        return repository.findAllByTrainerId(trainerId);
    }

    @Override
    public List<Post> findAllByTrainerIdIn(List<Integer> lstTrainerId) {
        return repository.findAllByTrainerIdIn(lstTrainerId);
    }

    @Override
    public void updatePostDetalle(int id, int clienteId, String cliNomFull, Boolean flagLiked, Boolean flagFav) throws JsonProcessingException {
        PostDetalle postDetalle = new PostDetalle(clienteId, cliNomFull, flagLiked, flagFav, new Date(), null);
        repository.updatePostDetalle(id, new ObjectMapper().writeValueAsString(postDetalle));
    }

    @Override
    public boolean verificarExisteLike(int id, int clienteId) {
        return repository.checkLikeExists(id, clienteId).orElse(false);
    }

    @Override
    public void actualizarFlagLiked(int id, int clienteId, boolean flgLiked) {
        repository.updateGenericBooleanColumnByIdAndClienteId(id, clienteId, flgLiked, "flgLiked");
    }

    @Override
    public void actualizarFlagFav(int id, int clienteId, boolean flgFav) {
        repository.updateGenericBooleanColumnByIdAndClienteId(id, clienteId, flgFav, "flgFav");
    }

    @Override
    public List<Post> obtenerPostFavoritos(int clienteId) {
        return repository.getPostFavoritos(clienteId);
    }
}
