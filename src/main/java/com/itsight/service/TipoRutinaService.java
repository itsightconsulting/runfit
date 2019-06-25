package com.itsight.service;

import com.itsight.domain.TipoRutina;
import com.itsight.domain.dto.TipoRutinaDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface TipoRutinaService extends BaseService<TipoRutina, Integer> {

    List<TipoRutina> obtenerTipoRutina(String txtFiltro , String flagEstado);
    TipoRutina ingresarTipoRutina(TipoRutinaDTO tipoRutina);
    void actualizarFlagActivadoRutina(Integer id);
    TipoRutina obtenerTipoRutinaporId(Integer id);
    TipoRutina actualizarTipoRutina(TipoRutinaDTO tipoRutina);
}
