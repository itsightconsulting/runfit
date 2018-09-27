package com.itsight.service;

import com.itsight.domain.KilometrajeBase;
import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.generic.BaseService;

import java.util.List;

public interface KilometrajeBaseService extends BaseService<KilometrajeBase> {

    List<KilometrajeBase> findAllByNivelAndDistancia(int nivel, int distancia);
}
