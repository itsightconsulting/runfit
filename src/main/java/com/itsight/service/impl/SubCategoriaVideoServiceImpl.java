package com.itsight.service.impl;

import com.itsight.domain.SubCategoriaVideo;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.SubCategoriaVideoRepository;
import com.itsight.service.SubCategoriaVideoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubCategoriaVideoServiceImpl extends BaseServiceImpl<SubCategoriaVideoRepository> implements SubCategoriaVideoService {

    public SubCategoriaVideoServiceImpl(SubCategoriaVideoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<SubCategoriaVideo> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<SubCategoriaVideo> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivoOrderById(flagActivo);
    }

    @Override
    public SubCategoriaVideo findOne(int subTipoVideoId) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(subTipoVideoId));
    }

    @Override
    public SubCategoriaVideo save(SubCategoriaVideo subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.save(subTipoVideo);
    }

    @Override
    public SubCategoriaVideo update(SubCategoriaVideo subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(subTipoVideo);
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(id));
    }

    @Override
    public SubCategoriaVideo findOneWithFT(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<SubCategoriaVideo> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<SubCategoriaVideo> lstSubCategoriaVideo;
        String categoria = fk.equals("0") ? null : fk;
        comodin = comodin.equals("0") ? null : comodin;
        estado = estado.equals("-1") ? null : estado;

        if (comodin == null && estado == null && categoria == null)
            lstSubCategoriaVideo = repository.findAllByOrderById();
        else if (estado == null && categoria == null)
            lstSubCategoriaVideo = repository.findAllByNombreContainingIgnoreCase(comodin);
        else if (comodin == null && categoria == null)
            lstSubCategoriaVideo = repository.findAllByFlagActivoOrderById(Boolean.parseBoolean(estado));
        else if (comodin == null && estado == null)
            lstSubCategoriaVideo = repository.findAllByCategoriaVideoId(Integer.parseInt(categoria));
        else if (comodin == null)
            lstSubCategoriaVideo = repository.findAllByCategoriaVideoIdAndFlagActivoOrderById(Integer.parseInt(categoria), Boolean.valueOf(estado));
        else if (categoria == null)
            lstSubCategoriaVideo = repository.findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(comodin, Boolean.valueOf(estado));
        else if (estado == null)
            lstSubCategoriaVideo = repository.findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseOrderById(Integer.parseInt(categoria), comodin);
        else
            lstSubCategoriaVideo = repository.findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(Integer.parseInt(categoria), comodin, Boolean.valueOf(estado));
        return lstSubCategoriaVideo;
    }

    @Override
    public String registrar(SubCategoriaVideo entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String actualizar(SubCategoriaVideo entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public List<SubCategoriaVideo> listarPorCategoria(int categoriaVideoId) {
        return repository.findByCategoriaId(categoriaVideoId);
    }
}
