package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.ClienteFitness;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.dto.ClienteFitnessDTO;
import com.itsight.generic.BaseService;
import org.springframework.http.ResponseEntity;

public interface ClienteFitnessService extends BaseService<ClienteFitness, Integer> {

    String registrar(ClienteFitnessDTO clienteFitnessDto) throws CustomValidationException;

    ClienteFitness findByClienteId(Integer clienteId);

    ResponseEntity<String> actualizarFull(ClienteDTO cliente, Integer clienteId) throws CustomValidationException, JsonProcessingException;
}
