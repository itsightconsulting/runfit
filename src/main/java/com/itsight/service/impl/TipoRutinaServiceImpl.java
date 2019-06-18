package com.itsight.service.impl;

import com.itsight.domain.TipoRutina;
import com.itsight.domain.dto.TipoRutinaDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TipoRutinaRepository;
import com.itsight.service.TipoRutinaService;
import com.itsight.util.Enums;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class TipoRutinaServiceImpl extends BaseServiceImpl<TipoRutinaRepository> implements TipoRutinaService {


    public TipoRutinaServiceImpl(TipoRutinaRepository repository) {
        super(repository);
    }

    @Override
    public TipoRutina consultarTipoRutina(Integer id) {

        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminarTipoRutina(Integer id) {

        repository.deleteById(id);

    }

    @Override
    public TipoRutina ingresarTipoRutina(TipoRutinaDTO tipoRutina) {

        TipoRutina obj = new TipoRutina();

        BeanUtils.copyProperties(tipoRutina, obj);
        return repository.save(obj);
    }

    @Override
    public void actualizarTipoRutina(TipoRutinaDTO tipoRutina) {

        try{

            TipoRutina obj = new TipoRutina();
            BeanUtils.copyProperties(tipoRutina, obj);

            TipoRutina qRutina = repository.findById(obj.getId()).orElse(null);


            qRutina.setNombre(obj.getNombre());

            repository.saveAndFlush(qRutina);
        }

        catch (Exception e) {
            e.printStackTrace();


        }

    }

}
