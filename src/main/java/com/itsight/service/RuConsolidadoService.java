package com.itsight.service;

import com.itsight.domain.RuConsolidado;
import com.itsight.generic.BaseService;

public interface RuConsolidadoService extends BaseService<RuConsolidado, Integer> {

    void updateMatrizMejoraVelocidades(Integer rutinaId, String mVz);



}
