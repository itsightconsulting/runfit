package com.itsight.service;

import com.itsight.domain.TipoImagen;

import java.util.List;

public interface TipoImagenService {

    List<TipoImagen> listAll();

    TipoImagen add(TipoImagen tipoImagen);

    TipoImagen update(TipoImagen tipoImagen);

    TipoImagen findOneById(Integer id);

    void delete(Integer id);

}
