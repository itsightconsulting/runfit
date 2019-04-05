package com.itsight.service.impl;

import com.itsight.domain.SubCategoriaEjercicio;
import com.itsight.domain.Video;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.SubCategoriaEjercicioRepository;
import com.itsight.service.SubCategoriaEjercicioService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubCategoriaEjercicioServiceImpl extends BaseServiceImpl<SubCategoriaEjercicioRepository> implements SubCategoriaEjercicioService {

    public SubCategoriaEjercicioServiceImpl(SubCategoriaEjercicioRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<SubCategoriaEjercicio> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<SubCategoriaEjercicio> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivoOrderById(flagActivo);
    }

    @Override
    public SubCategoriaEjercicio findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.getById(id);
    }

    @Override
    public SubCategoriaEjercicio save(SubCategoriaEjercicio subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.save(subTipoVideo);
    }

    @Override
    public SubCategoriaEjercicio update(SubCategoriaEjercicio subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(subTipoVideo);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public SubCategoriaEjercicio findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaEjercicio> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaEjercicio> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<SubCategoriaEjercicio> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaEjercicio> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaEjercicio> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaEjercicio> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<SubCategoriaEjercicio> lstSubCategoriaEjercicio;
        String categoria = fk.equals("0") ? null : fk;
        comodin = comodin.equals("0") ? null : comodin;
        estado = estado.equals("-1") ? null : estado;

        if (comodin == null && estado == null && categoria == null)
            lstSubCategoriaEjercicio = repository.findAllByOrderById();
        else if (estado == null && categoria == null)
            lstSubCategoriaEjercicio = repository.findAllByNombreContainingIgnoreCase(comodin);
        else if (comodin == null && categoria == null)
            lstSubCategoriaEjercicio = repository.findAllByFlagActivoOrderById(Boolean.parseBoolean(estado));
        else if (comodin == null && estado == null)
            lstSubCategoriaEjercicio = repository.findAllByCategoriaEjercicioId(Integer.parseInt(categoria));
        else if (comodin == null)
            lstSubCategoriaEjercicio = repository.findAllByCategoriaEjercicioIdAndFlagActivoOrderById(Integer.parseInt(categoria), Boolean.valueOf(estado));
        else if (categoria == null)
            lstSubCategoriaEjercicio = repository.findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(comodin, Boolean.valueOf(estado));
        else if (estado == null)
            lstSubCategoriaEjercicio = repository.findAllByCategoriaEjercicioIdAndNombreContainingIgnoreCaseOrderById(Integer.parseInt(categoria), comodin);
        else
            lstSubCategoriaEjercicio = repository.findAllByCategoriaEjercicioIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(Integer.parseInt(categoria), comodin, Boolean.valueOf(estado));

        return lstSubCategoriaEjercicio;
    }

    @Override
    public String registrar(SubCategoriaEjercicio entity, String wildcard) {
        // TODO Auto-generated method stub
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(SubCategoriaEjercicio entity, String wildcard) {
        // TODO Auto-generated method stub
        repository.saveAndFlush(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public void insertaArtificio() {
        repository.insertArtificio();
    }

    @Override
    public List<SubCategoriaEjercicio> findAllByOrderById() {
        return repository.findAllByOrderById();
    }
}
