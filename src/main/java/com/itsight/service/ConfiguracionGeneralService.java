package com.itsight.service;

import com.itsight.domain.ConfiguracionGeneral;
import com.itsight.domain.dto.ConfiguracionClienteDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface ConfiguracionGeneralService extends BaseService<ConfiguracionGeneral, Integer> {

    List<ConfiguracionClienteDTO> getAll();
}
