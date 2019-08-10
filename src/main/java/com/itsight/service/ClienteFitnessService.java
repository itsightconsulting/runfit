package com.itsight.service;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.ClienteFitness;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.dto.ClienteFitnessDTO;
import com.itsight.generic.BaseService;

public interface ClienteFitnessService extends BaseService<ClienteFitness, Integer> {

    String registrar(ClienteFitnessDTO clienteFitnessDto) throws CustomValidationException;

    ClienteFitness findByClienteId(Integer clienteId);

    void actualizarFull(ClienteDTO cliente, Integer clienteId) throws CustomValidationException;
}
