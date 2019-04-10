package com.itsight.service;

import com.itsight.domain.ClienteFitness;
import com.itsight.domain.dto.ClienteFitnessDTO;
import com.itsight.generic.BaseService;

public interface ClienteFitnessService extends BaseService<ClienteFitness, Integer> {

    String registrar(ClienteFitnessDTO clienteFitnessDto);

    ClienteFitness findByClienteId(Integer clienteId);

}
