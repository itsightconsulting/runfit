 package com.itsight.service;

 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.itsight.domain.dto.ClienteDTO;

 import java.util.List;

 public interface ClienteProcedureInvoker {


  List<ClienteDTO> getDistribucionDepartamentoCliente(Integer trainerId);

  boolean actualizarClienteById(ClienteDTO cliente, Integer clienteId) throws JsonProcessingException;


 }
