package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.TipoRutina;
import com.itsight.domain.dto.TipoRutinaDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TipoRutinaRepository;
import com.itsight.service.TipoRutinaService;
import com.itsight.util.Enums;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class TipoRutinaServiceImpl extends BaseServiceImpl<TipoRutinaRepository> implements TipoRutinaService {


    public TipoRutinaServiceImpl(TipoRutinaRepository repository) {
        super(repository);
    }

    @Override
    public TipoRutina save(TipoRutina entity) {
        return repository.save(entity);
    }

    @Override
    public TipoRutina update(TipoRutina entity) {
        return null;
    }

    @Override
    public TipoRutina findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TipoRutina findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TipoRutina> findAll() {
        return null;
    }

    @Override
    public List<TipoRutina> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<TipoRutina> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<TipoRutina> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<TipoRutina> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TipoRutina> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<TipoRutina> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<TipoRutina> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(TipoRutina entity, String wildcard) throws CustomValidationException {
        return null;
    }

    @Override
    public String actualizar(TipoRutina entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }





    @Override
    public List<TipoRutina> obtenerTipoRutina(String txtFiltro , String flagEstado) {

        TipoRutina tipoRutina = new TipoRutina();

        txtFiltro = txtFiltro.equals("0") ? null : txtFiltro;

        flagEstado = (flagEstado).equals("-1") ? null : flagEstado;


        tipoRutina.setNombre(txtFiltro);
        tipoRutina.setFlagActivo(BooleanUtils.toBooleanObject(flagEstado));

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();

        Example<TipoRutina> example = Example.of(tipoRutina, matcher);

        return repository.findAll(example);

    }

    @Override
    public TipoRutina obtenerTipoRutinaporId(Integer id) {
        return repository.findById(id).orElse(null);
    }



    @Override
    public TipoRutina ingresarTipoRutina(TipoRutinaDTO tipoRutina) {

        TipoRutina obj = new TipoRutina();
        BeanUtils.copyProperties(tipoRutina, obj);
        return repository.save(obj);
    }

    @Override
    public TipoRutina actualizarTipoRutina(TipoRutinaDTO tipoRutina) {

        TipoRutina obj = new TipoRutina();

        BeanUtils.copyProperties(tipoRutina, obj);


        return repository.saveAndFlush(obj);
    }

    @Override
    public void actualizarFlagActivadoRutina(Integer id) {

        try{


            TipoRutina qRTipoRutina = repository.findById(id).orElse(null);


            qRTipoRutina.setFlagActivo(!qRTipoRutina.isFlagActivo());

            repository.saveAndFlush(qRTipoRutina);
        }

        catch (Exception e) {
            e.printStackTrace();


        }

    }
}
