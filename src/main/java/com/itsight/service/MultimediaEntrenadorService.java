package com.itsight.service;

import com.itsight.domain.MultimediaDetalle;
import com.itsight.domain.MultimediaEntrenador;
import com.itsight.generic.BaseService;

import java.util.List;

public interface MultimediaEntrenadorService extends BaseService<MultimediaEntrenador> {

    List<MultimediaEntrenador> findByTrainer(int id);
    List<MultimediaEntrenador> findByTrainerTop(int id);
    int findDetalleTopCantidad(int id);
    List<MultimediaEntrenador> findByListEntrenador(List<String> list);
    List<MultimediaDetalle> findByIdEntrenador(int id);

}
