package com.itsight.service;

import com.itsight.domain.ClientePlan;

import java.util.List;

public interface ClientePlanService {

    List<ClientePlan> listAll();

    ClientePlan add(ClientePlan clientePlan);

    ClientePlan update(ClientePlan clientePlan);

    void delete(int clientePlanId);

    ClientePlan findOneById(int id);
}
