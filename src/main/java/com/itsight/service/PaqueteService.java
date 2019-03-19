package com.itsight.service;

import com.itsight.domain.Paquete;
import com.itsight.generic.BaseService;

import java.util.List;

public interface PaqueteService extends BaseService<Paquete, Integer> {

    List<Paquete> listAll();

    Paquete getPaqueteById(Integer paqueteId);

    List<Paquete> findAllByFlagActivo(Boolean flagActivo);

    List<Paquete> findAllByNombreContainingOrDescripcionContaining(String nombre, String descripcion);

}
