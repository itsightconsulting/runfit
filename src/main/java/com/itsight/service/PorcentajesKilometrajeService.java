package com.itsight.service;

import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.domain.dto.PorcentajeKilometrajeDto;
import com.itsight.generic.BaseService;

import java.util.List;

public interface PorcentajesKilometrajeService extends BaseService<PorcentajesKilometraje> {

    List<PorcentajesKilometraje> findAllByUsuarioId(int trainerId);

    PorcentajesKilometraje findByTrainerIdAndDistancia(int trainerId, int parseInt);

    void actualizarPorcentajesComplexByTrainerIdAndDistancia(int trainerId, int distancia, List<PorcentajeKilometrajeDto> porcentajes);
}
