package com.itsight.service;

import com.itsight.domain.dto.ClienteDTO;

import java.util.List;

public interface ClienteProcedureInvoker {


 List<ClienteDTO> getDistribucionDepartamentoCliente();

 void actualizarClienteById(ClienteDTO cliente, Integer clienteId);


}
