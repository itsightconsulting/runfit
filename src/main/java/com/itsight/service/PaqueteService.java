package com.itsight.service;

import com.itsight.domain.Paquete;
import com.itsight.generic.BaseService;

import java.util.List;

public interface PaqueteService extends BaseService<Paquete> {

    List<Paquete> listAll();

    Paquete getPaqueteById(int paqueteId);

    List<Paquete> findAllByFlagActivo(Boolean flagActivo);

    List<Paquete> findAllByNombreContainingOrDescripcionContaining(String nombre, String descripcion);

}
