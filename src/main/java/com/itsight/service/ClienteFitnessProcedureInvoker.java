package com.itsight.service;

import com.itsight.domain.pojo.ClienteFitnessPOJO;

import java.util.List;

public interface ClienteFitnessProcedureInvoker {

    public ClienteFitnessPOJO getById(Integer id);
    public List<ClienteFitnessPOJO> getDistribucionMercado(Integer id);


}
