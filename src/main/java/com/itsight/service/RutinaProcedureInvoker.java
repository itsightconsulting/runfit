package com.itsight.service;

import com.itsight.domain.pojo.RuCliPOJO;

public interface RutinaProcedureInvoker {

    RuCliPOJO getLastByClienteId(Integer clienteId, Integer limit);

    RuCliPOJO findById(Integer id);
}
