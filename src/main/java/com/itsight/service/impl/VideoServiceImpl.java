package com.itsight.service.impl;

import com.itsight.domain.Video;
import com.itsight.domain.dto.VideoDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.VideoRepository;
import com.itsight.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VideoServiceImpl extends BaseServiceImpl<VideoRepository> implements VideoService {

    public VideoServiceImpl(VideoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Video save(Video entity) {
        // TODO Auto-generated method stub
        return repository.save(entity);
    }

    @Override
    public Video update(Video entity) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(entity);
    }

    @Override
    public Video findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public Video findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return repository.getById(id);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Video> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<Video> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Video> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Video> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Video> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Video> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Video> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Video> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<Video> lstVideo;
        String subCat = fk.equals("0") ? null : fk;
        comodin = comodin.equals("0") ? null : comodin;
        estado = estado.equals("-1") ? null : estado;

        if (comodin == null && estado == null && subCat == null)
            lstVideo = repository.findAllByOrderById();
        else if (estado == null && subCat == null)
            lstVideo = repository.findAllByNombreContainingIgnoreCase(comodin);
        else if (comodin == null && subCat == null)
            lstVideo = repository.findAllByFlagActivoOrderById(Boolean.parseBoolean(estado));
        else if (comodin == null && estado == null)
            lstVideo = repository.findAllBySubCategoriaVideoId(Integer.parseInt(subCat));
        else if (comodin == null)
            lstVideo = repository.findAllBySubCategoriaVideoIdAndFlagActivoOrderById(Integer.parseInt(subCat), Boolean.valueOf(estado));
        else if (subCat == null)
            lstVideo = repository.findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(comodin, Boolean.valueOf(estado));
        else if (estado == null)
            lstVideo = repository.findAllBySubCategoriaVideoIdAndNombreContainingIgnoreCaseOrderById(Integer.parseInt(subCat), comodin);
        else
            lstVideo = repository.findAllBySubCategoriaVideoIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(Integer.parseInt(subCat), comodin, Boolean.valueOf(estado));
        return lstVideo;
    }

    @Override
    public String registrar(Video entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String actualizar(Video entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String obtenerNombrePorId(Integer id, String uuid) {
        // TODO Auto-generated method stub
        return repository.findNombreByIdAndUuid(id, UUID.fromString(uuid));
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public List<Video> obtenerTodosConJerarquia() {
        return repository.findAllTree();
    }

    @Override
    public List<VideoDTO> obtenerTodosConJerarquiaDto() {
        return null;
    }
}
