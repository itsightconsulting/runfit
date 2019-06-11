package com.itsight.service;

import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.RedFitCliDTO;

import java.util.List;

public interface RedFitnessProcedureInvoker {

    List<RedFitCliDTO> findAllByNombreDynamic(Integer trainerId, String nombreCompleto, QueryParamsDTO queryParams);

}
