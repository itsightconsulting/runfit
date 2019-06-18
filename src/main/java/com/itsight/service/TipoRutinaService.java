package com.itsight.service;

import com.itsight.domain.Rutina;
import com.itsight.domain.TipoRutina;
import com.itsight.domain.dto.TipoRutinaDTO;

public interface TipoRutinaService {

    TipoRutina consultarTipoRutina(Integer id);
    void eliminarTipoRutina(Integer id);
    TipoRutina ingresarTipoRutina(TipoRutinaDTO tipoRutina);
    void actualizarTipoRutina(TipoRutinaDTO tipoRutina);



}
