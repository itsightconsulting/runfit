package com.itsight.service;

import com.itsight.domain.Paquete;

import java.util.List;

public interface PaqueteService {

    List<Paquete> listAll();

    Paquete add(Paquete paquete);

    Paquete update(Paquete paquete);

    void delete(int paqueteId);

    Paquete getPaqueteById(int paqueteId);

    List<Paquete> findAllByFlagActivo(Boolean flagActivo);

    List<Paquete> findAllByNombreContainingOrDescripcionContaining(String nombre, String descripcion);

    Paquete findRouteNamesById(int paqueteId);

}
