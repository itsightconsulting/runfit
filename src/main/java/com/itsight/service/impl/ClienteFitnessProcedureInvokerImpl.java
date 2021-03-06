package com.itsight.service.impl;

import com.itsight.domain.pojo.ClienteFitnessPOJO;
import com.itsight.service.ClienteFitnessProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
@Transactional


public class ClienteFitnessProcedureInvokerImpl implements ClienteFitnessProcedureInvoker {

    @Autowired
    private EntityManager entityManager;

    @Override
    public ClienteFitnessPOJO getById(Integer id) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_cliente_fitness_q_by_id", "resultMappingClienteFitness");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, id);
        return (ClienteFitnessPOJO) storedProcedureQuery.getSingleResult();
    }

    @Override
    public List<ClienteFitnessPOJO> getDistribucionMercado(Integer id) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_distr_mercado_q_dynamic_where", "resultMappingClienteFitnessDistribucion");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, id);
        return  storedProcedureQuery.getResultList();
    }


    @Override
    public List<ClienteFitnessPOJO> getDistribucionMercadoEmpresa(Integer id) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_distr_mercado_empresa_q_dynamic_where", "resultMappingClienteFitnessDistribucion");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, id);
        return  storedProcedureQuery.getResultList();
    }


}
