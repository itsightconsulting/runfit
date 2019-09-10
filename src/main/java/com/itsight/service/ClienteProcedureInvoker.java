 package com.itsight.service;

 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.itsight.domain.dto.ClienteDTO;
 import com.itsight.domain.pojo.TycClientePOJO;

 import java.util.List;

 public interface ClienteProcedureInvoker {


  List<ClienteDTO> getDistribucionDepartamentoCliente(Integer trainerId);

  boolean actualizarClienteById(ClienteDTO cliente, Integer clienteId) throws JsonProcessingException;

  List<TycClientePOJO> getTycServiciosById(Integer clienteId);


 }
