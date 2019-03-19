package com.itsight.service;

import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.domain.dto.PorcentajeKilometrajeDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface PorcentajesKilometrajeService extends BaseService<PorcentajesKilometraje, Integer> {

    PorcentajesKilometraje findByTrainerIdAndDistancia(Long trainerId, int distancia);

    void actualizarPorcentajesComplexByTrainerIdAndDistancia(Long trainerId, int distancia, List<PorcentajeKilometrajeDTO> porcentajes);
}
