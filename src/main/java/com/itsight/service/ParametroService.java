package com.itsight.service;

import com.itsight.domain.Parametro;

import java.util.List;

public interface ParametroService {

    List<Parametro> listAll();

    Parametro add(Parametro parametro);

    Parametro update(Parametro parametro);

    void delete(Integer id);

    Parametro getParametroById(Integer id);

    Parametro findByClave(String clave);

    List<Parametro> findAllByClave(String clave);

}
