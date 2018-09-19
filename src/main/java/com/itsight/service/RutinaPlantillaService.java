package com.itsight.service;

import com.itsight.domain.RutinaPlantilla;
import com.itsight.generic.BaseService;

import java.util.List;

public interface RutinaPlantillaService extends BaseService<RutinaPlantilla> {

    List<RutinaPlantilla> listarPorFiltroPT(int trainerId);
}
