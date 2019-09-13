package com.itsight.service;

import com.itsight.domain.dto.ConfiguracionClienteDTO;

import java.util.List;

public interface ConfiguracionClienteProcedureInvoker {

    List<ConfiguracionClienteDTO> getAllById(Integer clienteId);
}
