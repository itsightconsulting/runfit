package com.itsight.service.impl;

import com.itsight.domain.GrupoVideo;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.GrupoVideoRepository;
import com.itsight.service.GrupoVideoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GrupoVideoServiceImpl extends BaseServiceImpl<GrupoVideoRepository> implements GrupoVideoService {

    public GrupoVideoServiceImpl(GrupoVideoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<GrupoVideo> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public GrupoVideo save(GrupoVideo tipoVideo) {
        // TODO Auto-generated method stub
        return repository.save(tipoVideo);
    }

    @Override
    public GrupoVideo update(GrupoVideo tipoVideo) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(tipoVideo);
    }

    @Override
    public void delete(int tipoVideoId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(tipoVideoId));
    }

    @Override
    public GrupoVideo findOne(int tipoVideoId) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(tipoVideoId));
    }

    @Override
    public List<GrupoVideo> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public List<GrupoVideo> findByNombre(String nombres) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombres);
    }

    @Override
    public GrupoVideo findOneWithFT(int id) {
        // TODO Auto-generated method stub
        return repository.findById(new Integer(id));
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<GrupoVideo> lstGrupoVideo;
        if (comodin.equals("0") && estado.equals("-1")) {
            lstGrupoVideo = repository.findAll();
        } else {
            if (comodin.equals("0")) lstGrupoVideo = repository.findAllByFlagActivo(Boolean.valueOf(estado));
            else {
                lstGrupoVideo = !estado.equals("-1") ? repository.findAllByNombreContainingIgnoreCaseAndFlagActivo(comodin, Boolean.valueOf(estado)) : repository.findAllByNombreContainingIgnoreCase(comodin);
            }
        }
        return lstGrupoVideo;
    }

    @Override
    public String registrar(GrupoVideo entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String actualizar(GrupoVideo entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public List<GrupoVideo> encontrarGrupoConSusDepedencias() {
        return repository.findDistinctByOrderById();
    }
}
