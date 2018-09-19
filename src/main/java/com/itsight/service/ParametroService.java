package com.itsight.service;

import com.itsight.domain.Parametro;

import java.util.List;

public interface ParametroService {

    List<Parametro> listAll();

    Parametro add(Parametro parametro);

    Parametro update(Parametro parametro);

    void delete(int parametroId);

    Parametro getParametroById(int parametroId);

    Parametro findByClave(String clave);

    List<Parametro> findAllByClave(String clave);

}
