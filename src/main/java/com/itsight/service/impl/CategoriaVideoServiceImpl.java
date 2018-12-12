package com.itsight.service.impl;

import com.itsight.domain.CategoriaVideo;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.CategoriaVideoRepository;
import com.itsight.service.CategoriaVideoService;
import com.itsight.util.Enums;
import com.itsight.util.Enums.ResponseCode;
import com.itsight.util.Utilitarios;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaVideoServiceImpl extends BaseServiceImpl<CategoriaVideoRepository> implements CategoriaVideoService {

    public CategoriaVideoServiceImpl(CategoriaVideoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<CategoriaVideo> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<CategoriaVideo> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivoOrderById(flagActivo);
    }

    @Override
    public CategoriaVideo findOne(int subTipoVideoId) {
        // TODO Auto-generated method stub
        return repository.findById(subTipoVideoId);
    }

    @Override
    public CategoriaVideo save(CategoriaVideo subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.save(subTipoVideo);
    }

    @Override
    public CategoriaVideo update(CategoriaVideo subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(subTipoVideo);
    }

    @Override
    public void delete(int subTipoVideoId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(subTipoVideoId));
    }

    @Override
    public CategoriaVideo findOneWithFT(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaVideo> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaVideo> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<CategoriaVideo> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaVideo> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaVideo> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaVideo> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<CategoriaVideo> lstCategoriaVideo;
        String grupo = fk.equals("0") ? null : fk;
        comodin = comodin.equals("0") ? null : comodin;
        estado = estado.equals("-1") ? null : estado;

        if (comodin == null && estado == null && grupo == null)
            lstCategoriaVideo = repository.findAllByOrderById();
        else if (estado == null && grupo == null)
            lstCategoriaVideo = repository.findAllByNombreContainingIgnoreCase(comodin);
        else if (comodin == null && grupo == null)
            lstCategoriaVideo = repository.findAllByFlagActivoOrderById(Boolean.parseBoolean(estado));
        else if (comodin == null && estado == null)
            lstCategoriaVideo = repository.findAllByCategoriaVideoId(Integer.parseInt(grupo));
        else if (comodin == null)
            lstCategoriaVideo = repository.findAllByCategoriaVideoIdAndFlagActivoOrderById(Integer.parseInt(grupo), Boolean.valueOf(estado));
        else if (grupo == null)
            lstCategoriaVideo = repository.findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(comodin, Boolean.valueOf(estado));
        else if (estado == null)
            lstCategoriaVideo = repository.findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseOrderById(Integer.parseInt(grupo), comodin);
        else
            lstCategoriaVideo = repository.findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(Integer.parseInt(grupo), comodin, Boolean.valueOf(estado));

        return lstCategoriaVideo;
    }

    @Override
    public String registrar(CategoriaVideo entity, String wildcard) {
        // TODO Auto-generated method stub
        repository.save(entity);
        return Utilitarios.customResponse(ResponseCode.REGISTRO.get(), null);
    }

    @Override
    public String actualizar(CategoriaVideo entity, String wildcard) {
        repository.saveAndFlush(entity);
        return Utilitarios.customResponse(ResponseCode.ACTUALIZACION.get(), null);
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public void insertArtificio() {
        repository.insertArtificio();
    }

    @Override
    public List<CategoriaVideo> findAllByOrderById() {
        return repository.findAllByOrderById();
    }
}