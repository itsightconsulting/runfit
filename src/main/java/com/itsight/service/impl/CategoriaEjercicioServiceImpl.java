package com.itsight.service.impl;

import com.itsight.domain.CategoriaEjercicio;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.CategoriaEjercicioRepository;
import com.itsight.service.CategoriaEjercicioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaEjercicioServiceImpl extends BaseServiceImpl<CategoriaEjercicioRepository> implements CategoriaEjercicioService {


    public CategoriaEjercicioServiceImpl(CategoriaEjercicioRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<CategoriaEjercicio> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public CategoriaEjercicio save(CategoriaEjercicio tipoVideo) {
        // TODO Auto-generated method stub
        return repository.save(tipoVideo);
    }

    @Override
    public CategoriaEjercicio update(CategoriaEjercicio tipoVideo) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(tipoVideo);
    }

    @Override
    public void delete(int tipoVideoId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(tipoVideoId));
    }

    @Override
    public CategoriaEjercicio findOne(int tipoVideoId) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(tipoVideoId));
    }

    @Override
    public List<CategoriaEjercicio> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public List<CategoriaEjercicio> findByNombre(String nombres) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombres);
    }

    @Override
    public CategoriaEjercicio findOneWithFT(int id) {
        // TODO Auto-generated method stub
        return repository.findById(new Integer(id));
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaEjercicio> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaEjercicio> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaEjercicio> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaEjercicio> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CategoriaEjercicio> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<CategoriaEjercicio> lstCategoriaEjercicio;
        if (comodin.equals("0") && estado.equals("-1")) {
            lstCategoriaEjercicio = repository.findAll();
        } else {
            if (comodin.equals("0")) lstCategoriaEjercicio = repository.findAllByFlagActivo(Boolean.valueOf(estado));
            else {
                lstCategoriaEjercicio = !estado.equals("-1") ? repository.findAllByNombreContainingIgnoreCaseAndFlagActivo(comodin, Boolean.valueOf(estado)) : repository.findAllByNombreContainingIgnoreCase(comodin);
            }
        }
        return lstCategoriaEjercicio;
    }

    @Override
    public String registrar(CategoriaEjercicio entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String actualizar(CategoriaEjercicio entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public List<CategoriaEjercicio> encontrarCategoriaConSusDepedencias() {
        return repository.findDistinctByOrderById();
    }
}
