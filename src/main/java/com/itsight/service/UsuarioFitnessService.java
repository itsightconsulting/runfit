package com.itsight.service;

import com.itsight.domain.UsuarioFitness;
import com.itsight.domain.dto.UsuarioFitnessDto;
import com.itsight.generic.BaseService;

public interface UsuarioFitnessService extends BaseService<UsuarioFitness> {

    String registrar(UsuarioFitnessDto usuarioFitnessDto);

    UsuarioFitness findByUsuarioId(int usuarioId);
}
