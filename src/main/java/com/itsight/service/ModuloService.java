package com.itsight.service;

import com.itsight.domain.Modulo;
import com.itsight.generic.BaseService;

public interface ModuloService extends BaseService<Modulo, Integer> {

    Modulo findOneByNombre(String nombre);
}
