package com.itsight.service.impl;

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
