package com.itsight.service;

import com.itsight.domain.CondicionMejora;
import com.itsight.domain.dto.CondicionMejoraDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface CondicionMejoraService extends BaseService<CondicionMejora, Integer> {

    List<CondicionMejoraDTO> getAll();
}
