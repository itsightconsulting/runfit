package com.itsight.service.impl;

import com.itsight.domain.VideoAudioFavorito;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.VideoAudioFavoritoRepository;
import com.itsight.service.VideoAudioFavoritoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class VideoAudioFavoritoServiceImpl extends BaseServiceImpl<VideoAudioFavoritoRepository> implements VideoAudioFavoritoService {

    public VideoAudioFavoritoServiceImpl(VideoAudioFavoritoRepository repository) {
        super(repository);
    }

    @Override
    public VideoAudioFavorito findOne(int id) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(id));
    }

    @Override
    public VideoAudioFavorito findOneWithFT(int id) {
        return null;
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<VideoAudioFavorito> findAll() {
        return null;
    }

    @Override
    public List<VideoAudioFavorito> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<VideoAudioFavorito> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<VideoAudioFavorito> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<VideoAudioFavorito> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<VideoAudioFavorito> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<VideoAudioFavorito> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<VideoAudioFavorito> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(VideoAudioFavorito entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(VideoAudioFavorito entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public VideoAudioFavorito save(VideoAudioFavorito videoaudio) {
        // TODO Auto-generated method stub
        return repository.save(videoaudio);
    }

    @Override
    public VideoAudioFavorito update(VideoAudioFavorito entity) {
        return null;
    }

    @Override
    public VideoAudioFavorito getVideoAudioFavoritoAudio(int clienteId, int idaudio)
    {
        return repository.findByClienteAudio(clienteId,idaudio);
    }
    @Override
    public VideoAudioFavorito getVideoAudioFavoritoVideo(int clienteId, int idvideo)
    {
        return repository.findByClienteVideo(clienteId,idvideo);
    }


    @Override
    public List<VideoAudioFavorito> findAllByClienteByTipo(int clienteId, int tipo) {
        return repository.findAllByClienteByTipo(clienteId, tipo);
    }

    @Override
    public List<VideoAudioFavorito> findByClienteId(int clienteId) {
        return repository.findByClienteId(clienteId);
    }


}
