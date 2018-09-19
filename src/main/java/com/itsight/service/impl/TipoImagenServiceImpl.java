package com.itsight.service.impl;

import com.itsight.domain.TipoImagen;
import com.itsight.repository.TipoImagenRepository;
import com.itsight.service.TipoImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoImagenServiceImpl implements TipoImagenService {

    private TipoImagenRepository tipoImagenRepository;

    @Autowired
    public TipoImagenServiceImpl(TipoImagenRepository tipoImagenRepository) {
        this.tipoImagenRepository = tipoImagenRepository;
    }

    @Override
    public List<TipoImagen> listAll() {
        // TODO Auto-generated method stub
        return tipoImagenRepository.findAll();
    }

    @Override
    public TipoImagen findOneById(int tipoImagenId) {
        // TODO Auto-generated method stub
        return tipoImagenRepository.findOne(new Integer(tipoImagenId));
    }

    @Override
    public TipoImagen add(TipoImagen tipoImagen) {
        // TODO Auto-generated method stub
        return tipoImagenRepository.save(tipoImagen);
    }

    @Override
    public TipoImagen update(TipoImagen tipoImagen) {
        // TODO Auto-generated method stub
        return tipoImagenRepository.saveAndFlush(tipoImagen);
    }

    @Override
    public void delete(int tipoImagenId) {
        // TODO Auto-generated method stub
        tipoImagenRepository.delete(new Integer(tipoImagenId));
    }
}
