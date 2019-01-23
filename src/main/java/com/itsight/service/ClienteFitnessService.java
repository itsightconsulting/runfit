package com.itsight.service;

import com.itsight.domain.ClienteFitness;
import com.itsight.domain.dto.ClienteFitnessDto;
import com.itsight.generic.BaseService;

public interface ClienteFitnessService extends BaseService<ClienteFitness> {

    String registrar(ClienteFitnessDto clienteFitnessDto);

    ClienteFitness findByClienteId(int clienteId);
}
