package com.itsight.service;

import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.generic.BaseService;

import java.util.List;

public interface PorcentajesKilometrajeService extends BaseService<PorcentajesKilometraje> {

    List<PorcentajesKilometraje> findAllByUsuarioId(int trainerId);
}
