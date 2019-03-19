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
    public VideoAudioFavorito findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findOne(id);
    }

    @Override
    public VideoAudioFavorito findOneWithFT(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
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
    public List<VideoAudioFavorito> findByIdsIn(List<Long> ids) {
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
    public void actualizarFlagActivoById(Long id, boolean flagActivo) {

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
    public VideoAudioFavorito getVideoAudioFavoritoAudio(Long clienteId, Long idaudio)
    {
        return repository.findByClienteAudio(clienteId,idaudio);
    }
    @Override
    public VideoAudioFavorito getVideoAudioFavoritoVideo(Long clienteId, Long idvideo)
    {
        return repository.findByClienteVideo(clienteId,idvideo);
    }

    @Override
    public List<VideoAudioFavorito> findAllByClienteByTipo(Long clienteId, int tipo) {
        return repository.findAllByClienteByTipo(clienteId, tipo);
    }

    @Override
    public List<VideoAudioFavorito> findByClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }


}
