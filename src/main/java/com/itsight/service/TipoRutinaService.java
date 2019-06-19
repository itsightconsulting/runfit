package com.itsight.service;

import com.itsight.domain.Rutina;
import com.itsight.domain.TipoRutina;
import com.itsight.domain.dto.TipoRutinaDTO;

import java.util.List;

public interface TipoRutinaService {

    List<TipoRutina> obtenerTipoRutina(String txtFiltro , String flagEstado);
    TipoRutina ingresarTipoRutina(TipoRutinaDTO tipoRutina);
    void actualizarFlagActivadoRutina(Integer id);
    TipoRutina obtenerTipoRutinaporId(Integer id);

    TipoRutina actualizarTipoRutina(TipoRutinaDTO tipoRutina);
}
