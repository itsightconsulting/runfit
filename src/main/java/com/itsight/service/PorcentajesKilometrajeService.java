package com.itsight.service;

import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.domain.dto.PorcentajeKilometrajeDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface PorcentajesKilometrajeService extends BaseService<PorcentajesKilometraje, Integer> {

    PorcentajesKilometraje findByTrainerIdAndDistancia(Integer trainerId, int distancia);

    void actualizarPorcentajesComplexByTrainerIdAndDistancia(Integer trainerId, int distancia, List<PorcentajeKilometrajeDTO> porcentajes);
}
